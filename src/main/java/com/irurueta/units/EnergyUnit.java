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
 * Enumerator containing recognized typical energy units.
 */
public enum EnergyUnit {
    /**
     * Joule (J).
     */
    JOULE,

    /**
     * Kilojoule (kJ).
     */
    KILOJOULE,

    /**
     * Calorie (cal).
     */
    CALORIE,

    /**
     * Kilocalorie (kcal).
     */
    KILOCALORIE,

    /**
     * Kilowatt-hour (kWh).
     */
    KILOWATT_HOUR,

    /**
     * British thermal unit (BTU).
     */
    BTU;

    /**
     * Returns unit system for provided energy unit.
     *
     * @param unit energy unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final EnergyUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case BTU -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric energy units.
     *
     * @return all supported metric energy units.
     */
    public static EnergyUnit[] getMetricUnits() {
        return new EnergyUnit[]{
                JOULE,
                KILOJOULE,
                CALORIE,
                KILOCALORIE,
                KILOWATT_HOUR
        };
    }

    /**
     * Gets all supported imperial energy units.
     *
     * @return all supported imperial energy units.
     */
    public static EnergyUnit[] getImperialUnits() {
        return new EnergyUnit[]{
                BTU
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit energy unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final EnergyUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit energy unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final EnergyUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
