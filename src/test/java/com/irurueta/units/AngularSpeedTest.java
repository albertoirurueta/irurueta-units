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

class AngularSpeedTest {

    private static final double ERROR = 1e-6;

    @Test
    void testConstructor() {
        // test empty constructor
        var s = new AngularSpeed();

        // check
        assertNull(s.getValue());
        assertNull(s.getUnit());

        // test constructor with value and unit
        s = new AngularSpeed(323, AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(323, s.getValue());
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> new AngularSpeed(null, AngularSpeedUnit.DEGREES_PER_SECOND));
        assertThrows(IllegalArgumentException.class, () -> new AngularSpeed(323, null));
    }

    @Test
    void testEquals() {
        final var value = new Random().nextDouble();
        final var s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s3 = new AngularSpeed(value + 1.0, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);

        assertNotEquals(null, s1);
        assertNotEquals(s1, new Object());
    }

    @Test
    void testHashCode() {
        final var value = new Random().nextDouble();
        final var s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s3 = new AngularSpeed(value + 1.0, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);

        assertEquals(s1.hashCode(), s1.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
    }

    @Test
    void testEqualsWithTolerance() {
        final var value = new Random().nextDouble();
        final var s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s3 = new AngularSpeed(value + 0.5 * ERROR, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s4 = new AngularSpeed(value, AngularSpeedUnit.RADIANS_PER_SECOND);
        final var s5 = new AngularSpeed(value * Math.PI / 180.0, AngularSpeedUnit.RADIANS_PER_SECOND);

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
        final var s = new AngularSpeed(1, AngularSpeedUnit.DEGREES_PER_SECOND);

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
        final var s = new AngularSpeed(1, AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s.getUnit());

        // set new value
        s.setUnit(AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> s.setUnit(null));
    }

    @Test
    void testAdd1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularSpeed.add(value1, AngularSpeedUnit.DEGREES_PER_SECOND,
                value2, AngularSpeedUnit.DEGREES_PER_SECOND, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testAdd2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularSpeed.add(new BigDecimal(value1), AngularSpeedUnit.DEGREES_PER_SECOND,
                new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals((value1 + value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testAdd3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = new AngularSpeed(0.0, AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeed.add(s1, s2, result);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = AngularSpeed.addAndReturnNew(s1, s2, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = s1.addAndReturnNew(value2, AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = s1.addAndReturnNew(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAddAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = s1.addAndReturnNew(s2, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testAdd6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.add(s2);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testAdd7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = new AngularSpeed(0.0, AngularSpeedUnit.RADIANS_PER_SECOND);
        s1.add(s2, result);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 + value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularSpeed.subtract(value1, AngularSpeedUnit.DEGREES_PER_SECOND,
                value2, AngularSpeedUnit.DEGREES_PER_SECOND, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result, ERROR);
    }

    @Test
    void testSubtract2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = AngularSpeed.subtract(new BigDecimal(value1), AngularSpeedUnit.DEGREES_PER_SECOND,
                new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals((value1 - value2) * Math.PI / 180.0, result.doubleValue(), ERROR);
    }

    @Test
    void testSubtract3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = new AngularSpeed(0.0, AngularSpeedUnit.RADIANS_PER_SECOND);
        AngularSpeed.subtract(s1, s2, result);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew1() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = AngularSpeed.subtractAndReturnNew(s1, s2, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew2() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = s1.subtractAndReturnNew(value2, AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew3() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        final AngularSpeed result = s1.subtractAndReturnNew(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND,
                AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtractAndReturnNew4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = s1.subtractAndReturnNew(s2, AngularSpeedUnit.RADIANS_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0, result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract4() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract5() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(new BigDecimal(value2), AngularSpeedUnit.DEGREES_PER_SECOND);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSubtract6() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        s1.subtract(s2);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    @Test
    void testSubtract7() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new AngularSpeed(value1, AngularSpeedUnit.DEGREES_PER_SECOND);
        final var s2 = new AngularSpeed(value2, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var result = new AngularSpeed(0.0, AngularSpeedUnit.RADIANS_PER_SECOND);
        s1.subtract(s2, result);

        // check
        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.DEGREES_PER_SECOND, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(AngularSpeedUnit.RADIANS_PER_SECOND, result.getUnit());
        assertEquals((value1 - value2) * Math.PI / 180.0,
                result.getValue().doubleValue(), ERROR);
    }

    @Test
    void testSerializeDeserialize() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var s1 = new AngularSpeed(value, AngularSpeedUnit.DEGREES_PER_SECOND);

        final var bytes = SerializationHelper.serialize(s1);
        final var s2 = SerializationHelper.deserialize(bytes);

        assertEquals(s1, s2);
        assertNotSame(s1, s2);
    }
}
