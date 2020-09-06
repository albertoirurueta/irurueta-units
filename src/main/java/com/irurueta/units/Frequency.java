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
 * Contains a frequency value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class Frequency extends Measurement<FrequencyUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value frequency value.
     * @param unit  unit of frequency.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Frequency(final Number value, final FrequencyUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Frequency() {
        super();
    }

    /**
     * Determines if two frequencies are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two frequency instances are equal or not.
     * @return true if provided frequency is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(
            final Measurement<FrequencyUnit> other,
            final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        final double otherValue = FrequencyConverter.convert(
                other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two frequency values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final FrequencyUnit unit1,
            final double value2, final FrequencyUnit unit2,
            final FrequencyUnit resultUnit) {
        final double v1 = FrequencyConverter.convert(value1, unit1, resultUnit);
        final double v2 = FrequencyConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two frequency values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final FrequencyUnit unit1,
            final Number value2, final FrequencyUnit unit2,
            final FrequencyUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two frequency instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final Frequency arg1, final Frequency arg2,
            final Frequency result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two frequency instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned frequency.
     * @return a new instance containing result.
     */
    public static Frequency addAndReturnNew(
            final Frequency arg1, final Frequency arg2,
            final FrequencyUnit unit) {
        final Frequency result = new Frequency();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(
            final double value, final FrequencyUnit unit,
            final FrequencyUnit resultUnit) {
        final Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided frequency value and unit and returns a new frequency instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(
            final Number value, final FrequencyUnit unit,
            final FrequencyUnit resultUnit) {
        final Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided frequency to current instance and returns a new frequency instance.
     *
     * @param f    frequency to be added.
     * @param unit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(
            final Frequency f, final FrequencyUnit unit) {
        return addAndReturnNew(this, f, unit);
    }

    /**
     * Adds provided frequency value and unit and updates current frequency instance.
     *
     * @param value frequency value to be added.
     * @param unit  unit of frequency value.
     */
    public void add(final double value, final FrequencyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided frequency value and unit and updates current frequency instance.
     *
     * @param value frequency value to be added.
     * @param unit  unit of frequency value.
     */
    public void add(final Number value, final FrequencyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided frequency and updates current frequency instance.
     *
     * @param frequency frequency to be added.
     */
    public void add(final Frequency frequency) {
        add(this, frequency, this);
    }

    /**
     * Adds provided frequency and stores the result into provided frequency instance.
     *
     * @param f      frequency to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Frequency f, final Frequency result) {
        add(this, f, result);
    }

    /**
     * Subtracts two frequency values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final FrequencyUnit unit1,
            final double value2, final FrequencyUnit unit2,
            final FrequencyUnit resultUnit) {
        final double v1 = FrequencyConverter.convert(value1, unit1, resultUnit);
        final double v2 = FrequencyConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two frequency values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final FrequencyUnit unit1,
            final Number value2, final FrequencyUnit unit2,
            final FrequencyUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two frequency instances and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final Frequency arg1, final Frequency arg2,
            final Frequency result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two frequency instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned frequency.
     * @return a new instance containing result.
     */
    public static Frequency subtractAndReturnNew(
            final Frequency arg1, final Frequency arg2,
            final FrequencyUnit unit) {
        final Frequency result = new Frequency();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(
            final double value, final FrequencyUnit unit,
            final FrequencyUnit resultUnit) {
        final Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(
            final Number value, final FrequencyUnit unit,
            final FrequencyUnit resultUnit) {
        final Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided frequency from current instance and returns a new instance.
     *
     * @param f    frequency to be subtracted.
     * @param unit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(
            final Frequency f, final FrequencyUnit unit) {
        return subtractAndReturnNew(this, f, unit);
    }

    /**
     * Subtracts provided frequency value and unit and updates current frequency
     * instance.
     *
     * @param value frequency value to be subtracted.
     * @param unit  unit of frequency value.
     */
    public void subtract(final double value, final FrequencyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided frequency value and unit and updates current frequency instance.
     *
     * @param value frequency value to be subtracted.
     * @param unit  unit of frequency value.
     */
    public void subtract(final Number value, final FrequencyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided frequency and updates current frequency.
     *
     * @param frequency frequency to be subtracted.
     */
    public void subtract(final Frequency frequency) {
        subtract(this, frequency, this);
    }

    /**
     * Subtracts provided frequency and stores the result into provided frequency.
     *
     * @param f      frequency to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Frequency f, final Frequency result) {
        subtract(this, f, result);
    }
}
