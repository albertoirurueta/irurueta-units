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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class FrequencyTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Frequency f = new Frequency();

        // check
        assertNull(f.getValue());
        assertNull(f.getUnit());

        // test constructor with value and unit
        f = new Frequency(1000, FrequencyUnit.HERTZ);

        // check
        assertEquals(f.getValue(), 1000);
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        // force IllegalArgumentException
        f = null;
        try {
            f = new Frequency(null, FrequencyUnit.GIGAHERTZ);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            f = new Frequency(1000, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(f);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Frequency f1 = new Frequency(value, FrequencyUnit.HERTZ);
        final Frequency f2 = new Frequency(value, FrequencyUnit.HERTZ);
        final Frequency f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        final Frequency f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1, f1);
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);

        assertNotEquals(null, f1);
        assertNotEquals(f1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Frequency f1 = new Frequency(value, FrequencyUnit.HERTZ);
        final Frequency f2 = new Frequency(value, FrequencyUnit.HERTZ);
        final Frequency f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        final Frequency f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
        assertNotEquals(f1.hashCode(), f4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Frequency f1 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        final Frequency f3 = new Frequency(value + 0.5 * ERROR,
                FrequencyUnit.KILOHERTZ);
        final Frequency f4 = new Frequency(value, FrequencyUnit.HERTZ);
        final Frequency f5 = new Frequency(value * 1000.0,
                FrequencyUnit.HERTZ);

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
        final Frequency f = new Frequency(1, FrequencyUnit.HERTZ);

        // check
        assertEquals(f.getValue(), 1);

        // set new value
        f.setValue(2.5);

        // check
        assertEquals(f.getValue(), 2.5);

        // force IllegalArgumentException
        try {
            f.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Frequency f = new Frequency(1, FrequencyUnit.HERTZ);

        // check
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        // set new value
        f.setUnit(FrequencyUnit.HERTZ);

        // check
        assertEquals(f.getUnit(), FrequencyUnit.HERTZ);

        // force IllegalArgumentException
        try {
            f.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Frequency.add(value1, FrequencyUnit.KILOHERTZ,
                value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Frequency.add(new BigDecimal(value1), FrequencyUnit.KILOHERTZ,
                new BigDecimal(value2), FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.add(f1, f2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = Frequency.addAndReturnNew(f1, f2, FrequencyUnit.HERTZ);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.addAndReturnNew(value2, FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.addAndReturnNew(new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.addAndReturnNew(f2, FrequencyUnit.HERTZ);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(value2, FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(new BigDecimal(value2), FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.add(f2);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 + value2,
                ERROR);

        assertEquals(f2.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.add(f2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Frequency.subtract(value1, FrequencyUnit.KILOHERTZ,
                value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Frequency.subtract(new BigDecimal(value1), FrequencyUnit.KILOHERTZ,
                new BigDecimal(value2), FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.subtract(f1, f2, result);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = Frequency.subtractAndReturnNew(f1, f2,
                FrequencyUnit.HERTZ);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.subtractAndReturnNew(value2,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.subtractAndReturnNew(new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = f1.subtractAndReturnNew(f2, FrequencyUnit.HERTZ);

        // check
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
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(value2, FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(new BigDecimal(value2), FrequencyUnit.HERTZ);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.subtract(f2);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f1.getValue().doubleValue(), value1 - value2,
                ERROR);

        assertEquals(f2.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Frequency f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final Frequency f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final Frequency result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.subtract(f2, result);

        // check
        assertEquals(f1.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f1.getValue().doubleValue(), value1, 0.0);

        assertEquals(f2.getUnit(), FrequencyUnit.KILOHERTZ);
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), FrequencyUnit.HERTZ);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final double value = new Random().nextDouble();
        final Frequency f1 = new Frequency(value, FrequencyUnit.HERTZ);

        final byte[] bytes = SerializationHelper.serialize(f1);
        final Frequency f2 = SerializationHelper.deserialize(bytes);

        assertEquals(f1, f2);
        assertNotSame(f1, f2);
    }
}
