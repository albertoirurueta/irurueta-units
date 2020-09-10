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
 * Contains angular acceleration value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class AngularAcceleration extends Measurement<AngularAccelerationUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value angular acceleration value.
     * @param unit  unit of angualr acceleration.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public AngularAcceleration(
            final Number value, final AngularAccelerationUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    AngularAcceleration() {
        super();
    }

    /**
     * Determines if two angular acceleration values are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance true if provided measurement is assumed to be equal to this
     *                  instance up to provided tolerance.
     * @return true if provided angular acceleration is assumed to be equal to this
     * instance, false otherwise.
     */
    public boolean equals(
            final Measurement<AngularAccelerationUnit> other,
            final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = AngularAccelerationConverter.convert(
                other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
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
    public static double add(final double value1, final AngularAccelerationUnit unit1,
                             final double value2, final AngularAccelerationUnit unit2,
                             final AngularAccelerationUnit resultUnit) {
        final double v1 = AngularAccelerationConverter.convert(value1, unit1, resultUnit);
        final double v2 = AngularAccelerationConverter.convert(value2, unit2, resultUnit);
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
    public static Number add(final Number value1, final AngularAccelerationUnit unit1,
                             final Number value2, final AngularAccelerationUnit unit2,
                             final AngularAccelerationUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two angular accelerations and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final AngularAcceleration arg1, final AngularAcceleration arg2,
                           final AngularAcceleration result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two angular accelerations.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular acceleration.
     * @return a new instance containing result.
     */
    public static AngularAcceleration addAndReturnNew(
            final AngularAcceleration arg1,
            final AngularAcceleration arg2,
            final AngularAccelerationUnit unit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided angular acceleration value and unit and returns a new angular
     * acceleration instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration addAndReturnNew(
            final double value, final AngularAccelerationUnit unit,
            final AngularAccelerationUnit resultUnit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided angular acceleration value and unit and returns a new angular
     * acceleration instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration addAndReturnNew(
            final Number value, final AngularAccelerationUnit unit,
            final AngularAccelerationUnit resultUnit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided angular acceleration to current instance and returns a new angular
     * acceleration.
     *
     * @param a    angular acceleration to be added.
     * @param unit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration addAndReturnNew(
            final AngularAcceleration a,
            final AngularAccelerationUnit unit) {
        return addAndReturnNew(this, a, unit);
    }

    /**
     * Adds provided angular acceleration value and unit and updates current angular
     * acceleration instance.
     *
     * @param value angular acceleration value to be added.
     * @param unit  unit of angular acceleration value.
     */
    public void add(final double value,
                    final AngularAccelerationUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular acceleration value and unit and updates current angulasr
     * acceleration instance.
     *
     * @param value angular acceleration value to be added.
     * @param unit  unit of angular acceleration value.
     */
    public void add(final Number value,
                    final AngularAccelerationUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular acceleration and updates current angular acceleration.
     *
     * @param a angular acceleration to be added.
     */
    public void add(final AngularAcceleration a) {
        add(this, a, this);
    }

    /**
     * Adds provided angular acceleration and stores the result into provided angular
     * acceleration.
     *
     * @param a      angular acceleration to be added.
     * @param result instance where result will be stored.
     */
    public void add(final AngularAcceleration a,
                    final AngularAcceleration result) {
        add(this, a, result);
    }

    /**
     * Subtracts two angular acceleration values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return resutl of subtraction.
     */
    public static double subtract(
            final double value1, final AngularAccelerationUnit unit1,
            final double value2, final AngularAccelerationUnit unit2,
            final AngularAccelerationUnit resultUnit) {
        final double v1 = AngularAccelerationConverter.convert(value1, unit1, resultUnit);
        final double v2 = AngularAccelerationConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two angular acceleration values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st agument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final AngularAccelerationUnit unit1,
            final Number value2, final AngularAccelerationUnit unit2,
            final AngularAccelerationUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two angular accelerations and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final AngularAcceleration arg1, final AngularAcceleration arg2,
            final AngularAcceleration result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two angular accelerations.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public static AngularAcceleration subtractAndReturnNew(
            final AngularAcceleration arg1,
            final AngularAcceleration arg2,
            final AngularAccelerationUnit unit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided angular acceleration value and unit and returns a new angular
     * acceleration instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration subtractAndReturnNew(
            final double value, final AngularAccelerationUnit unit,
            final AngularAccelerationUnit resultUnit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular acceleration value and unit and returns a new angular
     * acceleration instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration subtractAndReturnNew(
            final Number value, final AngularAccelerationUnit unit,
            final AngularAccelerationUnit resultUnit) {
        final AngularAcceleration result = new AngularAcceleration();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular acceleration to current instance and returns a new
     * angular acceleration.
     *
     * @param a    angular acceleration to be subtracted.
     * @param unit unit of returned angular acceleration.
     * @return a new angular acceleration containing result.
     */
    public AngularAcceleration subtractAndReturnNew(
            final AngularAcceleration a,
            final AngularAccelerationUnit unit) {
        return subtractAndReturnNew(this, a, unit);
    }

    /**
     * Subtracts provided angular acceleration value and unit and updates current
     * angular acceleration instance.
     *
     * @param value angular acceleration value to be subtracted.
     * @param unit  unit of angular acceleration value.
     */
    public void subtract(
            final double value, final AngularAccelerationUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular acceleration value and unit and updates current
     * angular acceleration instance.
     *
     * @param value angular acceleration to be subtracted.
     * @param unit  unit of angular acceleration value.
     */
    public void subtract(
            final Number value, final AngularAccelerationUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular acceleration and updates current angular acceleration.
     *
     * @param a angular acceleration to be subtracted.
     */
    public void subtract(final AngularAcceleration a) {
        subtract(this, a, this);
    }

    /**
     * Subtracts provided angular acceleration and stores the result into provided
     * angular acceleration.
     *
     * @param a      angular acceleration to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(
            final AngularAcceleration a, final AngularAcceleration result) {
        subtract(this, a, result);
    }
}
