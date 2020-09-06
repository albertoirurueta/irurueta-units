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

    @Test
    public void testConstructor() {
        // test empty constructor
        AngularAcceleration a = new AngularAcceleration();

        // check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        // test constructor with value and unit
        a = new AngularAcceleration(323,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a.getValue(), 323);
        assertEquals(a.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // force IllegalArgumentException
        a = null;
        try {
            a = new AngularAcceleration(null,
                    AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            a = new AngularAcceleration(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(a);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a3 = new AngularAcceleration(value + 1.0,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

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
        final AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a3 = new AngularAcceleration(value + 1.0,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final AngularAcceleration a1 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a3 = new AngularAcceleration(value + 0.5 * ERROR,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a4 = new AngularAcceleration(value,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        final AngularAcceleration a5 = new AngularAcceleration(value * Math.PI / 180.0,
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
        final AngularAcceleration a = new AngularAcceleration(1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

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
        final AngularAcceleration a = new AngularAcceleration(1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // set new value
        a.setUnit(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(a.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

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

        final double result = AngularAcceleration.add(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = AngularAcceleration.add(new BigDecimal(value1),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.add(a1, a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = AngularAcceleration.addAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.addAndReturnNew(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.addAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 + value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.addAndReturnNew(a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(a2);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 + value2, ERROR);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.add(a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = AngularAcceleration.subtract(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = AngularAcceleration.subtract(new BigDecimal(value1),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.subtract(a1, a2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = AngularAcceleration.subtractAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.subtractAndReturnNew(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(),
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = a1.subtractAndReturnNew(a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(a2);

        // check
        assertEquals(a1.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(a2.getUnit(), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final AngularAcceleration a1 = new AngularAcceleration(value1,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final AngularAcceleration a2 = new AngularAcceleration(value2,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final AngularAcceleration result = new AngularAcceleration(0.0,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.subtract(a2, result);

        // check
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
