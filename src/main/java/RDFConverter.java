import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.vocabulary.RDF;
import org.opensky.model.StateVector;

import java.util.List;

public class RDFConverter {
    static void convertStaticData(List<Aircraft> aircrafts) {
        Model model = ModelFactory.createDefaultModel();

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
        Property hasEngine = model.createProperty("http://aircraft/hasEngine");
        Property hasLatestState = model.createProperty("http://aircraft/hasLatestState");

        for (Aircraft aircraft : aircrafts) {
            String aircraftUri = "http://aircraft/aircraft#";
            Resource AircraftData = model.createResource(aircraftUri+aircraft.getIcao())
                    .addProperty(hasIcao, String.valueOf(aircraft.getIcao()))
                    .addProperty(hasRegistration, String.valueOf(aircraft.getRegistration()))
                    .addProperty(hasManufacturer, String.valueOf(aircraft.getManufacturer().getIcao()))
                    .addProperty(hasAircraftModel, String.valueOf(aircraft.getModel()))
                    .addProperty(hasTypeCode, String.valueOf(aircraft.getTypeCode()))
                    .addProperty(hasSerialNumber, String.valueOf(aircraft.getSerialNumber()))
                    .addProperty(hasIcaoAircraftType, String.valueOf(aircraft.getIcaoAircraftType()))
                    .addProperty(hasRegistered, String.valueOf(aircraft.getRegistered()))
                    .addProperty(hasRegUntil, String.valueOf(aircraft.getRegUntil()))
                    .addProperty(hasBuilt, String.valueOf(aircraft.getBuilt()))
                    .addProperty(hasFirstFlightDate, String.valueOf(aircraft.getFirstFlightDate()))
                    .addProperty(hasModes, String.valueOf(aircraft.isModes()))
                    .addProperty(hasAdsb, String.valueOf(aircraft.isAdsb()))
                    .addProperty(hasAcars, String.valueOf(aircraft.isAcars()))
                    .addProperty(hasNotes, String.valueOf(aircraft.getNotes()))
                    .addProperty(hasCategoryDescription, String.valueOf(aircraft.getCategoryDescription()))
                    .addProperty(hasOperator, String.valueOf(aircraft.getOperator().getIcao()))
                    .addProperty(hasOwner, String.valueOf(aircraft.getOwner()))
                    .addProperty(hasEngine, String.valueOf(aircraft.getEngine()));
            model.add(AircraftData, RDF.type, "Aircraft");
        }
        try ( RDFConnection conn = RDFConnection.connect("http://localhost:3030/Test") ) {
            conn.load(model);
        }
        model.write(System.out, "TURTLE");
    }

    static void convertDynamicData(List<Aircraft> aircrafts){
        String flightURI = "http://aircraft/flight#";
        Model model = ModelFactory.createDefaultModel();

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

        for (Aircraft aircraft : aircrafts) {
            if(aircraft.states.isEmpty()) break;
            StateVector state = aircraft.states.get(aircraft.states.size()-1);

            Resource FlightState = model.createResource(flightURI+state.getIcao24())
                    .addProperty(hasBaroAltitude, String.valueOf(state.getBaroAltitude()))
                    .addProperty(hasGeoAltitude, String.valueOf(state.getGeoAltitude()))
                    .addProperty(hasVelocity, String.valueOf(state.getVelocity()))
                    .addProperty(hasLastContact, String.valueOf(state.getLastContact()))
                    .addProperty(hasLastPositionUpdate, String.valueOf(state.getLastPositionUpdate()))
                    .addProperty(hasOnGround, String.valueOf(state.isOnGround()))
                    .addProperty(hasOriginCountry, String.valueOf(state.getOriginCountry()))
                    .addProperty(hasLatitude, String.valueOf(state.getLatitude()))
                    .addProperty(hasLongitude, String.valueOf(state.getLongitude()))
                    .addProperty(hasHeading, String.valueOf(state.getHeading()))
                    .addProperty(hasVerticalRate, String.valueOf(state.getVerticalRate()))
                    .addProperty(hasIcao24, String.valueOf(state.getIcao24()))
                    .addProperty(hasCallsign, String.valueOf(state.getCallsign()))
                    .addProperty(hasSquawk, String.valueOf(state.getSquawk()))
                    .addProperty(hasSpi, String.valueOf(state.isSpi()))
                    .addProperty(hasPositionSource, String.valueOf(state.getPositionSource()))
                    .addProperty(hasSerials, String.valueOf(state.getSerials()));
            model.add(FlightState, RDF.type, "FlightState");
        }
        try ( RDFConnection conn = RDFConnection.connect("http://localhost:3030/Test") ) {
            conn.load(model);
        }
        model.write(System.out, "TURTLE");
    }
}

