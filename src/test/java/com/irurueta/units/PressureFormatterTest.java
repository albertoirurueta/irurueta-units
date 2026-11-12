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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link PressureFormatter}.
 */
class PressureFormatterTest {

    /**
     * Value used when formatting without conversion.
     */
    private static final double VALUE = 5.50;

    /**
     * Maximum fraction digits used to get deterministic output.
     */
    private static final int FRACTION_DIGITS = 2;

    /**
     * Spanish locale (metric, decimal comma).
     */
    private static final Locale SPAIN = Locale.forLanguageTag("es-ES");

    /**
     * US locale (imperial, decimal point).
     */
    private static final Locale US = Locale.forLanguageTag("en-US");

    /**
     * Checks the default constructor.
     */
    @Test
    void constructor_whenEmpty_usesDefaultLocale() {
        final var formatter = new PressureFormatter();

        assertEquals(Locale.getDefault(), formatter.getLocale());
        assertEquals(NumberFormat.getInstance().getMaximumFractionDigits(), formatter.getMaximumFractionDigits());
        assertEquals(UnitLocale.getDefault(), formatter.getUnitSystem());
        assertEquals(MeasureFormatter.DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN, formatter.getValueAndUnitFormatPattern());
    }

    /**
     * Checks the constructor with locale.
     */
    @Test
    void constructor_whenLocale_usesLocale() {
        final var formatter = new PressureFormatter(SPAIN);

        assertEquals(SPAIN, formatter.getLocale());
        assertEquals(NumberFormat.getInstance(SPAIN).getMaximumFractionDigits(),
                formatter.getMaximumFractionDigits());
        assertEquals(UnitLocale.getFrom(SPAIN), formatter.getUnitSystem());
    }

    /**
     * Checks that a null locale is rejected.
     */
    @Test
    void constructor_whenNullLocale_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new PressureFormatter((Locale) null));
    }

    /**
     * Checks the copy constructor.
     */
    @Test
    void constructor_whenFormatter_copiesLocale() {
        final var formatter = new PressureFormatter(SPAIN);

        final var copy = new PressureFormatter(formatter);

        assertEquals(SPAIN, copy.getLocale());
        assertEquals(formatter, copy);
    }

    /**
     * Checks that a null formatter is rejected by the copy constructor.
     */
    @Test
    void constructor_whenNullFormatter_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PressureFormatter((PressureFormatter) null));
    }

    /**
     * Checks cloning before and after the internal number format is initialized.
     */
    @Test
    void clone_whenCalled_returnsEqualButDifferentInstance() throws CloneNotSupportedException {
        final var formatter1 = new PressureFormatter();
        final var formatter2 = (PressureFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter2);
        assertEquals(formatter1, formatter2);

        assertNotNull(formatter1.format(0.5, PressureUnit.PASCAL, new StringBuffer(), new FieldPosition(0)));
        final var formatter3 = (PressureFormatter) formatter1.clone();

        assertNotSame(formatter1, formatter3);
        assertEquals(formatter1, formatter3);
    }

    /**
     * Checks equality and hash code.
     */
    @Test
    void equals_whenSameOrDifferentLocale_returnsExpectedResult() {
        final var formatter1 = new PressureFormatter(Locale.ENGLISH);
        final var formatter2 = new PressureFormatter(Locale.ENGLISH);
        final var formatter3 = new PressureFormatter(Locale.FRENCH);

        assertEquals(formatter1, formatter2);
        assertEquals(formatter1.hashCode(), formatter2.hashCode());
        assertNotEquals(formatter1, formatter3);
        assertNotEquals(formatter1.hashCode(), formatter3.hashCode());
        assertNotEquals(formatter1, new Object());
        assertNotEquals(null, formatter1);
    }

    /**
     * Checks formatting of every unit through all format overloads.
     */
    @Test
    void format_whenAnyOverload_returnsValueAndSymbol() {
        final var formatter = new PressureFormatter(SPAIN);
        final var expected = new String[]{"5,5 Pa", "5,5 kPa", "5,5 bar", "5,5 atm", "5,5 psi"};
        final var units = PressureUnit.values();

        for (var i = 0; i < units.length; i++) {
            final var unit = units[i];
            assertEquals(expected[i], formatter.format(BigDecimal.valueOf(VALUE), unit));
            assertEquals(expected[i], formatter.format(VALUE, unit));
            assertEquals(expected[i], formatter.format(new Pressure(VALUE, unit)));
            assertEquals(expected[i], formatter.format(BigDecimal.valueOf(VALUE), unit, new StringBuffer(),
                    new FieldPosition(0)).toString());
            assertEquals(expected[i], formatter.format(VALUE, unit, new StringBuffer(),
                    new FieldPosition(0)).toString());
            assertEquals(expected[i], formatter.format(new Pressure(VALUE, unit), new StringBuffer(),
                    new FieldPosition(0)).toString());
        }
    }

    /**
     * Checks metric conversion thresholds.
     */
    @Test
    void formatAndConvertMetric_whenDifferentMagnitudes_picksPascalKilopascalOrBar() {
        final var formatter = new PressureFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("500 Pa", formatter.formatAndConvertMetric(500.0, PressureUnit.PASCAL));
        assertEquals("1 kPa", formatter.formatAndConvertMetric(1000.0, PressureUnit.PASCAL));
        assertEquals("50 kPa", formatter.formatAndConvertMetric(0.5, PressureUnit.BAR));
        assertEquals("1 bar", formatter.formatAndConvertMetric(100.0, PressureUnit.KILOPASCAL));
        assertEquals("1,01 bar", formatter.formatAndConvertMetric(1.0, PressureUnit.ATMOSPHERE));
        assertEquals("6,89 kPa", formatter.formatAndConvertMetric(1.0, PressureUnit.PSI));
        assertEquals("-500 Pa", formatter.formatAndConvertMetric(-500.0, PressureUnit.PASCAL));
    }

    /**
     * Checks that imperial conversion always uses psi.
     */
    @Test
    void formatAndConvertImperial_whenAnyUnit_returnsPsi() {
        final var formatter = new PressureFormatter(US);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("1 psi", formatter.formatAndConvertImperial(1.0, PressureUnit.PSI));
        assertEquals("14.7 psi", formatter.formatAndConvertImperial(1.0, PressureUnit.ATMOSPHERE));
        assertEquals("14.5 psi", formatter.formatAndConvertImperial(1.0, PressureUnit.BAR));
        assertEquals("0.15 psi", formatter.formatAndConvertImperial(1.0, PressureUnit.KILOPASCAL));
    }

    /**
     * Checks conversion with an explicit unit system, for every overload.
     */
    @Test
    void formatAndConvert_whenUnitSystem_usesThatSystem() {
        final var formatter = new PressureFormatter(SPAIN);
        formatter.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("50 kPa", formatter.formatAndConvert(BigDecimal.valueOf(0.5), PressureUnit.BAR,
                UnitSystem.METRIC));
        assertEquals("50 kPa", formatter.formatAndConvert(0.5, PressureUnit.BAR, UnitSystem.METRIC));
        assertEquals("50 kPa", formatter.formatAndConvert(new Pressure(0.5, PressureUnit.BAR), UnitSystem.METRIC));

        assertEquals("7,25 psi", formatter.formatAndConvert(BigDecimal.valueOf(0.5), PressureUnit.BAR,
                UnitSystem.IMPERIAL));
        assertEquals("7,25 psi", formatter.formatAndConvert(0.5, PressureUnit.BAR, UnitSystem.IMPERIAL));
        assertEquals("7,25 psi", formatter.formatAndConvert(new Pressure(0.5, PressureUnit.BAR),
                UnitSystem.IMPERIAL));
    }

    /**
     * Checks conversion using the unit system of the formatter locale.
     */
    @Test
    void formatAndConvert_whenNoUnitSystem_usesLocaleSystem() {
        final var metric = new PressureFormatter(SPAIN);
        metric.setMaximumFractionDigits(FRACTION_DIGITS);
        final var imperial = new PressureFormatter(US);
        imperial.setMaximumFractionDigits(FRACTION_DIGITS);

        assertEquals("50 kPa", metric.formatAndConvert(BigDecimal.valueOf(0.5), PressureUnit.BAR));
        assertEquals("50 kPa", metric.formatAndConvert(0.5, PressureUnit.BAR));
        assertEquals("50 kPa", metric.formatAndConvert(new Pressure(0.5, PressureUnit.BAR)));

        assertEquals("7.25 psi", imperial.formatAndConvert(BigDecimal.valueOf(0.5), PressureUnit.BAR));
        assertEquals("7.25 psi", imperial.formatAndConvert(0.5, PressureUnit.BAR));
        assertEquals("7.25 psi", imperial.formatAndConvert(new Pressure(0.5, PressureUnit.BAR)));
    }

    /**
     * Checks that validity checks recognize every unit symbol.
     */
    @Test
    void isValidUnit_whenKnownSymbol_returnsTrue() {
        final var formatter = new PressureFormatter();

        for (final var symbol : new String[]{"Pa", "kPa", "bar", "atm", "psi"}) {
            assertTrue(formatter.isValidUnit(symbol));
            assertTrue(formatter.isValidUnit(symbol + " "));
        }
        assertFalse(formatter.isValidUnit("m"));
    }

    /**
     * Checks measurement validity, metric and imperial detection.
     */
    @Test
    void isValidMeasurement_whenKnownOrUnknownUnit_returnsExpectedResult() {
        final var formatter = new PressureFormatter(SPAIN);

        for (final var text : new String[]{"5,5 Pa", "5,5 kPa", "5,5 bar", "5,5 atm", "5,5 psi"}) {
            assertTrue(formatter.isValidMeasurement(text));
        }
        assertFalse(formatter.isValidMeasurement("5,5 s"));
        assertFalse(formatter.isValidMeasurement("m"));
    }

    /**
     * Checks metric unit detection in strings.
     */
    @Test
    void isMetricUnit_whenSource_returnsExpectedResult() {
        final var formatter = new PressureFormatter(SPAIN);

        assertTrue(formatter.isMetricUnit("5,5 Pa"));
        assertTrue(formatter.isMetricUnit("5,5 kPa"));
        assertTrue(formatter.isMetricUnit("5,5 bar"));
        assertTrue(formatter.isMetricUnit("5,5 atm"));
        assertFalse(formatter.isMetricUnit("5,5 psi"));
        assertFalse(formatter.isMetricUnit("5,5 s"));
    }

    /**
     * Checks imperial unit detection in strings.
     */
    @Test
    void isImperialUnit_whenSource_returnsExpectedResult() {
        final var formatter = new PressureFormatter(SPAIN);

        assertFalse(formatter.isImperialUnit("5,5 Pa"));
        assertFalse(formatter.isImperialUnit("5,5 kPa"));
        assertFalse(formatter.isImperialUnit("5,5 bar"));
        assertFalse(formatter.isImperialUnit("5,5 atm"));
        assertTrue(formatter.isImperialUnit("5,5 psi"));
        assertFalse(formatter.isImperialUnit("5,5 s"));
    }

    /**
     * Checks unit system detection from strings.
     */
    @Test
    void getUnitSystem_whenSource_returnsDetectedSystem() {
        final var formatter = new PressureFormatter(SPAIN);

        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 Pa"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 kPa"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 bar"));
        assertEquals(UnitSystem.METRIC, formatter.getUnitSystem("5,5 atm"));
        assertEquals(UnitSystem.IMPERIAL, formatter.getUnitSystem("5,5 psi"));
        assertNull(formatter.getUnitSystem("5,5 s"));
    }

    /**
     * Checks that the formatter locale determines its default unit system.
     */
    @Test
    void getUnitSystem_whenNoSource_returnsLocaleSystem() {
        assertEquals(UnitSystem.METRIC, new PressureFormatter(SPAIN).getUnitSystem());
        assertEquals(UnitSystem.IMPERIAL, new PressureFormatter(US).getUnitSystem());
    }

    /**
     * Checks parsing of every unit, including kPa not being mistaken for Pa.
     */
    @Test
    void parse_whenKnownUnit_returnsPressure() throws ParseException, UnknownUnitException {
        final var formatter = new PressureFormatter(SPAIN);
        final var texts = new String[]{"5,5 Pa", "5,5 kPa", "5,5 bar", "5,5 atm", "5,5 psi"};
        final var units = PressureUnit.values();

        for (var i = 0; i < units.length; i++) {
            final var p = formatter.parse(texts[i]);
            assertEquals(5.5, p.getValue().doubleValue(), 0.0);
            assertEquals(units[i], p.getUnit());
        }
    }

    /**
     * Checks that an unknown unit fails parsing.
     */
    @Test
    void parse_whenUnknownUnit_throwsUnknownUnitException() {
        final var formatter = new PressureFormatter(SPAIN);

        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 s"));
    }

    /**
     * Checks that a string without a number fails parsing.
     */
    @Test
    void parse_whenNoNumber_throwsParseException() {
        final var formatter = new PressureFormatter(SPAIN);

        assertThrows(ParseException.class, () -> formatter.parse("Pa"));
    }

    /**
     * Checks unit detection in strings.
     */
    @Test
    void findUnit_whenSource_returnsDetectedUnit() {
        final var formatter = new PressureFormatter(SPAIN);

        assertEquals(PressureUnit.PASCAL, formatter.findUnit("5,5 Pa"));
        assertEquals(PressureUnit.KILOPASCAL, formatter.findUnit("5,5 kPa"));
        assertEquals(PressureUnit.KILOPASCAL, formatter.findUnit("5,5 kPa extra"));
        assertEquals(PressureUnit.BAR, formatter.findUnit("5,5 bar"));
        assertEquals(PressureUnit.ATMOSPHERE, formatter.findUnit("5,5 atm"));
        assertEquals(PressureUnit.PSI, formatter.findUnit("5,5 psi"));
        assertNull(formatter.findUnit("5,5 s"));
    }

    /**
     * Checks that unsupported prefixed units are rejected instead of being parsed as a supported unit.
     */
    @Test
    void findUnit_whenUnsupportedPrefix_returnsNullAndParseFails() {
        final var formatter = new PressureFormatter(SPAIN);

        assertNull(formatter.findUnit("5,5 hPa"));
        assertNull(formatter.findUnit("5,5 MPa"));
        assertEquals(PressureUnit.PASCAL, formatter.findUnit("1 hPa = 100 Pa"));
        assertEquals(PressureUnit.PASCAL, formatter.findUnit("Pa"));
        assertNull(formatter.findUnit("5,5 Pax"));
        assertThrows(UnknownUnitException.class, () -> formatter.parse("5,5 hPa"));
    }

    /**
     * Checks unit symbols.
     */
    @Test
    void getUnitSymbol_whenAnyUnit_returnsSymbol() {
        final var formatter = new PressureFormatter();

        assertEquals(PressureFormatter.PASCAL, formatter.getUnitSymbol(PressureUnit.PASCAL));
        assertEquals(PressureFormatter.KILOPASCAL, formatter.getUnitSymbol(PressureUnit.KILOPASCAL));
        assertEquals(PressureFormatter.BAR, formatter.getUnitSymbol(PressureUnit.BAR));
        assertEquals(PressureFormatter.ATMOSPHERE, formatter.getUnitSymbol(PressureUnit.ATMOSPHERE));
        assertEquals(PressureFormatter.PSI, formatter.getUnitSymbol(PressureUnit.PSI));
        assertEquals("Pa", PressureFormatter.PASCAL);
        assertEquals("kPa", PressureFormatter.KILOPASCAL);
        assertEquals("bar", PressureFormatter.BAR);
        assertEquals("atm", PressureFormatter.ATMOSPHERE);
        assertEquals("psi", PressureFormatter.PSI);
    }
}
