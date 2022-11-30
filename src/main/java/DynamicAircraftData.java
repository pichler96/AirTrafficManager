import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicAircraftData {
    private static final String OPENSKYUSERNAME = "jku1234";
    private static final String OPENSKYPASSWORD = "jku1234";
    private static final OpenSkyApi API = new OpenSkyApi(OPENSKYUSERNAME, OPENSKYPASSWORD);
    private static final OpenSkyApi.BoundingBox AUSTRIA = new OpenSkyApi.BoundingBox(/*46.4318173285, 49.0390742051, 9.47996951665, 16.9796667823*/46.22,49.01,9.32,17.1);

    static List<StateVector> loadDynamicAircraftData() {
        try {
            return loadStateVector();
        } catch (IOException e) {
            System.out.println("Could not load dynamic productive data. Loading dummy test data...");
            System.out.println("Test data successfully loaded.");
            return loadDummyAircraftData();
        }
    }

    private static List<StateVector> loadStateVector() throws IOException {
        OpenSkyStates os = API.getStates(0, null, AUSTRIA);
        return new ArrayList<>(os.getStates());
    }

    static List<StateVector> loadDummyAircraftData() {
        List<StateVector> states = new ArrayList<>();
        for (Aircraft aircraft : Main.aircrafts) {
            StateVector state = new StateVector(aircraft.getIcao());
            state.setBaroAltitude(3244.34);
            state.setGeoAltitude(3123.3);
            state.setVelocity(213123.3);
            state.setLastContact(0.0);
            state.setLastPositionUpdate(0.0);
            state.setOnGround(true);
            state.setOriginCountry("GER");
            state.setLatitude(0.0);
            state.setLongitude(0.0);
            states.add(state);
        }
        return states;
    }
}
