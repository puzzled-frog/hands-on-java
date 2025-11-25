# Design Notes: Refactored Version

## Overview
The refactored version separates business logic from presentation logic, demonstrating professional software design principles. The game logic is extracted into dedicated classes that can be tested, reused, and maintained independently.

## Architecture

### Class Responsibilities

```
Main.java           → Presentation Layer (UI/Console)
GuessChecker.java   → Business Logic Layer (Game Rules)
GuessResult.java    → Data Transfer Object (Result Communication)
```

**Separation of Concerns:**
- Each class has a single, well-defined responsibility
- Changes to UI don't affect game logic
- Game logic can be tested without I/O

## Design Principles Applied

### 1. Single Responsibility Principle (SRP)

**Main** - Handles user interaction:
- Read user input from console
- Display messages to user
- Control game flow (the loop)
- Format output

**GuessChecker** - Manages game logic:
- Store secret number
- Validate guesses
- Track game state (attempts, win condition)
- Apply game rules

**GuessResult** - Represents outcomes:
- Type-safe communication between layers
- Self-documenting possible results

### 2. Encapsulation

**Private state, public interface:**
```java
public class GuessChecker {
    // Private - internal state hidden
    private final int secretNumber;
    private int attemptsLeft;
    private boolean hasWon;
    
    // Public - controlled access
    public GuessResult checkGuess(int guess) { ... }
    public boolean isGameOver() { ... }
    public int getAttemptsLeft() { ... }
}
```

**Benefits:**
- Internal implementation can change without affecting callers
- State can only be modified through controlled methods
- Invariants can be maintained (e.g., attemptsLeft never negative)

### 3. Tell, Don't Ask

❌ **Bad (Asking for data to make decisions):**
```java
// Main is making game logic decisions
while (game.getAttemptsLeft() > 0 && !game.hasWon()) {
    // Main knows the rules about when game ends
}
```

✅ **Good (Telling object to make decisions):**
```java
// GuessChecker knows when game is over
while (!game.isGameOver()) {
    // Main doesn't need to know the rules
}
```

**In GuessChecker:**
```java
public boolean isGameOver() {
    return attemptsLeft == 0 || hasWon;  // Logic encapsulated here
}
```

### 4. Dependency Injection (Testability)

**Problem:** Random numbers make testing impossible
```java
public GuessChecker() {
    this.secretNumber = new Random().nextInt(1, 101);
    // How do you test this? You don't know the number!
}
```

**Solution:** Constructor overloading
```java
// Production - random number
public GuessChecker() {
    this(new Random().nextInt(MIN_NUMBER, MAX_NUMBER + 1));
}

// Testing - controlled number
public GuessChecker(int secretNumber) {
    if (secretNumber < MIN_NUMBER || secretNumber > MAX_NUMBER) {
        throw new IllegalArgumentException("Invalid secret number");
    }
    this.secretNumber = secretNumber;
}
```

**Now testing is deterministic:**
```java
@Test
public void testCorrectGuess() {
    GuessChecker game = new GuessChecker(50);  // Known value!
    assertEquals(GuessResult.CORRECT, game.checkGuess(50));
}
```

## Implementation Details

### 1. Enum for Type Safety

**Instead of strings or integers:**
```java
public enum GuessResult {
    CORRECT,
    TOO_HIGH,
    TOO_LOW,
    OUT_OF_RANGE
}
```

**Benefits:**
- Compile-time checking (typos impossible)
- Clear, limited set of possibilities
- Self-documenting code
- IDE autocomplete support
- Can't accidentally use invalid value

**Usage in switch:**
```java
switch (result) {
    case CORRECT -> handleWin();
    case TOO_HIGH -> System.out.println("Too high!");
    // Compiler warns if you miss a case
}
```

### 2. Boolean Method Naming Conventions

Java conventions make code read like English:

**`is` prefix** - State/Condition
```java
public boolean isGameOver() { ... }
if (game.isGameOver()) { ... }  // Reads naturally
```

**`has` prefix** - Possession/Existence
```java
public boolean hasWon() { ... }
if (game.hasWon()) { ... }
```

**`can` prefix** - Capability
```java
public boolean canContinue() { ... }
```

### 3. Constants Visibility

```java
public static final int MIN_NUMBER = 1;
public static final int MAX_NUMBER = 100;
public static final int MAX_ATTEMPTS = 7;
```

**Why public:**
- UI layer needs these for display messages
- They define the "contract" of the game
- They're constants (can't be modified)
- No risk in exposing them

**Accessed via class name:**
```java
System.out.println("Number between " + GuessChecker.MIN_NUMBER 
                   + " and " + GuessChecker.MAX_NUMBER);
```

### 4. Smart Switch Expression

Using `Integer.compare()` for elegant comparison:
```java
return switch (Integer.compare(guess, secretNumber)) {
    case 0 -> {                    // Equal
        hasWon = true;
        yield GuessResult.CORRECT;
    }
    case -1 -> GuessResult.TOO_LOW;   // guess < secret
    case 1 -> GuessResult.TOO_HIGH;   // guess > secret
    default -> throw new IllegalStateException();
};
```

**Benefits:**
- Concise and clear
- Single comparison (more efficient than if-else chain)
- Switch expression returns a value directly

## Testing Strategy

### What to Test in GuessChecker

```java
@Test
public void testCorrectGuess() {
    GuessChecker game = new GuessChecker(50);
    assertEquals(GuessResult.CORRECT, game.checkGuess(50));
    assertTrue(game.hasWon());
}

@Test
public void testTooHighGuess() {
    GuessChecker game = new GuessChecker(50);
    assertEquals(GuessResult.TOO_HIGH, game.checkGuess(75));
}

@Test
public void testAttemptsDecrement() {
    GuessChecker game = new GuessChecker(50);
    assertEquals(7, game.getAttemptsLeft());
    game.checkGuess(25);
    assertEquals(6, game.getAttemptsLeft());
}

@Test
public void testGameOverAfterSevenAttempts() {
    GuessChecker game = new GuessChecker(50);
    for (int i = 0; i < 7; i++) {
        game.checkGuess(1);  // Wrong guess
    }
    assertTrue(game.isGameOver());
}

@Test
public void testOutOfRangeDoesntConsumeAttempt() {
    GuessChecker game = new GuessChecker(50);
    game.checkGuess(101);  // Out of range
    assertEquals(7, game.getAttemptsLeft());  // Should still be 7
}
```

### What NOT to Test
- Don't test `Main` directly (it's just UI wiring)
- Don't test with random numbers (non-deterministic)
- Don't test console output (fragile, hard to maintain)

## Benefits of This Architecture

### 1. Testability
- Game logic can be tested without I/O
- Deterministic tests using constructor injection
- Fast tests (no console interaction needed)

### 2. Flexibility
Want a GUI? Just replace `Main`:
```java
public class GuiMain extends JFrame {
    private GuessChecker game = new GuessChecker();
    // Use same game logic, different UI!
}
```

Want a web API? Same logic:
```java
@PostMapping("/guess")
public GuessResult makeGuess(@RequestBody int guess) {
    return game.checkGuess(guess);
}
```

### 3. Maintainability
- Changes to game rules? Edit `GuessChecker` only
- Changes to UI? Edit `Main` only
- Each class is small, focused, and understandable

### 4. Reusability
- `GuessChecker` can be used in any context
- Can create multiple games simultaneously
- Logic is independent of presentation

## Common Design Questions

### Q: Should the loop live in Main or GuessChecker?
**A: Main** - The UI controls flow, business logic provides state

### Q: Should OUT_OF_RANGE consume an attempt?
**A: Design choice** - Current implementation doesn't (more forgiving)

### Q: Isn't the parameterized constructor only for tests?
**A: No** - It also enables:
- Debugging specific scenarios
- Demo mode with known numbers
- Game replays
- Custom difficulty levels

Plus, testability often reveals better design!

### Q: Why not just use booleans instead of GuessResult enum?
**A: Enums are better** when you have multiple outcomes:
- Type-safe (can't use wrong value)
- Self-documenting (clear what all possibilities are)
- Compiler-checked (won't miss a case in switch)

## Key Takeaways

1. **Separation of concerns** makes code maintainable
2. **Testability** drives good design
3. **Encapsulation** protects invariants
4. **Type safety** prevents bugs
5. **Small classes** with single responsibilities are easier to understand
6. **Design for change** - assume requirements will evolve

## Comparison with Simple Version

| Aspect | Simple Version | Refactored Version |
|--------|---------------|-------------------|
| Lines of code | ~70 in one file | ~120 in 3 files |
| Testability | Very difficult | Easy |
| Reusability | None | High |
| Maintainability | Low | High |
| Learning curve | Easy | Moderate |
| Best for | Prototypes, learning | Production, growth |

## When to Refactor

Start with the simple version, refactor when:
- You need to write tests
- Requirements are growing
- Multiple developers involved
- Code will be maintained long-term
- Need to reuse logic elsewhere

**Don't over-engineer** - Simple version is perfect for throwaway scripts and learning exercises!
