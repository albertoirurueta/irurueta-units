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
 * Enumerator containing recognized typical acceleration units.
 */
public enum AccelerationUnit {
    /**
     * Meters per squared second (m/s^2).
     */
    METERS_PER_SQUARED_SECOND,

    /**
     * Relative to gravitational acceleration (9.81 m/s^2).
     */
    G,

    /**
     * Feet per squared second (ft/s^2).
     */
    FEET_PER_SQUARED_SECOND;

    /**
     * Returns unit system for provided acceleration unit.
     *
     * @param unit acceleration unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    @SuppressWarnings("DuplicatedCode")
    public static UnitSystem getUnitSystem(final AccelerationUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        switch (unit) {
            case FEET_PER_SQUARED_SECOND:
                return UnitSystem.IMPERIAL;
            case METERS_PER_SQUARED_SECOND:
            case G:
            default:
                return UnitSystem.METRIC;
        }
    }

    /**
     * Gets all supported metric acceleration units.
     *
     * @return all supported metric acceleration units.
     */
    public static AccelerationUnit[] getMetricUnits() {
        return new AccelerationUnit[]{
                METERS_PER_SQUARED_SECOND,
                G
        };
    }

    /**
     * Gets all supported imperial acceleration units.
     *
     * @return all supported imperial acceleration units.
     */
    public static AccelerationUnit[] getImperialUnits() {
        return new AccelerationUnit[]{
                FEET_PER_SQUARED_SECOND
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit acceleration unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final AccelerationUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit acceleration unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final AccelerationUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
