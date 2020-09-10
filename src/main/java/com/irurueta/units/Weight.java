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

import java.math.BigDecimal;

/**
 * Contains a weight value and unit.
 */
public class Weight extends Measurement<WeightUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value weight value.
     * @param unit  unit of weight.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Weight(final Number value, final WeightUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Weight() {
        super();
    }

    /**
     * Determines if two weights are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two measurements are equal or not.
     * @return true if provided weight is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(Measurement<WeightUnit> other, double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        // attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = WeightConverter.convert(
                other.getValue().doubleValue(), other.getUnit(),
                getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two weight values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final WeightUnit unit1,
            final double value2, final WeightUnit unit2,
            final WeightUnit resultUnit) {
        final double v1 = WeightConverter.convert(value1, unit1, resultUnit);
        final double v2 = WeightConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two weight values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final WeightUnit unit1,
            final Number value2, final WeightUnit unit2,
            final WeightUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two weights and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final Weight arg1, final Weight arg2, final Weight result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two weights.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned weight.
     * @return a new instance containing result.
     */
    public static Weight addAndReturnNew(
            final Weight arg1, final Weight arg2,
            final WeightUnit unit) {
        final Weight result = new Weight();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided weight value and unit and returns a new weight instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight addAndReturnNew(
            final double value, final WeightUnit unit,
            final WeightUnit resultUnit) {
        final Weight result = new Weight();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided weight value and unit and returns a new weight instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight addAndReturnNew(
            final Number value, final WeightUnit unit,
            final WeightUnit resultUnit) {
        final Weight result = new Weight();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided weight to current instance and returns a new weight.
     *
     * @param w    weight to be added.
     * @param unit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight addAndReturnNew(final Weight w, final WeightUnit unit) {
        return addAndReturnNew(this, w, unit);
    }

    /**
     * Adds provided weight value and unit and updates current weight instance.
     *
     * @param value weight value to be added.
     * @param unit  unit of weight value.
     */
    public void add(final double value, final WeightUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided weight value and unit and updates current weight instance.
     *
     * @param value weight value to be added.
     * @param unit  unit of weight value.
     */
    public void add(final Number value, final WeightUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided weight and updates current weight.
     *
     * @param weight weght to be added.
     */
    public void add(final Weight weight) {
        add(this, weight, this);
    }

    /**
     * Adds provided weight and stores the result into provided weight.
     *
     * @param w      weight to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Weight w, final Weight result) {
        add(this, w, result);
    }

    /**
     * Subtracts two weight values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final WeightUnit unit1,
            final double value2, final WeightUnit unit2,
            final WeightUnit resultUnit) {
        final double v1 = WeightConverter.convert(value1, unit1, resultUnit);
        final double v2 = WeightConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two weight values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final WeightUnit unit1,
            final Number value2, final WeightUnit unit2,
            final WeightUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two weights and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final Weight arg1, final Weight arg2,
            final Weight result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two weights.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned weight.
     * @return a new instance containing result.
     */
    public static Weight subtractAndReturnNew(
            final Weight arg1, final Weight arg2,
            final WeightUnit unit) {
        final Weight result = new Weight();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided weight value and unit and returns a new weight instance
     * using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight subtractAndReturnNew(
            final double value, final WeightUnit unit,
            final WeightUnit resultUnit) {
        final Weight result = new Weight();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided weight value and unit and returns a new weight instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight subtractAndReturnNew(
            final Number value, final WeightUnit unit,
            final WeightUnit resultUnit) {
        final Weight result = new Weight();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided weight from current instance and returns a new
     * weight.
     *
     * @param w    weight to be subtracted.
     * @param unit unit of returned weight.
     * @return a new weight containing result.
     */
    public Weight subtractAndReturnNew(
            final Weight w, final WeightUnit unit) {
        return subtractAndReturnNew(this, w, unit);
    }

    /**
     * Subtracts provided weight value and unit and updates current weight
     * instance.
     *
     * @param value weight value to be subtracted.
     * @param unit  unit of weight value.
     */
    public void subtract(final double value, final WeightUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided weight value and unit and updates current weight
     * instance.
     *
     * @param value weight value to be subtracted.
     * @param unit unit of weight value.
     */
    public void subtract(final Number value, final WeightUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided weight and updates current weight.
     *
     * @param weight weight to be subtracted.
     */
    public void subtract(final Weight weight) {
        subtract(this, weight, this);
    }

    /**
     * Subtracts provided weight and stores the result into provided
     * weight.
     *
     * @param w weight to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Weight w, final Weight result) {
        subtract(this, w, result);
    }
}
