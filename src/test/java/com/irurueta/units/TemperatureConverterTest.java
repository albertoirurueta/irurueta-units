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

        assertEquals(inputValue + TemperatureConverter.ABSOLUTE_ZERO,
                TemperatureConverter.kelvinToCelsius(inputValue),
                ERROR);
        assertEquals(inputValue - TemperatureConverter.ABSOLUTE_ZERO,
                TemperatureConverter.celsiusToKelvin(inputValue),
                ERROR);

        assertEquals(inputValue,
                TemperatureConverter.celsiusToKelvin(
                        TemperatureConverter.kelvinToCelsius(inputValue)),
                ERROR);
        assertEquals(inputValue,
                TemperatureConverter.kelvinToCelsius(
                        TemperatureConverter.celsiusToKelvin(inputValue)),
                ERROR);
    }

    @Test
    public void testCelsiusFahrenheit() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue * 9.0 / 5.0 + 32.0,
                TemperatureConverter.celsiusToFahrenheit(inputValue),
                ERROR);
        assertEquals((inputValue - 32.0) * 5.0 / 9.0,
                TemperatureConverter.fahrenheitToCelsius(inputValue),
                ERROR);

        assertEquals(inputValue,
                TemperatureConverter.fahrenheitToCelsius(
                        TemperatureConverter.celsiusToFahrenheit(inputValue)),
                ERROR);
        assertEquals(inputValue,
                TemperatureConverter.celsiusToFahrenheit(
                        TemperatureConverter.fahrenheitToCelsius(inputValue)),
                ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.CELSIUS, TemperatureUnit.CELSIUS),
                ERROR);
        assertEquals(TemperatureConverter.celsiusToFahrenheit(inputValue),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.CELSIUS, TemperatureUnit.FAHRENHEIT),
                ERROR);
        assertEquals(TemperatureConverter.celsiusToKelvin(inputValue),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN),
                ERROR);

        assertEquals(TemperatureConverter.fahrenheitToCelsius(inputValue),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.FAHRENHEIT, TemperatureUnit.CELSIUS),
                ERROR);
        assertEquals(inputValue,
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.FAHRENHEIT, TemperatureUnit.FAHRENHEIT),
                ERROR);
        assertEquals(TemperatureConverter.celsiusToKelvin(
                        TemperatureConverter.fahrenheitToCelsius(inputValue)),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.FAHRENHEIT, TemperatureUnit.KELVIN),
                ERROR);

        assertEquals(TemperatureConverter.kelvinToCelsius(inputValue),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.KELVIN, TemperatureUnit.CELSIUS),
                ERROR);
        assertEquals(TemperatureConverter.celsiusToFahrenheit(
                        TemperatureConverter.kelvinToCelsius(inputValue)),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.KELVIN, TemperatureUnit.FAHRENHEIT),
                ERROR);
        assertEquals(inputValue,
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.KELVIN, TemperatureUnit.KELVIN),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                TemperatureConverter.convert(inputValue,
                        TemperatureUnit.KELVIN, TemperatureUnit.KELVIN).doubleValue(),
                ERROR);
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
        assertEquals(value, inputTemperature.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.CELSIUS, inputTemperature.getUnit());

        assertEquals(TemperatureUnit.KELVIN, outputTemperature.getUnit());
        assertEquals(TemperatureConverter.convert(value, inputTemperature.getUnit(),
                outputTemperature.getUnit()), outputTemperature.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndUpdateTemperature() {
        final double value = new Random().nextDouble();
        final Temperature temperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        TemperatureConverter.convert(temperature, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, temperature.getUnit());
        assertEquals(TemperatureConverter.convert(value, TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN), temperature.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndReturnNewTemperature() {
        final double value = new Random().nextDouble();
        final Temperature inputTemperature = new Temperature(
                value, TemperatureUnit.CELSIUS);

        final Temperature outputTemperature = TemperatureConverter
                .convertAndReturnNew(inputTemperature, TemperatureUnit.KELVIN);

        // check
        assertEquals(value, inputTemperature.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.CELSIUS, inputTemperature.getUnit());

        assertEquals(TemperatureUnit.KELVIN, outputTemperature.getUnit());
        assertEquals(TemperatureConverter.convert(value, inputTemperature.getUnit(),
                outputTemperature.getUnit()), outputTemperature.getValue().doubleValue(), 0.0);
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
        assertEquals(value, inputTemperature.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.CELSIUS, inputTemperature.getUnit());

        assertEquals(TemperatureUnit.KELVIN, outputTemperature.getUnit());
        assertEquals(TemperatureConverter.convert(value, inputTemperature.getUnit(),
                outputTemperature.getUnit()), outputTemperature.getValue().doubleValue(), 0.0);
    }
}
