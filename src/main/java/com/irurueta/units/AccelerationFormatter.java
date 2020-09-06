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
 * Formats and parses acceleration value and unit.
 */
@SuppressWarnings({"WeakerAccess", "Duplicates"})
public class AccelerationFormatter extends
        MeasureFormatter<Acceleration, AccelerationUnit> implements Cloneable {

    /**
     * Meters per squared second symbol.
     */
    public static final String METERS_PER_SQUARED_SECOND = "m/s²";

    /**
     * Standard gravity symbol.
     */
    public static final String G = "g₀";

    /**
     * Feet per squared second symbol.
     */
    public static final String FEET_PER_SQUARED_SECOND = "ft/s²";

    /**
     * Constructor.
     */
    public AccelerationFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public AccelerationFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public AccelerationFormatter(final AccelerationFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two acceleration formatters are equal by comparing all of their internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof AccelerationFormatter) && equals;
    }

    /**
     * Hash code generated for this instance. Hash codes can be internally used by some collections to coarsely
     * compare objects. This implementation only calls parent implementation to avoid static analyzer warning.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Gets unit system for detected unit into provided string representation of a measurement.
     *
     * @param source a measurement string representation to be checked.
     * @return a unit system (either metric or imperial) or null if unit cannot be determined.
     */
    public UnitSystem getUnitSystem(final String source) {
        final AccelerationUnit unit = findUnit(source);
        return unit != null ? AccelerationUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine acceleration value and unit.
     *
     * @param source a string to be parsed.
     * @return an acceleration containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Acceleration parse(final String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Acceleration());
    }

    /**
     * Attempts to determine an acceleration unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return an acceleration unit, or null if none can be determined.
     */
    @Override
    public AccelerationUnit findUnit(final String source) {
        if (source.contains(METERS_PER_SQUARED_SECOND + " ") ||
                source.endsWith(METERS_PER_SQUARED_SECOND)) {
            return AccelerationUnit.METERS_PER_SQUARED_SECOND;
        }
        if (source.contains(G + " ") || source.endsWith(G)) {
            return AccelerationUnit.G;
        }
        if (source.contains(FEET_PER_SQUARED_SECOND + " ") ||
                source.endsWith(FEET_PER_SQUARED_SECOND)) {
            return AccelerationUnit.FEET_PER_SQUARED_SECOND;
        }
        return null;
    }

    /**
     * Formats and converts provided acceleration value and unit using provided unit system. If provided value is
     * too large for provided unit, this method will convert it to a more appropriate unit using provided unit
     * system (either metric or imperial).
     *
     * @param value  an acceleration value.
     * @param unit   an acceleration unit.
     * @param system system unit to convert measurement to.
     * @return a string representation of acceleration value and unit.
     */
    public String formatAndConvert(
            final Number value, final AccelerationUnit unit,
            final UnitSystem system) {
        switch (system) {
            case IMPERIAL:
                return formatAndConvertImperial(value, unit);
            case METRIC:
            default:
                return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided acceleration value and unit using metric unit system.
     *
     * @param value an acceleration value.
     * @param unit  an acceleration unit.
     * @return a string representation of acceleration value and unit using metric unit system.
     */
    public String formatAndConvertMetric(
            final Number value, final AccelerationUnit unit) {
        //always format as meters per squared second
        return format(AccelerationConverter.convert(value, unit,
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
    }

    /**
     * Formats and converts provided acceleration value and unit using imperial unit system.
     *
     * @param value an acceleration value.
     * @param unit  an acceleration unit.
     * @return a string representation of acceleration value and unit using imperial unit system.
     */
    public String formatAndConvertImperial(
            final Number value, final AccelerationUnit unit) {
        //always format as feet per squared second
        return format(AccelerationConverter.convert(value, unit,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit an acceleration unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final AccelerationUnit unit) {
        switch (unit) {
            case FEET_PER_SQUARED_SECOND:
                return FEET_PER_SQUARED_SECOND;
            case G:
                return G;
            case METERS_PER_SQUARED_SECOND:
            default:
                return METERS_PER_SQUARED_SECOND;
        }
    }

    /**
     * Clones this acceleration formatter.
     *
     * @return a copy of this acceleration formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final AccelerationFormatter copy = (AccelerationFormatter) super.clone();
        return internalClone(copy);
    }
}
