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

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TemperatureConverterTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testKelvinCelsius() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TemperatureConverter.kelvinToCelsius(inputValue),
                inputValue + TemperatureConverter.ABSOLUTE_ZERO, ERROR);
        assertEquals(TemperatureConverter.celsiusToKelvin(inputValue),
                inputValue - TemperatureConverter.ABSOLUTE_ZERO, ERROR);

        assertEquals(TemperatureConverter.celsiusToKelvin(
                TemperatureConverter.kelvinToCelsius(inputValue)),
                inputValue, ERROR);
        assertEquals(TemperatureConverter.kelvinToCelsius(
                TemperatureConverter.celsiusToKelvin(inputValue)),
                inputValue, ERROR);
    }

    @Test
    public void testCelsiusFahrenheit() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TemperatureConverter.celsiusToFahrenheit(inputValue),
                inputValue * 9.0 / 5.0 + 32.0, ERROR);
        assertEquals(TemperatureConverter.fahrenheitToCelsius(inputValue),
                (inputValue - 32.0) * 5.0 / 9.0, ERROR);

        assertEquals(TemperatureConverter.fahrenheitToCelsius(
                TemperatureConverter.celsiusToFahrenheit(inputValue)),
                inputValue, ERROR);
        assertEquals(TemperatureConverter.celsiusToFahrenheit(
                TemperatureConverter.fahrenheitToCelsius(inputValue)),
                inputValue, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.CELSIUS, TemperatureUnit.CELSIUS),
                inputValue, ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.CELSIUS, TemperatureUnit.FAHRENHEIT),
                TemperatureConverter.celsiusToFahrenheit(inputValue), ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN),
                TemperatureConverter.celsiusToKelvin(inputValue), ERROR);

        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.FAHRENHEIT, TemperatureUnit.CELSIUS),
                TemperatureConverter.fahrenheitToCelsius(inputValue), ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.FAHRENHEIT, TemperatureUnit.FAHRENHEIT),
                inputValue, ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.FAHRENHEIT, TemperatureUnit.KELVIN),
                TemperatureConverter.celsiusToKelvin(
                        TemperatureConverter.fahrenheitToCelsius(inputValue)),
                ERROR);

        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.KELVIN, TemperatureUnit.CELSIUS),
                TemperatureConverter.kelvinToCelsius(inputValue), ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.KELVIN, TemperatureUnit.FAHRENHEIT),
                TemperatureConverter.celsiusToFahrenheit(
                        TemperatureConverter.kelvinToCelsius(inputValue)),
                ERROR);
        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.KELVIN, TemperatureUnit.KELVIN),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(TemperatureConverter.convert(inputValue,
                TemperatureUnit.KELVIN, TemperatureUnit.KELVIN).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertTemperature() {
        final double value = new Random().nextDouble();
        final Temperature inputTemperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        final Temperature outputTemperature = new Temperature();
        TemperatureConverter.convert(inputTemperature, TemperatureUnit.KELVIN,
                outputTemperature);

        // check
        assertEquals(inputTemperature.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTemperature.getUnit(), TemperatureUnit.CELSIUS);

        assertEquals(outputTemperature.getUnit(), TemperatureUnit.KELVIN);
        assertEquals(outputTemperature.getValue().doubleValue(),
                TemperatureConverter.convert(value, inputTemperature.getUnit(),
                        outputTemperature.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateTemperature() {
        final double value = new Random().nextDouble();
        final Temperature temperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        TemperatureConverter.convert(temperature, TemperatureUnit.KELVIN);

        // check
        assertEquals(temperature.getUnit(), TemperatureUnit.KELVIN);
        assertEquals(temperature.getValue().doubleValue(),
                TemperatureConverter.convert(value, TemperatureUnit.CELSIUS,
                        TemperatureUnit.KELVIN), 0.0);
    }

    @Test
    public void testConvertAndReturnNewTemperature() {
        final double value = new Random().nextDouble();
        final Temperature inputTemperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        final Temperature outputTemperature = TemperatureConverter
                .convertAndReturnNew(inputTemperature, TemperatureUnit.KELVIN);

        // check
        assertEquals(inputTemperature.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTemperature.getUnit(), TemperatureUnit.CELSIUS);

        assertEquals(outputTemperature.getUnit(), TemperatureUnit.KELVIN);
        assertEquals(outputTemperature.getValue().doubleValue(),
                TemperatureConverter.convert(value, inputTemperature.getUnit(),
                        outputTemperature.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputTemperatureUnit() {
        final double value = new Random().nextDouble();
        final Temperature inputTemperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        final Temperature outputTemperature = new Temperature();
        outputTemperature.setUnit(TemperatureUnit.KELVIN);
        TemperatureConverter.convert(inputTemperature, outputTemperature);

        // check
        assertEquals(inputTemperature.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTemperature.getUnit(), TemperatureUnit.CELSIUS);

        assertEquals(outputTemperature.getUnit(), TemperatureUnit.KELVIN);
        assertEquals(outputTemperature.getValue().doubleValue(),
                TemperatureConverter.convert(value, inputTemperature.getUnit(),
                        outputTemperature.getUnit()), 0.0);
    }
}
