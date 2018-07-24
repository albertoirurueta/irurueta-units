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

public class SpeedUnitTest {

    public SpeedUnitTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }

    @Before
    public void setUp() { }

    @After
    public void tearDown() { }

    @Test
    public void testGetUnitSystem() {
        assertEquals(SpeedUnit.getUnitSystem(SpeedUnit.METERS_PER_SECOND),
                UnitSystem.METRIC);
        assertEquals(SpeedUnit.getUnitSystem(SpeedUnit.KILOMETERS_PER_HOUR),
                UnitSystem.METRIC);
        assertEquals(SpeedUnit.getUnitSystem(SpeedUnit.KILOMETERS_PER_SECOND),
                UnitSystem.METRIC);
        assertEquals(SpeedUnit.getUnitSystem(SpeedUnit.MILES_PER_HOUR),
                UnitSystem.IMPERIAL);

        //Force IllegalArgumentException
        try {
            SpeedUnit.getUnitSystem(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testGetMetricUnits() {
        SpeedUnit[] metricUnits = SpeedUnit.getMetricUnits();
        SpeedUnit[] imperialUnits = SpeedUnit.getImperialUnits();

        for (SpeedUnit metricUnit : metricUnits) {
            assertTrue(SpeedUnit.isMetric(metricUnit));
            assertFalse(SpeedUnit.isImperial(metricUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                SpeedUnit.values().length);
    }

    @Test
    public void testGetImperialUnits() {
        SpeedUnit[] metricUnits = SpeedUnit.getMetricUnits();
        SpeedUnit[] imperialUnits = SpeedUnit.getImperialUnits();

        for (SpeedUnit imperialUnit : imperialUnits) {
            assertTrue(SpeedUnit.isImperial(imperialUnit));
            assertFalse(SpeedUnit.isMetric(imperialUnit));
        }

        assertEquals(metricUnits.length + imperialUnits.length,
                SpeedUnit.values().length);
    }

    @Test
    public void testIsMetric() {
        assertTrue(SpeedUnit.isMetric(SpeedUnit.METERS_PER_SECOND));
        assertTrue(SpeedUnit.isMetric(SpeedUnit.KILOMETERS_PER_HOUR));
        assertTrue(SpeedUnit.isMetric(SpeedUnit.KILOMETERS_PER_SECOND));
        assertFalse(SpeedUnit.isMetric(SpeedUnit.MILES_PER_HOUR));

        //Force IllegalArgumentException
        try {
            //noinspection all
            SpeedUnit.isMetric(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testIsImperial() {
        assertFalse(SpeedUnit.isImperial(SpeedUnit.METERS_PER_SECOND));
        assertFalse(SpeedUnit.isImperial(SpeedUnit.KILOMETERS_PER_HOUR));
        assertFalse(SpeedUnit.isImperial(SpeedUnit.KILOMETERS_PER_SECOND));
        assertTrue(SpeedUnit.isImperial(SpeedUnit.MILES_PER_HOUR));

        //Force IllegalArgumentException
        try {
            //noinspection all
            SpeedUnit.isImperial(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }
}
