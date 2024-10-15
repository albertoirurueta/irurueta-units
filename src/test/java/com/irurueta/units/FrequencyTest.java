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

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FrequencyTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var f = new Frequency();

        // check
        assertNull(f.getValue());
        assertNull(f.getUnit());

        // test constructor with value and unit
        f = new Frequency(1000, FrequencyUnit.HERTZ);

        // check
        assertEquals(1000, f.getValue());
        assertEquals(FrequencyUnit.HERTZ, f.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Frequency(null, FrequencyUnit.GIGAHERTZ));
        assertThrows(IllegalArgumentException.class, () -> new Frequency(1000, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var f1 = new Frequency(value, FrequencyUnit.HERTZ);
        final var f2 = new Frequency(value, FrequencyUnit.HERTZ);
        final var f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        final var f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);

        assertNotEquals(null, f1);
        assertNotEquals(f1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var f1 = new Frequency(value, FrequencyUnit.HERTZ);
        final var f2 = new Frequency(value, FrequencyUnit.HERTZ);
        final var f3 = new Frequency(value + 1.0, FrequencyUnit.HERTZ);
        final var f4 = new Frequency(value, FrequencyUnit.KILOHERTZ);

        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
        assertNotEquals(f1.hashCode(), f4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var f1 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value, FrequencyUnit.KILOHERTZ);
        final var f3 = new Frequency(value + 0.5 * ERROR, FrequencyUnit.KILOHERTZ);
        final var f4 = new Frequency(value, FrequencyUnit.HERTZ);
        final var f5 = new Frequency(value * 1000.0, FrequencyUnit.HERTZ);

        assertTrue(f1.equals(f1, 0.0));
        assertTrue(f1.equals(f2, 0.0));
        assertFalse(f1.equals(f3, 0.0));
        assertTrue(f1.equals(f3, ERROR));
        assertFalse(f1.equals(f4, ERROR));
        assertTrue(f1.equals(f5, ERROR));

        assertFalse(f1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var f = new Frequency(1, FrequencyUnit.HERTZ);

        // check
        assertEquals(1, f.getValue());

        // set new value
        f.setValue(2.5);

        // check
        assertEquals(2.5, f.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> f.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var f = new Frequency(1, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f.getUnit());

        // set new value
        f.setUnit(FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> f.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Frequency.add(value1, FrequencyUnit.KILOHERTZ, value2, FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Frequency.add(new BigDecimal(value1), FrequencyUnit.KILOHERTZ, new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.add(f1, f2, result);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = Frequency.addAndReturnNew(f1, f2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final var result = f1.addAndReturnNew(value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final var result = f1.addAndReturnNew(new BigDecimal(value2), FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = f1.addAndReturnNew(f2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(value2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.add(new BigDecimal(value2), FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.add(f2);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);

        assertEquals(FrequencyUnit.HERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.add(f2, result);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Frequency.subtract(value1, FrequencyUnit.KILOHERTZ, value2, FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Frequency.subtract(new BigDecimal(value1), FrequencyUnit.KILOHERTZ, new BigDecimal(value2),
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = new Frequency(0.0, FrequencyUnit.HERTZ);
        Frequency.subtract(f1, f2, result);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = Frequency.subtractAndReturnNew(f1, f2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final var result = f1.subtractAndReturnNew(value2, FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);

        final var result = f1.subtractAndReturnNew(new BigDecimal(value2), FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = f1.subtractAndReturnNew(f2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(value2, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);

        f1.subtract(new BigDecimal(value2), FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.HERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.HERTZ);

        f1.subtract(f2);

        // check
        assertEquals(FrequencyUnit.HERTZ, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);

        assertEquals(FrequencyUnit.HERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Frequency(value1, FrequencyUnit.KILOHERTZ);
        final var f2 = new Frequency(value2, FrequencyUnit.KILOHERTZ);

        final var result = new Frequency(0.0, FrequencyUnit.HERTZ);
        f1.subtract(f2, result);

        // check
        assertEquals(FrequencyUnit.KILOHERTZ, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.KILOHERTZ, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(FrequencyUnit.HERTZ, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var f1 = new Frequency(value, FrequencyUnit.HERTZ);

        final var bytes = SerializationHelper.serialize(f1);
        final var f2 = SerializationHelper.deserialize(bytes);

        assertEquals(f1, f2);
        assertNotSame(f1, f2);
    }
}
