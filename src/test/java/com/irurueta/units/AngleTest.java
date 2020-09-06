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

    @Test
    public void testConstructor() {
        // test empty constructor
        Angle a = new Angle();

        // check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        // test constructor with value and unit
        a = new Angle(323, AngleUnit.DEGREES);

        // check
        assertEquals(a.getValue(), 323);
        assertEquals(a.getUnit(), AngleUnit.DEGREES);

        // force IllegalArgumentException
        a = null;
        try {
            a = new Angle(null, AngleUnit.DEGREES);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            a = new Angle(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(a);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Angle a1 = new Angle(value, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value, AngleUnit.DEGREES);
        final Angle a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        final Angle a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1, a1);
        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);

        assertNotEquals(null, a1);
        assertNotEquals(a1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Angle a1 = new Angle(value, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value, AngleUnit.DEGREES);
        final Angle a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        final Angle a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Angle a1 = new Angle(value, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value, AngleUnit.DEGREES);
        final Angle a3 = new Angle(value + 0.5 * ERROR, AngleUnit.DEGREES);
        final Angle a4 = new Angle(value, AngleUnit.RADIANS);
        final Angle a5 = new Angle(value * Math.PI / 180.0, AngleUnit.RADIANS);

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
        final Angle a = new Angle(1, AngleUnit.DEGREES);

        // check
        assertEquals(a.getValue(), 1);

        // set new value
        a.setValue(2.5);

        // check
        assertEquals(a.getValue(), 2.5);

        // force IllegalArgumentException
        try {
            a.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Angle a = new Angle(1, AngleUnit.DEGREES);

        // check
        assertEquals(a.getUnit(), AngleUnit.DEGREES);

        // set new value
        a.setUnit(AngleUnit.RADIANS);

        // check
        assertEquals(a.getUnit(), AngleUnit.RADIANS);

        // force IllegalArgumentException
        try {
            a.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Angle.add(value1, AngleUnit.DEGREES,
                value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Angle.add(new BigDecimal(value1), AngleUnit.DEGREES,
                new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.add(a1, a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = Angle.addAndReturnNew(a1, a2,
                AngleUnit.RADIANS);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        final Angle result = a1.addAndReturnNew(value2, AngleUnit.DEGREES,
                AngleUnit.RADIANS);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        final Angle result = a1.addAndReturnNew(new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = a1.addAndReturnNew(a2, AngleUnit.RADIANS);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(value2, AngleUnit.DEGREES);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(new BigDecimal(value2), AngleUnit.DEGREES);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.add(a2);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = new Angle(0.0, AngleUnit.RADIANS);
        a1.add(a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Angle.subtract(value1, AngleUnit.DEGREES,
                value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Angle.subtract(new BigDecimal(value1), AngleUnit.DEGREES,
                new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.subtract(a1, a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = Angle.subtractAndReturnNew(a1, a2, AngleUnit.RADIANS);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        final Angle result = a1.subtractAndReturnNew(value2, AngleUnit.DEGREES,
                AngleUnit.RADIANS);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        final Angle result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = a1.subtractAndReturnNew(a2, AngleUnit.RADIANS);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(value2, AngleUnit.DEGREES);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(new BigDecimal(value2), AngleUnit.DEGREES);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.subtract(a2);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Angle a1 = new Angle(value1, AngleUnit.DEGREES);
        final Angle a2 = new Angle(value2, AngleUnit.DEGREES);

        final Angle result = new Angle(0.0, AngleUnit.RADIANS);
        a1.subtract(a2, result);

        // check
        assertEquals(a1.getUnit(), AngleUnit.DEGREES);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(a2.getUnit(), AngleUnit.DEGREES);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), AngleUnit.RADIANS);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }
}
