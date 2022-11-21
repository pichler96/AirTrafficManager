import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.shacl.validation.ShaclPlainValidator;
import org.apache.jena.shacl.vocabulary.SHACL;
import org.apache.jena.vocabulary.RDF;
import org.opensky.model.StateVector;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RDFConverter {
    static void convertToRDF(List<Aircraft> aircrafts) {
        String AircraftURI    = "http://aircraft/firstAircraft";
        String icao = "3c5eec";
        String registration = "D-AGWL";
        Manufacturer manufacturer = new Manufacturer("AIRBUS", "Airbus");
        String aircraftModel = "A319132";
        String typeCode = "A319";
        String serialNumber = "3534";
        String icaoAircraftType = "L2J";
        LocalDate registered = LocalDate.now();
        LocalDate regUntil = LocalDate.now();
        LocalDate built = LocalDate.now();
        LocalDate firstFlightDate = LocalDate.now();
        boolean modes = false;
        boolean adsb = false;
        boolean acars = false;
        String notes = "null";
        String categoryDescription = "Large (75000 to 300000)";
        Operator operator = new Operator(null, "GERMANWINGS", "GWI", null,null);
        String owner = "Germanwings";
        String engine = "test";
        List<StateVector> states = new ArrayList<>();
        // create an empty Model
        Model model = ModelFactory.createDefaultModel();
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



        Resource firstAircraft = model.createResource(AircraftURI)
                .addProperty(hasIcao, icao)
                .addProperty(hasRegistration, registration)
                .addProperty(hasManufacturer, manufacturer.getIcao())
                .addProperty(hasAircraftModel, aircraftModel)
                .addProperty(hasTypeCode, typeCode)
                .addProperty(hasSerialNumber, serialNumber)
                .addProperty(hasIcaoAircraftType, icaoAircraftType)
                .addProperty(hasRegistered, registered.toString())
                .addProperty(hasRegUntil, regUntil.toString())
                .addProperty(hasBuilt, built.toString())
                .addProperty(hasFirstFlightDate, firstFlightDate.toString())
                .addProperty(hasModes, String.valueOf(modes))
                .addProperty(hasAdsb, String.valueOf(adsb))
                .addProperty(hasAcars, String.valueOf(acars))
                .addProperty(hasNotes, notes)
                .addProperty(hasCategoryDescription, categoryDescription)
                .addProperty(hasOperator, operator.getIcao())
                .addProperty(hasOwner, owner)
                .addProperty(hasEngine, engine);

        model.add(firstAircraft, RDF.type, "Aircraft");
        //TODO deal with null
        if(states.size() == 0) {
            firstAircraft.addProperty(hasLatestState, "null");
        }
        else {
            firstAircraft.addProperty(hasLatestState, states.get(states.size()-1).getIcao24());
        }




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
    }
}

