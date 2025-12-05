# CSV Data Analyzer

## Description

The CSV Data Analyzer reads sales data from a CSV file and generates useful business statistics. It calculates total revenue, identifies best-selling products, and computes average sale values, giving you insights into sales performance.

This challenge introduces you to file I/O and data processing. You'll learn how to read structured data from files, parse text into meaningful values, and perform calculations across datasets. The program demonstrates how software transforms raw data into actionable information.

You'll practice working with strings, handling file operations, and dealing with potential errors like missing files or malformed data. This is your first experience with processing external data sources, a fundamental skill in real-world applications where data rarely comes from user input alone.

## CSV Format

The program expects a CSV file with the following format:

```
date,product,quantity,price
2024-01-15,Wireless Mouse,25,29.99
2024-01-16,USB Keyboard,15,45.50
```

**Fields:**
- `date` - Date of sale (format: YYYY-MM-DD)
- `product` - Product name (string)
- `quantity` - Number of units sold (integer)
- `price` - Price per unit (decimal number)

**Sample Data:**
- `sales_data.csv` - A sample CSV file with 20 sales records
- `sales_data_with_errors.csv` - A test file with intentional errors for testing error handling

## Features

- **CSV file parsing**: Read and process sales data from files
- **Statistical analysis**: Calculate revenue, averages, and identify top products
- **Error handling**: Gracefully handle missing files and malformed data
- **Summary reports**: Present statistics in a clear, readable format
- **Row-by-row processing**: Handle files of varying sizes

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/csv-data-analyzer.jar Main
```

## How to Test

Run all tests:

```bash
./gradlew test
```

View test report:

```bash
open build/reports/tests/test/index.html
```

## Usage Example

Using the provided sample file:

```
Enter CSV file path: sales_data.csv

Sales Analysis Report
=====================
Total Revenue: $15,246.89
Best-Selling Product: Wireless Mouse (182 units)
Average Sale Value: $762.34
Total Records Processed: 20
Failed Records: 0
```

**Testing with errors:**

```
Enter CSV file path: sales_data_with_errors.csv

Error at line 4: For input string: "invalid"
Error at line 6: Index 3 out of bounds for length 3
Error at line 8: For input string: "not-a-number"

Sales Analysis Report
=====================
Total Revenue: $4,936.40
Best-Selling Product: Wireless Mouse (85 units)
Average Sale Value: $705.20
Total Records Processed: 7
Failed Records: 3
```

