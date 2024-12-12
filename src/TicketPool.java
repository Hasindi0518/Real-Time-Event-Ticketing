import java.util.LinkedList;
import java.util.Queue;

/**
 * This class represents the ticket-pool's available for purchase and adding.
 *  A Queue is used to ensure First-In-First-Out order for ticket handling.
 */
public class TicketPool {
    private final int maxCapacity; // Ensures ticket pool doesn't exceed capacity
    private final Queue<Ticket> tickets = new LinkedList<>(); // Queue to hold tickets
    private int totalTicketsIssued; // Tracks how many tickets have been issued so far


    public TicketPool(int maxCapacity, int initialTickets) {
        this.maxCapacity = maxCapacity;

        try {
            for (int i = 1; i <= initialTickets; i++) {  // Initialize ticket pool with given number of tickets
                tickets.add(new Ticket(i));
                totalTicketsIssued++;
            }
        } catch (Exception e) {
            System.out.println("Error initializing ticket pool: " + e.getMessage());
        }
    }

    /**
     * add tickets to the pool until maximum capacity is reached.
     * The method ensures thread-safe operations using synchronized blocks.
      */

    public synchronized boolean addTickets(int vendorId, int count) {
        try {
            int added = 0;
            while (tickets.size() < maxCapacity && added < count) {
                tickets.add(new Ticket(++totalTicketsIssued));
                added++;
            }
            if (tickets.size() == maxCapacity) {
                Main.log("Vendor-" + vendorId + ": Trying to add Ticket but capacity is full.");
            } else {
                Main.log("Vendor-" + vendorId + " added " + added + " tickets. Total tickets: " + tickets.size());
            }
            return added > 0;
        } catch (Exception e) {
            Main.log("Error adding tickets: " + e.getMessage());
            return false;
        }
    }

    // Allows customers to purchase tickets
    public synchronized int purchaseTickets(int customerId, int count) {
        try {
            int purchased = 0;
            while (!tickets.isEmpty() && purchased < count) {
                Ticket ticket = tickets.poll();
                Main.log("Customer-" + customerId + " purchased ticket: " + ticket);
                purchased++;
            }
            if (purchased == 0) {
                Main.log("Customer-" + customerId + " tried purchasing tickets, but none were available.");
            } else {
                Main.log("Customer-" + customerId + " completed purchase. Remaining tickets: " + tickets.size());
            }
            return purchased;
        } catch (Exception e) {
            Main.log("Error purchasing tickets: " + e.getMessage());
            return 0;
        }
    }
}
