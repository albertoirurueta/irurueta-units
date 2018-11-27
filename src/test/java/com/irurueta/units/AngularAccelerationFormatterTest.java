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

public class AngularAccelerationFormatterTest {

    public AngularAccelerationFormatterTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }

    @Before
    public void setUp() { }

    @After
    public void tearDown() { }

    @Test
    public void testConstructor() {
        //test empty constructor
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        //check
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

        //test constructor with locale
        Locale locale = new Locale("es", "ES");
        formatter = new AngularAccelerationFormatter(locale);

        //check
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


        //force IllegalArgumentException
        formatter = null;
        try {
            formatter = new AngularAccelerationFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(formatter);


        //test copy constructor
        formatter = new AngularAccelerationFormatter(locale);
        AngularAccelerationFormatter formatter2 = new AngularAccelerationFormatter(formatter);

        //check
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
            //noinspection all
            formatter = new AngularAccelerationFormatter((AngularAccelerationFormatter)null);
            fail("NullPointerException expected but not thrown");
        } catch (NullPointerException ignore) { }
        assertNull(formatter);
    }

    @Test
    public void testEquals() {
        AngularAccelerationFormatter formatter1 = new AngularAccelerationFormatter(
                Locale.ENGLISH);
        AngularAccelerationFormatter formatter2 = new AngularAccelerationFormatter(
                Locale.ENGLISH);
        AngularAccelerationFormatter formatter3 = new AngularAccelerationFormatter(
                Locale.FRENCH);

        //check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        //noinspection all
        assertFalse(formatter1.equals(null));
    }

    @Test
    public void testHashCode() {
        AngularAccelerationFormatter formatter1 = new AngularAccelerationFormatter(
                Locale.ENGLISH);
        AngularAccelerationFormatter formatter2 = new AngularAccelerationFormatter(
                Locale.ENGLISH);
        AngularAccelerationFormatter formatter3 = new AngularAccelerationFormatter(
                Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                "5,5 º/s²");
        assertEquals(formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 º/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 rad/s²");
    }

    @Test
    public void testFormatDouble() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        assertEquals(formatter.format(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                "5,5 º/s²");
        assertEquals(formatter.format(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 º/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 rad/s²");
    }

    @Test
    public void testFormatAngularAcceleration() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        assertEquals(formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND)),
                "5,5 º/s²");
        assertEquals(formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND)),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatAngularAccelerationAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 º/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConverNumber() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConvertDouble() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConvertAngularAcceleration() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND)),
                "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND)),
                "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                UnitSystem.METRIC), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                UnitSystem.METRIC), "5,5 rad/s²");

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                UnitSystem.IMPERIAL), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                UnitSystem.IMPERIAL), "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                UnitSystem.METRIC), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                UnitSystem.METRIC), "5,5 rad/s²");

        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                UnitSystem.IMPERIAL), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                UnitSystem.IMPERIAL), "5,5 rad/s²");
    }

    @Test
    public void testFormatAndConvertAngularAccelerationAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                UnitSystem.METRIC), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                UnitSystem.METRIC), "5,5 rad/s²");

        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                UnitSystem.IMPERIAL), "5,5 º/s²");
        assertEquals(formatter.formatAndConvert(new AngularAcceleration(5.50,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                UnitSystem.IMPERIAL), "5,5 rad/s²");
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = AngularAccelerationFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        //set new value
        formatter.setMaximumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        //set new value
        formatter.setMaximumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        //set new value
        formatter.setMinimumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        //set new value
        formatter.setMinimumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        //set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        //check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());

        //set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        //check correctness
        assertEquals(formatter.isGroupingUsed(),
                !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    public void testIsSetParseIntegerOnly() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());

        //set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        //check correctness
        assertEquals(formatter.isParseIntegerOnly(),
                !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    public void testGetSetValueAndUnitFormatPattern() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        //new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        //check correctness
        assertEquals(formatter.getValueAndUnitFormatPattern(), "{0}{1}");

        //force IllegalArgumentException
        try {
            formatter.setValueAndUnitFormatPattern(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testGetUnitSystem() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new AngularAccelerationFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertTrue(formatter.isValidUnit("º/s²"));
        assertTrue(formatter.isValidUnit("º/s² "));

        assertTrue(formatter.isValidUnit("rad/s²"));
        assertTrue(formatter.isValidUnit("rad/s² "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 rad/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 rad/s²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 s²";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 rad/s²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 s²";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 rad/s²";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 s²";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        AngularAcceleration a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        text = "5,5 rad/s²";
        a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //Force UnknownUnitException
        text = "5,5 s";
        try {
            formatter.parse(text);
            fail("UnknownUnitException expected but not thrown");
        } catch (UnknownUnitException ignore) { }

        //Force ParseException
        try {
            formatter.parse("m");
            fail("ParseException expected but not thrown");
        } catch (ParseException ignore) { }
    }

    @Test
    public void testFindUnit() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 º/s²";
        assertEquals(formatter.findUnit(text),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        text = "5,5 rad/s²";
        assertEquals(formatter.findUnit(text),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        AngularAccelerationFormatter formatter = new AngularAccelerationFormatter();

        assertEquals(formatter.getUnitSymbol(
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                AngularAccelerationFormatter.DEGREES_PER_SQUARED_SECOND);
        assertEquals(formatter.getUnitSymbol(
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                AngularAccelerationFormatter.RADIANS_PER_SQUARED_SECOND);
    }
}
