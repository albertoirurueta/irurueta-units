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
 * Formats and parses frequency value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class FrequencyFormatter extends MeasureFormatter<Frequency, FrequencyUnit>
        implements Cloneable {

    /**
     * Hertz symbol.
     */
    public static final String HERTZ = "Hz";

    /**
     * KiloHertz symbol.
     */
    public static final String KILOHERTZ = "KHz";

    /**
     * MegaHertz symbol.
     */
    public static final String MEGAHERTZ = "MHz";

    /**
     * GigaHertz symbol.
     */
    public static final String GIGAHERTZ = "GHz";

    /**
     * TeraHertz symbol.
     */
    public static final String TERAHERTZ = "THz";

    /**
     * Constructor.
     */
    public FrequencyFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public FrequencyFormatter(Locale locale) throws IllegalArgumentException {
        super(locale);
    }

    /**
     * Clones this frequency formatter.
     * @return a copy of this frequency formatter.
     */
    @Override
    public Object clone() {
        FrequencyFormatter copy = new FrequencyFormatter();
        return internalClone(copy);
    }

    /**
     * Determines if two frequency formatters are equal by comparing all of its
     * internal parameters.
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        return (!equals || obj instanceof FrequencyFormatter) && equals;
    }

    /**
     * Gets unit system for detected unit into provided string representation
     * of a measurement.
     * @param source a measurement string representation to be checked.
     * @return a unit system (either metric or imperial) or null if unit
     * cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(String source) {
        return UnitSystem.METRIC;
    }

    /**
     * Parses provided string and tries to determine a frequency value and unit.
     * @param source a string to be parsed.
     * @return a frequency containing a value and unit.
     * @throws ParseException if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Frequency parse(String source) throws ParseException,
            UnknownUnitException {
        return internalParse(source, new Frequency());
    }

    /**
     * Attempts to determine a frequency unit within a frequency string
     * representation.
     * @param source a frequency measurement string representation.
     * @return a frequency unit, or null if none can be determined.
     */
    @Override
    public FrequencyUnit findUnit(String source) {
        if (source.contains(KILOHERTZ + " ") || source.endsWith(KILOHERTZ)) {
            return FrequencyUnit.KILOHERTZ;
        }
        if (source.contains(MEGAHERTZ + " ") || source.endsWith(MEGAHERTZ)) {
            return FrequencyUnit.MEGAHERTZ;
        }
        if (source.contains(GIGAHERTZ + " ") || source.endsWith(GIGAHERTZ)) {
            return FrequencyUnit.GIGAHERTZ;
        }
        if (source.contains(TERAHERTZ + " ") ||source.endsWith(TERAHERTZ)) {
            return FrequencyUnit.TERAHERTZ;
        }

        if (source.contains(HERTZ + " ") || source.endsWith(HERTZ)) {
            return FrequencyUnit.HERTZ;
        }
        return null;
    }

    /**
     * Formats and converts provided frequency value and unit using provided unit
     * system.
     * If provided value is too large for provided unit, this method will convert
     * it to a more appropriate unit. This implementation ignores unit system.
     * @param value a measurment value.
     * @param unit a measurement unit.
     * @param system system unit to convert measurement to (it is ignored).
     * @return a string representation of frequency value and unit.
     */
    @Override
    public String formatAndConvert(Number value, FrequencyUnit unit,
                                   UnitSystem system) {
        return formatAndConvertMetric(value, unit);
    }

    /**
     * Formats and converts provided frequency value and unit using metric unit
     * system.
     * If provided frequency value is too large for provided frequency unit, this
     * method will convert it to a more appropriate unit.
     * @param value a frequency value.
     * @param unit a frequency unit.
     * @return a string representation of frequency value and unit using metric
     * unit system.
     */
    public String formatAndConvertMetric(Number value, FrequencyUnit unit) {
        double v = value.doubleValue();

        double hertz = FrequencyConverter.convert(v, unit, FrequencyUnit.HERTZ);
        if (Math.abs(hertz) < (FrequencyConverter.HERTZS_PER_KILOHERTZ)) {
            return format(hertz, FrequencyUnit.HERTZ);
        }

        double kiloHertz = FrequencyConverter.convert(v, unit,
                FrequencyUnit.KILOHERTZ);
        if (Math.abs(kiloHertz) < (FrequencyConverter.HERTZ_PER_MEGAHERTZ /
                FrequencyConverter.HERTZS_PER_KILOHERTZ)) {
            return format(kiloHertz, FrequencyUnit.KILOHERTZ);
        }

        double megaHertz = FrequencyConverter.convert(v, unit,
                FrequencyUnit.MEGAHERTZ);
        if (Math.abs(megaHertz) < (FrequencyConverter.HERTZ_PER_GIGAHERTZ /
                FrequencyConverter.HERTZ_PER_MEGAHERTZ)) {
            return format(megaHertz, FrequencyUnit.MEGAHERTZ);
        }

        double gigaHertz = FrequencyConverter.convert(v, unit,
                FrequencyUnit.GIGAHERTZ);
        if (Math.abs(gigaHertz) < (FrequencyConverter.HERTZ_PER_TERAHERTZ /
                FrequencyConverter.HERTZ_PER_GIGAHERTZ)) {
            return format(gigaHertz, FrequencyUnit.GIGAHERTZ);
        }

        double teraHertz = FrequencyConverter.convert(v, unit,
                FrequencyUnit.TERAHERTZ);
        return format(teraHertz, FrequencyUnit.TERAHERTZ);
    }

    /**
     * Returns unit string representation.
     * @param unit a frequency unit.
     * @return its string representation
     */
    @Override
    public String getUnitSymbol(FrequencyUnit unit) {
        String unitStr;
        switch (unit) {
            case KILOHERTZ:
                unitStr = KILOHERTZ;
                break;
            case MEGAHERTZ:
                unitStr = MEGAHERTZ;
                break;
            case GIGAHERTZ:
                unitStr = GIGAHERTZ;
                break;
            case TERAHERTZ:
                unitStr = TERAHERTZ;
                break;

            case HERTZ:
            default:
                unitStr = HERTZ;
                break;
        }
        return unitStr;
    }

    /**
     * Returns unit system this instance will use based on its assigned locale.
     * Notice that if no locale was assigned, then the default system locale
     * will be used.
     * @return unit system this instance will use.
     */
    @Override
    public UnitSystem getUnitSystem() {
        return UnitSystem.METRIC;
    }
}
