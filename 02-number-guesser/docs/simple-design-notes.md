# Design Notes: Simple Version

## Overview
The simple version implements all game logic within a single `Main` class. This approach is suitable for small programs and demonstrates fundamental programming concepts.

## Key Concepts

### 1. Program Structure
- **Single class design**: All functionality contained in one place
- **Static methods**: `main()` and `playGame()` don't require object instantiation
- **Linear flow**: Easy to follow top-to-bottom execution

### 2. Control Flow
```java
while (attemptsLeft > 0) {
    // Game loop continues until attempts exhausted
}
```
- **While loops**: Repeat gameplay until condition met
- **Early return**: Exit immediately on correct guess
- **Continue statement**: Skip invalid input without consuming attempts

### 3. Random Number Generation
```java
Random random = new Random();
int secretNumber = random.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
```
- Generate random integers within a range
- Upper bound is exclusive (hence `MAX_NUMBER + 1`)

### 4. Input Validation
**Two layers of validation:**
1. **Type validation**: Catch non-numeric input
```java
try {
    guess = Integer.parseInt(scanner.nextLine().trim());
} catch (NumberFormatException e) {
    System.out.println("Invalid input!");
    continue;  // Don't consume an attempt
}
```

2. **Range validation**: Check business rules
```java
if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
    System.out.println("Number out of range!");
    continue;  // Don't consume an attempt
}
```

### 5. Constants vs Magic Numbers
❌ **Bad (Magic numbers):**
```java
int secretNumber = random.nextInt(1, 101);
if (guess < 1 || guess > 100) { ... }
System.out.println("You have 7 attempts");
```

✅ **Good (Named constants):**
```java
private static final int MIN_NUMBER = 1;
private static final int MAX_NUMBER = 100;
private static final int MAX_ATTEMPTS = 7;
```

**Benefits:**
- Self-documenting code
- Single source of truth
- Easy to modify game parameters

### 6. Resource Management
```java
try (Scanner scanner = new Scanner(System.in)) {
    playGame(scanner);
}
```
- **Try-with-resources** ensures Scanner is properly closed
- Prevents resource leaks
- Scanner automatically closes even if exceptions occur

### 7. User Experience Details
**String pluralization:**
```java
"attempt" + (attemptsTaken == 1 ? "" : "s")
```
- "1 attempt" (singular)
- "2 attempts" (plural)

**Clear feedback:**
- Show remaining attempts after each guess
- Reveal correct number on game over
- Provide hints (too high/too low)

## Limitations of This Approach

### 1. Hard to Test
- Cannot easily verify game logic without running entire program
- Random number makes testing non-deterministic
- Input/output coupled with logic

### 2. Hard to Extend
- Adding GUI would require rewriting everything
- Cannot reuse game logic in different contexts
- Difficult to add features like difficulty levels

### 3. Single Responsibility Violation
The `playGame()` method does multiple things:
- Manages game state
- Reads user input
- Validates input
- Checks guesses
- Formats output
- Controls game flow

## When to Use This Approach
✅ **Good for:**
- Learning exercises
- Prototypes
- Simple scripts
- One-off utilities

❌ **Not good for:**
- Production applications
- Code that needs testing
- Programs that will grow
- Reusable components

## Key Takeaways
1. **Start simple** - This version is perfectly fine for learning
2. **Constants matter** - Always extract magic numbers
3. **Resource management** - Always close what you open
4. **Input validation** - Never trust user input
5. **User experience** - Small touches (pluralization, clear messages) matter

## Next Steps
For more maintainable code, see the refactored version which demonstrates:
- Separation of concerns
- Testable design
- Object-oriented principles
