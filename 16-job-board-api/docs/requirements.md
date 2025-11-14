# Requirements: Job Board API

## Overview
Create a job posting platform with role-based access control where companies can post jobs and users can apply.

## Functional Requirements

### User Management
- Register users with email, password, and user type (job seeker or company)
- Login with email and password
- Update user profile information
- Support user roles: JOB_SEEKER, COMPANY, ADMIN

### Company Profiles
- Companies can create company profile with name, description, website, location
- Update company information
- View company profile with all posted jobs
- Only authenticated companies can manage their profile

### Job Posting
- Companies can create job posts with: title, description, requirements, salary range, location, job type (full-time/part-time/contract)
- Update own job posts
- Delete own job posts
- Mark jobs as open or closed
- View all jobs posted by the company

### Job Search and Discovery
- Public access to view all open jobs (no authentication required)
- Search jobs by title or description
- Filter by location, job type, salary range
- Sort by posted date or salary
- View job details including company information

### Job Applications
- Job seekers can apply to jobs with cover letter and resume URL
- Track application status (pending, reviewed, rejected, accepted)
- View application history for job seeker
- Prevent duplicate applications to same job
- Only authenticated job seekers can apply

### Application Management
- Companies can view all applications for their jobs
- Companies can update application status
- Companies can filter applications by status
- Job seekers can view status of their applications

### Authorization Rules
- Public users can browse and search jobs
- Only companies can create, update, and delete jobs
- Only companies can view and manage applications for their jobs
- Only job seekers can submit applications
- Users can only modify their own data
- Admins can view all data

## Non-Functional Requirements

### Security
- Passwords must be encrypted
- API endpoints must enforce authorization rules
- Users can only access their own data (except admins)

### Data Integrity
- Applications reference valid jobs and users
- Deleted jobs should retain application history
- Company deletion should handle or prevent if jobs exist

