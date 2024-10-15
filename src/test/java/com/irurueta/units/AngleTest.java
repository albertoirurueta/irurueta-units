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

class AngleTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var a = new Angle();

        // check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        // test constructor with value and unit
        a = new Angle(323, AngleUnit.DEGREES);

        // check
        assertEquals(323, a.getValue());
        assertEquals(AngleUnit.DEGREES, a.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Angle(null, AngleUnit.DEGREES));
        assertThrows(IllegalArgumentException.class, () ->  new Angle(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var a1 = new Angle(value, AngleUnit.DEGREES);
        final var a2 = new Angle(value, AngleUnit.DEGREES);
        final var a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        final var a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);

        assertNotEquals(null, a1);
        assertNotEquals(a1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var a1 = new Angle(value, AngleUnit.DEGREES);
        final var a2 = new Angle(value, AngleUnit.DEGREES);
        final var a3 = new Angle(value + 1.0, AngleUnit.DEGREES);
        final var a4 = new Angle(value, AngleUnit.RADIANS);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var a1 = new Angle(value, AngleUnit.DEGREES);
        final var a2 = new Angle(value, AngleUnit.DEGREES);
        final var a3 = new Angle(value + 0.5 * ERROR, AngleUnit.DEGREES);
        final var a4 = new Angle(value, AngleUnit.RADIANS);
        final var a5 = new Angle(value * Math.PI / 180.0, AngleUnit.RADIANS);

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
        final var a = new Angle(1, AngleUnit.DEGREES);

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
        final var a = new Angle(1, AngleUnit.DEGREES);

        // check
        assertEquals(AngleUnit.DEGREES, a.getUnit());

        // set new value
        a.setUnit(AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.RADIANS, a.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> a.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Angle.add(value1, AngleUnit.DEGREES, value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Angle.add(new BigDecimal(value1), AngleUnit.DEGREES, new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.add(a1, a2, result);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(),0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = Angle.addAndReturnNew(a1, a2, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        final var result = a1.addAndReturnNew(value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        final var result = a1.addAndReturnNew(new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = a1.addAndReturnNew(a2, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(value2, AngleUnit.DEGREES);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.add(new BigDecimal(value2), AngleUnit.DEGREES);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.add(a2);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = new Angle(0.0, AngleUnit.RADIANS);
        a1.add(a2, result);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Angle.subtract(value1, AngleUnit.DEGREES, value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Angle.subtract(new BigDecimal(value1), AngleUnit.DEGREES, new BigDecimal(value2),
                AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = new Angle(0.0, AngleUnit.RADIANS);
        Angle.subtract(a1, a2, result);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = Angle.subtractAndReturnNew(a1, a2, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        final var result = a1.subtractAndReturnNew(value2, AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        final var result = a1.subtractAndReturnNew(new BigDecimal(value2), AngleUnit.DEGREES, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = a1.subtractAndReturnNew(a2, AngleUnit.RADIANS);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(value2, AngleUnit.DEGREES);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);

        a1.subtract(new BigDecimal(value2), AngleUnit.DEGREES);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        a1.subtract(a2);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var a1 = new Angle(value1, AngleUnit.DEGREES);
        final var a2 = new Angle(value2, AngleUnit.DEGREES);

        final var result = new Angle(0.0, AngleUnit.RADIANS);
        a1.subtract(a2, result);

        // check
        assertEquals(AngleUnit.DEGREES, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.DEGREES, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AngleUnit.RADIANS, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var a1 = new Angle(value, AngleUnit.DEGREES);

        final var bytes = SerializationHelper.serialize(a1);
        final var a2 = SerializationHelper.deserialize(bytes);

        assertEquals(a1, a2);
        assertNotSame(a1, a2);
    }
}
