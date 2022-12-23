import java.time.Instant;


public class Response {

    private Double time;

    public Response() {
        this.time = -1.0;
    }
    public Response(double time) {
            this.time = time;
        }

    public double getTime() {
        return this.time;
    }
}
