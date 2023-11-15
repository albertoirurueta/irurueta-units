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

        assertEquals(inputValue / METERS_PER_FOOT,
                AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(inputValue), ERROR);
        assertEquals(inputValue * METERS_PER_FOOT,
                AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(inputValue), ERROR);
    }

    @Test
    public void testMetersPerSquaredSecondGravity() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / AccelerationConverter.STANDARD_GRAVITY,
                AccelerationConverter.metersPerSquaredSecondToGravity(inputValue), ERROR);
        assertEquals(inputValue * AccelerationConverter.STANDARD_GRAVITY,
                AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue), ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                AccelerationConverter.convert(inputValue, AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                        inputValue),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(AccelerationConverter.metersPerSquaredSecondToGravity(inputValue),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.G),
                ERROR);

        assertEquals(AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                        inputValue),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(inputValue,
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(AccelerationConverter.metersPerSquaredSecondToGravity(
                        AccelerationConverter.feetPerSquaredSecondToMetersPerSquaredSecond(
                                inputValue)),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND,
                        AccelerationUnit.G),
                ERROR);

        assertEquals(AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.G,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(AccelerationConverter.metersPerSquaredSecondToFeetPerSquaredSecond(
                        AccelerationConverter.gravityToMetersPerSquaredSecond(inputValue)),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.G,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(inputValue,
                AccelerationConverter.convert(inputValue, AccelerationUnit.G, AccelerationUnit.G),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                AccelerationConverter.convert(inputValue,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertAcceleration() {
        final double value = new Random().nextDouble();
        final Acceleration acceleration = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        AccelerationConverter.convert(acceleration,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND,
                acceleration.getUnit());
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
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND,
                inputAcceleration.getUnit());

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND,
                outputAcceleration.getUnit());
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
        assertEquals(value, inputAcceleration.getValue().doubleValue(), 0.0);
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, inputAcceleration.getUnit());

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, outputAcceleration.getUnit());
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
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND,
                inputAcceleration.getUnit());

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND,
                outputAcceleration.getUnit());
        assertEquals(outputAcceleration.getValue().doubleValue(),
                AccelerationConverter.convert(value, inputAcceleration.getUnit(),
                        outputAcceleration.getUnit()), 0.0);
    }
}
