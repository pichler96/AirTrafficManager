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
    private static final OpenSkyApi API = new OpenSkyApi(OPENSKYUSERNAME, OPENSKYPASSWORD);
    private static final OpenSkyApi.BoundingBox AUSTRIA = new OpenSkyApi.BoundingBox(/*46.4318173285, 49.0390742051, 9.47996951665, 16.9796667823*/46.22,49.01,9.32,17.1);

    static void startUpdateServiceDynamicAircraftData(List<Aircraft> aircraftList) {
        while (true) {
            loadDynamicAircraftData(aircraftList);
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void loadDynamicAircraftData(List<Aircraft> aircraftList) {
        try {
            List<StateVector> states = loadStateVector();
            setDataAircrafts(states);
        } catch (IOException e) {
            System.out.println("Could not load dynamic productive data. Loading dummy test data...");
            loadDummyAircraftData();
            System.out.println("Test data successfully loaded.");
        } catch (NullPointerException ignored) {}
    }

    private static List<StateVector> loadStateVector() throws IOException {
        OpenSkyStates os = API.getStates(0, null, AUSTRIA);
        return new ArrayList<>(os.getStates());
    }

    private static void setDataAircrafts(List<StateVector> stateVectorList) {
        Boolean icaoFound = false;
        for (Aircraft aircraft : Main.aircrafts) {
            for (StateVector state : stateVectorList) {
                if (aircraft.getIcao().equals(state.getIcao24())) {
                    icaoFound = true;
                    System.out.println(aircraft.getIcao());
                    aircraft.states.add(state);
                }
            }
            System.out.println(icaoFound);
            icaoFound = false;
        }
        /*
        for (Aircraft aircraft : Main.aircrafts) {
            Optional<StateVector> state = stateVectorList.stream().filter(s -> s.getIcao24().equals(aircraft.getIcao())).findAny()/*.findFirst()*//*;
            if (state.isPresent()) aircraft.states.add(state.get());
        }*/
    }

    static void loadDummyAircraftData() {
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
