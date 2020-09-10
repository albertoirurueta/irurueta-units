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
 * Contains a time value and unit.
 */
public class Time extends Measurement<TimeUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value time value.
     * @param unit  unit of distance.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    @SuppressWarnings("WeakerAccess")
    public Time(final Number value, final TimeUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Time() {
        super();
    }

    /**
     * Determines if two time instances are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another time to compare.
     * @param tolerance amount of tolerance to determine whether two time instances are equal or not.
     * @return true if provided time is assumed to be equal to this instance, false
     * otherwise.
     */
    @Override
    public boolean equals(final Measurement<TimeUnit> other,
                          final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = TimeConverter.convert(
                other.getValue().doubleValue(), other.getUnit(),
                getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two time values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(final double value1, final TimeUnit unit1,
                             final double value2, final TimeUnit unit2,
                             final TimeUnit resultUnit) {
        final double v1 = TimeConverter.convert(value1, unit1, resultUnit);
        final double v2 = TimeConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two time values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(final Number value1, final TimeUnit unit1,
                             final Number value2, final TimeUnit unit2,
                             final TimeUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two time instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final Time arg1, final Time arg2, final Time result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two time instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned time.
     * @return a new instance containing result.
     */
    public static Time addAndReturnNew(
            final Time arg1, final Time arg2, final TimeUnit unit) {
        final Time result = new Time();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided time value and unit and returns a new time instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned time.
     * @return a new time containing result.
     */
    public Time addAndReturnNew(
            final double value, final TimeUnit unit,
            final TimeUnit resultUnit) {
        final Time result = new Time();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided time value and unit and returns a new time instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned time.
     * @return a new time containing result.
     */
    public Time addAndReturnNew(
            final Number value, final TimeUnit unit,
            final TimeUnit resultUnit) {
        final Time result = new Time();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided time to current instance and returns a new time
     * instance.
     *
     * @param t    time to be added.
     * @param unit unit of returned time.
     * @return a new time containing result.
     */
    public Time addAndReturnNew(final Time t, final TimeUnit unit) {
        return addAndReturnNew(this, t, unit);
    }

    /**
     * Adds provided time value and unit and updates current time instance.
     *
     * @param value time value to be added.
     * @param unit  unit of time value.
     */
    public void add(final double value, final TimeUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided time value and unit and updates current time instance.
     *
     * @param value time value to be added.
     * @param unit  unit of time value.
     */
    public void add(final Number value, final TimeUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided time and updates current time instance.
     *
     * @param time time to be added.
     */
    public void add(final Time time) {
        add(this, time, this);
    }

    /**
     * Adds provided time and stores the result into provided time instance.
     *
     * @param t      time to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Time t, final Time result) {
        add(this, t, result);
    }

    /**
     * Subtracts two time values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final TimeUnit unit1,
            final double value2, final TimeUnit unit2,
            final TimeUnit resultUnit) {
        final double v1 = TimeConverter.convert(value1, unit1, resultUnit);
        final double v2 = TimeConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two time values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final TimeUnit unit1,
            final Number value2, final TimeUnit unit2,
            final TimeUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two time instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final Time arg1, final Time arg2, final Time result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two time instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned time.
     * @return a new instance containing result.
     */
    public static Time subtractAndReturnNew(
            final Time arg1, final Time arg2,
            final TimeUnit unit) {
        final Time result = new Time();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided time value and unit and returns a new time instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned time.
     * @return a new time containing result.
     */
    public Time subtractAndReturnNew(
            final double value, final TimeUnit unit,
            final TimeUnit resultUnit) {
        final Time result = new Time();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(),
                value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided time value and unit and returns a new time instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned time.
     * @return a new time containing result.
     */
    public Time subtractAndReturnNew(
            final Number value, final TimeUnit unit,
            final TimeUnit resultUnit) {
        final Time result = new Time();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided time to current instance and returns a new
     * instance.
     *
     * @param t    time to be subtracted.
     * @param unit unit of returned time.
     * @return a new time containing result.
     */
    public Time subtractAndReturnNew(final Time t, final TimeUnit unit) {
        return subtractAndReturnNew(this, t, unit);
    }

    /**
     * Subtracts provided time value and unit and updates current time instance.
     *
     * @param value time value to be subtracted.
     * @param unit  unit of time value.
     */
    public void subtract(final double value, final TimeUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided time value and unit and updates current time instance.
     *
     * @param value time value to be subtracted.
     * @param unit  unit of time value.
     */
    public void subtract(final Number value, final TimeUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtract provided time and updates current time.
     *
     * @param time time to be subtracted.
     */
    public void subtract(final Time time) {
        subtract(this, time, this);
    }

    /**
     * Subtracts provided time and stores the result into provided time.
     *
     * @param t      time to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Time t, final Time result) {
        subtract(this, t, result);
    }
}
