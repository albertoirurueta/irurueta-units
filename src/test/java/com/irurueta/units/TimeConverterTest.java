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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class TimeConverterTest {

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
    void testSecondNanosecond() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_NANOSECOND, TimeConverter.secondToNanosecond(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_NANOSECOND, TimeConverter.nanosecondToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondMicrosecond() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_MICROSECOND, TimeConverter.secondToMicrosecond(inputValue),
                ERROR);
        assertEquals(inputValue * SECONDS_PER_MICROSECOND, TimeConverter.microsecondToSecond(inputValue),
                ERROR);
    }

    @Test
    void testSecondMillisecond() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_MILLISECOND, TimeConverter.secondToMillisecond(inputValue),
                ERROR);
        assertEquals(inputValue * SECONDS_PER_MILLISECOND, TimeConverter.millisecondToSecond(inputValue),
                ERROR);
    }

    @Test
    void testSecondMinute() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_MINUTE, TimeConverter.secondToMinute(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_MINUTE, TimeConverter.minuteToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondHour() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_HOUR, TimeConverter.secondToHour(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_HOUR, TimeConverter.hourToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondDay() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_DAY, TimeConverter.secondToDay(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_DAY, TimeConverter.dayToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondWeek() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_WEEK, TimeConverter.secondToWeek(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_WEEK, TimeConverter.weekToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondMonth() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_MONTH, TimeConverter.secondToMonth(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_MONTH, TimeConverter.monthToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondYear() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_YEAR, TimeConverter.secondToYear(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_YEAR, TimeConverter.yearToSecond(inputValue), ERROR);
    }

    @Test
    void testSecondCentury() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / SECONDS_PER_CENTURY, TimeConverter.secondToCentury(inputValue), ERROR);
        assertEquals(inputValue * SECONDS_PER_CENTURY, TimeConverter.centuryToSecond(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.nanosecondToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.nanosecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.NANOSECOND, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.NANOSECOND), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.microsecondToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.microsecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MICROSECOND, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.MICROSECOND), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.millisecondToSecond(inputValue), TimeConverter.convert(inputValue,
                        TimeUnit.MILLISECOND, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.millisecondToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MILLISECOND, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.MILLISECOND), ERROR);
        assertEquals(inputValue,
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.minuteToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.SECOND), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.minuteToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MINUTE, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.hourToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.MINUTE), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.hourToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.HOUR, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.dayToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.HOUR), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.dayToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.DAY, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.weekToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.DAY), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.weekToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.WEEK, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.monthToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.WEEK), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.monthToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.MONTH, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.yearToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.MONTH), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.YEAR), ERROR);
        assertEquals(TimeConverter.secondToCentury(TimeConverter.yearToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.YEAR, TimeUnit.CENTURY), ERROR);

        assertEquals(TimeConverter.secondToNanosecond(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.NANOSECOND), ERROR);
        assertEquals(TimeConverter.secondToMicrosecond(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.MICROSECOND), ERROR);
        assertEquals(TimeConverter.secondToMillisecond(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.MILLISECOND), ERROR);
        assertEquals(TimeConverter.centuryToSecond(inputValue),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.SECOND), ERROR);
        assertEquals(TimeConverter.secondToMinute(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.MINUTE), ERROR);
        assertEquals(TimeConverter.secondToHour(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.HOUR), ERROR);
        assertEquals(TimeConverter.secondToDay(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.DAY), ERROR);
        assertEquals(TimeConverter.secondToWeek(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.WEEK), ERROR);
        assertEquals(TimeConverter.secondToMonth(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.MONTH), ERROR);
        assertEquals(TimeConverter.secondToYear(TimeConverter.centuryToSecond(inputValue)),
                TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.YEAR), ERROR);
        assertEquals(inputValue, TimeConverter.convert(inputValue, TimeUnit.CENTURY, TimeUnit.CENTURY), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                TimeConverter.convert(inputValue, TimeUnit.SECOND, TimeUnit.SECOND).doubleValue(), ERROR);
    }

    @Test
    void testConvertTime() {
        final var value = new Random().nextDouble();
        final var inputTime = new Time(value, TimeUnit.SECOND);

        final var outputTime = new Time();
        TimeConverter.convert(inputTime, TimeUnit.HOUR, outputTime);

        // check
        assertEquals(value, inputTime.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.SECOND, inputTime.getUnit());

        assertEquals(TimeUnit.HOUR, outputTime.getUnit());
        assertEquals(TimeConverter.convert(value, inputTime.getUnit(), outputTime.getUnit()),
                outputTime.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateTime() {
        final var value = new Random().nextDouble();
        final var time = new Time(value, TimeUnit.SECOND);

        TimeConverter.convert(time, TimeUnit.HOUR);

        // check
        assertEquals(TimeUnit.HOUR, time.getUnit());
        assertEquals(TimeConverter.convert(value, TimeUnit.SECOND, TimeUnit.HOUR), time.getValue().doubleValue(),
                0.0);
    }

    @Test
    void testConvertAndReturnNewTime() {
        final var value = new Random().nextDouble();
        final var inputTime = new Time(value, TimeUnit.SECOND);

        final var outputTime = TimeConverter.convertAndReturnNew(inputTime, TimeUnit.HOUR);

        // check
        assertEquals(value, inputTime.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.SECOND, inputTime.getUnit());

        assertEquals(TimeUnit.HOUR, outputTime.getUnit());
        assertEquals(TimeConverter.convert(value, inputTime.getUnit(),
                outputTime.getUnit()), outputTime.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputTimeUnit() {
        final var value = new Random().nextDouble();
        final var inputTime = new Time(value, TimeUnit.SECOND);

        final var outputTime = new Time();
        outputTime.setUnit(TimeUnit.HOUR);
        TimeConverter.convert(inputTime, outputTime);

        // check
        assertEquals(value, inputTime.getValue().doubleValue(), 0.0);
        assertEquals(TimeUnit.SECOND, inputTime.getUnit());

        assertEquals(TimeUnit.HOUR, outputTime.getUnit());
        assertEquals(TimeConverter.convert(value, inputTime.getUnit(), outputTime.getUnit()),
                outputTime.getValue().doubleValue(), 0.0);
    }
}
