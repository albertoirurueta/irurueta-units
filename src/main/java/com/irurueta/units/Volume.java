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
 * Contains a volume value and unit.
 */
public class Volume extends Measurement<VolumeUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value volume value.
     * @param unit  unit of volume.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public Volume(final Number value, final VolumeUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    Volume() {
        super();
    }

    /**
     * Determine if two volumes are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two measurements are equal or not.
     * @return true if provided volume is assumed to be equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(final Measurement<VolumeUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        // attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = VolumeConverter.convert(
                other.getValue().doubleValue(), other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Adds two volume values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final VolumeUnit unit1,
            final double value2, final VolumeUnit unit2,
            final VolumeUnit resultUnit) {
        final var v1 = VolumeConverter.convert(value1, unit1, resultUnit);
        final var v2 = VolumeConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two volume values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final VolumeUnit unit1,
            final Number value2, final VolumeUnit unit2,
            final VolumeUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two volumes and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(final Volume arg1, final Volume arg2, final Volume result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two volumes.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned volume.
     * @return a new instance containing result.
     */
    public static Volume addAndReturnNew(
            final Volume arg1, final Volume arg2, final VolumeUnit unit) {
        final var result = new Volume();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided volume value and unit and returns a new volume instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume addAndReturnNew(
            final double value, final VolumeUnit unit,
            final VolumeUnit resultUnit) {
        final var result = new Volume();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided volume value and unit and returns a new volume instance using
     * provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume addAndReturnNew(
            final Number value, final VolumeUnit unit,
            final VolumeUnit resultUnit) {
        final var result = new Volume();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided volume to current instance and returns a new volume.
     *
     * @param v    volume to be added.
     * @param unit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume addAndReturnNew(final Volume v, final VolumeUnit unit) {
        return addAndReturnNew(this, v, unit);
    }

    /**
     * Adds provided volume value and unit and updates current volume instance.
     *
     * @param value volume value to be added.
     * @param unit  unit of volume value.
     */
    public void add(final double value, final VolumeUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided volume value and unit and updates current volume instance.
     *
     * @param value volume value to be added.
     * @param unit  unit of volume value.
     */
    public void add(final Number value, final VolumeUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided volume and updates current volume.
     *
     * @param volume volume to be added.
     */
    public void add(final Volume volume) {
        add(this, volume, this);
    }

    /**
     * Adds provided volume and stores the result into provided volume.
     *
     * @param v      volume to be added.
     * @param result instance where result will be stored.
     */
    public void add(final Volume v, final Volume result) {
        add(this, v, result);
    }

    /**
     * Subtracts two volume values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final VolumeUnit unit1,
            final double value2, final VolumeUnit unit2,
            final VolumeUnit resultUnit) {
        final var v1 = VolumeConverter.convert(value1, unit1, resultUnit);
        final var v2 = VolumeConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two volume values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final VolumeUnit unit1,
            final Number value2, final VolumeUnit unit2,
            final VolumeUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two volumes and stores the result into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(final Volume arg1, final Volume arg2, final Volume result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two volumes.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned volume.
     * @return a new instance containing result.
     */
    public static Volume subtractAndReturnNew(final Volume arg1, final Volume arg2, final VolumeUnit unit) {
        final var result = new Volume();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided volume value and unit and returns a new volume instance
     * using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume subtractAndReturnNew(final double value, final VolumeUnit unit, final VolumeUnit resultUnit) {
        final var result = new Volume();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided volume value and unit and returns a new volume instance using
     * provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume subtractAndReturnNew(final Number value, final VolumeUnit unit, final VolumeUnit resultUnit) {
        final var result = new Volume();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided volume from current instance and returns a new
     * volume.
     *
     * @param v    volume to be subtracted.
     * @param unit unit of returned volume.
     * @return a new volume containing result.
     */
    public Volume subtractAndReturnNew(final Volume v, final VolumeUnit unit) {
        return subtractAndReturnNew(this, v, unit);
    }

    /**
     * Subtracts provided volume value and unit and updates current volume
     * instance.
     *
     * @param value volume value to be subtracted.
     * @param unit  unit of volume value.
     */
    public void subtract(final double value, final VolumeUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided volume value and unit and updates current volume
     * instance.
     *
     * @param value volume value to be subtracted.
     * @param unit  unit of volume value.
     */
    public void subtract(final Number value, final VolumeUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided volume and updates current volume.
     *
     * @param volume volume to be subtracted.
     */
    public void subtract(final Volume volume) {
        subtract(this, volume, this);
    }

    /**
     * Subtracts provided volume and stores the result into provided
     * volume.
     *
     * @param v      volume to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final Volume v, final Volume result) {
        subtract(this, v, result);
    }
}
