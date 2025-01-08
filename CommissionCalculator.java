public class CommissionCalculator {

    // Constants
    private static final double LAPTOP_PRICE = 90.0; // Price in OMR
    private static final double TIER1_THRESHOLD = 20000.0; // Threshold for highest commission rate
    private static final double TIER2_THRESHOLD = 10000.0; // Threshold for second-highest commission rate

    /**
     * Calculates the sales profit based on the number of laptops sold.
     *
     * @param laptopsSold Number of laptops sold
     * @return Sales profit in OMR
     */
    public double calculateSalesProfit(int laptopsSold) {
        return LAPTOP_PRICE * laptopsSold;
    }

    /**
     * Determines the charge code based on the sales profit.
     *
     * @param salesProfit The total sales profit
     * @return Charge code ("RATE1", "RATE2", "RATE3")
     */
    public String determineChargeCode(double salesProfit) {
        if (salesProfit > TIER1_THRESHOLD) {
            return "RATE1";
        } else if (salesProfit > TIER2_THRESHOLD) {
            return "RATE2";
        } else {
            return "RATE3";
        }
    }

    /**
     * Calculates the commission based on the sales profit and charge rate.
     *
     * @param salesProfit The total sales profit
     * @param chargeRate  The charge rate in percentage
     * @return The commission amount in OMR
     */
    public double calculateCommission(double salesProfit, double chargeRate) {
        return salesProfit * (chargeRate / 100.0);
    }
}
