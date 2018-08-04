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

public class AngularAccelerationConverterTest {

    private static final double ERROR = 1e-6;

    public AngularAccelerationConverterTest() { }

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
        assertNotNull(new AngularAccelerationConverter());
    }

    @Test
    public void testRadiansPerSquaredSecondDegreesPerSquaredSecond() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngularAccelerationConverter.
                radiansPerSquaredSecondToDegreesPerSquaredSecond(inputValue),
                inputValue * 180.0 / Math.PI, ERROR);
        assertEquals(AngularAccelerationConverter.
                degreesPerSquaredSecondToRadiansPerSquaredSecond(inputValue),
                inputValue * Math.PI / 180.0, ERROR);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                inputValue, ERROR);
        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                AngularAccelerationConverter.degreesPerSquaredSecondToRadiansPerSquaredSecond(inputValue),
                ERROR);

        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                AngularAccelerationConverter.radiansPerSquaredSecondToDegreesPerSquaredSecond(inputValue),
                ERROR);
        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND).doubleValue(),
                AngularAccelerationConverter.degreesPerSquaredSecondToRadiansPerSquaredSecond(
                        inputValue.doubleValue()), ERROR);

        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND).doubleValue(),
                AngularAccelerationConverter.radiansPerSquaredSecondToDegreesPerSquaredSecond(
                        inputValue.doubleValue()), ERROR);
        assertEquals(AngularAccelerationConverter.convert(inputValue,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertAngularAcceleration() {
        double value = new Random().nextDouble();
        AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration output = new AngularAcceleration();
        AngularAccelerationConverter.convert(input,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, output);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        assertEquals(output.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularAccelerationConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngularAcceleration() {
        double value = new Random().nextDouble();
        AngularAcceleration angularAcceleration = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAccelerationConverter.convert(angularAcceleration,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(angularAcceleration.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals(angularAcceleration.getValue().doubleValue(),
                AngularAccelerationConverter.convert(value,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngularAcceleration() {
        double value = new Random().nextDouble();
        AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration output = AngularAccelerationConverter.convertAndReturnNew(
                input, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        assertEquals(output.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularAccelerationConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputAngularAccelerationUnit() {
        double value = new Random().nextDouble();
        AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration output = new AngularAcceleration();
        output.setUnit(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAccelerationConverter.convert(input, output);

        //check
        assertEquals(input.getValue().doubleValue(), value, 0.0);
        assertEquals(input.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        assertEquals(output.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals(output.getValue().doubleValue(),
                AngularAccelerationConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }
}
