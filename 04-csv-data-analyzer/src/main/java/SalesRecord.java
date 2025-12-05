import java.time.LocalDate;

public class SalesRecord {

    private final LocalDate date;
    private final String product;
    private final int quantity;
    private final double price;

    public SalesRecord(LocalDate date, String product, int quantity, double price) {
        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public static SalesRecord fromCsvFields(String[] fields) {
        return new SalesRecord(
                LocalDate.parse(fields[0]),
                fields[1],
                Integer.parseInt(fields[2]),
                Double.parseDouble(fields[3])
        );
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double calculateRevenue() {
        return this.price * this.quantity;
    }
}
