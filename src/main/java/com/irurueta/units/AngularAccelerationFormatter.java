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

import java.text.ParseException;
import java.util.Locale;

/**
 * Formats and parses angular acceleration value and unit.
 */
public class AngularAccelerationFormatter extends
        MeasureFormatter<AngularAcceleration, AngularAccelerationUnit>
        implements Cloneable {

    /**
     * Degrees per squared second symbol.
     */
    public static final String DEGREES_PER_SQUARED_SECOND = "º/s²";

    /**
     * Radians per squared second symbol.
     */
    public static final String RADIANS_PER_SQUARED_SECOND = "rad/s²";

    /**
     * Constructor.
     */
    public AngularAccelerationFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public AngularAccelerationFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public AngularAccelerationFormatter(
            final AngularAccelerationFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two angular acceleration formatters are equal by comparing all of
     * its internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof AngularAccelerationFormatter) && equals;
    }

    /**
     * Hash code generated for this instance.
     * Hash codes can be internally used by some collections to coarsely compare objects.
     * This implementation only calls parent implementation to avoid static analyzer warning.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Gets unit system for detected unit into provided string representation of a
     * measurement.
     *
     * @param source a measurement string representation to be checked.
     * @return a unit system (metric) or null if unit cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(final String source) {
        final AngularAccelerationUnit unit = findUnit(source);
        return unit != null ? UnitSystem.METRIC : null;
    }

    /**
     * Parses provided string and tries to determine an angular acceleration value
     * and unit.
     *
     * @param source text to be parsed.
     * @return an angular acceleration containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public AngularAcceleration parse(final String source)
            throws ParseException, UnknownUnitException {
        return internalParse(source, new AngularAcceleration());
    }

    /**
     * Attempts to determine an angular acceleration unit within a measurement string
     * representation.
     *
     * @param source an angular acceleration measurement string representation.
     * @return an angular acceleration unit, or null if none can be determined.
     */
    @Override
    public AngularAccelerationUnit findUnit(final String source) {
        if (source.contains(DEGREES_PER_SQUARED_SECOND + " ") ||
                source.endsWith(DEGREES_PER_SQUARED_SECOND)) {
            return AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND;
        }
        if (source.contains(RADIANS_PER_SQUARED_SECOND + " ") ||
                source.endsWith(RADIANS_PER_SQUARED_SECOND)) {
            return AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND;
        }
        return null;
    }

    /**
     * Formats and converts provided angular acceleration value and unit using metric
     * system.
     * This implementation ignored provided unit system.
     *
     * @param value  a measurement value.
     * @param unit   a measurement unit.
     * @param system ignored.
     * @return a string representation of angular acceleration value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final AngularAccelerationUnit unit,
            final UnitSystem system) {
        return format(value, unit);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit an angular acceleration unit.
     * @return its string representation.
     */
    public String getUnitSymbol(final AngularAccelerationUnit unit) {
        switch (unit) {
            case DEGREES_PER_SQUARED_SECOND:
                return DEGREES_PER_SQUARED_SECOND;
            case RADIANS_PER_SQUARED_SECOND:
            default:
                return RADIANS_PER_SQUARED_SECOND;
        }
    }

    /**
     * Clones this angular acceleration formatter.
     *
     * @return a copy of this angular acceleration formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final AngularAccelerationFormatter copy = (AngularAccelerationFormatter) super.clone();
        return internalClone(copy);
    }
}
