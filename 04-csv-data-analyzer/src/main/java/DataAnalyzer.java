import java.util.HashMap;
import java.util.Map;

public class DataAnalyzer {

    private int totalRecords;
    private double totalRevenue;
    HashMap<String, Integer> productQuantities = new HashMap<>();

    public void processRecord(SalesRecord record) {
        totalRevenue += record.calculateRevenue();
        productQuantities.merge(record.getProduct(), record.getQuantity(), Integer::sum);
        totalRecords++;
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

    public double getAverageSaleValue() {
        return totalRecords > 0 ? (totalRevenue / totalRecords) : 0.0;
    }

    public int getTotalRecords() {
        return totalRecords;
    }
}
