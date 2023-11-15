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

public class AngularAccelerationConverterTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testRadiansPerSquaredSecondDegreesPerSquaredSecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(AngularAccelerationConverter.
                        radiansPerSquaredSecondToDegreesPerSquaredSecond(inputValue),
                inputValue * 180.0 / Math.PI, ERROR);
        assertEquals(AngularAccelerationConverter.
                        degreesPerSquaredSecondToRadiansPerSquaredSecond(inputValue),
                inputValue * Math.PI / 180.0, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(
                AngularAccelerationConverter.degreesPerSquaredSecondToRadiansPerSquaredSecond(inputValue),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                ERROR);

        assertEquals(
                AngularAccelerationConverter.radiansPerSquaredSecondToDegreesPerSquaredSecond(inputValue),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND),
                ERROR);
        assertEquals(inputValue,
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND).doubleValue(),
                ERROR);
        assertEquals(
                AngularAccelerationConverter.degreesPerSquaredSecondToRadiansPerSquaredSecond(
                        inputValue.doubleValue()),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND).doubleValue(),
                ERROR);

        assertEquals(AngularAccelerationConverter.radiansPerSquaredSecondToDegreesPerSquaredSecond(
                        inputValue.doubleValue()),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND).doubleValue(),
                ERROR);
        assertEquals(inputValue.doubleValue(),
                AngularAccelerationConverter.convert(inputValue,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertAngularAcceleration() {
        final double value = new Random().nextDouble();
        final AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration output = new AngularAcceleration();
        AngularAccelerationConverter.convert(input,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, output);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, input.getUnit());

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, output.getUnit());
        assertEquals(AngularAccelerationConverter.convert(value, input.getUnit(),
                output.getUnit()), output.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngularAcceleration() {
        final double value = new Random().nextDouble();
        final AngularAcceleration angularAcceleration = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAccelerationConverter.convert(angularAcceleration,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND,
                angularAcceleration.getUnit());
        assertEquals(AngularAccelerationConverter.convert(value,
                        AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                        AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND),
                angularAcceleration.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngularAcceleration() {
        final double value = new Random().nextDouble();
        final AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration output = AngularAccelerationConverter.convertAndReturnNew(
                input, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, input.getUnit());

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, output.getUnit());
        assertEquals(AngularAccelerationConverter.convert(value, input.getUnit(),
                        output.getUnit()),
                output.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertToOutputAngularAccelerationUnit() {
        final double value = new Random().nextDouble();
        final AngularAcceleration input = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration output = new AngularAcceleration();
        output.setUnit(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAccelerationConverter.convert(input, output);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, input.getUnit());

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, output.getUnit());
        assertEquals(AngularAccelerationConverter.convert(value, input.getUnit(),
                        output.getUnit()),
                output.getValue().doubleValue(), 0.0);
    }
}
