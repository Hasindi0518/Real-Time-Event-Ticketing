import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private final int maxCapacity;
    private final Queue<Ticket> tickets = new LinkedList<>();
    private int totalTicketsIssued;

    public TicketPool(int maxCapacity, int initialTickets) {
        this.maxCapacity = maxCapacity;

        try {
            for (int i = 1; i <= initialTickets; i++) {
                tickets.add(new Ticket(i));
                totalTicketsIssued++;
            }
        } catch (Exception e) {
            System.out.println("Error initializing ticket pool: " + e.getMessage());
        }
    }

    public synchronized boolean addTickets(int vendorId, int count) {
        try {
            int added = 0;
            while (tickets.size() < maxCapacity && added < count) {
                tickets.add(new Ticket(++totalTicketsIssued));
                added++;
            }
            if (tickets.size() == maxCapacity) {
                System.out.println("Vendor-" + vendorId + ": Ticket pool capacity is full.");
            } else {
                System.out.println("Vendor-" + vendorId + " added " + added + " tickets. Total tickets: " + tickets.size());
            }
            return added > 0;
        } catch (Exception e) {
            System.out.println("Error adding tickets: " + e.getMessage());
            return false;
        }
    }

    public synchronized int purchaseTickets(int customerId, int count) {
        try {
            int purchased = 0;
            while (!tickets.isEmpty() && purchased < count) {
                Ticket ticket = tickets.poll();
                System.out.println("Customer-" + customerId + " purchased ticket: " + ticket);
                purchased++;
            }
            if (purchased == 0) {
                System.out.println("Customer-" + customerId + " tried purchasing tickets, but none were available.");
            } else {
                System.out.println("Customer-" + customerId + " completed purchase. Remaining tickets: " + tickets.size());
            }
            return purchased;
        } catch (Exception e) {
            System.out.println("Error purchasing tickets: " + e.getMessage());
            return 0;
        }
    }
}
