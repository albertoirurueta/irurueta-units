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
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EnergyConverterTest {

    private static final double JOULES_PER_KILOJOULE = 1000.0;
    private static final double JOULES_PER_CALORIE = 4.184;
    private static final double JOULES_PER_KILOCALORIE = 4184.0;
    private static final double JOULES_PER_KILOWATT_HOUR = 3.6e6;
    private static final double JOULES_PER_BTU = 1055.05585262;

    private static final double ERROR = 1e-6;

    @Test
    void testKnownValues() {
        assertEquals(4184.0, EnergyConverter.convert(1.0, EnergyUnit.KILOCALORIE, EnergyUnit.JOULE), ERROR);
        assertEquals(1000.0, EnergyConverter.convert(1.0, EnergyUnit.KILOCALORIE, EnergyUnit.CALORIE), ERROR);
        assertEquals(3600.0, EnergyConverter.convert(1.0, EnergyUnit.KILOWATT_HOUR, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(3412.14163, EnergyConverter.convert(1.0, EnergyUnit.KILOWATT_HOUR, EnergyUnit.BTU), 1e-3);
        assertEquals(1055.05585262, EnergyConverter.convert(1.0, EnergyUnit.BTU, EnergyUnit.JOULE), ERROR);
    }

    @Test
    void testJouleKilojoule() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOJOULE, EnergyConverter.jouleToKilojoule(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOJOULE, EnergyConverter.kilojouleToJoule(inputValue), ERROR);
    }

    @Test
    void testJouleCalorie() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_CALORIE, EnergyConverter.jouleToCalorie(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_CALORIE, EnergyConverter.calorieToJoule(inputValue), ERROR);
    }

    @Test
    void testJouleKilocalorie() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOCALORIE, EnergyConverter.jouleToKilocalorie(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOCALORIE, EnergyConverter.kilocalorieToJoule(inputValue), ERROR);
    }

    @Test
    void testJouleKilowattHour() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOWATT_HOUR, EnergyConverter.jouleToKilowattHour(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOWATT_HOUR, EnergyConverter.kilowattHourToJoule(inputValue), ERROR);
    }

    @Test
    void testJouleBtu() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_BTU, EnergyConverter.jouleToBtu(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_BTU, EnergyConverter.btuToJoule(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        for (final var from : EnergyUnit.values()) {
            for (final var to : EnergyUnit.values()) {
                final var expected = fromJoules(toJoules(inputValue, from), to);
                final var converted = EnergyConverter.convert(inputValue, from, to);
                assertEquals(expected, converted, ERROR);

                // round trip
                assertEquals(inputValue, EnergyConverter.convert(converted, to, from), ERROR);
            }
        }
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.JOULE).doubleValue(), ERROR);
        assertEquals(inputValue.doubleValue() / JOULES_PER_KILOJOULE,
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.KILOJOULE).doubleValue(), ERROR);
    }

    @Test
    void testConvertEnergy() {
        final var value = new Random().nextDouble();
        final var inputEnergy = new Energy(value, EnergyUnit.JOULE);

        final var outputEnergy = new Energy();
        EnergyConverter.convert(inputEnergy, EnergyUnit.KILOJOULE, outputEnergy);

        // check
        assertEquals(value, inputEnergy.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.JOULE, inputEnergy.getUnit());

        assertEquals(EnergyUnit.KILOJOULE, outputEnergy.getUnit());
        assertEquals(EnergyConverter.convert(value, inputEnergy.getUnit(), outputEnergy.getUnit()),
                outputEnergy.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateEnergy() {
        final var value = new Random().nextDouble();
        final var energy = new Energy(value, EnergyUnit.JOULE);

        EnergyConverter.convert(energy, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.KILOJOULE, energy.getUnit());
        assertEquals(EnergyConverter.convert(value, EnergyUnit.JOULE, EnergyUnit.KILOJOULE),
                energy.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewEnergy() {
        final var value = new Random().nextDouble();
        final var inputEnergy = new Energy(value, EnergyUnit.JOULE);

        final var outputEnergy = EnergyConverter.convertAndReturnNew(inputEnergy, EnergyUnit.KILOJOULE);

        // check
        assertEquals(value, inputEnergy.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.JOULE, inputEnergy.getUnit());

        assertEquals(EnergyUnit.KILOJOULE, outputEnergy.getUnit());
        assertEquals(EnergyConverter.convert(value, inputEnergy.getUnit(), outputEnergy.getUnit()),
                outputEnergy.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputEnergyUnit() {
        final var value = new Random().nextDouble();
        final var inputEnergy = new Energy(value, EnergyUnit.JOULE);

        final var outputEnergy = new Energy();
        outputEnergy.setUnit(EnergyUnit.KILOJOULE);
        EnergyConverter.convert(inputEnergy, outputEnergy);

        // check
        assertEquals(value, inputEnergy.getValue().doubleValue(), 0.0);
        assertEquals(EnergyUnit.JOULE, inputEnergy.getUnit());

        assertEquals(EnergyUnit.KILOJOULE, outputEnergy.getUnit());
        assertEquals(EnergyConverter.convert(value, inputEnergy.getUnit(), outputEnergy.getUnit()),
                outputEnergy.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertNullArguments() {
        assertThrows(NullPointerException.class, () -> EnergyConverter.convert(1.0, null, EnergyUnit.JOULE));
        assertThrows(NullPointerException.class, () -> EnergyConverter.convert(1.0, EnergyUnit.JOULE, null));
        assertThrows(NullPointerException.class,
                () -> EnergyConverter.convert((Number) null, EnergyUnit.JOULE, EnergyUnit.JOULE));
    }

    private static double toJoules(final double value, final EnergyUnit unit) {
        return switch (unit) {
            case KILOJOULE -> value * JOULES_PER_KILOJOULE;
            case CALORIE -> value * JOULES_PER_CALORIE;
            case KILOCALORIE -> value * JOULES_PER_KILOCALORIE;
            case KILOWATT_HOUR -> value * JOULES_PER_KILOWATT_HOUR;
            case BTU -> value * JOULES_PER_BTU;
            default -> value;
        };
    }

    private static double fromJoules(final double joules, final EnergyUnit unit) {
        return switch (unit) {
            case KILOJOULE -> joules / JOULES_PER_KILOJOULE;
            case CALORIE -> joules / JOULES_PER_CALORIE;
            case KILOCALORIE -> joules / JOULES_PER_KILOCALORIE;
            case KILOWATT_HOUR -> joules / JOULES_PER_KILOWATT_HOUR;
            case BTU -> joules / JOULES_PER_BTU;
            default -> joules;
        };
    }
}
