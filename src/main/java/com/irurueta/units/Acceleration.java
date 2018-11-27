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
 * Contains an acceleration value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Acceleration extends Measurement<AccelerationUnit> {

    /**
     * Constructor with value and unit.
     * @param value acceleration value.
     * @param unit unit of acceleration.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Acceleration(Number value, AccelerationUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Acceleration() {
        super();
    }

    /**
     * Determines if two accelerations are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     * @param other another acceleration to compare.
     * @param tolerance true if provided acceleration is assumed to be equal to this
     *                  instance up to provided tolerance.
     * @return true if provided acceleration is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(Measurement<AccelerationUnit> other, double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        double otherValue = AccelerationConverter.convert(other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two acceleration values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(double value1, AccelerationUnit unit1,
                             double value2, AccelerationUnit unit2,
                             AccelerationUnit resultUnit) {
        double v1 = AccelerationConverter.convert(value1, unit1, resultUnit);
        double v2 = AccelerationConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two acceleration values and unit and reeturns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(Number value1, AccelerationUnit unit1,
                             Number value2, AccelerationUnit unit2,
                             AccelerationUnit resultUnit) {
        return new BigDecimal(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two acceleration instances and stores the result into provided instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(Acceleration arg1, Acceleration arg2, Acceleration result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two acceleration instances.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned acceleration.
     * @return a new instance containing result.
     */
    public static Acceleration addAndReturnNew(Acceleration arg1, Acceleration arg2,
                                               AccelerationUnit unit) {
        Acceleration result = new Acceleration();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided acceleration value and unit and returns a new acceleration
     * instance using provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration addAndReturnNew(double value, AccelerationUnit unit,
                                        AccelerationUnit resultUnit) {
        Acceleration result = new Acceleration();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided acceleration value and unit and returns a new acceleration
     * instance using provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration addAndReturnNew(Number value, AccelerationUnit unit,
                                        AccelerationUnit resultUnit) {
        Acceleration result = new Acceleration();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided acceleration to current instance and returns a new acceleration.
     * @param a acceleration to be added.
     * @param unit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration addAndReturnNew(Acceleration a, AccelerationUnit unit) {
        return addAndReturnNew(this, a, unit);
    }

    /**
     * Adds provided acceleration value and unit and updates current acceleration
     * instance.
     * @param value acceleration value to be added.
     * @param unit unit of acceleration value.
     */
    public void add(double value, AccelerationUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided acceleration value and unit and updates current acceleration
     * instance.
     * @param value acceleration value to be added.
     * @param unit unit of acceleration value.
     */
    public void add(Number value, AccelerationUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided acceleration and updates current acceleration.
     * @param acceleration acceleration to be added.
     */
    public void add(Acceleration acceleration) {
        add(this, acceleration, this);
    }

    /**
     * Adds provided acceleration and stores the result into provided acceleration.
     * @param a acceleration to be added.
     * @param result instance where result will be stored.
     */
    public void add(Acceleration a, Acceleration result) {
        add(this, a, result);
    }

    /**
     * Subtracts two acceleration values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd agument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(double value1, AccelerationUnit unit1,
                                  double value2, AccelerationUnit unit2,
                                  AccelerationUnit resultUnit) {
        double v1 = AccelerationConverter.convert(value1, unit1, resultUnit);
        double v2 = AccelerationConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two acceleration values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(Number value1, AccelerationUnit unit1,
                                  Number value2, AccelerationUnit unit2,
                                  AccelerationUnit resultUnit) {
        return new BigDecimal(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two acceleration instances and stores the result into provided
     * instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(Acceleration arg1, Acceleration arg2,
                                Acceleration result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two acceleration instances.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned acceleration.
     * @return a new instance containing result.
     */
    public static Acceleration subtractAndReturnNew(Acceleration arg1,
            Acceleration arg2, AccelerationUnit unit) {
        Acceleration result = new Acceleration();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided acceleration value and unit and returns a new acceleration
     * instance using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration subtractAndReturnNew(double value, AccelerationUnit unit,
                                             AccelerationUnit resultUnit) {
        Acceleration result = new Acceleration();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value,
                unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided acceleration value and unit and returns a new acceleration
     * instance using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration subtractAndReturnNew(Number value, AccelerationUnit unit,
                                             AccelerationUnit resultUnit) {
        Acceleration result = new Acceleration();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided acceleration to current instance and returns a new
     * acceleration instance.
     * @param a acceleration to be subtracted.
     * @param unit unit of returned acceleration.
     * @return a new acceleration containing result.
     */
    public Acceleration subtractAndReturnNew(Acceleration a, AccelerationUnit unit) {
        return subtractAndReturnNew(this, a, unit);
    }

    /**
     * Subtracts provided acceleration value and unit and updates current acceleration
     * instance.
     * @param value acceleration value to be subtracted.
     * @param unit unit of acceleration value.
     */
    public void subtract(double value, AccelerationUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided acceleration value and unit and updates current acceleration
     * instance.
     * @param value acceleration value to be subtracted.
     * @param unit unit of acceleration value.
     */
    public void subtract(Number value, AccelerationUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided acceleration and updates current acceleration.
     * @param acceleration acceleration to be subtracted.
     */
    public void subtract(Acceleration acceleration) {
        subtract(this, acceleration, this);
    }

    /**
     * Subtracts provided acceleration and stores the result into provided acceleration.
     * @param a acceleration to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(Acceleration a, Acceleration result) {
        subtract(this, a, result);
    }
}
