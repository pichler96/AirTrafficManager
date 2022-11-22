import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.shacl.validation.ShaclPlainValidator;
import org.apache.jena.shacl.vocabulary.SHACL;
import org.apache.jena.vocabulary.RDF;
import org.opensky.model.StateVector;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RDFConverter {






    static void convertToRDF(List<Aircraft> aircrafts) {

        // create an empty Model

        //TODO add properties to model instead of resource
        //TODO load model into fuseki

        //FusekiRemoteConnection(); zum laden des aktuellen States in einen named Graph




        //create class URI resource
        //Resource Aircraft = ResourceFactory.createResource("URI");



        //model.add()
        //RDF.type
        //model.add(firstAircraft, RDF.type, Aircraft);

        //Validation through SHACL with method call

        //load static and dynamic data into seperate named graphs


        // create the resource
        //TODO deal with null
        //TODO generate properties first, use later










        /*
        StmtIterator iter = model.listStatements();

        // print out the predicate, subject and object of each statement
        while (iter.hasNext()) {
            Statement stmt      = iter.nextStatement();  // get next statement
            Resource  subject   = stmt.getSubject();     // get the subject
            Property  predicate = stmt.getPredicate();   // get the predicate
            RDFNode   object    = stmt.getObject();      // get the object

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                // object is a literal
                System.out.print(" \"" + object.toString() + "\"");
            }
            System.out.println(" .");
        }
        */
    }

    static Model convertStaticData(List<Aircraft> aircrafts){


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
                    .addProperty(hasIcao, aircraft.getIcao())
                    .addProperty(hasRegistration, aircraft.getRegistration())
                    .addProperty(hasManufacturer, aircraft.getManufacturer().getIcao())
                    .addProperty(hasAircraftModel, aircraft.getAircraftType())
                    .addProperty(hasTypeCode, aircraft.getTypeCode())
                    .addProperty(hasSerialNumber, aircraft.getSerialNumber())
                    .addProperty(hasIcaoAircraftType, aircraft.getIcaoAircraftType())
                    .addProperty(hasRegistered, aircraft.getRegistered().toString())
                    .addProperty(hasRegUntil, aircraft.getRegUntil().toString())
                    .addProperty(hasBuilt, aircraft.getBuilt().toString())
                    .addProperty(hasFirstFlightDate, aircraft.getFirstFlightDate().toString())
                    .addProperty(hasModes, String.valueOf(aircraft.isModes()))
                    .addProperty(hasAdsb, String.valueOf(aircraft.isAdsb()))
                    .addProperty(hasAcars, String.valueOf(aircraft.isAcars()))
                    .addProperty(hasNotes, aircraft.getNotes())
                    .addProperty(hasCategoryDescription, aircraft.getCategoryDescription())
                    .addProperty(hasOperator, aircraft.getOperator().getIcao())
                    .addProperty(hasOwner, aircraft.getOwner())
                    .addProperty(hasEngine, aircraft.getEngine());

            model.add(AircraftData, RDF.type, "Aircraft");
            //TODO deal with null
            if(aircraft.getStates().size() == 0) {
                AircraftData.addProperty(hasLatestState, "null");
            }
            else {
                AircraftData.addProperty(hasLatestState, aircraft.getStates().get(aircraft.getStates().size()-1).getIcao24());
            }
        }


        return model;
    }

    static Model convertDynamicData(List<Aircraft> aircrafts){

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

            StateVector state = aircraft.getStates().get(aircrafts.size()-1);

            Resource flight = model.createResource(flightURI+state.getIcao24())
                    .addProperty(hasBaroAltitude,String.valueOf(state.getBaroAltitude()))
                    .addProperty(hasGeoAltitude,String.valueOf(state.getGeoAltitude()))
                    .addProperty(hasVelocity,String.valueOf(state.getVelocity()))
                    .addProperty(hasLastContact,String.valueOf(state.getLastContact()))
                    .addProperty(hasLastPositionUpdate,String.valueOf(state.getLastPositionUpdate()))
                    .addProperty(hasOnGround,String.valueOf(state.isOnGround()))
                    .addProperty(hasOriginCountry,String.valueOf(state.getOriginCountry()))
                    .addProperty(hasLatitude,String.valueOf(state.getLatitude()))
                    .addProperty(hasLongitude,String.valueOf(state.getLongitude()))
                    .addProperty(hasHeading,String.valueOf(state.getHeading()))
                    .addProperty(hasVerticalRate,String.valueOf(state.getVerticalRate()))
                    .addProperty(hasIcao24,String.valueOf(state.getIcao24()))
                    .addProperty(hasCallsign,String.valueOf(state.getCallsign()))
                    .addProperty(hasSquawk,String.valueOf(state.getSquawk()))
                    .addProperty(hasSpi,String.valueOf(state.isSpi()))
                    .addProperty(hasPositionSource,String.valueOf(state.getPositionSource()))
                    .addProperty(hasSerials,String.valueOf(state.getSerials()));
        }


        return model;
    }

}

