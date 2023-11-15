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
 * Formats and parses volume value and unit.
 */
public class VolumeFormatter extends MeasureFormatter<Volume, VolumeUnit> {

    /**
     * Cubic centimeter symbol.
     */
    public static final String CUBIC_CENTIMETER = "cm³";

    /**
     * Milliliter symbol.
     */
    public static final String MILLILITER = "mL";

    /**
     * Cubic decimeter symbol.
     */
    public static final String CUBIC_DECIMETER = "dm³";

    /**
     * Liter symbol.
     */
    public static final String LITER = "L";

    /**
     * Hectoliter symbol.
     */
    public static final String HECTOLITER = "hL";

    /**
     * Cubic meter symbol.
     */
    public static final String CUBIC_METER = "m³";

    /**
     * Cubic inch symbol.
     */
    public static final String CUBIC_INCH = "in³";

    /**
     * Pint symbol.
     */
    public static final String PINT = "pt";

    /**
     * Gallon symbol.
     */
    public static final String GALLON = "gal";

    /**
     * Cubic foot symbol.
     */
    public static final String CUBIC_FOOT = "ft³";

    /**
     * Barrel symbol.
     */
    public static final String BARREL = "bbl";

    /**
     * Constructor.
     */
    public VolumeFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public VolumeFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public VolumeFormatter(final VolumeFormatter formatter) {
        this(formatter.getLocale());
    }

    /**
     * Determines if two volume formatters are equal by comparing all of their internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final boolean equals = super.equals(obj);
        return (obj instanceof VolumeFormatter) && equals;
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
     * @return a unit system (either metric or imperial) or null if unit cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(final String source) {
        final VolumeUnit unit = findUnit(source);
        return unit != null ? VolumeUnit.getUnitSystem(unit) : null;
    }

    /**
     * Parses provided string and tries to determine a volume value and unit.
     *
     * @param source a string to be parsed.
     * @return a volume containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Volume parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Volume());
    }

    /**
     * Attempts to determine a volume unit within a measurement string representation.
     *
     * @param source a volume measurement string representation.
     * @return a volume unit, or null if none can be determined.
     */
    @Override
    public VolumeUnit findUnit(final String source) {
        if (source.contains(CUBIC_CENTIMETER + " ") || source.endsWith(CUBIC_CENTIMETER)) {
            return VolumeUnit.CUBIC_CENTIMETER;
        }
        if (source.contains(MILLILITER + " ") || source.endsWith(MILLILITER)) {
            return VolumeUnit.MILLILITER;
        }
        if (source.contains(CUBIC_DECIMETER + " ") || source.endsWith(CUBIC_DECIMETER)) {
            return VolumeUnit.CUBIC_DECIMETER;
        }
        if (source.contains(HECTOLITER + " ") || source.endsWith(HECTOLITER)) {
            return VolumeUnit.HECTOLITER;
        }
        if (source.contains(LITER + " ") || source.endsWith(LITER)) {
            return VolumeUnit.LITER;
        }
        if (source.contains(CUBIC_METER + " ") || source.endsWith(CUBIC_METER)) {
            return VolumeUnit.CUBIC_METER;
        }
        if (source.contains(CUBIC_INCH + " ") || source.endsWith(CUBIC_INCH)) {
            return VolumeUnit.CUBIC_INCH;
        }
        if (source.contains(PINT + " ") || source.endsWith(PINT)) {
            return VolumeUnit.PINT;
        }
        if (source.contains(GALLON + " ") || source.endsWith(GALLON)) {
            return VolumeUnit.GALLON;
        }
        if (source.contains(CUBIC_FOOT + " ") || source.endsWith(CUBIC_FOOT)) {
            return VolumeUnit.CUBIC_FOOT;
        }
        if (source.contains(BARREL + " ") || source.endsWith(BARREL)) {
            return VolumeUnit.BARREL;
        }

        return null;
    }

    /**
     * Formats and converts provided volume value and unit using provided unit system.
     * If provided value is too large for provided unit, this method will convert it
     * to a more appropriate unit using provided unit system (either metric or imperial).
     *
     * @param value  a volume value.
     * @param unit   a volume unit.
     * @param system system unit to convert measurement to.
     * @return a string representation of volume value and unit.
     */
    @Override
    public String formatAndConvert(final Number value, final VolumeUnit unit,
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
     * Formats and converts provided volume value and unit using metric unit system.
     * If provided volume value is too large for provided volume unit,
     * this method will convert to a more appropriate unit.
     *
     * @param value a volume value.
     * @param unit a volume unit.
     * @return a string representation of volume value and unit using metric unit
     * system.
     */
    public String formatAndConvertMetric(final Number value, final VolumeUnit unit) {
        final double v = value.doubleValue();

        final double cubicCentimeter = VolumeConverter.convert(v, unit,
                VolumeUnit.CUBIC_CENTIMETER);
        if (Math.abs(cubicCentimeter) < (VolumeConverter.CUBIC_METER_PER_LITER /
                VolumeConverter.CUBIC_METER_PER_CUBIC_CENTIMETER)) {
            return format(cubicCentimeter, VolumeUnit.CUBIC_CENTIMETER);
        }

        final double liter = VolumeConverter.convert(v, unit, VolumeUnit.LITER);
        if (Math.abs(liter) < (VolumeConverter.CUBIC_METER_PER_HECTOLITER /
                VolumeConverter.CUBIC_METER_PER_LITER)) {
            return format(liter, VolumeUnit.LITER);
        }

        final double hectoliter = VolumeConverter.convert(v, unit, VolumeUnit.HECTOLITER);
        if (Math.abs(hectoliter) < (1.0 / VolumeConverter.CUBIC_METER_PER_HECTOLITER)) {
            return format(hectoliter, VolumeUnit.HECTOLITER);
        }

        final double cubicMeter = VolumeConverter.convert(v, unit, VolumeUnit.CUBIC_METER);
        return format(cubicMeter, VolumeUnit.CUBIC_METER);
    }

    /**
     * Formats and converts provided volume value and unit using imperial unit system.
     * If provided volume value is too large for provided volume unit,
     * this method will convert to a more appropriate unit.
     *
     * @param value a volume value.
     * @param unit a volume unit.
     * @return a string representation of volume value and unit using imperial unit
     * system.
     */
    public String formatAndConvertImperial(final Number value, final VolumeUnit unit) {
        final double v = value.doubleValue();

        final double cubicInch = VolumeConverter.convert(v, unit, VolumeUnit.CUBIC_INCH);
        if (Math.abs(cubicInch) < (VolumeConverter.CUBIC_METER_PER_PINT /
                VolumeConverter.CUBIC_METER_PER_CUBIC_INCH)) {
            return format(cubicInch, VolumeUnit.CUBIC_INCH);
        }

        final double pint = VolumeConverter.convert(v, unit, VolumeUnit.PINT);
        if (Math.abs(pint) < (VolumeConverter.CUBIC_METER_PER_GALLON /
                VolumeConverter.CUBIC_METER_PER_PINT)) {
            return format(pint, VolumeUnit.PINT);
        }

        final double gallon = VolumeConverter.convert(v, unit, VolumeUnit.GALLON);
        if (Math.abs(gallon) < (VolumeConverter.CUBIC_METER_PER_CUBIC_FOOT /
                VolumeConverter.CUBIC_METER_PER_GALLON)) {
            return format(gallon, VolumeUnit.GALLON);
        }

        final double cubicFoot = VolumeConverter.convert(v, unit, VolumeUnit.CUBIC_FOOT);
        if (Math.abs(cubicFoot) < (VolumeConverter.CUBIC_METER_PER_BARREL /
                VolumeConverter.CUBIC_METER_PER_CUBIC_FOOT)) {
            return format(cubicFoot, VolumeUnit.CUBIC_FOOT);
        }

        final double barrel = VolumeConverter.convert(v, unit, VolumeUnit.BARREL);
        return format(barrel, VolumeUnit.BARREL);
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a volume unit.
     * @return its string representation.
     */
    @SuppressWarnings("DuplicatedCode")
    @Override
    public String getUnitSymbol(final VolumeUnit unit) {
        switch (unit) {
            case CUBIC_CENTIMETER:
                return CUBIC_CENTIMETER;
            case MILLILITER:
                return MILLILITER;
            case CUBIC_DECIMETER:
                return CUBIC_DECIMETER;
            case LITER:
                return LITER;
            case HECTOLITER:
                return HECTOLITER;
            case CUBIC_INCH:
                return CUBIC_INCH;
            case PINT:
                return PINT;
            case GALLON:
                return GALLON;
            case CUBIC_FOOT:
                return CUBIC_FOOT;
            case BARREL:
                return BARREL;

            case CUBIC_METER:
            default:
                return CUBIC_METER;

        }
    }
}
