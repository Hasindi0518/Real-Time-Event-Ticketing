public class Vendor implements Runnable {
    private final int id;
    private final TicketPool ticketPool;
    private final int releaseInterval;
    private boolean running = true;

    public Vendor(int id, TicketPool ticketPool, int releaseInterval) {
        this.id = id;
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
    }

    @Override
    public void run() {
        try {
            while (running) {
                Thread.sleep(1000);
                ticketPool.addTickets(id, 2); // Add 2 tickets at a time
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor-" + id + " interrupted.");
        } catch (Exception e) {
            System.out.println("Vendor-" + id + " encountered an error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
