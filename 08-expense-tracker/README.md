# Expense Tracker with Reports

## Description

The Expense Tracker helps you monitor your spending by categorizing expenses and generating monthly reports. Track where your money goes, export data to CSV and JSON formats, and analyze your spending patterns over time.

This challenge introduces you to Java's date and time API, generics, and working with multiple file formats. You'll learn how to handle temporal data properly, use generic types to write flexible code, and serialize data in different formats for various purposes. The application demonstrates real-world data transformation: taking user input, storing it with metadata, and presenting it in meaningful ways.

You'll practice working with categories, calculating aggregations, and generating reports that provide insights. This teaches you how applications process and present data to help users make informed decisions.

## Features

- **Expense management**: Add, edit, and delete expenses with dates and categories
- **Predefined categories**: Food, Transport, Entertainment, Bills, and Other
- **Monthly reports**: Automatically generated spending summaries
- **Trend analysis**: Month-over-month comparisons and patterns
- **Multiple export formats**: CSV for spreadsheets, JSON for data exchange
- **Date filtering**: View expenses by custom date ranges
- **Category totals**: See spending breakdown by type

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/expense-tracker.jar Main
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
=== Expense Tracker ===
1. Add expense
2. View expenses
3. Generate monthly report
4. Export to CSV
5. Export to JSON
6. Exit

Choose an option: 1
Amount: 45.50
Category: Food
Description: Lunch at cafe
Date (YYYY-MM-DD): 2024-01-15
Expense added!

Choose an option: 3
Select month (1-12): 1
Select year: 2024

January 2024 Report
===================
Food: $245.00 (35%)
Transport: $120.00 (17%)
Entertainment: $180.00 (26%)
Bills: $150.00 (22%)
Total: $695.00

Change from December: +$45.00 (+6.9%)
```

