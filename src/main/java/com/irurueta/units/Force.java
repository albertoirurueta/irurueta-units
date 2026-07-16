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

import java.math.BigDecimal;

/**
 * Contains a force value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Force extends Measurement<ForceUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value force value.
     * @param unit  unit of force.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Force(final Number value, final ForceUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Force() {
        super();
    }

    /**
     * Determines if two forces are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another force to compare.
     * @param tolerance amount of tolerance to determine whether two force instances are equal or not.
     * @return true if provided force is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<ForceUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = ForceConverter.convert(other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two force values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final ForceUnit unit1,
                             final double value2, final ForceUnit unit2,
                             final ForceUnit resultUnit) {
        final var v1 = ForceConverter.convert(value1, unit1, resultUnit);
        final var v2 = ForceConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two force values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final ForceUnit unit1,
                             final Number value2, final ForceUnit unit2,
                             final ForceUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two force instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Force arg1, final Force arg2, final Force result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two force instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned force.
     * @return a new instance containing result.
     */
    public static Force addAndReturnNew(final Force arg1, final Force arg2, final ForceUnit unit) {
        final var result = new Force();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided force value and unit and returns a new force instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned force.
     * @return a new force containing result.
     */
    public Force addAndReturnNew(
            final double value, final ForceUnit unit, final ForceUnit resultUnit) {
        final var result = new Force();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided force value and unit and returns a new force instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned force.
     * @return a new force containing result.
     */
    public Force addAndReturnNew(
            final Number value, final ForceUnit unit, final ForceUnit resultUnit) {
        final var result = new Force();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided force to current instance and returns a new force.
     *
     * @param f    force to be added.
     * @param unit unit of returned force.
     * @return a new force containing result.
     */
    public Force addAndReturnNew(final Force f, final ForceUnit unit) {
        return addAndReturnNew(this, f, unit);
    }

    /**
     * Adds provided force value and unit and updates current force instance.
     *
     * @param value force value to be added.
     * @param unit  unit of force value.
     */
    public void add(final double value, final ForceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided force value and unit and updates current force instance.
     *
     * @param value force value to be added.
     * @param unit  unit of force value.
     */
    public void add(final Number value, final ForceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided force and updates current force.
     *
     * @param force force to be added.
     */
    public void add(final Force force) {
        add(this, force, this);
    }

    /**
     * Adds provided force and stores the result into provided force.
     *
     * @param f      force to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Force f, final Force result) {
        add(this, f, result);
    }

    /**
     * Subtracts two force values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final ForceUnit unit1,
            final double value2, final ForceUnit unit2,
            final ForceUnit resultUnit) {
        final var v1 = ForceConverter.convert(value1, unit1, resultUnit);
        final var v2 = ForceConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two force values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final ForceUnit unit1,
            final Number value2, final ForceUnit unit2,
            final ForceUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two force instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Force arg1, final Force arg2, final Force result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two force instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned force.
     * @return a new instance containing result.
     */
    public static Force subtractAndReturnNew(
            final Force arg1, final Force arg2, final ForceUnit unit) {
        final var result = new Force();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided force value and unit and returns a nw force instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned force.
     * @return a new force containing result.
     */
    public Force subtractAndReturnNew(
            final double value, final ForceUnit unit, final ForceUnit resultUnit) {
        final var result = new Force();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided force value and unit and returns a new force instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned force.
     * @return a new force containing result.
     */
    public Force subtractAndReturnNew(
            final Number value, final ForceUnit unit, final ForceUnit resultUnit) {
        final var result = new Force();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided force to current instance and returns a new force instance.
     *
     * @param f    force to be subtracted.
     * @param unit unit of returned force.
     * @return a new force containing result.
     */
    public Force subtractAndReturnNew(final Force f, final ForceUnit unit) {
        return subtractAndReturnNew(this, f, unit);
    }

    /**
     * Subtracts provided force value and unit and updates current force instance.
     *
     * @param value force value to be subtracted.
     * @param unit  unit of force value.
     */
    public void subtract(final double value, final ForceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided force value and unit and updates current force instance.
     *
     * @param value force value to be subtracted.
     * @param unit  unit of force value.
     */
    public void subtract(final Number value, final ForceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided force and updates current force.
     *
     * @param force force to be subtracted.
     */
    public void subtract(final Force force) {
        subtract(this, force, this);
    }

    /**
     * Subtracts provided force and stores the result into provided force.
     *
     * @param f      force to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Force f, final Force result) {
        subtract(this, f, result);
    }
}
