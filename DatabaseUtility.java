import java.sql.*;

public class DatabaseUtility {

    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/sharaf_dg";
    private static final String USER = "root";
    private static final String PASSWORD = "your_password"; // Replace with your actual password

    private Connection connection;

    /**
     * Establishes a connection to the database.
     *
     * @throws SQLException If the connection cannot be established.
     */
    public void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Database connection established successfully.");
    }

    /**
     * Retrieves the charge rate for a given charge code.
     *
     * @param chargeCode The charge code (e.g., "RATE1", "RATE2", "RATE3").
     * @return The charge rate as a double.
     * @throws SQLException If there is an error querying the database.
     */
    public double getChargeRate(String chargeCode) throws SQLException {
        String query = "SELECT charge_rate FROM charge_rates WHERE charge_code = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, chargeCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("charge_rate");
            }
        }
        return 0.0; // Default rate if not found
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }
}
