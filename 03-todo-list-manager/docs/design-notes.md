# Design Notes: Todo List Manager

This document explores key design decisions and patterns that emerge when building a todo list application with multiple classes and collections. These considerations will help you think about encapsulation, object collaboration, and managing data structures.

## 1. Encapsulation: Controlling Access to Data

### The Open Door Problem

When creating a class, you might be tempted to make fields accessible without thinking about visibility:

```java
public class Task {
    String description;  // Package-private by default
    boolean completed;
}
```

One potential pitfall is that this allows any class in the same package to directly access and modify these fields:

```java
task.description = "Changed!";  // Anyone can do this
task.completed = false;         // No control over state changes
```

### Private by Default

An alternative approach is to make fields `private` and expose them only through methods:

```java
public class Task {
    private String description;
    private boolean completed;
    
    public String getDescription() {
        return description;
    }
    
    public boolean isCompleted() {
        return completed;
    }
}
```

Consider what this enables:
- **Control**: You decide what can be read and what can be modified
- **Validation**: Methods can check values before accepting them
- **Future flexibility**: You can change internal representation without breaking other code
- **Documentation**: The public methods show the intended API

### The Collection Exposure Trap

A particularly important case involves collections:

```java
public class TaskManager {
    ArrayList<Task> tasks = new ArrayList<>();  // Package-private
}
```

This creates a serious problem: code outside TaskManager can directly manipulate the list, bypassing all your logic:

```java
taskManager.tasks.clear();        // Deletes everything!
taskManager.tasks.add(null);      // Adds invalid data
taskManager.tasks = new ArrayList<>();  // Replaces entire list
```

Making the field `private` prevents this:

```java
private ArrayList<Task> tasks = new ArrayList<>();
```

Now other classes must go through your methods like `addTask()` and `deleteTask()`, giving you control over what happens.

## 2. The `final` Keyword: Two Different Meanings

### Final for Immutable Data

You may encounter your IDE suggesting `final` for a field that never changes after construction. Consider a task's description—once created, it typically doesn't change:

```java
private final String description;  // Can only be assigned once

public Task(String description) {
    this.description = description;  // OK: first assignment
}

public void updateDescription(String newDesc) {
    this.description = newDesc;  // ERROR: cannot reassign final field
}
```

This communicates intent: "This value will never change." Benefits include:
- Makes code easier to reason about (fewer moving parts)
- Prevents accidental modification
- Thread-safety guarantees in concurrent code
- Compiler can optimize based on immutability

### Final for Reference Variables

A potentially confusing case is `final` with collections:

```java
private final ArrayList<Task> tasks = new ArrayList<>();

public void addTask(String description) {
    tasks.add(new Task(description));  // ✓ This works fine!
}

public void resetAllTasks() {
    tasks = new ArrayList<>();  // ✗ ERROR: cannot reassign final variable
}
```

The key insight: **`final` prevents reassigning the reference, not modifying the object's contents.**

Think of it like this:
- The variable `tasks` is like a house address written in permanent ink
- You can't move to a different house (can't reassign)
- But you can rearrange furniture inside (can modify the ArrayList)

This is a very common pattern in Java—collection fields are almost always declared `final` because you want to keep the same collection object throughout the object's lifetime, just changing what's inside it.

## 3. Object Delegation: Who Should Do What?

### The Setter Alternative

When you need to mark a task as complete, one approach is a generic setter:

```java
public class Task {
    private boolean completed;
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}

// Usage
task.setCompleted(true);
```

This works, but consider the semantic meaning: `setCompleted(true)` is generic and could be used to set it back to `false` without clear intent.

### The Domain Method Approach

An alternative is to create methods that express domain operations:

```java
public class Task {
    private boolean completed;
    
    public void complete() {
        this.completed = true;
    }
}

// Usage
task.complete();  // Clear intent
```

### Weighing the Trade-offs

**Generic setters:**
- More flexible (can set any value)
- Standard JavaBean pattern
- Familiar to developers

**Domain methods:**
- More expressive (`complete()` vs `setCompleted(true)`)
- Encapsulates business logic
- Easier to extend (add timestamps, validation, side effects)
- Prevents misuse (no accidental `setCompleted(false)`)

You might ask: "But `complete()` only sets a boolean—is a method really necessary?" Consider that even simple operations can grow:

```java
public void complete() {
    this.completed = true;
    this.completedAt = LocalDateTime.now();
    // Or: validate the task isn't already deleted
    // Or: notify observers
}
```

The method provides a place for this logic without changing any calling code.

## 4. Collections: Your First Dynamic Data Structure

### From Single Values to Many

Previous challenges worked with individual values—numbers, strings. This challenge introduces managing *multiple* objects that can grow and shrink:

```java
private ArrayList<Task> tasks = new ArrayList<>();
```

Key differences from arrays:
- **Size changes dynamically**: No need to declare capacity upfront
- **Easy insertion/removal**: `.add()`, `.remove()` handle the details
- **Type safety with generics**: `ArrayList<Task>` can only hold Tasks

### Iterating Over Collections

When displaying tasks, you have several options:

```java
// For-each loop (most common)
for (Task task : tasks) {
    System.out.println(task.getDisplayString());
}

// Traditional for loop (when you need the index)
for (int i = 0; i < tasks.size(); i++) {
    Task task = tasks.get(i);
    System.out.println((i + 1) + ". " + task.getDisplayString());
}
```

The for-each loop is preferred when you just need each element. The indexed loop is useful when you need to display position numbers or modify the list during iteration.

### The Index Shift Problem

One potential pitfall with ArrayList is that indices shift when you remove elements:

```
Before: [Task1, Task2, Task3, Task4]
         Index:  0      1      2      3

Remove index 1:
After:  [Task1, Task3, Task4]
         Index:  0      1      2
```

What was Task3 at index 2 is now at index 1. This means:
- User sees "Task 3" but must now enter "2" to reference it
- After deletion, all subsequent task numbers change

For a simple todo app, this is acceptable and actually matches user expectations (the list reflows). An alternative would be to assign stable IDs to tasks, but that adds complexity—you'd need to search the list instead of using direct index access.

## 5. Validation as a Separate Concern

### Duplicated Validation Logic

When building `completeTask()` and `deleteTask()`, you might write the same validation twice:

```java
public boolean completeTask(int taskNumber) {
    int index = taskNumber - 1;
    if (index >= 0 && index < tasks.size()) {
        tasks.get(index).complete();
        return true;
    }
    return false;
}

public boolean deleteTask(int taskNumber) {
    int index = taskNumber - 1;
    if (index >= 0 && index < tasks.size()) {  // Same check!
        tasks.remove(index);
        return true;
    }
    return false;
}
```

### Extracting Common Validation

An alternative is to extract the validation into a private helper method:

```java
private boolean isValidTaskNumber(int taskNumber) {
    int index = taskNumber - 1;
    return index >= 0 && index < tasks.size();
}

public boolean completeTask(int taskNumber) {
    if (!isValidTaskNumber(taskNumber)) {
        return false;
    }
    tasks.get(taskNumber - 1).complete();
    return true;
}
```

Benefits of this approach:
- **Single source of truth**: Change validation logic in one place
- **Easier to test**: Can test validation independently
- **More readable**: The intent is clear from the method name
- **Private helper**: Implementation detail, not part of public API

The `private` modifier is key here—this is an internal helper that callers don't need to know about. This is called **information hiding** and is a core principle of good object-oriented design.

## 6. Boolean Returns for Operation Results

### Signaling Success or Failure

When operations can fail (like deleting a non-existent task), you need to communicate this to the caller. One approach is to return a boolean:

```java
public boolean deleteTask(int taskNumber) {
    if (!isValidTaskNumber(taskNumber)) {
        return false;  // Failed
    }
    tasks.remove(taskNumber - 1);
    return true;  // Succeeded
}
```

This allows the calling code to provide appropriate feedback:

```java
if (taskManager.deleteTask(taskNumber)) {
    System.out.println("Task deleted!");
} else {
    System.out.println("Invalid task number.");
}
```

### Alternative Approaches

Other ways to signal results include:

**Printing directly in the method:**
```java
public void deleteTask(int taskNumber) {
    if (!isValidTaskNumber(taskNumber)) {
        System.out.println("Invalid task number");
        return;
    }
    tasks.remove(taskNumber - 1);
    System.out.println("Task deleted!");
}
```

This is simpler but couples the TaskManager to console output, making it harder to test and reuse.

**Throwing exceptions:**
```java
public void deleteTask(int taskNumber) {
    if (!isValidTaskNumber(taskNumber)) {
        throw new IllegalArgumentException("Invalid task number");
    }
    tasks.remove(taskNumber - 1);
}
```

This is appropriate for truly exceptional conditions, but invalid user input is expected and normal, not exceptional.

**Return enum with detailed reasons:**
```java
enum OperationResult { SUCCESS, INVALID_ID, LIST_EMPTY }

public OperationResult deleteTask(int taskNumber) { ... }
```

This provides more detail but adds complexity. For this challenge, boolean returns strike a good balance.

### Separation of Concerns

The boolean return pattern maintains separation between:
- **Business logic** (TaskManager): Validates and performs operations
- **User interface** (Main): Handles input/output and user messaging

This makes both easier to understand and modify independently.

## 7. Exception Handling: Keep Try Blocks Narrow

### The Catch-All Temptation

When parsing user input, you might be tempted to wrap large blocks in try-catch:

```java
try {
    int taskNumber = Integer.parseInt(scanner.nextLine());
    if (taskManager.completeTask(taskNumber)) {
        System.out.println("Task marked as completed.");
    } else {
        System.out.println("Invalid task number.");
    }
} catch (NumberFormatException e) {
    System.out.println("Please enter a valid number.");
}
```

One potential pitfall: The try block contains code that has nothing to do with number parsing. If `taskManager.completeTask()` somehow threw a `NumberFormatException` in the future, it would be caught incorrectly.

### Narrow Try Blocks

A better approach is to wrap only the code that can throw the exception:

```java
int taskNumber;
try {
    taskNumber = Integer.parseInt(scanner.nextLine());
} catch (NumberFormatException e) {
    System.out.println("Please enter a valid number.");
    return;  // Exit early
}

// Only reached if parsing succeeded
if (taskManager.completeTask(taskNumber)) {
    System.out.println("Task marked as completed.");
} else {
    System.out.println("Invalid task number.");
}
```

Benefits:
- **Precise handling**: Only catches exceptions from the risky operation
- **Clear intent**: The try block shows exactly what might fail
- **Safer refactoring**: Changes to surrounding code won't accidentally be caught

The early `return` is crucial—without it, the code would continue with an uninitialized or default value, leading to confusing error messages.

### Exceptions vs. Validation

Consider the difference:

**Exception handling**: For operations that *might* fail in ways you can't easily predict
```java
try {
    Integer.parseInt(userInput);  // Can't easily check before parsing
} catch (NumberFormatException e) { ... }
```

**Validation**: For conditions you *can* check beforehand
```java
if (index >= 0 && index < tasks.size()) {
    tasks.get(index);  // We know this is safe
}
```

Using try-catch for validation (catching `IndexOutOfBoundsException`) is considered poor practice in Java because:
- It's using exceptions for control flow
- Invalid input is expected, not exceptional
- Checking is straightforward with simple comparisons
- The check makes your intent clearer

## 8. StringBuilder: When String Concatenation Matters

### The Immutable String Problem

When building a list of tasks, you might use string concatenation:

```java
String taskList = "";
for (Task task : tasks) {
    taskList += task.getDisplayString() + "\n";  // Creates a new String each time
}
return taskList;
```

One potential pitfall: Strings are immutable. Each `+=` operation:
1. Creates a new String object
2. Copies the old content
3. Adds the new content
4. Discards the old String

With 10 tasks, this creates 10 string objects. With 100 tasks, it creates 100 objects and does a lot of copying.

### Mutable String Building

An alternative is `StringBuilder`, which builds strings efficiently:

```java
StringBuilder taskList = new StringBuilder();
for (Task task : tasks) {
    taskList.append(task.getDisplayString()).append("\n");  // Modifies in place
}
return taskList.toString();  // Convert to String once at the end
```

The `append()` method modifies the StringBuilder's internal buffer instead of creating new objects.

### When to Use Each

**String concatenation (`+`):**
- Fine for a few concatenations
- Readable for simple cases: `"Hello " + name + "!"`
- Compiler optimizes simple cases anyway

**StringBuilder:**
- Better for loops with many iterations
- Required when building strings incrementally across many operations
- More efficient but slightly more verbose

For this challenge with small task lists, either approach works. But developing the habit of using StringBuilder in loops is valuable for when you work with larger datasets.

## 9. The Empty State: Edge Cases with Collections

### Assuming Data Exists

When implementing `viewAllTasks()`, you might write:

```java
public String viewAllTasks() {
    StringBuilder taskList = new StringBuilder();
    for (Task task : tasks) {
        taskList.append(task.getDisplayString()).append("\n");
    }
    return taskList.toString();
}
```

One potential pitfall: What if `tasks` is empty? The loop doesn't execute, and you return an empty string. The user sees nothing and might think the feature is broken.

### Handling Empty Collections

Consider checking for the empty case explicitly:

```java
public String viewAllTasks() {
    if (tasks.isEmpty()) {
        return "You don't have any tasks yet.\n";
    }
    
    StringBuilder taskList = new StringBuilder();
    for (Task task : tasks) {
        taskList.append(task.getDisplayString()).append("\n");
    }
    return taskList.toString();
}
```

This improves the user experience by making the empty state clear.

### Where Should Empty State Logic Live?

You have a choice about where to handle this:

**In the data class (TaskManager):**
- The class knows its own state best
- Consistent behavior regardless of caller
- But mixes data logic with presentation concerns

**In the UI layer (Main):**
```java
String taskList = taskManager.viewAllTasks();
if (taskList.isEmpty()) {
    System.out.println("You don't have any tasks yet.");
} else {
    System.out.println(taskList);
}
```
- Keeps presentation logic in the presentation layer
- But spreads the empty-state handling around

For small applications, either approach works. Consider whether the "no tasks" message is part of the data's contract or part of the UI's responsibility.

## 10. Method Extraction: Breaking Down Complexity

### The Monolithic Main Method

You might be tempted to put all logic in `main()`:

```java
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    TaskManager taskManager = new TaskManager();
    
    while (true) {
        System.out.println("=== Menu ===");
        System.out.println("1. Add task");
        // ... more menu options ...
        
        String choice = scanner.nextLine();
        
        if (choice.equals("1")) {
            System.out.print("Enter task description: ");
            String description = scanner.nextLine().trim();
            if (description.isEmpty()) {
                System.out.println("Cannot be empty!");
            } else {
                taskManager.addTask(description);
                System.out.println("Added!");
            }
        } else if (choice.equals("2")) {
            // ... more logic ...
        }
        // ... hundreds of lines ...
    }
}
```

One potential pitfall: As complexity grows, this becomes difficult to read and maintain. Each operation is buried in the control flow.

### Extracting Operations into Methods

An alternative approach is to extract each operation:

```java
public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
        TaskManager taskManager = new TaskManager();
        boolean running = true;
        
        while (running) {
            displayMenu();
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1" -> handleAddTask(scanner, taskManager);
                case "2" -> handleViewTasks(taskManager);
                case "3" -> handleCompleteTask(scanner, taskManager);
                case "4" -> handleDeleteTask(scanner, taskManager);
                case "5" -> running = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }
}

private static void handleAddTask(Scanner scanner, TaskManager taskManager) {
    // All add-task logic here
}

private static void handleCompleteTask(Scanner scanner, TaskManager taskManager) {
    // All complete-task logic here
}
```

Benefits of this structure:
- **Main loop is readable**: Easy to see all available operations
- **Each operation is isolated**: Easier to understand and modify
- **Self-documenting**: Method names describe what they do
- **Easier to test**: Each operation can be tested independently

### Parameter Passing vs. Static Fields

You may be tempted to make `scanner` and `taskManager` static fields to avoid passing them:

```java
static Scanner scanner;
static TaskManager taskManager;

public static void main(String[] args) {
    scanner = new Scanner(System.in);
    taskManager = new TaskManager();
    
    handleAddTask();  // No parameters needed
}

private static void handleAddTask() {
    // Uses static fields directly
}
```

While this feels convenient, it creates hidden dependencies—you can't tell from the method signature what data it needs. An alternative is to pass dependencies explicitly as parameters. This makes the code:
- **Easier to test**: Can pass test doubles
- **More reusable**: Methods don't depend on global state
- **Clearer**: Dependencies are visible in the signature

For challenge 3, explicit parameter passing is good practice even though it requires a bit more typing.

## 11. toString() vs. Display Methods

### The toString() Override

Java's `Object` class provides a default `toString()` method, but its output isn't useful:

```java
Task@5e91993f  // Default toString() output
```

You can override it to provide meaningful output:

```java
@Override
public String toString() {
    return "Task{description='" + description + "', completed=" + completed + "}";
}
```

### The Purpose of toString()

One consideration: `toString()` is conventionally used for debugging and logging, not user-facing display. It typically shows the object's complete state in a developer-friendly format.

### Separate Display Methods

An alternative for user-facing display is a separate method:

```java
@Override
public String toString() {
    return "Task{description='" + description + "', completed=" + completed + "}";
}

public String getDisplayString() {
    String status = completed ? "[X]" : "[ ]";
    return status + " " + description;
}
```

Benefits:
- **Separation of concerns**: Debug output vs. user display
- **Flexibility**: Can have multiple display formats
- **Clear intent**: Method name indicates purpose

This pattern becomes especially valuable when you need different representations:
- `toString()`: For developers/logs
- `getDisplayString()`: For users
- `toJson()`: For APIs
- `toHtml()`: For web pages

## Final Thoughts

The todo list manager introduces fundamental object-oriented concepts that you'll use in every Java program: encapsulation, object collaboration, and collection management. None of these patterns are strictly required—a working todo app is a working todo app—but they represent decisions that affect maintainability and future changes.

As you build more complex applications, these patterns compound:
- Private fields prevent unexpected modifications in larger codebases
- Validation helpers scale to more complex business rules
- Method extraction becomes crucial as feature sets grow
- Separation of concerns enables testing and reuse

The goal isn't to memorize "best practices," but to understand the trade-offs involved in different approaches. Each decision balances simplicity, flexibility, and maintainability. The "right" choice depends on your specific context, but understanding these patterns gives you tools to make informed decisions.

