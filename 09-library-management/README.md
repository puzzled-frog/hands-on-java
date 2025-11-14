# Library Management System

## Description

The Library Management System handles book lending operations for a library. Manage books, register members, process checkouts and returns, track due dates, and calculate fines for late returns. Search across multiple criteria to find books quickly.

This challenge combines multiple object-oriented concepts into a cohesive system with complex relationships. Books have borrowing states, members have borrowing limits, and loans connect them with due dates. You'll use enums for fixed sets of values, Optional to handle absence safely, and custom exceptions for domain-specific errors.

The system enforces business rules: members can't borrow when they have overdue books, books can only be checked out if available, and fines accumulate for late returns. This teaches you how to model real-world constraints in code and maintain data consistency across operations.

## Features

- **Book catalog**: Track title, author, ISBN, genre, and availability
- **Member management**: Register and manage library members
- **Checkout operations**: Lend books with automatic due date calculation
- **Return processing**: Handle returns and calculate late fees
- **Fine system**: $0.50 per day for overdue books
- **Borrowing limits**: Max 5 books per member
- **Search functionality**: Find books by multiple criteria
- **Overdue tracking**: View all overdue books and responsible members

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/library-management.jar Main
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
=== Library Management System ===
1. Add book
2. Register member
3. Checkout book
4. Return book
5. Search books
6. View overdue books
7. Process fine payment
8. Exit

Choose an option: 3
Member ID: M001
Book ISBN: 978-0134685991
Book checked out successfully!
Due date: 2024-02-01

Choose an option: 4
Book ISBN: 978-0134685991
Days overdue: 3
Fine: $1.50
Book returned successfully!

Choose an option: 6
Overdue Books:
- "Clean Code" by Robert Martin
  Borrowed by: John Doe (M002)
  Days overdue: 7
  Fine: $3.50
```

