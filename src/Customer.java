import java.util.Random;

public class Customer implements Runnable {
    private final int id;
    private final TicketPool ticketPool;
    private final int retrievalInterval;
    private boolean running = true;

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
                Thread.sleep(1500);
                int ticketsToPurchase = random.nextInt(3) + 1; // Purchase 1-3 tickets randomly
                ticketPool.purchaseTickets(id, ticketsToPurchase);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer-" + id + " interrupted.");
        } catch (Exception e) {
            System.out.println("Customer-" + id + " encountered an error: " + e.getMessage());
        }
    }

    public void stop() {
        running = false;
    }
}
