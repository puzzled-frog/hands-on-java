# Requirements: RESTful Note-Taking API

## Overview
Build a REST API for managing notes with full CRUD operations, validation, and proper HTTP semantics.

## Functional Requirements

### Note Management
- Create notes with title and content
- Retrieve a single note by ID
- Retrieve all notes
- Update existing notes (full update)
- Delete notes
- Track creation and last modified timestamps

### API Endpoints
- `POST /api/notes` - Create a new note
- `GET /api/notes` - Get all notes
- `GET /api/notes/{id}` - Get note by ID
- `PUT /api/notes/{id}` - Update note
- `DELETE /api/notes/{id}` - Delete note

### Request Validation
- Title is required and must be 1-200 characters
- Content is required and must be 1-10000 characters
- Return 400 Bad Request for validation failures
- Include validation error messages in response

### Response Handling
- Return 201 Created with location header when creating notes
- Return 200 OK for successful GET and PUT operations
- Return 204 No Content for successful DELETE operations
- Return 404 Not Found when note doesn't exist
- Return proper error response format with messages

### Data Transfer
- Use DTOs for request and response bodies
- Include all note fields in responses (id, title, content, createdAt, updatedAt)
- Return ISO 8601 formatted timestamps

## Non-Functional Requirements

### API Design
- Follow REST conventions for endpoints and methods
- Use appropriate HTTP status codes
- Include proper content-type headers (application/json)

### Data Validation
- Validate all input data before processing
- Provide clear, actionable error messages
- Prevent SQL injection and XSS attacks

### Code Organization
- Separate controllers, services, and data access layers
- Use dependency injection appropriately
- Follow Spring Boot best practices

