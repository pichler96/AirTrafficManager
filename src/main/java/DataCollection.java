import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.util.ModelPrinter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class DataCollection {
    static void calculateFlightPosition(long dateTime) {

        // Load the data model that rules
        Model dataModel = loadModel(false, dateTime);
        Model rulesModel = RDFDataMgr.loadModel("state-flight-position.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/FlightPosition/" + dateTime, result);
            System.out.println("   TASK 1) ESTIMATED FLIGHT POSITIONS UPDATED");
        }
    }


    public static void detectCollision(long dateTime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, dateTime);
        Model rulesModel = RDFDataMgr.loadModel("state-flight-position.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/FlightPosition/" + dateTime, result);
            System.out.println("   2) POTENTIAL COLLISION RISKS LISTED");
        }
    }

    static void detectCollision(long datetime, Double minDistance) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("collisionIdentification.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager")) {
            conn.load("http://localhost:3030/CollisionDetection/" + datetime, result);
            System.out.println("   2) Collision detected");
        }
    }

    public static void detectSpeedChange(long dateTime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, dateTime);
        Model rulesModel = RDFDataMgr.loadModel("speed-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/SpeedChange/" + dateTime, result);
            System.out.println("   3) SPEED CHANGE DETECTED");
        }
    }

    static void detectSpeedChange(long datetime, Double minSpeedChange) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("speed-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager")) {
            conn.load("http://localhost:3030/SpeedChange/" + datetime, result);
            System.out.println("   3) Speed Change detected");
        }
    }

    public static void detectDirectionChange(long dateTime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, dateTime);
        Model rulesModel = RDFDataMgr.loadModel("direction-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/DirectionChange/" + dateTime, result);
            System.out.println("   3) DIRECTION CHANGE DETECTED");
        }
    }

    static void detectDirectionChange(long datetime, Double minDirectionChange) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("direction-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Load result in the knowledge graph
        try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
            conn.load("http://localhost:3030/DirectionChange/" + datetime, result);
            System.out.println("   3) Direction Change detected");
        }
    }

    static void calculateAggregation(long datetime, String owner){
        //Load the data model
        Model dataModel = loadModel(true, datetime);
        String modelPath = "count-rule.ttl";

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
            System.out.println("   1) AGGREGATION CREATED");
        }
    }

    private static Model loadModel(boolean staticData, long datetime) {
        String graph = "http://localhost:3030/";
        RDFConnection conn = RDFConnection.connect(graph + "AirTrafficManager");

        Model dataGraph = conn.fetch(graph+"DynamicData/"+datetime);
        if (staticData) {
            graph += "StaticData";
            Model staticGraph = conn.fetch(graph);
            dataGraph.add(staticGraph);
            return dataGraph;
        } else {
            return dataGraph;
        }

    }
}