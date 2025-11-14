# Requirements: Book Catalog API with Database

## Overview
Build a book catalog REST API with PostgreSQL database, supporting advanced search, filtering, pagination, and sorting.

## Functional Requirements

### Book Management
- Create books with title, author, ISBN, genre, publication year, and description
- Retrieve books with full details
- Update book information
- Delete books
- Validate ISBN format (10 or 13 digits)
- Prevent duplicate ISBNs

### Search and Filtering
- Search books by title (partial, case-insensitive match)
- Filter by author (exact match)
- Filter by genre
- Filter by publication year (exact, range, or before/after)
- Support combining multiple filters
- Return empty list when no matches found

### Pagination
- Support page-based pagination with configurable page size
- Default page size: 20 books
- Maximum page size: 100 books
- Return pagination metadata: current page, total pages, total items
- Handle out-of-bounds page numbers gracefully

### Sorting
- Sort by title (ascending/descending)
- Sort by author (ascending/descending)
- Sort by publication year (ascending/descending)
- Default sort: title ascending
- Support combining sort with filters and pagination

### API Endpoints
- `POST /api/books` - Create book
- `GET /api/books` - Get all books (with pagination, filtering, sorting)
- `GET /api/books/{id}` - Get book by ID
- `PUT /api/books/{id}` - Update book
- `DELETE /api/books/{id}` - Delete book
- `GET /api/books/search` - Advanced search with all filters

## Non-Functional Requirements

### Database Design
- Use appropriate data types and constraints
- Create indexes for frequently queried fields
- Ensure data integrity with foreign keys where applicable

### Performance
- Queries should be optimized for large datasets
- Use database-level pagination (not in-memory)
- Avoid N+1 query problems

### API Design
- Follow REST conventions
- Use query parameters for filters, pagination, and sorting
- Return appropriate HTTP status codes
- Include helpful error messages

