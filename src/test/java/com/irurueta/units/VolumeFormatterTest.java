/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
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

class VolumeFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new VolumeFormatter();

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
        formatter = new VolumeFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new VolumeFormatter((Locale) null));

        // test copy constructor
        formatter = new VolumeFormatter(locale);
        final var formatter2 = new VolumeFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new VolumeFormatter((VolumeFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new VolumeFormatter();
        final var formatter2 = (VolumeFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, VolumeUnit.LITER, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (VolumeFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new VolumeFormatter(Locale.ENGLISH);
        final var formatter2 = new VolumeFormatter(Locale.ENGLISH);
        final var formatter3 = new VolumeFormatter(Locale.FRENCH);

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
        final var formatter1 = new VolumeFormatter(Locale.ENGLISH);
        final var formatter2 = new VolumeFormatter(Locale.ENGLISH);
        final var formatter3 = new VolumeFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        assertEquals("5,5 cm³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("5,5 mL", formatter.format(new BigDecimal(value), VolumeUnit.MILLILITER));
        assertEquals("5,5 dm³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_DECIMETER));
        assertEquals("5,5 L", formatter.format(new BigDecimal(value), VolumeUnit.LITER));
        assertEquals("5,5 hL", formatter.format(new BigDecimal(value), VolumeUnit.HECTOLITER));
        assertEquals("5,5 m³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_METER));
        assertEquals("5,5 in³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_INCH));
        assertEquals("5,5 pt", formatter.format(new BigDecimal(value), VolumeUnit.PINT));
        assertEquals("5,5 gal", formatter.format(new BigDecimal(value), VolumeUnit.GALLON));
        assertEquals("5,5 ft³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_FOOT));
        assertEquals("5,5 bbl", formatter.format(new BigDecimal(value), VolumeUnit.BARREL));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 cm³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mL", formatter.format(new BigDecimal(value), VolumeUnit.MILLILITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dm³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_DECIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 L", formatter.format(new BigDecimal(value), VolumeUnit.LITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hL", formatter.format(new BigDecimal(value), VolumeUnit.HECTOLITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 pt", formatter.format(new BigDecimal(value), VolumeUnit.PINT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 gal", formatter.format(new BigDecimal(value), VolumeUnit.GALLON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft³", formatter.format(new BigDecimal(value), VolumeUnit.CUBIC_FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bbl", formatter.format(new BigDecimal(value), VolumeUnit.BARREL, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        assertEquals("5,5 cm³", formatter.format(value, VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("5,5 mL", formatter.format(value, VolumeUnit.MILLILITER));
        assertEquals("5,5 dm³", formatter.format(value, VolumeUnit.CUBIC_DECIMETER));
        assertEquals("5,5 L", formatter.format(value, VolumeUnit.LITER));
        assertEquals("5,5 hL", formatter.format(value, VolumeUnit.HECTOLITER));
        assertEquals("5,5 m³", formatter.format(value, VolumeUnit.CUBIC_METER));
        assertEquals("5,5 in³", formatter.format(value, VolumeUnit.CUBIC_INCH));
        assertEquals("5,5 pt", formatter.format(value, VolumeUnit.PINT));
        assertEquals("5,5 gal", formatter.format(value, VolumeUnit.GALLON));
        assertEquals("5,5 ft³", formatter.format(value, VolumeUnit.CUBIC_FOOT));
        assertEquals("5,5 bbl", formatter.format(value, VolumeUnit.BARREL));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 cm³", formatter.format(value, VolumeUnit.CUBIC_CENTIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mL", formatter.format(value, VolumeUnit.MILLILITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dm³", formatter.format(value, VolumeUnit.CUBIC_DECIMETER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 L", formatter.format(value, VolumeUnit.LITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hL", formatter.format(value, VolumeUnit.HECTOLITER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m³", formatter.format(value, VolumeUnit.CUBIC_METER, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in³", formatter.format(value, VolumeUnit.CUBIC_INCH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 pt", formatter.format(value, VolumeUnit.PINT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 gal", formatter.format(value, VolumeUnit.GALLON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft³", formatter.format(value, VolumeUnit.CUBIC_FOOT, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bbl", formatter.format(value, VolumeUnit.BARREL, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatVolume() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        assertEquals("5,5 cm³", formatter.format(new Volume(value, VolumeUnit.CUBIC_CENTIMETER)));
        assertEquals("5,5 mL", formatter.format(new Volume(value, VolumeUnit.MILLILITER)));
        assertEquals("5,5 dm³", formatter.format(new Volume(value, VolumeUnit.CUBIC_DECIMETER)));
        assertEquals("5,5 L", formatter.format(new Volume(value, VolumeUnit.LITER)));
        assertEquals("5,5 hL", formatter.format(new Volume(value, VolumeUnit.HECTOLITER)));
        assertEquals("5,5 m³", formatter.format(new Volume(value, VolumeUnit.CUBIC_METER)));
        assertEquals("5,5 in³", formatter.format(new Volume(value, VolumeUnit.CUBIC_INCH)));
        assertEquals("5,5 pt", formatter.format(new Volume(value, VolumeUnit.PINT)));
        assertEquals("5,5 gal", formatter.format(new Volume(value, VolumeUnit.GALLON)));
        assertEquals("5,5 ft³", formatter.format(new Volume(value, VolumeUnit.CUBIC_FOOT)));
        assertEquals("5,5 bbl", formatter.format(new Volume(value, VolumeUnit.BARREL)));
    }

    @Test
    void testFormatVolumeAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new VolumeFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 cm³", formatter.format(new Volume(value, VolumeUnit.CUBIC_CENTIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mL", formatter.format(new Volume(value, VolumeUnit.MILLILITER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 dm³", formatter.format(new Volume(value, VolumeUnit.CUBIC_DECIMETER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 L", formatter.format(new Volume(value, VolumeUnit.LITER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 hL", formatter.format(new Volume(value, VolumeUnit.HECTOLITER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 m³", formatter.format(new Volume(value, VolumeUnit.CUBIC_METER), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 in³", formatter.format(new Volume(value, VolumeUnit.CUBIC_INCH), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 pt", formatter.format(new Volume(value, VolumeUnit.PINT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 gal", formatter.format(new Volume(value, VolumeUnit.GALLON), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft³", formatter.format(new Volume(value, VolumeUnit.CUBIC_FOOT), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 bbl", formatter.format(new Volume(value, VolumeUnit.BARREL), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("5,5 cm³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.MILLILITER));
        assertEquals("5 L", formatter.formatAndConvert(new BigDecimal("5000.50"), VolumeUnit.MILLILITER));
        assertEquals("5,5 L", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER));
        assertEquals("5,5 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.LITER));
        assertEquals("5 hL", formatter.formatAndConvert(new BigDecimal("500.50"), VolumeUnit.LITER));
        assertEquals("5,5 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.HECTOLITER));
        assertEquals("5,05 m³", formatter.formatAndConvert(new BigDecimal("50.50"),
                VolumeUnit.HECTOLITER));
        assertEquals("5,5 m³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_METER));

        assertEquals("90,13 cm³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH));
        assertEquals("2,6 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.PINT));
        assertEquals("20,82 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.GALLON));
        assertEquals("1,56 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_FOOT));
        assertEquals("8,74 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.BARREL));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.34 in³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("0.34 in³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER));
        assertEquals("1.45 gal", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER));
        assertEquals("1.45 gal", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.LITER));
        assertEquals("3.46 bbl", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER));
        assertEquals("34.59 bbl", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER));

        assertEquals("5.5 in³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_INCH));
        assertEquals("5.5 pt", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.PINT));
        assertEquals("5.5 gal", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.GALLON));
        assertEquals("5.5 ft³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_FOOT));
        assertEquals("5.5 bbl", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.BARREL));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("5,5 cm³", formatter.formatAndConvert(5.50, VolumeUnit.MILLILITER));
        assertEquals("5 L", formatter.formatAndConvert(5000.50, VolumeUnit.MILLILITER));
        assertEquals("5,5 L", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_DECIMETER));
        assertEquals("5,5 L", formatter.formatAndConvert(5.50, VolumeUnit.LITER));
        assertEquals("5 hL", formatter.formatAndConvert(500.50, VolumeUnit.LITER));
        assertEquals("5,5 hL", formatter.formatAndConvert(5.50, VolumeUnit.HECTOLITER));
        assertEquals("5,05 m³", formatter.formatAndConvert(50.50, VolumeUnit.HECTOLITER));
        assertEquals("5,5 m³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_METER));

        assertEquals("90,13 cm³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_INCH));
        assertEquals("2,6 L", formatter.formatAndConvert(5.50, VolumeUnit.PINT));
        assertEquals("20,82 L", formatter.formatAndConvert(5.50, VolumeUnit.GALLON));
        assertEquals("1,56 hL", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_FOOT));
        assertEquals("8,74 hL", formatter.formatAndConvert(5.50, VolumeUnit.BARREL));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.34 in³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("0.34 in³", formatter.formatAndConvert(5.50, VolumeUnit.MILLILITER));
        assertEquals("1.45 gal", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_DECIMETER));
        assertEquals("1.45 gal", formatter.formatAndConvert(5.50, VolumeUnit.LITER));
        assertEquals("3.46 bbl", formatter.formatAndConvert(5.50, VolumeUnit.HECTOLITER));
        assertEquals("34.59 bbl", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_METER));

        assertEquals("5.5 in³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_INCH));
        assertEquals("5.5 pt", formatter.formatAndConvert(5.50, VolumeUnit.PINT));
        assertEquals("5.5 gal", formatter.formatAndConvert(5.50, VolumeUnit.GALLON));
        assertEquals("5.5 ft³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_FOOT));
        assertEquals("5.5 bbl", formatter.formatAndConvert(5.50, VolumeUnit.BARREL));
    }

    @Test
    void testFormatAndConvertVolume() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(new Volume(5.50,
                VolumeUnit.CUBIC_CENTIMETER)));
        assertEquals("5,5 cm³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.MILLILITER)));
        assertEquals("5 L", formatter.formatAndConvert(new Volume(5000.50, VolumeUnit.MILLILITER)));
        assertEquals("5,5 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_DECIMETER)));
        assertEquals("5,5 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.LITER)));
        assertEquals("5 hL", formatter.formatAndConvert(new Volume(500.50, VolumeUnit.LITER)));
        assertEquals("5,5 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.HECTOLITER)));
        assertEquals("5,05 m³", formatter.formatAndConvert(new Volume(50.50, VolumeUnit.HECTOLITER)));
        assertEquals("5,5 m³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_METER)));

        assertEquals("90,13 cm³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_INCH)));
        assertEquals("2,6 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.PINT)));
        assertEquals("20,82 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.GALLON)));
        assertEquals("1,56 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_FOOT)));
        assertEquals("8,74 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.BARREL)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.34 in³", formatter.formatAndConvert(new Volume(5.50,
                VolumeUnit.CUBIC_CENTIMETER)));
        assertEquals("0.34 in³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.MILLILITER)));
        assertEquals("1.45 gal", formatter.formatAndConvert(new Volume(5.50,
                VolumeUnit.CUBIC_DECIMETER)));
        assertEquals("1.45 gal", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.LITER)));
        assertEquals("3.46 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.HECTOLITER)));
        assertEquals("34.59 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_METER)));

        assertEquals("5.5 in³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_INCH)));
        assertEquals("5.5 pt", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.PINT)));
        assertEquals("5.5 gal", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.GALLON)));
        assertEquals("5.5 ft³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_FOOT)));
        assertEquals("5.5 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.BARREL)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.METRIC));
        assertEquals("5,5 cm³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.MILLILITER,
                UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.LITER,
                UnitSystem.METRIC));
        assertEquals("5,5 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.HECTOLITER,
                UnitSystem.METRIC));
        assertEquals("5,5 m³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_METER,
                UnitSystem.METRIC));
        assertEquals("90,13 cm³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_INCH,
                UnitSystem.METRIC));
        assertEquals("2,6 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.PINT,
                UnitSystem.METRIC));
        assertEquals("20,82 L", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.GALLON,
                UnitSystem.METRIC));
        assertEquals("1,56 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_FOOT,
                UnitSystem.METRIC));
        assertEquals("8,74 hL", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.BARREL,
                UnitSystem.METRIC));

        assertEquals("0,34 in³", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.IMPERIAL));
        assertEquals("0,34 in³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.MILLILITER,
                UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.LITER,
                UnitSystem.IMPERIAL));
        assertEquals("3,46 bbl", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.HECTOLITER,
                UnitSystem.IMPERIAL));
        assertEquals("34,59 bbl", formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER, UnitSystem.IMPERIAL));
        assertEquals("5,5 in³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_INCH,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 pt", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.PINT,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 gal", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.GALLON,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 ft³", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.CUBIC_FOOT,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 bbl", formatter.formatAndConvert(new BigDecimal("5.50"), VolumeUnit.BARREL,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_CENTIMETER,
                UnitSystem.METRIC));
        assertEquals("5,5 cm³", formatter.formatAndConvert(5.50, VolumeUnit.MILLILITER,
                UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_DECIMETER,
                UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(5.50, VolumeUnit.LITER,
                UnitSystem.METRIC));
        assertEquals("5,5 hL", formatter.formatAndConvert(5.50, VolumeUnit.HECTOLITER,
                UnitSystem.METRIC));
        assertEquals("5,5 m³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_METER,
                UnitSystem.METRIC));
        assertEquals("90,13 cm³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_INCH,
                UnitSystem.METRIC));
        assertEquals("2,6 L", formatter.formatAndConvert(5.50, VolumeUnit.PINT, UnitSystem.METRIC));
        assertEquals("20,82 L", formatter.formatAndConvert(5.50, VolumeUnit.GALLON, UnitSystem.METRIC));
        assertEquals("1,56 hL", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_FOOT,
                UnitSystem.METRIC));
        assertEquals("8,74 hL", formatter.formatAndConvert(5.50, VolumeUnit.BARREL, UnitSystem.METRIC));

        assertEquals("0,34 in³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_CENTIMETER,
                UnitSystem.IMPERIAL));
        assertEquals("0,34 in³", formatter.formatAndConvert(5.50, VolumeUnit.MILLILITER,
                UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_DECIMETER,
                UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(5.50, VolumeUnit.LITER,
                UnitSystem.IMPERIAL));
        assertEquals("3,46 bbl", formatter.formatAndConvert(5.50, VolumeUnit.HECTOLITER,
                UnitSystem.IMPERIAL));
        assertEquals("34,59 bbl", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_METER,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 in³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_INCH,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 pt", formatter.formatAndConvert(5.50, VolumeUnit.PINT, UnitSystem.IMPERIAL));
        assertEquals("5,5 gal", formatter.formatAndConvert(5.50, VolumeUnit.GALLON,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 ft³", formatter.formatAndConvert(5.50, VolumeUnit.CUBIC_FOOT,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 bbl", formatter.formatAndConvert(5.50, VolumeUnit.BARREL,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertVolumeAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_CENTIMETER),
                UnitSystem.METRIC));
        assertEquals("5,5 cm³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.MILLILITER),
                UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_DECIMETER),
                UnitSystem.METRIC));
        assertEquals("5,5 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.LITER),
                UnitSystem.METRIC));
        assertEquals("5,5 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.HECTOLITER),
                UnitSystem.METRIC));
        assertEquals("5,5 m³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_METER),
                UnitSystem.METRIC));
        assertEquals("90,13 cm³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_INCH),
                UnitSystem.METRIC));
        assertEquals("2,6 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.PINT),
                UnitSystem.METRIC));
        assertEquals("20,82 L", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.GALLON),
                UnitSystem.METRIC));
        assertEquals("1,56 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_FOOT),
                UnitSystem.METRIC));
        assertEquals("8,74 hL", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.BARREL),
                UnitSystem.METRIC));

        assertEquals("0,34 in³", formatter.formatAndConvert(new Volume(5.50,
                VolumeUnit.CUBIC_CENTIMETER), UnitSystem.IMPERIAL));
        assertEquals("0,34 in³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.MILLILITER),
                UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_DECIMETER),
                UnitSystem.IMPERIAL));
        assertEquals("1,45 gal", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.LITER),
                UnitSystem.IMPERIAL));
        assertEquals("3,46 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.HECTOLITER),
                UnitSystem.IMPERIAL));
        assertEquals("34,59 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_METER),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 in³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_INCH),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 pt", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.PINT),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 gal", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.GALLON),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 ft³", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.CUBIC_FOOT),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 bbl", formatter.formatAndConvert(new Volume(5.50, VolumeUnit.BARREL),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 cm³", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("5,5 cm³", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER));
        assertEquals("5,5 L", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER));
        assertEquals("5,5 L", formatter.formatAndConvertMetric(new BigDecimal("5.50"), VolumeUnit.LITER));
        assertEquals("5,5 hL", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER));
        assertEquals("5,5 m³", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER));
        assertEquals("90,13 cm³", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH));
        assertEquals("2,6 L", formatter.formatAndConvertMetric(new BigDecimal("5.50"), VolumeUnit.PINT));
        assertEquals("20,82 L", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.GALLON));
        assertEquals("1,56 hL", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT));
        assertEquals("8,74 hL", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.BARREL));
    }

    @Test
    void testFormatAndConvertImperial() {
        var l = new Locale("es", "ES");

        var formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,34 in³", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER));
        assertEquals("0,34 in³", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER));
        assertEquals("1,45 gal", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER));
        assertEquals("1,45 gal", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.LITER));
        assertEquals("3,46 bbl", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER));
        assertEquals("34,59 bbl", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER));
        assertEquals("5,5 in³", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH));
        assertEquals("5,5 pt", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.PINT));
        assertEquals("5,5 gal", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.GALLON));
        assertEquals("5,5 ft³", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT));
        assertEquals("5,5 bbl", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.BARREL));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = VolumeFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new VolumeFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new VolumeFormatter();

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
        var formatter = new VolumeFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new VolumeFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new VolumeFormatter();

        assertTrue(formatter.isValidUnit("cm³"));
        assertTrue(formatter.isValidUnit("cm³ "));

        assertTrue(formatter.isValidUnit("mL"));
        assertTrue(formatter.isValidUnit("mL "));

        assertTrue(formatter.isValidUnit("dm³"));
        assertTrue(formatter.isValidUnit("dm³ "));

        assertTrue(formatter.isValidUnit("L"));
        assertTrue(formatter.isValidUnit("L "));

        assertTrue(formatter.isValidUnit("hL"));
        assertTrue(formatter.isValidUnit("hL "));

        assertTrue(formatter.isValidUnit("m³"));
        assertTrue(formatter.isValidUnit("m³ "));

        assertTrue(formatter.isValidUnit("in³"));
        assertTrue(formatter.isValidUnit("in³ "));

        assertTrue(formatter.isValidUnit("pt"));
        assertTrue(formatter.isValidUnit("pt "));

        assertTrue(formatter.isValidUnit("gal"));
        assertTrue(formatter.isValidUnit("gal "));

        assertTrue(formatter.isValidUnit("ft³"));
        assertTrue(formatter.isValidUnit("ft³ "));

        assertTrue(formatter.isValidUnit("bbl"));
        assertTrue(formatter.isValidUnit("bbl "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mL";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 dm³";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 L";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 hL";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 m³";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 in³";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 pt";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 gal";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ft³";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 bbl";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 mL";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 dm³";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 L";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 hL";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 m³";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 in³";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 pt";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 gal";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 ft³";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 bbl";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));

        text = "m";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
     void testIsImperialUnit() {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 mL";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 dm³";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 L";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 hL";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 m³";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 in³";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 pt";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 gal";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 ft³";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 bbl";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));

        text = "m";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 mL";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 dm³";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 L";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 hL";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 m³";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 in³";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 pt";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 gal";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 ft³";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 bbl";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));

        text = "m";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        var v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_CENTIMETER, v.getUnit());

        text = "5,5 mL";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.MILLILITER, v.getUnit());

        text = "5,5 dm³";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_DECIMETER, v.getUnit());

        text = "5,5 L";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.LITER, v.getUnit());

        text = "5,5 hL";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.HECTOLITER, v.getUnit());

        text = "5,5 m³";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_METER, v.getUnit());

        text = "5,5 in³";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_INCH, v.getUnit());

        text = "5,5 pt";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.PINT, v.getUnit());

        text = "5,5 gal";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.GALLON, v.getUnit());

        text = "5,5 ft³";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_FOOT, v.getUnit());

        text = "5,5 bbl";
        v = formatter.parse(text);
        assertEquals(5.5, v.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.BARREL, v.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new VolumeFormatter(new Locale("es", "ES"));

        var text = "5,5 cm³";
        assertEquals(VolumeUnit.CUBIC_CENTIMETER, formatter.findUnit(text));

        text = "5,5 mL";
        assertEquals(VolumeUnit.MILLILITER, formatter.findUnit(text));

        text = "5,5 dm³";
        assertEquals(VolumeUnit.CUBIC_DECIMETER, formatter.findUnit(text));

        text = "5,5 L";
        assertEquals(VolumeUnit.LITER, formatter.findUnit(text));

        text = "5,5 hL";
        assertEquals(VolumeUnit.HECTOLITER, formatter.findUnit(text));

        text = "5,5 m³";
        assertEquals(VolumeUnit.CUBIC_METER, formatter.findUnit(text));

        text = "5,5 in³";
        assertEquals(VolumeUnit.CUBIC_INCH, formatter.findUnit(text));

        text = "5,5 pt";
        assertEquals(VolumeUnit.PINT, formatter.findUnit(text));

        text = "5,5 gal";
        assertEquals(VolumeUnit.GALLON, formatter.findUnit(text));

        text = "5,5 ft³";
        assertEquals(VolumeUnit.CUBIC_FOOT, formatter.findUnit(text));

        text = "5,5 bbl";
        assertEquals(VolumeUnit.BARREL, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));

        text = "m";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new VolumeFormatter();

        assertEquals(VolumeFormatter.CUBIC_CENTIMETER, formatter.getUnitSymbol(VolumeUnit.CUBIC_CENTIMETER));
        assertEquals(VolumeFormatter.MILLILITER, formatter.getUnitSymbol(VolumeUnit.MILLILITER));
        assertEquals(VolumeFormatter.CUBIC_DECIMETER, formatter.getUnitSymbol(VolumeUnit.CUBIC_DECIMETER));
        assertEquals(VolumeFormatter.LITER, formatter.getUnitSymbol(VolumeUnit.LITER));
        assertEquals(VolumeFormatter.HECTOLITER, formatter.getUnitSymbol(VolumeUnit.HECTOLITER));
        assertEquals(VolumeFormatter.CUBIC_METER, formatter.getUnitSymbol(VolumeUnit.CUBIC_METER));
        assertEquals(VolumeFormatter.CUBIC_INCH, formatter.getUnitSymbol(VolumeUnit.CUBIC_INCH));
        assertEquals(VolumeFormatter.PINT, formatter.getUnitSymbol(VolumeUnit.PINT));
        assertEquals(VolumeFormatter.GALLON, formatter.getUnitSymbol(VolumeUnit.GALLON));
        assertEquals(VolumeFormatter.CUBIC_FOOT, formatter.getUnitSymbol(VolumeUnit.CUBIC_FOOT));
        assertEquals(VolumeFormatter.BARREL, formatter.getUnitSymbol(VolumeUnit.BARREL));
    }
}
