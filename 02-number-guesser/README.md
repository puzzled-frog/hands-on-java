# Number Guesser

## Description

The Number Guesser is a guessing game where the program picks a random number between 1 and 100, and you have a limited number of attempts to guess it. After each guess, the program provides hints to guide you toward the answer.

This challenge introduces you to loops, random number generation, and controlling program flow based on conditions. You'll learn how to keep a program running until a specific condition is met, how to track state across iterations, and how to give users multiple chances to succeed.

The game demonstrates fundamental programming concepts like iteration and decision-making while creating an engaging, interactive experience. You'll also practice input validation to handle cases where users don't enter valid numbers.

## Features

- **Random number generation**: Each game picks a new random target
- **Limited attempts**: 7 attempts to find the number
- **Helpful hints**: "Too high" or "Too low" feedback after each guess
- **Remaining attempts tracking**: Shows how many tries are left
- **Input validation**: Handles non-numeric input gracefully
- **Win/loss conditions**: Clear messages for both outcomes

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/number-guesser.jar Main
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
Guess a number between 1 and 100!
You have 7 attempts.

Enter your guess: 50
Too high! You have 6 attempts left.

Enter your guess: 25
Too low! You have 5 attempts left.

Enter your guess: 37
Correct! You guessed it in 3 attempts!
```

