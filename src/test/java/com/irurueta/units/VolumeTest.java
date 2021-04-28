/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
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

public class VolumeTest {

    public static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Volume v = new Volume();

        // check
        assertNull(v.getValue());
        assertNull(v.getUnit());

        // test constructor with value and unit
        v = new Volume(123, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(v.getValue(), 123);
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_METER);

        // Force IllegalArgumentException
        v = null;
        try {
            v = new Volume(null, VolumeUnit.CUBIC_METER);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            v = new Volume(123, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(v);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Volume v1 = new Volume(value, VolumeUnit.LITER);
        final Volume v2 = new Volume(value, VolumeUnit.LITER);
        final Volume v3 = new Volume(value + 1.0, VolumeUnit.LITER);
        final Volume v4 = new Volume(value, VolumeUnit.CUBIC_METER);

        assertEquals(v1, v1);
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);

        assertNotEquals(null, v1);
        assertNotEquals(v1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Volume v1 = new Volume(value, VolumeUnit.LITER);
        final Volume v2 = new Volume(value, VolumeUnit.LITER);
        final Volume v3 = new Volume(value + 1.0, VolumeUnit.LITER);
        final Volume v4 = new Volume(value, VolumeUnit.CUBIC_METER);

        assertEquals(v1.hashCode(), v1.hashCode());
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
        assertNotEquals(v1.hashCode(), v4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Volume v1 = new Volume(value, VolumeUnit.LITER);
        final Volume v2 = new Volume(value, VolumeUnit.LITER);
        final Volume v3 = new Volume(value + 0.5 * ERROR, VolumeUnit.LITER);
        final Volume v4 = new Volume(value, VolumeUnit.CUBIC_CENTIMETER);
        final Volume v5 = new Volume(value * 1000.0, VolumeUnit.CUBIC_CENTIMETER);

        assertTrue(v1.equals(v1, 0.0));
        assertTrue(v1.equals(v2, 0.0));
        assertFalse(v1.equals(v3, 0.0));
        assertTrue(v1.equals(v3, ERROR));
        assertFalse(v1.equals(v4, ERROR));
        assertTrue(v1.equals(v5, ERROR));

        assertFalse(v1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final Volume v = new Volume(1.0, VolumeUnit.LITER);

        // check
        assertEquals(v.getValue(), 1.0);

        // set new value
        v.setValue(2.5);

        // check
        assertEquals(v.getValue(), 2.5);

        // Force IllegalArgumentException
        try {
            v.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Volume v = new Volume(1.0, VolumeUnit.LITER);

        // check
        assertEquals(v.getUnit(), VolumeUnit.LITER);

        // set new value
        v.setUnit(VolumeUnit.CUBIC_METER);

        // check
        assertEquals(v.getUnit(), VolumeUnit.CUBIC_METER);

        // force IllegalArgumentException
        try {
            v.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Volume.add(value1, VolumeUnit.CUBIC_METER,
                value2, VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Volume.add(new BigDecimal(value1), VolumeUnit.CUBIC_METER,
                new BigDecimal(value2), VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = new Volume(0.0, VolumeUnit.LITER);
        Volume.add(v1, v2, result);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = Volume.addAndReturnNew(v1, v2, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final Volume result = v1.addAndReturnNew(value2, VolumeUnit.CUBIC_METER,
                VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final Volume result = v1.addAndReturnNew(new BigDecimal(value2),
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = v1.addAndReturnNew(v2, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        v1.add(value2, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.LITER);

        v1.add(new BigDecimal(value2), VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.LITER);
        assertEquals(v1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        v1.add(v2);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1 + value2, ERROR);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = new Volume(0.0, VolumeUnit.LITER);
        v1.add(v2, result);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Volume.subtract(value1, VolumeUnit.CUBIC_METER,
                value2, VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Volume.subtract(new BigDecimal(value1), VolumeUnit.CUBIC_METER,
                new BigDecimal(value2), VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = new Volume(0.0, VolumeUnit.LITER);
        Volume.subtract(v1, v2, result);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = Volume.subtractAndReturnNew(v1, v2, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final Volume result = v1.subtractAndReturnNew(value2, VolumeUnit.CUBIC_METER,
                VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final Volume result = v1.subtractAndReturnNew(new BigDecimal(value2),
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = v1.subtractAndReturnNew(v2, VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        v1.subtract(value2, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1 - value2, ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.LITER);

        v1.subtract(new BigDecimal(value2), VolumeUnit.LITER);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.LITER);
        assertEquals(v1.getValue().doubleValue(), value1 - value2, ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        v1.subtract(v2);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1 - value2, ERROR);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Volume v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final Volume v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final Volume result = new Volume(0.0, VolumeUnit.LITER);
        v1.subtract(v2, result);

        // check
        assertEquals(v1.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v1.getValue().doubleValue(), value1, 0.0);

        assertEquals(v2.getUnit(), VolumeUnit.CUBIC_METER);
        assertEquals(v2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), VolumeUnit.LITER);
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

}
