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
 * Tests for {@link PowerConverter}.
 */
class PowerConverterTest {

    /**
     * Number of watts in 1 kilowatt.
     */
    private static final double WATTS_PER_KILOWATT = 1000.0;

    /**
     * Number of watts in 1 megawatt.
     */
    private static final double WATTS_PER_MEGAWATT = 1.0e6;

    /**
     * Number of watts in 1 mechanical horsepower.
     */
    private static final double WATTS_PER_HORSEPOWER = 745.69987158227022;

    /**
     * Input value used by tests.
     */
    private static final double VALUE = 3.5;

    /**
     * Allowed error.
     */
    private static final double ERROR = 1e-6;

    /**
     * Checks kilowatt to watt conversions.
     */
    @Test
    void kilowattsToWatts_whenValueProvided_convertsBothWays() {
        assertEquals(VALUE * WATTS_PER_KILOWATT, PowerConverter.kilowattsToWatts(VALUE), ERROR);
        assertEquals(VALUE / WATTS_PER_KILOWATT, PowerConverter.wattsToKilowatts(VALUE), ERROR);
    }

    /**
     * Checks megawatt to watt conversions.
     */
    @Test
    void megawattsToWatts_whenValueProvided_convertsBothWays() {
        assertEquals(VALUE * WATTS_PER_MEGAWATT, PowerConverter.megawattsToWatts(VALUE), ERROR);
        assertEquals(VALUE / WATTS_PER_MEGAWATT, PowerConverter.wattsToMegawatts(VALUE), ERROR);
    }

    /**
     * Checks horsepower to watt conversions.
     */
    @Test
    void horsepowerToWatts_whenValueProvided_convertsBothWays() {
        assertEquals(VALUE * WATTS_PER_HORSEPOWER, PowerConverter.horsepowerToWatts(VALUE), ERROR);
        assertEquals(VALUE / WATTS_PER_HORSEPOWER, PowerConverter.wattsToHorsepower(VALUE), ERROR);
    }

    /**
     * Checks known reference values.
     */
    @Test
    void convert_whenKnownValues_returnsExpectedResult() {
        assertEquals(1000.0, PowerConverter.convert(1.0, PowerUnit.KILOWATT, PowerUnit.WATT), ERROR);
        assertEquals(1000.0, PowerConverter.convert(1.0, PowerUnit.MEGAWATT, PowerUnit.KILOWATT), ERROR);
        assertEquals(745.69987158227022, PowerConverter.convert(1.0, PowerUnit.HORSEPOWER, PowerUnit.WATT), ERROR);
    }

    /**
     * Checks conversions between every pair of units and round trips.
     */
    @Test
    void convert_whenDouble_convertsBetweenAllUnits() {
        for (final var from : PowerUnit.values()) {
            assertEquals(VALUE, PowerConverter.convert(VALUE, from, from), ERROR);
            for (final var to : PowerUnit.values()) {
                final var converted = PowerConverter.convert(VALUE, from, to);
                assertEquals(VALUE, PowerConverter.convert(converted, to, from), ERROR);
            }
        }

        assertEquals(PowerConverter.wattsToKilowatts(VALUE),
                PowerConverter.convert(VALUE, PowerUnit.WATT, PowerUnit.KILOWATT), ERROR);
        assertEquals(PowerConverter.wattsToMegawatts(VALUE),
                PowerConverter.convert(VALUE, PowerUnit.WATT, PowerUnit.MEGAWATT), ERROR);
        assertEquals(PowerConverter.wattsToHorsepower(VALUE),
                PowerConverter.convert(VALUE, PowerUnit.WATT, PowerUnit.HORSEPOWER), ERROR);
    }

    /**
     * Checks conversion of a number value.
     */
    @Test
    void convert_whenNumber_returnsConvertedNumber() {
        final var result = PowerConverter.convert(BigDecimal.valueOf(VALUE), PowerUnit.KILOWATT, PowerUnit.WATT);

        assertEquals(VALUE * WATTS_PER_KILOWATT, result.doubleValue(), ERROR);
    }

    /**
     * Checks conversion of power instances in all overloads.
     */
    @Test
    void convert_whenPowerInstances_storesConvertedResult() {
        final var input = new Power(VALUE, PowerUnit.KILOWATT);
        final var expected = VALUE * WATTS_PER_KILOWATT;

        final var output = new Power(0.0, PowerUnit.WATT);
        PowerConverter.convert(input, output);
        assertEquals(expected, output.getValue().doubleValue(), ERROR);
        assertEquals(PowerUnit.WATT, output.getUnit());

        final var result = PowerConverter.convertAndReturnNew(input, PowerUnit.WATT);
        assertEquals(expected, result.getValue().doubleValue(), ERROR);
        assertEquals(PowerUnit.WATT, result.getUnit());

        final var updated = new Power(VALUE, PowerUnit.KILOWATT);
        PowerConverter.convert(updated, PowerUnit.WATT);
        assertEquals(expected, updated.getValue().doubleValue(), ERROR);
        assertEquals(PowerUnit.WATT, updated.getUnit());

        final var target = new Power(0.0, PowerUnit.MEGAWATT);
        PowerConverter.convert(input, PowerUnit.WATT, target);
        assertEquals(expected, target.getValue().doubleValue(), ERROR);
        assertEquals(PowerUnit.WATT, target.getUnit());
    }

    /**
     * Checks that null units are rejected.
     */
    @Test
    void convert_whenUnitIsNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> PowerConverter.convert(VALUE, null, PowerUnit.WATT));
        assertThrows(NullPointerException.class, () -> PowerConverter.convert(VALUE, PowerUnit.WATT, null));
    }
}
