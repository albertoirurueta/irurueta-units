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
 * Unit tests for {@link EnergyUnit}.
 */
class EnergyUnitTest {

    /**
     * Checks that every unit maps to its expected unit system and that a null unit is rejected.
     */
    @Test
    void getUnitSystem_whenUnit_returnsExpectedSystemOrThrowsWhenNull() {
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.JOULE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOJOULE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.CALORIE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOCALORIE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOWATT_HOUR));
        assertEquals(UnitSystem.IMPERIAL, EnergyUnit.getUnitSystem(EnergyUnit.BTU));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EnergyUnit.getUnitSystem(null));
    }

    /**
     * Checks that metric units are metric and that metric and imperial units cover all units.
     */
    @Test
    void getMetricUnits_whenCalled_returnsOnlyMetricUnits() {
        final var metricUnits = EnergyUnit.getMetricUnits();
        final var imperialUnits = EnergyUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(EnergyUnit.isMetric(metricUnit));
            assertFalse(EnergyUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, EnergyUnit.values().length);
    }

    /**
     * Checks that imperial units are imperial and that metric and imperial units cover all units.
     */
    @Test
    void getImperialUnits_whenCalled_returnsOnlyImperialUnits() {
        final var metricUnits = EnergyUnit.getMetricUnits();
        final var imperialUnits = EnergyUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(EnergyUnit.isImperial(imperialUnit));
            assertFalse(EnergyUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, EnergyUnit.values().length);
    }

    /**
     * Checks metric detection for every unit and that a null unit is rejected.
     */
    @Test
    void isMetric_whenUnit_returnsExpectedResultOrThrowsWhenNull() {
        assertTrue(EnergyUnit.isMetric(EnergyUnit.JOULE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOJOULE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.CALORIE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOCALORIE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOWATT_HOUR));
        assertFalse(EnergyUnit.isMetric(EnergyUnit.BTU));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EnergyUnit.isMetric(null));
    }

    /**
     * Checks imperial detection for every unit and that a null unit is rejected.
     */
    @Test
    void isImperial_whenUnit_returnsExpectedResultOrThrowsWhenNull() {
        assertFalse(EnergyUnit.isImperial(EnergyUnit.JOULE));
        assertFalse(EnergyUnit.isImperial(EnergyUnit.KILOJOULE));
        assertFalse(EnergyUnit.isImperial(EnergyUnit.CALORIE));
        assertFalse(EnergyUnit.isImperial(EnergyUnit.KILOCALORIE));
        assertFalse(EnergyUnit.isImperial(EnergyUnit.KILOWATT_HOUR));
        assertTrue(EnergyUnit.isImperial(EnergyUnit.BTU));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EnergyUnit.isImperial(null));
    }
}
