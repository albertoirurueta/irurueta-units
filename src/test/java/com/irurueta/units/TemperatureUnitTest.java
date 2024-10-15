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

class TemperatureUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, TemperatureUnit.getUnitSystem(TemperatureUnit.CELSIUS));
        assertEquals(UnitSystem.METRIC, TemperatureUnit.getUnitSystem(TemperatureUnit.KELVIN));
        assertEquals(UnitSystem.IMPERIAL, TemperatureUnit.getUnitSystem(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TemperatureUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = TemperatureUnit.getMetricUnits();
        final var imperialUnits = TemperatureUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(TemperatureUnit.isMetric(metricUnit));
            assertFalse(TemperatureUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, TemperatureUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = TemperatureUnit.getMetricUnits();
        final var imperialUnits = TemperatureUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(TemperatureUnit.isImperial(imperialUnit));
            assertFalse(TemperatureUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, TemperatureUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(TemperatureUnit.isMetric(TemperatureUnit.CELSIUS));
        assertTrue(TemperatureUnit.isMetric(TemperatureUnit.KELVIN));
        assertFalse(TemperatureUnit.isMetric(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TemperatureUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(TemperatureUnit.isImperial(TemperatureUnit.CELSIUS));
        assertFalse(TemperatureUnit.isImperial(TemperatureUnit.KELVIN));
        assertTrue(TemperatureUnit.isImperial(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TemperatureUnit.isImperial(null));
    }
}
