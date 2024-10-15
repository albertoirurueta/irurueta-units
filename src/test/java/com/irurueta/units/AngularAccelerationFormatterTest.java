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

class AngularAccelerationFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new AngularAccelerationFormatter();

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
        formatter = new AngularAccelerationFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new AngularAccelerationFormatter((Locale) null));

        // test copy constructor
        formatter = new AngularAccelerationFormatter(locale);
        final var formatter2 = new AngularAccelerationFormatter(formatter);

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
        assertThrows(NullPointerException.class,
                () -> new AngularAccelerationFormatter((AngularAccelerationFormatter) null));
    }

    @Test
    void testEquals() {
        final var formatter1 = new AngularAccelerationFormatter(Locale.ENGLISH);
        final var formatter2 = new AngularAccelerationFormatter(Locale.ENGLISH);
        final var formatter3 = new AngularAccelerationFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    void testHashCode() {
        final var formatter1 = new AngularAccelerationFormatter(Locale.ENGLISH);
        final var formatter2 = new AngularAccelerationFormatter(Locale.ENGLISH);
        final var formatter3 = new AngularAccelerationFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        assertEquals("5,5 º/s²", formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertEquals("5,5 rad/s²", formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s²", formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s²", formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        assertEquals("5,5 º/s²", formatter.format(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertEquals("5,5 rad/s²", formatter.format(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s²", formatter.format(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s²", formatter.format(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAngularAcceleration() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        assertEquals("5,5 º/s²", formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND)));
        assertEquals("5,5 rad/s²", formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND)));
    }

    @Test
    void testFormatAngularAccelerationAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º/s²", formatter.format(new AngularAcceleration(value,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad/s²", formatter.format(new AngularAcceleration(value,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND), buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND));
    }

    @Test
    void testFormatAndConvertAngularAcceleration() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND)));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, UnitSystem.METRIC));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, UnitSystem.METRIC));

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, UnitSystem.METRIC));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, UnitSystem.METRIC));

        assertEquals("5,5 º/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertAngularAccelerationAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND), UnitSystem.METRIC));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND), UnitSystem.METRIC));

        assertEquals("5,5 º/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND), UnitSystem.IMPERIAL));
        assertEquals("5,5 rad/s²", formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND), UnitSystem.IMPERIAL));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = AngularAccelerationFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(NumberFormat.getInstance().isGroupingUsed(),formatter.isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new AngularAccelerationFormatter();

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
        var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new AngularAccelerationFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new AngularAccelerationFormatter();

        assertTrue(formatter.isValidUnit("º/s²"));
        assertTrue(formatter.isValidUnit("º/s² "));

        assertTrue(formatter.isValidUnit("rad/s²"));
        assertTrue(formatter.isValidUnit("rad/s² "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 rad/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 rad/s²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 s²";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 rad/s²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 s²";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 rad/s²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s²";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s²";
        var a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a.getUnit());

        text = "5,5 rad/s²";
        a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, a.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new AngularAccelerationFormatter(new Locale("es", "ES"));

        var text = "5,5 º/s²";
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, formatter.findUnit(text));

        text = "5,5 rad/s²";
        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new AngularAccelerationFormatter();

        assertEquals(AngularAccelerationFormatter.DEGREES_PER_SQUARED_SECOND, formatter.getUnitSymbol(
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertEquals(AngularAccelerationFormatter.RADIANS_PER_SQUARED_SECOND, formatter.getUnitSymbol(
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND));
    }
}
