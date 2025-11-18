# Hello Calculator

## Description

The Hello Calculator is a command-line application that performs basic arithmetic operations on two numbers. This is your first Java program, focusing on the fundamentals: reading user input, performing calculations, and displaying results.

You'll learn the core syntax of Java—variables, operators, conditionals, and console I/O—while building something immediately useful. The program prompts you for two numbers and an operator, performs the calculation, and shows the result. It's a simple concept that teaches you how to create a project, compile and run Java programs, handle different data types, and make decisions based on user input.

This challenge introduces you to the development workflow: writing code, compiling it, running it, and seeing the results. By the end, you'll understand how basic Java programs are structured and how to interact with users through the console.

## Two Versions

Since this is your first Java program, there are **two versions** of the solution:

### Main.java - The Basic Version

Focuses on **essential Java concepts only**:
- **Scanner basics**: Reading input with `Scanner.nextDouble()` and `Scanner.next()`
- **Variables**: Declaring and using `double` and `String` variables
- **Primitive operations**: Basic arithmetic (`+`, `-`, `*`, `/`)
- **If-else chains**: Simple conditional logic using `if/else if/else`
- **String comparison**: Using `.equals()` to compare strings
- **Console output**: Printing results with `System.out.println()`
- **Resource cleanup**: Explicit `scanner.close()` call

**What's intentionally left out:**
- No input validation (invalid operators simply result in 0)
- No division by zero checking (let it crash and see what happens)
- No advanced control flow or error handling
- No try-with-resources or modern Java syntax

This version is meant to get you writing code quickly. Encountering edge cases and unexpected behavior is **part of the learning process**.

### MainStretch.java - The Enhanced Version

Builds on the basics and adds **best practices and modern Java features**:
- **Input validation**: Checks for invalid operators using regex pattern matching
- **Error handling**: Graceful handling of division by zero with clear error messages
- **Try-with-resources**: Automatic resource management with `try (Scanner scanner = ...)`
- **Switch expressions**: Modern operator dispatch using switch expressions (Java 14+)
- **Early returns**: Clean error handling without nested conditions
- **Better output**: Shows the complete operation (e.g., "10.0 * 5.0 = 50.0")
- **Defensive programming**: Throws `IllegalStateException` for impossible cases

**New concepts demonstrated:**
- Resource management patterns
- Input validation with regex (`.matches()`)
- Early return pattern for error handling
- Modern Java syntax improvements
- Defensive programming techniques

### Which One to Start With?

Start with `Main.java` to understand the fundamentals. The requirements are intentionally simple—the program doesn't need to handle edge cases perfectly. If you enter an invalid operator or divide by zero, seeing what happens is part of the learning experience.

Once you're comfortable with the basics, and if you want a stretch, explore `MainStretch.java` to see how validation, error handling, and modern Java features improve the code. This introduces patterns you'll use throughout the rest of the challenges.

## Features

- **Basic arithmetic operations**: Addition, subtraction, multiplication, and division
- **Interactive prompts**: Clear input prompts for numbers and operators

## How to Run

**Basic version:**

```bash
cd 01-hello-calculator
javac src/main/java/Main.java
java -cp src/main/java Main
```

**Stretch version:**

```bash
cd 01-hello-calculator
javac src/main/java/MainStretch.java
java -cp src/main/java MainStretch
```

**Using Gradle (optional):**

```bash
./gradlew run
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

## Usage Examples

**Basic version (Main.java):**

```
Enter first number: 10
Enter operator (+, -, *, /): *
Enter second number: 5
Result: 50.0
```

**Stretch version (MainStretch.java):**

```
Enter first number: 10
Enter operator (+, -, *, /): *
Enter second number: 5
10.0 * 5.0 = 50.0
```

**Error handling in stretch version:**

Division by zero:
```
Enter first number: 10
Enter operator (+, -, *, /): /
Enter second number: 0
Error: Cannot divide by zero
```

Invalid operator:
```
Enter first number: 10
Enter operator (+, -, *, /): %
Enter second number: 5
Error: Invalid operator '%'. Please use +, -, *, or /
```

