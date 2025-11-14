>🚧 WORK IN PROGRESS 🚧

# Requirements

- Only allows the four basic operations: +, -, *, /
- Does not support parentheses
- Only supports one operation per input
- Requires the whole operation in the command in the following format: a op b
- Returns a friendly error message if:
  - the operator is not supported
  - the format of the expression is not supported
  - the operation is division by zero
- Input is asked via console
- Loops over the input until the user exits
- The user exits by typing “exit” and then Enter
- Operands will all be treated as BigDecimal

# Arquitecture

Classes:
- ConsoleIO
- Calculator
- Tokenizer
- Parser
- Evaluator
- Result

# High-level Flow

ConsoleIO  →  Calculator  →  Tokenizer  →  Parser  →  Evaluator

