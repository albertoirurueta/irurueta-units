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
 * Contains a speed value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Speed extends Measurement<SpeedUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value speed value.
     * @param unit  unit of speed.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Speed(final Number value, final SpeedUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Speed() {
        super();
    }

    /**
     * Determines if two speeds are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another speed to compare.
     * @param tolerance amount of tolerance to determine whether two speed instances are equal or not.
     * @return true if provided speed is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(
            final Measurement<SpeedUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = SpeedConverter.convert(
                other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final SpeedUnit unit1,
                             final double value2, final SpeedUnit unit2,
                             final SpeedUnit resultUnit) {
        final double v1 = SpeedConverter.convert(value1, unit1, resultUnit);
        final double v2 = SpeedConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two speed values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final SpeedUnit unit1,
                             final Number value2, final SpeedUnit unit2,
                             final SpeedUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two speed instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final Speed arg1, final Speed arg2, final Speed result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two speed instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned speed.
     * @return a new instance containing result.
     */
    public static Speed addAndReturnNew(
            final Speed arg1, final Speed arg2, final SpeedUnit unit) {
        final Speed result = new Speed();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided speed value and unit and returns a new speed instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed addAndReturnNew(
            final double value, final SpeedUnit unit,
            final SpeedUnit resultUnit) {
        final Speed result = new Speed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided speed value and unit and returns a new speed instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed addAndReturnNew(
            final Number value, final SpeedUnit unit,
            final SpeedUnit resultUnit) {
        final Speed result = new Speed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided speed to current instance and returns a new speed.
     *
     * @param s    speed to be added.
     * @param unit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed addAndReturnNew(final Speed s, final SpeedUnit unit) {
        return addAndReturnNew(this, s, unit);
    }

    /**
     * Adds provided speed value and unit and updates current speed instance.
     *
     * @param value speed value to be added.
     * @param unit  unit of speed value.
     */
    public void add(final double value, final SpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided speed value and unit and updates current speed instance.
     *
     * @param value speed value to be added.
     * @param unit  unit of speed value.
     */
    public void add(final Number value, final SpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided speed and updates current speed.
     *
     * @param speed speed to be added.
     */
    public void add(final Speed speed) {
        add(this, speed, this);
    }

    /**
     * Adds provided speed and stores the result into provided speed.
     *
     * @param s      speed to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Speed s, final Speed result) {
        add(this, s, result);
    }

    /**
     * Subtracts two speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final SpeedUnit unit1,
            final double value2, final SpeedUnit unit2,
            final SpeedUnit resultUnit) {
        final double v1 = SpeedConverter.convert(value1, unit1, resultUnit);
        final double v2 = SpeedConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final SpeedUnit unit1,
            final Number value2, final SpeedUnit unit2,
            final SpeedUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two speed instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final Speed arg1, final Speed arg2, final Speed result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two speed instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned speed.
     * @return a new instance containing result.
     */
    public static Speed subtractAndReturnNew(
            final Speed arg1, final Speed arg2,
            final SpeedUnit unit) {
        final Speed result = new Speed();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided speed value and unit and returns a nw speed instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed subtractAndReturnNew(
            final double value, final SpeedUnit unit,
            final SpeedUnit resultUnit) {
        final Speed result = new Speed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value,
                unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided speed value and unit and returns a new speed instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed subtractAndReturnNew(
            final Number value, final SpeedUnit unit,
            final SpeedUnit resultUnit) {
        final Speed result = new Speed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided speed to current instance and returns a new speed instance.
     *
     * @param s    speed to be subtracted.
     * @param unit unit of returned speed.
     * @return a new speed containing result.
     */
    public Speed subtractAndReturnNew(
            final Speed s, final SpeedUnit unit) {
        return subtractAndReturnNew(this, s, unit);
    }

    /**
     * Subtracts provided speed value and unit and updates current speed instance.
     *
     * @param value speed value to be subtracted.
     * @param unit  unit of speed value.
     */
    public void subtract(final double value, final SpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided speed value and unit and updates current speed instance.
     *
     * @param value speed value to be subtracted.
     * @param unit  unit of speed value.
     */
    public void subtract(final Number value, final SpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided speed and updates current speed.
     *
     * @param speed speed to be subtracted.
     */
    public void subtract(final Speed speed) {
        subtract(this, speed, this);
    }

    /**
     * Subtracts provided speed and stores the result into provided speed.
     *
     * @param s      speed to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Speed s, final Speed result) {
        subtract(this, s, result);
    }
}
