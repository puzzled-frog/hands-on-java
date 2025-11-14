# Book Catalog API with Database

## Description

The Book Catalog API extends REST API development by adding a PostgreSQL database for persistent storage. Implement advanced search with multiple filters, pagination for handling large datasets, and sorting options to organize results. This API demonstrates how to build production-ready endpoints that scale.

This challenge introduces you to Spring Data JPA and relational database design. You'll learn how to map Java objects to database tables, write efficient queries, and handle relationships between entities. The application teaches you pagination strategies that keep response times fast even with millions of records, and how to combine multiple filters to create powerful search capabilities.

You'll practice designing database schemas, creating indexes for performance, and using JPA repositories to abstract database operations. This is your foundation for building data-driven applications that require reliable, persistent storage.

## Features

- **Complete book management**: Add, update, delete, and retrieve books
- **Advanced search**: Filter by title, author, genre, and publication year
- **Pagination**: Handle large datasets efficiently with configurable page sizes
- **Multiple sort options**: Sort by title, author, or year (ascending/descending)
- **ISBN validation**: Ensure proper ISBN-10 or ISBN-13 format
- **Duplicate prevention**: No duplicate ISBNs in the catalog
- **Database optimization**: Indexes on frequently queried fields

## How to Run

Ensure PostgreSQL is running and create a database:
```sql
CREATE DATABASE book_catalog;
```

Using Gradle wrapper:

```bash
./gradlew bootRun
```

Or build and run the JAR:

```bash
./gradlew build
java -jar build/libs/book-catalog-api.jar
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
# Create a book
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Clean Code",
    "author": "Robert Martin",
    "isbn": "978-0132350884",
    "genre": "Programming",
    "publicationYear": 2008,
    "description": "A handbook of agile software craftsmanship"
  }'

# Search with filters and pagination
curl "http://localhost:8080/api/books/search?author=Martin&genre=Programming&page=0&size=20&sort=title,asc"

# Response:
{
  "content": [...],
  "page": 0,
  "size": 20,
  "totalElements": 45,
  "totalPages": 3
}

# Filter by publication year range
curl "http://localhost:8080/api/books/search?yearFrom=2000&yearTo=2010&sort=publicationYear,desc"
```

