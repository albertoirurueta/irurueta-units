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
     * @param value angular speed value.
     * @param unit unit of angular speed.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public AngularSpeed(Number value, AngularSpeedUnit unit)
            throws IllegalArgumentException {
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
     * @param other another measurement to compare.
     * @param tolerance true if provided measurement is assumed to be equal to this
     *                  instance up to provided tolerance.
     * @return true if provided angular speed is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(Measurement<AngularSpeedUnit> other, double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        double otherValue = AngularSpeedConverter.convert(other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two angular values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(double value1, AngularSpeedUnit unit1,
                             double value2, AngularSpeedUnit unit2,
                             AngularSpeedUnit resultUnit) {
        double v1 = AngularSpeedConverter.convert(value1, unit1, resultUnit);
        double v2 = AngularSpeedConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two angular values and unit and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(Number value1, AngularSpeedUnit unit1,
                             Number value2, AngularSpeedUnit unit2,
                             AngularSpeedUnit resultUnit) {
        return new BigDecimal(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two angular speed and stores the result into provided instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(AngularSpeed arg1, AngularSpeed arg2, AngularSpeed result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two angular speeds.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular speed.
     * @return a new instance containing result.
     */
    public static AngularSpeed addAndReturnNew(AngularSpeed arg1, AngularSpeed arg2,
                                              AngularSpeedUnit unit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(double value, AngularSpeedUnit unit,
                                        AngularSpeedUnit resultUnit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(Number value, AngularSpeedUnit unit,
                                        AngularSpeedUnit resultUnit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided angular speed to current instance and returns a new angular
     * speed.
     * @param s angular speed to be added.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed addAndReturnNew(AngularSpeed s, AngularSpeedUnit unit) {
        return addAndReturnNew(this, s, unit);
    }

    /**
     * Adds provided angular speed value and unit and updates current angular speed
     * instance.
     * @param value angular speed value to be added.
     * @param unit unit of angular speed value.
     */
    public void add(double value, AngularSpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular speed value and unit and updates current angular speed
     * instance.
     * @param value angular speed value to be added.
     * @param unit unit of angular value.
     */
    public void add(Number value, AngularSpeedUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angular speed and updates current angular speed.
     * @param s angular speed to be added.
     */
    public void add(AngularSpeed s) {
        add(this, s, this);
    }

    /**
     * Adds provided angular speed and stores the result into provided angular speed.
     * @param s angular speed to be added.
     * @param result instance where result will be stored.
     */
    public void add(AngularSpeed s, AngularSpeed result) {
        add(this, s, result);
    }

    /**
     * Subtracts two angular speed values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(double value1, AngularSpeedUnit unit1,
                                  double value2, AngularSpeedUnit unit2,
                                  AngularSpeedUnit resultUnit) {
        double v1 = AngularSpeedConverter.convert(value1, unit1, resultUnit);
        double v2 = AngularSpeedConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two angular speed values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(Number value1, AngularSpeedUnit unit1,
                                  Number value2, AngularSpeedUnit unit2,
                                  AngularSpeedUnit resultUnit) {
        return new BigDecimal(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two angular speeds and stores the result into provided instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(AngularSpeed arg1, AngularSpeed arg2,
                                AngularSpeed result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two angular speeds.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public static AngularSpeed subtractAndReturnNew(AngularSpeed arg1,
            AngularSpeed arg2, AngularSpeedUnit unit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(double value, AngularSpeedUnit unit,
                                             AngularSpeedUnit resultUnit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular speed value and unit and returns a new angular speed
     * instance using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(Number value, AngularSpeedUnit unit,
                                             AngularSpeedUnit resultUnit) {
        AngularSpeed result = new AngularSpeed();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided angular speed to current instance and returns a new angular
     * speed.
     * @param s angular speed to be subtracted.
     * @param unit unit of returned angular speed.
     * @return a new angular speed containing result.
     */
    public AngularSpeed subtractAndReturnNew(AngularSpeed s, AngularSpeedUnit unit) {
        return subtractAndReturnNew(this, s, unit);
    }

    /**
     * Subtracts provided angular speed value and unit and updates current angular
     * speed instance.
     * @param value angular speed value to be subtracted.
     * @param unit unit of angular speed value.
     */
    public void subtract(double value, AngularSpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular speed value and unit and updates current angular
     * speed instance.
     * @param value angular speed value to be subtracted.
     * @param unit unit of angular speed value.
     */
    public void subtract(Number value, AngularSpeedUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angular speed and updates current angular speed.
     * @param s angular speed to be subtracted.
     */
    public void subtract(AngularSpeed s) {
        subtract(this, s, this);
    }

    /**
     * Subtracts provided angular speed and stores the result into provided
     * angular speed.
     * @param s angular speed to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(AngularSpeed s, AngularSpeed result) {
        subtract(this, s, result);
    }
}
