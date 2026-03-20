import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * The Floor class represents a floor in a building and is responsible for reading elevator requests
 * from an input file and sending those requests to the scheduler.
 */
public class Floor extends Thread {
    private Box toScheduler;
    private Box fromScheduler;
    public enum Button { UP, DOWN }; // Enumeration to represent button press direction

    private int floorNumber; // The floor number associated with this instance
    private ArrayList<ElevatorRequest> requests; // List to store elevator requests

    /**
     * Constructor to initialize a Floor instance.
     *
     * @param s The scheduler to which elevator requests will be submitted.
     * @param n The floor number associated with this instance.
     */
    public Floor(Box fromScheduler, Box toScheduler, int n) {
        this.fromScheduler = fromScheduler;
        this.toScheduler = toScheduler;
        this.floorNumber = n;
        this.requests = new ArrayList<>();

        try {
            readInputFile(); // Read elevator requests from the input file during initialization
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions, possibly log or rethrow as unchecked
        }
    }

    /**
     * Reads elevator requests from an input file and populates the 'requests' list.
     *
     * @throws IOException If an error occurs while reading the input file.
     */
    private void readInputFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Input_file"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ElevatorRequest req = newElevatorRequest(line);
                requests.add(req);
            }
        } // BufferedReader is automatically closed here
    }

    /**
     * Parses a string representing an elevator request and creates an ElevatorRequest object.
     *
     * @param newReq The string representation of the elevator request.
     * @return An ElevatorRequest object with parsed values.
     */
    private ElevatorRequest newElevatorRequest(String newReq) {
        String[] values = newReq.split("\\s+");
        String time = values[0]; // Time of the request
        int floorNum = Integer.parseInt(values[1]); // Floor number where the request is made
        ElevatorRequest.Button floorButton = ElevatorRequest.Button.valueOf(values[2].toUpperCase()); // Button pressed (UP/DOWN)
        int destination = Integer.parseInt(values[3]); // Destination floor
        int elevatorNum = Integer.parseInt(values[4]); // The specific elevator intended for this request

        return new ElevatorRequest(time, floorNum, floorButton, destination, elevatorNum);
    }

    /**
     * The run method sends elevator requests to the scheduler and simulates a delay between sending requests.
     */
    @Override
    public void run() {
        for (ElevatorRequest req : requests) {
            try {
                toScheduler.put(req);
                Thread.sleep(1000); // Simulate a delay between sending requests
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
