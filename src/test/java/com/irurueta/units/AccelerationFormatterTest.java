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

public class AccelerationFormatterTest {

    public AccelerationFormatterTest() { }

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
        AccelerationFormatter formatter = new AccelerationFormatter();

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
        formatter = new AccelerationFormatter(locale);

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
            formatter = new AccelerationFormatter(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(formatter);
    }

    @Test
    public void testClone() {
        AccelerationFormatter formatter1 = new AccelerationFormatter();
        AccelerationFormatter formatter2 = (AccelerationFormatter) formatter1.clone();

        //check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        //test after initializing internal number format
        assertNotNull(formatter1.format(0.5, AccelerationUnit.METERS_PER_SQUARED_SECOND,
                new StringBuffer(), new FieldPosition(0)));
        AccelerationFormatter formatter3 = (AccelerationFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        AccelerationFormatter formatter1 = new AccelerationFormatter(Locale.ENGLISH);
        AccelerationFormatter formatter2 = new AccelerationFormatter(Locale.ENGLISH);
        AccelerationFormatter formatter3 = new AccelerationFormatter(Locale.FRENCH);

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
        AccelerationFormatter formatter1 = new AccelerationFormatter(Locale.ENGLISH);
        AccelerationFormatter formatter2 = new AccelerationFormatter(Locale.ENGLISH);
        AccelerationFormatter formatter3 = new AccelerationFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.METERS_PER_SQUARED_SECOND), "5,5 m/s²");
        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.G), "5,5 g₀");
        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.FEET_PER_SQUARED_SECOND), "5,5 ft/s²");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.METERS_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 m/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.G, buffer,
                new FieldPosition(0)).toString(), "5,5 g₀");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                AccelerationUnit.FEET_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s²");
    }

    @Test
    public void testFormatDouble() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals(formatter.format(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND), "5,5 m/s²");
        assertEquals(formatter.format(value,
                AccelerationUnit.G), "5,5 g₀");
        assertEquals(formatter.format(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND), "5,5 ft/s²");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 m/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AccelerationUnit.G, buffer,
                new FieldPosition(0)).toString(), "5,5 g₀");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s²");
    }

    @Test
    public void testFormatAcceleration() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND)), "5,5 m/s²");
        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.G)), "5,5 g₀");
        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND)), "5,5 ft/s²");
    }

    @Test
    public void testFormatAccelerationAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 m/s²");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.G), buffer,
                new FieldPosition(0)).toString(), "5,5 g₀");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Acceleration(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s²");
    }

    @Test
    public void testFormatAndConvertNumber() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.METERS_PER_SQUARED_SECOND), "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(1),
                AccelerationUnit.G), "9,81 m/s²");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.FEET_PER_SQUARED_SECOND), "5.5 ft/s²");
    }

    @Test
    public void testFormatAndConvertDouble() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND), "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(1.0,
                AccelerationUnit.G), "9,81 m/s²");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND), "5.5 ft/s²");
    }

    @Test
    public void testFormatAndConvertAcceleration() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND)), "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(new Acceleration(1.0,
                AccelerationUnit.G)), "9,81 m/s²");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND)), "5.5 ft/s²");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.METRIC),
                "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(1.0),
                AccelerationUnit.G, UnitSystem.METRIC), "9,81 m/s²");

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.METRIC),
                "1,68 m/s²");


        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.IMPERIAL),
                "5.5 ft/s²");

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.5),
                AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL),
                "18.04 ft/s²");
        assertEquals(formatter.formatAndConvert(new BigDecimal(1.0),
                AccelerationUnit.G, UnitSystem.IMPERIAL), "32.17 ft/s²");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.METRIC),
                "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(1.0,
                AccelerationUnit.G, UnitSystem.METRIC), "9,81 m/s²");

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.METRIC),
                "1,68 m/s²");


        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.IMPERIAL),
                "5.5 ft/s²");

        assertEquals(formatter.formatAndConvert(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL),
                "18.04 ft/s²");
        assertEquals(formatter.formatAndConvert(1.0,
                AccelerationUnit.G, UnitSystem.IMPERIAL), "32.17 ft/s²");
    }

    @Test
    public void testFormatAndConvertAccelerationAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND), UnitSystem.METRIC),
                "5,5 m/s²");
        assertEquals(formatter.formatAndConvert(new Acceleration(1.0,
                AccelerationUnit.G), UnitSystem.METRIC), "9,81 m/s²");

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND), UnitSystem.METRIC),
                "1,68 m/s²");


        //test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.FEET_PER_SQUARED_SECOND), UnitSystem.IMPERIAL),
                "5.5 ft/s²");

        assertEquals(formatter.formatAndConvert(new Acceleration(5.5,
                AccelerationUnit.METERS_PER_SQUARED_SECOND), UnitSystem.IMPERIAL),
                "18.04 ft/s²");
        assertEquals(formatter.formatAndConvert(new Acceleration(1.0,
                AccelerationUnit.G), UnitSystem.IMPERIAL), "32.17 ft/s²");
    }

    @Test
    public void testFormatAndConvertMetric() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5.5),
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                "5,5 m/s²");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(1.0),
                AccelerationUnit.G), "9,81 m/s²");

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5.5),
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                "1,68 m/s²");
    }

    @Test
    public void testFormatAndConvertImperial() {
        Locale l = new Locale("en", "US");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertImperial(new BigDecimal(5.5),
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                "5.5 ft/s²");

        assertEquals(formatter.formatAndConvertImperial(new BigDecimal(5.5),
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                "18.04 ft/s²");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal(1.0),
                AccelerationUnit.G), "32.17 ft/s²");
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = AccelerationFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        //set new value
        formatter.setMaximumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        //set new value
        formatter.setMaximumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        //set new value
        formatter.setMinimumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        //set new value
        formatter.setMinimumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        //set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        //check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        AccelerationFormatter formatter = new AccelerationFormatter();

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
        AccelerationFormatter formatter = new AccelerationFormatter();

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
        AccelerationFormatter formatter = new AccelerationFormatter();

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
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new AccelerationFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertTrue(formatter.isValidUnit("m/s²"));
        assertTrue(formatter.isValidUnit("m/s² "));

        assertTrue(formatter.isValidUnit("g₀"));
        assertTrue(formatter.isValidUnit("g₀ "));

        assertTrue(formatter.isValidUnit("ft/s²"));
        assertTrue(formatter.isValidUnit("ft/s² "));
    }

    @Test
    public void testIsValidMeasurement() {
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 g₀";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ft/s²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 g₀";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ft/s²";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 g₀";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ft/s²";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 g₀";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 ft/s²";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        Acceleration a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AccelerationUnit.METERS_PER_SQUARED_SECOND);

        text = "5,5 g₀";
        a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AccelerationUnit.G);

        text = "5,5 ft/s²";
        a = formatter.parse(text);
        assertEquals(a.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(a.getUnit(), AccelerationUnit.FEET_PER_SQUARED_SECOND);

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
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertEquals(formatter.findUnit(text),
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        text = "5,5 g₀";
        assertEquals(formatter.findUnit(text), AccelerationUnit.G);

        text = "5,5 ft/s²";
        assertEquals(formatter.findUnit(text),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getUnitSymbol(AccelerationUnit.METERS_PER_SQUARED_SECOND),
                AccelerationFormatter.METERS_PER_SQUARED_SECOND);
        assertEquals(formatter.getUnitSymbol(AccelerationUnit.G),
                AccelerationFormatter.G);
        assertEquals(formatter.getUnitSymbol(AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationFormatter.FEET_PER_SQUARED_SECOND);
    }
}
