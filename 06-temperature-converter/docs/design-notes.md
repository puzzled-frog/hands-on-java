# Design Notes: Temperature Converter Challenge

This document explores key design decisions and patterns that emerge when building a temperature converter. These aren't "the only way" to solve the problem, but rather important considerations that will help you think more deeply about your code.

## 1. When Control Flow Becomes Data

### The Pattern You Might Write First

When building the menu system, it's natural to reach for control flow structures. You might write something like:

```java
switch (menuChoice) {
    case "1" -> {
        source = Scale.CELSIUS;
        target = Scale.FAHRENHEIT;
    }
    case "2" -> {
        source = Scale.FAHRENHEIT;
        target = Scale.CELSIUS;
    }
    case "3" -> {
        source = Scale.CELSIUS;
        target = Scale.KELVIN;
    }
    // ... and so on
}
```

This works! But notice the repetition—each case follows the exact same pattern, just with different values.

### The Alternative: Representing Logic as Data

Consider this approach instead:

```java
Map<String, ConversionChoice> conversions = Map.of(
    "1", new ConversionChoice(Scale.CELSIUS, Scale.FAHRENHEIT),
    "2", new ConversionChoice(Scale.FAHRENHEIT, Scale.CELSIUS),
    "3", new ConversionChoice(Scale.CELSIUS, Scale.KELVIN),
    "4", new ConversionChoice(Scale.KELVIN, Scale.CELSIUS),
    "5", new ConversionChoice(Scale.FAHRENHEIT, Scale.KELVIN),
    "6", new ConversionChoice(Scale.KELVIN, Scale.FAHRENHEIT)
);

ConversionChoice choice = conversions.get(menuChoice);
```

### What Changes When Logic Becomes Data

Consider what becomes possible when you represent choices as data rather than control flow:

1. **Adding new options is trivial** - Just add a new entry to the map, no new code paths
2. **The data could come from anywhere** - A configuration file, database, or user customization (try doing that with a switch statement!)
3. **Validation becomes automatic** - If the key isn't in the map, it's invalid. No need to check ranges or maintain a separate validation list
4. **You can query the structure** - How many conversions do we support? `conversions.size()`. Which conversions start from Celsius? Filter the values.

### When to Consider This Approach

You may be tempted to keep using control flow structures when you find yourself writing nearly-identical code blocks that only differ in their values. An alternative design is to ask whether that structure could be represented as data instead.

This pattern appears everywhere in real-world software:
- HTTP routing tables (URL → handler function)
- Configuration systems (key → value)
- Command processors (command name → action)
- State machines (state + event → next state)

## 2. The Static Field Trap

### A Common Beginner Pattern

It's tempting to use static fields to share data between methods:

```java
static String menuChoice;
static Scale source;
static Scale target;
static double temperature;

public static void main(String[] args) {
    readMenuChoice();
    readTemperature();
    performConversion();
}
```

This feels convenient—no parameters to pass around! But there's a hidden cost.

### One Potential Pitfall: Invisible Dependencies

**Static fields create invisible dependencies.** When you look at a method signature:

```java
static void performConversion()
```

You can't tell what data it needs. It might read from `source`, `target`, and `temperature`, but this isn't visible in the signature. Compare with:

```java
static double performConversion(double temp, Scale source, Scale target)
```

Now the dependencies are explicit and clear.

**Testing becomes difficult.** Static state is shared across all tests. If one test modifies `source` and doesn't clean up, the next test might fail mysteriously.

**The code becomes harder to reuse.** What if you later want to convert multiple temperatures in parallel? With static state, they'd all interfere with each other.

### When Static Makes Sense

Static isn't always bad. Consider using it for:

- **Constants**: `private static final double KELVIN_OFFSET = 273.15`
- **Stateless utility functions**: `Math.sqrt()`, `Integer.parseInt()`
- **Carefully controlled singletons**: Database connection pools, loggers

A useful question to ask: "Is this truly shared across the entire application, or is it just convenient?"

## 3. Resource Management and Cleanup

### The Scanner That Never Closes

When you create a Scanner, you're opening a system resource:

```java
Scanner scanner = new Scanner(System.in);
// ... use scanner ...
// ⚠️ Scanner never closed!
```

Even though `System.in` is special and doesn't really "leak" the way a file would, it's still poor practice.

### Try-With-Resources

Java provides automatic resource management:

```java
try (Scanner scanner = new Scanner(System.in)) {
    // ... use scanner ...
}  // Automatically closed here, even if an exception occurs
```

This pattern works with any `AutoCloseable` resource:
- Files: `BufferedReader`, `FileWriter`
- Database connections: `Connection`, `ResultSet`
- Network sockets: `Socket`, `ServerSocket`

### Why Resource Management Matters

In a simple CLI app like this, a leaked Scanner might not cause problems. One potential pitfall when moving to more complex applications is encountering:
- Files that can't be deleted because they're still open
- Database connections that exhaust the connection pool
- Memory leaks from unclosed streams

Developing the habit of proper resource management early helps avoid these issues.

## 4. How to Exit Gracefully

### The Nuclear Option

When the user types "exit", you might write:

```java
if (input.equals("exit")) {
    System.exit(0);  // Terminates the entire JVM
}
```

`System.exit()` is like the "emergency stop" button—it immediately terminates everything. This makes sense in a standalone application's main method, but causes problems elsewhere:

- **Can't be caught or handled** - Code that needs to clean up can't do so
- **Kills your test framework** - Tests just stop running
- **Violates separation of concerns** - A helper method shouldn't have the power to terminate the entire application

### Normal Control Flow Alternatives

Option 1: **Return a sentinel value**

```java
Optional<ConversionChoice> readConversionChoice(Scanner scanner) {
    if (input.equals("exit")) {
        return Optional.empty();  // Signals "no value" without killing the app
    }
    return Optional.of(choice);
}
```

Option 2: **Loop with a flag**

```java
boolean done = false;
while (!done) {
    // ... process input ...
    if (input.equals("exit")) {
        done = true;
    }
}
```

Option 3: **Return from main normally**

```java
public static void main(String[] args) {
    // ...
    if (userWantsToExit) {
        return;  // Just exit main, JVM will terminate naturally
    }
}
```

### Consider Weighing These Trade-offs

- `System.exit()` in `main()` when you need specific exit codes for scripts: Often acceptable
- `System.exit()` in helper methods or library code: Generally worth avoiding
- User choosing to quit: Consider using `Optional` or return values (it's not an error!)
- Unrecoverable errors: You might let exceptions propagate, or use `System.exit()` with a non-zero status

## 5. Making Enums Work Harder

### The Minimal Enum

You might start with:

```java
enum Scale {
    CELSIUS, FAHRENHEIT, KELVIN
}
```

Then when displaying output:

```java
System.out.printf("Temperature in %s: ", source.toString().toLowerCase());
```

This works, but you're transforming the enum name every time you use it.

### Enums Can Carry Data

Enums aren't just named constants—they can have fields, methods, and constructors:

```java
public enum Scale {
    CELSIUS("celsius", "°C", -273.15),
    FAHRENHEIT("fahrenheit", "°F", -459.67),
    KELVIN("kelvin", "K", 0.0);
    
    private final String displayName;
    private final String symbol;
    private final double absoluteZero;
    
    Scale(String displayName, String symbol, double absoluteZero) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.absoluteZero = absoluteZero;
    }
    
    public String getDisplayName() { return displayName; }
    public String getSymbol() { return symbol; }
    public double getAbsoluteZero() { return absoluteZero; }
}
```

Now you can write:

```java
System.out.printf("Temperature in %s: ", source.getDisplayName());
System.out.printf("Result: %.2f%s%n", result, target.getSymbol());

if (temp < source.getAbsoluteZero()) {
    System.out.println("Temperature below absolute zero!");
}
```

### What This Approach Offers

- **No more string manipulation** - Display names are stored directly
- **Additional behavior** - Each scale knows its absolute zero
- **Type safety** - Symbols can't be mixed up or mistyped
- **Single source of truth** - All scale-related data lives in one place

This pattern is worth considering whenever an enum represents something with associated properties, not just a simple choice.

## 6. Recognizing Objects in Disguise

### Two Variables or One Object?

Look at these two variables:

```java
Scale source;
Scale target;
```

A key object-oriented insight: **When two pieces of data always travel together, they might actually be one thing.**

Consider these questions:
- Does a `source` make sense without a `target`?
- Does a `target` make sense without a `source`?
- Are they always passed to methods together?

If the answer is "they're always together," consider making them one object:

```java
record ConversionChoice(Scale source, Scale target) {}
```

### What Changes with This Approach

**Fewer parameters:**
```java
// Before: 4 parameters to pass source and target around
double convert(double temp, Scale source, Scale target)
void displayResult(double result, Scale source, Scale target)

// After: 3 parameters
double convert(double temp, ConversionChoice choice)
void displayResult(double result, ConversionChoice choice)
```

**Clearer intent:**
```java
// What are these two scales?
processTemperature(temp, Scale.CELSIUS, Scale.FAHRENHEIT);

// Ah, it's a conversion choice!
processTemperature(temp, new ConversionChoice(Scale.CELSIUS, Scale.FAHRENHEIT));
```

**Easier to extend:**
If you later want to add metadata about a conversion (e.g., "popular", "common in science"), you can add it to `ConversionChoice`. With separate variables, you'd need to pass even more parameters everywhere.

### Records for Simple Data

Java records are perfect for these "data carrier" objects:

```java
// One line gives you:
// - Constructor
// - Getters: choice.source(), choice.target()
// - equals(), hashCode(), toString()
// - Immutability
record ConversionChoice(Scale source, Scale target) {}
```

## 7. Validation: Catching Physical Impossibilities

### Beyond Format Validation

It's common to validate that user input is a number:

```java
try {
    double temp = Double.parseDouble(input);
} catch (NumberFormatException e) {
    System.out.println("Not a number!");
}
```

But consider: what if the user enters `-500` for Celsius? That's a valid number, but physically impossible!

### Domain Validation

Each temperature scale has an absolute zero—the coldest possible temperature:
- Celsius: -273.15°C
- Fahrenheit: -459.67°F
- Kelvin: 0 K

You can validate against these:

```java
if (inputValue < source.getAbsoluteZero()) {
    System.out.printf("Temperature cannot be below absolute zero: %.2f%s%n", 
                      source.getAbsoluteZero(), source.getSymbol());
    continue;  // Try again
}
```

### Beyond Type Checking

Validation isn't just about data types—it's about domain rules. Consider asking:
- What are the physical constraints?
- What are the business rules?
- What assumptions does my code make?

Catching invalid data early (at the input boundary) can prevent bugs deeper in your code.

## 8. The Guard Clause Pattern

### Nested Conditions

When validating input, you might write:

```java
if (inputValue >= source.getAbsoluteZero()) {
    return inputValue;  // Success path
} else {
    System.out.println("Error: too cold");
    // ... loop continues ...
}
```

### Flattening with Guard Clauses

An alternative structure:

```java
if (inputValue < source.getAbsoluteZero()) {
    System.out.println("Error: too cold");
    continue;  // Skip rest of iteration, try again
}

return inputValue;  // Success path - no else needed!
```

### What This Pattern Offers

- **Reduces nesting** - Error handling doesn't wrap the success path
- **Early exit** - Invalid cases are handled immediately
- **Clearer intent** - "If error, bail out; otherwise, proceed"

This pattern becomes particularly useful when you have multiple validations:

```java
// Without guard clauses
if (condition1) {
    if (condition2) {
        if (condition3) {
            // Success path is deeply nested
        }
    }
}

// With guard clauses
if (!condition1) return;
if (!condition2) return;
if (!condition3) return;
// Success path at top level
```

## 9. The Hub-and-Spoke Conversion Strategy

### The Naive Approach

With three temperature scales, you might think you need 6 conversion methods:
- Celsius → Fahrenheit
- Celsius → Kelvin
- Fahrenheit → Celsius
- Fahrenheit → Kelvin
- Kelvin → Celsius
- Kelvin → Fahrenheit

With *n* scales, that's *n × (n-1)* conversions!

### The Hub Approach

Instead, pick one scale as the "hub" (Celsius is natural here):

```java
double toCelsius(double value, Scale source) {
    // Only 3 conversions: F→C, K→C, C→C
}

double fromCelsius(double celsius, Scale target) {
    // Only 3 conversions: C→F, C→K, C→C
}

double convert(double value, Scale source, Scale target) {
    double celsius = toCelsius(value, source);
    return fromCelsius(celsius, target);
}
```

Now you only need *2n* conversions instead of *n²*!

### Consider Weighing These Trade-offs

**Advantages:**
- Much less code to write and maintain
- Single source of truth for each conversion formula
- Easy to add new scales—just add two new conversions

**Disadvantages:**
- Tiny precision loss from double conversions (negligible for this use case)
- Slightly less efficient (one extra operation, but unnoticeable)

For most applications, the simplicity may win out. You might optimize to direct conversions if you have specific performance requirements or precision concerns.

## Final Thoughts

None of these patterns are mandatory for your solution. A working temperature converter using switch statements and static variables is a legitimate solution! But as you grow as a programmer, these patterns will help you write code that's:

- **Easier to change** - When requirements evolve (and they always do)
- **Easier to test** - When you need confidence your code works
- **Easier to understand** - When you (or others) return to it months later
- **Easier to reuse** - When similar problems arise elsewhere

The goal isn't to memorize rules, but to develop intuition for recognizing patterns and understanding trade-offs. Each of these decisions involves balancing competing concerns, and the "right" choice depends on your specific context.
