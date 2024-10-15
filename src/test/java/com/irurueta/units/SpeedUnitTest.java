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

class SpeedUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, SpeedUnit.getUnitSystem(SpeedUnit.METERS_PER_SECOND));
        assertEquals(UnitSystem.METRIC, SpeedUnit.getUnitSystem(SpeedUnit.KILOMETERS_PER_HOUR));
        assertEquals(UnitSystem.METRIC, SpeedUnit.getUnitSystem(SpeedUnit.KILOMETERS_PER_SECOND));
        assertEquals(UnitSystem.IMPERIAL, SpeedUnit.getUnitSystem(SpeedUnit.FEET_PER_SECOND));
        assertEquals(UnitSystem.IMPERIAL, SpeedUnit.getUnitSystem(SpeedUnit.MILES_PER_HOUR));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SpeedUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = SpeedUnit.getMetricUnits();
        final var imperialUnits = SpeedUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(SpeedUnit.isMetric(metricUnit));
            assertFalse(SpeedUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, SpeedUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = SpeedUnit.getMetricUnits();
        final var imperialUnits = SpeedUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(SpeedUnit.isImperial(imperialUnit));
            assertFalse(SpeedUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, SpeedUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(SpeedUnit.isMetric(SpeedUnit.METERS_PER_SECOND));
        assertTrue(SpeedUnit.isMetric(SpeedUnit.KILOMETERS_PER_HOUR));
        assertTrue(SpeedUnit.isMetric(SpeedUnit.KILOMETERS_PER_SECOND));
        assertFalse(SpeedUnit.isMetric(SpeedUnit.FEET_PER_SECOND));
        assertFalse(SpeedUnit.isMetric(SpeedUnit.MILES_PER_HOUR));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SpeedUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(SpeedUnit.isImperial(SpeedUnit.METERS_PER_SECOND));
        assertFalse(SpeedUnit.isImperial(SpeedUnit.KILOMETERS_PER_HOUR));
        assertFalse(SpeedUnit.isImperial(SpeedUnit.KILOMETERS_PER_SECOND));
        assertTrue(SpeedUnit.isImperial(SpeedUnit.FEET_PER_SECOND));
        assertTrue(SpeedUnit.isImperial(SpeedUnit.MILES_PER_HOUR));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SpeedUnit.isImperial(null));
    }
}
