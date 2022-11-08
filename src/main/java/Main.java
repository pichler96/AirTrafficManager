import java.util.List;

public class Main {
    public static List<Aircraft> aircrafts = null;

    public static void main(String[] args) {
        List<Aircraft> aircraftList = null;
        System.out.println(StaticAircraftData.loadStaticAircraftData().toString());
        DynamicAircraftData.startUpdateServiceDynamicAircraftData(aircraftList);
    }
}
