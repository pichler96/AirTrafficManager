import java.util.ArrayList;

public class Operator {

    private String icao;
    private String iata;
    private String name;
    private String callsign;
    private ArrayList<Aircraft> listOfAircrafts = new ArrayList<>();

    public Operator(String name, String callsign, String icao, String iata, ArrayList<Aircraft> listOfAircrafts) {
        this.name = name;
        this.callsign = callsign;
        this.icao = icao;
        this.iata = iata;
        this.listOfAircrafts = listOfAircrafts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public ArrayList<Aircraft> getListOfAircrafts() {
        return listOfAircrafts;
    }

    public void setListOfAircrafts(ArrayList<Aircraft> listOfAircrafts) {
        this.listOfAircrafts = listOfAircrafts;
    }

    @Override
    public String toString() {
        return "Operator{" +
                "icao='" + icao + '\'' +
                ", iata='" + iata + '\'' +
                ", name='" + name + '\'' +
                ", callsign='" + callsign + '\'' +
                ", listOfAircrafts=" + listOfAircrafts +
                '}';
    }
}
