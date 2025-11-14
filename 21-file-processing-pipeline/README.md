# File Processing Pipeline

## Description

The File Processing Pipeline handles uploaded CSV files asynchronously with validation, transformation, database storage, and completion notifications. Process large files efficiently with progress tracking, handle errors gracefully, and provide detailed reports on success and failure rates.

This challenge teaches you asynchronous processing with CompletableFuture, batch operations, and progress tracking. You'll learn how to process files without blocking the main thread, use streaming to handle files that don't fit in memory, and coordinate complex multi-step workflows. The pipeline demonstrates how to build systems that handle time-consuming operations efficiently.

You'll practice designing APIs for long-running operations, implementing resumable processing, and providing real-time feedback to users. This is essential for applications that need to process large datasets or perform operations that take minutes or hours.

## Features

- **Async file upload**: Immediate response with job ID
- **Validation layer**: Check structure, data types, and business rules
- **Transformation**: Clean, normalize, and enrich data
- **Batch processing**: Efficient database inserts in chunks
- **Progress tracking**: Real-time percentage and row counts
- **Error handling**: Continue on errors, collect all failures
- **Concurrent processing**: Handle multiple file uploads simultaneously
- **Job management**: View status, pause, resume, or retry
- **Notification system**: Email on completion with summary
- **Error reports**: Downloadable file with failed rows and reasons

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/file-processing-pipeline.jar
```

API will be available at: `http://localhost:8080`

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

```bash
# Upload a CSV file
curl -X POST http://localhost:8080/api/files/upload \
  -F "file=@customers.csv"

# Response (immediate):
{
  "jobId": "job-12345",
  "status": "QUEUED",
  "fileName": "customers.csv",
  "totalRows": 50000
}

# Check progress
curl http://localhost:8080/api/files/jobs/job-12345

# Response:
{
  "jobId": "job-12345",
  "status": "PROCESSING",
  "fileName": "customers.csv",
  "progress": 42.5,
  "rowsProcessed": 21250,
  "totalRows": 50000,
  "successCount": 21100,
  "failureCount": 150,
  "estimatedTimeRemaining": "00:02:15"
}

# When complete:
{
  "jobId": "job-12345",
  "status": "COMPLETED",
  "progress": 100,
  "rowsProcessed": 50000,
  "successCount": 49800,
  "failureCount": 200,
  "duration": "00:04:32",
  "errorReportUrl": "/api/files/jobs/job-12345/errors"
}

# Download error report
curl http://localhost:8080/api/files/jobs/job-12345/errors \
  -o errors.csv

# Retry failed job
curl -X POST http://localhost:8080/api/files/jobs/job-12345/retry

# Cancel running job
curl -X DELETE http://localhost:8080/api/files/jobs/job-12345
```

