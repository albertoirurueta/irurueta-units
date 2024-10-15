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

class MagneticFluxDensityFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new MagneticFluxDensityFormatter();

        // check
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final var locale = new Locale("es", "ES");
        formatter = new MagneticFluxDensityFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new MagneticFluxDensityFormatter((Locale) null));

        // test copy constructor
        formatter = new MagneticFluxDensityFormatter(locale);
        final var formatter2 = new MagneticFluxDensityFormatter(formatter);

        // check
        assertEquals(locale, formatter2.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter2.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter2.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter2.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter2.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter2.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter2.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter2.getValueAndUnitFormatPattern());

        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new MagneticFluxDensityFormatter(
                (MagneticFluxDensityFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new MagneticFluxDensityFormatter();
        final MagneticFluxDensityFormatter formatter2 = (MagneticFluxDensityFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, MagneticFluxDensityUnit.TESLA, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (MagneticFluxDensityFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final var formatter2 = new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final var formatter3 = new MagneticFluxDensityFormatter(Locale.FRENCH);

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
        final var formatter1 = new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final var formatter2 = new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final var formatter3 = new MagneticFluxDensityFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        assertEquals("5,5 nT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 µT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 mT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MILLITESLA));
        assertEquals("5,5 T", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.TESLA));
        assertEquals("5,5 KT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.KILOTESLA));
        assertEquals("5,5 MT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MEGATESLA));
        assertEquals("5,5 GT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.GIGATESLA));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 nT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.NANOTESLA,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MICROTESLA,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MILLITESLA,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 T", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.TESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.KILOTESLA,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.MEGATESLA,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GT", formatter.format(new BigDecimal(value), MagneticFluxDensityUnit.GIGATESLA,
                buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        assertEquals("5,5 nT", formatter.format(value, MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 µT", formatter.format(value, MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 mT", formatter.format(value, MagneticFluxDensityUnit.MILLITESLA));
        assertEquals("5,5 T", formatter.format(value, MagneticFluxDensityUnit.TESLA));
        assertEquals("5,5 KT", formatter.format(value, MagneticFluxDensityUnit.KILOTESLA));
        assertEquals("5,5 MT", formatter.format(value, MagneticFluxDensityUnit.MEGATESLA));
        assertEquals("5,5 GT", formatter.format(value, MagneticFluxDensityUnit.GIGATESLA));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 nT", formatter.format(value, MagneticFluxDensityUnit.NANOTESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µT", formatter.format(value, MagneticFluxDensityUnit.MICROTESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mT", formatter.format(value, MagneticFluxDensityUnit.MILLITESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 T", formatter.format(value, MagneticFluxDensityUnit.TESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KT", formatter.format(value, MagneticFluxDensityUnit.KILOTESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MT", formatter.format(value, MagneticFluxDensityUnit.MEGATESLA, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GT", formatter.format(value, MagneticFluxDensityUnit.GIGATESLA, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatMagneticFluxDensity() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        assertEquals("5,5 nT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 µT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MICROTESLA)));
        assertEquals("5,5 mT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MILLITESLA)));
        assertEquals("5,5 T", formatter.format(new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA)));
        assertEquals("5,5 KT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.KILOTESLA)));
        assertEquals("5,5 MT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MEGATESLA)));
        assertEquals("5,5 GT", formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.GIGATESLA)));
    }

    @Test
    void testFormatMagneticFluxDensityAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 nT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.NANOTESLA), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 µT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.MICROTESLA), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.MILLITESLA), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 T", formatter.format(new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 KT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.KILOTESLA), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 MT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.MEGATESLA), buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 GT", formatter.format(new MagneticFluxDensity(value,
                        MagneticFluxDensityUnit.GIGATESLA), buffer, new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 T", formatter.formatAndConvert(new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 KT", formatter.formatAndConvert(new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 MT", formatter.formatAndConvert(new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 GT", formatter.formatAndConvert(new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));

        assertEquals("5,5 nT", formatter.formatAndConvert(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA));
    }

    @Test
    void testFormatAndConvertDouble() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(5.50, MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvert(5500.00, MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvert(5500000.00,
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 T", formatter.formatAndConvert(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 KT", formatter.formatAndConvert(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 MT", formatter.formatAndConvert(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 GT", formatter.formatAndConvert(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA));

        assertEquals("5,5 nT", formatter.formatAndConvert(5.5e-3, MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvert(5.50, MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvert(5500.00, MagneticFluxDensityUnit.MICROTESLA));
    }

    @Test
    void testFormatAndConvertMagneticFluxDensity() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(new MagneticFluxDensity(5.50,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 µT", formatter.formatAndConvert(new MagneticFluxDensity(5500.00,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 mT", formatter.formatAndConvert(new MagneticFluxDensity(5500000.00,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 T", formatter.formatAndConvert(new MagneticFluxDensity(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 KT", formatter.formatAndConvert(new MagneticFluxDensity(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 MT", formatter.formatAndConvert(new MagneticFluxDensity(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)));
        assertEquals("5,5 GT", formatter.formatAndConvert(new MagneticFluxDensity(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)));

        assertEquals("5,5 nT", formatter.formatAndConvert(new MagneticFluxDensity(5.5e-3,
                MagneticFluxDensityUnit.MICROTESLA)));
        assertEquals("5,5 µT", formatter.formatAndConvert(new MagneticFluxDensity(5.50,
                MagneticFluxDensityUnit.MICROTESLA)));
        assertEquals("5,5 mT", formatter.formatAndConvert(new MagneticFluxDensity(5500.00,
                MagneticFluxDensityUnit.MICROTESLA)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 T", formatter.formatAndConvert(new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 KT", formatter.formatAndConvert(new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 MT", formatter.formatAndConvert(new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 GT", formatter.formatAndConvert(new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));

        assertEquals("5,5 nT", formatter.formatAndConvert(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(5.50, MagneticFluxDensityUnit.NANOTESLA,
                UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(5500.00, MagneticFluxDensityUnit.NANOTESLA,
                UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(5500000.00, MagneticFluxDensityUnit.NANOTESLA,
                UnitSystem.METRIC));
        assertEquals("5,5 T", formatter.formatAndConvert(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 KT", formatter.formatAndConvert(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 MT", formatter.formatAndConvert(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));
        assertEquals("5,5 GT", formatter.formatAndConvert(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC));

        assertEquals("5,5 nT", formatter.formatAndConvert(5.5e-3, MagneticFluxDensityUnit.MICROTESLA,
                UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(5.50, MagneticFluxDensityUnit.MICROTESLA,
                UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(5500.00, MagneticFluxDensityUnit.MICROTESLA,
                UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertMagneticFluxDensityAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvert(
                new MagneticFluxDensity(5.50, MagneticFluxDensityUnit.NANOTESLA), UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00, MagneticFluxDensityUnit.NANOTESLA), UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500000.00, MagneticFluxDensityUnit.NANOTESLA), UnitSystem.METRIC));
        assertEquals("5,5 T", formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000.00, MagneticFluxDensityUnit.NANOTESLA), UnitSystem.METRIC));
        assertEquals("5,5 KT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000.00, MagneticFluxDensityUnit.NANOTESLA), UnitSystem.METRIC));
        assertEquals("5,5 MT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000.00, MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC));
        assertEquals("5,5 GT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000000.00, MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC));

        assertEquals("5,5 nT", formatter.formatAndConvert(
                new MagneticFluxDensity(5.5e-3, MagneticFluxDensityUnit.MICROTESLA), UnitSystem.METRIC));
        assertEquals("5,5 µT", formatter.formatAndConvert(
                new MagneticFluxDensity(5.50, MagneticFluxDensityUnit.MICROTESLA), UnitSystem.METRIC));
        assertEquals("5,5 mT", formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00, MagneticFluxDensityUnit.MICROTESLA), UnitSystem.METRIC));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 nT", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 T", formatter.formatAndConvertMetric(new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 KT", formatter.formatAndConvertMetric(new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 MT", formatter.formatAndConvertMetric(new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));
        assertEquals("5,5 GT", formatter.formatAndConvertMetric(new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA));

        assertEquals("5,5 nT", formatter.formatAndConvertMetric(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 µT", formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA));
        assertEquals("5,5 mT", formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = MagneticFluxDensityFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new MagneticFluxDensityFormatter();

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
        var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new MagneticFluxDensityFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertTrue(formatter.isValidUnit("nT"));
        assertTrue(formatter.isValidUnit("nT "));

        assertTrue(formatter.isValidUnit("µT"));
        assertTrue(formatter.isValidUnit("µT "));

        assertTrue(formatter.isValidUnit("mT"));
        assertTrue(formatter.isValidUnit("mT "));

        assertTrue(formatter.isValidUnit("T"));
        assertTrue(formatter.isValidUnit("T "));

        assertTrue(formatter.isValidUnit("KT"));
        assertTrue(formatter.isValidUnit("KT "));

        assertTrue(formatter.isValidUnit("MT"));
        assertTrue(formatter.isValidUnit("MT "));

        assertTrue(formatter.isValidUnit("GT"));
        assertTrue(formatter.isValidUnit("GT "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    void testIsValidMeasurement() {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 µT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 T";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 KT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 MT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 GT";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 µT";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 mT";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 T";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 KT";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 MT";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 GT";
        assertTrue(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 µT";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 mT";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 T";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 KT";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 MT";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 GT";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 µT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 mT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 T";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 KT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 MT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 GT";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        var b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.NANOTESLA, b.getUnit());

        text = "5,5 µT";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MICROTESLA, b.getUnit());

        text = "5,5 mT";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MILLITESLA, b.getUnit());

        text = "5,5 T";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        text = "5,5 KT";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b.getUnit());

        text = "5,5 MT";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MEGATESLA, b.getUnit());

        text = "5,5 GT";
        b = formatter.parse(text);
        assertEquals(5.5, b.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.GIGATESLA, b.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new MagneticFluxDensityFormatter(new Locale("es", "ES"));

        var text = "5,5 nT";
        assertEquals(MagneticFluxDensityUnit.NANOTESLA, formatter.findUnit(text));

        text = "5,5 µT";
        assertEquals(MagneticFluxDensityUnit.MICROTESLA, formatter.findUnit(text));

        text = "5,5 mT";
        assertEquals(MagneticFluxDensityUnit.MILLITESLA, formatter.findUnit(text));

        text = "5,5 T";
        assertEquals(MagneticFluxDensityUnit.TESLA, formatter.findUnit(text));

        text = "5,5 KT";
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, formatter.findUnit(text));

        text = "5,5 MT";
        assertEquals(MagneticFluxDensityUnit.MEGATESLA, formatter.findUnit(text));

        text = "5,5 GT";
        assertEquals(MagneticFluxDensityUnit.GIGATESLA, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new MagneticFluxDensityFormatter();

        assertEquals(MagneticFluxDensityFormatter.NANOTESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.NANOTESLA));
        assertEquals(MagneticFluxDensityFormatter.MICROTESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.MICROTESLA));
        assertEquals(MagneticFluxDensityFormatter.MILLITESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.MILLITESLA));
        assertEquals(MagneticFluxDensityFormatter.TESLA, formatter.getUnitSymbol(MagneticFluxDensityUnit.TESLA));
        assertEquals(MagneticFluxDensityFormatter.KILOTESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.KILOTESLA));
        assertEquals(MagneticFluxDensityFormatter.MEGATESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.MEGATESLA));
        assertEquals(MagneticFluxDensityFormatter.GIGATESLA,
                formatter.getUnitSymbol(MagneticFluxDensityUnit.GIGATESLA));
    }
}
