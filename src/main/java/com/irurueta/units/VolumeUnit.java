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
 * Enumerator containing recognized typical volume units.
 */
public enum VolumeUnit {
    /**
     * Cubic centimeter.
     */
    CUBIC_CENTIMETER,

    /**
     * Milliliter.
     */
    MILLILITER,

    /**
     * Cubic decimeter.
     */
    CUBIC_DECIMETER,

    /**
     * Liter.
     */
    LITER,

    /**
     * Hectoliter.
     */
    HECTOLITER,

    /**
     * Cubic meter.
     */
    CUBIC_METER,

    /**
     * Cubic inch.
     */
    CUBIC_INCH,

    /**
     * Pint.
     */
    PINT,

    /**
     * Gallon.
     */
    GALLON,

    /**
     * Cubic foot.
     */
    CUBIC_FOOT,

    /**
     * Barrel.
     */
    BARREL;

    /**
     * Returns unit system from provided volume unit.
     *
     * @param unit volume unit to be checked.
     * @return unit system (metric or imperial).
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static UnitSystem getUnitSystem(final VolumeUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        return switch (unit) {
            case CUBIC_INCH, PINT, GALLON, CUBIC_FOOT, BARREL -> UnitSystem.IMPERIAL;
            default -> UnitSystem.METRIC;
        };
    }

    /**
     * Gets all supported metric volume units.
     *
     * @return all supported metric volume units.
     */
    public static VolumeUnit[] getMetricUnits() {
        return new VolumeUnit[]{
                CUBIC_CENTIMETER,
                MILLILITER,
                CUBIC_DECIMETER,
                LITER,
                HECTOLITER,
                CUBIC_METER
        };
    }

    /**
     * Gets all supported imperial volume units.
     *
     * @return all supported imperial volume units.
     */
    public static VolumeUnit[] getImperialUnits() {
        return new VolumeUnit[]{
                CUBIC_INCH,
                PINT,
                GALLON,
                CUBIC_FOOT,
                BARREL
        };
    }

    /**
     * Indicates whether provided unit belongs to the metric unit system.
     *
     * @param unit volume unit to be checked.
     * @return true if unit belongs to metric unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isMetric(final VolumeUnit unit) {
        return getUnitSystem(unit) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided unit belongs to the imperial unit system.
     *
     * @param unit volume unit to be checked.
     * @return true if unit belongs to imperial unit system.
     * @throws IllegalArgumentException if unit is null or not supported.
     */
    public static boolean isImperial(final VolumeUnit unit) {
        return getUnitSystem(unit) == UnitSystem.IMPERIAL;
    }
}
