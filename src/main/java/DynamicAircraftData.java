import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DynamicAircraftData {
    private static final String OPENSKYUSERNAME = "jku1234";
    private static final String OPENSKYPASSWORD = "jku1234";
    private static final OpenSkyApi API = new OpenSkyApi();
    private static final OpenSkyApi.BoundingBox AUSTRIA = new OpenSkyApi.BoundingBox(46.4318173285, 49.0390742051, 9.47996951665, 16.9796667823);


    static void loadDynamicAircraftData(List<Aircraft> aircraftList) {
        try {
            List<StateVector> states = loadStateVector();
            setDataAircrafts(states);
        } catch (IOException e) {
            loadDummyAircraftData();
        } catch (NullPointerException ignored) {}
    }

    private static List<StateVector> loadStateVector() throws IOException {
        OpenSkyStates os = API.getStates(0, null, AUSTRIA);
        return new ArrayList<>(os.getStates());
    }


    private static void setDataAircrafts(List<StateVector> stateVectorList) {
        for (Aircraft aircraft : Main.aircrafts) {
            Optional<StateVector> state = stateVectorList.stream().filter(s -> s.getIcao24().equals(aircraft.getIcao())).findFirst();
            if (state.isPresent()) aircraft.states.add(state.get());
        }
    }

    private static void loadDummyAircraftData() {
        for (Aircraft aircraft : Main.aircrafts) {
            StateVector vector = new StateVector(aircraft.getIcao());
            vector.setBaroAltitude(3244.34);
            vector.setGeoAltitude(3123.3);
            vector.setVelocity(213123.3);
            vector.setLastContact(0.0);
            vector.setLastPositionUpdate(0.0);
            vector.setOnGround(true);
            vector.setOriginCountry("GER");
            vector.setLatitude(0.0);
            vector.setLongitude(0.0);
            aircraft.states.add(vector);
        }
    }
}
