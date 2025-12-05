import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SalesRecordTest {

    @Test
    void testConstructorWithValidValues() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        SalesRecord record = new SalesRecord(date, "Wireless Mouse", 25, 29.99);
        
        assertEquals(date, record.getDate());
        assertEquals("Wireless Mouse", record.getProduct());
        assertEquals(25, record.getQuantity());
        assertEquals(29.99, record.getPrice(), 0.001);
    }

    @Test
    void testConstructorRejectsNegativeQuantity() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SalesRecord(date, "Mouse", -5, 29.99)
        );
        
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void testConstructorRejectsZeroQuantity() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SalesRecord(date, "Mouse", 0, 29.99)
        );
        
        assertEquals("Quantity must be positive", exception.getMessage());
    }

    @Test
    void testConstructorRejectsNegativePrice() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new SalesRecord(date, "Mouse", 25, -10.0)
        );
        
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    void testFromCsvFieldsWithValidData() {
        String[] fields = {"2024-01-15", "Wireless Mouse", "25", "29.99"};
        
        SalesRecord record = SalesRecord.fromCsvFields(fields);
        
        assertEquals(LocalDate.of(2024, 1, 15), record.getDate());
        assertEquals("Wireless Mouse", record.getProduct());
        assertEquals(25, record.getQuantity());
        assertEquals(29.99, record.getPrice(), 0.001);
    }

    @Test
    void testFromCsvFieldsWithInvalidDate() {
        String[] fields = {"invalid-date", "Mouse", "25", "29.99"};
        
        assertThrows(java.time.format.DateTimeParseException.class,
            () -> SalesRecord.fromCsvFields(fields)
        );
    }

    @Test
    void testFromCsvFieldsWithInvalidQuantity() {
        String[] fields = {"2024-01-15", "Mouse", "not-a-number", "29.99"};
        
        assertThrows(NumberFormatException.class,
            () -> SalesRecord.fromCsvFields(fields)
        );
    }

    @Test
    void testFromCsvFieldsWithInvalidPrice() {
        String[] fields = {"2024-01-15", "Mouse", "25", "invalid-price"};
        
        assertThrows(NumberFormatException.class,
            () -> SalesRecord.fromCsvFields(fields)
        );
    }

    @Test
    void testFromCsvFieldsWithMissingFields() {
        String[] fields = {"2024-01-15", "Mouse"};
        
        assertThrows(ArrayIndexOutOfBoundsException.class,
            () -> SalesRecord.fromCsvFields(fields)
        );
    }

    @Test
    void testCalculateRevenue() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        SalesRecord record = new SalesRecord(date, "Mouse", 10, 25.50);
        
        assertEquals(255.0, record.calculateRevenue(), 0.001);
    }

    @Test
    void testCalculateRevenueWithDecimalQuantity() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        SalesRecord record = new SalesRecord(date, "Keyboard", 3, 45.99);
        
        assertEquals(137.97, record.calculateRevenue(), 0.001);
    }
}

