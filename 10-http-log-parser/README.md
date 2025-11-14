# HTTP Log Parser

## Description

The HTTP Log Parser analyzes web server access logs to generate comprehensive analytics. Parse Apache/Nginx log files, extract meaningful information, and produce insights about traffic patterns, popular pages, error rates, and user behavior.

This challenge introduces you to regular expressions, functional programming with streams, and data aggregation. You'll learn how to process large text files efficiently, extract structured information from unstructured logs, and transform raw data into actionable metrics. The parser handles malformed entries gracefully, demonstrating robust error handling in production scenarios.

You'll practice working with the Stream API to process data declaratively, use collectors to aggregate results, and apply functional programming concepts like map, filter, and reduce. This teaches you modern Java approaches to data processing that are both expressive and performant.

## Features

- **Log format support**: Parse standard Apache/Nginx combined log format
- **Traffic analytics**: Requests per hour, peak times, total requests
- **Page popularity**: Most visited URLs and endpoints
- **Error analysis**: Error rates, common status codes, failure patterns
- **User agent detection**: Browser types, bots, and device categories
- **Bandwidth tracking**: Total data transferred
- **Security insights**: Detect suspicious patterns like SQL injection attempts
- **Robust parsing**: Handle and report malformed entries

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/http-log-parser.jar Main
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
Enter log file path: access.log

Parsing log file... Done!

=== Traffic Analytics ===
Total Requests: 45,892
Valid Entries: 45,720 (99.6%)
Malformed Entries: 172 (0.4%)

=== Most Visited Pages ===
1. /index.html - 8,943 requests (19.5%)
2. /products - 6,721 requests (14.6%)
3. /api/users - 4,832 requests (10.5%)

=== Error Analysis ===
Error Rate: 2.3%
404 Not Found: 892 occurrences
500 Internal Server Error: 143 occurrences

=== Peak Hours ===
14:00-15:00: 6,432 requests
15:00-16:00: 5,981 requests

=== User Agents ===
Chrome: 58.3%
Firefox: 21.7%
Safari: 15.2%
Bots: 4.8%
```

