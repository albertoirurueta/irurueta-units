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

public class AccelerationFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        AccelerationFormatter formatter = new AccelerationFormatter();

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
        assertEquals(NumberFormat.getInstance().getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getDefault(), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new AccelerationFormatter(locale);

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
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        formatter = null;
        try {
            formatter = new AccelerationFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new AccelerationFormatter(locale);
        final AccelerationFormatter formatter2 = new AccelerationFormatter(formatter);

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
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter2.getValueAndUnitFormatPattern());

        formatter = null;
        try {
            //noinspection ConstantConditions
            formatter = new AccelerationFormatter((AccelerationFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final AccelerationFormatter formatter1 = new AccelerationFormatter();
        final AccelerationFormatter formatter2 = (AccelerationFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, AccelerationUnit.METERS_PER_SQUARED_SECOND,
                new StringBuffer(), new FieldPosition(0)));
        final AccelerationFormatter formatter3 = (AccelerationFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final AccelerationFormatter formatter1 = new AccelerationFormatter(Locale.ENGLISH);
        final AccelerationFormatter formatter2 = new AccelerationFormatter(Locale.ENGLISH);
        final AccelerationFormatter formatter3 = new AccelerationFormatter(Locale.FRENCH);

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
        final AccelerationFormatter formatter1 = new AccelerationFormatter(Locale.ENGLISH);
        final AccelerationFormatter formatter2 = new AccelerationFormatter(Locale.ENGLISH);
        final AccelerationFormatter formatter3 = new AccelerationFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals("5,5 m/s²",
                formatter.format(new BigDecimal(value), AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("5,5 g₀", formatter.format(new BigDecimal(value), AccelerationUnit.G));
        assertEquals("5,5 ft/s²",
                formatter.format(new BigDecimal(value), AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 m/s²",
                formatter.format(new BigDecimal(value), AccelerationUnit.METERS_PER_SQUARED_SECOND, buffer,
                        new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g₀",
                formatter.format(new BigDecimal(value), AccelerationUnit.G, buffer,
                        new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s²", formatter.format(new BigDecimal(value),
                AccelerationUnit.FEET_PER_SQUARED_SECOND, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals("5,5 m/s²",
                formatter.format(value, AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("5,5 g₀",
                formatter.format(value, AccelerationUnit.G));
        assertEquals("5,5 ft/s²",
                formatter.format(value, AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 m/s²",
                formatter.format(value, AccelerationUnit.METERS_PER_SQUARED_SECOND, buffer,
                        new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g₀",
                formatter.format(value, AccelerationUnit.G, buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s²",
                formatter.format(value, AccelerationUnit.FEET_PER_SQUARED_SECOND, buffer,
                        new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAcceleration() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        assertEquals("5,5 m/s²",
                formatter.format(new Acceleration(value, AccelerationUnit.METERS_PER_SQUARED_SECOND)));
        assertEquals("5,5 g₀",
                formatter.format(new Acceleration(value, AccelerationUnit.G)));
        assertEquals("5,5 ft/s²",
                formatter.format(new Acceleration(value, AccelerationUnit.FEET_PER_SQUARED_SECOND)));
    }

    @Test
    public void testFormatAccelerationAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 m/s²",
                formatter.format(new Acceleration(value, AccelerationUnit.METERS_PER_SQUARED_SECOND),
                        buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g₀",
                formatter.format(new Acceleration(value, AccelerationUnit.G), buffer,
                        new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s²",
                formatter.format(new Acceleration(value, AccelerationUnit.FEET_PER_SQUARED_SECOND), buffer,
                        new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAndConvertNumber() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("9,81 m/s²",
                formatter.formatAndConvert(new BigDecimal(1), AccelerationUnit.G));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }

    @Test
    public void testFormatAndConvertDouble() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(5.5, AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("9,81 m/s²",
                formatter.formatAndConvert(1.0, AccelerationUnit.G));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(5.5, AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }

    @Test
    public void testFormatAndConvertAcceleration() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND)));
        assertEquals("9,81 m/s²",
                formatter.formatAndConvert(new Acceleration(1.0, AccelerationUnit.G)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND)));
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.METRIC));
        assertEquals("9,81 m/s²", formatter.formatAndConvert(new BigDecimal("1.0"),
                AccelerationUnit.G, UnitSystem.METRIC));

        assertEquals("1,68 m/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.METRIC));


        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));

        assertEquals("18.04 ft/s²",
                formatter.formatAndConvert(new BigDecimal("5.5"),
                        AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
        assertEquals("32.17 ft/s²",
                formatter.formatAndConvert(new BigDecimal("1.0"),
                        AccelerationUnit.G, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(5.5,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.METRIC));
        assertEquals("9,81 m/s²", formatter.formatAndConvert(1.0,
                AccelerationUnit.G, UnitSystem.METRIC));

        assertEquals("1,68 m/s²",
                formatter.formatAndConvert(5.5,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.METRIC));


        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(5.5,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));

        assertEquals("18.04 ft/s²",
                formatter.formatAndConvert(5.5,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND, UnitSystem.IMPERIAL));
        assertEquals("32.17 ft/s²",
                formatter.formatAndConvert(1.0, AccelerationUnit.G, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertAccelerationAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND), UnitSystem.METRIC));
        assertEquals("9,81 m/s²",
                formatter.formatAndConvert(new Acceleration(1.0, AccelerationUnit.G),
                        UnitSystem.METRIC));

        assertEquals("1,68 m/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND), UnitSystem.METRIC));


        // test for imperial system
        l = new Locale("en", "US");

        formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND), UnitSystem.IMPERIAL));

        assertEquals("18.04 ft/s²",
                formatter.formatAndConvert(new Acceleration(5.5,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND), UnitSystem.IMPERIAL));
        assertEquals("32.17 ft/s²",
                formatter.formatAndConvert(new Acceleration(1.0, AccelerationUnit.G),
                        UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertMetric() {
        final Locale l = new Locale("es", "ES");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 m/s²",
                formatter.formatAndConvertMetric(new BigDecimal("5.5"),
                        AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("9,81 m/s²",
                formatter.formatAndConvertMetric(new BigDecimal("1.0"), AccelerationUnit.G));

        assertEquals("1,68 m/s²",
                formatter.formatAndConvertMetric(new BigDecimal("5.5"),
                        AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }

    @Test
    public void testFormatAndConvertImperial() {
        final Locale l = new Locale("en", "US");

        final AccelerationFormatter formatter = new AccelerationFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 ft/s²",
                formatter.formatAndConvertImperial(new BigDecimal("5.5"),
                        AccelerationUnit.FEET_PER_SQUARED_SECOND));

        assertEquals("18.04 ft/s²",
                formatter.formatAndConvertImperial(new BigDecimal("5.5"),
                        AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals("32.17 ft/s²",
                formatter.formatAndConvertImperial(new BigDecimal("1.0"),
                        AccelerationUnit.G));
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = AccelerationFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    public void testGetSetRoundingMode() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    public void testIsSetGroupingUsed() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

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
        final AccelerationFormatter formatter = new AccelerationFormatter();

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
        final AccelerationFormatter formatter = new AccelerationFormatter();

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
        AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new AccelerationFormatter(
                new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    public void testIsValidUnit() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertTrue(formatter.isValidUnit("m/s²"));
        assertTrue(formatter.isValidUnit("m/s² "));

        assertTrue(formatter.isValidUnit("g₀"));
        assertTrue(formatter.isValidUnit("g₀ "));

        assertTrue(formatter.isValidUnit("ft/s²"));
        assertTrue(formatter.isValidUnit("ft/s² "));
    }

    @Test
    public void testIsValidMeasurement() {
        final AccelerationFormatter formatter = new AccelerationFormatter(
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
        final AccelerationFormatter formatter = new AccelerationFormatter(
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
        final AccelerationFormatter formatter = new AccelerationFormatter(
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
        final AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 g₀";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ft/s²";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        Acceleration a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a.getUnit());

        text = "5,5 g₀";
        a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AccelerationUnit.G, a.getUnit());

        text = "5,5 ft/s²";
        a = formatter.parse(text);
        assertEquals(5.5, a.getValue().doubleValue(), 0.0);
        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, a.getUnit());

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
        final AccelerationFormatter formatter = new AccelerationFormatter(
                new Locale("es", "ES"));

        String text = "5,5 m/s²";
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND,
                formatter.findUnit(text));

        text = "5,5 g₀";
        assertEquals(AccelerationUnit.G, formatter.findUnit(text));

        text = "5,5 ft/s²";
        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND,
                formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final AccelerationFormatter formatter = new AccelerationFormatter();

        assertEquals(AccelerationFormatter.METERS_PER_SQUARED_SECOND,
                formatter.getUnitSymbol(AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals(AccelerationFormatter.G,
                formatter.getUnitSymbol(AccelerationUnit.G));
        assertEquals(AccelerationFormatter.FEET_PER_SQUARED_SECOND,
                formatter.getUnitSymbol(AccelerationUnit.FEET_PER_SQUARED_SECOND));
    }
}
