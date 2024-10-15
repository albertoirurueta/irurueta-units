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

import java.math.BigDecimal;

/**
 * Contains a surface value and unit.
 */
public class Surface extends Measurement<SurfaceUnit> {

    /**
     * Constructor with value and units.
     *
     * @param value surface value.
     * @param unit  unit of surface.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    @SuppressWarnings("WeakerAccess")
    public Surface(final Number value, final SurfaceUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Surface() {
        super();
    }

    /**
     * Determines if two surfaces are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another surface to compare.
     * @param tolerance amount of tolerance to determine whether two surface instances are equal or not.
     * @return true if provided surface is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<SurfaceUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = SurfaceConverter.convert(other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two surface values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final SurfaceUnit unit1,
                             final double value2, final SurfaceUnit unit2,
                             final SurfaceUnit resultUnit) {
        final var v1 = SurfaceConverter.convert(value1, unit1, resultUnit);
        final var v2 = SurfaceConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds to surface values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final SurfaceUnit unit1,
                             final Number value2, final SurfaceUnit unit2,
                             final SurfaceUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two surfaces and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Surface arg1, final Surface arg2, final Surface result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two surfaces.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned surface.
     * @return a new instance containing result.
     */
    public static Surface addAndReturnNew(final Surface arg1, final Surface arg2, final SurfaceUnit unit) {
        final var result = new Surface();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided surface value and unit and returns a new surface instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned surface.
     * @return a new surface containing result.
     */
    public Surface addAndReturnNew(final double value, final SurfaceUnit unit, final SurfaceUnit resultUnit) {
        final var result = new Surface();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided surface value and unit and returns a new surface instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned surface.
     * @return a new surface containing result.
     */
    public Surface addAndReturnNew(
            final Number value, final SurfaceUnit unit, final SurfaceUnit resultUnit) {
        final var result = new Surface();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided surface to current instance and returns a new
     * surface.
     *
     * @param s    surface to be added.
     * @param unit unit of returned surface.
     * @return a new surface containing result.
     */
    public Surface addAndReturnNew(final Surface s, final SurfaceUnit unit) {
        return addAndReturnNew(this, s, unit);
    }

    /**
     * Adds provided surface value and unit and updates current surface instance.
     *
     * @param value surface value to be added.
     * @param unit  unit of surface value.
     */
    public void add(final double value, final SurfaceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided surface value and unit and updates current surface instance.
     *
     * @param value surface value to be added.
     * @param unit  unit of surface value.
     */
    public void add(final Number value, final SurfaceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided surface and updates current distance.
     *
     * @param surface surface to be added.
     */
    public void add(final Surface surface) {
        add(this, surface, this);
    }

    /**
     * Adds provided surface and stores the result into provided
     * surface.
     *
     * @param s      surface to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Surface s, final Surface result) {
        add(this, s, result);
    }

    /**
     * Subtracts two surface values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final SurfaceUnit unit1,
            final double value2, final SurfaceUnit unit2,
            final SurfaceUnit resultUnit) {
        final var v1 = SurfaceConverter.convert(value1, unit1, resultUnit);
        final var v2 = SurfaceConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two surface values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final SurfaceUnit unit1,
            final Number value2, final SurfaceUnit unit2,
            final SurfaceUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two surfaces and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Surface arg1, final Surface arg2, final Surface result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two surfaces.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned surface.
     * @return a new instance containing result.
     */
    public static Surface subtractAndReturnNew(final Surface arg1, final Surface arg2, final SurfaceUnit unit) {
        final var result = new Surface();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided surface value and unit and returns a new surface instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned surface.
     * @return a new surface containing result.
     */
    public Surface subtractAndReturnNew(final double value, final SurfaceUnit unit, final SurfaceUnit resultUnit) {
        final var result = new Surface();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided surface value and unit and returns a new surface instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned time.
     * @return a new surface containing result.
     */
    public Surface subtractAndReturnNew(final Number value, final SurfaceUnit unit, final SurfaceUnit resultUnit) {
        final var result = new Surface();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided surface to current instance and returns a new
     * surface.
     *
     * @param s    surface to be subtracted.
     * @param unit unit of returned surface.
     * @return a new surface containing result.
     */
    public Surface subtractAndReturnNew(final Surface s, final SurfaceUnit unit) {
        return subtractAndReturnNew(this, s, unit);
    }

    /**
     * Subtracts provided surface value and unit and updates current surface instance.
     *
     * @param value surface value to be subtracted.
     * @param unit  unit of surface value.
     */
    public void subtract(final double value, final SurfaceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided surface value and unit and updates current surface instance.
     *
     * @param value surface value to be subtracted.
     * @param unit  unit of surface value.
     */
    public void subtract(final Number value, final SurfaceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided surface and updates current surface.
     *
     * @param surface surface to be subtracted.
     */
    public void subtract(final Surface surface) {
        subtract(this, surface, this);
    }

    /**
     * Subtracts provided surface and stores the result into provided
     * surface.
     *
     * @param s      surface to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Surface s, final Surface result) {
        subtract(this, s, result);
    }
}
