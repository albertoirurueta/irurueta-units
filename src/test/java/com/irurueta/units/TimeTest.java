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

class TimeTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var t = new Time();

        // check
        assertNull(t.getValue());
        assertNull(t.getUnit());

        // test constructor with value and unit
        t = new Time(332, TimeUnit.SECOND);

        // check
        assertEquals(332, t.getValue());
        assertEquals(TimeUnit.SECOND, t.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Time(null, TimeUnit.SECOND));
        assertThrows(IllegalArgumentException.class, () -> new Time(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var t1 = new Time(value, TimeUnit.SECOND);
        final var t2 = new Time(value, TimeUnit.SECOND);
        final var t3 = new Time(value + 1.0, TimeUnit.SECOND);
        final var t4 = new Time(value, TimeUnit.MILLISECOND);

        //noinspection EqualsWithItself
        assertEquals(t1, t1);
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertNotEquals(t1, t4);

        assertNotEquals(null, t1);
        assertNotEquals(t1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var t1 = new Time(value, TimeUnit.SECOND);
        final var t2 = new Time(value, TimeUnit.SECOND);
        final var t3 = new Time(value + 1.0, TimeUnit.SECOND);
        final var t4 = new Time(value, TimeUnit.MILLISECOND);

        assertEquals(t1.hashCode(), t1.hashCode());
        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1.hashCode(), t3.hashCode());
        assertNotEquals(t1.hashCode(), t4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var t1 = new Time(value, TimeUnit.SECOND);
        final var t2 = new Time(value, TimeUnit.SECOND);
        final var t3 = new Time(value + 0.5 * ERROR, TimeUnit.SECOND);
        final var t4 = new Time(value, TimeUnit.MILLISECOND);
        final var t5 = new Time(value * 1000.0, TimeUnit.MILLISECOND);

        assertTrue(t1.equals(t1, 0.0));
        assertTrue(t1.equals(t2, 0.0));
        assertFalse(t1.equals(t3, 0.0));
        assertTrue(t1.equals(t3, ERROR));
        assertFalse(t1.equals(t4, ERROR));
        assertTrue(t1.equals(t5, ERROR));

        assertFalse(t1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var t = new Time(1, TimeUnit.SECOND);

        // check
        assertEquals(1, t.getValue());

        // set new value
        t.setValue(2.5);

        // check
        assertEquals(2.5, t.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> t.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var t = new Time(1, TimeUnit.SECOND);

        // check
        assertEquals(TimeUnit.SECOND, t.getUnit());

        // set new value
        t.setUnit(TimeUnit.DAY);

        // check
        assertEquals(TimeUnit.DAY, t.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> t.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Time.add(value1, TimeUnit.SECOND, value2, TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Time.add(new BigDecimal(value1), TimeUnit.SECOND, new BigDecimal(value2), TimeUnit.SECOND,
                TimeUnit.MILLISECOND);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = new Time(0.0, TimeUnit.MILLISECOND);
        Time.add(t1, t2, result);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = Time.addAndReturnNew(t1, t2, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        final var result = t1.addAndReturnNew(value2, TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        final var result = t1.addAndReturnNew(new BigDecimal(value2), TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = t1.addAndReturnNew(t2, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        t1.add(value2, TimeUnit.SECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        t1.add(new BigDecimal(value2), TimeUnit.SECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        t1.add(t2);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 + value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = new Time(0.0, TimeUnit.MILLISECOND);
        t1.add(t2, result);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Time.subtract(value1, TimeUnit.SECOND, value2, TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Time.subtract(new BigDecimal(value1), TimeUnit.SECOND, new BigDecimal(value2),
                TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = new Time(0.0, TimeUnit.MILLISECOND);
        Time.subtract(t1, t2, result);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = Time.subtractAndReturnNew(t1, t2, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        final var result = t1.subtractAndReturnNew(value2, TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        final var result = t1.subtractAndReturnNew(new BigDecimal(value2), TimeUnit.SECOND, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = t1.subtractAndReturnNew(t2, TimeUnit.MILLISECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        t1.subtract(value2, TimeUnit.SECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);

        t1.subtract(new BigDecimal(value2), TimeUnit.SECOND);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        t1.subtract(t2);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1 - value2, t1.getValue().doubleValue(), ERROR);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(t2.getValue().doubleValue(), value2, 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var t1 = new Time(value1, TimeUnit.SECOND);
        final var t2 = new Time(value2, TimeUnit.SECOND);

        final var result = new Time(0.0, TimeUnit.MILLISECOND);
        t1.subtract(t2, result);

        // check
        assertEquals(TimeUnit.SECOND, t1.getUnit());
        assertEquals(value1, t1.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.SECOND, t2.getUnit());
        assertEquals(value2, t2.getValue().doubleValue(), 0.0);

        assertEquals(TimeUnit.MILLISECOND, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var t1 = new Time(value, TimeUnit.SECOND);

        final var bytes = SerializationHelper.serialize(t1);
        final var t2 = SerializationHelper.deserialize(bytes);

        assertEquals(t1, t2);
        assertNotSame(t1, t2);
    }
}
