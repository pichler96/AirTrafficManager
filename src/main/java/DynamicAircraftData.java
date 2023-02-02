import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;


import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DynamicAircraftData {
    private static final String OPENSKYUSERNAME = "jku1234";
    private static final String OPENSKYPASSWORD = "jku1234";
    private static final OpenSkyApi API = new OpenSkyApi(OPENSKYUSERNAME, OPENSKYPASSWORD);
    private static final OpenSkyApi.BoundingBox AUSTRIA = new OpenSkyApi.BoundingBox(/*46.4318173285, 49.0390742051, 9.47996951665, 16.9796667823*/46.22,49.01,9.32,17.1);

    static List<State> loadDynamicAircraftData() {
        try {
            return loadStateVector();
        } catch (IOException e) {
            System.out.println("Could not load dynamic productive data. Loading dummy test data...");
            System.out.println("Test data successfully loaded.");
            return loadDummyAircraftData();
        }
    }

    private static List<State> loadStateVector() throws IOException {
        OpenSkyStates os = API.getStates(0, null, AUSTRIA);
        Response response = new Response(Instant.now().getEpochSecond());
        ArrayList<State> states = new ArrayList<>();
        for (StateVector stateVector : os.getStates()) {
            State newState = new State(stateVector);
            newState.setResponse(response);
            for (Aircraft aircraft :
                    Main.aircrafts) {
                if (newState.getIcao24() == aircraft.getIcao()) {
                    newState.setAircraft(aircraft);
                }
            }
            states.add(newState);
        }
        return states;
    }

    static List<State> loadDummyAircraftData() {
        List<State> states = new ArrayList<>();
        for (Aircraft aircraft : Main.aircrafts) {
            State state = new State(
                    aircraft.getIcao(),
                    (Math.random() * ((3500.00 - 3000.00) + 1)) + 3000.00,
                    (Math.random() * ((3500.00 - 3000.00) + 1)) + 3000.00,
                    0.0,
                    1.675324671E9,
                    1.675324645E9,
                    false,
                    "GER",
                    (Math.random() * ((50.00 - 30.00) + 1)) + 30.00,
                    (Math.random() * ((25.00 - 8.00) + 1)) + 8.00,
                    new Response(-1.0),
                    (Math.random() * ((1.00 - (-1.00)) + 1)) + (-1.00),
                    (Math.random() * ((1.00 - (-1.00)) + 1)) + (-1.00),
                    (Math.random() * ((5.00 - (-5.00)) + 1)) + (-5.00),
                    "callSign",
                    "squawk",
                    true);

            states.add(state);
            if(states.size()>30)
                break;
        }
        return states;
    }

}
