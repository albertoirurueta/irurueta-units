/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
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
 * Formats and parses weight value and unit.
 */
public class WeightFormatter extends MeasureFormatter<Weight, WeightUnit> {

    /**
     * Picogram symbol.
     */
    public static final String PICOGRAM = "pg";

    /**
     * Nanogram symbol.
     */
    public static final String NANOGRAM = "ng";

    /**
     * Microgram symbol.
     */
    public static final String MICROGRAM = "µg";

    /**
     * Milligram symbol.
     */
    public static final String MILLIGRAM = "mg";

    /**
     * Gram symbol.
     */
    public static final String GRAM = "g";

    /**
     * Kilogram symbol.
     */
    public static final String KILOGRAM = "Kg";

    /**
     * Metric tonne symbol.
     */
    public static final String TONNE = "t";

    /**
     * Megatonne symbol.
     */
    public static final String MEGATONNE = "Mt";

    /**
     * U.S. OR U.K. ton symbol.
     */
    public static final String US_UK_TON = "ton";

    /**
     * Pound symbol.
     */
    public static final String POUND = "lb";

    /**
     * Ounce symbol.
     */
    public static final String OUNCE = "oz";

    /**
     * Constructor.
     */
    public WeightFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public WeightFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public WeightFormatter(final WeightFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two weight formatters are equal by comparing all of their
     * internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof WeightFormatter) && equals;
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
        return unit != null ? WeightUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine a weight value and unit.
     *
     * @param source a string to be parsed.
     * @return a weight containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Weight parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Weight());
    }

    /**
     * Attempts to determine a weight unit within a measurement string
     * representation.
     *
     * @param source a weight measurement string representation.
     * @return a weight unit, or null if none can be determined.
     */
    @Override
    public WeightUnit findUnit(final String source) {
        if (source.contains(PICOGRAM + " ") || source.endsWith(PICOGRAM)) {
            return WeightUnit.PICOGRAM;
        }
        if (source.contains(NANOGRAM + " ") || source.endsWith(NANOGRAM)) {
            return WeightUnit.NANOGRAM;
        }
        if (source.contains(MICROGRAM + " ") || source.endsWith(MICROGRAM)) {
            return WeightUnit.MICROGRAM;
        }
        if (source.contains(MILLIGRAM + " ") || source.endsWith(MILLIGRAM)) {
            return WeightUnit.MILLIGRAM;
        }
        if (source.contains(KILOGRAM + " ") || source.endsWith(KILOGRAM)) {
            return WeightUnit.KILOGRAM;
        }
        if (source.contains(MEGATONNE + " ") || source.endsWith(MEGATONNE)) {
            return WeightUnit.MEGATONNE;
        }
        if (source.contains(POUND + " ") || source.endsWith(POUND)) {
            return WeightUnit.POUND;
        }
        if (source.contains(OUNCE + " ") || source.endsWith(OUNCE)) {
            return WeightUnit.OUNCE;
        }

        if (source.contains(US_UK_TON + " ") || source.endsWith(US_UK_TON)) {
            final var locale = getLocale();
            if (Locale.UK.getCountry().equals(locale.getCountry())) {
                // UK
                return WeightUnit.UK_TON;
            } else {
                // US
                return WeightUnit.US_TON;
            }
        }

        if (source.contains(TONNE + " ") || source.endsWith(TONNE)) {
            return WeightUnit.TONNE;
        }
        if (source.contains(GRAM + " ") || source.endsWith(GRAM)) {
            return WeightUnit.GRAM;
        }

        return null;
    }

    /**
     * Formats and converts provided weight value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a weight value.
     * @param unit   a weight unit.
     * @param system system unit to convert measurement to.
     * @return a string representation of weight value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final WeightUnit unit, final UnitSystem system) {
        if (system == UnitSystem.IMPERIAL) {
            return formatAndConvertImperial(value, unit);
        } else {
            return formatAndConvertMetric(value, unit);
        }
    }

    /**
     * Formats and converts provided weight value and unit using metric unit
     * system.
     * If provided weight value is too large for provided weight unit,
     * this method will convert to a more appropriate unit.
     *
     * @param value a weight value.
     * @param unit  a weight unit.
     * @return a string representation of weight value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(final Number value, final WeightUnit unit) {
        final var v = value.doubleValue();

        final var picogram = WeightConverter.convert(v, unit, WeightUnit.PICOGRAM);
        if (Math.abs(picogram) < (WeightConverter.GRAMS_PER_NANOGRAM / WeightConverter.GRAMS_PER_PICOGRAM)) {
            return format(picogram, WeightUnit.PICOGRAM);
        }

        final var nanogram = WeightConverter.convert(v, unit, WeightUnit.NANOGRAM);
        if (Math.abs(nanogram) < (WeightConverter.GRAMS_PER_MICROGRAM / WeightConverter.GRAMS_PER_NANOGRAM)) {
            return format(nanogram, WeightUnit.NANOGRAM);
        }

        final var microgram = WeightConverter.convert(v, unit, WeightUnit.MICROGRAM);
        if (Math.abs(microgram) < (WeightConverter.GRAMS_PER_MILLIGRAM / WeightConverter.GRAMS_PER_MICROGRAM)) {
            return format(microgram, WeightUnit.MICROGRAM);
        }

        final var milligram = WeightConverter.convert(v, unit, WeightUnit.MILLIGRAM);
        if (Math.abs(milligram) < (1.0 / WeightConverter.GRAMS_PER_MILLIGRAM)) {
            return format(milligram, WeightUnit.MILLIGRAM);
        }

        final var gram = WeightConverter.convert(v, unit, WeightUnit.GRAM);
        if (Math.abs(gram) < WeightConverter.GRAMS_PER_KILOGRAM) {
            return format(gram, WeightUnit.GRAM);
        }

        final var kilogram = WeightConverter.convert(v, unit, WeightUnit.KILOGRAM);
        if (Math.abs(kilogram) < (WeightConverter.GRAMS_PER_TONNE / WeightConverter.GRAMS_PER_KILOGRAM)) {
            return format(kilogram, WeightUnit.KILOGRAM);
        }

        final var tonne = WeightConverter.convert(v, unit, WeightUnit.TONNE);
        if (Math.abs(tonne) < (WeightConverter.GRAMS_PER_MEGATONNE / WeightConverter.GRAMS_PER_TONNE)) {
            return format(tonne, WeightUnit.TONNE);
        }

        final var megatonne = WeightConverter.convert(v, unit, WeightUnit.MEGATONNE);
        return format(megatonne, WeightUnit.MEGATONNE);
    }


    /**
     * Formats and converts provided weight value and unit using imperial unit
     * system.
     * If provided weight value is too large for provided weight unit,
     * this method will convert to a more appropriate unit.
     *
     * @param value a weight value.
     * @param unit  a weight unit.
     * @return a string representation of weight value and unit using imperial
     * unit system.
     */
    public String formatAndConvertImperial(final Number value, final WeightUnit unit) {
        final var v = value.doubleValue();

        final var ounce = WeightConverter.convert(v, unit, WeightUnit.OUNCE);
        if (Math.abs(ounce) < (WeightConverter.GRAMS_PER_POUND / WeightConverter.GRAMS_PER_OUNCE)) {
            return format(ounce, WeightUnit.OUNCE);
        }

        final var pound = WeightConverter.convert(v, unit, WeightUnit.POUND);

        final var locale = getLocale();
        if (Locale.UK.getCountry().equals(locale.getCountry())) {
            // UK
            if (Math.abs(pound) < (WeightConverter.GRAMS_PER_UK_TON / WeightConverter.GRAMS_PER_POUND)) {
                return format(pound, WeightUnit.POUND);
            }

            final var ton = WeightConverter.convert(v, unit, WeightUnit.UK_TON);
            return format(ton, WeightUnit.UK_TON);
        } else {
            // US
            if (Math.abs(pound) < (WeightConverter.GRAMS_PER_US_TON / WeightConverter.GRAMS_PER_POUND)) {
                return format(pound, WeightUnit.POUND);
            }

            final var ton = WeightConverter.convert(v, unit, WeightUnit.US_TON);
            return format(ton, WeightUnit.US_TON);
        }
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a weight unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final WeightUnit unit) {
        return switch (unit) {
            case PICOGRAM -> PICOGRAM;
            case NANOGRAM -> NANOGRAM;
            case MICROGRAM -> MICROGRAM;
            case MILLIGRAM -> MILLIGRAM;
            case KILOGRAM -> KILOGRAM;
            case TONNE -> TONNE;
            case MEGATONNE -> MEGATONNE;
            case US_TON, UK_TON -> US_UK_TON;
            case POUND -> POUND;
            case OUNCE -> OUNCE;
            default -> GRAM;
        };
    }
}
