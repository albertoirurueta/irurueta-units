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

public class WeightFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        WeightFormatter formatter = new WeightFormatter();

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
        formatter = new WeightFormatter(locale);

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
            formatter = new WeightFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(formatter);


        // test copy constructor
        formatter = new WeightFormatter(locale);
        final WeightFormatter formatter2 = new WeightFormatter(formatter);

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
            formatter = new WeightFormatter((WeightFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final WeightFormatter formatter1 = new WeightFormatter();
        final WeightFormatter formatter2 = (WeightFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, WeightUnit.GRAM,
                new StringBuffer(), new FieldPosition(0)));
        final WeightFormatter formatter3 = (WeightFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final WeightFormatter formatter1 = new WeightFormatter(Locale.ENGLISH);
        final WeightFormatter formatter2 = new WeightFormatter(Locale.ENGLISH);
        final WeightFormatter formatter3 = new WeightFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final WeightFormatter formatter1 = new WeightFormatter(Locale.ENGLISH);
        final WeightFormatter formatter2 = new WeightFormatter(Locale.ENGLISH);
        final WeightFormatter formatter3 = new WeightFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.PICOGRAM), "5,5 pg");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.NANOGRAM), "5,5 ng");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MICROGRAM), "5,5 µg");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MILLIGRAM), "5,5 mg");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.GRAM), "5,5 g");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.KILOGRAM), "5,5 Kg");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.TONNE), "5,5 t");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MEGATONNE), "5,5 Mt");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.US_TON), "5,5 ton");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.UK_TON), "5,5 ton");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.POUND), "5,5 lb");
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.OUNCE), "5,5 oz");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.PICOGRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 pg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.NANOGRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 ng");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MICROGRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 µg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MILLIGRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 mg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.GRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 g");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.KILOGRAM, buffer, new FieldPosition(0)).toString(),
                "5,5 Kg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.TONNE, buffer, new FieldPosition(0)).toString(),
                "5,5 t");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.MEGATONNE, buffer, new FieldPosition(0)).toString(),
                "5,5 Mt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.US_TON, buffer, new FieldPosition(0)).toString(),
                "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.UK_TON, buffer, new FieldPosition(0)).toString(),
                "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.POUND, buffer, new FieldPosition(0)).toString(),
                "5,5 lb");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                WeightUnit.OUNCE, buffer, new FieldPosition(0)).toString(),
                "5,5 oz");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        assertEquals(formatter.format(value, WeightUnit.PICOGRAM), "5,5 pg");
        assertEquals(formatter.format(value, WeightUnit.NANOGRAM), "5,5 ng");
        assertEquals(formatter.format(value, WeightUnit.MICROGRAM), "5,5 µg");
        assertEquals(formatter.format(value, WeightUnit.MILLIGRAM), "5,5 mg");
        assertEquals(formatter.format(value, WeightUnit.GRAM), "5,5 g");
        assertEquals(formatter.format(value, WeightUnit.KILOGRAM), "5,5 Kg");
        assertEquals(formatter.format(value, WeightUnit.TONNE), "5,5 t");
        assertEquals(formatter.format(value, WeightUnit.MEGATONNE), "5,5 Mt");
        assertEquals(formatter.format(value, WeightUnit.US_TON), "5,5 ton");
        assertEquals(formatter.format(value, WeightUnit.UK_TON), "5,5 ton");
        assertEquals(formatter.format(value, WeightUnit.POUND), "5,5 lb");
        assertEquals(formatter.format(value, WeightUnit.OUNCE), "5,5 oz");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.PICOGRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 pg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.NANOGRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 ng");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.MICROGRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 µg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.MILLIGRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 mg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.GRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 g");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.KILOGRAM, buffer,
                new FieldPosition(0)).toString(), "5,5 Kg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.TONNE, buffer,
                new FieldPosition(0)).toString(), "5,5 t");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.MEGATONNE, buffer,
                new FieldPosition(0)).toString(), "5,5 Mt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.US_TON, buffer,
                new FieldPosition(0)).toString(), "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.UK_TON, buffer,
                new FieldPosition(0)).toString(), "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.POUND, buffer,
                new FieldPosition(0)).toString(), "5,5 lb");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, WeightUnit.OUNCE, buffer,
                new FieldPosition(0)).toString(), "5,5 oz");
    }

    @Test
    public void testFormatWeight() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        assertEquals(formatter.format(new Weight(value, WeightUnit.PICOGRAM)), "5,5 pg");
        assertEquals(formatter.format(new Weight(value, WeightUnit.NANOGRAM)), "5,5 ng");
        assertEquals(formatter.format(new Weight(value, WeightUnit.MICROGRAM)), "5,5 µg");
        assertEquals(formatter.format(new Weight(value, WeightUnit.MILLIGRAM)), "5,5 mg");
        assertEquals(formatter.format(new Weight(value, WeightUnit.GRAM)), "5,5 g");
        assertEquals(formatter.format(new Weight(value, WeightUnit.KILOGRAM)), "5,5 Kg");
        assertEquals(formatter.format(new Weight(value, WeightUnit.TONNE)), "5,5 t");
        assertEquals(formatter.format(new Weight(value, WeightUnit.MEGATONNE)), "5,5 Mt");
        assertEquals(formatter.format(new Weight(value, WeightUnit.US_TON)), "5,5 ton");
        assertEquals(formatter.format(new Weight(value, WeightUnit.UK_TON)), "5,5 ton");
        assertEquals(formatter.format(new Weight(value, WeightUnit.POUND)), "5,5 lb");
        assertEquals(formatter.format(new Weight(value, WeightUnit.OUNCE)), "5,5 oz");
    }

    @Test
    public void testFormatWeightAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final WeightFormatter formatter = new WeightFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.PICOGRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 pg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.NANOGRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 ng");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.MICROGRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 µg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.MILLIGRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 mg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.GRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 g");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.KILOGRAM), buffer,
                new FieldPosition(0)).toString(), "5,5 Kg");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.TONNE), buffer,
                new FieldPosition(0)).toString(), "5,5 t");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.MEGATONNE), buffer,
                new FieldPosition(0)).toString(), "5,5 Mt");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.US_TON), buffer,
                new FieldPosition(0)).toString(), "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.UK_TON), buffer,
                new FieldPosition(0)).toString(), "5,5 ton");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.POUND), buffer,
                new FieldPosition(0)).toString(), "5,5 lb");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Weight(value, WeightUnit.OUNCE), buffer,
                new FieldPosition(0)).toString(), "5,5 oz");
    }

    @Test
    public void testFormatAndConvertNumber() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.PICOGRAM), "5,5 pg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000.50"),
                WeightUnit.PICOGRAM), "5 ng");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000.50"),
                WeightUnit.PICOGRAM), "5 µg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000000.50"),
                WeightUnit.PICOGRAM), "5 mg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000000000.50"),
                WeightUnit.PICOGRAM), "5 g");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000000000000.50"),
                WeightUnit.PICOGRAM), "5 Kg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000000000000000.50"),
                WeightUnit.PICOGRAM), "5 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5000000000000000000000000.50"),
                WeightUnit.PICOGRAM), "5 Mt");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM), "5,5 ng");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM), "5,5 µg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM), "5,5 mg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM), "5,5 g");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE), "5,5 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON), "4,99 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON), "5,59 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE), "155,92 g");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM), "0.19 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM), "12.13 lb");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE), "6.06 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE), "6,063,947.08 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON), "5.5 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON), "6.16 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND), "5.5 lb");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE), "5.5 oz");
    }

    @Test
    public void testFormatAndConvertDouble() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.PICOGRAM), "5,5 pg");
        assertEquals(formatter.formatAndConvert(5000.50,
                WeightUnit.PICOGRAM), "5 ng");
        assertEquals(formatter.formatAndConvert(5000000.50,
                WeightUnit.PICOGRAM), "5 µg");
        assertEquals(formatter.formatAndConvert(5000000000.50,
                WeightUnit.PICOGRAM), "5 mg");
        assertEquals(formatter.formatAndConvert(5000000000000.50,
                WeightUnit.PICOGRAM), "5 g");
        assertEquals(formatter.formatAndConvert(5000000000000000.50,
                WeightUnit.PICOGRAM), "5 Kg");
        assertEquals(formatter.formatAndConvert(5000000000000000000.50,
                WeightUnit.PICOGRAM), "5 t");
        assertEquals(formatter.formatAndConvert(5000000000000000000000000.50,
                WeightUnit.PICOGRAM), "5 Mt");

        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.NANOGRAM), "5,5 ng");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MICROGRAM), "5,5 µg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MILLIGRAM), "5,5 mg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.GRAM), "5,5 g");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.KILOGRAM), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.TONNE), "5,5 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MEGATONNE), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.US_TON), "4,99 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.UK_TON), "5,59 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.POUND), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.OUNCE), "155,92 g");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.NANOGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MICROGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MILLIGRAM), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.GRAM), "0.19 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.KILOGRAM), "12.13 lb");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.TONNE), "6.06 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MEGATONNE), "6,063,947.08 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.US_TON), "5.5 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.UK_TON), "6.16 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.POUND), "5.5 lb");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.OUNCE), "5.5 oz");
    }

    @Test
    public void testFormatAndConvertWeight() {
        // test for metric system
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.PICOGRAM)), "5,5 pg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000.50, WeightUnit.PICOGRAM)), "5 ng");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000.50, WeightUnit.PICOGRAM)), "5 µg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000000.50, WeightUnit.PICOGRAM)), "5 mg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000000000.50, WeightUnit.PICOGRAM)), "5 g");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000000000000.50, WeightUnit.PICOGRAM)), "5 Kg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000000000000000.50, WeightUnit.PICOGRAM)), "5 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5000000000000000000000000.50, WeightUnit.PICOGRAM)),
                "5 Mt");

        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.NANOGRAM)), "5,5 ng");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MICROGRAM)), "5,5 µg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MILLIGRAM)), "5,5 mg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.GRAM)), "5,5 g");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.KILOGRAM)), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.TONNE)), "5,5 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MEGATONNE)), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.US_TON)), "4,99 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.UK_TON)), "5,59 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.POUND)), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.OUNCE)), "155,92 g");

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.NANOGRAM)), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MICROGRAM)), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MILLIGRAM)), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.GRAM)), "0.19 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.KILOGRAM)), "12.13 lb");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.TONNE)), "6.06 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MEGATONNE)), "6,063,947.08 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.US_TON)), "5.5 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.UK_TON)), "6.16 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.POUND)), "5.5 lb");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.OUNCE)), "5.5 oz");
    }

    @Test
    public void testFormatAnConvertNumberAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM, UnitSystem.METRIC), "5,5 ng");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM, UnitSystem.METRIC), "5,5 µg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM, UnitSystem.METRIC), "5,5 mg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM, UnitSystem.METRIC), "5,5 g");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM, UnitSystem.METRIC), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE, UnitSystem.METRIC), "5,5 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE, UnitSystem.METRIC), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON, UnitSystem.METRIC), "4,99 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON, UnitSystem.METRIC), "5,59 t");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND, UnitSystem.METRIC), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE, UnitSystem.METRIC), "155,92 g");


        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM, UnitSystem.IMPERIAL), "0,19 oz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM, UnitSystem.IMPERIAL), "12,13 lb");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE, UnitSystem.IMPERIAL), "6,06 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE, UnitSystem.IMPERIAL), "6.063.947,08 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON, UnitSystem.IMPERIAL), "5,5 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON, UnitSystem.IMPERIAL), "6,16 ton");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND, UnitSystem.IMPERIAL), "5,5 lb");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE, UnitSystem.IMPERIAL), "5,5 oz");

    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.NANOGRAM, UnitSystem.METRIC), "5,5 ng");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MICROGRAM, UnitSystem.METRIC), "5,5 µg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MILLIGRAM, UnitSystem.METRIC), "5,5 mg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.GRAM, UnitSystem.METRIC), "5,5 g");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.KILOGRAM, UnitSystem.METRIC), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.TONNE, UnitSystem.METRIC), "5,5 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MEGATONNE, UnitSystem.METRIC), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.US_TON, UnitSystem.METRIC), "4,99 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.UK_TON, UnitSystem.METRIC), "5,59 t");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.POUND, UnitSystem.METRIC), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.OUNCE, UnitSystem.METRIC), "155,92 g");


        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.NANOGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MICROGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MILLIGRAM, UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.GRAM, UnitSystem.IMPERIAL), "0,19 oz");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.KILOGRAM, UnitSystem.IMPERIAL), "12,13 lb");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.TONNE, UnitSystem.IMPERIAL), "6,06 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.MEGATONNE, UnitSystem.IMPERIAL), "6.063.947,08 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.US_TON, UnitSystem.IMPERIAL), "5,5 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.UK_TON, UnitSystem.IMPERIAL), "6,16 ton");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.POUND, UnitSystem.IMPERIAL), "5,5 lb");
        assertEquals(formatter.formatAndConvert(5.50,
                WeightUnit.OUNCE, UnitSystem.IMPERIAL), "5,5 oz");

    }

    @Test
    public void testFormatAndConvertWeightAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.NANOGRAM), UnitSystem.METRIC), "5,5 ng");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MICROGRAM), UnitSystem.METRIC), "5,5 µg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MILLIGRAM), UnitSystem.METRIC), "5,5 mg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.GRAM), UnitSystem.METRIC), "5,5 g");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.KILOGRAM), UnitSystem.METRIC), "5,5 Kg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.TONNE), UnitSystem.METRIC), "5,5 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MEGATONNE), UnitSystem.METRIC), "5,5 Mt");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.US_TON), UnitSystem.METRIC), "4,99 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.UK_TON), UnitSystem.METRIC), "5,59 t");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.POUND), UnitSystem.METRIC), "2,49 Kg");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.OUNCE), UnitSystem.METRIC), "155,92 g");


        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.NANOGRAM), UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MICROGRAM), UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MILLIGRAM), UnitSystem.IMPERIAL), "0 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.GRAM), UnitSystem.IMPERIAL), "0,19 oz");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.KILOGRAM), UnitSystem.IMPERIAL),
                "12,13 lb");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.TONNE), UnitSystem.IMPERIAL),
                "6,06 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.MEGATONNE), UnitSystem.IMPERIAL),
                "6.063.947,08 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.US_TON), UnitSystem.IMPERIAL),
                "5,5 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.UK_TON), UnitSystem.IMPERIAL), "6,16 ton");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.POUND), UnitSystem.IMPERIAL), "5,5 lb");
        assertEquals(formatter.formatAndConvert(
                new Weight(5.50, WeightUnit.OUNCE), UnitSystem.IMPERIAL), "5,5 oz");
    }

    @Test
    public void testFormatAndConvertMetric() {
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM), "5,5 ng");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM), "5,5 µg");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM), "5,5 mg");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.GRAM), "5,5 g");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM), "5,5 Kg");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.TONNE), "5,5 t");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE), "5,5 Mt");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.US_TON), "4,99 t");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.UK_TON), "5,59 t");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.POUND), "2,49 Kg");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.OUNCE), "155,92 g");
    }

    @Test
    public void testFormatAndConvertImperial() {
        Locale l = new Locale("es", "ES");

        WeightFormatter formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM), "0 oz");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM), "0 oz");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM), "0 oz");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.GRAM), "0,19 oz");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM), "12,13 lb");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.TONNE), "6,06 ton");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE), "6.063.947,08 ton");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.US_TON), "5,5 ton");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.UK_TON), "6,16 ton");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.POUND), "5,5 lb");
        assertEquals(formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.OUNCE), "5,5 oz");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = WeightFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final WeightFormatter formatter = new WeightFormatter();

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
        final WeightFormatter formatter = new WeightFormatter();

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
        final WeightFormatter formatter = new WeightFormatter();

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
        WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new WeightFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.IMPERIAL);
    }

    @Test
    public void testIsValidUnit() {
        final WeightFormatter formatter = new WeightFormatter();

        assertTrue(formatter.isValidUnit("pg"));
        assertTrue(formatter.isValidUnit("pg "));

        assertTrue(formatter.isValidUnit("ng"));
        assertTrue(formatter.isValidUnit("ng "));

        assertTrue(formatter.isValidUnit("µg"));
        assertTrue(formatter.isValidUnit("µg "));

        assertTrue(formatter.isValidUnit("mg"));
        assertTrue(formatter.isValidUnit("mg "));

        assertTrue(formatter.isValidUnit("g"));
        assertTrue(formatter.isValidUnit("g "));

        assertTrue(formatter.isValidUnit("Kg"));
        assertTrue(formatter.isValidUnit("Kg "));

        assertTrue(formatter.isValidUnit("t"));
        assertTrue(formatter.isValidUnit("t "));

        assertTrue(formatter.isValidUnit("Mt"));
        assertTrue(formatter.isValidUnit("Mt "));

        assertTrue(formatter.isValidUnit("ton"));
        assertTrue(formatter.isValidUnit("ton "));

        assertTrue(formatter.isValidUnit("lb"));
        assertTrue(formatter.isValidUnit("lb "));

        assertTrue(formatter.isValidUnit("oz"));
        assertTrue(formatter.isValidUnit("oz "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ng";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 µg";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mg";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 g";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Kg";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 t";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Mt";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ton";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 lg";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 oz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ng";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 µg";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 mg";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 g";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Kg";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 t";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Mt";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ton";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 lb";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 oz";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ng";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 µg";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 mg";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 g";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Kg";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 t";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Mt";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ton";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 lb";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 oz";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 ng";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 µg";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 mg";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 g";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 Kg";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 t";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 Mt";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 ton";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 lb";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 oz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.IMPERIAL);

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        Weight w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.PICOGRAM);

        text = "5,5 ng";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.NANOGRAM);

        text = "5,5 µg";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.MICROGRAM);

        text = "5,5 mg";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.MILLIGRAM);

        text = "5,5 g";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.GRAM);

        text = "5,5 Kg";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.KILOGRAM);

        text = "5,5 t";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.TONNE);

        text = "5,5 Mt";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.MEGATONNE);

        text = "5,5 ton";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.US_TON);

        text = "5,5 lb";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.POUND);

        text = "5,5 oz";
        w = formatter.parse(text);
        assertEquals(w.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(w.getUnit(), WeightUnit.OUNCE);

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
        final WeightFormatter formatter = new WeightFormatter(
                new Locale("es", "ES"));

        String text = "5,5 pg";
        assertEquals(formatter.findUnit(text), WeightUnit.PICOGRAM);

        text = "5,5 ng";
        assertEquals(formatter.findUnit(text), WeightUnit.NANOGRAM);

        text = "5,5 µg";
        assertEquals(formatter.findUnit(text), WeightUnit.MICROGRAM);

        text = "5,5 mg";
        assertEquals(formatter.findUnit(text), WeightUnit.MILLIGRAM);

        text = "5,5 g";
        assertEquals(formatter.findUnit(text), WeightUnit.GRAM);

        text = "5,5 Kg";
        assertEquals(formatter.findUnit(text), WeightUnit.KILOGRAM);

        text = "5,5 t";
        assertEquals(formatter.findUnit(text), WeightUnit.TONNE);

        text = "5,5 Mt";
        assertEquals(formatter.findUnit(text), WeightUnit.MEGATONNE);

        text = "5,5 ton";
        assertEquals(formatter.findUnit(text), WeightUnit.US_TON);

        text = "5,5 lb";
        assertEquals(formatter.findUnit(text), WeightUnit.POUND);

        text = "5,5 oz";
        assertEquals(formatter.findUnit(text), WeightUnit.OUNCE);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final WeightFormatter formatter = new WeightFormatter();

        assertEquals(formatter.getUnitSymbol(WeightUnit.PICOGRAM),
                WeightFormatter.PICOGRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.NANOGRAM),
                WeightFormatter.NANOGRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.MICROGRAM),
                WeightFormatter.MICROGRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.MILLIGRAM),
                WeightFormatter.MILLIGRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.GRAM),
                WeightFormatter.GRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.KILOGRAM),
                WeightFormatter.KILOGRAM);
        assertEquals(formatter.getUnitSymbol(WeightUnit.TONNE),
                WeightFormatter.TONNE);
        assertEquals(formatter.getUnitSymbol(WeightUnit.MEGATONNE),
                WeightFormatter.MEGATONNE);
        assertEquals(formatter.getUnitSymbol(WeightUnit.US_TON),
                WeightFormatter.US_UK_TON);
        assertEquals(formatter.getUnitSymbol(WeightUnit.UK_TON),
                WeightFormatter.US_UK_TON);
        assertEquals(formatter.getUnitSymbol(WeightUnit.POUND),
                WeightFormatter.POUND);
        assertEquals(formatter.getUnitSymbol(WeightUnit.OUNCE),
                WeightFormatter.OUNCE);
    }
}
