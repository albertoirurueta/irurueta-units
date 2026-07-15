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

/**
 * Enumerator containing recognized typical power units.
 */
public enum PowerUnit {
    /**
     * Watt (W).
     */
    WATT,

    /**
     * Kilowatt (kW).
     */
    KILOWATT,

    /**
     * Megawatt (MW).
     */
    MEGAWATT,

    /**
     * Horsepower (hp).
     */
    HORSEPOWER;

    /**
     * Returns unit system for provided power unit.
     *
     * @param unit power unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final PowerUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case HORSEPOWER -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric power units.
     *
     * @return all supported metric power units.
     */
    public static PowerUnit[] getMetricUnits() {
        return new PowerUnit[]{
            WATT,
            KILOWATT,
            MEGAWATT
        };
    }

    /**
     * Gets all supported imperial power units.
     *
     * @return all supported imperial power units.
     */
    public static PowerUnit[] getImperialUnits() {
        return new PowerUnit[]{
            HORSEPOWER
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit power unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final PowerUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit power unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final PowerUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
