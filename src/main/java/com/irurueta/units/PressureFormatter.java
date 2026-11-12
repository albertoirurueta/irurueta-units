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
 * Formats and parses pressure value and unit.
 */
public class PressureFormatter extends MeasureFormatter<Pressure, PressureUnit> {

    /**
     * Pascal symbol.
     */
    public static final String PASCAL = "Pa";

    /**
     * Kilopascal symbol.
     */
    public static final String KILOPASCAL = "kPa";

    /**
     * Bar symbol.
     */
    public static final String BAR = "bar";

    /**
     * Standard atmosphere symbol.
     */
    public static final String ATMOSPHERE = "atm";

    /**
     * Pounds per square inch symbol.
     */
    public static final String PSI = "psi";

    /**
     * Constructor.
     */
    public PressureFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public PressureFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public PressureFormatter(final PressureFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two pressure formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof PressureFormatter) && equals;
    }

    /**
     * Hash code generated for this instance.
     * Hash codes can be internally used by some collections to coarsely compare objects.
     * This implementation only calls parent implementation to avoid static analyzer warning.
     * It is kept because {@link #equals(Object)} is overridden, so both
     * methods remain consistent.
     *
     * @return hash code.
     */
    @Override
    @SuppressWarnings("PMD.UselessOverridingMethod")
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
        return unit != null ? PressureUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine pressure value and unit.
     *
     * @param source a string to be parsed.
     * @return a pressure containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Pressure parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Pressure());
    }

    /**
     * Attempts to determine a pressure unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return a pressure unit, or null if none can be determined.
     */
    @Override
    public PressureUnit findUnit(final String source) {
        if (containsSymbol(source, KILOPASCAL)) {
            return PressureUnit.KILOPASCAL;
        }
        if (containsSymbol(source, PASCAL)) {
            return PressureUnit.PASCAL;
        }
        if (containsSymbol(source, BAR)) {
            return PressureUnit.BAR;
        }
        if (containsSymbol(source, ATMOSPHERE)) {
            return PressureUnit.ATMOSPHERE;
        }
        if (containsSymbol(source, PSI)) {
            return PressureUnit.PSI;
        }
        return null;
    }

    /**
     * Formats and converts provided pressure value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a pressure value.
     * @param unit   a pressure unit.
     * @param system system unit to convert pressure to.
     * @return a string representation of pressure value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final PressureUnit unit, final UnitSystem system) {
        if (system == UnitSystem.IMPERIAL) {
            return formatAndConvertImperial(value, unit);
        } else {
            return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided pressure value and unit using metric unit
     * system.
     * Values below 1 kPa are expressed in pascals, values below 1 bar are expressed in
     * kilopascals and any larger value is expressed in bars.
     *
     * @param value a pressure value.
     * @param unit  a pressure unit.
     * @return a string representation of pressure value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(final Number value, final PressureUnit unit) {
        final var v = value.doubleValue();

        final var pascals = PressureConverter.convert(v, unit, PressureUnit.PASCAL);
        if (Math.abs(pascals) < PressureConverter.PASCALS_PER_KILOPASCAL) {
            return format(pascals, PressureUnit.PASCAL);
        }

        if (Math.abs(pascals) < PressureConverter.PASCALS_PER_BAR) {
            return format(PressureConverter.convert(v, unit, PressureUnit.KILOPASCAL), PressureUnit.KILOPASCAL);
        }

        return format(PressureConverter.convert(v, unit, PressureUnit.BAR), PressureUnit.BAR);
    }

    /**
     * Formats and converts provided pressure value and unit using imperial unit
     * system.
     * Pounds per square inch is the only imperial pressure unit, so any value is
     * expressed in it.
     *
     * @param value a pressure value.
     * @param unit  a pressure unit.
     * @return a string representation of pressure value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(final Number value, final PressureUnit unit) {
        final var psi = PressureConverter.convert(value.doubleValue(), unit, PressureUnit.PSI);
        return format(psi, PressureUnit.PSI);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a pressure unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final PressureUnit unit) {
        return switch (unit) {
            case KILOPASCAL -> KILOPASCAL;
            case BAR -> BAR;
            case ATMOSPHERE -> ATMOSPHERE;
            case PSI -> PSI;
            default -> PASCAL;
        };
    }
}
