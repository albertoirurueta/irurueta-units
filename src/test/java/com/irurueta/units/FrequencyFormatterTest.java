/*
 * Copyright (C) 2018 Alberto Irurueta Carro (alberto@irurueta.com)
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

class FrequencyFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new FrequencyFormatter();

        // check
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final var locale = new Locale("es", "ES");
        formatter = new FrequencyFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new FrequencyFormatter((Locale) null));

        // test copy constructor
        formatter = new FrequencyFormatter(locale);
        final var formatter2 = new FrequencyFormatter(formatter);

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

        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new FrequencyFormatter((FrequencyFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new FrequencyFormatter();
        final var formatter2 = (FrequencyFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, FrequencyUnit.HERTZ, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (FrequencyFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        final var formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        final var formatter3 = new FrequencyFormatter(Locale.FRENCH);

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
        final var formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        final var formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        final var formatter3 = new FrequencyFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        assertEquals("5,5 Hz", formatter.format(new BigDecimal(value), FrequencyUnit.HERTZ));
        assertEquals("5,5 KHz", formatter.format(new BigDecimal(value), FrequencyUnit.KILOHERTZ));
        assertEquals("5,5 MHz", formatter.format(new BigDecimal(value), FrequencyUnit.MEGAHERTZ));
        assertEquals("5,5 GHz", formatter.format(new BigDecimal(value), FrequencyUnit.GIGAHERTZ));
        assertEquals("5,5 THz", formatter.format(new BigDecimal(value), FrequencyUnit.TERAHERTZ));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Hz", formatter.format(new BigDecimal(value), FrequencyUnit.HERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KHz", formatter.format(new BigDecimal(value), FrequencyUnit.KILOHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MHz", formatter.format(new BigDecimal(value), FrequencyUnit.MEGAHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GHz", formatter.format(new BigDecimal(value), FrequencyUnit.GIGAHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 THz", formatter.format(new BigDecimal(value), FrequencyUnit.TERAHERTZ, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        assertEquals("5,5 Hz", formatter.format(value, FrequencyUnit.HERTZ));
        assertEquals("5,5 KHz", formatter.format(value, FrequencyUnit.KILOHERTZ));
        assertEquals("5,5 MHz", formatter.format(value, FrequencyUnit.MEGAHERTZ));
        assertEquals("5,5 GHz", formatter.format(value, FrequencyUnit.GIGAHERTZ));
        assertEquals("5,5 THz", formatter.format(value, FrequencyUnit.TERAHERTZ));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Hz", formatter.format(value, FrequencyUnit.HERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KHz", formatter.format(value, FrequencyUnit.KILOHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MHz", formatter.format(value, FrequencyUnit.MEGAHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GHz", formatter.format(value, FrequencyUnit.GIGAHERTZ, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 THz", formatter.format(value, FrequencyUnit.TERAHERTZ, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatFrequency() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        assertEquals("5,5 Hz", formatter.format(new Frequency(value, FrequencyUnit.HERTZ)));
        assertEquals("5,5 KHz", formatter.format(new Frequency(value, FrequencyUnit.KILOHERTZ)));
        assertEquals("5,5 MHz", formatter.format(new Frequency(value, FrequencyUnit.MEGAHERTZ)));
        assertEquals("5,5 GHz", formatter.format(new Frequency(value, FrequencyUnit.GIGAHERTZ)));
        assertEquals("5,5 THz", formatter.format(new Frequency(value, FrequencyUnit.TERAHERTZ)));
    }

    @Test
    void testFormatFrequencyAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 Hz", formatter.format(new Frequency(value, FrequencyUnit.HERTZ), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KHz", formatter.format(new Frequency(value, FrequencyUnit.KILOHERTZ), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MHz", formatter.format(new Frequency(value, FrequencyUnit.MEGAHERTZ), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GHz", formatter.format(new Frequency(value, FrequencyUnit.GIGAHERTZ), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 THz", formatter.format(new Frequency(value, FrequencyUnit.TERAHERTZ), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(new BigDecimal("5.50"), FrequencyUnit.HERTZ));
        assertEquals("5,5 KHz", formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.HERTZ));
        assertEquals("5,5 MHz", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ));
        assertEquals("5,5 GHz", formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ));
        assertEquals("5,5 THz", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(5.50, FrequencyUnit.HERTZ));
        assertEquals("5,5 KHz", formatter.formatAndConvert(5500.00, FrequencyUnit.HERTZ));
        assertEquals("5,5 MHz", formatter.formatAndConvert(5500000.00, FrequencyUnit.HERTZ));
        assertEquals("5,5 GHz", formatter.formatAndConvert(5500.00, FrequencyUnit.MEGAHERTZ));
        assertEquals("5,5 THz", formatter.formatAndConvert(5500000.00, FrequencyUnit.MEGAHERTZ));
    }

    @Test
    void testFormatAndConvertFrequency() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(new Frequency(5.50, FrequencyUnit.HERTZ)));
        assertEquals("5,5 KHz", formatter.formatAndConvert(new Frequency(5500.00, FrequencyUnit.HERTZ)));
        assertEquals("5,5 MHz", formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.HERTZ)));
        assertEquals("5,5 GHz", formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.MEGAHERTZ)));
        assertEquals("5,5 THz", formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.MEGAHERTZ)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(new BigDecimal("5.50"), FrequencyUnit.HERTZ,
                UnitSystem.METRIC));
        assertEquals("5,5 KHz", formatter.formatAndConvert(new BigDecimal("5500.00"), FrequencyUnit.HERTZ,
                UnitSystem.METRIC));
        assertEquals("5,5 MHz", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ, UnitSystem.METRIC));
        assertEquals("5,5 GHz", formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC));
        assertEquals("5,5 THz", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(5.50, FrequencyUnit.HERTZ, UnitSystem.METRIC));
        assertEquals("5,5 KHz", formatter.formatAndConvert(5500.00, FrequencyUnit.HERTZ,
                UnitSystem.METRIC));
        assertEquals("5,5 MHz", formatter.formatAndConvert(5500000.00, FrequencyUnit.HERTZ,
                UnitSystem.METRIC));
        assertEquals("5,5 GHz", formatter.formatAndConvert(5500.00, FrequencyUnit.MEGAHERTZ,
                UnitSystem.METRIC));
        assertEquals("5,5 THz", formatter.formatAndConvert(5500000.00, FrequencyUnit.MEGAHERTZ,
                UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertFrequencyAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvert(new Frequency(5.50, FrequencyUnit.HERTZ),
                UnitSystem.METRIC));
        assertEquals("5,5 KHz", formatter.formatAndConvert(new Frequency(5500.00, FrequencyUnit.HERTZ),
                UnitSystem.METRIC));
        assertEquals("5,5 MHz", formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.HERTZ), UnitSystem.METRIC));
        assertEquals("5,5 GHz", formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.MEGAHERTZ), UnitSystem.METRIC));
        assertEquals("5,5 THz", formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.MEGAHERTZ), UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 Hz", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                FrequencyUnit.HERTZ));
        assertEquals("5,5 KHz", formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                FrequencyUnit.HERTZ));
        assertEquals("5,5 MHz", formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ));
        assertEquals("5,5 GHz", formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ));
        assertEquals("5,5 THz", formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = FrequencyFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new FrequencyFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new FrequencyFormatter();

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
        var formatter = new FrequencyFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new FrequencyFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new FrequencyFormatter();

        assertTrue(formatter.isValidUnit("Hz"));
        assertTrue(formatter.isValidUnit("Hz "));

        assertTrue(formatter.isValidUnit("KHz"));
        assertTrue(formatter.isValidUnit("KHz "));

        assertTrue(formatter.isValidUnit("MHz"));
        assertTrue(formatter.isValidUnit("MHz "));

        assertTrue(formatter.isValidUnit("GHz"));
        assertTrue(formatter.isValidUnit("GHz "));

        assertTrue(formatter.isValidUnit("THz"));
        assertTrue(formatter.isValidUnit("THz "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 KHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 MHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 GHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 THz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 KHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 MHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 GHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 THz";
        assertTrue(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 KHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 MHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 GHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 THz";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 KHz";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 MHz";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 GHz";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 THz";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        Frequency f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.HERTZ, f.getUnit());

        text = "5,5 KHz";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.KILOHERTZ, f.getUnit());

        text = "5,5 MHz";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.MEGAHERTZ, f.getUnit());

        text = "5,5 GHz";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.GIGAHERTZ, f.getUnit());

        text = "5,5 THz";
        f = formatter.parse(text);
        assertEquals(5.5, f.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.TERAHERTZ, f.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new FrequencyFormatter(new Locale("es", "ES"));

        var text = "5,5 Hz";
        assertEquals(FrequencyUnit.HERTZ, formatter.findUnit(text));

        text = "5,5 KHz";
        assertEquals(FrequencyUnit.KILOHERTZ, formatter.findUnit(text));

        text = "5,5 MHz";
        assertEquals(FrequencyUnit.MEGAHERTZ, formatter.findUnit(text));

        text = "5,5 GHz";
        assertEquals(FrequencyUnit.GIGAHERTZ, formatter.findUnit(text));

        text = "5,5 THz";
        assertEquals(FrequencyUnit.TERAHERTZ, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new FrequencyFormatter();

        assertEquals(FrequencyFormatter.HERTZ, formatter.getUnitSymbol(FrequencyUnit.HERTZ));
        assertEquals(FrequencyFormatter.KILOHERTZ, formatter.getUnitSymbol(FrequencyUnit.KILOHERTZ));
        assertEquals(FrequencyFormatter.MEGAHERTZ, formatter.getUnitSymbol(FrequencyUnit.MEGAHERTZ));
        assertEquals(FrequencyFormatter.GIGAHERTZ, formatter.getUnitSymbol(FrequencyUnit.GIGAHERTZ));
        assertEquals(FrequencyFormatter.TERAHERTZ, formatter.getUnitSymbol(FrequencyUnit.TERAHERTZ));
    }
}
