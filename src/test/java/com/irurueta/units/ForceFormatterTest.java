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
 * Unit tests for {@link ForceFormatter}.
 */
class ForceFormatterTest {

    /**
     * Spanish locale (metric, comma as decimal separator).
     */
    private static final Locale SPAIN = Locale.of("es", "ES");

    /**
     * US locale (imperial, dot as decimal separator).
     */
    private static final Locale US = Locale.of("en", "US");

    /**
     * Value used when formatting.
     */
    private static final double VALUE = 5.5;

    /**
     * Maximum fraction digits used in conversion tests.
     */
    private static final int FRACTION_DIGITS = 2;

    /**
     * Checks that unit symbols are the expected ones.
     */
    @Test
    void constants_whenRead_matchExpectedSymbols() {
        assertEquals("N", ForceFormatter.NEWTON);
        assertEquals("kN", ForceFormatter.KILONEWTON);
        assertEquals("lbf", ForceFormatter.POUND_FORCE);
        assertEquals("dyn", ForceFormatter.DYNE);
        assertEquals("kgf", ForceFormatter.KILOGRAM_FORCE);
    }

    /**
     * Checks the empty constructor uses defaults.
     */
    @Test
    void constructor_whenEmpty_usesDefaults() {
        final var formatter = new ForceFormatter();

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
    }

    /**
     * Checks the constructor with locale uses that locale.
     */
    @Test
    void constructor_whenLocale_usesLocale() {
        final var formatter = new ForceFormatter(SPAIN);

        assertEquals(SPAIN, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(SPAIN).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(NumberFormat.getInstance(SPAIN).getRoundingMode(), formatter.getRoundingMode());
        assertEquals(UnitLocale.getFrom(SPAIN), formatter.getUnitSystem());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());
    }

    /**
     * Checks that a null locale is rejected.
     */
    @Test
    void constructor_whenNullLocale_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ForceFormatter((Locale) null));
    }

    /**
     * Checks the copy constructor.
     */
    @Test
    void constructor_whenCopy_copiesLocale() {
        final var formatter = new ForceFormatter(SPAIN);

        final var copy = new ForceFormatter(formatter);

        assertEquals(SPAIN, copy.getLocale());
        assertEquals(UnitLocale.getFrom(SPAIN), copy.getUnitSystem());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, copy.getValueAndUnitFormatPattern());
    }

    /**
     * Checks that a null formatter to copy is rejected.
     */
    @Test
    void constructor_whenNullFormatter_throwsNullPointerException() {
        //noinspection DataFlowIssue
        assertThrows(NullPointerException.class, () -> new ForceFormatter((ForceFormatter) null));
    }

    /**
     * Checks cloning, before and after the internal number format is initialized.
     *
     * @throws CloneNotSupportedException never happens.
     */
    @Test
    void clone_whenCalled_returnsEqualDifferentInstance() throws CloneNotSupportedException {
        final var formatter1 = new ForceFormatter();
        final var formatter2 = (ForceFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        assertNotNull(formatter1.format(0.5, ForceUnit.NEWTON, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (ForceFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    /**
     * Checks equality semantics.
     */
    @Test
    void equals_whenDifferentLocales_comparesLocale() {
        final var formatter1 = new ForceFormatter(Locale.ENGLISH);
        final var formatter2 = new ForceFormatter(Locale.ENGLISH);
        final var formatter3 = new ForceFormatter(Locale.FRENCH);

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
        final var formatter1 = new ForceFormatter(Locale.ENGLISH);
        final var formatter2 = new ForceFormatter(Locale.ENGLISH);
        final var formatter3 = new ForceFormatter(Locale.FRENCH);

        assertEquals(formatter1.hashCode(), formatter1.hashCode());
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
    }

    /**
     * Checks formatting of number values.
     */
    @Test
    void format_whenNumber_returnsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);
        final var value = BigDecimal.valueOf(VALUE);

        assertEquals("5,5 N", formatter.format(value, ForceUnit.NEWTON));
        assertEquals("5,5 kN", formatter.format(value, ForceUnit.KILONEWTON));
        assertEquals("5,5 lbf", formatter.format(value, ForceUnit.POUND_FORCE));
        assertEquals("5,5 dyn", formatter.format(value, ForceUnit.DYNE));
        assertEquals("5,5 kgf", formatter.format(value, ForceUnit.KILOGRAM_FORCE));
    }

    /**
     * Checks formatting of number values into a buffer.
     */
    @Test
    void format_whenNumberAndBuffer_appendsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);
        final var value = BigDecimal.valueOf(VALUE);

        for (final var unit : ForceUnit.values()) {
            final var buffer = new StringBuffer();
            assertEquals(formatter.format(value, unit),
                    formatter.format(value, unit, buffer, new FieldPosition(0)).toString());
        }
        assertEquals("5,5 kgf", formatter.format(value, ForceUnit.KILOGRAM_FORCE, new StringBuffer(),
                new FieldPosition(0)).toString());
    }

    /**
     * Checks formatting of double values.
     */
    @Test
    void format_whenDouble_returnsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);

        assertEquals("5,5 N", formatter.format(VALUE, ForceUnit.NEWTON));
        assertEquals("5,5 kN", formatter.format(VALUE, ForceUnit.KILONEWTON));
        assertEquals("5,5 lbf", formatter.format(VALUE, ForceUnit.POUND_FORCE));
        assertEquals("5,5 dyn", formatter.format(VALUE, ForceUnit.DYNE));
        assertEquals("5,5 kgf", formatter.format(VALUE, ForceUnit.KILOGRAM_FORCE));
    }

    /**
     * Checks formatting of double values into a buffer.
     */
    @Test
    void format_whenDoubleAndBuffer_appendsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);

        for (final var unit : ForceUnit.values()) {
            final var buffer = new StringBuffer();
            assertEquals(formatter.format(VALUE, unit),
                    formatter.format(VALUE, unit, buffer, new FieldPosition(0)).toString());
        }
    }

    /**
     * Checks formatting of force instances.
     */
    @Test
    void format_whenForce_returnsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);

        assertEquals("5,5 N", formatter.format(new Force(VALUE, ForceUnit.NEWTON)));
        assertEquals("5,5 kN", formatter.format(new Force(VALUE, ForceUnit.KILONEWTON)));
        assertEquals("5,5 lbf", formatter.format(new Force(VALUE, ForceUnit.POUND_FORCE)));
        assertEquals("5,5 dyn", formatter.format(new Force(VALUE, ForceUnit.DYNE)));
        assertEquals("5,5 kgf", formatter.format(new Force(VALUE, ForceUnit.KILOGRAM_FORCE)));
    }

    /**
     * Checks formatting of force instances into a buffer.
     */
    @Test
    void format_whenForceAndBuffer_appendsValueAndSymbol() {
        final var formatter = new ForceFormatter(SPAIN);

        for (final var unit : ForceUnit.values()) {
            final var force = new Force(VALUE, unit);
            final var buffer = new StringBuffer();
            assertEquals(formatter.format(force),
                    formatter.format(force, buffer, new FieldPosition(0)).toString());
        }
    }

    /**
     * Checks metric conversion by magnitude.
     */
    @Test
    void formatAndConvertMetric_whenDifferentMagnitudes_picksNewtonOrKilonewton() {
        final var formatter = new ForceFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("0,5 N", formatter.formatAndConvertMetric(BigDecimal.valueOf(0.5), ForceUnit.NEWTON));
        assertEquals("999 N", formatter.formatAndConvertMetric(BigDecimal.valueOf(999.0), ForceUnit.NEWTON));
        assertEquals("1 kN", formatter.formatAndConvertMetric(BigDecimal.valueOf(1000.0), ForceUnit.NEWTON));
        assertEquals("1,5 kN", formatter.formatAndConvertMetric(BigDecimal.valueOf(1500.0), ForceUnit.NEWTON));
        assertEquals("2 kN", formatter.formatAndConvertMetric(BigDecimal.valueOf(2.0), ForceUnit.KILONEWTON));
        assertEquals("-1,5 kN", formatter.formatAndConvertMetric(BigDecimal.valueOf(-1500.0), ForceUnit.NEWTON));
        assertEquals("4,45 N", formatter.formatAndConvertMetric(BigDecimal.ONE, ForceUnit.POUND_FORCE));
        assertEquals("9,81 N", formatter.formatAndConvertMetric(BigDecimal.ONE, ForceUnit.KILOGRAM_FORCE));
        assertEquals("1 N", formatter.formatAndConvertMetric(BigDecimal.valueOf(100000.0), ForceUnit.DYNE));
    }

    /**
     * Checks imperial conversion always yields pound-force.
     */
    @Test
    void formatAndConvertImperial_whenAnyUnit_returnsPoundForce() {
        final var formatter = new ForceFormatter(US);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("2.25 lbf", formatter.formatAndConvertImperial(BigDecimal.TEN, ForceUnit.NEWTON));
        assertEquals("224.81 lbf", formatter.formatAndConvertImperial(BigDecimal.ONE, ForceUnit.KILONEWTON));
        assertEquals("65 lbf", formatter.formatAndConvertImperial(BigDecimal.valueOf(65.0), ForceUnit.POUND_FORCE));
    }

    /**
     * Checks formatAndConvert with number and unit system.
     */
    @Test
    void formatAndConvert_whenNumberAndUnitSystem_usesGivenSystem() {
        final var formatter = new ForceFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("1,5 kN", formatter.formatAndConvert(BigDecimal.valueOf(1500.0), ForceUnit.NEWTON,
                UnitSystem.METRIC));
        assertEquals("2,25 lbf", formatter.formatAndConvert(BigDecimal.TEN, ForceUnit.NEWTON, UnitSystem.IMPERIAL));
    }

    /**
     * Checks formatAndConvert with double and unit system.
     */
    @Test
    void formatAndConvert_whenDoubleAndUnitSystem_usesGivenSystem() {
        final var formatter = new ForceFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("1,5 kN", formatter.formatAndConvert(1500.0, ForceUnit.NEWTON, UnitSystem.METRIC));
        assertEquals("2,25 lbf", formatter.formatAndConvert(10.0, ForceUnit.NEWTON, UnitSystem.IMPERIAL));
    }

    /**
     * Checks formatAndConvert with force and unit system.
     */
    @Test
    void formatAndConvert_whenForceAndUnitSystem_usesGivenSystem() {
        final var formatter = new ForceFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("1,5 kN", formatter.formatAndConvert(new Force(1500.0, ForceUnit.NEWTON), UnitSystem.METRIC));
        assertEquals("2,25 lbf", formatter.formatAndConvert(new Force(10.0, ForceUnit.NEWTON),
                UnitSystem.IMPERIAL));
    }

    /**
     * Checks formatAndConvert using the unit system of the locale.
     */
    @Test
    void formatAndConvert_whenNoUnitSystem_usesLocaleSystem() {
        final var metric = new ForceFormatter(SPAIN);
        metric.setMaximumFractionDigits(FRACTION_DIGITS);
        final var imperial = new ForceFormatter(US);
        imperial.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("1,5 kN", metric.formatAndConvert(BigDecimal.valueOf(1500.0), ForceUnit.NEWTON));
        assertEquals("1,5 kN", metric.formatAndConvert(1500.0, ForceUnit.NEWTON));
        assertEquals("1,5 kN", metric.formatAndConvert(new Force(1500.0, ForceUnit.NEWTON)));

        assertEquals("2.25 lbf", imperial.formatAndConvert(BigDecimal.TEN, ForceUnit.NEWTON));
        assertEquals("2.25 lbf", imperial.formatAndConvert(10.0, ForceUnit.NEWTON));
        assertEquals("2.25 lbf", imperial.formatAndConvert(new Force(10.0, ForceUnit.NEWTON)));
    }

    /**
     * Checks available locales.
     */
    @Test
    void getAvailableLocales_whenCalled_returnsNumberFormatLocales() {
        assertArrayEquals(NumberFormat.getAvailableLocales(), ForceFormatter.getAvailableLocales());
    }

    /**
     * Checks maximum fraction digits accessors.
     */
    @Test
    void setMaximumFractionDigits_whenValid_updatesValue() {
        final var formatter = new ForceFormatter();

        formatter.setMaximumFractionDigits(2);

        assertEquals(2, formatter.getMaximumFractionDigits());
    }

    /**
     * Checks maximum integer digits accessors.
     */
    @Test
    void setMaximumIntegerDigits_whenValid_updatesValue() {
        final var formatter = new ForceFormatter();

        formatter.setMaximumIntegerDigits(2);

        assertEquals(2, formatter.getMaximumIntegerDigits());
    }

    /**
     * Checks minimum fraction digits accessors.
     */
    @Test
    void setMinimumFractionDigits_whenValid_updatesValue() {
        final var formatter = new ForceFormatter();

        formatter.setMinimumFractionDigits(2);

        assertEquals(2, formatter.getMinimumFractionDigits());
    }

    /**
     * Checks minimum integer digits accessors.
     */
    @Test
    void setMinimumIntegerDigits_whenValid_updatesValue() {
        final var formatter = new ForceFormatter();

        formatter.setMinimumIntegerDigits(2);

        assertEquals(2, formatter.getMinimumIntegerDigits());
    }

    /**
     * Checks rounding mode accessors.
     */
    @Test
    void setRoundingMode_whenValid_updatesValue() {
        final var formatter = new ForceFormatter();

        formatter.setRoundingMode(RoundingMode.UNNECESSARY);

        assertEquals(RoundingMode.UNNECESSARY, formatter.getRoundingMode());
    }

    /**
     * Checks grouping accessors.
     */
    @Test
    void setGroupingUsed_whenToggled_updatesValue() {
        final var formatter = new ForceFormatter();
        final var expected = !formatter.isGroupingUsed();

        formatter.setGroupingUsed(expected);

        assertEquals(expected, formatter.isGroupingUsed());
    }

    /**
     * Checks parse integer only accessors.
     */
    @Test
    void setParseIntegerOnly_whenToggled_updatesValue() {
        final var formatter = new ForceFormatter();
        final var expected = !formatter.isParseIntegerOnly();

        formatter.setParseIntegerOnly(expected);

        assertEquals(expected, formatter.isParseIntegerOnly());
    }

    /**
     * Checks value and unit format pattern accessors.
     */
    @Test
    void setValueAndUnitFormatPattern_whenValid_updatesPattern() {
        final var formatter = new ForceFormatter();

        formatter.setValueAndUnitFormatPattern("{0}{1}");

        assertEquals("{0}{1}", formatter.getValueAndUnitFormatPattern());
    }

    /**
     * Checks that a null pattern is rejected.
     */
    @Test
    void setValueAndUnitFormatPattern_whenNull_throwsIllegalArgumentException() {
        final var formatter = new ForceFormatter();

        assertThrows(IllegalArgumentException.class, () -> formatter.setValueAndUnitFormatPattern(null));
    }

    /**
     * Checks the unit system of the formatter depends on its locale.
     */
    @Test
    void getUnitSystem_whenLocale_returnsLocaleUnitSystem() {
        assertEquals(UnitSystem.METRIC, new ForceFormatter(SPAIN).getUnitSystem());
        assertEquals(UnitSystem.IMPERIAL, new ForceFormatter(US).getUnitSystem());
    }

    /**
     * Checks unit validity detection.
     */
    @Test
    void isValidUnit_whenKnownSymbol_returnsTrue() {
        final var formatter = new ForceFormatter();

        for (final var symbol : new String[]{"N", "kN", "lbf", "dyn", "kgf"}) {
            assertTrue(formatter.isValidUnit(symbol));
            assertTrue(formatter.isValidUnit(symbol + " "));
        }
        assertFalse(formatter.isValidUnit("s"));
    }

    /**
     * Checks measurement validity detection.
     */
    @Test
    void isValidMeasurement_whenSource_returnsExpectedResult() {
        final var formatter = new ForceFormatter(SPAIN);

        assertTrue(formatter.isValidMeasurement("5,5 N"));
        assertTrue(formatter.isValidMeasurement("5,5 kN"));
        assertTrue(formatter.isValidMeasurement("5,5 lbf"));
        assertTrue(formatter.isValidMeasurement("5,5 dyn"));
        assertTrue(formatter.isValidMeasurement("5,5 kgf"));
        assertFalse(formatter.isValidMeasurement("5,5 s"));
        assertFalse(formatter.isValidMeasurement("N"));
    }

    /**
     * Checks metric unit detection.
     */
    @Test
    void isMetricUnit_whenSource_returnsExpectedResult() {
        final var formatter = new ForceFormatter(SPAIN);

        assertTrue(formatter.isMetricUnit("5,5 N"));
        assertTrue(formatter.isMetricUnit("5,5 kN"));
        assertFalse(formatter.isMetricUnit("5,5 lbf"));
        assertTrue(formatter.isMetricUnit("5,5 dyn"));
        assertTrue(formatter.isMetricUnit("5,5 kgf"));
        assertFalse(formatter.isMetricUnit("5,5 s"));
    }

    /**
     * Checks imperial unit detection.
     */
    @Test
    void isImperialUnit_whenSource_returnsExpectedResult() {
        final var formatter = new ForceFormatter(SPAIN);

        assertFalse(formatter.isImperialUnit("5,5 N"));
        assertFalse(formatter.isImperialUnit("5,5 kN"));
        assertTrue(formatter.isImperialUnit("5,5 lbf"));
        assertFalse(formatter.isImperialUnit("5,5 dyn"));
        assertFalse(formatter.isImperialUnit("5,5 kgf"));
        assertFalse(formatter.isImperialUnit("5,5 s"));
    }

    /**
     * Checks unit system detection from source.
     */
    @Test
    void getUnitSystem_whenSource_returnsDetectedSystem() {
        final var formatter = new ForceFormatter(SPAIN);

        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 N"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 kN"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem("5,5 lbf"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 dyn"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 kgf"));
        assertNull(formatter.getUnitSystem("5,5 s"));
    }

    /**
     * Checks parsing of every supported unit.
     *
     * @throws ParseException       never happens.
     * @throws UnknownUnitException never happens.
     */
    @Test
    void parse_whenValidSource_returnsValueAndUnit() throws ParseException, UnknownUnitException {
        final var formatter = new ForceFormatter(SPAIN);

        final var expectedUnits = new ForceUnit[]{ForceUnit.NEWTON, ForceUnit.KILONEWTON, ForceUnit.POUND_FORCE,
                ForceUnit.DYNE, ForceUnit.KILOGRAM_FORCE};
        for (final var unit : expectedUnits) {
            final var force = formatter.parse("5,5 " + formatter.getUnitSymbol(unit));

            assertEquals(VALUE, force.getValue().doubleValue(), 0.0);
            assertEquals(unit, force.getUnit());
        }
    }

    /**
     * Checks that parsing a source with unknown unit fails.
     */
    @Test
    void parse_whenUnknownUnit_throwsUnknownUnitException() {
        final var formatter = new ForceFormatter(SPAIN);

        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));
    }

    /**
     * Checks that parsing a source without a value fails.
     */
    @Test
    void parse_whenNoValue_throwsParseException() {
        final var formatter = new ForceFormatter(SPAIN);

        assertThrows(ParseException.class, () -> formatter.parse("N"));
    }

    /**
     * Checks that formatted values can be parsed back.
     *
     * @throws ParseException       never happens.
     * @throws UnknownUnitException never happens.
     */
    @Test
    void parse_whenFormattedValue_roundTrips() throws ParseException, UnknownUnitException {
        final var formatter = new ForceFormatter(US);

        for (final var unit : ForceUnit.values()) {
            final var force = formatter.parse(formatter.format(VALUE, unit));

            assertEquals(VALUE, force.getValue().doubleValue(), 0.0);
            assertEquals(unit, force.getUnit());
        }
    }

    /**
     * Checks unit detection, notably that kilonewton is not mistaken for newton.
     */
    @Test
    void findUnit_whenSource_returnsDetectedUnit() {
        final var formatter = new ForceFormatter(SPAIN);

        assertEquals(ForceUnit.NEWTON, formatter.findUnit("5,5 N"));
        assertEquals(ForceUnit.KILONEWTON, formatter.findUnit("5,5 kN"));
        assertEquals(ForceUnit.POUND_FORCE, formatter.findUnit("5,5 lbf"));
        assertEquals(ForceUnit.DYNE, formatter.findUnit("5,5 dyn"));
        assertEquals(ForceUnit.KILOGRAM_FORCE, formatter.findUnit("5,5 kgf"));
        assertEquals(ForceUnit.KILONEWTON, formatter.findUnit("5,5 kN extra"));
        assertEquals(ForceUnit.NEWTON, formatter.findUnit("5,5 N extra"));
        assertNull(formatter.findUnit("5,5 s"));
    }

    /**
     * Checks unit symbols.
     */
    @Test
    void getUnitSymbol_whenUnit_returnsSymbol() {
        final var formatter = new ForceFormatter();

        assertEquals(ForceFormatter.NEWTON, formatter.getUnitSymbol(ForceUnit.NEWTON));
        assertEquals(ForceFormatter.KILONEWTON, formatter.getUnitSymbol(ForceUnit.KILONEWTON));
        assertEquals(ForceFormatter.POUND_FORCE, formatter.getUnitSymbol(ForceUnit.POUND_FORCE));
        assertEquals(ForceFormatter.DYNE, formatter.getUnitSymbol(ForceUnit.DYNE));
        assertEquals(ForceFormatter.KILOGRAM_FORCE, formatter.getUnitSymbol(ForceUnit.KILOGRAM_FORCE));
    }
}
