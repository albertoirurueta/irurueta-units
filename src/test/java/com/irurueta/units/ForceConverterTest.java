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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link ForceConverter}.
 */
class ForceConverterTest {

    /**
     * Number of newtons in 1 kilonewton.
     */
    private static final double NEWTONS_PER_KILONEWTON = 1000.0;

    /**
     * Number of newtons in 1 pound-force.
     */
    private static final double NEWTONS_PER_POUND_FORCE = 4.4482216152605;

    /**
     * Number of newtons in 1 dyne.
     */
    private static final double NEWTONS_PER_DYNE = 1.0e-5;

    /**
     * Number of newtons in 1 kilogram-force.
     */
    private static final double NEWTONS_PER_KILOGRAM_FORCE = 9.80665;

    /**
     * Value used in conversions.
     */
    private static final double VALUE = 12.5;

    /**
     * Tolerance used when comparing floating point values.
     */
    private static final double ERROR = 1e-9;

    /**
     * Relative tolerance used when checking round trip conversions.
     */
    private static final double ROUND_TRIP_ERROR = 1e-12;

    /**
     * Checks that package-private constants match the expected conversion factors.
     */
    @Test
    void constants_whenRead_matchExpectedFactors() {
        assertEquals(NEWTONS_PER_KILONEWTON, ForceConverter.NEWTONS_PER_KILONEWTON, 0.0);
        assertEquals(NEWTONS_PER_POUND_FORCE, ForceConverter.NEWTONS_PER_POUND_FORCE, 0.0);
        assertEquals(NEWTONS_PER_DYNE, ForceConverter.NEWTONS_PER_DYNE, 0.0);
        assertEquals(NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.NEWTONS_PER_KILOGRAM_FORCE, 0.0);
    }

    /**
     * Checks newton and kilonewton conversions.
     */
    @Test
    void newtonToKilonewton_whenKnownValue_returnsExpectedResult() {
        assertEquals(VALUE / NEWTONS_PER_KILONEWTON, ForceConverter.newtonToKilonewton(VALUE), ERROR);
        assertEquals(VALUE * NEWTONS_PER_KILONEWTON, ForceConverter.kilonewtonToNewton(VALUE), ERROR);
        assertEquals(VALUE, ForceConverter.kilonewtonToNewton(ForceConverter.newtonToKilonewton(VALUE)), ERROR);
    }

    /**
     * Checks newton and pound-force conversions.
     */
    @Test
    void newtonToPoundForce_whenKnownValue_returnsExpectedResult() {
        assertEquals(VALUE / NEWTONS_PER_POUND_FORCE, ForceConverter.newtonToPoundForce(VALUE), ERROR);
        assertEquals(VALUE * NEWTONS_PER_POUND_FORCE, ForceConverter.poundForceToNewton(VALUE), ERROR);
        assertEquals(VALUE, ForceConverter.poundForceToNewton(ForceConverter.newtonToPoundForce(VALUE)), ERROR);
    }

    /**
     * Checks newton and dyne conversions.
     */
    @Test
    void newtonToDyne_whenKnownValue_returnsExpectedResult() {
        assertEquals(VALUE / NEWTONS_PER_DYNE, ForceConverter.newtonToDyne(VALUE), ERROR);
        assertEquals(VALUE * NEWTONS_PER_DYNE, ForceConverter.dyneToNewton(VALUE), ERROR);
        assertEquals(VALUE, ForceConverter.dyneToNewton(ForceConverter.newtonToDyne(VALUE)), ERROR);
    }

    /**
     * Checks newton and kilogram-force conversions.
     */
    @Test
    void newtonToKilogramForce_whenKnownValue_returnsExpectedResult() {
        assertEquals(VALUE / NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.newtonToKilogramForce(VALUE), ERROR);
        assertEquals(VALUE * NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.kilogramForceToNewton(VALUE), ERROR);
        assertEquals(VALUE,
                ForceConverter.kilogramForceToNewton(ForceConverter.newtonToKilogramForce(VALUE)), ERROR);
    }

    /**
     * Checks well known equivalences between units.
     */
    @Test
    void convert_whenWellKnownEquivalences_returnsExpectedValues() {
        assertEquals(1.0, ForceConverter.convert(1.0, ForceUnit.KILOGRAM_FORCE, ForceUnit.NEWTON)
                / NEWTONS_PER_KILOGRAM_FORCE, ERROR);
        assertEquals(1.0, ForceConverter.convert(1.0 / NEWTONS_PER_DYNE, ForceUnit.DYNE, ForceUnit.NEWTON), ERROR);
        assertEquals(1.0, ForceConverter.convert(NEWTONS_PER_KILONEWTON, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                ERROR);
        assertEquals(1.0, ForceConverter.convert(NEWTONS_PER_POUND_FORCE, ForceUnit.NEWTON, ForceUnit.POUND_FORCE),
                ERROR);
    }

    /**
     * Checks that converting between every pair of units matches going through newtons,
     * and that a unit converted to itself is unchanged.
     */
    @Test
    void convert_whenDoubleAndAllUnitPairs_matchesConversionThroughNewtons() {
        for (final var input : ForceUnit.values()) {
            for (final var output : ForceUnit.values()) {
                final var newtons = ForceConverter.convert(VALUE, input, ForceUnit.NEWTON);
                final var expected = ForceConverter.convert(newtons, ForceUnit.NEWTON, output);

                final var result = ForceConverter.convert(VALUE, input, output);

                assertEquals(expected, result, Math.abs(expected) * ERROR + ERROR);
                if (input == output) {
                    assertEquals(VALUE, result, ERROR);
                }
            }
        }
    }

    /**
     * Checks that converting back and forth recovers the original value.
     */
    @Test
    void convert_whenRoundTrip_recoversOriginalValue() {
        for (final var input : ForceUnit.values()) {
            for (final var output : ForceUnit.values()) {
                final var converted = ForceConverter.convert(VALUE, input, output);
                final var back = ForceConverter.convert(converted, output, input);

                assertEquals(VALUE, back, VALUE * ROUND_TRIP_ERROR);
            }
        }
    }

    /**
     * Checks conversion of a number value.
     */
    @Test
    void convert_whenNumber_returnsExpectedValue() {
        final var result = ForceConverter.convert(BigDecimal.valueOf(VALUE), ForceUnit.KILONEWTON,
                ForceUnit.NEWTON);

        assertEquals(VALUE * NEWTONS_PER_KILONEWTON, result.doubleValue(), ERROR);
    }

    /**
     * Checks that a null number is rejected.
     */
    @Test
    void convert_whenNullNumber_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> ForceConverter.convert((Number) null, ForceUnit.NEWTON, ForceUnit.KILONEWTON));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void convert_whenNullUnit_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ForceConverter.convert(VALUE, null, ForceUnit.NEWTON));
        assertThrows(NullPointerException.class, () -> ForceConverter.convert(VALUE, ForceUnit.NEWTON, null));
    }

    /**
     * Checks conversion of a force storing the result in another instance.
     */
    @Test
    void convert_whenForceAndResult_storesResultInProvidedInstance() {
        final var input = new Force(VALUE, ForceUnit.NEWTON);
        final var output = new Force();

        ForceConverter.convert(input, ForceUnit.POUND_FORCE, output);

        assertEquals(VALUE, input.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, input.getUnit());
        assertEquals(ForceUnit.POUND_FORCE, output.getUnit());
        assertEquals(ForceConverter.convert(VALUE, ForceUnit.NEWTON, ForceUnit.POUND_FORCE),
                output.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks in-place conversion of a force.
     */
    @Test
    void convert_whenForceAndUnit_updatesInstance() {
        final var force = new Force(VALUE, ForceUnit.NEWTON);

        ForceConverter.convert(force, ForceUnit.DYNE);

        assertEquals(ForceUnit.DYNE, force.getUnit());
        assertEquals(ForceConverter.convert(VALUE, ForceUnit.NEWTON, ForceUnit.DYNE),
                force.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks conversion of a force returning a new instance.
     */
    @Test
    void convertAndReturnNew_whenForce_returnsNewConvertedForce() {
        final var input = new Force(VALUE, ForceUnit.NEWTON);

        final var output = ForceConverter.convertAndReturnNew(input, ForceUnit.KILOGRAM_FORCE);

        assertEquals(VALUE, input.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, input.getUnit());
        assertEquals(ForceUnit.KILOGRAM_FORCE, output.getUnit());
        assertEquals(ForceConverter.convert(VALUE, ForceUnit.NEWTON, ForceUnit.KILOGRAM_FORCE),
                output.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks conversion of a force to the unit of an output instance.
     */
    @Test
    void convert_whenForceAndOutputForce_usesOutputUnit() {
        final var input = new Force(VALUE, ForceUnit.NEWTON);
        final var output = new Force();
        output.setUnit(ForceUnit.KILONEWTON);

        ForceConverter.convert(input, output);

        assertEquals(ForceUnit.NEWTON, input.getUnit());
        assertEquals(ForceUnit.KILONEWTON, output.getUnit());
        assertEquals(ForceConverter.convert(VALUE, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                output.getValue().doubleValue(), 0.0);
    }
}
