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
 * Does pressure conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying pressure measurements.
 */
@SuppressWarnings("WeakerAccess")
public class PressureConverter {

    /**
     * Number of pascals in 1 kilopascal.
     */
    static final double PASCALS_PER_KILOPASCAL = 1000.0;

    /**
     * Number of pascals in 1 bar.
     */
    static final double PASCALS_PER_BAR = 100000.0;

    /**
     * Number of pascals in 1 atmosphere.
     */
    static final double PASCALS_PER_ATMOSPHERE = 101325.0;

    /**
     * Number of pascals in 1 psi.
     */
    static final double PASCALS_PER_PSI = 6894.757293168361;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private PressureConverter() {
    }

    /**
     * Converts a pressure instance to provided output pressure unit.
     *
     * @param input  input pressure to be converted.
     * @param output output pressure where result will be stored containing output unit.
     */
    public static void convert(final Pressure input, final Pressure output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a pressure instance to requested output unit.
     *
     * @param input      input pressure to be converted.
     * @param outputUnit requested output unit.
     * @return converted pressure.
     */
    public static Pressure convertAndReturnNew(
            final Pressure input, final PressureUnit outputUnit) {
        final var result = new Pressure();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a pressure to requested output unit.
     *
     * @param pressure   input pressure to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Pressure pressure, final PressureUnit outputUnit) {
        convert(pressure, outputUnit, pressure);
    }

    /**
     * Converts a pressure to requested output unit.
     *
     * @param input      input pressure to be converted.
     * @param outputUnit requested output unit.
     * @param result     pressure instance where result will be stored.
     */
    public static void convert(
            final Pressure input, final PressureUnit outputUnit, final Pressure result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a pressure value from input unit to provided output unit.
     *
     * @param input      pressure value.
     * @param inputUnit  input pressure unit.
     * @param outputUnit output pressure unit.
     * @return converted pressure value.
     */
    public static Number convert(final Number input, final PressureUnit inputUnit, final PressureUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a pressure value from input unit to provided output unit.
     *
     * @param input      pressure value.
     * @param inputUnit  input pressure unit.
     * @param outputUnit output pressure unit.
     * @return converted pressure value.
     */
    public static double convert(final double input, final PressureUnit inputUnit, final PressureUnit outputUnit) {
        //convert to pascals
        final var pascals = switch (inputUnit) {
            case KILOPASCAL -> kilopascalToPascal(input);
            case BAR -> barToPascal(input);
            case ATMOSPHERE -> atmosphereToPascal(input);
            case PSI -> psiToPascal(input);
            default -> input;
        };

        //convert from pascals to required output unit
        return switch (outputUnit) {
            case KILOPASCAL -> pascalToKilopascal(pascals);
            case BAR -> pascalToBar(pascals);
            case ATMOSPHERE -> pascalToAtmosphere(pascals);
            case PSI -> pascalToPsi(pascals);
            default -> pascals;
        };
    }

    /**
     * Converts provided kilopascal value to pascal.
     *
     * @param kilopascal kilopascal value.
     * @return same pressure converted to pascal.
     */
    public static double kilopascalToPascal(final double kilopascal) {
        return kilopascal * PASCALS_PER_KILOPASCAL;
    }

    /**
     * Converts provided pascal value to kilopascal.
     *
     * @param pascal pascal value.
     * @return same pressure converted to kilopascal.
     */
    public static double pascalToKilopascal(final double pascal) {
        return pascal / PASCALS_PER_KILOPASCAL;
    }

    /**
     * Converts provided bar value to pascal.
     *
     * @param bar bar value.
     * @return same pressure converted to pascal.
     */
    public static double barToPascal(final double bar) {
        return bar * PASCALS_PER_BAR;
    }

    /**
     * Converts provided pascal value to bar.
     *
     * @param pascal pascal value.
     * @return same pressure converted to bar.
     */
    public static double pascalToBar(final double pascal) {
        return pascal / PASCALS_PER_BAR;
    }

    /**
     * Converts provided atmosphere value to pascal.
     *
     * @param atmosphere atmosphere value.
     * @return same pressure converted to pascal.
     */
    public static double atmosphereToPascal(final double atmosphere) {
        return atmosphere * PASCALS_PER_ATMOSPHERE;
    }

    /**
     * Converts provided pascal value to atmosphere.
     *
     * @param pascal pascal value.
     * @return same pressure converted to atmosphere.
     */
    public static double pascalToAtmosphere(final double pascal) {
        return pascal / PASCALS_PER_ATMOSPHERE;
    }

    /**
     * Converts provided psi value to pascal.
     *
     * @param psi psi value.
     * @return same pressure converted to pascal.
     */
    public static double psiToPascal(final double psi) {
        return psi * PASCALS_PER_PSI;
    }

    /**
     * Converts provided pascal value to psi.
     *
     * @param pascal pascal value.
     * @return same pressure converted to psi.
     */
    public static double pascalToPsi(final double pascal) {
        return pascal / PASCALS_PER_PSI;
    }
}
