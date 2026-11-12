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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link PressureUnit}.
 */
class PressureUnitTest {

    /**
     * Checks that every unit maps to the expected unit system.
     */
    @Test
    void getUnitSystem_whenValidUnit_returnsExpectedSystem() {
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.PASCAL));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.KILOPASCAL));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.BAR));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.ATMOSPHERE));
        assertEquals(UnitSystem.IMPERIAL, PressureUnit.getUnitSystem(PressureUnit.PSI));
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void getUnitSystem_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.getUnitSystem(null));
    }

    /**
     * Checks that metric units are metric and that metric and imperial units cover all values.
     */
    @Test
    void getMetricUnits_whenCalled_returnsOnlyMetricUnits() {
        final var metricUnits = PressureUnit.getMetricUnits();
        final var imperialUnits = PressureUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(PressureUnit.isMetric(metricUnit));
            assertFalse(PressureUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PressureUnit.values().length);
    }

    /**
     * Checks that imperial units are imperial and that metric and imperial units cover all values.
     */
    @Test
    void getImperialUnits_whenCalled_returnsOnlyImperialUnits() {
        final var metricUnits = PressureUnit.getMetricUnits();
        final var imperialUnits = PressureUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(PressureUnit.isImperial(imperialUnit));
            assertFalse(PressureUnit.isMetric(imperialUnit));
        }

        assertEquals(1, imperialUnits.length);
        assertEquals(PressureUnit.PSI, imperialUnits[0]);
        assertEquals(metricUnits.length + imperialUnits.length, PressureUnit.values().length);
    }

    /**
     * Checks metric detection for every unit.
     */
    @Test
    void isMetric_whenValidUnit_returnsExpectedResult() {
        assertTrue(PressureUnit.isMetric(PressureUnit.PASCAL));
        assertTrue(PressureUnit.isMetric(PressureUnit.KILOPASCAL));
        assertTrue(PressureUnit.isMetric(PressureUnit.BAR));
        assertTrue(PressureUnit.isMetric(PressureUnit.ATMOSPHERE));
        assertFalse(PressureUnit.isMetric(PressureUnit.PSI));
    }

    /**
     * Checks that metric detection rejects a null unit.
     */
    @Test
    void isMetric_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.isMetric(null));
    }

    /**
     * Checks imperial detection for every unit.
     */
    @Test
    void isImperial_whenValidUnit_returnsExpectedResult() {
        assertFalse(PressureUnit.isImperial(PressureUnit.PASCAL));
        assertFalse(PressureUnit.isImperial(PressureUnit.KILOPASCAL));
        assertFalse(PressureUnit.isImperial(PressureUnit.BAR));
        assertFalse(PressureUnit.isImperial(PressureUnit.ATMOSPHERE));
        assertTrue(PressureUnit.isImperial(PressureUnit.PSI));
    }

    /**
     * Checks that imperial detection rejects a null unit.
     */
    @Test
    void isImperial_whenNullUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.isImperial(null));
    }
}
