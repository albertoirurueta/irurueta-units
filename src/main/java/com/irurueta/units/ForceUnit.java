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
 * Enumerator containing recognized typical force units.
 */
public enum ForceUnit {
    /**
     * Newton (N).
     */
    NEWTON,

    /**
     * Kilonewton (kN).
     */
    KILONEWTON,

    /**
     * Pound-force (lbf).
     */
    POUND_FORCE,

    /**
     * Dyne (dyn).
     */
    DYNE,

    /**
     * Kilogram-force (kgf).
     */
    KILOGRAM_FORCE;

    /**
     * Returns unit system for provided force unit.
     *
     * @param unit force unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final ForceUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case POUND_FORCE -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric force units.
     *
     * @return all supported metric force units.
     */
    public static ForceUnit[] getMetricUnits() {
        return new ForceUnit[]{
                NEWTON,
                KILONEWTON,
                DYNE,
                KILOGRAM_FORCE
        };
    }

    /**
     * Gets all supported imperial force units.
     *
     * @return all supported imperial force units.
     */
    public static ForceUnit[] getImperialUnits() {
        return new ForceUnit[]{
                POUND_FORCE
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit force unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final ForceUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit force unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final ForceUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
