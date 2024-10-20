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

/**
 * Does time conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying time measurements.
 */
@SuppressWarnings("WeakerAccess")
public class TimeConverter {

    /**
     * Number of seconds in 1 nanosecond.
     */
    static final double SECONDS_PER_NANOSECOND = 1e-9;

    /**
     * Number of seconds in 1 microsecond.
     */
    static final double SECONDS_PER_MICROSECOND = 1e-6;

    /**
     * Number of seconds in 1 milliseconds.
     */
    static final double SECONDS_PER_MILLISECOND = 1e-3;

    /**
     * Number of seconds in 1 minute.
     */
    static final double SECONDS_PER_MINUTE = 60.0;

    /**
     * Number of seconds in 1 hour.
     */
    static final double SECONDS_PER_HOUR = 3600.0;

    /**
     * Number of seconds in 1 day.
     */
    static final double SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;

    /**
     * Number of seconds in 1 week.
     */
    static final double SECONDS_PER_WEEK = 7 * SECONDS_PER_DAY;

    /**
     * Number of seconds in 1 month.
     */
    static final double SECONDS_PER_MONTH = 30 * SECONDS_PER_DAY;

    /**
     * Number of seconds in 1 year.
     */
    static final double SECONDS_PER_YEAR = 365 * SECONDS_PER_DAY;

    /**
     * Number of seconds in 1 century.
     */
    static final double SECONDS_PER_CENTURY = 100 * SECONDS_PER_YEAR;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private TimeConverter() {
    }

    /**
     * Converts a time to provided output time unit.
     *
     * @param input  input time to be converted.
     * @param output output time where result will be stored and containing
     *               output unit.
     */
    public static void convert(final Time input, final Time output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a time to requested output unit.
     *
     * @param input      input time to be converted.
     * @param outputUnit requested output unit.
     * @return converted time.
     */
    public static Time convertAndReturnNew(final Time input, final TimeUnit outputUnit) {
        final var result = new Time();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a time to requested output unit.
     *
     * @param time       input time to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Time time, final TimeUnit outputUnit) {
        convert(time, outputUnit, time);
    }

    /**
     * Converts a time to requested output unit.
     *
     * @param input      input time to be converted.
     * @param outputUnit requested output unit.
     * @param result     time unit where result will be stored.
     */
    public static void convert(final Time input, final TimeUnit outputUnit, final Time result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a time value from input unit to provided output unit.
     *
     * @param input      time value.
     * @param inputUnit  input time unit.
     * @param outputUnit output time unit.
     * @return converted time value.
     */
    public static Number convert(final Number input, final TimeUnit inputUnit, final TimeUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a time value from input unit to provided output unit.
     *
     * @param input      time value.
     * @param inputUnit  input time unit.
     * @param outputUnit output time unit.
     * @return converted time value.
     */
    public static double convert(
            final double input, final TimeUnit inputUnit, final TimeUnit outputUnit) {
        //convert to seconds
        final var seconds = switch (inputUnit) {
            case NANOSECOND -> nanosecondToSecond(input);
            case MICROSECOND -> microsecondToSecond(input);
            case MILLISECOND -> millisecondToSecond(input);
            case MINUTE -> minuteToSecond(input);
            case HOUR -> hourToSecond(input);
            case DAY -> dayToSecond(input);
            case WEEK -> weekToSecond(input);
            case MONTH -> monthToSecond(input);
            case YEAR -> yearToSecond(input);
            case CENTURY -> centuryToSecond(input);
            default -> input;
        };

        //convert from seconds to required output unit
        return switch (outputUnit) {
            case NANOSECOND -> secondToNanosecond(seconds);
            case MICROSECOND -> secondToMicrosecond(seconds);
            case MILLISECOND -> secondToMillisecond(seconds);
            case MINUTE -> secondToMinute(seconds);
            case HOUR -> secondToHour(seconds);
            case DAY -> secondToDay(seconds);
            case WEEK -> secondToWeek(seconds);
            case MONTH -> secondToMonth(seconds);
            case YEAR -> secondToYear(seconds);
            case CENTURY -> secondToCentury(seconds);
            default -> seconds;
        };
    }

    /**
     * Converts provided second value to nanoseconds.
     *
     * @param second second value.
     * @return same amount of time converted to nanoseconds.
     */
    public static double secondToNanosecond(final double second) {
        return second / SECONDS_PER_NANOSECOND;
    }

    /**
     * Converts provided nanosecond value to seconds.
     *
     * @param nanosecond nanosecond value.
     * @return same amount of time converted to seconds.
     */
    public static double nanosecondToSecond(final double nanosecond) {
        return nanosecond * SECONDS_PER_NANOSECOND;
    }

    /**
     * Converts provided second value to microseconds.
     *
     * @param second second value.
     * @return same amount of time converted to microseconds.
     */
    public static double secondToMicrosecond(final double second) {
        return second / SECONDS_PER_MICROSECOND;
    }

    /**
     * Converts provided microsecond value to seconds.
     *
     * @param microsecond microsecond value.
     * @return same amount of time converted to seconds.
     */
    public static double microsecondToSecond(final double microsecond) {
        return microsecond * SECONDS_PER_MICROSECOND;
    }

    /**
     * Converts provided second value to milliseconds.
     *
     * @param second second value.
     * @return same amount of time converted to milliseconds.
     */
    public static double secondToMillisecond(final double second) {
        return second / SECONDS_PER_MILLISECOND;
    }

    /**
     * Converts provided millisecond value to seconds.
     *
     * @param millisecond millisecond value.
     * @return same amount of time converted to seconds.
     */
    public static double millisecondToSecond(final double millisecond) {
        return millisecond * SECONDS_PER_MILLISECOND;
    }

    /**
     * Converts provided second value to minutes.
     *
     * @param second second value.
     * @return same amount of time converted to minutes.
     */
    public static double secondToMinute(final double second) {
        return second / SECONDS_PER_MINUTE;
    }

    /**
     * Converts provided minute value to seconds.
     *
     * @param minute minute value.
     * @return same amount of time converted to seconds.
     */
    public static double minuteToSecond(final double minute) {
        return minute * SECONDS_PER_MINUTE;
    }

    /**
     * Converts provided second value to hours.
     *
     * @param second second value.
     * @return same amount of time converted to hours.
     */
    public static double secondToHour(final double second) {
        return second / SECONDS_PER_HOUR;
    }

    /**
     * Converts provided hour value to seconds.
     *
     * @param hour hour value.
     * @return same amount of time converted to seconds.
     */
    public static double hourToSecond(final double hour) {
        return hour * SECONDS_PER_HOUR;
    }

    /**
     * Converts provided second value to days.
     *
     * @param second second value.
     * @return same amount of time converted to days.
     */
    public static double secondToDay(final double second) {
        return second / SECONDS_PER_DAY;
    }

    /**
     * Converts provided day value to seconds.
     *
     * @param day day value.
     * @return same amount of time converted to seconds.
     */
    public static double dayToSecond(final double day) {
        return day * SECONDS_PER_DAY;
    }

    /**
     * Converts provided second value to weeks.
     *
     * @param second second value.
     * @return same amount of time converted to weeks.
     */
    public static double secondToWeek(final double second) {
        return second / SECONDS_PER_WEEK;
    }

    /**
     * Converts provided week value to seconds.
     *
     * @param week week value.
     * @return same amount of time converted to seconds.
     */
    public static double weekToSecond(final double week) {
        return week * SECONDS_PER_WEEK;
    }

    /**
     * Converts provided second value to months.
     *
     * @param second second value.
     * @return same amount of time converted to months.
     */
    public static double secondToMonth(final double second) {
        return second / SECONDS_PER_MONTH;
    }

    /**
     * Converts provided month value to seconds.
     *
     * @param month month value.
     * @return same amount of time converted to seconds.
     */
    public static double monthToSecond(final double month) {
        return month * SECONDS_PER_MONTH;
    }

    /**
     * Converts provided second value to years.
     *
     * @param second second value.
     * @return same amount of time converted to years.
     */
    public static double secondToYear(final double second) {
        return second / SECONDS_PER_YEAR;
    }

    /**
     * Converts provided year value to seconds.
     *
     * @param year year value.
     * @return same amount of time converted to seconds.
     */
    public static double yearToSecond(final double year) {
        return year * SECONDS_PER_YEAR;
    }

    /**
     * Converts provided second value to century.
     *
     * @param second second value.
     * @return same amount of time converted to centuries.
     */
    public static double secondToCentury(final double second) {
        return second / SECONDS_PER_CENTURY;
    }

    /**
     * Converts provided century value to seconds.
     *
     * @param century century value.
     * @return same amount of time converted to seconds.
     */
    public static double centuryToSecond(final double century) {
        return century * SECONDS_PER_CENTURY;
    }
}
