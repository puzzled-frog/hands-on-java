# Requirements: Number Guesser

## Overview
Create a number guessing game where the player tries to guess a randomly generated number within a limited number of attempts.

## Functional Requirements

### Game Setup
- Generate a random number between 1 and 100 at the start of each game
- Provide the player with 7 attempts to guess the number
- Display game instructions and rules to the player

### Gameplay
- Prompt the player to enter a guess
- Accept numeric input from the player
- Provide feedback after each guess:
  - "Too high!" if the guess is greater than the target number
  - "Too low!" if the guess is less than the target number
  - "Correct!" if the guess matches the target number
- Display remaining attempts after each incorrect guess
- Reject non-numeric input with an appropriate error message

### Game End
- End the game when the player guesses correctly
- End the game when the player runs out of attempts
- Reveal the correct number if the player fails to guess it
- Display a congratulatory or consolation message based on the outcome

## Non-Functional Requirements

### Usability
- Feedback should be clear and immediate
- The game should be easy to understand for first-time players

