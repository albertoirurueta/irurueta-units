/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
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

class WeightUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.IMPERIAL, WeightUnit.getUnitSystem(WeightUnit.US_TON));
        assertEquals(UnitSystem.IMPERIAL, WeightUnit.getUnitSystem(WeightUnit.UK_TON));
        assertEquals(UnitSystem.IMPERIAL, WeightUnit.getUnitSystem(WeightUnit.POUND));
        assertEquals(UnitSystem.IMPERIAL, WeightUnit.getUnitSystem(WeightUnit.OUNCE));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.PICOGRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.NANOGRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.MICROGRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.MILLIGRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.GRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.KILOGRAM));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.TONNE));
        assertEquals(UnitSystem.METRIC, WeightUnit.getUnitSystem(WeightUnit.MEGATONNE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> WeightUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = WeightUnit.getMetricUnits();
        final var imperialUnits = WeightUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(WeightUnit.isMetric(metricUnit));
            assertFalse(WeightUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, WeightUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = WeightUnit.getMetricUnits();
        final var imperialUnits = WeightUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(WeightUnit.isImperial(imperialUnit));
            assertFalse(WeightUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, WeightUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(WeightUnit.isMetric(WeightUnit.PICOGRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.NANOGRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.MICROGRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.MILLIGRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.GRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.KILOGRAM));
        assertTrue(WeightUnit.isMetric(WeightUnit.TONNE));
        assertTrue(WeightUnit.isMetric(WeightUnit.MEGATONNE));

        assertFalse(WeightUnit.isMetric(WeightUnit.US_TON));
        assertFalse(WeightUnit.isMetric(WeightUnit.UK_TON));
        assertFalse(WeightUnit.isMetric(WeightUnit.POUND));
        assertFalse(WeightUnit.isMetric(WeightUnit.OUNCE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> WeightUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(WeightUnit.isImperial(WeightUnit.PICOGRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.NANOGRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.MICROGRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.MILLIGRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.GRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.KILOGRAM));
        assertFalse(WeightUnit.isImperial(WeightUnit.TONNE));
        assertFalse(WeightUnit.isImperial(WeightUnit.MEGATONNE));

        assertTrue(WeightUnit.isImperial(WeightUnit.US_TON));
        assertTrue(WeightUnit.isImperial(WeightUnit.UK_TON));
        assertTrue(WeightUnit.isImperial(WeightUnit.POUND));
        assertTrue(WeightUnit.isImperial(WeightUnit.OUNCE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> WeightUnit.isImperial(null));
    }
}
