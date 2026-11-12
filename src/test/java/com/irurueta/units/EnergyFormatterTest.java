/*
 * Copyright (C) 2026 Alberto Irurueta Carro (alberto@irurueta.com)
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

/**
 * Unit tests for {@link EnergyFormatter}.
 */
class EnergyFormatterTest {

    /**
     * Checks the empty, locale and copy constructors, and that a null locale or formatter is rejected.
     */
    @Test
    void constructor_whenEmptyLocaleOrCopy_initializesExpectedState() {
        // test empty constructor
        var formatter = new EnergyFormatter();

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
        formatter = new EnergyFormatter(locale);

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
        assertThrows(IllegalArgumentException.class, () -> new EnergyFormatter((Locale) null));

        // test copy constructor
        formatter = new EnergyFormatter(locale);
        final var formatter2 = new EnergyFormatter(formatter);

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
        assertThrows(NullPointerException.class, () -> new EnergyFormatter((EnergyFormatter) null));
    }

    /**
     * Checks cloning, before and after the internal number format is initialized.
     *
     * @throws CloneNotSupportedException never happens.
     */
    @Test
    void clone_whenCalled_returnsEqualDifferentInstance() throws CloneNotSupportedException {
        final var formatter1 = new EnergyFormatter();
        final var formatter2 = (EnergyFormatter) formatter1.clone();

        // check
        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        // test after initializing internal number format
        assertNotNull(formatter1.format(0.5, EnergyUnit.JOULE, new StringBuffer(),
                new FieldPosition(0)));
        final var formatter3 = (EnergyFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    /**
     * Checks equality semantics.
     */
    @Test
    void equals_whenDifferentLocales_comparesLocale() {
        final var formatter1 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter2 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter3 = new EnergyFormatter(Locale.FRENCH);

        // check
        //noinspection EqualsWithItself
        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertNotEquals(formatter1, formatter3);

        assertNotEquals(formatter1, new Object());

        assertNotEquals(null, formatter1);
    }

    /**
     * Checks hash code semantics.
     */
    @Test
    void hashCode_whenDifferentLocales_isConsistentWithEquals() {
        final var formatter1 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter2 = new EnergyFormatter(Locale.ENGLISH);
        final var formatter3 = new EnergyFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    /**
     * Checks formatting of {@link Number} values.
     */
    @Test
    void format_whenNumber_returnsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(new BigDecimal(value), EnergyUnit.JOULE));
        assertEquals("5,5 kJ", formatter.format(new BigDecimal(value), EnergyUnit.KILOJOULE));
        assertEquals("5,5 kWh", formatter.format(new BigDecimal(value), EnergyUnit.KILOWATT_HOUR));
        assertEquals("5,5 cal", formatter.format(new BigDecimal(value), EnergyUnit.CALORIE));
        assertEquals("5,5 BTU", formatter.format(new BigDecimal(value), EnergyUnit.BTU));
    }

    /**
     * Checks formatting of {@link Number} values into a buffer.
     */
    @Test
    void format_whenNumberAndBuffer_appendsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(new BigDecimal(value), EnergyUnit.JOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(new BigDecimal(value), EnergyUnit.KILOJOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(new BigDecimal(value), EnergyUnit.KILOWATT_HOUR,
                buffer, new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(new BigDecimal(value), EnergyUnit.CALORIE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(new BigDecimal(value), EnergyUnit.BTU, buffer,
                new FieldPosition(0)).toString());
    }

    /**
     * Checks formatting of double values.
     */
    @Test
    void format_whenDouble_returnsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(value, EnergyUnit.JOULE));
        assertEquals("5,5 kJ", formatter.format(value, EnergyUnit.KILOJOULE));
        assertEquals("5,5 kWh", formatter.format(value, EnergyUnit.KILOWATT_HOUR));
        assertEquals("5,5 cal", formatter.format(value, EnergyUnit.CALORIE));
        assertEquals("5,5 BTU", formatter.format(value, EnergyUnit.BTU));
    }

    /**
     * Checks formatting of double values into a buffer.
     */
    @Test
    void format_whenDoubleAndBuffer_appendsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(value, EnergyUnit.JOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(value, EnergyUnit.KILOJOULE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(value, EnergyUnit.KILOWATT_HOUR, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(value, EnergyUnit.CALORIE, buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(value, EnergyUnit.BTU, buffer,
                new FieldPosition(0)).toString());
    }

    /**
     * Checks formatting of energies.
     */
    @Test
    void format_whenEnergy_returnsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        assertEquals("5,5 J", formatter.format(new Energy(value, EnergyUnit.JOULE)));
        assertEquals("5,5 kJ", formatter.format(new Energy(value, EnergyUnit.KILOJOULE)));
        assertEquals("5,5 kWh", formatter.format(new Energy(value, EnergyUnit.KILOWATT_HOUR)));
        assertEquals("5,5 cal", formatter.format(new Energy(value, EnergyUnit.CALORIE)));
        assertEquals("5,5 BTU", formatter.format(new Energy(value, EnergyUnit.BTU)));
    }

    /**
     * Checks formatting of energies into a buffer.
     */
    @Test
    void format_whenEnergyAndBuffer_appendsValueAndSymbol() {
        final var value = 5.50;
        final var l = new Locale("es", "ES");

        final var formatter = new EnergyFormatter(l);

        var buffer = new StringBuffer();
        assertEquals("5,5 J", formatter.format(new Energy(value, EnergyUnit.JOULE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kJ", formatter.format(new Energy(value, EnergyUnit.KILOJOULE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 kWh", formatter.format(new Energy(value, EnergyUnit.KILOWATT_HOUR), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 cal", formatter.format(new Energy(value, EnergyUnit.CALORIE), buffer,
                new FieldPosition(0)).toString());

        buffer = new StringBuffer();
        assertEquals("5,5 BTU", formatter.format(new Energy(value, EnergyUnit.BTU), buffer,
                new FieldPosition(0)).toString());
    }

    /**
     * Checks that formatting and converting a {@link Number} uses the locale unit system.
     */
    @Test
    void formatAndConvert_whenNumberAndNoUnitSystem_usesLocaleSystem() {
        // test for metric system
        var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new BigDecimal("0.1"), EnergyUnit.JOULE));
        assertEquals("5 kJ", formatter.formatAndConvert(new BigDecimal("5000.0"), EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvert(new BigDecimal("7200000.0"), EnergyUnit.JOULE));
        assertEquals("4,18 kJ", formatter.formatAndConvert(new BigDecimal("1.0"), EnergyUnit.KILOCALORIE));
        assertEquals("527,53 J", formatter.formatAndConvert(new BigDecimal("0.5"), EnergyUnit.BTU));

        // test for imperial system
        formatter = new EnergyFormatter(new Locale("en", "US"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.95 BTU", formatter.formatAndConvert(new BigDecimal("1.0"), EnergyUnit.KILOJOULE));
        assertEquals("65 BTU", formatter.formatAndConvert(new BigDecimal("65.0"), EnergyUnit.BTU));
    }

    /**
     * Checks that formatting and converting a double uses the locale unit system.
     */
    @Test
    void formatAndConvert_whenDoubleAndNoUnitSystem_usesLocaleSystem() {
        // test for metric system
        var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(0.1, EnergyUnit.JOULE));
        assertEquals("5 kJ", formatter.formatAndConvert(5000.0, EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvert(7200000.0, EnergyUnit.JOULE));
        assertEquals("4,18 kJ", formatter.formatAndConvert(1.0, EnergyUnit.KILOCALORIE));
        assertEquals("527,53 J", formatter.formatAndConvert(0.5, EnergyUnit.BTU));

        // test for imperial system
        formatter = new EnergyFormatter(new Locale("en", "US"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.95 BTU", formatter.formatAndConvert(1.0, EnergyUnit.KILOJOULE));
        assertEquals("65 BTU", formatter.formatAndConvert(65.0, EnergyUnit.BTU));
    }

    /**
     * Checks that formatting and converting an energy uses the locale unit system.
     */
    @Test
    void formatAndConvert_whenEnergyAndNoUnitSystem_usesLocaleSystem() {
        // test for metric system
        var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new Energy(0.1, EnergyUnit.JOULE)));
        assertEquals("5 kJ", formatter.formatAndConvert(new Energy(5000.0, EnergyUnit.JOULE)));
        assertEquals("2 kWh", formatter.formatAndConvert(new Energy(7200000.0, EnergyUnit.JOULE)));
        assertEquals("4,18 kJ", formatter.formatAndConvert(new Energy(1.0, EnergyUnit.KILOCALORIE)));
        assertEquals("527,53 J", formatter.formatAndConvert(new Energy(0.5, EnergyUnit.BTU)));

        // test for imperial system
        formatter = new EnergyFormatter(new Locale("en", "US"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0.95 BTU", formatter.formatAndConvert(new Energy(1.0, EnergyUnit.KILOJOULE)));
        assertEquals("65 BTU", formatter.formatAndConvert(new Energy(65.0, EnergyUnit.BTU)));
    }

    /**
     * Checks formatting and conversion of {@link Number} values with an explicit unit system.
     */
    @Test
    void formatAndConvert_whenNumberAndUnitSystem_usesGivenSystem() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new BigDecimal("0.1"), EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("5 kJ", formatter.formatAndConvert(new BigDecimal("5000.0"), EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("2 kWh", formatter.formatAndConvert(new BigDecimal("7200000.0"), EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("4,18 kJ", formatter.formatAndConvert(new BigDecimal("1.0"), EnergyUnit.KILOCALORIE, UnitSystem.METRIC));

        assertEquals("0,95 BTU", formatter.formatAndConvert(new BigDecimal("1.0"), EnergyUnit.KILOJOULE, UnitSystem.IMPERIAL));
        assertEquals("65 BTU", formatter.formatAndConvert(new BigDecimal("65.0"), EnergyUnit.BTU, UnitSystem.IMPERIAL));
    }

    /**
     * Checks formatting and conversion of double values with an explicit unit system.
     */
    @Test
    void formatAndConvert_whenDoubleAndUnitSystem_usesGivenSystem() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(0.1, EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("5 kJ", formatter.formatAndConvert(5000.0, EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("2 kWh", formatter.formatAndConvert(7200000.0, EnergyUnit.JOULE, UnitSystem.METRIC));
        assertEquals("4,18 kJ", formatter.formatAndConvert(1.0, EnergyUnit.KILOCALORIE, UnitSystem.METRIC));

        assertEquals("0,95 BTU", formatter.formatAndConvert(1.0, EnergyUnit.KILOJOULE, UnitSystem.IMPERIAL));
        assertEquals("65 BTU", formatter.formatAndConvert(65.0, EnergyUnit.BTU, UnitSystem.IMPERIAL));
    }

    /**
     * Checks formatting and conversion of energies with an explicit unit system.
     */
    @Test
    void formatAndConvert_whenEnergyAndUnitSystem_usesGivenSystem() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvert(new Energy(0.1, EnergyUnit.JOULE), UnitSystem.METRIC));
        assertEquals("5 kJ", formatter.formatAndConvert(new Energy(5000.0, EnergyUnit.JOULE), UnitSystem.METRIC));
        assertEquals("2 kWh", formatter.formatAndConvert(new Energy(7200000.0, EnergyUnit.JOULE), UnitSystem.METRIC));
        assertEquals("4,18 kJ", formatter.formatAndConvert(new Energy(1.0, EnergyUnit.KILOCALORIE), UnitSystem.METRIC));

        assertEquals("0,95 BTU", formatter.formatAndConvert(new Energy(1.0, EnergyUnit.KILOJOULE), UnitSystem.IMPERIAL));
        assertEquals("65 BTU", formatter.formatAndConvert(new Energy(65.0, EnergyUnit.BTU), UnitSystem.IMPERIAL));
    }

    /**
     * Checks that metric formatting picks the most suitable metric unit.
     */
    @Test
    void formatAndConvertMetric_whenDifferentMagnitudes_picksBestMetricUnit() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,1 J", formatter.formatAndConvertMetric(new BigDecimal("0.1"), EnergyUnit.JOULE));
        assertEquals("5 kJ", formatter.formatAndConvertMetric(new BigDecimal("5000.0"), EnergyUnit.JOULE));
        assertEquals("2 kWh", formatter.formatAndConvertMetric(new BigDecimal("7200000.0"), EnergyUnit.JOULE));

        assertEquals("527,53 J", formatter.formatAndConvertMetric(new BigDecimal("0.5"), EnergyUnit.BTU));
        assertEquals("68,58 kJ", formatter.formatAndConvertMetric(new BigDecimal("65.0"), EnergyUnit.BTU));
    }

    /**
     * Checks that imperial formatting always uses BTU.
     */
    @Test
    void formatAndConvertImperial_whenAnyUnit_returnsBtu() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));
        formatter.setMaximumFractionDigits(2);

        assertEquals("0,95 BTU", formatter.formatAndConvertImperial(new BigDecimal("1.0"), EnergyUnit.KILOJOULE));
        assertEquals("65 BTU", formatter.formatAndConvertImperial(new BigDecimal("65.0"), EnergyUnit.BTU));
    }

    /**
     * Checks available locales.
     */
    @Test
    void getAvailableLocales_whenCalled_returnsNumberFormatLocales() {
        final var locales = EnergyFormatter.getAvailableLocales();
        assertArrayEquals(locales, NumberFormat.getAvailableLocales());
    }

    /**
     * Checks getter and setter of maximum fraction digits.
     */
    @Test
    void setMaximumFractionDigits_whenValid_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMaximumFractionDigits(), NumberFormat.getInstance().getMaximumFractionDigits());

        // set new value
        formatter.setMaximumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    /**
     * Checks getter and setter of maximum integer digits.
     */
    @Test
    void setMaximumIntegerDigits_whenValid_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMaximumIntegerDigits(), NumberFormat.getInstance().getMaximumIntegerDigits());

        // set new value
        formatter.setMaximumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    /**
     * Checks getter and setter of minimum fraction digits.
     */
    @Test
    void setMinimumFractionDigits_whenValid_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMinimumFractionDigits(), NumberFormat.getInstance().getMinimumFractionDigits());

        // set new value
        formatter.setMinimumFractionDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    /**
     * Checks getter and setter of minimum integer digits.
     */
    @Test
    void setMinimumIntegerDigits_whenValid_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getMinimumIntegerDigits(), NumberFormat.getInstance().getMinimumIntegerDigits());

        // set new value
        formatter.setMinimumIntegerDigits(2);

        // check correctness
        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    /**
     * Checks getter and setter of rounding mode.
     */
    @Test
    void setRoundingMode_whenValid_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.getRoundingMode(), NumberFormat.getInstance().getRoundingMode());

        // set new value
        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        // check correctness
        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    /**
     * Checks getter and setter of grouping flag.
     */
    @Test
    void setGroupingUsed_whenToggled_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.isGroupingUsed(), NumberFormat.getInstance().isGroupingUsed());

        // set new value
        formatter.setGroupingUsed(!formatter.isGroupingUsed());

        // check correctness
        assertEquals(formatter.isGroupingUsed(), !NumberFormat.getInstance().isGroupingUsed());
    }

    /**
     * Checks getter and setter of parse integer only flag.
     */
    @Test
    void setParseIntegerOnly_whenToggled_updatesValue() {
        final var formatter = new EnergyFormatter();

        assertEquals(formatter.isParseIntegerOnly(), NumberFormat.getInstance().isParseIntegerOnly());

        // set new value
        formatter.setParseIntegerOnly(!formatter.isParseIntegerOnly());

        // check correctness
        assertEquals(formatter.isParseIntegerOnly(), !NumberFormat.getInstance().isParseIntegerOnly());
    }

    /**
     * Checks getter and setter of the value and unit format pattern, and that a null pattern is rejected.
     */
    @Test
    void setValueAndUnitFormatPattern_whenValidOrNull_updatesPatternOrThrows() {
        final var formatter = new EnergyFormatter();

        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());

        // new value
        formatter.setValueAndUnitFormatPattern("{0}{1}");

        // check correctness
        assertEquals("{0}{1}", formatter.getValueAndUnitFormatPattern());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> formatter.setValueAndUnitFormatPattern(null));
    }

    /**
     * Checks that the unit system depends on the locale.
     */
    @Test
    void getUnitSystem_whenLocale_returnsLocaleUnitSystem() {
        var formatter = new EnergyFormatter(new Locale("es", "ES"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem());

        formatter = new EnergyFormatter(new Locale("en", "US"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem());
    }

    /**
     * Checks that every known symbol is a valid unit, with or without trailing space.
     */
    @Test
    void isValidUnit_whenKnownSymbol_returnsTrue() {
        final var formatter = new EnergyFormatter();

        assertTrue(formatter.isValidUnit("J"));
        assertTrue(formatter.isValidUnit("J "));
        assertTrue(formatter.isValidUnit("kJ"));
        assertTrue(formatter.isValidUnit("kJ "));
        assertTrue(formatter.isValidUnit("cal"));
        assertTrue(formatter.isValidUnit("cal "));
        assertTrue(formatter.isValidUnit("kcal"));
        assertTrue(formatter.isValidUnit("kcal "));
        assertTrue(formatter.isValidUnit("kWh"));
        assertTrue(formatter.isValidUnit("kWh "));
        assertTrue(formatter.isValidUnit("BTU"));
        assertTrue(formatter.isValidUnit("BTU "));
    }

    /**
     * Checks measurement validation.
     */
    @Test
    void isValidMeasurement_whenSource_returnsExpectedResult() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kJ";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 cal";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kcal";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 kWh";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 BTU";
        assertTrue(formatter.isValidMeasurement(text));

        text = "5,5 s";
        assertFalse(formatter.isValidMeasurement(text));
    }

    /**
     * Checks metric unit detection on text.
     */
    @Test
    void isMetricUnit_whenSource_returnsExpectedResult() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kJ";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 cal";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kcal";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 kWh";
        assertTrue(formatter.isMetricUnit(text));

        text = "5,5 BTU";
        assertFalse(formatter.isMetricUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isMetricUnit(text));
    }

    /**
     * Checks imperial unit detection on text.
     */
    @Test
    void isImperialUnit_whenSource_returnsExpectedResult() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kJ";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 cal";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kcal";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 kWh";
        assertFalse(formatter.isImperialUnit(text));

        text = "5,5 BTU";
        assertTrue(formatter.isImperialUnit(text));

        text = "5,5 s";
        assertFalse(formatter.isImperialUnit(text));
    }

    /**
     * Checks unit system detection on text.
     */
    @Test
    void getUnitSystem_whenSource_returnsDetectedSystem() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kJ";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 cal";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kcal";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 kWh";
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem(text));

        text = "5,5 BTU";
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem(text));

        text = "5,5 s";
        assertNull(formatter.getUnitSystem(text));
    }

    /**
     * Checks parsing of valid text, and that unknown units and missing values are rejected.
     *
     * @throws ParseException never happens.
     * @throws UnknownUnitException never happens.
     */
    @Test
    void parse_whenSource_returnsValueAndUnitOrThrows() throws ParseException, UnknownUnitException {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        var s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.JOULE, s.getUnit());

        text = "5,5 kJ";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOJOULE, s.getUnit());

        text = "5,5 cal";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.CALORIE, s.getUnit());

        text = "5,5 kcal";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOCALORIE, s.getUnit());

        text = "5,5 kWh";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.KILOWATT_HOUR, s.getUnit());

        text = "5,5 BTU";
        s = formatter.parse(text);
        assertEquals(5.5, s.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.BTU, s.getUnit());

        // Force UnknownUnitException
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));

        // Force ParseException
        assertThrows(ParseException.class, () -> formatter.parse("m"));
    }

    /**
     * Checks unit detection on text.
     */
    @Test
    void findUnit_whenSource_returnsDetectedUnit() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        var text = "5,5 J";
        assertEquals(EnergyUnit.JOULE, formatter.findUnit(text));

        text = "5,5 kJ";
        assertEquals(EnergyUnit.KILOJOULE, formatter.findUnit(text));

        text = "5,5 MJ";
        assertEquals(EnergyUnit.MEGAJOULE, formatter.findUnit(text));

        text = "5,5 cal";
        assertEquals(EnergyUnit.CALORIE, formatter.findUnit(text));

        text = "5,5 kcal";
        assertEquals(EnergyUnit.KILOCALORIE, formatter.findUnit(text));

        text = "5,5 kWh";
        assertEquals(EnergyUnit.KILOWATT_HOUR, formatter.findUnit(text));

        text = "5,5 BTU";
        assertEquals(EnergyUnit.BTU, formatter.findUnit(text));

        text = "5,5 s";
        assertNull(formatter.findUnit(text));
    }

    /**
     * Checks that symbols only match as whole tokens, so that unsupported prefixed units (such as gigajoules or
     * millijoules) are rejected rather than misparsed as joules, while a symbol followed by trailing text still
     * matches.
     */
    @Test
    void findUnit_whenUnsupportedPrefixedUnit_returnsNullAndParseThrowsUnknownUnitException() {
        final var formatter = new EnergyFormatter(new Locale("es", "ES"));

        assertNull(formatter.findUnit("5,5 GJ"));
        assertNull(formatter.findUnit("5,5 mJ"));
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 GJ"));

        assertEquals(EnergyUnit.KILOJOULE, formatter.findUnit("5,5 kJ total"));
    }

    /**
     * Checks unit symbols.
     */
    @Test
    void getUnitSymbol_whenUnit_returnsSymbol() {
        final var formatter = new EnergyFormatter();

        assertEquals(EnergyFormatter.JOULE, formatter.getUnitSymbol(EnergyUnit.JOULE));
        assertEquals(EnergyFormatter.KILOJOULE, formatter.getUnitSymbol(EnergyUnit.KILOJOULE));
        assertEquals(EnergyFormatter.MEGAJOULE, formatter.getUnitSymbol(EnergyUnit.MEGAJOULE));
        assertEquals(EnergyFormatter.CALORIE, formatter.getUnitSymbol(EnergyUnit.CALORIE));
        assertEquals(EnergyFormatter.KILOCALORIE, formatter.getUnitSymbol(EnergyUnit.KILOCALORIE));
        assertEquals(EnergyFormatter.KILOWATT_HOUR, formatter.getUnitSymbol(EnergyUnit.KILOWATT_HOUR));
        assertEquals(EnergyFormatter.BTU, formatter.getUnitSymbol(EnergyUnit.BTU));
    }
}
