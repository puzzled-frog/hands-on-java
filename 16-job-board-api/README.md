# Job Board API

## Description

The Job Board API is a platform where companies post jobs and users apply. Implement role-based access control where companies can create and manage job postings, job seekers can browse and apply, and the system enforces authorization rules to protect data. Track application status through the hiring pipeline.

This challenge introduces you to Spring Security, authentication, and authorization. You'll learn how to secure API endpoints, manage user roles (job seeker, company, admin), and enforce access rules so users can only see and modify their own data. The platform demonstrates how to build multi-tenant systems where different user types have different capabilities.

You'll practice designing APIs with security in mind, implementing authentication flows, and using Spring Security's powerful authorization framework. This is essential knowledge for building any application that handles user accounts and sensitive data.

## Features

- **User management**: Registration with role selection (job seeker or company)
- **Authentication**: Login with JWT tokens
- **Company profiles**: Companies manage their profile and jobs
- **Job posting**: Create, update, and delete job listings
- **Public job browsing**: Anyone can view open positions
- **Application system**: Job seekers apply with cover letters
- **Application tracking**: View application status and history
- **Role-based access**: Enforce permissions per user type
- **Status management**: Companies update application status

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/job-board-api.jar
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
# Register as a company
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "hr@company.com",
    "password": "SecurePass123!",
    "userType": "COMPANY"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "hr@company.com", "password": "SecurePass123!"}'

# Post a job (company only)
curl -X POST http://localhost:8080/api/jobs \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Senior Java Developer",
    "description": "Join our team...",
    "location": "Remote",
    "jobType": "FULL_TIME",
    "salaryMin": 100000,
    "salaryMax": 150000
  }'

# Browse jobs (public)
curl "http://localhost:8080/api/jobs?location=Remote&jobType=FULL_TIME"

# Apply to job (job seeker only)
curl -X POST http://localhost:8080/api/jobs/1/apply \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"coverLetter": "I am excited to apply...", "resumeUrl": "https://..."}'

# View applications for my job (company only)
curl http://localhost:8080/api/jobs/1/applications \
  -H "Authorization: Bearer <token>"
```

