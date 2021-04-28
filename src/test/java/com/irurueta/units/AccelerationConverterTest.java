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

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class AccelerationConverterTest {
    private static final double METERS_PER_FOOT = 0.3048;

    private static final double ERROR = 1e-6;

    @Test
    public void testMeterPerSquaredSecondFeetPerSquaredSecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                inputValue), inputValue / METERS_PER_FOOT, ERROR);
        assertEquals(AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                inputValue), inputValue * METERS_PER_FOOT, ERROR);
    }

    @Test
    public void testMetersPerSquaredSecondGravity() {
        final double inputValue = new Random().nextDouble();

        assertEquals(AccelerationConverter.metersPerSquaredSecondToGravity(inputValue),
                inputValue / AccelerationConverter.STANDARD_GRAVITY, ERROR);
        assertEquals(AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue),
                inputValue * AccelerationConverter.STANDARD_GRAVITY, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

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
                AccelerationUnit.G), AccelerationConverter.metersPerSquaredSecondToGravity(
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
                AccelerationUnit.G), AccelerationConverter.metersPerSquaredSecondToGravity(
                AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                        inputValue)), ERROR);

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G,
                AccelerationUnit.METERS_PER_SQUARED_SECOND),
                AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue), ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                        AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue)),
                ERROR);
        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.G, AccelerationUnit.G),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(AccelerationConverter.convert(inputValue,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.METERS_PER_SQUARED_SECOND).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertAcceleration() {
        final double value = new Random().nextDouble();
        final Acceleration acceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        AccelerationConverter.convert(acceleration,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(acceleration.getUnit(),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        assertEquals(acceleration.getValue().doubleValue(),
                AccelerationConverter.convert(value, AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND), 0.0);
    }

    @Test
    public void testConvertAcceleration2() {
        final double value = new Random().nextDouble();
        final Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration outputAcceleration = new Acceleration();
        AccelerationConverter.convert(inputAcceleration,
                AccelerationUnit.FEET_PER_SQUARED_SECOND, outputAcceleration);

        // check
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
        final double value = new Random().nextDouble();
        final Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration outputAcceleration = AccelerationConverter.convertAndReturnNew(
                inputAcceleration, AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
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
        final double value = new Random().nextDouble();
        final Acceleration inputAcceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration outputAcceleration = new Acceleration();
        outputAcceleration.setUnit(AccelerationUnit.FEET_PER_SQUARED_SECOND);
        AccelerationConverter.convert(inputAcceleration, outputAcceleration);

        // check
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
