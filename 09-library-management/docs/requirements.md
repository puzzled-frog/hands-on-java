# Requirements: Library Management System

## Overview
Build a system to manage library books, members, and lending operations with due date tracking and fine calculations.

## Functional Requirements

### Book Management
- Add books with title, author, ISBN, genre, and total copies
- Update book information
- Remove books from the system
- Track available vs. borrowed copies
- Search books by title, author, genre, or ISBN
- Display book details including borrowing status

### Member Management
- Register new members with name, member ID, and contact information
- Update member information
- View member details including borrowing history
- Deactivate member accounts
- Track books currently borrowed by each member

### Lending Operations
- Check out books to members
- Enforce maximum books per member limit (5 books)
- Set due dates (14 days from checkout)
- Prevent checkout if member has overdue books or unpaid fines
- Record checkout date and due date for each loan

### Return Operations
- Process book returns from members
- Calculate days overdue for late returns
- Calculate fines for late returns ($0.50 per day)
- Update member's fine balance
- Make returned books available for checkout

### Fine Management
- Track accumulated fines for each member
- Allow fine payments (full or partial)
- Display outstanding fine balance
- Block checkouts for members with fines over $10.00

### Search and Reporting
- Search books by multiple criteria with partial matches
- View all overdue books with member information
- Generate member borrowing history
- List members with outstanding fines

## Non-Functional Requirements

### Data Integrity
- Book availability must be accurately tracked
- Fine calculations must be precise
- Data must persist across application restarts

### Business Rules
- Due dates and fine calculations must be consistent
- System must prevent invalid operations (e.g., returning non-borrowed books)

