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

class EnergyUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.JOULE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOJOULE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.CALORIE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOCALORIE));
        assertEquals(UnitSystem.METRIC, EnergyUnit.getUnitSystem(EnergyUnit.KILOWATT_HOUR));
        assertEquals(UnitSystem.IMPERIAL, EnergyUnit.getUnitSystem(EnergyUnit.BTU));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EnergyUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = EnergyUnit.getMetricUnits();
        final var imperialUnits = EnergyUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(EnergyUnit.isMetric(metricUnit));
            assertFalse(EnergyUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, EnergyUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = EnergyUnit.getMetricUnits();
        final var imperialUnits = EnergyUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(EnergyUnit.isImperial(imperialUnit));
            assertFalse(EnergyUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, EnergyUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(EnergyUnit.isMetric(EnergyUnit.JOULE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOJOULE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.CALORIE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOCALORIE));
        assertTrue(EnergyUnit.isMetric(EnergyUnit.KILOWATT_HOUR));
        assertFalse(EnergyUnit.isMetric(EnergyUnit.BTU));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> EnergyUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
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
