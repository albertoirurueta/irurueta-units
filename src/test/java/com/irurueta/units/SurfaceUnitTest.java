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

class SurfaceUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_MILLIMETER));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_CENTIMETER));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_METER));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_KILOMETER));
        assertEquals(UnitSystem.IMPERIAL, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_INCH));
        assertEquals(UnitSystem.IMPERIAL, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_FOOT));
        assertEquals(UnitSystem.IMPERIAL, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_YARD));
        assertEquals(UnitSystem.IMPERIAL, SurfaceUnit.getUnitSystem(SurfaceUnit.SQUARE_MILE));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.CENTIARE));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.ARE));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.DECARE));
        assertEquals(UnitSystem.METRIC, SurfaceUnit.getUnitSystem(SurfaceUnit.HECTARE));
        assertEquals(UnitSystem.IMPERIAL, SurfaceUnit.getUnitSystem(SurfaceUnit.ACRE));

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SurfaceUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = SurfaceUnit.getMetricUnits();
        final var imperialUnits = SurfaceUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(SurfaceUnit.isMetric(metricUnit));
            assertFalse(SurfaceUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, SurfaceUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = SurfaceUnit.getMetricUnits();
        final var imperialUnits = SurfaceUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(SurfaceUnit.isImperial(imperialUnit));
            assertFalse(SurfaceUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, SurfaceUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_MILLIMETER));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_CENTIMETER));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_METER));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_KILOMETER));
        assertFalse(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_INCH));
        assertFalse(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_FOOT));
        assertFalse(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_YARD));
        assertFalse(SurfaceUnit.isMetric(SurfaceUnit.SQUARE_MILE));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.CENTIARE));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.ARE));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.DECARE));
        assertTrue(SurfaceUnit.isMetric(SurfaceUnit.HECTARE));
        assertFalse(SurfaceUnit.isMetric(SurfaceUnit.ACRE));

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SurfaceUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_MILLIMETER));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_CENTIMETER));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_METER));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_KILOMETER));
        assertTrue(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_INCH));
        assertTrue(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_FOOT));
        assertTrue(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_YARD));
        assertTrue(SurfaceUnit.isImperial(SurfaceUnit.SQUARE_MILE));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.CENTIARE));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.ARE));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.DECARE));
        assertFalse(SurfaceUnit.isImperial(SurfaceUnit.HECTARE));
        assertTrue(SurfaceUnit.isImperial(SurfaceUnit.ACRE));

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> SurfaceUnit.isImperial(null));
    }
}
