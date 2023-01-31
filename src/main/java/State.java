import org.opensky.model.StateVector;

import java.util.HashSet;
import java.util.Set;


public class State {

    private Double geoAltitude;
    private Double longitude;
    private Double latitude;
    private Double velocity;
    private Double heading;
    private Double verticalRate;
    private String icao24;
    private String callsign;
    private boolean onGround;
    private Double lastContact;
    private Double lastPositionUpdate;
    private String originCountry;
    private String squawk;
    private boolean spi;
    private Double baroAltitude;
    private PositionSource positionSource;

    private Double headingSin;
    private Set<Integer> serials;

    private Response response;
    private Aircraft ofAircraft;

    private Double headingCos;

    public State(StateVector stateVector) {
        this.geoAltitude = stateVector.getGeoAltitude();
        this.longitude = stateVector.getLongitude();
        this.latitude = stateVector.getLatitude();
        this.velocity = stateVector.getVelocity();
        this.headingSin = Math.sin(stateVector.getHeading());
        this.headingCos = Math.cos(stateVector.getHeading());
        this.verticalRate = stateVector.getVerticalRate();
        this.icao24 = stateVector.getIcao24();
        this.callsign = stateVector.getCallsign();
        this.onGround = stateVector.isOnGround();
        this.lastContact = stateVector.getLastContact();
        this.lastPositionUpdate = stateVector.getLastPositionUpdate();
        this.originCountry = stateVector.getOriginCountry();
        this.squawk = stateVector.getSquawk();
        this.spi = stateVector.isSpi();
        this.baroAltitude = stateVector.getBaroAltitude();
        this.positionSource = getPositionSource();
        this.serials = stateVector.getSerials();;
    }

    public State(String icao24, Double baroAltitude, Double geoAltitude, Double velocity, Double lastContact, Double lastPositionUpdate,
                 Boolean isOnGround, String originCountry, Double latitude, Double longitude, Response response, Double headingSin, Double headingCos,Double verticalRate,String callsign,String squawk, boolean spi) {
        this.icao24 = icao24;
        this.baroAltitude = baroAltitude;
        this.geoAltitude = geoAltitude;
        this.velocity = velocity;
        this.lastContact = lastContact;
        this.lastPositionUpdate = lastPositionUpdate;
        this.onGround = isOnGround;
        this.originCountry = originCountry;
        this.latitude = latitude;
        this.longitude = longitude;
        this.response = response;
        this.headingSin = headingSin;
        this.headingCos = headingCos;
        this.verticalRate = verticalRate;
        this.callsign = callsign;
        this.squawk = squawk;
        this.spi = spi;
    }

    public Double getHeadingSin(){
        return headingSin;
    }

    public void setHeadingSin(Double headingSin){
        this.headingSin = headingSin;
    }

    public void setHeadingCos(Double headingCos){
        this.headingCos = headingCos;
    }
    public Double getHeadingCos(){
        return headingCos;
    }
    public Double getGeoAltitude() {
        return geoAltitude;
    }

    public void setGeoAltitude(Double geoAltitude) {
        this.geoAltitude = geoAltitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public Double getHeading() {
        return heading;
    }

    public void setHeading(Double heading) {
        this.heading = heading;
    }

    public Double getVerticalRate() {
        return verticalRate;
    }

    public void setVerticalRate(Double verticalRate) {
        this.verticalRate = verticalRate;
    }

    public String getIcao24() {
        return icao24;
    }

    public void setIcao24(String icao24) {
        this.icao24 = icao24;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public Double getLastContact() {
        return lastContact;
    }

    public void setLastContact(Double lastContact) {
        this.lastContact = lastContact;
    }

    public Double getLastPositionUpdate() {
        return lastPositionUpdate;
    }

    public void setLastPositionUpdate(Double lastPositionUpdate) {
        this.lastPositionUpdate = lastPositionUpdate;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getSquawk() {
        return squawk;
    }

    public void setSquawk(String squawk) {
        this.squawk = squawk;
    }

    public boolean isSpi() {
        return spi;
    }

    public void setSpi(boolean spi) {
        this.spi = spi;
    }

    public Double getBaroAltitude() {
        return baroAltitude;
    }

    public void setBaroAltitude(Double baroAltitude) {
        this.baroAltitude = baroAltitude;
    }

    public PositionSource getPositionSource() {
        return positionSource;
    }

    public void setPositionSource(PositionSource positionSource) {
        this.positionSource = positionSource;
    }

    public Set<Integer> getSerials() {
        return serials;
    }

    public void addSerial(int serial) {
        if (this.serials == null) {
            this.serials = new HashSet<>();
        }
        this.serials.add(serial);
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Aircraft getAircraft() {
        return ofAircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.ofAircraft = aircraft;
    }

    @Override
    public String toString() {
        return "StateVector{" +
                "geoAltitude=" + geoAltitude +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", velocity=" + velocity +
                ", heading=" + heading +
                ", verticalRate=" + verticalRate +
                ", icao24='" + icao24 + '\'' +
                ", callsign='" + callsign + '\'' +
                ", onGround=" + onGround +
                ", lastContact=" + lastContact +
                ", lastPositionUpdate=" + lastPositionUpdate +
                ", originCountry='" + originCountry + '\'' +
                ", squawk='" + squawk + '\'' +
                ", spi=" + spi +
                ", baroAltitude=" + baroAltitude +
                ", positionSource=" + positionSource +
                ", serials=" + serials +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State that = (State) o;

        if (onGround != that.onGround) return false;
        if (spi != that.spi) return false;
        if (geoAltitude != null ? !geoAltitude.equals(that.geoAltitude) : that.geoAltitude != null) return false;
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false;
        if (velocity != null ? !velocity.equals(that.velocity) : that.velocity != null) return false;
        if (heading != null ? !heading.equals(that.heading) : that.heading != null) return false;
        if (verticalRate != null ? !verticalRate.equals(that.verticalRate) : that.verticalRate != null) return false;
        if (!icao24.equals(that.icao24)) return false;
        if (callsign != null ? !callsign.equals(that.callsign) : that.callsign != null) return false;
        if (lastContact != null ? !lastContact.equals(that.lastContact) : that.lastContact != null) return false;
        if (lastPositionUpdate != null ? !lastPositionUpdate.equals(that.lastPositionUpdate) : that.lastPositionUpdate != null)
            return false;
        if (originCountry != null ? !originCountry.equals(that.originCountry) : that.originCountry != null)
            return false;
        if (squawk != null ? !squawk.equals(that.squawk) : that.squawk != null) return false;
        if (baroAltitude != null ? !baroAltitude.equals(that.baroAltitude) : that.baroAltitude != null) return false;
        if (positionSource != that.positionSource) return false;
        return serials != null ? serials.equals(that.serials) : that.serials == null;
    }

    @Override
    public int hashCode() {
        int result = geoAltitude != null ? geoAltitude.hashCode() : 0;
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (velocity != null ? velocity.hashCode() : 0);
        result = 31 * result + (heading != null ? heading.hashCode() : 0);
        result = 31 * result + (verticalRate != null ? verticalRate.hashCode() : 0);
        result = 31 * result + icao24.hashCode();
        result = 31 * result + (callsign != null ? callsign.hashCode() : 0);
        result = 31 * result + (onGround ? 1 : 0);
        result = 31 * result + (lastContact != null ? lastContact.hashCode() : 0);
        result = 31 * result + (lastPositionUpdate != null ? lastPositionUpdate.hashCode() : 0);
        result = 31 * result + (originCountry != null ? originCountry.hashCode() : 0);
        result = 31 * result + (squawk != null ? squawk.hashCode() : 0);
        result = 31 * result + (spi ? 1 : 0);
        result = 31 * result + (baroAltitude != null ? baroAltitude.hashCode() : 0);
        result = 31 * result + (positionSource != null ? positionSource.hashCode() : 0);
        result = 31 * result + (serials != null ? serials.hashCode() : 0);
        return result;
    }

    public enum PositionSource {
        ADS_B,
        ASTERIX,
        MLAT,
        FLARM,
        UNKNOWN
    }
}
