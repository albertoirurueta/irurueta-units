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

import java.text.MessageFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formats and parses angle value and unit.
 */
@SuppressWarnings("WeakerAccess")
public class AngleFormatter extends MeasureFormatter<Angle, AngleUnit> implements Cloneable {

    /**
     * Degree symbol.
     */
    public static final String DEGREE = "º";

    /**
     * Radian symbol.
     */
    public static final String RADIAN = "rad";

    /**
     * Pattern to format degrees and minutes.
     */
    private static final String DEGREES_AND_MINUTES_MESSAGE_PATTERN = "{0}º {1}''";

    /**
     * Pattern to format degrees, minutes and seconds.
     */
    private static final String DEGREES_MINUTES_AND_SECONDS_MESSAGE_PATTERN =
            "{0}º {1}'' {2}\"";

    /**
     * Pattern to parse degrees and minutes expressions.
     */
    private static final String DEGREES_AND_MINUTES_PATTERN =
            "^(-?[0-9]+)º(\\s+)(\\d+)'";

    /**
     * Pattern to parse degrees, minutes and seconds expressions.
     */
    private static final String DEGREES_MINUTES_AND_SECONDS_PATTERN =
            "^(-?[0-9]+)º(\\s+)([0-9]+)'(\\s+)(\\d+)\"";

    /**
     * Pattern to parse angle in degrees and decimal minutes format.
     */
    private Pattern mDegreesAndMinutesPattern;

    /**
     * Pattern to parse angle in degrees, minutes and decimal seconds format.
     */
    private Pattern mDegreesMinutesAndSecondsPattern;

    /**
     * Constructor.
     */
    public AngleFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    public AngleFormatter(Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public AngleFormatter(AngleFormatter formatter) {
        this(formatter.getLocale());
        mDegreesAndMinutesPattern = formatter.mDegreesAndMinutesPattern;
        mDegreesMinutesAndSecondsPattern = formatter.mDegreesMinutesAndSecondsPattern;
    }

    /**
     * Clones this angle formatter.
     * @return a copy of this angle formatter.
     */
    @Override
    public Object clone() {
        AngleFormatter copy = new AngleFormatter();
        return internalClone(copy);
    }

    /**
     * Determines if two angle formatters are equal by comparing all of its internal
     * parameters.
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(Object obj) {
        boolean equals = super.equals(obj);
        return (obj instanceof AngleFormatter) && equals;
    }

    /**
     * Hash code generated for this instance.
     * Hash codes can be internally used by some collections to coarsely compare objects.
     * This implementation only calls parent implementation to avoid static analyzer warning.
     * @return hash code.
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Gets unit system for detected unit into provided string representation
     * of a measurement.
     * @param source a measurement string representation to be checked.
     * @return a unit system (metric) or null if unit cannot be determined.
     */
    @Override
    public UnitSystem getUnitSystem(String source) {
        AngleUnit unit = findUnit(source);
        return unit != null ? UnitSystem.METRIC : null;
    }

    /**
     * Parses provided string and tries to determine an angle value and unit.
     * @param source a string to be parsed.
     * @return an angle containing a value and unit.
     * @throws ParseException if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Angle parse(String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Angle());
    }

    /**
     * Attempts to determine an angle unit within a measurement string representation.
     * @param source an angle measurement string representation.
     * @return an angle unit, or null if none can be determined.
     */
    @Override
    public AngleUnit findUnit(String source) {
        if (source.contains(DEGREE + " ") || source.endsWith(DEGREE)) {
            return AngleUnit.DEGREES;
        }
        if (source.contains(RADIAN + " ") || source.endsWith(RADIAN)) {
            return AngleUnit.RADIANS;
        }
        return null;
    }

    /**
     * Formats and converts provided angle value and unit using metric system.
     * This implementation ignores provided unit system.
     * @param value a measurment value.
     * @param unit a measurement unit.
     * @param system ignored.
     * @return a string representation of angle value and unit.
     */
    @Override
    public String formatAndConvert(Number value, AngleUnit unit, UnitSystem system) {
        return format(value, unit);
    }

    /**
     * Returns unit string representation.
     * @param unit an angle unit.
     * @return its string representation.
     */
    @Override
    @SuppressWarnings("Duplicates")
    public String getUnitSymbol(AngleUnit unit) {
        String unitStr;
        switch (unit) {
            case DEGREES:
                unitStr = DEGREE;
                break;
            case RADIANS:
            default:
                unitStr = RADIAN;
                break;
        }
        return unitStr;
    }

    /**
     * Formats provided angle into degrees and decimal minutes.
     * @param angle angle to be formatted.
     * @return string representation of provided angle expressed as degrees and
     * decimal minutes.
     */
    public String formatDegreesAndMinutes(Angle angle) {
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);
        return MessageFormat.format(DEGREES_AND_MINUTES_MESSAGE_PATTERN,
                mNumberFormat.format(degreesAndMinutes[0]),
                mNumberFormat.format(degreesAndMinutes[1]));
    }

    /**
     * Formats provided angle into degrees, minutes and decimal seconds.
     * @param angle angle to be formatted.
     * @return string representation of provided angle expressed as degrees,
     * minutes and decimal seconds.
     */
    public String formatDegreesMinutesAndSeconds(Angle angle) {
        double[] degreesMinutesAndSeconds =
                AngleConverter.toDegreesMinutesAndSeconds(angle);
        return MessageFormat.format(DEGREES_MINUTES_AND_SECONDS_MESSAGE_PATTERN,
                mNumberFormat.format(degreesMinutesAndSeconds[0]),
                mNumberFormat.format(degreesMinutesAndSeconds[1]),
                mNumberFormat.format(degreesMinutesAndSeconds[2]));
    }

    /**
     * Parses provided string representation using degrees and decimal minutes format.
     * Note: decimals are not accepted on degrees value.
     * @param source string to be parsed.
     * @return parsed angle.
     * @throws ParseException if parsing fails.
     * @throws UnknownUnitException if format is not recognized.
     */
    @SuppressWarnings("Duplicates")
    public Angle parseDegreesAndMinutes(CharSequence source) throws ParseException,
            UnknownUnitException{
        if (mDegreesAndMinutesPattern == null) {
            mDegreesAndMinutesPattern = Pattern.compile(DEGREES_AND_MINUTES_PATTERN);
        }

        Matcher matcher = mDegreesAndMinutesPattern.matcher(source);
        if (!matcher.matches()) {
            throw new UnknownUnitException();
        }

        String degreeString = matcher.group(1);
        String minuteString = matcher.group(3);

        Number degree = mNumberFormat.parse(degreeString);
        Number minute = mNumberFormat.parse(minuteString);

        double angle = AngleConverter.fromDegreesAndMinutes(degree.intValue(),
                minute.doubleValue(), AngleUnit.DEGREES);
        return new Angle(angle, AngleUnit.DEGREES);
    }

    /**
     * Parses provided string representation using degrees, minutes and decimal
     * seconds format.
     * @param source string to be parsed.
     * @return parsed angle.
     * @throws ParseException if parsing fails.
     * @throws UnknownUnitException if format is not recognized.
     */
    @SuppressWarnings("Duplicates")
    public Angle parseDegreesMinutesAndSeconds(CharSequence source)
            throws ParseException, UnknownUnitException {
        if (mDegreesMinutesAndSecondsPattern == null) {
            mDegreesMinutesAndSecondsPattern = Pattern.compile(
                    DEGREES_MINUTES_AND_SECONDS_PATTERN);
        }

        Matcher matcher = mDegreesMinutesAndSecondsPattern.matcher(source);
        if (!matcher.matches()) {
            throw new UnknownUnitException();
        }

        String degreeString = matcher.group(1);
        String minuteString = matcher.group(3);
        String secondString = matcher.group(5);

        Number degree = mNumberFormat.parse(degreeString);
        Number minute = mNumberFormat.parse(minuteString);
        Number second = mNumberFormat.parse(secondString);

        double angle = AngleConverter.fromDegreesMinutesAndSeconds(degree.intValue(),
                minute.intValue(), second.doubleValue(), AngleUnit.DEGREES);
        return new Angle(angle, AngleUnit.DEGREES);
    }
}
