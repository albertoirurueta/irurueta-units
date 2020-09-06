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

import org.junit.*;

import static org.junit.Assert.*;

public class AccelerationUnitTest {

    @Test
    public void testGetUnitSystem() {
        assertEquals(AccelerationUnit.getUnitSystem(AccelerationUnit.METERS_PER_SQUARED_SECOND),
                UnitSystem.METRIC);
        assertEquals(AccelerationUnit.getUnitSystem(AccelerationUnit.G),
                UnitSystem.METRIC);
        assertEquals(AccelerationUnit.getUnitSystem(AccelerationUnit.FEET_PER_SQUARED_SECOND),
                UnitSystem.IMPERIAL);

        // Force IllegalArgumentException
        try {
            AccelerationUnit.getUnitSystem(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetMetricUnits() {
        final AccelerationUnit[] metricUnits = AccelerationUnit.getMetricUnits();
        final AccelerationUnit[] imperialUnits = AccelerationUnit.getImperialUnits();

        for (AccelerationUnit metricUnit : metricUnits) {
            assertTrue(AccelerationUnit.isMetric(metricUnit));
            assertFalse(AccelerationUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                AccelerationUnit.values().length);
    }

    @Test
    public void testGetImperialUnits() {
        final AccelerationUnit[] metricUnits = AccelerationUnit.getMetricUnits();
        final AccelerationUnit[] imperialUnits = AccelerationUnit.getImperialUnits();

        for (AccelerationUnit imperialUnit : imperialUnits) {
            assertTrue(AccelerationUnit.isImperial(imperialUnit));
            assertFalse(AccelerationUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                AccelerationUnit.values().length);
    }

    @Test
    public void testIsMetric() {
        assertTrue(AccelerationUnit.isMetric(
                AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertTrue(AccelerationUnit.isMetric(AccelerationUnit.G));
        assertFalse(AccelerationUnit.isMetric(
                AccelerationUnit.FEET_PER_SQUARED_SECOND));

        // Force IllegalArgumentException
        try {
            //noinspection ResultOfMethodCallIgnored
            AccelerationUnit.isMetric(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testIsImperial() {
        assertFalse(AccelerationUnit.isImperial(
                AccelerationUnit.METERS_PER_SQUARED_SECOND));
        assertFalse(AccelerationUnit.isImperial(AccelerationUnit.G));
        assertTrue(AccelerationUnit.isImperial(
                AccelerationUnit.FEET_PER_SQUARED_SECOND));

        // Force IllegalArgumentException
        try {
            //noinspection ResultOfMethodCallIgnored
            AccelerationUnit.isImperial(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }
}
