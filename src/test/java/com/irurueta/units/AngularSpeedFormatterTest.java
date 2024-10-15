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

class AngularSpeedFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new AngularSpeedFormatter();

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
        formatter = new AngularSpeedFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new AngularSpeedFormatter((Locale) null));

        // test copy constructor
        formatter = new AngularSpeedFormatter(locale);
        final var formatter2 = new AngularSpeedFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new AngularSpeedFormatter((AngularSpeedFormatter) null));
    }

    @Test
    void testEquals() {
        final var formatter1 = new AngularSpeedFormatter(Locale.ENGLISH);
        final var formatter2 = new AngularSpeedFormatter(Locale.ENGLISH);
        final var formatter3 = new AngularSpeedFormatter(Locale.FRENCH);

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
        final var formatter1 = new AngularSpeedFormatter(Locale.ENGLISH);
        final var formatter2 = new AngularSpeedFormatter(Locale.ENGLISH);
        final var formatter3 = new AngularSpeedFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        assertEquals("5,5 º/s", formatter.format(new BigDecimal(value), AngularSpeedUnit.DEGREES_PER_SECOND));
        assertEquals("5,5 rad/s", formatter.format(new BigDecimal(value),
                AngularSpeedUnit.RADIANS_PER_SECOND));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s", formatter.format(new BigDecimal(value), AngularSpeedUnit.DEGREES_PER_SECOND,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s", formatter.format(new BigDecimal(value), AngularSpeedUnit.RADIANS_PER_SECOND,
                buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        assertEquals("5,5 º/s", formatter.format(value, AngularSpeedUnit.DEGREES_PER_SECOND));
        assertEquals("5,5 rad/s", formatter.format(value, AngularSpeedUnit.RADIANS_PER_SECOND));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s", formatter.format(value, AngularSpeedUnit.DEGREES_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s", formatter.format(value, AngularSpeedUnit.RADIANS_PER_SECOND, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAngularSpeed() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        assertEquals("5,5 º/s", formatter.format(new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND)));
        assertEquals("5,5 rad/s", formatter.format(new AngularSpeed(value,
                AngularSpeedUnit.RADIANS_PER_SECOND)));
    }

    @Test
    void testFormatAngularSpeedAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s", formatter.format(new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s", formatter.format(new AngularSpeed(value,
                        AngularSpeedUnit.RADIANS_PER_SECOND), buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.DEGREES_PER_SECOND));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.RADIANS_PER_SECOND));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.DEGREES_PER_SECOND));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.RADIANS_PER_SECOND));
    }

    @Test
    void testFormatAndConvertAngularSpeed() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.DEGREES_PER_SECOND)));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.RADIANS_PER_SECOND)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.DEGREES_PER_SECOND, UnitSystem.METRIC));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.RADIANS_PER_SECOND, UnitSystem.METRIC));

        assertEquals("5,5 º/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.DEGREES_PER_SECOND, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularSpeedUnit.RADIANS_PER_SECOND, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.DEGREES_PER_SECOND,
                UnitSystem.METRIC));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.RADIANS_PER_SECOND,
                UnitSystem.METRIC));

        assertEquals("5,5 º/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.DEGREES_PER_SECOND,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(5.50, AngularSpeedUnit.RADIANS_PER_SECOND,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertAngularSpeedAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularSpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.DEGREES_PER_SECOND), UnitSystem.METRIC));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.RADIANS_PER_SECOND), UnitSystem.METRIC));

        assertEquals("5,5 º/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.DEGREES_PER_SECOND), UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s", formatter.formatAndConvert(new AngularSpeed(5.50,
                AngularSpeedUnit.RADIANS_PER_SECOND), UnitSystem.IMPERIAL));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = AngularSpeedFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new AngularSpeedFormatter();

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
        var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new AngularSpeedFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new AngularSpeedFormatter();

        assertTrue(formatter.isValidUnit("º/s"));
        assertTrue(formatter.isValidUnit("º/s "));

        assertTrue(formatter.isValidUnit("rad/s"));
        assertTrue(formatter.isValidUnit("rad/s "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 rad/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 rad/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 rad/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 rad/s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        var s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s.getUnit());

        text = "5,5 rad/s";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, s.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new AngularSpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s";
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, formatter.findUnit(text));

        text = "5,5 rad/s";
        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new AngularSpeedFormatter();

        assertEquals(AngularSpeedFormatter.DEGREES_PER_SECOND,
                formatter.getUnitSymbol(AngularSpeedUnit.DEGREES_PER_SECOND));
        assertEquals(AngularSpeedFormatter.RADIANS_PER_SECOND,
                formatter.getUnitSymbol(AngularSpeedUnit.RADIANS_PER_SECOND));
    }
}
