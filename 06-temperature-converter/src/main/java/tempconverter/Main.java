package tempconverter;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    /**
     * Entry point for the temperature converter application.
     * Prompts the user to select a conversion type, input a temperature value,
     * and displays the converted result.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {

            printMenu();

            Optional<ConversionChoice> choiceOpt = readConversionChoice(scanner);
            if (choiceOpt.isEmpty()) {
                return;
            }
            ConversionChoice choice = choiceOpt.get();

            double temperature = readTemperature(scanner, choice.source());
            double result = Converter.convert(temperature, choice.source(), choice.target());

            System.out.printf("Temperature in %s: %.2f%s%n", choice.target().getDisplayName(), result, choice.target().getSymbol());

        }

    }

    private static Optional<ConversionChoice> readConversionChoice(Scanner scanner) {
        Map<String, ConversionChoice> conversions = Map.of(
                "1", new ConversionChoice(Scale.CELSIUS, Scale.FAHRENHEIT),
                "2", new ConversionChoice(Scale.FAHRENHEIT, Scale.CELSIUS),
                "3", new ConversionChoice(Scale.CELSIUS, Scale.KELVIN),
                "4", new ConversionChoice(Scale.KELVIN, Scale.CELSIUS),
                "5", new ConversionChoice(Scale.FAHRENHEIT, Scale.KELVIN),
                "6", new ConversionChoice(Scale.KELVIN, Scale.FAHRENHEIT)
        );

        while (true) {
            System.out.print(">> ");
            String menuChoice = scanner.nextLine().trim();

            if (menuChoice.equals("exit")) {
                System.out.println("Bye!");
                return Optional.empty();
            }
            ConversionChoice choice = conversions.get(menuChoice);
            if (choice != null) {
                return Optional.of(choice);
            } else {
                System.out.println("Please choose an available option.");
            }

        }

    }

    private static void printMenu() {
        System.out.println("""
                Choose a conversion:
                1. Celsius to Fahrenheit
                2. Fahrenheit to Celsius
                3. Celsius to Kelvin
                4. Kelvin to Celsius
                5. Fahrenheit to Kelvin
                6. Kelvin to Fahrenheit
                
                Or type "exit" to leave.
                """);
    }

    private static double readTemperature(Scanner scanner, Scale source) {
        while (true) {
            System.out.printf("Temperature in %s: ", source.getDisplayName());

            try {
                String input = scanner.nextLine().trim();
                double inputValue = Double.parseDouble(input);
                if (inputValue < source.getAbsoluteZero()) {
                    System.out.printf("The temperature cannot be lower than the absolute zero: %.2f%s%n%n", source.getAbsoluteZero(), source.getSymbol());
                    continue;
                }
                return inputValue;
            } catch (NumberFormatException e) {
                System.out.println("Not a number.");
            }

        }
    }

}

