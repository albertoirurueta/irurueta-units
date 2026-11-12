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

import java.io.Serializable;

/**
 * Base class to define a measurement unit and value.
 *
 * @param <T> a measurement unit.
 */
public abstract class Measurement<T extends Enum<?>> implements Serializable {

    /**
     * Initial value used when computing hash codes.
     */
    private static final int HASH_SEED = 7;

    /**
     * Multiplier used when computing hash codes.
     */
    private static final int HASH_MULTIPLIER = 19;

    /**
     * Measurement value.
     */
    private Number value;

    /**
     * Measurement unit.
     */
    private T unit;

    /**
     * Constructor.
     *
     * @param initialValue measurement value.
     * @param initialUnit  measurement unit.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    protected Measurement(final Number initialValue, final T initialUnit) {
        this(validate(initialValue, initialUnit));
    }

    /**
     * Internal constructor with already validated arguments.
     *
     * @param arguments validated (not null) measurement value and unit.
     */
    private Measurement(final ValidArguments<T> arguments) {
        value = arguments.value();
        unit = arguments.unit();
    }

    /**
     * Constructor.
     */
    Measurement() {
    }

    /**
     * Checks that provided measurement value and unit are not null.
     * Validation is performed here (instead of directly within the constructor) so that no exception
     * is thrown from the constructor body itself.
     *
     * @param initialValue measurement value to be checked.
     * @param initialUnit  measurement unit to be checked.
     * @param <U>          type of measurement unit.
     * @return validated measurement value and unit.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    private static <U extends Enum<?>> ValidArguments<U> validate(final Number initialValue, final U initialUnit) {
        if (initialValue == null || initialUnit == null) {
            throw new IllegalArgumentException();
        }
        return new ValidArguments<>(initialValue, initialUnit);
    }

    /**
     * Determines if two measurements are equal.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Measurement)) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        //noinspection unchecked
        final var other = (Measurement<T>) obj;
        return value != null && unit != null
                && other.value != null && other.unit != null
                && value.equals(other.value) && unit == other.unit;
    }

    /**
     * Hash code generated for this instance. Hash codes can be internally used by some collections to coarsely
     * compare objects.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        var hash = HASH_SEED;
        hash = HASH_MULTIPLIER * hash + (value != null ? value.hashCode() : 0);
        hash = HASH_MULTIPLIER * hash + (unit != null ? unit.hashCode() : 0);
        return hash;
    }

    /**
     * Determines if two measurements are equal up to a certain tolerance.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two measurements are equal or not.
     * @return true if provided measurement is assumed to be equal to this instance, false otherwise.
     */
    public boolean equals(final Measurement<T> other, final double tolerance) {
        return value != null && unit != null && other != null
                && other.value != null && other.unit != null
                && unit == other.unit
                && (Math.abs(value.doubleValue() - other.value.doubleValue()) <= tolerance);
    }

    /**
     * Returns measurement value.
     *
     * @return measurement value.
     */
    public Number getValue() {
        return value;
    }

    /**
     * Sets measurement value.
     *
     * @param newValue measurement value.
     * @throws IllegalArgumentException if measurement value is null.
     */
    public void setValue(final Number newValue) {
        if (newValue == null) {
            throw new IllegalArgumentException();
        }

        value = newValue;
    }

    /**
     * Returns measurement unit.
     *
     * @return measurement unit.
     */
    public T getUnit() {
        return unit;
    }

    /**
     * Sets measurement unit.
     *
     * @param newUnit measurement unit.
     * @throws IllegalArgumentException if measurement unit is null.
     */
    public void setUnit(final T newUnit) {
        if (newUnit == null) {
            throw new IllegalArgumentException();
        }

        unit = newUnit;
    }

    /**
     * Measurement value and unit that have already been validated to be not null.
     *
     * @param value measurement value.
     * @param unit  measurement unit.
     * @param <U>   type of measurement unit.
     */
    private record ValidArguments<U extends Enum<?>>(Number value, U unit) {
    }
}
