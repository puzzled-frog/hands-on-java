package simple;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 100;
    private static final int MAX_ATTEMPTS = 7;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            playGame(scanner);
        }
    }

    private static void playGame(Scanner scanner) {
        Random random = new Random();
        int secretNumber = random.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
        int attemptsLeft = MAX_ATTEMPTS;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + MIN_NUMBER + " and " + MAX_NUMBER + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts to guess it.\n");

        while (attemptsLeft > 0) {
            System.out.println("Attempts remaining: " + attemptsLeft);
            System.out.print("Enter your guess: ");

            int guess;
            try {
                guess = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid integer.\n");
                continue;
            }

            if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
                System.out.println("Number out of range! Please guess between " 
                    + MIN_NUMBER + " and " + MAX_NUMBER + ".\n");
                continue;
            }

            attemptsLeft--;

            if (guess == secretNumber) {
                int attemptsTaken = MAX_ATTEMPTS - attemptsLeft;
                System.out.println("Correct! You guessed it in " + attemptsTaken 
                    + " attempt" + (attemptsTaken == 1 ? "" : "s") + "!");
                return;
            }

            if (attemptsLeft > 0) {
                if (guess > secretNumber) {
                    System.out.println("Too high! Try again.\n");
                } else {
                    System.out.println("Too low! Try again.\n");
                }
            }
        }

        System.out.println("\nGame Over! You've run out of attempts.");
        System.out.println("The correct number was: " + secretNumber);
    }
}

