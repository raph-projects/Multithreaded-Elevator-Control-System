/**
 * The Elevator class represents an elevator in the building's elevator system.
 * Each elevator operates as a separate thread, responding to requests and simulating elevator movements.
 */
public class Elevator extends Thread {
    private Box fromScheduler;
    private Box toScheduler;
    private int elevatorNumber; // Unique identifier for this elevator
    private int currentFloor = 0; // Current floor where the elevator is located, default is ground floor
    private boolean motorRunning = false; // Flag indicating whether the elevator motor is running
    private boolean doorClosed = true; // Flag indicating whether the elevator doors are closed
    public boolean shouldContinue = true;

    private volatile boolean running = true;

    public void stopRunning() {
        this.running = false;
    }

    /**
     * Constructor to initialize the Elevator with a reference to the scheduler and an elevator number.
     *
     * @param fromScheduler The scheduler managing elevator requests.
     * @param n The unique identifier for this elevator.
     */
    public Elevator(Box fromScheduler,Box toScheduler, int n) {
        this.fromScheduler =fromScheduler;
        this.toScheduler = toScheduler;
        this.elevatorNumber = n;
    }

    /**
     * Checks if the elevator should process a given elevator request.
     *
     * @param request The elevator request to be checked.
     * @return True if the request is intended for this elevator, otherwise false.
     */
    public boolean checkElevator(ElevatorRequest request) {
        return request.getElevatorUsed() == this.elevatorNumber;
    }

    /**
     * Processes an elevator request, simulating the elevator's movement and passenger interactions.
     *
     * @param request The elevator request to be processed.
     */
    private void processJob(ElevatorRequest request) {
        // Simulate moving to the source floor if necessary
        if (this.currentFloor != request.getSourceFloor()) {
            moveToFloor(request.getSourceFloor());
        }

        // Simulate the process of passengers entering
        openDoors();
        waitForPassengers();
        closeDoors();

        // Move to the destination floor
        moveToFloor(request.getDestinationFloor());

        // Simulate the process of passengers exiting
        openDoors();
        waitForPassengers();
        closeDoors();
    }

    /**
     * Simulates the elevator moving to a specific floor.
     *
     * @param floor The target floor to move to.
     */
    private void moveToFloor(int floor) {
        System.out.println("Elevator moving from floor " + this.currentFloor + " to floor " + floor);
        this.currentFloor = floor;
        // Simulate time taken to move between floors
        try {
            Thread.sleep(1000); // 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while moving to floor: " + e.getMessage());

        }
    }

    /**
     * Simulates opening the elevator doors.
     */
    private void openDoors() {
        System.out.println("Doors opening at floor " + this.currentFloor);
        this.doorClosed = false;
        // Simulate time for doors to open
        try {
            Thread.sleep(500); // 0.5-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while opening doors: " + e.getMessage());

        }
    }

    /**
     * Simulates closing the elevator doors.
     */
    private void closeDoors() {
        System.out.println("Doors closing at floor " + this.currentFloor);
        this.doorClosed = true;
        // Simulate time for doors to close
        try {
            Thread.sleep(500); // 0.5-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while closing doors: " + e.getMessage());

        }
    }

    /**
     * Simulates waiting for passengers to enter or exit the elevator.
     */
    private void waitForPassengers() {
        System.out.println("Waiting for passengers at floor " + this.currentFloor);
        // Simulate time for passengers to enter/exit
        try {
            Thread.sleep(1000); // 1-second delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while waiting for passengers: " + e.getMessage());

        }
    }
    /**
     * The main run method for the Elevator thread.
     * It continuously checks for elevator requests from the scheduler and processes them.
     */
    public void run() {
        while (!Thread.currentThread().isInterrupted() && running) {
            ElevatorRequest req = null;
            try {
                req = fromScheduler.get();
                if (req != null && checkElevator(req)) {
                    processJob(req);
                    toScheduler.put(req);
                }
                else if (req != null){
                    fromScheduler.put(req);}
                Thread.sleep(500);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                System.err.println("InterruptedException occurred: " + e.getMessage());
                break;
            }

            }

        }

    public Object getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int n) {
        this.currentFloor = n;
    }
}
