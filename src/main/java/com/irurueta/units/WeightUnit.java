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

/**
 * Enumerator containing recognized typical weight units.
 */
public enum WeightUnit {
    /**
     * Picogram weight unit.
     */
    PICOGRAM,

    /**
     * Nanogram weight unit.
     */
    NANOGRAM,

    /**
     * Microgram weight unit.
     */
    MICROGRAM,

    /**
     * Milligram weight unit.
     */
    MILLIGRAM,

    /**
     * Gram weight unit.
     */
    GRAM,

    /**
     * Kilogram weight unit.
     */
    KILOGRAM,

    /**
     * Metric tonne weight unit.
     */
    TONNE,

    /**
     * Metric mega tonne weight unit.
     */
    MEGATONNE,

    /**
     * US ton weight unit.
     */
    US_TON,

    /**
     * UK ton weight unit.
     */
    UK_TON,

    /**
     * Pound weight unit.
     */
    POUND,

    /**
     * Ounce weight unit.
     */
    OUNCE;

    /**
     * Returns unit system for provided weight unit.
     *
     * @param unit weight unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case US_TON, UK_TON, POUND, OUNCE -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric weight units.
     *
     * @return all supported metric weight units.
     */
    public static WeightUnit[] getMetricUnits() {
        return new WeightUnit[]{
                PICOGRAM,
                NANOGRAM,
                MICROGRAM,
                MILLIGRAM,
                GRAM,
                KILOGRAM,
                TONNE,
                MEGATONNE
        };
    }

    /**
     * Gets all supported imperial weight units.
     *
     * @return all supported imperial weight units.
     */
    public static WeightUnit[] getImperialUnits() {
        return new WeightUnit[]{
                US_TON,
                UK_TON,
                POUND,
                OUNCE
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system
     *
     * @param unit weight unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final WeightUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit weight unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final WeightUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
