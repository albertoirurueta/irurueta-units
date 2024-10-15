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

class TimeUnitTest {

    @Test
    void testGetUnitSystem() {
        assertEquals(UnitSystem.METRIC, TimeUnit.getUnitSystem(TimeUnit.NANOSECOND));
        assertEquals(UnitSystem.METRIC, TimeUnit.getUnitSystem(TimeUnit.MICROSECOND));
        assertEquals(UnitSystem.METRIC, TimeUnit.getUnitSystem(TimeUnit.MILLISECOND));
        assertEquals(UnitSystem.METRIC, TimeUnit.getUnitSystem(TimeUnit.SECOND));

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.MINUTE));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.HOUR));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.DAY));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.WEEK));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.MONTH));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.YEAR));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(TimeUnit.CENTURY));
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.getUnitSystem(null));
    }

    @Test
    void testGetMetricUnits() {
        final var metricUnits = TimeUnit.getMetricUnits();
        final var nonISUnits = TimeUnit.getNonInternationalSystemUnits();

        for (final var metricUnit : metricUnits) {
            assertTrue(TimeUnit.isMetric(metricUnit));
            assertFalse(TimeUnit.isNonInternationalSystem(metricUnit));
        }

        assertEquals(metricUnits.length + nonISUnits.length, TimeUnit.values().length);
    }

    @Test
    void testGetNonInternationalSystemUnits() {
        final var metricUnits = TimeUnit.getMetricUnits();
        final var nonISUnits = TimeUnit.getNonInternationalSystemUnits();

        for (final var nonSIUnit : nonISUnits) {
            assertTrue(TimeUnit.isNonInternationalSystem(nonSIUnit));
            assertFalse(TimeUnit.isMetric(nonSIUnit));
        }

        assertEquals(metricUnits.length + nonISUnits.length, TimeUnit.values().length);
    }

    @Test
    void testIsMetric() {
        assertTrue(TimeUnit.isMetric(TimeUnit.NANOSECOND));
        assertTrue(TimeUnit.isMetric(TimeUnit.MICROSECOND));
        assertTrue(TimeUnit.isMetric(TimeUnit.MILLISECOND));
        assertTrue(TimeUnit.isMetric(TimeUnit.SECOND));
        assertFalse(TimeUnit.isMetric(TimeUnit.MINUTE));
        assertFalse(TimeUnit.isMetric(TimeUnit.HOUR));
        assertFalse(TimeUnit.isMetric(TimeUnit.DAY));
        assertFalse(TimeUnit.isMetric(TimeUnit.WEEK));
        assertFalse(TimeUnit.isMetric(TimeUnit.MONTH));
        assertFalse(TimeUnit.isMetric(TimeUnit.YEAR));
        assertFalse(TimeUnit.isMetric(TimeUnit.CENTURY));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.isMetric(null));
    }

    @Test
    void testIsNonInternationalSystem() {
        assertFalse(TimeUnit.isNonInternationalSystem(TimeUnit.NANOSECOND));
        assertFalse(TimeUnit.isNonInternationalSystem(TimeUnit.MICROSECOND));
        assertFalse(TimeUnit.isNonInternationalSystem(TimeUnit.MILLISECOND));
        assertFalse(TimeUnit.isNonInternationalSystem(TimeUnit.SECOND));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.MINUTE));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.HOUR));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.DAY));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.WEEK));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.MONTH));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.YEAR));
        assertTrue(TimeUnit.isNonInternationalSystem(TimeUnit.CENTURY));

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> TimeUnit.isNonInternationalSystem(null));
    }
}
