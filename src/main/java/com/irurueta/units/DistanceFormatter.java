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
 * Formats and parses distance value and unit.
 */
public class DistanceFormatter extends MeasureFormatter<Distance, DistanceUnit> implements Cloneable {

    /**
     * Millimeter symbol.
     */
    public static final String MILLIMETER = "mm";

    /**
     * Centimeter symbol.
     */
    public static final String CENTIMETER = "cm";

    /**
     * Meter symbol.
     */
    public static final String METER = "m";

    /**
     * Kilometer symbol.
     */
    public static final String KILOMETER = "Km";

    /**
     * Inch symbol.
     */
    public static final String INCH = "in";

    /**
     * Foot symbol.
     */
    public static final String FOOT = "ft";

    /**
     * Yard symbol.
     */
    public static final String YARD = "yd";

    /**
     * Mile symbol.
     */
    public static final String MILE = "mi";

    /**
     * Constructor.
     */
    public DistanceFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public DistanceFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public DistanceFormatter(final DistanceFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two distance formatters are equal by comparing all of their internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof DistanceFormatter) && equals;
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
        final DistanceUnit unit = findUnit(source);
        return unit != null ? DistanceUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine a distance value and unit.
     *
     * @param source a string to be parsed.
     * @return a distance containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Distance parse(final String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Distance());
    }

    /**
     * Attempts to determine a distance unit within a measurement string
     * representation.
     *
     * @param source a distance measurement string representation.
     * @return a distance unit, or null if none can be determined.
     */
    @Override
    public DistanceUnit findUnit(final String source) {
        if (source.contains(MILLIMETER + " ") || source.endsWith(MILLIMETER)) {
            return DistanceUnit.MILLIMETER;
        }
        if (source.contains(CENTIMETER + " ") || source.endsWith(CENTIMETER)) {
            return DistanceUnit.CENTIMETER;
        }
        if (source.contains(KILOMETER + " ") || source.endsWith(KILOMETER)) {
            return DistanceUnit.KILOMETER;
        }
        if (source.contains(INCH + " ") || source.endsWith(INCH)) {
            return DistanceUnit.INCH;
        }
        if (source.contains(FOOT + " ") || source.endsWith(FOOT)) {
            return DistanceUnit.FOOT;
        }
        if (source.contains(YARD + " ") || source.endsWith(YARD)) {
            return DistanceUnit.YARD;
        }
        if (source.contains(MILE + " ") || source.endsWith(MILE)) {
            return DistanceUnit.MILE;
        }

        if (source.contains(METER + " ") || source.endsWith(METER)) {
            return DistanceUnit.METER;
        }
        return null;
    }

    /**
     * Formats and converts provided distance value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a distance value.
     * @param unit   a distance unit.
     * @param system system unit to convert distance to.
     * @return a string representation of distance value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final DistanceUnit unit,
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
     * Formats and converts provided distance value and unit using metric unit
     * system.
     * If provided distance value is too large for provided distance unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a distance value.
     * @param unit  a distance unit.
     * @return a string representation of distance value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(
            final Number value, final DistanceUnit unit) {
        final double v = value.doubleValue();

        final double millimeters = DistanceConverter.convert(v, unit,
                DistanceUnit.MILLIMETER);
        if (Math.abs(millimeters) < (DistanceConverter.METERS_PER_CENTIMETER /
                DistanceConverter.METERS_PER_MILLIMETER)) {
            return format(millimeters, DistanceUnit.MILLIMETER);
        }

        final double centimeters = DistanceConverter.convert(v, unit,
                DistanceUnit.CENTIMETER);
        if (Math.abs(centimeters) <
                (1.0 / DistanceConverter.METERS_PER_CENTIMETER)) {
            return format(centimeters, DistanceUnit.CENTIMETER);
        }

        final double meters = DistanceConverter.convert(v, unit, DistanceUnit.METER);
        if (Math.abs(meters) < DistanceConverter.METERS_PER_KILOMETER) {
            return format(meters, DistanceUnit.METER);
        }

        final double kilometers = DistanceConverter.convert(v, unit,
                DistanceUnit.KILOMETER);
        return format(kilometers, DistanceUnit.KILOMETER);
    }

    /**
     * Formats and converts provided distance value and unit using imperial unit
     * system.
     * If provided distance value is too large for provided distance unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a distance value.
     * @param unit  a distance unit.
     * @return a string representation of distance value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(
            final Number value, final DistanceUnit unit) {
        final double v = value.doubleValue();

        final double inches = DistanceConverter.convert(v, unit,
                DistanceUnit.INCH);
        if (Math.abs(inches) < (DistanceConverter.METERS_PER_FOOT /
                DistanceConverter.METERS_PER_INCH)) {
            return format(inches, DistanceUnit.INCH);
        }

        final double feet = DistanceConverter.convert(v, unit,
                DistanceUnit.FOOT);
        if (Math.abs(feet) < (DistanceConverter.METERS_PER_YARD /
                DistanceConverter.METERS_PER_FOOT)) {
            return format(feet, DistanceUnit.FOOT);
        }

        final double yards = DistanceConverter.convert(v, unit,
                DistanceUnit.YARD);
        if (Math.abs(yards) < (DistanceConverter.METERS_PER_MILE /
                DistanceConverter.METERS_PER_YARD)) {
            return format(yards, DistanceUnit.YARD);
        }

        final double miles = DistanceConverter.convert(v, unit,
                DistanceUnit.MILE);
        return format(miles, DistanceUnit.MILE);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a distance unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final DistanceUnit unit) {
        switch (unit) {
            case MILLIMETER:
                return MILLIMETER;
            case CENTIMETER:
                return CENTIMETER;
            case KILOMETER:
                return KILOMETER;
            case INCH:
                return INCH;
            case FOOT:
                return FOOT;
            case YARD:
                return YARD;
            case MILE:
                return MILE;
            case METER:
            default:
                return METER;
        }
    }

    /**
     * Clones this distance formatter.
     *
     * @return a copy of this distance formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final DistanceFormatter copy = (DistanceFormatter) super.clone();
        return internalClone(copy);
    }
}
