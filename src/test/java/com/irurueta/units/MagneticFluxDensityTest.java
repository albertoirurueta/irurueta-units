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

class MagneticFluxDensityTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var b = new MagneticFluxDensity();

        // check
        assertNull(b.getValue());
        assertNull(b.getUnit());

        // test constructor with value and unit
        b = new MagneticFluxDensity(1000, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(1000, b.getValue());
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // Force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new MagneticFluxDensity(null,
                MagneticFluxDensityUnit.GIGATESLA));
        assertThrows(IllegalArgumentException.class, () -> new MagneticFluxDensity(1000, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var b1 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);
        final var b2 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);
        final var b3 = new MagneticFluxDensity(value + 1.0, MagneticFluxDensityUnit.TESLA);
        final var b4 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.NANOTESLA);

        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertNotEquals(b1, b4);

        assertNotEquals(null, b1);
        assertNotEquals(b1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var b1 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);
        final var b2 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);
        final var b3 = new MagneticFluxDensity(value + 1.0, MagneticFluxDensityUnit.TESLA);
        final var b4 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.NANOTESLA);

        assertEquals(b1.hashCode(), b1.hashCode());
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
        assertNotEquals(b1.hashCode(), b4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var b1 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.KILOTESLA);
        final var b3 = new MagneticFluxDensity(value + 0.5 * ERROR, MagneticFluxDensityUnit.KILOTESLA);
        final var b4 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);
        final var b5 = new MagneticFluxDensity(value * 1000.0, MagneticFluxDensityUnit.TESLA);

        assertTrue(b1.equals(b1, 0.0));
        assertTrue(b1.equals(b2, 0.0));
        assertFalse(b1.equals(b3, 0.0));
        assertTrue(b1.equals(b3, ERROR));
        assertFalse(b1.equals(b4, ERROR));
        assertTrue(b1.equals(b5, ERROR));

        assertFalse(b1.equals(null, ERROR));
    }

    @Test
    void testGetSetValue() {
        final var b = new MagneticFluxDensity(1, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(1, b.getValue());

        // set new value
        b.setValue(2.5);

        // check
        assertEquals(2.5, b.getValue());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> b.setValue(null));
    }

    @Test
    void testGetSetUnit() {
        final var b = new MagneticFluxDensity(1, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // set new value
        b.setUnit(MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> b.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = MagneticFluxDensity.add(
                value1, MagneticFluxDensityUnit.KILOTESLA,
                value2, MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = MagneticFluxDensity.add(new BigDecimal(value1), MagneticFluxDensityUnit.KILOTESLA,
                new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = new MagneticFluxDensity(0.0, MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensity.add(b1, b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = MagneticFluxDensity.addAndReturnNew(b1, b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.addAndReturnNew(value2, MagneticFluxDensityUnit.KILOTESLA, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.addAndReturnNew(new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.addAndReturnNew(b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);

        b1.add(value2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);

        b1.add(new BigDecimal(value2), MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.TESLA);

        b1.add(b2);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);

        assertEquals(MagneticFluxDensityUnit.TESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = new MagneticFluxDensity(0.0, MagneticFluxDensityUnit.TESLA);
        b1.add(b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = MagneticFluxDensity.subtract(value1, MagneticFluxDensityUnit.KILOTESLA,
                value2, MagneticFluxDensityUnit.KILOTESLA, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = MagneticFluxDensity.subtract(new BigDecimal(value1), MagneticFluxDensityUnit.KILOTESLA,
                new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = new MagneticFluxDensity(0.0, MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensity.subtract(b1, b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = MagneticFluxDensity.subtractAndReturnNew(b1, b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.subtractAndReturnNew(value2, MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.subtractAndReturnNew(new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = b1.subtractAndReturnNew(b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);

        b1.subtract(value2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);

        b1.subtract(new BigDecimal(value2), MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.TESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.TESLA);

        b1.subtract(b2);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);

        assertEquals(MagneticFluxDensityUnit.TESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var b1 = new MagneticFluxDensity(value1, MagneticFluxDensityUnit.KILOTESLA);
        final var b2 = new MagneticFluxDensity(value2, MagneticFluxDensityUnit.KILOTESLA);

        final var result = new MagneticFluxDensity(0.0, MagneticFluxDensityUnit.TESLA);
        b1.subtract(b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var b1 = new MagneticFluxDensity(value, MagneticFluxDensityUnit.TESLA);

        final var bytes = SerializationHelper.serialize(b1);
        final var b2 = SerializationHelper.deserialize(bytes);

        assertEquals(b1, b2);
        assertNotSame(b1, b2);
    }
}
