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
public class Frequency extends Measurement<FrequencyUnit> {

    /**
     * Constructor with value and unit.
     * @param value frequency value.
     * @param unit unit of frequency.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Frequency(Number value, FrequencyUnit unit) throws IllegalArgumentException {
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
     * @param other another measurement to compare.
     * @param tolerance true if provided measurement is assumed to be equal to this
     *                  instance up to provided tolerance.
     * @return true if provided frequency is assumed to be equal to this instance,
     * false otherwise.
     */
    @Override
    public boolean equals(Measurement<FrequencyUnit> other, double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        //attempt conversion to common units
        if (other == null) {
            return false;
        }

        double otherValue = FrequencyConverter.convert(other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return (Math.abs(getValue().doubleValue() - otherValue)) <= tolerance;
    }

    /**
     * Adds two frequency values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(double value1, FrequencyUnit unit1,
                             double value2, FrequencyUnit unit2,
                             FrequencyUnit resultUnit) {
        double v1 = FrequencyConverter.convert(value1, unit1, resultUnit);
        double v2 = FrequencyConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two frequency values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(Number value1, FrequencyUnit unit1,
                             Number value2, FrequencyUnit unit2,
                             FrequencyUnit resultUnit) {
        return new BigDecimal(add(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two frequency instances and stores the result into provided instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(Frequency arg1, Frequency arg2, Frequency result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two frequency instances.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned frequency.
     * @return a new instance containing result.
     */
    public static Frequency addAndReturnNew(Frequency arg1, Frequency arg2,
                                            FrequencyUnit unit) {
        Frequency result = new Frequency();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(double value, FrequencyUnit unit,
                                     FrequencyUnit resultUnit) {
        Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Adds provided frequency value and unit and returns a new frequency instance using
     * provided unit.
     * @param value value to be added.
     * @param unit unit of value to be added.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(Number value, FrequencyUnit unit,
                                     FrequencyUnit resultUnit) {
        Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided frequency to current instance and returns a new frequency instance.
     * @param f frequency to be added.
     * @param unit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency addAndReturnNew(Frequency f, FrequencyUnit unit) {
        return addAndReturnNew(this, f, unit);
    }

    /**
     * Adds provided frequency value and unit and updates current frequency instance.
     * @param value frequency value to be added.
     * @param unit unit of frequency value.
     */
    public void add(double value, FrequencyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided frequency value and unit and updates current frequency instance.
     * @param value frequency value to be added.
     * @param unit unit of frequency value.
     */
    public void add(Number value, FrequencyUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided frequency and updates current frequency instance.
     * @param frequency frequency to be added.
     */
    public void add(Frequency frequency) {
        add(this, frequency, this);
    }

    /**
     * Adds provided frequency and stores the result into provided frequency instance.
     * @param f frequency to be added.
     * @param result instance where result will be stored.
     */
    public void add(Frequency f, Frequency result) {
        add(this, f, result);
    }

    /**
     * Subtracts two frequency values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(double value1, FrequencyUnit unit1,
                                  double value2, FrequencyUnit unit2,
                                  FrequencyUnit resultUnit) {
        double v1 = FrequencyConverter.convert(value1, unit1, resultUnit);
        double v2 = FrequencyConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two frequency values and units and returns the result.
     * @param value1 1st argument value.
     * @param unit1 1st argument unit.
     * @param value2 2nd argument value.
     * @param unit2 2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(Number value1, FrequencyUnit unit1,
                                  Number value2, FrequencyUnit unit2,
                                  FrequencyUnit resultUnit) {
        return new BigDecimal(subtract(value1.doubleValue(), unit1,
                value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two frequency instances and stores the result into provided instance.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(Frequency arg1, Frequency arg2, Frequency result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(),
                arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtract two frequency instances.
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned frequency.
     * @return a new instance containing result.
     */
    public static Frequency subtractAndReturnNew(Frequency arg1, Frequency arg2,
                                                 FrequencyUnit unit) {
        Frequency result = new Frequency();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(double value, FrequencyUnit unit,
                                          FrequencyUnit resultUnit) {
        Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit,
                resultUnit));
        return result;
    }

    /**
     * Subtracts provided frequency value and unit and returns a new frequency instance
     * using provided unit.
     * @param value value to be subtracted.
     * @param unit unit of value to be subtracted.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(Number value, FrequencyUnit unit,
                                          FrequencyUnit resultUnit) {
        Frequency result = new Frequency();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided frequency to current instance and returns a new instance.
     * @param f frequency to be subtracted.
     * @param unit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public Frequency subtractAndReturnNew(Frequency f, FrequencyUnit unit) {
        return subtractAndReturnNew(this, f, unit);
    }

    /**
     * Subtracts provided frequency value and unit and updates current frequency
     * instance.
     * @param value frequency value to be subtracted.
     * @param unit unit of frequency value.
     */
    public void subtract(double value, FrequencyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided frequency value and unit and updates current frequency instance.
     * @param value frequency value to be subtracted.
     * @param unit unit of time value.
     */
    public void subtract(Number value, FrequencyUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtract provided frequency and updates current frequency.
     * @param frequency frequency to be subtracted.
     */
    public void subtract(Frequency frequency) {
        subtract(this, frequency, this);
    }

    /**
     * Subtracts provided frequency and stores the result into provided frequency.
     * @param f frequency to be subtracted.
     * @param result instane where result will be stored.
     */
    public void subtract(Frequency f, Frequency result) {
        subtract(this, f, result);
    }
}
