package tempconverter;

public enum Scale {
    CELSIUS("celsius", "°C", -273.15),
    FAHRENHEIT("fahrenheit", "°F", -459.67),
    KELVIN("kelvin", "K", 0.0);

    private final String displayName;
    private final String symbol;
    private final double absoluteZero;

    Scale(String displayName, String symbol, double absoluteZero) {
        this.displayName = displayName;
        this.symbol = symbol;
        this.absoluteZero = absoluteZero;
    }

    /**
     * Returns the human-readable display name of this temperature scale.
     *
     * @return the display name in lowercase (e.g., "celsius", "fahrenheit", "kelvin")
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the symbol used to represent this temperature scale.
     *
     * @return the scale symbol (e.g., "°C", "°F", "K")
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the absolute zero temperature value for this scale.
     * Temperatures below this value are physically impossible.
     *
     * @return the absolute zero value (e.g., -273.15 for Celsius, -459.67 for Fahrenheit, 0.0 for Kelvin)
     */
    public double getAbsoluteZero() {
        return absoluteZero;
    }
}
