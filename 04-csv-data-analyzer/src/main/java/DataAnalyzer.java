import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyzer {

    private int totalRecords;
    private int totalFailedRecords;
    private double totalRevenue;
    private LocalDate firstSaleDate;
    private LocalDate lastSaleDate;
    private final Map<String, Integer> productQuantities = new HashMap<>();

    public void processRecord(SalesRecord record) {
        totalRevenue += record.calculateRevenue();
        productQuantities.merge(record.getProduct(), record.getQuantity(), Integer::sum);
        totalRecords++;

        LocalDate recordDate = record.getDate();
        if (firstSaleDate == null || recordDate.isBefore(firstSaleDate)) {
            firstSaleDate = recordDate;
        }
        if (lastSaleDate == null || recordDate.isAfter(lastSaleDate)) {
            lastSaleDate = recordDate;
        }
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public String getBestSellingProduct() {
        return productQuantities.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("None");
    }

    public int getBestSellingQuantity() {
        return productQuantities.values().stream()
                .max(Integer::compare)
                .orElse(0);
    }

    public double getAverageSaleValue() {
        return totalRecords > 0 ? (totalRevenue / totalRecords) : 0.0;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public String getDateRange() {
        return (firstSaleDate != null && lastSaleDate != null) ? (firstSaleDate + " to " + lastSaleDate) : "No valid records";
    }

    public void recordError() {
        totalFailedRecords++;
    }

    public int getTotalFailedRecords() {
        return totalFailedRecords;
    }
}
