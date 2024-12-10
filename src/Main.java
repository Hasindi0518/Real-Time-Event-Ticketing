import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String CONFIG_FILE = "ticket_config.json";
        Configuration config = loadOrCreateConfiguration(CONFIG_FILE);

        if (config == null) {
            System.out.println("Failed to load or create configuration. Exiting.");
            return;
        }

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        int vendorCount = 3;
        int customerCount = 5;

        Thread[] vendorThreads = new Thread[vendorCount];
        Vendor[] vendors = new Vendor[vendorCount];

        Thread[] customerThreads = new Thread[customerCount];
        Customer[] customers = new Customer[customerCount];

        try {
            // Start vendors
            for (int i = 0; i < vendorCount; i++) {
                vendors[i] = new Vendor(i + 1, ticketPool, config.getTicketReleaseRate());
                vendorThreads[i] = new Thread(vendors[i], "Vendor-" + (i + 1));
                vendorThreads[i].start();
            }

            // Start customers
            for (int i = 0; i < customerCount; i++) {
                customers[i] = new Customer(i + 1, ticketPool, config.getCustomerRetrievalRate());
                customerThreads[i] = new Thread(customers[i], "Customer-" + (i + 1));
                customerThreads[i].start();
            }

            // Command-line menu
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n**** Ticketing System Menu ****");
            System.out.println("1. Stop");

            while (true) {
                try {

                    int choice = scanner.nextInt();
                    if (choice == 1) {
                        System.out.println("Stopping system...");
                        for (Vendor vendor : vendors) vendor.stop();
                        for (Customer customer : customers) customer.stop();

                        for (Thread thread : vendorThreads)
                            thread.interrupt();
                            scanner.close();
                        for (Thread thread : customerThreads)
                            thread.interrupt();
                            scanner.close();
                        System.out.println("System stopped successfully.");
                        break;
                    } else {
                        System.out.println("Invalid choice! Enter 1 to stop.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a numeric value.");
                    scanner.nextLine(); // Clear buffer
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static Configuration loadOrCreateConfiguration(String filePath) {
        Configuration config = Configuration.loadConfigurationFromFile(filePath);
        if (config != null) {
            System.out.println("Previous configuration found.");
            System.out.println("1. Use previous configuration");
            System.out.println("2. Enter new configuration");

            Scanner scanner = new Scanner(System.in);
            try {
                int choice = scanner.nextInt();
                if (choice == 1) return config;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Defaulting to new configuration.");
                scanner.nextLine(); // Clear buffer
            }
        }

        System.out.println("Enter new configuration:");
        Configuration newConfig = Configuration.configureFromInput();
        newConfig.saveConfigurationToFile(filePath);
        System.out.println("New configuration saved.");
        return newConfig;
    }
}
