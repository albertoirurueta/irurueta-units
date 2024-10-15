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

class VolumeTest {

    public static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var v = new Volume();

        // check
        assertNull(v.getValue());
        assertNull(v.getUnit());

        // test constructor with value and unit
        v = new Volume(123, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(123, v.getValue());
        assertEquals(VolumeUnit.CUBIC_METER, v.getUnit());

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Volume(null, VolumeUnit.CUBIC_METER));
        assertThrows(IllegalArgumentException.class, () -> new Volume(123, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var v1 = new Volume(value, VolumeUnit.LITER);
        final var v2 = new Volume(value, VolumeUnit.LITER);
        final var v3 = new Volume(value + 1.0, VolumeUnit.LITER);
        final var v4 = new Volume(value, VolumeUnit.CUBIC_METER);

        //noinspection EqualsWithItself
        assertEquals(v1, v1);
        assertEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);

        assertNotEquals(null, v1);
        assertNotEquals(v1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var v1 = new Volume(value, VolumeUnit.LITER);
        final var v2 = new Volume(value, VolumeUnit.LITER);
        final var v3 = new Volume(value + 1.0, VolumeUnit.LITER);
        final var v4 = new Volume(value, VolumeUnit.CUBIC_METER);

        assertEquals(v1.hashCode(), v1.hashCode());
        assertEquals(v1.hashCode(), v2.hashCode());
        assertNotEquals(v1.hashCode(), v3.hashCode());
        assertNotEquals(v1.hashCode(), v4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var v1 = new Volume(value, VolumeUnit.LITER);
        final var v2 = new Volume(value, VolumeUnit.LITER);
        final var v3 = new Volume(value + 0.5 * ERROR, VolumeUnit.LITER);
        final var v4 = new Volume(value, VolumeUnit.CUBIC_CENTIMETER);
        final var v5 = new Volume(value * 1000.0, VolumeUnit.CUBIC_CENTIMETER);

        assertTrue(v1.equals(v1, 0.0));
        assertTrue(v1.equals(v2, 0.0));
        assertFalse(v1.equals(v3, 0.0));
        assertTrue(v1.equals(v3, ERROR));
        assertFalse(v1.equals(v4, ERROR));
        assertTrue(v1.equals(v5, ERROR));

        assertFalse(v1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var v = new Volume(1.0, VolumeUnit.LITER);

        // check
        assertEquals(1.0, v.getValue());

        // set new value
        v.setValue(2.5);

        // check
        assertEquals(2.5, v.getValue());

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> v.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var v = new Volume(1.0, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.LITER, v.getUnit());

        // set new value
        v.setUnit(VolumeUnit.CUBIC_METER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> v.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Volume.add(value1, VolumeUnit.CUBIC_METER, value2, VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Volume.add(new BigDecimal(value1), VolumeUnit.CUBIC_METER, new BigDecimal(value2),
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = new Volume(0.0, VolumeUnit.LITER);
        Volume.add(v1, v2, result);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = Volume.addAndReturnNew(v1, v2, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(),0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final var result = v1.addAndReturnNew(value2, VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final var result = v1.addAndReturnNew(new BigDecimal(value2), VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = v1.addAndReturnNew(v2, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        v1.add(value2, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1 + value2, v1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.LITER);

        v1.add(new BigDecimal(value2), VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.LITER, v1.getUnit());
        assertEquals(value1 + value2, v1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        v1.add(v2);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1 + value2, v1.getValue().doubleValue(), ERROR);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = new Volume(0.0, VolumeUnit.LITER);
        v1.add(v2, result);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Volume.subtract(value1, VolumeUnit.CUBIC_METER, value2, VolumeUnit.CUBIC_METER,
                VolumeUnit.LITER);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Volume.subtract(new BigDecimal(value1), VolumeUnit.CUBIC_METER, new BigDecimal(value2),
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = new Volume(0.0, VolumeUnit.LITER);
        Volume.subtract(v1, v2, result);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = Volume.subtractAndReturnNew(v1, v2, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final var result = v1.subtractAndReturnNew(value2, VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        final var result = v1.subtractAndReturnNew(new BigDecimal(value2), VolumeUnit.CUBIC_METER, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = v1.subtractAndReturnNew(v2, VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);

        v1.subtract(value2, VolumeUnit.CUBIC_METER);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1 - value2, v1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.LITER);

        v1.subtract(new BigDecimal(value2), VolumeUnit.LITER);

        // check
        assertEquals(VolumeUnit.LITER, v1.getUnit());
        assertEquals(value1 - value2, v1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        v1.subtract(v2);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1 - value2, v1.getValue().doubleValue(), ERROR);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var v1 = new Volume(value1, VolumeUnit.CUBIC_METER);
        final var v2 = new Volume(value2, VolumeUnit.CUBIC_METER);

        final var result = new Volume(0.0, VolumeUnit.LITER);
        v1.subtract(v2, result);

        // check
        assertEquals(VolumeUnit.CUBIC_METER, v1.getUnit());
        assertEquals(value1, v1.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.CUBIC_METER, v2.getUnit());
        assertEquals(value2, v2.getValue().doubleValue(), 0.0);

        assertEquals(VolumeUnit.LITER, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var v1 = new Volume(value, VolumeUnit.LITER);

        final var bytes = SerializationHelper.serialize(v1);
        final var v2 = SerializationHelper.deserialize(bytes);

        assertEquals(v1, v2);
        assertNotSame(v1, v2);
    }
}
