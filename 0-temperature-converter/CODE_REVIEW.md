# Code Review: Temperature tempconverter.Converter

## What You're Doing Well ✓

1. **Using enums** - Your `tempconverter.Scale` enum is a great choice
2. **Separation of concerns** - You separated I/O, conversion logic, and data types
3. **Input validation** - Using try-catch to handle invalid inputs
4. **Modern Java features** - Switch expressions and text blocks are nice touches

## Key Improvements to Consider

### 1. **Avoid Static Fields for Application State**

Your biggest issue is using static fields to store application state:

```java
static String menuChoice;
static tempconverter.Scale source;
static tempconverter.Scale target;
static double result;
```

**Problem**: These should be local variables. Static fields belong to the class, not instances, and make your code harder to test and reason about.

**Better approach**: Pass data as method parameters or local variables:

```java
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    printMenu();
    
    tempconverter.ConversionChoice choice = readConversionChoice(scanner);
    double temperature = readTemperature(scanner, choice.source());
    double result = tempconverter.Converter.convert(temperature, choice.source(), choice.target());
    
    System.out.printf("Temperature in %s: %.2f%n", 
                      choice.target().toString().toLowerCase(), result);
}

record tempconverter.ConversionChoice(tempconverter.Scale source, tempconverter.Scale target) {}
```

### 2. **Close Resources Properly**

Your `Scanner` is never closed, which can leak resources.

**Use try-with-resources**:

```java
public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
        // ... your code here
    }
}
```

### 3. **Add Package Declarations**

All Java classes should be in packages to avoid naming conflicts:

```java
package com.example.tempconverter;

public class ConsoleIO {
    // ...
}
```

### 4. **Reduce Switch Statement Repetition**

Your switch statement has a lot of repetition. Consider using a map or simplifying:

```java
private static tempconverter.ConversionChoice readConversionChoice(Scanner scanner) {
    Map<String, tempconverter.ConversionChoice> conversions = Map.of(
        "1", new tempconverter.ConversionChoice(tempconverter.Scale.CELSIUS, tempconverter.Scale.FAHRENHEIT),
        "2", new tempconverter.ConversionChoice(tempconverter.Scale.FAHRENHEIT, tempconverter.Scale.CELSIUS),
        "3", new tempconverter.ConversionChoice(tempconverter.Scale.CELSIUS, tempconverter.Scale.KELVIN),
        "4", new tempconverter.ConversionChoice(tempconverter.Scale.KELVIN, tempconverter.Scale.CELSIUS),
        "5", new tempconverter.ConversionChoice(tempconverter.Scale.FAHRENHEIT, tempconverter.Scale.KELVIN),
        "6", new tempconverter.ConversionChoice(tempconverter.Scale.KELVIN, tempconverter.Scale.FAHRENHEIT)
    );
    
    while (true) {
        String choice = readChoice(scanner);
        if (choice.equals("exit")) {
            System.exit(0);
        }
        
        tempconverter.ConversionChoice conversion = conversions.get(choice);
        if (conversion != null) {
            return conversion;
        }
        System.out.println("Invalid choice");
    }
}
```

### 5. **Avoid System.exit() in Library Code**

Calling `System.exit()` in multiple places makes your code hard to test and reuse. Instead, let methods return normally or throw exceptions.

```java
// Instead of System.exit(0), consider:
if (input.equalsIgnoreCase("exit")) {
    throw new UserExitException();
}

// Or return an Optional<Double>
```

### 6. **Add Display Names to Your Enum**

```java
public enum tempconverter.Scale {
    CELSIUS("celsius", "°C"),
    FAHRENHEIT("fahrenheit", "°F"),
    KELVIN("kelvin", "K");
    
    private final String displayName;
    private final String symbol;
    
    tempconverter.Scale(String displayName, String symbol) {
        this.displayName = displayName;
        this.symbol = symbol;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getSymbol() {
        return symbol;
    }
}

// Now you can use:
System.out.printf("Temperature in %s: %.2f %s%n", 
                  scale.getDisplayName(), temp, scale.getSymbol());
```

### 7. **Add JavaDoc Comments**

Document your public methods:

```java
/**
 * Converts a temperature value from one scale to another.
 * 
 * @param value the temperature value to convert
 * @param source the source temperature scale
 * @param target the target temperature scale
 * @return the converted temperature value
 */
public static double convert(double value, tempconverter.Scale source, tempconverter.Scale target) {
    // ...
}
```

### 8. **Consider Integer Division**

In your converter, be careful with integer division:

```java
case FAHRENHEIT -> (value - 32) * 5 / 9;
```

This works fine because `5 / 9` happens after multiplication by the double value, but it's clearer to write:

```java
case FAHRENHEIT -> (value - 32) * 5.0 / 9.0;
```

### 9. **Create Unit Tests**

Add tests to verify your conversions are correct:

```java
@Test
void testCelsiusToFahrenheit() {
    assertEquals(32.0, tempconverter.Converter.convert(0, tempconverter.Scale.CELSIUS, tempconverter.Scale.FAHRENHEIT));
    assertEquals(212.0, tempconverter.Converter.convert(100, tempconverter.Scale.CELSIUS, tempconverter.Scale.FAHRENHEIT));
}
```

### 10. **Validate Temperature Input**

Consider validating that temperatures are physically possible (e.g., nothing below absolute zero):

```java
if (source == tempconverter.Scale.KELVIN && value < 0) {
    System.out.println("Kelvin cannot be negative!");
    continue;
}
```

## Overall Structure Suggestion

For better organization, consider this structure:

```
src/main/java/com/example/tempconverter/
  ├── Main.java              // Entry point
  ├── ConsoleUI.java         // User interface only
  ├── TemperatureConverter.java  // Conversion logic
  └── tempconverter.Scale.java             // Enum with display names
```

---

Your code shows good fundamentals! Keep practicing and gradually incorporate these patterns as you learn more Java.

