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

public class AngularAccelerationTest {

    private static final double ERROR = 1e-6;

    public AngularAccelerationTest() { }

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
        AngularAcceleration a = new AngularAcceleration();

        //check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        //test constructor with value and unit
        a = new AngularAcceleration(323,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a.getValue(), 323);
        assertEquals(a.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //force IllegalArgumentException
        a = null;
        try {
            a = new AngularAcceleration(null,
                    AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            a = new AngularAcceleration(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(a);
    }

    @Test
    public void testEquals() {
        double value = new Random().nextDouble();
        AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a3 = new AngularAcceleration(value + 1.0,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

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
        AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a3 = new AngularAcceleration(value + 1.0,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        double value = new Random().nextDouble();
        AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a3 = new AngularAcceleration(value + 0.5 * ERROR,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration a5 = new AngularAcceleration(value * Math.PI / 180.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

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
        AngularAcceleration a = new AngularAcceleration(1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

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
        AngularAcceleration a = new AngularAcceleration(1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //set new value
        a.setUnit(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

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

        double result = AngularAcceleration.add(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = AngularAcceleration.add(new BigDecimal(value1),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.add(a1, a2, result);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = AngularAcceleration.addAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.addAndReturnNew(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.addAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.addAndReturnNew(a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(a2);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.add(a2, result);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = AngularAcceleration.subtract(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = AngularAcceleration.subtract(new BigDecimal(value1),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.subtract(a1, a2, result);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = AngularAcceleration.subtractAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.subtractAndReturnNew(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = a1.subtractAndReturnNew(a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(a2);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.subtract(a2, result);

        //check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }
}
