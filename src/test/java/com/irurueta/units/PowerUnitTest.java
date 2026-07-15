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

class PowerUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.WATT));
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.KILOWATT));
        assertEquals(UnitSystem.METRIC, PowerUnit.getUnitSystem(PowerUnit.MEGAWATT));
        assertEquals(UnitSystem.IMPERIAL, PowerUnit.getUnitSystem(PowerUnit.HORSEPOWER));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = PowerUnit.getMetricUnits();
        final var imperialUnits = PowerUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(PowerUnit.isMetric(metricUnit));
            assertFalse(PowerUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PowerUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = PowerUnit.getMetricUnits();
        final var imperialUnits = PowerUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(PowerUnit.isImperial(imperialUnit));
            assertFalse(PowerUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PowerUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(PowerUnit.isMetric(PowerUnit.WATT));
        assertTrue(PowerUnit.isMetric(PowerUnit.KILOWATT));
        assertTrue(PowerUnit.isMetric(PowerUnit.MEGAWATT));
        assertFalse(PowerUnit.isMetric(PowerUnit.HORSEPOWER));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(PowerUnit.isImperial(PowerUnit.WATT));
        assertFalse(PowerUnit.isImperial(PowerUnit.KILOWATT));
        assertFalse(PowerUnit.isImperial(PowerUnit.MEGAWATT));
        assertTrue(PowerUnit.isImperial(PowerUnit.HORSEPOWER));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PowerUnit.isImperial(null));
    }
}
