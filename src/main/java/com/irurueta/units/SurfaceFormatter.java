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
 * Formats and parses surface value and unit.
 */
public class SurfaceFormatter extends MeasureFormatter<Surface, SurfaceUnit> {
    /**
     * Square millimeter symbol.
     */
    public static final String SQUARE_MILLIMETER = "mm²";

    /**
     * Square centimeter symbol.
     */
    public static final String SQUARE_CENTIMETER = "cm²";

    /**
     * Square meter symbol.
     */
    public static final String SQUARE_METER = "m²";

    /**
     * Square kilometer symbol.
     */
    public static final String SQUARE_KILOMETER = "Km²";

    /**
     * Square inch symbol.
     */
    public static final String SQUARE_INCH = "sq in";

    /**
     * Square foot symbol.
     */
    public static final String SQUARE_FOOT = "sq ft";

    /**
     * Square yard symbol.
     */
    public static final String SQUARE_YARD = "sq yd";

    /**
     * Square mile symbol.
     */
    public static final String SQUARE_MILE = "sq mi";

    /**
     * Centiare symbol.
     */
    public static final String CENTIARE = "ca";

    /**
     * Are symbol.
     */
    public static final String ARE = "a";

    /**
     * Decare symbol.
     */
    public static final String DECARE = "daa";

    /**
     * Hectare symbol.
     */
    public static final String HECTARE = "ha";

    /**
     * Acre symbol.
     */
    public static final String ACRE = "acre";

    /**
     * Constructor.
     */
    @SuppressWarnings("WeakerAccess")
    public SurfaceFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    @SuppressWarnings("WeakerAccess")
    public SurfaceFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public SurfaceFormatter(final SurfaceFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two distance formatters are equal by comparing all of its
     * internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof SurfaceFormatter) && equals;
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
        final SurfaceUnit unit = findUnit(source);
        return unit != null ? SurfaceUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine a surface value and unit.
     *
     * @param source text to be parsed.
     * @return a surface containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Surface parse(final String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Surface());
    }

    /**
     * Attempts to determine a surface unit within a measurement string
     * representation.
     *
     * @param source a surface measurement string representation.
     * @return a surface unit, or null if none can be determined.
     */
    @Override
    public SurfaceUnit findUnit(final String source) {
        if (source.contains(SQUARE_MILLIMETER + " ") || source.endsWith(SQUARE_MILLIMETER)) {
            return SurfaceUnit.SQUARE_MILLIMETER;
        }
        if (source.contains(SQUARE_CENTIMETER + " ") || source.endsWith(SQUARE_CENTIMETER)) {
            return SurfaceUnit.SQUARE_CENTIMETER;
        }
        if (source.contains(SQUARE_KILOMETER + " ") || source.endsWith(SQUARE_KILOMETER)) {
            return SurfaceUnit.SQUARE_KILOMETER;
        }
        if (source.contains(SQUARE_INCH + " ") || source.endsWith(SQUARE_INCH)) {
            return SurfaceUnit.SQUARE_INCH;
        }
        if (source.contains(SQUARE_FOOT + " ") || source.endsWith(SQUARE_FOOT)) {
            return SurfaceUnit.SQUARE_FOOT;
        }
        if (source.contains(SQUARE_YARD + " ") || source.endsWith(SQUARE_YARD)) {
            return SurfaceUnit.SQUARE_YARD;
        }
        if (source.contains(SQUARE_MILE + " ") || source.endsWith(SQUARE_MILE)) {
            return SurfaceUnit.SQUARE_MILE;
        }
        if (source.contains(CENTIARE + " ") || source.endsWith(CENTIARE)) {
            return SurfaceUnit.CENTIARE;
        }
        if (source.contains(DECARE + " ") || source.endsWith(DECARE)) {
            return SurfaceUnit.DECARE;
        }
        if (source.contains(HECTARE + " ") || source.endsWith(HECTARE)) {
            return SurfaceUnit.HECTARE;
        }
        if (source.contains(ACRE + " ") || source.endsWith(ACRE)) {
            return SurfaceUnit.ACRE;
        }

        if (source.contains(ARE + " ") || source.endsWith(ARE)) {
            return SurfaceUnit.ARE;
        }
        if (source.contains(SQUARE_METER + " ") || source.endsWith(SQUARE_METER)) {
            return SurfaceUnit.SQUARE_METER;
        }
        return null;
    }

    /**
     * Formats and converts provided surface value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a surface value.
     * @param unit   a surface unit.
     * @param system system unit to convert surface to.
     * @return a string representation of surface value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final SurfaceUnit unit,
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
     * Formats and converts provided surface value and unit using metric unit
     * system.
     * If provided surface value is too large for provided surface unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a surface value.
     * @param unit  a surface unit.
     * @return a string representation of surface value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(
            final Number value, final SurfaceUnit unit) {
        final double v = value.doubleValue();

        final double squareMillimeters = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_MILLIMETER);
        if (Math.abs(squareMillimeters) < (SurfaceConverter.SQUARE_METERS_PER_SQUARE_CENTIMETER /
                SurfaceConverter.SQUARE_METERS_PER_SQUARE_MILLIMETER)) {
            return format(squareMillimeters, SurfaceUnit.SQUARE_MILLIMETER);
        }

        final double squareCentimeters = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_CENTIMETER);
        if (Math.abs(squareCentimeters) <
                (1.0 / SurfaceConverter.SQUARE_METERS_PER_SQUARE_CENTIMETER)) {
            return format(squareCentimeters, SurfaceUnit.SQUARE_CENTIMETER);
        }

        final double squareMeters = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_METER);
        if (Math.abs(squareMeters) < SurfaceConverter.SQUARE_METERS_PER_SQUARE_KILOMETER) {
            return format(squareMeters, SurfaceUnit.SQUARE_METER);
        }

        final double squareKilometers = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_KILOMETER);
        return format(squareKilometers, SurfaceUnit.SQUARE_KILOMETER);
    }

    /**
     * Formats and converts provided surface value and unit using imperial unit
     * system.
     * If provided surface value is too large for provided surface unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a surface value.
     * @param unit  a surface unit.
     * @return a string representation of surface value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(
            final Number value, final SurfaceUnit unit) {
        final double v = value.doubleValue();

        final double squareInches = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_INCH);
        if (Math.abs(squareInches) < (SurfaceConverter.SQUARE_METERS_PER_SQUARE_FOOT /
                SurfaceConverter.SQUARE_METERS_PER_SQUARE_INCH)) {
            return format(squareInches, SurfaceUnit.SQUARE_INCH);
        }

        final double squareFeet = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_FOOT);
        if (Math.abs(squareFeet) < (SurfaceConverter.SQUARE_METERS_PER_SQUARE_YARD /
                SurfaceConverter.SQUARE_METERS_PER_SQUARE_FOOT)) {
            return format(squareFeet, SurfaceUnit.SQUARE_FOOT);
        }

        final double squareYards = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_YARD);
        if (Math.abs(squareYards) < (SurfaceConverter.SQUARE_METERS_PER_SQUARE_MILE /
                SurfaceConverter.SQUARE_METERS_PER_SQUARE_YARD)) {
            return format(squareYards, SurfaceUnit.SQUARE_YARD);
        }

        final double squareMiles = SurfaceConverter.convert(v, unit,
                SurfaceUnit.SQUARE_MILE);
        return format(squareMiles, SurfaceUnit.SQUARE_MILE);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a measure unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final SurfaceUnit unit) {
        switch (unit) {
            case SQUARE_MILLIMETER:
                return SQUARE_MILLIMETER;
            case SQUARE_CENTIMETER:
                return SQUARE_CENTIMETER;
            case SQUARE_KILOMETER:
                return SQUARE_KILOMETER;
            case SQUARE_INCH:
                return SQUARE_INCH;
            case SQUARE_FOOT:
                return SQUARE_FOOT;
            case SQUARE_YARD:
                return SQUARE_YARD;
            case SQUARE_MILE:
                return SQUARE_MILE;
            case CENTIARE:
                return CENTIARE;
            case ARE:
                return ARE;
            case DECARE:
                return DECARE;
            case HECTARE:
                return HECTARE;
            case ACRE:
                return ACRE;
            case SQUARE_METER:
            default:
                return SQUARE_METER;
        }
    }
}
