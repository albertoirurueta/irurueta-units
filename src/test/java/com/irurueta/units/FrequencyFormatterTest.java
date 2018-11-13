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

    public FrequencyFormatterTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }

    @Before
    public void setUp() { }

    @After
    public void tearDown() { }

    @Test
    public void testConstructor() {
        //test empty constructor
        FrequencyFormatter formatter = new FrequencyFormatter();

        //check
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

        //test constructor with locale
        Locale locale = new Locale("es", "ES");
        formatter = new FrequencyFormatter(locale);

        //check
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


        //force IllegalArgumentException
        formatter = null;
        try {
            formatter = new FrequencyFormatter(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(formatter);
    }

    @Test
    public void testClone() {
        FrequencyFormatter formatter1 = new FrequencyFormatter();
        FrequencyFormatter formatter2 = (FrequencyFormatter) formatter1.clone();

        //check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        //test after initializing internal number format
        assertNotNull(formatter1.format(0.5, FrequencyUnit.HERTZ,
                new StringBuffer(), new FieldPosition(0)));
        FrequencyFormatter formatter3 = (FrequencyFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        FrequencyFormatter formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        FrequencyFormatter formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        FrequencyFormatter formatter3 = new FrequencyFormatter(Locale.FRENCH);

        //check
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        //noinspection all
        assertFalse(formatter1.equals(null));
    }

    @Test
    public void testHashCode() {
        FrequencyFormatter formatter1 = new FrequencyFormatter(Locale.ENGLISH);
        FrequencyFormatter formatter2 = new FrequencyFormatter(Locale.ENGLISH);
        FrequencyFormatter formatter3 = new FrequencyFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        double value = 5.50;
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);

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
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500.00),
                FrequencyUnit.HERTZ), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500000.00),
                FrequencyUnit.HERTZ), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500.00),
                FrequencyUnit.MEGAHERTZ), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500000.00),
                FrequencyUnit.MEGAHERTZ), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertDouble()  {
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
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
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
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
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvert(new BigDecimal(5.50),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 Hz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500.00),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 KHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500000.00),
                FrequencyUnit.HERTZ, UnitSystem.METRIC), "5,5 MHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500.00),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 GHz");
        assertEquals(formatter.formatAndConvert(new BigDecimal(5500000.00),
                FrequencyUnit.MEGAHERTZ, UnitSystem.METRIC), "5,5 THz");
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
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
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
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
        Locale l = new Locale("es", "ES");

        FrequencyFormatter formatter = new FrequencyFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5.50),
                FrequencyUnit.HERTZ), "5,5 Hz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5500.00),
                FrequencyUnit.HERTZ), "5,5 KHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5500000.00),
                FrequencyUnit.HERTZ), "5,5 MHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5500.00),
                FrequencyUnit.MEGAHERTZ), "5,5 GHz");
        assertEquals(formatter.formatAndConvertMetric(new BigDecimal(5500000.00),
                FrequencyUnit.MEGAHERTZ), "5,5 THz");
    }

    @Test
    public void testGetAvailableLocales() {
        Locale[] locales = FrequencyFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumFractionDigits(),
                NumberFormat.getInstance().getMaximumFractionDigits());

        //set new value
        formatter.setMaximumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        //set new value
        formatter.setMaximumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMaximumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        //set new value
        formatter.setMinimumFractionDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumFractionDigits(), 2);
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        //set new value
        formatter.setMinimumIntegerDigits(2);

        //check correctness
        assertEquals(formatter.getMinimumIntegerDigits(), 2);
    }

    @Test
    public void testGetSetRoundingMode() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        //set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        //check correctness
        assertEquals(formatter.getRoundingMode(), RoundingMode.UNNECESSARY);
    }

    @Test
    public void testIsSetGroupingUsed() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());

        //set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        //check correctness
        assertEquals(formatter.isGroupingUsed(),
                !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    public void testIsSetParseIntegerOnly() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());

        //set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        //check correctness
        assertEquals(formatter.isParseIntegerOnly(),
                !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    public void testGetSetValueAndUnitFormatPattern() {
        FrequencyFormatter formatter = new FrequencyFormatter();

        assertEquals(formatter.getValueAndUnitFormatPattern(),
                MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN);

        //new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        //check correctness
        assertEquals(formatter.getValueAndUnitFormatPattern(), "{0}{1}");

        //force IllegalArgumentException
        try {
            formatter.setValueAndUnitFormatPattern(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
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
        FrequencyFormatter formatter = new FrequencyFormatter();

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
        FrequencyFormatter formatter = new FrequencyFormatter(
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
        FrequencyFormatter formatter = new FrequencyFormatter(
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
        FrequencyFormatter formatter = new FrequencyFormatter(
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
        FrequencyFormatter formatter = new FrequencyFormatter(
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
        FrequencyFormatter formatter = new FrequencyFormatter(
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

        //Force UnknownUnitException
        text = "5,5 s";
        try {
            formatter.parse(text);
            fail("UnknownUnitException expected but not thrown");
        } catch (UnknownUnitException ignore) { }

        //Force ParseException
        try {
            formatter.parse("m");
            fail("ParseException expected but not thrown");
        } catch (ParseException ignore) { }
    }

    @Test
    public void testFindUnit() {
        FrequencyFormatter formatter = new FrequencyFormatter(
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
        FrequencyFormatter formatter = new FrequencyFormatter();

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
