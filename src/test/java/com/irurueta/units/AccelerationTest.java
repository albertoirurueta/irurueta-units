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

import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.*;

public class AccelerationTest {

    private static final double ERROR = 1e-6;

    @Test
    public void testConstructor() {
        // test empty constructor
        Acceleration a = new Acceleration();

        // check
        assertNull(a.getValue());
        assertNull(a.getUnit());

        // test constructor with value and unit
        a = new Acceleration(323, AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(323, a.getValue());
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a.getUnit());

        // force IllegalArgumentException
        a = null;
        try {
            a = new Acceleration(null, AccelerationUnit.METERS_PER_SQUARED_SECOND);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            a = new Acceleration(323, null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
        //noinspection ConstantConditions
        assertNull(a);
    }

    @Test
    public void testEquals() {
        final double value = new Random().nextDouble();
        final Acceleration a1 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a3 = new Acceleration(value + 1.0,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a4 = new Acceleration(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        assertEquals(a1, a2);
        assertNotEquals(a1, a3);
        assertNotEquals(a1, a4);

        assertNotEquals(null, a1);
        assertNotEquals(a1, new Object());
    }

    @Test
    public void testHashCode() {
        final double value = new Random().nextDouble();
        final Acceleration a1 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a3 = new Acceleration(value + 1.0,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a4 = new Acceleration(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        assertEquals(a1.hashCode(), a1.hashCode());
        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), a3.hashCode());
        assertNotEquals(a1.hashCode(), a4.hashCode());
    }

    @Test
    public void testEqualsWithTolerance() {
        final double value = new Random().nextDouble();
        final Acceleration a1 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a3 = new Acceleration(value + 0.5 * ERROR,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a4 = new Acceleration(value,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        final Acceleration a5 = new Acceleration(AccelerationConverter.convert(
                value, AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND),
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        assertTrue(a1.equals(a1, 0.0));
        assertTrue(a1.equals(a2, 0.0));
        assertFalse(a1.equals(a3, 0.0));
        assertTrue(a1.equals(a3, ERROR));
        assertFalse(a1.equals(a4, ERROR));
        assertTrue(a1.equals(a5, ERROR));

        assertFalse(a1.equals(null, ERROR));
    }

    @Test
    public void testGetSetValue() {
        final Acceleration a = new Acceleration(1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(1, a.getValue());

        // set new value
        a.setValue(2.5);

        // check
        assertEquals(2.5, a.getValue());

        // force IllegalArgumentException
        try {
            a.setValue(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testGetSetUnit() {
        final Acceleration a = new Acceleration(1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a.getUnit());

        // set new value
        a.setUnit(AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, a.getUnit());

        // Force IllegalArgumentException
        try {
            a.setUnit(null);
            fail("IllegalArgumentException expected but not thrown");
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testAdd1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Acceleration.add(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND, value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(result,
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAdd2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Acceleration.add(new BigDecimal(value1),
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                new BigDecimal(value2), AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(result.doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAdd3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = new Acceleration(0.0,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        Acceleration.add(a1, a2, result);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAddAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = Acceleration.addAndReturnNew(a1, a2,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAddAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.addAndReturnNew(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAddAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.addAndReturnNew(new BigDecimal(value2),
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAddAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.addAndReturnNew(a2,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testAdd4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.add(value2, AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(),
                ERROR);
    }

    @Test
    public void testAdd5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.add(new BigDecimal(value2), AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(),
                ERROR);
    }

    @Test
    public void testAdd6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.add(a2);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 + value2, a1.getValue().doubleValue(),
                ERROR);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testAdd7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = new Acceleration(0.0,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        a1.add(a2, result);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 + value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtract1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final double result = Acceleration.subtract(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND, value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(result,
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtract2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Number result = Acceleration.subtract(new BigDecimal(value1),
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                new BigDecimal(value2),
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(result.doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtract3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = new Acceleration(0.0,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        Acceleration.subtract(a1, a2, result);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtractAndReturnNew1() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = Acceleration.subtractAndReturnNew(a1, a2,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtractAndReturnNew2() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.subtractAndReturnNew(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtractAndReturnNew3() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.subtractAndReturnNew(new BigDecimal(value2),
                AccelerationUnit.METERS_PER_SQUARED_SECOND,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtractAndReturnNew4() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = a1.subtractAndReturnNew(a2,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND, result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSubtract5() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.subtract(value2, AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract6() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.subtract(new BigDecimal(value2), AccelerationUnit.METERS_PER_SQUARED_SECOND);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);
    }

    @Test
    public void testSubtract7() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        a1.subtract(a2);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1 - value2, a1.getValue().doubleValue(), ERROR);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(value2, a2.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testSubtract8() {
        final Random r = new Random();
        final double value1 = r.nextDouble();
        final double value2 = r.nextDouble();

        final Acceleration a1 = new Acceleration(value1,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);
        final Acceleration a2 = new Acceleration(value2,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final Acceleration result = new Acceleration(0.0,
                AccelerationUnit.FEET_PER_SQUARED_SECOND);
        a1.subtract(a2, result);

        // check
        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a1.getUnit());
        assertEquals(value1, a1.getValue().doubleValue(), 0.0);

        assertEquals(AccelerationUnit.METERS_PER_SQUARED_SECOND, a2.getUnit());
        assertEquals(a2.getValue().doubleValue(), value2, 0.0);

        assertEquals(AccelerationUnit.FEET_PER_SQUARED_SECOND,
                result.getUnit());
        assertEquals(result.getValue().doubleValue(),
                AccelerationConverter.convert(value1 - value2,
                        AccelerationUnit.METERS_PER_SQUARED_SECOND,
                        AccelerationUnit.FEET_PER_SQUARED_SECOND),
                ERROR);
    }

    @Test
    public void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final double value = new Random().nextDouble();
        final Acceleration a1 = new Acceleration(value,
                AccelerationUnit.METERS_PER_SQUARED_SECOND);

        final byte[] bytes = SerializationHelper.serialize(a1);
        final Acceleration a2 = SerializationHelper.deserialize(bytes);

        assertEquals(a1, a2);
        assertNotSame(a1, a2);
    }
}
