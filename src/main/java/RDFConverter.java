import org.apache.jena.rdf.model.*;
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
        AircraftModel aircraftModel = new AircraftModel("A319132");
        String typeCode = "A319";
        String serialNumber = "3534";
        IcaoAircraftType icaoAircraftType = new IcaoAircraftType("L2J");
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
        Owner owner = new Owner("Germanwings");
        Engine engine = new Engine("test");
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
        Property hasIcao = ResourceFactory.createProperty("http://aircraft/hasicao");
        Resource firstAircraft = model.createResource(AircraftURI)
                .addProperty(hasIcao, icao)
                .addProperty(model.createProperty("http://aircraft/hasregistration"), registration)
                .addProperty(model.createProperty("http://aircraft/hasmanufacturer"), manufacturer.getIcao())
                .addProperty(model.createProperty("http://aircraft/hasaircraftModel"), aircraftModel.getName())
                .addProperty(model.createProperty("http://aircraft/hastypeCode"), typeCode)
                .addProperty(model.createProperty("http://aircraft/hasserialNumber"), serialNumber)
                .addProperty(model.createProperty("http://aircraft/hasicaoAircraftType"), icaoAircraftType.getTypeName())
                .addProperty(model.createProperty("http://aircraft/hasregistered"), registered.toString())
                .addProperty(model.createProperty("http://aircraft/hasregUntil"), regUntil.toString())
                .addProperty(model.createProperty("http://aircraft/hasbuilt"), built.toString())
                .addProperty(model.createProperty("http://aircraft/hasfirstFlightDate"), firstFlightDate.toString())
                .addProperty(model.createProperty("http://aircraft/hasmodes"), String.valueOf(modes))
                .addProperty(model.createProperty("http://aircraft/hasadsb"), String.valueOf(adsb))
                .addProperty(model.createProperty("http://aircraft/hasacars"), String.valueOf(acars))
                .addProperty(model.createProperty("http://aircraft/hasnotes"), notes)
                .addProperty(model.createProperty("http://aircraft/hascategoryDescription"), categoryDescription)
                .addProperty(model.createProperty("http://aircraft/hasoperator"), operator.getIcao())
                .addProperty(model.createProperty("http://aircraft/hasowner"), owner.getName())
                .addProperty(model.createProperty("http://aircraft/hasengine"), engine.getName());
        //TODO deal with null
        if(states.size() == 0) {
            firstAircraft.addProperty(model.createProperty("http://aircraft/haslateststate"), "null");
        }
        else {
            firstAircraft.addProperty(model.createProperty("http://aircraft/haslateststate"), states.get(states.size()-1).getIcao24());
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

