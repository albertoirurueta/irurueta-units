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
 * Contains an energy value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Energy extends Measurement<EnergyUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value energy value.
     * @param unit  unit of energy.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Energy(final Number value, final EnergyUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Energy() {
        super();
    }

    /**
     * Determines if two energies are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another energy to compare.
     * @param tolerance amount of tolerance to determine whether two energy instances are equal or not.
     * @return true if provided energy is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<EnergyUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = EnergyConverter.convert(
                other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two energy values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final EnergyUnit unit1,
                             final double value2, final EnergyUnit unit2,
                             final EnergyUnit resultUnit) {
        final var v1 = EnergyConverter.convert(value1, unit1, resultUnit);
        final var v2 = EnergyConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two energy values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final EnergyUnit unit1,
                             final Number value2, final EnergyUnit unit2,
                             final EnergyUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two energy instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Energy arg1, final Energy arg2, final Energy result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two energy instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned energy.
     * @return a new instance containing result.
     */
    public static Energy addAndReturnNew(final Energy arg1, final Energy arg2, final EnergyUnit unit) {
        final var result = new Energy();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided energy value and unit and returns a new energy instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy addAndReturnNew(
            final double value, final EnergyUnit unit, final EnergyUnit resultUnit) {
        final var result = new Energy();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided energy value and unit and returns a new energy instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy addAndReturnNew(
            final Number value, final EnergyUnit unit, final EnergyUnit resultUnit) {
        final var result = new Energy();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided energy to current instance and returns a new energy.
     *
     * @param e    energy to be added.
     * @param unit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy addAndReturnNew(final Energy e, final EnergyUnit unit) {
        return addAndReturnNew(this, e, unit);
    }

    /**
     * Adds provided energy value and unit and updates current energy instance.
     *
     * @param value energy value to be added.
     * @param unit  unit of energy value.
     */
    public void add(final double value, final EnergyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided energy value and unit and updates current energy instance.
     *
     * @param value energy value to be added.
     * @param unit  unit of energy value.
     */
    public void add(final Number value, final EnergyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided energy and updates current energy.
     *
     * @param energy energy to be added.
     */
    public void add(final Energy energy) {
        add(this, energy, this);
    }

    /**
     * Adds provided energy and stores the result into provided energy.
     *
     * @param e      energy to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Energy e, final Energy result) {
        add(this, e, result);
    }

    /**
     * Subtracts two energy values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final EnergyUnit unit1,
            final double value2, final EnergyUnit unit2,
            final EnergyUnit resultUnit) {
        final var v1 = EnergyConverter.convert(value1, unit1, resultUnit);
        final var v2 = EnergyConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two energy values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final EnergyUnit unit1,
            final Number value2, final EnergyUnit unit2,
            final EnergyUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two energy instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Energy arg1, final Energy arg2, final Energy result) {
        result.setValue(subtract(
                arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two energy instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned energy.
     * @return a new instance containing result.
     */
    public static Energy subtractAndReturnNew(
            final Energy arg1, final Energy arg2, final EnergyUnit unit) {
        final var result = new Energy();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided energy value and unit and returns a nw energy instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy subtractAndReturnNew(
            final double value, final EnergyUnit unit, final EnergyUnit resultUnit) {
        final var result = new Energy();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided energy value and unit and returns a new energy instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy subtractAndReturnNew(
            final Number value, final EnergyUnit unit, final EnergyUnit resultUnit) {
        final var result = new Energy();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided energy to current instance and returns a new energy instance.
     *
     * @param e    energy to be subtracted.
     * @param unit unit of returned energy.
     * @return a new energy containing result.
     */
    public Energy subtractAndReturnNew(final Energy e, final EnergyUnit unit) {
        return subtractAndReturnNew(this, e, unit);
    }

    /**
     * Subtracts provided energy value and unit and updates current energy instance.
     *
     * @param value energy value to be subtracted.
     * @param unit  unit of energy value.
     */
    public void subtract(final double value, final EnergyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided energy value and unit and updates current energy instance.
     *
     * @param value energy value to be subtracted.
     * @param unit  unit of energy value.
     */
    public void subtract(final Number value, final EnergyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided energy and updates current energy.
     *
     * @param energy energy to be subtracted.
     */
    public void subtract(final Energy energy) {
        subtract(this, energy, this);
    }

    /**
     * Subtracts provided energy and stores the result into provided energy.
     *
     * @param e      energy to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Energy e, final Energy result) {
        subtract(this, e, result);
    }
}
