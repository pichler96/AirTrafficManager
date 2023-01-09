import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.vocabulary.RDF;

import java.util.List;

public class RDFConverter {

    static String sh_URL = "http://www.w3.org/ns/shacl#";
    static String xsd_URL = "http://www.w3.org/2022/example#";
    static String ex_URL = "http://www.w3.org/2022/example#";
    static String rdf_URL = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static String aircraft_URL = "http://www.dke-pr/aircraft#";
    static String state_URL = "http://www.dke-pr/aircraft/state#";
    static String response_URL = "http://www.dke-pr/aircraft/response#";

    /*
    @prefix aircraft:
    @prefix state:
    @prefix response:
    * */

    static void convertStaticData(List<Aircraft> aircrafts) {
        Model model = ModelFactory.createDefaultModel();
        String aircraftUri = "http://www.dke-pr/aircraft#";

        model.setNsPrefix("sh" , sh_URL);
        model.setNsPrefix("xsd" , xsd_URL);
        model.setNsPrefix("ex" , ex_URL);
        model.setNsPrefix("rdf" , rdf_URL);
        model.setNsPrefix("aircraft", aircraft_URL);
        model.setNsPrefix("state", state_URL);
        model.setNsPrefix("response", response_URL);


        Property hasIcao = model.createProperty(ex_URL+"hasIcao24");
        Property hasRegistration = model.createProperty(ex_URL +"hasRegistration");
        Property hasManufacturer = model.createProperty(ex_URL +"hasManufacturer");
        Property hasAircraftModel = model.createProperty(ex_URL+"hasAircraftModel");
        Property hasTypeCode = model.createProperty(ex_URL+"hasTypeCode");
        Property hasSerialNumber = model.createProperty(ex_URL+"hasSerialNumber");
        Property hasIcaoAircraftType = model.createProperty(ex_URL+"hasIcaoAircraftType");
        Property hasRegistered = model.createProperty(ex_URL+"hasRegistered");
        Property hasRegUntil = model.createProperty(ex_URL+"hasRegUntil");
        Property hasBuilt = model.createProperty(ex_URL+"hasBuilt");
        Property hasFirstFlightDate = model.createProperty(ex_URL+"hasFirstFlightDate");
        Property hasModes = model.createProperty(ex_URL+"hasModes");
        Property hasAdsb = model.createProperty(ex_URL+"hasAdsb");
        Property hasAcars = model.createProperty(ex_URL+"hasAcars");
        Property hasNotes = model.createProperty(ex_URL+"hasNotes");
        Property hasCategoryDescription = model.createProperty(ex_URL+"hasCategoryDescription");
        Property hasOperator = model.createProperty(ex_URL+"hasOperator");
        Property hasOwner = model.createProperty(ex_URL+"hasOwner");
        Property hasEngine = model.createProperty(ex_URL+"hasEngine");

        for (Aircraft aircraft : aircrafts) {
            //TODO: Remove Strings [Open -> mit Paul besprechen]
            Resource aircraftData = model.createResource(aircraftUri + aircraft.getIcao());
            if (aircraft.getIcao() != null || aircraft.getIcao() == "") aircraftData.addLiteral(hasIcao, aircraft.getIcao());
            if (aircraft.getRegistration() != null || aircraft.getRegistration() == "") aircraftData.addLiteral(hasRegistration, aircraft.getRegistration());
            if (aircraft.getManufacturer().getIcao() != null ||aircraft.getManufacturer().getIcao() =="") aircraftData.addLiteral(hasManufacturer, aircraft.getManufacturer());
            if (aircraft.getModel() != null || aircraft.getModel() =="") aircraftData.addLiteral(hasAircraftModel, aircraft.getModel());
            if (aircraft.getTypeCode() != null || aircraft.getTypeCode() =="") aircraftData.addLiteral(hasTypeCode, aircraft.getTypeCode());
            if (aircraft.getSerialNumber() != null|| aircraft.getSerialNumber() =="") aircraftData.addLiteral(hasSerialNumber, aircraft.getSerialNumber());
            if (aircraft.getIcaoAircraftType() != null ||aircraft.getIcaoAircraftType() =="") aircraftData.addLiteral(hasIcaoAircraftType, aircraft.getIcaoAircraftType());
            if (aircraft.getRegistered() != null ||aircraft.getRegistered() =="") aircraftData.addLiteral(hasRegistered, aircraft.getRegistered());
            if (aircraft.getRegUntil() != null ||aircraft.getRegUntil() =="") aircraftData.addLiteral(hasRegUntil, aircraft.getRegUntil());
            if (aircraft.getBuilt() != null || aircraft.getBuilt() =="") aircraftData.addLiteral(hasBuilt, aircraft.getBuilt());
            if (aircraft.getFirstFlightDate() != null ||aircraft.getFirstFlightDate()=="") aircraftData.addLiteral(hasFirstFlightDate, aircraft.getFirstFlightDate());
            if (aircraft.isModes() != null) aircraftData.addLiteral(hasModes, aircraft.isModes());
            if (aircraft.isAdsb() != null) aircraftData.addLiteral(hasAdsb, aircraft.isAdsb());
            if (aircraft.isAcars() != null) aircraftData.addLiteral(hasAcars, aircraft.isAcars());
            if (aircraft.getNotes() != null || aircraft.getNotes() == "") aircraftData.addLiteral(hasNotes, aircraft.getNotes());
            if (aircraft.getCategoryDescription() != null || aircraft.getCategoryDescription() =="") aircraftData.addLiteral(hasCategoryDescription, aircraft.getCategoryDescription());
            if (aircraft.getOperator().getIcao() != null ||aircraft.getOperator().getIcao() =="") aircraftData.addLiteral(hasOperator, aircraft.getOperator());
            if (aircraft.getOwner() != null ||aircraft.getOwner()=="") aircraftData.addLiteral(hasOwner, aircraft.getOwner());
            if (aircraft.getEngine() != null || aircraft.getEngine() =="") aircraftData.addLiteral(hasEngine, aircraft.getEngine());
            aircraftData.addProperty(RDF.type, model.createProperty(ex_URL+"Aircraft"));
            
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("aircraft-shacl.ttl"));


        if (ShaclValidator.get().validate(shapes, model.getGraph()).conforms()) {
            System.out.println("SHACL VALIDATION (STATIC) SUCCESSFUL");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/StaticData") ) {
                conn.load("http://localhost:3030/StaticData", model);
            } catch (Exception ignored) {}
            //model.write(System.out, "TURTLE");
        } else {
            System.out.println("SHACL VALIDATION NOT (STATIC) SUCCESSFUL");
            //System.out.println(ShaclValidator.get().validate(shapes, model.getGraph()).getGraph());
            RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
        }
    }

    static void convertDynamicData(List<State> states){
        Model model = ModelFactory.createDefaultModel();
        String stateURI = "http://www.dke-pr/state#";


        model.setNsPrefix("sh" , "http://www.w3.org/ns/shacl#");
        model.setNsPrefix("xsd" , "http://www.w3.org/2022/example#");
        model.setNsPrefix("ex" , "http://www.w3.org/2022/example#");
        model.setNsPrefix("rdf" , "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        model.setNsPrefix("aircraft", "http://www.dke-pr/aircraft#");
        model.setNsPrefix("state", "http://www.dke-pr/aircraft/state#");
        model.setNsPrefix("response", "http://www.dke-pr/aircraft/response#");


        Property hasBaroAltitude = model.createProperty(ex_URL+"hasBaroAltitude");
        Property hasGeoAltitude = model.createProperty(ex_URL+"hasGeoAltitude");
        Property hasVelocity = model.createProperty(ex_URL+"hasVelocity");
        Property hasLastContact = model.createProperty(ex_URL+"hasLastContact");
        Property hasLastPositionUpdate = model.createProperty(ex_URL+"hasLastPositionUpdate");
        Property hasOnGround = model.createProperty(ex_URL+"hasOnGround");
        Property hasOriginCountry = model.createProperty(ex_URL+"hasOriginCountry");
        Property hasLatitude = model.createProperty(ex_URL+"hasLatitude");
        Property hasLongitude = model.createProperty(ex_URL+"hasLongitude");
        Property hasHeading = model.createProperty(ex_URL+"hasHeading");
        Property hasVerticalRate = model.createProperty(ex_URL+"hasVerticalRate");
        Property hasIcao24 = model.createProperty(ex_URL+"hasIcao24");
        Property hasCallsign = model.createProperty(ex_URL+"hasCallsign");
        Property hasSquawk = model.createProperty(ex_URL+"hasSquawk");
        Property hasSpi = model.createProperty(ex_URL+"hasSpi");
        Property hasPositionSource = model.createProperty(ex_URL+"hasPositionSource");
        Property hasSerials = model.createProperty(ex_URL+"hasSerials");
        Property hasResponse = model.createProperty(ex_URL+"hasResponse");

        for (State state : states) {
            //TODO: Remove Strings [Open -> mit Paul besprechen]
            Resource flightState = model.createResource(stateURI+state.getIcao24());
            if (state.getBaroAltitude() != null) flightState.addLiteral(hasBaroAltitude, state.getBaroAltitude());
            if (state.getGeoAltitude() != null) flightState.addLiteral(hasGeoAltitude, state.getGeoAltitude());
            if (state.getVelocity() != null) flightState.addLiteral(hasVelocity, state.getVelocity());
            if (state.getLastContact() != null) flightState.addLiteral(hasLastContact, state.getLastContact());
            if (state.getLastPositionUpdate() != null) flightState.addLiteral(hasLastPositionUpdate, state.getLastPositionUpdate());
            if (String.valueOf(state.isOnGround()) != null) flightState.addLiteral(hasOnGround, state.isOnGround());
            if (state.getOriginCountry() != null) flightState.addLiteral(hasOriginCountry, state.getOriginCountry());
            if (state.getLatitude() != null) flightState.addLiteral(hasLatitude, state.getLatitude());
            if (state.getLongitude() != null) flightState.addLiteral(hasLongitude, state.getLongitude());
            if (state.getHeading() != null) flightState.addLiteral(hasHeading, state.getHeading());
            if (state.getVerticalRate() != null) flightState.addLiteral(hasVerticalRate, state.getVerticalRate());
            if (state.getIcao24() != null) flightState.addLiteral(hasIcao24, state.getIcao24());
            if (state.getCallsign() != null) flightState.addLiteral(hasCallsign, state.getCallsign());
            if (state.getSquawk() != null) flightState.addLiteral(hasSquawk, state.getSquawk());
            if (String.valueOf(state.isSpi()) != null) flightState.addLiteral(hasSpi, state.isSpi());
            if (state.getPositionSource() != null) flightState.addLiteral(hasPositionSource, state.getPositionSource());
            if (state.getSerials() != null) flightState.addLiteral(hasSerials, state.getSerials());
            if (state.getResponse() != null) flightState.addLiteral(hasResponse, state.getResponse().getTime());


            flightState.addProperty(RDF.type, model.createProperty(ex_URL+"State"));
            //TODO attributsname in shacl ändern (hasicao24 auf hasicao) [Lukas]
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("state-shacl.ttl"));
        //System.out.println(model);
        //System.out.println(shapes.getGraph());
        if (ShaclValidator.get().validate(shapes, model.getGraph()).conforms()) {
            //TODO check for invalid shacl shapes [Arion]
            //TODO für jeden aufruf neuen dynamischen graph erstellen
            //TODO bei named graph time dynamisch an String anhängen (außerhalb der Iteration)
            System.out.println("SHACL VALIDATION (DYNAMIC) SUCCESSFUL");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/DynamicData") ) {
                //TODO [Arion]:
                conn.load("http://localhost:3030/DynamicData",model);
            }
            catch (Exception err) {}
            //model.write(System.out, "TURTLE");
        } else {
            System.out.println("SHACL VALIDATION NOT (DYNAMIC) SUCCESSFUL");
            RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
        }
    }
}

