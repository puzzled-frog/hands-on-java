package calculator;

import java.util.Scanner;

public class ConsoleIO {
    public static void main(String[] args) {

        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        Evaluator evaluator = new Evaluator();
        Calculator calculator = new Calculator(tokenizer, parser, evaluator);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            Result result = calculator.evaluate(input);
            if (result.isSuccess()) {
                System.out.println(result.getValue());
            } else {
                System.err.println(result.getErrorMessage());
            }

        }

    }
}
