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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PowerUnit}.
 */
class PowerUnitTest {

    /**
     * Checks that unit systems are resolved for every unit.
     */
    @Test
    void getUnitSystem_whenAnyUnit_returnsExpectedSystem() {
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.WATT));
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.KILOWATT));
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.MEGAWATT));
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.GIGAWATT));
        assertEquals(UnitSystem.IMPERIAL, PowerUnit.getUnitSystem(PowerUnit.HORSEPOWER));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void getUnitSystem_whenUnitIsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.getUnitSystem(null));
    }

    /**
     * Checks that metric units are all metric and, with imperial ones, cover every unit.
     */
    @Test
    void getMetricUnits_whenCalled_returnsOnlyMetricUnits() {
        final var metricUnits = PowerUnit.getMetricUnits();
        final var imperialUnits = PowerUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(PowerUnit.isMetric(metricUnit));
            assertFalse(PowerUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PowerUnit.values().length);
    }

    /**
     * Checks that imperial units are all imperial and, with metric ones, cover every unit.
     */
    @Test
    void getImperialUnits_whenCalled_returnsOnlyImperialUnits() {
        final var metricUnits = PowerUnit.getMetricUnits();
        final var imperialUnits = PowerUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(PowerUnit.isImperial(imperialUnit));
            assertFalse(PowerUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PowerUnit.values().length);
    }

    /**
     * Checks metric detection for every unit.
     */
    @Test
    void isMetric_whenAnyUnit_returnsExpectedResult() {
        assertTrue(PowerUnit.isMetric(PowerUnit.WATT));
        assertTrue(PowerUnit.isMetric(PowerUnit.KILOWATT));
        assertTrue(PowerUnit.isMetric(PowerUnit.MEGAWATT));
        assertTrue(PowerUnit.isMetric(PowerUnit.GIGAWATT));
        assertFalse(PowerUnit.isMetric(PowerUnit.HORSEPOWER));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void isMetric_whenUnitIsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.isMetric(null));
    }

    /**
     * Checks imperial detection for every unit.
     */
    @Test
    void isImperial_whenAnyUnit_returnsExpectedResult() {
        assertFalse(PowerUnit.isImperial(PowerUnit.WATT));
        assertFalse(PowerUnit.isImperial(PowerUnit.KILOWATT));
        assertFalse(PowerUnit.isImperial(PowerUnit.MEGAWATT));
        assertFalse(PowerUnit.isImperial(PowerUnit.GIGAWATT));
        assertTrue(PowerUnit.isImperial(PowerUnit.HORSEPOWER));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void isImperial_whenUnitIsNull_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.isImperial(null));
    }
}
