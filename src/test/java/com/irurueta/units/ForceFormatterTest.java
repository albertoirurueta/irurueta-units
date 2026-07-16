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

class ForceFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new ForceFormatter();

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
        formatter = new ForceFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new ForceFormatter((Locale) null));

        // test copy constructor
        formatter = new ForceFormatter(locale);
        final var formatter2 = new ForceFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new ForceFormatter((ForceFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new ForceFormatter();
        final var formatter2 = (ForceFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, ForceUnit.NEWTON, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (ForceFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new ForceFormatter(Locale.ENGLISH);
        final var formatter2 = new ForceFormatter(Locale.ENGLISH);
        final var formatter3 = new ForceFormatter(Locale.FRENCH);

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
        final var formatter1 = new ForceFormatter(Locale.ENGLISH);
        final var formatter2 = new ForceFormatter(Locale.ENGLISH);
        final var formatter3 = new ForceFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        assertEquals("5,5 N", formatter.format(new BigDecimal(value), ForceUnit.NEWTON));
        assertEquals("5,5 kN", formatter.format(new BigDecimal(value), ForceUnit.KILONEWTON));
        assertEquals("5,5 lbf", formatter.format(new BigDecimal(value), ForceUnit.POUND_FORCE));
        assertEquals("5,5 dyn", formatter.format(new BigDecimal(value), ForceUnit.DYNE));
        assertEquals("5,5 kgf", formatter.format(new BigDecimal(value), ForceUnit.KILOGRAM_FORCE));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 N", formatter.format(new BigDecimal(value), ForceUnit.NEWTON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kN", formatter.format(new BigDecimal(value), ForceUnit.KILONEWTON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lbf", formatter.format(new BigDecimal(value), ForceUnit.POUND_FORCE,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dyn", formatter.format(new BigDecimal(value), ForceUnit.DYNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kgf", formatter.format(new BigDecimal(value), ForceUnit.KILOGRAM_FORCE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        assertEquals("5,5 N", formatter.format(value, ForceUnit.NEWTON));
        assertEquals("5,5 kN", formatter.format(value, ForceUnit.KILONEWTON));
        assertEquals("5,5 lbf", formatter.format(value, ForceUnit.POUND_FORCE));
        assertEquals("5,5 dyn", formatter.format(value, ForceUnit.DYNE));
        assertEquals("5,5 kgf", formatter.format(value, ForceUnit.KILOGRAM_FORCE));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 N", formatter.format(value, ForceUnit.NEWTON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kN", formatter.format(value, ForceUnit.KILONEWTON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lbf", formatter.format(value, ForceUnit.POUND_FORCE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dyn", formatter.format(value, ForceUnit.DYNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kgf", formatter.format(value, ForceUnit.KILOGRAM_FORCE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatForce() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        assertEquals("5,5 N", formatter.format(new Force(value, ForceUnit.NEWTON)));
        assertEquals("5,5 kN", formatter.format(new Force(value, ForceUnit.KILONEWTON)));
        assertEquals("5,5 lbf", formatter.format(new Force(value, ForceUnit.POUND_FORCE)));
        assertEquals("5,5 dyn", formatter.format(new Force(value, ForceUnit.DYNE)));
        assertEquals("5,5 kgf", formatter.format(new Force(value, ForceUnit.KILOGRAM_FORCE)));
    }

    @Test
    void testFormatForceAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 N", formatter.format(new Force(value, ForceUnit.NEWTON), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kN", formatter.format(new Force(value, ForceUnit.KILONEWTON), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lbf", formatter.format(new Force(value, ForceUnit.POUND_FORCE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dyn", formatter.format(new Force(value, ForceUnit.DYNE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kgf", formatter.format(new Force(value, ForceUnit.KILOGRAM_FORCE), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(new BigDecimal("0.0005"), ForceUnit.NEWTON));
        assertEquals("5 N", formatter.formatAndConvert(new BigDecimal("5.0"), ForceUnit.NEWTON));
        assertEquals("10 kgf", formatter.formatAndConvert(new BigDecimal("98.0665"), ForceUnit.NEWTON));
        assertEquals("5 kN", formatter.formatAndConvert(new BigDecimal("5000.0"), ForceUnit.NEWTON));

        assertEquals("4,45 N", formatter.formatAndConvert(new BigDecimal("1.0"), ForceUnit.POUND_FORCE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("2.25 lbf", formatter.formatAndConvert(new BigDecimal("10.0"), ForceUnit.NEWTON));
        assertEquals("65 lbf", formatter.formatAndConvert(new BigDecimal("65.0"), ForceUnit.POUND_FORCE));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(0.0005, ForceUnit.NEWTON));
        assertEquals("5 N", formatter.formatAndConvert(5.0, ForceUnit.NEWTON));
        assertEquals("10 kgf", formatter.formatAndConvert(98.0665, ForceUnit.NEWTON));
        assertEquals("5 kN", formatter.formatAndConvert(5000.0, ForceUnit.NEWTON));

        assertEquals("4,45 N", formatter.formatAndConvert(1.0, ForceUnit.POUND_FORCE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("2.25 lbf", formatter.formatAndConvert(10.0, ForceUnit.NEWTON));
        assertEquals("65 lbf", formatter.formatAndConvert(65.0, ForceUnit.POUND_FORCE));
    }

    @Test
    void testFormatAndConvertForce() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(new Force(0.0005, ForceUnit.NEWTON)));
        assertEquals("5 N", formatter.formatAndConvert(new Force(5.0, ForceUnit.NEWTON)));
        assertEquals("10 kgf", formatter.formatAndConvert(new Force(98.0665, ForceUnit.NEWTON)));
        assertEquals("5 kN", formatter.formatAndConvert(new Force(5000.0, ForceUnit.NEWTON)));

        assertEquals("4,45 N", formatter.formatAndConvert(new Force(1.0, ForceUnit.POUND_FORCE)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("2.25 lbf", formatter.formatAndConvert(new Force(10.0, ForceUnit.NEWTON)));
        assertEquals("65 lbf", formatter.formatAndConvert(new Force(65.0, ForceUnit.POUND_FORCE)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(new BigDecimal("0.0005"),
                ForceUnit.NEWTON, UnitSystem.METRIC));
        assertEquals("5 N", formatter.formatAndConvert(new BigDecimal("5.0"),
                ForceUnit.NEWTON, UnitSystem.METRIC));
        assertEquals("10 kgf", formatter.formatAndConvert(new BigDecimal("98.0665"),
                ForceUnit.NEWTON, UnitSystem.METRIC));
        assertEquals("5 kN", formatter.formatAndConvert(new BigDecimal("5000.0"),
                ForceUnit.NEWTON, UnitSystem.METRIC));

        assertEquals("4,45 N", formatter.formatAndConvert(new BigDecimal("1.0"),
                ForceUnit.POUND_FORCE, UnitSystem.METRIC));

        assertEquals("2,25 lbf", formatter.formatAndConvert(new BigDecimal("10.0"),
                ForceUnit.NEWTON, UnitSystem.IMPERIAL));
        assertEquals("65 lbf", formatter.formatAndConvert(new BigDecimal("65.0"),
                ForceUnit.POUND_FORCE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(0.0005, ForceUnit.NEWTON,
                UnitSystem.METRIC));
        assertEquals("5 N", formatter.formatAndConvert(5.0, ForceUnit.NEWTON,
                UnitSystem.METRIC));
        assertEquals("10 kgf", formatter.formatAndConvert(98.0665, ForceUnit.NEWTON,
                UnitSystem.METRIC));
        assertEquals("5 kN", formatter.formatAndConvert(5000.0, ForceUnit.NEWTON,
                UnitSystem.METRIC));

        assertEquals("4,45 N", formatter.formatAndConvert(1.0, ForceUnit.POUND_FORCE,
                UnitSystem.METRIC));

        assertEquals("2,25 lbf", formatter.formatAndConvert(10.0, ForceUnit.NEWTON,
                UnitSystem.IMPERIAL));
        assertEquals("65 lbf", formatter.formatAndConvert(65.0, ForceUnit.POUND_FORCE,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertForceAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvert(new Force(0.0005, ForceUnit.NEWTON),
                UnitSystem.METRIC));
        assertEquals("5 N", formatter.formatAndConvert(new Force(5.0,
                ForceUnit.NEWTON), UnitSystem.METRIC));
        assertEquals("10 kgf", formatter.formatAndConvert(new Force(98.0665,
                ForceUnit.NEWTON), UnitSystem.METRIC));
        assertEquals("5 kN", formatter.formatAndConvert(new Force(5000.0,
                ForceUnit.NEWTON), UnitSystem.METRIC));

        assertEquals("4,45 N", formatter.formatAndConvert(new Force(1.0, ForceUnit.POUND_FORCE),
                UnitSystem.METRIC));

        assertEquals("2,25 lbf", formatter.formatAndConvert(new Force(10.0, ForceUnit.NEWTON),
                UnitSystem.IMPERIAL));
        assertEquals("65 lbf", formatter.formatAndConvert(new Force(65.0, ForceUnit.POUND_FORCE),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("50 dyn", formatter.formatAndConvertMetric(new BigDecimal("0.0005"),
                ForceUnit.NEWTON));
        assertEquals("5 N", formatter.formatAndConvertMetric(new BigDecimal("5.0"),
                ForceUnit.NEWTON));
        assertEquals("10 kgf", formatter.formatAndConvertMetric(new BigDecimal("98.0665"),
                ForceUnit.NEWTON));
        assertEquals("5 kN", formatter.formatAndConvertMetric(new BigDecimal("5000.0"),
                ForceUnit.NEWTON));

        assertEquals("4,45 N", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                ForceUnit.POUND_FORCE));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("es", "ES");

        final var formatter = new ForceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("2,25 lbf", formatter.formatAndConvertImperial(new BigDecimal("10.0"),
                ForceUnit.NEWTON));
        assertEquals("65 lbf", formatter.formatAndConvertImperial(new BigDecimal("65.0"),
                ForceUnit.POUND_FORCE));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = ForceFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new ForceFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new ForceFormatter();

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
        var formatter = new ForceFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new ForceFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new ForceFormatter();

        assertTrue(formatter.isValidUnit("N"));
        assertTrue(formatter.isValidUnit("N "));

        assertTrue(formatter.isValidUnit("kN"));
        assertTrue(formatter.isValidUnit("kN "));

        assertTrue(formatter.isValidUnit("lbf"));
        assertTrue(formatter.isValidUnit("lbf "));

        assertTrue(formatter.isValidUnit("dyn"));
        assertTrue(formatter.isValidUnit("dyn "));

        assertTrue(formatter.isValidUnit("kgf"));
        assertTrue(formatter.isValidUnit("kgf "));
    }

    @Test
    void testIsValidMeasurement() {
        final ForceFormatter formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kN";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 lbf";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 dyn";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kgf";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kN";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 dyn";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kgf";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 lbf";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kN";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 dyn";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kgf";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 lbf";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kN";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 dyn";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kgf";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 lbf";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        var f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, f.getUnit());

        text = "5,5 kN";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, f.getUnit());

        text = "5,5 lbf";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.POUND_FORCE, f.getUnit());

        text = "5,5 dyn";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.DYNE, f.getUnit());

        text = "5,5 kgf";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILOGRAM_FORCE, f.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new ForceFormatter(new Locale("es", "ES"));

        var text = "5,5 N";
        assertEquals(ForceUnit.NEWTON, formatter.findUnit(text));

        text = "5,5 kN";
        assertEquals(ForceUnit.KILONEWTON, formatter.findUnit(text));

        text = "5,5 lbf";
        assertEquals(ForceUnit.POUND_FORCE, formatter.findUnit(text));

        text = "5,5 dyn";
        assertEquals(ForceUnit.DYNE, formatter.findUnit(text));

        text = "5,5 kgf";
        assertEquals(ForceUnit.KILOGRAM_FORCE, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new ForceFormatter();

        assertEquals(ForceFormatter.NEWTON, formatter.getUnitSymbol(ForceUnit.NEWTON));
        assertEquals(ForceFormatter.KILONEWTON, formatter.getUnitSymbol(ForceUnit.KILONEWTON));
        assertEquals(ForceFormatter.POUND_FORCE, formatter.getUnitSymbol(ForceUnit.POUND_FORCE));
        assertEquals(ForceFormatter.DYNE, formatter.getUnitSymbol(ForceUnit.DYNE));
        assertEquals(ForceFormatter.KILOGRAM_FORCE, formatter.getUnitSymbol(ForceUnit.KILOGRAM_FORCE));
    }
}
