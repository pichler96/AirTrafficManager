import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.vocabulary.RDF;
import org.opensky.model.StateVector;
import java.util.List;

public class RDFConverter {
    /*
    @prefix aircraft:
@prefix state:
@prefix response:
    * */

    static void convertStaticData(List<Aircraft> aircrafts) {
        Model model = ModelFactory.createDefaultModel();
        String aircraftUri = "http://aircraft/aircraft#";


        Property hasIcao = model.createProperty("http://aircraft/hasIcao");
        Property hasRegistration = model.createProperty("http://aircraft/hasRegistration");
        Property hasManufacturer = model.createProperty("http://aircraft/hasManufacturer");
        Property hasAircraftModel = model.createProperty("http://aircraft/hasAircraftModel");
        Property hasTypeCode = model.createProperty("http://aircraft/hasTypeCode");
        Property hasSerialNumber = model.createProperty("http://aircraft/hasSerialNumber");
        Property hasIcaoAircraftType = model.createProperty("http://aircraft/hasIcaoAircraftType");
        Property hasRegistered = model.createProperty("http://aircraft/hasRegistered");
        Property hasRegUntil = model.createProperty("http://aircraft/hasRegUntil");
        Property hasBuilt = model.createProperty("http://aircraft/hasBuilt");
        Property hasFirstFlightDate = model.createProperty("http://aircraft/hasFirstFlightDate");
        Property hasModes = model.createProperty("http://aircraft/hasModes");
        Property hasAdsb = model.createProperty("http://aircraft/hasAdsb");
        Property hasAcars = model.createProperty("http://aircraft/hasAcars");
        Property hasNotes = model.createProperty("http://aircraft/hasnotes");
        Property hasCategoryDescription = model.createProperty("http://aircraft/hasCategoryDescription");
        Property hasOperator = model.createProperty("http://aircraft/hasOperator");
        Property hasOwner = model.createProperty("http://aircraft/hasOwner");
        Property hasEngine = model.createProperty("http://aircraft/hasEnginer");

        for (Aircraft aircraft : aircrafts) {
            //TODO: Remove Strings [Open -> mit Paul besprechen]
            Resource aircraftData = model.createResource(aircraftUri + aircraft.getIcao());
            if (aircraft.getIcao() != null) aircraftData.addProperty(hasIcao, String.valueOf(aircraft.getIcao()));
            if (aircraft.getRegistration() != null) aircraftData.addProperty(hasRegistration, String.valueOf(aircraft.getRegistration()));
            if (aircraft.getManufacturer().getIcao() != null) aircraftData.addProperty(hasManufacturer, String.valueOf(aircraft.getManufacturer().getIcao()));
            if (aircraft.getModel() != null) aircraftData.addProperty(hasAircraftModel, String.valueOf(aircraft.getModel()));
            if (aircraft.getTypeCode() != null) aircraftData.addProperty(hasTypeCode, String.valueOf(aircraft.getTypeCode()));
            if (aircraft.getSerialNumber() != null) aircraftData.addProperty(hasSerialNumber, String.valueOf(aircraft.getSerialNumber()));
            if (aircraft.getIcaoAircraftType() != null) aircraftData.addProperty(hasIcaoAircraftType, String.valueOf(aircraft.getIcaoAircraftType()));
            if (aircraft.getRegistered() != null) aircraftData.addProperty(hasRegistered, String.valueOf(aircraft.getRegistered()));
            if (aircraft.getRegUntil() != null) aircraftData.addProperty(hasRegUntil, String.valueOf(aircraft.getRegUntil()));
            if (aircraft.getBuilt() != null) aircraftData.addProperty(hasBuilt, String.valueOf(aircraft.getBuilt()));
            if (aircraft.getFirstFlightDate() != null) aircraftData.addProperty(hasFirstFlightDate, String.valueOf(aircraft.getFirstFlightDate()));
            if (aircraft.isModes() != null) aircraftData.addProperty(hasModes, String.valueOf(aircraft.isModes()));
            if (aircraft.isAdsb() != null) aircraftData.addProperty(hasAdsb, String.valueOf(aircraft.isAdsb()));
            if (aircraft.isAcars() != null) aircraftData.addProperty(hasAcars, String.valueOf(aircraft.isAcars()));
            if (aircraft.getNotes() != null) aircraftData.addProperty(hasNotes, String.valueOf(aircraft.getNotes()));
            if (aircraft.getCategoryDescription() != null) aircraftData.addProperty(hasCategoryDescription, String.valueOf(aircraft.getCategoryDescription()));
            if (aircraft.getOperator().getIcao() != null) aircraftData.addProperty(hasOperator, String.valueOf(aircraft.getOperator().getIcao()));
            if (aircraft.getOwner() != null) aircraftData.addProperty(hasOwner, String.valueOf(aircraft.getOwner()));
            if (aircraft.getEngine() != null) aircraftData.addProperty(hasEngine, String.valueOf(aircraft.getEngine()));
            model.add(aircraftData, RDF.type, "Aircraft");
            
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("state-shacl.ttl"));
        if (ShaclValidator.get().validate(shapes, model.getGraph()).conforms()) {
            System.out.println("SHACL VALIDATION (STATIC) SUCCESSFUL");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/StaticData") ) {
                conn.load("http://localhost:3030/StaticData", model);
            } catch (Exception ignored) {}
            //model.write(System.out, "TURTLE");
        } else {
            System.out.println("SHACL VALIDATION NOT (STATIC) SUCCESSFUL");
        }
    }

    static void convertDynamicData(List<State> states){
        Model model = ModelFactory.createDefaultModel();
        String stateURI = "http://aircraft/state#";

        model.setNsPrefix("sh" , "http://www.w3.org/ns/shacl#");
        model.setNsPrefix("xsd" , "http://www.w3.org/2022/example#");
        model.setNsPrefix("ex" , "http://www.w3.org/2022/example#");
        model.setNsPrefix("rdf" , "http://www.w3.org/1999/02/22-rdf-syntax-ns#");

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


            model.add(flightState, RDF.type, "State");
            //TODO attributsname in shacl ändern (hasicao24 auf hasicao) [Lukas]
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("state-shacl.ttl"));
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

