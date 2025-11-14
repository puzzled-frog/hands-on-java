# Batch Processing System with Spring Batch

## Description

The Batch Processing System uses Spring Batch to process large datasets in scheduled jobs. Implement chunk-oriented processing for reading from database, transforming data, and writing to external systems. Handle job scheduling, restart failed jobs from the last successful point, and monitor job execution through a dashboard.

This challenge introduces you to batch processing patterns and Spring Batch framework. You'll learn how to design jobs that process millions of records efficiently, implement skip and retry logic for transient failures, and partition jobs for parallel processing. The system demonstrates how to build reliable data pipelines that run on schedules and handle massive datasets.

You'll practice designing batch architectures, tuning performance with chunk sizes, and implementing monitoring for long-running jobs. This is essential knowledge for ETL pipelines, report generation, and any data processing that happens in the background.

## Features

- **Spring Batch jobs**: Multi-step batch processing
- **Chunk processing**: Read-process-write pattern with configurable chunks
- **Job scheduling**: Cron-based automatic execution
- **Restartability**: Resume from last successful chunk after failure
- **Skip logic**: Continue processing when individual items fail
- **Retry logic**: Retry transient failures before skipping
- **Parallel processing**: Partition jobs across threads
- **Job parameters**: Pass runtime parameters to jobs
- **Execution history**: Track all job runs with metrics
- **Monitoring dashboard**: View job status, progress, and performance

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/batch-processing-system.jar
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
# Trigger a job manually
curl -X POST http://localhost:8080/api/batch/jobs/monthlyReport/run \
  -H "Content-Type: application/json" \
  -d '{
    "month": "2024-01",
    "outputFormat": "CSV"
  }'

# Response:
{
  "jobExecutionId": 12345,
  "status": "STARTING",
  "startTime": "2024-01-15T10:30:00Z"
}

# Check job status
curl http://localhost:8080/api/batch/jobs/executions/12345

# Response (in progress):
{
  "jobExecutionId": 12345,
  "jobName": "monthlyReport",
  "status": "STARTED",
  "startTime": "2024-01-15T10:30:00Z",
  "progress": {
    "totalItems": 1000000,
    "processedItems": 450000,
    "percentage": 45.0,
    "estimatedTimeRemaining": "00:08:30"
  },
  "steps": [
    {
      "stepName": "extractData",
      "status": "COMPLETED",
      "readCount": 1000000,
      "writeCount": 1000000
    },
    {
      "stepName": "transformData",
      "status": "STARTED",
      "readCount": 450000,
      "writeCount": 450000,
      "skipCount": 12
    }
  ]
}

# Response (completed):
{
  "jobExecutionId": 12345,
  "jobName": "monthlyReport",
  "status": "COMPLETED",
  "startTime": "2024-01-15T10:30:00Z",
  "endTime": "2024-01-15T10:45:23Z",
  "duration": "00:15:23",
  "exitStatus": "COMPLETED",
  "steps": [
    {
      "stepName": "extractData",
      "status": "COMPLETED",
      "readCount": 1000000,
      "writeCount": 1000000,
      "duration": "00:05:12"
    },
    {
      "stepName": "transformData",
      "status": "COMPLETED",
      "readCount": 1000000,
      "writeCount": 998765,
      "skipCount": 1235,
      "duration": "00:07:45"
    },
    {
      "stepName": "exportReport",
      "status": "COMPLETED",
      "writeCount": 1,
      "duration": "00:02:26"
    }
  ]
}

# Response (failed):
{
  "jobExecutionId": 12346,
  "jobName": "monthlyReport",
  "status": "FAILED",
  "exitStatus": "FAILED",
  "failureExceptions": [
    "Database connection timeout at chunk 450"
  ]
}

# Restart failed job
curl -X POST http://localhost:8080/api/batch/jobs/executions/12346/restart

# Response:
{
  "jobExecutionId": 12347,
  "status": "STARTING",
  "message": "Job restarted from last successful chunk"
}

# Stop running job
curl -X POST http://localhost:8080/api/batch/jobs/executions/12345/stop

# List all jobs
curl http://localhost:8080/api/batch/jobs

# Response:
{
  "jobs": [
    {
      "jobName": "monthlyReport",
      "lastExecution": {
        "executionId": 12345,
        "status": "COMPLETED",
        "startTime": "2024-01-15T10:30:00Z",
        "duration": "00:15:23"
      }
    },
    {
      "jobName": "dataExport",
      "lastExecution": {
        "executionId": 12344,
        "status": "COMPLETED",
        "startTime": "2024-01-15T08:00:00Z",
        "duration": "01:23:45"
      }
    }
  ]
}

# Get job execution history
curl "http://localhost:8080/api/batch/jobs/monthlyReport/executions?page=0&size=20"

# View scheduled jobs
curl http://localhost:8080/api/batch/jobs/scheduled

# Response:
{
  "scheduledJobs": [
    {
      "jobName": "monthlyReport",
      "schedule": "0 0 1 * *",
      "description": "Monthly report - 1st of each month at midnight",
      "nextExecution": "2024-02-01T00:00:00Z"
    },
    {
      "jobName": "dailyBackup",
      "schedule": "0 2 * * *",
      "description": "Daily backup - every day at 2 AM",
      "nextExecution": "2024-01-16T02:00:00Z"
    }
  ]
}
```

