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
 * Does force conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying force measurements.
 */
@SuppressWarnings("WeakerAccess")
public class ForceConverter {

    /**
     * Number of newtons in 1 kilonewton.
     */
    static final double NEWTONS_PER_KILONEWTON = 1000.0;

    /**
     * Number of newtons in 1 dyne.
     */
    static final double NEWTONS_PER_DYNE = 1e-5;

    /**
     * Number of newtons in 1 kilogram-force.
     */
    static final double NEWTONS_PER_KILOGRAM_FORCE = 9.80665;

    /**
     * Number of newtons in 1 pound-force.
     */
    static final double NEWTONS_PER_POUND_FORCE = 4.4482216152605;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private ForceConverter() {
    }

    /**
     * Converts a force instance to provided output force unit.
     *
     * @param input  input force to be converted.
     * @param output output force where result will be stored containing output unit.
     */
    public static void convert(final Force input, final Force output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a force instance to requested output unit.
     *
     * @param input      input force to be converted.
     * @param outputUnit requested output unit.
     * @return converted force.
     */
    public static Force convertAndReturnNew(
            final Force input, final ForceUnit outputUnit) {
        final var result = new Force();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a force to requested output unit.
     *
     * @param force      input force to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Force force, final ForceUnit outputUnit) {
        convert(force, outputUnit, force);
    }

    /**
     * Converts a force to requested output unit.
     *
     * @param input      input force to be converted.
     * @param outputUnit requested output unit.
     * @param result     force instance where result will be stored.
     */
    public static void convert(
            final Force input, final ForceUnit outputUnit, final Force result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a force value from input unit to provided output unit.
     *
     * @param input      force value.
     * @param inputUnit  input force unit.
     * @param outputUnit output force unit.
     * @return converted force value.
     */
    public static Number convert(final Number input, final ForceUnit inputUnit, final ForceUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a force value from input unit to provided output unit.
     *
     * @param input      force value.
     * @param inputUnit  input force unit.
     * @param outputUnit output force unit.
     * @return converted force value.
     */
    public static double convert(final double input, final ForceUnit inputUnit, final ForceUnit outputUnit) {
        //convert to newtons
        final var newton = switch (inputUnit) {
            case KILONEWTON -> kilonewtonToNewton(input);
            case DYNE -> dyneToNewton(input);
            case KILOGRAM_FORCE -> kilogramForceToNewton(input);
            case POUND_FORCE -> poundForceToNewton(input);
            default -> input;
        };

        //convert from newtons to required output unit
        return switch (outputUnit) {
            case KILONEWTON -> newtonToKilonewton(newton);
            case DYNE -> newtonToDyne(newton);
            case KILOGRAM_FORCE -> newtonToKilogramForce(newton);
            case POUND_FORCE -> newtonToPoundForce(newton);
            default -> newton;
        };
    }

    /**
     * Converts provided kilonewton value to newtons.
     *
     * @param kilonewton kilonewton value.
     * @return same force converted to newtons.
     */
    public static double kilonewtonToNewton(final double kilonewton) {
        return kilonewton * NEWTONS_PER_KILONEWTON;
    }

    /**
     * Converts provided newton value to kilonewtons.
     *
     * @param newton newton value.
     * @return same force converted to kilonewtons.
     */
    public static double newtonToKilonewton(final double newton) {
        return newton / NEWTONS_PER_KILONEWTON;
    }

    /**
     * Converts provided dyne value to newtons.
     *
     * @param dyne dyne value.
     * @return same force converted to newtons.
     */
    public static double dyneToNewton(final double dyne) {
        return dyne * NEWTONS_PER_DYNE;
    }

    /**
     * Converts provided newton value to dynes.
     *
     * @param newton newton value.
     * @return same force converted to dynes.
     */
    public static double newtonToDyne(final double newton) {
        return newton / NEWTONS_PER_DYNE;
    }

    /**
     * Converts provided kilogram-force value to newtons.
     *
     * @param kilogramForce kilogram-force value.
     * @return same force converted to newtons.
     */
    public static double kilogramForceToNewton(final double kilogramForce) {
        return kilogramForce * NEWTONS_PER_KILOGRAM_FORCE;
    }

    /**
     * Converts provided newton value to kilogram-force.
     *
     * @param newton newton value.
     * @return same force converted to kilogram-force.
     */
    public static double newtonToKilogramForce(final double newton) {
        return newton / NEWTONS_PER_KILOGRAM_FORCE;
    }

    /**
     * Converts provided pound-force value to newtons.
     *
     * @param poundForce pound-force value.
     * @return same force converted to newtons.
     */
    public static double poundForceToNewton(final double poundForce) {
        return poundForce * NEWTONS_PER_POUND_FORCE;
    }

    /**
     * Converts provided newton value to pound-force.
     *
     * @param newton newton value.
     * @return same force converted to pound-force.
     */
    public static double newtonToPoundForce(final double newton) {
        return newton / NEWTONS_PER_POUND_FORCE;
    }
}
