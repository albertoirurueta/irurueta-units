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

    public AngleConverterTest() { }

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
        assertNotNull(new AngleConverter());
    }

    @Test
    public void testRadiansDegrees() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngleConverter.radianToDegree(inputValue),
                inputValue * 180.0 / Math.PI, ERROR);
        assertEquals(AngleConverter.degreeToRadian(inputValue),
                inputValue * Math.PI / 180.0, ERROR);
    }

    @Test
    public void testToDegreesAndMinutes1() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                    new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesAndMinutes2() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                inputValue, AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesAndMinutes3() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue),
                AngleUnit.DEGREES, degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(new BigDecimal(inputValue),
                    AngleUnit.DEGREES, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesAndMinutes4() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                new BigDecimal(inputValue), AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(
                new BigDecimal(inputValue), AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesAndMinutes5() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesAndMinutes(angle, degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesAndMinutes(angle, degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);

        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesAndMinutes(angle, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesAndMinutes6() {
        double inputValue = new Random().nextDouble();

        //test degrees
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        double[] degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesAndMinutes = AngleConverter.toDegreesAndMinutes(angle);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testFromDegreesAndMinutes1() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        double outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        outputValue = AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.fromDegreesAndMinutes((int)degreesAndMinutes[0],
                    -degreesAndMinutes[1], AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testFromDegreesAndMinutes2() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        Angle angle = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesAndMinutes((int)degreesAndMinutes[0],
                degreesAndMinutes[1], angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        //test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        angle = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesAndMinutes(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.fromDegreesAndMinutes((int)degreesAndMinutes[0],
                    -degreesAndMinutes[1], angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testFromDegreesAndMinutesAndReturnNew() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesAndMinutes = new double[2];
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.DEGREES,
                degreesAndMinutes);

        Angle angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        //test radians
        AngleConverter.toDegreesAndMinutes(inputValue, AngleUnit.RADIANS,
                degreesAndMinutes);

        angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                (int)degreesAndMinutes[0], degreesAndMinutes[1], AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        //force IllegalArgumentException
        angle = null;
        try {
            angle = AngleConverter.fromDegreesAndMinutesAndReturnNew(
                    (int)degreesAndMinutes[0], -degreesAndMinutes[1],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(angle);
    }

    @Test
    public void testToDegreesMinutesAndSeconds1() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                    new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesMinutesAndSeconds2() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                inputValue, AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                inputValue, AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesMinutesAndSeconds3() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.DEGREES, degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                AngleUnit.RADIANS, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(new BigDecimal(inputValue),
                    AngleUnit.DEGREES, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesMinutesAndSeconds4() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                new BigDecimal(inputValue), AngleUnit.DEGREES);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                new BigDecimal(inputValue), AngleUnit.RADIANS);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testToDegreesMinutesAndSeconds5() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        AngleConverter.toDegreesMinutesAndSeconds(angle, degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        AngleConverter.toDegreesMinutesAndSeconds(angle, degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.toDegreesMinutesAndSeconds(angle, new double[1]);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testToDegreesMinutesAndSeconds6() {
        double inputValue = new Random().nextDouble();

        //test degrees
        Angle angle = new Angle(inputValue, AngleUnit.DEGREES);
        double[] degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(
                angle);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        angle = new Angle(inputValue, AngleUnit.RADIANS);
        degreesMinutesAndSeconds = AngleConverter.toDegreesMinutesAndSeconds(angle);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);
    }

    @Test
    public void testFromDegreesMinutesAndSeconds1() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        double outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, outputValue, ERROR);

        //test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        outputValue = AngleConverter.fromDegreesMinutesAndSeconds(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, outputValue, ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                    -(int)degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                    (int)degreesMinutesAndSeconds[1], -degreesMinutesAndSeconds[2],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testFromDegreesMinutesAndSeconds2() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        Angle angle = new Angle(0.0, AngleUnit.DEGREES);
        AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                (int)degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        //test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        angle = new Angle(0.0, AngleUnit.RADIANS);
        AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                (int)degreesMinutesAndSeconds[1], degreesMinutesAndSeconds[2],
                angle);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        //force IllegalArgumentException
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                    -1, degreesMinutesAndSeconds[2],
                    angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            AngleConverter.fromDegreesMinutesAndSeconds((int)degreesMinutesAndSeconds[0],
                    (int)degreesMinutesAndSeconds[1], -1.0,
                    angle);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testFromDegreesMinutesAndSecondsAndReturnNew() {
        double inputValue = new Random().nextDouble();

        //test degrees
        double[] degreesMinutesAndSeconds = new double[3];
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.DEGREES,
                degreesMinutesAndSeconds);

        Angle angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.DEGREES);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);

        //test radians
        AngleConverter.toDegreesMinutesAndSeconds(inputValue, AngleUnit.RADIANS,
                degreesMinutesAndSeconds);

        angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                (int)degreesMinutesAndSeconds[0], (int)degreesMinutesAndSeconds[1],
                degreesMinutesAndSeconds[2], AngleUnit.RADIANS);

        assertEquals(inputValue, angle.getValue().doubleValue(), ERROR);


        //force IllegalArgumentException
        angle = null;
        try {
            angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                    (int)degreesMinutesAndSeconds[0],
                    -1, degreesMinutesAndSeconds[2],
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            angle = AngleConverter.fromDegreesMinutesAndSecondsAndReturnNew(
                    (int)degreesMinutesAndSeconds[0],
                    (int)degreesMinutesAndSeconds[1], -1.0,
                    AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(angle);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.DEGREES, AngleUnit.DEGREES), inputValue, ERROR);
        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.DEGREES, AngleUnit.RADIANS),
                AngleConverter.degreeToRadian(inputValue), ERROR);

        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.RADIANS, AngleUnit.DEGREES),
                AngleConverter.radianToDegree(inputValue), ERROR);
        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.RADIANS, AngleUnit.RADIANS), inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.DEGREES, AngleUnit.DEGREES).doubleValue(),
                inputValue.doubleValue(), ERROR);
        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.DEGREES, AngleUnit.RADIANS).doubleValue(),
                AngleConverter.degreeToRadian(inputValue.doubleValue()), ERROR);

        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.RADIANS, AngleUnit.DEGREES).doubleValue(),
                AngleConverter.radianToDegree(inputValue.doubleValue()), ERROR);
        assertEquals(AngleConverter.convert(inputValue,
                AngleUnit.RADIANS, AngleUnit.RADIANS).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertAngle() {
        double value = new Random().nextDouble();
        Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        Angle outputAngle = new Angle();
        AngleConverter.convert(inputAngle, AngleUnit.RADIANS, outputAngle);

        //check
        assertEquals(inputAngle.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAngle.getUnit(), AngleUnit.DEGREES);

        assertEquals(outputAngle.getUnit(), AngleUnit.RADIANS);
        assertEquals(outputAngle.getValue().doubleValue(),
                AngleConverter.convert(value, inputAngle.getUnit(),
                        outputAngle.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateAngle() {
        double value = new Random().nextDouble();
        Angle angle = new Angle(value, AngleUnit.DEGREES);

        AngleConverter.convert(angle, AngleUnit.RADIANS);

        //check
        assertEquals(angle.getUnit(), AngleUnit.RADIANS);
        assertEquals(angle.getValue().doubleValue(),
                AngleConverter.convert(value, AngleUnit.DEGREES,
                        AngleUnit.RADIANS), 0.0);
    }

    @Test
    public void testConvertAndReturnNewAngle() {
        double value = new Random().nextDouble();
        Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        Angle outputAngle = AngleConverter.convertAndReturnNew(inputAngle,
                AngleUnit.RADIANS);

        //check
        assertEquals(inputAngle.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAngle.getUnit(), AngleUnit.DEGREES);

        assertEquals(outputAngle.getUnit(), AngleUnit.RADIANS);
        assertEquals(outputAngle.getValue().doubleValue(),
                AngleConverter.convert(value, inputAngle.getUnit(),
                        outputAngle.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputAngleUnit() {
        double value = new Random().nextDouble();
        Angle inputAngle = new Angle(value, AngleUnit.DEGREES);

        Angle outputAngle = new Angle();
        outputAngle.setUnit(AngleUnit.RADIANS);
        AngleConverter.convert(inputAngle, outputAngle);

        //check
        assertEquals(inputAngle.getValue().doubleValue(), value, 0.0);
        assertEquals(inputAngle.getUnit(), AngleUnit.DEGREES);

        assertEquals(outputAngle.getUnit(), AngleUnit.RADIANS);
        assertEquals(outputAngle.getValue().doubleValue(),
                AngleConverter.convert(value, inputAngle.getUnit(),
                        outputAngle.getUnit()), 0.0);
    }

}
