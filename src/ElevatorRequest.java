/**
 * The ElevatorRequest class represents an elevator request read from an input file line.
 * It holds all the necessary information about the request, including the timestamp, source floor,
 * button press (UP or DOWN), destination floor, and the elevator used to serve the request.
 */
public class ElevatorRequest {
    public enum Button {UP, DOWN}

    ; // Enumeration to represent button press direction

    private String time; // Timestamp of the request
    private int floor; // Source floor of the request
    private Button buttonPress; // Button press direction (UP or DOWN)
    private int destination; // Destination floor of the request
    private int elevatorUsed; // Identifier of the elevator assigned to the request

    private boolean arrived = false; // Flag to indicate if the elevator has arrived at the destination

    /**
     * Constructor to create an ElevatorRequest object with the provided parameters.
     *
     * @param t The timestamp of the request.
     * @param f The source floor of the request.
     * @param b The button press direction (UP or DOWN).
     * @param d The destination floor of the request.
     * @param e The identifier of the elevator used to serve the request.
     */
    public ElevatorRequest(String t, int f, Button b, int d, int e) {
        time = t;
        floor = f;
        buttonPress = b;
        destination = d;
        elevatorUsed = e;
    }

    /**
     * Gets the timestamp of the elevator request.
     *
     * @return The timestamp of the request.
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets the source floor of the elevator request.
     *
     * @return The source floor of the request.
     */
    public int getSourceFloor() {
        return floor;
    }

    /**
     * Gets the button press direction (UP or DOWN) of the elevator request.
     *
     * @return The button press direction.
     */
    public Button getButtonPress() {
        return buttonPress;
    }

    /**
     * Gets the destination floor of the elevator request.
     *
     * @return The destination floor of the request.
     */
    public int getDestinationFloor() {
        return destination;
    }

    /**
     * Gets the identifier of the elevator used to serve the request.
     *
     * @return The identifier of the elevator.
     */
    public int getElevatorUsed() {
        return elevatorUsed;
    }
}
