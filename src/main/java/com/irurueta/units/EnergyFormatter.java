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
 * Formats and parses energy value and unit.
 */
public class EnergyFormatter extends MeasureFormatter<Energy, EnergyUnit> {

    /**
     * Joule symbol.
     */
    public static final String JOULE = "J";

    /**
     * Kilojoule symbol.
     */
    public static final String KILOJOULE = "kJ";

    /**
     * Calorie symbol.
     */
    public static final String CALORIE = "cal";

    /**
     * Kilocalorie symbol.
     */
    public static final String KILOCALORIE = "kcal";

    /**
     * Kilowatt hour symbol.
     */
    public static final String KILOWATT_HOUR = "kWh";

    /**
     * BTU symbol.
     */
    public static final String BTU = "BTU";

    /**
     * Constructor.
     */
    public EnergyFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public EnergyFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public EnergyFormatter(final EnergyFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two energy formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof EnergyFormatter) && equals;
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
        return unit != null ? EnergyUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine energy value and unit.
     *
     * @param source a string to be parsed.
     * @return an energy containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Energy parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Energy());
    }

    /**
     * Attempts to determine an energy unit within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return an energy unit, or null if none can be determined.
     */
    @Override
    public EnergyUnit findUnit(final String source) {
        if (source.contains(KILOJOULE + " ") || source.endsWith(KILOJOULE)) {
            return EnergyUnit.KILOJOULE;
        }
        if (source.contains(KILOCALORIE + " ") || source.endsWith(KILOCALORIE)) {
            return EnergyUnit.KILOCALORIE;
        }
        if (source.contains(KILOWATT_HOUR + " ") || source.endsWith(KILOWATT_HOUR)) {
            return EnergyUnit.KILOWATT_HOUR;
        }
        if (source.contains(BTU + " ") || source.endsWith(BTU)) {
            return EnergyUnit.BTU;
        }
        if (source.contains(CALORIE + " ") || source.endsWith(CALORIE)) {
            return EnergyUnit.CALORIE;
        }
        if (source.contains(JOULE + " ") || source.endsWith(JOULE)) {
            return EnergyUnit.JOULE;
        }
        return null;
    }

    /**
     * Formats and converts provided energy value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  an energy value.
     * @param unit   an energy unit.
     * @param system system unit to convert energy to.
     * @return a string representation of energy value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final EnergyUnit unit, final UnitSystem system) {
        if (system == UnitSystem.IMPERIAL) {
            return formatAndConvertImperial(value, unit);
        } else {
            return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided energy value and unit using metric unit
     * system.
     * If provided energy value is too large for provided energy unit,
     * this method will convert it to a more appropriate unit.
     *
     * @param value an energy value.
     * @param unit  an energy unit.
     * @return a string representation of energy value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(final Number value, final EnergyUnit unit) {
        final var v = value.doubleValue();

        final var joule = EnergyConverter.convert(v, unit, EnergyUnit.JOULE);
        if (Math.abs(joule) < EnergyConverter.JOULES_PER_CALORIE) {
            return format(joule, EnergyUnit.JOULE);
        }

        final var calorie = EnergyConverter.convert(v, unit, EnergyUnit.CALORIE);
        if (Math.abs(joule) < EnergyConverter.JOULES_PER_KILOJOULE) {
            return format(calorie, EnergyUnit.CALORIE);
        }

        final var kilojoule = EnergyConverter.convert(v, unit, EnergyUnit.KILOJOULE);
        if (Math.abs(joule) < EnergyConverter.JOULES_PER_KILOCALORIE) {
            return format(kilojoule, EnergyUnit.KILOJOULE);
        }

        final var kilocalorie = EnergyConverter.convert(v, unit, EnergyUnit.KILOCALORIE);
        if (Math.abs(joule) < EnergyConverter.JOULES_PER_KILOWATT_HOUR) {
            return format(kilocalorie, EnergyUnit.KILOCALORIE);
        }

        final var kilowattHour = EnergyConverter.convert(v, unit, EnergyUnit.KILOWATT_HOUR);
        return format(kilowattHour, EnergyUnit.KILOWATT_HOUR);
    }

    /**
     * Formats and converts provided energy value and unit using imperial unit
     * system.
     *
     * @param value an energy value.
     * @param unit  an energy unit.
     * @return a string representation of energy value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(final Number value, final EnergyUnit unit) {
        final var v = value.doubleValue();

        final var btu = EnergyConverter.convert(v, unit, EnergyUnit.BTU);
        return format(btu, EnergyUnit.BTU);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit an energy unit.
     * @return its string representation.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String getUnitSymbol(final EnergyUnit unit) {
        return switch (unit) {
            case KILOJOULE -> KILOJOULE;
            case CALORIE -> CALORIE;
            case KILOCALORIE -> KILOCALORIE;
            case KILOWATT_HOUR -> KILOWATT_HOUR;
            case BTU -> BTU;
            default -> JOULE;
        };
    }
}
