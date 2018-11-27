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

/**
 * Enumerator containing recognized typical speed units.
 */
public enum SpeedUnit {
    /**
     * Meters per second (m/s).
     */
    METERS_PER_SECOND,

    /**
     * Kilometers per hour (Km/h)
     */
    KILOMETERS_PER_HOUR,

    /**
     * Kilometers per second (Km/s)
     */
    KILOMETERS_PER_SECOND,

    /**
     * Keet per second (ft/s)
     */
    FEET_PER_SECOND,

    /**
     * Miles per hour (mph)
     */
    MILES_PER_HOUR;

    /**
     * Returns unit system for provided speed unit.
     * @param unit speed unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(SpeedUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        switch (unit) {
            case FEET_PER_SECOND:
            case MILES_PER_HOUR:
                return UnitSystem.IMPERIAL;
            case METERS_PER_SECOND:
            case KILOMETERS_PER_HOUR:
            case KILOMETERS_PER_SECOND:
            default:
                return UnitSystem.METRIC;
        }
    }

    /**
     * Gets all supported metric speed units.
     * @return all supported metric speed units.
     */
    public static SpeedUnit[] getMetricUnits() {
        return new SpeedUnit[] {
                METERS_PER_SECOND,
                KILOMETERS_PER_HOUR,
                KILOMETERS_PER_SECOND
        };
    }

    /**
     * Gets all supported imperial speed units.
     * @return all supported imperial speed units.
     */
    public static SpeedUnit[] getImperialUnits() {
        return new SpeedUnit[] {
                FEET_PER_SECOND,
                MILES_PER_HOUR
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     * @param unit speed unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(SpeedUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     * @param unit speed unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(SpeedUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
