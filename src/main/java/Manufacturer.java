public class Manufacturer {
    private String icao;
    private String name;

    public Manufacturer(String icao, String name) {
        this.icao = icao;
        this.name = name;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }
}
