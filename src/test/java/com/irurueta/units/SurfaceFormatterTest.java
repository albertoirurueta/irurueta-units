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

class SurfaceFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new SurfaceFormatter();

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
        formatter = new SurfaceFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new SurfaceFormatter((Locale) null));

        // test copy constructor
        formatter = new SurfaceFormatter(locale);
        final var formatter2 = new SurfaceFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new SurfaceFormatter((SurfaceFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new SurfaceFormatter();
        final var formatter2 = (SurfaceFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, SurfaceUnit.SQUARE_METER, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (SurfaceFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new SurfaceFormatter(Locale.ENGLISH);
        final var formatter2 = new SurfaceFormatter(Locale.ENGLISH);
        final var formatter3 = new SurfaceFormatter(Locale.FRENCH);

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
        final var formatter1 = new SurfaceFormatter(Locale.ENGLISH);
        final var formatter2 = new SurfaceFormatter(Locale.ENGLISH);
        final var formatter3 = new SurfaceFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        assertEquals("5,5 mm²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5,5 cm²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_CENTIMETER));
        assertEquals("5,5 m²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_METER));
        assertEquals("5,5 Km²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_KILOMETER));
        assertEquals("5,5 sq in", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_INCH));
        assertEquals("5,5 sq ft", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_FOOT));
        assertEquals("5,5 sq yd", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_YARD));
        assertEquals("5,5 sq mi", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_MILE));
        assertEquals("5,5 ca", formatter.format(new BigDecimal(value), SurfaceUnit.CENTIARE));
        assertEquals("5,5 a", formatter.format(new BigDecimal(value), SurfaceUnit.ARE));
        assertEquals("5,5 daa", formatter.format(new BigDecimal(value), SurfaceUnit.DECARE));
        assertEquals("5,5 ha", formatter.format(new BigDecimal(value), SurfaceUnit.HECTARE));
        assertEquals("5,5 acre", formatter.format(new BigDecimal(value), SurfaceUnit.ACRE));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 mm²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_MILLIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km²", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_KILOMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq in", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq ft", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq yd", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_YARD, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq mi", formatter.format(new BigDecimal(value), SurfaceUnit.SQUARE_MILE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ca", formatter.format(new BigDecimal(value), SurfaceUnit.CENTIARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 a", formatter.format(new BigDecimal(value), SurfaceUnit.ARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 daa", formatter.format(new BigDecimal(value), SurfaceUnit.DECARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ha", formatter.format(new BigDecimal(value), SurfaceUnit.HECTARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 acre", formatter.format(new BigDecimal(value), SurfaceUnit.ACRE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        assertEquals("5,5 mm²", formatter.format(value, SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5,5 cm²", formatter.format(value, SurfaceUnit.SQUARE_CENTIMETER));
        assertEquals("5,5 m²", formatter.format(value, SurfaceUnit.SQUARE_METER));
        assertEquals("5,5 Km²", formatter.format(value, SurfaceUnit.SQUARE_KILOMETER));
        assertEquals("5,5 sq in", formatter.format(value, SurfaceUnit.SQUARE_INCH));
        assertEquals("5,5 sq ft", formatter.format(value, SurfaceUnit.SQUARE_FOOT));
        assertEquals("5,5 sq yd", formatter.format(value, SurfaceUnit.SQUARE_YARD));
        assertEquals("5,5 sq mi", formatter.format(value, SurfaceUnit.SQUARE_MILE));
        assertEquals("5,5 ca", formatter.format(value, SurfaceUnit.CENTIARE));
        assertEquals("5,5 a", formatter.format(value, SurfaceUnit.ARE));
        assertEquals("5,5 daa", formatter.format(value, SurfaceUnit.DECARE));
        assertEquals("5,5 ha", formatter.format(value, SurfaceUnit.HECTARE));
        assertEquals("5,5 acre", formatter.format(value, SurfaceUnit.ACRE));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 mm²", formatter.format(value, SurfaceUnit.SQUARE_MILLIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm²", formatter.format(value, SurfaceUnit.SQUARE_CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m²", formatter.format(value, SurfaceUnit.SQUARE_METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km²", formatter.format(value, SurfaceUnit.SQUARE_KILOMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq in", formatter.format(value, SurfaceUnit.SQUARE_INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq ft", formatter.format(value, SurfaceUnit.SQUARE_FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq yd", formatter.format(value, SurfaceUnit.SQUARE_YARD, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq mi", formatter.format(value, SurfaceUnit.SQUARE_MILE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ca", formatter.format(value, SurfaceUnit.CENTIARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 a", formatter.format(value, SurfaceUnit.ARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 daa", formatter.format(value, SurfaceUnit.DECARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ha", formatter.format(value, SurfaceUnit.HECTARE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 acre", formatter.format(value, SurfaceUnit.ACRE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatSurface() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        assertEquals("5,5 mm²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_MILLIMETER)));
        assertEquals("5,5 cm²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_CENTIMETER)));
        assertEquals("5,5 m²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_METER)));
        assertEquals("5,5 Km²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_KILOMETER)));
        assertEquals("5,5 sq in", formatter.format(new Surface(value, SurfaceUnit.SQUARE_INCH)));
        assertEquals("5,5 sq ft", formatter.format(new Surface(value, SurfaceUnit.SQUARE_FOOT)));
        assertEquals("5,5 sq yd", formatter.format(new Surface(value, SurfaceUnit.SQUARE_YARD)));
        assertEquals("5,5 sq mi", formatter.format(new Surface(value, SurfaceUnit.SQUARE_MILE)));
        assertEquals("5,5 ca", formatter.format(new Surface(value, SurfaceUnit.CENTIARE)));
        assertEquals("5,5 a", formatter.format(new Surface(value, SurfaceUnit.ARE)));
        assertEquals("5,5 daa", formatter.format(new Surface(value, SurfaceUnit.DECARE)));
        assertEquals("5,5 ha", formatter.format(new Surface(value, SurfaceUnit.HECTARE)));
        assertEquals("5,5 acre", formatter.format(new Surface(value, SurfaceUnit.ACRE)));
    }

    @Test
    void testFormatSurfaceAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 mm²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_MILLIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cm²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_CENTIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_METER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km²", formatter.format(new Surface(value, SurfaceUnit.SQUARE_KILOMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq in", formatter.format(new Surface(value, SurfaceUnit.SQUARE_INCH), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq ft", formatter.format(new Surface(value, SurfaceUnit.SQUARE_FOOT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq yd", formatter.format(new Surface(value, SurfaceUnit.SQUARE_YARD), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 sq mi", formatter.format(new Surface(value, SurfaceUnit.SQUARE_MILE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ca", formatter.format(new Surface(value, SurfaceUnit.CENTIARE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 a", formatter.format(new Surface(value, SurfaceUnit.ARE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 daa", formatter.format(new Surface(value, SurfaceUnit.DECARE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ha", formatter.format(new Surface(value, SurfaceUnit.HECTARE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 acre", formatter.format(new Surface(value, SurfaceUnit.ACRE), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvert(new BigDecimal("5.50"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5,05 cm²", formatter.formatAndConvert(new BigDecimal("505.00"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 m²", formatter.formatAndConvert(new BigDecimal("5000005.00"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 Km²", formatter.formatAndConvert(new BigDecimal("5000000000000.50"),
                SurfaceUnit.SQUARE_MILLIMETER));

        assertEquals("6,45 cm²", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("929,03 cm²", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_FOOT));
        assertEquals("8.361,27 cm²", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_YARD));
        assertEquals("2,59 Km²", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_MILE));

        assertEquals("1 m²", formatter.formatAndConvert(new BigDecimal("1.0"), SurfaceUnit.CENTIARE));
        assertEquals("100 m²", formatter.formatAndConvert(new BigDecimal("1.0"), SurfaceUnit.ARE));
        assertEquals("1.000 m²", formatter.formatAndConvert(new BigDecimal("1.0"), SurfaceUnit.DECARE));
        assertEquals("10.000 m²", formatter.formatAndConvert(new BigDecimal("1.0"), SurfaceUnit.HECTARE));
        assertEquals("4.046,86 m²", formatter.formatAndConvert(new BigDecimal("1.0"), SurfaceUnit.ACRE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 sq in", formatter.formatAndConvert(new BigDecimal("5.50"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("1.25 sq ft", formatter.formatAndConvert(new BigDecimal("180.0"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("1.83 sq yd", formatter.formatAndConvert(new BigDecimal("16.50"),
                SurfaceUnit.SQUARE_FOOT));
        assertEquals("5.5 sq mi", formatter.formatAndConvert(new BigDecimal("3520.0"), SurfaceUnit.ACRE));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvert(5.50, SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5,05 cm²", formatter.formatAndConvert(505.00, SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 m²", formatter.formatAndConvert(5000005.00, SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 Km²", formatter.formatAndConvert(5000000000000.50,
                SurfaceUnit.SQUARE_MILLIMETER));

        assertEquals("6,45 cm²", formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_INCH));
        assertEquals("929,03 cm²", formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_FOOT));
        assertEquals("8.361,27 cm²", formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_YARD));
        assertEquals("2,59 Km²", formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_MILE));

        assertEquals("1 m²", formatter.formatAndConvert(1.0, SurfaceUnit.CENTIARE));
        assertEquals("100 m²", formatter.formatAndConvert(1.0, SurfaceUnit.ARE));
        assertEquals("1.000 m²", formatter.formatAndConvert(1.0, SurfaceUnit.DECARE));
        assertEquals("10.000 m²", formatter.formatAndConvert(1.0, SurfaceUnit.HECTARE));
        assertEquals("4.046,86 m²", formatter.formatAndConvert(1.0, SurfaceUnit.ACRE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 sq in", formatter.formatAndConvert(5.50, SurfaceUnit.SQUARE_INCH));
        assertEquals("1.25 sq ft", formatter.formatAndConvert(180.0, SurfaceUnit.SQUARE_INCH));
        assertEquals("1.83 sq yd", formatter.formatAndConvert(16.50, SurfaceUnit.SQUARE_FOOT));
        assertEquals("5.5 sq mi", formatter.formatAndConvert(3520.0, SurfaceUnit.ACRE));
    }

    @Test
    void testFormatAndConvertSurface() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvert(new Surface(5.50,
                SurfaceUnit.SQUARE_MILLIMETER)));
        assertEquals("5,05 cm²", formatter.formatAndConvert(new Surface(505.00,
                SurfaceUnit.SQUARE_MILLIMETER)));
        assertEquals("5 m²", formatter.formatAndConvert(new Surface(5000005.00,
                SurfaceUnit.SQUARE_MILLIMETER)));
        assertEquals("5 Km²", formatter.formatAndConvert(new Surface(5000000000000.50,
                SurfaceUnit.SQUARE_MILLIMETER)));

        assertEquals("6,45 cm²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_INCH)));
        assertEquals("929,03 cm²", formatter.formatAndConvert(new Surface(1.0,
                SurfaceUnit.SQUARE_FOOT)));
        assertEquals("8.361,27 cm²", formatter.formatAndConvert(new Surface(1.0,
                SurfaceUnit.SQUARE_YARD)));
        assertEquals("2,59 Km²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_MILE)));

        assertEquals("1 m²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.CENTIARE)));
        assertEquals("100 m²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.ARE)));
        assertEquals("1.000 m²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.DECARE)));
        assertEquals("10.000 m²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.HECTARE)));
        assertEquals("4.046,86 m²", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.ACRE)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 sq in", formatter.formatAndConvert(new Surface(5.50,
                SurfaceUnit.SQUARE_INCH)));
        assertEquals("1.25 sq ft", formatter.formatAndConvert(new Surface(180.0,
                SurfaceUnit.SQUARE_INCH)));
        assertEquals("1.83 sq yd", formatter.formatAndConvert(new Surface(16.50,
                SurfaceUnit.SQUARE_FOOT)));
        assertEquals("5.5 sq mi", formatter.formatAndConvert(new Surface(3520.0,
                SurfaceUnit.ACRE)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvert(new BigDecimal("5.50"),
                SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5,05 cm²", formatter.formatAndConvert(new BigDecimal("505.00"),
                SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 m²", formatter.formatAndConvert(new BigDecimal("5000005.00"),
                SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 Km²", formatter.formatAndConvert(new BigDecimal("5000000000000.50"),
                SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));

        assertEquals("1 sq in", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_INCH, UnitSystem.IMPERIAL));
        assertEquals("1 sq ft", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_FOOT, UnitSystem.IMPERIAL));
        assertEquals("1 sq yd", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_YARD, UnitSystem.IMPERIAL));
        assertEquals("1 sq mi", formatter.formatAndConvert(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_MILE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²",
                formatter.formatAndConvert(5.50, SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5,05 cm²",
                formatter.formatAndConvert(505.00, SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 m²",
                formatter.formatAndConvert(5000005.00, SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));
        assertEquals("5 Km²",
                formatter.formatAndConvert(5000000000000.50, SurfaceUnit.SQUARE_MILLIMETER, UnitSystem.METRIC));

        assertEquals("1 sq in",
                formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_INCH, UnitSystem.IMPERIAL));
        assertEquals("1 sq ft",
                formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_FOOT, UnitSystem.IMPERIAL));
        assertEquals("1 sq yd",
                formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_YARD, UnitSystem.IMPERIAL));
        assertEquals("1 sq mi",
                formatter.formatAndConvert(1.0, SurfaceUnit.SQUARE_MILE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertSurfaceAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvert(new Surface(5.50,
                SurfaceUnit.SQUARE_MILLIMETER), UnitSystem.METRIC));
        assertEquals("5,05 cm²", formatter.formatAndConvert(new Surface(505.00,
                SurfaceUnit.SQUARE_MILLIMETER), UnitSystem.METRIC));
        assertEquals("5 m²", formatter.formatAndConvert(new Surface(5000005.00,
                SurfaceUnit.SQUARE_MILLIMETER), UnitSystem.METRIC));
        assertEquals("5 Km²", formatter.formatAndConvert(new Surface(5000000000000.50,
                SurfaceUnit.SQUARE_MILLIMETER), UnitSystem.METRIC));

        assertEquals("1 sq in", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_INCH),
                UnitSystem.IMPERIAL));
        assertEquals("1 sq ft", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_FOOT),
                UnitSystem.IMPERIAL));
        assertEquals("1 sq yd", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_YARD),
                UnitSystem.IMPERIAL));
        assertEquals("1 sq mi", formatter.formatAndConvert(new Surface(1.0, SurfaceUnit.SQUARE_MILE),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 mm²", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5,05 cm²", formatter.formatAndConvertMetric(new BigDecimal("505.00"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 m²", formatter.formatAndConvertMetric(new BigDecimal("5000005.00"),
                SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals("5 Km²", formatter.formatAndConvertMetric(new BigDecimal("5000000000000.50"),
                SurfaceUnit.SQUARE_MILLIMETER));

        assertEquals("6,45 cm²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("929,03 cm²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_FOOT));
        assertEquals("8.361,27 cm²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_YARD));
        assertEquals("2,59 Km²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.SQUARE_MILE));

        assertEquals("1 m²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.CENTIARE));
        assertEquals("100 m²", formatter.formatAndConvertMetric(new BigDecimal("1.0"), SurfaceUnit.ARE));
        assertEquals("1.000 m²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.DECARE));
        assertEquals("10.000 m²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.HECTARE));
        assertEquals("4.046,86 m²", formatter.formatAndConvertMetric(new BigDecimal("1.0"),
                SurfaceUnit.ACRE));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("en", "US");

        final var formatter = new SurfaceFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5.5 sq in", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("1.25 sq ft", formatter.formatAndConvertImperial(new BigDecimal("180.0"),
                SurfaceUnit.SQUARE_INCH));
        assertEquals("1.83 sq yd", formatter.formatAndConvertImperial(new BigDecimal("16.50"),
                SurfaceUnit.SQUARE_FOOT));
        assertEquals("5.5 sq mi", formatter.formatAndConvertImperial(new BigDecimal("3520.0"),
                SurfaceUnit.ACRE));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = SurfaceFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new SurfaceFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new SurfaceFormatter();

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
        var formatter = new SurfaceFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new SurfaceFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new SurfaceFormatter();

        assertTrue(formatter.isValidUnit("mm²"));
        assertTrue(formatter.isValidUnit("mm² "));

        assertTrue(formatter.isValidUnit("cm²"));
        assertTrue(formatter.isValidUnit("cm² "));

        assertTrue(formatter.isValidUnit("m²"));
        assertTrue(formatter.isValidUnit("m² "));

        assertTrue(formatter.isValidUnit("Km²"));
        assertTrue(formatter.isValidUnit("Km² "));

        assertTrue(formatter.isValidUnit("sq ft"));
        assertTrue(formatter.isValidUnit("sq ft "));

        assertTrue(formatter.isValidUnit("sq mi"));
        assertTrue(formatter.isValidUnit("sq mi "));

        assertTrue(formatter.isValidUnit("ca"));
        assertTrue(formatter.isValidUnit("ca "));

        assertTrue(formatter.isValidUnit("a"));
        assertTrue(formatter.isValidUnit("a "));

        assertTrue(formatter.isValidUnit("daa"));
        assertTrue(formatter.isValidUnit("daa "));

        assertTrue(formatter.isValidUnit("ha"));
        assertTrue(formatter.isValidUnit("ha "));

        assertTrue(formatter.isValidUnit("acre"));
        assertTrue(formatter.isValidUnit("acre "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {

        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 cm²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 m²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km²";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 sq in";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 sq ft";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 sq yd";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 sq mi";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ca";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 a";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 daa";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ha";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 acre";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 cm²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 m²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km²";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 sq in";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 sq ft";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 sq yd";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 sq mi";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 ca";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 a";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 daa";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ha";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 acre";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 cm²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 m²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km²";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 sq in";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 sq ft";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 sq yd";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 sq mi";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 ca";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 a";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 daa";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ha";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 acre";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 cm²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 m²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Km²";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 sq in";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 sq ft";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 sq yd";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 sq mi";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 ca";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 a";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 daa";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ha";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 acre";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        var s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_MILLIMETER, s.getUnit());

        text = "5,5 cm²";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_CENTIMETER, s.getUnit());

        text = "5,5 m²";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_METER, s.getUnit());

        text = "5,5 Km²";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_KILOMETER, s.getUnit());

        text = "5,5 sq in";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_INCH, s.getUnit());

        text = "5,5 sq ft";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_FOOT, s.getUnit());

        text = "5,5 sq yd";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_YARD, s.getUnit());

        text = "5,5 sq mi";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_MILE, s.getUnit());

        text = "5,5 ca";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.CENTIARE, s.getUnit());

        text = "5,5 a";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.ARE, s.getUnit());

        text = "5,5 daa";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.DECARE, s.getUnit());

        text = "5,5 ha";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.HECTARE, s.getUnit());

        text = "5,5 acre";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.ACRE, s.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        var text = "5,5 mm²";
        assertEquals(SurfaceUnit.SQUARE_MILLIMETER, formatter.findUnit(text));

        text = "5,5 cm²";
        assertEquals(SurfaceUnit.SQUARE_CENTIMETER, formatter.findUnit(text));

        text = "5,5 m²";
        assertEquals(SurfaceUnit.SQUARE_METER, formatter.findUnit(text));

        text = "5,5 Km²";
        assertEquals(SurfaceUnit.SQUARE_KILOMETER, formatter.findUnit(text));

        text = "5,5 sq in";
        assertEquals(SurfaceUnit.SQUARE_INCH, formatter.findUnit(text));

        text = "5,5 sq ft";
        assertEquals(SurfaceUnit.SQUARE_FOOT, formatter.findUnit(text));

        text = "5,5 sq yd";
        assertEquals(SurfaceUnit.SQUARE_YARD, formatter.findUnit(text));

        text = "5,5 sq mi";
        assertEquals(SurfaceUnit.SQUARE_MILE, formatter.findUnit(text));

        text = "5,5 ca";
        assertEquals(SurfaceUnit.CENTIARE, formatter.findUnit(text));

        text = "5,5 a";
        assertEquals(SurfaceUnit.ARE, formatter.findUnit(text));

        text = "5,5 daa";
        assertEquals(SurfaceUnit.DECARE, formatter.findUnit(text));

        text = "5,5 ha";
        assertEquals(SurfaceUnit.HECTARE, formatter.findUnit(text));

        text = "5,5 acre";
        assertEquals(SurfaceUnit.ACRE, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new SurfaceFormatter(new Locale("es", "ES"));

        assertEquals(SurfaceFormatter.SQUARE_MILLIMETER, formatter.getUnitSymbol(SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals(SurfaceFormatter.SQUARE_CENTIMETER, formatter.getUnitSymbol(SurfaceUnit.SQUARE_CENTIMETER));
        assertEquals(SurfaceFormatter.SQUARE_METER, formatter.getUnitSymbol(SurfaceUnit.SQUARE_METER));
        assertEquals(SurfaceFormatter.SQUARE_KILOMETER, formatter.getUnitSymbol(SurfaceUnit.SQUARE_KILOMETER));
        assertEquals(SurfaceFormatter.SQUARE_INCH, formatter.getUnitSymbol(SurfaceUnit.SQUARE_INCH));
        assertEquals(SurfaceFormatter.SQUARE_FOOT, formatter.getUnitSymbol(SurfaceUnit.SQUARE_FOOT));
        assertEquals(SurfaceFormatter.SQUARE_YARD, formatter.getUnitSymbol(SurfaceUnit.SQUARE_YARD));
        assertEquals(SurfaceFormatter.SQUARE_MILE, formatter.getUnitSymbol(SurfaceUnit.SQUARE_MILE));
        assertEquals(SurfaceFormatter.CENTIARE, formatter.getUnitSymbol(SurfaceUnit.CENTIARE));
        assertEquals(SurfaceFormatter.ARE, formatter.getUnitSymbol(SurfaceUnit.ARE));
        assertEquals(SurfaceFormatter.DECARE, formatter.getUnitSymbol(SurfaceUnit.DECARE));
        assertEquals(SurfaceFormatter.HECTARE, formatter.getUnitSymbol(SurfaceUnit.HECTARE));
        assertEquals(SurfaceFormatter.ACRE, formatter.getUnitSymbol(SurfaceUnit.ACRE));
    }
}
