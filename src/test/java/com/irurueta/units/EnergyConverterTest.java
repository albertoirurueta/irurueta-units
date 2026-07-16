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
    private static final double JOULES_PER_KILOWATT_HOUR = 3600000.0;
    private static final double JOULES_PER_BTU = 1055.05585262;

    private static final double ERROR = 1e-6;

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

        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.JOULE), ERROR);
        assertEquals(EnergyConverter.jouleToKilojoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(EnergyConverter.jouleToCalorie(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.CALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilocalorie(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilowattHour(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(EnergyConverter.jouleToBtu(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.BTU), ERROR);

        assertEquals(EnergyConverter.kilojouleToJoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.JOULE), ERROR);
        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(EnergyConverter.jouleToCalorie(
                EnergyConverter.kilojouleToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.CALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilocalorie(
                EnergyConverter.kilojouleToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilowattHour(
                EnergyConverter.kilojouleToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(EnergyConverter.jouleToBtu(
                EnergyConverter.kilojouleToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOJOULE, EnergyUnit.BTU), ERROR);

        assertEquals(EnergyConverter.calorieToJoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.JOULE), ERROR);
        assertEquals(EnergyConverter.jouleToKilojoule(
                EnergyConverter.calorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.CALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilocalorie(
                EnergyConverter.calorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilowattHour(
                EnergyConverter.calorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(EnergyConverter.jouleToBtu(
                EnergyConverter.calorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.CALORIE, EnergyUnit.BTU), ERROR);

        assertEquals(EnergyConverter.kilocalorieToJoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.JOULE), ERROR);
        assertEquals(EnergyConverter.jouleToKilojoule(
                EnergyConverter.kilocalorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(EnergyConverter.jouleToCalorie(
                EnergyConverter.kilocalorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.CALORIE), ERROR);
        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilowattHour(
                EnergyConverter.kilocalorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(EnergyConverter.jouleToBtu(
                EnergyConverter.kilocalorieToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOCALORIE, EnergyUnit.BTU), ERROR);

        assertEquals(EnergyConverter.kilowattHourToJoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.JOULE), ERROR);
        assertEquals(EnergyConverter.jouleToKilojoule(
                EnergyConverter.kilowattHourToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(EnergyConverter.jouleToCalorie(
                EnergyConverter.kilowattHourToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.CALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilocalorie(
                EnergyConverter.kilowattHourToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(EnergyConverter.jouleToBtu(
                EnergyConverter.kilowattHourToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.KILOWATT_HOUR, EnergyUnit.BTU), ERROR);

        assertEquals(EnergyConverter.btuToJoule(inputValue),
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.JOULE), ERROR);
        assertEquals(EnergyConverter.jouleToKilojoule(
                EnergyConverter.btuToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(EnergyConverter.jouleToCalorie(
                EnergyConverter.btuToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.CALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilocalorie(
                EnergyConverter.btuToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.KILOCALORIE), ERROR);
        assertEquals(EnergyConverter.jouleToKilowattHour(
                EnergyConverter.btuToJoule(inputValue)),
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.KILOWATT_HOUR), ERROR);
        assertEquals(inputValue,
                EnergyConverter.convert(inputValue, EnergyUnit.BTU, EnergyUnit.BTU), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.JOULE)
                        .doubleValue(), ERROR);
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
        assertEquals(EnergyConverter.convert(value, inputEnergy.getUnit(),
                outputEnergy.getUnit()), outputEnergy.getValue().doubleValue(), 0.0);
    }
}
