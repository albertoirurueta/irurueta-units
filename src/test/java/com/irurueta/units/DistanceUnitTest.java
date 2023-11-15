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

import org.junit.Test;

import static org.junit.Assert.*;

public class DistanceUnitTest {

    @Test
    public void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC,
                DistanceUnit.getUnitSystem(DistanceUnit.MILLIMETER));
        assertEquals(UnitSystem.METRIC,
                DistanceUnit.getUnitSystem(DistanceUnit.CENTIMETER));
        assertEquals(UnitSystem.METRIC,
                DistanceUnit.getUnitSystem(DistanceUnit.METER));
        assertEquals(UnitSystem.METRIC,
                DistanceUnit.getUnitSystem(DistanceUnit.KILOMETER));
        assertEquals(UnitSystem.IMPERIAL,
                DistanceUnit.getUnitSystem(DistanceUnit.INCH));
        assertEquals(UnitSystem.IMPERIAL,
                DistanceUnit.getUnitSystem(DistanceUnit.FOOT));
        assertEquals(UnitSystem.IMPERIAL,
                DistanceUnit.getUnitSystem(DistanceUnit.YARD));
        assertEquals(UnitSystem.IMPERIAL,
                DistanceUnit.getUnitSystem(DistanceUnit.MILE));

        // Force IllegalArgumentException
        try {
            DistanceUnit.getUnitSystem(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetMetricUnits() {
        final DistanceUnit[] metricUnits = DistanceUnit.getMetricUnits();
        final DistanceUnit[] imperialUnits = DistanceUnit.getImperialUnits();

        for (final DistanceUnit metricUnit : metricUnits) {
            assertTrue(DistanceUnit.isMetric(metricUnit));
            assertFalse(DistanceUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, DistanceUnit.values().length);
    }

    @Test
    public void testGetImperialUnits() {
        final DistanceUnit[] metricUnits = DistanceUnit.getMetricUnits();
        final DistanceUnit[] imperialUnits = DistanceUnit.getImperialUnits();

        for (final DistanceUnit imperialUnit : imperialUnits) {
            assertTrue(DistanceUnit.isImperial(imperialUnit));
            assertFalse(DistanceUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length, DistanceUnit.values().length);
    }

    @Test
    public void testIsMetric() {
        assertTrue(DistanceUnit.isMetric(DistanceUnit.MILLIMETER));
        assertTrue(DistanceUnit.isMetric(DistanceUnit.CENTIMETER));
        assertTrue(DistanceUnit.isMetric(DistanceUnit.METER));
        assertTrue(DistanceUnit.isMetric(DistanceUnit.KILOMETER));
        assertFalse(DistanceUnit.isMetric(DistanceUnit.INCH));
        assertFalse(DistanceUnit.isMetric(DistanceUnit.FOOT));
        assertFalse(DistanceUnit.isMetric(DistanceUnit.YARD));
        assertFalse(DistanceUnit.isMetric(DistanceUnit.MILE));

        // Force IllegalArgumentException
        try {
            // noinspection ResultOfMethodCallIgnored
            DistanceUnit.isMetric(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testIsImperial() {
        assertFalse(DistanceUnit.isImperial(DistanceUnit.MILLIMETER));
        assertFalse(DistanceUnit.isImperial(DistanceUnit.CENTIMETER));
        assertFalse(DistanceUnit.isImperial(DistanceUnit.METER));
        assertFalse(DistanceUnit.isImperial(DistanceUnit.KILOMETER));
        assertTrue(DistanceUnit.isImperial(DistanceUnit.INCH));
        assertTrue(DistanceUnit.isImperial(DistanceUnit.FOOT));
        assertTrue(DistanceUnit.isImperial(DistanceUnit.YARD));
        assertTrue(DistanceUnit.isImperial(DistanceUnit.MILE));

        // Force IllegalArgumentException
        try {
            // noinspection ResultOfMethodCallIgnored
            DistanceUnit.isImperial(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }
}
