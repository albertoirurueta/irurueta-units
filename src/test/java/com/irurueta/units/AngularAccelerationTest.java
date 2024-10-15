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

class AngularAccelerationTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var a = new AngularAcceleration();

        // check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        // test constructor with value and unit
        a = new AngularAcceleration(323, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(323, a.getValue());
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new AngularAcceleration(null,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND));
        assertThrows(IllegalArgumentException.class, () -> new AngularAcceleration(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var a1 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a3 = new AngularAcceleration(value + 1.0, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a4 = new AngularAcceleration(value, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);

        assertNotEquals(null, a1);
        assertNotEquals(a1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var a1 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a3 = new AngularAcceleration(value + 1.0, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a4 = new AngularAcceleration(value, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var a1 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a3 = new AngularAcceleration(value + 0.5 * ERROR,
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a4 = new AngularAcceleration(value, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        final var a5 = new AngularAcceleration(value * Math.PI / 180.0,
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
    void testGetSetValue() {
        final var a = new AngularAcceleration(1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(1, a.getValue());

        // set new value
        a.setValue(2.5);

        // check
        assertEquals(2.5, a.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> a.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var a = new AngularAcceleration(1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a.getUnit());

        // set new value
        a.setUnit(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, a.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> a.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularAcceleration.add(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularAcceleration.add(
                new BigDecimal(value1), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = new AngularAcceleration(0.0, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.add(a1, a2, result);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = AngularAcceleration.addAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.addAndReturnNew(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.addAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.addAndReturnNew(a2, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(new BigDecimal(value2), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.add(a2);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = new AngularAcceleration(0.0, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.add(a2, result);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularAcceleration.subtract(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularAcceleration.subtract(
                new BigDecimal(value1), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                new BigDecimal(value2), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = new AngularAcceleration(0.0, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        AngularAcceleration.subtract(a1, a2, result);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = AngularAcceleration.subtractAndReturnNew(a1, a2,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.subtractAndReturnNew(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND,
                AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = a1.subtractAndReturnNew(a2, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(new BigDecimal(value2), AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        a1.subtract(a2);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new AngularAcceleration(value1, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);
        final var a2 = new AngularAcceleration(value2, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var result = new AngularAcceleration(0.0, AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND);
        a1.subtract(a2, result);

        // check
        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngularAccelerationUnit.RADIANS_PER_SQUARED_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var a1 = new AngularAcceleration(value, AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND);

        final var bytes = SerializationHelper.serialize(a1);
        final var a2 = SerializationHelper.deserialize(bytes);

        assertEquals(a1, a2);
        assertNotSame(a1, a2);
    }
}
