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

        assertEquals(inputValue / GRAMS_PER_PICOGRAM,
                WeightConverter.gramToPicogram(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_PICOGRAM,
                WeightConverter.picogramToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramNanogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_NANOGRAM,
                WeightConverter.gramToNanogram(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_NANOGRAM,
                WeightConverter.nanogramToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramMicrogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_MICROGRAM,
                WeightConverter.gramToMicrogram(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_MICROGRAM,
                WeightConverter.microgramToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramMilligram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_MILLIGRAM,
                WeightConverter.gramToMilligram(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_MILLIGRAM,
                WeightConverter.milligramToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramKilogram() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_KILOGRAM,
                WeightConverter.gramToKilogram(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_KILOGRAM,
                WeightConverter.kilogramToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramTonne() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_TONNE,
                WeightConverter.gramToTonne(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_TONNE,
                WeightConverter.tonneToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramToMegatonne() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_MEGATONNE,
                WeightConverter.gramToMegatonne(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_MEGATONNE,
                WeightConverter.megatonneToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramUsTon() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_US_TON,
                WeightConverter.gramToUsTon(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_US_TON,
                WeightConverter.usTonToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramUkTon() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_UK_TON,
                WeightConverter.gramToUkTon(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_UK_TON,
                WeightConverter.ukTonToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramPound() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_POUND,
                WeightConverter.gramToPound(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_POUND,
                WeightConverter.poundToGram(inputValue),
                ERROR);
    }

    @Test
    public void testGramOunce() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / GRAMS_PER_OUNCE,
                WeightConverter.gramToOunce(inputValue),
                ERROR);
        assertEquals(inputValue * GRAMS_PER_OUNCE,
                WeightConverter.ounceToGram(inputValue),
                ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.PICOGRAM), ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.picogramToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.picogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.PICOGRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.NANOGRAM), ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.nanogramToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.nanogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.NANOGRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.MICROGRAM), ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.microgramToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.microgramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MICROGRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.MILLIGRAM), ERROR);
        assertEquals(WeightConverter.milligramToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.milligramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MILLIGRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.GRAM), ERROR);
        assertEquals(WeightConverter.gramToKilogram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.kilogramToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.GRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.KILOGRAM), ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.kilogramToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.KILOGRAM, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.tonneToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.TONNE), ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.tonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.TONNE, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.megatonneToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.TONNE),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.MEGATONNE), ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.megatonneToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.MEGATONNE, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.usTonToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.US_TON), ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.usTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.US_TON, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.ukTonToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.POUND),
                ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.ukTonToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.UK_TON, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.poundToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.UK_TON),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.POUND), ERROR);
        assertEquals(WeightConverter.gramToOunce(WeightConverter.poundToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.POUND, WeightUnit.OUNCE),
                ERROR);


        assertEquals(WeightConverter.gramToPicogram(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.PICOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToNanogram(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.NANOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMicrogram(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.MICROGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToMilligram(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.MILLIGRAM),
                ERROR);
        assertEquals(WeightConverter.ounceToGram(inputValue),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.GRAM),
                ERROR);
        assertEquals(WeightConverter.gramToKilogram(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.KILOGRAM),
                ERROR);
        assertEquals(WeightConverter.gramToTonne(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.TONNE),
                ERROR);
        assertEquals(WeightConverter.gramToMegatonne(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.MEGATONNE),
                ERROR);
        assertEquals(WeightConverter.gramToUsTon(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.US_TON),
                ERROR);
        assertEquals(WeightConverter.gramToUkTon(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.UK_TON),
                ERROR);
        assertEquals(WeightConverter.gramToPound(WeightConverter.ounceToGram(inputValue)),
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.POUND),
                ERROR);
        assertEquals(inputValue,
                WeightConverter.convert(inputValue, WeightUnit.OUNCE, WeightUnit.OUNCE), ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                WeightConverter.convert(inputValue, WeightUnit.GRAM, WeightUnit.GRAM).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertWeight() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = new Weight();
        WeightConverter.convert(inputWeight, WeightUnit.KILOGRAM, outputWeight);

        // check
        assertEquals(value, inputWeight.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.GRAM, inputWeight.getUnit());

        assertEquals(WeightUnit.KILOGRAM, outputWeight.getUnit());
        assertEquals(WeightConverter.convert(value, inputWeight.getUnit(), outputWeight.getUnit()),
                outputWeight.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndUpdateWeight() {
        final double value = new Random().nextDouble();
        final Weight weight = new Weight(value, WeightUnit.GRAM);

        WeightConverter.convert(weight, WeightUnit.KILOGRAM);

        // check
        assertEquals(WeightUnit.KILOGRAM, weight.getUnit());
        assertEquals(WeightConverter.convert(value, WeightUnit.GRAM, WeightUnit.KILOGRAM),
                weight.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndReturnNewWeight() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = WeightConverter.convertAndReturnNew(
                inputWeight, WeightUnit.KILOGRAM);

        // check
        assertEquals(value, inputWeight.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.GRAM, inputWeight.getUnit());

        assertEquals(WeightUnit.KILOGRAM, outputWeight.getUnit());
        assertEquals(WeightConverter.convert(value, inputWeight.getUnit(), outputWeight.getUnit()),
                outputWeight.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertToOutputWeightUnit() {
        final double value = new Random().nextDouble();
        final Weight inputWeight = new Weight(value, WeightUnit.GRAM);

        final Weight outputWeight = new Weight();
        outputWeight.setUnit(WeightUnit.KILOGRAM);
        WeightConverter.convert(inputWeight, outputWeight);

        // check
        assertEquals(value, inputWeight.getValue().doubleValue(), 0.0);
        assertEquals(WeightUnit.GRAM, inputWeight.getUnit());

        assertEquals(WeightUnit.KILOGRAM, outputWeight.getUnit());
        assertEquals(WeightConverter.convert(value, inputWeight.getUnit(), outputWeight.getUnit()),
                outputWeight.getValue().doubleValue(), 0.0);
    }
}
