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
 * Contains a temperature value and unit.
 */
public class Temperature extends Measurement<TemperatureUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value temperature value.
     * @param unit  unit of temperature.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Temperature(final Number value, final TemperatureUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Temperature() {
        super();
    }

    /**
     * Determines if two temperatures are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two temperature instances are equal or not.
     * @return true if provided temperature is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(final Measurement<TemperatureUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        // attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = TemperatureConverter.convert(other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two temperature values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final TemperatureUnit unit1,
            final double value2, final TemperatureUnit unit2,
            final TemperatureUnit resultUnit) {
        // 1st convert both values to common unit1
        final var v2 = TemperatureConverter.convert(value2, unit2, unit1);

        // then add and convert to result unit
        return TemperatureConverter.convert(value1 + v2, unit1, resultUnit);
    }

    /**
     * Adds two temperature values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final TemperatureUnit unit1,
            final Number value2, final TemperatureUnit unit2,
            final TemperatureUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two temperature instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Temperature arg1, final Temperature arg2, final Temperature result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two temperature instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned temperature.
     * @return a new instance containing result.
     */
    public static Temperature addAndReturnNew(final Temperature arg1, final Temperature arg2, final TemperatureUnit unit) {
        final var result = new Temperature();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided temperature value and unit and returns a new temperature
     * instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature addAndReturnNew(
            final double value, final TemperatureUnit unit, final TemperatureUnit resultUnit) {
        final var result = new Temperature();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided temperature value and unit and returns a new temperature
     * instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature addAndReturnNew(
            final Number value, final TemperatureUnit unit, final TemperatureUnit resultUnit) {
        final var result = new Temperature();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided temperature to current instance and returns a new temperature
     * instance.
     *
     * @param t    temperature to be added.
     * @param unit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature addAndReturnNew(final Temperature t, final TemperatureUnit unit) {
        return addAndReturnNew(this, t, unit);
    }

    /**
     * Adds provided temperature value and unit and updates current temperature
     * instance.
     *
     * @param value temperature value to be added.
     * @param unit  unit of temperature value.
     */
    public void add(final double value, final TemperatureUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided temperature value and unit and updates current temperature
     * instance.
     *
     * @param value temperature value to be added.
     * @param unit  unit of temperature value.
     */
    public void add(final Number value, final TemperatureUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided temperature and updates current temperature instance.
     *
     * @param temperature temperature to be added.
     */
    public void add(final Temperature temperature) {
        add(this, temperature, this);
    }

    /**
     * Adds provided temperature and stores the result into provided temperature
     * instance.
     *
     * @param t      temperature to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Temperature t, final Temperature result) {
        add(this, t, result);
    }

    /**
     * Subtracts two temperature values and unit and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final TemperatureUnit unit1,
            final double value2, final TemperatureUnit unit2,
            final TemperatureUnit resultUnit) {
        // 1st convert both values to common uni1
        final var v2 = TemperatureConverter.convert(value2, unit2, unit1);

        // then subtract and convert to result unit
        return TemperatureConverter.convert(value1 - v2, unit1, resultUnit);
    }

    /**
     * Subtracts two temperature values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final TemperatureUnit unit1,
            final Number value2, final TemperatureUnit unit2,
            final TemperatureUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two temperature instances and stores the result into provided
     * instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Temperature arg1, final Temperature arg2, final Temperature result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two temperature instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned temperature.
     * @return a new instance containing result.
     */
    public static Temperature subtractAndReturnNew(
            final Temperature arg1, final Temperature arg2, final TemperatureUnit unit) {
        final var result = new Temperature();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided temperature value and unit and returns a new temperature
     * instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature subtractAndReturnNew(
            final double value, final TemperatureUnit unit, final TemperatureUnit resultUnit) {
        final var result = new Temperature();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided temperature value and unit and returns a new temperature
     * instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature subtractAndReturnNew(
            final Number value, final TemperatureUnit unit, final TemperatureUnit resultUnit) {
        final var result = new Temperature();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided temperature from current instance and returns a new
     * instance.
     *
     * @param t    temperature to be subtracted.
     * @param unit unit of returned temperature.
     * @return a new temperature containing result.
     */
    public Temperature subtractAndReturnNew(final Temperature t, final TemperatureUnit unit) {
        return subtractAndReturnNew(this, t, unit);
    }

    /**
     * Subtracts provided temperature value and unit and updates current
     * temperature instance.
     *
     * @param value temperature value to be subtracted.
     * @param unit  unit of temperature value.
     */
    public void subtract(final double value, final TemperatureUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided temperature value and unit and updates current
     * temperature instance.
     *
     * @param value temperature value to be subtracted.
     * @param unit  unit of temperature value.
     */
    public void subtract(final Number value, final TemperatureUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided temperature and updates current temperature.
     *
     * @param temperature temperature to be subtracted.
     */
    public void subtract(final Temperature temperature) {
        subtract(this, temperature, this);
    }

    /**
     * Subtracts provided temperature and stores the result into provided
     * temperature.
     *
     * @param t      temperature to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Temperature t, final Temperature result) {
        subtract(this, t, result);
    }
}
