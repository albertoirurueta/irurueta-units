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

public class MagneticFluxDensityFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        MagneticFluxDensityFormatter formatter = new MagneticFluxDensityFormatter();

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
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new MagneticFluxDensityFormatter(locale);

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
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);
        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);


        // force IllegalArgumentException
        formatter = null;
        try {
            formatter = new MagneticFluxDensityFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new MagneticFluxDensityFormatter(locale);
        final MagneticFluxDensityFormatter formatter2 =
                new MagneticFluxDensityFormatter(formatter);

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
        assertEquals(formatter2.getUnitSystem(), UnitSystem.METRIC);
        assertEquals(formatter2.isGroupingUsed(),
                NumberFormat.getInstance(locale).isGroupingUsed());
        assertEquals(formatter2.isParseIntegerOnly(),
                NumberFormat.getInstance(locale).isParseIntegerOnly());
        assertEquals(formatter2.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        formatter = null;
        try {
            // noinspection ConstantConditions
            formatter = new MagneticFluxDensityFormatter(
                    (MagneticFluxDensityFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final MagneticFluxDensityFormatter formatter1 =
                new MagneticFluxDensityFormatter();
        final MagneticFluxDensityFormatter formatter2 =
                (MagneticFluxDensityFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, MagneticFluxDensityUnit.TESLA,
                new StringBuffer(), new FieldPosition(0)));
        final MagneticFluxDensityFormatter formatter3 =
                (MagneticFluxDensityFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final MagneticFluxDensityFormatter formatter1 =
                new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final MagneticFluxDensityFormatter formatter2 =
                new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final MagneticFluxDensityFormatter formatter3 =
                new MagneticFluxDensityFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final MagneticFluxDensityFormatter formatter1 =
                new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final MagneticFluxDensityFormatter formatter2 =
                new MagneticFluxDensityFormatter(Locale.ENGLISH);
        final MagneticFluxDensityFormatter formatter3 =
                new MagneticFluxDensityFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 nT");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 µT");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MILLITESLA), "5,5 mT");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.TESLA), "5,5 T");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.KILOTESLA), "5,5 KT");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MEGATESLA), "5,5 MT");
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.GIGATESLA), "5,5 GT");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.NANOTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 nT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MICROTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 µT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MILLITESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 mT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.TESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 T");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.KILOTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 KT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.MEGATESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 MT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                MagneticFluxDensityUnit.GIGATESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 GT");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 nT");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MICROTESLA), "5,5 µT");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MILLITESLA), "5,5 mT");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.TESLA), "5,5 T");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.KILOTESLA), "5,5 KT");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MEGATESLA), "5,5 MT");
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.GIGATESLA), "5,5 GT");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.NANOTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 nT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MICROTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 µT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MILLITESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 mT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.TESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 T");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.KILOTESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 KT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.MEGATESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 MT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value,
                MagneticFluxDensityUnit.GIGATESLA, buffer,
                new FieldPosition(0)).toString(), "5,5 GT");
    }

    @Test
    public void testFormatMagneticFluxDensity() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 nT");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MICROTESLA)), "5,5 µT");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MILLITESLA)), "5,5 mT");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA)), "5,5 T");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.KILOTESLA)), "5,5 KT");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MEGATESLA)), "5,5 MT");
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.GIGATESLA)), "5,5 GT");
    }
    
    @Test
    public void testFormatMagneticFluxDensityAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.NANOTESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 nT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MICROTESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 µT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MILLITESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 mT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 T");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.KILOTESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 KT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.MEGATESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 MT");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.GIGATESLA), buffer,
                new FieldPosition(0)).toString(), "5,5 GT");
    }

    @Test
    public void testFormatAndConvertNumber() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 mT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 T");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 KT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 MT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 GT");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 mT");
    }

    @Test
    public void testFormatAndConvertDouble() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvert(5500.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvert(5500000.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 mT");
        assertEquals(formatter.formatAndConvert(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 T");
        assertEquals(formatter.formatAndConvert(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 KT");
        assertEquals(formatter.formatAndConvert(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 MT");
        assertEquals(formatter.formatAndConvert(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA), "5,5 GT");

        assertEquals(formatter.formatAndConvert(5.5e-3,
                MagneticFluxDensityUnit.MICROTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvert(5.50,
                MagneticFluxDensityUnit.MICROTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvert(5500.00,
                MagneticFluxDensityUnit.MICROTESLA), "5,5 mT");
    }

    @Test
    public void testFormatAndConvertMagneticFluxDensity() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new MagneticFluxDensity(
                5.50, MagneticFluxDensityUnit.NANOTESLA)), "5,5 nT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 µT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 mT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 T");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 KT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 MT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA)), "5,5 GT");

        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5.5e-3,
                MagneticFluxDensityUnit.MICROTESLA)), "5,5 nT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5.50,
                MagneticFluxDensityUnit.MICROTESLA)), "5,5 µT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00,
                MagneticFluxDensityUnit.MICROTESLA)), "5,5 mT");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 nT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 µT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 mT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 T");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 KT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 MT");
        assertEquals(formatter.formatAndConvert(
                new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 GT");

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 nT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 µT");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 mT");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 nT");
        assertEquals(formatter.formatAndConvert(5500.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 µT");
        assertEquals(formatter.formatAndConvert(5500000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 mT");
        assertEquals(formatter.formatAndConvert(5500000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 T");
        assertEquals(formatter.formatAndConvert(5500000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 KT");
        assertEquals(formatter.formatAndConvert(5500000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 MT");
        assertEquals(formatter.formatAndConvert(5500000000000000000.00,
                MagneticFluxDensityUnit.NANOTESLA, UnitSystem.METRIC),
                "5,5 GT");

        assertEquals(formatter.formatAndConvert(5.5e-3,
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 nT");
        assertEquals(formatter.formatAndConvert(5.50,
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 µT");
        assertEquals(formatter.formatAndConvert(5500.00,
                MagneticFluxDensityUnit.MICROTESLA, UnitSystem.METRIC),
                "5,5 mT");
    }

    @Test
    public void testFormatAndConvertMagneticFluxDensityAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new MagneticFluxDensity(
                5.50, MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 nT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 µT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 mT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 T");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 KT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 MT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500000000000000000.00,
                        MagneticFluxDensityUnit.NANOTESLA),
                UnitSystem.METRIC), "5,5 GT");

        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5.5e-3,
                        MagneticFluxDensityUnit.MICROTESLA),
                UnitSystem.METRIC), "5,5 nT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5.50,
                        MagneticFluxDensityUnit.MICROTESLA),
                UnitSystem.METRIC), "5,5 µT");
        assertEquals(formatter.formatAndConvert(
                new MagneticFluxDensity(5500.00,
                        MagneticFluxDensityUnit.MICROTESLA),
                UnitSystem.METRIC), "5,5 mT");
    }

    @Test
    public void testFormatAndConvertMetric() {
        final Locale l = new Locale("es", "ES");

        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 mT");
        assertEquals(formatter.formatAndConvertMetric(
                new BigDecimal("5500000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 T");
        assertEquals(formatter.formatAndConvertMetric(
                new BigDecimal("5500000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 KT");
        assertEquals(formatter.formatAndConvertMetric(
                new BigDecimal("5500000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 MT");
        assertEquals(formatter.formatAndConvertMetric(
                new BigDecimal("5500000000000000000.00"),
                MagneticFluxDensityUnit.NANOTESLA), "5,5 GT");

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.5e-3"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 nT");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 µT");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                MagneticFluxDensityUnit.MICROTESLA), "5,5 mT");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = MagneticFluxDensityFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

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
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

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
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

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
        MagneticFluxDensityFormatter formatter = new MagneticFluxDensityFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new MagneticFluxDensityFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);
    }

    @Test
    public void testIsValidUnit() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

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
    public void testIsValidMeasurement() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
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
    public void testIsMetricUnit() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
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
    public void testIsImperialUnit() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
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
    public void testGetUnitSystemFromSource() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 µT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 mT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 T";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 KT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 MT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 GT";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 s";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
        MagneticFluxDensity b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.NANOTESLA);

        text = "5,5 µT";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.MICROTESLA);

        text = "5,5 mT";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.MILLITESLA);

        text = "5,5 T";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.TESLA);

        text = "5,5 KT";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.KILOTESLA);

        text = "5,5 MT";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.MEGATESLA);

        text = "5,5 GT";
        b = formatter.parse(text);
        assertEquals(b.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.GIGATESLA);

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
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter(
                        new Locale("es", "ES"));

        String text = "5,5 nT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.NANOTESLA);

        text = "5,5 µT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.MICROTESLA);

        text = "5,5 mT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.MILLITESLA);

        text = "5,5 T";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.TESLA);

        text = "5,5 KT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.KILOTESLA);

        text = "5,5 MT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.MEGATESLA);

        text = "5,5 GT";
        assertEquals(formatter.findUnit(text), MagneticFluxDensityUnit.GIGATESLA);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final MagneticFluxDensityFormatter formatter =
                new MagneticFluxDensityFormatter();

        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityFormatter.NANOTESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityFormatter.MICROTESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityFormatter.MILLITESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityFormatter.TESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityFormatter.KILOTESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityFormatter.MEGATESLA);
        assertEquals(formatter.getUnitSymbol(MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityFormatter.GIGATESLA);
    }

}
