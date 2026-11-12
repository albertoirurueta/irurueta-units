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
 * Unit tests for {@link ForceUnit}.
 */
class ForceUnitTest {

    /**
     * Checks that every unit maps to its expected unit system.
     */
    @Test
    void getUnitSystem_whenValidUnit_returnsExpectedSystem() {
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.NEWTON));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.KILONEWTON));
        assertEquals(UnitSystem.IMPERIAL, ForceUnit.getUnitSystem(ForceUnit.POUND_FORCE));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.DYNE));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.KILOGRAM_FORCE));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void getUnitSystem_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.getUnitSystem(null));
    }

    /**
     * Checks that metric units are metric and that metric and imperial units cover all units.
     */
    @Test
    void getMetricUnits_whenCalled_returnsOnlyMetricUnits() {
        final var metricUnits = ForceUnit.getMetricUnits();
        final var imperialUnits = ForceUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(ForceUnit.isMetric(metricUnit));
            assertFalse(ForceUnit.isImperial(metricUnit));
        }

        assertEquals(ForceUnit.values().length, metricUnits.length + imperialUnits.length);
    }

    /**
     * Checks that imperial units are imperial and that metric and imperial units cover all units.
     */
    @Test
    void getImperialUnits_whenCalled_returnsOnlyImperialUnits() {
        final var metricUnits = ForceUnit.getMetricUnits();
        final var imperialUnits = ForceUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(ForceUnit.isImperial(imperialUnit));
            assertFalse(ForceUnit.isMetric(imperialUnit));
        }

        assertArrayEquals(new ForceUnit[]{ForceUnit.POUND_FORCE}, imperialUnits);
        assertEquals(ForceUnit.values().length, metricUnits.length + imperialUnits.length);
    }

    /**
     * Checks metric detection for every unit.
     */
    @Test
    void isMetric_whenValidUnit_returnsExpectedResult() {
        assertTrue(ForceUnit.isMetric(ForceUnit.NEWTON));
        assertTrue(ForceUnit.isMetric(ForceUnit.KILONEWTON));
        assertFalse(ForceUnit.isMetric(ForceUnit.POUND_FORCE));
        assertTrue(ForceUnit.isMetric(ForceUnit.DYNE));
        assertTrue(ForceUnit.isMetric(ForceUnit.KILOGRAM_FORCE));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void isMetric_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.isMetric(null));
    }

    /**
     * Checks imperial detection for every unit.
     */
    @Test
    void isImperial_whenValidUnit_returnsExpectedResult() {
        assertFalse(ForceUnit.isImperial(ForceUnit.NEWTON));
        assertFalse(ForceUnit.isImperial(ForceUnit.KILONEWTON));
        assertTrue(ForceUnit.isImperial(ForceUnit.POUND_FORCE));
        assertFalse(ForceUnit.isImperial(ForceUnit.DYNE));
        assertFalse(ForceUnit.isImperial(ForceUnit.KILOGRAM_FORCE));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void isImperial_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.isImperial(null));
    }
}
