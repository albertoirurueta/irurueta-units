/*
 * Copyright (C) 2026 Alberto Irurueta Carro (alberto@irurueta.com)
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

class PowerFormatterTest {

    private static final double WATTS_PER_KILOWATT = 1000.0;
    private static final double WATTS_PER_HORSEPOWER = 745.699872;

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new PowerFormatter();

        // check
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getDefault(), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final var locale = new Locale("es", "ES");
        formatter = new PowerFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new PowerFormatter((Locale) null));

        // test copy constructor
        formatter = new PowerFormatter(locale);
        final var formatter2 = new PowerFormatter(formatter);

        // check
        assertEquals(locale, formatter2.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter2.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter2.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter2.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter2.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter2.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter2.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter2.getValueAndUnitFormatPattern());

        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new PowerFormatter((PowerFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new PowerFormatter();
        final var formatter2 = (PowerFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, PowerUnit.WATT, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (PowerFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new PowerFormatter(Locale.ENGLISH);
        final var formatter2 = new PowerFormatter(Locale.ENGLISH);
        final var formatter3 = new PowerFormatter(Locale.FRENCH);

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
        final var formatter1 = new PowerFormatter(Locale.ENGLISH);
        final var formatter2 = new PowerFormatter(Locale.ENGLISH);
        final var formatter3 = new PowerFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        assertEquals("5,5 W", formatter.format(new BigDecimal(value), PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.format(new BigDecimal(value), PowerUnit.KILOWATT));
        assertEquals("5,5 MW", formatter.format(new BigDecimal(value), PowerUnit.MEGAWATT));
        assertEquals("5,5 hp", formatter.format(new BigDecimal(value), PowerUnit.HORSEPOWER));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 W", formatter.format(new BigDecimal(value), PowerUnit.WATT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kW", formatter.format(new BigDecimal(value), PowerUnit.KILOWATT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MW", formatter.format(new BigDecimal(value), PowerUnit.MEGAWATT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hp", formatter.format(new BigDecimal(value), PowerUnit.HORSEPOWER, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        assertEquals("5,5 W", formatter.format(value, PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.format(value, PowerUnit.KILOWATT));
        assertEquals("5,5 MW", formatter.format(value, PowerUnit.MEGAWATT));
        assertEquals("5,5 hp", formatter.format(value, PowerUnit.HORSEPOWER));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 W", formatter.format(value, PowerUnit.WATT, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kW", formatter.format(value, PowerUnit.KILOWATT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MW", formatter.format(value, PowerUnit.MEGAWATT, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hp", formatter.format(value, PowerUnit.HORSEPOWER, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatPower() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        assertEquals("5,5 W", formatter.format(new Power(value, PowerUnit.WATT)));
        assertEquals("5,5 kW", formatter.format(new Power(value, PowerUnit.KILOWATT)));
        assertEquals("5,5 MW", formatter.format(new Power(value, PowerUnit.MEGAWATT)));
        assertEquals("5,5 hp", formatter.format(new Power(value, PowerUnit.HORSEPOWER)));
    }

    @Test
    void testFormatPowerAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 W", formatter.format(new Power(value, PowerUnit.WATT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kW", formatter.format(new Power(value, PowerUnit.KILOWATT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MW", formatter.format(new Power(value, PowerUnit.MEGAWATT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hp", formatter.format(new Power(value, PowerUnit.HORSEPOWER), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT),
                formatter.formatAndConvert(new BigDecimal("5.5"), PowerUnit.WATT));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT),
                formatter.formatAndConvert(new BigDecimal("1.0"), PowerUnit.KILOWATT));
        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT),
                formatter.formatAndConvert(new BigDecimal("5.5"), PowerUnit.HORSEPOWER));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(new BigDecimal("5.5"), PowerUnit.HORSEPOWER));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT),
                formatter.formatAndConvert(5.5, PowerUnit.WATT));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT),
                formatter.formatAndConvert(1.0, PowerUnit.KILOWATT));
        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT),
                formatter.formatAndConvert(5.5, PowerUnit.HORSEPOWER));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(5.5, PowerUnit.HORSEPOWER));
    }

    @Test
    void testFormatAndConvertPower() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT),
                formatter.formatAndConvert(new Power(5.5, PowerUnit.WATT)));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT),
                formatter.formatAndConvert(new Power(1.0, PowerUnit.KILOWATT)));
        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT),
                formatter.formatAndConvert(new Power(5.5, PowerUnit.HORSEPOWER)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(new Power(5.5, PowerUnit.HORSEPOWER)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT), formatter.formatAndConvert(
                new BigDecimal("5.5"), PowerUnit.WATT, UnitSystem.METRIC));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT), formatter.formatAndConvert(
                new BigDecimal("1.0"), PowerUnit.KILOWATT, UnitSystem.METRIC));

        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT), formatter.formatAndConvert(
                new BigDecimal("5.5"), PowerUnit.HORSEPOWER, UnitSystem.METRIC));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                new BigDecimal("5.5"), PowerUnit.HORSEPOWER, UnitSystem.IMPERIAL));

        assertEquals(formatter.format(5.5 / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                new BigDecimal("5.5"), PowerUnit.WATT, UnitSystem.IMPERIAL));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(new BigDecimal("1.0"), PowerUnit.KILOWATT, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT), formatter.formatAndConvert(
                5.5, PowerUnit.WATT, UnitSystem.METRIC));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT), formatter.formatAndConvert(
                1.0, PowerUnit.KILOWATT, UnitSystem.METRIC));

        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT), formatter.formatAndConvert(
                5.5, PowerUnit.HORSEPOWER, UnitSystem.METRIC));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                5.5, PowerUnit.HORSEPOWER, UnitSystem.IMPERIAL));

        assertEquals(formatter.format(5.5 / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                5.5, PowerUnit.WATT, UnitSystem.IMPERIAL));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(1.0, PowerUnit.KILOWATT, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertPowerAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT), formatter.formatAndConvert(
                new Power(5.5, PowerUnit.WATT), UnitSystem.METRIC));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT), formatter.formatAndConvert(
                new Power(1.0, PowerUnit.KILOWATT), UnitSystem.METRIC));

        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT), formatter.formatAndConvert(
                new Power(5.5, PowerUnit.HORSEPOWER), UnitSystem.METRIC));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                new Power(5.5, PowerUnit.HORSEPOWER), UnitSystem.IMPERIAL));

        assertEquals(formatter.format(5.5 / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER), formatter.formatAndConvert(
                new Power(5.5, PowerUnit.WATT), UnitSystem.IMPERIAL));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER),
                formatter.formatAndConvert(new Power(1.0, PowerUnit.KILOWATT), UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.WATT),
                formatter.formatAndConvertMetric(new BigDecimal("5.5"), PowerUnit.WATT));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT, PowerUnit.WATT),
                formatter.formatAndConvertMetric(new BigDecimal("1.0"), PowerUnit.KILOWATT));
        assertEquals(formatter.format(5.5 * WATTS_PER_HORSEPOWER, PowerUnit.WATT),
                formatter.formatAndConvertMetric(new BigDecimal("5.5"), PowerUnit.HORSEPOWER));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("en", "US");

        final var formatter = new PowerFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.format(5.5, PowerUnit.HORSEPOWER),
                formatter.formatAndConvertImperial(new BigDecimal("5.5"), PowerUnit.HORSEPOWER));

        assertEquals(formatter.format(5.5 / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER),
                formatter.formatAndConvertImperial(new BigDecimal("5.5"), PowerUnit.WATT));
        assertEquals(formatter.format(1.0 * WATTS_PER_KILOWATT / WATTS_PER_HORSEPOWER, PowerUnit.HORSEPOWER),
                formatter.formatAndConvertImperial(new BigDecimal("1.0"), PowerUnit.KILOWATT));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = PowerFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new PowerFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new PowerFormatter();

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
        var formatter = new PowerFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new PowerFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new PowerFormatter();

        assertTrue(formatter.isValidUnit("W"));
        assertTrue(formatter.isValidUnit("W "));

        assertTrue(formatter.isValidUnit("kW"));
        assertTrue(formatter.isValidUnit("kW "));

        assertTrue(formatter.isValidUnit("MW"));
        assertTrue(formatter.isValidUnit("MW "));

        assertTrue(formatter.isValidUnit("hp"));
        assertTrue(formatter.isValidUnit("hp "));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kW";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 MW";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 hp";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kW";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 MW";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 hp";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kW";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 MW";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 hp";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kW";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 MW";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 hp";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        var p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.WATT, p.getUnit());

        text = "5,5 kW";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.KILOWATT, p.getUnit());

        text = "5,5 MW";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.MEGAWATT, p.getUnit());

        text = "5,5 hp";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.HORSEPOWER, p.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new PowerFormatter(new Locale("es", "ES"));

        var text = "5,5 W";
        assertEquals(PowerUnit.WATT, formatter.findUnit(text));

        text = "5,5 kW";
        assertEquals(PowerUnit.KILOWATT, formatter.findUnit(text));

        text = "5,5 MW";
        assertEquals(PowerUnit.MEGAWATT, formatter.findUnit(text));

        text = "5,5 hp";
        assertEquals(PowerUnit.HORSEPOWER, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new PowerFormatter();

        assertEquals(PowerFormatter.WATT, formatter.getUnitSymbol(PowerUnit.WATT));
        assertEquals(PowerFormatter.KILOWATT, formatter.getUnitSymbol(PowerUnit.KILOWATT));
        assertEquals(PowerFormatter.MEGAWATT, formatter.getUnitSymbol(PowerUnit.MEGAWATT));
        assertEquals(PowerFormatter.HORSEPOWER, formatter.getUnitSymbol(PowerUnit.HORSEPOWER));
    }
}
