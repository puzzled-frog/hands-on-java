# Todo List Manager

## Description

The Todo List Manager is a console application for managing your personal todo list. You can add tasks, view them with their status, mark them as completed, and delete them. All tasks are stored in memory while the application runs.

This challenge introduces you to object-oriented programming and working with collections. You'll learn how to use ArrayList to store multiple items, how to model real-world concepts as objects, and how to organize your code into logical units. The application demonstrates basic CRUD (Create, Read, Update, Delete) operations that form the foundation of most software applications.

You'll practice encapsulation by keeping your task data organized, and you'll build a menu-driven interface that lets users perform different operations. This is your first step into building applications that manage data structures more complex than simple variables.

## Features

- **Add tasks**: Create new todos with descriptions
- **View all tasks**: Display the complete list with status indicators
- **Complete tasks**: Mark tasks as done
- **Delete tasks**: Remove tasks by their number or ID
- **Menu-driven interface**: Clear options for all operations
- **In-memory storage**: Tasks persist during the session

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/todo-list-manager.jar Main
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
=== Todo List Manager ===
1. Add task
2. View all tasks
3. Complete task
4. Delete task
5. Exit

Choose an option: 1
Enter task description: Buy groceries
Task added successfully!

Choose an option: 2
Your tasks:
1. [ ] Buy groceries

Choose an option: 3
Enter task number to complete: 1
Task marked as complete!

Choose an option: 2
Your tasks:
1. [X] Buy groceries
```

