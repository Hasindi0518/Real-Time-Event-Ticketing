import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Configuration {
    private final int totalTickets;
    private final int ticketReleaseRate;
    private final int customerRetrievalRate;
    private final int maxTicketCapacity;

    public Configuration(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public static Configuration configureFromInput() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the total number of tickets: ");
            int totalTickets = scanner.nextInt();

            System.out.print("Enter ticket release rate (number of tickets a vendor releases per cycle): ");
            int releaseRate = scanner.nextInt();

            System.out.print("Enter customer retrieval rate (number of tickets a customer purchases per cycle): ");
            int retrievalRate = scanner.nextInt();

            System.out.print("Enter maximum ticket capacity: ");
            int maxCapacity = scanner.nextInt();

            return new Configuration(totalTickets, releaseRate, retrievalRate, maxCapacity);
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter numeric values.");
            return configureFromInput(); // Retry on invalid input
        }
    }

    public void saveConfigurationToFile(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(this, writer);
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to save configuration: " + e.getMessage());
        }
    }

    public static Configuration loadConfigurationFromFile(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, Configuration.class);
        } catch (IOException e) {
            System.out.println("Failed to load configuration: " + e.getMessage());
            return null;
        }
    }
}
