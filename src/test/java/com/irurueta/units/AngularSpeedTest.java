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

public class AngularSpeedTest {

    private static final double ERROR = 1e-6;

    public AngularSpeedTest() { }

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
        //test empty constructor
        AngularSpeed s = new AngularSpeed();

        //check
        assertNull(s.getValue());
        assertNull(s.getUnit());

        //test constructor with value and unit
        s = new AngularSpeed(323, AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s.getValue(), 323);
        assertEquals(s.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);

        //force IllegalArgumentException
        s = null;
        try {
            s = new AngularSpeed(null, AngularSpeedUnit.DEGREES_PER_SECOND);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            s = new AngularSpeed(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(s);
    }

    @Test
    public void testEquals() {
        double value = new Random().nextDouble();
        AngularSpeed s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s3 = new AngularSpeed(value + 1.0,
                AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);

        assertEquals(s1, s1);
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);

        //noinspection all
        assertFalse(s1.equals(null));
        //noinspection all
        assertFalse(s1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        double value = new Random().nextDouble();
        AngularSpeed s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s3 = new AngularSpeed(value + 1.0,
                AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);

        assertEquals(s1.hashCode(), s1.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        double value = new Random().nextDouble();
        AngularSpeed s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s3 = new AngularSpeed(value + 0.5 * ERROR,
                AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeed s5 = new AngularSpeed(value * Math.PI / 180.0,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        assertTrue(s1.equals(s1, 0.0));
        assertTrue(s1.equals(s2, 0.0));
        assertFalse(s1.equals(s3, 0.0));
        assertTrue(s1.equals(s3, ERROR));
        assertFalse(s1.equals(s4, ERROR));
        assertTrue(s1.equals(s5, ERROR));

        assertFalse(s1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        AngularSpeed s = new AngularSpeed(1,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s.getValue(), 1);

        //set new value
        s.setValue(2.5);

        //check
        assertEquals(s.getValue(), 2.5);

        //force IllegalArgumentException
        try {
            s.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testGetSetUnit() {
        AngularSpeed s = new AngularSpeed(1,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);

        //set new value
        s.setUnit(AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);

        //force IllegalArgumentException
        try {
            s.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testAdd1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = AngularSpeed.add(value1, AngularSpeedUnit.DEGREES_PER_SECOND,
                value2, AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = AngularSpeed.add(new BigDecimal(value1),
                AngularSpeedUnit.DEGREES_PER_SECOND,
                new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = new AngularSpeed(0.0,
                AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeed.add(s1, s2, result);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = AngularSpeed.addAndReturnNew(s1, s2,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.addAndReturnNew(value2,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.addAndReturnNew(new BigDecimal(value2),
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.addAndReturnNew(s2,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(s2);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = new AngularSpeed(0.0,
                AngularSpeedUnit.RADIANS_PER_SECOND);
        s1.add(s2, result);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = AngularSpeed.subtract(value1,
                AngularSpeedUnit.DEGREES_PER_SECOND, value2,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = AngularSpeed.subtract(new BigDecimal(value1),
                AngularSpeedUnit.DEGREES_PER_SECOND, new BigDecimal(value2),
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(),
                ERROR);
    }

    @Test
    public void testSubtract3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = new AngularSpeed(0.0,
                AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeed.subtract(s1, s2, result);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = AngularSpeed.subtractAndReturnNew(s1, s2,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.subtractAndReturnNew(value2,
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1,
                AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.subtractAndReturnNew(new BigDecimal(value2),
                AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = s1.subtractAndReturnNew(s2,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(s2);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularSpeed s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        AngularSpeed s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        AngularSpeed result = new AngularSpeed(0.0, AngularSpeedUnit.RADIANS_PER_SECOND);
        s1.subtract(s2, result);

        //check
        assertEquals(s1.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), AngularSpeedUnit.DEGREES_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularSpeedUnit.RADIANS_PER_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }
}
