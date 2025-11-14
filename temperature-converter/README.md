# Temperature Converter

## Description

The Temperature Converter is a command-line application that helps you convert temperature values between the three major temperature scales: Celsius, Fahrenheit, and Kelvin. Whether you're working on a science project, cooking with international recipes, or just curious about temperature equivalents, this tool provides quick and accurate conversions.

The application presents you with a simple menu where you can choose from six conversion options covering all possible pairs between the three scales. After selecting your conversion type, you enter the temperature value you want to convert, and the application instantly displays the result with precise decimal formatting.

The converter includes smart validation to ensure reliable results. It checks that your input is a valid number and, more importantly, verifies that the temperature is physically possible by preventing values below absolute zero for each scale. If you make a mistake, the application will let you know what went wrong and give you another chance to enter a valid value.

You can perform as many conversions as you need in a single session, and when you're done, simply type "exit" to close the application. It's a straightforward tool designed to make temperature conversions effortless and accurate.

## Features

- **Complete conversion support**: All possible conversions between Celsius, Fahrenheit, and Kelvin (6 conversion options)
- **User-friendly menu**: Simple numeric selection for conversion types
- **Robust error handling**:
  - Invalid menu option validation
  - Invalid temperature format detection
  - Absolute zero validation (prevents physically impossible temperatures)
- **Precise calculations**: All temperatures displayed with 2 decimal points
- **Interactive loop**: Continues until user types "exit"

## Architecture

### Classes

- **`Main`**: Entry point that handles console I/O, menu display, user input validation, and coordinates the conversion flow
- **`Converter`**: Static utility class that implements all temperature conversion logic using the canonical (hub) approach
- **`Scale`**: Enum representing the three temperature scales (CELSIUS, FAHRENHEIT, KELVIN) with:
  - Display names for user-friendly output
  - Scale symbols (°C, °F, K)
  - Absolute zero values for validation
- **`ConversionChoice`**: Record that encapsulates a source and target scale pair

### Tests

- **`ConverterTest`**: Comprehensive JUnit test suite with 24 test cases covering:
  - All conversion pairs between scales
  - Edge cases (absolute zero, freezing/boiling points)
  - Same-scale conversions (identity)
  - Negative temperatures and high-temperature values
  - Decimal precision

## Conversion Strategy

The implementation uses the **canonical (hub) approach** with Celsius as the central scale:

1. All temperatures are first converted from the source scale to Celsius using `toCelsius()`
2. Then converted from Celsius to the target scale using `fromCelsius()`
3. This approach simplifies the logic by requiring only 2 conversion methods instead of 6

**Example**: Converting Fahrenheit to Kelvin:
- Fahrenheit → Celsius (via `toCelsius()`)
- Celsius → Kelvin (via `fromCelsius()`)

## How to Run

Using Gradle wrapper:

```bash
./gradlew run
```

Or build and run the JAR:

```bash
./gradlew build
java -cp build/libs/temperature-converter.jar tempconverter.Main
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
Choose a conversion:
1. Celsius to Fahrenheit
2. Fahrenheit to Celsius
3. Celsius to Kelvin
4. Kelvin to Celsius
5. Fahrenheit to Kelvin
6. Kelvin to Fahrenheit

Or type "exit" to leave.

>> 1
Temperature in celsius: 25
Temperature in fahrenheit: 77.00°F
```
