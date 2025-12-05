# Design Notes: CSV Data Analyzer

This document explores key design decisions that emerge when reading external data sources and processing them. These considerations involve file I/O strategies, error handling approaches, and how to structure collaborating classes effectively.

## 1. File Reading Approaches: Streaming vs. Loading

### The Three Main Options

When reading a text file in Java, you have several approaches with fundamentally different memory characteristics:

**BufferedReader (Streaming):**
```java
try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
    String line;
    while ((line = reader.readLine()) != null) {
        // Process line immediately, then discard
    }
}
```

**Files.readAllLines() (Load Everything):**
```java
List<String> lines = Files.readAllLines(Paths.get(filePath));
for (String line : lines) {
    // Process line, but ALL lines still in memory
}
```

**Scanner (Convenient but Slower):**
```java
try (Scanner scanner = new Scanner(new File(filePath))) {
    while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        // Process line
    }
}
```

### The Memory Trade-off

The critical difference isn't about syntax—it's about **when and how data enters memory**.

With `Files.readAllLines()`, a 10 GB file requires ~10 GB of RAM upfront. The entire file must fit in memory before you process anything. You might wonder: "Won't BufferedReader eventually read all the data too?" The key insight is **temporal locality**:

```
Files.readAllLines():
Memory: [Line1][Line2][Line3]...[Line1000000]  <- All at once
Process: Work with the list

BufferedReader:
Memory: [Line1] -> Process -> Discard
Memory: [Line2] -> Process -> Discard  
Memory: [Line3] -> Process -> Discard
```

With BufferedReader, only the current line occupies memory. Previous lines are eligible for garbage collection. When you're accumulating statistics (like total revenue), you don't need the raw text—just the computed values.

### Why Scanner Is Slower

Scanner does more work than BufferedReader:
- Built-in parsing and type conversion (even when unused)
- Synchronization overhead for thread safety
- Smaller default buffer size (1KB vs 8KB)
- Regex-based tokenization

For simple line-by-line reading, BufferedReader is the standard choice. Scanner shines when you need its parsing features, like reading structured input directly as typed values.

### When Each Approach Makes Sense

**Use BufferedReader when:**
- Processing large files
- Building aggregate results (like our CSV analyzer)
- Files might not fit in memory

**Use Files.readAllLines() when:**
- Files are small (< few MB)
- You need to process lines multiple times
- You need random access to lines

**Use Scanner when:**
- Parsing structured input with mixed types
- Reading from System.in interactively
- Convenience matters more than performance

One potential pitfall is choosing `Files.readAllLines()` because it looks cleaner, without considering that production data files might be gigabytes in size. The streaming approach with BufferedReader scales naturally from small test files to large datasets.

## 2. Exception Handling: Categories of Failure

### Not All Errors Are Equal

When reading and parsing CSV files, you encounter fundamentally different categories of errors:

**Category 1: Fatal errors (can't continue):**
- File doesn't exist (`FileNotFoundException`)
- Permission denied (`IOException`)
- Disk failure (`IOException`)

**Category 2: Recoverable errors (skip and continue):**
- Invalid date format (`DateTimeParseException`)
- Non-numeric value (`NumberFormatException`)
- Missing fields (`ArrayIndexOutOfBoundsException`)
- Invalid business rules (`IllegalArgumentException`)

You may be tempted to catch `Exception` generically:

```java
try {
    SalesRecord record = SalesRecord.fromCsvFields(fields);
    analyzer.processRecord(record);
} catch (Exception e) {
    // What kind of error was this?
}
```

### The Case for Specific Catches

An alternative approach is to catch exactly what you expect:

```java
try {
    SalesRecord record = SalesRecord.fromCsvFields(fields);
    analyzer.processRecord(record);
} catch (DateTimeParseException | NumberFormatException | 
         ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
    // We know this is a parsing/validation error
    errorCount++;
}
```

Benefits of specific exception handling:
- **Documents expectations**: The catch clause shows what errors are anticipated
- **Catches unexpected bugs**: If `NullPointerException` occurs, it won't be silently counted as a "parsing error"
- **Enables different handling**: File I/O errors might need different response than data errors

Consider the difference:

```java
// Generic catch
} catch (Exception e) {
    errorCount++;  // Counts everything, including bugs in your code!
}

// Specific catches with separate handling
} catch (DateTimeParseException | NumberFormatException | 
         ArrayIndexOutOfBoundsException e) {
    errorCount++;  // Count as malformed data
    System.err.println("Error at line " + lineNum + ": " + e.getMessage());
} catch (FileNotFoundException e) {
    System.err.println("File not found: " + filePath);
    return;  // Can't continue
} catch (IOException e) {
    System.err.println("Error reading file: " + e.getMessage());
    return;  // Can't continue
}
```

This structure clearly separates:
- Per-record errors (count and continue)
- File-level errors (report and exit)

One consideration: More specific catches mean more code. You're weighing explicitness against brevity. For learning and maintainability, explicit error handling tends to pay dividends as programs grow.

## 3. Class Responsibilities: Orchestration and Collaboration

### The Three-Layer Structure

This challenge introduces a pattern where three classes collaborate with distinct responsibilities:

```
Main (Orchestrator)
  ↓ creates and wires
CsvReader (Worker) ← receives → DataAnalyzer (Accumulator)
  ↓ calls methods on
```

You might wonder: "Why not have CsvReader create its own DataAnalyzer internally?"

```java
// Alternative: CsvReader creates its own analyzer
public class CsvReader {
    public void readFile(String filePath) {
        DataAnalyzer analyzer = new DataAnalyzer();  // Creates internally
        // ... read and analyze
        // Problem: How does Main get the results?
    }
}
```

One potential pitfall: CsvReader now "owns" the analyzer, making it difficult for Main to retrieve results. You'd need to either return the analyzer or add forwarding methods:

```java
public DataAnalyzer readFile(String filePath) {
    DataAnalyzer analyzer = new DataAnalyzer();
    // ... process
    return analyzer;  // Loses streaming benefit
}

// Or add forwarding methods
public double getTotalRevenue() {
    return analyzer.getTotalRevenue();  // CsvReader delegates to analyzer
}
```

### Dependency Injection Pattern

An alternative is **dependency injection**—Main creates the analyzer and "injects" it into CsvReader:

```java
// Main orchestrates
DataAnalyzer analyzer = new DataAnalyzer();
CsvReader reader = new CsvReader();
reader.readFile(filePath, analyzer);  // Pass analyzer as parameter

// Now Main has direct access to results
System.out.println(analyzer.getTotalRevenue());
```

This pattern, where an outer layer creates dependencies and passes them to inner layers, has several benefits:

**Testability:**
```java
// Can test with a mock analyzer
DataAnalyzer mockAnalyzer = new MockAnalyzer();
reader.readFile("test.csv", mockAnalyzer);
// Verify what data was sent to analyzer
```

**Flexibility:**
```java
// Can use the same reader with different analyzers
reader.readFile("sales.csv", salesAnalyzer);
reader.readFile("returns.csv", returnsAnalyzer);
```

**Clear ownership:**
- Main owns the lifecycle of both objects
- CsvReader doesn't need to know about returning results
- DataAnalyzer is a service that accumulates data

This is sometimes called the **callback pattern**—CsvReader "calls back" to the analyzer as it reads each line. It's how many real libraries work: you provide an object that implements certain methods, and the library calls those methods with data as it becomes available.

## 4. Factory Methods: Separating Construction Concerns

### The Parsing Problem

When creating a `SalesRecord` from CSV fields, you need to parse strings into typed values:

```java
String[] fields = {"2024-01-15", "Mouse", "25", "29.99"};
// Need: LocalDate, String, int, double
```

You may be tempted to handle parsing in the constructor:

```java
public SalesRecord(String date, String product, String quantity, String price) {
    this.date = LocalDate.parse(date);
    this.quantity = Integer.parseInt(quantity);
    this.price = Double.parseDouble(price);
}
```

One potential pitfall: The constructor now forces everyone to use strings, even when they already have proper types:

```java
// In tests, creating test data becomes awkward
SalesRecord record = new SalesRecord(
    "2024-01-15",           // Have to convert LocalDate to string
    "Mouse",
    String.valueOf(25),     // Have to convert int to string
    String.valueOf(29.99)   // Have to convert double to string
);
// Then the constructor parses them back! Wasteful.
```

### The Static Factory Method

An alternative is a **factory method** specifically for CSV parsing:

```java
public class SalesRecord {
    // Main constructor takes typed values
    public SalesRecord(LocalDate date, String product, int quantity, double price) {
        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
    
    // Factory method for CSV parsing
    public static SalesRecord fromCsvFields(String[] fields) {
        return new SalesRecord(
            LocalDate.parse(fields[0]),
            fields[1],
            Integer.parseInt(fields[2]),
            Double.parseDouble(fields[3])
        );
    }
}
```

Now you have **two ways to create records**:

```java
// From CSV
SalesRecord record1 = SalesRecord.fromCsvFields(fields);

// From typed values (in tests, from database, etc.)
SalesRecord record2 = new SalesRecord(
    LocalDate.of(2024, 1, 15),
    "Mouse",
    25,
    29.99
);
```

Benefits of this pattern:
- **Constructor stays general-purpose**: Works with any data source
- **Factory method is format-specific**: CSV parsing logic lives in one place
- **Clear intent**: Method name indicates what format it expects
- **Extensible**: Can add `fromJson()`, `fromDatabase()`, etc.

The factory method pattern is common in Java:
```java
LocalDate.parse("2024-01-15")      // Factory method
LocalDate.of(2024, 1, 15)          // Factory method
String.valueOf(123)                 // Factory method
Collections.emptyList()             // Factory method
```

One consideration: Should the factory method throw exceptions or handle errors internally? For CSV parsing, letting exceptions bubble up makes sense—the caller (CsvReader) is in the best position to decide whether to log the error, count it, or abort processing.

## 5. Programming to Interfaces: Map vs HashMap

### The Declaration Choice

When declaring a field to hold product quantities, you might write:

```java
private HashMap<String, Integer> productQuantities = new HashMap<>();
```

An alternative is to declare with the interface type:

```java
private Map<String, Integer> productQuantities = new HashMap<>();
```

You may wonder: "Both work—does it matter?" The difference becomes apparent when you want to change implementations:

```java
// With concrete type
private HashMap<String, Integer> productQuantities = new HashMap<>();

// Later, decide you want insertion order preserved
private HashMap<String, Integer> productQuantities = new LinkedHashMap<>();
// ❌ Type mismatch! LinkedHashMap is not a HashMap
```

```java
// With interface type
private Map<String, Integer> productQuantities = new HashMap<>();

// Later, change implementation
private Map<String, Integer> productQuantities = new LinkedHashMap<>();
// ✓ Works fine! Both implement Map
```

### What You Actually Need

Consider what methods you use:

```java
productQuantities.merge(key, value, Integer::sum);  // Map method
productQuantities.entrySet()                        // Map method  
productQuantities.values()                          // Map method
```

You're only using **Map interface methods**, not HashMap-specific ones. Your declaration should reflect what you actually need, not a specific implementation detail.

### The Flexibility Benefit

This principle—**program to an interface, not an implementation**—provides flexibility:

```java
public Map<String, Integer> getProductQuantities() {
    return productQuantities;  // Works with any Map implementation
}

// Callers work with the interface
Map<String, Integer> quantities = analyzer.getProductQuantities();
// Doesn't care if it's HashMap, TreeMap, or LinkedHashMap
```

One consideration: When should you use the concrete type? Only when you need implementation-specific methods:

```java
HashMap<String, Integer> map = new HashMap<>();
map.clone();  // HashMap-specific method
```

For 99% of use cases, you only need Map interface methods. The convention in Java is:

```java
List<String> list = new ArrayList<>();        // Not ArrayList<String>
Set<Integer> set = new HashSet<>();           // Not HashSet<Integer>
Map<String, Integer> map = new HashMap<>();   // Not HashMap<String, Integer>
```

This is such a common pattern that you'll see it throughout the Java standard library and well-designed codebases.

## 6. LocalDate and the Java Time API

### Why Not String?

When your CSV has a date field, you might store it as a String:

```java
private final String date;  // "2024-01-15"
```

This works if dates are just display values. But consider what operations you might need:

```java
// Which date came first?
if (date1.compareTo(date2) < 0)  // String comparison: "2024-01-15" vs "2024-01-09"
// This works for ISO format, but is it obvious?

// Is this date in January?
if (date.startsWith("2024-01"))  // String manipulation
// Fragile and unclear

// Days between two dates?
// Can't do it with strings!
```

An alternative is `LocalDate`, which represents dates as actual date objects:

```java
private final LocalDate date;

// Clear date operations
if (date1.isBefore(date2))                    // Clear intent
if (date.getMonth() == Month.JANUARY)         // Semantic
long days = ChronoUnit.DAYS.between(date1, date2)  // Actual calculation
```

### Type Safety and Validation

One benefit of `LocalDate` is built-in validation:

```java
// String accepts anything
String date = "2024-99-99";  // Invalid but compiles

// LocalDate validates
LocalDate date = LocalDate.parse("2024-99-99");  
// Throws DateTimeParseException
```

This means errors are caught early, at parsing time, rather than causing subtle bugs later.

### Automatic String Conversion

When you concatenate a LocalDate with a String, Java automatically calls its `toString()` method:

```java
LocalDate date = LocalDate.of(2024, 1, 15);
System.out.println("Date: " + date);  
// Calls date.toString(), prints: Date: 2024-01-15
```

You might wonder why you don't need to call `toString()` explicitly. This is a Java convenience—when using `+` with Strings, any object is automatically converted using its `toString()` method. This works for any object:

```java
Integer num = 42;
System.out.println("Number: " + num);  // Calls num.toString()

SalesRecord record = new SalesRecord(...);
System.out.println("Record: " + record);  // Calls record.toString()
```

One potential pitfall: This only works when concatenating with strings. If you need the string for other purposes, call `toString()` explicitly:

```java
String dateStr = date.toString();
String upperDate = dateStr.toUpperCase();  // Now you can manipulate it
```

### Should You Always Use LocalDate?

Consider the trade-offs:

**Use LocalDate when:**
- You need date operations (comparison, filtering, grouping)
- Validation matters
- Type safety is valuable
- It communicates that this field is semantically a date

**Use String when:**
- Date is purely for display
- You're not doing date operations
- Simplicity matters more than type safety

For this challenge, using `LocalDate` is appropriate even though we don't do complex date operations—it validates the data and represents the domain concept accurately. As the requirements grow (filtering by date range, grouping by month), the date operations become available naturally.

## 7. Map.merge() and Method References

### Accumulating Values

When tracking product quantities, each CSV row might have the same product appearing multiple times:

```
Mouse, quantity: 25
Mouse, quantity: 32  <- Need to add to existing 25
Mouse, quantity: 28  <- Need to add to existing 57
```

You might handle this with explicit checks:

```java
if (productQuantities.containsKey(product)) {
    int current = productQuantities.get(product);
    productQuantities.put(product, current + quantity);
} else {
    productQuantities.put(product, quantity);
}
```

### The merge() Method

An alternative is `Map.merge()`, which handles both cases:

```java
productQuantities.merge(product, quantity, Integer::sum);
```

This single line says: "If product exists, sum the old value with quantity. Otherwise, insert quantity."

The three parameters are:
1. **Key**: The product name
2. **Value**: The quantity to add
3. **Remapping function**: How to combine old and new values

### What Is Integer::sum?

The `::` operator is a **method reference**—a shorthand for a lambda expression. These are equivalent:

```java
Integer::sum                          // Method reference
(a, b) -> Integer.sum(a, b)          // Lambda expression
(a, b) -> a + b                      // Lambda expression (inline)
```

All three mean: "Take two integers and add them." The method reference is the most concise when you're just calling an existing method without additional logic.

Common method references you'll encounter:

```java
Integer::sum         // Static method: Integer.sum(a, b)
String::toUpperCase  // Instance method: str.toUpperCase()
System.out::println  // Instance method: System.out.println(x)
List::size          // Instance method: list.size()
```

### When to Use merge()

The `merge()` method is particularly useful for the "add to existing or insert new" pattern:

```java
// Counting occurrences
map.merge(word, 1, Integer::sum);

// Accumulating values  
map.merge(category, amount, Double::sum);

// Keeping maximum
map.merge(key, value, Integer::max);

// Custom logic
map.merge(key, newValue, (old, new) -> old * 2 + new);
```

One consideration: Is method reference `Integer::sum` clearer than the lambda `(a, b) -> a + b`? This is somewhat subjective. The method reference is standard Java idiom, but the lambda might be more immediately understandable. Both work; consistency with idiomatic Java favors method references.

## 8. Where Should Error Tracking Live?

### The Responsibility Question

When parsing errors occur, you need to count them. You might track this in `CsvReader`:

```java
public class CsvReader {
    private int errorCount = 0;
    
    public void readFile(String filePath, DataAnalyzer analyzer) {
        // ... reading
        try {
            // ... parse
        } catch (Exception e) {
            errorCount++;
        }
    }
    
    public int getErrorCount() {
        return errorCount;
    }
}
```

An alternative is to track errors in `DataAnalyzer`:

```java
public class DataAnalyzer {
    private int errorCount = 0;
    
    public void recordError() {
        errorCount++;
    }
}

// CsvReader calls the analyzer
catch (Exception e) {
    analyzer.recordError();
}
```

### Weighing the Options

Consider what each class represents:

**CsvReader:**
- Responsibility: Read and parse files
- State: Current file, line number
- Knows about: File operations, CSV format

**DataAnalyzer:**
- Responsibility: Analyze sales data
- State: Accumulated statistics, error count
- Knows about: Revenue, products, dates, records processed

Error count is a **statistic about the data**, not about file reading. If you later read from a database instead of a file, error counting would still be relevant. If you process multiple files into one analyzer, errors from all files would accumulate together.

This suggests error tracking belongs in `DataAnalyzer` alongside other analysis results:

```java
System.out.println("Total Records: " + analyzer.getTotalRecords());
System.out.println("Failed Records: " + analyzer.getFailedRecords());
System.out.println("Total Revenue: " + analyzer.getTotalRevenue());
```

All analysis results come from one place, making the API consistent.

One potential pitfall: Tracking errors in CsvReader works fine for this challenge. The downside emerges when you want to:
- Process multiple files into one analysis
- Change data source (from file to database)
- Test DataAnalyzer independently

The dependency injection pattern naturally leads to this structure: CsvReader is a worker that delegates all data concerns to the analyzer service.

## 9. Validation in Domain Objects

### Where Should Validation Happen?

When creating a `SalesRecord`, you might validate in the factory method:

```java
public static SalesRecord fromCsvFields(String[] fields) {
    int quantity = Integer.parseInt(fields[2]);
    if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity must be positive");
    }
    
    return new SalesRecord(...);
}
```

An alternative is to validate in the constructor:

```java
public SalesRecord(LocalDate date, String product, int quantity, double price) {
    if (quantity <= 0) {
        throw new IllegalArgumentException("Quantity must be positive");
    }
    if (price < 0) {
        throw new IllegalArgumentException("Price cannot be negative");
    }
    
    // Only reached if validation passes
    this.date = date;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
}
```

### The Constructor Approach

Validating in the constructor has an important property: **it's impossible to create invalid objects**. No matter how you construct a SalesRecord—from CSV, from tests, from a database—the validation always applies:

```java
// From CSV
SalesRecord.fromCsvFields(fields);  // Validates

// From tests
new SalesRecord(date, "Mouse", -5, 29.99);  // Validates

// From anywhere
new SalesRecord(date, product, qty, price);  // Validates
```

This is called **maintaining invariants**—the object ensures its own validity at construction time. Once a SalesRecord exists, you know it contains valid data.

### Constructor vs. Factory Validation

One consideration: Should the factory method add additional validation beyond the constructor?

```java
public static SalesRecord fromCsvFields(String[] fields) {
    // CSV-specific validation
    if (fields.length != 4) {
        throw new IllegalArgumentException("CSV must have 4 fields");
    }
    
    // Delegates to constructor for domain validation
    return new SalesRecord(
        LocalDate.parse(fields[0]),
        fields[1],
        Integer.parseInt(fields[2]),
        Double.parseDouble(fields[3])
    );
}
```

This separates concerns:
- **Format validation**: Factory method (is this valid CSV?)
- **Domain validation**: Constructor (is this a valid SalesRecord?)

One potential pitfall: Over-validating. Do you really need to reject negative prices? Maybe the business has refunds represented as negative values. Validation should enforce **invariants**—rules that must always be true—not assume all business cases. For a learning project, simple validation is appropriate. In production, validation rules would come from business requirements.

## 10. The BigDecimal Question: Precision vs. Practicality

### Money and Floating Point

When working with money, you may encounter the advice: "Never use `double` for money, always use `BigDecimal`." This stems from floating-point precision issues:

```java
double total = 0.1 + 0.1 + 0.1;
System.out.println(total);  // 0.30000000000000004
System.out.println(total == 0.3);  // false!
```

For financial calculations where precision matters (banking, accounting), `BigDecimal` is essential:

```java
BigDecimal price = new BigDecimal("29.99");
BigDecimal quantity = new BigDecimal("25");
BigDecimal revenue = price.multiply(quantity);  // Exactly 749.75
```

### The Practical Trade-offs

For this challenge, `double` is acceptable for several reasons:

**Complexity cost:**
```java
// With double (simple)
double total = price * quantity;
totalRevenue += revenue;

// With BigDecimal (verbose)
BigDecimal revenue = price.multiply(BigDecimal.valueOf(quantity));
totalRevenue = totalRevenue.add(revenue);
```

**Learning focus:**
This challenge teaches file I/O, CSV parsing, and class design. Adding BigDecimal complexity distracts from these core concepts.

**Practical impact:**
With typical prices like $29.99, errors are negligible:
```java
double price = 29.99;
double quantity = 25;
System.out.println(price * quantity);  // 749.75 (actually fine!)
```

You'd need unusual numbers to see problems:
```java
0.1 + 0.1 + 0.1  // Problem
29.99 * 25       // Fine
```

### When to Graduate to BigDecimal

Consider using BigDecimal when:
- Building real financial systems
- Regulatory requirements demand exact decimal arithmetic
- Accumulating many small amounts (rounding errors compound)
- The domain requires exact precision

For learning projects, prototypes, or when dealing with measurements (not money), `double` is practical. Think of it as learning to drive an automatic transmission before learning manual—both are valuable, but one at a time.

## Final Thoughts

The CSV Data Analyzer introduces external data processing, which requires thinking about problems you don't encounter with user input: file operations can fail, data can be malformed, memory usage matters at scale. These concerns lead to patterns like streaming processing, layered error handling, and careful responsibility assignment.

None of these patterns are strictly required—a working analyzer is a working analyzer—but they represent decisions that affect maintainability, scalability, and robustness:

- Streaming vs. loading affects what file sizes you can handle
- Specific exception handling catches bugs and documents intent
- Dependency injection enables testing and reuse
- Factory methods separate format-specific logic from domain logic
- Programming to interfaces provides flexibility for change
- Type-safe date handling prevents subtle bugs

As with previous challenges, the goal isn't to memorize "best practices," but to understand the trade-offs in different approaches. Each decision balances simplicity, performance, and maintainability. The "right" choice depends on your specific context, but understanding these patterns gives you tools to make informed decisions as your programs grow from processing a dozen test records to handling production datasets of millions of rows.

