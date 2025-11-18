import java.util.Scanner;

/**
 * Calculator with input validation and error handling.
 * 
 * This is a stretch version that includes:
 * - Division by zero checking
 * - Invalid operator detection
 * - Formatted output showing the complete operation
 * - Try-with-resources for automatic resource management
 * - Switch expression (Java 14+) for cleaner operator dispatch
 */
public class MainStretch {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();

            System.out.print("Enter operator (+, -, *, /): ");
            String operator = scanner.next();

            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();

            // Validate operator (early return pattern - exit immediately on invalid input)
            if (!operator.matches("[+\\-*/]")) {
                System.out.println("Error: Invalid operator '" + operator + "'. Please use +, -, *, or /");
                return;
            }

            // Check for division by zero (another early return for error handling)
            if (operator.equals("/") && num2 == 0) {
                System.out.println("Error: Cannot divide by zero");
                return;
            }

            // Calculate result using switch expression (cleaner than if-else chain)
            double result = switch (operator) {
                case "+" -> num1 + num2;
                case "-" -> num1 - num2;
                case "*" -> num1 * num2;
                case "/" -> num1 / num2;
                // Should never reach here due to validation above, but defensive programming
                default -> throw new IllegalStateException("Unexpected operator: " + operator);
            };

            // Display result with full operation
            System.out.println(num1 + " " + operator + " " + num2 + " = " + result);
        }
    }
}

