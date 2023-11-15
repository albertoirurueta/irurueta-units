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

public class AngleConverterTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testRadiansDegrees() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue * 180.0 / Math.PI,
                AngleConverter.radianToDegree(inputValue),
                ERROR);
        assertEquals(inputValue * Math.PI / 180.0,
                AngleConverter.degreeToRadian(inputValue),
                ERROR);
    }

    @Test
    public void testToDegreesAndMinutes1() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                    new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesAndMinutes2() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                inputValue, AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesAndMinutes3() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue),
                AngleUnit.DEGREES, degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue),
                    AngleUnit.DEGREES, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesAndMinutes4() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                new BigDecimal(inputValue), AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                new BigDecimal(inputValue), AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesAndMinutes5() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesAndMinutes(angle, degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesAndMinutes(angle, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(angle, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesAndMinutes6() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testFromDegreesAndMinutes1() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0],
                    -degreesAndMinutes[1], AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testFromDegreesAndMinutes2() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        Angle angle = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0],
                degreesAndMinutes[1], angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        angle = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesAndMinutes(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.fromDegreesAndMinutes((int) degreesAndMinutes[0],
                    -degreesAndMinutes[1], angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testFromDegreesAndMinutesAndReturnNew() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        Angle angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                (int) degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        // force IllegalArgumentException
        angle = null;
        try {
            angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                    (int) degreesAndMinutes[0], -degreesAndMinutes[1],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(angle);
    }

    @Test
    public void testToDegreesMinutesAndSeconds1() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                    new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesMinutesAndSeconds2() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                inputValue, AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesMinutesAndSeconds3() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.DEGREES, degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.RADIANS, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                    AngleUnit.DEGREES, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesMinutesAndSeconds4() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                new BigDecimal(inputValue), AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                new BigDecimal(inputValue), AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesMinutesAndSeconds5() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesMinutesAndSeconds(angle, degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesMinutesAndSeconds(angle, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(angle, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToDegreesMinutesAndSeconds6() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                angle);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(angle);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testFromDegreesMinutesAndSeconds1() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                    -1, degreesMinutesAndSeconds[2],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                    (int) degreesMinutesAndSeconds[1], -1.0,
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testFromDegreesMinutesAndSeconds2() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        Angle angle = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        angle = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                (int) degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        // force IllegalArgumentException
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                    -1, degreesMinutesAndSeconds[2],
                    angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int) degreesMinutesAndSeconds[0],
                    (int) degreesMinutesAndSeconds[1], -1.0,
                    angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testFromDegreesMinutesAndSecondsAndReturnNew() {
        final double inputValue = new Random().nextDouble();

        // test degrees
        final double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        Angle angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        // test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int) degreesMinutesAndSeconds[0], (int) degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        // force IllegalArgumentException
        angle = null;
        try {
            angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                    (int) degreesMinutesAndSeconds[0],
                    -1, degreesMinutesAndSeconds[2],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                    (int) degreesMinutesAndSeconds[0],
                    (int) degreesMinutesAndSeconds[1], -1.0,
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(angle);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.DEGREES), ERROR);
        assertEquals(AngleConverter.degreeToRadian(inputValue),
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.RADIANS), ERROR);

        assertEquals(AngleConverter.radianToDegree(inputValue),
                AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.DEGREES), ERROR);
        assertEquals(inputValue, AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.RADIANS),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.DEGREES).doubleValue(),
                ERROR);
        assertEquals(AngleConverter.degreeToRadian(inputValue.doubleValue()),
                AngleConverter.convert(inputValue, AngleUnit.DEGREES, AngleUnit.RADIANS).doubleValue(),
                ERROR);

        assertEquals(AngleConverter.radianToDegree(inputValue.doubleValue()),
                AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.DEGREES).doubleValue(),
                ERROR);
        assertEquals(inputValue.doubleValue(),
                AngleConverter.convert(inputValue, AngleUnit.RADIANS, AngleUnit.RADIANS).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertAngle() {
        final double value = new Random().nextDouble();
        final Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        final Angle outputAngle = new Angle();
        AngleConverter.convert(inputAngle, AngleUnit.RADIANS, outputAngle);

        // check
        assertEquals(value, inputAngle.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, inputAngle.getUnit());

        assertEquals(AngleUnit.RADIANS, outputAngle.getUnit());
        assertEquals(AngleConverter.convert(value, inputAngle.getUnit(), outputAngle.getUnit()),
                outputAngle.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngle() {
        final double value = new Random().nextDouble();
        final Angle angle = new Angle(value, AngleUnit.DEGREES);

        AngleConverter.convert(angle, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.RADIANS, angle.getUnit());
        assertEquals(AngleConverter.convert(value, AngleUnit.DEGREES, AngleUnit.RADIANS),
                angle.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngle() {
        final double value = new Random().nextDouble();
        final Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        final Angle outputAngle = AngleConverter.convertAndReturnNew(inputAngle,
                AngleUnit.RADIANS);

        // check
        assertEquals(value, inputAngle.getValue().doubleValue(), 0.0);
        assertEquals(AngleUnit.DEGREES, inputAngle.getUnit());

        assertEquals(AngleUnit.RADIANS, outputAngle.getUnit());
        assertEquals(AngleConverter.convert(value, inputAngle.getUnit(), outputAngle.getUnit()),
                outputAngle.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertToOutputAngleUnit() {
        final double value = new Random().nextDouble();
        final Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        final Angle outputAngle = new Angle();
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
