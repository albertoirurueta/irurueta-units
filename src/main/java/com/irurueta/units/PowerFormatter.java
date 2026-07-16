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
 * Formats and parses power value and unit.
 */
public class PowerFormatter extends MeasureFormatter<Power, PowerUnit> {

    /**
     * Watt symbol.
     */
    public static final String WATT = "W";

    /**
     * Kilowatt symbol.
     */
    public static final String KILOWATT = "kW";

    /**
     * Megawatt symbol.
     */
    public static final String MEGAWATT = "MW";

    /**
     * Horsepower symbol.
     */
    public static final String HORSEPOWER = "hp";

    /**
     * Constructor.
     */
    public PowerFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public PowerFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public PowerFormatter(final PowerFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two power formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof PowerFormatter) && equals;
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
        return unit != null ? PowerUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine power value and unit.
     *
     * @param source a string to be parsed.
     * @return a power containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Power parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Power());
    }

    /**
     * Attempts to determine a power unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return a power unit, or null if none can be determined.
     */
    @Override
    public PowerUnit findUnit(final String source) {
        if (source.contains(KILOWATT + " ") || source.endsWith(KILOWATT)) {
            return PowerUnit.KILOWATT;
        }
        if (source.contains(MEGAWATT + " ") || source.endsWith(MEGAWATT)) {
            return PowerUnit.MEGAWATT;
        }
        if (source.contains(HORSEPOWER + " ") || source.endsWith(HORSEPOWER)) {
            return PowerUnit.HORSEPOWER;
        }
        if (source.contains(WATT + " ") || source.endsWith(WATT)) {
            return PowerUnit.WATT;
        }
        return null;
    }

    /**
     * Formats and converts provided power value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a power value.
     * @param unit   a power unit.
     * @param system system unit to convert power to.
     * @return a string representation of power value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final PowerUnit unit, final UnitSystem system) {
        if (system == UnitSystem.IMPERIAL) {
            return formatAndConvertImperial(value, unit);
        } else {
            return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided power value and unit using metric unit
     * system.
     * If provided power value is too large for provided power unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value a power value.
     * @param unit  a power unit.
     * @return a string representation of power value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(final Number value, final PowerUnit unit) {
        final var v = value.doubleValue();

        final var watt = PowerConverter.convert(v, unit, PowerUnit.WATT);
        if (Math.abs(watt) < PowerConverter.WATTS_PER_KILOWATT) {
            return format(watt, PowerUnit.WATT);
        }

        final var kilowatt = PowerConverter.convert(v, unit, PowerUnit.KILOWATT);

        if (Math.abs(kilowatt) < PowerConverter.WATTS_PER_MEGAWATT / PowerConverter.WATTS_PER_KILOWATT) {
            return format(kilowatt, PowerUnit.KILOWATT);
        }

        final var megawatt = PowerConverter.convert(v, unit, PowerUnit.MEGAWATT);
        return format(megawatt, PowerUnit.MEGAWATT);
    }

    /**
     * Formats and converts provided power value and unit using imperial unit
     * system.
     *
     * @param value a power value.
     * @param unit  a power unit.
     * @return a string representation of power value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(final Number value, final PowerUnit unit) {
        final var v = value.doubleValue();

        final var horsepower = PowerConverter.convert(v, unit, PowerUnit.HORSEPOWER);
        return format(horsepower, PowerUnit.HORSEPOWER);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a power unit.
     * @return its string representation.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String getUnitSymbol(final PowerUnit unit) {
        return switch (unit) {
            case KILOWATT -> KILOWATT;
            case MEGAWATT -> MEGAWATT;
            case HORSEPOWER -> HORSEPOWER;
            default -> WATT;
        };
    }
}
