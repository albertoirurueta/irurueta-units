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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.Objects;

/**
 * Base class to format and parse a given measure using its value and unit.
 *
 * @param <M> type of measurement (i.e. Distance or Surface).
 * @param <U> type of unit (i.e. DistanceUnit or SurfaceUnit).
 */
@SuppressWarnings("WeakerAccess")
public abstract class MeasureFormatter<M extends Measurement<U>, U extends Enum<?>> implements Cloneable {

    /**
     * Default pattern to format values and units together into a single string.
     * {0} corresponds to the value, {1} corresponds to the unit part.
     */
    public static final String DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN = "{0} {1}";

    /**
     * Internal string formatter.
     */
    NumberFormat numberFormat;

    /**
     * Internal string formatter.
     */
    private MessageFormat format;

    /**
     * Internal locale.
     */
    private Locale locale;

    /**
     * Pattern to format values and unit together into a single string. {0} corresponds to
     * the value, {1} corresponds to the unit part.
     */
    private String valueAndUnitFormatPattern;

    /**
     * Constructor.
     */
    MeasureFormatter() {
        numberFormat = NumberFormat.getInstance();
        locale = Locale.getDefault();
        valueAndUnitFormatPattern = DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN;
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    MeasureFormatter(final Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException();
        }

        numberFormat = NumberFormat.getInstance(locale);
        this.locale = locale;
        valueAndUnitFormatPattern = DEFAULT_VALUE_AND_UNIT_FORMAT_PATTERN;
    }

    /**
     * Determines if two measure formatters are equal by comparing all of its internal
     * parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MeasureFormatter)) {
            return false;
        }
        if (this == obj) {
            return true;
        }

        //noinspection unchecked
        final var other = (MeasureFormatter<M, U>) obj;
        return numberFormat.equals(other.numberFormat) &&
                locale.equals(other.locale) &&
                valueAndUnitFormatPattern.equals(other.valueAndUnitFormatPattern);
    }

    /**
     * Hash code generated for this instance.
     * Hash codes can be internally used by some collections to coarsely compare objects.
     *
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberFormat, format, locale, valueAndUnitFormatPattern);
    }

    /**
     * Formats provided measurement value and unit into a string representation.
     *
     * @param value a measurement value.
     * @param unit  a measurement unit.
     * @return string representation of provided measurement value and unit.
     */
    public String format(final Number value, final U unit) {
        return MessageFormat.format(valueAndUnitFormatPattern, numberFormat.format(value), getUnitSymbol(unit));
    }

    /**
     * Formats provided measurement value and unit into a string representation
     * and appends the result into provided string buffer.
     *
     * @param value      a measurement value.
     * @param unit       a measurement unit.
     * @param toAppendTo buffer to append the result to.
     * @param pos        field position where result will be appended.
     * @return provided string buffer where result is appended.
     */
    public StringBuffer format(
            final Number value, final U unit,
            final StringBuffer toAppendTo, final FieldPosition pos) {
        if (format == null) {
            format = new MessageFormat(valueAndUnitFormatPattern);
        }
        return format.format(new Object[]{numberFormat.format(value), getUnitSymbol(unit)}, toAppendTo, pos);
    }

    /**
     * Formats provided measurement value and unit into a string representation.
     *
     * @param value a measurement value.
     * @param unit  a measurement unit.
     * @return string representation of provided measurement value and unit.
     */
    public String format(final double value, final U unit) {
        return format(BigDecimal.valueOf(value), unit);
    }

    /**
     * Formats provided measurement value and unit into a string representation
     * and appends the result into provided string buffer.
     *
     * @param value      a measurement value.
     * @param unit       a measurement unit.
     * @param toAppendTo buffer to append the result to.
     * @param pos        field position where result will be appended.
     * @return provided string buffer where result is appended.
     */
    public StringBuffer format(
            final double value, final U unit, final StringBuffer toAppendTo, final FieldPosition pos) {
        return format(BigDecimal.valueOf(value), unit, toAppendTo, pos);
    }

    /**
     * Formats provided measurement into a string representation.
     *
     * @param measurement a measurement.
     * @return string representation of provided measurement.
     */
    public String format(final M measurement) {
        return format(measurement.getValue(), measurement.getUnit());
    }

    /**
     * Formats provided measurement into a string representation and appends the
     * result into provided string buffer.
     *
     * @param measurement a measurement.
     * @param toAppendTo  buffer to append the result to.
     * @param pos         field position where result will be appended.
     * @return provided string buffer where result is appended.
     */
    public StringBuffer format(
            final M measurement, final StringBuffer toAppendTo, final FieldPosition pos) {
        return format(measurement.getValue(), measurement.getUnit(), toAppendTo, pos);
    }

    /**
     * Formats and converts provided measurement value and unit using unit system
     * assigned to locale of this instance (if no locale has been provided it
     * is assumed that the system default locale is used).
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit.
     *
     * @param value a measurement value.
     * @param unit  a measurement unit.
     * @return a string representation of measurement value and unit.
     */
    public String formatAndConvert(final Number value, final U unit) {
        return formatAndConvert(value, unit, getUnitSystem());
    }

    /**
     * Formats and converts provided measurement value and unit using unit system
     * assigned to locale of this instance (if no locale has been provided it
     * is assumed that the system default locale is used).
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit.
     *
     * @param value a measurement value.
     * @param unit  a measurement unit.
     * @return a string representation of measurement value and unit.
     */
    public String formatAndConvert(final double value, final U unit) {
        return formatAndConvert(BigDecimal.valueOf(value), unit);
    }

    /**
     * Formats and converts provided measurement using unit system assigned to
     * locale of this instance (if no locale has been provided it is assumed
     * that the system default locale is used).
     * If provided measurement value is too large for its unit, this method
     * will convert it to a more appropriate unit.
     *
     * @param measurement measurement to be formatted.
     * @return a string representation of measurement value and unit.
     */
    public String formatAndConvert(final M measurement) {
        return formatAndConvert(measurement.getValue(), measurement.getUnit());
    }

    /**
     * Formats and converts provided measurement value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a measurement value.
     * @param unit   a measurement unit.
     * @param system system unit to convert measurement to.
     * @return a string representation of measurement value and unit.
     */
    public abstract String formatAndConvert(final Number value, final U unit, final UnitSystem system);

    /**
     * Formats and converts provided measurement value and unit using provided
     * unit system.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit using provided unit system (either
     * metric or imperial).
     *
     * @param value  a measurement value.
     * @param unit   a measurement unit.
     * @param system system unit to convert measurement to.
     * @return a string representation of measurement value and unit.
     */
    public String formatAndConvert(final double value, final U unit, final UnitSystem system) {
        return formatAndConvert(BigDecimal.valueOf(value), unit, system);
    }

    /**
     * Formats and converts provided measurement using provided unit system.
     * If provided measurement value is too large for its unit, this method
     * will convert it to a more appropriate unit using provided unit
     * system.
     *
     * @param measurement a measurement to be formatted.
     * @param unitSystem  system unit to convert measurement to.
     * @return a string representation of measurement value and unit.
     */
    public String formatAndConvert(final M measurement, final UnitSystem unitSystem) {
        return formatAndConvert(measurement.getValue(), measurement.getUnit(), unitSystem);
    }

    /**
     * Returns available locales for this formatter.
     *
     * @return available locales.
     */
    public static Locale[] getAvailableLocales() {
        return NumberFormat.getAvailableLocales();
    }

    /**
     * Gets locale assigned to this instance.
     * Locale determines number format and unit system (metric or imperial)
     * if not specified.
     *
     * @return a locale.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns maximum fraction digits to be shown when formatting a measure.
     *
     * @return maximum fraction digits.
     */
    public int getMaximumFractionDigits() {
        return numberFormat.getMaximumFractionDigits();
    }

    /**
     * Sets maximum fraction digits to use when formatting a measure.
     *
     * @param newValue maximum fraction digits to be set.
     */
    public void setMaximumFractionDigits(final int newValue) {
        numberFormat.setMaximumFractionDigits(newValue);
    }

    /**
     * Returns maximum integer digits to be shown when formatting a measure.
     *
     * @return maximum integer digits.
     */
    public int getMaximumIntegerDigits() {
        return numberFormat.getMaximumIntegerDigits();
    }

    /**
     * Sets maximum integer digits to use when formatting a measure.
     *
     * @param newValue maximum integer digits to be set.
     */
    public void setMaximumIntegerDigits(final int newValue) {
        numberFormat.setMaximumIntegerDigits(newValue);
    }

    /**
     * Returns minimum fraction digits to be shown when formatting a measure.
     *
     * @return minimum fraction digits.
     */
    public int getMinimumFractionDigits() {
        return numberFormat.getMinimumFractionDigits();
    }

    /**
     * Sets minimum fraction digits to use when formatting a measure.
     *
     * @param newValue minimum fraction digits to be set.
     */
    public void setMinimumFractionDigits(final int newValue) {
        numberFormat.setMinimumFractionDigits(newValue);
    }

    /**
     * Returns minimum integer digits to be shown when formatting a measure.
     *
     * @return minimum integer digits.
     */
    public int getMinimumIntegerDigits() {
        return numberFormat.getMinimumIntegerDigits();
    }

    /**
     * Sets minimum integer digits to use when formatting a measure.
     *
     * @param newValue minimum integer digits to be set.
     */
    public void setMinimumIntegerDigits(final int newValue) {
        numberFormat.setMinimumIntegerDigits(newValue);
    }

    /**
     * Returns rounding mode to be used when formatting a measure.
     *
     * @return rounding mode to be used when formatting a measure.
     */
    public RoundingMode getRoundingMode() {
        return numberFormat.getRoundingMode();
    }

    /**
     * Specifies rounding mode to use when formatting a measure.
     *
     * @param roundingMode rounding mode to be set.
     */
    public void setRoundingMode(final RoundingMode roundingMode) {
        numberFormat.setRoundingMode(roundingMode);
    }

    /**
     * Indicates if grouping is used when formatting a measure.
     *
     * @return true if grouping is used, false otherwise.
     */
    public boolean isGroupingUsed() {
        return numberFormat.isGroupingUsed();
    }

    /**
     * Sets if grouping is used when formatting a measure.
     *
     * @param newValue true if grouping is enabled, false otherwise.
     */
    public void setGroupingUsed(final boolean newValue) {
        numberFormat.setGroupingUsed(newValue);
    }

    /**
     * Indicates if only integer values are parsed.
     *
     * @return true if only integer values are parsed, false otherwise.
     */
    public boolean isParseIntegerOnly() {
        return numberFormat.isParseIntegerOnly();
    }

    /**
     * Specifies whether only integer values are parsed or not.
     *
     * @param value if true only integer values will be parsed.
     */
    public void setParseIntegerOnly(final boolean value) {
        numberFormat.setParseIntegerOnly(value);
    }

    /**
     * Obtains pattern to format values and unit together into a single string.
     * {0} corresponds to the value, {1} corresponds to the unit part.
     *
     * @return pattern to format values and unit together.
     */
    public String getValueAndUnitFormatPattern() {
        return valueAndUnitFormatPattern;
    }

    /**
     * Sets pattern to format values and unit together into a single string.
     * {0} corresponds to the value, {1} corresponds to the unit part.
     *
     * @param valueAndUnitFormatPattern pattern to format values and unit
     *                                  together.
     * @throws IllegalArgumentException if provided pattern is null.
     */
    public void setValueAndUnitFormatPattern(final String valueAndUnitFormatPattern) {
        if (valueAndUnitFormatPattern == null) {
            throw new IllegalArgumentException();
        }
        this.valueAndUnitFormatPattern = valueAndUnitFormatPattern;
    }

    /**
     * Returns unit system this instance will use based on its assigned locale.
     * Notice that if no locale was assigned, then the default system locale
     * will be used.
     *
     * @return unit system this instance will use.
     */
    public UnitSystem getUnitSystem() {
        return UnitLocale.getFrom(locale);
    }

    /**
     * Indicates whether provided string representation contains a valid
     * measurement unit or not.
     *
     * @param source a string measurement representation to be checked.
     * @return true if provided string has a valid (i.e. recognized) unit, false
     * otherwise.
     */
    public boolean isValidUnit(final String source) {
        return findUnit(source) != null;
    }

    /**
     * Indicates whether provided string representation is a valid measurement
     * representation or not.
     *
     * @param source a string measurement representation to be checked.
     * @return true if provided string representation is valid (contains valid
     * value and unit), false otherwise.
     */
    public boolean isValidMeasurement(final String source) {
        try {
            numberFormat.parse(source);
            return isValidUnit(source);
        } catch (final ParseException e) {
            return false;
        }
    }

    /**
     * Indicates whether provided string representation of a measurement
     * contains a metric system unit.
     *
     * @param source a measurement string representation to be checked.
     * @return true if found unit is metric, false otherwise or if unit
     * cannot be determined.
     */
    public boolean isMetricUnit(final String source) {
        return getUnitSystem(source) == UnitSystem.METRIC;
    }

    /**
     * Indicates whether provided string representation of a measurement
     * contains an imperial system unit.
     *
     * @param source a measurement string representation to be checked.
     * @return true if found unit is imperial, false otherwise or if unit
     * cannot be determined.
     */
    public boolean isImperialUnit(final String source) {
        return getUnitSystem(source) == UnitSystem.IMPERIAL;
    }

    /**
     * Gets unit system for detected unit into provided string representation
     * of a measurement.
     *
     * @param source a measurement string representation to be checked.
     * @return a unit system (either metric or imperial) or null if unit
     * cannot be determined.
     */
    public abstract UnitSystem getUnitSystem(final String source);

    /**
     * Parses a string into a measure.
     *
     * @param source text to be parsed.
     * @return a measure containing measure value and unit obtained from parsed
     * text.
     * @throws ParseException       if parsing failed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    public abstract M parse(final String source) throws ParseException, UnknownUnitException;

    /**
     * Finds measure unit from within a measurement string representation.
     *
     * @param source a measurement string representation.
     * @return a measure unit or null if none can be determined.
     */
    public abstract U findUnit(final String source);

    /**
     * Obtains measure unit symbol.
     *
     * @param unit a measure unit.
     * @return measure unit symbol.
     */
    public abstract String getUnitSymbol(final U unit);

    /**
     * Internal method to parse a string into a measure.
     *
     * @param source  text to be parsed.
     * @param measure a measure to be initialized with parsed contents.
     * @return provided measure.
     * @throws ParseException       if parsing failed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    M internalParse(final String source, final M measure) throws ParseException, UnknownUnitException {
        measure.setValue(numberFormat.parse(source));
        try {
            measure.setUnit(findUnit(source));
        } catch (final IllegalArgumentException e) {
            throw new UnknownUnitException(e);
        }
        return measure;
    }

    /**
     * Internal method to clone this measure formatter.
     *
     * @param copy an instantiated copy of a measure formatter that needs to be initialized.
     * @return provided copy.
     */
    MeasureFormatter<M, U> internalClone(final MeasureFormatter<M, U> copy) {
        copy.numberFormat = (NumberFormat) numberFormat.clone();
        if (format != null) {
            copy.format = (MessageFormat) format.clone();
        }
        if (locale != null) {
            copy.locale = (Locale) locale.clone();
        }
        return copy;
    }

    @Override
    protected MeasureFormatter<M , U> clone() throws CloneNotSupportedException {
        //noinspection unchecked
        return internalClone((MeasureFormatter<M , U>) super.clone());
    }
}
