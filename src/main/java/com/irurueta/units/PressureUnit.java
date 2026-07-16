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
 * Enumerator containing recognized typical pressure units.
 */
public enum PressureUnit {
    /**
     * Pascal (Pa).
     */
    PASCAL,

    /**
     * Kilopascal (kPa)
     */
    KILOPASCAL,

    /**
     * Bar (bar)
     */
    BAR,

    /**
     * Atmosphere (atm)
     */
    ATMOSPHERE,

    /**
     * Pound per square inch (psi)
     */
    PSI;

    /**
     * Returns unit system for provided pressure unit.
     *
     * @param unit pressure unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final PressureUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case PSI -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric pressure units.
     *
     * @return all supported metric pressure units.
     */
    public static PressureUnit[] getMetricUnits() {
        return new PressureUnit[]{
                PASCAL,
                KILOPASCAL,
                BAR,
                ATMOSPHERE
        };
    }

    /**
     * Gets all supported imperial pressure units.
     *
     * @return all supported imperial pressure units.
     */
    public static PressureUnit[] getImperialUnits() {
        return new PressureUnit[]{
                PSI
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit pressure unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final PressureUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit pressure unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final PressureUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
