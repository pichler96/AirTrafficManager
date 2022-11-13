import org.apache.jena.rdf.model.*;
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

        // create the resource
        //TODO deal with null
        Resource firstAircraft = model.createResource(AircraftURI)
                .addProperty(model.createProperty("http://aircraft/hasicao/"+icao), icao)
                .addProperty(model.createProperty("http://aircraft/hasregistration/"+registration), registration)
                .addProperty(model.createProperty("http://aircraft/hasmanufacturer/"+manufacturer.getIcao()), manufacturer.getIcao())
                .addProperty(model.createProperty("http://aircraft/hasaircraftModel/"+aircraftModel.getName()), aircraftModel.getName())
                .addProperty(model.createProperty("http://aircraft/hastypeCode/"+typeCode), typeCode)
                .addProperty(model.createProperty("http://aircraft/hasserialNumber/"+serialNumber), serialNumber)
                .addProperty(model.createProperty("http://aircraft/hasicaoAircraftType/"+icaoAircraftType.getTypeName()), icaoAircraftType.getTypeName())
                .addProperty(model.createProperty("http://aircraft/hasregistered/"+registered.toString()), registered.toString())
                .addProperty(model.createProperty("http://aircraft/hasregUntil/"+regUntil.toString()), regUntil.toString())
                .addProperty(model.createProperty("http://aircraft/hasbuilt/"+built.toString()), built.toString())
                .addProperty(model.createProperty("http://aircraft/hasfirstFlightDate/"+firstFlightDate.toString()), firstFlightDate.toString())
                .addProperty(model.createProperty("http://aircraft/hasmodes/"+String.valueOf(modes)), String.valueOf(modes))
                .addProperty(model.createProperty("http://aircraft/hasadsb/"+String.valueOf(adsb)), String.valueOf(adsb))
                .addProperty(model.createProperty("http://aircraft/hasacars/"+String.valueOf(acars)), String.valueOf(acars))
                .addProperty(model.createProperty("http://aircraft/hasnotes/"+notes), notes)
                .addProperty(model.createProperty("http://aircraft/hascategoryDescription/"+categoryDescription), categoryDescription)
                .addProperty(model.createProperty("http://aircraft/hasoperator/"+operator.getIcao()), operator.getIcao())
                .addProperty(model.createProperty("http://aircraft/hasowner/"+owner.getName()), owner.getName())
                .addProperty(model.createProperty("http://aircraft/hasengine/"+engine.getName()), engine.getName());
        //TODO deal with null
        if(states.size() == 0) {
            firstAircraft.addProperty(model.createProperty("http://aircraft/haslateststate/null"), "null");
        }
        else {
            firstAircraft.addProperty(model.createProperty("http://aircraft/haslateststate/"+states.get(states.size()-1).getIcao24()), states.get(states.size()-1).getIcao24());
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

