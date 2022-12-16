import org.opensky.model.StateVector;
//TODO load stateVector data into extended state [Gerald]
public class State extends StateVector {

    private Response response;

    public State(String icao24) {
        super(icao24);
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
