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

public class SpeedConverterTest {

    private static final double METERS_PER_KILOMETER = 1000.0;
    private static final double METERS_PER_MILE = 1609.344;
    private static final double METERS_PER_FOOT = 0.3048;
    private static final double SECONDS_PER_HOUR = 3600.0;

    private static final double ERROR = 1e-6;

    public SpeedConverterTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }

    @Before
    public void setUp() { }

    @After
    public void tearDown() { }

    @Test
    public void testConstructor() {
        //noinspection all
        assertNotNull(new SpeedConverter());
    }

    @Test
    public void testMetersPerSecondFeetPerSecond() {
        double inputValue = new Random().nextDouble();

        assertEquals(SpeedConverter.metersPerSecondToFeetPerSecond(inputValue),
                inputValue / METERS_PER_FOOT, ERROR);
        assertEquals(SpeedConverter.feetPerSecondToMetersPerSecond(inputValue),
                inputValue * METERS_PER_FOOT, ERROR);
    }

    @Test
    public void testMetersPerSecondMilesPerHour() {
        double inputValue = new Random().nextDouble();

        assertEquals(SpeedConverter.metersPerSecondToMilesPerHour(inputValue),
                inputValue / METERS_PER_MILE * SECONDS_PER_HOUR, ERROR);
        assertEquals(SpeedConverter.milesPerHourToMetersPerSecond(inputValue),
                inputValue * METERS_PER_MILE / SECONDS_PER_HOUR, ERROR);
    }

    @Test
    public void testMetersPerSecondKilometersPerHour() {
        double inputValue = new Random().nextDouble();

        assertEquals(SpeedConverter.metersPerSecondToKilometersPerHour(inputValue),
                inputValue / METERS_PER_KILOMETER * SECONDS_PER_HOUR, ERROR);
        assertEquals(SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue),
                inputValue * METERS_PER_KILOMETER / SECONDS_PER_HOUR, ERROR);
    }

    @Test
    public void testMetersPerSecondKilometersPerSecond() {
        double inputValue = new Random().nextDouble();

        assertEquals(SpeedConverter.metersPerSecondToKilometersPerSecond(inputValue),
                inputValue / METERS_PER_KILOMETER, ERROR);
        assertEquals(SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue),
                inputValue * METERS_PER_KILOMETER, ERROR);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND),
                inputValue, ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedConverter.metersPerSecondToKilometersPerHour(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedConverter.metersPerSecondToKilometersPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.FEET_PER_SECOND),
                SpeedConverter.metersPerSecondToFeetPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.MILES_PER_HOUR),
                SpeedConverter.metersPerSecondToMilesPerHour(inputValue), ERROR);

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.METERS_PER_SECOND),
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.KILOMETERS_PER_HOUR),
                inputValue, ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.FEET_PER_SECOND),
                SpeedConverter.metersPerSecondToFeetPerSecond(
                        SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_HOUR, SpeedUnit.MILES_PER_HOUR),
                SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.kilometersPerHourToMetersPerSecond(inputValue)), ERROR);

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND),
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedConverter.metersPerSecondToKilometersPerHour(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.FEET_PER_SECOND),
                SpeedConverter.metersPerSecondToFeetPerSecond(
                        SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.KILOMETERS_PER_SECOND, SpeedUnit.MILES_PER_HOUR),
                SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.kilometersPerSecondToMetersPerSecond(inputValue)), ERROR);

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.FEET_PER_SECOND, SpeedUnit.METERS_PER_SECOND),
                SpeedConverter.feetPerSecondToMetersPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.FEET_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedConverter.metersPerSecondToKilometersPerHour(
                        SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.FEET_PER_SECOND, SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedConverter.metersPerSecondToKilometersPerSecond(
                        SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.FEET_PER_SECOND, SpeedUnit.FEET_PER_SECOND), inputValue,
                ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.FEET_PER_SECOND, SpeedUnit.MILES_PER_HOUR),
                SpeedConverter.metersPerSecondToMilesPerHour(
                SpeedConverter.feetPerSecondToMetersPerSecond(inputValue)), ERROR);

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.MILES_PER_HOUR, SpeedUnit.METERS_PER_SECOND),
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.MILES_PER_HOUR, SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedConverter.metersPerSecondToKilometersPerHour(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.MILES_PER_HOUR, SpeedUnit.KILOMETERS_PER_SECOND),
                SpeedConverter.metersPerSecondToKilometersPerSecond(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.MILES_PER_HOUR, SpeedUnit.FEET_PER_SECOND),
                SpeedConverter.metersPerSecondToFeetPerSecond(
                SpeedConverter.milesPerHourToMetersPerSecond(inputValue)), ERROR);
        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.MILES_PER_HOUR, SpeedUnit.MILES_PER_HOUR),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(SpeedConverter.convert(inputValue,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.METERS_PER_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertSpeed() {
        double value = new Random().nextDouble();
        Speed inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        Speed outputSpeed = new Speed();
        SpeedConverter.convert(inputSpeed, SpeedUnit.KILOMETERS_PER_HOUR,
                outputSpeed);

        //check
        assertEquals(inputSpeed.getValue().doubleValue(), value, 0.0);
        assertEquals(inputSpeed.getUnit(), SpeedUnit.METERS_PER_SECOND);

        assertEquals(outputSpeed.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(outputSpeed.getValue().doubleValue(),
                SpeedConverter.convert(value, inputSpeed.getUnit(),
                        outputSpeed.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateSpeed() {
        double value = new Random().nextDouble();
        Speed speed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        SpeedConverter.convert(speed, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(speed.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(speed.getValue().doubleValue(),
                SpeedConverter.convert(value,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR), 0.0);
    }

    @Test
    public void testConvertAndReturnNewSpeed() {
        double value = new Random().nextDouble();
        Speed inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        Speed outputSpeed = SpeedConverter.convertAndReturnNew(
                inputSpeed, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(inputSpeed.getValue().doubleValue(), value, 0.0);
        assertEquals(inputSpeed.getUnit(), SpeedUnit.METERS_PER_SECOND);

        assertEquals(outputSpeed.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(outputSpeed.getValue().doubleValue(),
                SpeedConverter.convert(value, inputSpeed.getUnit(),
                        outputSpeed.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputSpeedUnit() {
        double value = new Random().nextDouble();
        Speed inputSpeed = new Speed(value, SpeedUnit.METERS_PER_SECOND);

        Speed outputSpeed = new Speed();
        outputSpeed.setUnit(SpeedUnit.KILOMETERS_PER_HOUR);
        SpeedConverter.convert(inputSpeed, outputSpeed);

        //check
        assertEquals(inputSpeed.getValue().doubleValue(), value, 0.0);
        assertEquals(inputSpeed.getUnit(), SpeedUnit.METERS_PER_SECOND);

        assertEquals(outputSpeed.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(outputSpeed.getValue().doubleValue(),
                SpeedConverter.convert(value, inputSpeed.getUnit(),
                        outputSpeed.getUnit()), 0.0);
    }
}
