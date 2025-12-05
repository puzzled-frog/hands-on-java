import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DataAnalyzerTest {

    private DataAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        analyzer = new DataAnalyzer();
    }

    @Test
    void testInitialState() {
        assertEquals(0.0, analyzer.getTotalRevenue(), 0.001);
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(0, analyzer.getTotalFailedRecords());
        assertEquals("None", analyzer.getBestSellingProduct());
        assertEquals(0, analyzer.getBestSellingQuantity());
        assertEquals("No valid records", analyzer.getDateRange());
    }

    @Test
    void testProcessSingleRecord() {
        SalesRecord record = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Wireless Mouse",
            10,
            25.50
        );
        
        analyzer.processRecord(record);
        
        assertEquals(255.0, analyzer.getTotalRevenue(), 0.001);
        assertEquals(1, analyzer.getTotalRecords());
        assertEquals("Wireless Mouse", analyzer.getBestSellingProduct());
        assertEquals(10, analyzer.getBestSellingQuantity());
        assertEquals(255.0, analyzer.getAverageSaleValue(), 0.001);
    }

    @Test
    void testProcessMultipleRecordsSameProduct() {
        SalesRecord record1 = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Mouse",
            10,
            25.0
        );
        SalesRecord record2 = new SalesRecord(
            LocalDate.of(2024, 1, 16),
            "Mouse",
            15,
            25.0
        );
        
        analyzer.processRecord(record1);
        analyzer.processRecord(record2);
        
        assertEquals(625.0, analyzer.getTotalRevenue(), 0.001);
        assertEquals(2, analyzer.getTotalRecords());
        assertEquals("Mouse", analyzer.getBestSellingProduct());
        assertEquals(25, analyzer.getBestSellingQuantity()); // 10 + 15
    }

    @Test
    void testProcessMultipleRecordsDifferentProducts() {
        SalesRecord record1 = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Mouse",
            10,
            25.0
        );
        SalesRecord record2 = new SalesRecord(
            LocalDate.of(2024, 1, 16),
            "Keyboard",
            20,
            45.0
        );
        SalesRecord record3 = new SalesRecord(
            LocalDate.of(2024, 1, 17),
            "Monitor",
            5,
            150.0
        );
        
        analyzer.processRecord(record1);
        analyzer.processRecord(record2);
        analyzer.processRecord(record3);
        
        assertEquals(1900.0, analyzer.getTotalRevenue(), 0.001); // 250 + 900 + 750
        assertEquals(3, analyzer.getTotalRecords());
        assertEquals("Keyboard", analyzer.getBestSellingProduct());
        assertEquals(20, analyzer.getBestSellingQuantity());
        assertEquals(633.33, analyzer.getAverageSaleValue(), 0.01);
    }

    @Test
    void testDateRangeWithSingleRecord() {
        SalesRecord record = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Mouse",
            10,
            25.0
        );
        
        analyzer.processRecord(record);
        
        assertEquals("2024-01-15 to 2024-01-15", analyzer.getDateRange());
    }

    @Test
    void testDateRangeWithMultipleRecords() {
        SalesRecord record1 = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Mouse",
            10,
            25.0
        );
        SalesRecord record2 = new SalesRecord(
            LocalDate.of(2024, 1, 20),
            "Keyboard",
            5,
            45.0
        );
        SalesRecord record3 = new SalesRecord(
            LocalDate.of(2024, 1, 18),
            "Monitor",
            3,
            150.0
        );
        
        analyzer.processRecord(record1);
        analyzer.processRecord(record2);
        analyzer.processRecord(record3);
        
        assertEquals("2024-01-15 to 2024-01-20", analyzer.getDateRange());
    }

    @Test
    void testRecordError() {
        analyzer.recordError();
        assertEquals(1, analyzer.getTotalFailedRecords());
        
        analyzer.recordError();
        analyzer.recordError();
        assertEquals(3, analyzer.getTotalFailedRecords());
    }

    @Test
    void testAverageSaleValueWithNoRecords() {
        assertEquals(0.0, analyzer.getAverageSaleValue(), 0.001);
    }

    @Test
    void testBestSellingProductWithTie() {
        // When two products have same quantity, either is acceptable
        SalesRecord record1 = new SalesRecord(
            LocalDate.of(2024, 1, 15),
            "Mouse",
            10,
            25.0
        );
        SalesRecord record2 = new SalesRecord(
            LocalDate.of(2024, 1, 16),
            "Keyboard",
            10,
            45.0
        );
        
        analyzer.processRecord(record1);
        analyzer.processRecord(record2);
        
        String bestSeller = analyzer.getBestSellingProduct();
        assertTrue(bestSeller.equals("Mouse") || bestSeller.equals("Keyboard"));
        assertEquals(10, analyzer.getBestSellingQuantity());
    }

    @Test
    void testCombinedAnalysisScenario() {
        // Simulate realistic sales data
        SalesRecord[] records = {
            new SalesRecord(LocalDate.of(2024, 1, 15), "Mouse", 25, 29.99),
            new SalesRecord(LocalDate.of(2024, 1, 15), "Keyboard", 15, 45.50),
            new SalesRecord(LocalDate.of(2024, 1, 16), "Mouse", 32, 29.99),
            new SalesRecord(LocalDate.of(2024, 1, 17), "Monitor", 8, 89.99),
            new SalesRecord(LocalDate.of(2024, 1, 18), "Mouse", 28, 29.99)
        };
        
        for (SalesRecord record : records) {
            analyzer.processRecord(record);
        }
        
        assertEquals(5, analyzer.getTotalRecords());
        assertEquals("Mouse", analyzer.getBestSellingProduct());
        assertEquals(85, analyzer.getBestSellingQuantity()); // 25 + 32 + 28
        assertEquals("2024-01-15 to 2024-01-18", analyzer.getDateRange());
        assertTrue(analyzer.getTotalRevenue() > 0);
    }
}

