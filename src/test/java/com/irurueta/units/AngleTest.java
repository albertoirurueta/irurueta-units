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

public class AngleTest {

    private static final double ERROR = 1e-6;

    public AngleTest() { }

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
        Angle a = new Angle();

        //check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        //test constructor with value and unit
        a = new Angle(323, AngleUnit.DEGREES);

        //check
        assertEquals(a.getValue(), 323);
        assertEquals(a.getUnit(), AngleUnit.DEGREES);

        //force IllegalArgumentException
        a = null;
        try {
            a = new Angle(null, AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            a = new Angle(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(a);
    }

    @Test
    public void testEquals() {
        double value = new Random().nextDouble();
        Angle a1 = new Angle(value, AngleUnit.DEGREES);
        Angle a2 = new Angle(value, AngleUnit.DEGREES);
        Angle a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        Angle a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);

        //noinspection all
        assertFalse(a1.equals(null));
        //noinspection all
        assertFalse(a1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        double value = new Random().nextDouble();
        Angle a1 = new Angle(value, AngleUnit.DEGREES);
        Angle a2 = new Angle(value, AngleUnit.DEGREES);
        Angle a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        Angle a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        double value = new Random().nextDouble();
        Angle a1 = new Angle(value, AngleUnit.DEGREES);
        Angle a2 = new Angle(value, AngleUnit.DEGREES);
        Angle a3 = new Angle(value + 0.5 * ERROR, AngleUnit.DEGREES);
        Angle a4 = new Angle(value, AngleUnit.RADIANS);
        Angle a5 = new Angle(value * Math.PI / 180.0, AngleUnit.RADIANS);

        assertTrue(a1.equals(a1, 0.0));
        assertTrue(a1.equals(a2, 0.0));
        assertFalse(a1.equals(a3, 0.0));
        assertTrue(a1.equals(a3, ERROR));
        assertFalse(a1.equals(a4, ERROR));
        assertTrue(a1.equals(a5, ERROR));

        assertFalse(a1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        Angle a = new Angle(1, AngleUnit.DEGREES);

        //check
        assertEquals(a.getValue(), 1);

        //set new value
        a.setValue(2.5);

        //check
        assertEquals(a.getValue(), 2.5);

        //force IllegalArgumentException
        try {
            a.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testGetSetUnit() {
        Angle a = new Angle(1, AngleUnit.DEGREES);

        //check
        assertEquals(a.getUnit(), AngleUnit.DEGREES);

        //set new value
        a.setUnit(AngleUnit.RADIANS);

        //check
        assertEquals(a.getUnit(), AngleUnit.RADIANS);

        //force IllegalArgumentException
        try {
            a.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testAdd1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = Angle.add(value1, AngleUnit.DEGREES,
                value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Angle.add(new BigDecimal(value1), AngleUnit.DEGREES,
                new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.add(a1, a2, result);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = Angle.addAndReturnNew(a1, a2,
                AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        Angle result = a1.addAndReturnNew(value2, AngleUnit.DEGREES,
                AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        Angle result = a1.addAndReturnNew(new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = a1.addAndReturnNew(a2, AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(value2, AngleUnit.DEGREES);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(new BigDecimal(value2), AngleUnit.DEGREES);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.add(a2);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = new Angle(0.0, AngleUnit.RADIANS);
        a1.add(a2, result);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = Angle.subtract(value1, AngleUnit.DEGREES,
                value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Angle.subtract(new BigDecimal(value1), AngleUnit.DEGREES,
                new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.subtract(a1, a2, result);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = Angle.subtractAndReturnNew(a1, a2, AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        Angle result = a1.subtractAndReturnNew(value2, AngleUnit.DEGREES,
                AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        Angle result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = a1.subtractAndReturnNew(a2, AngleUnit.RADIANS);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(value2, AngleUnit.DEGREES);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(new BigDecimal(value2), AngleUnit.DEGREES);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.subtract(a2);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        Angle result = new Angle(0.0, AngleUnit.RADIANS);
        a1.subtract(a2, result);

        //check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }
}
