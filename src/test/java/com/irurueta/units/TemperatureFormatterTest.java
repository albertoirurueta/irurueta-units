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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new TemperatureFormatter();

        // check
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(Locale.getDefault()), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final var locale = new Locale("es", "ES");
        formatter = new TemperatureFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new TemperatureFormatter((Locale) null));

        // test copy constructor
        formatter = new TemperatureFormatter(locale);
        final var formatter2 = new TemperatureFormatter(formatter);

        // check
        assertEquals(locale, formatter2.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter2.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter2.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter2.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter2.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter2.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter2.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter2.getValueAndUnitFormatPattern());

        // Force NullPointerException
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new TemperatureFormatter((TemperatureFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new TemperatureFormatter();
        final var formatter2 = (TemperatureFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, TemperatureUnit.CELSIUS, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (TemperatureFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new TemperatureFormatter(Locale.ENGLISH);
        final var formatter2 = new TemperatureFormatter(Locale.ENGLISH);
        final var formatter3 = new TemperatureFormatter(Locale.FRENCH);

        // check
        //noinspection EqualsWithItself
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    void testHashCode() {
        final var formatter1 = new TemperatureFormatter(Locale.ENGLISH);
        final var formatter2 = new TemperatureFormatter(Locale.ENGLISH);
        final var formatter3 = new TemperatureFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        assertEquals("5,5 ºC", formatter.format(new BigDecimal(value), TemperatureUnit.CELSIUS));
        assertEquals("5,5 ºF", formatter.format(new BigDecimal(value), TemperatureUnit.FAHRENHEIT));
        assertEquals("5,5 K", formatter.format(new BigDecimal(value), TemperatureUnit.KELVIN));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 ºC", formatter.format(new BigDecimal(value), TemperatureUnit.CELSIUS, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ºF", formatter.format(new BigDecimal(value), TemperatureUnit.FAHRENHEIT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 K", formatter.format(new BigDecimal(value), TemperatureUnit.KELVIN, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        assertEquals("5,5 ºC", formatter.format(value, TemperatureUnit.CELSIUS));
        assertEquals("5,5 ºF", formatter.format(value, TemperatureUnit.FAHRENHEIT));
        assertEquals("5,5 K", formatter.format(value, TemperatureUnit.KELVIN));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 ºC", formatter.format(value, TemperatureUnit.CELSIUS, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ºF", formatter.format(value, TemperatureUnit.FAHRENHEIT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 K", formatter.format(value, TemperatureUnit.KELVIN, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatTemperature() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        assertEquals("5,5 K", formatter.format(new Temperature(value, TemperatureUnit.KELVIN)));
        assertEquals("5,5 ºF", formatter.format(new Temperature(value, TemperatureUnit.FAHRENHEIT)));
        assertEquals("5,5 ºC", formatter.format(new Temperature(value, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testFormatTemperatureAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 K", formatter.format(new Temperature(value, TemperatureUnit.KELVIN), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ºF", formatter.format(new Temperature(value, TemperatureUnit.FAHRENHEIT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ºC", formatter.format(new Temperature(value, TemperatureUnit.CELSIUS), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS), formatter.formatAndConvert(new BigDecimal(value),
                TemperatureUnit.CELSIUS));
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT), formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN), formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.KELVIN));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS), formatter.formatAndConvert(value,
                TemperatureUnit.CELSIUS));
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT), formatter.formatAndConvert(value,
                TemperatureUnit.FAHRENHEIT));
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN), formatter.formatAndConvert(value,
                TemperatureUnit.KELVIN));
    }

    @Test
    void testFormatAndConvertTemperature() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 K", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.KELVIN)));
        assertEquals("5,5 ºF", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.FAHRENHEIT)));
        assertEquals("5,5 ºC", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.CELSIUS)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS), formatter.formatAndConvert(new BigDecimal(value),
                TemperatureUnit.CELSIUS, UnitSystem.METRIC));
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT), formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.FAHRENHEIT, UnitSystem.IMPERIAL));
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN), formatter.formatAndConvert(
                new BigDecimal(value), TemperatureUnit.KELVIN, UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(value, TemperatureUnit.CELSIUS), formatter.formatAndConvert(value,
                TemperatureUnit.CELSIUS, UnitSystem.METRIC));
        assertEquals(formatter.format(value, TemperatureUnit.FAHRENHEIT), formatter.formatAndConvert(value,
                TemperatureUnit.FAHRENHEIT, UnitSystem.IMPERIAL));
        assertEquals(formatter.format(value, TemperatureUnit.KELVIN), formatter.formatAndConvert(value,
                TemperatureUnit.KELVIN, UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertTemperatureAndUnitSystem() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new TemperatureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 K", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.KELVIN),
                UnitSystem.METRIC));
        assertEquals("5,5 ºF", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.FAHRENHEIT),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 ºC", formatter.formatAndConvert(new Temperature(value, TemperatureUnit.CELSIUS),
                UnitSystem.METRIC));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = TemperatureFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new TemperatureFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new TemperatureFormatter();

        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        // check correctness
        assertEquals("{0}{1}", formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> formatter.setValueAndUnitFormatPattern(null));
    }

    @Test
    void testGetUnitSystem() {
        var formatter = new TemperatureFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new TemperatureFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new TemperatureFormatter();

        assertTrue(formatter.isValidUnit("ºC"));
        assertTrue(formatter.isValidUnit("ºC "));

        assertTrue(formatter.isValidUnit("ºF"));
        assertTrue(formatter.isValidUnit("ºF "));

        assertTrue(formatter.isValidUnit("K"));
        assertTrue(formatter.isValidUnit("K "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ºF";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 K";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 K";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ºF";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 K";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ºF";
        assertTrue(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 K";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ºF";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        var t = formatter.parse(text);
        assertEquals(5.5, t.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.CELSIUS, t.getUnit());

        text = "5,5 ºF";
        t = formatter.parse(text);
        assertEquals(5.5, t.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.FAHRENHEIT, t.getUnit());

        text = "5,5 K";
        t = formatter.parse(text);
        assertEquals(5.5, t.getValue().doubleValue(), 0.0);
        assertEquals(TemperatureUnit.KELVIN, t.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new TemperatureFormatter(new Locale("es", "ES"));

        var text = "5,5 ºC";
        assertEquals(TemperatureUnit.CELSIUS, formatter.findUnit(text));

        text = "5,5 ºF";
        assertEquals(TemperatureUnit.FAHRENHEIT, formatter.findUnit(text));

        text = "5,5 K";
        assertEquals(TemperatureUnit.KELVIN, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new TemperatureFormatter();

        assertEquals(TemperatureFormatter.CELSIUS, formatter.getUnitSymbol(TemperatureUnit.CELSIUS));
        assertEquals(TemperatureFormatter.FAHRENHEIT, formatter.getUnitSymbol(TemperatureUnit.FAHRENHEIT));
        assertEquals(TemperatureFormatter.KELVIN, formatter.getUnitSymbol(TemperatureUnit.KELVIN));
    }
}
