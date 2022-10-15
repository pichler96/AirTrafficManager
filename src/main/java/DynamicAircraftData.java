import org.opensky.api.OpenSkyApi;
import org.opensky.model.OpenSkyStates;
import org.opensky.model.StateVector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DynamicAircraftData {
    private static final String OPENSKYUSERNAME = "jku1234";
    private static final String OPENSKYPASSWORD = "jku1234";

    static void loadDynamicAircraftData(List<Aircraft> aircraftList) {
        String[] icaos = getIcaosFromAircraft(aircraftList);
        try {
            setDataAircrafts(aircraftList, loadStateVector(icaos));
        } catch (IOException e) {
            loadDummyAircraftData(aircraftList);
        }
    }

    private static String[] getIcaosFromAircraft(List<Aircraft> aircraftList) {
        List<String> icaosList = new ArrayList<String>();
        for (Aircraft aircraft : aircraftList) {
            icaosList.add(aircraft.icao);
        }
        return (String[]) icaosList.toArray();
    }

    private static List<StateVector> loadStateVector(String[] icaos) throws IOException {
        OpenSkyApi api = new OpenSkyApi(OPENSKYUSERNAME, OPENSKYPASSWORD);
        OpenSkyStates os = api.getMyStates(0, icaos, null);
        return new ArrayList<>(os.getStates());
    }

    private static void setDataAircrafts(List<Aircraft> aircraftList,List<StateVector> stateVectorList) {
        // Load data for Aircrafts
    }

    private static void loadDummyAircraftData(List<Aircraft> aircraftList) {
        // Load dummy data for Aircrafts
    }
}
