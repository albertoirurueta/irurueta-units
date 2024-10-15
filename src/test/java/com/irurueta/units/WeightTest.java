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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WeightTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var w = new Weight();

        // check
        assertNull(w.getValue());
        assertNull(w.getUnit());

        // test constructor with value and unit
        w = new Weight(123, WeightUnit.GRAM);

        // check
        assertEquals(123, w.getValue());
        assertEquals(WeightUnit.GRAM, w.getUnit());

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Weight(null, WeightUnit.GRAM));
        assertThrows(IllegalArgumentException.class, () -> new Weight(123, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var w1 = new Weight(value, WeightUnit.GRAM);
        final var w2 = new Weight(value, WeightUnit.GRAM);
        final var w3 = new Weight(value + 1.0, WeightUnit.GRAM);
        final var w4 = new Weight(value, WeightUnit.KILOGRAM);

        //noinspection EqualsWithItself
        assertEquals(w1, w1);
        assertEquals(w1, w2);
        assertNotEquals(w1, w3);
        assertNotEquals(w1, w4);

        assertNotEquals(null, w1);
        assertNotEquals(w1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var w1 = new Weight(value, WeightUnit.GRAM);
        final var w2 = new Weight(value, WeightUnit.GRAM);
        final var w3 = new Weight(value + 1.0, WeightUnit.GRAM);
        final var w4 = new Weight(value, WeightUnit.KILOGRAM);

        assertEquals(w1.hashCode(), w1.hashCode());
        assertEquals(w1.hashCode(), w2.hashCode());
        assertNotEquals(w1.hashCode(), w3.hashCode());
        assertNotEquals(w1.hashCode(), w4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var w1 = new Weight(value, WeightUnit.GRAM);
        final var w2 = new Weight(value, WeightUnit.GRAM);
        final var w3 = new Weight(value + 0.5 * ERROR, WeightUnit.GRAM);
        final var w4 = new Weight(value, WeightUnit.MILLIGRAM);
        final var w5 = new Weight(value * 1000.0, WeightUnit.MILLIGRAM);

        assertTrue(w1.equals(w1, 0.0));
        assertTrue(w1.equals(w2, 0.0));
        assertFalse(w1.equals(w3, 0.0));
        assertTrue(w1.equals(w3, ERROR));
        assertFalse(w1.equals(w4, ERROR));
        assertTrue(w1.equals(w5, ERROR));

        assertFalse(w1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var w = new Weight(1.0, WeightUnit.GRAM);

        // check
        assertEquals(1.0, w.getValue());

        // set new value
        w.setValue(2.5);

        // check
        assertEquals(2.5, w.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> w.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var w = new Weight(1.0, WeightUnit.GRAM);

        // check
        assertEquals(WeightUnit.GRAM, w.getUnit());

        // set new value
        w.setUnit(WeightUnit.KILOGRAM);

        // check
        assertEquals(WeightUnit.KILOGRAM, w.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> w.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Weight.add(value1, WeightUnit.GRAM, value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Weight.add(new BigDecimal(value1), WeightUnit.GRAM, new BigDecimal(value2), WeightUnit.GRAM,
                WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = new Weight(0.0, WeightUnit.MILLIGRAM);
        Weight.add(w1, w2, result);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = Weight.addAndReturnNew(w1, w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        final var result = w1.addAndReturnNew(value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        final var result = w1.addAndReturnNew(new BigDecimal(value2), WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = w1.addAndReturnNew(w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        w1.add(value2, WeightUnit.GRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 + value2, w1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        w1.add(new BigDecimal(value2), WeightUnit.GRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 + value2, w1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        w1.add(w2);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 + value2, w1.getValue().doubleValue(), ERROR);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = new Weight(0.0, WeightUnit.MILLIGRAM);
        w1.add(w2, result);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Weight.subtract(value1, WeightUnit.GRAM, value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Weight.subtract(new BigDecimal(value1), WeightUnit.GRAM, new BigDecimal(value2),
                WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = new Weight(0.0, WeightUnit.MILLIGRAM);
        Weight.subtract(w1, w2, result);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = Weight.subtractAndReturnNew(w1, w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        final var result = w1.subtractAndReturnNew(value2, WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        final var result = w1.subtractAndReturnNew(new BigDecimal(value2),
                WeightUnit.GRAM, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = w1.subtractAndReturnNew(w2, WeightUnit.MILLIGRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        w1.subtract(value2, WeightUnit.GRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 - value2, w1.getValue().doubleValue(),  ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);

        w1.subtract(new BigDecimal(value2), WeightUnit.GRAM);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 - value2, w1.getValue().doubleValue(),  ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        w1.subtract(w2);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1 - value2, w1.getValue().doubleValue(),  ERROR);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var w1 = new Weight(value1, WeightUnit.GRAM);
        final var w2 = new Weight(value2, WeightUnit.GRAM);

        final var result = new Weight(0.0, WeightUnit.MILLIGRAM);
        w1.subtract(w2, result);

        // check
        assertEquals(WeightUnit.GRAM, w1.getUnit());
        assertEquals(value1, w1.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.GRAM, w2.getUnit());
        assertEquals(value2, w2.getValue().doubleValue(), 0.0);

        assertEquals(WeightUnit.MILLIGRAM, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var w1 = new Weight(value, WeightUnit.GRAM);

        final var bytes = SerializationHelper.serialize(w1);
        final var w2 = SerializationHelper.deserialize(bytes);

        assertEquals(w1, w2);
        assertNotSame(w1, w2);
    }
}
