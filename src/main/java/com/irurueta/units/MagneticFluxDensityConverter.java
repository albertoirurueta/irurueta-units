/*
 * Copyright (C) 2020 Alberto Irurueta Carro (alberto@irurueta.com)
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
 * Does magnetic flux density conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying magnetic flux density measurements.
 */
public class MagneticFluxDensityConverter {

    /**
     * Number of Teslas in one nanotesla.
     */
    static final double TESLAS_PER_NANOTESLA = 1e-9;

    /**
     * Number of Teslas in one microtesla.
     */
    static final double TESLAS_PER_MICROTESLA = 1e-6;

    /**
     * Number of Teslas in one millitesla.
     */
    static final double TESLAS_PER_MILLITESLA = 1e-3;

    /**
     * Number of Teslas in one Kilotesla.
     */
    static final double TESLAS_PER_KILOTESLA = 1e3;

    /**
     * Number of Teslas in one Megatesla.
     */
    static final double TESLAS_PER_MEGATESLA = 1e6;

    /**
     * Number of Teslas in one Gigatesla.
     */
    static final double TESLAS_PER_GIGATESLA = 1e9;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private MagneticFluxDensityConverter() {
    }

    /**
     * Converts a magnetic flux density to provided output magnetic flux density unit.
     *
     * @param input  input magnetic flux density to be converted.
     * @param output output magnetic flux density where result will be stored and
     *               containing output unit.
     */
    public static void convert(final MagneticFluxDensity input, final MagneticFluxDensity output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a magnetic flux density to requested output unit.
     *
     * @param input      input magnetic flux density to be converted.
     * @param outputUnit requested output unit.
     * @return converted magnetic flux density.
     */
    public static MagneticFluxDensity convertAndReturnNew(
            final MagneticFluxDensity input, final MagneticFluxDensityUnit outputUnit) {
        final var result = new MagneticFluxDensity();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a magnetic flux density to requested output unit.
     *
     * @param magneticFluxDensity input magnetic flux density to be converted
     *                            and updated.
     * @param outputUnit          requested output unit.
     */
    public static void convert(
            final MagneticFluxDensity magneticFluxDensity, final MagneticFluxDensityUnit outputUnit) {
        convert(magneticFluxDensity, outputUnit, magneticFluxDensity);
    }

    /**
     * Converts a magnetic flux density to requested output unit.
     *
     * @param input      input magnetic flux density to be converted.
     * @param outputUnit requested output unit.
     * @param result     magnetic flux density where result will be stored.
     */
    public static void convert(
            final MagneticFluxDensity input, final MagneticFluxDensityUnit outputUnit,
            final MagneticFluxDensity result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a magnetic flux density value from input unit to provided
     * output unit.
     *
     * @param input      magnetic flux density value.
     * @param inputUnit  input magnetic flux density unit.
     * @param outputUnit output magnetic flux density unit.
     * @return converted magnetic flux density value.
     */
    public static Number convert(
            final Number input, final MagneticFluxDensityUnit inputUnit, final MagneticFluxDensityUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a magnetic flux density value from input unit to provided
     * output unit.
     *
     * @param input      magnetic flux density value.
     * @param inputUnit  input magnetic flux density unit.
     * @param outputUnit output magnetic flux density unit.
     * @return converted magnetic flux density value.
     */
    public static double convert(
            final double input, final MagneticFluxDensityUnit inputUnit, final MagneticFluxDensityUnit outputUnit) {
        // convert to Tesla
        final var tesla = switch (inputUnit) {
            case NANOTESLA -> nanoTeslaToTesla(input);
            case MICROTESLA -> microTeslaToTesla(input);
            case MILLITESLA -> milliTeslaToTesla(input);
            case KILOTESLA -> kiloTeslaToTesla(input);
            case MEGATESLA -> megaTeslaToTesla(input);
            case GIGATESLA -> gigaTeslaToTesla(input);
            default -> input;
        };

        // convert from Tesla to output unit
        return switch (outputUnit) {
            case NANOTESLA -> teslaToNanoTesla(tesla);
            case MICROTESLA -> teslaToMicroTesla(tesla);
            case MILLITESLA -> teslaToMilliTesla(tesla);
            case KILOTESLA -> teslaToKiloTesla(tesla);
            case MEGATESLA -> teslaToMegaTesla(tesla);
            case GIGATESLA -> teslaToGigaTesla(tesla);
            default -> tesla;
        };
    }

    /**
     * Converts provided Tesla value to nanoteslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to nanoteslas.
     */
    public static double teslaToNanoTesla(final double tesla) {
        return tesla / TESLAS_PER_NANOTESLA;
    }

    /**
     * Converts provided Tesla value to microteslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to microteslas.
     */
    public static double teslaToMicroTesla(final double tesla) {
        return tesla / TESLAS_PER_MICROTESLA;
    }

    /**
     * Converts provided Tesla value to milliteslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to milliteslas.
     */
    public static double teslaToMilliTesla(final double tesla) {
        return tesla / TESLAS_PER_MILLITESLA;
    }

    /**
     * Converts provided Tesla value to Kiloteslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to Kiloteslas.
     */
    public static double teslaToKiloTesla(final double tesla) {
        return tesla / TESLAS_PER_KILOTESLA;
    }

    /**
     * Converts provided Tesla value to Megateslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to Megateslas.
     */
    public static double teslaToMegaTesla(final double tesla) {
        return tesla / TESLAS_PER_MEGATESLA;
    }

    /**
     * Converts provided Tesla value to Gigateslas.
     *
     * @param tesla tesla value.
     * @return some amount of magnetic flux density converted to Gigateslas.
     */
    public static double teslaToGigaTesla(final double tesla) {
        return tesla / TESLAS_PER_GIGATESLA;
    }

    /**
     * Converts provided nanotesla value to Teslas.
     *
     * @param nanoTesla nanotesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double nanoTeslaToTesla(final double nanoTesla) {
        return nanoTesla * TESLAS_PER_NANOTESLA;
    }

    /**
     * Converts provided microtesla value to Teslas.
     *
     * @param microTesla microtesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double microTeslaToTesla(final double microTesla) {
        return microTesla * TESLAS_PER_MICROTESLA;
    }

    /**
     * Converts provided millitesla value to Teslas.
     *
     * @param milliTesla millitesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double milliTeslaToTesla(final double milliTesla) {
        return milliTesla * TESLAS_PER_MILLITESLA;
    }

    /**
     * Converts provided Kilotesla value to Teslas.
     *
     * @param kiloTesla Kilotesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double kiloTeslaToTesla(final double kiloTesla) {
        return kiloTesla * TESLAS_PER_KILOTESLA;
    }

    /**
     * Converts provided Megatesla value to Teslas.
     *
     * @param megaTesla Megatesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double megaTeslaToTesla(final double megaTesla) {
        return megaTesla * TESLAS_PER_MEGATESLA;
    }

    /**
     * Converts provided Gigatesla value to Teslas.
     *
     * @param gigaTesla Gigatesla value.
     * @return some amount of magnetic flux density converted to Teslas.
     */
    public static double gigaTeslaToTesla(final double gigaTesla) {
        return gigaTesla * TESLAS_PER_GIGATESLA;
    }
}
