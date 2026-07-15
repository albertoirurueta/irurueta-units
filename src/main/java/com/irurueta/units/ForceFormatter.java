/*
 * Copyright (C) 2026 Alberto Irurueta Carro (alberto@irurueta.com)
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
 * Formats and parses force value and unit.
 */
public class ForceFormatter extends MeasureFormatter<Force, ForceUnit> {

    /**
     * Newton symbol.
     */
    public static final String NEWTON = "N";

    /**
     * Kilonewton symbol.
     */
    public static final String KILONEWTON = "kN";

    /**
     * Pound-force symbol.
     */
    public static final String POUND_FORCE = "lbf";

    /**
     * Dyne symbol.
     */
    public static final String DYNE = "dyn";

    /**
     * Kilogram-force symbol.
     */
    public static final String KILOGRAM_FORCE = "kgf";

    /**
     * Constructor.
     */
    public ForceFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public ForceFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public ForceFormatter(final ForceFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two force formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof ForceFormatter) && equals;
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
        final var unit = findUnit(source);
        return unit != null ? ForceUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine force value and unit.
     *
     * @param source a string to be parsed.
     * @return a force containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Force parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Force());
    }

    /**
     * Attempts to determine a force unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return a force unit, or null if none can be determined.
     */
    @Override
    public ForceUnit findUnit(final String source) {
        if (source.contains(KILONEWTON + " ") || source.endsWith(KILONEWTON)) {
            return ForceUnit.KILONEWTON;
        }
        if (source.contains(POUND_FORCE + " ") || source.endsWith(POUND_FORCE)) {
            return ForceUnit.POUND_FORCE;
        }
        if (source.contains(DYNE + " ") || source.endsWith(DYNE)) {
            return ForceUnit.DYNE;
        }
        if (source.contains(KILOGRAM_FORCE + " ") || source.endsWith(KILOGRAM_FORCE)) {
            return ForceUnit.KILOGRAM_FORCE;
        }
        if (source.contains(NEWTON + " ") || source.endsWith(NEWTON)) {
            return ForceUnit.NEWTON;
        }
        return null;
    }

    /**
     * Formats and converts provided force value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a force value.
     * @param unit   a force unit.
     * @param system system unit to convert force to.
     * @return a string representation of force value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final ForceUnit unit, final UnitSystem system) {
        if (system == UnitSystem.IMPERIAL) {
            return formatAndConvertImperial(value, unit);
        } else {
            return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided force value and unit using metric unit system.
     *
     * @param value a force value.
     * @param unit  a force unit.
     * @return a string representation of force value and unit using metric unit system.
     */
    public String formatAndConvertMetric(final Number value, final ForceUnit unit) {
        //always format as newtons
        return format(ForceConverter.convert(value, unit, ForceUnit.NEWTON), ForceUnit.NEWTON);
    }

    /**
     * Formats and converts provided force value and unit using imperial unit system.
     *
     * @param value a force value.
     * @param unit  a force unit.
     * @return a string representation of force value and unit using imperial unit system.
     */
    public String formatAndConvertImperial(final Number value, final ForceUnit unit) {
        //always format as pound-force
        return format(ForceConverter.convert(value, unit, ForceUnit.POUND_FORCE), ForceUnit.POUND_FORCE);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a force unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final ForceUnit unit) {
        return switch (unit) {
            case KILONEWTON -> KILONEWTON;
            case POUND_FORCE -> POUND_FORCE;
            case DYNE -> DYNE;
            case KILOGRAM_FORCE -> KILOGRAM_FORCE;
            default -> NEWTON;
        };
    }
}
