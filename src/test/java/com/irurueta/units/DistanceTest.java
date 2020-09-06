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

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class DistanceTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Distance d = new Distance();

        // check
        assertNull(d.getValue());
        assertNull(d.getUnit());

        // test constructor with value and unit
        d = new Distance(323, DistanceUnit.METER);

        // check
        assertEquals(d.getValue(), 323);
        assertEquals(d.getUnit(), DistanceUnit.METER);

        // force IllegalArgumentException
        d = null;
        try {
            d = new Distance(null, DistanceUnit.METER);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            d = new Distance(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        assertNull(d);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Distance d1 = new Distance(value, DistanceUnit.METER);
        final Distance d2 = new Distance(value, DistanceUnit.METER);
        final Distance d3 = new Distance(value + 1.0, DistanceUnit.METER);
        final Distance d4 = new Distance(value, DistanceUnit.CENTIMETER);

        assertEquals(d1, d1);
        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
        assertNotEquals(d1, d4);

        assertNotEquals(null, d1);
        assertNotEquals(d1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Distance d1 = new Distance(value, DistanceUnit.METER);
        final Distance d2 = new Distance(value, DistanceUnit.METER);
        final Distance d3 = new Distance(value + 1.0, DistanceUnit.METER);
        final Distance d4 = new Distance(value, DistanceUnit.CENTIMETER);

        assertEquals(d1.hashCode(), d1.hashCode());
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1.hashCode(), d3.hashCode());
        assertNotEquals(d1.hashCode(), d4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Distance d1 = new Distance(value, DistanceUnit.METER);
        final Distance d2 = new Distance(value, DistanceUnit.METER);
        final Distance d3 = new Distance(value + 0.5 * ERROR, DistanceUnit.METER);
        final Distance d4 = new Distance(value, DistanceUnit.CENTIMETER);
        final Distance d5 = new Distance(value * 100.0, DistanceUnit.CENTIMETER);

        assertTrue(d1.equals(d1, 0.0));
        assertTrue(d1.equals(d2, 0.0));
        assertFalse(d1.equals(d3, 0.0));
        assertTrue(d1.equals(d3, ERROR));
        assertFalse(d1.equals(d4, ERROR));
        assertTrue(d1.equals(d5, ERROR));

        assertFalse(d1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final Distance d = new Distance(1, DistanceUnit.METER);

        // check
        assertEquals(d.getValue(), 1);

        // set new value
        d.setValue(2.5);

        // check
        assertEquals(d.getValue(), 2.5);

        // force IllegalArgumentException
        try {
            d.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Distance d = new Distance(1, DistanceUnit.METER);

        // check
        assertEquals(d.getUnit(), DistanceUnit.METER);

        // set new value
        d.setUnit(DistanceUnit.INCH);

        // check
        assertEquals(d.getUnit(), DistanceUnit.INCH);

        // force IllegalArgumentException
        try {
            d.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Distance.add(value1, DistanceUnit.METER,
                value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 + value2) * 100.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Distance.add(new BigDecimal(value1), DistanceUnit.METER,
                new BigDecimal(value2), DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 + value2) * 100.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = new Distance(0.0, DistanceUnit.CENTIMETER);
        Distance.add(d1, d2, result);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = Distance.addAndReturnNew(d1, d2,
                DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        final Distance result = d1.addAndReturnNew(value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        final Distance result = d1.addAndReturnNew(new BigDecimal(value2),
                DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = d1.addAndReturnNew(d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        d1.add(value2, DistanceUnit.METER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        d1.add(new BigDecimal(value2), DistanceUnit.METER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        d1.add(d2);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = new Distance(0.0, DistanceUnit.CENTIMETER);
        d1.add(d2, result);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 + value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Distance.subtract(value1, DistanceUnit.METER,
                value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 - value2) * 100.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Distance.subtract(new BigDecimal(value1), DistanceUnit.METER,
                new BigDecimal(value2), DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 - value2) * 100.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = new Distance(0.0, DistanceUnit.CENTIMETER);
        Distance.subtract(d1, d2, result);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = Distance.subtractAndReturnNew(d1, d2,
                DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        final Distance result = d1.subtractAndReturnNew(value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        final Distance result = d1.subtractAndReturnNew(new BigDecimal(value2),
                DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = d1.subtractAndReturnNew(d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        d1.subtract(value2, DistanceUnit.METER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);

        d1.subtract(new BigDecimal(value2), DistanceUnit.METER);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        d1.subtract(d2);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Distance d1 = new Distance(value1, DistanceUnit.METER);
        final Distance d2 = new Distance(value2, DistanceUnit.METER);

        final Distance result = new Distance(0.0, DistanceUnit.CENTIMETER);
        d1.subtract(d2, result);

        // check
        assertEquals(d1.getUnit(), DistanceUnit.METER);
        assertEquals(d1.getValue().doubleValue(), value1, 0.0);

        assertEquals(d2.getUnit(), DistanceUnit.METER);
        assertEquals(d2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), DistanceUnit.CENTIMETER);
        assertEquals((value1 - value2) * 100.0,
                result.getValue().doubleValue(), ERROR);
    }
}
