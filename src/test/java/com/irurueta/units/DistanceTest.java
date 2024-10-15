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

class DistanceTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var d = new Distance();

        // check
        assertNull(d.getValue());
        assertNull(d.getUnit());

        // test constructor with value and unit
        d = new Distance(323, DistanceUnit.METER);

        // check
        assertEquals(323, d.getValue());
        assertEquals(DistanceUnit.METER, d.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Distance(null, DistanceUnit.METER));
        assertThrows(IllegalArgumentException.class, () -> new Distance(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var d1 = new Distance(value, DistanceUnit.METER);
        final var d2 = new Distance(value, DistanceUnit.METER);
        final var d3 = new Distance(value + 1.0, DistanceUnit.METER);
        final var d4 = new Distance(value, DistanceUnit.CENTIMETER);

        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
        assertNotEquals(d1, d4);

        assertNotEquals(null, d1);
        assertNotEquals(d1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var d1 = new Distance(value, DistanceUnit.METER);
        final var d2 = new Distance(value, DistanceUnit.METER);
        final var d3 = new Distance(value + 1.0, DistanceUnit.METER);
        final var d4 = new Distance(value, DistanceUnit.CENTIMETER);

        assertEquals(d1.hashCode(), d1.hashCode());
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1.hashCode(), d3.hashCode());
        assertNotEquals(d1.hashCode(), d4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var d1 = new Distance(value, DistanceUnit.METER);
        final var d2 = new Distance(value, DistanceUnit.METER);
        final var d3 = new Distance(value + 0.5 * ERROR, DistanceUnit.METER);
        final var d4 = new Distance(value, DistanceUnit.CENTIMETER);
        final var d5 = new Distance(value * 100.0, DistanceUnit.CENTIMETER);

        assertTrue(d1.equals(d1, 0.0));
        assertTrue(d1.equals(d2, 0.0));
        assertFalse(d1.equals(d3, 0.0));
        assertTrue(d1.equals(d3, ERROR));
        assertFalse(d1.equals(d4, ERROR));
        assertTrue(d1.equals(d5, ERROR));

        assertFalse(d1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var d = new Distance(1, DistanceUnit.METER);

        // check
        assertEquals(1, d.getValue());

        // set new value
        d.setValue(2.5);

        // check
        assertEquals(2.5, d.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> d.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var d = new Distance(1, DistanceUnit.METER);

        // check
        assertEquals(DistanceUnit.METER, d.getUnit());

        // set new value
        d.setUnit(DistanceUnit.INCH);

        // check
        assertEquals(DistanceUnit.INCH, d.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> d.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Distance.add(value1, DistanceUnit.METER, value2, DistanceUnit.METER,
                DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 + value2) * 100.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Distance.add(new BigDecimal(value1), DistanceUnit.METER, new BigDecimal(value2),
                DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 + value2) * 100.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = new Distance(0.0, DistanceUnit.CENTIMETER);
        Distance.add(d1, d2, result);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 + value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = Distance.addAndReturnNew(d1, d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 + value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        final var result = d1.addAndReturnNew(value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals(result.getValue().doubleValue(), (value1 + value2) * 100.0, ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        final var result = d1.addAndReturnNew(new BigDecimal(value2), DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 + value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = d1.addAndReturnNew(d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 + value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        d1.add(value2, DistanceUnit.METER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 + value2, d1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        d1.add(new BigDecimal(value2), DistanceUnit.METER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 + value2, d1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        d1.add(d2);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 + value2, d1.getValue().doubleValue(), ERROR);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = new Distance(0.0, DistanceUnit.CENTIMETER);
        d1.add(d2, result);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 + value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Distance.subtract(value1, DistanceUnit.METER, value2, DistanceUnit.METER,
                DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 - value2) * 100.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Distance.subtract(new BigDecimal(value1), DistanceUnit.METER, new BigDecimal(value2),
                DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals((value1 - value2) * 100.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = new Distance(0.0, DistanceUnit.CENTIMETER);
        Distance.subtract(d1, d2, result);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = Distance.subtractAndReturnNew(d1, d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        final var result = d1.subtractAndReturnNew(value2, DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        final var result = d1.subtractAndReturnNew(new BigDecimal(value2), DistanceUnit.METER, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = d1.subtractAndReturnNew(d2, DistanceUnit.CENTIMETER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        d1.subtract(value2, DistanceUnit.METER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 - value2, d1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);

        d1.subtract(new BigDecimal(value2), DistanceUnit.METER);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 - value2, d1.getValue().doubleValue(),  ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        d1.subtract(d2);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1 - value2, d1.getValue().doubleValue(), ERROR);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var d1 = new Distance(value1, DistanceUnit.METER);
        final var d2 = new Distance(value2, DistanceUnit.METER);

        final var result = new Distance(0.0, DistanceUnit.CENTIMETER);
        d1.subtract(d2, result);

        // check
        assertEquals(DistanceUnit.METER, d1.getUnit());
        assertEquals(value1, d1.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.METER, d2.getUnit());
        assertEquals(value2, d2.getValue().doubleValue(), 0.0);

        assertEquals(DistanceUnit.CENTIMETER, result.getUnit());
        assertEquals((value1 - value2) * 100.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var d1 = new Distance(value, DistanceUnit.METER);

        final var bytes = SerializationHelper.serialize(d1);
        final var d2 = SerializationHelper.deserialize(bytes);

        assertEquals(d1, d2);
        assertNotSame(d1, d2);
    }
}
