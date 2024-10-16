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

class SpeedFormatterTest {

    @Test
    void testConstructor() {
        // test empty constructor
        var formatter = new SpeedFormatter();

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
        formatter = new SpeedFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(), formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(), formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(), formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(locale), formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(), formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(), formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new SpeedFormatter((Locale) null));

        // test copy constructor
        formatter = new SpeedFormatter(locale);
        final var formatter2 = new SpeedFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new SpeedFormatter((SpeedFormatter) null));
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        final var formatter1 = new SpeedFormatter();
        final var formatter2 = (SpeedFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, SpeedUnit.METERS_PER_SECOND, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (SpeedFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    void testEquals() {
        final var formatter1 = new SpeedFormatter(Locale.ENGLISH);
        final var formatter2 = new SpeedFormatter(Locale.ENGLISH);
        final var formatter3 = new SpeedFormatter(Locale.FRENCH);

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
        final var formatter1 = new SpeedFormatter(Locale.ENGLISH);
        final var formatter2 = new SpeedFormatter(Locale.ENGLISH);
        final var formatter3 = new SpeedFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    void testFormatNumber() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        assertEquals("5,5 m/s", formatter.format(new BigDecimal(value), SpeedUnit.METERS_PER_SECOND));
        assertEquals("5,5 Km/h", formatter.format(new BigDecimal(value), SpeedUnit.KILOMETERS_PER_HOUR));
        assertEquals("5,5 Km/s", formatter.format(new BigDecimal(value), SpeedUnit.KILOMETERS_PER_SECOND));
        assertEquals("5,5 ft/s", formatter.format(new BigDecimal(value), SpeedUnit.FEET_PER_SECOND));
        assertEquals("5,5 mph", formatter.format(new BigDecimal(value), SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testFormatNumberAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 m/s", formatter.format(new BigDecimal(value), SpeedUnit.METERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/h", formatter.format(new BigDecimal(value), SpeedUnit.KILOMETERS_PER_HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/s", formatter.format(new BigDecimal(value), SpeedUnit.KILOMETERS_PER_SECOND,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s", formatter.format(new BigDecimal(value), SpeedUnit.FEET_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mph", formatter.format(new BigDecimal(value), SpeedUnit.MILES_PER_HOUR, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatDouble() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        assertEquals("5,5 m/s", formatter.format(value, SpeedUnit.METERS_PER_SECOND));
        assertEquals("5,5 Km/h", formatter.format(value, SpeedUnit.KILOMETERS_PER_HOUR));
        assertEquals("5,5 Km/s", formatter.format(value, SpeedUnit.KILOMETERS_PER_SECOND));
        assertEquals("5,5 ft/s", formatter.format(value, SpeedUnit.FEET_PER_SECOND));
        assertEquals("5,5 mph", formatter.format(value, SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testFormatDoubleAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 m/s", formatter.format(value, SpeedUnit.METERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/h", formatter.format(value, SpeedUnit.KILOMETERS_PER_HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/s", formatter.format(value, SpeedUnit.KILOMETERS_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s", formatter.format(value, SpeedUnit.FEET_PER_SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mph", formatter.format(value, SpeedUnit.MILES_PER_HOUR, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatSpeed() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        assertEquals("5,5 m/s", formatter.format(new Speed(value, SpeedUnit.METERS_PER_SECOND)));
        assertEquals("5,5 Km/h", formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR)));
        assertEquals("5,5 Km/s", formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_SECOND)));
        assertEquals("5,5 ft/s", formatter.format(new Speed(value, SpeedUnit.FEET_PER_SECOND)));
        assertEquals("5,5 mph", formatter.format(new Speed(value, SpeedUnit.MILES_PER_HOUR)));
    }

    @Test
    void testFormatSpeedAndStringBuffer() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 m/s", formatter.format(new Speed(value, SpeedUnit.METERS_PER_SECOND), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/h", formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 Km/s", formatter.format(new Speed(value, SpeedUnit.KILOMETERS_PER_SECOND), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 ft/s", formatter.format(new Speed(value, SpeedUnit.FEET_PER_SECOND), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 mph", formatter.format(new Speed(value, SpeedUnit.MILES_PER_HOUR), buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    void testFormatAndConvertNumber() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(new BigDecimal("0.1"),
                SpeedUnit.METERS_PER_SECOND));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(new BigDecimal("3.6"),
                SpeedUnit.METERS_PER_SECOND));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(new BigDecimal("3600.0"),
                SpeedUnit.METERS_PER_SECOND));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.73 ft/s", formatter.formatAndConvert(new BigDecimal("0.5"),
                SpeedUnit.MILES_PER_HOUR));
        assertEquals("65 mph", formatter.formatAndConvert(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testFormatAndConvertDouble() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(0.1, SpeedUnit.METERS_PER_SECOND));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(3.6, SpeedUnit.METERS_PER_SECOND));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(3600.0, SpeedUnit.METERS_PER_SECOND));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(65.0, SpeedUnit.MILES_PER_HOUR));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.73 ft/s", formatter.formatAndConvert(0.5, SpeedUnit.MILES_PER_HOUR));
        assertEquals("65 mph", formatter.formatAndConvert(65.0, SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testFormatAndConvertSpeed() {
        // test for metric system
        var l = new Locale("es", "ES");

        var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(new Speed(0.1, SpeedUnit.METERS_PER_SECOND)));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(new Speed(3.6,
                SpeedUnit.METERS_PER_SECOND)));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(new Speed(3600.0,
                SpeedUnit.METERS_PER_SECOND)));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(new Speed(65.0,
                SpeedUnit.MILES_PER_HOUR)));

        // test for imperial system
        l = new Locale("en", "US");

        formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.73 ft/s", formatter.formatAndConvert(new Speed(0.5, SpeedUnit.MILES_PER_HOUR)));
        assertEquals("65 mph", formatter.formatAndConvert(new Speed(65.0, SpeedUnit.MILES_PER_HOUR)));
    }

    @Test
    void testFormatAndConvertNumberAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(new BigDecimal("0.1"),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(new BigDecimal("3.6"),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(new BigDecimal("3600.0"),
                SpeedUnit.METERS_PER_SECOND, UnitSystem.METRIC));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.METRIC));

        assertEquals("0,73 ft/s", formatter.formatAndConvert(new BigDecimal("0.5"),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL));
        assertEquals("65 mph", formatter.formatAndConvert(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR, UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertDoubleAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(0.1, SpeedUnit.METERS_PER_SECOND,
                UnitSystem.METRIC));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(3.6, SpeedUnit.METERS_PER_SECOND,
                UnitSystem.METRIC));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(3600.0, SpeedUnit.METERS_PER_SECOND,
                UnitSystem.METRIC));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(65.0, SpeedUnit.MILES_PER_HOUR,
                UnitSystem.METRIC));

        assertEquals("0,73 ft/s", formatter.formatAndConvert(0.5, SpeedUnit.MILES_PER_HOUR,
                UnitSystem.IMPERIAL));
        assertEquals("65 mph", formatter.formatAndConvert(65.0, SpeedUnit.MILES_PER_HOUR,
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertSpeedAndUnitSystem() {
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvert(new Speed(0.1, SpeedUnit.METERS_PER_SECOND),
                UnitSystem.METRIC));
        assertEquals("12,96 Km/h", formatter.formatAndConvert(new Speed(3.6,
                SpeedUnit.METERS_PER_SECOND), UnitSystem.METRIC));
        assertEquals("3,6 Km/s", formatter.formatAndConvert(new Speed(3600.0,
                SpeedUnit.METERS_PER_SECOND), UnitSystem.METRIC));

        assertEquals("104,61 Km/h", formatter.formatAndConvert(new Speed(65.0, SpeedUnit.MILES_PER_HOUR),
                UnitSystem.METRIC));

        assertEquals("0,73 ft/s", formatter.formatAndConvert(new Speed(0.5, SpeedUnit.MILES_PER_HOUR),
                UnitSystem.IMPERIAL));
        assertEquals("65 mph", formatter.formatAndConvert(new Speed(65.0, SpeedUnit.MILES_PER_HOUR),
                UnitSystem.IMPERIAL));
    }

    @Test
    void testFormatAndConvertMetric() {
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 m/s", formatter.formatAndConvertMetric(new BigDecimal("0.1"),
                SpeedUnit.METERS_PER_SECOND));
        assertEquals("12,96 Km/h", formatter.formatAndConvertMetric(new BigDecimal("3.6"),
                SpeedUnit.METERS_PER_SECOND));
        assertEquals("3,6 Km/s", formatter.formatAndConvertMetric(new BigDecimal("3600.0"),
                SpeedUnit.METERS_PER_SECOND));

        assertEquals("0,22 m/s", formatter.formatAndConvertMetric(new BigDecimal("0.5"),
                SpeedUnit.MILES_PER_HOUR));
        assertEquals("104,61 Km/h", formatter.formatAndConvertMetric(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testFormatAndConvertImperial() {
        final var l = new Locale("es", "ES");

        final var formatter = new SpeedFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,73 ft/s", formatter.formatAndConvertImperial(new BigDecimal("0.5"),
                SpeedUnit.MILES_PER_HOUR));
        assertEquals("65 mph", formatter.formatAndConvertImperial(new BigDecimal("65.0"),
                SpeedUnit.MILES_PER_HOUR));
    }

    @Test
    void testGetAvailableLocales() {
        final var locales = SpeedFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    @Test
    void testGetSetMaximumFractionDigits() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    void testGetSetMaximumIntegerDigits() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    void testGetSetMinimumFractionDigits() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    void testGetSetMinimumIntegerDigits() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    void testGetSetRoundingMode() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    void testIsSetGroupingUsed() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    @Test
    void testIsSetParseIntegerOnly() {
        final var formatter = new SpeedFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    @Test
    void testGetSetValueAndUnitFormatPattern() {
        final var formatter = new SpeedFormatter();

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
        var formatter = new SpeedFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new SpeedFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    @Test
    void testIsValidUnit() {
        final var formatter = new SpeedFormatter();

        assertTrue(formatter.isValidUnit("m/s"));
        assertTrue(formatter.isValidUnit("m/s "));

        assertTrue(formatter.isValidUnit("Km/h"));
        assertTrue(formatter.isValidUnit("Km/h "));

        assertTrue(formatter.isValidUnit("Km/s"));
        assertTrue(formatter.isValidUnit("Km/s "));

        assertTrue(formatter.isValidUnit("mph"));
        assertTrue(formatter.isValidUnit("mph "));
    }

    @Test
    void testIsValidMeasurement() {
        final SpeedFormatter formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km/h";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 Km/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 ft/s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 mph";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));

        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    void testIsMetricUnit() {
        final var formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km/h";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 Km/s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 ft/s";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 mph";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    void testIsImperialUnit() {
        final var formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km/h";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 Km/s";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 ft/s";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 mph";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    @Test
    void testGetUnitSystemFromSource() {
        final var formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Km/h";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 Km/s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 ft/s";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 mph";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    void testParse() throws ParseException, UnknownUnitException {
        final var formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        var s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.METERS_PER_SECOND, s.getUnit());

        text = "5,5 Km/h";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, s.getUnit());

        text = "5,5 Km/s";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.KILOMETERS_PER_SECOND, s.getUnit());

        text = "5,5 ft/s";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.FEET_PER_SECOND, s.getUnit());

        text = "5,5 mph";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.MILES_PER_HOUR, s.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    @Test
    void testFindUnit() {
        final var formatter = new SpeedFormatter(new Locale("es", "ES"));

        var text = "5,5 m/s";
        assertEquals(SpeedUnit.METERS_PER_SECOND, formatter.findUnit(text));

        text = "5,5 Km/h";
        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, formatter.findUnit(text));

        text = "5,5 Km/s";
        assertEquals(SpeedUnit.KILOMETERS_PER_SECOND, formatter.findUnit(text));

        text = "5,5 ft/s";
        assertEquals(SpeedUnit.FEET_PER_SECOND, formatter.findUnit(text));

        text = "5,5 mph";
        assertEquals(SpeedUnit.MILES_PER_HOUR, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    @Test
    void testGetUnitSymbol() {
        final var formatter = new SpeedFormatter();

        assertEquals(SpeedFormatter.METERS_PER_SECOND, formatter.getUnitSymbol(SpeedUnit.METERS_PER_SECOND));
        assertEquals(SpeedFormatter.KILOMETERS_PER_HOUR, formatter.getUnitSymbol(SpeedUnit.KILOMETERS_PER_HOUR));
        assertEquals(SpeedFormatter.KILOMETERS_PER_SECOND, formatter.getUnitSymbol(SpeedUnit.KILOMETERS_PER_SECOND));
        assertEquals(SpeedFormatter.FEET_PER_SECOND, formatter.getUnitSymbol(SpeedUnit.FEET_PER_SECOND));
        assertEquals(SpeedFormatter.MILES_PER_HOUR, formatter.getUnitSymbol(SpeedUnit.MILES_PER_HOUR));
    }
}
