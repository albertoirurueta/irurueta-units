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

public class FrequencyTest {

    private static final double ERROR = 1e-6;

    public FrequencyTest() { }

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
        Frequency f = new Frequency();

        //check
        assertNull(f.getValue());
        assertNull(f.getUnit());

        //test constructor with value and unit
        f = new Frequency(1000, FrequencyUnit.HERTZ);

        //check
        assertEquals(f.getValue(), 1000);
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        //force IllegalArgumentException
        f = null;
        try {
            f = new Frequency(null, FrequencyUnit.GIGAHERTZ);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            f = new Frequency(1000, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(f);
    }

    @Test
    public void testEquals() {
        double value = new Random().nextDouble();
        Frequency f1 = new Frequency(value, FrequencyUnit.HERTZ);
        Frequency f2 = new Frequency(value, FrequencyUnit.HERTZ);
        Frequency f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        Frequency f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1, f1);
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);

        //noinespection all
        assertNotEquals(null, f1);
        //noinspection all
        assertFalse(f1.equals(new Object()));
    }

    @Test
    public void testHashCode() {
        double value = new Random().nextDouble();
        Frequency f1 = new Frequency(value, FrequencyUnit.HERTZ);
        Frequency f2 = new Frequency(value, FrequencyUnit.HERTZ);
        Frequency f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        Frequency f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
        assertNotEquals(f1.hashCode(), f4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        double value = new Random().nextDouble();
        Frequency f1 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        Frequency f3 = new Frequency(value + 0.5 * ERROR, FrequencyUnit.KILOHERTZ);
        Frequency f4 = new Frequency(value, FrequencyUnit.HERTZ);
        Frequency f5 = new Frequency(value * 1000.0 , FrequencyUnit.HERTZ);

        assertTrue(f1.equals(f1, 0.0));
        assertTrue(f1.equals(f2, 0.0));
        assertFalse(f1.equals(f3, 0.0));
        assertTrue(f1.equals(f3, ERROR));
        assertFalse(f1.equals(f4, ERROR));
        assertTrue(f1.equals(f5, ERROR));

        assertFalse(f1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        Frequency f = new Frequency(1, FrequencyUnit.HERTZ);

        //check
        assertEquals(f.getValue(), 1);

        //set new value
        f.setValue(2.5);

        //check
        assertEquals(f.getValue(), 2.5);

        //force IllegalArgumentException
        try {
            f.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testGetSetUnit() {
        Frequency f = new Frequency(1, FrequencyUnit.HERTZ);

        //check
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        //set new value
        f.setUnit(FrequencyUnit.HERTZ);

        //check
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        //force IllegalArgumentException
        try {
            f.setUnit(null);
            fail("IllegalARgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
    }

    @Test
    public void testAdd1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = Frequency.add(value1, FrequencyUnit.KILOHERTZ,
                value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Frequency.add(new BigDecimal(value1), FrequencyUnit.KILOHERTZ,
                new BigDecimal(value2), FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.add(f1, f2, result);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = Frequency.addAndReturnNew(f1, f2, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.addAndReturnNew(value2, FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.addAndReturnNew(new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.addAndReturnNew(f2, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(value2, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(new BigDecimal(value2), FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.add(f2);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(f2.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.add(f2, result);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = Frequency.subtract(value1, FrequencyUnit.KILOHERTZ,
                value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Frequency.subtract(new BigDecimal(value1), FrequencyUnit.KILOHERTZ,
                new BigDecimal(value2), FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.subtract(f1, f2, result);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = Frequency.subtractAndReturnNew(f1, f2,
                FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.subtractAndReturnNew(value2,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.subtractAndReturnNew(new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = f1.subtractAndReturnNew(f2, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(value2, FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(new BigDecimal(value2), FrequencyUnit.HERTZ);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.subtract(f2);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(f2.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.subtract(f2, result);

        //check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }
}
