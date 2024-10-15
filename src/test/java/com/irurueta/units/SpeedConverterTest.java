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

class SpeedConverterTest {

    private static final double METERS_PER_KILOMETER = 1000.0;
    private static final double METERS_PER_MILE = 1609.344;
    private static final double METERS_PER_FOOT = 0.3048;
    private static final double SECONDS_PER_HOUR = 3600.0;

    private static final double ERROR = 1e-6;

    @Test
    void testMetersPerSecondFeetPerSecond() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_FOOT, SpeedConverter.metersPerSecondToFeetPerSecond(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_FOOT, SpeedConverter.feetPerSecondToMetersPerSecond(inputValue),
                ERROR);
    }

    @Test
    void testMetersPerSecondMilesPerHour() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_MILE * SECONDS_PER_HOUR,
                SpeedConverter.metersPerSecondToMilesPerHour(inputValue), ERROR);
        assertEquals(inputValue * METERS_PER_MILE / SECONDS_PER_HOUR,
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue), ERROR);
    }

    @Test
    void testMetersPerSecondKilometersPerHour() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_KILOMETER * SECONDS_PER_HOUR,
                SpeedConverter.metersPerSecondToKilometersPerHour(inputValue), ERROR);
        assertEquals(inputValue * METERS_PER_KILOMETER / SECONDS_PER_HOUR,
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue), ERROR);
    }

    @Test
    void testMetersPerSecondKilometersPerSecond() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_KILOMETER,
                SpeedConverter.metersPerSecondToKilometersPerSecond(inputValue), ERROR);
        assertEquals(inputValue * METERS_PER_KILOMETER,
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerHour(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToFeetPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.FEET_PER_SECOND), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToMilesPerHour(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.MILES_PER_HOUR), ERROR);

        assertEquals(SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.METERS_PER_SECOND), ERROR);
        assertEquals(inputValue,
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.KILOMETERS_PER_HOUR),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.KILOMETERS_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToFeetPerSecond(
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.FEET_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.MILES_PER_HOUR),
                ERROR);

        assertEquals(SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerHour(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToFeetPerSecond(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.FEET_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.MILES_PER_HOUR),
                ERROR);

        assertEquals(SpeedConverter.feetPerSecondToMetersPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.FEET_PER_SECOND, SpeedUnit.METERS_PER_SECOND),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerHour(
                SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.FEET_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.FEET_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                ERROR);
        assertEquals(inputValue,
                SpeedConverter.convert(inputValue, SpeedUnit.FEET_PER_SECOND, SpeedUnit.FEET_PER_SECOND), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.FEET_PER_SECOND, SpeedUnit.MILES_PER_HOUR),
                ERROR);

        assertEquals(SpeedConverter.milesPerHourToMetersPerSecond(inputValue),
                SpeedConverter.convert(inputValue, SpeedUnit.MILES_PER_HOUR, SpeedUnit.METERS_PER_SECOND), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerHour(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.MILES_PER_HOUR, SpeedUnit.KILOMETERS_PER_HOUR), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.MILES_PER_HOUR, SpeedUnit.KILOMETERS_PER_SECOND), ERROR);
        assertEquals(SpeedConverter.metersPerSecondToFeetPerSecond(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)),
                SpeedConverter.convert(inputValue, SpeedUnit.MILES_PER_HOUR, SpeedUnit.FEET_PER_SECOND), ERROR);
        assertEquals(inputValue, SpeedConverter.convert(inputValue, SpeedUnit.MILES_PER_HOUR, SpeedUnit.MILES_PER_HOUR),
                ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                SpeedConverter.convert(inputValue, SpeedUnit.METERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND)
                        .doubleValue(), ERROR);
    }

    @Test
    void testConvertSpeed() {
        final var value = new Random().nextDouble();
        final var inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        final var outputSpeed = new Speed();
        SpeedConverter.convert(inputSpeed, SpeedUnit.KILOMETERS_PER_HOUR, outputSpeed);

        // check
        assertEquals(value, inputSpeed.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.METERS_PER_SECOND, inputSpeed.getUnit());

        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, outputSpeed.getUnit());
        assertEquals(SpeedConverter.convert(value, inputSpeed.getUnit(), outputSpeed.getUnit()),
                outputSpeed.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateSpeed() {
        final var value = new Random().nextDouble();
        final var speed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        SpeedConverter.convert(speed, SpeedUnit.KILOMETERS_PER_HOUR);

        // check
        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, speed.getUnit());
        assertEquals(SpeedConverter.convert(value, SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                speed.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewSpeed() {
        final var value = new Random().nextDouble();
        final var inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        final var outputSpeed = SpeedConverter.convertAndReturnNew(inputSpeed, SpeedUnit.KILOMETERS_PER_HOUR);

        // check
        assertEquals(value, inputSpeed.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.METERS_PER_SECOND, inputSpeed.getUnit());

        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, outputSpeed.getUnit());
        assertEquals(SpeedConverter.convert(value, inputSpeed.getUnit(), outputSpeed.getUnit()),
                outputSpeed.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputSpeedUnit() {
        final var value = new Random().nextDouble();
        final var inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        final var outputSpeed = new Speed();
        outputSpeed.setUnit(SpeedUnit.KILOMETERS_PER_HOUR);
        SpeedConverter.convert(inputSpeed, outputSpeed);

        // check
        assertEquals(value, inputSpeed.getValue().doubleValue(), 0.0);
        assertEquals(SpeedUnit.METERS_PER_SECOND, inputSpeed.getUnit());

        assertEquals(SpeedUnit.KILOMETERS_PER_HOUR, outputSpeed.getUnit());
        assertEquals(SpeedConverter.convert(value, inputSpeed.getUnit(),
                outputSpeed.getUnit()), outputSpeed.getValue().doubleValue(), 0.0);
    }
}
