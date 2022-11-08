import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Aircraft> aircrafts = null;

    public static void main(String[] args) {
        List<Aircraft> aircraftList = new ArrayList<>();
        Aircraft aircraft = new Aircraft("4407fe");
        aircraftList.add(aircraft);
        DynamicAircraftData.startUpdateServiceDynamicAircraftData(aircraftList);
        System.out.println(aircraftList.get(0).states);

        /*
        // some definitions
        String AircraftURI    = "http://somewhere/JohnSmith";
        String icao24     =      "John Smith";

        // create an empty Model
        Model model = ModelFactory.createDefaultModel();

        // create the resource
        Resource johnSmith = model.createResource(AircraftURI);

        // add the property
        johnSmith.addProperty(VCARD.FN, icao24);
        */
    }
}
