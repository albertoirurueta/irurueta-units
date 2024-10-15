/*
 * Copyright (C) 2018 Alberto Irurueta Carro (alberto@irurueta.com)
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

class AccelerationUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, AccelerationUnit.getUnitSystem(AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertEquals(UnitSystem.METRIC, AccelerationUnit.getUnitSystem(AccelerationUnit.G));
        assertEquals(UnitSystem.IMPERIAL, AccelerationUnit.getUnitSystem(AccelerationUnit.FEET_PER_SQUARED_SECOND));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AccelerationUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = AccelerationUnit.getMetricUnits();
        final var imperialUnits = AccelerationUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(AccelerationUnit.isMetric(metricUnit));
            assertFalse(AccelerationUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, AccelerationUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = AccelerationUnit.getMetricUnits();
        final var imperialUnits = AccelerationUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(AccelerationUnit.isImperial(imperialUnit));
            assertFalse(AccelerationUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, AccelerationUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(AccelerationUnit.isMetric(AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertTrue(AccelerationUnit.isMetric(AccelerationUnit.G));
        assertFalse(AccelerationUnit.isMetric(AccelerationUnit.FEET_PER_SQUARED_SECOND));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AccelerationUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(AccelerationUnit.isImperial(AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertFalse(AccelerationUnit.isImperial(AccelerationUnit.G));
        assertTrue(AccelerationUnit.isImperial(AccelerationUnit.FEET_PER_SQUARED_SECOND));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AccelerationUnit.isImperial(null));
    }
}
