import java.util.Random;

public class Customer implements Runnable {
    private final int id; // Customer ID
    private final TicketPool ticketPool;   // Reference to the TicketPool
    private final int retrievalInterval;   // Time interval for ticket purchases
    private boolean running = true;  //Control customer thread

    public Customer(int id, TicketPool ticketPool, int retrievalInterval) {
        this.id = id;
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (running) {
                Thread.sleep(1500); //wait before purchasing tickets
                int ticketsToPurchase = random.nextInt(3) + 1; // Purchase 1-3 tickets randomly
                ticketPool.purchaseTickets(id, ticketsToPurchase);
            }
        } catch (InterruptedException e) {
            Main.log("Customer-" + id + " interrupted.");
        } catch (Exception e) {
            Main.log("Customer-" + id + " encountered an error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false; // Stop the thread
    }
}
