# Design Notes: Hello Calculator

## Reading User Input

When accepting user input in Java, the `Scanner` class provides a straightforward API. You may be tempted to use `nextLine()` for all inputs, but notice that `nextDouble()` and `next()` are more convenient for reading specific types—they handle parsing automatically and skip whitespace.

One potential pitfall is mixing `nextLine()` with other `next*()` methods. The `nextLine()` method consumes the entire line including the newline character, while methods like `nextDouble()` leave the newline in the buffer. This can lead to unexpected behavior if you call `nextLine()` after `nextDouble()`. For this simple calculator, sticking with `nextDouble()` and `next()` avoids this issue entirely.

## String Comparison: equals() vs ==

When comparing strings in Java, you'll need to use `.equals()` rather than `==`. This is because `==` compares object references (memory addresses), while `.equals()` compares the actual content of the strings.

```java
if (operator == "+")        // Incorrect: compares memory addresses
if (operator.equals("+"))   // Correct: compares string content
```

You may wonder why this works differently from primitive types like `int` or `double`, where `==` works as expected. The distinction lies in how Java handles objects versus primitives—strings are objects, so reference comparison and value comparison are separate concerns.

## Handling Different Operations: If-Else vs Switch

There are several ways to dispatch to different operations based on the operator:

**If-else chains** are straightforward and require no special syntax beyond what you already know:
```java
if (operator.equals("+")) {
    result = num1 + num2;
} else if (operator.equals("-")) {
    result = num1 - num2;
}
```

**Traditional switch statements** can be more readable when you have multiple discrete cases:
```java
switch (operator) {
    case "+":
        result = num1 + num2;
        break;
    case "-":
        result = num1 - num2;
        break;
}
```

**Switch expressions** (Java 14+) eliminate the need for break statements and can assign directly to a variable:
```java
double result = switch (operator) {
    case "+" -> num1 + num2;
    case "-" -> num1 - num2;
};
```

Consider the trade-offs: if-else chains are most flexible and work in all versions of Java; traditional switches are clearer for many cases but require `break` statements; switch expressions are most concise but require Java 14 or later. For a first project, if-else chains minimize the number of new concepts to learn.

## Resource Management

The `Scanner` class uses system resources that should be released when you're done. You may be tempted to simply call `scanner.close()` at the end of your program and assume that's sufficient.

One potential pitfall is forgetting to close the scanner when errors occur. If your program returns early due to invalid input, any cleanup code at the end won't execute:

```java
Scanner scanner = new Scanner(System.in);
// ... read input ...
if (invalidInput) {
    return;  // scanner never gets closed!
}
scanner.close();
```

An alternative is the **try-with-resources** pattern, which automatically closes resources even when exceptions occur or early returns happen:

```java
try (Scanner scanner = new Scanner(System.in)) {
    // ... use scanner ...
    // Automatically closed when this block exits
}
```

Consider weighing these approaches: explicit `close()` calls are simpler to understand initially, while try-with-resources prevents resource leaks but introduces additional syntax. For a first program, explicit closing is acceptable. As programs grow more complex with multiple error paths, automatic resource management becomes increasingly valuable.

## Input Validation Strategies

When accepting user input, you'll face decisions about how thoroughly to validate it. You may be tempted to handle every possible edge case upfront, but this can complicate your first programs significantly.

**Minimal validation** means doing little or no checking:
```java
double result = 0;
if (operator.equals("+")) {
    result = num1 + num2;
}
// Invalid operator → result stays 0
// Division by zero → throws ArithmeticException
```

This approach lets you focus on core functionality first. Unexpected inputs produce unexpected results, which is often a valuable learning experience.

**Explicit validation** checks inputs before processing:
```java
if (!operator.matches("[+\\-*/]")) {
    System.out.println("Error: Invalid operator");
    return;
}
if (operator.equals("/") && num2 == 0) {
    System.out.println("Error: Cannot divide by zero");
    return;
}
```

Consider the trade-offs: minimal validation keeps code simple and lets you experience failure modes firsthand; explicit validation provides better user experience but adds complexity. One potential pitfall with explicit validation is validating some cases but forgetting others, creating inconsistent behavior.

## The Early Return Pattern

When validating inputs, you might write error handling like this:

```java
if (validInput) {
    // ... process normally ...
} else {
    System.out.println("Error");
}
```

An alternative is the **early return pattern**, which checks for errors first and exits immediately:

```java
if (invalidInput) {
    System.out.println("Error");
    return;
}
// ... rest of code assumes valid input ...
```

This pattern has several benefits: it reduces nesting, makes the "happy path" more prominent, and ensures subsequent code can assume inputs are valid. You may find this pattern particularly useful as your programs grow and accumulate multiple validation checks.

## Representing Invalid Results

When validation fails or errors occur, you'll need to decide how to represent the failure. You may be tempted to use a "magic value" like `0` or `-1` to indicate errors:

```java
double result = switch (operator) {
    case "+" -> num1 + num2;
    default -> 0;  // Error case
};
```

One potential pitfall is that magic values can be legitimate results. Zero is a perfectly valid answer to "5 - 5", so using it to signal errors creates ambiguity.

Alternative approaches include:
- **Print error and exit**: Display a message and call `return` or `System.exit()`
- **Throw exceptions**: Use `throw new IllegalStateException()` for cases that shouldn't happen
- **Optional types**: Return `Optional<Double>` to explicitly represent "value or nothing"

Consider the trade-offs: printing and exiting is simple but gives callers no chance to handle errors; exceptions work well for exceptional conditions but may be overkill for expected user errors; Optional types are explicit but add complexity. For defensive programming in cases that "should never happen" (like a default case after validation), throwing an exception documents the assumption clearly.

## Defensive Programming

After validating inputs, you might wonder whether to include error handling in code that should never execute. Consider this switch expression:

```java
// Already validated operator is one of: +, -, *, /
double result = switch (operator) {
    case "+" -> num1 + num2;
    case "-" -> num1 - num2;
    case "*" -> num1 * num2;
    case "/" -> num1 / num2;
    default -> ???  // Should never reach here
};
```

You may be tempted to either omit the `default` case (causing compilation errors) or return a magic value like `0`. An alternative is **defensive programming**: explicitly handling the "impossible" case by throwing an exception:

```java
default -> throw new IllegalStateException("Unexpected operator: " + operator);
```

This approach has several benefits: it satisfies the compiler's requirement for exhaustive cases, documents that this case shouldn't occur, and provides clear failure if your assumptions are violated (perhaps due to future code changes). One potential pitfall is over-engineering early code, but in this case the defensive check is simple and the documentation value is significant.

## Validation Pattern: Regex vs Explicit Checks

When validating that an operator is one of several allowed values, you have multiple options:

```java
// Chaining comparisons
if (!operator.equals("+") && !operator.equals("-") && 
    !operator.equals("*") && !operator.equals("/"))

// Regex pattern
if (!operator.matches("[+\\-*/]"))

// Set membership (more advanced)
if (!Set.of("+", "-", "*", "/").contains(operator))
```

Consider the trade-offs: explicit comparisons are most straightforward to understand; regex patterns are concise but introduce new syntax (and require escaping special characters like `-` and `*`); set membership is very readable but introduces collections. You may find regex particularly valuable once you become comfortable with pattern syntax, as it scales well to more complex validation rules.

## Formatting Output

When displaying results, you might simply print the answer:
```java
System.out.println(result);  // Outputs: 50.0
```

An alternative is to show the complete operation:
```java
System.out.println(num1 + " " + operator + " " + num2 + " = " + result);
// Outputs: 10.0 * 5.0 = 50.0
```

This "show your work" approach makes output more informative and helps with debugging. One potential pitfall is that string concatenation with `+` can become hard to read with many parts. For more complex formatting, consider `String.format()` or `printf()`, though simple concatenation is sufficient for this calculator.

## Learning Through Failure

Perhaps the most important design consideration for a first project is deciding what errors to prevent versus what errors to allow. You may be tempted to anticipate and handle every possible failure mode before running your code for the first time.

An alternative approach is to build a minimal version first, run it, and observe what breaks. Division by zero? Let it happen, see the exception, understand what it means, then add checking. Invalid operator? Watch the program return 0, recognize the problem, then add validation.

This iterative approach—build, run, break, fix—mirrors how experienced developers work and creates stronger understanding than trying to handle all cases upfront. The stretch version can then demonstrate how to handle these cases properly, providing contrast between minimal and robust implementations.

