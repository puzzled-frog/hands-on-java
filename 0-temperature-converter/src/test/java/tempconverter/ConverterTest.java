package tempconverter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tempconverter.Scale.*;

/**
 * Unit tests for the Converter class.
 * Tests all temperature scale conversions including edge cases.
 */
class ConverterTest {

    private static final double DELTA = 0.001;

    // ========== Celsius to other scales ==========

    @Test
    void celsiusToFahrenheit_freezingPoint() {
        double result = Converter.convert(0, CELSIUS, FAHRENHEIT);
        assertEquals(32.0, result, DELTA);
    }

    @Test
    void celsiusToFahrenheit_boilingPoint() {
        double result = Converter.convert(100, CELSIUS, FAHRENHEIT);
        assertEquals(212.0, result, DELTA);
    }

    @Test
    void celsiusToFahrenheit_negativeForty() {
        double result = Converter.convert(-40, CELSIUS, FAHRENHEIT);
        assertEquals(-40.0, result, DELTA);
    }

    @Test
    void celsiusToFahrenheit_roomTemperature() {
        double result = Converter.convert(20, CELSIUS, FAHRENHEIT);
        assertEquals(68.0, result, DELTA);
    }

    @Test
    void celsiusToKelvin_freezingPoint() {
        double result = Converter.convert(0, CELSIUS, KELVIN);
        assertEquals(273.15, result, DELTA);
    }

    @Test
    void celsiusToKelvin_boilingPoint() {
        double result = Converter.convert(100, CELSIUS, KELVIN);
        assertEquals(373.15, result, DELTA);
    }

    @Test
    void celsiusToKelvin_absoluteZero() {
        double result = Converter.convert(-273.15, CELSIUS, KELVIN);
        assertEquals(0.0, result, DELTA);
    }

    // ========== Fahrenheit to other scales ==========

    @Test
    void fahrenheitToCelsius_freezingPoint() {
        double result = Converter.convert(32, FAHRENHEIT, CELSIUS);
        assertEquals(0.0, result, DELTA);
    }

    @Test
    void fahrenheitToCelsius_boilingPoint() {
        double result = Converter.convert(212, FAHRENHEIT, CELSIUS);
        assertEquals(100.0, result, DELTA);
    }

    @Test
    void fahrenheitToCelsius_negativeForty() {
        double result = Converter.convert(-40, FAHRENHEIT, CELSIUS);
        assertEquals(-40.0, result, DELTA);
    }

    @Test
    void fahrenheitToCelsius_roomTemperature() {
        double result = Converter.convert(68, FAHRENHEIT, CELSIUS);
        assertEquals(20.0, result, DELTA);
    }

    @Test
    void fahrenheitToKelvin_freezingPoint() {
        double result = Converter.convert(32, FAHRENHEIT, KELVIN);
        assertEquals(273.15, result, DELTA);
    }

    @Test
    void fahrenheitToKelvin_boilingPoint() {
        double result = Converter.convert(212, FAHRENHEIT, KELVIN);
        assertEquals(373.15, result, DELTA);
    }

    @Test
    void fahrenheitToKelvin_absoluteZero() {
        double result = Converter.convert(-459.67, FAHRENHEIT, KELVIN);
        assertEquals(0.0, result, DELTA);
    }

    // ========== Kelvin to other scales ==========

    @Test
    void kelvinToCelsius_absoluteZero() {
        double result = Converter.convert(0, KELVIN, CELSIUS);
        assertEquals(-273.15, result, DELTA);
    }

    @Test
    void kelvinToCelsius_freezingPoint() {
        double result = Converter.convert(273.15, KELVIN, CELSIUS);
        assertEquals(0.0, result, DELTA);
    }

    @Test
    void kelvinToCelsius_boilingPoint() {
        double result = Converter.convert(373.15, KELVIN, CELSIUS);
        assertEquals(100.0, result, DELTA);
    }

    @Test
    void kelvinToFahrenheit_absoluteZero() {
        double result = Converter.convert(0, KELVIN, FAHRENHEIT);
        assertEquals(-459.67, result, DELTA);
    }

    @Test
    void kelvinToFahrenheit_freezingPoint() {
        double result = Converter.convert(273.15, KELVIN, FAHRENHEIT);
        assertEquals(32.0, result, DELTA);
    }

    @Test
    void kelvinToFahrenheit_boilingPoint() {
        double result = Converter.convert(373.15, KELVIN, FAHRENHEIT);
        assertEquals(212.0, result, DELTA);
    }

    // ========== Same scale conversions (identity) ==========

    @Test
    void celsiusToCelsius_returnsInput() {
        double result = Converter.convert(100, CELSIUS, CELSIUS);
        assertEquals(100.0, result, DELTA);
    }

    @Test
    void fahrenheitToFahrenheit_returnsInput() {
        double result = Converter.convert(100, FAHRENHEIT, FAHRENHEIT);
        assertEquals(100.0, result, DELTA);
    }

    @Test
    void kelvinToKelvin_returnsInput() {
        double result = Converter.convert(100, KELVIN, KELVIN);
        assertEquals(100.0, result, DELTA);
    }

    // ========== Edge cases and special values ==========

    @Test
    void negativeTemperaturesAreHandledCorrectly() {
        double result = Converter.convert(-273.15, CELSIUS, FAHRENHEIT);
        assertEquals(-459.67, result, DELTA);
    }

    @Test
    void veryHighTemperaturesWork() {
        double result = Converter.convert(1000, CELSIUS, FAHRENHEIT);
        assertEquals(1832.0, result, DELTA);
    }

    @Test
    void decimalValuesAreHandledAccurately() {
        double result = Converter.convert(37.5, CELSIUS, FAHRENHEIT);
        assertEquals(99.5, result, DELTA);
    }

    @Test
    void smallDecimalDifferencesArePreserved() {
        double result = Converter.convert(0.01, CELSIUS, KELVIN);
        assertEquals(273.16, result, DELTA);
    }
}
