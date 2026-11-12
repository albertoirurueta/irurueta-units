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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for {@link PressureConverter}.
 */
class PressureConverterTest {

    /**
     * Pascals in a kilopascal.
     */
    private static final double PASCALS_PER_KILOPASCAL = 1000.0;

    /**
     * Pascals in a bar.
     */
    private static final double PASCALS_PER_BAR = 100000.0;

    /**
     * Pascals in a standard atmosphere.
     */
    private static final double PASCALS_PER_ATMOSPHERE = 101325.0;

    /**
     * Pascals in a pound per square inch.
     */
    private static final double PASCALS_PER_PSI = 6894.757293168;

    /**
     * Arbitrary input value.
     */
    private static final double VALUE = 3.75;

    /**
     * Allowed error in comparisons.
     */
    private static final double ERROR = 1e-6;

    /**
     * Checks pascal to/from kilopascal conversions.
     */
    @Test
    void kilopascalsToPascals_whenValue_returnsExpectedResult() {
        assertEquals(VALUE * PASCALS_PER_KILOPASCAL, PressureConverter.kilopascalsToPascals(VALUE), ERROR);
        assertEquals(VALUE / PASCALS_PER_KILOPASCAL, PressureConverter.pascalsToKilopascals(VALUE), ERROR);
    }

    /**
     * Checks pascal to/from bar conversions.
     */
    @Test
    void barsToPascals_whenValue_returnsExpectedResult() {
        assertEquals(VALUE * PASCALS_PER_BAR, PressureConverter.barsToPascals(VALUE), ERROR);
        assertEquals(VALUE / PASCALS_PER_BAR, PressureConverter.pascalsToBars(VALUE), ERROR);
    }

    /**
     * Checks pascal to/from atmosphere conversions.
     */
    @Test
    void atmospheresToPascals_whenValue_returnsExpectedResult() {
        assertEquals(VALUE * PASCALS_PER_ATMOSPHERE, PressureConverter.atmospheresToPascals(VALUE), ERROR);
        assertEquals(VALUE / PASCALS_PER_ATMOSPHERE, PressureConverter.pascalsToAtmospheres(VALUE), ERROR);
    }

    /**
     * Checks pascal to/from psi conversions.
     */
    @Test
    void psiToPascals_whenValue_returnsExpectedResult() {
        assertEquals(VALUE * PASCALS_PER_PSI, PressureConverter.psiToPascals(VALUE), ERROR);
        assertEquals(VALUE / PASCALS_PER_PSI, PressureConverter.pascalsToPsi(VALUE), ERROR);
    }

    /**
     * Checks known reference values.
     */
    @Test
    void convert_whenKnownValues_returnsExpectedResult() {
        assertEquals(1.0, PressureConverter.convert(1.0, PressureUnit.ATMOSPHERE, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(1.01325, PressureConverter.convert(1.0, PressureUnit.ATMOSPHERE, PressureUnit.BAR), ERROR);
        assertEquals(101.325, PressureConverter.convert(1.0, PressureUnit.ATMOSPHERE, PressureUnit.KILOPASCAL),
                ERROR);
        assertEquals(14.69594878, PressureConverter.convert(1.0, PressureUnit.ATMOSPHERE, PressureUnit.PSI), 1e-6);
        assertEquals(100.0, PressureConverter.convert(1.0, PressureUnit.BAR, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(1000.0, PressureConverter.convert(1.0, PressureUnit.KILOPASCAL, PressureUnit.PASCAL), ERROR);
    }

    /**
     * Checks conversions between every pair of units, going through pascals.
     */
    @Test
    void convert_whenDoubleAndAnyUnitPair_returnsExpectedResult() {
        for (final var from : PressureUnit.values()) {
            final var pascals = PressureConverter.convert(VALUE, from, PressureUnit.PASCAL);
            for (final var to : PressureUnit.values()) {
                final var expected = PressureConverter.convert(pascals, PressureUnit.PASCAL, to);
                assertEquals(expected, PressureConverter.convert(VALUE, from, to), ERROR);
            }
        }
    }

    /**
     * Checks that converting back and forth recovers the original value.
     */
    @Test
    void convert_whenRoundTrip_returnsOriginalValue() {
        for (final var from : PressureUnit.values()) {
            for (final var to : PressureUnit.values()) {
                final var converted = PressureConverter.convert(VALUE, from, to);
                assertEquals(VALUE, PressureConverter.convert(converted, to, from), ERROR);
            }
        }
    }

    /**
     * Checks that conversion with a null unit fails.
     */
    @Test
    void convert_whenNullUnit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PressureConverter.convert(VALUE, null, PressureUnit.BAR));
        assertThrows(NullPointerException.class, () -> PressureConverter.convert(VALUE, PressureUnit.BAR, null));
    }

    /**
     * Checks conversion of a Number.
     */
    @Test
    void convert_whenNumber_returnsExpectedResult() {
        final var result = PressureConverter.convert(BigDecimal.valueOf(VALUE), PressureUnit.BAR,
                PressureUnit.KILOPASCAL);

        assertEquals(VALUE * 100.0, result.doubleValue(), ERROR);
    }

    /**
     * Checks that a null Number fails.
     */
    @Test
    void convert_whenNullNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> PressureConverter.convert((Number) null, PressureUnit.BAR, PressureUnit.PASCAL));
    }

    /**
     * Checks conversion storing the result in a provided instance.
     */
    @Test
    void convert_whenInputAndResultInstances_storesResultAndKeepsInput() {
        final var input = new Pressure(VALUE, PressureUnit.BAR);
        final var output = new Pressure();

        PressureConverter.convert(input, PressureUnit.PSI, output);

        assertEquals(VALUE, input.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.BAR, input.getUnit());
        assertEquals(PressureUnit.PSI, output.getUnit());
        assertEquals(PressureConverter.convert(VALUE, PressureUnit.BAR, PressureUnit.PSI),
                output.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks in-place conversion.
     */
    @Test
    void convert_whenPressureAndUnit_updatesInstance() {
        final var pressure = new Pressure(VALUE, PressureUnit.BAR);

        PressureConverter.convert(pressure, PressureUnit.KILOPASCAL);

        assertEquals(PressureUnit.KILOPASCAL, pressure.getUnit());
        assertEquals(VALUE * 100.0, pressure.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks conversion returning a new instance.
     */
    @Test
    void convertAndReturnNew_whenPressure_returnsNewConvertedInstance() {
        final var input = new Pressure(VALUE, PressureUnit.ATMOSPHERE);

        final var output = PressureConverter.convertAndReturnNew(input, PressureUnit.PASCAL);

        assertEquals(PressureUnit.ATMOSPHERE, input.getUnit());
        assertEquals(PressureUnit.PASCAL, output.getUnit());
        assertEquals(VALUE * PASCALS_PER_ATMOSPHERE, output.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks conversion using the unit of the output instance.
     */
    @Test
    void convert_whenOutputInstance_usesOutputUnit() {
        final var input = new Pressure(VALUE, PressureUnit.PSI);
        final var output = new Pressure(0.0, PressureUnit.PASCAL);

        PressureConverter.convert(input, output);

        assertEquals(PressureUnit.PASCAL, output.getUnit());
        assertEquals(VALUE * PASCALS_PER_PSI, output.getValue().doubleValue(), ERROR);
    }
}
