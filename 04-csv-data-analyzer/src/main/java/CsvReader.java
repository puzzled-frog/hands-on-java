import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class CsvReader {

    public void readFile(String filePath, DataAnalyzer analyzer) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // to skip the csv header
            String line;
            int lineCount = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] fields = line.split(",");
                lineCount++;
                try {
                    SalesRecord salesRecord = SalesRecord.fromCsvFields(fields);
                    analyzer.processRecord(salesRecord);
                } catch (DateTimeParseException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
                    analyzer.recordError();
                    System.err.println("Error processing line " + lineCount + ": " + e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found at " + filePath + "\n" + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }
}
