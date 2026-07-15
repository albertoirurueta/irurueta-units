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
 * Contains a pressure value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Pressure extends Measurement<PressureUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value pressure value.
     * @param unit  unit of pressure.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Pressure(final Number value, final PressureUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Pressure() {
        super();
    }

    /**
     * Determines if two pressures are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another pressure to compare.
     * @param tolerance amount of tolerance to determine whether two pressure instances are equal or not.
     * @return true if provided pressure is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<PressureUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = PressureConverter.convert(
                other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two pressure values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final PressureUnit unit1,
                             final double value2, final PressureUnit unit2,
                             final PressureUnit resultUnit) {
        final var v1 = PressureConverter.convert(value1, unit1, resultUnit);
        final var v2 = PressureConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two pressure values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final PressureUnit unit1,
                             final Number value2, final PressureUnit unit2,
                             final PressureUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two pressure instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Pressure arg1, final Pressure arg2, final Pressure result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two pressure instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned pressure.
     * @return a new instance containing result.
     */
    public static Pressure addAndReturnNew(final Pressure arg1, final Pressure arg2, final PressureUnit unit) {
        final var result = new Pressure();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided pressure value and unit and returns a new pressure instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure addAndReturnNew(
            final double value, final PressureUnit unit, final PressureUnit resultUnit) {
        final var result = new Pressure();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided pressure value and unit and returns a new pressure instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure addAndReturnNew(
            final Number value, final PressureUnit unit, final PressureUnit resultUnit) {
        final var result = new Pressure();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided pressure to current instance and returns a new pressure.
     *
     * @param p    pressure to be added.
     * @param unit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure addAndReturnNew(final Pressure p, final PressureUnit unit) {
        return addAndReturnNew(this, p, unit);
    }

    /**
     * Adds provided pressure value and unit and updates current pressure instance.
     *
     * @param value pressure value to be added.
     * @param unit  unit of pressure value.
     */
    public void add(final double value, final PressureUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided pressure value and unit and updates current pressure instance.
     *
     * @param value pressure value to be added.
     * @param unit  unit of pressure value.
     */
    public void add(final Number value, final PressureUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided pressure and updates current pressure.
     *
     * @param pressure pressure to be added.
     */
    public void add(final Pressure pressure) {
        add(this, pressure, this);
    }

    /**
     * Adds provided pressure and stores the result into provided pressure.
     *
     * @param p      pressure to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Pressure p, final Pressure result) {
        add(this, p, result);
    }

    /**
     * Subtracts two pressure values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final PressureUnit unit1,
            final double value2, final PressureUnit unit2,
            final PressureUnit resultUnit) {
        final var v1 = PressureConverter.convert(value1, unit1, resultUnit);
        final var v2 = PressureConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two pressure values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final PressureUnit unit1,
            final Number value2, final PressureUnit unit2,
            final PressureUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two pressure instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Pressure arg1, final Pressure arg2, final Pressure result) {
        result.setValue(subtract(
                arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two pressure instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned pressure.
     * @return a new instance containing result.
     */
    public static Pressure subtractAndReturnNew(
            final Pressure arg1, final Pressure arg2, final PressureUnit unit) {
        final var result = new Pressure();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided pressure value and unit and returns a nw pressure instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure subtractAndReturnNew(
            final double value, final PressureUnit unit, final PressureUnit resultUnit) {
        final var result = new Pressure();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided pressure value and unit and returns a new pressure instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure subtractAndReturnNew(
            final Number value, final PressureUnit unit, final PressureUnit resultUnit) {
        final var result = new Pressure();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided pressure to current instance and returns a new pressure instance.
     *
     * @param p    pressure to be subtracted.
     * @param unit unit of returned pressure.
     * @return a new pressure containing result.
     */
    public Pressure subtractAndReturnNew(final Pressure p, final PressureUnit unit) {
        return subtractAndReturnNew(this, p, unit);
    }

    /**
     * Subtracts provided pressure value and unit and updates current pressure instance.
     *
     * @param value pressure value to be subtracted.
     * @param unit  unit of pressure value.
     */
    public void subtract(final double value, final PressureUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided pressure value and unit and updates current pressure instance.
     *
     * @param value pressure value to be subtracted.
     * @param unit  unit of pressure value.
     */
    public void subtract(final Number value, final PressureUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided pressure and updates current pressure.
     *
     * @param pressure pressure to be subtracted.
     */
    public void subtract(final Pressure pressure) {
        subtract(this, pressure, this);
    }

    /**
     * Subtracts provided pressure and stores the result into provided pressure.
     *
     * @param p      pressure to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Pressure p, final Pressure result) {
        subtract(this, p, result);
    }
}
