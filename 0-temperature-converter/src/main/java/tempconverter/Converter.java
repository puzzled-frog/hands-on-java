package tempconverter;

public class Converter {

    private static final double KELVIN_OFFSET = 273.15;

    /**
     * Converts a temperature value from one scale to another. It uses Celsius as a canonical scale,
     * meaning every temperature is first converted from the source scale to Celsius and then to the
     * target scale.
     *
     * @param value the temperature value to convert
     * @param source the source temperature scale
     * @param target the target temperature scale
     * @return the converted temperature value
     */
    public static double convert(double value, Scale source, Scale target) {
        double celsius = toCelsius(value, source);
        return fromCelsius(celsius, target);
    }

    private static double toCelsius(double value, Scale source) {
        return switch(source) {
            case CELSIUS -> value;
            case FAHRENHEIT -> (value - 32) * 5.0 / 9.0;
            case KELVIN -> value - KELVIN_OFFSET;
        };
    }

    private static double fromCelsius(double value, Scale target) {
        return switch (target) {
            case CELSIUS -> value;
            case FAHRENHEIT -> value * 9.0 / 5.0 + 32.0;
            case KELVIN -> value + KELVIN_OFFSET;
        };
    }

}
