# Requirements: Temperature Converter

## Overview
Build a command-line application that converts temperatures between Celsius, Fahrenheit, and Kelvin scales.

## Functional Requirements

### FR1: Conversion Selection
- Display a menu listing all possible temperature conversions
- Support conversions between all three scales: Celsius, Fahrenheit, and Kelvin
- Accept numeric input to select the desired conversion type
- Show appropriate error message when user enters an invalid menu option

### FR2: Temperature Input
- Prompt the user to enter a temperature value for the selected source scale
- Accept decimal numbers as input
- Display the scale name and symbol when prompting for input

### FR3: Input Validation
- Reject non-numeric input and display an error message
- Validate that the entered temperature is not below absolute zero for the given scale
  - Celsius: not below -273.15°C
  - Fahrenheit: not below -459.67°F
  - Kelvin: not below 0 K
- Re-prompt the user after validation failures

### FR4: Temperature Conversion
- Convert the input temperature from the source scale to the target scale
- Display the result with 2 decimal places of precision
- Include the scale name and symbol in the output

### FR5: Exit Functionality
- Allow the user to exit the application by typing "exit" at the menu prompt
- Display a farewell message when exiting

### FR6: Error Recovery
- Keep the application running after validation errors
- Allow the user to try again without restarting the program

## Non-Functional Requirements

### NFR1: Accuracy
- Temperature conversions must be mathematically accurate

### NFR2: Usability
- Prompts and error messages should be clear and helpful
- The menu should be easy to read and understand
- Output should be formatted in a user-friendly way

