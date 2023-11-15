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

public class TimeFormatterTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        TimeFormatter formatter = new TimeFormatter();

        // check
        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMaximumIntegerDigits(),
                formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getMinimumFractionDigits(),
                formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance().getMinimumIntegerDigits(),
                formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance().getRoundingMode(),
                formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance().isGroupingUsed(),
                formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance().isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // test constructor with locale
        final Locale locale = new Locale("es", "ES");
        formatter = new TimeFormatter(locale);

        // check
        assertEquals(locale, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(),
                formatter.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(),
                formatter.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(),
                formatter.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(),
                formatter.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());


        // force IllegalArgumentException
        formatter = null;
        try {
            formatter = new TimeFormatter((Locale) null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(formatter);


        // test copy constructor
        formatter = new TimeFormatter(locale);
        final TimeFormatter formatter2 = new TimeFormatter(formatter);

        // check
        assertEquals(locale, formatter2.getLocale());
        assertEquals(NumberFormat.getInstance(locale).getMaximumFractionDigits(),
                formatter2.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMaximumIntegerDigits(),
                formatter2.getMaximumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumFractionDigits(),
                formatter2.getMinimumFractionDigits());
        assertEquals(NumberFormat.getInstance(locale).getMinimumIntegerDigits(),
                formatter2.getMinimumIntegerDigits());
        assertEquals(NumberFormat.getInstance(locale).getRoundingMode(),
                formatter2.getRoundingMode());
        assertEquals(UnitSystem.METRIC, formatter2.getUnitSystem());
        assertEquals(NumberFormat.getInstance(locale).isGroupingUsed(),
                formatter2.isGroupingUsed());
        assertEquals(NumberFormat.getInstance(locale).isParseIntegerOnly(),
                formatter2.isParseIntegerOnly());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter2.getValueAndUnitFormatPattern());

        formatter = null;
        try {
            // noinspection ConstantConditions
            formatter = new TimeFormatter((TimeFormatter) null);
            fail("NullPointerException expected but not thrown");
        } catch (final NullPointerException ignore) {
        }
        assertNull(formatter);
    }

    @Test
    public void testClone() throws CloneNotSupportedException {
        final TimeFormatter formatter1 = new TimeFormatter();
        final TimeFormatter formatter2 = new TimeFormatter();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, TimeUnit.SECOND,
                new StringBuffer(), new FieldPosition(0)));
        final TimeFormatter formatter3 = (TimeFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    @Test
    public void testEquals() {
        final TimeFormatter formatter1 = new TimeFormatter(Locale.ENGLISH);
        final TimeFormatter formatter2 = new TimeFormatter(Locale.ENGLISH);
        final TimeFormatter formatter3 = new TimeFormatter(Locale.FRENCH);

        // check
        //noinspection EqualsWithItself
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    @Test
    public void testHashCode() {
        final TimeFormatter formatter1 = new TimeFormatter(Locale.ENGLISH);
        final TimeFormatter formatter2 = new TimeFormatter(Locale.ENGLISH);
        final TimeFormatter formatter3 = new TimeFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    @Test
    public void testFormatNumber() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        assertEquals("3 ns", formatter.format(new BigDecimal(value),
                TimeUnit.NANOSECOND));
        assertEquals("3 µs", formatter.format(new BigDecimal(value),
                TimeUnit.MICROSECOND));
        assertEquals("3 ms", formatter.format(new BigDecimal(value),
                TimeUnit.MILLISECOND));
        assertEquals("3 s", formatter.format(new BigDecimal(value),
                TimeUnit.SECOND));
        assertEquals("3 min", formatter.format(new BigDecimal(value),
                TimeUnit.MINUTE));
        assertEquals("3 h", formatter.format(new BigDecimal(value),
                TimeUnit.HOUR));
        assertEquals("3 d", formatter.format(new BigDecimal(value),
                TimeUnit.DAY));
        assertEquals("3 wk", formatter.format(new BigDecimal(value),
                TimeUnit.WEEK));
        assertEquals("3 mon", formatter.format(new BigDecimal(value),
                TimeUnit.MONTH));
        assertEquals("3 yr", formatter.format(new BigDecimal(value),
                TimeUnit.YEAR));
        assertEquals("1st c.", formatter.format(new BigDecimal("1.0"),
                TimeUnit.CENTURY));
        assertEquals("2nd c.", formatter.format(new BigDecimal("2.0"),
                TimeUnit.CENTURY));
        assertEquals("3rd c.", formatter.format(new BigDecimal("3.0"),
                TimeUnit.CENTURY));
        assertEquals("4th c.", formatter.format(new BigDecimal("4.0"),
                TimeUnit.CENTURY));
    }

    @Test
    public void testFormatNumberAndStringBuffer() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("3 ns", formatter.format(new BigDecimal(value),
                TimeUnit.NANOSECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 µs", formatter.format(new BigDecimal(value),
                TimeUnit.MICROSECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 ms", formatter.format(new BigDecimal(value),
                TimeUnit.MILLISECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 s", formatter.format(new BigDecimal(value),
                TimeUnit.SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 min", formatter.format(new BigDecimal(value),
                TimeUnit.MINUTE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 h", formatter.format(new BigDecimal(value),
                TimeUnit.HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 d", formatter.format(new BigDecimal(value),
                TimeUnit.DAY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 wk", formatter.format(new BigDecimal(value),
                TimeUnit.WEEK, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 mon", formatter.format(new BigDecimal(value),
                TimeUnit.MONTH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 yr", formatter.format(new BigDecimal(value),
                TimeUnit.YEAR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("1st c.", formatter.format(new BigDecimal("1.0"),
                TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("2nd c.", formatter.format(new BigDecimal("2.0"),
                TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3rd c.", formatter.format(new BigDecimal("3.0"),
                TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("4th c.", formatter.format(new BigDecimal("4.0"),
                TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatDouble() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        assertEquals("3 ns",
                formatter.format(value, TimeUnit.NANOSECOND));
        assertEquals("3 µs",
                formatter.format(value, TimeUnit.MICROSECOND));
        assertEquals("3 ms",
                formatter.format(value, TimeUnit.MILLISECOND));
        assertEquals("3 s",
                formatter.format(value, TimeUnit.SECOND));
        assertEquals("3 min",
                formatter.format(value, TimeUnit.MINUTE));
        assertEquals("3 h",
                formatter.format(value, TimeUnit.HOUR));
        assertEquals("3 d",
                formatter.format(value, TimeUnit.DAY));
        assertEquals("3 wk",
                formatter.format(value, TimeUnit.WEEK));
        assertEquals("3 mon",
                formatter.format(value, TimeUnit.MONTH));
        assertEquals("3 yr",
                formatter.format(value, TimeUnit.YEAR));
        assertEquals("1st c.",
                formatter.format(1.0, TimeUnit.CENTURY));
        assertEquals("2nd c.",
                formatter.format(2.0, TimeUnit.CENTURY));
        assertEquals("3rd c.",
                formatter.format(3.0, TimeUnit.CENTURY));
        assertEquals("4th c.",
                formatter.format(4.0, TimeUnit.CENTURY));
    }

    @Test
    public void testFormatDoubleAndStringBuffer() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("3 ns", formatter.format(value, TimeUnit.NANOSECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 µs", formatter.format(value, TimeUnit.MICROSECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 ms", formatter.format(value, TimeUnit.MILLISECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 s", formatter.format(value, TimeUnit.SECOND, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 min", formatter.format(value, TimeUnit.MINUTE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 h", formatter.format(value, TimeUnit.HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 d", formatter.format(value, TimeUnit.DAY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 wk", formatter.format(value, TimeUnit.WEEK, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 mon", formatter.format(value, TimeUnit.MONTH, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 yr", formatter.format(value, TimeUnit.YEAR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("1st c.", formatter.format(1.0, TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("2nd c.", formatter.format(2.0, TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3rd c.", formatter.format(3.0, TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("4th c.", formatter.format(4.0, TimeUnit.CENTURY, buffer,
                new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatTime() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        assertEquals("3 ns",
                formatter.format(new Time(value, TimeUnit.NANOSECOND)));
        assertEquals("3 µs",
                formatter.format(new Time(value, TimeUnit.MICROSECOND)));
        assertEquals("3 ms",
                formatter.format(new Time(value, TimeUnit.MILLISECOND)));
        assertEquals("3 s",
                formatter.format(new Time(value, TimeUnit.SECOND)));
        assertEquals("3 min",
                formatter.format(new Time(value, TimeUnit.MINUTE)));
        assertEquals("3 h",
                formatter.format(new Time(value, TimeUnit.HOUR)));
        assertEquals("3 d",
                formatter.format(new Time(value, TimeUnit.DAY)));
        assertEquals("3 wk",
                formatter.format(new Time(value, TimeUnit.WEEK)));
        assertEquals("3 mon",
                formatter.format(new Time(value, TimeUnit.MONTH)));
        assertEquals("3 yr",
                formatter.format(new Time(value, TimeUnit.YEAR)));
        assertEquals("1st c.",
                formatter.format(new Time(1.0, TimeUnit.CENTURY)));
        assertEquals("2nd c.",
                formatter.format(new Time(2.0, TimeUnit.CENTURY)));
        assertEquals("3rd c.",
                formatter.format(new Time(3.0, TimeUnit.CENTURY)));
        assertEquals("4th c.",
                formatter.format(new Time(4.0, TimeUnit.CENTURY)));
    }

    @Test
    public void testFormatTimeAndStringBuffer() {
        final double value = 3.0;
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);

        StringBuffer buffer = new StringBuffer();
        assertEquals("3 ns", formatter.format(new Time(value, TimeUnit.NANOSECOND),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 µs", formatter.format(new Time(value, TimeUnit.MICROSECOND),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 ms", formatter.format(new Time(value, TimeUnit.MILLISECOND),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 s", formatter.format(new Time(value, TimeUnit.SECOND),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 min", formatter.format(new Time(value, TimeUnit.MINUTE),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 h", formatter.format(new Time(value, TimeUnit.HOUR),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 d", formatter.format(new Time(value, TimeUnit.DAY),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 wk", formatter.format(new Time(value, TimeUnit.WEEK),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 mon", formatter.format(new Time(value, TimeUnit.MONTH),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3 yr", formatter.format(new Time(value, TimeUnit.YEAR),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("1st c.", formatter.format(new Time(1.0, TimeUnit.CENTURY),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("2nd c.", formatter.format(new Time(2.0, TimeUnit.CENTURY),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("3rd c.", formatter.format(new Time(3.0, TimeUnit.CENTURY),
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("4th c.", formatter.format(new Time(4.0, TimeUnit.CENTURY),
                buffer, new FieldPosition(0)).toString());
    }

    @Test
    public void testFormatAndConvertNumber() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.NANOSECOND));
        assertEquals("5,5 µs", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MICROSECOND));
        assertEquals("5,5 ms", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MILLISECOND));
        assertEquals("5,5 s", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.SECOND));
        assertEquals("5,5 min", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MINUTE));
        assertEquals("5,5 h", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.HOUR));
        assertEquals("5,5 d", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.DAY));
        assertEquals("3,5 wk", formatter.formatAndConvert(new BigDecimal("3.50"),
                TimeUnit.WEEK));
        assertEquals("5,5 mon", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MONTH));
        assertEquals("5,5 yr", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.YEAR));
        assertEquals("5th c.", formatter.formatAndConvert(new BigDecimal("5.0"),
                TimeUnit.CENTURY));
    }

    @Test
    public void testFormatAndConvertDouble() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(5.50,
                TimeUnit.NANOSECOND));
        assertEquals("5,5 µs", formatter.formatAndConvert(5.50,
                TimeUnit.MICROSECOND));
        assertEquals("5,5 ms", formatter.formatAndConvert(5.50,
                TimeUnit.MILLISECOND));
        assertEquals("5,5 s", formatter.formatAndConvert(5.50,
                TimeUnit.SECOND));
        assertEquals("5,5 min", formatter.formatAndConvert(5.50,
                TimeUnit.MINUTE));
        assertEquals("5,5 h", formatter.formatAndConvert(5.50,
                TimeUnit.HOUR));
        assertEquals("5,5 d", formatter.formatAndConvert(5.50,
                TimeUnit.DAY));
        assertEquals("3,5 wk", formatter.formatAndConvert(3.50,
                TimeUnit.WEEK));
        assertEquals("5,5 mon", formatter.formatAndConvert(5.50,
                TimeUnit.MONTH));
        assertEquals("5,5 yr", formatter.formatAndConvert(5.50,
                TimeUnit.YEAR));
        assertEquals("5th c.", formatter.formatAndConvert(5.0,
                TimeUnit.CENTURY));
    }

    @Test
    public void testFormatAndConvertTime() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.NANOSECOND)));
        assertEquals("5,5 µs", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MICROSECOND)));
        assertEquals("5,5 ms", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MILLISECOND)));
        assertEquals("5,5 s", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.SECOND)));
        assertEquals("5,5 min", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MINUTE)));
        assertEquals("5,5 h", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.HOUR)));
        assertEquals("5,5 d", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.DAY)));
        assertEquals("3,5 wk", formatter.formatAndConvert(new Time(3.50,
                TimeUnit.WEEK)));
        assertEquals("5,5 mon", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MONTH)));
        assertEquals("5,5 yr", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.YEAR)));
        assertEquals("5th c.", formatter.formatAndConvert(new Time(5.0,
                TimeUnit.CENTURY)));
    }

    @Test
    public void testFormatAndConvertNumberAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.NANOSECOND, null));
        assertEquals("5,5 µs", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MICROSECOND, null));
        assertEquals("5,5 ms", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MILLISECOND, null));
        assertEquals("5,5 s", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.SECOND, null));
        assertEquals("5,5 min", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MINUTE, null));
        assertEquals("5,5 h", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.HOUR, null));
        assertEquals("5,5 d", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.DAY, null));
        assertEquals("3,5 wk", formatter.formatAndConvert(new BigDecimal("3.50"),
                TimeUnit.WEEK, null));
        assertEquals("5,5 mon", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.MONTH, null));
        assertEquals("5,5 yr", formatter.formatAndConvert(new BigDecimal("5.50"),
                TimeUnit.YEAR, null));
        assertEquals("5th c.", formatter.formatAndConvert(new BigDecimal("5.0"),
                TimeUnit.CENTURY, null));
    }

    @Test
    public void testFormatAndConvertDoubleAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(5.50,
                TimeUnit.NANOSECOND, null));
        assertEquals("5,5 µs", formatter.formatAndConvert(5.50,
                TimeUnit.MICROSECOND, null));
        assertEquals("5,5 ms", formatter.formatAndConvert(5.50,
                TimeUnit.MILLISECOND, null));
        assertEquals("5,5 s", formatter.formatAndConvert(5.50,
                TimeUnit.SECOND, null));
        assertEquals("5,5 min", formatter.formatAndConvert(5.50,
                TimeUnit.MINUTE, null));
        assertEquals("5,5 h", formatter.formatAndConvert(5.50,
                TimeUnit.HOUR, null));
        assertEquals("5,5 d", formatter.formatAndConvert(5.50,
                TimeUnit.DAY, null));
        assertEquals("3,5 wk", formatter.formatAndConvert(3.50,
                TimeUnit.WEEK, null));
        assertEquals("5,5 mon", formatter.formatAndConvert(5.50,
                TimeUnit.MONTH, null));
        assertEquals("5,5 yr", formatter.formatAndConvert(5.50,
                TimeUnit.YEAR, null));
        assertEquals("5th c.", formatter.formatAndConvert(5.0,
                TimeUnit.CENTURY, null));
    }

    @Test
    public void testFormatAndConvertTimeAndUnitSystem() {
        final Locale l = new Locale("es", "ES");

        final TimeFormatter formatter = new TimeFormatter(l);
        formatter.setMaximumFractionDigits(2);

        assertEquals("5,5 ns", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.NANOSECOND), null));
        assertEquals("5,5 µs", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MICROSECOND), null));
        assertEquals("5,5 ms", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MILLISECOND), null));
        assertEquals("5,5 s", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.SECOND), null));
        assertEquals("5,5 min", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MINUTE), null));
        assertEquals("5,5 h", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.HOUR), null));
        assertEquals("5,5 d", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.DAY), null));
        assertEquals("3,5 wk", formatter.formatAndConvert(new Time(3.50,
                TimeUnit.WEEK), null));
        assertEquals("5,5 mon", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.MONTH), null));
        assertEquals("5,5 yr", formatter.formatAndConvert(new Time(5.50,
                TimeUnit.YEAR), null));
        assertEquals("5th c.", formatter.formatAndConvert(new Time(5.0,
                TimeUnit.CENTURY), null));
    }

    @Test
    public void testGetAvailableLocales() {
        final Locale[] locales = TimeFormatter.getAvailableLocales();
        assertArrayEquals(NumberFormat.getAvailableLocales(), locales);
    }

    @Test
    public void testGetSetMaximumFractionDigits() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    @Test
    public void testGetSetMaximumIntegerDigits() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(),
                NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    @Test
    public void testGetSetMinimumFractionDigits() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.getMinimumFractionDigits(),
                NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    @Test
    public void testGetSetMinimumIntegerDigits() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(),
                NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    @Test
    public void testGetSetRoundingMode() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.getRoundingMode(),
                NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    @Test
    public void testIsSetGroupingUsed() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.isGroupingUsed(),
                NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isGroupingUsed(),
                formatter.isGroupingUsed());
    }

    @Test
    public void testIsSetParseIntegerOnly() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(formatter.isParseIntegerOnly(),
                NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(!NumberFormat.getInstance().isParseIntegerOnly(),
                formatter.isParseIntegerOnly());
    }

    @Test
    public void testGetSetValueAndUnitFormatPattern() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN,
                formatter.getValueAndUnitFormatPattern());

        // new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        // check correctness
        assertEquals("{0}{1}", formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        try {
            formatter.setValueAndUnitFormatPattern(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetUnitSystem() {
        TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new TimeFormatter(
                new Locale("en", "US"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());
    }

    @Test
    public void testIsValidUnit() {
        final TimeFormatter formatter = new TimeFormatter();

        assertTrue(formatter.isValidUnit("ns"));
        assertTrue(formatter.isValidUnit("µs"));
        assertTrue(formatter.isValidUnit("s"));
        assertTrue(formatter.isValidUnit("min"));
        assertTrue(formatter.isValidUnit("h"));
        assertTrue(formatter.isValidUnit("d"));
        assertTrue(formatter.isValidUnit("wk"));
        assertTrue(formatter.isValidUnit("mon"));
        assertTrue(formatter.isValidUnit("yr"));
        assertTrue(formatter.isValidUnit("st c."));
        assertTrue(formatter.isValidUnit("nd c."));
        assertTrue(formatter.isValidUnit("rd c."));
        assertTrue(formatter.isValidUnit("th c."));
    }

    @Test
    public void testIsValidMeasurement() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        String text = "5 ns";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 µs";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 ms";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 s";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 min";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 h";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 d";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 wk";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 mon";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5 yr";
        assertTrue(formatter.isValidMeasurement(text));

        text = "1st c.";
        assertTrue(formatter.isValidMeasurement(text));

        text = "2nd c.";
        assertTrue(formatter.isValidMeasurement(text));

        text = "3rd c.";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5th c.";
        assertTrue(formatter.isValidMeasurement(text));


        text = "m";
        assertFalse(formatter.isValidMeasurement(text));
    }

    @Test
    public void testIsMetricUnit() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        String text = "5 ns";
        assertTrue(formatter.isMetricUnit(text));

        text = "5 µs";
        assertTrue(formatter.isMetricUnit(text));

        text = "5 ms";
        assertTrue(formatter.isMetricUnit(text));

        text = "5 s";
        assertTrue(formatter.isMetricUnit(text));

        text = "5 min";
        assertFalse(formatter.isMetricUnit(text));

        text = "5 h";
        assertFalse(formatter.isMetricUnit(text));

        text = "5 d";
        assertFalse(formatter.isMetricUnit(text));

        text = "5 wk";
        assertFalse(formatter.isMetricUnit(text));

        text = "5 mon";
        assertFalse(formatter.isMetricUnit(text));

        text = "5 yr";
        assertFalse(formatter.isMetricUnit(text));

        text = "5th c.";
        assertFalse(formatter.isMetricUnit(text));
    }

    @Test
    public void testGetUnitSystemFromSource() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        String text = "5 ns";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5 µs";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5 ms";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5 s";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5 min";
        assertNull(formatter.getUnitSystem(text));

        text = "5 h";
        assertNull(formatter.getUnitSystem(text));

        text = "5 d";
        assertNull(formatter.getUnitSystem(text));

        text = "5 wk";
        assertNull(formatter.getUnitSystem(text));

        text = "5 mon";
        assertNull(formatter.getUnitSystem(text));

        text = "5 yr";
        assertNull(formatter.getUnitSystem(text));

        text = "5th c.";
        assertNull(formatter.getUnitSystem(text));
    }

    @Test
    public void testParse() throws ParseException, UnknownUnitException {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        String text = "5 ns";
        Time t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.NANOSECOND, t.getUnit());

        text = "5 µs";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.MICROSECOND, t.getUnit());

        text = "5 ms";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.MILLISECOND, t.getUnit());

        text = "5 s";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.SECOND, t.getUnit());

        text = "5 min";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.MINUTE, t.getUnit());

        text = "5 h";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.HOUR, t.getUnit());

        text = "5 d";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.DAY, t.getUnit());

        text = "5 wk";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.WEEK, t.getUnit());

        text = "5 mon";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.MONTH, t.getUnit());

        text = "5 yr";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.YEAR, t.getUnit());

        text = "1st c.";
        t = formatter.parse(text);
        assertEquals(1.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.CENTURY, t.getUnit());

        text = "2nd c.";
        t = formatter.parse(text);
        assertEquals(2.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.CENTURY, t.getUnit());

        text = "3rd c.";
        t = formatter.parse(text);
        assertEquals(3.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.CENTURY, t.getUnit());

        text = "5th c.";
        t = formatter.parse(text);
        assertEquals(5.0, t.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.CENTURY, t.getUnit());

        // Force UnknownUnitException
        text = "5,5 m";
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
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        String text = "5 ns";
        assertEquals(TimeUnit.NANOSECOND, formatter.findUnit(text));

        text = "5 µs";
        assertEquals(TimeUnit.MICROSECOND, formatter.findUnit(text));

        text = "5 ms";
        assertEquals(TimeUnit.MILLISECOND, formatter.findUnit(text));

        text = "5 s";
        assertEquals(TimeUnit.SECOND, formatter.findUnit(text));

        text = "5 min";
        assertEquals(TimeUnit.MINUTE, formatter.findUnit(text));

        text = "5 h";
        assertEquals(TimeUnit.HOUR, formatter.findUnit(text));

        text = "5 d";
        assertEquals(TimeUnit.DAY, formatter.findUnit(text));

        text = "5 wk";
        assertEquals(TimeUnit.WEEK, formatter.findUnit(text));

        text = "5 mon";
        assertEquals(TimeUnit.MONTH, formatter.findUnit(text));

        text = "5 yr";
        assertEquals(TimeUnit.YEAR, formatter.findUnit(text));

        text = "1st c.";
        assertEquals(TimeUnit.CENTURY, formatter.findUnit(text));

        text = "2nd c.";
        assertEquals(TimeUnit.CENTURY, formatter.findUnit(text));

        text = "5th c.";
        assertEquals(TimeUnit.CENTURY, formatter.findUnit(text));

        text = "5,5 m";
        assertNull(formatter.findUnit(text));
    }

    @Test
    public void testGetUnitSymbol() {
        final TimeFormatter formatter = new TimeFormatter();

        assertEquals(TimeFormatter.NANOSECOND_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.NANOSECOND));
        assertEquals(TimeFormatter.MICROSECOND_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.MICROSECOND));
        assertEquals(TimeFormatter.MILLISECOND_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.MILLISECOND));
        assertEquals(TimeFormatter.SECOND_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.SECOND));
        assertEquals(TimeFormatter.MINUTE_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.MINUTE));
        assertEquals(TimeFormatter.HOUR_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.HOUR));
        assertEquals(TimeFormatter.DAY_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.DAY));
        assertEquals(TimeFormatter.WEEK_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.WEEK));
        assertEquals(TimeFormatter.MONTH_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.MONTH));
        assertEquals(TimeFormatter.YEAR_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.YEAR));
        assertEquals(TimeFormatter.CENTURY_SYMBOL,
                formatter.getUnitSymbol(TimeUnit.CENTURY));
    }

    @Test
    public void testFormatHourMinute() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        final Time t1 = new Time(3.25, TimeUnit.HOUR);
        final Time t2 = new Time(3.5, TimeUnit.HOUR);
        final Time t3 = new Time(4.0, TimeUnit.HOUR);
        final Time t4 = new Time(12.0, TimeUnit.HOUR);
        final Time t5 = new Time(123.0, TimeUnit.HOUR);
        final Time t6 = new Time(3.425, TimeUnit.HOUR);
        final Time t7 = new Time(3.0, TimeUnit.HOUR).
                addAndReturnNew(new Time(61.0, TimeUnit.MINUTE),
                        TimeUnit.HOUR);


        // check
        assertEquals("03:15", formatter.formatHourMinute(t1));
        assertEquals("03:30", formatter.formatHourMinute(t2));
        assertEquals("04:00", formatter.formatHourMinute(t3));
        assertEquals("12:00", formatter.formatHourMinute(t4));
        assertEquals("123:00", formatter.formatHourMinute(t5));
        assertEquals("03:25,5", formatter.formatHourMinute(t6));
        assertEquals("04:01", formatter.formatHourMinute(t7));
    }

    @Test
    public void testParseHourMinute() throws ParseException, UnknownUnitException {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        final Time t1 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinute("03:15"), TimeUnit.HOUR);

        // check
        assertEquals(3.25, t1.getValue().doubleValue(), ERROR);


        final Time t2 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinute("03:30"), TimeUnit.HOUR);

        // check
        assertEquals(3.5, t2.getValue().doubleValue(), ERROR);


        final Time t3 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinute("04:00"), TimeUnit.HOUR);

        // check
        assertEquals(4.0, t3.getValue().doubleValue(), ERROR);


        final Time t4 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinute("12:00"), TimeUnit.HOUR);

        // check
        assertEquals(12.0, t4.getValue().doubleValue(), ERROR);


        final Time t5 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinute("123:00"), TimeUnit.HOUR);

        // check
        assertEquals(123.0, t5.getValue().doubleValue(), ERROR);


        // Force UnknownUnitException
        Time t6 = null;
        try {
            t6 = TimeConverter.convertAndReturnNew(
                    formatter.parseHourMinute("03:25,5"), TimeUnit.HOUR);
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        assertNull(t6);
    }

    @Test
    public void testFormatHourMinuteSecond() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        final Time t1 = new Time(3.25, TimeUnit.HOUR);
        final Time t2 = new Time(3.5, TimeUnit.HOUR);
        final Time t3 = new Time(4.0, TimeUnit.HOUR);
        final Time t4 = new Time(12.0, TimeUnit.HOUR);
        final Time t5 = new Time(123.0, TimeUnit.HOUR);
        final Time t6 = new Time(3.425, TimeUnit.HOUR);
        final Time t7 = new Time(3.0 + (25.0 + (30.5 / 60.0)) / 60.0, TimeUnit.HOUR);
        final Time t8 = new Time(3.0, TimeUnit.HOUR).
                addAndReturnNew(new Time(61, TimeUnit.MINUTE), TimeUnit.HOUR).
                addAndReturnNew(new Time(61, TimeUnit.SECOND), TimeUnit.HOUR);

        // check
        assertEquals("03:15:00", formatter.formatHourMinuteSecond(t1));
        assertEquals("03:30:00", formatter.formatHourMinuteSecond(t2));
        assertEquals("04:00:00", formatter.formatHourMinuteSecond(t3));
        assertEquals("12:00:00", formatter.formatHourMinuteSecond(t4));
        assertEquals("123:00:00", formatter.formatHourMinuteSecond(t5));
        assertEquals("03:25:30", formatter.formatHourMinuteSecond(t6));
        assertEquals("03:25:30,5", formatter.formatHourMinuteSecond(t7));
        assertEquals("04:02:01", formatter.formatHourMinuteSecond(t8));
    }

    @Test
    public void testParseHourMinuteSecond() throws ParseException, UnknownUnitException {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        final Time t1 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("03:15:00"), TimeUnit.HOUR);

        // check
        assertEquals(3.25, t1.getValue().doubleValue(), ERROR);


        final Time t2 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("03:30:00"), TimeUnit.HOUR);

        // check
        assertEquals(3.5, t2.getValue().doubleValue(), ERROR);


        final Time t3 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("04:00:00"), TimeUnit.HOUR);

        // check
        assertEquals(4.0, t3.getValue().doubleValue(), ERROR);


        final Time t4 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("12:00:00"), TimeUnit.HOUR);

        // check
        assertEquals(12.0, t4.getValue().doubleValue(), ERROR);


        final Time t5 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("123:00:00"), TimeUnit.HOUR);

        // check
        assertEquals(123.0, t5.getValue().doubleValue(), ERROR);


        final Time t6 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("03:25:30"), TimeUnit.HOUR);

        // check
        assertEquals(3.425, t6.getValue().doubleValue(), ERROR);


        final double value = new Time(3.0, TimeUnit.HOUR).
                addAndReturnNew(new Time(61, TimeUnit.MINUTE), TimeUnit.HOUR).
                addAndReturnNew(new Time(61, TimeUnit.SECOND), TimeUnit.HOUR).
                getValue().doubleValue();

        final Time t7 = TimeConverter.convertAndReturnNew(
                formatter.parseHourMinuteSecond("04:02:01"), TimeUnit.HOUR);

        // check
        assertEquals(t7.getValue().doubleValue(), value, ERROR);


        // Force UnknownUnitException
        Time t8 = null;
        try {
            t8 = formatter.parseHourMinuteSecond("03:25:30,5");
        } catch (final UnknownUnitException ignore) {
        }
        assertNull(t8);
    }

    @Test
    public void testFormatMultiple() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        // format all
        Time t = new Time(1.0, TimeUnit.CENTURY);
        assertEquals("1st c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(2.0, TimeUnit.CENTURY);
        assertEquals("2nd c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(3.0, TimeUnit.CENTURY);
        assertEquals("3rd c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(4.0, TimeUnit.CENTURY);
        assertEquals("4th c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.YEAR);
        assertEquals("1 yr",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.MONTH);
        assertEquals("1 mon",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.WEEK);
        assertEquals("1 wk",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.DAY);
        assertEquals("1 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.HOUR);
        assertEquals("1 h",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.MINUTE);
        assertEquals("1 min",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.SECOND);
        assertEquals("1 s",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.MILLISECOND);
        assertEquals("1 ms",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.MICROSECOND);
        assertEquals("1 µs",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));

        t = new Time(1.0, TimeUnit.NANOSECOND);
        assertEquals("1 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL));


        // format centuries
        t = new Time(1.5, TimeUnit.CENTURY);
        assertEquals("1,5nd c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_CENTURIES));

        // format years
        t = new Time(1.5, TimeUnit.CENTURY);
        assertEquals("150 yr",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_YEARS));

        // format months
        t = new Time(1.5, TimeUnit.YEAR);
        assertEquals("18,25 mon",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MONTHS));

        // format weeks
        t = new Time(1.5, TimeUnit.MONTH);
        assertEquals("6,429 wk",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_WEEKS));

        // format days
        t = new Time(1.5, TimeUnit.WEEK);
        assertEquals("10,5 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DAYS));

        // format hours
        t = new Time(1.5, TimeUnit.DAY);
        assertEquals("36 h",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_HOURS));

        // format minute
        t = new Time(1.5, TimeUnit.HOUR);
        assertEquals("90 min",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MINUTES));

        // format seconds
        t = new Time(1.5, TimeUnit.MINUTE);
        assertEquals("90 s",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_SECONDS));

        // format milliseconds
        t = new Time(1.5, TimeUnit.SECOND);
        assertEquals("1.500 ms",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MILLISECONDS));

        // format microseconds
        t = new Time(1.5, TimeUnit.MILLISECOND);
        assertEquals("1.500 µs",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MICROSECONDS));

        // format nanoseconds
        t = new Time(1.5, TimeUnit.MICROSECOND);
        assertEquals("1.500 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_NANOSECONDS));

        // format time all
        t = new Time(3.0, TimeUnit.DAY).
                addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY).
                addAndReturnNew(new Time(22.0, TimeUnit.SECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(138.0, TimeUnit.MILLISECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(385.0, TimeUnit.MICROSECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(460.466, TimeUnit.NANOSECOND), TimeUnit.DAY);
        assertEquals("79 h 48 min 22 s 138 ms 385 µs 460,466 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_TIME_ALL));

        // format time standard
        assertEquals("79 h 48 min 22,138 s",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_TIME_STANDARD));

        // format date all
        t = new Time(1.0, TimeUnit.CENTURY).
                addAndReturnNew(new Time(2.0, TimeUnit.YEAR), TimeUnit.DAY).
                addAndReturnNew(new Time(3.0, TimeUnit.MONTH), TimeUnit.DAY).
                addAndReturnNew(new Time(1.0, TimeUnit.WEEK), TimeUnit.WEEK).
                addAndReturnNew(new Time(2.0, TimeUnit.DAY), TimeUnit.DAY);
        assertEquals("1st c. 2 yr 3 mon 1 wk 2 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DATE_ALL));

        // format date standard
        assertEquals("102 yr 3 mon 9 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DATE_STANDARD));
    }

    @Test
    public void testFormatMultipleIncludingZeroValues() {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        // format all
        Time t = new Time(1.0, TimeUnit.CENTURY);
        assertEquals("1st c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(2.0, TimeUnit.CENTURY);
        assertEquals("2nd c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(3.0, TimeUnit.CENTURY);
        assertEquals("3rd c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(4.0, TimeUnit.CENTURY);
        assertEquals("4th c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.YEAR);
        assertEquals("0st c. 1 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.MONTH);
        assertEquals("0st c. 0 yr 1 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.WEEK);
        assertEquals("0st c. 0 yr 0 mon 1 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.DAY);
        assertEquals("0st c. 0 yr 0 mon 0 wk 1 d 0 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.HOUR);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 1 h 0 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.MINUTE);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 0 h 1 min 0 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.SECOND);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 1 s 0 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.MILLISECOND);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 1 ms 0 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.MICROSECOND);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 1 µs 0 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));

        t = new Time(1.0, TimeUnit.NANOSECOND);
        assertEquals("0st c. 0 yr 0 mon 0 wk 0 d 0 h 0 min 0 s 0 ms 0 µs 1 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_ALL, false));


        // format centuries
        t = new Time(1.5, TimeUnit.CENTURY);
        assertEquals("1,5nd c.",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_CENTURIES, false));

        // format years
        t = new Time(1.5, TimeUnit.CENTURY);
        assertEquals("150 yr",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_YEARS, false));

        // format months
        t = new Time(1.5, TimeUnit.YEAR);
        assertEquals("18,25 mon",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MONTHS, false));

        // format weeks
        t = new Time(1.5, TimeUnit.MONTH);
        assertEquals("6,429 wk",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_WEEKS, false));

        // format days
        t = new Time(1.5, TimeUnit.WEEK);
        assertEquals("10,5 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DAYS, false));

        // format hours
        t = new Time(1.5, TimeUnit.DAY);
        assertEquals("36 h",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_HOURS, false));

        // format minute
        t = new Time(1.5, TimeUnit.HOUR);
        assertEquals("90 min",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MINUTES, false));

        // format seconds
        t = new Time(1.5, TimeUnit.MINUTE);
        assertEquals("90 s",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_SECONDS, false));

        // format milliseconds
        t = new Time(1.5, TimeUnit.SECOND);
        assertEquals("1.500 ms",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MILLISECONDS, false));

        // format microseconds
        t = new Time(1.5, TimeUnit.MILLISECOND);
        assertEquals("1.500 µs",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_MICROSECONDS, false));

        // format nanoseconds
        t = new Time(1.5, TimeUnit.MICROSECOND);
        assertEquals("1.500 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_NANOSECONDS, false));

        // format time all
        t = new Time(3.0, TimeUnit.DAY).
                addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY).
                addAndReturnNew(new Time(22.0, TimeUnit.SECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(138.0, TimeUnit.MILLISECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(385.0, TimeUnit.MICROSECOND), TimeUnit.DAY).
                addAndReturnNew(new Time(460.466, TimeUnit.NANOSECOND), TimeUnit.DAY);
        assertEquals("79 h 48 min 22 s 138 ms 385 µs 460,466 ns",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_TIME_ALL, false));

        // format time standard
        assertEquals("79 h 48 min 22,138 s",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_TIME_STANDARD, false));

        // format date all
        t = new Time(1.0, TimeUnit.CENTURY).
                addAndReturnNew(new Time(2.0, TimeUnit.YEAR), TimeUnit.DAY).
                addAndReturnNew(new Time(3.0, TimeUnit.MONTH), TimeUnit.DAY).
                addAndReturnNew(new Time(0.0, TimeUnit.WEEK), TimeUnit.WEEK).
                addAndReturnNew(new Time(2.0, TimeUnit.DAY), TimeUnit.DAY);
        assertEquals("1st c. 2 yr 3 mon 0 wk 2 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DATE_ALL, false));

        // format date standard
        assertEquals("102 yr 3 mon 2 d",
                formatter.formatMultiple(t, TimeFormatter.FORMAT_DATE_STANDARD, false));
    }

    @Test
    public void testParseMultiple() throws ParseException, UnknownUnitException {
        final TimeFormatter formatter = new TimeFormatter(
                new Locale("es", "ES"));

        Time t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1st c."), TimeUnit.CENTURY);
        assertTrue(t.equals(new Time(1.0, TimeUnit.CENTURY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("2nd c."), TimeUnit.CENTURY);
        assertTrue(t.equals(new Time(2.0, TimeUnit.CENTURY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("3rd c."), TimeUnit.CENTURY);
        assertTrue(t.equals(new Time(3.0, TimeUnit.CENTURY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("4th c."), TimeUnit.CENTURY);
        assertTrue(t.equals(new Time(4.0, TimeUnit.CENTURY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 yr"), TimeUnit.YEAR);
        assertTrue(t.equals(new Time(1.0, TimeUnit.YEAR), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 mon"), TimeUnit.MONTH);
        assertTrue(t.equals(new Time(1.0, TimeUnit.MONTH), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 wk"), TimeUnit.WEEK);
        assertTrue(t.equals(new Time(1.0, TimeUnit.WEEK), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 d"), TimeUnit.DAY);
        assertTrue(t.equals(new Time(1.0, TimeUnit.DAY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 h"), TimeUnit.HOUR);
        assertTrue(t.equals(new Time(1.0, TimeUnit.HOUR), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 min"), TimeUnit.MINUTE);
        assertTrue(t.equals(new Time(1.0, TimeUnit.MINUTE), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 s"), TimeUnit.SECOND);
        assertTrue(t.equals(new Time(1.0, TimeUnit.SECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 ms"), TimeUnit.MILLISECOND);
        assertTrue(t.equals(new Time(1.0, TimeUnit.MILLISECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 µs"), TimeUnit.MICROSECOND);
        assertTrue(t.equals(new Time(1.0, TimeUnit.MICROSECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1 ns"), TimeUnit.NANOSECOND);
        assertTrue(t.equals(new Time(1.0, TimeUnit.NANOSECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("150 yr"), TimeUnit.CENTURY);
        assertTrue(t.equals(new Time(1.5, TimeUnit.CENTURY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("36 h"), TimeUnit.DAY);
        assertTrue(t.equals(new Time(1.5, TimeUnit.DAY), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("90 min"), TimeUnit.HOUR);
        assertTrue(t.equals(new Time(1.5, TimeUnit.HOUR), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("90 s"), TimeUnit.MINUTE);
        assertTrue(t.equals(new Time(1.5, TimeUnit.MINUTE), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1500 ms"), TimeUnit.SECOND);
        assertTrue(t.equals(new Time(1.5, TimeUnit.SECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1500 µs"), TimeUnit.MILLISECOND);
        assertTrue(t.equals(new Time(1.5, TimeUnit.MILLISECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1500 ns"), TimeUnit.MICROSECOND);
        assertTrue(t.equals(new Time(1.5, TimeUnit.MICROSECOND), ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("79 h 48 min 22 s 138 ms 385 µs 460 ns"),
                TimeUnit.DAY);
        assertTrue(t.equals(new Time(3.0, TimeUnit.DAY).
                        addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                        addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY).
                        addAndReturnNew(new Time(22.0, TimeUnit.SECOND), TimeUnit.DAY).
                        addAndReturnNew(new Time(138.0, TimeUnit.MILLISECOND), TimeUnit.DAY).
                        addAndReturnNew(new Time(385.0, TimeUnit.MICROSECOND), TimeUnit.DAY).
                        addAndReturnNew(new Time(460.0, TimeUnit.NANOSECOND), TimeUnit.DAY),
                ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("79 h 48 min 22 s"),
                TimeUnit.DAY);
        assertTrue(t.equals(new Time(3.0, TimeUnit.DAY).
                        addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                        addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY).
                        addAndReturnNew(new Time(22.0, TimeUnit.SECOND), TimeUnit.DAY),
                ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("1st c. 2 yr 3 mon 0 wk 2 d"),
                TimeUnit.DAY);
        assertTrue(t.equals(new Time(1.0, TimeUnit.CENTURY).
                        addAndReturnNew(new Time(2.0, TimeUnit.YEAR), TimeUnit.DAY).
                        addAndReturnNew(new Time(3.0, TimeUnit.MONTH), TimeUnit.DAY).
                        addAndReturnNew(new Time(2.0, TimeUnit.DAY), TimeUnit.DAY),
                ERROR));

        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("102 yr 3 mon 2 d"), TimeUnit.DAY);
        assertTrue(t.equals(new Time(1.0, TimeUnit.CENTURY).
                        addAndReturnNew(new Time(2.0, TimeUnit.YEAR), TimeUnit.DAY).
                        addAndReturnNew(new Time(3.0, TimeUnit.MONTH), TimeUnit.DAY).
                        addAndReturnNew(new Time(2.0, TimeUnit.DAY), TimeUnit.DAY),
                ERROR));

        t = TimeConverter.convertAndReturnNew(formatter.parseMultiple("7 d 3h"), TimeUnit.DAY);
        assertTrue(t.equals(new Time(7.0, TimeUnit.DAY).
                addAndReturnNew(new Time(3.0, TimeUnit.HOUR), TimeUnit.DAY), ERROR));

        // components with decimals or thousand separators are ignored
        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("79 h 48 min 22 s 138 ms 385 µs 460,466 ns"),
                TimeUnit.DAY);
        assertTrue(t.equals(new Time(3.0, TimeUnit.DAY).
                        addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                        addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY).
                        addAndReturnNew(new Time(22.0, TimeUnit.SECOND), TimeUnit.DAY).
                        addAndReturnNew(new Time(138.0, TimeUnit.MILLISECOND), TimeUnit.DAY).
                        addAndReturnNew(new Time(385.0, TimeUnit.MICROSECOND), TimeUnit.DAY),
                ERROR));
        t = TimeConverter.convertAndReturnNew(
                formatter.parseMultiple("79 h 48 min 22,138 s"),
                TimeUnit.DAY);
        assertTrue(t.equals(new Time(3.0, TimeUnit.DAY).
                        addAndReturnNew(new Time(7.0, TimeUnit.HOUR), TimeUnit.DAY).
                        addAndReturnNew(new Time(48.0, TimeUnit.MINUTE), TimeUnit.DAY),
                ERROR));


        // force UnknownUnitException (decimals or thousand separators are not allowed)
        t = null;
        try {
            t = formatter.parseMultiple("1,5nd c.");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("18,25 mon");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("6,429 wk");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("10,5 d");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("1.500 ms");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("1.500 µs");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        try {
            t = formatter.parseMultiple("1.500 ns");
            fail("UnknownUnitException expected but not thrown");
        } catch (final UnknownUnitException ignore) {
        }
        assertNull(t);
    }
}
