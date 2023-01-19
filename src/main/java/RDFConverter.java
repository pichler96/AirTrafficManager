import org.apache.jena.rdf.model.*;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.vocabulary.RDF;
import org.topbraid.shacl.validation.ValidationUtil;

import java.time.Instant;
import java.util.List;

public class RDFConverter {

    static String sh_URL = "http://www.w3.org/ns/shacl#";
    static String xsd_URL = "http://www.w3.org/2001/XMLSchema#";
    static String ex_URL = "http://www.w3.org/2022/example#";
    static String rdf_URL = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
    static String aircraft_URL = "http://www.dke-pr/aircraft#";
    static String state_URL = "http://www.dke-pr/state#";
    static String property_URL ="http://www.dke-pr/property#";
    static String response_URL = "http://www.dke-pr/response#";
    static String manufacturer_URL = "http://www.dke-pr/manufacturer#";
    static String operator_URL = "http://www.dke-pr/operator#";

    static void convertStaticData(List<Aircraft> aircrafts) {
        Model model = ModelFactory.createDefaultModel();

        model.setNsPrefix("sh" , sh_URL);
        model.setNsPrefix("xsd" , xsd_URL);
        model.setNsPrefix("ex" , ex_URL);
        model.setNsPrefix("rdf" , rdf_URL);
        model.setNsPrefix("aircraft", aircraft_URL);
        model.setNsPrefix("state", state_URL);
        model.setNsPrefix("response", response_URL);
        model.setNsPrefix("property", property_URL);
        //model.setNsPrefix("manufacturer", manufacturer_URL);
        //model.setNsPrefix("operator",operator_URL);


        Property hasIcao = model.createProperty(property_URL+"hasIcao24");
        Property hasRegistration = model.createProperty(property_URL +"hasRegistration");
        Property hasManufacturer = model.createProperty(property_URL +"hasManufacturer");
        Property hasAircraftModel = model.createProperty(property_URL+"hasAircraftModel");
        Property hasTypeCode = model.createProperty(property_URL+"hasTypeCode");
        Property hasSerialNumber = model.createProperty(property_URL+"hasSerialNumber");
        Property hasIcaoAircraftType = model.createProperty(property_URL+"hasIcaoAircraftType");
        Property hasRegistered = model.createProperty(property_URL+"hasRegistered");
        Property hasRegUntil = model.createProperty(property_URL+"hasRegUntil");
        Property hasBuilt = model.createProperty(property_URL+"hasBuilt");
        Property hasFirstFlightDate = model.createProperty(property_URL+"hasFirstFlightDate");
        Property hasModes = model.createProperty(property_URL+"hasModes");
        Property hasAdsb = model.createProperty(property_URL+"hasAdsb");
        Property hasAcars = model.createProperty(property_URL+"hasAcars");
        Property hasNotes = model.createProperty(property_URL+"hasNotes");
        Property hasCategoryDescription = model.createProperty(property_URL+"hasCategoryDescription");
        Property hasOperator = model.createProperty(property_URL+"hasOperator");
        Property hasOwner = model.createProperty(property_URL+"hasOwner");
        Property hasEngine = model.createProperty(property_URL+"hasEngine");
        Property hasManufacturerName = model.createProperty(property_URL+"hasManufacturerName");
        Property hasManufacturerIcao = model.createProperty(property_URL+"hasManufacturerIcao");
        Property hasOperatorIcao = model.createProperty(property_URL+"hasOperatorIcao");
        Property hasOperatorIata = model.createProperty(property_URL+"hasOperatorIata");
        Property hasOperatorName = model.createProperty(property_URL+"hasOperatorName");
        Property hasOperatorCallsign = model.createProperty(property_URL+"hasOperatorCallsign");

        for (Aircraft aircraft : aircrafts) {
            Resource aircraftData = model.createResource(aircraft_URL + aircraft.getIcao());
            if (aircraft.getIcao() != null) model.add(aircraftData,hasIcao,model.createTypedLiteral(aircraft.getIcao()));
            if (aircraft.getRegistration() != null) model.add(aircraftData,hasRegistration,model.createTypedLiteral(aircraft.getRegistration()));
            if (aircraft.getManufacturer().getIcao() != null ) {
                Resource manufacturer = model.createResource(manufacturer_URL+aircraft.getManufacturer().getIcao());
                model.add(manufacturer,hasManufacturerIcao,model.createTypedLiteral(aircraft.getManufacturer().getIcao()));
                model.add(manufacturer,hasManufacturerName,model.createTypedLiteral(aircraft.getManufacturer().getName()));
                model.add(manufacturer,RDF.type,ex_URL+"Manufacturer");
                model.add(aircraftData,hasManufacturer,manufacturer);
            }
            if (aircraft.getModel() != null ) model.add(aircraftData,hasAircraftModel,model.createTypedLiteral(aircraft.getModel()));
            if (aircraft.getTypeCode() != null ) model.add(aircraftData,hasTypeCode,model.createTypedLiteral(aircraft.getTypeCode()));
            if (aircraft.getSerialNumber() != null) model.add(aircraftData,hasSerialNumber,model.createTypedLiteral(aircraft.getSerialNumber()));
            if (aircraft.getIcaoAircraftType() != null ) model.add(aircraftData,hasIcaoAircraftType,model.createTypedLiteral(aircraft.getIcaoAircraftType()));
            if (aircraft.getRegistered() != null ) model.add(aircraftData,hasRegistered,model.createTypedLiteral(aircraft.getRegistered()));
            if (aircraft.getRegUntil() != null ) model.add(aircraftData,hasRegUntil,model.createTypedLiteral(aircraft.getRegUntil()));
            if (aircraft.getBuilt() != null ) model.add(aircraftData,hasBuilt,model.createTypedLiteral(aircraft.getBuilt()));
            if (aircraft.getFirstFlightDate() != null ) model.add(aircraftData,hasFirstFlightDate,model.createTypedLiteral(aircraft.getFirstFlightDate()));
            if (aircraft.isModes() != null) model.add(aircraftData,hasModes,model.createTypedLiteral(aircraft.isModes()));
            if (aircraft.isAdsb() != null) model.add(aircraftData,hasAdsb,model.createTypedLiteral(aircraft.isAdsb()));
            if (aircraft.isAcars() != null) model.add(aircraftData,hasAcars,model.createTypedLiteral(aircraft.isAcars()));
            if (aircraft.getNotes() != null || aircraft.getNotes() != "") model.add(aircraftData,hasNotes,model.createTypedLiteral(aircraft.getNotes()));
            if (aircraft.getCategoryDescription() != null ) model.add(aircraftData,hasCategoryDescription,model.createTypedLiteral(aircraft.getCategoryDescription()));
            if (aircraft.getOperator().getIcao() != null ) {
                Resource operator = model.createResource(operator_URL+aircraft.getOperator().getIcao());
                model.add(operator,hasOperatorIcao, model.createTypedLiteral(aircraft.getOperator().getIcao()));
                model.add(operator,hasOperatorIata,model.createTypedLiteral(aircraft.getOperator().getIata()));
                model.add(operator,hasOperatorName,model.createTypedLiteral(aircraft.getOperator().getName()));
                model.add(operator,hasOperatorCallsign,model.createTypedLiteral(aircraft.getOperator().getCallsign()));
                model.add(operator,RDF.type,model.createResource(ex_URL+"Operator"));
                model.add(aircraftData,hasOperator,operator);
            }
            if (aircraft.getOwner() != null ) model.add(aircraftData,hasOwner,model.createTypedLiteral(aircraft.getOwner()));
            if (aircraft.getEngine() != null ) model.add(aircraftData,hasEngine,model.createTypedLiteral(aircraft.getEngine()));
            model.add(aircraftData,RDF.type,ex_URL+"Aircraft");
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("aircraft-shacl.ttl"));
        if (ShaclValidator.get().validate(shapes, model.getGraph()).conforms()) {
            System.out.println("SHACL VALIDATION (STATIC) SUCCESSFUL");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/StaticData/", model);
                RDFDataMgr.write(System.out,model.getGraph(), Lang.TTL);
            } catch (Exception ignored) {
                System.out.println(ignored);
                RDFDataMgr.write(System.out,model.getGraph(), Lang.TTL);
                //RDFDataMgr.write(System.out,model.getGraph(), Lang.TTL);
                //RDFDataMgr.write(System.out,model.getGraph(),Lang.TTL);
                //RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
            }
        } else {
            System.out.println("SHACL VALIDATION NOT (STATIC) SUCCESSFUL");
            //System.out.println(ShaclValidator.get().validate(shapes, model.getGraph()).getGraph());
            RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
        }
    }

    static long convertDynamicData(List<State> states){
        Model model = ModelFactory.createDefaultModel();
        String stateURI = "http://www.dke-pr/state#";


        model.setNsPrefix("sh" , sh_URL);
        model.setNsPrefix("xsd" , xsd_URL);
        model.setNsPrefix("ex" , ex_URL);
        model.setNsPrefix("rdf" , rdf_URL);
        model.setNsPrefix("aircraft", aircraft_URL);
        model.setNsPrefix("state", state_URL);
        model.setNsPrefix("response", response_URL);
        model.setNsPrefix("property", property_URL);
        model.setNsPrefix("manufacturer", manufacturer_URL);
        model.setNsPrefix("operator", operator_URL);

        Property hasBaroAltitude = model.createProperty(property_URL+"hasBaroAltitude");
        Property hasGeoAltitude = model.createProperty(property_URL+"hasGeoAltitude");
        Property hasVelocity = model.createProperty(property_URL+"hasVelocity");
        Property hasLastContact = model.createProperty(property_URL+"hasLastContact");
        Property hasLastPositionUpdate = model.createProperty(property_URL+"hasLastPositionUpdate");
        Property hasOnGround = model.createProperty(property_URL+"hasOnGround");
        Property hasOriginCountry = model.createProperty(property_URL+"hasOriginCountry");
        Property hasLatitude = model.createProperty(property_URL+"hasLatitude");
        Property hasLongitude = model.createProperty(property_URL+"hasLongitude");
        Property hasHeadingSin = model.createProperty(property_URL+"hasHeadingSin");
        Property hasHeadingCos = model.createProperty(property_URL+"hasHeadingCos");
        Property hasVerticalRate = model.createProperty(property_URL+"hasVerticalRate");
        Property ofAircraft = model.createProperty(property_URL+"ofAircraft");
        Property hasCallsign = model.createProperty(property_URL+"hasCallsign");
        Property hasSquawk = model.createProperty(property_URL+"hasSquawk");
        Property hasSpi = model.createProperty(property_URL+"hasSpi");
        Property hasPositionSource = model.createProperty(property_URL+"hasPositionSource");
        Property hasSerials = model.createProperty(property_URL+"hasSerials");
        Property hasResponse = model.createProperty(property_URL+"hasResponse");

        long dateTime = Instant.now().getEpochSecond();

        for (State state : states) {
            Resource flightState = model.createResource(state_URL+state.getIcao24());
            if (state.getBaroAltitude() != null) model.add(flightState,hasBaroAltitude, model.createTypedLiteral(state.getBaroAltitude()));
            if (state.getGeoAltitude() != null) model.add(flightState,hasGeoAltitude,model.createTypedLiteral(state.getGeoAltitude()));
            if (state.getVelocity() != null) model.add(flightState,hasVelocity,model.createTypedLiteral(state.getVelocity()));
            if (state.getLastContact() != null) model.add(flightState,hasLastContact,model.createTypedLiteral(state.getLastContact()));
            if (state.getLastPositionUpdate() != null) model.add(flightState,hasLastPositionUpdate,model.createTypedLiteral(state.getLastPositionUpdate()));
            if (String.valueOf(state.isOnGround()) != null) model.add(flightState,hasOnGround,model.createTypedLiteral(state.isOnGround()));
            if (state.getOriginCountry() != null) model.add(flightState,hasOriginCountry,model.createTypedLiteral(state.getOriginCountry()));
            if (state.getLatitude() != null) model.add(flightState,hasLatitude,model.createTypedLiteral(state.getLatitude()));
            if (state.getLongitude() != null) model.add(flightState,hasLongitude,model.createTypedLiteral(state.getLongitude()));
            if (state.getHeadingSin() != null) model.add(flightState,hasHeadingSin,model.createTypedLiteral(state.getHeadingSin()));
            if (state.getHeadingCos() != null) model.add(flightState,hasHeadingCos,model.createTypedLiteral(state.getHeadingCos()));
            if (state.getVerticalRate() != null) model.add(flightState,hasVerticalRate,model.createTypedLiteral(state.getVerticalRate()));
            if (state.getIcao24() != null) {
                    Resource aircraft = model.createResource(aircraft_URL+state.getIcao24());
                    aircraft.addProperty(RDF.type, model.createResource(ex_URL+"Aircraft"));
                    model.add(flightState,ofAircraft,aircraft);
            };
            if (state.getCallsign() != null) model.add(flightState,hasCallsign,model.createTypedLiteral(state.getCallsign()));
            if (state.getSquawk() != null) model.add(flightState,hasSquawk,model.createTypedLiteral(state.getSquawk()));
            if (String.valueOf(state.isSpi()) != null) model.add(flightState,hasSpi,model.createTypedLiteral(state.isSpi()));
            if (state.getPositionSource() != null) model.add(flightState,hasPositionSource,model.createTypedLiteral(state.getPositionSource()));
            if (state.getSerials() != null) model.add(flightState,hasSerials,model.createTypedLiteral(state.getSerials()));
            if (state.getResponse() != null) model.add(flightState,hasResponse,model.createTypedLiteral(state.getResponse().getTime()));
            model.add(flightState,RDF.type,ex_URL+"State");
        }

        Shapes shapes = Shapes.parse(RDFDataMgr.loadGraph("state-shacl.ttl"));
        //Model shapesModel = RDFDataMgr.loadModel("state-shacl.ttl");

        //System.out.println(shapes.getGraph());
        if (ShaclValidator.get().validate(shapes, model.getGraph()).conforms()) {

            //Resource report = ValidationUtil.validateModel(model, shapesModel, false);
            //RDFDataMgr.write(System.out, report.getModel(), Lang.TTL);
            //RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
            System.out.println("SHACL VALIDATION (DYNAMIC) SUCCESSFUL");
            try (RDFConnection conn = RDFConnection.connect("http://localhost:3030/AirTrafficManager") ) {
                conn.load("http://localhost:3030/DynamicData/"+ dateTime,model);
            }
            catch (Exception err) {
                System.out.println(err);
            }
            //model.write(System.out, "TURTLE");
        } else {
            System.out.println("SHACL VALIDATION NOT (DYNAMIC) SUCCESSFUL");
            RDFDataMgr.write(System.out, ShaclValidator.get().validate(shapes, model.getGraph()).getModel(), Lang.TTL);
        }
        return dateTime;
    }
}

