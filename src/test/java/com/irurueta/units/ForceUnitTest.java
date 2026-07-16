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

class ForceUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.NEWTON));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.KILONEWTON));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.DYNE));
        assertEquals(UnitSystem.METRIC, ForceUnit.getUnitSystem(ForceUnit.KILOGRAM_FORCE));
        assertEquals(UnitSystem.IMPERIAL, ForceUnit.getUnitSystem(ForceUnit.POUND_FORCE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = ForceUnit.getMetricUnits();
        final var imperialUnits = ForceUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(ForceUnit.isMetric(metricUnit));
            assertFalse(ForceUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, ForceUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = ForceUnit.getMetricUnits();
        final var imperialUnits = ForceUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(ForceUnit.isImperial(imperialUnit));
            assertFalse(ForceUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, ForceUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(ForceUnit.isMetric(ForceUnit.NEWTON));
        assertTrue(ForceUnit.isMetric(ForceUnit.KILONEWTON));
        assertTrue(ForceUnit.isMetric(ForceUnit.DYNE));
        assertTrue(ForceUnit.isMetric(ForceUnit.KILOGRAM_FORCE));
        assertFalse(ForceUnit.isMetric(ForceUnit.POUND_FORCE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(ForceUnit.isImperial(ForceUnit.NEWTON));
        assertFalse(ForceUnit.isImperial(ForceUnit.KILONEWTON));
        assertFalse(ForceUnit.isImperial(ForceUnit.DYNE));
        assertFalse(ForceUnit.isImperial(ForceUnit.KILOGRAM_FORCE));
        assertTrue(ForceUnit.isImperial(ForceUnit.POUND_FORCE));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> ForceUnit.isImperial(null));
    }
}
