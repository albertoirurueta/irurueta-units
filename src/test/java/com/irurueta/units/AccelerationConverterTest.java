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

public class AccelerationConverterTest {
    private static final double METERS_PER_FOOT = 0.3048;

    private static final double ERROR = 1e-6;

    public AccelerationConverterTest() { }

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
        assertNotNull(new AccelerationConverter());
    }

    @Test
    public void testMeterPerSquaredSecondFeetPerSquaredSecond() {
        double inputValue = new Random().nextDouble();

        assertEquals(AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                inputValue), inputValue / METERS_PER_FOOT, ERROR);
        assertEquals(AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                inputValue), inputValue * METERS_PER_FOOT, ERROR);
    }

    @Test
    public void testMetersPerSquaredSecondG() {
        double inputValue = new Random().nextDouble();

        assertEquals(AccelerationConverter.metersPerSquaredSecondToG(inputValue),
                inputValue / AccelerationConverter.STANDARD_GRAVITY, ERROR);
        assertEquals(AccelerationConverter.GToMetersPerSquaredSecond(inputValue),
                inputValue * AccelerationConverter.STANDARD_GRAVITY, ERROR);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                inputValue, ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                        inputValue), ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.G), AccelerationConverter.metersPerSquaredSecondToG(
                        inputValue), ERROR);

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.FEET_PER_SQUARED_SECOND,
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                        inputValue), ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.FEET_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                inputValue, ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.FEET_PER_SQUARED_SECOND,
                AccelerationUnit.G), AccelerationConverter.metersPerSquaredSecondToG(
                        AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                                inputValue)), ERROR);

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G,
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                AccelerationConverter.GToMetersPerSquaredSecond(inputValue), ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                        AccelerationConverter.GToMetersPerSquaredSecond(inputValue)),
                ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G, AccelerationUnit.G),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.METERS_PER_SQUARED_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertAcceleration() {
        double value = new Random().nextDouble();
        Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        Acceleration outputAcceleration = new Acceleration();
        AccelerationConverter.convert(inputAcceleration,
                AccelerationUnit.FEET_PER_SQUARED_SECOND, outputAcceleration);

        //check
        assertEquals(inputAcceleration.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAcceleration.getUnit(),
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        assertEquals(outputAcceleration.getUnit(),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        assertEquals(outputAcceleration.getValue().doubleValue(),
                AccelerationConverter.convert(value, inputAcceleration.getUnit(),
                        outputAcceleration.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateAcceleration() {
        double value = new Random().nextDouble();
        Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        Acceleration outputAcceleration = AccelerationConverter.convertAndReturnNew(
                inputAcceleration, AccelerationUnit.FEET_PER_SQUARED_SECOND);

        //check
        assertEquals(inputAcceleration.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAcceleration.getUnit(),
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        assertEquals(outputAcceleration.getUnit(),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        assertEquals(outputAcceleration.getValue().doubleValue(),
                AccelerationConverter.convert(value, inputAcceleration.getUnit(),
                        outputAcceleration.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputAccelerationUnit() {
        double value = new Random().nextDouble();
        Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        Acceleration outputAcceleration = new Acceleration();
        outputAcceleration.setUnit(AccelerationUnit.FEET_PER_SQUARED_SECOND);
        AccelerationConverter.convert(inputAcceleration, outputAcceleration);

        //check
        assertEquals(inputAcceleration.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAcceleration.getUnit(),
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        assertEquals(outputAcceleration.getUnit(),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        assertEquals(outputAcceleration.getValue().doubleValue(),
                AccelerationConverter.convert(value, inputAcceleration.getUnit(),
                        outputAcceleration.getUnit()), 0.0);
    }
}
