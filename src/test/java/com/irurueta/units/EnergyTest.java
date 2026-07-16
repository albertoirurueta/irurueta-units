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

class EnergyTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var e = new Energy();

        // check
        assertNull(e.getValue());
        assertNull(e.getUnit());

        // test constructor with value and unit
        e = new Energy(323, EnergyUnit.JOULE);

        // check
        assertEquals(323, e.getValue());
        assertEquals(EnergyUnit.JOULE, e.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Energy(null, EnergyUnit.JOULE));
        assertThrows(IllegalArgumentException.class, () -> new Energy(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var e1 = new Energy(value, EnergyUnit.JOULE);
        final var e2 = new Energy(value, EnergyUnit.JOULE);
        final var e3 = new Energy(value + 1.0, EnergyUnit.JOULE);
        final var e4 = new Energy(value, EnergyUnit.KILOJOULE);

        //noinspection EqualsWithItself
        assertEquals(e1, e1);
        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertNotEquals(e1, e4);

        assertNotEquals(null, e1);
        assertNotEquals(e1, new Object());
    }

    @Test
    void tetHashCode() {
        final var value = new Random().nextDouble();
        final var e1 = new Energy(value, EnergyUnit.JOULE);
        final var e2 = new Energy(value, EnergyUnit.JOULE);
        final var e3 = new Energy(value + 1.0, EnergyUnit.JOULE);
        final var e4 = new Energy(value, EnergyUnit.KILOJOULE);

        assertEquals(e1.hashCode(), e1.hashCode());
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
        assertNotEquals(e1.hashCode(), e4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var e1 = new Energy(value, EnergyUnit.JOULE);
        final var e2 = new Energy(value, EnergyUnit.JOULE);
        final var e3 = new Energy(value + 0.5 * ERROR, EnergyUnit.JOULE);
        final var e4 = new Energy(value, EnergyUnit.KILOJOULE);
        final var e5 = new Energy(EnergyConverter.convert(value, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), EnergyUnit.KILOJOULE);

        assertTrue(e1.equals(e1, 0.0));
        assertTrue(e1.equals(e2, 0.0));
        assertFalse(e1.equals(e3, 0.0));
        assertTrue(e1.equals(e3, ERROR));
        assertFalse(e1.equals(e4, ERROR));
        assertTrue(e1.equals(e5, ERROR));

        assertFalse(e1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var e = new Energy(1, EnergyUnit.JOULE);

        // check
        assertEquals(1, e.getValue());

        // set new value
        e.setValue(2.5);

        // check
        assertEquals(2.5, e.getValue());

        //force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> e.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var e = new Energy(1, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e.getUnit());

        // set new value
        e.setUnit(EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.KILOJOULE, e.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> e.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.add(value1, EnergyUnit.JOULE, value2,
                EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                        EnergyUnit.KILOJOULE), result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.add(new BigDecimal(value1), EnergyUnit.JOULE,
                new BigDecimal(value2), EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                        EnergyUnit.KILOJOULE), result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        Energy.add(e1, e2, result);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = Energy.addAndReturnNew(e1, e2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = e1.addAndReturnNew(value2, EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = e1.addAndReturnNew(new BigDecimal(value2), EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = e1.addAndReturnNew(e2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(e2.getValue().doubleValue(), value2, 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        e1.add(value2, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 + value2, e1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        e1.add(new BigDecimal(value2), EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 + value2, e1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        e1.add(e2);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 + value2, e1.getValue().doubleValue(), ERROR);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        e1.add(e2, result);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.subtract(value1, EnergyUnit.JOULE, value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.subtract(new BigDecimal(value1), EnergyUnit.JOULE, new BigDecimal(value2),
                EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        Energy.subtract(e1, e2, result);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = Energy.subtractAndReturnNew(e1, e2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = e1.subtractAndReturnNew(value2, EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = e1.subtractAndReturnNew(new BigDecimal(value2), EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = e1.subtractAndReturnNew(e2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        e1.subtract(value2, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 - value2, e1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);

        e1.subtract(new BigDecimal(value2), EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 - value2, e1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        e1.subtract(e2);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1 - value2, e1.getValue().doubleValue(), ERROR);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var e1 = new Energy(value1, EnergyUnit.JOULE);
        final var e2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        e1.subtract(e2, result);

        // check
        assertEquals(EnergyUnit.JOULE, e1.getUnit());
        assertEquals(value1, e1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, e2.getUnit());
        assertEquals(value2, e2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var e1 = new Energy(value, EnergyUnit.JOULE);

        final var bytes = SerializationHelper.serialize(e1);
        final var e2 = SerializationHelper.deserialize(bytes);

        assertEquals(e1, e2);
        assertNotSame(e1, e2);
    }
}
