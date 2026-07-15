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

import java.math.BigDecimal;

/**
 * Does energy conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying energy measurements.
 */
@SuppressWarnings("WeakerAccess")
public class EnergyConverter {

    /**
     * Number of joules in 1 kilojoule.
     */
    static final double JOULES_PER_KILOJOULE = 1000.0;

    /**
     * Number of joules in 1 calorie.
     */
    static final double JOULES_PER_CALORIE = 4.184;

    /**
     * Number of joules in 1 kilocalorie.
     */
    static final double JOULES_PER_KILOCALORIE = 4184.0;

    /**
     * Number of joules in 1 kilowatt-hour.
     */
    static final double JOULES_PER_KILOWATT_HOUR = 3600000.0;

    /**
     * Number of joules in 1 BTU.
     */
    static final double JOULES_PER_BTU = 1055.05585262;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private EnergyConverter() {
    }

    /**
     * Converts an energy instance to provided output energy unit.
     *
     * @param input  input energy to be converted.
     * @param output output energy where result will be stored containing output unit.
     */
    public static void convert(final Energy input, final Energy output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts an energy instance to requested output unit.
     *
     * @param input      input energy to be converted.
     * @param outputUnit requested output unit.
     * @return converted energy.
     */
    public static Energy convertAndReturnNew(
            final Energy input, final EnergyUnit outputUnit) {
        final var result = new Energy();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates an energy to requested output unit.
     *
     * @param energy     input energy to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Energy energy, final EnergyUnit outputUnit) {
        convert(energy, outputUnit, energy);
    }

    /**
     * Converts an energy to requested output unit.
     *
     * @param input      input energy to be converted.
     * @param outputUnit requested output unit.
     * @param result     energy instance where result will be stored.
     */
    public static void convert(
            final Energy input, final EnergyUnit outputUnit, final Energy result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts an energy value from input unit to provided output unit.
     *
     * @param input      energy value.
     * @param inputUnit  input energy unit.
     * @param outputUnit output energy unit.
     * @return converted energy value.
     */
    public static Number convert(final Number input, final EnergyUnit inputUnit, final EnergyUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts an energy value from input unit to provided output unit.
     *
     * @param input      energy value.
     * @param inputUnit  input energy unit.
     * @param outputUnit output energy unit.
     * @return converted energy value.
     */
    public static double convert(final double input, final EnergyUnit inputUnit, final EnergyUnit outputUnit) {
        //convert to joules
        final var joules = switch (inputUnit) {
            case KILOJOULE -> kilojouleToJoule(input);
            case CALORIE -> calorieToJoule(input);
            case KILOCALORIE -> kilocalorieToJoule(input);
            case KILOWATT_HOUR -> kilowattHourToJoule(input);
            case BTU -> btuToJoule(input);
            default -> input;
        };

        //convert from joules to required output unit
        return switch (outputUnit) {
            case KILOJOULE -> jouleToKilojoule(joules);
            case CALORIE -> jouleToCalorie(joules);
            case KILOCALORIE -> jouleToKilocalorie(joules);
            case KILOWATT_HOUR -> jouleToKilowattHour(joules);
            case BTU -> jouleToBtu(joules);
            default -> joules;
        };
    }

    /**
     * Converts provided kilojoule value to joules.
     *
     * @param kilojoule kilojoule value.
     * @return same energy converted to joules.
     */
    public static double kilojouleToJoule(final double kilojoule) {
        return kilojoule * JOULES_PER_KILOJOULE;
    }

    /**
     * Converts provided joule value to kilojoule.
     *
     * @param joule joule value.
     * @return same energy converted to kilojoule.
     */
    public static double jouleToKilojoule(final double joule) {
        return joule / JOULES_PER_KILOJOULE;
    }

    /**
     * Converts provided calorie value to joules.
     *
     * @param calorie calorie value.
     * @return same energy converted to joules.
     */
    public static double calorieToJoule(final double calorie) {
        return calorie * JOULES_PER_CALORIE;
    }

    /**
     * Converts provided joule value to calorie.
     *
     * @param joule joule value.
     * @return same energy converted to calorie.
     */
    public static double jouleToCalorie(final double joule) {
        return joule / JOULES_PER_CALORIE;
    }

    /**
     * Converts provided kilocalorie value to joules.
     *
     * @param kilocalorie kilocalorie value.
     * @return same energy converted to joules.
     */
    public static double kilocalorieToJoule(final double kilocalorie) {
        return kilocalorie * JOULES_PER_KILOCALORIE;
    }

    /**
     * Converts provided joule value to kilocalorie.
     *
     * @param joule joule value.
     * @return same energy converted to kilocalorie.
     */
    public static double jouleToKilocalorie(final double joule) {
        return joule / JOULES_PER_KILOCALORIE;
    }

    /**
     * Converts provided kilowatt-hour value to joules.
     *
     * @param kilowattHour kilowatt-hour value.
     * @return same energy converted to joules.
     */
    public static double kilowattHourToJoule(final double kilowattHour) {
        return kilowattHour * JOULES_PER_KILOWATT_HOUR;
    }

    /**
     * Converts provided joule value to kilowatt-hour.
     *
     * @param joule joule value.
     * @return same energy converted to kilowatt-hour.
     */
    public static double jouleToKilowattHour(final double joule) {
        return joule / JOULES_PER_KILOWATT_HOUR;
    }

    /**
     * Converts provided BTU value to joules.
     *
     * @param btu BTU value.
     * @return same energy converted to joules.
     */
    public static double btuToJoule(final double btu) {
        return btu * JOULES_PER_BTU;
    }

    /**
     * Converts provided joule value to BTU.
     *
     * @param joule joule value.
     * @return same energy converted to BTU.
     */
    public static double jouleToBtu(final double joule) {
        return joule / JOULES_PER_BTU;
    }
}
