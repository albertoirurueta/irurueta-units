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

class PressureTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var p = new Pressure();

        // check
        assertNull(p.getValue());
        assertNull(p.getUnit());

        // test constructor with value and unit
        p = new Pressure(323, PressureUnit.PASCAL);

        // check
        assertEquals(323, p.getValue());
        assertEquals(PressureUnit.PASCAL, p.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Pressure(null, PressureUnit.PASCAL));
        assertThrows(IllegalArgumentException.class, () -> new Pressure(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var p1 = new Pressure(value, PressureUnit.PASCAL);
        final var p2 = new Pressure(value, PressureUnit.PASCAL);
        final var p3 = new Pressure(value + 1.0, PressureUnit.PASCAL);
        final var p4 = new Pressure(value, PressureUnit.KILOPASCAL);

        //noinspection EqualsWithItself
        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);

        assertNotEquals(null, p1);
        assertNotEquals(p1, new Object());
    }

    @Test
    void tetHashCode() {
        final var value = new Random().nextDouble();
        final var p1 = new Pressure(value, PressureUnit.PASCAL);
        final var p2 = new Pressure(value, PressureUnit.PASCAL);
        final var p3 = new Pressure(value + 1.0, PressureUnit.PASCAL);
        final var p4 = new Pressure(value, PressureUnit.KILOPASCAL);

        assertEquals(p1.hashCode(), p1.hashCode());
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertNotEquals(p1.hashCode(), p4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var p1 = new Pressure(value, PressureUnit.PASCAL);
        final var p2 = new Pressure(value, PressureUnit.PASCAL);
        final var p3 = new Pressure(value + 0.5 * ERROR, PressureUnit.PASCAL);
        final var p4 = new Pressure(value, PressureUnit.KILOPASCAL);
        final var p5 = new Pressure(PressureConverter.convert(value, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), PressureUnit.KILOPASCAL);

        assertTrue(p1.equals(p1, 0.0));
        assertTrue(p1.equals(p2, 0.0));
        assertFalse(p1.equals(p3, 0.0));
        assertTrue(p1.equals(p3, ERROR));
        assertFalse(p1.equals(p4, ERROR));
        assertTrue(p1.equals(p5, ERROR));

        assertFalse(p1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var p = new Pressure(1, PressureUnit.PASCAL);

        // check
        assertEquals(1, p.getValue());

        // set new value
        p.setValue(2.5);

        // check
        assertEquals(2.5, p.getValue());

        //force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> p.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var p = new Pressure(1, PressureUnit.PASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p.getUnit());

        // set new value
        p.setUnit(PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.KILOPASCAL, p.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> p.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Pressure.add(value1, PressureUnit.PASCAL, value2,
                PressureUnit.PASCAL, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                        PressureUnit.KILOPASCAL), result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Pressure.add(new BigDecimal(value1), PressureUnit.PASCAL,
                new BigDecimal(value2), PressureUnit.PASCAL, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                        PressureUnit.KILOPASCAL), result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = new Pressure(0.0, PressureUnit.KILOPASCAL);
        Pressure.add(p1, p2, result);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = Pressure.addAndReturnNew(p1, p2, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        final var result = p1.addAndReturnNew(value2, PressureUnit.PASCAL, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        final var result = p1.addAndReturnNew(new BigDecimal(value2), PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = p1.addAndReturnNew(p2, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(p2.getValue().doubleValue(), value2, 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        p1.add(value2, PressureUnit.PASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 + value2, p1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        p1.add(new BigDecimal(value2), PressureUnit.PASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 + value2, p1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        p1.add(p2);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 + value2, p1.getValue().doubleValue(), ERROR);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = new Pressure(0.0, PressureUnit.KILOPASCAL);
        p1.add(p2, result);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 + value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Pressure.subtract(value1, PressureUnit.PASCAL, value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Pressure.subtract(new BigDecimal(value1), PressureUnit.PASCAL, new BigDecimal(value2),
                PressureUnit.PASCAL, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = new Pressure(0.0, PressureUnit.KILOPASCAL);
        Pressure.subtract(p1, p2, result);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = Pressure.subtractAndReturnNew(p1, p2, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        final var result = p1.subtractAndReturnNew(value2, PressureUnit.PASCAL, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        final var result = p1.subtractAndReturnNew(new BigDecimal(value2), PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = p1.subtractAndReturnNew(p2, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        p1.subtract(value2, PressureUnit.PASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 - value2, p1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);

        p1.subtract(new BigDecimal(value2), PressureUnit.PASCAL);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 - value2, p1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        p1.subtract(p2);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1 - value2, p1.getValue().doubleValue(), ERROR);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var p1 = new Pressure(value1, PressureUnit.PASCAL);
        final var p2 = new Pressure(value2, PressureUnit.PASCAL);

        final var result = new Pressure(0.0, PressureUnit.KILOPASCAL);
        p1.subtract(p2, result);

        // check
        assertEquals(PressureUnit.PASCAL, p1.getUnit());
        assertEquals(value1, p1.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.PASCAL, p2.getUnit());
        assertEquals(value2, p2.getValue().doubleValue(), 0.0);

        assertEquals(PressureUnit.KILOPASCAL, result.getUnit());
        assertEquals(PressureConverter.convert(value1 - value2, PressureUnit.PASCAL,
                PressureUnit.KILOPASCAL), result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var p1 = new Pressure(value, PressureUnit.PASCAL);

        final var bytes = SerializationHelper.serialize(p1);
        final var p2 = SerializationHelper.deserialize(bytes);

        assertEquals(p1, p2);
        assertNotSame(p1, p2);
    }
}
