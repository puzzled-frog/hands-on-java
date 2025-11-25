package refactored;

import java.util.Random;

public class GuessChecker {

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 100;
    public static final int MAX_ATTEMPTS = 7;
    private final int secretNumber;
    private int attemptsLeft = MAX_ATTEMPTS;
    private boolean hasWon = false;

    public GuessChecker() {
        Random random = new Random();
        secretNumber = random.nextInt(MIN_NUMBER, MAX_NUMBER + 1);
    }

    public GuessChecker(int secretNumber) {
        if (secretNumber < MIN_NUMBER || secretNumber > MAX_NUMBER) {
            throw new IllegalArgumentException();
        }
        this.secretNumber = secretNumber;
    }

    public GuessResult checkGuess(int guess) {

        if (guess < MIN_NUMBER || guess > MAX_NUMBER) {
            return GuessResult.OUT_OF_RANGE;
        }

        attemptsLeft--;

        return switch (Integer.compare(guess, secretNumber)) {
            case 0 -> {
                hasWon = true;
                yield GuessResult.CORRECT;
            }
            case -1 -> GuessResult.TOO_LOW;
            case 1 -> GuessResult.TOO_HIGH;
            default -> throw new IllegalStateException();
        };
    }

    public boolean isGameOver() {
        return attemptsLeft == 0 || hasWon;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public int getAttemptsTaken() {
        return MAX_ATTEMPTS - attemptsLeft;
    }

    public int getSecretNumber() {
        return secretNumber;
    }

}
