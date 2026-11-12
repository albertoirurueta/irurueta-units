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

/**
 * Unit tests for {@link Energy}.
 */
class EnergyTest {

    /**
     * Tolerance used when comparing floating point values.
     */
    private static final double ERROR = 1e-6;

    /**
     * Checks the empty constructor, the constructor with value and unit, and that a null value or unit is
     * rejected.
     */
    @Test
    void constructor_whenEmptyOrValueAndUnit_storesThemAndRejectsNulls() {
        // test empty constructor
        var s = new Energy();

        // check
        assertNull(s.getValue());
        assertNull(s.getUnit());

        // test constructor with value and unit
        s = new Energy(323, EnergyUnit.JOULE);

        // check
        assertEquals(323, s.getValue());
        assertEquals(EnergyUnit.JOULE, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> new Energy(null, EnergyUnit.JOULE));
        assertThrows(IllegalArgumentException.class, () -> new Energy(323, null));
    }

    /**
     * Checks equality semantics.
     */
    @Test
    void equals_whenDifferentInstances_comparesValueAndUnit() {
        final var value = new Random().nextDouble();
        final var s1 = new Energy(value, EnergyUnit.JOULE);
        final var s2 = new Energy(value, EnergyUnit.JOULE);
        final var s3 = new Energy(value + 1.0, EnergyUnit.JOULE);
        final var s4 = new Energy(value, EnergyUnit.KILOJOULE);

        //noinspection EqualsWithItself
        assertEquals(s1, s1);
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);

        assertNotEquals(null, s1);
        assertNotEquals(s1, new Object());
    }

    /**
     * Checks hash code semantics.
     */
    @Test
    void hashCode_whenDifferentInstances_isConsistentWithEquals() {
        final var value = new Random().nextDouble();
        final var s1 = new Energy(value, EnergyUnit.JOULE);
        final var s2 = new Energy(value, EnergyUnit.JOULE);
        final var s3 = new Energy(value + 1.0, EnergyUnit.JOULE);
        final var s4 = new Energy(value, EnergyUnit.KILOJOULE);

        assertEquals(s1.hashCode(), s1.hashCode());
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
    }

    /**
     * Checks tolerance based equality, including unit conversion and null handling.
     */
    @Test
    void equals_whenTolerance_comparesAfterUnitConversion() {
        final var value = new Random().nextDouble();
        final var s1 = new Energy(value, EnergyUnit.JOULE);
        final var s2 = new Energy(value, EnergyUnit.JOULE);
        final var s3 = new Energy(value + 0.5 * ERROR, EnergyUnit.JOULE);
        final var s4 = new Energy(value, EnergyUnit.KILOJOULE);
        final var s5 = new Energy(EnergyConverter.convert(value, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), EnergyUnit.KILOJOULE);

        assertTrue(s1.equals(s1, 0.0));
        assertTrue(s1.equals(s2, 0.0));
        assertFalse(s1.equals(s3, 0.0));
        assertTrue(s1.equals(s3, ERROR));
        assertFalse(s1.equals(s4, ERROR));
        assertTrue(s1.equals(s5, ERROR));

        assertFalse(s1.equals(null, ERROR));
    }

    /**
     * Checks getter and setter of value, and that a null value is rejected.
     */
    @Test
    void setValue_whenValueOrNull_updatesValueOrThrows() {
        final var s = new Energy(1, EnergyUnit.JOULE);

        // check
        assertEquals(1, s.getValue());

        // set new value
        s.setValue(2.5);

        // check
        assertEquals(2.5, s.getValue());

        //force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> s.setValue(null));
    }

    /**
     * Checks getter and setter of unit, and that a null unit is rejected.
     */
    @Test
    void setUnit_whenUnitOrNull_updatesUnitOrThrows() {
        final var s = new Energy(1, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s.getUnit());

        // set new value
        s.setUnit(EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.KILOJOULE, s.getUnit());

        // force IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> s.setUnit(null));
    }

    /**
     * Checks static addition of double values with conversion to an output unit.
     */
    @Test
    void add_whenStaticDoubles_returnsConvertedSum() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.add(value1, EnergyUnit.JOULE, value2,
                EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                        EnergyUnit.KILOJOULE), result, ERROR);
    }

    /**
     * Checks static addition of {@link Number} values with conversion to an output unit.
     */
    @Test
    void add_whenStaticNumbers_returnsConvertedSum() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.add(new BigDecimal(value1), EnergyUnit.JOULE,
                new BigDecimal(value2), EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                        EnergyUnit.KILOJOULE), result.doubleValue(), ERROR);
    }

    /**
     * Checks static addition of energies storing the result in a provided instance.
     */
    @Test
    void add_whenStaticEnergies_storesResultInProvidedInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        Energy.add(s1, s2, result);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static addition of energies returning a new instance.
     */
    @Test
    void addAndReturnNew_whenStaticEnergies_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = Energy.addAndReturnNew(s1, s2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding a double value and returning a new instance.
     */
    @Test
    void addAndReturnNew_whenDoubleValue_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = s1.addAndReturnNew(value2, EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding a {@link Number} value and returning a new instance.
     */
    @Test
    void addAndReturnNew_whenNumberValue_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = s1.addAndReturnNew(new BigDecimal(value2), EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding an energy and returning a new instance.
     */
    @Test
    void addAndReturnNew_whenEnergy_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = s1.addAndReturnNew(s2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(s2.getValue().doubleValue(), value2, 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding a double value in place.
     */
    @Test
    void add_whenDoubleValue_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        s1.add(value2, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding a {@link Number} value in place.
     */
    @Test
    void add_whenNumberValue_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        s1.add(new BigDecimal(value2), EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks adding an energy in place.
     */
    @Test
    void add_whenEnergy_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        s1.add(s2);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 + value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks adding an energy storing the result in a provided instance.
     */
    @Test
    void add_whenEnergyAndResult_storesResultInProvidedInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        s1.add(s2, result);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 + value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of double values with conversion to an output unit.
     */
    @Test
    void subtract_whenStaticDoubles_returnsConvertedDifference() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.subtract(value1, EnergyUnit.JOULE, value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result, ERROR);
    }

    /**
     * Checks static subtraction of {@link Number} values with conversion to an output unit.
     */
    @Test
    void subtract_whenStaticNumbers_returnsConvertedDifference() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var result = Energy.subtract(new BigDecimal(value1), EnergyUnit.JOULE, new BigDecimal(value2),
                EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of energies storing the result in a provided instance.
     */
    @Test
    void subtract_whenStaticEnergies_storesResultInProvidedInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        Energy.subtract(s1, s2, result);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks static subtraction of energies returning a new instance.
     */
    @Test
    void subtractAndReturnNew_whenStaticEnergies_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = Energy.subtractAndReturnNew(s1, s2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting a double value and returning a new instance.
     */
    @Test
    void subtractAndReturnNew_whenDoubleValue_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = s1.subtractAndReturnNew(value2, EnergyUnit.JOULE, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting a {@link Number} value and returning a new instance.
     */
    @Test
    void subtractAndReturnNew_whenNumberValue_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        final var result = s1.subtractAndReturnNew(new BigDecimal(value2), EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting an energy and returning a new instance.
     */
    @Test
    void subtractAndReturnNew_whenEnergy_returnsNewEnergy() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = s1.subtractAndReturnNew(s2, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting a double value in place.
     */
    @Test
    void subtract_whenDoubleValue_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        s1.subtract(value2, EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting a {@link Number} value in place.
     */
    @Test
    void subtract_whenNumberValue_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);

        s1.subtract(new BigDecimal(value2), EnergyUnit.JOULE);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks subtracting an energy in place.
     */
    @Test
    void subtract_whenEnergy_updatesInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        s1.subtract(s2);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1 - value2, s1.getValue().doubleValue(), ERROR);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks subtracting an energy storing the result in a provided instance.
     */
    @Test
    void subtract_whenEnergyAndResult_storesResultInProvidedInstance() {
        final var r = new Random();
        final var value1 = r.nextDouble();
        final var value2 = r.nextDouble();

        final var s1 = new Energy(value1, EnergyUnit.JOULE);
        final var s2 = new Energy(value2, EnergyUnit.JOULE);

        final var result = new Energy(0.0, EnergyUnit.KILOJOULE);
        s1.subtract(s2, result);

        // check
        assertEquals(EnergyUnit.JOULE, s1.getUnit());
        assertEquals(value1, s1.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.JOULE, s2.getUnit());
        assertEquals(value2, s2.getValue().doubleValue(), 0.0);

        assertEquals(EnergyUnit.KILOJOULE, result.getUnit());
        assertEquals(EnergyConverter.convert(value1 - value2, EnergyUnit.JOULE,
                EnergyUnit.KILOJOULE), result.getValue().doubleValue(), ERROR);
    }

    /**
     * Checks that serialization and deserialization preserve the measurement.
     *
     * @throws IOException never happens.
     * @throws ClassNotFoundException never happens.
     */
    @Test
    void serialize_whenRoundTrip_returnsEqualInstance() throws IOException, ClassNotFoundException {
        final var value = new Random().nextDouble();
        final var s1 = new Energy(value, EnergyUnit.JOULE);

        final var bytes = SerializationHelper.serialize(s1);
        final var s2 = SerializationHelper.deserialize(bytes);

        assertEquals(s1, s2);
        assertNotSame(s1, s2);
    }
}
