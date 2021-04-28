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

public class FrequencyFormatterTest {

    @Test
    public void testConstructor() {
        // test empty constructor
        FrequencyFormatter formatter = new FrequencyFormatter();

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
        formatter = new FrequencyFormatter(locale);

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
            formatter = new FrequencyFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new FrequencyFormatter(locale);
        final FrequencyFormatter formatter2 = new FrequencyFormatter(formatter);

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
            formatter = new FrequencyFormatter((FrequencyFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final FrequencyFormatter formatter1 = new FrequencyFormatter();
        final FrequencyFormatter formatter2 = (FrequencyFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, FrequencyUnit.HERTZ,
                new StringBuffer(), new FieldPosition(0)));
        final FrequencyFormatter formatter3 = (FrequencyFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final FrequencyFormatter formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        final FrequencyFormatter formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        final FrequencyFormatter formatter3 = new FrequencyFormatter(Locale.FRENCH);

        // check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final FrequencyFormatter formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        final FrequencyFormatter formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        final FrequencyFormatter formatter3 = new FrequencyFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.KILOHERTZ), "5,5 KHz");
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.MEGAHERTZ), "5,5 MHz");
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.GIGAHERTZ), "5,5 GHz");
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.TERAHERTZ), "5,5 THz");
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.HERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 Hz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.KILOHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 KHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.MEGAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 MHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.GIGAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 GHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new BigDecimal(value),
                FrequencyUnit.TERAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 THz");
    }

    @Test
    public void testFormatDouble() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        assertEquals(formatter.format(value, FrequencyUnit.HERTZ),
                "5,5 Hz");
        assertEquals(formatter.format(value, FrequencyUnit.KILOHERTZ),
                "5,5 KHz");
        assertEquals(formatter.format(value, FrequencyUnit.MEGAHERTZ),
                "5,5 MHz");
        assertEquals(formatter.format(value, FrequencyUnit.GIGAHERTZ),
                "5,5 GHz");
        assertEquals(formatter.format(value, FrequencyUnit.TERAHERTZ),
                "5,5 THz");
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(value, FrequencyUnit.HERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 Hz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, FrequencyUnit.KILOHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 KHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, FrequencyUnit.MEGAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 MHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, FrequencyUnit.GIGAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 GHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(value, FrequencyUnit.TERAHERTZ, buffer,
                new FieldPosition(0)).toString(), "5,5 THz");
    }

    @Test
    public void testFormatFrequency() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.HERTZ)),
                "5,5 Hz");
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.KILOHERTZ)),
                "5,5 KHz");
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.MEGAHERTZ)),
                "5,5 MHz");
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.GIGAHERTZ)),
                "5,5 GHz");
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.TERAHERTZ)),
                "5,5 THz");
    }

    @Test
    public void testFormatFrequencyAndStringBuffer() {
        final double value = 5.50;
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.HERTZ),
                buffer, new FieldPosition(0)).toString(), "5,5 Hz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.KILOHERTZ),
                buffer, new FieldPosition(0)).toString(), "5,5 KHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.MEGAHERTZ),
                buffer, new FieldPosition(0)).toString(), "5,5 MHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.GIGAHERTZ),
                buffer, new FieldPosition(0)).toString(), "5,5 GHz");

        buffer = new StringBuffer();
        assertEquals(formatter.format(new Frequency(value, FrequencyUnit.TERAHERTZ),
                buffer, new FieldPosition(0)).toString(), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertNumber() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.HERTZ), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertDouble() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(5500.00,
                FrequencyUnit.HERTZ), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(5500000.00,
                FrequencyUnit.HERTZ), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(5500.00,
                FrequencyUnit.MEGAHERTZ), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(5500000.00,
                FrequencyUnit.MEGAHERTZ), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertFrequency() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Frequency(5.50,
                FrequencyUnit.HERTZ)), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.HERTZ)), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.HERTZ)), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.MEGAHERTZ)), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.MEGAHERTZ)), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal("5.50"),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(5.50,
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(5500.00,
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(5500000.00,
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(5500.00,
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(5500000.00,
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertFrequencyAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new Frequency(5.50,
                FrequencyUnit.HERTZ), UnitSystem.METRIC), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.HERTZ), UnitSystem.METRIC), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.HERTZ), UnitSystem.METRIC), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500.00,
                FrequencyUnit.MEGAHERTZ), UnitSystem.METRIC), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new Frequency(5500000.00,
                FrequencyUnit.MEGAHERTZ), UnitSystem.METRIC), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertMetric() {
        final Locale l = new Locale("es", "ES");

        final FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5.50"),
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                FrequencyUnit.HERTZ), "5,5 KHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                FrequencyUnit.HERTZ), "5,5 MHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500.00"),
                FrequencyUnit.MEGAHERTZ), "5,5 GHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal("5500000.00"),
                FrequencyUnit.MEGAHERTZ), "5,5 THz");
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = FrequencyFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

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
        final FrequencyFormatter formatter = new FrequencyFormatter();

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
        final FrequencyFormatter formatter = new FrequencyFormatter();

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
        FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);

        formatter = new FrequencyFormatter(
                new Locale("en", "US"));
        assertEquals(formatter.getUnitSystem(), UnitSystem.METRIC);
    }

    @Test
    public void testIsValidUnit() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertTrue(formatter.isValidUnit("Hz"));
        assertTrue(formatter.isValidUnit("Hz "));

        assertTrue(formatter.isValidUnit("KHz"));
        assertTrue(formatter.isValidUnit("KHz "));

        assertTrue(formatter.isValidUnit("MHz"));
        assertTrue(formatter.isValidUnit("MHz "));

        assertTrue(formatter.isValidUnit("GHz"));
        assertTrue(formatter.isValidUnit("GHz "));

        assertTrue(formatter.isValidUnit("THz"));
        assertTrue(formatter.isValidUnit("THz "));

        assertFalse(formatter.isValidUnit("w"));
    }

    @Test
    public void testIsValidMeasurement() {
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 KHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 MHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 GHz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 THz";
        assertTrue(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 KHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 MHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 GHz";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 THz";
        assertTrue(formatter.isMetricUnit(text));
    }

    @Test
    public void testIsImperialUnit() {
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 KHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 MHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 GHz";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 THz";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 KHz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 MHz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 GHz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 THz";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);

        text = "5,5 s";
        assertEquals(formatter.getUnitSystem(text), UnitSystem.METRIC);
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        Frequency f = formatter.parse(text);
        assertEquals(f.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        text = "5,5 KHz";
        f = formatter.parse(text);
        assertEquals(f.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(f.getUnit(), FrequencyUnit.KILOHERTZ);

        text = "5,5 MHz";
        f = formatter.parse(text);
        assertEquals(f.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(f.getUnit(), FrequencyUnit.MEGAHERTZ);

        text = "5,5 GHz";
        f = formatter.parse(text);
        assertEquals(f.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(f.getUnit(), FrequencyUnit.GIGAHERTZ);

        text = "5,5 THz";
        f = formatter.parse(text);
        assertEquals(f.getValue().doubleValue(), 5.5, 0.0);
        assertEquals(f.getUnit(), FrequencyUnit.TERAHERTZ);

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
        final FrequencyFormatter formatter = new FrequencyFormatter(
                new Locale("es", "ES"));

        String text = "5,5 Hz";
        assertEquals(formatter.findUnit(text), FrequencyUnit.HERTZ);

        text = "5,5 KHz";
        assertEquals(formatter.findUnit(text), FrequencyUnit.KILOHERTZ);

        text = "5,5 MHz";
        assertEquals(formatter.findUnit(text), FrequencyUnit.MEGAHERTZ);

        text = "5,5 GHz";
        assertEquals(formatter.findUnit(text), FrequencyUnit.GIGAHERTZ);

        text = "5,5 THz";
        assertEquals(formatter.findUnit(text), FrequencyUnit.TERAHERTZ);

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getUnitSymbol(FrequencyUnit.HERTZ),
                FrequencyFormatter.HERTZ);
        assertEquals(formatter.getUnitSymbol(FrequencyUnit.KILOHERTZ),
                FrequencyFormatter.KILOHERTZ);
        assertEquals(formatter.getUnitSymbol(FrequencyUnit.MEGAHERTZ),
                FrequencyFormatter.MEGAHERTZ);
        assertEquals(formatter.getUnitSymbol(FrequencyUnit.GIGAHERTZ),
                FrequencyFormatter.GIGAHERTZ);
        assertEquals(formatter.getUnitSymbol(FrequencyUnit.TERAHERTZ),
                FrequencyFormatter.TERAHERTZ);
    }
}
