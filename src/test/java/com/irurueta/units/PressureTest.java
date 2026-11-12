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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link Pressure}.
 */
class PressureTest {

    /**
     * First arbitrary value.
     */
    private static final double VALUE1 = 1.25;

    /**
     * Second arbitrary value.
     */
    private static final double VALUE2 = 2.5;

    /**
     * Allowed error in comparisons.
     */
    private static final double ERROR = 1e-6;

    /**
     * Checks the empty (package-private) constructor.
     */
    @Test
    void constructor_whenEmpty_hasNoValueNorUnit() {
        final var p = new Pressure();

        assertNull(p.getValue());
        assertNull(p.getUnit());
    }

    /**
     * Checks the constructor with value and unit.
     */
    @Test
    void constructor_whenValueAndUnit_setsThem() {
        final var p = new Pressure(VALUE1, PressureUnit.BAR);

        assertEquals(VALUE1, p.getValue());
        assertEquals(PressureUnit.BAR, p.getUnit());
    }

    /**
     * Checks that null arguments are rejected by the constructor.
     */
    @Test
    void constructor_whenNullArguments_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Pressure(null, PressureUnit.BAR));
        assertThrows(IllegalArgumentException.class, () -> new Pressure(VALUE1, null));
    }

    /**
     * Checks equality and hash code.
     */
    @Test
    void equals_whenSameOrDifferentContent_returnsExpectedResult() {
        final var p1 = new Pressure(VALUE1, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE1, PressureUnit.BAR);
        final var p3 = new Pressure(VALUE2, PressureUnit.BAR);
        final var p4 = new Pressure(VALUE1, PressureUnit.PSI);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, p4);
        assertNotEquals(null, p1);
        assertNotEquals(p1, new Object());
    }

    /**
     * Checks equality with tolerance, including conversion between units.
     */
    @Test
    void equals_whenTolerance_returnsExpectedResult() {
        final var p1 = new Pressure(VALUE1, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE1 + 0.5 * ERROR, PressureUnit.BAR);
        final var p3 = new Pressure(PressureConverter.convert(VALUE1, PressureUnit.BAR, PressureUnit.PSI),
                PressureUnit.PSI);
        final var p4 = new Pressure(VALUE1, PressureUnit.PSI);

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
    void setValueAndSetUnit_whenValid_updateState() {
        final var p = new Pressure(1, PressureUnit.PASCAL);

        p.setValue(VALUE2);
        p.setUnit(PressureUnit.ATMOSPHERE);

        assertEquals(VALUE2, p.getValue());
        assertEquals(PressureUnit.ATMOSPHERE, p.getUnit());
    }

    /**
     * Checks that null value or unit is rejected by the setters.
     */
    @Test
    void setValueAndSetUnit_whenNull_throwsIllegalArgumentException() {
        final var p = new Pressure(1, PressureUnit.PASCAL);

        assertThrows(IllegalArgumentException.class, () -> p.setValue(null));
        assertThrows(IllegalArgumentException.class, () -> p.setUnit(null));
    }

    /**
     * Checks static addition of doubles.
     */
    @Test
    void add_whenDoubles_returnsSumInResultUnit() {
        final var result = Pressure.add(VALUE1, PressureUnit.BAR, VALUE2, PressureUnit.BAR, PressureUnit.KILOPASCAL);

        assertEquals((VALUE1 + VALUE2) * 100.0, result, ERROR);
    }

    /**
     * Checks static addition of numbers.
     */
    @Test
    void add_whenNumbers_returnsSumInResultUnit() {
        final var result = Pressure.add(BigDecimal.valueOf(VALUE1), PressureUnit.BAR,
                BigDecimal.valueOf(VALUE2), PressureUnit.BAR, PressureUnit.KILOPASCAL);

        assertEquals((VALUE1 + VALUE2) * 100.0, result.doubleValue(), ERROR);
    }

    /**
     * Checks static addition of instances with mixed units.
     */
    @Test
    void add_whenInstances_storesSumInResult() {
        final var p1 = new Pressure(VALUE1, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE2 * 100.0, PressureUnit.KILOPASCAL);
        final var result = new Pressure(0.0, PressureUnit.BAR);

        Pressure.add(p1, p2, result);

        assertEquals(PressureUnit.BAR, result.getUnit());
        assertEquals(VALUE1 + VALUE2, result.getValue().doubleValue(), ERROR);
        assertEquals(VALUE1, p1.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks addition variants that return new instances.
     */
    @Test
    void addAndReturnNew_whenAnyVariant_returnsNewInstanceWithSum() {
        final var p1 = new Pressure(VALUE1, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE2, PressureUnit.BAR);
        final var expected = (VALUE1 + VALUE2) * 100.0;

        final var r1 = Pressure.addAndReturnNew(p1, p2, PressureUnit.KILOPASCAL);
        final var r2 = p1.addAndReturnNew(VALUE2, PressureUnit.BAR, PressureUnit.KILOPASCAL);
        final var r3 = p1.addAndReturnNew(BigDecimal.valueOf(VALUE2), PressureUnit.BAR, PressureUnit.KILOPASCAL);
        final var r4 = p1.addAndReturnNew(p2, PressureUnit.KILOPASCAL);

        for (final var r : new Pressure[]{r1, r2, r3, r4}) {
            assertEquals(PressureUnit.KILOPASCAL, r.getUnit());
            assertEquals(expected, r.getValue().doubleValue(), ERROR);
        }
        assertEquals(VALUE1, p1.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks in-place addition variants.
     */
    @Test
    void add_whenInPlaceVariants_updatesInstance() {
        final var p = new Pressure(VALUE1, PressureUnit.BAR);

        p.add(VALUE2, PressureUnit.BAR);
        assertEquals(VALUE1 + VALUE2, p.getValue().doubleValue(), ERROR);

        p.add(BigDecimal.valueOf(VALUE2), PressureUnit.BAR);
        assertEquals(VALUE1 + 2.0 * VALUE2, p.getValue().doubleValue(), ERROR);

        p.add(new Pressure(VALUE2 * 100.0, PressureUnit.KILOPASCAL));
        assertEquals(VALUE1 + 3.0 * VALUE2, p.getValue().doubleValue(), ERROR);

        final var result = new Pressure(0.0, PressureUnit.PASCAL);
        p.add(new Pressure(1.0, PressureUnit.PASCAL), result);
        assertEquals(p.getValue().doubleValue() * 100000.0 + 1.0, result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of doubles.
     */
    @Test
    void subtract_whenDoubles_returnsDifferenceInResultUnit() {
        final var result = Pressure.subtract(VALUE2, PressureUnit.BAR, VALUE1, PressureUnit.BAR,
                PressureUnit.KILOPASCAL);

        assertEquals((VALUE2 - VALUE1) * 100.0, result, ERROR);
    }

    /**
     * Checks static subtraction of numbers.
     */
    @Test
    void subtract_whenNumbers_returnsDifferenceInResultUnit() {
        final var result = Pressure.subtract(BigDecimal.valueOf(VALUE2), PressureUnit.BAR,
                BigDecimal.valueOf(VALUE1), PressureUnit.BAR, PressureUnit.KILOPASCAL);

        assertEquals((VALUE2 - VALUE1) * 100.0, result.doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of instances with mixed units.
     */
    @Test
    void subtract_whenInstances_storesDifferenceInResult() {
        final var p1 = new Pressure(VALUE2, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE1 * 100.0, PressureUnit.KILOPASCAL);
        final var result = new Pressure(0.0, PressureUnit.BAR);

        Pressure.subtract(p1, p2, result);

        assertEquals(PressureUnit.BAR, result.getUnit());
        assertEquals(VALUE2 - VALUE1, result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtraction variants that return new instances.
     */
    @Test
    void subtractAndReturnNew_whenAnyVariant_returnsNewInstanceWithDifference() {
        final var p1 = new Pressure(VALUE2, PressureUnit.BAR);
        final var p2 = new Pressure(VALUE1, PressureUnit.BAR);
        final var expected = (VALUE2 - VALUE1) * 100.0;

        final var r1 = Pressure.subtractAndReturnNew(p1, p2, PressureUnit.KILOPASCAL);
        final var r2 = p1.subtractAndReturnNew(VALUE1, PressureUnit.BAR, PressureUnit.KILOPASCAL);
        final var r3 = p1.subtractAndReturnNew(BigDecimal.valueOf(VALUE1), PressureUnit.BAR,
                PressureUnit.KILOPASCAL);
        final var r4 = p1.subtractAndReturnNew(p2, PressureUnit.KILOPASCAL);

        for (final var r : new Pressure[]{r1, r2, r3, r4}) {
            assertEquals(PressureUnit.KILOPASCAL, r.getUnit());
            assertEquals(expected, r.getValue().doubleValue(), ERROR);
        }
        assertEquals(VALUE2, p1.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks in-place subtraction variants.
     */
    @Test
    void subtract_whenInPlaceVariants_updatesInstance() {
        final var p = new Pressure(10.0, PressureUnit.BAR);

        p.subtract(VALUE1, PressureUnit.BAR);
        assertEquals(10.0 - VALUE1, p.getValue().doubleValue(), ERROR);

        p.subtract(BigDecimal.valueOf(VALUE1), PressureUnit.BAR);
        assertEquals(10.0 - 2.0 * VALUE1, p.getValue().doubleValue(), ERROR);

        p.subtract(new Pressure(VALUE1 * 100.0, PressureUnit.KILOPASCAL));
        assertEquals(10.0 - 3.0 * VALUE1, p.getValue().doubleValue(), ERROR);

        final var result = new Pressure(0.0, PressureUnit.BAR);
        p.subtract(new Pressure(VALUE1, PressureUnit.BAR), result);
        assertEquals(10.0 - 4.0 * VALUE1, result.getValue().doubleValue(), ERROR);
    }
}
