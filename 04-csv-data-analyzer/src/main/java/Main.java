import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.print("Enter CSV file path: ");

        try (Scanner scanner = new Scanner(System.in)) {
            String filePath = scanner.nextLine();
            DataAnalyzer analyzer = new DataAnalyzer();
            CsvReader csvReader = new CsvReader();
            csvReader.readFile(filePath, analyzer);

            System.out.println("Sales Analysis Report");
            System.out.println("=====================");
            System.out.printf("Total Revenue: %.2f\n", analyzer.getTotalRevenue());
            System.out.printf("Best-Selling Product: %s\n", analyzer.getBestSellingProduct());
            System.out.printf("Average Sale Value: %.2f\n", analyzer.getAverageSaleValue());
            System.out.println("Total Records Processed: " + analyzer.getTotalRecords());
        }

    }

}
