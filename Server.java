import java.io.*;
import java.net.*;
import java.sql.SQLException;

public class Server implements Runnable {

    private ServerSocket serverSocket;
    private static final int PORT = 5000;
    private boolean running = true;

    // Constructor to initialize the server
    public Server() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);
        } catch (IOException e) {
            System.out.println("Server failed to start: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        while (running) {
            try {
                // Accept client connections
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                System.out.println("Error accepting client connection: " + e.getMessage());
            }
        }
    }

    // Inner class to handle client connections
    private class ClientHandler implements Runnable {

        private Socket clientSocket;
        private DataInputStream input;
        private DataOutputStream output;
        private DatabaseUtility dbUtil;
        private CommissionCalculator calculator;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.dbUtil = new DatabaseUtility();
            this.calculator = new CommissionCalculator();

            try {
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                System.out.println("Error setting up client handler: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                // Connect to the database
                dbUtil.connect();

                // Read client data
                String salesRepId = input.readUTF();
                int laptopsSold = input.readInt();

                // Calculate sales profit
                double salesProfit = calculator.calculateSalesProfit(laptopsSold);

                // Determine charge code
                String chargeCode = calculator.determineChargeCode(salesProfit);

                // Get charge rate from the database
                double chargeRate = dbUtil.getChargeRate(chargeCode);

                // Calculate commission
                double commission = calculator.calculateCommission(salesProfit, chargeRate);

                // Send results back to the client
                output.writeDouble(salesProfit);
                output.writeDouble(chargeRate);
                output.writeDouble(commission);
                output.flush();

                // Log the transaction
                System.out.printf("Processed sale for Rep %s: %d laptops, %.2f OMR profit, %.2f%% rate, %.2f OMR commission%n",
                        salesRepId, laptopsSold, salesProfit, chargeRate, commission);

            } catch (IOException | SQLException e) {
                System.out.println("Error processing client request: " + e.getMessage());
            } finally {
                // Close all connections
                closeConnections();
            }
        }

        private void closeConnections() {
            try {
                if (input != null) input.close();
                if (output != null) output.close();
                if (clientSocket != null) clientSocket.close();
                if (dbUtil != null) dbUtil.closeConnection();
            } catch (IOException e) {
                System.out.println("Error closing connections: " + e.getMessage());
            }
        }
    }

    // Method to stop the server
    public void stop() {
        running = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Error stopping server: " + e.getMessage());
        }
    }

    // Main method to start the server
    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
