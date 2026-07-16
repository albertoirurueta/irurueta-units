/*
 * Copyright (C) 2026 Alberto Irurueta Carro (alberto@irurueta.com)
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

class ForceTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var f = new Force();

        // check
        assertNull(f.getValue());
        assertNull(f.getUnit());

        // test constructor with value and unit
        f = new Force(323, ForceUnit.NEWTON);

        // check
        assertEquals(323, f.getValue());
        assertEquals(ForceUnit.NEWTON, f.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Force(null, ForceUnit.NEWTON));
        assertThrows(IllegalArgumentException.class, () -> new Force(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var f1 = new Force(value, ForceUnit.NEWTON);
        final var f2 = new Force(value, ForceUnit.NEWTON);
        final var f3 = new Force(value + 1.0, ForceUnit.NEWTON);
        final var f4 = new Force(value, ForceUnit.KILONEWTON);

        //noinspection EqualsWithItself
        assertEquals(f1, f1);
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);

        assertNotEquals(null, f1);
        assertNotEquals(f1, new Object());
    }

    @Test
    void tetHashCode() {
        final var value = new Random().nextDouble();
        final var f1 = new Force(value, ForceUnit.NEWTON);
        final var f2 = new Force(value, ForceUnit.NEWTON);
        final var f3 = new Force(value + 1.0, ForceUnit.NEWTON);
        final var f4 = new Force(value, ForceUnit.KILONEWTON);

        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
        assertNotEquals(f1.hashCode(), f4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var f1 = new Force(value, ForceUnit.NEWTON);
        final var f2 = new Force(value, ForceUnit.NEWTON);
        final var f3 = new Force(value + 0.5 * ERROR, ForceUnit.NEWTON);
        final var f4 = new Force(value, ForceUnit.KILONEWTON);
        final var f5 = new Force(ForceConverter.convert(value, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), ForceUnit.KILONEWTON);

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
        final var f = new Force(1, ForceUnit.NEWTON);

        // check
        assertEquals(1, f.getValue());

        // set new value
        f.setValue(2.5);

        // check
        assertEquals(2.5, f.getValue());

        //force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> f.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var f = new Force(1, ForceUnit.NEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f.getUnit());

        // set new value
        f.setUnit(ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.KILONEWTON, f.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> f.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Force.add(value1, ForceUnit.NEWTON, value2,
                ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                        ForceUnit.KILONEWTON), result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Force.add(new BigDecimal(value1), ForceUnit.NEWTON,
                new BigDecimal(value2), ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                        ForceUnit.KILONEWTON), result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = new Force(0.0, ForceUnit.KILONEWTON);
        Force.add(f1, f2, result);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = Force.addAndReturnNew(f1, f2, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(value2, ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(new BigDecimal(value2), ForceUnit.NEWTON,
                ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(f2, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(f2.getValue().doubleValue(), value2, 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        f1.add(value2, ForceUnit.NEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        f1.add(new BigDecimal(value2), ForceUnit.NEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        f1.add(f2);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 + value2, f1.getValue().doubleValue(), ERROR);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = new Force(0.0, ForceUnit.KILONEWTON);
        f1.add(f2, result);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 + value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Force.subtract(value1, ForceUnit.NEWTON, value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Force.subtract(new BigDecimal(value1), ForceUnit.NEWTON, new BigDecimal(value2),
                ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = new Force(0.0, ForceUnit.KILONEWTON);
        Force.subtract(f1, f2, result);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = Force.subtractAndReturnNew(f1, f2, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(value2, ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(new BigDecimal(value2), ForceUnit.NEWTON,
                ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(f2, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        f1.subtract(value2, ForceUnit.NEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);

        f1.subtract(new BigDecimal(value2), ForceUnit.NEWTON);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        f1.subtract(f2);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1 - value2, f1.getValue().doubleValue(), ERROR);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var f1 = new Force(value1, ForceUnit.NEWTON);
        final var f2 = new Force(value2, ForceUnit.NEWTON);

        final var result = new Force(0.0, ForceUnit.KILONEWTON);
        f1.subtract(f2, result);

        // check
        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(value1, f1.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(value2, f2.getValue().doubleValue(), 0.0);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(value1 - value2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var f1 = new Force(value, ForceUnit.NEWTON);

        final var bytes = SerializationHelper.serialize(f1);
        final var f2 = SerializationHelper.deserialize(bytes);

        assertEquals(f1, f2);
        assertNotSame(f1, f2);
    }
}
