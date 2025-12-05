import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CsvReaderTest {

    private CsvReader reader;
    private DataAnalyzer analyzer;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        reader = new CsvReader();
        analyzer = new DataAnalyzer();
        System.setErr(new PrintStream(errContent));
    }

    @Test
    void testReadValidCsvFile() {
        String filePath = "src/test/resources/test_valid.csv";
        
        reader.readFile(filePath, analyzer);
        
        assertEquals(3, analyzer.getTotalRecords());
        assertEquals(0, analyzer.getTotalFailedRecords());
        assertEquals(780.0, analyzer.getTotalRevenue(), 0.001); // 255 + 225 + 300
        assertEquals("Mouse", analyzer.getBestSellingProduct()); // 10 units
    }

    @Test
    void testReadCsvWithErrors() {
        String filePath = "src/test/resources/test_with_errors.csv";
        
        reader.readFile(filePath, analyzer);
        
        assertEquals(2, analyzer.getTotalRecords()); // Only 2 valid records
        assertEquals(3, analyzer.getTotalFailedRecords()); // 3 error rows
        
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error processing line"));
    }

    @Test
    void testReadCsvWithEmptyLines() {
        String filePath = "src/test/resources/test_empty_lines.csv";
        
        reader.readFile(filePath, analyzer);
        
        assertEquals(2, analyzer.getTotalRecords());
        assertEquals(0, analyzer.getTotalFailedRecords());
    }

    @Test
    void testReadEmptyCsvFile() {
        String filePath = "src/test/resources/test_empty.csv";
        
        reader.readFile(filePath, analyzer);
        
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(0, analyzer.getTotalFailedRecords());
    }

    @Test
    void testReadNonExistentFile() {
        String filePath = "non_existent_file.csv";
        
        reader.readFile(filePath, analyzer);
        
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("File not found"));
        assertTrue(errorOutput.contains(filePath));
    }

    @Test
    void testReadFileWithInvalidDateFormat() throws IOException {
        Path tempFile = Files.createTempFile("test_bad_date", ".csv");
        Files.writeString(tempFile, """
            date,product,quantity,price
            not-a-date,Mouse,10,25.50
            """);
        
        reader.readFile(tempFile.toString(), analyzer);
        
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(1, analyzer.getTotalFailedRecords());
        
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error processing line"));
        
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testReadFileWithMissingFields() throws IOException {
        Path tempFile = Files.createTempFile("test_missing_fields", ".csv");
        Files.writeString(tempFile, """
            date,product,quantity,price
            2024-01-15,Mouse
            """);
        
        reader.readFile(tempFile.toString(), analyzer);
        
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(1, analyzer.getTotalFailedRecords());
        
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testAnalyzerReceivesCorrectData() {
        String filePath = "src/test/resources/test_valid.csv";
        
        reader.readFile(filePath, analyzer);
        
        // Verify analyzer accumulated data correctly
        assertEquals("2024-01-15 to 2024-01-17", analyzer.getDateRange());
        assertTrue(analyzer.getTotalRevenue() > 0);
        assertEquals(260.0, analyzer.getAverageSaleValue(), 0.01); // 780 / 3
    }

    @Test
    void testMultipleReadsWithSameAnalyzer() {
        String filePath = "src/test/resources/test_valid.csv";
        
        reader.readFile(filePath, analyzer);
        int firstRecordCount = analyzer.getTotalRecords();
        
        reader.readFile(filePath, analyzer);
        
        // Analyzer should accumulate from both reads
        assertEquals(firstRecordCount * 2, analyzer.getTotalRecords());
    }

    @Test
    void testFileWithNegativeQuantityIsRejected() throws IOException {
        Path tempFile = Files.createTempFile("test_negative", ".csv");
        Files.writeString(tempFile, """
            date,product,quantity,price
            2024-01-15,Mouse,-10,25.50
            """);
        
        reader.readFile(tempFile.toString(), analyzer);
        
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(1, analyzer.getTotalFailedRecords());
        
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Quantity must be positive") || 
                   errorOutput.contains("Error processing line"));
        
        Files.deleteIfExists(tempFile);
    }

    @Test
    void testFileWithNegativePriceIsRejected() throws IOException {
        Path tempFile = Files.createTempFile("test_negative_price", ".csv");
        Files.writeString(tempFile, """
            date,product,quantity,price
            2024-01-15,Mouse,10,-25.50
            """);
        
        reader.readFile(tempFile.toString(), analyzer);
        
        assertEquals(0, analyzer.getTotalRecords());
        assertEquals(1, analyzer.getTotalFailedRecords());
        
        Files.deleteIfExists(tempFile);
    }

    @org.junit.jupiter.api.AfterEach
    void restoreStreams() {
        System.setErr(originalErr);
    }
}

