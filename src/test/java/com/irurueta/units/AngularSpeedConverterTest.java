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

    @Test
    public void testRadiansPerSecondDegreesPerSecond() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue * 180.0 / Math.PI,
                AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(inputValue), ERROR);
        assertEquals(inputValue * Math.PI / 180.0,
                AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(inputValue), ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.DEGREES_PER_SECOND),
                ERROR);
        assertEquals(
                AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(inputValue),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND),
                ERROR);

        assertEquals(
                AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(inputValue),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.RADIANS_PER_SECOND,
                        AngularSpeedUnit.DEGREES_PER_SECOND),
                ERROR);
        assertEquals(inputValue,
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.RADIANS_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.DEGREES_PER_SECOND).doubleValue(),
                ERROR);
        assertEquals(
                AngularSpeedConverter.degreesPerSecondToRadiansPerSecond(
                        inputValue.doubleValue()),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND).doubleValue(),
                ERROR);

        assertEquals(
                AngularSpeedConverter.radiansPerSecondToDegreesPerSecond(
                        inputValue.doubleValue()),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.RADIANS_PER_SECOND,
                        AngularSpeedUnit.DEGREES_PER_SECOND).doubleValue(),
                ERROR);
        assertEquals(inputValue.doubleValue(),
                AngularSpeedConverter.convert(inputValue,
                        AngularSpeedUnit.RADIANS_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertAngularSpeed() {
        final double value = new Random().nextDouble();
        final AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        final AngularSpeed output = new AngularSpeed();
        AngularSpeedConverter.convert(input, AngularSpeedUnit.RADIANS_PER_SECOND,
                output);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, input.getUnit());

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, output.getUnit());
        assertEquals(output.getValue().doubleValue(),
                AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngularSpeed() {
        final double value = new Random().nextDouble();
        final AngularSpeed angularSpeed = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeedConverter.convert(angularSpeed,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, angularSpeed.getUnit());
        assertEquals(angularSpeed.getValue().doubleValue(),
                AngularSpeedConverter.convert(value,
                        AngularSpeedUnit.DEGREES_PER_SECOND,
                        AngularSpeedUnit.RADIANS_PER_SECOND), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngularSpeed() {
        final double value = new Random().nextDouble();
        final AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        final AngularSpeed output = AngularSpeedConverter.convertAndReturnNew(input,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, input.getUnit());

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, output.getUnit());
        assertEquals(AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()),
                output.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertToOutputAngularSpeedUnit() {
        final double value = new Random().nextDouble();
        final AngularSpeed input = new AngularSpeed(value,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        final AngularSpeed output = new AngularSpeed();
        output.setUnit(AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeedConverter.convert(input, output);

        // check
        assertEquals(value, input.getValue().doubleValue(), 0.0);
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, input.getUnit());

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, output.getUnit());
        assertEquals(output.getValue().doubleValue(),
                AngularSpeedConverter.convert(value, input.getUnit(),
                        output.getUnit()), 0.0);
    }
}
