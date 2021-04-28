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
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public class TemperatureFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        TemperatureFormatter formatter = new TemperatureFormatter();

        // check
        assertEquals(formatter.getLocale(), Locale.getDefault());
        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());
        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());
        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());
        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());
        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());
        assertEquals(formatter.getUnitSystem(),
                UnitLocale.getFrom(Locale.getDefault()));
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new TemperatureFormatter(locale);

        // check
        assertEquals(formatter.getLocale(), locale);
        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance(locale).getMaximumFractionDigits());
        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance(locale).getMaximumIntegerDigits());
        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance(locale).getMinimumFractionDigits());
        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance(locale).getMinimumIntegerDigits());
        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance(locale).getRoundingMode());
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);


        // force IllegalArgumentException
        formatter = null;
        try {
            formatter = new TemperatureFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new TemperatureFormatter(locale);
        final TemperatureFormatter formatter2 = new TemperatureFormatter(formatter);

        // check
        assertEquals(formatter2.getLocale(), locale);
        assertEquals(formatter2.getMaximumFractionDigits(),
                NumberFormat.getInstance(locale).getMaximumFractionDigits());
        assertEquals(formatter2.getMaximumIntegerDigits(),
                NumberFormat.getInstance(locale).getMaximumIntegerDigits());
        assertEquals(formatter2.getMinimumFractionDigits(),
                NumberFormat.getInstance(locale).getMinimumFractionDigits());
        assertEquals(formatter2.getMinimumIntegerDigits(),
                NumberFormat.getInstance(locale).getMinimumIntegerDigits());
        assertEquals(formatter2.getRoundingMode(),
                NumberFormat.getInstance(locale).getRoundingMode());
        assertEquals(formatter2.getUnitSystem(), UnitSystem.METRIC);
        assertEquals(formatter2.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter2.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter2.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        formatter = null;
        try {
            //noinspection ConstantConditions
            formatter = new TemperatureFormatter((TemperatureFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final TemperatureFormatter formatter1 = new TemperatureFormatter();
        final TemperatureFormatter formatter2 = (TemperatureFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, TemperatureUnit.CELSIUS,
                new StringBuffer(), new FieldPosition(0)));
        final TemperatureFormatter formatter3 = (TemperatureFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final TemperatureFormatter formatter1 = new TemperatureFormatter(
                Locale.ENGLISH);
        final TemperatureFormatter formatter2 = new TemperatureFormatter(
                Locale.ENGLISH);
        final TemperatureFormatter formatter3 = new TemperatureFormatter(
                Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final TemperatureFormatter formatter1 = new TemperatureFormatter(
                Locale.ENGLISH);
        final TemperatureFormatter formatter2 = new TemperatureFormatter(
                Locale.ENGLISH);
        final TemperatureFormatter formatter3 = new TemperatureFormatter(
                Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.CELSIUS), "5,5 ºC");
        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.FAHRENHEIT), "5,5 ºF");
        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.KELVIN), "5,5 K");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.CELSIUS, buffer,
                new FieldPosition(0)).toString(), "5,5 ºC");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.FAHRENHEIT, buffer,
                new FieldPosition(0)).toString(), "5,5 ºF");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                TemperatureUnit.KELVIN, buffer,
                new FieldPosition(0)).toString(), "5,5 K");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS),
                "5,5 ºC");
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT),
                "5,5 ºF");
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN),
                "5,5 K");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS, buffer,
                new FieldPosition(0)).toString(), "5,5 ºC");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT, buffer,
                new FieldPosition(0)).toString(), "5,5 ºF");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN, buffer,
                new FieldPosition(0)).toString(), "5,5 K");
    }

    @Test
    public void testFormatTemperature() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.KELVIN)),
                "5,5 K");
        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.FAHRENHEIT)),
                "5,5 ºF");
        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.CELSIUS)),
                "5,5 ºC");
    }

    @Test
    public void testFormatTemperatureAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.KELVIN),
                buffer, new FieldPosition(0)).toString(), "5,5 K");

        buffer = new StringBuffer();
        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.FAHRENHEIT),
                buffer, new FieldPosition(0)).toString(), "5,5 ºF");

        buffer = new StringBuffer();
        assertEquals(formatter.format(
                new Temperature(value, TemperatureUnit.CELSIUS),
                buffer, new FieldPosition(0)).toString(), "5,5 ºC");
    }

    @Test
    public void testFormatAndConvertNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.CELSIUS),
                formatter.format(value, TemperatureUnit.CELSIUS));
        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.FAHRENHEIT),
                formatter.format(value, TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.KELVIN),
                formatter.format(value, TemperatureUnit.KELVIN));
    }

    @Test
    public void testFormatAndConvertDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.CELSIUS),
                formatter.format(value, TemperatureUnit.CELSIUS));
        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.FAHRENHEIT),
                formatter.format(value, TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.KELVIN),
                formatter.format(value, TemperatureUnit.KELVIN));
    }

    @Test
    public void testFormatAndConvertTemperature() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.KELVIN)),
                "5,5 K");
        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.FAHRENHEIT)),
                "5,5 ºF");
        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.CELSIUS)),
                "5,5 ºC");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.CELSIUS, UnitSystem.METRIC),
                formatter.format(value, TemperatureUnit.CELSIUS));
        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.FAHRENHEIT, UnitSystem.IMPERIAL),
                formatter.format(value, TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.KELVIN, UnitSystem.METRIC),
                formatter.format(value, TemperatureUnit.KELVIN));
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.CELSIUS, UnitSystem.METRIC),
                formatter.format(value, TemperatureUnit.CELSIUS));
        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.FAHRENHEIT, UnitSystem.IMPERIAL),
                formatter.format(value, TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.formatAndConvert(
                value, TemperatureUnit.KELVIN, UnitSystem.METRIC),
                formatter.format(value, TemperatureUnit.KELVIN));
    }

    @Test
    public void testFormatAndConvertTemperatureAndUnitSystem() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final TemperatureFormatter formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.KELVIN), UnitSystem.METRIC),
                "5,5 K");
        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.FAHRENHEIT), UnitSystem.IMPERIAL),
                "5,5 ºF");
        assertEquals(formatter.formatAndConvert(
                new Temperature(value, TemperatureUnit.CELSIUS), UnitSystem.METRIC),
                "5,5 ºC");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = TemperatureFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(),
                !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    public void testIsSetParseIntegerOnly() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(),
                !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    public void testGetSetValueAndUnitFormatPattern() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        // new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        // check correctness
        assertEquals(formatter.getValueAndUnitFormatPattern(), "{0}{1}");

        // force IllegalArgumentException
        try {
            formatter.setValueAndUnitFormatPattern(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetUnitSystem() {
        TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new TemperatureFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertTrue(formatter.isValidUnit("ºC"));
        assertTrue(formatter.isValidUnit("ºC "));

        assertTrue(formatter.isValidUnit("ºF"));
        assertTrue(formatter.isValidUnit("ºF "));

        assertTrue(formatter.isValidUnit("K"));
        assertTrue(formatter.isValidUnit("K "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ºF";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 K";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 K";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ºF";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 K";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ºF";
        assertTrue(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 K";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 ºF";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        Temperature t = formatter.parse(text);
        assertEquals(t.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(t.getUnit(), TemperatureUnit.CELSIUS);

        text = "5,5 ºF";
        t = formatter.parse(text);
        assertEquals(t.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(t.getUnit(), TemperatureUnit.FAHRENHEIT);

        text = "5,5 K";
        t = formatter.parse(text);
        assertEquals(t.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(t.getUnit(), TemperatureUnit.KELVIN);

        // Force UnknownUnitException
        text = "5,5 s";
        try {
            formatter.parse(text);
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }

        // Force ParseException
        try {
            formatter.parse("m");
            fail("ParseException expected but not thrown");
        } catch (final ParseException ignore) {
        }
    }

    @Test
    public void testFindUnit() {
        final TemperatureFormatter formatter = new TemperatureFormatter(
                new Locale("es", "ES"));

        String text = "5,5 ºC";
        assertEquals(formatter.findUnit(text), TemperatureUnit.CELSIUS);

        text = "5,5 ºF";
        assertEquals(formatter.findUnit(text), TemperatureUnit.FAHRENHEIT);

        text = "5,5 K";
        assertEquals(formatter.findUnit(text), TemperatureUnit.KELVIN);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final TemperatureFormatter formatter = new TemperatureFormatter();

        assertEquals(formatter.getUnitSymbol(TemperatureUnit.CELSIUS),
                TemperatureFormatter.CELSIUS);
        assertEquals(formatter.getUnitSymbol(TemperatureUnit.FAHRENHEIT),
                TemperatureFormatter.FAHRENHEIT);
        assertEquals(formatter.getUnitSymbol(TemperatureUnit.KELVIN),
                TemperatureFormatter.KELVIN);
    }
}
