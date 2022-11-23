public class Manufacturer {
    private String manufacturerIcao;
    private String manufactorerName;

    public Manufacturer(String icao, String manufactorerName) {
        this.manufacturerIcao = icao;
        this.manufactorerName = manufactorerName;
    }

    public String getManufacturerIcao() {
        return manufacturerIcao;
    }

    public void setManufacturerIcao(String manufacturerIcao) {
        this.manufacturerIcao = manufacturerIcao;
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "icao='" + manufacturerIcao + '\'' +
                ", name='" + manufactorerName + '\'' +
                '}';
    }
}
