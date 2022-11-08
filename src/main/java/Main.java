
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static List<Aircraft> aircrafts = new ArrayList<>();

    public static void main(String[] args) {
        aircrafts = StaticAircraftData.loadStaticAircraftData();
        DynamicAircraftData.loadDynamicAircraftData(aircrafts);
        RDFConverter.convertToRDF();
    }
}
