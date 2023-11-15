/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.irurueta.units;

import java.math.BigDecimal;

/**
 * Does temperature conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying temperature measurements.
 */
public class TemperatureConverter {

    /**
     * Absolute zero temperature expressed in Kelvins (K).
     */
    public static final double ABSOLUTE_ZERO = -273.15;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private TemperatureConverter() {
    }

    /**
     * Converts a temperature to provided output temperature unit.
     *
     * @param input  input temperature to be converted.
     * @param output output temperature where result will be stored and containing
     *               output unit.
     */
    public static void convert(final Temperature input, final Temperature output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a temperature to requested output unit.
     *
     * @param input      input temperature to be converted.
     * @param outputUnit requested output unit.
     * @return converted temperature.
     */
    public static Temperature convertAndReturnNew(
            final Temperature input,
            final TemperatureUnit outputUnit) {
        final Temperature result = new Temperature();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a temperature to requested output unit.
     *
     * @param temperature input temperature to be converted and updated.
     * @param outputUnit  requested output unit.
     */
    public static void convert(
            final Temperature temperature, final TemperatureUnit outputUnit) {
        convert(temperature, outputUnit, temperature);
    }

    /**
     * Converts a temperature to requested output unit.
     *
     * @param input      input temperature to be converted.
     * @param outputUnit requested output unit.
     * @param result     temperature unit where result will be stored.
     */
    public static void convert(
            final Temperature input, final TemperatureUnit outputUnit,
            final Temperature result) {
        final Number value = convert(input.getValue(), input.getUnit(),
                outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a temperature value from input unit to provided output unit.
     *
     * @param input      temperature value.
     * @param inputUnit  input temperature unit.
     * @param outputUnit output temperature unit.
     * @return converted temperature value.
     */
    public static Number convert(
            final Number input, final TemperatureUnit inputUnit,
            final TemperatureUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit,
                outputUnit));
    }

    /**
     * Converts a temperature value from input unit to provided output unit.
     *
     * @param input      temperature value.
     * @param inputUnit  input temperature unit.
     * @param outputUnit output temperature unit.
     * @return converted temperature value.
     */
    public static double convert(
            final double input, final TemperatureUnit inputUnit,
            final TemperatureUnit outputUnit) {

        double celsius;

        // convert to celsius
        switch (inputUnit) {
            case FAHRENHEIT:
                celsius = fahrenheitToCelsius(input);
                break;
            case KELVIN:
                celsius = kelvinToCelsius(input);
                break;
            case CELSIUS:
            default:
                celsius = input;
                break;
        }

        // convert from celsius to required output unit
        switch (outputUnit) {
            case FAHRENHEIT:
                return celsiusToFahrenheit(celsius);
            case KELVIN:
                return celsiusToKelvin(celsius);

            case CELSIUS:
            default:
                return celsius;
        }
    }

    /**
     * Converts provided Kelvin value to Celsius.
     *
     * @param kelvin kelvin value.
     * @return some amount of temperature converted to Celsius.
     */
    public static double kelvinToCelsius(final double kelvin) {
        return kelvin + ABSOLUTE_ZERO;
    }

    /**
     * Converts provided Celsius value to Kelvin.
     *
     * @param celsius celsius value.
     * @return some amount of temperature converted to Kelvin.
     */
    public static double celsiusToKelvin(final double celsius) {
        return celsius - ABSOLUTE_ZERO;
    }

    /**
     * Converts provided Celsius value to Fahrenheit.
     *
     * @param celsius celsius value.
     * @return some amount of temperature converted to Fahrenheit.
     */
    public static double celsiusToFahrenheit(final double celsius) {
        return celsius * 9.0 / 5.0 + 32.0;
    }

    /**
     * Converts provided Fahrenheit value to Celsius.
     *
     * @param fahrenheit fahrenheit value.
     * @return some amount of temperature converted to Celsius.
     */
    public static double fahrenheitToCelsius(final double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }
}
