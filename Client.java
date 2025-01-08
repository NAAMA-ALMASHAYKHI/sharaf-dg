import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client implements Runnable {

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Scanner scanner;

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    // Constructor
    public Client() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        try {
            // Connect to server
            connectToServer();

            // Get sales data from user
            String salesRepId = getSalesRepId();
            int laptopsSold = getLaptopsSold();

            // Send data to server
            sendDataToServer(salesRepId, laptopsSold);

            // Receive and display results
            receiveAndDisplayResults();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close connections
            closeConnection();
        }
    }

    // Method to connect to the server
    private void connectToServer() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        System.out.println("Connected to server successfully!");
    }

    // Method to get Sales Representative ID
    private String getSalesRepId() {
        String salesRepId;
        do {
            System.out.print("Enter Sales Representative ID: ");
            salesRepId = scanner.nextLine().trim();
            if (salesRepId.isEmpty()) {
                System.out.println("Sales Representative ID cannot be empty!");
            }
        } while (salesRepId.isEmpty());
        return salesRepId;
    }

    // Method to get the number of laptops sold
    private int getLaptopsSold() {
        int laptopsSold = -1;
        do {
            try {
                System.out.print("Enter number of laptops sold: ");
                laptopsSold = Integer.parseInt(scanner.nextLine());
                if (laptopsSold < 0) {
                    System.out.println("Number of laptops cannot be negative!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        } while (laptopsSold < 0);
        return laptopsSold;
    }

    // Method to send data to the server
    private void sendDataToServer(String salesRepId, int laptopsSold) throws IOException {
        output.writeUTF(salesRepId);
        output.writeInt(laptopsSold);
        output.flush();
    }

    // Method to receive and display results from the server
    private void receiveAndDisplayResults() throws IOException {
        double salesProfit = input.readDouble();
        double commissionRate = input.readDouble();
        double commissionValue = input.readDouble();

        System.out.println("\nResults:");
        System.out.println("Sales Profit: " + salesProfit + " OMR");
        System.out.println("Commission Rate: " + commissionRate + "%");
        System.out.println("Commission Value: " + commissionValue + " OMR");
    }

    // Method to close all connections
    private void closeConnection() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
            if (scanner != null) scanner.close();
        } catch (IOException e) {
            System.out.println("Error closing connections: " + e.getMessage());
        }
    }

    // Main method to start the client
    public static void main(String[] args) {
        Client client = new Client();
        Thread clientThread = new Thread(client);
        clientThread.start();
    }
}
