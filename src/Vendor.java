public class Vendor implements Runnable {
    private final int id; // Vendor ID
    private final TicketPool ticketPool; // Reference to the TicketPool
    private final int releaseInterval;   // Time interval for adding tickets
    private boolean running = true;  // Controls the vendor thread's execution

    public Vendor(int id, TicketPool ticketPool, int releaseInterval) {
        this.id = id;
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000); //waiting time
                ticketPool.addTickets(id, 2); // Add 2 tickets at a time
            }
        } catch (InterruptedException e) {
            Main.log("Vendor-" + id + " interrupted.");
        } catch (Exception e) {
            Main.log("Vendor-" + id + " encountered an error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false; // Stop the thread
    }
}
