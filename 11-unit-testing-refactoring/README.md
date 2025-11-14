# Unit Testing & Refactoring Challenge

## Description

This challenge provides you with an existing e-commerce shopping cart system that has no tests and poor design. Your task is to add comprehensive unit tests, refactor the code for better quality, and improve its testability—all while maintaining the existing functionality.

You'll learn test-driven development principles, how to use JUnit effectively, and when to use mocking to isolate units under test. The challenge teaches you to identify code smells, apply refactoring techniques safely, and write tests that give you confidence in your changes. You'll practice the discipline of making code better without changing what it does.

This is your introduction to professional software practices: testing, refactoring, and continuous improvement. You'll see how tests enable fearless refactoring and how better design makes code easier to test, creating a virtuous cycle of quality improvement.

## Features

- **Comprehensive test suite**: Unit tests for all business logic components
- **High code coverage**: Target 80%+ coverage with meaningful tests
- **Mocking strategy**: Isolate units from external dependencies
- **Refactored codebase**: Improved naming, structure, and design
- **SOLID principles applied**: Better separation of concerns
- **Documentation**: Explain complex test scenarios and refactoring decisions

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/unit-testing-refactoring.jar Main
```

## How to Test

Run all tests:

```bash
./gradlew test
```

View test report with coverage:

```bash
./gradlew test jacocoTestReport
open build/reports/jacoco/test/html/index.html
```

## Usage Example

Before refactoring:
```java
public class Cart {
    public static double total;
    public static ArrayList items = new ArrayList();
    
    public void add(String i, double p) {
        items.add(i);
        total = total + p;
    }
}
```

After refactoring:
```java
public class ShoppingCart {
    private final List<CartItem> items;
    private final PriceCalculator calculator;
    
    public void addItem(CartItem item) {
        validateItem(item);
        items.add(item);
    }
    
    public Money calculateTotal() {
        return calculator.calculate(items);
    }
}
```

With comprehensive tests:
```java
@Test
void shouldCalculateTotalWithMultipleItems() {
    cart.addItem(new CartItem("Book", Money.of(10.00)));
    cart.addItem(new CartItem("Pen", Money.of(2.50)));
    
    assertThat(cart.calculateTotal())
        .isEqualTo(Money.of(12.50));
}
```

