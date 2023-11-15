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

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class MagneticFluxDensityTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        MagneticFluxDensity b = new MagneticFluxDensity();

        // check
        assertNull(b.getValue());
        assertNull(b.getUnit());

        // test constructor with value and unit
        b = new MagneticFluxDensity(1000, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(1000, b.getValue());
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // Force IllegalArgumentException
        b = null;
        try {
            b = new MagneticFluxDensity(null,
                    MagneticFluxDensityUnit.GIGATESLA);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            b = new MagneticFluxDensity(1000, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(b);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity b1 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b3 = new MagneticFluxDensity(value + 1.0,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b4 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.NANOTESLA);

        assertEquals(b1, b2);
        assertNotEquals(b1, b3);
        assertNotEquals(b1, b4);

        assertNotEquals(null, b1);
        assertNotEquals(b1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity b1 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b3 = new MagneticFluxDensity(value + 1.0,
                MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b4 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.NANOTESLA);

        assertEquals(b1.hashCode(), b1.hashCode());
        assertEquals(b1.hashCode(), b2.hashCode());
        assertNotEquals(b1.hashCode(), b3.hashCode());
        assertNotEquals(b1.hashCode(), b4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity b1 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b3 = new MagneticFluxDensity(
                value + 0.5 * ERROR, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b4 = new MagneticFluxDensity(
                value, MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b5 = new MagneticFluxDensity(value * 1000.0,
                MagneticFluxDensityUnit.TESLA);

        assertTrue(b1.equals(b1, 0.0));
        assertTrue(b1.equals(b2, 0.0));
        assertFalse(b1.equals(b3, 0.0));
        assertTrue(b1.equals(b3, ERROR));
        assertFalse(b1.equals(b4, ERROR));
        assertTrue(b1.equals(b5, ERROR));

        assertFalse(b1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final MagneticFluxDensity b = new MagneticFluxDensity(1,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(1, b.getValue());

        // set new value
        b.setValue(2.5);

        // check
        assertEquals(2.5, b.getValue());

        // force IllegalArgumentException
        try {
            b.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final MagneticFluxDensity b = new MagneticFluxDensity(1,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // set new value
        b.setUnit(MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());

        // force IllegalArgumentException
        try {
            b.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = MagneticFluxDensity.add(
                value1, MagneticFluxDensityUnit.KILOTESLA,
                value2, MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 + value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = MagneticFluxDensity.add(
                new BigDecimal(value1), MagneticFluxDensityUnit.KILOTESLA,
                new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 + value2) * 1000.0, result.doubleValue(),
                ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(value1,
                MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(value2,
                MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = new MagneticFluxDensity(
                0.0, MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensity.add(b1, b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = MagneticFluxDensity.addAndReturnNew(
                b1, b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(value1,
                MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.addAndReturnNew(
                value2, MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.addAndReturnNew(
                new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.addAndReturnNew(b2,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);

        b1.add(value2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);

        b1.add(new BigDecimal(value2), MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.TESLA);

        b1.add(b2);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 + value2, b1.getValue().doubleValue(), ERROR);

        assertEquals(MagneticFluxDensityUnit.TESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = new MagneticFluxDensity(
                0.0, MagneticFluxDensityUnit.TESLA);
        b1.add(b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 + value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = MagneticFluxDensity.subtract(
                value1, MagneticFluxDensityUnit.KILOTESLA,
                value2, MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 - value2) * 1000.0, result, ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = MagneticFluxDensity.subtract(
                new BigDecimal(value1), MagneticFluxDensityUnit.KILOTESLA,
                new BigDecimal(value2), MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals((value1 - value2) * 1000.0, result.doubleValue(),
                ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = new MagneticFluxDensity(
                0.0, MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensity.subtract(b1, b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = MagneticFluxDensity
                .subtractAndReturnNew(b1, b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.subtractAndReturnNew(value2,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.subtractAndReturnNew(
                new BigDecimal(value2),
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = b1.subtractAndReturnNew(
                b2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);

        b1.subtract(value2, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);

        b1.subtract(new BigDecimal(value2), MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.TESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.TESLA);

        b1.subtract(b2);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b1.getUnit());
        assertEquals(value1 - value2, b1.getValue().doubleValue(), ERROR);

        assertEquals(MagneticFluxDensityUnit.TESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final MagneticFluxDensity b1 = new MagneticFluxDensity(
                value1, MagneticFluxDensityUnit.KILOTESLA);
        final MagneticFluxDensity b2 = new MagneticFluxDensity(
                value2, MagneticFluxDensityUnit.KILOTESLA);

        final MagneticFluxDensity result = new MagneticFluxDensity(
                0.0, MagneticFluxDensityUnit.TESLA);
        b1.subtract(b2, result);

        // check
        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b1.getUnit());
        assertEquals(value1, b1.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.KILOTESLA, b2.getUnit());
        assertEquals(value2, b2.getValue().doubleValue(), 0.0);

        assertEquals(MagneticFluxDensityUnit.TESLA, result.getUnit());
        assertEquals((value1 - value2) * 1000.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity b1 = new MagneticFluxDensity(value,
                MagneticFluxDensityUnit.TESLA);

        final byte[] bytes = SerializationHelper.serialize(b1);
        final MagneticFluxDensity b2 = SerializationHelper.deserialize(bytes);

        assertEquals(b1, b2);
        assertNotSame(b1, b2);
    }
}
