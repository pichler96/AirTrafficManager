import org.apache.jena.base.Sys;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Aircraft> aircrafts = new ArrayList<>();

    public static void main(String[] args) {
        Aircraft aircraft = new Aircraft("710070");
        aircrafts.add(aircraft);
        DynamicAircraftData.loadDynamicAircraftData(aircrafts);
        RDFConverter.convertToRDF();




    }
}
