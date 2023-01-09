import java.util.ArrayList;

public class Operator {

    private String icao;
    private String iata;
    private String name;
    private String callsign;


    public Operator(String name, String callsign, String icao, String iata) {
        this.name = name;
        this.callsign = callsign;
        this.icao = icao;
        this.iata = iata;

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



    @Override
    public String toString() {
        return "Operator{" +
                "icao='" + icao + '\'' +
                ", iata='" + iata + '\'' +
                ", name='" + name + '\'' +
                ", callsign='" + callsign + '\'' +
                '}';
    }
}
