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

import org.junit.*;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class TimeConverterTest {

    private static final double SECONDS_PER_NANOSECOND = 1e-9;
    private static final double SECONDS_PER_MICROSECOND = 1e-6;
    private static final double SECONDS_PER_MILLISECOND = 1e-3;
    private static final double SECONDS_PER_MINUTE = 60.0;
    private static final double SECONDS_PER_HOUR = 3600.0;
    private static final double SECONDS_PER_DAY = 24 * SECONDS_PER_HOUR;
    private static final double SECONDS_PER_WEEK = 7 * SECONDS_PER_DAY;
    private static final double SECONDS_PER_MONTH = 30 * SECONDS_PER_DAY;
    private static final double SECONDS_PER_YEAR = 365 * SECONDS_PER_DAY;
    private static final double SECONDS_PER_CENTURY = 100 * SECONDS_PER_YEAR;

    private static final double ERROR = 1e-6;

    @Test
    public void testSecondNanosecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToNanosecond(inputValue),
                inputValue / SECONDS_PER_NANOSECOND, ERROR);
        assertEquals(TimeConverter.nanosecondToSecond(inputValue),
                inputValue * SECONDS_PER_NANOSECOND, ERROR);
    }

    @Test
    public void testSecondMicrosecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToMicrosecond(inputValue),
                inputValue / SECONDS_PER_MICROSECOND, ERROR);
        assertEquals(TimeConverter.microsecondToSecond(inputValue),
                inputValue * SECONDS_PER_MICROSECOND, ERROR);
    }

    @Test
    public void testSecondMillisecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToMillisecond(inputValue),
                inputValue / SECONDS_PER_MILLISECOND, ERROR);
        assertEquals(TimeConverter.millisecondToSecond(inputValue),
                inputValue * SECONDS_PER_MILLISECOND, ERROR);
    }

    @Test
    public void testSecondMinute() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToMinute(inputValue),
                inputValue / SECONDS_PER_MINUTE, ERROR);
        assertEquals(TimeConverter.minuteToSecond(inputValue),
                inputValue * SECONDS_PER_MINUTE, ERROR);
    }

    @Test
    public void testSecondHour() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToHour(inputValue),
                inputValue / SECONDS_PER_HOUR, ERROR);
        assertEquals(TimeConverter.hourToSecond(inputValue),
                inputValue * SECONDS_PER_HOUR, ERROR);
    }

    @Test
    public void testSecondDay() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToDay(inputValue),
                inputValue / SECONDS_PER_DAY, ERROR);
        assertEquals(TimeConverter.dayToSecond(inputValue),
                inputValue * SECONDS_PER_DAY, ERROR);
    }

    @Test
    public void testSecondWeek() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToWeek(inputValue),
                inputValue / SECONDS_PER_WEEK, ERROR);
        assertEquals(TimeConverter.weekToSecond(inputValue),
                inputValue * SECONDS_PER_WEEK, ERROR);
    }

    @Test
    public void testSecondMonth() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToMonth(inputValue),
                inputValue / SECONDS_PER_MONTH, ERROR);
        assertEquals(TimeConverter.monthToSecond(inputValue),
                inputValue * SECONDS_PER_MONTH, ERROR);
    }

    @Test
    public void testSecondYear() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToYear(inputValue),
                inputValue / SECONDS_PER_YEAR, ERROR);
        assertEquals(TimeConverter.yearToSecond(inputValue),
                inputValue * SECONDS_PER_YEAR, ERROR);
    }

    @Test
    public void testSecondCentury() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.secondToCentury(inputValue),
                inputValue / SECONDS_PER_CENTURY, ERROR);
        assertEquals(TimeConverter.centuryToSecond(inputValue),
                inputValue * SECONDS_PER_CENTURY, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.NANOSECOND),
                inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.SECOND),
                TimeConverter.nanosecondToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.NANOSECOND, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.nanosecondToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.MICROSECOND),
                inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.SECOND),
                TimeConverter.microsecondToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MICROSECOND, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.microsecondToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.MILLISECOND),
                inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.SECOND),
                TimeConverter.millisecondToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MILLISECOND, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.millisecondToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.SECOND), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.HOUR),
                TimeConverter.secondToHour(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.DAY),
                TimeConverter.secondToDay(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.WEEK),
                TimeConverter.secondToWeek(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.MONTH),
                TimeConverter.secondToMonth(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.YEAR),
                TimeConverter.secondToYear(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(inputValue), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.SECOND),
                TimeConverter.minuteToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.MINUTE), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MINUTE, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.minuteToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.SECOND),
                TimeConverter.hourToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.HOUR), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.hourToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.HOUR, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.hourToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.SECOND),
                TimeConverter.dayToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.DAY), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.dayToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.DAY, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.dayToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.SECOND),
                TimeConverter.weekToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.WEEK), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.weekToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.WEEK, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.weekToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.SECOND),
                TimeConverter.monthToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.MONTH), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.monthToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.MONTH, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.monthToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.SECOND),
                TimeConverter.yearToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.yearToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.YEAR), inputValue, ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.YEAR, TimeUnit.CENTURY),
                TimeConverter.secondToCentury(
                        TimeConverter.yearToSecond(inputValue)), ERROR);

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.NANOSECOND),
                TimeConverter.secondToNanosecond(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.MICROSECOND),
                TimeConverter.secondToMicrosecond(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.MILLISECOND),
                TimeConverter.secondToMillisecond(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.SECOND),
                TimeConverter.centuryToSecond(inputValue), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.MINUTE),
                TimeConverter.secondToMinute(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.HOUR),
                TimeConverter.secondToHour(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.DAY),
                TimeConverter.secondToDay(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.WEEK),
                TimeConverter.secondToWeek(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.MONTH),
                TimeConverter.secondToMonth(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.YEAR),
                TimeConverter.secondToYear(
                        TimeConverter.centuryToSecond(inputValue)), ERROR);
        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.CENTURY, TimeUnit.CENTURY), inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(TimeConverter.convert(inputValue,
                TimeUnit.SECOND, TimeUnit.SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertTime() {
        final double value = new Random().nextDouble();
        final Time inputTime = new Time(value, TimeUnit.SECOND);

        final Time outputTime = new Time();
        TimeConverter.convert(inputTime, TimeUnit.HOUR, outputTime);

        // check
        assertEquals(inputTime.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTime.getUnit(), TimeUnit.SECOND);

        assertEquals(outputTime.getUnit(), TimeUnit.HOUR);
        assertEquals(outputTime.getValue().doubleValue(),
                TimeConverter.convert(value, inputTime.getUnit(),
                        outputTime.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateTime() {
        final double value = new Random().nextDouble();
        final Time time = new Time(value, TimeUnit.SECOND);

        TimeConverter.convert(time, TimeUnit.HOUR);

        // check
        assertEquals(time.getUnit(), TimeUnit.HOUR);
        assertEquals(time.getValue().doubleValue(),
                TimeConverter.convert(value,
                        TimeUnit.SECOND, TimeUnit.HOUR), 0.0);
    }

    @Test
    public void testConvertAndReturnNewTime() {
        final double value = new Random().nextDouble();
        final Time inputTime = new Time(value, TimeUnit.SECOND);

        final Time outputTime = TimeConverter.convertAndReturnNew(
                inputTime, TimeUnit.HOUR);

        // check
        assertEquals(inputTime.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTime.getUnit(), TimeUnit.SECOND);

        assertEquals(outputTime.getUnit(), TimeUnit.HOUR);
        assertEquals(outputTime.getValue().doubleValue(),
                TimeConverter.convert(value, inputTime.getUnit(),
                        outputTime.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputTimeUnit() {
        final double value = new Random().nextDouble();
        final Time inputTime = new Time(value, TimeUnit.SECOND);

        final Time outputTime = new Time();
        outputTime.setUnit(TimeUnit.HOUR);
        TimeConverter.convert(inputTime, outputTime);

        // check
        assertEquals(inputTime.getValue().doubleValue(), value, 0.0);
        assertEquals(inputTime.getUnit(), TimeUnit.SECOND);

        assertEquals(outputTime.getUnit(), TimeUnit.HOUR);
        assertEquals(outputTime.getValue().doubleValue(),
                TimeConverter.convert(value, inputTime.getUnit(),
                        outputTime.getUnit()), 0.0);
    }
}
