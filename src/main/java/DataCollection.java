import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.shacl.ValidationReport;
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

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("state-flight-position-shacl.ttl"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 1) ESTIMATED FLIGHT POSITIONS UPDATED");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/FlightPosition/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 1) ESTIMATED FLIGHT POSITIONS FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }
    }


    public static void detectCollision(long datetime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("state-flight-position.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);


        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 2) Collision detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/CollisionDetection/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 2) Collision Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }


    }

    static void detectCollision(long datetime, Double minDistance) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("collisionIdentification.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 2) Collision detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/CollisionDetection/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 2) Collision Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }


    }

    public static void detectSpeedChange(long datetime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("speed-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 3) Speed Change detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/SpeedChange/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 3) Speed Change Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }



    }

    static void detectSpeedChange(long datetime, Double minSpeedChange) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("speed-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 3) Speed Change detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/SpeedChange/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 3) Speed Change Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }


    }

    public static void detectDirectionChange(long datetime) {
        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("direction-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 3) Direction Change detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/DirectionChange/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 3) Direction Change Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
        }

    }

    static void detectDirectionChange(long datetime, Double minDirectionChange) {

        // Load the data model that rules
        Model dataModel = loadModel(false, datetime);
        Model rulesModel = RDFDataMgr.loadModel("direction-function.ttl");

        // Perform the rule calculation
        Model result = RuleUtil.executeRules(dataModel, rulesModel, null, null);

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("Platzhalter für SHACL-Shape"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 3) Direction Change detected");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/DirectionChange/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 3) Direction Change Detection FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
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

        // Validate the result and load in the knowledge graph
        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("count-shape-shacl.ttl"));
        if (ShaclValidator.get().validate(shapes, result.getGraph()).conforms()) {
            System.out.println("    TASK 4) Aggregation Count Succesful");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/Aggregation/" + datetime, result);
            }
        } else {
            System.out.println("    TASK 4) Aggregation Count FAILED");
            RDFDataMgr.write(System.out, result, Lang.TTL);
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