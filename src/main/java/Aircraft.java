import java.time.LocalDate;

public class Aircraft {
    private String icao;
    private String registration;
    private Manufacturer manufacturer;
    private String aircraftModel;
    private String typeCode;
    private String serialNumber;
    private String icaoAircraftType;
    private String registered;
    private String regUntil;
    private String built;
    private String firstFlightDate;
    private Boolean modes;
    private Boolean adsb;
    private Boolean acars;
    private String notes;
    private String categoryDescription;
    private Operator operator;
    private String owner;
    private String engine;

    public Aircraft() {
    }

    public Aircraft(String icao) {
        this.icao = icao;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }



    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return aircraftModel;
    }

    public void setModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getIcaoAircraftType() {
        return icaoAircraftType;
    }

    public void setAircraftType(String icaoAircraftType) {
        this.icaoAircraftType = icaoAircraftType;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getRegUntil() {
        return regUntil;
    }

    public void setRegUntil(String regUntil) {
        this.regUntil = regUntil;
    }

    public String getBuilt() {
        return built;
    }

    public void setBuilt(String built) {
        this.built = built;
    }

    public String getFirstFlightDate() {
        return firstFlightDate;
    }

    public void setFirstFlightDate(String firstFlightDate) {
        this.firstFlightDate = firstFlightDate;
    }

    public Boolean isModes() {
        return modes;
    }

    public void setModes(Boolean modes) {
        this.modes = modes;
    }

    public Boolean isAdsb() {
        return adsb;
    }

    public void setAdsb(Boolean adsb) {
        this.adsb = adsb;
    }

    public Boolean isAcars() {
        return acars;
    }

    public void setAcars(Boolean acars) {
        this.acars = acars;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nicao: \t" + this.icao + "\n");
        sb.append("registration: \t" + this.registration + "\n");
        sb.append("manufacturer: \t" + this.manufacturer + "\n");
        sb.append("aircraftModel: \t" + this.aircraftModel + "\n");
        sb.append("typeCode: \t" + this.typeCode + "\n");
        sb.append("serialNumber: \t" + this.serialNumber + "\n");
        sb.append("icaoAircraftType: \t" + this.icaoAircraftType + "\n");
        sb.append("registered: \t" + this.registered + "\n");
        sb.append("regUntil: \t" + this.regUntil + "\n");
        sb.append("built: \t" + this.built + "\n");
        sb.append("firstFlightDate: \t" + this.firstFlightDate + "\n");
        sb.append("modes: \t" + this.modes + "\n");
        sb.append("adsb: \t" + this.adsb + "\n");
        sb.append("acars: \t" + this.acars + "\n");
        sb.append("notes: \t" + this.notes + "\n");
        sb.append("categoryDescription: \t" + this.categoryDescription + "\n");
        sb.append("operator: \t" + this.operator + "\n");
        sb.append("owner: \t" + this.owner + "\n");
        sb.append("engine: \t" + this.engine + "\n");
        return sb.toString();
    }

}
