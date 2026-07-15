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

class PressureUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.PASCAL));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.KILOPASCAL));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.BAR));
        assertEquals(UnitSystem.METRIC, PressureUnit.getUnitSystem(PressureUnit.ATMOSPHERE));
        assertEquals(UnitSystem.IMPERIAL, PressureUnit.getUnitSystem(PressureUnit.PSI));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = PressureUnit.getMetricUnits();
        final var imperialUnits = PressureUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(PressureUnit.isMetric(metricUnit));
            assertFalse(PressureUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PressureUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = PressureUnit.getMetricUnits();
        final var imperialUnits = PressureUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(PressureUnit.isImperial(imperialUnit));
            assertFalse(PressureUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, PressureUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(PressureUnit.isMetric(PressureUnit.PASCAL));
        assertTrue(PressureUnit.isMetric(PressureUnit.KILOPASCAL));
        assertTrue(PressureUnit.isMetric(PressureUnit.BAR));
        assertTrue(PressureUnit.isMetric(PressureUnit.ATMOSPHERE));
        assertFalse(PressureUnit.isMetric(PressureUnit.PSI));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(PressureUnit.isImperial(PressureUnit.PASCAL));
        assertFalse(PressureUnit.isImperial(PressureUnit.KILOPASCAL));
        assertFalse(PressureUnit.isImperial(PressureUnit.BAR));
        assertFalse(PressureUnit.isImperial(PressureUnit.ATMOSPHERE));
        assertTrue(PressureUnit.isImperial(PressureUnit.PSI));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> PressureUnit.isImperial(null));
    }
}
