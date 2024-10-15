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
 * Enumerator containing recognized typical temperature units.
 */
public enum TemperatureUnit {
    /**
     * Celsius (ºC).
     */
    CELSIUS,

    /**
     * Fahrenheit (ºF).
     */
    FAHRENHEIT,

    /**
     * Kelvin (K).
     */
    KELVIN;

    /**
     * Returns unit system for provided temperature unit.
     *
     * @param unit temperature unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    @SuppressWarnings("DuplicatedCode")
    public static UnitSystem getUnitSystem(final TemperatureUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        if (unit == FAHRENHEIT) {
            return UnitSystem.IMPERIAL;
        } else {
            return UnitSystem.METRIC;
        }
    }

    /**
     * Gets all supported metric temperature units.
     *
     * @return all supported metric temperature units.
     */
    public static TemperatureUnit[] getMetricUnits() {
        return new TemperatureUnit[]{
                CELSIUS,
                KELVIN
        };
    }

    /**
     * Gets all supported imperial temperature units.
     *
     * @return all supported imperial temperature units.
     */
    public static TemperatureUnit[] getImperialUnits() {
        return new TemperatureUnit[]{
                FAHRENHEIT
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit temperature unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final TemperatureUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit temperature unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final TemperatureUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
