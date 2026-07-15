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
 * Does power conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying power measurements.
 */
@SuppressWarnings("WeakerAccess")
public class PowerConverter {

    /**
     * Number of watts in 1 kilowatt.
     */
    static final double WATTS_PER_KILOWATT = 1000.0;

    /**
     * Number of watts in 1 megawatt.
     */
    static final double WATTS_PER_MEGAWATT = 1000000.0;

    /**
     * Number of watts in 1 horsepower.
     */
    static final double WATTS_PER_HORSEPOWER = 745.699872;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private PowerConverter() {
    }

    /**
     * Converts a power instance to provided output power unit.
     *
     * @param input  input power to be converted.
     * @param output output power where result will be stored containing output unit.
     */
    public static void convert(final Power input, final Power output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a power instance to requested output unit.
     *
     * @param input      input power to be converted.
     * @param outputUnit requested output unit.
     * @return converted power.
     */
    public static Power convertAndReturnNew(
            final Power input, final PowerUnit outputUnit) {
        final var result = new Power();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a power to requested output unit.
     *
     * @param power      input power to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Power power, final PowerUnit outputUnit) {
        convert(power, outputUnit, power);
    }

    /**
     * Converts a power to requested output unit.
     *
     * @param input      input power to be converted.
     * @param outputUnit requested output unit.
     * @param result     power instance where result will be stored.
     */
    public static void convert(
            final Power input, final PowerUnit outputUnit, final Power result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a power value from input unit to provided output unit.
     *
     * @param input      power value.
     * @param inputUnit  input power unit.
     * @param outputUnit output power unit.
     * @return converted power value.
     */
    public static Number convert(final Number input, final PowerUnit inputUnit, final PowerUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a power value from input unit to provided output unit.
     *
     * @param input      power value.
     * @param inputUnit  input power unit.
     * @param outputUnit output power unit.
     * @return converted power value.
     */
    public static double convert(final double input, final PowerUnit inputUnit, final PowerUnit outputUnit) {
        //convert to watts
        final var watts = switch (inputUnit) {
            case KILOWATT -> kilowattToWatt(input);
            case MEGAWATT -> megawattToWatt(input);
            case HORSEPOWER -> horsepowerToWatt(input);
            default -> input;
        };

        //convert from watts to required output unit
        return switch (outputUnit) {
            case KILOWATT -> wattToKilowatt(watts);
            case MEGAWATT -> wattToMegawatt(watts);
            case HORSEPOWER -> wattToHorsepower(watts);
            default -> watts;
        };
    }

    /**
     * Converts provided kilowatt value to watt.
     *
     * @param kilowatt kilowatt value.
     * @return same power converted to watt.
     */
    public static double kilowattToWatt(final double kilowatt) {
        return kilowatt * WATTS_PER_KILOWATT;
    }

    /**
     * Converts provided watt value to kilowatt.
     *
     * @param watt watt value.
     * @return same power converted to kilowatt.
     */
    public static double wattToKilowatt(final double watt) {
        return watt / WATTS_PER_KILOWATT;
    }

    /**
     * Converts provided megawatt value to watt.
     *
     * @param megawatt megawatt value.
     * @return same power converted to watt.
     */
    public static double megawattToWatt(final double megawatt) {
        return megawatt * WATTS_PER_MEGAWATT;
    }

    /**
     * Converts provided watt value to megawatt.
     *
     * @param watt watt value.
     * @return same power converted to megawatt.
     */
    public static double wattToMegawatt(final double watt) {
        return watt / WATTS_PER_MEGAWATT;
    }

    /**
     * Converts provided horsepower value to watt.
     *
     * @param horsepower horsepower value.
     * @return same power converted to watt.
     */
    public static double horsepowerToWatt(final double horsepower) {
        return horsepower * WATTS_PER_HORSEPOWER;
    }

    /**
     * Converts provided watt value to horsepower.
     *
     * @param watt watt value.
     * @return same power converted to horsepower.
     */
    public static double wattToHorsepower(final double watt) {
        return watt / WATTS_PER_HORSEPOWER;
    }
}
