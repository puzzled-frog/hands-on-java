package refactored;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuessCheckerTest {

    private GuessChecker guessChecker;

    @BeforeEach
    void setup() {
        guessChecker = new GuessChecker(43);
    }

    @Test
    void correctGuessReturnsCorrect() {
        GuessResult result = guessChecker.checkGuess(43);
        assertEquals(GuessResult.CORRECT, result);
    }

    @Test
    void higherGuessReturnsTooHigh() {
        GuessResult result = guessChecker.checkGuess(90);
        assertEquals(GuessResult.TOO_HIGH, result);
    }

    @Test
    void lowerGuessReturnsTooLow() {
        GuessResult result = guessChecker.checkGuess(5);
        assertEquals(GuessResult.TOO_LOW, result);
    }

    @Test
    void outOfRangeGuess() {
        GuessResult result = guessChecker.checkGuess(450);
        assertEquals(GuessResult.OUT_OF_RANGE, result);
    }

    @Test
    void outOfRangeGuessUpperBoundary() {
        GuessResult result = guessChecker.checkGuess(101);
        assertEquals(GuessResult.OUT_OF_RANGE, result);
    }

    @Test
    void outOfRangeGuessLowerBoundary() {
        GuessResult result = guessChecker.checkGuess(0);
        assertEquals(GuessResult.OUT_OF_RANGE, result);
    }

    @Test
    void guess1ReturnsCorrect() {
        GuessChecker guessChecker = new GuessChecker(1);
        GuessResult result = guessChecker.checkGuess(1);
        assertEquals(GuessResult.CORRECT, result);
    }

    @Test
    void guess100ReturnsCorrect() {
        GuessChecker guessChecker = new GuessChecker(100);
        GuessResult result = guessChecker.checkGuess(100);
        assertEquals(GuessResult.CORRECT, result);
    }

    @Test
    void getAttemptsTaken_startsAtZero() {
        assertEquals(0, guessChecker.getAttemptsTaken());
    }

    @Test
    void getAttemptsTaken_incrementsWithEachGuess() {
        assertEquals(0, guessChecker.getAttemptsTaken());

        guessChecker.checkGuess(50);
        assertEquals(1, guessChecker.getAttemptsTaken());

        guessChecker.checkGuess(25);
        assertEquals(2, guessChecker.getAttemptsTaken());
    }

    @Test
    void attemptsLeft_decrementsWithEachGuess() {
        assertEquals(7, guessChecker.getAttemptsLeft());
        guessChecker.checkGuess(50);
        assertEquals(6, guessChecker.getAttemptsLeft());
    }

    @Test
    void isGameOver_returnsFalseInitially() {
        assertFalse(guessChecker.isGameOver());
    }

    @Test
    void isGameOver_returnsTrueAfterCorrectGuess() {
        guessChecker.checkGuess(43);
        assertTrue(guessChecker.isGameOver());
    }

    @Test
    void isGameOver_returnsTrueAfterMaxAttempts() {
        for (int i = 0; i < 7; i++) {
            guessChecker.checkGuess(1);  // wrong guesses
        }
        assertTrue(guessChecker.isGameOver());
    }

    @Test
    void winningOnLastAttempt_makesGameOver() {
        // Make 6 wrong guesses
        for (int i = 0; i < 6; i++) {
            guessChecker.checkGuess(1);
        }
        assertEquals(1, guessChecker.getAttemptsLeft());

        // Win on attempt 7
        GuessResult result = guessChecker.checkGuess(43);
        assertEquals(GuessResult.CORRECT, result);
        assertTrue(guessChecker.isGameOver());
        assertEquals(0, guessChecker.getAttemptsLeft());
    }

    @Test
    void outOfRangeGuess_doesNotdecrementAttempts() {
        assertEquals(GuessResult.OUT_OF_RANGE, guessChecker.checkGuess(450));
        assertEquals(7, guessChecker.getAttemptsLeft());
    }

}
