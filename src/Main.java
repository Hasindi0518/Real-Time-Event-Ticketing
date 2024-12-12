import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class initialize and manage the real-time event ticketing system.
 */
public class Main {
    private static final String LOG_FILE = "log.txt";   //txt file name

    public static void main(String[] args) {
        final String CONFIG_FILE = "ticket_config.json";   //json file
        clearLogFile(); // Clear previous log file at the start

        // Load or create the configuration for the ticketing system
        Configuration config = loadOrCreateConfiguration(CONFIG_FILE);  // Load or create configuration
        log("Configuration loaded: " + config);

        // Exit if configuration failed
        if (config == null) {
            log("Failed to load or create configuration. Exiting.");
            return;
        }

        //Initialize ticket pool
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        int vendorCount = 3;
        int customerCount = 5;

        Thread[] vendorThreads = new Thread[vendorCount];
        Vendor[] vendors = new Vendor[vendorCount];

        Thread[] customerThreads = new Thread[customerCount];
        Customer[] customers = new Customer[customerCount];

        try {
            // Start vendors thread
            for (int i = 0; i < vendorCount; i++) {
                vendors[i] = new Vendor(i + 1, ticketPool, config.getTicketReleaseRate());
                vendorThreads[i] = new Thread(vendors[i], "Vendor-" + (i + 1));
                vendorThreads[i].start();
            }

            // Start customers thread
            for (int i = 0; i < customerCount; i++) {
                customers[i] = new Customer(i + 1, ticketPool, config.getCustomerRetrievalRate());
                customerThreads[i] = new Thread(customers[i], "Customer-" + (i + 1));
                customerThreads[i].start();
            }

            // Command-line menu
            Scanner scanner = new Scanner(System.in);
            log("**** Ticketing System Menu ****");
            log("1. Stop");

            while (true) {
                try {
                    int choice = scanner.nextInt();
                    log("User choice: " + choice);

                    //to stop system.
                    if (choice == 1) {
                        log("Stopping system...");
                        for (Vendor vendor : vendors)
                            vendor.stop();
                        for (Customer customer : customers)
                            customer.stop();

                        for (Thread thread : vendorThreads)
                            thread.interrupt();
                        for (Thread thread : customerThreads)
                            thread.interrupt();

                        log("System stopped successfully.");
                        break;
                    } else {
                        log("Invalid choice! Enter 1 to stop.");
                    }
                } catch (InputMismatchException e) {
                    log("Invalid input! Please enter a numeric value.");
                    scanner.nextLine(); // Clear buffer
                }
            }
        } catch (Exception e) {
            log("An error occurred: " + e.getMessage());
        }
    }

    // Loads or creates a configuration file
    private static Configuration loadOrCreateConfiguration(String filePath) {
        Configuration config = Configuration.loadConfigurationFromFile(filePath);
        if (config != null) {
            log("Previous configuration found.");
            log("1. Use previous configuration");
            log("2. Enter new configuration");

            Scanner scanner = new Scanner(System.in);
            try {
                log("Enter your choice: ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    log("** Using previous configuration **");
                    return config;
                } else if (choice == 2) {
                    log("** Start entering new configuration **");
                } else {
                    log("Invalid input. Try again.");
                }
            } catch (InputMismatchException e) {
                log("Invalid input! Defaulting to new configuration.");
                scanner.nextLine(); // Clear buffer
            }
        }

        log("Enter new configuration");
        Configuration newConfig = Configuration.configureFromInput();
        newConfig.saveConfigurationToFile(filePath);
        log("New configuration saved.");
        return newConfig;
    }

    // Logs a message to file
    public static void log(String message) {
        System.out.println(message);
        try (FileWriter fw = new FileWriter(LOG_FILE, true); PrintWriter pw = new PrintWriter(fw)) {
            pw.println(message);
        } catch (IOException e) {
            System.out.println("Failed to write to log file: " + e.getMessage());
        }
    }

    // Clears the log file at startup
    private static void clearLogFile() {
        try (FileWriter fw = new FileWriter(LOG_FILE, false)) {
            fw.write(""); // Overwrite the file with an empty string
        } catch (IOException e) {
            System.out.println("Failed to clear log file: " + e.getMessage());
        }
    }
}
