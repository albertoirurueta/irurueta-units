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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Force}.
 */
class ForceTest {

    /**
     * Tolerance used when comparing floating point values.
     */
    private static final double ERROR = 1e-6;

    /**
     * First value used in tests.
     */
    private static final double VALUE1 = 12.5;

    /**
     * Second value used in tests.
     */
    private static final double VALUE2 = 3.25;

    /**
     * Value used to build a force with an arbitrary value.
     */
    private static final int ARBITRARY_VALUE = 323;

    /**
     * Checks that the empty constructor leaves value and unit unset.
     */
    @Test
    void constructor_whenEmpty_hasNoValueAndUnit() {
        final var f = new Force();

        assertNull(f.getValue());
        assertNull(f.getUnit());
    }

    /**
     * Checks that the constructor with value and unit stores them.
     */
    @Test
    void constructor_whenValueAndUnit_storesThem() {
        final var f = new Force(ARBITRARY_VALUE, ForceUnit.NEWTON);

        assertEquals(ARBITRARY_VALUE, f.getValue());
        assertEquals(ForceUnit.NEWTON, f.getUnit());
    }

    /**
     * Checks that a null value or unit is rejected.
     */
    @Test
    void constructor_whenNullValueOrUnit_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Force(null, ForceUnit.NEWTON));
        assertThrows(IllegalArgumentException.class, () -> new Force(ARBITRARY_VALUE, null));
    }

    /**
     * Checks equality semantics.
     */
    @Test
    void equals_whenDifferentInstances_comparesValueAndUnit() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f3 = new Force(VALUE1 + 1.0, ForceUnit.NEWTON);
        final var f4 = new Force(VALUE1, ForceUnit.KILONEWTON);

        //noinspection EqualsWithItself
        assertEquals(f1, f1);
        assertEquals(f1, f2);
        assertNotEquals(f1, f3);
        assertNotEquals(f1, f4);
        assertNotEquals(null, f1);
        assertNotEquals(f1, new Object());
    }

    /**
     * Checks hash code semantics.
     */
    @Test
    void hashCode_whenDifferentInstances_isConsistentWithEquals() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f3 = new Force(VALUE1 + 1.0, ForceUnit.NEWTON);
        final var f4 = new Force(VALUE1, ForceUnit.KILONEWTON);

        assertEquals(f1.hashCode(), f1.hashCode());
        assertEquals(f1.hashCode(), f2.hashCode());
        assertNotEquals(f1.hashCode(), f3.hashCode());
        assertNotEquals(f1.hashCode(), f4.hashCode());
    }

    /**
     * Checks tolerance based equality, including unit conversion and null handling.
     */
    @Test
    void equals_whenTolerance_comparesAfterUnitConversion() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f3 = new Force(VALUE1 + 0.5 * ERROR, ForceUnit.NEWTON);
        final var f4 = new Force(VALUE1, ForceUnit.KILONEWTON);
        final var f5 = new Force(ForceConverter.convert(VALUE1, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                ForceUnit.KILONEWTON);
        final var f6 = new Force(ForceConverter.convert(VALUE1, ForceUnit.NEWTON, ForceUnit.POUND_FORCE),
                ForceUnit.POUND_FORCE);

        assertTrue(f1.equals(f1, 0.0));
        assertTrue(f1.equals(f2, 0.0));
        assertFalse(f1.equals(f3, 0.0));
        assertTrue(f1.equals(f3, ERROR));
        assertFalse(f1.equals(f4, ERROR));
        assertTrue(f1.equals(f5, ERROR));
        assertTrue(f1.equals(f6, ERROR));
        assertFalse(f1.equals(null, ERROR));
    }

    /**
     * Checks getter and setter of value.
     */
    @Test
    void setValue_whenValid_updatesValue() {
        final var f = new Force(1, ForceUnit.NEWTON);
        assertEquals(1, f.getValue());

        f.setValue(2.5);

        assertEquals(2.5, f.getValue());
    }

    /**
     * Checks that a null value is rejected.
     */
    @Test
    void setValue_whenNull_throwsIllegalArgumentException() {
        final var f = new Force(1, ForceUnit.NEWTON);

        assertThrows(IllegalArgumentException.class, () -> f.setValue(null));
    }

    /**
     * Checks getter and setter of unit.
     */
    @Test
    void setUnit_whenValid_updatesUnit() {
        final var f = new Force(1, ForceUnit.NEWTON);
        assertEquals(ForceUnit.NEWTON, f.getUnit());

        f.setUnit(ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.KILONEWTON, f.getUnit());
    }

    /**
     * Checks that a null unit is rejected.
     */
    @Test
    void setUnit_whenNull_throwsIllegalArgumentException() {
        final var f = new Force(1, ForceUnit.NEWTON);

        assertThrows(IllegalArgumentException.class, () -> f.setUnit(null));
    }

    /**
     * Checks static addition of primitive values.
     */
    @Test
    void add_whenStaticDoubles_returnsConvertedSum() {
        final var result = Force.add(VALUE1, ForceUnit.NEWTON, VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON), result,
                ERROR);
    }

    /**
     * Checks static addition of numbers with different units.
     */
    @Test
    void add_whenStaticNumbersWithDifferentUnits_returnsConvertedSum() {
        final var result = Force.add(BigDecimal.valueOf(VALUE1), ForceUnit.KILONEWTON,
                BigDecimal.valueOf(VALUE2), ForceUnit.POUND_FORCE, ForceUnit.NEWTON);

        final var expected = ForceConverter.convert(VALUE1, ForceUnit.KILONEWTON, ForceUnit.NEWTON)
                + ForceConverter.convert(VALUE2, ForceUnit.POUND_FORCE, ForceUnit.NEWTON);
        assertEquals(expected, result.doubleValue(), ERROR);
    }

    /**
     * Checks static addition of forces into a result instance.
     */
    @Test
    void add_whenStaticForces_storesResultInProvidedInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);
        final var result = new Force(0.0, ForceUnit.KILONEWTON);

        Force.add(f1, f2, result);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static addAndReturnNew of two forces.
     */
    @Test
    void addAndReturnNew_whenStaticForces_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        final var result = Force.addAndReturnNew(f1, f2, ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks addAndReturnNew with a double value.
     */
    @Test
    void addAndReturnNew_whenDoubleValue_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks addAndReturnNew with a number value.
     */
    @Test
    void addAndReturnNew_whenNumberValue_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(BigDecimal.valueOf(VALUE2), ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks addAndReturnNew with another force.
     */
    @Test
    void addAndReturnNew_whenForce_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        final var result = f1.addAndReturnNew(f2, ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(ForceUnit.NEWTON, f2.getUnit());
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place addition of a double value.
     */
    @Test
    void add_whenDoubleValue_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        f1.add(VALUE2, ForceUnit.NEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1 + VALUE2, f1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place addition of a number value.
     */
    @Test
    void add_whenNumberValue_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        f1.add(BigDecimal.valueOf(VALUE2), ForceUnit.NEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1 + VALUE2, f1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place addition of another force.
     */
    @Test
    void add_whenForce_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        f1.add(f2);

        assertEquals(VALUE1 + VALUE2, f1.getValue().doubleValue(), ERROR);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks addition of another force storing the result in a provided instance.
     */
    @Test
    void add_whenForceAndResult_storesResultInProvidedInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);
        final var result = new Force(0.0, ForceUnit.KILONEWTON);

        f1.add(f2, result);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 + VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of primitive values.
     */
    @Test
    void subtract_whenStaticDoubles_returnsConvertedDifference() {
        final var result = Force.subtract(VALUE1, ForceUnit.NEWTON, VALUE2, ForceUnit.NEWTON,
                ForceUnit.KILONEWTON);

        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON), result,
                ERROR);
    }

    /**
     * Checks static subtraction of numbers with different units.
     */
    @Test
    void subtract_whenStaticNumbersWithDifferentUnits_returnsConvertedDifference() {
        final var result = Force.subtract(BigDecimal.valueOf(VALUE1), ForceUnit.KILONEWTON,
                BigDecimal.valueOf(VALUE2), ForceUnit.KILOGRAM_FORCE, ForceUnit.NEWTON);

        final var expected = ForceConverter.convert(VALUE1, ForceUnit.KILONEWTON, ForceUnit.NEWTON)
                - ForceConverter.convert(VALUE2, ForceUnit.KILOGRAM_FORCE, ForceUnit.NEWTON);
        assertEquals(expected, result.doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of forces into a result instance.
     */
    @Test
    void subtract_whenStaticForces_storesResultInProvidedInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);
        final var result = new Force(0.0, ForceUnit.KILONEWTON);

        Force.subtract(f1, f2, result);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtractAndReturnNew of two forces.
     */
    @Test
    void subtractAndReturnNew_whenStaticForces_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        final var result = Force.subtractAndReturnNew(f1, f2, ForceUnit.KILONEWTON);

        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtractAndReturnNew with a double value.
     */
    @Test
    void subtractAndReturnNew_whenDoubleValue_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtractAndReturnNew with a number value.
     */
    @Test
    void subtractAndReturnNew_whenNumberValue_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(BigDecimal.valueOf(VALUE2), ForceUnit.NEWTON,
                ForceUnit.KILONEWTON);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtractAndReturnNew with another force.
     */
    @Test
    void subtractAndReturnNew_whenForce_returnsNewForce() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        final var result = f1.subtractAndReturnNew(f2, ForceUnit.KILONEWTON);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place subtraction of a double value.
     */
    @Test
    void subtract_whenDoubleValue_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        f1.subtract(VALUE2, ForceUnit.NEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1 - VALUE2, f1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place subtraction of a number value.
     */
    @Test
    void subtract_whenNumberValue_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        f1.subtract(BigDecimal.valueOf(VALUE2), ForceUnit.NEWTON);

        assertEquals(ForceUnit.NEWTON, f1.getUnit());
        assertEquals(VALUE1 - VALUE2, f1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks in-place subtraction of another force.
     */
    @Test
    void subtract_whenForce_updatesInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);

        f1.subtract(f2);

        assertEquals(VALUE1 - VALUE2, f1.getValue().doubleValue(), ERROR);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks subtraction of another force storing the result in a provided instance.
     */
    @Test
    void subtract_whenForceAndResult_storesResultInProvidedInstance() {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);
        final var f2 = new Force(VALUE2, ForceUnit.NEWTON);
        final var result = new Force(0.0, ForceUnit.KILONEWTON);

        f1.subtract(f2, result);

        assertEquals(VALUE1, f1.getValue().doubleValue(), 0.0);
        assertEquals(VALUE2, f2.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.KILONEWTON, result.getUnit());
        assertEquals(ForceConverter.convert(VALUE1 - VALUE2, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks that a force survives a serialization round trip.
     *
     * @throws IOException            if serialization fails.
     * @throws ClassNotFoundException if deserialization fails.
     */
    @Test
    void serialize_whenRoundTrip_returnsEqualInstance() throws IOException, ClassNotFoundException {
        final var f1 = new Force(VALUE1, ForceUnit.NEWTON);

        final var bytes = SerializationHelper.serialize(f1);
        final var f2 = SerializationHelper.deserialize(bytes);

        assertEquals(f1, f2);
        assertNotSame(f1, f2);
    }
}
