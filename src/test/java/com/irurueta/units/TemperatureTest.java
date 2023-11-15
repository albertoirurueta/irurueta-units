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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class TemperatureTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Temperature t = new Temperature();

        // check
        assertNull(t.getValue());
        assertNull(t.getUnit());

        // test constructor with value and unit
        t = new Temperature(1000, TemperatureUnit.CELSIUS);

        // check
        assertEquals(1000, t.getValue());
        assertEquals(TemperatureUnit.CELSIUS, t.getUnit());

        // Force IllegalArgumentException
        t = null;
        try {
            t = new Temperature(null, TemperatureUnit.CELSIUS);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            t = new Temperature(1000, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(t);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Temperature t1 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t3 = new Temperature(value + 1.0,
                TemperatureUnit.CELSIUS);
        final Temperature t4 = new Temperature(value, TemperatureUnit.KELVIN);

        //noinspection EqualsWithItself
        assertEquals(t1, t1);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1, t4);

        assertNotEquals(null, t1);
        assertNotEquals(t1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Temperature t1 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t3 = new Temperature(value + 1.0,
                TemperatureUnit.CELSIUS);
        final Temperature t4 = new Temperature(value, TemperatureUnit.KELVIN);

        assertEquals(t1.hashCode(), t1.hashCode());
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1.hashCode(), t3.hashCode());
        assertNotEquals(t1.hashCode(), t4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Temperature t1 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value, TemperatureUnit.CELSIUS);
        final Temperature t3 = new Temperature(value + 0.5 * ERROR,
                TemperatureUnit.CELSIUS);
        final Temperature t4 = new Temperature(value, TemperatureUnit.FAHRENHEIT);
        final Temperature t5 = new Temperature(
                TemperatureConverter.celsiusToKelvin(value), TemperatureUnit.KELVIN);

        assertTrue(t1.equals(t1, 0.0));
        assertTrue(t1.equals(t2, 0.0));
        assertFalse(t1.equals(t3, 0.0));
        assertTrue(t1.equals(t3, ERROR));
        assertFalse(t1.equals(t4, ERROR));
        assertTrue(t1.equals(t5, ERROR));

        assertFalse(t1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final Temperature t = new Temperature(1, TemperatureUnit.CELSIUS);

        // check
        assertEquals(1, t.getValue());

        // set new value
        t.setValue(2.5);

        // check
        assertEquals(2.5, t.getValue());

        // force IllegalArgumentException
        try {
            t.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Temperature t = new Temperature(1, TemperatureUnit.CELSIUS);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t.getUnit());

        // set new value
        t.setUnit(TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, t.getUnit());

        // force IllegalArgumentException
        try {
            t.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Temperature.add(value1, TemperatureUnit.CELSIUS,
                value2, TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN);

        // check
        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(expected, result, ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final double result2 = Temperature.add(value1, TemperatureUnit.CELSIUS,
                value2b, TemperatureUnit.FAHRENHEIT, TemperatureUnit.KELVIN);

        // check
        assertEquals(expected, result2, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Temperature.add(
                new BigDecimal(value1), TemperatureUnit.CELSIUS,
                new BigDecimal(value2), TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN);

        // check
        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(expected, result.doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Number result2 = Temperature.add(
                new BigDecimal(value1), TemperatureUnit.CELSIUS,
                new BigDecimal(value2b), TemperatureUnit.FAHRENHEIT,
                TemperatureUnit.KELVIN);

        // check
        assertEquals(expected, result2.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = new Temperature(0.0, TemperatureUnit.KELVIN);
        Temperature.add(t1, t2, result);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = new Temperature(0.0, TemperatureUnit.KELVIN);
        Temperature.add(t1, t2b, result2);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = Temperature.addAndReturnNew(
                t1, t2, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = Temperature.addAndReturnNew(
                t1, t2b, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        final Temperature result = t1.addAndReturnNew(value2, TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        final Temperature result = t1.addAndReturnNew(
                new BigDecimal(value2), TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = t1.addAndReturnNew(t2, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = t1.addAndReturnNew(t2b, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.add(value2, TemperatureUnit.CELSIUS);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.add(value2b, TemperatureUnit.FAHRENHEIT);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.add(new BigDecimal(value2), TemperatureUnit.CELSIUS);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.add(new BigDecimal(value2b), TemperatureUnit.FAHRENHEIT);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        t1.add(t2);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2b = new Temperature(value2b, TemperatureUnit.FAHRENHEIT);

        t1.add(t2b);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TemperatureUnit.FAHRENHEIT, t2b.getUnit());
        assertEquals(value2b, t2b.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = new Temperature(
                0.0, TemperatureUnit.KELVIN);
        t1.add(t2, result);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 + value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = new Temperature(
                0.0, TemperatureUnit.KELVIN);
        t1.add(t2b, result2);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Temperature.subtract(value1, TemperatureUnit.CELSIUS,
                value2, TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN);

        // check
        final double expected = TemperatureConverter.celsiusToKelvin(value1 - value2);
        assertEquals(expected, result, ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final double result2 = Temperature.subtract(value1, TemperatureUnit.CELSIUS,
                value2b, TemperatureUnit.FAHRENHEIT, TemperatureUnit.KELVIN);

        // check
        assertEquals(expected, result2, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Temperature.subtract(
                new BigDecimal(value1), TemperatureUnit.CELSIUS,
                new BigDecimal(value2), TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN);

        // check
        final double expected = TemperatureConverter.celsiusToKelvin(value1 - value2);
        assertEquals(expected, result.doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Number result2 = Temperature.subtract(
                new BigDecimal(value1), TemperatureUnit.CELSIUS,
                new BigDecimal(value2b), TemperatureUnit.FAHRENHEIT,
                TemperatureUnit.KELVIN);

        // check
        assertEquals(expected, result2.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = new Temperature(0.0, TemperatureUnit.KELVIN);
        Temperature.subtract(t1, t2, result);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = new Temperature(0.0, TemperatureUnit.KELVIN);
        Temperature.subtract(t1, t2b, result2);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = Temperature.subtractAndReturnNew(
                t1, t2, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = Temperature.subtractAndReturnNew(
                t1, t2b, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        final Temperature result = t1.subtractAndReturnNew(
                value2, TemperatureUnit.CELSIUS, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(
                value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        final Temperature result = t1.subtractAndReturnNew(
                new BigDecimal(value2), TemperatureUnit.CELSIUS,
                TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(
                value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = t1.subtractAndReturnNew(
                t2, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(
                value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = t1.subtractAndReturnNew(
                t2b, TemperatureUnit.KELVIN);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.subtract(value2, TemperatureUnit.CELSIUS);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.subtract(value2b, TemperatureUnit.FAHRENHEIT);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.subtract(new BigDecimal(value2), TemperatureUnit.CELSIUS);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);

        t1.subtract(new BigDecimal(value2b), TemperatureUnit.FAHRENHEIT);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        t1.subtract(t2);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        t1.subtract(t2b);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TemperatureUnit.FAHRENHEIT, t2b.getUnit());
        assertEquals(value2b, t2b.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Temperature t1 = new Temperature(value1, TemperatureUnit.CELSIUS);
        final Temperature t2 = new Temperature(value2, TemperatureUnit.CELSIUS);

        final Temperature result = new Temperature(
                0.0, TemperatureUnit.KELVIN);
        t1.subtract(t2, result);

        // check
        assertEquals(TemperatureUnit.CELSIUS, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TemperatureUnit.CELSIUS, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        final double expected = TemperatureConverter.celsiusToKelvin(
                value1 - value2);
        assertEquals(TemperatureUnit.KELVIN, result.getUnit());
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        // test again for different units
        final double value2b = TemperatureConverter.celsiusToFahrenheit(value2);
        final Temperature t2b = new Temperature(
                value2b, TemperatureUnit.FAHRENHEIT);

        final Temperature result2 = new Temperature(
                0.0, TemperatureUnit.KELVIN);
        t1.subtract(t2b, result2);

        // check
        assertEquals(TemperatureUnit.KELVIN, result2.getUnit());
        assertEquals(expected, result2.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final double value = new Random().nextDouble();
        final Temperature t1 = new Temperature(value, TemperatureUnit.CELSIUS);

        final byte[] bytes = SerializationHelper.serialize(t1);
        final Temperature t2 = SerializationHelper.deserialize(bytes);

        assertEquals(t1, t2);
        assertNotSame(t1, t2);
    }
}
