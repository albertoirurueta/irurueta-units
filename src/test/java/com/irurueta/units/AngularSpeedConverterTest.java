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

public class AngularSpeedConverterTest {

    private static final double ERROR = 1e-6;

    public AngularSpeedConverterTest() { }

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
        assertNotNull(new AngularSpeedConverter());
    }

    @Test
    public void testRadiansPerSecondDegreesPerSecond() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(
                inputValue), inputValue * 180.0 / Math.PI, ERROR);
        assertEquals(AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(
                inputValue), inputValue * Math.PI / 180.0, ERROR);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.DEGREES_PER_SECOND), inputValue, ERROR);
        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND),
                AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(inputValue),
                ERROR);

        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.RADIANS_PER_SECOND,
                AngularSpeedUnit.DEGREES_PER_SECOND),
                AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(inputValue),
                ERROR);
        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.RADIANS_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.DEGREES_PER_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND).doubleValue(),
                AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(
                        inputValue.doubleValue()), ERROR);

        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.RADIANS_PER_SECOND,
                AngularSpeedUnit.DEGREES_PER_SECOND).doubleValue(),
                AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(
                        inputValue.doubleValue()), ERROR);
        assertEquals(AngularSpeedConverter.convert(inputValue,
                AngularSpeedUnit.RADIANS_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertAngularSpeed() {
        double value = new Random().nextDouble();
        AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed output = new AngularSpeed();
        AngularSpeedConverter.convert(input, AngularSpeedUnit.RADIANS_PER_SECOND,
                output);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);

        assertEquals(output.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngularSpeed() {
        double value = new Random().nextDouble();
        AngularSpeed angularSpeed = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeedConverter.convert(angularSpeed,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(angularSpeed.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals(angularSpeed.getValue().doubleValue(),
                AngularSpeedConverter.convert(value,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngle() {
        double value = new Random().nextDouble();
        AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed output = AngularSpeedConverter.convertAndReturnNew(input,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);

        assertEquals(output.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputAngularSpeedUnit() {
        double value = new Random().nextDouble();
        AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed output = new AngularSpeed();
        output.setUnit(AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeedConverter.convert(input, output);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);

        assertEquals(output.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }
}
