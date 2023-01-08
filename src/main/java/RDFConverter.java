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
        Property hasNotes = model.createProperty(ex_URL+"hasnotes");
        Property hasCategoryDescription = model.createProperty(ex_URL+"hasCategoryDescription");
        Property hasOperator = model.createProperty(ex_URL+"hasOperator");
        Property hasOwner = model.createProperty(ex_URL+"hasOwner");
        Property hasEngine = model.createProperty(ex_URL+"hasEngine");

        for (Aircraft aircraft : aircrafts) {
            //TODO: Remove Strings [Open -> mit Paul besprechen]
            Resource aircraftData = model.createResource(aircraftUri + aircraft.getIcao());
            if (aircraft.getIcao() != null) aircraftData.addLiteral(hasIcao, aircraft.getIcao());
            if (aircraft.getRegistration() != null) aircraftData.addLiteral(hasRegistration, aircraft.getRegistration());
            if (aircraft.getManufacturer().getIcao() != null) aircraftData.addLiteral(hasManufacturer, aircraft.getManufacturer());
            if (aircraft.getModel() != null) aircraftData.addLiteral(hasAircraftModel, aircraft.getModel());
            if (aircraft.getTypeCode() != null) aircraftData.addLiteral(hasTypeCode, aircraft.getTypeCode());
            if (aircraft.getSerialNumber() != null) aircraftData.addLiteral(hasSerialNumber, aircraft.getSerialNumber());
            if (aircraft.getIcaoAircraftType() != null) aircraftData.addLiteral(hasIcaoAircraftType, aircraft.getIcaoAircraftType());
            if (aircraft.getRegistered() != null) aircraftData.addLiteral(hasRegistered, aircraft.getRegistered());
            if (aircraft.getRegUntil() != null) aircraftData.addLiteral(hasRegUntil, aircraft.getRegUntil());
            if (aircraft.getBuilt() != null) aircraftData.addLiteral(hasBuilt, aircraft.getBuilt());
            if (aircraft.getFirstFlightDate() != null) aircraftData.addLiteral(hasFirstFlightDate, aircraft.getFirstFlightDate());
            if (aircraft.isModes() != null) aircraftData.addLiteral(hasModes, aircraft.isModes());
            if (aircraft.isAdsb() != null) aircraftData.addLiteral(hasAdsb, aircraft.isAdsb());
            if (aircraft.isAcars() != null) aircraftData.addLiteral(hasAcars, aircraft.isAcars());
            if (aircraft.getNotes() != null) aircraftData.addLiteral(hasNotes, aircraft.getNotes());
            if (aircraft.getCategoryDescription() != null) aircraftData.addLiteral(hasCategoryDescription, aircraft.getCategoryDescription());
            if (aircraft.getOperator().getIcao() != null) aircraftData.addLiteral(hasOperator, aircraft.getOperator());
            if (aircraft.getOwner() != null) aircraftData.addLiteral(hasOwner, aircraft.getOwner());
            if (aircraft.getEngine() != null) aircraftData.addLiteral(hasEngine, aircraft.getEngine());
            aircraftData.addProperty(RDF.type, model.createProperty("http://www.w3.org/2022/example#Aircraft"));
            
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("aircraft-shacl.ttl"));
        System.out.println(model);
        System.out.println(shapes.getGraph());
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


        Property hasBaroAltitude = model.createProperty("http://aircraft/hasBaroAltitude");
        Property hasGeoAltitude = model.createProperty("http://aircraft/hasGeoAltitude");
        Property hasVelocity = model.createProperty("http://aircraft/hasVelocity");
        Property hasLastContact = model.createProperty("http://aircraft/hasLastContact");
        Property hasLastPositionUpdate = model.createProperty("http://aircraft/hasLastPositionUpdate");
        Property hasOnGround = model.createProperty("http://aircraft/hasOnGround");
        Property hasOriginCountry = model.createProperty("http://aircraft/hasOriginCountry");
        Property hasLatitude = model.createProperty("http://aircraft/hasLatitude");
        Property hasLongitude = model.createProperty("http://aircraft/hasLongitude");
        Property hasHeading = model.createProperty("http://aircraft/hasHeading");
        Property hasVerticalRate = model.createProperty("http://aircraft/hasVerticalRate");
        Property hasIcao24 = model.createProperty("http://aircraft/hasIcao24");
        Property hasCallsign = model.createProperty("http://aircraft/hasCallsign");
        Property hasSquawk = model.createProperty("http://aircraft/hasSquawk");
        Property hasSpi = model.createProperty("http://aircraft/hasSpi");
        Property hasPositionSource = model.createProperty("http://aircraft/hasPositionSource");
        Property hasSerials = model.createProperty("http://aircraft/hasSerials");
        Property hasResponse = model.createProperty("http://aircraft/hasResponse");

        for (State state : states) {
            //TODO: Remove Strings [Open -> mit Paul besprechen]
            Resource flightState = model.createResource(stateURI+state.getIcao24());
            if (state.getBaroAltitude() != null) flightState.addProperty(hasBaroAltitude, String.valueOf(state.getBaroAltitude()));
            if (state.getGeoAltitude() != null) flightState.addProperty(hasGeoAltitude, String.valueOf(state.getGeoAltitude()));
            if (state.getVelocity() != null) flightState.addProperty(hasVelocity, String.valueOf(state.getVelocity()));
            if (state.getLastContact() != null) flightState.addProperty(hasLastContact, String.valueOf(state.getLastContact()));
            if (state.getLastPositionUpdate() != null) flightState.addProperty(hasLastPositionUpdate, String.valueOf(state.getLastPositionUpdate()));
            if (String.valueOf(state.isOnGround()) != null) flightState.addProperty(hasOnGround, String.valueOf(state.isOnGround()));
            if (state.getOriginCountry() != null) flightState.addProperty(hasOriginCountry, String.valueOf(state.getOriginCountry()));
            if (state.getLatitude() != null) flightState.addProperty(hasLatitude, String.valueOf(state.getLatitude()));
            if (state.getLongitude() != null) flightState.addProperty(hasLongitude, String.valueOf(state.getLongitude()));
            if (state.getHeading() != null) flightState.addProperty(hasHeading, String.valueOf(state.getHeading()));
            if (state.getVerticalRate() != null) flightState.addProperty(hasVerticalRate, String.valueOf(state.getVerticalRate()));
            if (state.getIcao24() != null) flightState.addProperty(hasIcao24, String.valueOf(state.getIcao24()));
            if (state.getCallsign() != null) flightState.addProperty(hasCallsign, String.valueOf(state.getCallsign()));
            if (state.getSquawk() != null) flightState.addProperty(hasSquawk, String.valueOf(state.getSquawk()));
            if (String.valueOf(state.isSpi()) != null) flightState.addProperty(hasSpi, String.valueOf(state.isSpi()));
            if (state.getPositionSource() != null) flightState.addProperty(hasPositionSource, String.valueOf(state.getPositionSource()));
            if (state.getSerials() != null) flightState.addProperty(hasSerials, String.valueOf(state.getSerials()));
            if (state.getResponse() != null) flightState.addProperty(hasResponse, String.valueOf(state.getResponse()));


            flightState.addProperty(RDF.type, model.createProperty("http://www.w3.org/2022/example#State"));
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
        }
    }
}

