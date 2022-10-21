import java.util.List;

public class Main {
    public static List<Aircraft> aircrafts = null;

    public static void main(String[] args) {
        List<Aircraft> aircraftList = null;
        DynamicAircraftData.startUpdateServiceDynamicAircraftData(aircraftList);
    }
}
