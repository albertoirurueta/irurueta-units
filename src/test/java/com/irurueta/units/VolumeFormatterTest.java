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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class VolumeFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        VolumeFormatter formatter = new VolumeFormatter();

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
        formatter = new VolumeFormatter(locale);

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
            formatter = new VolumeFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(formatter);


        // test copy constructor
        formatter = new VolumeFormatter(locale);
        final VolumeFormatter formatter2 = new VolumeFormatter(formatter);

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
            formatter = new VolumeFormatter((VolumeFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final VolumeFormatter formatter1 = new VolumeFormatter();
        final VolumeFormatter formatter2 = (VolumeFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, VolumeUnit.LITER,
                new StringBuffer(), new FieldPosition(0)));
        final VolumeFormatter formatter3 = (VolumeFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final VolumeFormatter formatter1 = new VolumeFormatter(Locale.ENGLISH);
        final VolumeFormatter formatter2 = new VolumeFormatter(Locale.ENGLISH);
        final VolumeFormatter formatter3 = new VolumeFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final VolumeFormatter formatter1 = new VolumeFormatter(Locale.ENGLISH);
        final VolumeFormatter formatter2 = new VolumeFormatter(Locale.ENGLISH);
        final VolumeFormatter formatter3 = new VolumeFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_CENTIMETER), "5,5 cm³");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.MILLILITER), "5,5 mL");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_DECIMETER), "5,5 dm³");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.LITER), "5,5 L");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.HECTOLITER), "5,5 hL");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_METER), "5,5 m³");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_INCH), "5,5 in³");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.PINT), "5,5 pt");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.GALLON), "5,5 gal");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_FOOT), "5,5 ft³");
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.BARREL), "5,5 bbl");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_CENTIMETER, buffer, new FieldPosition(0)).toString(),
                "5,5 cm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.MILLILITER, buffer, new FieldPosition(0)).toString(),
                "5,5 mL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_DECIMETER, buffer, new FieldPosition(0)).toString(),
                "5,5 dm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.LITER, buffer, new FieldPosition(0)).toString(),
                "5,5 L");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.HECTOLITER, buffer, new FieldPosition(0)).toString(),
                "5,5 hL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_METER, buffer, new FieldPosition(0)).toString(),
                "5,5 m³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_INCH, buffer, new FieldPosition(0)).toString(),
                "5,5 in³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.PINT, buffer, new FieldPosition(0)).toString(),
                "5,5 pt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.GALLON, buffer, new FieldPosition(0)).toString(),
                "5,5 gal");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.CUBIC_FOOT, buffer, new FieldPosition(0)).toString(),
                "5,5 ft³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                VolumeUnit.BARREL, buffer, new FieldPosition(0)).toString(),
                "5,5 bbl");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        assertEquals(formatter.format(value, VolumeUnit.CUBIC_CENTIMETER), "5,5 cm³");
        assertEquals(formatter.format(value, VolumeUnit.MILLILITER), "5,5 mL");
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_DECIMETER), "5,5 dm³");
        assertEquals(formatter.format(value, VolumeUnit.LITER), "5,5 L");
        assertEquals(formatter.format(value, VolumeUnit.HECTOLITER), "5,5 hL");
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_METER), "5,5 m³");
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_INCH), "5,5 in³");
        assertEquals(formatter.format(value, VolumeUnit.PINT), "5,5 pt");
        assertEquals(formatter.format(value, VolumeUnit.GALLON), "5,5 gal");
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_FOOT), "5,5 ft³");
        assertEquals(formatter.format(value, VolumeUnit.BARREL), "5,5 bbl");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_CENTIMETER, buffer,
                new FieldPosition(0)).toString(), "5,5 cm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.MILLILITER, buffer,
                new FieldPosition(0)).toString(), "5,5 mL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_DECIMETER, buffer,
                new FieldPosition(0)).toString(), "5,5 dm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.LITER, buffer,
                new FieldPosition(0)).toString(), "5,5 L");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.HECTOLITER, buffer,
                new FieldPosition(0)).toString(), "5,5 hL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_METER, buffer,
                new FieldPosition(0)).toString(), "5,5 m³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_INCH, buffer,
                new FieldPosition(0)).toString(), "5,5 in³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.PINT, buffer,
                new FieldPosition(0)).toString(), "5,5 pt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.GALLON, buffer,
                new FieldPosition(0)).toString(), "5,5 gal");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.CUBIC_FOOT, buffer,
                new FieldPosition(0)).toString(), "5,5 ft³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, VolumeUnit.BARREL, buffer,
                new FieldPosition(0)).toString(), "5,5 bbl");
    }

    @Test
    public void testFormatVolume() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_CENTIMETER)),
                "5,5 cm³");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.MILLILITER)),
                "5,5 mL");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_DECIMETER)),
                "5,5 dm³");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.LITER)),
                "5,5 L");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.HECTOLITER)),
                "5,5 hL");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_METER)),
                "5,5 m³");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_INCH)),
                "5,5 in³");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.PINT)),
                "5,5 pt");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.GALLON)),
                "5,5 gal");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_FOOT)),
                "5,5 ft³");
        assertEquals(formatter.format(new Volume(value, VolumeUnit.BARREL)),
                "5,5 bbl");
    }

    @Test
    public void testFormatVolumeAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final VolumeFormatter formatter = new VolumeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_CENTIMETER), buffer,
                new FieldPosition(0)).toString(), "5,5 cm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.MILLILITER), buffer,
                new FieldPosition(0)).toString(), "5,5 mL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_DECIMETER), buffer,
                new FieldPosition(0)).toString(), "5,5 dm³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.LITER), buffer,
                new FieldPosition(0)).toString(), "5,5 L");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.HECTOLITER), buffer,
                new FieldPosition(0)).toString(), "5,5 hL");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_METER), buffer,
                new FieldPosition(0)).toString(), "5,5 m³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_INCH), buffer,
                new FieldPosition(0)).toString(), "5,5 in³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.PINT), buffer,
                new FieldPosition(0)).toString(), "5,5 pt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.GALLON), buffer,
                new FieldPosition(0)).toString(), "5,5 gal");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.CUBIC_FOOT), buffer,
                new FieldPosition(0)).toString(), "5,5 ft³");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Volume(value, VolumeUnit.BARREL), buffer,
                new FieldPosition(0)).toString(), "5,5 bbl");
    }

    @Test
    public void testFormatAndConvertNumber() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000.50"),
                VolumeUnit.MILLILITER), "5 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER), "5,5 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.LITER), "5,5 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("500.50"),
                VolumeUnit.LITER), "5 hL");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER), "5,5 hL");
        assertEquals(formatter.formatAndConvert(new BigDecimal("50.50"),
                VolumeUnit.HECTOLITER), "5,05 m³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER), "5,5 m³");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH), "90,13 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.PINT), "2,6 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.GALLON), "20,82 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT), "1,56 hL");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.BARREL), "8,74 hL");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER), "0.34 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER), "0.34 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER), "1.45 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.LITER), "1.45 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER), "3.46 bbl");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER), "34.59 bbl");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH), "5.5 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.PINT), "5.5 pt");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.GALLON), "5.5 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT), "5.5 ft³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.BARREL), "5.5 bbl");

    }

    @Test
    public void testFormatAndConvertDouble() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_CENTIMETER), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.MILLILITER), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(5000.50,
                VolumeUnit.MILLILITER), "5 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_DECIMETER), "5,5 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.LITER), "5,5 L");
        assertEquals(formatter.formatAndConvert(500.50,
                VolumeUnit.LITER), "5 hL");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.HECTOLITER), "5,5 hL");
        assertEquals(formatter.formatAndConvert(50.50,
                VolumeUnit.HECTOLITER), "5,05 m³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_METER), "5,5 m³");

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_INCH), "90,13 cm³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.PINT), "2,6 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.GALLON), "20,82 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_FOOT), "1,56 hL");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.BARREL), "8,74 hL");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_CENTIMETER), "0.34 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.MILLILITER), "0.34 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_DECIMETER), "1.45 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.LITER), "1.45 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.HECTOLITER), "3.46 bbl");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_METER), "34.59 bbl");

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_INCH), "5.5 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.PINT), "5.5 pt");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.GALLON), "5.5 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_FOOT), "5.5 ft³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.BARREL), "5.5 bbl");

    }

    @Test
    public void testFormatAndConvertVolume() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_CENTIMETER)), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.MILLILITER)), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5000.50, VolumeUnit.MILLILITER)), "5 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_DECIMETER)), "5,5 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.LITER)), "5,5 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(500.50, VolumeUnit.LITER)), "5 hL");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.HECTOLITER)), "5,5 hL");
        assertEquals(formatter.formatAndConvert(
                new Volume(50.50, VolumeUnit.HECTOLITER)), "5,05 m³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_METER)), "5,5 m³");

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_INCH)), "90,13 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.PINT)), "2,6 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.GALLON)), "20,82 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_FOOT)), "1,56 hL");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.BARREL)), "8,74 hL");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_CENTIMETER)), "0.34 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.MILLILITER)), "0.34 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_DECIMETER)), "1.45 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.LITER)), "1.45 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.HECTOLITER)), "3.46 bbl");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_METER)), "34.59 bbl");

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_INCH)), "5.5 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.PINT)), "5.5 pt");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.GALLON)), "5.5 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_FOOT)), "5.5 ft³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.BARREL)), "5.5 bbl");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.METRIC), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER, UnitSystem.METRIC), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.METRIC), "5,5 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.LITER, UnitSystem.METRIC), "5,5 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER, UnitSystem.METRIC), "5,5 hL");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER, UnitSystem.METRIC), "5,5 m³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH, UnitSystem.METRIC), "90,13 cm³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.PINT, UnitSystem.METRIC), "2,6 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.GALLON, UnitSystem.METRIC), "20,82 L");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT, UnitSystem.METRIC), "1,56 hL");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.BARREL, UnitSystem.METRIC), "8,74 hL");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.IMPERIAL), "0,34 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER, UnitSystem.IMPERIAL), "0,34 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.IMPERIAL), "1,45 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.LITER, UnitSystem.IMPERIAL), "1,45 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER, UnitSystem.IMPERIAL), "3,46 bbl");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER, UnitSystem.IMPERIAL), "34,59 bbl");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH, UnitSystem.IMPERIAL), "5,5 in³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.PINT, UnitSystem.IMPERIAL), "5,5 pt");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.GALLON, UnitSystem.IMPERIAL), "5,5 gal");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT, UnitSystem.IMPERIAL), "5,5 ft³");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                VolumeUnit.BARREL, UnitSystem.IMPERIAL), "5,5 bbl");

    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.METRIC), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.MILLILITER, UnitSystem.METRIC), "5,5 cm³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.METRIC), "5,5 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.LITER, UnitSystem.METRIC), "5,5 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.HECTOLITER, UnitSystem.METRIC), "5,5 hL");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_METER, UnitSystem.METRIC), "5,5 m³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_INCH, UnitSystem.METRIC), "90,13 cm³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.PINT, UnitSystem.METRIC), "2,6 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.GALLON, UnitSystem.METRIC), "20,82 L");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_FOOT, UnitSystem.METRIC), "1,56 hL");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.BARREL, UnitSystem.METRIC), "8,74 hL");

        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_CENTIMETER, UnitSystem.IMPERIAL), "0,34 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.MILLILITER, UnitSystem.IMPERIAL), "0,34 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_DECIMETER, UnitSystem.IMPERIAL), "1,45 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.LITER, UnitSystem.IMPERIAL), "1,45 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.HECTOLITER, UnitSystem.IMPERIAL), "3,46 bbl");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_METER, UnitSystem.IMPERIAL), "34,59 bbl");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_INCH, UnitSystem.IMPERIAL), "5,5 in³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.PINT, UnitSystem.IMPERIAL), "5,5 pt");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.GALLON, UnitSystem.IMPERIAL), "5,5 gal");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.CUBIC_FOOT, UnitSystem.IMPERIAL), "5,5 ft³");
        assertEquals(formatter.formatAndConvert(5.50,
                VolumeUnit.BARREL, UnitSystem.IMPERIAL), "5,5 bbl");
    }

    @Test
    public void testFormatAndConvertVolumeAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_CENTIMETER), UnitSystem.METRIC),
                "5,5 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.MILLILITER), UnitSystem.METRIC),
                "5,5 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_DECIMETER), UnitSystem.METRIC),
                "5,5 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.LITER), UnitSystem.METRIC),
                "5,5 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.HECTOLITER), UnitSystem.METRIC),
                "5,5 hL");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_METER), UnitSystem.METRIC),
                "5,5 m³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_INCH), UnitSystem.METRIC),
                "90,13 cm³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.PINT), UnitSystem.METRIC),
                "2,6 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.GALLON), UnitSystem.METRIC),
                "20,82 L");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_FOOT), UnitSystem.METRIC),
                "1,56 hL");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.BARREL), UnitSystem.METRIC),
                "8,74 hL");

        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_CENTIMETER), UnitSystem.IMPERIAL),
                "0,34 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.MILLILITER), UnitSystem.IMPERIAL),
                "0,34 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_DECIMETER), UnitSystem.IMPERIAL),
                "1,45 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.LITER), UnitSystem.IMPERIAL),
                "1,45 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.HECTOLITER), UnitSystem.IMPERIAL),
                "3,46 bbl");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_METER), UnitSystem.IMPERIAL),
                "34,59 bbl");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_INCH), UnitSystem.IMPERIAL),
                "5,5 in³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.PINT), UnitSystem.IMPERIAL),
                "5,5 pt");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.GALLON), UnitSystem.IMPERIAL), "5,5 gal");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.CUBIC_FOOT), UnitSystem.IMPERIAL),
                "5,5 ft³");
        assertEquals(formatter.formatAndConvert(
                new Volume(5.50, VolumeUnit.BARREL), UnitSystem.IMPERIAL),
                "5,5 bbl");
    }

    @Test
    public void testFormatAndConvertMetric() {
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER), "5,5 cm³");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER), "5,5 cm³");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER), "5,5 L");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.LITER), "5,5 L");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER), "5,5 hL");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER), "5,5 m³");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH), "90,13 cm³");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.PINT), "2,6 L");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.GALLON), "20,82 L");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT), "1,56 hL");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                VolumeUnit.BARREL), "8,74 hL");
    }

    @Test
    public void testFormatAndConvertImperial() {
        Locale l = new Locale("es", "ES");

        VolumeFormatter formatter = new VolumeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_CENTIMETER), "0,34 in³");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.MILLILITER), "0,34 in³");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_DECIMETER), "1,45 gal");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.LITER), "1,45 gal");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.HECTOLITER), "3,46 bbl");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_METER), "34,59 bbl");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_INCH), "5,5 in³");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.PINT), "5,5 pt");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.GALLON), "5,5 gal");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.CUBIC_FOOT), "5,5 ft³");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                VolumeUnit.BARREL), "5,5 bbl");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = VolumeFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final VolumeFormatter formatter = new VolumeFormatter();

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
        final VolumeFormatter formatter = new VolumeFormatter();

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
        final VolumeFormatter formatter = new VolumeFormatter();

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
        VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new VolumeFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        final VolumeFormatter formatter = new VolumeFormatter();

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
    public void testIsValidMeasurement() {
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
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
    public void testIsMetricUnit() {
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
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
    public void testIsImperialUnit() {
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
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
    public void testGetUnitSystemFromSource() {
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 mL";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 dm³";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 L";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 hL";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 m³";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 in³";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 pt";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 gal";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 ft³";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 bbl";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));

        text = "m";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
        Volume v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_CENTIMETER);

        text = "5,5 mL";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.MILLILITER);

        text = "5,5 dm³";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_DECIMETER);

        text = "5,5 L";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.LITER);

        text = "5,5 hL";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.HECTOLITER);

        text = "5,5 m³";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_METER);

        text = "5,5 in³";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_INCH);

        text = "5,5 pt";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.PINT);

        text = "5,5 gal";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.GALLON);

        text = "5,5 ft³";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_FOOT);

        text = "5,5 bbl";
        v = formatter.parse(text);
        assertEquals(v.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(v.getUnit(), VolumeUnit.BARREL);

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
        final VolumeFormatter formatter = new VolumeFormatter(
                new Locale("es", "ES"));

        String text = "5,5 cm³";
        assertEquals(formatter.findUnit(text), VolumeUnit.CUBIC_CENTIMETER);

        text = "5,5 mL";
        assertEquals(formatter.findUnit(text), VolumeUnit.MILLILITER);

        text = "5,5 dm³";
        assertEquals(formatter.findUnit(text), VolumeUnit.CUBIC_DECIMETER);

        text = "5,5 L";
        assertEquals(formatter.findUnit(text), VolumeUnit.LITER);

        text = "5,5 hL";
        assertEquals(formatter.findUnit(text), VolumeUnit.HECTOLITER);

        text = "5,5 m³";
        assertEquals(formatter.findUnit(text), VolumeUnit.CUBIC_METER);

        text = "5,5 in³";
        assertEquals(formatter.findUnit(text), VolumeUnit.CUBIC_INCH);

        text = "5,5 pt";
        assertEquals(formatter.findUnit(text), VolumeUnit.PINT);

        text = "5,5 gal";
        assertEquals(formatter.findUnit(text), VolumeUnit.GALLON);

        text = "5,5 ft³";
        assertEquals(formatter.findUnit(text), VolumeUnit.CUBIC_FOOT);

        text = "5,5 bbl";
        assertEquals(formatter.findUnit(text), VolumeUnit.BARREL);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));

        text = "m";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymvol() {
        final VolumeFormatter formatter = new VolumeFormatter();

        assertEquals(formatter.getUnitSymbol(VolumeUnit.CUBIC_CENTIMETER),
                VolumeFormatter.CUBIC_CENTIMETER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.MILLILITER),
                VolumeFormatter.MILLILITER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.CUBIC_DECIMETER),
                VolumeFormatter.CUBIC_DECIMETER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.LITER),
                VolumeFormatter.LITER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.HECTOLITER),
                VolumeFormatter.HECTOLITER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.CUBIC_METER),
                VolumeFormatter.CUBIC_METER);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.CUBIC_INCH),
                VolumeFormatter.CUBIC_INCH);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.PINT),
                VolumeFormatter.PINT);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.GALLON),
                VolumeFormatter.GALLON);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.CUBIC_FOOT),
                VolumeFormatter.CUBIC_FOOT);
        assertEquals(formatter.getUnitSymbol(VolumeUnit.BARREL),
                VolumeFormatter.BARREL);
    }
}
