import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {
    public void readFile(String filePath, DataAnalyzer analyzer) {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            bufferedReader.readLine(); // to skip the csv header
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");
                SalesRecord salesRecord = SalesRecord.fromCsvFields(fields);
                analyzer.processRecord(salesRecord);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

    }
}
