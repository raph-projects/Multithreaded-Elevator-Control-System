public class Box {
    /**
     * Object stored in the box
     */
    private ElevatorRequest contents = null;

    /**
     * State of the box
     */
    private boolean empty = true;

    public synchronized void put(ElevatorRequest er) throws InterruptedException {
        while(!empty){
            wait();
        }
        contents = er;
        empty = false;
        notifyAll();
    }

    public synchronized ElevatorRequest get() throws InterruptedException {
        while(empty){
            wait();
        }
        ElevatorRequest er = contents;
        empty = true;
        notifyAll();
        return er;
    }

    public boolean isEmpty(){
        return empty;
    }
}
