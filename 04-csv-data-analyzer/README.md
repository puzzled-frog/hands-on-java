# CSV Data Analyzer

## Description

The CSV Data Analyzer reads sales data from a CSV file and generates useful business statistics. It calculates total revenue, identifies best-selling products, and computes average sale values, giving you insights into sales performance.

This challenge introduces you to file I/O and data processing. You'll learn how to read structured data from files, parse text into meaningful values, and perform calculations across datasets. The program demonstrates how software transforms raw data into actionable information.

You'll practice working with strings, handling file operations, and dealing with potential errors like missing files or malformed data. This is your first experience with processing external data sources, a fundamental skill in real-world applications where data rarely comes from user input alone.

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

```
Enter CSV file path: sales_data.csv

Sales Analysis Report
=====================
Total Revenue: $15,487.50
Best-Selling Product: Wireless Mouse (487 units)
Average Sale Value: $47.23
Total Records Processed: 328
Failed Records: 2
```

