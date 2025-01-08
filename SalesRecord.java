import java.util.*;

public class SalesRecord {

    private ArrayList<Sale> salesList;
    private Scanner scanner;

    // Constructor
    public SalesRecord() {
        salesList = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Utility class to represent a single sale
    public static class Sale {
        private String salesRepId;
        private int laptopsSold;
        private double salesProfit;
        private double commissionRate;
        private double commissionValue;

        public Sale(String salesRepId, int laptopsSold, double salesProfit, double commissionRate, double commissionValue) {
            this.salesRepId = salesRepId;
            this.laptopsSold = laptopsSold;
            this.salesProfit = salesProfit;
            this.commissionRate = commissionRate;
            this.commissionValue = commissionValue;
        }

        @Override
        public String toString() {
            return String.format("Sales Rep ID: %s, Laptops Sold: %d, Sales Profit: %.2f OMR, "
                    + "Commission Rate: %.2f%%, Commission Value: %.2f OMR",
                    salesRepId, laptopsSold, salesProfit, commissionRate, commissionValue);
        }

        // Getters
        public String getSalesRepId() { return salesRepId; }
        public int getLaptopsSold() { return laptopsSold; }
        public double getSalesProfit() { return salesProfit; }
        public double getCommissionRate() { return commissionRate; }
        public double getCommissionValue() { return commissionValue; }
    }

    // Display menu for the sales record system
    public void displayMenu() {
        int choice;
        do {
            System.out.println("\n=== Sales Record Management System ===");
            System.out.println("1. Add Sales Record");
            System.out.println("2. Add Sales Record at Specific Index");
            System.out.println("3. Search Sales Record");
            System.out.println("4. Display All Records");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addSalesRecord();
                        break;
                    case 2:
                        addSalesRecordAtIndex();
                        break;
                    case 3:
                        searchSalesRecord();
                        break;
                    case 4:
                        displayAllRecords();
                        break;
                    case 5:
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                choice = 0;
            }
        } while (choice != 5);
    }

    // Add a sales record to the end of the list
    private void addSalesRecord() {
        Sale sale = createSaleRecord();
        salesList.add(sale);
        System.out.println("Sales record added successfully!");
    }

    // Add a sales record at a specific index
    private void addSalesRecordAtIndex() {
        if (salesList.isEmpty()) {
            System.out.println("List is empty. Adding at index 0.");
            addSalesRecord();
            return;
        }

        System.out.println("Current list size: " + salesList.size());
        int index = -1;
        do {
            try {
                System.out.print("Enter index (0-" + salesList.size() + "): ");
                index = Integer.parseInt(scanner.nextLine());
                if (index < 0 || index > salesList.size()) {
                    System.out.println("Invalid index! Please enter a value between 0 and " + salesList.size());
                    index = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        } while (index == -1);

        Sale sale = createSaleRecord();
        salesList.add(index, sale);
        System.out.println("Sales record added at index " + index + " successfully!");
    }

    // Create a sales record with user input
    private Sale createSaleRecord() {
        System.out.print("Enter Sales Representative ID: ");
        String salesRepId = scanner.nextLine();

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

        // Calculate sales profit (90 OMR per laptop)
        double salesProfit = laptopsSold * 90.0;

        // Determine commission rate
        double commissionRate;
        if (salesProfit > 20000) {
            commissionRate = 10.0;
        } else if (salesProfit > 10000) {
            commissionRate = 5.0;
        } else {
            commissionRate = 0.0;
        }

        // Calculate commission value
        double commissionValue = salesProfit * (commissionRate / 100.0);

        return new Sale(salesRepId, laptopsSold, salesProfit, commissionRate, commissionValue);
    }

    // Search for a sales record by Sales Representative ID
    private void searchSalesRecord() {
        if (salesList.isEmpty()) {
            System.out.println("No records to search!");
            return;
        }

        System.out.print("Enter Sales Representative ID to search: ");
        String searchId = scanner.nextLine();

        boolean found = false;
        for (Sale sale : salesList) {
            if (sale.getSalesRepId().equalsIgnoreCase(searchId)) {
                System.out.println("\nRecord found:");
                System.out.println(sale);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No records found for Sales Representative ID: " + searchId);
        }
    }

    // Display all sales records
    private void displayAllRecords() {
        if (salesList.isEmpty()) {
            System.out.println("No records to display!");
            return;
        }

        System.out.println("\nAll Sales Records:");
        for (int i = 0; i < salesList.size(); i++) {
            System.out.println("Record " + (i + 1) + ": " + salesList.get(i));
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        SalesRecord salesRecord = new SalesRecord();
        salesRecord.displayMenu();
    }
}
