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

import org.junit.Test;

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
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(),
                formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(),
                formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(),
                formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(),
                formatter.getRoundingMode());
        assertEquals(UnitLocale.getDefault(), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new AngleFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(),
                formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(),
                formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(),
                formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(),
                formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());


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
        assertEquals(locale, formatter2.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter2.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(),
                formatter2.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter2.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(),
                formatter2.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(),
                formatter2.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter2.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(),
                formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(),
                formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter2.getValueAndUnitFormatPattern());

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
        //noinspection EqualsWithItself
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

        assertEquals("5,5 º",
                formatter.format(new BigDecimal(value), AngleUnit.DEGREES));
        assertEquals("5,5 rad",
                formatter.format(new BigDecimal(value), AngleUnit.RADIANS));
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 º",
                formatter.format(new BigDecimal(value),
                        AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad",
                formatter.format(new BigDecimal(value),
                        AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        assertEquals("5,5 º",
                formatter.format(value, AngleUnit.DEGREES));
        assertEquals("5,5 rad",
                formatter.format(value, AngleUnit.RADIANS));
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 º",
                formatter.format(value,
                        AngleUnit.DEGREES, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad",
                formatter.format(value,
                        AngleUnit.RADIANS, buffer, new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAngle() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        assertEquals("5,5 º",
                formatter.format(new Angle(value, AngleUnit.DEGREES)));
        assertEquals("5,5 rad",
                formatter.format(new Angle(value, AngleUnit.RADIANS)));
    }

    @Test
    public void testFormatAngleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 º", formatter.format(new Angle(value, AngleUnit.DEGREES), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 rad", formatter.format(new Angle(value, AngleUnit.RADIANS), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAndConvertNumber() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.formatAndConvert(new BigDecimal("5.50"),
                AngleUnit.RADIANS));
    }

    @Test
    public void testFormatAndConvertDouble() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º", formatter.formatAndConvert(5.50, AngleUnit.DEGREES));
        assertEquals("5,5 rad", formatter.formatAndConvert(5.50, AngleUnit.RADIANS));
    }

    @Test
    public void testFormatAndConvertAngle() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.DEGREES)));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(new Angle(5.50, AngleUnit.RADIANS)));
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º",
                formatter.formatAndConvert(new BigDecimal("5.50"),
                        AngleUnit.DEGREES, UnitSystem.METRIC));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(new BigDecimal("5.50"),
                        AngleUnit.RADIANS, UnitSystem.METRIC));

        assertEquals("5,5 º",
                formatter.formatAndConvert(new BigDecimal("5.50"),
                        AngleUnit.DEGREES, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(new BigDecimal("5.50"),
                        AngleUnit.RADIANS, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 º",
                formatter.formatAndConvert(5.50, AngleUnit.DEGREES, UnitSystem.METRIC));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(5.50, AngleUnit.RADIANS, UnitSystem.METRIC));

        assertEquals("5,5 º",
                formatter.formatAndConvert(5.50, AngleUnit.DEGREES, UnitSystem.IMPERIAL));
        assertEquals("5,5 rad",
                formatter.formatAndConvert(5.50, AngleUnit.RADIANS, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertAngleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final AngleFormatter formatter = new AngleFormatter(l);
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
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    public void testGetSetRoundingMode() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
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

        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        // check correctness
        assertEquals("{0}{1}", formatter.getValueAndUnitFormatPattern());

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
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new AngleFormatter(
                new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
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
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 rad";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º";
        Angle a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, a.getUnit());

        text = "5,5 rad";
        a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.RADIANS, a.getUnit());

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
        assertEquals(AngleUnit.DEGREES, formatter.findUnit(text));

        text = "5,5 rad";
        assertEquals(AngleUnit.RADIANS, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final AngleFormatter formatter = new AngleFormatter();

        assertEquals(AngleFormatter.DEGREE,
                formatter.getUnitSymbol(AngleUnit.DEGREES));
        assertEquals(AngleFormatter.RADIAN,
                formatter.getUnitSymbol(AngleUnit.RADIANS));
    }

    @Test
    public void testFormatDegreesAndMinutes() {
        final AngleFormatter formatter = new AngleFormatter(
                new Locale("es", "ES"));

        final Angle angle1 = new Angle(45.5, AngleUnit.DEGREES);
        final Angle angle2 = new Angle(-45.5, AngleUnit.DEGREES);

        // check
        assertEquals("45º 30'", formatter.formatDegreesAndMinutes(angle1));
        assertEquals("-46º 30'", formatter.formatDegreesAndMinutes(angle2));
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
        assertEquals("45º 30' 0\"", formatter.formatDegreesMinutesAndSeconds(angle1));
        assertEquals("-46º 30' 0\"", formatter.formatDegreesMinutesAndSeconds(angle2));
        assertEquals("45º 32' 60\"", formatter.formatDegreesMinutesAndSeconds(angle3));
        assertEquals("45º 33' 18\"", formatter.formatDegreesMinutesAndSeconds(angle4));
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

        assertEquals("45º 30'", str1);
        assertEquals("-46º 30'", str2);

        final Angle angle1b = formatter.parseDegreesAndMinutes(str1);
        final Angle angle2b = formatter.parseDegreesAndMinutes(str2);

        assertEquals(AngleUnit.DEGREES, angle1b.getUnit());
        assertEquals(45.5, angle1b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle2b.getUnit());
        assertEquals(-45.5, angle2b.getValue().doubleValue(), ERROR);

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

        assertEquals("45º 30' 0\"", str1);
        assertEquals("-46º 30' 0\"", str2);
        assertEquals("45º 32' 60\"", str3);
        assertEquals("45º 33' 18\"", str4);

        final Angle angle1b = formatter.parseDegreesMinutesAndSeconds(str1);
        final Angle angle2b = formatter.parseDegreesMinutesAndSeconds(str2);
        final Angle angle3b = formatter.parseDegreesMinutesAndSeconds(str3);
        final Angle angle4b = formatter.parseDegreesMinutesAndSeconds(str4);

        assertEquals(AngleUnit.DEGREES, angle1b.getUnit());
        assertEquals(45.5, angle1b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle2b.getUnit());
        assertEquals(-45.5, angle2b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle3b.getUnit());
        assertEquals(45.55, angle3b.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, angle4b.getUnit());
        assertEquals(45.555, angle4b.getValue().doubleValue(), ERROR);

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
