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

public class DistanceFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        DistanceFormatter formatter = new DistanceFormatter();

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
        assertEquals(NumberFormat.getInstance().isGroupingUsed(),
                formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new DistanceFormatter(locale);

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
            formatter = new DistanceFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new DistanceFormatter(locale);
        final DistanceFormatter formatter2 = new DistanceFormatter(formatter);

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
        assertEquals(formatter2.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter2.getValueAndUnitFormatPattern());

        formatter = null;
        try {
            // noinspection ConstantConditions
            formatter = new DistanceFormatter((DistanceFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final DistanceFormatter formatter1 = new DistanceFormatter();
        final DistanceFormatter formatter2 = (DistanceFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, DistanceUnit.METER,
                new StringBuffer(), new FieldPosition(0)));
        final DistanceFormatter formatter3 = (DistanceFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final DistanceFormatter formatter1 = new DistanceFormatter(Locale.ENGLISH);
        final DistanceFormatter formatter2 = new DistanceFormatter(Locale.ENGLISH);
        final DistanceFormatter formatter3 = new DistanceFormatter(Locale.FRENCH);

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
        final DistanceFormatter formatter1 = new DistanceFormatter(Locale.ENGLISH);
        final DistanceFormatter formatter2 = new DistanceFormatter(Locale.ENGLISH);
        final DistanceFormatter formatter3 = new DistanceFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        assertEquals("5,5 mm", formatter.format(new BigDecimal(value),
                DistanceUnit.MILLIMETER));
        assertEquals("5,5 cm", formatter.format(new BigDecimal(value),
                DistanceUnit.CENTIMETER));
        assertEquals("5,5 m", formatter.format(new BigDecimal(value),
                DistanceUnit.METER));
        assertEquals("5,5 Km", formatter.format(new BigDecimal(value),
                DistanceUnit.KILOMETER));
        assertEquals("5,5 in", formatter.format(new BigDecimal(value),
                DistanceUnit.INCH));
        assertEquals("5,5 ft", formatter.format(new BigDecimal(value),
                DistanceUnit.FOOT));
        assertEquals("5,5 yd", formatter.format(new BigDecimal(value),
                DistanceUnit.YARD));
        assertEquals("5,5 mi", formatter.format(new BigDecimal(value),
                DistanceUnit.MILE));
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 mm", formatter.format(new BigDecimal(value),
                DistanceUnit.MILLIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm", formatter.format(new BigDecimal(value),
                DistanceUnit.CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m", formatter.format(new BigDecimal(value),
                DistanceUnit.METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km", formatter.format(new BigDecimal(value),
                DistanceUnit.KILOMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in", formatter.format(new BigDecimal(value),
                DistanceUnit.INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft", formatter.format(new BigDecimal(value),
                DistanceUnit.FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 yd", formatter.format(new BigDecimal(value),
                DistanceUnit.YARD, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mi", formatter.format(new BigDecimal(value),
                DistanceUnit.MILE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        assertEquals("5,5 mm",
                formatter.format(value, DistanceUnit.MILLIMETER));
        assertEquals("5,5 cm",
                formatter.format(value, DistanceUnit.CENTIMETER));
        assertEquals("5,5 m",
                formatter.format(value, DistanceUnit.METER));
        assertEquals("5,5 Km",
                formatter.format(value, DistanceUnit.KILOMETER));
        assertEquals("5,5 in",
                formatter.format(value, DistanceUnit.INCH));
        assertEquals("5,5 ft",
                formatter.format(value, DistanceUnit.FOOT));
        assertEquals("5,5 yd",
                formatter.format(value, DistanceUnit.YARD));
        assertEquals("5,5 mi",
                formatter.format(value, DistanceUnit.MILE));
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 mm", formatter.format(value, DistanceUnit.MILLIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm", formatter.format(value, DistanceUnit.CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m", formatter.format(value, DistanceUnit.METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km", formatter.format(value, DistanceUnit.KILOMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in", formatter.format(value, DistanceUnit.INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft", formatter.format(value, DistanceUnit.FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 yd", formatter.format(value, DistanceUnit.YARD, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mi", formatter.format(value, DistanceUnit.MILE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatDistance() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        assertEquals("5,5 mm", formatter.format(
                new Distance(value, DistanceUnit.MILLIMETER)));
        assertEquals("5,5 cm", formatter.format(
                new Distance(value, DistanceUnit.CENTIMETER)));
        assertEquals("5,5 m", formatter.format(
                new Distance(value, DistanceUnit.METER)));
        assertEquals("5,5 Km", formatter.format(
                new Distance(value, DistanceUnit.KILOMETER)));
        assertEquals("5,5 in", formatter.format(
                new Distance(value, DistanceUnit.INCH)));
        assertEquals("5,5 ft", formatter.format(
                new Distance(value, DistanceUnit.FOOT)));
        assertEquals("5,5 yd", formatter.format(
                new Distance(value, DistanceUnit.YARD)));
        assertEquals("5,5 mi", formatter.format(
                new Distance(value, DistanceUnit.MILE)));
    }

    @Test
    public void testFormatDistanceAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("5,5 mm", formatter.format(
                new Distance(value, DistanceUnit.MILLIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm", formatter.format(
                new Distance(value, DistanceUnit.CENTIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m", formatter.format(
                new Distance(value, DistanceUnit.METER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km", formatter.format(
                new Distance(value, DistanceUnit.KILOMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in", formatter.format(
                new Distance(value, DistanceUnit.INCH), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft", formatter.format(
                new Distance(value, DistanceUnit.FOOT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 yd", formatter.format(
                new Distance(value, DistanceUnit.YARD), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mi", formatter.format(
                new Distance(value, DistanceUnit.MILE), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAndConvertNumber() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(new BigDecimal("5.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5,05 cm", formatter.formatAndConvert(new BigDecimal("50.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5 m", formatter.formatAndConvert(new BigDecimal("5000.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5 Km", formatter.formatAndConvert(new BigDecimal("5000000.50"),
                DistanceUnit.MILLIMETER));

        assertEquals("2,54 cm", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.INCH));
        assertEquals("30,48 cm", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.FOOT));
        assertEquals("91,44 cm", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.YARD));
        assertEquals("1,61 Km", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.MILE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 in", formatter.formatAndConvert(new BigDecimal("5.50"),
                DistanceUnit.INCH));
        assertEquals("1.5 ft", formatter.formatAndConvert(new BigDecimal("18.0"),
                DistanceUnit.INCH));
        assertEquals("5.5 yd", formatter.formatAndConvert(new BigDecimal("16.50"),
                DistanceUnit.FOOT));
        assertEquals("5.5 mi", formatter.formatAndConvert(new BigDecimal("348480.0"),
                DistanceUnit.INCH));
    }

    @Test
    public void testFormatAndConvertDouble() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(5.50,
                DistanceUnit.MILLIMETER));
        assertEquals("5,05 cm", formatter.formatAndConvert(50.50,
                DistanceUnit.MILLIMETER));
        assertEquals("5 m", formatter.formatAndConvert(5000.50,
                DistanceUnit.MILLIMETER));
        assertEquals("5 Km", formatter.formatAndConvert(5000000.50,
                DistanceUnit.MILLIMETER));

        assertEquals("2,54 cm", formatter.formatAndConvert(1.0,
                DistanceUnit.INCH));
        assertEquals("30,48 cm", formatter.formatAndConvert(1.0,
                DistanceUnit.FOOT));
        assertEquals("91,44 cm", formatter.formatAndConvert(1.0,
                DistanceUnit.YARD));
        assertEquals("1,61 Km", formatter.formatAndConvert(1.0,
                DistanceUnit.MILE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 in", formatter.formatAndConvert(5.50,
                DistanceUnit.INCH));
        assertEquals("1.5 ft", formatter.formatAndConvert(18.0,
                DistanceUnit.INCH));
        assertEquals("5.5 yd", formatter.formatAndConvert(16.50,
                DistanceUnit.FOOT));
        assertEquals("5.5 mi", formatter.formatAndConvert(348480.0,
                DistanceUnit.INCH));
    }

    @Test
    public void testFormatAndConvertDistance() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(new Distance(5.50,
                DistanceUnit.MILLIMETER)));
        assertEquals("5,05 cm", formatter.formatAndConvert(new Distance(50.50,
                DistanceUnit.MILLIMETER)));
        assertEquals("5 m", formatter.formatAndConvert(new Distance(5000.50,
                DistanceUnit.MILLIMETER)));
        assertEquals("5 Km", formatter.formatAndConvert(new Distance(5000000.50,
                DistanceUnit.MILLIMETER)));

        assertEquals("2,54 cm", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.INCH)));
        assertEquals("30,48 cm", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.FOOT)));
        assertEquals("91,44 cm", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.YARD)));
        assertEquals("1,61 Km", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.MILE)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 in", formatter.formatAndConvert(new Distance(5.50,
                DistanceUnit.INCH)));
        assertEquals("1.5 ft", formatter.formatAndConvert(new Distance(18.0,
                DistanceUnit.INCH)));
        assertEquals("5.5 yd", formatter.formatAndConvert(new Distance(16.50,
                DistanceUnit.FOOT)));
        assertEquals("5.5 mi", formatter.formatAndConvert(new Distance(348480.0,
                DistanceUnit.INCH)));
    }

    @Test
    public void testFormatAnConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(new BigDecimal("5.50"),
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5,05 cm", formatter.formatAndConvert(new BigDecimal("50.50"),
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 m", formatter.formatAndConvert(new BigDecimal("5000.50"),
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 Km", formatter.formatAndConvert(new BigDecimal("5000000.50"),
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));

        assertEquals("1 in", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.INCH, UnitSystem.IMPERIAL));
        assertEquals("1 ft", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.FOOT, UnitSystem.IMPERIAL));
        assertEquals("1 yd", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.YARD, UnitSystem.IMPERIAL));
        assertEquals("1 mi", formatter.formatAndConvert(new BigDecimal("1.0"),
                DistanceUnit.MILE, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(5.50,
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5,05 cm", formatter.formatAndConvert(50.50,
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 m", formatter.formatAndConvert(5000.50,
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 Km", formatter.formatAndConvert(5000000.50,
                DistanceUnit.MILLIMETER, UnitSystem.METRIC));

        assertEquals("1 in", formatter.formatAndConvert(1.0,
                DistanceUnit.INCH, UnitSystem.IMPERIAL));
        assertEquals("1 ft", formatter.formatAndConvert(1.0,
                DistanceUnit.FOOT, UnitSystem.IMPERIAL));
        assertEquals("1 yd", formatter.formatAndConvert(1.0,
                DistanceUnit.YARD, UnitSystem.IMPERIAL));
        assertEquals("1 mi", formatter.formatAndConvert(1.0,
                DistanceUnit.MILE, UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertDistanceAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvert(new Distance(5.50,
                DistanceUnit.MILLIMETER), UnitSystem.METRIC));
        assertEquals("5,05 cm", formatter.formatAndConvert(new Distance(50.50,
                DistanceUnit.MILLIMETER), UnitSystem.METRIC));
        assertEquals("5 m", formatter.formatAndConvert(new Distance(5000.50,
                DistanceUnit.MILLIMETER), UnitSystem.METRIC));
        assertEquals("5 Km", formatter.formatAndConvert(new Distance(5000000.50,
                DistanceUnit.MILLIMETER), UnitSystem.METRIC));

        assertEquals("1 in", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.INCH), UnitSystem.IMPERIAL));
        assertEquals("1 ft", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.FOOT), UnitSystem.IMPERIAL));
        assertEquals("1 yd", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.YARD), UnitSystem.IMPERIAL));
        assertEquals("1 mi", formatter.formatAndConvert(new Distance(1.0,
                DistanceUnit.MILE), UnitSystem.IMPERIAL));
    }

    @Test
    public void testFormatAndConvertMetric() {
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5,05 cm", formatter.formatAndConvertMetric(new BigDecimal("50.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5 m", formatter.formatAndConvertMetric(new BigDecimal("5000.50"),
                DistanceUnit.MILLIMETER));
        assertEquals("5 Km", formatter.formatAndConvertMetric(new BigDecimal("5000000.50"),
                DistanceUnit.MILLIMETER));

        assertEquals("2,54 cm", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                DistanceUnit.INCH));
        assertEquals("30,48 cm", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                DistanceUnit.FOOT));
        assertEquals("91,44 cm", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                DistanceUnit.YARD));
        assertEquals("1,61 Km", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                DistanceUnit.MILE));
    }

    @Test
    public void testFormatAndConvertImperial() {
        final Locale l = new Locale("es", "ES");

        final DistanceFormatter formatter = new DistanceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 in", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                DistanceUnit.INCH));
        assertEquals("1,5 ft", formatter.formatAndConvertImperial(new BigDecimal("18.0"),
                DistanceUnit.INCH));
        assertEquals("5,5 yd", formatter.formatAndConvertImperial(new BigDecimal("16.50"),
                DistanceUnit.FOOT));
        assertEquals("5,5 mi", formatter.formatAndConvertImperial(new BigDecimal("348480.0"),
                DistanceUnit.INCH));
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = DistanceFormatter.getAvailableLocales();
        assertArrayEquals(NumberFormat.getAvailableLocales(), locales);
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    public void testGetSetRoundingMode() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    public void testIsSetGroupingUsed() {
        final DistanceFormatter formatter = new DistanceFormatter();

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
        final DistanceFormatter formatter = new DistanceFormatter();

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
        final DistanceFormatter formatter = new DistanceFormatter();

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
        DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new DistanceFormatter(
                new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    public void testIsValidUnit() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertTrue(formatter.isValidUnit("mm"));
        assertTrue(formatter.isValidUnit("mm "));

        assertTrue(formatter.isValidUnit("cm"));
        assertTrue(formatter.isValidUnit("cm "));

        assertTrue(formatter.isValidUnit("m"));
        assertTrue(formatter.isValidUnit("m "));

        assertTrue(formatter.isValidUnit("Km"));
        assertTrue(formatter.isValidUnit("Km "));

        assertTrue(formatter.isValidUnit("in"));
        assertTrue(formatter.isValidUnit("in "));

        assertTrue(formatter.isValidUnit("ft"));
        assertTrue(formatter.isValidUnit("ft "));

        assertTrue(formatter.isValidUnit("yd"));
        assertTrue(formatter.isValidUnit("yd "));

        assertTrue(formatter.isValidUnit("mi"));
        assertTrue(formatter.isValidUnit("mi "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 cm";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 m";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 in";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ft";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 yd";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mi";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 cm";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 m";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 in";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 ft";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 yd";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 mi";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 cm";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 m";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 in";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 ft";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 yd";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 mi";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 cm";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 m";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Km";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 in";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 ft";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 yd";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 mi";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        Distance d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.MILLIMETER, d.getUnit());

        text = "5,5 cm";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.CENTIMETER, d.getUnit());

        text = "5,5 m";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.METER, d.getUnit());

        text = "5,5 Km";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.KILOMETER, d.getUnit());

        text = "5,5 in";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.INCH, d.getUnit());

        text = "5,5 ft";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.FOOT, d.getUnit());

        text = "5,5 yd";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.YARD, d.getUnit());

        text = "5,5 mi";
        d = formatter.parse(text);
        assertEquals(5.5, d.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.MILE, d.getUnit());

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
        final DistanceFormatter formatter = new DistanceFormatter(
                new Locale("es", "ES"));

        String text = "5,5 mm";
        assertEquals(DistanceUnit.MILLIMETER, formatter.findUnit(text));

        text = "5,5 cm";
        assertEquals(DistanceUnit.CENTIMETER, formatter.findUnit(text));

        text = "5,5 m";
        assertEquals(DistanceUnit.METER, formatter.findUnit(text));

        text = "5,5 Km";
        assertEquals(DistanceUnit.KILOMETER, formatter.findUnit(text));

        text = "5,5 in";
        assertEquals(DistanceUnit.INCH, formatter.findUnit(text));

        text = "5,5 ft";
        assertEquals(DistanceUnit.FOOT, formatter.findUnit(text));

        text = "5,5 yd";
        assertEquals(DistanceUnit.YARD, formatter.findUnit(text));

        text = "5,5 mi";
        assertEquals(DistanceUnit.MILE, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final DistanceFormatter formatter = new DistanceFormatter();

        assertEquals(DistanceFormatter.MILLIMETER,
                formatter.getUnitSymbol(DistanceUnit.MILLIMETER));
        assertEquals(DistanceFormatter.CENTIMETER,
                formatter.getUnitSymbol(DistanceUnit.CENTIMETER));
        assertEquals(DistanceFormatter.METER,
                formatter.getUnitSymbol(DistanceUnit.METER));
        assertEquals(DistanceFormatter.KILOMETER,
                formatter.getUnitSymbol(DistanceUnit.KILOMETER));
        assertEquals(DistanceFormatter.INCH,
                formatter.getUnitSymbol(DistanceUnit.INCH));
        assertEquals(DistanceFormatter.FOOT,
                formatter.getUnitSymbol(DistanceUnit.FOOT));
        assertEquals(DistanceFormatter.YARD,
                formatter.getUnitSymbol(DistanceUnit.YARD));
        assertEquals(DistanceFormatter.MILE,
                formatter.getUnitSymbol(DistanceUnit.MILE));
    }
}
