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
     * Measurement value.
     */
    private Number mValue;

    /**
     * Measurement unit.
     */
    private T mUnit;

    /**
     * Constructor.
     *
     * @param value measurement value.
     * @param unit  measurement unit.
     * @throws IllegalArgumentException if either value or unit is null.
     */
    @SuppressWarnings("WeakerAccess")
    public Measurement(final Number value, final T unit) {
        if (value == null || unit == null) {
            throw new IllegalArgumentException();
        }

        mValue = value;
        mUnit = unit;
    }

    /**
     * Constructor.
     */
    Measurement() {
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
        final Measurement<T> other = (Measurement<T>) obj;
        return mValue != null && mUnit != null &&
                other.mValue != null && other.mUnit != null &&
                mValue.equals(other.mValue) && mUnit == other.mUnit;
    }

    /**
     * Hash code generated for this instance. Hash codes can be internally used by some collections to coarsely
     * compare objects.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (mValue != null ? mValue.hashCode() : 0);
        hash = 19 * hash + (mUnit != null ? mUnit.hashCode() : 0);
        return hash;
    }

    /**
     * Determines if two measurements are equal up to a certain tolerance.
     *
     * @param other     another measurement to compare.
     * @param tolerance amount of tolerance to determine whether two measurements are equal or not.
     * @return true if provided measurement is assumed to be equal to this instance, false otherwise.
     */
    public boolean equals(final Measurement<T> other,
                          final double tolerance) {
        return mValue != null && mUnit != null && other != null &&
                other.mValue != null && other.mUnit != null &&
                mUnit == other.mUnit &&
                (Math.abs(mValue.doubleValue() - other.mValue.doubleValue()) <= tolerance);
    }

    /**
     * Returns measurement value.
     *
     * @return measurement value.
     */
    public Number getValue() {
        return mValue;
    }

    /**
     * Sets measurement value.
     *
     * @param value measurement value.
     * @throws IllegalArgumentException if measurement value is null.
     */
    public void setValue(final Number value) {
        if (value == null) {
            throw new IllegalArgumentException();
        }

        mValue = value;
    }

    /**
     * Returns measurement unit.
     *
     * @return measurement unit.
     */
    public T getUnit() {
        return mUnit;
    }

    /**
     * Sets measurement unit.
     *
     * @param unit measurement unit.
     * @throws IllegalArgumentException if measurement unit is null.
     */
    public void setUnit(final T unit) {
        if (unit == null) {
            throw new IllegalArgumentException();
        }

        mUnit = unit;
    }
}
