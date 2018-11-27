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

public class SpeedFormatterTest {

    public SpeedFormatterTest() { }

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
        SpeedFormatter formatter = new SpeedFormatter();

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
        formatter = new SpeedFormatter(locale);

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
            formatter = new SpeedFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(formatter);


        //test copy constructor
        formatter = new SpeedFormatter(locale);
        SpeedFormatter formatter2 = new SpeedFormatter(formatter);

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
            formatter = new SpeedFormatter((SpeedFormatter)null);
            fail("NullPointerException expected but not thrown");
        } catch (NullPointerException ignore) { }
        assertNull(formatter);
    }

    @Test
    public void testClone() {
        SpeedFormatter formatter1 = new SpeedFormatter();
        SpeedFormatter formatter2 = (SpeedFormatter) formatter1.clone();

        //check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        //test after initializing internal number format
        assertNotNull(formatter1.format(0.5, SpeedUnit.METERS_PER_SECOND,
                new StringBuffer(), new FieldPosition(0)));
        SpeedFormatter formatter3 = (SpeedFormatter)formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        SpeedFormatter formatter1 = new SpeedFormatter(Locale.ENGLISH);
        SpeedFormatter formatter2 = new SpeedFormatter(Locale.ENGLISH);
        SpeedFormatter formatter3 = new SpeedFormatter(Locale.FRENCH);

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
        SpeedFormatter formatter1 = new SpeedFormatter(Locale.ENGLISH);
        SpeedFormatter formatter2 = new SpeedFormatter(Locale.ENGLISH);
        SpeedFormatter formatter3 = new SpeedFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.METERS_PER_SECOND), "5,5 m/s");
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.KILOMETERS_PER_HOUR), "5,5 Km/h");
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.KILOMETERS_PER_SECOND), "5,5 Km/s");
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.FEET_PER_SECOND), "5,5 ft/s");
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.MILES_PER_HOUR), "5,5 mph");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.METERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 m/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.KILOMETERS_PER_HOUR, buffer,
                new FieldPosition(0)).toString(), "5,5 Km/h");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.KILOMETERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 Km/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.FEET_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                SpeedUnit.MILES_PER_HOUR, buffer,
                new FieldPosition(0)).toString(), "5,5 mph");
    }

    @Test
    public void testFormatDouble() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        assertEquals(formatter.format(value, SpeedUnit.METERS_PER_SECOND),
                "5,5 m/s");
        assertEquals(formatter.format(value, SpeedUnit.KILOMETERS_PER_HOUR),
                "5,5 Km/h");
        assertEquals(formatter.format(value, SpeedUnit.KILOMETERS_PER_SECOND),
                "5,5 Km/s");
        assertEquals(formatter.format(value, SpeedUnit.FEET_PER_SECOND),
                "5,5 ft/s");
        assertEquals(formatter.format(value, SpeedUnit.MILES_PER_HOUR),
                "5,5 mph");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                SpeedUnit.METERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 m/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                SpeedUnit.KILOMETERS_PER_HOUR, buffer,
                new FieldPosition(0)).toString(), "5,5 Km/h");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                SpeedUnit.KILOMETERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 Km/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                SpeedUnit.FEET_PER_SECOND, buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                SpeedUnit.MILES_PER_HOUR, buffer,
                new FieldPosition(0)).toString(), "5,5 mph");
    }

    @Test
    public void testFormatSpeed() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        assertEquals(formatter.format(new Speed(value, SpeedUnit.METERS_PER_SECOND)),
                "5,5 m/s");
        assertEquals(formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR)),
                "5,5 Km/h");
        assertEquals(formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_SECOND)),
                "5,5 Km/s");
        assertEquals(formatter.format(new Speed(value, SpeedUnit.FEET_PER_SECOND)),
                "5,5 ft/s");
        assertEquals(formatter.format(new Speed(value, SpeedUnit.MILES_PER_HOUR)),
                "5,5 mph");
    }

    @Test
    public void testFormatSpeedAndStringBuffer() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Speed(value,
                SpeedUnit.METERS_PER_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 m/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Speed(value,
                SpeedUnit.KILOMETERS_PER_HOUR), buffer,
                new FieldPosition(0)).toString(), "5,5 Km/h");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Speed(value,
                SpeedUnit.KILOMETERS_PER_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 Km/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Speed(value,
                        SpeedUnit.FEET_PER_SECOND), buffer,
                new FieldPosition(0)).toString(), "5,5 ft/s");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Speed(value,
                SpeedUnit.MILES_PER_HOUR), buffer,
                new FieldPosition(0)).toString(), "5,5 mph");
    }

    @Test
    public void testFormatAndConvertNumber() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(0.1),
                SpeedUnit.METERS_PER_SECOND), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(new BigDecimal(3.6),
                SpeedUnit.METERS_PER_SECOND), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(new BigDecimal(3600.0),
                SpeedUnit.METERS_PER_SECOND), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR), "104,61 Km/h");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(0.5),
                SpeedUnit.MILES_PER_HOUR), "0.73 ft/s");
        assertEquals(formatter.formatAndConvert(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR), "65 mph");
    }

    @Test
    public void testFormatAndConvertDouble() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(0.1,
                SpeedUnit.METERS_PER_SECOND), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(3.6,
                SpeedUnit.METERS_PER_SECOND), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(3600.0,
                SpeedUnit.METERS_PER_SECOND), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(65.0,
                SpeedUnit.MILES_PER_HOUR), "104,61 Km/h");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(0.5,
                SpeedUnit.MILES_PER_HOUR), "0.73 ft/s");
        assertEquals(formatter.formatAndConvert(65.0,
                SpeedUnit.MILES_PER_HOUR), "65 mph");
    }

    @Test
    public void testFormatAndConvertSpeed() {
        //test for metric system
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Speed(0.1,
                SpeedUnit.METERS_PER_SECOND)), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(new Speed(3.6,
                SpeedUnit.METERS_PER_SECOND)), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(new Speed(3600.0,
                SpeedUnit.METERS_PER_SECOND)), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(new Speed(65.0,
                SpeedUnit.MILES_PER_HOUR)), "104,61 Km/h");

        //test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Speed(0.5,
                SpeedUnit.MILES_PER_HOUR)), "0.73 ft/s");
        assertEquals(formatter.formatAndConvert(new Speed(65.0,
                SpeedUnit.MILES_PER_HOUR)), "65 mph");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(0.1),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(new BigDecimal(3.6),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(new BigDecimal(3600.0),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.METRIC), "104,61 Km/h");

        assertEquals(formatter.formatAndConvert(new BigDecimal(0.5),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL), "0,73 ft/s");
        assertEquals(formatter.formatAndConvert(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL), "65 mph");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(0.1,
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(3.6,
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(3600.0,
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(65.0,
                SpeedUnit.MILES_PER_HOUR, UnitSystem.METRIC), "104,61 Km/h");

        assertEquals(formatter.formatAndConvert(0.5,
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL), "0,73 ft/s");
        assertEquals(formatter.formatAndConvert(65.0,
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL), "65 mph");
    }

    @Test
    public void testFormatAndConvertSpeedAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Speed(0.1,
                SpeedUnit.METERS_PER_SECOND), UnitSystem.METRIC), "0,1 m/s");
        assertEquals(formatter.formatAndConvert(new Speed(3.6,
                SpeedUnit.METERS_PER_SECOND), UnitSystem.METRIC), "12,96 Km/h");
        assertEquals(formatter.formatAndConvert(new Speed(3600.0,
                SpeedUnit.METERS_PER_SECOND), UnitSystem.METRIC), "3,6 Km/s");

        assertEquals(formatter.formatAndConvert(new Speed(65.0,
                SpeedUnit.MILES_PER_HOUR), UnitSystem.METRIC), "104,61 Km/h");

        assertEquals(formatter.formatAndConvert(new Speed(0.5,
                SpeedUnit.MILES_PER_HOUR), UnitSystem.IMPERIAL), "0,73 ft/s");
        assertEquals(formatter.formatAndConvert(new Speed(65.0,
                SpeedUnit.MILES_PER_HOUR), UnitSystem.IMPERIAL), "65 mph");
    }

    @Test
    public void testFormatAndConvertMetric() {
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(0.1),
                SpeedUnit.METERS_PER_SECOND), "0,1 m/s");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(3.6),
                SpeedUnit.METERS_PER_SECOND), "12,96 Km/h");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(3600.0),
                SpeedUnit.METERS_PER_SECOND), "3,6 Km/s");

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(0.5),
                SpeedUnit.MILES_PER_HOUR), "0,22 m/s");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR), "104,61 Km/h");
    }

    @Test
    public void testFormatAndConvertImperial() {
        Locale l = new Locale("es", "ES");

        SpeedFormatter formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertImperial(new BigDecimal(0.5),
                SpeedUnit.MILES_PER_HOUR), "0,73 ft/s");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal(65.0),
                SpeedUnit.MILES_PER_HOUR), "65 mph");
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = SpeedFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        //set new value
        formatter.setMaximumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        //set new value
        formatter.setMaximumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        //set new value
        formatter.setMinimumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        //set new value
        formatter.setMinimumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        //set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        //check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        SpeedFormatter formatter = new SpeedFormatter();

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
        SpeedFormatter formatter = new SpeedFormatter();

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
        SpeedFormatter formatter = new SpeedFormatter();

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
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new SpeedFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertTrue(formatter.isValidUnit("m/s"));
        assertTrue(formatter.isValidUnit("m/s "));

        assertTrue(formatter.isValidUnit("Km/h"));
        assertTrue(formatter.isValidUnit("Km/h "));

        assertTrue(formatter.isValidUnit("Km/s"));
        assertTrue(formatter.isValidUnit("Km/s "));

        assertTrue(formatter.isValidUnit("mph"));
        assertTrue(formatter.isValidUnit("mph "));
    }

    @Test
    public void testIsValidMeasurement() {
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km/h";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ft/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mph";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km/h";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ft/s";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 mph";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km/h";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ft/s";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 mph";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 Km/h";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 Km/s";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 ft/s";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 mph";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        Speed s = formatter.parse(text);
        assertEquals(s.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(s.getUnit(), SpeedUnit.METERS_PER_SECOND);

        text = "5,5 Km/h";
        s = formatter.parse(text);
        assertEquals(s.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(s.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);

        text = "5,5 Km/s";
        s = formatter.parse(text);
        assertEquals(s.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(s.getUnit(), SpeedUnit.KILOMETERS_PER_SECOND);

        text = "5,5 ft/s";
        s = formatter.parse(text);
        assertEquals(s.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(s.getUnit(), SpeedUnit.FEET_PER_SECOND);

        text = "5,5 mph";
        s = formatter.parse(text);
        assertEquals(s.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(s.getUnit(), SpeedUnit.MILES_PER_HOUR);

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
        SpeedFormatter formatter = new SpeedFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s";
        assertEquals(formatter.findUnit(text), SpeedUnit.METERS_PER_SECOND);

        text = "5,5 Km/h";
        assertEquals(formatter.findUnit(text), SpeedUnit.KILOMETERS_PER_HOUR);

        text = "5,5 Km/s";
        assertEquals(formatter.findUnit(text), SpeedUnit.KILOMETERS_PER_SECOND);

        text = "5,5 ft/s";
        assertEquals(formatter.findUnit(text), SpeedUnit.FEET_PER_SECOND);

        text = "5,5 mph";
        assertEquals(formatter.findUnit(text), SpeedUnit.MILES_PER_HOUR);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        SpeedFormatter formatter = new SpeedFormatter();

        assertEquals(formatter.getUnitSymbol(SpeedUnit.METERS_PER_SECOND),
                SpeedFormatter.METERS_PER_SECOND);
        assertEquals(formatter.getUnitSymbol(SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedFormatter.KILOMETERS_PER_HOUR);
        assertEquals(formatter.getUnitSymbol(SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedFormatter.KILOMETERS_PER_SECOND);
        assertEquals(formatter.getUnitSymbol(SpeedUnit.FEET_PER_SECOND),
                SpeedFormatter.FEET_PER_SECOND);
        assertEquals(formatter.getUnitSymbol(SpeedUnit.MILES_PER_HOUR),
                SpeedFormatter.MILES_PER_HOUR);
    }
}
