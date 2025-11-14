# Contact Book

## Description

The Contact Book is a contact management system that stores people's information persistently in a file. You can add, search, update, and delete contacts, and all changes are automatically saved so your data survives between program runs.

This challenge builds on file I/O by introducing persistent storage—your data doesn't disappear when the program closes. You'll learn how to save structured data to files, load it back when the program starts, and keep the file in sync with changes. The application uses Maps for efficient lookups, allowing quick searches by name.

You'll also practice data validation, ensuring contacts have proper email formats and phone numbers. This is your first experience with data persistence and the considerations that come with it: file corruption, concurrent access, and maintaining data integrity.

## Features

- **Persistent storage**: Contacts saved to file automatically after changes
- **CRUD operations**: Create, read, update, and delete contacts
- **Search functionality**: Find contacts by name with partial matching
- **Data validation**: Email and phone number format checking
- **Automatic loading**: Previous contacts loaded on startup
- **Duplicate prevention**: Prevent contacts with the same name

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/contact-book.jar Main
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
=== Contact Book ===
1. Add contact
2. View all contacts
3. Search contacts
4. Update contact
5. Delete contact
6. Exit

Choose an option: 1
Name: John Doe
Phone: (555) 123-4567
Email: john@example.com
Contact added successfully!

Choose an option: 3
Search by name: John
Found 1 contact(s):
- John Doe | (555) 123-4567 | john@example.com
```

