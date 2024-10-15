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

class SurfaceTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var s = new Surface();

        // check
        assertNull(s.getValue());
        assertNull(s.getUnit());

        // test constructor with value and unit
        s = new Surface(323, SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(323, s.getValue());
        assertEquals(SurfaceUnit.SQUARE_METER, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Surface(null, SurfaceUnit.SQUARE_METER));
        assertThrows(IllegalArgumentException.class, () -> new Surface(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var s1 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s3 = new Surface(value + 1.0, SurfaceUnit.SQUARE_METER);
        final var s4 = new Surface(value, SurfaceUnit.SQUARE_CENTIMETER);

        //noinspection EqualsWithItself
        assertEquals(s1, s1);
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);

        assertNotEquals(null, s1);
        assertNotEquals(s1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var s1 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s3 = new Surface(value + 1.0, SurfaceUnit.SQUARE_METER);
        final var s4 = new Surface(value, SurfaceUnit.SQUARE_CENTIMETER);

        assertEquals(s1.hashCode(), s1.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var s1 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value, SurfaceUnit.SQUARE_METER);
        final var s3 = new Surface(value + 0.5 * ERROR, SurfaceUnit.SQUARE_METER);
        final var s4 = new Surface(value, SurfaceUnit.SQUARE_CENTIMETER);
        final var s5 = new Surface(value * 10000.0, SurfaceUnit.SQUARE_CENTIMETER);

        assertTrue(s1.equals(s1, 0.0));
        assertTrue(s1.equals(s2, 0.0));
        assertFalse(s1.equals(s3, 0.0));
        assertTrue(s1.equals(s3, ERROR));
        assertFalse(s1.equals(s4, ERROR));
        assertTrue(s1.equals(s5, ERROR));

        assertFalse(s1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var s = new Surface(1, SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(1, s.getValue());

        // set new value
        s.setValue(2.5);

        // check
        assertEquals(2.5, s.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> s.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var s = new Surface(1, SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s.getUnit());

        // set new value
        s.setUnit(SurfaceUnit.SQUARE_YARD);

        // check
        assertEquals(SurfaceUnit.SQUARE_YARD, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> s.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Surface.add(value1, SurfaceUnit.SQUARE_METER, value2, SurfaceUnit.SQUARE_METER,
                SurfaceUnit.HECTARE);

        // check
        assertEquals((value1 + value2) * 1e-4, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Surface.add(new BigDecimal(value1), SurfaceUnit.SQUARE_METER, new BigDecimal(value2),
                SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE);

        // check
        assertEquals((value1 + value2) * 1e-4, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = new Surface(0.0, SurfaceUnit.HECTARE);
        Surface.add(s1, s2, result);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = Surface.addAndReturnNew(s1, s2, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        final var result = s1.addAndReturnNew(value2, SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        final var result = s1.addAndReturnNew(new BigDecimal(value2), SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = s1.addAndReturnNew(s2, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        s1.add(value2, SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        s1.add(new BigDecimal(value2), SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        s1.add(s2);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = new Surface(0.0, SurfaceUnit.HECTARE);
        s1.add(s2, result);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 + value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Surface.subtract(value1, SurfaceUnit.SQUARE_METER, value2, SurfaceUnit.SQUARE_METER,
                SurfaceUnit.HECTARE);

        // check
        assertEquals((value1 - value2) * 1e-4, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Surface.subtract(new BigDecimal(value1), SurfaceUnit.SQUARE_METER, new BigDecimal(value2),
                SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE);

        // check
        assertEquals((value1 - value2) * 1e-4, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = new Surface(0.0, SurfaceUnit.HECTARE);
        Surface.subtract(s1, s2, result);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = Surface.subtractAndReturnNew(s1, s2, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        final var result = s1.subtractAndReturnNew(value2, SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        final var result = s1.subtractAndReturnNew(new BigDecimal(value2), SurfaceUnit.SQUARE_METER,
                SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = s1.subtractAndReturnNew(s2, SurfaceUnit.HECTARE);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        s1.subtract(value2, SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);

        s1.subtract(new BigDecimal(value2), SurfaceUnit.SQUARE_METER);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        s1.subtract(s2);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Surface(value1, SurfaceUnit.SQUARE_METER);
        final var s2 = new Surface(value2, SurfaceUnit.SQUARE_METER);

        final var result = new Surface(0.0, SurfaceUnit.HECTARE);
        s1.subtract(s2, result);

        // check
        assertEquals(SurfaceUnit.SQUARE_METER, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.SQUARE_METER, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(SurfaceUnit.HECTARE, result.getUnit());
        assertEquals((value1 - value2) * 1e-4, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var s1 = new Surface(value, SurfaceUnit.SQUARE_METER);

        final var bytes = SerializationHelper.serialize(s1);
        final var s2 = SerializationHelper.deserialize(bytes);

        assertEquals(s1, s2);
        assertNotSame(s1, s2);
    }
}
