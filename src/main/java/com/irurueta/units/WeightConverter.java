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
 * Does weight conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying weight measurements.
 */
public class WeightConverter {

    /**
     * Number of grams in 1 picogram.
     */
    static final double GRAMS_PER_PICOGRAM = 1e-12;

    /**
     * Number of grams in 1 nanogram.
     */
    static final double GRAMS_PER_NANOGRAM = 1e-9;

    /**
     * Number of grams in 1 microgram.
     */
    static final double GRAMS_PER_MICROGRAM = 1e-6;

    /**
     * Number of grams in 1 milligram.
     */
    static final double GRAMS_PER_MILLIGRAM = 1e-3;

    /**
     * Number of grams in 1 kilogram.
     */
    static final double GRAMS_PER_KILOGRAM = 1e3;

    /**
     * Number of grams in 1 metric tonne.
     */
    static final double GRAMS_PER_TONNE = 1e6;

    /**
     * Number of grams in 1 megatonne.
     */
    static final double GRAMS_PER_MEGATONNE = 1e12;

    /**
     * Number of grams in 1 US ton.
     */
    static final double GRAMS_PER_US_TON = 0.907 * GRAMS_PER_TONNE;

    /**
     * Number of grams in 1 UK ton.
     */
    static final double GRAMS_PER_UK_TON = 1.016 * GRAMS_PER_TONNE;

    /**
     * Number of grams per pound.
     */
    static final double GRAMS_PER_POUND = 453.59;

    /**
     * Number of grams per ounce.
     */
    static final double GRAMS_PER_OUNCE = 28.35;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private WeightConverter() {
    }

    /**
     * Converts a weight to provided output weight unit.
     *
     * @param input  input weight to be converted.
     * @param output output weight where result will be stored and
     *               containing output unit.
     */
    public static void convert(final Weight input, final Weight output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a weight to requested output unit.
     *
     * @param input      input weight to be converted.
     * @param outputUnit requested output unit.
     * @return converted weight.
     */
    public static Weight convertAndReturnNew(final Weight input, final WeightUnit outputUnit) {
        final var result = new Weight();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a weight to requested output unit.
     *
     * @param weight     input weight to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Weight weight, final WeightUnit outputUnit) {
        convert(weight, outputUnit, weight);
    }

    /**
     * Converts a weight to requested output unit.
     *
     * @param input      input weight to be converted.
     * @param outputUnit requested output unit.
     * @param result     weight instance where result will be stored.
     */
    public static void convert(
            final Weight input, final WeightUnit outputUnit, final Weight result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a weight value from input unit to provided output unit.
     *
     * @param input      weight value.
     * @param inputUnit  input weight unit.
     * @param outputUnit output weight unit.
     * @return converted weight value.
     */
    public static Number convert(final Number input, final WeightUnit inputUnit, final WeightUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a weight value from input unit to provided output unit.
     *
     * @param input      weight value.
     * @param inputUnit  input weight unit.
     * @param outputUnit output weight unit.
     * @return converted weight value.
     */
    public static double convert(final double input, final WeightUnit inputUnit, final WeightUnit outputUnit) {
        // convert to grams
        final var grams = switch (inputUnit) {
            case PICOGRAM -> picogramToGram(input);
            case NANOGRAM -> nanogramToGram(input);
            case MICROGRAM -> microgramToGram(input);
            case MILLIGRAM -> milligramToGram(input);
            case KILOGRAM -> kilogramToGram(input);
            case TONNE -> tonneToGram(input);
            case MEGATONNE -> megatonneToGram(input);
            case US_TON -> usTonToGram(input);
            case UK_TON -> ukTonToGram(input);
            case POUND -> poundToGram(input);
            case OUNCE -> ounceToGram(input);
            default -> input;
        };

        // convert from gram to output unit
        return switch (outputUnit) {
            case PICOGRAM -> gramToPicogram(grams);
            case NANOGRAM -> gramToNanogram(grams);
            case MICROGRAM -> gramToMicrogram(grams);
            case MILLIGRAM -> gramToMilligram(grams);
            case KILOGRAM -> gramToKilogram(grams);
            case TONNE -> gramToTonne(grams);
            case MEGATONNE -> gramToMegatonne(grams);
            case US_TON -> gramToUsTon(grams);
            case UK_TON -> gramToUkTon(grams);
            case POUND -> gramToPound(grams);
            case OUNCE -> gramToOunce(grams);
            default -> grams;
        };

    }

    /**
     * Converts provided gram value to picograms.
     *
     * @param gram gram value.
     * @return some weight converted to picograms.
     */
    public static double gramToPicogram(final double gram) {
        return gram / GRAMS_PER_PICOGRAM;
    }

    /**
     * Converts provided picogram value to grams.
     *
     * @param picogram picogram value.
     * @return some weight converted to grams.
     */
    public static double picogramToGram(final double picogram) {
        return picogram * GRAMS_PER_PICOGRAM;
    }

    /**
     * Converts provided gram value to nanograms.
     *
     * @param gram gram value.
     * @return some weight converted to nanogram.
     */
    public static double gramToNanogram(final double gram) {
        return gram / GRAMS_PER_NANOGRAM;
    }

    /**
     * Converts provided nanogram value to grams.
     *
     * @param nanogram nanogram value.
     * @return some weight converted to grams.
     */
    public static double nanogramToGram(final double nanogram) {
        return nanogram * GRAMS_PER_NANOGRAM;
    }

    /**
     * Converts provided gram value to micrograms.
     *
     * @param gram gram value.
     * @return some weight converted to micrograms.
     */
    public static double gramToMicrogram(final double gram) {
        return gram / GRAMS_PER_MICROGRAM;
    }

    /**
     * Converts provided microgram value to grams.
     *
     * @param microgram microgram value.
     * @return some weight converted to grams.
     */
    public static double microgramToGram(final double microgram) {
        return microgram * GRAMS_PER_MICROGRAM;
    }

    /**
     * Converts provided gram value to milligrams.
     *
     * @param gram gram value.
     * @return some weight converted to milligrams.
     */
    public static double gramToMilligram(final double gram) {
        return gram / GRAMS_PER_MILLIGRAM;
    }

    /**
     * Converts provided milligram value to grams.
     *
     * @param milligram milligram value.
     * @return some weight converted to grams.
     */
    public static double milligramToGram(final double milligram) {
        return milligram * GRAMS_PER_MILLIGRAM;
    }

    /**
     * Converts provided gram value to kilograms.
     *
     * @param gram gram value.
     * @return some weight converted to kilograms.
     */
    public static double gramToKilogram(final double gram) {
        return gram / GRAMS_PER_KILOGRAM;
    }

    /**
     * Converts provided kilogram value to grams.
     *
     * @param kilogram kilogram value.
     * @return some weight converted to grams.
     */
    public static double kilogramToGram(final double kilogram) {
        return kilogram * GRAMS_PER_KILOGRAM;
    }

    /**
     * Converts provided gram value to tonnes.
     *
     * @param gram gram value.
     * @return some weight converted to tonnes.
     */
    public static double gramToTonne(final double gram) {
        return gram / GRAMS_PER_TONNE;
    }

    /**
     * Converts provided tonne value to grams.
     *
     * @param tonne tonne value.
     * @return some weight converted to grams.
     */
    public static double tonneToGram(final double tonne) {
        return tonne * GRAMS_PER_TONNE;
    }

    /**
     * Converts provided gram value to megatonnes.
     *
     * @param gram gram value.
     * @return some weight converted to megatonnes.
     */
    public static double gramToMegatonne(final double gram) {
        return gram / GRAMS_PER_MEGATONNE;
    }

    /**
     * Converts provided megatonne value to grams.
     *
     * @param megatonne megatonne value.
     * @return some weight converted to grams.
     */
    public static double megatonneToGram(final double megatonne) {
        return megatonne * GRAMS_PER_MEGATONNE;
    }

    /**
     * Converts provided gram value to US tons.
     *
     * @param gram gram value.
     * @return some weight converted to US tons.
     */
    public static double gramToUsTon(final double gram) {
        return gram / GRAMS_PER_US_TON;
    }

    /**
     * Converts provided US ton value to grams.
     *
     * @param usTon US ton value.
     * @return some weight converted to grams.
     */
    public static double usTonToGram(final double usTon) {
        return usTon * GRAMS_PER_US_TON;
    }

    /**
     * Converts provided gram value to UK tons.
     *
     * @param gram gram value.
     * @return some weight converted to UK tons.
     */
    public static double gramToUkTon(final double gram) {
        return gram / GRAMS_PER_UK_TON;
    }

    /**
     * Converts provided UK ton value to grams.
     *
     * @param ukTon UK ton value.
     * @return some weight converted to grams.
     */
    public static double ukTonToGram(final double ukTon) {
        return ukTon * GRAMS_PER_UK_TON;
    }

    /**
     * Converts provided gram value to pounds.
     *
     * @param gram gram value.
     * @return some weight converted to pounds.
     */
    public static double gramToPound(final double gram) {
        return gram / GRAMS_PER_POUND;
    }

    /**
     * Converts provided pound value to grams.
     *
     * @param pound pound value.
     * @return some weight converted to grams.
     */
    public static double poundToGram(final double pound) {
        return pound * GRAMS_PER_POUND;
    }

    /**
     * Converts provided gram value to ounces.
     *
     * @param gram gram value.
     * @return some weight converted to ounces.
     */
    public static double gramToOunce(final double gram) {
        return gram / GRAMS_PER_OUNCE;
    }

    /**
     * Converts provided ounce value to grams.
     *
     * @param ounce ounce value.
     * @return some weight converted to grams.
     */
    public static double ounceToGram(final double ounce) {
        return ounce * GRAMS_PER_OUNCE;
    }
}
