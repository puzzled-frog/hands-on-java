# RESTful Note-Taking API

## Description

The Note-Taking API is your first Spring Boot application—a REST API for managing notes. Create, retrieve, update, and delete notes through HTTP endpoints, with proper validation, status codes, and error handling.

This challenge introduces you to Spring Boot, REST architecture, and web service development. You'll learn how Spring simplifies building APIs through annotations, dependency injection, and convention over configuration. The API demonstrates proper HTTP semantics: using the right methods (GET, POST, PUT, DELETE), returning appropriate status codes, and structuring responses consistently.

You'll practice request validation to ensure data quality, implement DTOs to separate external contracts from internal models, and design endpoints that follow REST conventions. This is your foundation for building web services that other applications can consume.

## Features

- **RESTful endpoints**: Full CRUD operations following REST conventions
- **Request validation**: Automatic validation with helpful error messages
- **Proper HTTP semantics**: Correct status codes and methods
- **DTO pattern**: Clean separation between API and domain models
- **Error handling**: Consistent error response format
- **Timestamps**: Track when notes are created and updated
- **OpenAPI documentation**: Auto-generated API docs

## How to Run

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/note-taking-api.jar
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
# Create a note
curl -X POST http://localhost:8080/api/notes \
  -H "Content-Type: application/json" \
  -d '{"title": "Meeting Notes", "content": "Discuss project timeline"}'

# Response: 201 Created
{
  "id": 1,
  "title": "Meeting Notes",
  "content": "Discuss project timeline",
  "createdAt": "2024-01-15T10:30:00Z",
  "updatedAt": "2024-01-15T10:30:00Z"
}

# Get all notes
curl http://localhost:8080/api/notes

# Get a specific note
curl http://localhost:8080/api/notes/1

# Update a note
curl -X PUT http://localhost:8080/api/notes/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Updated Notes", "content": "New content"}'

# Delete a note
curl -X DELETE http://localhost:8080/api/notes/1
```

