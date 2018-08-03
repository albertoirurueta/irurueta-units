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

public class SpeedTest {

    private static final double ERROR = 1e-6;

    public SpeedTest() { }

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
        Speed s = new Speed();

        //check
        assertNull(s.getValue());
        assertNull(s.getUnit());

        //test constructor with value and unit
        s = new Speed(323, SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s.getValue(), 323);
        assertEquals(s.getUnit(), SpeedUnit.METERS_PER_SECOND);

        //force IllegalArgumentException
        s = null;
        try {
            s = new Speed(null, SpeedUnit.METERS_PER_SECOND);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        try {
            s = new Speed(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (IllegalArgumentException ignore) { }
        assertNull(s);
    }

    @Test
    public void testEquals() {
        double value = new Random().nextDouble();
        Speed s1 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s3 = new Speed(value + 1.0, SpeedUnit.METERS_PER_SECOND);
        Speed s4 = new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR);

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
    public void tetHashCode() {
        double value = new Random().nextDouble();
        Speed s1 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s3 = new Speed(value + 1.0, SpeedUnit.METERS_PER_SECOND);
        Speed s4 = new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR);

        assertEquals(s1.hashCode(), s1.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        double value = new Random().nextDouble();
        Speed s1 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value, SpeedUnit.METERS_PER_SECOND);
        Speed s3 = new Speed(value + 0.5 * ERROR, SpeedUnit.METERS_PER_SECOND);
        Speed s4 = new Speed(value, SpeedUnit.KILOMETERS_PER_HOUR);
        Speed s5 = new Speed(SpeedConverter.convert(value,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                SpeedUnit.KILOMETERS_PER_HOUR);

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
        Speed s = new Speed(1, SpeedUnit.METERS_PER_SECOND);

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
        Speed s = new Speed(1, SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s.getUnit(), SpeedUnit.METERS_PER_SECOND);

        //set new value
        s.setUnit(SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);

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

        double result = Speed.add(value1, SpeedUnit.METERS_PER_SECOND,
                value2, SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result, ERROR);
    }

    @Test
    public void testAdd2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Speed.add(new BigDecimal(value1), SpeedUnit.METERS_PER_SECOND,
                new BigDecimal(value2), SpeedUnit.METERS_PER_SECOND,
                SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = new Speed(0.0, SpeedUnit.KILOMETERS_PER_HOUR);
        Speed.add(s1, s2, result);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = Speed.addAndReturnNew(s1, s2,
                SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.addAndReturnNew(value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.addAndReturnNew(new BigDecimal(value2),
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.addAndReturnNew(s2, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        s1.add(value2, SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        s1.add(new BigDecimal(value2), SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        s1.add(s2);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = new Speed(0.0, SpeedUnit.KILOMETERS_PER_HOUR);
        s1.add(s2, result);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 + value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        double result = Speed.subtract(value1, SpeedUnit.METERS_PER_SECOND,
                value2, SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result, ERROR);
    }

    @Test
    public void testSubtract2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Number result = Speed.subtract(new BigDecimal(value1),
                SpeedUnit.METERS_PER_SECOND, new BigDecimal(value2),
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = new Speed(0.0, SpeedUnit.KILOMETERS_PER_HOUR);
        Speed.subtract(s1, s2, result);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = Speed.subtractAndReturnNew(s1, s2,
                SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.subtractAndReturnNew(value2, SpeedUnit.METERS_PER_SECOND,
                SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.subtractAndReturnNew(new BigDecimal(value2),
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = s1.subtractAndReturnNew(s2, SpeedUnit.KILOMETERS_PER_HOUR);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        s1.subtract(value2, SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);

        s1.subtract(new BigDecimal(value2), SpeedUnit.METERS_PER_SECOND);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        s1.subtract(s2);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        Random r = new Random();
        double value1 = r.nextDouble();
        double value2 = r.nextDouble();

        Speed s1 = new Speed(value1, SpeedUnit.METERS_PER_SECOND);
        Speed s2 = new Speed(value2, SpeedUnit.METERS_PER_SECOND);

        Speed result = new Speed(0.0, SpeedUnit.KILOMETERS_PER_HOUR);
        s1.subtract(s2, result);

        //check
        assertEquals(s1.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s1.getValue().doubleValue(), value1, 0.0);

        assertEquals(s2.getUnit(), SpeedUnit.METERS_PER_SECOND);
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), SpeedUnit.KILOMETERS_PER_HOUR);
        assertEquals(SpeedConverter.convert(value1 - value2,
                SpeedUnit.METERS_PER_SECOND, SpeedUnit.KILOMETERS_PER_HOUR),
                result.getValue().doubleValue(), ERROR);
    }
}