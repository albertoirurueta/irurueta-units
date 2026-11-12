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

import java.text.FieldPosition;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Formats and parses time value and unit.
 */
@SuppressWarnings("DuplicatedCode")
public class TimeFormatter extends MeasureFormatter<Time, TimeUnit> {

    /**
     * Flag indicating whether nanoseconds must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_NANOSECONDS = 1;

    /**
     * Flag indicating whether microseconds must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_MICROSECONDS = 1 << 1;

    /**
     * Flag indicating whether milliseconds must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_MILLISECONDS = 1 << 2;

    /**
     * Flag indicating whether seconds must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_SECONDS = 1 << TimeFormatter.SECONDS_BIT;

    /**
     * Flag indicating whether minutes must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_MINUTES = 1 << TimeFormatter.MINUTES_BIT;

    /**
     * Flag indicating whether hours must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_HOURS = 1 << TimeFormatter.HOURS_BIT;

    /**
     * Flag indicating whether days must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_DAYS = 1 << TimeFormatter.DAYS_BIT;

    /**
     * Flag indicating whether weeks must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_WEEKS = 1 << TimeFormatter.WEEKS_BIT;

    /**
     * Flag indicating whether months must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_MONTHS = 1 << TimeFormatter.MONTHS_BIT;

    /**
     * Flag indicating whether years must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_YEARS = 1 << TimeFormatter.YEARS_BIT;

    /**
     * Flag indicating whether centuries must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_CENTURIES = 1 << TimeFormatter.CENTURIES_BIT;

    /**
     * Flag indicating that all units must be included when formatting a time
     * instance.
     */
    public static final int FORMAT_ALL = FORMAT_NANOSECONDS | FORMAT_MICROSECONDS | FORMAT_MILLISECONDS
            | FORMAT_SECONDS | FORMAT_MINUTES | FORMAT_HOURS | FORMAT_DAYS | FORMAT_WEEKS | FORMAT_MONTHS
            | FORMAT_YEARS | FORMAT_CENTURIES;

    /**
     * Flag indicating that all time units (smaller than a day) must be used when
     * formatting a time instance.
     */
    public static final int FORMAT_TIME_ALL = FORMAT_NANOSECONDS | FORMAT_MICROSECONDS | FORMAT_MILLISECONDS
            | FORMAT_SECONDS | FORMAT_MINUTES | FORMAT_HOURS;

    /**
     * Flag indicating that standard time units (hours, minutes and seconds) must be
     * used when formatting a time instance.
     */
    public static final int FORMAT_TIME_STANDARD = FORMAT_SECONDS | FORMAT_MINUTES | FORMAT_HOURS;

    /**
     * Flag indicating that all date units (greater than hours) must be used when
     * formatting a time instance.
     */
    public static final int FORMAT_DATE_ALL = FORMAT_DAYS | FORMAT_WEEKS | FORMAT_MONTHS | FORMAT_YEARS
            | FORMAT_CENTURIES;

    /**
     * Flag indicating that standard date units (days, months and years) must be
     * used when formatting a time instance.
     */
    public static final int FORMAT_DATE_STANDARD = FORMAT_DAYS | FORMAT_MONTHS | FORMAT_YEARS;

    /**
     * Nanosecond symbol.
     */
    public static final String NANOSECOND_SYMBOL = "ns";

    /**
     * Microsecond symbol.
     */
    public static final String MICROSECOND_SYMBOL = "µs";

    /**
     * Millisecond symbol.
     */
    public static final String MILLISECOND_SYMBOL = "ms";

    /**
     * Second symbol.
     */
    public static final String SECOND_SYMBOL = "s";

    /**
     * Minute symbol.
     */
    public static final String MINUTE_SYMBOL = "min";

    /**
     * Hour symbol.
     */
    public static final String HOUR_SYMBOL = "h";

    /**
     * Day symbol.
     */
    public static final String DAY_SYMBOL = "d";

    /**
     * Week symbol.
     */
    public static final String WEEK_SYMBOL = "wk";

    /**
     * Month symbol.
     */
    public static final String MONTH_SYMBOL = "mon";

    /**
     * Year symbol.
     */
    public static final String YEAR_SYMBOL = "yr";

    /**
     * n-th century symbol.
     */
    public static final String CENTURY_SYMBOL = "th c.";

    /**
     * Bit position of the {@link #FORMAT_SECONDS} flag.
     */
    private static final int SECONDS_BIT = 3;

    /**
     * Bit position of the {@link #FORMAT_MINUTES} flag.
     */
    private static final int MINUTES_BIT = 4;

    /**
     * Bit position of the {@link #FORMAT_HOURS} flag.
     */
    private static final int HOURS_BIT = 5;

    /**
     * Bit position of the {@link #FORMAT_DAYS} flag.
     */
    private static final int DAYS_BIT = 6;

    /**
     * Bit position of the {@link #FORMAT_WEEKS} flag.
     */
    private static final int WEEKS_BIT = 7;

    /**
     * Bit position of the {@link #FORMAT_MONTHS} flag.
     */
    private static final int MONTHS_BIT = 8;

    /**
     * Bit position of the {@link #FORMAT_YEARS} flag.
     */
    private static final int YEARS_BIT = 9;

    /**
     * Bit position of the {@link #FORMAT_CENTURIES} flag.
     */
    private static final int CENTURIES_BIT = 10;

    /**
     * First century symbol.
     */
    private static final String FIRST_CENTURY_SYMBOL = "st c.";

    /**
     * Second century symbol.
     */
    private static final String SECOND_CENTURY_SYMBOL = "nd c.";

    /**
     * Third century symbol.
     */
    private static final String THIRD_CENTURY_SIMBOL = "rd c.";

    /**
     * Default pattern to format centuries.
     * {0} corresponds to the value, {1} corresponds to the unit part.
     */
    private static final String CENTURY_FORMAT_PATTERN = "{0}{1}";

    /**
     * Minimum number of digits for hours and minutes.
     */
    private static final int INTEGER_DIGITS = 2;

    /**
     * Pattern to format hour and minutes (hh:mm.s).
     */
    private static final String HOUR_MINUTE_PATTERN = "^(\\d+):(\\d{2,})$";


    /**
     * Pattern to format hour, minutes and seconds (hh:mm:ss.ms).
     */
    private static final String HOUR_MINUTE_SECOND_PATTERN = "^(\\d+):(\\d{2}):(\\d{2,})$";

    /**
     * Pattern to parse 1st century format.
     */
    private static final String FIRST_CENTURY_PATTERN = "(.*)(\\s+)(\\d+)(\\s?st c.)(\\s+)(.*)";

    /**
     * Pattern to parse 2nd century format.
     */
    private static final String SECOND_CENTURY_PATTERN = "(.*)(\\s+)(\\d+)(\\s?nd c.)(\\s+)(.*)";

    /**
     * Pattern to parse 3rd century format.
     */
    private static final String THIRD_CENTURY_PATTERN = "(.*)(\\s+)(\\d+)(\\s?rd c.)(\\s+)(.*)";

    /**
     * Pattern to parse n-th century format.
     */
    private static final String CENTURY_PATTERN = "(.*)(\\s+)(\\d+)(\\s?th c.)(\\s+)(.*)";

    /**
     * Pattern to parse year format.
     */
    private static final String YEAR_PATTERN = "(.*)(\\s+)(\\d+)(\\s?yr)(\\s+)(.*)";

    /**
     * Pattern to parse month format.
     */
    private static final String MONTH_PATTERN = "(.*)(\\s+)(\\d+)(\\s?mon)(\\s+)(.*)";

    /**
     * Pattern to parse week format.
     */
    private static final String WEEK_PATTERN = "(.*)(\\s+)(\\d+)(\\s?wk)(\\s+)(.*)";

    /**
     * Pattern to parse day format.
     */
    private static final String DAY_PATTERN = "(.*)(\\s+)(\\d+)(\\s?d)(\\s+)(.*)";

    /**
     * Pattern to parse hour format.
     */
    private static final String HOUR_PATTERN = "(.*)(\\s+)(\\d+)(\\s?h)(\\s+)(.*)";

    /**
     * Pattern to parse minute format.
     */
    private static final String MINUTE_PATTERN = "(.*)(\\s+)(\\d+)(\\s?min)(\\s+)(.*)";

    /**
     * Pattern to parse second format.
     */
    private static final String SECOND_PATTERN = "(.*)(\\s+)(\\d+)(\\s?s)(\\s+)(.*)";

    /**
     * Pattern to parse millisecond format.
     */
    private static final String MILLISECOND_PATTERN = "(.*)(\\s+)(\\d+)(\\s?ms)(\\s+)(.*)";

    /**
     * Pattern to parse microsecond format.
     */
    private static final String MICROSECOND_PATTERN = "(.*)(\\s+)(\\d+)(\\s?µs)(\\s+)(.*)";

    /**
     * Pattern to parse nanosecond format.
     */
    private static final String NANOSECOND_PATTERN = "(.*)(\\s+)(\\d+)(\\s?ns)(\\s+)(.*)";

    /**
     * Defines a space.
     */
    private static final String SPACE = " ";

    /**
     * Colon separator used in hour, minute and second formats.
     */
    private static final String COLON = ":";

    /**
     * Maximum absolute value of centuries that is still formatted using the third century symbol.
     */
    private static final double THIRD_CENTURY_LIMIT = 3.0;

    /**
     * Index of the regular expression group containing the numeric value when parsing a unit.
     */
    private static final int VALUE_GROUP = 3;

    /**
     * Index of the amount to be shown for a given unit within the array returned when splitting units.
     */
    private static final int VALUE_INDEX = 0;

    /**
     * Index of the remainder to be expressed using smaller units within the array returned when splitting units.
     */
    private static final int DIFF_INDEX = 1;

    /**
     * Pattern to parse time in hour and minute format (hh:mm.s).
     */
    private Pattern hourMinutePattern;

    /**
     * Pattern to parse time in hour, minute and second format (hh:mm:ss.ms).
     */
    private Pattern hourMinuteSecondPattern;

    /**
     * Pattern to parse 1st century format.
     */
    private Pattern firstCenturyPattern;

    /**
     * Pattern to parse 2nd century format.
     */
    private Pattern secondCenturyPattern;

    /**
     * Pattern to parse 3rd century format.
     */
    private Pattern thirdCenturyPattern;

    /**
     * Pattern to parse n-th century format.
     */
    private Pattern centuryPattern;

    /**
     * Pattern to parse year format.
     */
    private Pattern yearPattern;

    /**
     * Pattern to parse month format.
     */
    private Pattern monthPattern;

    /**
     * Pattern to parse week format.
     */
    private Pattern weekPattern;

    /**
     * Pattern to parse day format.
     */
    private Pattern dayPattern;

    /**
     * Pattern to parse hour format.
     */
    private Pattern hourPattern;

    /**
     * Pattern to parse minute format.
     */
    private Pattern minutePattern;

    /**
     * Pattern to parse second format.
     */
    private Pattern secondPattern;

    /**
     * Pattern to parse millisecond format.
     */
    private Pattern millisecondPattern;

    /**
     * Pattern to parse microsecond format.
     */
    private Pattern microsecondPattern;

    /**
     * Pattern to parse nanosecond format.
     */
    private Pattern nanosecondPattern;

    /**
     * Constructor.
     */
    @SuppressWarnings("WeakerAccess")
    public TimeFormatter() {
        super();
    }

    /**
     * Constructor with locale.
     *
     * @param locale locale.
     * @throws IllegalArgumentException if locale is null.
     */
    @SuppressWarnings("WeakerAccess")
    public TimeFormatter(final Locale locale) {
        super(locale);
    }

    /**
     * Copy constructor.
     *
     * @param formatter input instance to copy from.
     * @throws NullPointerException if provided formatter is null.
     */
    public TimeFormatter(final TimeFormatter formatter) {
        this(formatter.getLocale());
        hourMinutePattern = formatter.hourMinutePattern;
        hourMinuteSecondPattern = formatter.hourMinuteSecondPattern;
        firstCenturyPattern = formatter.firstCenturyPattern;
        secondCenturyPattern = formatter.secondCenturyPattern;
        thirdCenturyPattern = formatter.thirdCenturyPattern;
        centuryPattern = formatter.centuryPattern;
        yearPattern = formatter.yearPattern;
        monthPattern = formatter.monthPattern;
        weekPattern = formatter.weekPattern;
        dayPattern = formatter.dayPattern;
        hourPattern = formatter.hourPattern;
        minutePattern = formatter.minutePattern;
        secondPattern = formatter.secondPattern;
        millisecondPattern = formatter.millisecondPattern;
        microsecondPattern = formatter.microsecondPattern;
        nanosecondPattern = formatter.nanosecondPattern;
    }

    /**
     * Determines if two time formatters are equal by comparing all of its internal parameters.
     *
     * @param obj another object to compare.
     * @return true if provided object is assumed to be equal to this instance.
     */
    @Override
    public boolean equals(final Object obj) {
        final var equals = super.equals(obj);
        return (obj instanceof TimeFormatter) && equals;
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
     * @return returns metric system only for units belonging to the International
     * System of units.
     */
    @Override
    public UnitSystem getUnitSystem(final String source) {
        final var unit = findUnit(source);
        try {
            return unit != null ? TimeUnit.getUnitSystem(unit) : null;
        } catch (final IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Formats provided time value and unit into a string representation.
     *
     * @param value a time value.
     * @param unit  a time unit.
     * @return string representation of provided measurement value and unit.
     */
    @Override
    public String format(final Number value, final TimeUnit unit) {
        if (unit == TimeUnit.CENTURY) {
            final var v = value.doubleValue();

            final String symbol;
            if (Math.abs(v) <= 1.0) {
                symbol = FIRST_CENTURY_SYMBOL;
            } else if (Math.abs(v) <= 2.0) {
                symbol = SECOND_CENTURY_SYMBOL;
            } else if (Math.abs(v) <= THIRD_CENTURY_LIMIT) {
                symbol = THIRD_CENTURY_SIMBOL;
            } else {
                symbol = CENTURY_SYMBOL;
            }
            return MessageFormat.format(CENTURY_FORMAT_PATTERN, getNumberFormat().format(value), symbol);
        } else {
            return super.format(value, unit);
        }
    }

    /**
     * Formats provided time value and unit into a string representation
     * and appends the result into provided string buffer.
     *
     * @param value      a time value.
     * @param unit       a time unit.
     * @param toAppendTo buffer to append the result to.
     * @param pos        field position where result will be appended.
     * @return provided string buffer where result is appended.
     */
    @Override
    public StringBuffer format(
            final Number value, final TimeUnit unit, final StringBuffer toAppendTo, final FieldPosition pos) {
        if (unit == TimeUnit.CENTURY) {
            final var v = value.doubleValue();

            final String symbol;
            if (Math.abs(v) <= 1.0) {
                symbol = FIRST_CENTURY_SYMBOL;
            } else if (Math.abs(v) <= 2.0) {
                symbol = SECOND_CENTURY_SYMBOL;
            } else if (Math.abs(v) <= THIRD_CENTURY_LIMIT) {
                symbol = THIRD_CENTURY_SIMBOL;
            } else {
                symbol = CENTURY_SYMBOL;
            }

            final var format = new MessageFormat(CENTURY_FORMAT_PATTERN);
            return format.format(new Object[]{getNumberFormat().format(value), symbol}, toAppendTo, pos);
        } else {
            return super.format(value, unit, toAppendTo, pos);
        }
    }

    /**
     * Formats and converts provided time value and unit.
     * Unit system is ignored since time is always expressed in metric system,
     * but some units might not belong to the international system of units.
     * If provided value is too large for provided unit, this method will
     * convert it to a more appropriate unit.
     *
     * @param value  a measurement value.
     * @param unit   a measurement unit.
     * @param system it is ignored.
     * @return a string representation of measurement value and unit.
     */
    @Override
    public String formatAndConvert(
            final Number value, final TimeUnit unit, final UnitSystem system) {
        final var v = value.doubleValue();

        final var nanoseconds = TimeConverter.convert(v, unit, TimeUnit.NANOSECOND);
        if (Math.abs(nanoseconds) < (TimeConverter.SECONDS_PER_MICROSECOND / TimeConverter.SECONDS_PER_NANOSECOND)) {
            return format(nanoseconds, TimeUnit.NANOSECOND);
        }

        final var microseconds = TimeConverter.convert(v, unit, TimeUnit.MICROSECOND);
        if (Math.abs(microseconds) < (TimeConverter.SECONDS_PER_MILLISECOND / TimeConverter.SECONDS_PER_MICROSECOND)) {
            return format(microseconds, TimeUnit.MICROSECOND);
        }

        final var milliseconds = TimeConverter.convert(v, unit, TimeUnit.MILLISECOND);
        if (Math.abs(milliseconds) < (1.0 / TimeConverter.SECONDS_PER_MILLISECOND)) {
            return format(milliseconds, TimeUnit.MILLISECOND);
        }

        final var seconds = TimeConverter.convert(v, unit, TimeUnit.SECOND);
        if (Math.abs(seconds) < TimeConverter.SECONDS_PER_MINUTE) {
            return format(seconds, TimeUnit.SECOND);
        }

        final var minutes = TimeConverter.convert(v, unit, TimeUnit.MINUTE);
        if (Math.abs(minutes) < (TimeConverter.SECONDS_PER_HOUR / TimeConverter.SECONDS_PER_MINUTE)) {
            return format(minutes, TimeUnit.MINUTE);
        }

        final var hours = TimeConverter.convert(v, unit, TimeUnit.HOUR);
        if (Math.abs(hours) < (TimeConverter.SECONDS_PER_DAY / TimeConverter.SECONDS_PER_HOUR)) {
            return format(hours, TimeUnit.HOUR);
        }

        final var days = TimeConverter.convert(v, unit, TimeUnit.DAY);
        if (Math.abs(days) < (TimeConverter.SECONDS_PER_WEEK / TimeConverter.SECONDS_PER_DAY)) {
            return format(days, TimeUnit.DAY);
        }

        final var weeks = TimeConverter.convert(v, unit, TimeUnit.WEEK);
        if (Math.abs(weeks) < (TimeConverter.SECONDS_PER_MONTH / TimeConverter.SECONDS_PER_WEEK)) {
            return format(weeks, TimeUnit.WEEK);
        }

        final var months = TimeConverter.convert(v, unit, TimeUnit.MONTH);
        if (Math.abs(months) < (TimeConverter.SECONDS_PER_YEAR / TimeConverter.SECONDS_PER_MONTH)) {
            return format(months, TimeUnit.MONTH);
        }

        final var years = TimeConverter.convert(v, unit, TimeUnit.YEAR);
        if (Math.abs(years) < (TimeConverter.SECONDS_PER_CENTURY / TimeConverter.SECONDS_PER_YEAR)) {
            return format(years, TimeUnit.YEAR);
        }

        final var centuries = TimeConverter.convert(v, unit, TimeUnit.CENTURY);
        return format(centuries, TimeUnit.CENTURY);
    }

    /**
     * Returns unit system this instance will use based on its assigned locale.
     *
     * @return always returns metric system
     */
    @Override
    public UnitSystem getUnitSystem() {
        return UnitSystem.METRIC;
    }


    /**
     * Parses provided string and tries to determine a time value and unit.
     *
     * @param source text to be parsed.
     * @return time containing a value and unit.
     * @throws ParseException       if provided string cannot be parsed.
     * @throws UnknownUnitException if unit cannot be determined.
     */
    @Override
    public Time parse(final String source) throws ParseException, UnknownUnitException {
        return internalParse(source, new Time());
    }

    /**
     * Attempts to determine a time unit within a measurement string
     * representation.
     *
     * @param source a measurement string representation.
     * @return a time unit, or null if none can be determined.
     */
    @Override
    public TimeUnit findUnit(final String source) {
        if (containsSymbol(source, NANOSECOND_SYMBOL)) {
            return TimeUnit.NANOSECOND;
        }
        if (containsSymbol(source, MICROSECOND_SYMBOL)) {
            return TimeUnit.MICROSECOND;
        }
        if (containsSymbol(source, MILLISECOND_SYMBOL)) {
            return TimeUnit.MILLISECOND;
        }
        if (containsSymbol(source, SECOND_SYMBOL)) {
            return TimeUnit.SECOND;
        }
        if (containsSymbol(source, MINUTE_SYMBOL)) {
            return TimeUnit.MINUTE;
        }
        if (containsSymbol(source, WEEK_SYMBOL)) {
            return TimeUnit.WEEK;
        }
        if (containsSymbol(source, MONTH_SYMBOL)) {
            return TimeUnit.MONTH;
        }
        if (containsSymbol(source, YEAR_SYMBOL)) {
            return TimeUnit.YEAR;
        }
        if (source.contains(CENTURY_SYMBOL) || source.contains(FIRST_CENTURY_SYMBOL)
                || source.contains(SECOND_CENTURY_SYMBOL) || source.contains(THIRD_CENTURY_SIMBOL)) {
            return TimeUnit.CENTURY;
        }

        if (containsSymbol(source, DAY_SYMBOL)) {
            return TimeUnit.DAY;
        }
        if (containsSymbol(source, HOUR_SYMBOL)) {
            return TimeUnit.HOUR;
        }

        return null;
    }

    /**
     * Returns unit string representation.
     *
     * @param unit a measure unit.
     * @return its string representation.
     */
    @Override
    public String getUnitSymbol(final TimeUnit unit) {
        return switch (unit) {
            case NANOSECOND -> NANOSECOND_SYMBOL;
            case MICROSECOND -> MICROSECOND_SYMBOL;
            case MILLISECOND -> MILLISECOND_SYMBOL;
            case MINUTE -> MINUTE_SYMBOL;
            case HOUR -> HOUR_SYMBOL;
            case DAY -> DAY_SYMBOL;
            case WEEK -> WEEK_SYMBOL;
            case MONTH -> MONTH_SYMBOL;
            case YEAR -> YEAR_SYMBOL;
            case CENTURY -> CENTURY_SYMBOL;
            default -> SECOND_SYMBOL;
        };
    }

    /**
     * Formats time instance using hour and minute format (hh:mm.ms).
     *
     * @param time time to be formatted.
     * @return a string representation of provided time instance using hour and
     * minute format (hh:mm.ms).
     */
    public String formatHourMinute(final Time time) {
        final var exactHours = TimeConverter.convert(time.getValue().doubleValue(), time.getUnit(), TimeUnit.HOUR);
        final var hours = Math.floor(exactHours);
        final var diffHours = exactHours - hours;

        final var minutes = TimeConverter.convert(diffHours, TimeUnit.HOUR, TimeUnit.MINUTE);

        final var hourFormat = NumberFormat.getInstance(getLocale());
        hourFormat.setMinimumIntegerDigits(INTEGER_DIGITS);
        hourFormat.setMinimumFractionDigits(0);
        hourFormat.setMaximumFractionDigits(0);

        final var minuteFormat = NumberFormat.getInstance(getLocale());
        minuteFormat.setMinimumIntegerDigits(INTEGER_DIGITS);
        minuteFormat.setMaximumIntegerDigits(INTEGER_DIGITS);

        final var nf = getNumberFormat();
        hourFormat.setMaximumIntegerDigits(nf.getMaximumIntegerDigits());
        hourFormat.setRoundingMode(nf.getRoundingMode());
        hourFormat.setGroupingUsed(nf.isGroupingUsed());

        minuteFormat.setMinimumFractionDigits(nf.getMinimumFractionDigits());
        minuteFormat.setMaximumFractionDigits(nf.getMaximumFractionDigits());
        minuteFormat.setRoundingMode(nf.getRoundingMode());
        minuteFormat.setGroupingUsed(nf.isGroupingUsed());
        return hourFormat.format(hours) + COLON + minuteFormat.format(minutes);
    }

    /**
     * Parses provided string representation using hour and minute format (hh:mm).
     * Note: decimals are not accepted on either hour or minute values.
     *
     * @param source string to be parsed.
     * @return parsed time.
     * @throws ParseException       if parsing fails.
     * @throws UnknownUnitException if format is not recognized.
     */
    public Time parseHourMinute(final CharSequence source)
            throws ParseException, UnknownUnitException {
        if (hourMinutePattern == null) {
            hourMinutePattern = Pattern.compile(HOUR_MINUTE_PATTERN);
        }

        final var matcher = hourMinutePattern.matcher(source);
        if (!matcher.matches()) {
            throw new UnknownUnitException();
        }

        final var hourString = matcher.group(1);
        final var minuteString = matcher.group(2);

        final var hour = getNumberFormat().parse(hourString);
        final var minute = getNumberFormat().parse(minuteString);

        final var result = new Time(hour, TimeUnit.HOUR);
        TimeConverter.convert(result, TimeUnit.MINUTE);
        result.add(new Time(minute, TimeUnit.MINUTE));
        return result;
    }

    /**
     * Formats this instance using hour, minute and second format (hh:mm::ss.ms).
     *
     * @param time time to be formatted.
     * @return a string representation of provided time instance using hour, minute
     * and second format (hh:mm:ss.ms).
     */
    public String formatHourMinuteSecond(final Time time) {
        final var exactHours = TimeConverter.convert(time.getValue().doubleValue(), time.getUnit(), TimeUnit.HOUR);
        final var hours = Math.floor(exactHours);
        final var diffHours = exactHours - hours;

        final var exactMinutes = TimeConverter.convert(diffHours, TimeUnit.HOUR, TimeUnit.MINUTE);
        final var minutes = Math.floor(exactMinutes);
        final var diffMinutes = exactMinutes - minutes;

        final var seconds = TimeConverter.convert(diffMinutes, TimeUnit.MINUTE, TimeUnit.SECOND);

        final var hourFormat = NumberFormat.getInstance(getLocale());
        hourFormat.setMinimumIntegerDigits(INTEGER_DIGITS);
        hourFormat.setMinimumFractionDigits(0);
        hourFormat.setMaximumFractionDigits(0);

        final var minuteFormat = NumberFormat.getInstance(getLocale());
        minuteFormat.setMinimumIntegerDigits(INTEGER_DIGITS);
        minuteFormat.setMinimumFractionDigits(0);
        minuteFormat.setMaximumFractionDigits(0);

        final var secondFormat = NumberFormat.getInstance(getLocale());
        secondFormat.setMinimumIntegerDigits(INTEGER_DIGITS);
        secondFormat.setMaximumIntegerDigits(INTEGER_DIGITS);

        final var nf = getNumberFormat();
        hourFormat.setMaximumIntegerDigits(nf.getMaximumIntegerDigits());
        hourFormat.setRoundingMode(nf.getRoundingMode());
        hourFormat.setGroupingUsed(nf.isGroupingUsed());

        minuteFormat.setMaximumIntegerDigits(nf.getMaximumIntegerDigits());
        minuteFormat.setRoundingMode(nf.getRoundingMode());
        minuteFormat.setGroupingUsed(nf.isGroupingUsed());

        secondFormat.setMinimumFractionDigits(nf.getMinimumFractionDigits());
        secondFormat.setMaximumFractionDigits(nf.getMaximumFractionDigits());
        secondFormat.setRoundingMode(nf.getRoundingMode());
        secondFormat.setGroupingUsed(nf.isGroupingUsed());
        return hourFormat.format(hours) + COLON + minuteFormat.format(minutes) + COLON + secondFormat.format(seconds);
    }

    /**
     * Parses provided string representation using hour, minute and second format (hh:mm:ss).
     * Note: decimals are not accepted on either hour, minute or seconds values
     *
     * @param source string to be parsed.
     * @return parsed time.
     * @throws ParseException       if parsing fails.
     * @throws UnknownUnitException if format is not recognized.
     */
    public Time parseHourMinuteSecond(final CharSequence source) throws ParseException, UnknownUnitException {
        if (hourMinuteSecondPattern == null) {
            hourMinuteSecondPattern = Pattern.compile(HOUR_MINUTE_SECOND_PATTERN);
        }

        final var matcher = hourMinuteSecondPattern.matcher(source);
        if (!matcher.matches()) {
            throw new UnknownUnitException();
        }

        final var hourString = matcher.group(1);
        final var minuteString = matcher.group(2);
        final var secondString = matcher.group(3);

        final var hour = getNumberFormat().parse(hourString);
        final var minute = getNumberFormat().parse(minuteString);
        final var second = getNumberFormat().parse(secondString);

        final var result = new Time(hour, TimeUnit.HOUR);
        TimeConverter.convert(result, TimeUnit.MINUTE);
        result.add(new Time(minute, TimeUnit.MINUTE));
        TimeConverter.convert(result, TimeUnit.SECOND);
        result.add(new Time(second, TimeUnit.SECOND));
        return result;
    }

    /**
     * Formats provided time instance using required units as flags.
     * Flags can be provided as bitwise combinations of FORMAT constants.
     * Only non zero units will be included.
     *
     * @param time  time to be formatted.
     * @param flags flags indicating units to include.
     * @return formatted time.
     */
    public String formatMultiple(final Time time, final int flags) {
        return formatMultiple(time, flags, true);
    }

    /**
     * Formats provided time instance using required units as flags.
     * Flags can be provided as bitwise combinations of FORMAT constants.
     *
     * @param time        time to be formatted.
     * @param flags       flags indicating units to include.
     * @param onlyNonZero true indicates to include only non zero units, false to
     *                    include all selected units even if they are zero.
     * @return formatted time.
     */
    public String formatMultiple(final Time time, final int flags, final boolean onlyNonZero) {

        // centuries
        final var exactCenturies = TimeConverter.convert(time.getValue().doubleValue(), time.getUnit(),
                TimeUnit.CENTURY);
        final var centuriesSplit = splitUnit(exactCenturies, flags, FORMAT_CENTURIES,
                FORMAT_YEARS | FORMAT_MONTHS | FORMAT_WEEKS | FORMAT_DAYS | FORMAT_HOURS | FORMAT_MINUTES
                        | FORMAT_SECONDS | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var centuries = centuriesSplit[VALUE_INDEX];

        // years
        final var exactYears = TimeConverter.convert(centuriesSplit[DIFF_INDEX], TimeUnit.CENTURY, TimeUnit.YEAR);
        final var yearsSplit = splitUnit(exactYears, flags, FORMAT_YEARS,
                FORMAT_MONTHS | FORMAT_WEEKS | FORMAT_DAYS | FORMAT_HOURS | FORMAT_MINUTES | FORMAT_SECONDS
                        | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var years = yearsSplit[VALUE_INDEX];

        // months
        final var exactMonths = TimeConverter.convert(yearsSplit[DIFF_INDEX], TimeUnit.YEAR, TimeUnit.MONTH);
        final var monthsSplit = splitUnit(exactMonths, flags, FORMAT_MONTHS,
                FORMAT_WEEKS | FORMAT_DAYS | FORMAT_HOURS | FORMAT_MINUTES | FORMAT_SECONDS
                        | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var months = monthsSplit[VALUE_INDEX];

        // weeks
        final var exactWeeks = TimeConverter.convert(monthsSplit[DIFF_INDEX], TimeUnit.MONTH, TimeUnit.WEEK);
        final var weeksSplit = splitUnit(exactWeeks, flags, FORMAT_WEEKS,
                FORMAT_DAYS | FORMAT_HOURS | FORMAT_MINUTES | FORMAT_SECONDS | FORMAT_MILLISECONDS
                        | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var weeks = weeksSplit[VALUE_INDEX];

        // days
        final var exactDays = TimeConverter.convert(weeksSplit[DIFF_INDEX], TimeUnit.WEEK, TimeUnit.DAY);
        final var daysSplit = splitUnit(exactDays, flags, FORMAT_DAYS,
                FORMAT_HOURS | FORMAT_MINUTES | FORMAT_SECONDS | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS
                        | FORMAT_NANOSECONDS);
        final var days = daysSplit[VALUE_INDEX];

        // hours
        final var exactHours = TimeConverter.convert(daysSplit[DIFF_INDEX], TimeUnit.DAY, TimeUnit.HOUR);
        final var hoursSplit = splitUnit(exactHours, flags, FORMAT_HOURS,
                FORMAT_MINUTES | FORMAT_SECONDS | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var hours = hoursSplit[VALUE_INDEX];

        // minutes
        final var exactMinutes = TimeConverter.convert(hoursSplit[DIFF_INDEX], TimeUnit.HOUR, TimeUnit.MINUTE);
        final var minutesSplit = splitUnit(exactMinutes, flags, FORMAT_MINUTES,
                FORMAT_SECONDS | FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var minutes = minutesSplit[VALUE_INDEX];

        // seconds
        final var exactSeconds = TimeConverter.convert(minutesSplit[DIFF_INDEX], TimeUnit.MINUTE, TimeUnit.SECOND);
        final var secondsSplit = splitUnit(exactSeconds, flags, FORMAT_SECONDS,
                FORMAT_MILLISECONDS | FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var seconds = secondsSplit[VALUE_INDEX];

        // milliseconds
        final var exactMilliseconds = TimeConverter.convert(secondsSplit[DIFF_INDEX], TimeUnit.SECOND,
                TimeUnit.MILLISECOND);
        final var millisecondsSplit = splitUnit(exactMilliseconds, flags, FORMAT_MILLISECONDS,
                FORMAT_MICROSECONDS | FORMAT_NANOSECONDS);
        final var milliseconds = millisecondsSplit[VALUE_INDEX];

        // microseconds
        final var exactMicroseconds = TimeConverter.convert(millisecondsSplit[DIFF_INDEX], TimeUnit.MILLISECOND,
                TimeUnit.MICROSECOND);
        final var microsecondsSplit = splitUnit(exactMicroseconds, flags, FORMAT_MICROSECONDS, FORMAT_NANOSECONDS);
        final var microseconds = microsecondsSplit[VALUE_INDEX];

        // nanoseconds
        var nanoseconds = 0.0;
        if ((flags & FORMAT_NANOSECONDS) != 0) {
            nanoseconds = TimeConverter.convert(microsecondsSplit[DIFF_INDEX], TimeUnit.MICROSECOND,
                    TimeUnit.NANOSECOND);
        }

        // format result
        final var builder = new StringBuilder();
        if (((flags & FORMAT_CENTURIES) != 0) && (!onlyNonZero || centuries != 0.0)) {
            builder.append(format(centuries, TimeUnit.CENTURY));
        }
        if (((flags & FORMAT_YEARS) != 0) && (!onlyNonZero || years != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(years, TimeUnit.YEAR));
        }
        if (((flags & FORMAT_MONTHS) != 0) && (!onlyNonZero || months != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(months, TimeUnit.MONTH));
        }
        if (((flags & FORMAT_WEEKS) != 0) && (!onlyNonZero || weeks != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(weeks, TimeUnit.WEEK));
        }
        if (((flags & FORMAT_DAYS) != 0) && (!onlyNonZero || days != 0.0)) {
            appendSpaceIfNeeded(builder).append(
                    format(days, TimeUnit.DAY));
        }
        if (((flags & FORMAT_HOURS) != 0) && (!onlyNonZero || hours != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(hours, TimeUnit.HOUR));
        }
        if (((flags & FORMAT_MINUTES) != 0) && (!onlyNonZero || minutes != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(minutes, TimeUnit.MINUTE));
        }
        if (((flags & FORMAT_SECONDS) != 0) && (!onlyNonZero || seconds != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(seconds, TimeUnit.SECOND));
        }
        if (((flags & FORMAT_MILLISECONDS) != 0) && (!onlyNonZero || milliseconds != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(milliseconds, TimeUnit.MILLISECOND));
        }
        if (((flags & FORMAT_MICROSECONDS) != 0) && (!onlyNonZero || microseconds != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(microseconds, TimeUnit.MICROSECOND));
        }
        if (((flags & FORMAT_NANOSECONDS) != 0) && (!onlyNonZero || nanoseconds != 0.0)) {
            appendSpaceIfNeeded(builder).append(format(nanoseconds, TimeUnit.NANOSECOND));
        }
        return builder.toString();
    }

    /**
     * Splits an exact amount of a given unit into the part to be shown using that unit and the remainder
     * to be expressed using smaller units.
     *
     * @param exact        exact amount expressed in the unit being processed.
     * @param flags        flags indicating units to include.
     * @param unitFlag     flag of the unit being processed.
     * @param smallerFlags flags of all units smaller than the unit being processed.
     * @return array containing the amount to be shown using this unit (at {@link #VALUE_INDEX}) and the
     * remainder to be expressed using smaller units (at {@link #DIFF_INDEX}).
     */
    private static double[] splitUnit(
            final double exact, final int flags, final int unitFlag, final int smallerFlags) {
        var value = 0.0;
        final double diff;
        if ((flags & unitFlag) != 0) {
            if ((flags & smallerFlags) != 0) {
                // there are smaller units
                value = Math.floor(exact);
            } else {
                value = exact;
            }
            diff = exact - value;
        } else {
            diff = exact;
        }
        return new double[]{value, diff};
    }

    /**
     * Parses a string containing multiple units and returns the summation of all
     * found values as a single Time instance.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return summation of all time values that have been parsed.
     * @throws ParseException       if parsing fails.
     * @throws UnknownUnitException if format is not recognized.
     */
    public Time parseMultiple(final CharSequence source) throws ParseException, UnknownUnitException {
        final var wrapped = SPACE + source + SPACE;
        Time result = null;

        final var firstCentury = parse1stCentury(wrapped);
        final var secondCentury = parse2ndCentury(wrapped);
        final var thirdCentury = parse3rdCentury(wrapped);
        final var century = parseCentury(wrapped);
        final var year = parseYear(wrapped);
        final var month = parseMonth(wrapped);
        final var week = parseWeek(wrapped);
        final var day = parseDay(wrapped);
        final var hour = parseHour(wrapped);
        final var minute = parseMinute(wrapped);
        final var second = parseSecond(wrapped);
        final var millisecond = parseMillisecond(wrapped);
        final var microsecond = parseMicrosecond(wrapped);
        final var nanosecond = parseNanosecond(wrapped);

        // century
        if (firstCentury != null) {
            result = firstCentury;
        }
        if (secondCentury != null) {
            result = secondCentury;
        }
        if (thirdCentury != null) {
            result = thirdCentury;
        }
        if (century != null) {
            result = century;
        }

        // year
        if (year != null) {
            if (result == null) {
                result = year;
            } else {
                result.add(year);
            }
        }

        // month
        if (month != null) {
            if (result == null) {
                result = month;
            } else {
                result.add(month);
            }
        }

        // week
        if (week != null) {
            if (result == null) {
                result = week;
            } else {
                result.add(week);
            }
        }

        // day
        if (day != null) {
            if (result == null) {
                result = day;
            } else {
                result.add(day);
            }
        }

        // hour
        if (hour != null) {
            if (result == null) {
                result = hour;
            } else {
                result.add(hour);
            }
        }

        // minute
        if (minute != null) {
            if (result == null) {
                result = minute;
            } else {
                result.add(minute);
            }
        }

        // second
        if (second != null) {
            if (result == null) {
                result = second;
            } else {
                result.add(second);
            }
        }

        // millisecond
        if (millisecond != null) {
            if (result == null) {
                result = millisecond;
            } else {
                result.add(millisecond);
            }
        }

        // microsecond
        if (microsecond != null) {
            if (result == null) {
                result = microsecond;
            } else {
                result.add(microsecond);
            }
        }

        // nanosecond
        if (nanosecond != null) {
            if (result == null) {
                result = nanosecond;
            } else {
                result.add(nanosecond);
            }
        }

        if (result == null) {
            // no valid unit was found
            throw new UnknownUnitException();
        }

        return result;
    }

    /**
     * Parses string as 1st century format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in century units.
     * @throws ParseException if parsing fails.
     */
    private Time parse1stCentury(final CharSequence source) throws ParseException {
        if (firstCenturyPattern == null) {
            firstCenturyPattern = Pattern.compile(FIRST_CENTURY_PATTERN);
        }

        final var matcher = firstCenturyPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.CENTURY);
    }

    /**
     * Parses string as 2nd century format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in century units.
     * @throws ParseException if parsing fails.
     */
    private Time parse2ndCentury(final CharSequence source) throws ParseException {
        if (secondCenturyPattern == null) {
            secondCenturyPattern = Pattern.compile(SECOND_CENTURY_PATTERN);
        }

        final var matcher = secondCenturyPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.CENTURY);
    }

    /**
     * Parses string as 3rd century format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in century units.
     * @throws ParseException if parsing fails.
     */
    private Time parse3rdCentury(final CharSequence source) throws ParseException {
        if (thirdCenturyPattern == null) {
            thirdCenturyPattern = Pattern.compile(THIRD_CENTURY_PATTERN);
        }

        final var matcher = thirdCenturyPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.CENTURY);
    }

    /**
     * Parses string as n-th century format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in century units.
     * @throws ParseException if parsing fails.
     */
    private Time parseCentury(final CharSequence source) throws ParseException {
        if (centuryPattern == null) {
            centuryPattern = Pattern.compile(CENTURY_PATTERN);
        }

        final var matcher = centuryPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.CENTURY);
    }

    /**
     * Parses string as year format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in year units.
     * @throws ParseException if parsing fails.
     */
    private Time parseYear(final CharSequence source) throws ParseException {
        if (yearPattern == null) {
            yearPattern = Pattern.compile(YEAR_PATTERN);
        }

        final var matcher = yearPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.YEAR);
    }

    /**
     * Parses string as month format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in month units.
     * @throws ParseException if parsing fails.
     */
    private Time parseMonth(final CharSequence source) throws ParseException {
        if (monthPattern == null) {
            monthPattern = Pattern.compile(MONTH_PATTERN);
        }

        final var matcher = monthPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.MONTH);
    }

    /**
     * Parses string as week format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in week units.
     * @throws ParseException if parsing fails.
     */
    private Time parseWeek(final CharSequence source) throws ParseException {
        if (weekPattern == null) {
            weekPattern = Pattern.compile(WEEK_PATTERN);
        }

        final var matcher = weekPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.WEEK);
    }

    /**
     * Parses string as day format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in day units.
     * @throws ParseException if parsing fails.
     */
    private Time parseDay(final CharSequence source) throws ParseException {
        if (dayPattern == null) {
            dayPattern = Pattern.compile(DAY_PATTERN);
        }

        final var matcher = dayPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.DAY);
    }

    /**
     * Parses string as hour format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in hour units.
     * @throws ParseException if parsing fails.
     */
    private Time parseHour(final CharSequence source) throws ParseException {
        if (hourPattern == null) {
            hourPattern = Pattern.compile(HOUR_PATTERN);
        }

        final var matcher = hourPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.HOUR);
    }

    /**
     * Parses string as minute format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in minute units.
     * @throws ParseException if parsing fails.
     */
    private Time parseMinute(final CharSequence source) throws ParseException {
        if (minutePattern == null) {
            minutePattern = Pattern.compile(MINUTE_PATTERN);
        }

        final var matcher = minutePattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.MINUTE);
    }

    /**
     * Parses string as second format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in second units.
     * @throws ParseException if parsing fails.
     */
    private Time parseSecond(final CharSequence source) throws ParseException {
        if (secondPattern == null) {
            secondPattern = Pattern.compile(SECOND_PATTERN);
        }

        final var matcher = secondPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.SECOND);
    }

    /**
     * Parses string as millisecond format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in millisecond units.
     * @throws ParseException if parsing fails.
     */
    private Time parseMillisecond(final CharSequence source) throws ParseException {
        if (millisecondPattern == null) {
            millisecondPattern = Pattern.compile(MILLISECOND_PATTERN);
        }

        final var matcher = millisecondPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.MILLISECOND);
    }

    /**
     * Parses string as microsecond format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in microsecond units.
     * @throws ParseException if parsing fails.
     */
    private Time parseMicrosecond(final CharSequence source) throws ParseException {
        if (microsecondPattern == null) {
            microsecondPattern = Pattern.compile(MICROSECOND_PATTERN);
        }

        final var matcher = microsecondPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.MICROSECOND);
    }

    /**
     * Parses string as nanosecond format.
     * This method does not allow decimal values with fractions or thousand
     * separators.
     *
     * @param source string to be parsed.
     * @return parsed time in nanosecond units.
     * @throws ParseException if parsing fails.
     */
    private Time parseNanosecond(final CharSequence source) throws ParseException {
        if (nanosecondPattern == null) {
            nanosecondPattern = Pattern.compile(NANOSECOND_PATTERN);
        }

        final var matcher = nanosecondPattern.matcher(source);
        if (!matcher.matches()) {
            return null;
        }

        return new Time(getNumberFormat().parse(matcher.group(VALUE_GROUP)), TimeUnit.NANOSECOND);
    }

    /**
     * Appends a space if builder is not empty.
     *
     * @param builder builder to add space to.
     * @return same instance provided as parameter.
     */
    private StringBuilder appendSpaceIfNeeded(final StringBuilder builder) {
        if (!builder.isEmpty()) {
            builder.append(SPACE);
        }
        return builder;
    }
}
