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

import org.junit.Test;

import static org.junit.Assert.*;

public class TemperatureUnitTest {

    @Test
    public void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC,
                TemperatureUnit.getUnitSystem(TemperatureUnit.CELSIUS));
        assertEquals(UnitSystem.METRIC,
                TemperatureUnit.getUnitSystem(TemperatureUnit.KELVIN));
        assertEquals(UnitSystem.IMPERIAL,
                TemperatureUnit.getUnitSystem(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        try {
            TemperatureUnit.getUnitSystem(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetMetricUnits() {
        final TemperatureUnit[] metricUnits = TemperatureUnit.getMetricUnits();
        final TemperatureUnit[] imperialUnits = TemperatureUnit.getImperialUnits();

        for (final TemperatureUnit metricUnit : metricUnits) {
            assertTrue(TemperatureUnit.isMetric(metricUnit));
            assertFalse(TemperatureUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                TemperatureUnit.values().length);
    }

    @Test
    public void testGetImperialUnits() {
        final TemperatureUnit[] metricUnits = TemperatureUnit.getMetricUnits();
        final TemperatureUnit[] imperialUnits = TemperatureUnit.getImperialUnits();

        for (final TemperatureUnit imperialUnit : imperialUnits) {
            assertTrue(TemperatureUnit.isImperial(imperialUnit));
            assertFalse(TemperatureUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                TemperatureUnit.values().length);
    }

    @Test
    public void testIsMetric() {
        assertTrue(TemperatureUnit.isMetric(TemperatureUnit.CELSIUS));
        assertTrue(TemperatureUnit.isMetric(TemperatureUnit.KELVIN));
        assertFalse(TemperatureUnit.isMetric(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        try {
            //noinspection ResultOfMethodCallIgnored
            TemperatureUnit.isMetric(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testIsImperial() {
        assertFalse(TemperatureUnit.isImperial(TemperatureUnit.CELSIUS));
        assertFalse(TemperatureUnit.isImperial(TemperatureUnit.KELVIN));
        assertTrue(TemperatureUnit.isImperial(TemperatureUnit.FAHRENHEIT));

        // Force IllegalArgumentException
        try {
            //noinspection ResultOfMethodCallIgnored
            TemperatureUnit.isImperial(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }
}
