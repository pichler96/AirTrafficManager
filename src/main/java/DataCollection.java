import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.RDFDataMgr;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.util.ModelPrinter;


public class DataCollection {
    static void calculateFlightPosition(long datetime) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("state-flight-position.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/FlightPosition/" + datetime, result);
            System.out.println("   1) ESTIMATED FLIGHT POSITIONS UPDATED");
        }
    }

    static void calculateAggregation(long datetime, String owner){
        //Load the data model
        Model dataModel = loadModel(true, datetime);
        Model rulesModel = RDFDataMgr.loadModel("aggregation-shacl.ttl");

        Model result = RuleUtil.executeRules(dataModel, rulesModel, null,null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/Aggregation/" + datetime, result);
            System.out.println("   1) ESTIMATED FLIGHT POSITIONS UPDATED");
        }
    }

    private static Model loadModel(boolean staticData, long datetime) {
        String graph = "http://localhost:3030/";
        RDFConnection conn = RDFConnection.connect(graph + "AirTrafficManager");

        if (staticData) {
            graph += "StaticData";
        } else {
            graph += "DynamicData/" + datetime;
        }
        return conn.fetch(graph);
    }
}
