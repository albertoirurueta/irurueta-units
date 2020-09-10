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

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class WeightConverterTest {

    private static final double GRAMS_PER_PICOGRAM = 1e-12;
    private static final double GRAMS_PER_NANOGRAM = 1e-9;
    private static final double GRAMS_PER_MICROGRAM = 1e-6;
    private static final double GRAMS_PER_MILLIGRAM = 1e-3;
    private static final double GRAMS_PER_KILOGRAM = 1e3;
    private static final double GRAMS_PER_TONNE = 1e6;
    private static final double GRAMS_PER_MEGATONNE = 1e12;
    private static final double GRAMS_PER_US_TON = 0.907e6;
    private static final double GRAMS_PER_UK_TON = 1.016e6;
    private static final double GRAMS_PER_POUND = 453.59;
    private static final double GRAMS_PER_OUNCE = 28.35;

    private static final double ERROR = 1e-6;

    @Test
    public void testGramPicogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToPicogram(inputValue),
                inputValue / GRAMS_PER_PICOGRAM, ERROR);
        assertEquals(WeightConverter.picogramToGram(inputValue),
                inputValue * GRAMS_PER_PICOGRAM, ERROR);
    }

    @Test
    public void testGramNanogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToNanogram(inputValue),
                inputValue / GRAMS_PER_NANOGRAM, ERROR);
        assertEquals(WeightConverter.nanogramToGram(inputValue),
                inputValue * GRAMS_PER_NANOGRAM, ERROR);
    }

    @Test
    public void testGramMicrogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToMicrogram(inputValue),
                inputValue / GRAMS_PER_MICROGRAM, ERROR);
        assertEquals(WeightConverter.microgramToGram(inputValue),
                inputValue * GRAMS_PER_MICROGRAM, ERROR);
    }

    @Test
    public void testGramMilligram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToMilligram(inputValue),
                inputValue / GRAMS_PER_MILLIGRAM, ERROR);
        assertEquals(WeightConverter.milligramToGram(inputValue),
                inputValue * GRAMS_PER_MILLIGRAM, ERROR);
    }

    @Test
    public void testGramKilogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToKilogram(inputValue),
                inputValue / GRAMS_PER_KILOGRAM, ERROR);
        assertEquals(WeightConverter.kilogramToGram(inputValue),
                inputValue * GRAMS_PER_KILOGRAM, ERROR);
    }

    @Test
    public void testGramTonne() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToTonne(inputValue),
                inputValue / GRAMS_PER_TONNE, ERROR);
        assertEquals(WeightConverter.tonneToGram(inputValue),
                inputValue * GRAMS_PER_TONNE, ERROR);
    }

    @Test
    public void testGramToMegatonne() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToMegatonne(inputValue),
                inputValue / GRAMS_PER_MEGATONNE, ERROR);
        assertEquals(WeightConverter.megatonneToGram(inputValue),
                inputValue * GRAMS_PER_MEGATONNE, ERROR);
    }

    @Test
    public void testGramUsTon() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToUsTon(inputValue),
                inputValue / GRAMS_PER_US_TON, ERROR);
        assertEquals(WeightConverter.usTonToGram(inputValue),
                inputValue * GRAMS_PER_US_TON, ERROR);
    }

    @Test
    public void testGramUkTon() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToUkTon(inputValue),
                inputValue / GRAMS_PER_UK_TON, ERROR);
        assertEquals(WeightConverter.ukTonToGram(inputValue),
                inputValue * GRAMS_PER_UK_TON, ERROR);
    }

    @Test
    public void testGramPound() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToPound(inputValue),
                inputValue / GRAMS_PER_POUND, ERROR);
        assertEquals(WeightConverter.poundToGram(inputValue),
                inputValue * GRAMS_PER_POUND, ERROR);
    }

    @Test
    public void testGramOunce() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.gramToOunce(inputValue),
                inputValue / GRAMS_PER_OUNCE, ERROR);
        assertEquals(WeightConverter.ounceToGram(inputValue),
                inputValue * GRAMS_PER_OUNCE, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.PICOGRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.GRAM),
                WeightConverter.picogramToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.picogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.PICOGRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.picogramToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.NANOGRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.GRAM),
                WeightConverter.nanogramToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.nanogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.NANOGRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.nanogramToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.MICROGRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.GRAM),
                WeightConverter.microgramToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.microgramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MICROGRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.microgramToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.MILLIGRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.GRAM),
                WeightConverter.milligramToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.milligramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MILLIGRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.milligramToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.GRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(inputValue), ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.GRAM),
                WeightConverter.kilogramToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.KILOGRAM), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.kilogramToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.KILOGRAM, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.kilogramToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.GRAM),
                WeightConverter.tonneToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.TONNE), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.tonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.TONNE, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.tonneToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.GRAM),
                WeightConverter.megatonneToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.MEGATONNE), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.megatonneToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.MEGATONNE, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.megatonneToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.GRAM),
                WeightConverter.usTonToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.US_TON), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.usTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.US_TON, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.usTonToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.GRAM),
                WeightConverter.ukTonToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.ukTonToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.UK_TON, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.ukTonToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.GRAM),
                WeightConverter.poundToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.poundToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.POUND), inputValue, ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.POUND, WeightUnit.OUNCE),
                WeightConverter.gramToOunce(WeightConverter.poundToGram(inputValue)),
                ERROR);


        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.PICOGRAM),
                WeightConverter.gramToPicogram(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.NANOGRAM),
                WeightConverter.gramToNanogram(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.MICROGRAM),
                WeightConverter.gramToMicrogram(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.MILLIGRAM),
                WeightConverter.gramToMilligram(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.GRAM),
                WeightConverter.ounceToGram(inputValue), ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.KILOGRAM),
                WeightConverter.gramToKilogram(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.TONNE),
                WeightConverter.gramToTonne(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.MEGATONNE),
                WeightConverter.gramToMegatonne(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.US_TON),
                WeightConverter.gramToUsTon(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.UK_TON),
                WeightConverter.gramToUkTon(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.POUND),
                WeightConverter.gramToPound(WeightConverter.ounceToGram(inputValue)),
                ERROR);
        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.OUNCE, WeightUnit.OUNCE), inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(WeightConverter.convert(inputValue,
                WeightUnit.GRAM, WeightUnit.GRAM).doubleValue(), inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertWeight() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = new Weight();
        WeightConverter.convert(inputWeight, WeightUnit.KILOGRAM, outputWeight);

        // check
        assertEquals(inputWeight.getValue().doubleValue(), value, 0.0);
        assertEquals(inputWeight.getUnit(), WeightUnit.GRAM);

        assertEquals(outputWeight.getUnit(), WeightUnit.KILOGRAM);
        assertEquals(outputWeight.getValue().doubleValue(),
                WeightConverter.convert(value, inputWeight.getUnit(),
                        outputWeight.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateWeight() {
        final double value = new Random().nextDouble();
        final Weight weight = new Weight(value, WeightUnit.GRAM);

        WeightConverter.convert(weight, WeightUnit.KILOGRAM);

        // check
        assertEquals(weight.getUnit(), WeightUnit.KILOGRAM);
        assertEquals(weight.getValue().doubleValue(),
                WeightConverter.convert(value, WeightUnit.GRAM, WeightUnit.KILOGRAM), 0.0);
    }

    @Test
    public void testConvertAndReturnNewWeight() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = WeightConverter.convertAndReturnNew(
                inputWeight, WeightUnit.KILOGRAM);

        // check
        assertEquals(inputWeight.getValue().doubleValue(), value, 0.0);
        assertEquals(inputWeight.getUnit(), WeightUnit.GRAM);

        assertEquals(outputWeight.getUnit(), WeightUnit.KILOGRAM);
        assertEquals(outputWeight.getValue().doubleValue(),
                WeightConverter.convert(value, inputWeight.getUnit(),
                        outputWeight.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputWeightUnit() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = new Weight();
        outputWeight.setUnit(WeightUnit.KILOGRAM);
        WeightConverter.convert(inputWeight, outputWeight);

        // check
        assertEquals(inputWeight.getValue().doubleValue(), value, 0.0);
        assertEquals(inputWeight.getUnit(), WeightUnit.GRAM);

        assertEquals(outputWeight.getUnit(), WeightUnit.KILOGRAM);
        assertEquals(outputWeight.getValue().doubleValue(),
                WeightConverter.convert(value, inputWeight.getUnit(),
                        outputWeight.getUnit()), 0.0);
    }
}
