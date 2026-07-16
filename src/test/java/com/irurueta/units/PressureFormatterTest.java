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

class PressureFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new PressureFormatter();

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
        formatter = new PressureFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new PressureFormatter((Locale) null));

        // test copy constructor
        formatter = new PressureFormatter(locale);
        final var formatter2 = new PressureFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new PressureFormatter((PressureFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new PressureFormatter();
        final var formatter2 = (PressureFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, PressureUnit.PASCAL, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (PressureFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new PressureFormatter(Locale.ENGLISH);
        final var formatter2 = new PressureFormatter(Locale.ENGLISH);
        final var formatter3 = new PressureFormatter(Locale.FRENCH);

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
        final var formatter1 = new PressureFormatter(Locale.ENGLISH);
        final var formatter2 = new PressureFormatter(Locale.ENGLISH);
        final var formatter3 = new PressureFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        assertEquals("5,5 Pa", formatter.format(new BigDecimal(value), PressureUnit.PASCAL));
        assertEquals("5,5 kPa", formatter.format(new BigDecimal(value), PressureUnit.KILOPASCAL));
        assertEquals("5,5 bar", formatter.format(new BigDecimal(value), PressureUnit.BAR));
        assertEquals("5,5 atm", formatter.format(new BigDecimal(value), PressureUnit.ATMOSPHERE));
        assertEquals("5,5 psi", formatter.format(new BigDecimal(value), PressureUnit.PSI));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Pa", formatter.format(new BigDecimal(value), PressureUnit.PASCAL, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kPa", formatter.format(new BigDecimal(value), PressureUnit.KILOPASCAL, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bar", formatter.format(new BigDecimal(value), PressureUnit.BAR,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 atm", formatter.format(new BigDecimal(value), PressureUnit.ATMOSPHERE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 psi", formatter.format(new BigDecimal(value), PressureUnit.PSI, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        assertEquals("5,5 Pa", formatter.format(value, PressureUnit.PASCAL));
        assertEquals("5,5 kPa", formatter.format(value, PressureUnit.KILOPASCAL));
        assertEquals("5,5 bar", formatter.format(value, PressureUnit.BAR));
        assertEquals("5,5 atm", formatter.format(value, PressureUnit.ATMOSPHERE));
        assertEquals("5,5 psi", formatter.format(value, PressureUnit.PSI));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Pa", formatter.format(value, PressureUnit.PASCAL, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kPa", formatter.format(value, PressureUnit.KILOPASCAL, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bar", formatter.format(value, PressureUnit.BAR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 atm", formatter.format(value, PressureUnit.ATMOSPHERE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 psi", formatter.format(value, PressureUnit.PSI, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatPressure() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        assertEquals("5,5 Pa", formatter.format(new Pressure(value, PressureUnit.PASCAL)));
        assertEquals("5,5 kPa", formatter.format(new Pressure(value, PressureUnit.KILOPASCAL)));
        assertEquals("5,5 bar", formatter.format(new Pressure(value, PressureUnit.BAR)));
        assertEquals("5,5 atm", formatter.format(new Pressure(value, PressureUnit.ATMOSPHERE)));
        assertEquals("5,5 psi", formatter.format(new Pressure(value, PressureUnit.PSI)));
    }

    @Test
    void testFormatPressureAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Pa", formatter.format(new Pressure(value, PressureUnit.PASCAL), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kPa", formatter.format(new Pressure(value, PressureUnit.KILOPASCAL), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bar", formatter.format(new Pressure(value, PressureUnit.BAR), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 atm", formatter.format(new Pressure(value, PressureUnit.ATMOSPHERE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 psi", formatter.format(new Pressure(value, PressureUnit.PSI), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(new BigDecimal("0.1"),
                PressureUnit.PASCAL));
        assertEquals("3,6 kPa", formatter.formatAndConvert(new BigDecimal("3600.0"),
                PressureUnit.PASCAL));
        assertEquals("1,48 atm", formatter.formatAndConvert(new BigDecimal("150000.0"),
                PressureUnit.PASCAL));

        assertEquals("4,42 atm", formatter.formatAndConvert(new BigDecimal("65.0"),
                PressureUnit.PSI));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("14.5 psi", formatter.formatAndConvert(new BigDecimal("100000.0"),
                PressureUnit.PASCAL));
        assertEquals("65 psi", formatter.formatAndConvert(new BigDecimal("65.0"),
                PressureUnit.PSI));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(0.1, PressureUnit.PASCAL));
        assertEquals("3,6 kPa", formatter.formatAndConvert(3600.0, PressureUnit.PASCAL));
        assertEquals("1,48 atm", formatter.formatAndConvert(150000.0, PressureUnit.PASCAL));

        assertEquals("4,42 atm", formatter.formatAndConvert(65.0, PressureUnit.PSI));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("14.5 psi", formatter.formatAndConvert(100000.0, PressureUnit.PASCAL));
        assertEquals("65 psi", formatter.formatAndConvert(65.0, PressureUnit.PSI));
    }

    @Test
    void testFormatAndConvertPressure() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(new Pressure(0.1, PressureUnit.PASCAL)));
        assertEquals("3,6 kPa", formatter.formatAndConvert(new Pressure(3600.0,
                PressureUnit.PASCAL)));
        assertEquals("1,48 atm", formatter.formatAndConvert(new Pressure(150000.0,
                PressureUnit.PASCAL)));

        assertEquals("4,42 atm", formatter.formatAndConvert(new Pressure(65.0,
                PressureUnit.PSI)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("14.5 psi", formatter.formatAndConvert(new Pressure(100000.0, PressureUnit.PASCAL)));
        assertEquals("65 psi", formatter.formatAndConvert(new Pressure(65.0, PressureUnit.PSI)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(new BigDecimal("0.1"),
                PressureUnit.PASCAL, UnitSystem.METRIC));
        assertEquals("3,6 kPa", formatter.formatAndConvert(new BigDecimal("3600.0"),
                PressureUnit.PASCAL, UnitSystem.METRIC));
        assertEquals("1,48 atm", formatter.formatAndConvert(new BigDecimal("150000.0"),
                PressureUnit.PASCAL, UnitSystem.METRIC));

        assertEquals("4,42 atm", formatter.formatAndConvert(new BigDecimal("65.0"),
                PressureUnit.PSI, UnitSystem.METRIC));

        assertEquals("14,5 psi", formatter.formatAndConvert(new BigDecimal("100000.0"),
                PressureUnit.PASCAL, UnitSystem.IMPERIAL));
        assertEquals("65 psi", formatter.formatAndConvert(new BigDecimal("65.0"),
                PressureUnit.PSI, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(0.1, PressureUnit.PASCAL,
                UnitSystem.METRIC));
        assertEquals("3,6 kPa", formatter.formatAndConvert(3600.0, PressureUnit.PASCAL,
                UnitSystem.METRIC));
        assertEquals("1,48 atm", formatter.formatAndConvert(150000.0, PressureUnit.PASCAL,
                UnitSystem.METRIC));

        assertEquals("4,42 atm", formatter.formatAndConvert(65.0, PressureUnit.PSI,
                UnitSystem.METRIC));

        assertEquals("14,5 psi", formatter.formatAndConvert(100000.0, PressureUnit.PASCAL,
                UnitSystem.IMPERIAL));
        assertEquals("65 psi", formatter.formatAndConvert(65.0, PressureUnit.PSI,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertPressureAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvert(new Pressure(0.1, PressureUnit.PASCAL),
                UnitSystem.METRIC));
        assertEquals("3,6 kPa", formatter.formatAndConvert(new Pressure(3600.0,
                PressureUnit.PASCAL), UnitSystem.METRIC));
        assertEquals("1,48 atm", formatter.formatAndConvert(new Pressure(150000.0,
                PressureUnit.PASCAL), UnitSystem.METRIC));

        assertEquals("4,42 atm", formatter.formatAndConvert(new Pressure(65.0, PressureUnit.PSI),
                UnitSystem.METRIC));

        assertEquals("14,5 psi", formatter.formatAndConvert(new Pressure(100000.0, PressureUnit.PASCAL),
                UnitSystem.IMPERIAL));
        assertEquals("65 psi", formatter.formatAndConvert(new Pressure(65.0, PressureUnit.PSI),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 Pa", formatter.formatAndConvertMetric(new BigDecimal("0.1"),
                PressureUnit.PASCAL));
        assertEquals("3,6 kPa", formatter.formatAndConvertMetric(new BigDecimal("3600.0"),
                PressureUnit.PASCAL));
        assertEquals("1,48 atm", formatter.formatAndConvertMetric(new BigDecimal("150000.0"),
                PressureUnit.PASCAL));

        assertEquals("4,42 atm", formatter.formatAndConvertMetric(new BigDecimal("65.0"),
                PressureUnit.PSI));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("es", "ES");

        final var formatter = new PressureFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("14,5 psi", formatter.formatAndConvertImperial(new BigDecimal("100000.0"),
                PressureUnit.PASCAL));
        assertEquals("65 psi", formatter.formatAndConvertImperial(new BigDecimal("65.0"),
                PressureUnit.PSI));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = PressureFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new PressureFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new PressureFormatter();

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
        var formatter = new PressureFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new PressureFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new PressureFormatter();

        assertTrue(formatter.isValidUnit("Pa"));
        assertTrue(formatter.isValidUnit("Pa "));

        assertTrue(formatter.isValidUnit("kPa"));
        assertTrue(formatter.isValidUnit("kPa "));

        assertTrue(formatter.isValidUnit("bar"));
        assertTrue(formatter.isValidUnit("bar "));

        assertTrue(formatter.isValidUnit("atm"));
        assertTrue(formatter.isValidUnit("atm "));

        assertTrue(formatter.isValidUnit("psi"));
        assertTrue(formatter.isValidUnit("psi "));
    }

    @Test
    void testIsValidMeasurement() {
        final PressureFormatter formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kPa";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 bar";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 atm";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 psi";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kPa";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 bar";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 atm";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 psi";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kPa";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 bar";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 atm";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 psi";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kPa";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 bar";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 atm";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 psi";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        var p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.PASCAL, p.getUnit());

        text = "5,5 kPa";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.KILOPASCAL, p.getUnit());

        text = "5,5 bar";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.BAR, p.getUnit());

        text = "5,5 atm";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.ATMOSPHERE, p.getUnit());

        text = "5,5 psi";
        p = formatter.parse(text);
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.PSI, p.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new PressureFormatter(new Locale("es", "ES"));

        var text = "5,5 Pa";
        assertEquals(PressureUnit.PASCAL, formatter.findUnit(text));

        text = "5,5 kPa";
        assertEquals(PressureUnit.KILOPASCAL, formatter.findUnit(text));

        text = "5,5 bar";
        assertEquals(PressureUnit.BAR, formatter.findUnit(text));

        text = "5,5 atm";
        assertEquals(PressureUnit.ATMOSPHERE, formatter.findUnit(text));

        text = "5,5 psi";
        assertEquals(PressureUnit.PSI, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new PressureFormatter();

        assertEquals(PressureFormatter.PASCAL, formatter.getUnitSymbol(PressureUnit.PASCAL));
        assertEquals(PressureFormatter.KILOPASCAL, formatter.getUnitSymbol(PressureUnit.KILOPASCAL));
        assertEquals(PressureFormatter.BAR, formatter.getUnitSymbol(PressureUnit.BAR));
        assertEquals(PressureFormatter.ATMOSPHERE, formatter.getUnitSymbol(PressureUnit.ATMOSPHERE));
        assertEquals(PressureFormatter.PSI, formatter.getUnitSymbol(PressureUnit.PSI));
    }
}
