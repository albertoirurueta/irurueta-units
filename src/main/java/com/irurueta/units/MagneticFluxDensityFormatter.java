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
 * Formats and parses magnetic flux density value and unit.
 */
public class MagneticFluxDensityFormatter extends MeasureFormatter<MagneticFluxDensity, MagneticFluxDensityUnit>
        implements Cloneable {

    /**
     * Nanotesla symbol.
     */
    public static final String NANOTESLA = "nT";

    /**
     * Microtesla symbol.
     */
    public static final String MICROTESLA = "µT";

    /**
     * Millitesla symbol.
     */
    public static final String MILLITESLA = "mT";

    /**
     * Tesla symbol.
     */
    public static final String TESLA = "T";

    /**
     * Kilotesla symbol.
     */
    public static final String KILOTESLA = "KT";

    /**
     * Megatesla symbol.
     */
    public static final String MEGATESLA = "MT";

    /**
     * Gigatesla symbol.
     */
    public static final String GIGATESLA = "GT";

    /**
     * Constructor.
     */
    public MagneticFluxDensityFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public MagneticFluxDensityFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public MagneticFluxDensityFormatter(final MagneticFluxDensityFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two magnetic flux density formatters are equal by comparing all of their
     * internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof MagneticFluxDensityFormatter) && equals;
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
        return UnitSystem.METRIC;
    }

    /**
     * Parses provided string and tries to determine a magnetic flux density
     * value and unit.
     *
     * @param source a string to be parsed.
     * @return a magnetic flux density containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public MagneticFluxDensity parse(final String source)
            throws ParseException, UnknownUnitException {
        return internalParse(source, new MagneticFluxDensity());
    }

    /**
     * Attempts to determine a magnetic flux density unit within a string
     * representation.
     *
     * @param source a magnetic flux density string representation.
     * @return a magnetic flux density unit, or null if nonce can be
     * determined.
     */
    @Override
    public MagneticFluxDensityUnit findUnit(final String source) {
        if (source.contains(NANOTESLA + " ") || source.endsWith(NANOTESLA)) {
            return MagneticFluxDensityUnit.NANOTESLA;
        }
        if (source.contains(MICROTESLA + " ") || source.endsWith(MICROTESLA)) {
            return MagneticFluxDensityUnit.MICROTESLA;
        }
        if (source.contains(MILLITESLA + " ") || source.endsWith(MILLITESLA)) {
            return MagneticFluxDensityUnit.MILLITESLA;
        }
        if (source.contains(KILOTESLA + " ") || source.endsWith(KILOTESLA)) {
            return MagneticFluxDensityUnit.KILOTESLA;
        }
        if (source.contains(MEGATESLA + " ") || source.endsWith(MEGATESLA)) {
            return MagneticFluxDensityUnit.MEGATESLA;
        }
        if (source.contains(GIGATESLA + " ") || source.endsWith(GIGATESLA)) {
            return MagneticFluxDensityUnit.GIGATESLA;
        }

        if (source.contains(TESLA + " ") || source.endsWith(TESLA)) {
            return MagneticFluxDensityUnit.TESLA;
        }
        return null;
    }

    /**
     * Formats and converts provided magnetic flux density value and unit
     * using provided unit system.
     * If provided value is too large for provided unit, this method will convert
     * it to a more appropriate unit. This implementation ignores unit system.
     *
     * @param value  a measurement value.
     * @param unit   a measurement unit.
     * @param system system unit to convert measurement to (it is ignored).
     * @return a string representation of magnetic flux density value
     * and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final MagneticFluxDensityUnit unit,
            final UnitSystem system) {
        return formatAndConvertMetric(value, unit);
    }

    /**
     * Formats and converts provided magnetic flux density value and unit
     * using metric unit system.
     * If provided magnetic flux density value is too large for provided
     * magnetic flux density unit, this method will convert it to a more
     * appropriate unit.
     *
     * @param value a magnetic flux density value.
     * @param unit  a magnetic flux density unit.
     * @return a string representation of magnetic flux density value and
     * unit using metric unit system.
     */
    public String formatAndConvertMetric(
            final Number value, final MagneticFluxDensityUnit unit) {
        final double v = value.doubleValue();

        final double nanoTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.NANOTESLA);
        if (Math.abs(nanoTesla) < MagneticFluxDensityConverter.TESLAS_PER_MICROTESLA
                / MagneticFluxDensityConverter.TESLAS_PER_NANOTESLA) {
            return format(nanoTesla, MagneticFluxDensityUnit.NANOTESLA);
        }

        final double microTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.MICROTESLA);
        if (Math.abs(microTesla) < (MagneticFluxDensityConverter.TESLAS_PER_MILLITESLA /
                MagneticFluxDensityConverter.TESLAS_PER_MICROTESLA)) {
            return format(microTesla, MagneticFluxDensityUnit.MICROTESLA);
        }

        final double milliTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.MILLITESLA);
        if (Math.abs(milliTesla) < (1.0 / MagneticFluxDensityConverter.TESLAS_PER_MILLITESLA)) {
            return format(milliTesla, MagneticFluxDensityUnit.MILLITESLA);
        }

        final double tesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.TESLA);
        if (Math.abs(tesla) < MagneticFluxDensityConverter.TESLAS_PER_KILOTESLA) {
            return format(tesla, MagneticFluxDensityUnit.TESLA);
        }

        final double kiloTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.KILOTESLA);
        if (Math.abs(kiloTesla) < (MagneticFluxDensityConverter.TESLAS_PER_MEGATESLA /
                MagneticFluxDensityConverter.TESLAS_PER_KILOTESLA)) {
            return format(kiloTesla, MagneticFluxDensityUnit.KILOTESLA);
        }

        final double megaTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.MEGATESLA);
        if (Math.abs(megaTesla) < (MagneticFluxDensityConverter.TESLAS_PER_GIGATESLA /
                MagneticFluxDensityConverter.TESLAS_PER_MEGATESLA)) {
            return format(megaTesla, MagneticFluxDensityUnit.MEGATESLA);
        }

        final double gigaTesla = MagneticFluxDensityConverter.convert(
                v, unit, MagneticFluxDensityUnit.GIGATESLA);
        return format(gigaTesla, MagneticFluxDensityUnit.GIGATESLA);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a frequency unit.
     * @return its string representation
     */
    @Override
    public String getUnitSymbol(MagneticFluxDensityUnit unit) {
        switch (unit) {
            case NANOTESLA:
                return NANOTESLA;
            case MICROTESLA:
                return MICROTESLA;
            case MILLITESLA:
                return MILLITESLA;
            case KILOTESLA:
                return KILOTESLA;
            case MEGATESLA:
                return MEGATESLA;
            case GIGATESLA:
                return GIGATESLA;
            case TESLA:
            default:
                return TESLA;
        }
    }

    /**
     * Returns unit system this instance will use based on its assigned locale.
     * Notice that if no locale was assigned, then the default system locale
     * will be used.
     *
     * @return unit system this instance will use.
     */
    @Override
    public UnitSystem getUnitSystem() {
        return UnitSystem.METRIC;
    }

    /**
     * Clones this magnetic flux density formatter.
     *
     * @return a copy of this magnetic flux density formatter.
     * @throws CloneNotSupportedException if clone fails for any reason.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        final MagneticFluxDensityFormatter copy = (MagneticFluxDensityFormatter) super.clone();
        return internalClone(copy);
    }
}
