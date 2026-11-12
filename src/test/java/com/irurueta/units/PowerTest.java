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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link Power}.
 */
class PowerTest {

    /**
     * Allowed error.
     */
    private static final double ERROR = 1e-6;

    /**
     * First value.
     */
    private static final double VALUE1 = 1500.0;

    /**
     * Second value.
     */
    private static final double VALUE2 = 2.5;

    /**
     * Checks constructors.
     */
    @Test
    void constructor_whenEmpty_hasNoValueNorUnit() {
        final var p = new Power();

        assertNull(p.getValue());
        assertNull(p.getUnit());
    }

    /**
     * Checks constructor with value and unit.
     */
    @Test
    void constructor_whenValueAndUnit_setsThem() {
        final var p = new Power(323, PowerUnit.WATT);

        assertEquals(323, p.getValue());
        assertEquals(PowerUnit.WATT, p.getUnit());
    }

    /**
     * Checks that null arguments are rejected.
     */
    @Test
    void constructor_whenNullArgument_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Power(null, PowerUnit.WATT));
        assertThrows(IllegalArgumentException.class, () -> new Power(323, null));
    }

    /**
     * Checks equals and hash code.
     */
    @Test
    void equals_whenSameAndDifferentInstances_returnsExpectedResult() {
        final var p1 = new Power(VALUE1, PowerUnit.WATT);
        final var p2 = new Power(VALUE1, PowerUnit.WATT);
        final var p3 = new Power(VALUE1 + 1.0, PowerUnit.WATT);
        final var p4 = new Power(VALUE1, PowerUnit.KILOWATT);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(null, p1);
        assertNotEquals(p1, new Object());
    }

    /**
     * Checks equality with tolerance, including unit conversion.
     */
    @Test
    void equals_whenTolerance_comparesAfterConversion() {
        final var p1 = new Power(VALUE1, PowerUnit.WATT);
        final var p2 = new Power(VALUE1 + 0.5 * ERROR, PowerUnit.WATT);
        final var p3 = new Power(1.5, PowerUnit.KILOWATT);
        final var p4 = new Power(VALUE1, PowerUnit.KILOWATT);

        assertTrue(p1.equals(p1, 0.0));
        assertFalse(p1.equals(p2, 0.0));
        assertTrue(p1.equals(p2, ERROR));
        assertTrue(p1.equals(p3, ERROR));
        assertFalse(p1.equals(p4, ERROR));
        assertFalse(p1.equals(null, ERROR));
    }

    /**
     * Checks value and unit accessors.
     */
    @Test
    void setValue_whenNull_throwsIllegalArgumentException() {
        final var p = new Power(1, PowerUnit.WATT);

        p.setValue(2.5);
        assertEquals(2.5, p.getValue());
        assertThrows(IllegalArgumentException.class, () -> p.setValue(null));

        p.setUnit(PowerUnit.KILOWATT);
        assertEquals(PowerUnit.KILOWATT, p.getUnit());
        assertThrows(IllegalArgumentException.class, () -> p.setUnit(null));
    }

    /**
     * Checks static addition with doubles and numbers.
     */
    @Test
    void add_whenStaticValues_returnsSumInResultUnit() {
        final var expected = VALUE1 + VALUE2 * 1000.0;

        assertEquals(expected,
                Power.add(VALUE1, PowerUnit.WATT, VALUE2, PowerUnit.KILOWATT, PowerUnit.WATT), ERROR);
        assertEquals(expected, Power.add(BigDecimal.valueOf(VALUE1), PowerUnit.WATT,
                BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT, PowerUnit.WATT).doubleValue(), ERROR);
    }

    /**
     * Checks addition of instances in every overload.
     */
    @Test
    void add_whenInstances_storesSum() {
        final var expected = VALUE1 + VALUE2 * 1000.0;
        final var p1 = new Power(VALUE1, PowerUnit.WATT);
        final var p2 = new Power(VALUE2, PowerUnit.KILOWATT);

        final var result = new Power(0.0, PowerUnit.WATT);
        Power.add(p1, p2, result);
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        assertEquals(expected, Power.addAndReturnNew(p1, p2, PowerUnit.WATT).getValue().doubleValue(), ERROR);
        assertEquals(expected / 1000.0,
                p1.addAndReturnNew(VALUE2, PowerUnit.KILOWATT, PowerUnit.KILOWATT).getValue().doubleValue(), ERROR);
        assertEquals(expected, p1.addAndReturnNew(BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT,
                PowerUnit.WATT).getValue().doubleValue(), ERROR);
        assertEquals(expected, p1.addAndReturnNew(p2, PowerUnit.WATT).getValue().doubleValue(), ERROR);

        final var a = new Power(VALUE1, PowerUnit.WATT);
        a.add(VALUE2, PowerUnit.KILOWATT);
        assertEquals(expected, a.getValue().doubleValue(), ERROR);

        final var b = new Power(VALUE1, PowerUnit.WATT);
        b.add(BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT);
        assertEquals(expected, b.getValue().doubleValue(), ERROR);

        final var c = new Power(VALUE1, PowerUnit.WATT);
        c.add(p2);
        assertEquals(expected, c.getValue().doubleValue(), ERROR);

        final var d = new Power(0.0, PowerUnit.WATT);
        p1.add(p2, d);
        assertEquals(expected, d.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction with doubles and numbers.
     */
    @Test
    void subtract_whenStaticValues_returnsDifferenceInResultUnit() {
        final var expected = VALUE1 - VALUE2 * 1000.0;

        assertEquals(expected,
                Power.subtract(VALUE1, PowerUnit.WATT, VALUE2, PowerUnit.KILOWATT, PowerUnit.WATT), ERROR);
        assertEquals(expected, Power.subtract(BigDecimal.valueOf(VALUE1), PowerUnit.WATT,
                BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT, PowerUnit.WATT).doubleValue(), ERROR);
    }

    /**
     * Checks subtraction of instances in every overload.
     */
    @Test
    void subtract_whenInstances_storesDifference() {
        final var expected = VALUE1 - VALUE2 * 1000.0;
        final var p1 = new Power(VALUE1, PowerUnit.WATT);
        final var p2 = new Power(VALUE2, PowerUnit.KILOWATT);

        final var result = new Power(0.0, PowerUnit.WATT);
        Power.subtract(p1, p2, result);
        assertEquals(expected, result.getValue().doubleValue(), ERROR);

        assertEquals(expected,
                Power.subtractAndReturnNew(p1, p2, PowerUnit.WATT).getValue().doubleValue(), ERROR);
        assertEquals(expected / 1000.0, p1.subtractAndReturnNew(VALUE2, PowerUnit.KILOWATT,
                PowerUnit.KILOWATT).getValue().doubleValue(), ERROR);
        assertEquals(expected, p1.subtractAndReturnNew(BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT,
                PowerUnit.WATT).getValue().doubleValue(), ERROR);
        assertEquals(expected, p1.subtractAndReturnNew(p2, PowerUnit.WATT).getValue().doubleValue(), ERROR);

        final var a = new Power(VALUE1, PowerUnit.WATT);
        a.subtract(VALUE2, PowerUnit.KILOWATT);
        assertEquals(expected, a.getValue().doubleValue(), ERROR);

        final var b = new Power(VALUE1, PowerUnit.WATT);
        b.subtract(BigDecimal.valueOf(VALUE2), PowerUnit.KILOWATT);
        assertEquals(expected, b.getValue().doubleValue(), ERROR);

        final var c = new Power(VALUE1, PowerUnit.WATT);
        c.subtract(p2);
        assertEquals(expected, c.getValue().doubleValue(), ERROR);

        final var d = new Power(0.0, PowerUnit.WATT);
        p1.subtract(p2, d);
        assertEquals(expected, d.getValue().doubleValue(), ERROR);
    }
}
