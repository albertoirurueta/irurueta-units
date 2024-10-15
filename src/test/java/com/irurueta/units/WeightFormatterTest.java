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

class WeightFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new WeightFormatter();

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
        formatter = new WeightFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new WeightFormatter((Locale) null));

        // test copy constructor
        formatter = new WeightFormatter(locale);
        final var formatter2 = new WeightFormatter(formatter);

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

        // Force NullPointerException
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new WeightFormatter((WeightFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new WeightFormatter();
        final var formatter2 = (WeightFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, WeightUnit.GRAM, new StringBuffer(), new FieldPosition(0)));
        final WeightFormatter formatter3 = (WeightFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new WeightFormatter(Locale.ENGLISH);
        final var formatter2 = new WeightFormatter(Locale.ENGLISH);
        final var formatter3 = new WeightFormatter(Locale.FRENCH);

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
        final var formatter1 = new WeightFormatter(Locale.ENGLISH);
        final var formatter2 = new WeightFormatter(Locale.ENGLISH);
        final var formatter3 = new WeightFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        assertEquals("5,5 pg", formatter.format(new BigDecimal(value), WeightUnit.PICOGRAM));
        assertEquals("5,5 ng", formatter.format(new BigDecimal(value), WeightUnit.NANOGRAM));
        assertEquals("5,5 µg", formatter.format(new BigDecimal(value), WeightUnit.MICROGRAM));
        assertEquals("5,5 mg", formatter.format(new BigDecimal(value), WeightUnit.MILLIGRAM));
        assertEquals("5,5 g", formatter.format(new BigDecimal(value), WeightUnit.GRAM));
        assertEquals("5,5 Kg", formatter.format(new BigDecimal(value), WeightUnit.KILOGRAM));
        assertEquals("5,5 t", formatter.format(new BigDecimal(value), WeightUnit.TONNE));
        assertEquals("5,5 Mt", formatter.format(new BigDecimal(value), WeightUnit.MEGATONNE));
        assertEquals("5,5 ton", formatter.format(new BigDecimal(value), WeightUnit.US_TON));
        assertEquals("5,5 ton", formatter.format(new BigDecimal(value), WeightUnit.UK_TON));
        assertEquals("5,5 lb", formatter.format(new BigDecimal(value), WeightUnit.POUND));
        assertEquals("5,5 oz", formatter.format(new BigDecimal(value), WeightUnit.OUNCE));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 pg", formatter.format(new BigDecimal(value), WeightUnit.PICOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ng", formatter.format(new BigDecimal(value), WeightUnit.NANOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µg", formatter.format(new BigDecimal(value), WeightUnit.MICROGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mg", formatter.format(new BigDecimal(value), WeightUnit.MILLIGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g", formatter.format(new BigDecimal(value), WeightUnit.GRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Kg", formatter.format(new BigDecimal(value), WeightUnit.KILOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 t", formatter.format(new BigDecimal(value), WeightUnit.TONNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Mt", formatter.format(new BigDecimal(value), WeightUnit.MEGATONNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(new BigDecimal(value), WeightUnit.US_TON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(new BigDecimal(value), WeightUnit.UK_TON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lb", formatter.format(new BigDecimal(value), WeightUnit.POUND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 oz", formatter.format(new BigDecimal(value), WeightUnit.OUNCE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        assertEquals("5,5 pg", formatter.format(value, WeightUnit.PICOGRAM));
        assertEquals("5,5 ng", formatter.format(value, WeightUnit.NANOGRAM));
        assertEquals("5,5 µg", formatter.format(value, WeightUnit.MICROGRAM));
        assertEquals("5,5 mg", formatter.format(value, WeightUnit.MILLIGRAM));
        assertEquals("5,5 g", formatter.format(value, WeightUnit.GRAM));
        assertEquals("5,5 Kg", formatter.format(value, WeightUnit.KILOGRAM));
        assertEquals("5,5 t", formatter.format(value, WeightUnit.TONNE));
        assertEquals("5,5 Mt", formatter.format(value, WeightUnit.MEGATONNE));
        assertEquals("5,5 ton", formatter.format(value, WeightUnit.US_TON));
        assertEquals("5,5 ton", formatter.format(value, WeightUnit.UK_TON));
        assertEquals("5,5 lb", formatter.format(value, WeightUnit.POUND));
        assertEquals("5,5 oz", formatter.format(value, WeightUnit.OUNCE));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 pg", formatter.format(value, WeightUnit.PICOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ng", formatter.format(value, WeightUnit.NANOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µg", formatter.format(value, WeightUnit.MICROGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mg", formatter.format(value, WeightUnit.MILLIGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g", formatter.format(value, WeightUnit.GRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Kg", formatter.format(value, WeightUnit.KILOGRAM, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 t", formatter.format(value, WeightUnit.TONNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Mt", formatter.format(value, WeightUnit.MEGATONNE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(value, WeightUnit.US_TON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(value, WeightUnit.UK_TON, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lb", formatter.format(value, WeightUnit.POUND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 oz", formatter.format(value, WeightUnit.OUNCE, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatWeight() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        assertEquals("5,5 pg", formatter.format(new Weight(value, WeightUnit.PICOGRAM)));
        assertEquals("5,5 ng", formatter.format(new Weight(value, WeightUnit.NANOGRAM)));
        assertEquals("5,5 µg", formatter.format(new Weight(value, WeightUnit.MICROGRAM)));
        assertEquals("5,5 mg", formatter.format(new Weight(value, WeightUnit.MILLIGRAM)));
        assertEquals("5,5 g", formatter.format(new Weight(value, WeightUnit.GRAM)));
        assertEquals("5,5 Kg", formatter.format(new Weight(value, WeightUnit.KILOGRAM)));
        assertEquals("5,5 t", formatter.format(new Weight(value, WeightUnit.TONNE)));
        assertEquals("5,5 Mt", formatter.format(new Weight(value, WeightUnit.MEGATONNE)));
        assertEquals("5,5 ton", formatter.format(new Weight(value, WeightUnit.US_TON)));
        assertEquals("5,5 ton", formatter.format(new Weight(value, WeightUnit.UK_TON)));
        assertEquals("5,5 lb", formatter.format(new Weight(value, WeightUnit.POUND)));
        assertEquals("5,5 oz", formatter.format(new Weight(value, WeightUnit.OUNCE)));
    }

    @Test
    void testFormatWeightAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new WeightFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 pg", formatter.format(new Weight(value, WeightUnit.PICOGRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ng", formatter.format(new Weight(value, WeightUnit.NANOGRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µg", formatter.format(new Weight(value, WeightUnit.MICROGRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mg", formatter.format(new Weight(value, WeightUnit.MILLIGRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 g", formatter.format(new Weight(value, WeightUnit.GRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Kg", formatter.format(new Weight(value, WeightUnit.KILOGRAM), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 t", formatter.format(new Weight(value, WeightUnit.TONNE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Mt", formatter.format(new Weight(value, WeightUnit.MEGATONNE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(new Weight(value, WeightUnit.US_TON), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ton", formatter.format(new Weight(value, WeightUnit.UK_TON), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 lb", formatter.format(new Weight(value, WeightUnit.POUND), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 oz", formatter.format(new Weight(value, WeightUnit.OUNCE), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 pg", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.PICOGRAM));
        assertEquals("5 ng", formatter.formatAndConvert(new BigDecimal("5000.50"), WeightUnit.PICOGRAM));
        assertEquals("5 µg", formatter.formatAndConvert(new BigDecimal("5000000.50"),
                WeightUnit.PICOGRAM));
        assertEquals("5 mg", formatter.formatAndConvert(new BigDecimal("5000000000.50"),
                WeightUnit.PICOGRAM));
        assertEquals("5 g", formatter.formatAndConvert(new BigDecimal("5000000000000.50"),
                WeightUnit.PICOGRAM));
        assertEquals("5 Kg", formatter.formatAndConvert(new BigDecimal("5000000000000000.50"),
                WeightUnit.PICOGRAM));
        assertEquals("5 t", formatter.formatAndConvert(new BigDecimal("5000000000000000000.50"),
                WeightUnit.PICOGRAM));
        assertEquals("5 Mt", formatter.formatAndConvert(new BigDecimal("5000000000000000000000000.50"),
                WeightUnit.PICOGRAM));

        assertEquals("5,5 ng", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.NANOGRAM));
        assertEquals("5,5 µg", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.MICROGRAM));
        assertEquals("5,5 mg", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.MILLIGRAM));
        assertEquals("5,5 g", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.GRAM));
        assertEquals("5,5 Kg", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.KILOGRAM));
        assertEquals("5,5 t", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.TONNE));
        assertEquals("5,5 Mt", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.MEGATONNE));
        assertEquals("4,99 t", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.US_TON));
        assertEquals("5,59 t", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.UK_TON));
        assertEquals("2,49 Kg", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.POUND));
        assertEquals("155,92 g", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.OUNCE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.NANOGRAM));
        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.MICROGRAM));
        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.MILLIGRAM));
        assertEquals("0.19 oz", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.GRAM));
        assertEquals("12.13 lb", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.KILOGRAM));
        assertEquals("6.06 ton", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.TONNE));
        assertEquals("6,063,947.08 ton", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE));
        assertEquals("5.5 ton", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.US_TON));
        assertEquals("6.16 ton", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.UK_TON));
        assertEquals("5.5 lb", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.POUND));
        assertEquals("5.5 oz", formatter.formatAndConvert(new BigDecimal("5.50"), WeightUnit.OUNCE));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 pg", formatter.formatAndConvert(5.50, WeightUnit.PICOGRAM));
        assertEquals("5 ng", formatter.formatAndConvert(5000.50, WeightUnit.PICOGRAM));
        assertEquals("5 µg", formatter.formatAndConvert(5000000.50, WeightUnit.PICOGRAM));
        assertEquals("5 mg", formatter.formatAndConvert(5000000000.50, WeightUnit.PICOGRAM));
        assertEquals("5 g", formatter.formatAndConvert(5000000000000.50, WeightUnit.PICOGRAM));
        assertEquals("5 Kg", formatter.formatAndConvert(5000000000000000.50, WeightUnit.PICOGRAM));
        assertEquals("5 t", formatter.formatAndConvert(5000000000000000000.50, WeightUnit.PICOGRAM));
        assertEquals("5 Mt", formatter.formatAndConvert(5000000000000000000000000.50,
                WeightUnit.PICOGRAM));

        assertEquals("5,5 ng", formatter.formatAndConvert(5.50, WeightUnit.NANOGRAM));
        assertEquals("5,5 µg", formatter.formatAndConvert(5.50, WeightUnit.MICROGRAM));
        assertEquals("5,5 mg", formatter.formatAndConvert(5.50, WeightUnit.MILLIGRAM));
        assertEquals("5,5 g", formatter.formatAndConvert(5.50, WeightUnit.GRAM));
        assertEquals("5,5 Kg", formatter.formatAndConvert(5.50, WeightUnit.KILOGRAM));
        assertEquals("5,5 t", formatter.formatAndConvert(5.50, WeightUnit.TONNE));
        assertEquals("5,5 Mt", formatter.formatAndConvert(5.50, WeightUnit.MEGATONNE));
        assertEquals("4,99 t", formatter.formatAndConvert(5.50, WeightUnit.US_TON));
        assertEquals("5,59 t", formatter.formatAndConvert(5.50, WeightUnit.UK_TON));
        assertEquals("2,49 Kg", formatter.formatAndConvert(5.50, WeightUnit.POUND));
        assertEquals("155,92 g", formatter.formatAndConvert(5.50, WeightUnit.OUNCE));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.NANOGRAM));
        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.MICROGRAM));
        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.MILLIGRAM));
        assertEquals("0.19 oz", formatter.formatAndConvert(5.50, WeightUnit.GRAM));
        assertEquals("12.13 lb", formatter.formatAndConvert(5.50, WeightUnit.KILOGRAM));
        assertEquals("6.06 ton", formatter.formatAndConvert(5.50, WeightUnit.TONNE));
        assertEquals("6,063,947.08 ton", formatter.formatAndConvert(5.50, WeightUnit.MEGATONNE));
        assertEquals("5.5 ton", formatter.formatAndConvert(5.50, WeightUnit.US_TON));
        assertEquals("6.16 ton", formatter.formatAndConvert(5.50, WeightUnit.UK_TON));
        assertEquals("5.5 lb", formatter.formatAndConvert(5.50, WeightUnit.POUND));
        assertEquals("5.5 oz", formatter.formatAndConvert(5.50, WeightUnit.OUNCE));
    }

    @Test
    void testFormatAndConvertWeight() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 pg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.PICOGRAM)));
        assertEquals("5 ng", formatter.formatAndConvert(new Weight(5000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 µg", formatter.formatAndConvert(new Weight(5000000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 mg", formatter.formatAndConvert(new Weight(5000000000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 g", formatter.formatAndConvert(
                new Weight(5000000000000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 Kg", formatter.formatAndConvert(
                new Weight(5000000000000000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 t", formatter.formatAndConvert(
                new Weight(5000000000000000000.50, WeightUnit.PICOGRAM)));
        assertEquals("5 Mt", formatter.formatAndConvert(
                new Weight(5000000000000000000000000.50, WeightUnit.PICOGRAM)));

        assertEquals("5,5 ng", formatter.formatAndConvert(new Weight(5.50, WeightUnit.NANOGRAM)));
        assertEquals("5,5 µg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MICROGRAM)));
        assertEquals("5,5 mg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MILLIGRAM)));
        assertEquals("5,5 g", formatter.formatAndConvert(new Weight(5.50, WeightUnit.GRAM)));
        assertEquals("5,5 Kg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.KILOGRAM)));
        assertEquals("5,5 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.TONNE)));
        assertEquals("5,5 Mt", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MEGATONNE)));
        assertEquals("4,99 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.US_TON)));
        assertEquals("5,59 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.UK_TON)));
        assertEquals("2,49 Kg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.POUND)));
        assertEquals("155,92 g", formatter.formatAndConvert(new Weight(5.50, WeightUnit.OUNCE)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.NANOGRAM)));
        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MICROGRAM)));
        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MILLIGRAM)));
        assertEquals("0.19 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.GRAM)));
        assertEquals("12.13 lb", formatter.formatAndConvert(new Weight(5.50, WeightUnit.KILOGRAM)));
        assertEquals("6.06 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.TONNE)));
        assertEquals("6,063,947.08 ton", formatter.formatAndConvert(new Weight(5.50,
                WeightUnit.MEGATONNE)));
        assertEquals("5.5 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.US_TON)));
        assertEquals("6.16 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.UK_TON)));
        assertEquals("5.5 lb", formatter.formatAndConvert(new Weight(5.50, WeightUnit.POUND)));
        assertEquals("5.5 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.OUNCE)));
    }

    @Test
    void testFormatAnConvertNumberAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ng", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM, UnitSystem.METRIC));
        assertEquals("5,5 µg", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM, UnitSystem.METRIC));
        assertEquals("5,5 mg", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM, UnitSystem.METRIC));
        assertEquals("5,5 g", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM, UnitSystem.METRIC));
        assertEquals("5,5 Kg", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM, UnitSystem.METRIC));
        assertEquals("5,5 t", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE, UnitSystem.METRIC));
        assertEquals("5,5 Mt", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE, UnitSystem.METRIC));
        assertEquals("4,99 t", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON, UnitSystem.METRIC));
        assertEquals("5,59 t", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON, UnitSystem.METRIC));
        assertEquals("2,49 Kg", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND, UnitSystem.METRIC));
        assertEquals("155,92 g", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE, UnitSystem.METRIC));

        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM, UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM, UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM, UnitSystem.IMPERIAL));
        assertEquals("0,19 oz", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.GRAM, UnitSystem.IMPERIAL));
        assertEquals("12,13 lb", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM, UnitSystem.IMPERIAL));
        assertEquals("6,06 ton", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.TONNE, UnitSystem.IMPERIAL));
        assertEquals("6.063.947,08 ton", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE, UnitSystem.IMPERIAL));
        assertEquals("5,5 ton", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.US_TON, UnitSystem.IMPERIAL));
        assertEquals("6,16 ton", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.UK_TON, UnitSystem.IMPERIAL));
        assertEquals("5,5 lb", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.POUND, UnitSystem.IMPERIAL));
        assertEquals("5,5 oz", formatter.formatAndConvert(new BigDecimal("5.50"),
                WeightUnit.OUNCE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ng", formatter.formatAndConvert(5.50, WeightUnit.NANOGRAM, UnitSystem.METRIC));
        assertEquals("5,5 µg", formatter.formatAndConvert(5.50, WeightUnit.MICROGRAM,
                UnitSystem.METRIC));
        assertEquals("5,5 mg", formatter.formatAndConvert(5.50, WeightUnit.MILLIGRAM,
                UnitSystem.METRIC));
        assertEquals("5,5 g", formatter.formatAndConvert(5.50, WeightUnit.GRAM, UnitSystem.METRIC));
        assertEquals("5,5 Kg", formatter.formatAndConvert(5.50, WeightUnit.KILOGRAM, UnitSystem.METRIC));
        assertEquals("5,5 t", formatter.formatAndConvert(5.50, WeightUnit.TONNE, UnitSystem.METRIC));
        assertEquals("5,5 Mt", formatter.formatAndConvert(5.50, WeightUnit.MEGATONNE,
                UnitSystem.METRIC));
        assertEquals("4,99 t", formatter.formatAndConvert(5.50, WeightUnit.US_TON, UnitSystem.METRIC));
        assertEquals("5,59 t", formatter.formatAndConvert(5.50, WeightUnit.UK_TON, UnitSystem.METRIC));
        assertEquals("2,49 Kg", formatter.formatAndConvert(5.50, WeightUnit.POUND, UnitSystem.METRIC));
        assertEquals("155,92 g", formatter.formatAndConvert(5.50, WeightUnit.OUNCE, UnitSystem.METRIC));


        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.NANOGRAM, UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.MICROGRAM,
                UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(5.50, WeightUnit.MILLIGRAM,
                UnitSystem.IMPERIAL));
        assertEquals("0,19 oz", formatter.formatAndConvert(5.50, WeightUnit.GRAM, UnitSystem.IMPERIAL));
        assertEquals("12,13 lb", formatter.formatAndConvert(5.50, WeightUnit.KILOGRAM,
                UnitSystem.IMPERIAL));
        assertEquals("6,06 ton", formatter.formatAndConvert(5.50, WeightUnit.TONNE,
                UnitSystem.IMPERIAL));
        assertEquals("6.063.947,08 ton", formatter.formatAndConvert(5.50, WeightUnit.MEGATONNE,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 ton", formatter.formatAndConvert(5.50, WeightUnit.US_TON,
                UnitSystem.IMPERIAL));
        assertEquals("6,16 ton", formatter.formatAndConvert(5.50, WeightUnit.UK_TON,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 lb", formatter.formatAndConvert(5.50, WeightUnit.POUND, UnitSystem.IMPERIAL));
        assertEquals("5,5 oz", formatter.formatAndConvert(5.50, WeightUnit.OUNCE, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertWeightAndUnitSystem() {
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ng", formatter.formatAndConvert(new Weight(5.50, WeightUnit.NANOGRAM),
                UnitSystem.METRIC));
        assertEquals("5,5 µg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MICROGRAM),
                UnitSystem.METRIC));
        assertEquals("5,5 mg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MILLIGRAM),
                UnitSystem.METRIC));
        assertEquals("5,5 g", formatter.formatAndConvert(new Weight(5.50, WeightUnit.GRAM),
                UnitSystem.METRIC));
        assertEquals("5,5 Kg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.KILOGRAM),
                UnitSystem.METRIC));
        assertEquals("5,5 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.TONNE),
                UnitSystem.METRIC));
        assertEquals("5,5 Mt", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MEGATONNE),
                UnitSystem.METRIC));
        assertEquals("4,99 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.US_TON),
                UnitSystem.METRIC));
        assertEquals("5,59 t", formatter.formatAndConvert(new Weight(5.50, WeightUnit.UK_TON),
                UnitSystem.METRIC));
        assertEquals("2,49 Kg", formatter.formatAndConvert(new Weight(5.50, WeightUnit.POUND),
                UnitSystem.METRIC));
        assertEquals("155,92 g", formatter.formatAndConvert(new Weight(5.50, WeightUnit.OUNCE),
                UnitSystem.METRIC));

        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.NANOGRAM),
                UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MICROGRAM),
                UnitSystem.IMPERIAL));
        assertEquals("0 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.MILLIGRAM),
                UnitSystem.IMPERIAL));
        assertEquals("0,19 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.GRAM),
                UnitSystem.IMPERIAL));
        assertEquals("12,13 lb", formatter.formatAndConvert(new Weight(5.50, WeightUnit.KILOGRAM),
                UnitSystem.IMPERIAL));
        assertEquals("6,06 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.TONNE),
                UnitSystem.IMPERIAL));
        assertEquals("6.063.947,08 ton", formatter.formatAndConvert(new Weight(5.50,
                WeightUnit.MEGATONNE), UnitSystem.IMPERIAL));
        assertEquals("5,5 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.US_TON),
                UnitSystem.IMPERIAL));
        assertEquals("6,16 ton", formatter.formatAndConvert(new Weight(5.50, WeightUnit.UK_TON),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 lb", formatter.formatAndConvert(new Weight(5.50, WeightUnit.POUND),
                UnitSystem.IMPERIAL));
        assertEquals("5,5 oz", formatter.formatAndConvert(new Weight(5.50, WeightUnit.OUNCE),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ng", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM));
        assertEquals("5,5 µg", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM));
        assertEquals("5,5 mg", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM));
        assertEquals("5,5 g", formatter.formatAndConvertMetric(new BigDecimal("5.50"), WeightUnit.GRAM));
        assertEquals("5,5 Kg", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM));
        assertEquals("5,5 t", formatter.formatAndConvertMetric(new BigDecimal("5.50"), WeightUnit.TONNE));
        assertEquals("5,5 Mt", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE));
        assertEquals("4,99 t", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.US_TON));
        assertEquals("5,59 t", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.UK_TON));
        assertEquals("2,49 Kg", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.POUND));
        assertEquals("155,92 g", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                WeightUnit.OUNCE));
    }

    @Test
    void testFormatAndConvertImperial() {
        var l = new Locale("es", "ES");

        var formatter = new WeightFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0 oz", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.NANOGRAM));
        assertEquals("0 oz", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MICROGRAM));
        assertEquals("0 oz", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MILLIGRAM));
        assertEquals("0,19 oz", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.GRAM));
        assertEquals("12,13 lb", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.KILOGRAM));
        assertEquals("6,06 ton", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.TONNE));
        assertEquals("6.063.947,08 ton", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.MEGATONNE));
        assertEquals("5,5 ton", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.US_TON));
        assertEquals("6,16 ton", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.UK_TON));
        assertEquals("5,5 lb", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.POUND));
        assertEquals("5,5 oz", formatter.formatAndConvertImperial(new BigDecimal("5.50"),
                WeightUnit.OUNCE));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = WeightFormatter.getAvailableLocales();
        assertArrayEquals(NumberFormat.getAvailableLocales(), locales);
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new WeightFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new WeightFormatter();

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
        var formatter = new WeightFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new WeightFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new WeightFormatter();

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
    void testIsValidMeasurement() {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
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
    void testIsMetricUnit() {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
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
    void testIsImperialUnit() {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
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
    void testGetUnitSystemFromSource() {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ng";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 µg";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 mg";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 g";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Kg";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 t";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Mt";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ton";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 lb";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 oz";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
        var w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.PICOGRAM, w.getUnit());

        text = "5,5 ng";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.NANOGRAM, w.getUnit());

        text = "5,5 µg";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.MICROGRAM, w.getUnit());

        text = "5,5 mg";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.MILLIGRAM, w.getUnit());

        text = "5,5 g";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.GRAM, w.getUnit());

        text = "5,5 Kg";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.KILOGRAM, w.getUnit());

        text = "5,5 t";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.TONNE, w.getUnit());

        text = "5,5 Mt";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.MEGATONNE, w.getUnit());

        text = "5,5 ton";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.US_TON, w.getUnit());

        text = "5,5 lb";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.POUND, w.getUnit());

        text = "5,5 oz";
        w = formatter.parse(text);
        assertEquals(5.5, w.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.OUNCE, w.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new WeightFormatter(new Locale("es", "ES"));

        var text = "5,5 pg";
        assertEquals(WeightUnit.PICOGRAM, formatter.findUnit(text));

        text = "5,5 ng";
        assertEquals(WeightUnit.NANOGRAM, formatter.findUnit(text));

        text = "5,5 µg";
        assertEquals(WeightUnit.MICROGRAM, formatter.findUnit(text));

        text = "5,5 mg";
        assertEquals(WeightUnit.MILLIGRAM, formatter.findUnit(text));

        text = "5,5 g";
        assertEquals(WeightUnit.GRAM, formatter.findUnit(text));

        text = "5,5 Kg";
        assertEquals(WeightUnit.KILOGRAM, formatter.findUnit(text));

        text = "5,5 t";
        assertEquals(WeightUnit.TONNE, formatter.findUnit(text));

        text = "5,5 Mt";
        assertEquals(WeightUnit.MEGATONNE, formatter.findUnit(text));

        text = "5,5 ton";
        assertEquals(WeightUnit.US_TON, formatter.findUnit(text));

        text = "5,5 lb";
        assertEquals(WeightUnit.POUND, formatter.findUnit(text));

        text = "5,5 oz";
        assertEquals(WeightUnit.OUNCE, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new WeightFormatter();

        assertEquals(WeightFormatter.PICOGRAM, formatter.getUnitSymbol(WeightUnit.PICOGRAM));
        assertEquals(WeightFormatter.NANOGRAM, formatter.getUnitSymbol(WeightUnit.NANOGRAM));
        assertEquals(WeightFormatter.MICROGRAM, formatter.getUnitSymbol(WeightUnit.MICROGRAM));
        assertEquals(WeightFormatter.MILLIGRAM, formatter.getUnitSymbol(WeightUnit.MILLIGRAM));
        assertEquals(WeightFormatter.GRAM, formatter.getUnitSymbol(WeightUnit.GRAM));
        assertEquals(WeightFormatter.KILOGRAM, formatter.getUnitSymbol(WeightUnit.KILOGRAM));
        assertEquals(WeightFormatter.TONNE, formatter.getUnitSymbol(WeightUnit.TONNE));
        assertEquals(WeightFormatter.MEGATONNE, formatter.getUnitSymbol(WeightUnit.MEGATONNE));
        assertEquals(WeightFormatter.US_UK_TON, formatter.getUnitSymbol(WeightUnit.US_TON));
        assertEquals(WeightFormatter.US_UK_TON, formatter.getUnitSymbol(WeightUnit.UK_TON));
        assertEquals(WeightFormatter.POUND, formatter.getUnitSymbol(WeightUnit.POUND));
        assertEquals(WeightFormatter.OUNCE, formatter.getUnitSymbol(WeightUnit.OUNCE));
    }
}
