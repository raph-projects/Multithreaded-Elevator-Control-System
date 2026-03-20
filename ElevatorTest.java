/**
 * Main class to initialize and coordinate the Elevator Simulation.
 */
public class Main {
    public static void main(String[] args) {
        Box floorToScheduler = new Box(), schedulerToFloor = new Box(), schedulerToElevator = new Box(), elevatorToScheduler = new Box();
        // Initialize the Scheduler with its own thread
        Scheduler scheduler = new Scheduler(schedulerToElevator, elevatorToScheduler, schedulerToFloor, floorToScheduler);
        Thread schedulerThread = new Thread(scheduler);
        schedulerThread.start();

        // Initialize the Elevator
        Elevator elevator = new Elevator(schedulerToElevator, elevatorToScheduler, 1); // Assuming elevator number 1

        // Start the Elevator thread
        elevator.start();

        // Initialize the Floor
        Floor floor1 = new Floor(schedulerToFloor, floorToScheduler, 1); // The input file will be read inside the Floor constructor
        floor1.start();

        // Wait for threads to finish
        try {
            floor1.join(); // Wait for Floor thread to finish
            elevator.join(); // Wait for Elevator thread to finish
            schedulerThread.join(); // Wait for Scheduler thread to finish
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }
    }
}