# Requirements

- There is a menu to choose conversion (celsius to fahrenheit; fahrenheit to celsuis, celsius to kelvin, etc); only celsius to fahrenheit in the MVP
- The user chooses the option through the its number
- The program prompts for the original temperature
- The program shows the converted temperature

- Type is always double with 2 decimal points

- Error handling (friendly messages):
  - Not a menu option
  - Wrong temperature format

- Loops until user types "exit"

# Arquitecture

Classes:
- ConsoleIO: input/output; instantiates objects
- Menu: returns source temperature and target temperature based on the number selected by the user
- tempconverter.Converter: implements the conversion logic

# Conversion Strategy

We will use the canonical approach: we'll have a scale enum (the set of supported temperature scales): `CELSIUS,FAHRENHEIT, KELVIN`
We will pick **Celsius** as the hub scale.
tempconverter.Converter will have two methods: `toCelsius` and `fromCelsius`. In a conversion between other scales (eg., fahrenheit and kelvin), first we convert from the source scale to celsius

## Rationale

