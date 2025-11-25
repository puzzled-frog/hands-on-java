package refactored;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            playGame(scanner);
        }
    }

    private static void playGame(Scanner scanner) {

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + GuessChecker.MIN_NUMBER + " and " + GuessChecker.MAX_NUMBER + ".");
        System.out.println("You have " + GuessChecker.MAX_ATTEMPTS + " attempts to guess it.\n");

        int guess;
        GuessChecker guessChecker = new GuessChecker();

        while (!guessChecker.isGameOver()) {
            try {
                System.out.print("Enter your guess: ");
                guess = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Not a number.");
                continue;
            }

            GuessResult guessResult = guessChecker.checkGuess(guess);

            switch (guessResult) {
                case GuessResult.CORRECT -> {
                    int attemptsTaken = guessChecker.getAttemptsTaken();
                    System.out.println("You won in " + attemptsTaken + " attempt" + (attemptsTaken == 1 ? "!" : "s!"));
                    return;
                }
                case GuessResult.TOO_LOW ->
                        System.out.println("Too low.\nAttempts remaining: " + guessChecker.getAttemptsLeft() + "\n");
                case GuessResult.TOO_HIGH ->
                        System.out.println("Too high.\nAttempts remaining: " + guessChecker.getAttemptsLeft() + "\n");
                case GuessResult.OUT_OF_RANGE -> System.out.println("Number out of range.\n");
                default -> throw new IllegalStateException();
            }
        }

        System.out.println("\nGame Over! You've run out of attempts.");
        System.out.println("The correct number was: " + guessChecker.getSecretNumber());

    }
}
