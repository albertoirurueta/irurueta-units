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
 * Contains a magnetic flux density value and unit.
 */
public class MagneticFluxDensity extends Measurement<MagneticFluxDensityUnit> {

    /**
     * Constructor with value and unit.
     *
     * @param value magnetic flux density value.
     * @param unit  unit of magnetic flux density.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    public MagneticFluxDensity(final Number value, final MagneticFluxDensityUnit unit) {
        super(value, unit);
    }

    /**
     * Constructor.
     */
    MagneticFluxDensity() {
        super();
    }

    /**
     * Determines if two magnetic flux densities are equal up to a certain tolerance.
     * If needed, this method attempts unit conversion to compare both objects.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two magnetic flux density instances are equal or not.
     * @return true if provided magnetic flux density is assumed ot be equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(final Measurement<MagneticFluxDensityUnit> other, final double tolerance) {
        if (super.equals(other, tolerance)) {
            return true;
        }

        // attempt conversion to common units
        if (other == null) {
            return false;
        }

        final var otherValue = MagneticFluxDensityConverter.convert(other.getValue().doubleValue(),
                other.getUnit(), getUnit());
        return Math.abs(getValue().doubleValue() - otherValue) <= tolerance;
    }

    /**
     * Ads two magnetic flux density values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static double add(
            final double value1, final MagneticFluxDensityUnit unit1,
            final double value2, final MagneticFluxDensityUnit unit2,
            final MagneticFluxDensityUnit resultUnit) {
        final var v1 = MagneticFluxDensityConverter.convert(value1, unit1, resultUnit);
        final var v2 = MagneticFluxDensityConverter.convert(value2, unit2, resultUnit);
        return v1 + v2;
    }

    /**
     * Adds two magnetic flux density values and units and returns the result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of addition.
     */
    public static Number add(
            final Number value1, final MagneticFluxDensityUnit unit1,
            final Number value2, final MagneticFluxDensityUnit unit2,
            final MagneticFluxDensityUnit resultUnit) {
        return BigDecimal.valueOf(add(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Adds two magnetic flux density instances and stores the result into
     * provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void add(
            final MagneticFluxDensity arg1, final MagneticFluxDensity arg2, final MagneticFluxDensity result) {
        result.setValue(add(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Adds two magnetic flux density instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned magnetic flux density.
     * @return a new instance containing result.
     */
    public static MagneticFluxDensity addAndReturnNew(
            final MagneticFluxDensity arg1, final MagneticFluxDensity arg2, final MagneticFluxDensityUnit unit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(unit);
        add(arg1, arg2, result);
        return result;
    }

    /**
     * Adds provided magnetic flux density value and unit and returns a new
     * magnetic flux density instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned magnetic flux density.
     * @return a new magnetic flux density containing result.
     */
    public MagneticFluxDensity addAndReturnNew(
            final double value, final MagneticFluxDensityUnit unit, final MagneticFluxDensityUnit resultUnit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(resultUnit);
        result.setValue(add(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided magnetic flux density value and unit and returns a new
     * magnetic flux density instance using provided unit.
     *
     * @param value      value to be added.
     * @param unit       unit of value to be added.
     * @param resultUnit unit of returned frequency.
     * @return a new frequency containing result.
     */
    public MagneticFluxDensity addAndReturnNew(
            final Number value, final MagneticFluxDensityUnit unit, final MagneticFluxDensityUnit resultUnit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(resultUnit);
        result.setValue(add(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Adds provided magnetic flux density to current instance and returns
     * a new magnetic flux density instance.
     *
     * @param b    magnetic flux density to be added.
     * @param unit unit of returned magnetic flux density.
     * @return a new magnetic flux density containing result.
     */
    public MagneticFluxDensity addAndReturnNew(final MagneticFluxDensity b, final MagneticFluxDensityUnit unit) {
        return addAndReturnNew(this, b, unit);
    }

    /**
     * Adds provided magnetic flux density value and unit and updates current
     * magnetic flux density instance.
     *
     * @param value magnetic flux density value to be added.
     * @param unit  unit of magnetic flux density value.
     */
    public void add(final double value, final MagneticFluxDensityUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided magnetic flux density value and unit and updates current
     * magnetic flux density instance.
     *
     * @param value magnetic flux density value to be added.
     * @param unit  unit of magnetic flux density value.
     */
    public void add(final Number value, final MagneticFluxDensityUnit unit) {
        setValue(add(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Adds provided magnetic flux density and updates current magnetic
     * flux density instance.
     *
     * @param magneticFluxDensity magnetic flux density to be added.
     */
    public void add(final MagneticFluxDensity magneticFluxDensity) {
        add(this, magneticFluxDensity, this);
    }

    /**
     * Adds provided magnetic flux density and stores the result into
     * provided magnetic flux density instance.
     *
     * @param b      magnetic flux density to be added.
     * @param result instance where result will be stored.
     */
    public void add(final MagneticFluxDensity b, final MagneticFluxDensity result) {
        add(this, b, result);
    }

    /**
     * Subtracts two magnetic flux density values and units and returns the
     * result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static double subtract(
            final double value1, final MagneticFluxDensityUnit unit1,
            final double value2, final MagneticFluxDensityUnit unit2,
            final MagneticFluxDensityUnit resultUnit) {
        final var v1 = MagneticFluxDensityConverter.convert(value1, unit1, resultUnit);
        final var v2 = MagneticFluxDensityConverter.convert(value2, unit2, resultUnit);
        return v1 - v2;
    }

    /**
     * Subtracts two magnetic flux density values and units and returns the
     * result.
     *
     * @param value1     1st argument value.
     * @param unit1      1st argument unit.
     * @param value2     2nd argument value.
     * @param unit2      2nd argument unit.
     * @param resultUnit unit of result to be returned.
     * @return result of subtraction.
     */
    public static Number subtract(
            final Number value1, final MagneticFluxDensityUnit unit1,
            final Number value2, final MagneticFluxDensityUnit unit2,
            final MagneticFluxDensityUnit resultUnit) {
        return BigDecimal.valueOf(subtract(value1.doubleValue(), unit1, value2.doubleValue(), unit2, resultUnit));
    }

    /**
     * Subtracts two magnetic flux density instances and stores the result
     * into provided instance.
     *
     * @param arg1   1st argument.
     * @param arg2   2nd argument.
     * @param result instance where result will be stored.
     */
    public static void subtract(
            final MagneticFluxDensity arg1, final MagneticFluxDensity arg2, final MagneticFluxDensity result) {
        result.setValue(subtract(arg1.getValue(), arg1.getUnit(), arg2.getValue(), arg2.getUnit(), result.getUnit()));
    }

    /**
     * Subtracts two magnetic flux density instances.
     *
     * @param arg1 1st argument.
     * @param arg2 2nd argument.
     * @param unit unit of returned magnetic flux density.
     * @return a new instance containing result.
     */
    public static MagneticFluxDensity subtractAndReturnNew(
            final MagneticFluxDensity arg1, final MagneticFluxDensity arg2, final MagneticFluxDensityUnit unit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(unit);
        subtract(arg1, arg2, result);
        return result;
    }

    /**
     * Subtracts provided magnetic flux density value and unit and returns a
     * new magnetic flux density instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned magnetic flux density.
     * @return a new magnetic flux density containing result.
     */
    public MagneticFluxDensity subtractAndReturnNew(
            final double value, final MagneticFluxDensityUnit unit, final MagneticFluxDensityUnit resultUnit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue().doubleValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided magnetic flux density value and unit and returns a
     * new magnetic flux density instance using provided unit.
     *
     * @param value      value to be subtracted.
     * @param unit       unit of value to be subtracted.
     * @param resultUnit unit of returned magnetic flux density.
     * @return a new magnetic flux density containing result.
     */
    public MagneticFluxDensity subtractAndReturnNew(
            final Number value, final MagneticFluxDensityUnit unit, final MagneticFluxDensityUnit resultUnit) {
        final var result = new MagneticFluxDensity();
        result.setUnit(resultUnit);
        result.setValue(subtract(getValue(), getUnit(), value, unit, resultUnit));
        return result;
    }

    /**
     * Subtracts provided magnetic flux density from current instance and
     * returns a new instance.
     *
     * @param b    magnetic flux density to be subtracted.
     * @param unit unit of returned magnetic flux density.
     * @return a new magnetic flux density containing result.
     */
    public MagneticFluxDensity subtractAndReturnNew(final MagneticFluxDensity b, final MagneticFluxDensityUnit unit) {
        return subtractAndReturnNew(this, b, unit);
    }

    /**
     * Subtracts provided magnetic flux density value and unit and updates
     * current magnetic flux density instance.
     *
     * @param value magnetic flux density value to be subtracted.
     * @param unit  unit of magnetic flux density value.
     */
    public void subtract(final double value, final MagneticFluxDensityUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided magnetic flux density value and unit and update current
     * magnetic flux density instance.
     *
     * @param value magnetic flux density value to be subtracted.
     * @param unit  unit of magnetic flux density value.
     */
    public void subtract(final Number value, final MagneticFluxDensityUnit unit) {
        setValue(subtract(getValue(), getUnit(), value, unit, getUnit()));
    }

    /**
     * Subtracts provided magnetic flux density and updates current magnetic
     * flux density.
     *
     * @param b magnetic flux density to be subtracted.
     */
    public void subtract(final MagneticFluxDensity b) {
        subtract(this, b, this);
    }

    /**
     * Subtracts provided magnetic flux density and stores the result into
     * provided magnetic flux density.
     *
     * @param b      magnetic flux density to be subtracted.
     * @param result instance where result will be stored.
     */
    public void subtract(final MagneticFluxDensity b, final MagneticFluxDensity result) {
        subtract(this, b, result);
    }
}
