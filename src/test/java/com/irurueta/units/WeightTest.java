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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class WeightTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Weight w = new Weight();

        // check
        assertNull(w.getValue());
        assertNull(w.getUnit());

        // test constructor with value and unit
        w = new Weight(123, WeightUnit.GRAM);

        // check
        assertEquals(w.getValue(), 123);
        assertEquals(w.getUnit(), WeightUnit.GRAM);

        // Force IllegalArgumentException
        w = null;
        try {
            w = new Weight(null, WeightUnit.GRAM);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            w = new Weight(123, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(w);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Weight w1 = new Weight(value, WeightUnit.GRAM);
        final Weight w2 = new Weight(value, WeightUnit.GRAM);
        final Weight w3 = new Weight(value + 1.0, WeightUnit.GRAM);
        final Weight w4 = new Weight(value, WeightUnit.KILOGRAM);

        assertEquals(w1, w1);
        assertEquals(w1, w2);
        assertNotEquals(w1, w3);
        assertNotEquals(w1, w4);

        assertNotEquals(null, w1);
        assertNotEquals(w1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Weight w1 = new Weight(value, WeightUnit.GRAM);
        final Weight w2 = new Weight(value, WeightUnit.GRAM);
        final Weight w3 = new Weight(value + 1.0, WeightUnit.GRAM);
        final Weight w4 = new Weight(value, WeightUnit.KILOGRAM);

        assertEquals(w1.hashCode(), w1.hashCode());
        assertEquals(w1.hashCode(), w2.hashCode());
        assertNotEquals(w1.hashCode(), w3.hashCode());
        assertNotEquals(w1.hashCode(), w4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Weight w1 = new Weight(value, WeightUnit.GRAM);
        final Weight w2 = new Weight(value, WeightUnit.GRAM);
        final Weight w3 = new Weight(value + 0.5 * ERROR, WeightUnit.GRAM);
        final Weight w4 = new Weight(value, WeightUnit.MILLIGRAM);
        final Weight w5 = new Weight(value * 1000.0, WeightUnit.MILLIGRAM);

        assertTrue(w1.equals(w1, 0.0));
        assertTrue(w1.equals(w2, 0.0));
        assertFalse(w1.equals(w3, 0.0));
        assertTrue(w1.equals(w3, ERROR));
        assertFalse(w1.equals(w4, ERROR));
        assertTrue(w1.equals(w5, ERROR));

        assertFalse(w1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final Weight w = new Weight(1.0, WeightUnit.GRAM);

        // check
        assertEquals(w.getValue(), 1.0);

        // set new value
        w.setValue(2.5);

        // check
        assertEquals(w.getValue(), 2.5);

        // force IllegalArgumentException
        try {
            w.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Weight w = new Weight(1.0, WeightUnit.GRAM);

        // check
        assertEquals(w.getUnit(), WeightUnit.GRAM);

        // set new value
        w.setUnit(WeightUnit.KILOGRAM);

        // check
        assertEquals(w.getUnit(), WeightUnit.KILOGRAM);

        // force IllegalArgumentException
        try {
            w.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Weight.add(value1, WeightUnit.GRAM,
                value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Weight.add(new BigDecimal(value1), WeightUnit.GRAM,
                new BigDecimal(value2), WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = new Weight(0.0, WeightUnit.MILLIGRAM);
        Weight.add(w1, w2, result);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = Weight.addAndReturnNew(w1, w2,
                WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        final Weight result = w1.addAndReturnNew(value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        final Weight result = w1.addAndReturnNew(new BigDecimal(value2),
                WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = w1.addAndReturnNew(w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        w1.add(value2, WeightUnit.GRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        w1.add(new BigDecimal(value2), WeightUnit.GRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 + value2, ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        w1.add(w2);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 + value2, ERROR);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = new Weight(0.0, WeightUnit.MILLIGRAM);
        w1.add(w2, result);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Weight.subtract(value1, WeightUnit.GRAM,
                value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Weight.subtract(new BigDecimal(value1), WeightUnit.GRAM,
                new BigDecimal(value2), WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = new Weight(0.0, WeightUnit.MILLIGRAM);
        Weight.subtract(w1, w2, result);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = Weight.subtractAndReturnNew(w1, w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        final Weight result = w1.subtractAndReturnNew(value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        final Weight result = w1.subtractAndReturnNew(new BigDecimal(value2),
                WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = w1.subtractAndReturnNew(w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        w1.subtract(value2, WeightUnit.GRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 - value2, ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);

        w1.subtract(new BigDecimal(value2), WeightUnit.GRAM);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 - value2, ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        w1.subtract(w2);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1 - value2, ERROR);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Weight w1 = new Weight(value1, WeightUnit.GRAM);
        final Weight w2 = new Weight(value2, WeightUnit.GRAM);

        final Weight result = new Weight(0.0, WeightUnit.MILLIGRAM);
        w1.subtract(w2, result);

        // check
        assertEquals(w1.getUnit(), WeightUnit.GRAM);
        assertEquals(w1.getValue().doubleValue(), value1, 0.0);

        assertEquals(w2.getUnit(), WeightUnit.GRAM);
        assertEquals(w2.getValue().doubleValue(), value2, 0.0);

        assertEquals(result.getUnit(), WeightUnit.MILLIGRAM);
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final double value = new Random().nextDouble();
        final Weight w1 = new Weight(value, WeightUnit.GRAM);

        final byte[] bytes = SerializationHelper.serialize(w1);
        final Weight w2 = SerializationHelper.deserialize(bytes);

        assertEquals(w1, w2);
        assertNotSame(w1, w2);
    }
}
