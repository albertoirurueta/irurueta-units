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

class VolumeUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.CUBIC_CENTIMETER));
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.MILLILITER));
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.CUBIC_DECIMETER));
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.LITER));
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.HECTOLITER));
        assertEquals(UnitSystem.METRIC, VolumeUnit.getUnitSystem(VolumeUnit.CUBIC_METER));
        assertEquals(UnitSystem.IMPERIAL, VolumeUnit.getUnitSystem(VolumeUnit.CUBIC_INCH));
        assertEquals(UnitSystem.IMPERIAL, VolumeUnit.getUnitSystem(VolumeUnit.PINT));
        assertEquals(UnitSystem.IMPERIAL, VolumeUnit.getUnitSystem(VolumeUnit.GALLON));
        assertEquals(UnitSystem.IMPERIAL, VolumeUnit.getUnitSystem(VolumeUnit.CUBIC_FOOT));
        assertEquals(UnitSystem.IMPERIAL, VolumeUnit.getUnitSystem(VolumeUnit.BARREL));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> VolumeUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = VolumeUnit.getMetricUnits();
        final var imperialUnits = VolumeUnit.getImperialUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(VolumeUnit.isMetric(metricUnit));
            assertFalse(VolumeUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, VolumeUnit.values().length);
    }

    @Test
    void testGetImperialUnits() {
        final var metricUnits = VolumeUnit.getMetricUnits();
        final var imperialUnits = VolumeUnit.getImperialUnits();

        for (final var imperialUnit : imperialUnits) {
            assertTrue(VolumeUnit.isImperial(imperialUnit));
            assertFalse(VolumeUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, VolumeUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(VolumeUnit.isMetric(VolumeUnit.CUBIC_CENTIMETER));
        assertTrue(VolumeUnit.isMetric(VolumeUnit.MILLILITER));
        assertTrue(VolumeUnit.isMetric(VolumeUnit.CUBIC_DECIMETER));
        assertTrue(VolumeUnit.isMetric(VolumeUnit.LITER));
        assertTrue(VolumeUnit.isMetric(VolumeUnit.HECTOLITER));
        assertTrue(VolumeUnit.isMetric(VolumeUnit.CUBIC_METER));

        assertFalse(VolumeUnit.isMetric(VolumeUnit.CUBIC_INCH));
        assertFalse(VolumeUnit.isMetric(VolumeUnit.PINT));
        assertFalse(VolumeUnit.isMetric(VolumeUnit.GALLON));
        assertFalse(VolumeUnit.isMetric(VolumeUnit.CUBIC_FOOT));
        assertFalse(VolumeUnit.isMetric(VolumeUnit.BARREL));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> VolumeUnit.isMetric(null));
    }

    @Test
    void testIsImperial() {
        assertFalse(VolumeUnit.isImperial(VolumeUnit.CUBIC_CENTIMETER));
        assertFalse(VolumeUnit.isImperial(VolumeUnit.MILLILITER));
        assertFalse(VolumeUnit.isImperial(VolumeUnit.CUBIC_DECIMETER));
        assertFalse(VolumeUnit.isImperial(VolumeUnit.LITER));
        assertFalse(VolumeUnit.isImperial(VolumeUnit.HECTOLITER));
        assertFalse(VolumeUnit.isImperial(VolumeUnit.CUBIC_METER));

        assertTrue(VolumeUnit.isImperial(VolumeUnit.CUBIC_INCH));
        assertTrue(VolumeUnit.isImperial(VolumeUnit.PINT));
        assertTrue(VolumeUnit.isImperial(VolumeUnit.GALLON));
        assertTrue(VolumeUnit.isImperial(VolumeUnit.CUBIC_FOOT));
        assertTrue(VolumeUnit.isImperial(VolumeUnit.BARREL));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> VolumeUnit.isImperial(null));
    }
}
