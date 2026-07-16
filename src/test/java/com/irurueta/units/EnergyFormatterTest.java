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

class EnergyFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new EnergyFormatter();

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
        formatter = new EnergyFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new EnergyFormatter((Locale) null));

        // test copy constructor
        formatter = new EnergyFormatter(locale);
        final var formatter2 = new EnergyFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new EnergyFormatter((EnergyFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new EnergyFormatter();
        final var formatter2 = (EnergyFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, EnergyUnit.JOULE, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (EnergyFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter2 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter3 = new EnergyFormatter(Locale.FRENCH);

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
        final var formatter1 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter2 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter3 = new EnergyFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(new BigDecimal(value), EnergyUnit.JOULE));
        assertEquals("5,5 kJ", formatter.format(new BigDecimal(value), EnergyUnit.KILOJOULE));
        assertEquals("5,5 cal", formatter.format(new BigDecimal(value), EnergyUnit.CALORIE));
        assertEquals("5,5 kcal", formatter.format(new BigDecimal(value), EnergyUnit.KILOCALORIE));
        assertEquals("5,5 kWh", formatter.format(new BigDecimal(value), EnergyUnit.KILOWATT_HOUR));
        assertEquals("5,5 BTU", formatter.format(new BigDecimal(value), EnergyUnit.BTU));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(new BigDecimal(value), EnergyUnit.JOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(new BigDecimal(value), EnergyUnit.KILOJOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(new BigDecimal(value), EnergyUnit.CALORIE,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kcal", formatter.format(new BigDecimal(value), EnergyUnit.KILOCALORIE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(new BigDecimal(value), EnergyUnit.KILOWATT_HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(new BigDecimal(value), EnergyUnit.BTU, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(value, EnergyUnit.JOULE));
        assertEquals("5,5 kJ", formatter.format(value, EnergyUnit.KILOJOULE));
        assertEquals("5,5 cal", formatter.format(value, EnergyUnit.CALORIE));
        assertEquals("5,5 kcal", formatter.format(value, EnergyUnit.KILOCALORIE));
        assertEquals("5,5 kWh", formatter.format(value, EnergyUnit.KILOWATT_HOUR));
        assertEquals("5,5 BTU", formatter.format(value, EnergyUnit.BTU));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(value, EnergyUnit.JOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(value, EnergyUnit.KILOJOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(value, EnergyUnit.CALORIE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kcal", formatter.format(value, EnergyUnit.KILOCALORIE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(value, EnergyUnit.KILOWATT_HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(value, EnergyUnit.BTU, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatEnergy() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(new Energy(value, EnergyUnit.JOULE)));
        assertEquals("5,5 kJ", formatter.format(new Energy(value, EnergyUnit.KILOJOULE)));
        assertEquals("5,5 cal", formatter.format(new Energy(value, EnergyUnit.CALORIE)));
        assertEquals("5,5 kcal", formatter.format(new Energy(value, EnergyUnit.KILOCALORIE)));
        assertEquals("5,5 kWh", formatter.format(new Energy(value, EnergyUnit.KILOWATT_HOUR)));
        assertEquals("5,5 BTU", formatter.format(new Energy(value, EnergyUnit.BTU)));
    }

    @Test
    void testFormatEnergyAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(new Energy(value, EnergyUnit.JOULE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(new Energy(value, EnergyUnit.KILOJOULE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(new Energy(value, EnergyUnit.CALORIE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kcal", formatter.format(new Energy(value, EnergyUnit.KILOCALORIE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(new Energy(value, EnergyUnit.KILOWATT_HOUR), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(new Energy(value, EnergyUnit.BTU), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new BigDecimal("0.1"), EnergyUnit.JOULE));
        assertEquals("2 cal", formatter.formatAndConvert(new BigDecimal("8.368"), EnergyUnit.JOULE));
        assertEquals("4 kJ", formatter.formatAndConvert(new BigDecimal("4000"), EnergyUnit.JOULE));
        assertEquals("2 kcal", formatter.formatAndConvert(new BigDecimal("8368"), EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvert(new BigDecimal("7200000"), EnergyUnit.JOULE));

        assertEquals("2,09 kJ", formatter.formatAndConvert(new BigDecimal("500"), EnergyUnit.CALORIE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.5 BTU", formatter.formatAndConvert(new BigDecimal("0.5"), EnergyUnit.BTU));
        assertEquals("1 BTU", formatter.formatAndConvert(new BigDecimal("1055.05585262"), EnergyUnit.JOULE));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(0.1, EnergyUnit.JOULE));
        assertEquals("2 cal", formatter.formatAndConvert(8.368, EnergyUnit.JOULE));
        assertEquals("4 kJ", formatter.formatAndConvert(4000.0, EnergyUnit.JOULE));
        assertEquals("2 kcal", formatter.formatAndConvert(8368.0, EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvert(7200000.0, EnergyUnit.JOULE));

        assertEquals("2,09 kJ", formatter.formatAndConvert(500.0, EnergyUnit.CALORIE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.5 BTU", formatter.formatAndConvert(0.5, EnergyUnit.BTU));
        assertEquals("1 BTU", formatter.formatAndConvert(1055.05585262, EnergyUnit.JOULE));
    }

    @Test
    void testFormatAndConvertEnergy() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new Energy(0.1, EnergyUnit.JOULE)));
        assertEquals("2 cal", formatter.formatAndConvert(new Energy(8.368, EnergyUnit.JOULE)));
        assertEquals("4 kJ", formatter.formatAndConvert(new Energy(4000.0, EnergyUnit.JOULE)));
        assertEquals("2 kcal", formatter.formatAndConvert(new Energy(8368.0, EnergyUnit.JOULE)));
        assertEquals("2 kWh", formatter.formatAndConvert(new Energy(7200000.0, EnergyUnit.JOULE)));

        assertEquals("2,09 kJ", formatter.formatAndConvert(new Energy(500.0, EnergyUnit.CALORIE)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.5 BTU", formatter.formatAndConvert(new Energy(0.5, EnergyUnit.BTU)));
        assertEquals("1 BTU", formatter.formatAndConvert(new Energy(1055.05585262, EnergyUnit.JOULE)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new BigDecimal("0.1"),
                EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("2 cal", formatter.formatAndConvert(new BigDecimal("8.368"),
                EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("4 kJ", formatter.formatAndConvert(new BigDecimal("4000"),
                EnergyUnit.JOULE, UnitSystem.METRIC));

        assertEquals("0,5 BTU", formatter.formatAndConvert(new BigDecimal("0.5"),
                EnergyUnit.BTU, UnitSystem.IMPERIAL));
        assertEquals("1 BTU", formatter.formatAndConvert(new BigDecimal("1055.05585262"),
                EnergyUnit.JOULE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(0.1, EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("2 cal", formatter.formatAndConvert(8.368, EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("4 kJ", formatter.formatAndConvert(4000.0, EnergyUnit.JOULE, UnitSystem.METRIC));

        assertEquals("0,5 BTU", formatter.formatAndConvert(0.5, EnergyUnit.BTU, UnitSystem.IMPERIAL));
        assertEquals("1 BTU", formatter.formatAndConvert(1055.05585262, EnergyUnit.JOULE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertEnergyAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new Energy(0.1, EnergyUnit.JOULE),
                UnitSystem.METRIC));
        assertEquals("2 cal", formatter.formatAndConvert(new Energy(8.368,
                EnergyUnit.JOULE), UnitSystem.METRIC));
        assertEquals("4 kJ", formatter.formatAndConvert(new Energy(4000.0,
                EnergyUnit.JOULE), UnitSystem.METRIC));

        assertEquals("0,5 BTU", formatter.formatAndConvert(new Energy(0.5, EnergyUnit.BTU),
                UnitSystem.IMPERIAL));
        assertEquals("1 BTU", formatter.formatAndConvert(new Energy(1055.05585262, EnergyUnit.JOULE),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvertMetric(new BigDecimal("0.1"),
                EnergyUnit.JOULE));
        assertEquals("2 cal", formatter.formatAndConvertMetric(new BigDecimal("8.368"),
                EnergyUnit.JOULE));
        assertEquals("4 kJ", formatter.formatAndConvertMetric(new BigDecimal("4000"),
                EnergyUnit.JOULE));
        assertEquals("2 kcal", formatter.formatAndConvertMetric(new BigDecimal("8368"),
                EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvertMetric(new BigDecimal("7200000"),
                EnergyUnit.JOULE));

        assertEquals("2,09 kJ", formatter.formatAndConvertMetric(new BigDecimal("500"),
                EnergyUnit.CALORIE));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("en", "US");

        final var formatter = new EnergyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.5 BTU", formatter.formatAndConvertImperial(new BigDecimal("0.5"),
                EnergyUnit.BTU));
        assertEquals("1 BTU", formatter.formatAndConvertImperial(new BigDecimal("1055.05585262"),
                EnergyUnit.JOULE));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = EnergyFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new EnergyFormatter();

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
        var formatter = new EnergyFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new EnergyFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new EnergyFormatter();

        assertTrue(formatter.isValidUnit("J"));
        assertTrue(formatter.isValidUnit("J "));

        assertTrue(formatter.isValidUnit("kJ"));
        assertTrue(formatter.isValidUnit("kJ "));

        assertTrue(formatter.isValidUnit("cal"));
        assertTrue(formatter.isValidUnit("cal "));

        assertTrue(formatter.isValidUnit("kcal"));
        assertTrue(formatter.isValidUnit("kcal "));

        assertTrue(formatter.isValidUnit("kWh"));
        assertTrue(formatter.isValidUnit("kWh "));

        assertTrue(formatter.isValidUnit("BTU"));
        assertTrue(formatter.isValidUnit("BTU "));
    }

    @Test
    void testIsValidMeasurement() {
        final EnergyFormatter formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kJ";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 cal";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kcal";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kWh";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 BTU";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kJ";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 cal";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kcal";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kWh";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 BTU";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kJ";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 cal";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kcal";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kWh";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 BTU";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kJ";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 cal";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kcal";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kWh";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 BTU";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        var e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.JOULE, e.getUnit());

        text = "5,5 kJ";
        e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOJOULE, e.getUnit());

        text = "5,5 cal";
        e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.CALORIE, e.getUnit());

        text = "5,5 kcal";
        e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOCALORIE, e.getUnit());

        text = "5,5 kWh";
        e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOWATT_HOUR, e.getUnit());

        text = "5,5 BTU";
        e = formatter.parse(text);
        assertEquals(5.5, e.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.BTU, e.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertEquals(EnergyUnit.JOULE, formatter.findUnit(text));

        text = "5,5 kJ";
        assertEquals(EnergyUnit.KILOJOULE, formatter.findUnit(text));

        text = "5,5 cal";
        assertEquals(EnergyUnit.CALORIE, formatter.findUnit(text));

        text = "5,5 kcal";
        assertEquals(EnergyUnit.KILOCALORIE, formatter.findUnit(text));

        text = "5,5 kWh";
        assertEquals(EnergyUnit.KILOWATT_HOUR, formatter.findUnit(text));

        text = "5,5 BTU";
        assertEquals(EnergyUnit.BTU, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new EnergyFormatter();

        assertEquals(EnergyFormatter.JOULE, formatter.getUnitSymbol(EnergyUnit.JOULE));
        assertEquals(EnergyFormatter.KILOJOULE, formatter.getUnitSymbol(EnergyUnit.KILOJOULE));
        assertEquals(EnergyFormatter.CALORIE, formatter.getUnitSymbol(EnergyUnit.CALORIE));
        assertEquals(EnergyFormatter.KILOCALORIE, formatter.getUnitSymbol(EnergyUnit.KILOCALORIE));
        assertEquals(EnergyFormatter.KILOWATT_HOUR, formatter.getUnitSymbol(EnergyUnit.KILOWATT_HOUR));
        assertEquals(EnergyFormatter.BTU, formatter.getUnitSymbol(EnergyUnit.BTU));
    }
}
