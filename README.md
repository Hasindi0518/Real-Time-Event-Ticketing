# Real-Time Event Ticketing System

This project simulates a real-time event ticketing system using Java. It includes classes for configuration, ticket pool management, vendors, and customers. The system allows vendors to add tickets to a pool and customers to purchase tickets from the pool.

## Overview

The system is designed to manage the sale and distribution of event tickets in real-time. It consists of several key components:

1. **Configuration**: Handles the setup and storage of system settings.
2. **TicketPool**: Manages the pool of available tickets.
3. **Vendor**: Simulates vendors adding tickets to the pool.
4. **Customer**: Simulates customers purchasing tickets from the pool.
5. **Main**: Initializes and manages the overall system.

## Components

### Configuration

The `Configuration` class is responsible for setting up the system parameters. It allows users to input settings such as the total number of tickets, ticket release rate, customer retrieval rate, and maximum ticket capacity. These settings can be saved to and loaded from a JSON file, ensuring that the system can be easily configured and reused.

### TicketPool

The `TicketPool` class manages the tickets available for purchase. It uses a queue to ensure that tickets are handled in a First-In-First-Out (FIFO) order. The class includes methods for adding tickets to the pool and purchasing tickets from the pool, with synchronization to ensure thread safety.

### Vendor

The `Vendor` class represents a vendor that periodically adds tickets to the ticket pool. Each vendor runs in its own thread, adding a specified number of tickets at regular intervals. The class includes methods to start and stop the vendor's operations.

### Customer

The `Customer` class represents a customer that purchases tickets from the ticket pool. Each customer runs in its own thread, attempting to purchase a random number of tickets at regular intervals. The class includes methods to start and stop the customer's operations.

### Main

The `Main` class is the entry point of the application. It initializes the configuration, creates the ticket pool, and starts the vendor and customer threads. It also includes a command-line interface for stopping the system and logging activities to a file.

## Usage

1. **Configuration**: The system prompts the user to enter configuration settings or load existing settings from a file.
2. **Initialization**: The `Main` class initializes the ticket pool and starts the vendor and customer threads.
3. **Operation**: Vendors add tickets to the pool, and customers purchase tickets from the pool. The system logs all activities to a file.
4. **Termination**: The user can stop the system through the command-line interface, which gracefully stops all threads and logs the shutdown process.

## Logging

The system logs all significant events, such as ticket additions and purchases, to a log file. This helps in monitoring the system's operations and debugging any issues that arise.

## Conclusion

This project demonstrates a simple yet effective way to manage a real-time event ticketing system using Java. It covers essential concepts such as configuration management, thread synchronization, and logging, making it a useful reference for similar applications.

---

Feel free to ask if you need more details or have any specific questions!
