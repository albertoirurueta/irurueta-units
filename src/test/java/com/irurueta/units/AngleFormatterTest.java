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

import org.junit.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;

public class AngleFormatterTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        AngleFormatter formatter = new AngleFormatter();

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
        assertEquals(formatter.getUnitSystem(), UnitLocale.getDefault());
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new AngleFormatter(locale);

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
        assertEquals(formatter.getUnitSystem(), UnitLocale.getFrom(locale));
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);


        // force IllegalArgumentException
        formatter = null;
        try {
            formatter = new AngleFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new AngleFormatter(locale);
        AngleFormatter formatter2 = new AngleFormatter(formatter);

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
        assertEquals(formatter2.getUnitSystem(), UnitLocale.getFrom(locale));
        assertEquals(formatter2.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter2.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter2.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        formatter = null;
        try {
            //noinspection ConstantConditions
            formatter = new AngleFormatter((AngleFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testEquals() {
        final AngleFormatter formatter1 = new AngleFormatter(Locale.ENGLISH);
        final AngleFormatter formatter2 = new AngleFormatter(Locale.ENGLISH);
        final AngleFormatter formatter3 = new AngleFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final AngleFormatter formatter1 = new AngleFormatter(Locale.ENGLISH);
        final AngleFormatter formatter2 = new AngleFormatter(Locale.ENGLISH);
        final AngleFormatter formatter3 = new AngleFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value), AngleUnit.DEGREES),
                "5,5 º");
        assertEquals(formatter.format(new BigDecimal(value), AngleUnit.RADIANS),
                "5,5 rad");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString(),
                "5,5 º");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString(),
                "5,5 rad");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        assertEquals(formatter.format(value, AngleUnit.DEGREES),
                "5,5 º");
        assertEquals(formatter.format(value, AngleUnit.RADIANS),
                "5,5 rad");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString(),
                "5,5 º");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString(),
                "5,5 rad");
    }

    @Test
    public void testFormatAngle() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        assertEquals(formatter.format(new Angle(value, AngleUnit.DEGREES)),
                "5,5 º");
        assertEquals(formatter.format(new Angle(value, AngleUnit.RADIANS)),
                "5,5 rad");
    }

    @Test
    public void testFormatAngleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Angle(value, AngleUnit.DEGREES), buffer,
                new FieldPosition(0)).toString(), "5,5 º");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Angle(value, AngleUnit.RADIANS), buffer,
                new FieldPosition(0)).toString(), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertNumber() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES), "5,5 º");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertDouble() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.DEGREES), "5,5 º");
        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.RADIANS), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertAngle() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.DEGREES)), "5,5 º");
        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.RADIANS)), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES, UnitSystem.METRIC), "5,5 º");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS, UnitSystem.METRIC), "5,5 rad");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES, UnitSystem.IMPERIAL), "5,5 º");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS, UnitSystem.IMPERIAL), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.DEGREES, UnitSystem.METRIC), "5,5 º");
        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.RADIANS, UnitSystem.METRIC), "5,5 rad");

        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.DEGREES, UnitSystem.IMPERIAL), "5,5 º");
        assertEquals(formatter.formatAndConvert(5.50,
                AngleUnit.RADIANS, UnitSystem.IMPERIAL), "5,5 rad");
    }

    @Test
    public void testFormatAndConvertAngleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.DEGREES), UnitSystem.METRIC), "5,5 º");
        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.RADIANS), UnitSystem.METRIC), "5,5 rad");

        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.DEGREES), UnitSystem.IMPERIAL), "5,5 º");
        assertEquals(formatter.formatAndConvert(new Angle(5.50,
                AngleUnit.RADIANS), UnitSystem.IMPERIAL), "5,5 rad");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = AngleFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final AngleFormatter formatter = new AngleFormatter();

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
        final AngleFormatter formatter = new AngleFormatter();

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
        final AngleFormatter formatter = new AngleFormatter();

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
        AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new AngleFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        final AngleFormatter formatter = new AngleFormatter();

        assertTrue(formatter.isValidUnit("º"));
        assertTrue(formatter.isValidUnit("º "));

        assertTrue(formatter.isValidUnit("rad"));
        assertTrue(formatter.isValidUnit("rad "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 rad";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 rad";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 rad";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 rad";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        Angle a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AngleUnit.DEGREES);

        text = "5,5 rad";
        a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AngleUnit.RADIANS);

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
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        assertEquals(formatter.findUnit(text), AngleUnit.DEGREES);

        text = "5,5 rad";
        assertEquals(formatter.findUnit(text), AngleUnit.RADIANS);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getUnitSymbol(AngleUnit.DEGREES),
                AngleFormatter.DEGREE);
        assertEquals(formatter.getUnitSymbol(AngleUnit.RADIANS),
                AngleFormatter.RADIAN);
    }

    @Test
    public void testFormatDegreesAndMinutes() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        final Angle angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final Angle angle2 = new Angle(-45.5, AngleUnit.DEGREES);

        // check
        assertEquals(formatter.formatDegreesAndMinutes(angle1), "45º 30'");
        assertEquals(formatter.formatDegreesAndMinutes(angle2), "-46º 30'");
    }

    @Test
    public void testFormatDegreesMinutesAndSeconds() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        final Angle angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final Angle angle2 = new Angle(-45.5, AngleUnit.DEGREES);
        final Angle angle3 = new Angle(45.55, AngleUnit.DEGREES);
        final Angle angle4 = new Angle(45.555, AngleUnit.DEGREES);

        // check
        assertEquals(formatter.formatDegreesMinutesAndSeconds(angle1), "45º 30' 0\"");
        assertEquals(formatter.formatDegreesMinutesAndSeconds(angle2), "-46º 30' 0\"");
        assertEquals(formatter.formatDegreesMinutesAndSeconds(angle3), "45º 32' 60\"");
        assertEquals(formatter.formatDegreesMinutesAndSeconds(angle4), "45º 33' 18\"");
    }

    @Test
    public void testParseDegreesAndMinutes() throws ParseException, UnknownUnitException {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        final Angle angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final Angle angle2 = new Angle(-45.5, AngleUnit.DEGREES);

        // check
        final String str1 = formatter.formatDegreesAndMinutes(angle1);
        final String str2 = formatter.formatDegreesAndMinutes(angle2);

        assertEquals(str1, "45º 30'");
        assertEquals(str2, "-46º 30'");

        final Angle angle1b = formatter.parseDegreesAndMinutes(str1);
        final Angle angle2b = formatter.parseDegreesAndMinutes(str2);

        assertEquals(angle1b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle1b.getValue().doubleValue(), 45.5, ERROR);

        assertEquals(angle2b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle2b.getValue().doubleValue(), -45.5, ERROR);

        // force UnknownUnitException
        Angle a = null;
        try {
            a = formatter.parseDegreesAndMinutes("wrong");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        assertNull(a);
    }

    @Test
    public void testParseDegreesMinutesAndSeconds() throws ParseException, UnknownUnitException {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        final Angle angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final Angle angle2 = new Angle(-45.5, AngleUnit.DEGREES);
        final Angle angle3 = new Angle(45.55, AngleUnit.DEGREES);
        final Angle angle4 = new Angle(45.555, AngleUnit.DEGREES);

        // check
        final String str1 = formatter.formatDegreesMinutesAndSeconds(angle1);
        final String str2 = formatter.formatDegreesMinutesAndSeconds(angle2);
        final String str3 = formatter.formatDegreesMinutesAndSeconds(angle3);
        final String str4 = formatter.formatDegreesMinutesAndSeconds(angle4);

        assertEquals(str1, "45º 30' 0\"");
        assertEquals(str2, "-46º 30' 0\"");
        assertEquals(str3, "45º 32' 60\"");
        assertEquals(str4, "45º 33' 18\"");

        final Angle angle1b = formatter.parseDegreesMinutesAndSeconds(str1);
        final Angle angle2b = formatter.parseDegreesMinutesAndSeconds(str2);
        final Angle angle3b = formatter.parseDegreesMinutesAndSeconds(str3);
        final Angle angle4b = formatter.parseDegreesMinutesAndSeconds(str4);

        assertEquals(angle1b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle1b.getValue().doubleValue(), 45.5, ERROR);

        assertEquals(angle2b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle2b.getValue().doubleValue(), -45.5, ERROR);

        assertEquals(angle3b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle3b.getValue().doubleValue(), 45.55, ERROR);

        assertEquals(angle4b.getUnit(), AngleUnit.DEGREES);
        assertEquals(angle4b.getValue().doubleValue(), 45.555, ERROR);

        // force UnknownUnitException
        Angle a = null;
        try {
            a = formatter.parseDegreesMinutesAndSeconds("wrong");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        assertNull(a);
    }
}
