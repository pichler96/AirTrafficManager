import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.RDFDataMgr;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.util.ModelPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


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

    static void detectCollision(long datetime) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("collisionIdentification.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/CollisionDetection/" + datetime, result);
            System.out.println("   2) Collision detected");
        }
    }

    static void calculateAggregation(long datetime, String owner){
        //Load the data model
        Model dataModel = loadModel(true, datetime);
        String modelPath = "shacl-test.ttl";

        Path path = Paths.get(modelPath);

        String filetext = null;
        try {
            filetext = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filetext = filetext.replace("#Owner#", "" + owner);
        try {
            Files.writeString(path, filetext);
        } catch (IOException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
        Model rulesModel = RDFDataMgr.loadModel(modelPath);

        Model result = RuleUtil.executeRules(dataModel, rulesModel, null,null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/Aggregation/" + datetime, result);
            System.out.println("   4) Aggregation created");
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
