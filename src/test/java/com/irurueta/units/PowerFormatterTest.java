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
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PowerFormatter}.
 */
class PowerFormatterTest {

    /**
     * Spanish locale, which uses comma as decimal separator.
     */
    private static final Locale SPAIN = Locale.of("es", "ES");

    /**
     * Value used in formatting tests.
     */
    private static final double VALUE = 5.5;

    /**
     * Checks default constructor.
     */
    @Test
    void constructor_whenEmpty_usesDefaultLocale() {
        final var formatter = new PowerFormatter();

        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(UnitLocale.getDefault(), formatter.getUnitSystem());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());
    }

    /**
     * Checks constructor with locale.
     */
    @Test
    void constructor_whenLocale_usesLocale() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals(SPAIN, formatter.getLocale());
        assertEquals(UnitLocale.getFrom(SPAIN), formatter.getUnitSystem());
    }

    /**
     * Checks that a null locale is rejected.
     */
    @Test
    void constructor_whenNullLocale_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PowerFormatter((Locale) null));
    }

    /**
     * Checks copy constructor.
     */
    @Test
    void constructor_whenCopy_copiesLocale() {
        final var formatter = new PowerFormatter(new PowerFormatter(SPAIN));

        assertEquals(SPAIN, formatter.getLocale());
    }

    /**
     * Checks that a null formatter to copy is rejected.
     */
    @Test
    void constructor_whenNullFormatter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PowerFormatter((PowerFormatter) null));
    }

    /**
     * Checks cloning, before and after the internal number format is initialized.
     *
     * @throws CloneNotSupportedException never happens.
     */
    @Test
    void clone_whenCalled_returnsEqualInstance() throws CloneNotSupportedException {
        final var formatter1 = new PowerFormatter();
        final var formatter2 = (PowerFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        assertNotNull(formatter1.format(0.5, PowerUnit.WATT, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (PowerFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    /**
     * Checks equals and hash code.
     */
    @Test
    void equals_whenDifferentLocales_returnsExpectedResult() {
        final var formatter1 = new PowerFormatter(Locale.ENGLISH);
        final var formatter2 = new PowerFormatter(Locale.ENGLISH);
        final var formatter3 = new PowerFormatter(Locale.FRENCH);

        assertEquals(formatter1, formatter1);
        assertEquals(formatter1, formatter2);
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1, formatter3);
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
        assertNotEquals(formatter1, new Object());
        assertNotEquals(null, formatter1);
    }

    /**
     * Checks formatting of numbers, doubles, measurements and string buffers.
     */
    @Test
    void format_whenAnyUnit_returnsValueAndSymbol() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals("5,5 W", formatter.format(new BigDecimal("5.5"), PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.format(VALUE, PowerUnit.KILOWATT));
        assertEquals("5,5 MW", formatter.format(new Power(VALUE, PowerUnit.MEGAWATT)));
        assertEquals("5,5 hp", formatter.format(VALUE, PowerUnit.HORSEPOWER));

        assertEquals("5,5 W", formatter.format(new BigDecimal("5.5"), PowerUnit.WATT, new StringBuffer(),
                new FieldPosition(0)).toString());
        assertEquals("5,5 kW", formatter.format(VALUE, PowerUnit.KILOWATT, new StringBuffer(),
                new FieldPosition(0)).toString());
        assertEquals("5,5 hp", formatter.format(new Power(VALUE, PowerUnit.HORSEPOWER), new StringBuffer(),
                new FieldPosition(0)).toString());
    }

    /**
     * Checks metric formatting picks a unit by magnitude.
     */
    @Test
    void formatAndConvertMetric_whenDifferentMagnitudes_selectsAppropriateUnit() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals("5,5 W", formatter.formatAndConvertMetric(VALUE, PowerUnit.WATT));
        assertEquals("-5,5 W", formatter.formatAndConvertMetric(-VALUE, PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.formatAndConvertMetric(5500.0, PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.formatAndConvertMetric(VALUE, PowerUnit.KILOWATT));
        assertEquals("5,5 MW", formatter.formatAndConvertMetric(5.5e6, PowerUnit.WATT));
        assertEquals("5,5 MW", formatter.formatAndConvertMetric(VALUE, PowerUnit.MEGAWATT));
        assertEquals("5.500 MW", formatter.formatAndConvertMetric(5.5e9, PowerUnit.WATT));
        assertEquals("4,101 kW", formatter.formatAndConvertMetric(5.5, PowerUnit.HORSEPOWER));
    }

    /**
     * Checks imperial formatting always uses horsepower.
     */
    @Test
    void formatAndConvertImperial_whenAnyUnit_usesHorsepower() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals("5,5 hp", formatter.formatAndConvertImperial(VALUE, PowerUnit.HORSEPOWER));
        assertEquals("1,341 hp", formatter.formatAndConvertImperial(1.0, PowerUnit.KILOWATT));
    }

    /**
     * Checks conversion with unit system in all overloads and defaults.
     */
    @Test
    void formatAndConvert_whenUnitSystemGiven_dispatchesToSystem() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals("5,5 kW", formatter.formatAndConvert(new BigDecimal("5500"), PowerUnit.WATT,
                UnitSystem.METRIC));
        assertEquals("5,5 kW", formatter.formatAndConvert(5500.0, PowerUnit.WATT, UnitSystem.METRIC));
        assertEquals("5,5 kW", formatter.formatAndConvert(new Power(5500.0, PowerUnit.WATT), UnitSystem.METRIC));
        assertEquals("5,5 hp", formatter.formatAndConvert(new BigDecimal("5.5"), PowerUnit.HORSEPOWER,
                UnitSystem.IMPERIAL));
        assertEquals("5,5 hp", formatter.formatAndConvert(5.5, PowerUnit.HORSEPOWER, UnitSystem.IMPERIAL));
        assertEquals("5,5 hp", formatter.formatAndConvert(new Power(5.5, PowerUnit.HORSEPOWER), UnitSystem.IMPERIAL));

        // Spain uses metric system by default
        assertEquals("5,5 kW", formatter.formatAndConvert(new BigDecimal("5500"), PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.formatAndConvert(5500.0, PowerUnit.WATT));
        assertEquals("5,5 kW", formatter.formatAndConvert(new Power(5500.0, PowerUnit.WATT)));
    }

    /**
     * Checks unit system detection from a string.
     */
    @Test
    void getUnitSystem_whenSourceProvided_returnsSystemOrNull() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 W"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 kW"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 MW"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem("5,5 hp"));
        assertNull(formatter.getUnitSystem("5,5 s"));
        assertTrue(formatter.isMetricUnit("5,5 kW"));
        assertTrue(formatter.isImperialUnit("5,5 hp"));
        assertTrue(formatter.isValidUnit("5,5 hp"));
        assertFalse(formatter.isValidUnit("5,5 s"));
        assertTrue(formatter.isValidMeasurement("5,5 hp"));
        assertFalse(formatter.isValidMeasurement("W"));
    }

    /**
     * Checks parsing of every unit.
     *
     * @throws ParseException       if parsing fails.
     * @throws UnknownUnitException if a unit is not recognized.
     */
    @Test
    void parse_whenValidSource_returnsPower() throws ParseException, UnknownUnitException {
        final var formatter = new PowerFormatter(SPAIN);

        var p = formatter.parse("5,5 W");
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.WATT, p.getUnit());

        p = formatter.parse("5,5 kW");
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.KILOWATT, p.getUnit());

        p = formatter.parse("5,5 MW");
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.MEGAWATT, p.getUnit());

        p = formatter.parse("5,5 hp");
        assertEquals(5.5, p.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.HORSEPOWER, p.getUnit());
    }

    /**
     * Checks that an unknown unit is rejected.
     */
    @Test
    void parse_whenUnknownUnit_throwsUnknownUnitException() {
        final var formatter = new PowerFormatter(SPAIN);

        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));
    }

    /**
     * Checks that a source without a number is rejected.
     */
    @Test
    void parse_whenNoNumber_throwsParseException() {
        final var formatter = new PowerFormatter(SPAIN);

        assertThrows(ParseException.class, () -> formatter.parse("W"));
    }

    /**
     * Checks unit detection in strings.
     */
    @Test
    void findUnit_whenSourceProvided_returnsUnitOrNull() {
        final var formatter = new PowerFormatter(SPAIN);

        assertEquals(PowerUnit.WATT, formatter.findUnit("5,5 W"));
        assertEquals(PowerUnit.KILOWATT, formatter.findUnit("5,5 kW"));
        assertEquals(PowerUnit.MEGAWATT, formatter.findUnit("5,5 MW"));
        assertEquals(PowerUnit.HORSEPOWER, formatter.findUnit("5,5 hp"));
        assertEquals(PowerUnit.WATT, formatter.findUnit("5,5 W extra"));
        assertEquals(PowerUnit.KILOWATT, formatter.findUnit("5,5 kW extra"));
        assertNull(formatter.findUnit("5,5 s"));
    }

    /**
     * Checks unit symbols.
     */
    @Test
    void getUnitSymbol_whenAnyUnit_returnsSymbol() {
        final var formatter = new PowerFormatter();

        assertEquals(PowerFormatter.WATT, formatter.getUnitSymbol(PowerUnit.WATT));
        assertEquals(PowerFormatter.KILOWATT, formatter.getUnitSymbol(PowerUnit.KILOWATT));
        assertEquals(PowerFormatter.MEGAWATT, formatter.getUnitSymbol(PowerUnit.MEGAWATT));
        assertEquals(PowerFormatter.HORSEPOWER, formatter.getUnitSymbol(PowerUnit.HORSEPOWER));
    }
}
