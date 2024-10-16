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

class AngleConverterTest {

    private static final double ERROR = 1e-6;

    @Test
    void testRadiansDegrees() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * 180.0 / Math.PI, AngleConverter.radianToDegree(inputValue), ERROR);
        assertEquals(inputValue * Math.PI / 180.0, AngleConverter.degreeToRadian(inputValue), ERROR);
    }

    @Test
    void testToDegreesAndMinutes1() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES, degreesAndMinutes);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES, new double[1]));
    }

    @Test
    void testToDegreesAndMinutes2() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var degreesAndMinutes = AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testToDegreesAndMinutes3() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue), AngleUnit.DEGREES, degreesAndMinutes);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        final var v = new BigDecimal(inputValue);
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.toDegreesAndMinutes(v, AngleUnit.DEGREES, new double[1]));
    }

    @Test
    void testToDegreesAndMinutes4() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var degreesAndMinutes = AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue), AngleUnit.DEGREES);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue), AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testToDegreesAndMinutes5() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        final var angleDegrees = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesAndMinutes(angleDegrees, degreesAndMinutes);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        final var angleRadians = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesAndMinutes(angleRadians, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.toDegreesAndMinutes(angleRadians, new double[1]));
    }

    @Test
    void testToDegreesAndMinutes6() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var angle = new Angle(inputValue, AngleUnit.DEGREES);
        var degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testFromDegreesAndMinutes1() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES, degreesAndMinutes);

        var outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], -degreesAndMinutes[1],
                        AngleUnit.DEGREES));
    }

    @Test
    void testFromDegreesAndMinutes2() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES, degreesAndMinutes);

        final var angleDegrees = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1], angleDegrees);

        assertEquals(inputValue, angleDegrees.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS, degreesAndMinutes);

        final var angleRadians = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], degreesAndMinutes[1], angleRadians);

        assertEquals(inputValue, angleRadians.getValue().doubleValue(), ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0], -degreesAndMinutes[1],
                        angleRadians));
    }

    @Test
    void testFromDegreesAndMinutesAndReturnNew() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES, degreesAndMinutes);

        var angle = AngleConverter.fromDegreesAndMinutesAndReturnNew((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS, degreesAndMinutes);

        angle = AngleConverter.fromDegreesAndMinutesAndReturnNew((int) degreesAndMinutes[0], degreesAndMinutes[1],
                AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesAndMinutesAndReturnNew(
                (int) degreesAndMinutes[0], -degreesAndMinutes[1], AngleUnit.DEGREES));
    }

    @Test
    void testToDegreesMinutesAndSeconds1() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES, degreesMinutesAndSeconds);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.toDegreesMinutesAndSeconds(inputValue,
                AngleUnit.DEGREES, new double[1]));
    }

    @Test
    void testToDegreesMinutesAndSeconds2() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testToDegreesMinutesAndSeconds3() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue), AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue), AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        final var v = new BigDecimal(inputValue);
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.toDegreesMinutesAndSeconds(
                v, AngleUnit.DEGREES, new double[1]));
    }

    @Test
    void testToDegreesMinutesAndSeconds4() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.DEGREES);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testToDegreesMinutesAndSeconds5() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        final var angleDegrees = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesMinutesAndSeconds(angleDegrees, degreesMinutesAndSeconds);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        final var angleRadians = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesMinutesAndSeconds(angleRadians, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> AngleConverter.toDegreesMinutesAndSeconds(angleRadians, new double[1]));
    }

    @Test
    void testToDegreesMinutesAndSeconds6() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        var angle = new Angle(inputValue, AngleUnit.DEGREES);
        var degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(angle);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(angle);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    void testFromDegreesMinutesAndSeconds1() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES, degreesMinutesAndSeconds);

        var outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], -1, degreesMinutesAndSeconds[2], AngleUnit.DEGREES));
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1], -1.0,
                AngleUnit.DEGREES));
    }

    @Test
    void testFromDegreesMinutesAndSeconds2() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES, degreesMinutesAndSeconds);

        final var angleDegrees = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], angleDegrees);

        assertEquals(inputValue, angleDegrees.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS, degreesMinutesAndSeconds);

        final var angleRadians = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], angleRadians);

        assertEquals(inputValue, angleRadians.getValue().doubleValue(), ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], -1, degreesMinutesAndSeconds[2], angleRadians));
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1], -1.0, angleRadians));
    }

    @Test
    void testFromDegreesMinutesAndSecondsAndReturnNew() {
        final var inputValue = new Random().nextDouble();

        // test degrees
        final var degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES, degreesMinutesAndSeconds);

        var angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS, degreesMinutesAndSeconds);

        angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int) degreesMinutesAndSeconds[0], -1, degreesMinutesAndSeconds[2], AngleUnit.DEGREES));
        assertThrows(IllegalArgumentException.class, () -> AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1], -1.0, AngleUnit.DEGREES));
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue, AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.DEGREES), ERROR);
        assertEquals(AngleConverter.degreeToRadian(inputValue), AngleConverter.convert(inputValue, AngleUnit.DEGREES,
                AngleUnit.RADIANS), ERROR);

        assertEquals(AngleConverter.radianToDegree(inputValue), AngleConverter.convert(inputValue, AngleUnit.RADIANS,
                AngleUnit.DEGREES), ERROR);
        assertEquals(inputValue, AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.RADIANS), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.DEGREES).doubleValue(), ERROR);
        assertEquals(AngleConverter.degreeToRadian(inputValue.doubleValue()),
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.RADIANS).doubleValue(), ERROR);

        assertEquals(AngleConverter.radianToDegree(inputValue.doubleValue()),
                AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.DEGREES).doubleValue(), ERROR);
        assertEquals(inputValue.doubleValue(),
                AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.RADIANS).doubleValue(), ERROR);
    }

    @Test
    void testConvertAngle() {
        final var value = new Random().nextDouble();
        final var inputAngle = new Angle(value, AngleUnit.DEGREES);

        final var outputAngle = new Angle();
        AngleConverter.convert(inputAngle, AngleUnit.RADIANS, outputAngle);

        // check
        assertEquals(value, inputAngle.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, inputAngle.getUnit());

        assertEquals(AngleUnit.RADIANS, outputAngle.getUnit());
        assertEquals(AngleConverter.convert(value, inputAngle.getUnit(), outputAngle.getUnit()),
                outputAngle.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateAngle() {
        final var value = new Random().nextDouble();
        final var angle = new Angle(value, AngleUnit.DEGREES);

        AngleConverter.convert(angle, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.RADIANS, angle.getUnit());
        assertEquals(AngleConverter.convert(value, AngleUnit.DEGREES, AngleUnit.RADIANS),
                angle.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewAngle() {
        final var value = new Random().nextDouble();
        final var inputAngle = new Angle(value, AngleUnit.DEGREES);

        final var outputAngle = AngleConverter.convertAndReturnNew(inputAngle, AngleUnit.RADIANS);

        // check
        assertEquals(value, inputAngle.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, inputAngle.getUnit());

        assertEquals(AngleUnit.RADIANS, outputAngle.getUnit());
        assertEquals(AngleConverter.convert(value, inputAngle.getUnit(), outputAngle.getUnit()),
                outputAngle.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputAngleUnit() {
        final var value = new Random().nextDouble();
        final var inputAngle = new Angle(value, AngleUnit.DEGREES);

        final var outputAngle = new Angle();
        outputAngle.setUnit(AngleUnit.RADIANS);
        AngleConverter.convert(inputAngle, outputAngle);

        // check
        assertEquals(value, inputAngle.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, inputAngle.getUnit());

        assertEquals(AngleUnit.RADIANS, outputAngle.getUnit());
        assertEquals(AngleConverter.convert(value, inputAngle.getUnit(), outputAngle.getUnit()),
                outputAngle.getValue().doubleValue(), 0.0);
    }
}
