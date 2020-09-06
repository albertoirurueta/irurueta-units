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
 * Contains angular speed value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class AngularSpeed extends Measurement<AngularSpeedUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value angular speed value.
     * @param unit  unit of angular speed.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public AngularSpeed(final Number value, final AngularSpeedUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    AngularSpeed() {
        super();
    }

    /**
     * Determines if two angular speed values are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two angular speed instances are equal or not.
     * @return true if provided angular speed is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<AngularSpeedUnit> other, double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = AngularSpeedConverter.convert(other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two angular speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final AngularSpeedUnit unit1,
            final double value2, final AngularSpeedUnit unit2,
            final AngularSpeedUnit resultUnit) {
        final double v1 = AngularSpeedConverter.convert(value1, unit1, resultUnit);
        final double v2 = AngularSpeedConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two angular acceleration values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final AngularSpeedUnit unit1,
            final Number value2, final AngularSpeedUnit unit2,
            final AngularSpeedUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two angular speeds and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final AngularSpeed arg1, final AngularSpeed arg2,
            final AngularSpeed result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two angular speeds.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular speed.
     * @return a new instance containing result.
     */
    public static AngularSpeed addAndReturnNew(
            final AngularSpeed arg1, final AngularSpeed arg2,
            final AngularSpeedUnit unit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(
            final double value, final AngularSpeedUnit unit,
            final AngularSpeedUnit resultUnit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(
            final Number value, final AngularSpeedUnit unit,
            final AngularSpeedUnit resultUnit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided angular speed to current instance and returns a new angular
     * speed.
     *
     * @param s    angular speed to be added.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(
            final AngularSpeed s, final AngularSpeedUnit unit) {
        return addAndReturnNew(this, s, unit);
    }

    /**
     * Adds provided angular speed value and unit and updates current angular speed
     * instance.
     *
     * @param value angular speed value to be added.
     * @param unit  unit of angular speed value.
     */
    public void add(final double value, final AngularSpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular speed value and unit and updates current angular speed
     * instance.
     *
     * @param value angular speed value to be added.
     * @param unit  unit of angular speed value.
     */
    public void add(final Number value, final AngularSpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular speed and updates current angular speed.
     *
     * @param s angular speed to be added.
     */
    public void add(final AngularSpeed s) {
        add(this, s, this);
    }

    /**
     * Adds provided angular speed and stores the result into provided angular speed.
     *
     * @param s      angular speed to be added.
     * @param result instance where result will be stored.
     */
    public void add(final AngularSpeed s, final AngularSpeed result) {
        add(this, s, result);
    }

    /**
     * Subtracts two angular speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final AngularSpeedUnit unit1,
            final double value2, final AngularSpeedUnit unit2,
            final AngularSpeedUnit resultUnit) {
        final double v1 = AngularSpeedConverter.convert(value1, unit1, resultUnit);
        final double v2 = AngularSpeedConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two angular speed values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final AngularSpeedUnit unit1,
            final Number value2, final AngularSpeedUnit unit2,
            final AngularSpeedUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two angular speeds and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final AngularSpeed arg1, final AngularSpeed arg2,
            final AngularSpeed result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two angular speeds.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public static AngularSpeed subtractAndReturnNew(
            final AngularSpeed arg1,
            final AngularSpeed arg2,
            final AngularSpeedUnit unit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(
            final double value, final AngularSpeedUnit unit,
            final AngularSpeedUnit resultUnit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(
            final Number value, final AngularSpeedUnit unit,
            final AngularSpeedUnit resultUnit) {
        final AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular speed to current instance and returns a new angular
     * speed.
     *
     * @param s    angular speed to be subtracted.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(
            final AngularSpeed s, final AngularSpeedUnit unit) {
        return subtractAndReturnNew(this, s, unit);
    }

    /**
     * Subtracts provided angular speed value and unit and updates current angular
     * speed instance.
     *
     * @param value angular speed value to be subtracted.
     * @param unit  unit of angular speed value.
     */
    public void subtract(final double value, final AngularSpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular speed value and unit and updates current angular
     * speed instance.
     *
     * @param value angular speed value to be subtracted.
     * @param unit  unit of angular speed value.
     */
    public void subtract(final Number value, final AngularSpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular speed and updates current angular speed.
     *
     * @param s angular speed to be subtracted.
     */
    public void subtract(final AngularSpeed s) {
        subtract(this, s, this);
    }

    /**
     * Subtracts provided angular speed and stores the result into provided
     * angular speed.
     *
     * @param s      angular speed to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final AngularSpeed s, final AngularSpeed result) {
        subtract(this, s, result);
    }
}
