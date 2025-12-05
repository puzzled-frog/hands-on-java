import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DataAnalyzer {

    private int totalRecords;
    private double totalRevenue;
    private LocalDate firstSaleDate;
    private LocalDate lastSaleDate;
    HashMap<String, Integer> productQuantities = new HashMap<>();

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

    public double getAverageSaleValue() {
        return totalRecords > 0 ? (totalRevenue / totalRecords) : 0.0;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public LocalDate getFirstSaleDate() {
        return firstSaleDate;
    }

    public LocalDate getLastSaleDate() {
        return lastSaleDate;
    }
}
