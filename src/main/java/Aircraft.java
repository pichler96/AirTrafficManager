import org.opensky.model.StateVector;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Aircraft {
    private String icao;
    private String registration;
    private Manufacturer manufacturer;
    private AircraftModel aircraftModel;
    private String typeCode;
    private String serialNumber;
    private IcaoAircraftType icaoAircraftType;
    private LocalDate registered;
    private LocalDate regUntil;
    private LocalDate built;
    private LocalDate firstFlightDate;
    private boolean modes;
    private boolean adsb;
    private boolean acars;
    private String notes;
    private String categoryDescription;
    private Operator operator;
    private Owner owner;
    private Engine engine;
    List<StateVector> states;

    public Aircraft() {

    }

    public Aircraft(String icao) {
        this.icao = icao;
        this.states = new ArrayList<>();
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

    public AircraftModel getModel() {
        return aircraftModel;
    }

    public void setModel(AircraftModel aircraftModel) {
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

    public IcaoAircraftType getAircraftType() {
        return icaoAircraftType;
    }

    public void setAircraftType(IcaoAircraftType icaoAircraftType) {
        this.icaoAircraftType = icaoAircraftType;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public LocalDate getRegUntil() {
        return regUntil;
    }

    public void setRegUntil(LocalDate regUntil) {
        this.regUntil = regUntil;
    }

    public LocalDate getBuilt() {
        return built;
    }

    public void setBuilt(LocalDate built) {
        this.built = built;
    }

    public LocalDate getFirstFlightDate() {
        return firstFlightDate;
    }

    public void setFirstFlightDate(LocalDate firstFlightDate) {
        this.firstFlightDate = firstFlightDate;
    }

    public boolean isModes() {
        return modes;
    }

    public void setModes(boolean modes) {
        this.modes = modes;
    }

    public boolean isAdsb() {
        return adsb;
    }

    public void setAdsb(boolean adsb) {
        this.adsb = adsb;
    }

    public boolean isAcars() {
        return acars;
    }

    public void setAcars(boolean acars) {
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public List<StateVector> getStates() {
        return states;
    }

    public void setStates(List<StateVector> states) {
        this.states = states;
    }

    public String toString() {
        /*
        private String registration;
        private Manufacturer manufacturer;
        private AircraftModel aircraftModel;
        private String typeCode;
        private String serialNumber;
        private IcaoAircraftType icaoAircraftType;
        private LocalDate registered;
        private LocalDate regUntil;
        private LocalDate built;
        private LocalDate firstFlightDate;
        private boolean modes;
        private boolean adsb;
        private boolean acars;
        private String notes;
        private String categoryDescription;
        private Operator operator;
        private Owner owner;
        private Engine engine;
        List<StateVector> states;*/

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
        sb.append("states: \t" + this.states + "\n\n");
        return sb.toString();
    }

}
