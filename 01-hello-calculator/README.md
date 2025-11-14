# Hello Calculator

## Description

The Hello Calculator is a command-line application that performs basic arithmetic operations on two numbers. This is your first Java program, focusing on the fundamentals: reading user input, performing calculations, and displaying results.

You'll learn the core syntax of Java—variables, operators, conditionals, and console I/O—while building something immediately useful. The program prompts you for two numbers and an operator, performs the calculation, and shows the result. It's a simple concept that teaches you how to create a project, compile and run Java programs, handle different data types, and make decisions based on user input.

This challenge introduces you to the development workflow: writing code, compiling it, running it, and seeing the results. By the end, you'll understand how Java programs are structured and how to interact with users through the console.

## Features

- **Basic arithmetic operations**: Addition, subtraction, multiplication, and division
- **Interactive prompts**: Clear input prompts for numbers and operators
- **Error handling**: Division by zero detection with helpful messages
- **Formatted output**: Displays the complete operation with the result

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/hello-calculator.jar Main
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
Enter the first number: 10
Enter the operator (+, -, *, /): *
Enter the second number: 5
10 * 5 = 50
```

