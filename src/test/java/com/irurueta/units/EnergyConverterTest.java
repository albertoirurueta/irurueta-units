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

/**
 * Unit tests for {@link EnergyConverter}.
 */
class EnergyConverterTest {

    /**
     * Number of joules in 1 kilojoule.
     */
    private static final double JOULES_PER_KILOJOULE = 1000.0;

    /**
     * Number of joules in 1 megajoule.
     */
    private static final double JOULES_PER_MEGAJOULE = 1.0e6;

    /**
     * Number of joules in 1 calorie.
     */
    private static final double JOULES_PER_CALORIE = 4.184;

    /**
     * Number of joules in 1 kilocalorie.
     */
    private static final double JOULES_PER_KILOCALORIE = 4184.0;

    /**
     * Number of joules in 1 kilowatt-hour.
     */
    private static final double JOULES_PER_KILOWATT_HOUR = 3.6e6;

    /**
     * Number of joules in 1 BTU.
     */
    private static final double JOULES_PER_BTU = 1055.05585262;

    /**
     * Tolerance used when comparing floating point values.
     */
    private static final double ERROR = 1e-6;

    /**
     * Checks conversions against well-known energy equivalences.
     */
    @Test
    void convert_whenWellKnownEquivalences_returnsExpectedValues() {
        assertEquals(4184.0, EnergyConverter.convert(1.0, EnergyUnit.KILOCALORIE, EnergyUnit.JOULE), ERROR);
        assertEquals(1000.0, EnergyConverter.convert(1.0, EnergyUnit.KILOCALORIE, EnergyUnit.CALORIE), ERROR);
        assertEquals(3600.0, EnergyConverter.convert(1.0, EnergyUnit.KILOWATT_HOUR, EnergyUnit.KILOJOULE), ERROR);
        assertEquals(3412.14163, EnergyConverter.convert(1.0, EnergyUnit.KILOWATT_HOUR, EnergyUnit.BTU), 1e-3);
        assertEquals(1055.05585262, EnergyConverter.convert(1.0, EnergyUnit.BTU, EnergyUnit.JOULE), ERROR);
    }

    /**
     * Checks conversion between joules and kilojoules in both directions.
     */
    @Test
    void jouleToKilojoule_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOJOULE, EnergyConverter.jouleToKilojoule(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOJOULE, EnergyConverter.kilojouleToJoule(inputValue), ERROR);
    }

    /**
     * Checks conversion between joules and megajoules in both directions.
     */
    @Test
    void jouleToMegajoule_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_MEGAJOULE, EnergyConverter.jouleToMegajoule(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_MEGAJOULE, EnergyConverter.megajouleToJoule(inputValue), ERROR);
        assertEquals(3.6, EnergyConverter.convert(1.0, EnergyUnit.KILOWATT_HOUR, EnergyUnit.MEGAJOULE), ERROR);
    }

    /**
     * Checks conversion between joules and calories in both directions.
     */
    @Test
    void jouleToCalorie_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_CALORIE, EnergyConverter.jouleToCalorie(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_CALORIE, EnergyConverter.calorieToJoule(inputValue), ERROR);
    }

    /**
     * Checks conversion between joules and kilocalories in both directions.
     */
    @Test
    void jouleToKilocalorie_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOCALORIE, EnergyConverter.jouleToKilocalorie(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOCALORIE, EnergyConverter.kilocalorieToJoule(inputValue), ERROR);
    }

    /**
     * Checks conversion between joules and kilowatt-hours in both directions.
     */
    @Test
    void jouleToKilowattHour_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_KILOWATT_HOUR, EnergyConverter.jouleToKilowattHour(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_KILOWATT_HOUR, EnergyConverter.kilowattHourToJoule(inputValue), ERROR);
    }

    /**
     * Checks conversion between joules and BTU in both directions.
     */
    @Test
    void jouleToBtu_whenRandomValue_returnsExpectedResult() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / JOULES_PER_BTU, EnergyConverter.jouleToBtu(inputValue), ERROR);
        assertEquals(inputValue * JOULES_PER_BTU, EnergyConverter.btuToJoule(inputValue), ERROR);
    }

    /**
     * Checks every unit pair against a conversion through joules, and that converting back recovers the
     * original value.
     */
    @Test
    void convert_whenDoubleAndAllUnitPairs_matchesConversionThroughJoulesAndRoundTrips() {
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

    /**
     * Checks conversion of {@link Number} values.
     */
    @Test
    void convert_whenNumber_returnsExpectedValue() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.JOULE).doubleValue(), ERROR);
        assertEquals(inputValue.doubleValue() / JOULES_PER_KILOJOULE,
                EnergyConverter.convert(inputValue, EnergyUnit.JOULE, EnergyUnit.KILOJOULE).doubleValue(), ERROR);
    }

    /**
     * Checks that converting an energy stores the result in the provided instance without modifying the input.
     */
    @Test
    void convert_whenEnergyAndResult_storesResultInProvidedInstance() {
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

    /**
     * Checks that converting an energy to a unit updates that same instance.
     */
    @Test
    void convert_whenEnergyAndUnit_updatesInstance() {
        final var value = new Random().nextDouble();
        final var energy = new Energy(value, EnergyUnit.JOULE);

        EnergyConverter.convert(energy, EnergyUnit.KILOJOULE);

        // check
        assertEquals(EnergyUnit.KILOJOULE, energy.getUnit());
        assertEquals(EnergyConverter.convert(value, EnergyUnit.JOULE, EnergyUnit.KILOJOULE),
                energy.getValue().doubleValue(), 0.0);
    }

    /**
     * Checks that converting and returning a new energy leaves the input unchanged.
     */
    @Test
    void convertAndReturnNew_whenEnergy_returnsNewConvertedEnergy() {
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

    /**
     * Checks that converting into an output energy uses the unit already set on the output.
     */
    @Test
    void convert_whenEnergyAndOutputEnergy_usesOutputUnit() {
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

    /**
     * Checks that null units or a null value are rejected.
     */
    @Test
    void convert_whenNullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> EnergyConverter.convert(1.0, null, EnergyUnit.JOULE));
        assertThrows(NullPointerException.class, () -> EnergyConverter.convert(1.0, EnergyUnit.JOULE, null));
        assertThrows(NullPointerException.class,
                () -> EnergyConverter.convert((Number) null, EnergyUnit.JOULE, EnergyUnit.JOULE));
    }

    /**
     * Converts a value expressed in the given unit to joules.
     *
     * @param value value to convert.
     * @param unit unit of the value.
     * @return value expressed in joules.
     */
    private static double toJoules(final double value, final EnergyUnit unit) {
        return switch (unit) {
            case KILOJOULE -> value * JOULES_PER_KILOJOULE;
            case MEGAJOULE -> value * JOULES_PER_MEGAJOULE;
            case CALORIE -> value * JOULES_PER_CALORIE;
            case KILOCALORIE -> value * JOULES_PER_KILOCALORIE;
            case KILOWATT_HOUR -> value * JOULES_PER_KILOWATT_HOUR;
            case BTU -> value * JOULES_PER_BTU;
            default -> value;
        };
    }

    /**
     * Converts a value expressed in joules to the given unit.
     *
     * @param joules value in joules.
     * @param unit target unit.
     * @return value expressed in the target unit.
     */
    private static double fromJoules(final double joules, final EnergyUnit unit) {
        return switch (unit) {
            case KILOJOULE -> joules / JOULES_PER_KILOJOULE;
            case MEGAJOULE -> joules / JOULES_PER_MEGAJOULE;
            case CALORIE -> joules / JOULES_PER_CALORIE;
            case KILOCALORIE -> joules / JOULES_PER_KILOCALORIE;
            case KILOWATT_HOUR -> joules / JOULES_PER_KILOWATT_HOUR;
            case BTU -> joules / JOULES_PER_BTU;
            default -> joules;
        };
    }
}
