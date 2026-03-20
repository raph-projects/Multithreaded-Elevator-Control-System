/**
 * The Scheduler class manages elevator requests using a bounded buffer.
 */
public class Scheduler implements Runnable {
    private Box toElevator;
    private Box fromElevator;
    private Box toFloor;
    private Box fromFloor;

    private state currentState = state.IDLE;

    public enum state{
        IDLE,
        newRequest, //send to elevator
        sendElevator, //elevator is processing
        complete //elevator is done, notify floor
    }

    public Scheduler(Box toE, Box fromE, Box toF, Box fromF){
        this.toElevator = toE;
        this.fromElevator = fromE;
        this.toFloor = toF;
        this.fromFloor = fromF;
    }

    public state getCurrentState() {
        return currentState;
    }
    public void checkForNewRequest() throws InterruptedException {
        if(fromFloor.get() != null) {
            currentState = state.newRequest;
        }
    }


    /**
     * Send ElevatorRequest from Floor to Elevator
     */
   private void sendToElevator() throws InterruptedException {
        ElevatorRequest req = fromFloor.get();
        toElevator.put(req);
        currentState = state.sendElevator;
    }

    private void sendToFloor() throws InterruptedException {
        ElevatorRequest req = fromElevator.get();
        toFloor.put(req);
        currentState = state.complete;
    }
    public void run() {
        while (true) {
            try {
                switch (currentState) {
                    case IDLE:
                        checkForNewRequest();
                        break;
                    case newRequest:
                        sendToElevator();
                        break;
                    case sendElevator:
                        sendToFloor();
                        break;
                    case complete:
                        //add code to check if elevator completed a request
                        currentState = state.IDLE;

                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
