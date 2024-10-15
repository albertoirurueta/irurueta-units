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

class AngleFormatterTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new AngleFormatter();

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
        formatter = new AngleFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new AngleFormatter((Locale) null));

        // test copy constructor
        formatter = new AngleFormatter(locale);
        var formatter2 = new AngleFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new AngleFormatter((AngleFormatter) null));
    }

    @Test
    void testEquals() {
        final var formatter1 = new AngleFormatter(Locale.ENGLISH);
        final var formatter2 = new AngleFormatter(Locale.ENGLISH);
        final var formatter3 = new AngleFormatter(Locale.FRENCH);

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
        final var formatter1 = new AngleFormatter(Locale.ENGLISH);
        final var formatter2 = new AngleFormatter(Locale.ENGLISH);
        final var formatter3 = new AngleFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        assertEquals("5,5 º", formatter.format(new BigDecimal(value), AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.format(new BigDecimal(value), AngleUnit.RADIANS));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º", formatter.format(new BigDecimal(value),
                AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad", formatter.format(new BigDecimal(value),
                AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        assertEquals("5,5 º", formatter.format(value, AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.format(value, AngleUnit.RADIANS));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º", formatter.format(value,
                AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad", formatter.format(value,
                AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAngle() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        assertEquals("5,5 º", formatter.format(new Angle(value, AngleUnit.DEGREES)));
        assertEquals("5,5 rad", formatter.format(new Angle(value, AngleUnit.RADIANS)));
    }

    @Test
    void testFormatAngleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 º", formatter.format(new Angle(value, AngleUnit.DEGREES), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad", formatter.format(new Angle(value, AngleUnit.RADIANS), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(new BigDecimal("5.50"), AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.formatAndConvert(new BigDecimal("5.50"), AngleUnit.RADIANS));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(5.50, AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.formatAndConvert(5.50, AngleUnit.RADIANS));
    }

    @Test
    void testFormatAndConvertAngle() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(new Angle(5.50, AngleUnit.DEGREES)));
        assertEquals("5,5 rad", formatter.formatAndConvert(new Angle(5.50, AngleUnit.RADIANS)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES, UnitSystem.METRIC));
        assertEquals("5,5 rad", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS, UnitSystem.METRIC));

        assertEquals("5,5 º", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(5.50, AngleUnit.DEGREES, UnitSystem.METRIC));
        assertEquals("5,5 rad", formatter.formatAndConvert(5.50, AngleUnit.RADIANS, UnitSystem.METRIC));

        assertEquals("5,5 º", formatter.formatAndConvert(5.50, AngleUnit.DEGREES, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(5.50, AngleUnit.RADIANS, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertAngleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.DEGREES), UnitSystem.METRIC));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.RADIANS), UnitSystem.METRIC));

        assertEquals("5,5 º",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.DEGREES), UnitSystem.IMPERIAL));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.RADIANS), UnitSystem.IMPERIAL));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = AngleFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new AngleFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new AngleFormatter();

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
        var formatter = new AngleFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new AngleFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new AngleFormatter();

        assertTrue(formatter.isValidUnit("º"));
        assertTrue(formatter.isValidUnit("º "));

        assertTrue(formatter.isValidUnit("rad"));
        assertTrue(formatter.isValidUnit("rad "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 rad";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 rad";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 rad";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 rad";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        var a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, a.getUnit());

        text = "5,5 rad";
        a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.RADIANS, a.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        var text = "5,5 º";
        assertEquals(AngleUnit.DEGREES, formatter.findUnit(text));

        text = "5,5 rad";
        assertEquals(AngleUnit.RADIANS, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new AngleFormatter();

        assertEquals(AngleFormatter.DEGREE, formatter.getUnitSymbol(AngleUnit.DEGREES));
        assertEquals(AngleFormatter.RADIAN, formatter.getUnitSymbol(AngleUnit.RADIANS));
    }

    @Test
    void testFormatDegreesAndMinutes() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        final var angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final var angle2 = new Angle(-45.5, AngleUnit.DEGREES);

        // check
        assertEquals("45º 30'", formatter.formatDegreesAndMinutes(angle1));
        assertEquals("-46º 30'", formatter.formatDegreesAndMinutes(angle2));
    }

    @Test
    void testFormatDegreesMinutesAndSeconds() {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        final var angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final var angle2 = new Angle(-45.5, AngleUnit.DEGREES);
        final var angle3 = new Angle(45.55, AngleUnit.DEGREES);
        final var angle4 = new Angle(45.555, AngleUnit.DEGREES);

        // check
        assertEquals("45º 30' 0\"", formatter.formatDegreesMinutesAndSeconds(angle1));
        assertEquals("-46º 30' 0\"", formatter.formatDegreesMinutesAndSeconds(angle2));
        assertEquals("45º 32' 60\"", formatter.formatDegreesMinutesAndSeconds(angle3));
        assertEquals("45º 33' 18\"", formatter.formatDegreesMinutesAndSeconds(angle4));
    }

    @Test
    void testParseDegreesAndMinutes() throws ParseException, UnknownUnitException {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        final var angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final var angle2 = new Angle(-45.5, AngleUnit.DEGREES);

        // check
        final var str1 = formatter.formatDegreesAndMinutes(angle1);
        final var str2 = formatter.formatDegreesAndMinutes(angle2);

        assertEquals("45º 30'", str1);
        assertEquals("-46º 30'", str2);

        final var angle1b = formatter.parseDegreesAndMinutes(str1);
        final var angle2b = formatter.parseDegreesAndMinutes(str2);

        assertEquals(AngleUnit.DEGREES, angle1b.getUnit());
        assertEquals(45.5, angle1b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle2b.getUnit());
        assertEquals(-45.5, angle2b.getValue().doubleValue(), ERROR);

        // force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parseDegreesAndMinutes("wrong"));
    }

    @Test
    void testParseDegreesMinutesAndSeconds() throws ParseException, UnknownUnitException {
        final var formatter = new AngleFormatter(new Locale("es", "ES"));

        final var angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final var angle2 = new Angle(-45.5, AngleUnit.DEGREES);
        final var angle3 = new Angle(45.55, AngleUnit.DEGREES);
        final var angle4 = new Angle(45.555, AngleUnit.DEGREES);

        // check
        final var str1 = formatter.formatDegreesMinutesAndSeconds(angle1);
        final var str2 = formatter.formatDegreesMinutesAndSeconds(angle2);
        final var str3 = formatter.formatDegreesMinutesAndSeconds(angle3);
        final var str4 = formatter.formatDegreesMinutesAndSeconds(angle4);

        assertEquals("45º 30' 0\"", str1);
        assertEquals("-46º 30' 0\"", str2);
        assertEquals("45º 32' 60\"", str3);
        assertEquals("45º 33' 18\"", str4);

        final var angle1b = formatter.parseDegreesMinutesAndSeconds(str1);
        final var angle2b = formatter.parseDegreesMinutesAndSeconds(str2);
        final var angle3b = formatter.parseDegreesMinutesAndSeconds(str3);
        final var angle4b = formatter.parseDegreesMinutesAndSeconds(str4);

        assertEquals(AngleUnit.DEGREES, angle1b.getUnit());
        assertEquals(45.5, angle1b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle2b.getUnit());
        assertEquals(-45.5, angle2b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle3b.getUnit());
        assertEquals(45.55, angle3b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle4b.getUnit());
        assertEquals(45.555, angle4b.getValue().doubleValue(), ERROR);

        // force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parseDegreesMinutesAndSeconds("wrong"));
    }
}
