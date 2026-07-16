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
 * Contains a power value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Power extends Measurement<PowerUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value power value.
     * @param unit  unit of power.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Power(final Number value, final PowerUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Power() {
        super();
    }

    /**
     * Determines if two powers are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another power to compare.
     * @param tolerance amount of tolerance to determine whether two power instances are equal or not.
     * @return true if provided power is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<PowerUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = PowerConverter.convert(other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two power values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final PowerUnit unit1,
                             final double value2, final PowerUnit unit2,
                             final PowerUnit resultUnit) {
        final var v1 = PowerConverter.convert(value1, unit1, resultUnit);
        final var v2 = PowerConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two power values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final PowerUnit unit1,
                             final Number value2, final PowerUnit unit2,
                             final PowerUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two power instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Power arg1, final Power arg2, final Power result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two power instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned power.
     * @return a new instance containing result.
     */
    public static Power addAndReturnNew(final Power arg1, final Power arg2, final PowerUnit unit) {
        final var result = new Power();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided power value and unit and returns a new power instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned power.
     * @return a new power containing result.
     */
    public Power addAndReturnNew(
            final double value, final PowerUnit unit, final PowerUnit resultUnit) {
        final var result = new Power();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided power value and unit and returns a new power instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned power.
     * @return a new power containing result.
     */
    public Power addAndReturnNew(
            final Number value, final PowerUnit unit, final PowerUnit resultUnit) {
        final var result = new Power();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided power to current instance and returns a new power.
     *
     * @param p    power to be added.
     * @param unit unit of returned power.
     * @return a new power containing result.
     */
    public Power addAndReturnNew(final Power p, final PowerUnit unit) {
        return addAndReturnNew(this, p, unit);
    }

    /**
     * Adds provided power value and unit and updates current power instance.
     *
     * @param value power value to be added.
     * @param unit  unit of power value.
     */
    public void add(final double value, final PowerUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided power value and unit and updates current power instance.
     *
     * @param value power value to be added.
     * @param unit  unit of power value.
     */
    public void add(final Number value, final PowerUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided power and updates current power.
     *
     * @param power power to be added.
     */
    public void add(final Power power) {
        add(this, power, this);
    }

    /**
     * Adds provided power and stores the result into provided power.
     *
     * @param p      power to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Power p, final Power result) {
        add(this, p, result);
    }

    /**
     * Subtracts two power values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final PowerUnit unit1,
            final double value2, final PowerUnit unit2,
            final PowerUnit resultUnit) {
        final var v1 = PowerConverter.convert(value1, unit1, resultUnit);
        final var v2 = PowerConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two power values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final PowerUnit unit1,
            final Number value2, final PowerUnit unit2,
            final PowerUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two power instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Power arg1, final Power arg2, final Power result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two power instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned power.
     * @return a new instance containing result.
     */
    public static Power subtractAndReturnNew(
            final Power arg1, final Power arg2, final PowerUnit unit) {
        final var result = new Power();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided power value and unit and returns a nw power instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned power.
     * @return a new power containing result.
     */
    public Power subtractAndReturnNew(
            final double value, final PowerUnit unit, final PowerUnit resultUnit) {
        final var result = new Power();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided power value and unit and returns a new power instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned power.
     * @return a new power containing result.
     */
    public Power subtractAndReturnNew(
            final Number value, final PowerUnit unit, final PowerUnit resultUnit) {
        final var result = new Power();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided power to current instance and returns a new power instance.
     *
     * @param p    power to be subtracted.
     * @param unit unit of returned power.
     * @return a new power containing result.
     */
    public Power subtractAndReturnNew(final Power p, final PowerUnit unit) {
        return subtractAndReturnNew(this, p, unit);
    }

    /**
     * Subtracts provided power value and unit and updates current power instance.
     *
     * @param value power value to be subtracted.
     * @param unit  unit of power value.
     */
    public void subtract(final double value, final PowerUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided power value and unit and updates current power instance.
     *
     * @param value power value to be subtracted.
     * @param unit  unit of power value.
     */
    public void subtract(final Number value, final PowerUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided power and updates current power.
     *
     * @param power power to be subtracted.
     */
    public void subtract(final Power power) {
        subtract(this, power, this);
    }

    /**
     * Subtracts provided power and stores the result into provided power.
     *
     * @param p      power to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Power p, final Power result) {
        subtract(this, p, result);
    }
}
