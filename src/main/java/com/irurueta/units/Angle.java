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
 * Contains angle value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Angle extends Measurement<AngleUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value angle value.
     * @param unit  unit of angle.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Angle(final Number value, final AngleUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Angle() {
        super();
    }

    /**
     * Determines if two angles are equal up to a certain tolerance. If needed, this method attempts unit conversion
     * to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two angle instances are equal or not.
     * @return true if provided angle is assumed to be equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(final Measurement<AngleUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = AngleConverter.convert(other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two angle values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final AngleUnit unit1,
            final double value2, final AngleUnit unit2,
            final AngleUnit resultUnit) {
        final var v1 = AngleConverter.convert(value1, unit1, resultUnit);
        final var v2 = AngleConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two angle values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final AngleUnit unit1,
            final Number value2, final AngleUnit unit2,
            final AngleUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two angles and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Angle arg1, final Angle arg2, final Angle result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two angles.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angle.
     * @return a new instance containing result.
     */
    public static Angle addAndReturnNew(final Angle arg1, final Angle arg2, final AngleUnit unit) {
        final var result = new Angle();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided angle value and unit and returns a new angle instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle addAndReturnNew(
            final double value, final AngleUnit unit, final AngleUnit resultUnit) {
        final var result = new Angle();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided angle value and unit and returns a new angle instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle addAndReturnNew(
            final Number value, final AngleUnit unit, final AngleUnit resultUnit) {
        final var result = new Angle();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided angle to current instance and returns a new angle.
     *
     * @param a    angle to be added.
     * @param unit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle addAndReturnNew(final Angle a, final AngleUnit unit) {
        return addAndReturnNew(this, a, unit);
    }

    /**
     * Adds provided angle value and unit and updates current angle instance.
     *
     * @param value angle value to be added.
     * @param unit  unit of angle value.
     */
    public void add(final double value, final AngleUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angle value and unit and updates current angle instance.
     *
     * @param value angle value to be added.
     * @param unit  unit of angle value.
     */
    public void add(final Number value, final AngleUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided angle and updates current angle.
     *
     * @param angle angle to be added.
     */
    public void add(final Angle angle) {
        add(this, angle, this);
    }

    /**
     * Adds provided angle and stores the result into provided angle.
     *
     * @param a      angle to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Angle a, final Angle result) {
        add(this, a, result);
    }

    /**
     * Subtracts two angle values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final AngleUnit unit1,
            final double value2, final AngleUnit unit2,
            final AngleUnit resultUnit) {
        final var v1 = AngleConverter.convert(value1, unit1, resultUnit);
        final var v2 = AngleConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two angle values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final AngleUnit unit1,
            final Number value2, final AngleUnit unit2,
            final AngleUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two angles and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Angle arg1, final Angle arg2, final Angle result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two angles.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned angle.
     * @return a new angle containing result.
     */
    public static Angle subtractAndReturnNew(final Angle arg1, final Angle arg2, final AngleUnit unit) {
        final var result = new Angle();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided angle value and unit and returns a new angle instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle subtractAndReturnNew(
            final double value, final AngleUnit unit, final AngleUnit resultUnit) {
        final var result = new Angle();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided angle value and unit and returns a new angle instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle subtractAndReturnNew(
            final Number value, final AngleUnit unit, final AngleUnit resultUnit) {
        final var result = new Angle();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided angle to current instance and returns a new angle.
     *
     * @param a    angle to be subtracted.
     * @param unit unit of returned angle.
     * @return a new angle containing result.
     */
    public Angle subtractAndReturnNew(final Angle a, final AngleUnit unit) {
        return subtractAndReturnNew(this, a, unit);
    }

    /**
     * Subtracts provided angle value and unit and updates current angle instance.
     *
     * @param value angle value to be subtracted.
     * @param unit  unit of angle value.
     */
    public void subtract(final double value, final AngleUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angle value and unit and updates current angle instance.
     *
     * @param value angle value to be subtracted.
     * @param unit  unit of angle value.
     */
    public void subtract(final Number value, final AngleUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided angle and updates current angle.
     *
     * @param angle angle to be subtracted.
     */
    public void subtract(final Angle angle) {
        subtract(this, angle, this);
    }

    /**
     * Subtracts provided angle and stores the result into provided angle.
     *
     * @param a      angle to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Angle a, final Angle result) {
        subtract(this, a, result);
    }
}
