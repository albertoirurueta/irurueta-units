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
 * Contains a distance value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Distance extends Measurement<DistanceUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value distance value.
     * @param unit  unit of distance.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Distance(final Number value, final DistanceUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Distance() {
        super();
    }

    /**
     * Determines if two distances are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another distance to compare.
     * @param tolerance amount of tolerance to determine whether two distance instances are equal or not.
     * @return true if provided distance is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(
            final Measurement<DistanceUnit> other,
            final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = DistanceConverter.convert(
                other.getValue().doubleValue(), other.getUnit(),
                getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two distance values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, DistanceUnit unit1,
            final double value2, DistanceUnit unit2,
            final DistanceUnit resultUnit) {
        final double v1 = DistanceConverter.convert(value1, unit1, resultUnit);
        final double v2 = DistanceConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two distance values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final DistanceUnit unit1,
                             final Number value2, final DistanceUnit unit2,
                             final DistanceUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two distances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final Distance arg1, final Distance arg2, final Distance result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two distances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned distance.
     * @return a new instance containing result.
     */
    public static Distance addAndReturnNew(
            final Distance arg1, final Distance arg2,
            final DistanceUnit unit) {
        final Distance result = new Distance();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided distance value and unit and returns a new distance instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance addAndReturnNew(
            final double value, final DistanceUnit unit,
            final DistanceUnit resultUnit) {
        final Distance result = new Distance();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided distance value and unit and returns a new distance instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance addAndReturnNew(
            final Number value, final DistanceUnit unit,
            final DistanceUnit resultUnit) {
        final Distance result = new Distance();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided distance to current instance and returns a new
     * distance.
     *
     * @param d    distance to be added.
     * @param unit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance addAndReturnNew(
            final Distance d, final DistanceUnit unit) {
        return addAndReturnNew(this, d, unit);
    }

    /**
     * Adds provided distance value and unit and updates current distance instance.
     *
     * @param value distance value to be added.
     * @param unit  unit of distance value.
     */
    public void add(final double value, final DistanceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided distance value and unit and updates current distance instance.
     *
     * @param value distance value to be added.
     * @param unit  unit of distance value.
     */
    public void add(final Number value, final DistanceUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided distance and updates current distance.
     *
     * @param distance distance to be added.
     */
    public void add(final Distance distance) {
        add(this, distance, this);
    }

    /**
     * Adds provided distance and stores the result into provided
     * distance.
     *
     * @param d      distance to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Distance d, final Distance result) {
        add(this, d, result);
    }

    /**
     * Subtracts two distance values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final DistanceUnit unit1,
            final double value2, final DistanceUnit unit2,
            final DistanceUnit resultUnit) {
        final double v1 = DistanceConverter.convert(value1, unit1, resultUnit);
        final double v2 = DistanceConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two distance value and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final DistanceUnit unit1,
            final Number value2, final DistanceUnit unit2,
            final DistanceUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two distances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final Distance arg1, final Distance arg2,
            final Distance result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two distances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned distance.
     * @return a new instance containing result.
     */
    public static Distance subtractAndReturnNew(
            final Distance arg1, final Distance arg2,
            final DistanceUnit unit) {
        final Distance result = new Distance();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided distance value and unit and returns a new distance instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance subtractAndReturnNew(
            final double value, final DistanceUnit unit,
            final DistanceUnit resultUnit) {
        final Distance result = new Distance();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided distance value and unit and returns a new distance instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance subtractAndReturnNew(
            final Number value, final DistanceUnit unit,
            final DistanceUnit resultUnit) {
        final Distance result = new Distance();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided distance to current instance and returns a new
     * distance.
     *
     * @param d    distance to be subtracted.
     * @param unit unit of returned distance.
     * @return a new distance containing result.
     */
    public Distance subtractAndReturnNew(
            final Distance d, final DistanceUnit unit) {
        return subtractAndReturnNew(this, d, unit);
    }

    /**
     * Subtracts provided distance value and unit and updates current distance instance.
     *
     * @param value distance value to be subtracted.
     * @param unit  unit of distance value.
     */
    public void subtract(final double value, final DistanceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided distance value and unit and updates current distance instance.
     *
     * @param value distance value to be subtracted.
     * @param unit  unit of distance value.
     */
    public void subtract(final Number value, final DistanceUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided distance and updates current distance.
     *
     * @param distance distance to be subtracted.
     */
    public void subtract(final Distance distance) {
        subtract(this, distance, this);
    }

    /**
     * Subtracts provided distance and stores the result into provided
     * distance.
     *
     * @param d      distance to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Distance d, final Distance result) {
        subtract(this, d, result);
    }
}
