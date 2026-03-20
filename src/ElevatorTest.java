import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ElevatorTest {
    private BoxStub schedulerToElevator; // Stub for communication from Scheduler to Elevator
    private BoxStub elevatorToScheduler; // Stub for completion signal from Elevator to Scheduler

    @Before
    public void setUp() {
        schedulerToElevator = new BoxStub(); // Initialize the stubs before each test
        elevatorToScheduler = new BoxStub();
    }

    @Test
    public void testCheckElevator() {
        // Create an ElevatorRequest for elevator number 1
        ElevatorRequest request = new ElevatorRequest("08:00", 1, ElevatorRequest.Button.UP, 3, 1);

        // Create an Elevator with number 1, using BoxStub instances
        Elevator elevator = new Elevator(schedulerToElevator, elevatorToScheduler, 1);

        // Check if the elevator correctly identifies the request is for it
        assertTrue("Elevator 1 should accept the request", elevator.checkElevator(request));
    }

    @Test
    public void testElevatorMovementDirection() throws InterruptedException {
        // First part: Test moving from a lower floor to a higher floor
        BoxStub fromScheduler = new BoxStub();
        BoxStub toScheduler = new BoxStub();

        Elevator elevator = new Elevator(fromScheduler, toScheduler, 1);
        ElevatorRequest upRequest = new ElevatorRequest("08:00", 1, ElevatorRequest.Button.UP, 4, 1);
        fromScheduler.put(upRequest); // Simulate the scheduler sending a request

        elevator.start(); // Start processing the request
        Thread.sleep(100); // Allow some time for processing
        elevator.stopRunning(); // Stop the elevator thread
        elevator.join(); // Wait for the thread to terminate

        assertEquals(4, elevator.getCurrentFloor());

        // Second part: Test moving from a higher floor to a lower floor
        // Re-instantiate elevator for a new test scenario
        elevator = new Elevator(fromScheduler, toScheduler, 1);
        ElevatorRequest downRequest = new ElevatorRequest("08:05", 4, ElevatorRequest.Button.DOWN, 1, 1);
        fromScheduler.put(downRequest); // Simulate another request

        elevator.start(); // Start the new instance
        Thread.sleep(100); // Allow some time for processing
        elevator.stopRunning(); // Signal to stop the thread
        elevator.join(); // Wait for it to finish

        assertEquals(1, elevator.getCurrentFloor());
    }

}
