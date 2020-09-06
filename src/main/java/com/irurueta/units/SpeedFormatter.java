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
 * Formats and parses speed value and unit.
 */
@SuppressWarnings({"WeakerAccess", "Duplicates"})
public class SpeedFormatter extends MeasureFormatter<Speed, SpeedUnit>
        implements Cloneable {

    /**
     * Meters per second symbol.
     */
    public static final String METERS_PER_SECOND = "m/s";

    /**
     * Kilometers per hour symbol.
     */
    public static final String KILOMETERS_PER_HOUR = "Km/h";

    /**
     * Kilometers per second symbol.
     */
    public static final String KILOMETERS_PER_SECOND = "Km/s";

    /**
     * Feet per second symbol.
     */
    public static final String FEET_PER_SECOND = "ft/s";

    /**
     * Miles per hour symbol.
     */
    public static final String MILES_PER_HOUR = "mph";

    /**
     * Constructor.
     */
    public SpeedFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public SpeedFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public SpeedFormatter(final SpeedFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two speed formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof SpeedFormatter) && equals;
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
     * Gets unit system for detected unit into provided string representation
     * of a measurement.
     *
     * @param source a measurement string representation to be checked.
     * @return a unit system (either metric or imperial) or null if unit
     * cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(final String source) {
        final SpeedUnit unit = findUnit(source);
        return unit != null ? SpeedUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine speed value and unit.
     *
     * @param source a string to be parsed.
     * @return a speed containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Speed parse(final String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Speed());
    }

    /**
     * Attempts to determine a speed unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return a speed unit, or null if none can be determined.
     */
    @Override
    public SpeedUnit findUnit(final String source) {
        if (source.contains(KILOMETERS_PER_HOUR + " ") ||
                source.endsWith(KILOMETERS_PER_HOUR)) {
            return SpeedUnit.KILOMETERS_PER_HOUR;
        }
        if (source.contains(KILOMETERS_PER_SECOND + " ") ||
                source.endsWith(KILOMETERS_PER_SECOND)) {
            return SpeedUnit.KILOMETERS_PER_SECOND;
        }
        if (source.contains(FEET_PER_SECOND + " ") ||
                source.endsWith(FEET_PER_SECOND)) {
            return SpeedUnit.FEET_PER_SECOND;
        }
        if (source.contains(MILES_PER_HOUR + " ") ||
                source.endsWith(MILES_PER_HOUR)) {
            return SpeedUnit.MILES_PER_HOUR;
        }

        if (source.contains(METERS_PER_SECOND + " ") ||
                source.endsWith(METERS_PER_SECOND)) {
            return SpeedUnit.METERS_PER_SECOND;
        }
        return null;
    }

    /**
     * Formats and converts provided speed value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a speed value.
     * @param unit   a speed unit.
     * @param system system unit to convert speed to.
     * @return a string representation of speed value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final SpeedUnit unit,
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
     * Formats and converts provided speed value and unit using metric unit
     * system.
     * If provided speed value is too large for provided speed unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a speed value.
     * @param unit  a speed unit.
     * @return a string representation of speed value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(
            final Number value, final SpeedUnit unit) {
        final double v = value.doubleValue();

        final double metersPerSecond = SpeedConverter.convert(v, unit,
                SpeedUnit.METERS_PER_SECOND);
        if (Math.abs(metersPerSecond) <
                SpeedConverter.METERS_PER_KILOMETER / SpeedConverter.SECONDS_PER_HOUR) {
            return format(metersPerSecond, SpeedUnit.METERS_PER_SECOND);
        }

        final double kilometersPerHour = SpeedConverter.convert(v, unit,
                SpeedUnit.KILOMETERS_PER_HOUR);

        if (Math.abs(kilometersPerHour) < SpeedConverter.SECONDS_PER_HOUR) {
            return format(kilometersPerHour, SpeedUnit.KILOMETERS_PER_HOUR);
        }

        final double kilometersPerSecond = SpeedConverter.convert(v, unit,
                SpeedUnit.KILOMETERS_PER_SECOND);
        return format(kilometersPerSecond, SpeedUnit.KILOMETERS_PER_SECOND);
    }

    /**
     * Formats and converts provided speed value and unit using imperial unit
     * system.
     * If provided speed value is too large for provided speed unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a speed value.
     * @param unit  a speed unit.
     * @return a string representation of speed value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(
            final Number value, final SpeedUnit unit) {
        final double v = value.doubleValue();

        final double feetPerSecond = SpeedConverter.convert(v, unit,
                SpeedUnit.FEET_PER_SECOND);
        if (Math.abs(feetPerSecond) < SpeedConverter.METERS_PER_MILE /
                SpeedConverter.SECONDS_PER_HOUR / SpeedConverter.METERS_PER_FOOT) {
            return format(feetPerSecond, SpeedUnit.FEET_PER_SECOND);
        }

        final double milesPerHour = SpeedConverter.convert(v, unit,
                SpeedUnit.MILES_PER_HOUR);
        return format(milesPerHour, SpeedUnit.MILES_PER_HOUR);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a speed unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final SpeedUnit unit) {
        switch (unit) {
            case KILOMETERS_PER_HOUR:
                return KILOMETERS_PER_HOUR;
            case KILOMETERS_PER_SECOND:
                return KILOMETERS_PER_SECOND;
            case FEET_PER_SECOND:
                return FEET_PER_SECOND;
            case MILES_PER_HOUR:
                return MILES_PER_HOUR;
            case METERS_PER_SECOND:
            default:
                return METERS_PER_SECOND;
        }
    }

    /**
     * Clones this speed formatter.
     *
     * @return a copy of this speed formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final SpeedFormatter copy = (SpeedFormatter) super.clone();
        return internalClone(copy);
    }
}
