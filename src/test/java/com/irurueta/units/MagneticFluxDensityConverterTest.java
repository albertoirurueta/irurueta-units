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

public class MagneticFluxDensityConverterTest {

    private static final double TESLA_PER_NANOTESLA = 1e-9;
    private static final double TESLA_PER_MICROTESLA = 1e-6;
    private static final double TESLA_PER_MILLITESLA = 1e-3;
    private static final double TESLA_PER_KILOTESLA = 1e3;
    private static final double TESLA_PER_MEGATESLA = 1e6;
    private static final double TESLA_PER_GIGATESLA = 1e9;

    private static final double ERROR = 1e-6;

    @Test
    public void testTeslaNanoTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(inputValue),
                inputValue / TESLA_PER_NANOTESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue),
                inputValue * TESLA_PER_NANOTESLA, ERROR);
    }

    @Test
    public void testTeslaMicroTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(inputValue),
                inputValue / TESLA_PER_MICROTESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.microTeslaToTesla(inputValue),
                inputValue * TESLA_PER_MICROTESLA, ERROR);
    }

    @Test
    public void testTeslaMilliTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(inputValue),
                inputValue / TESLA_PER_MILLITESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.milliTeslaToTesla(inputValue),
                inputValue * TESLA_PER_MILLITESLA, ERROR);
    }

    @Test
    public void testTeslaKiloTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(inputValue),
                inputValue / TESLA_PER_KILOTESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue),
                inputValue * TESLA_PER_KILOTESLA, ERROR);
    }

    @Test
    public void testTeslaMegaTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(inputValue),
                inputValue / TESLA_PER_MEGATESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.megaTeslaToTesla(inputValue),
                inputValue * TESLA_PER_MEGATESLA, ERROR);
    }

    @Test
    public void testTeslaGigaTesla() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(inputValue),
                inputValue / TESLA_PER_GIGATESLA, ERROR);
        assertEquals(MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue),
                inputValue * TESLA_PER_GIGATESLA, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(
                        MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(
                        MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(
                        MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(
                        MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.NANOTESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(
                        MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(
                        MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(
                        MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(
                        MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(
                        MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(
                        MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(
                        MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(
                        MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(
                        MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(
                        MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MILLITESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(
                        MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.TESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(inputValue),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(inputValue),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(
                        MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(
                        MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(
                        MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue), ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(
                        MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.KILOTESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(
                        MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(
                        MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(
                        MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(
                        MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue), ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(
                        MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                inputValue, ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.MEGATESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                MagneticFluxDensityConverter.teslaToGigaTesla(
                        MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                ERROR);

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.NANOTESLA),
                MagneticFluxDensityConverter.teslaToNanoTesla(
                        MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.MICROTESLA),
                MagneticFluxDensityConverter.teslaToMicroTesla(
                        MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.MILLITESLA),
                MagneticFluxDensityConverter.teslaToMilliTesla(
                        MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.TESLA),
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue), ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.KILOTESLA),
                MagneticFluxDensityConverter.teslaToKiloTesla(
                        MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.MEGATESLA),
                MagneticFluxDensityConverter.teslaToMegaTesla(
                        MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                ERROR);
        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.GIGATESLA,
                MagneticFluxDensityUnit.GIGATESLA),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(MagneticFluxDensityConverter.convert(inputValue,
                MagneticFluxDensityUnit.TESLA,
                MagneticFluxDensityUnit.TESLA).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertMagneticFluxDensity() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity inputB = new MagneticFluxDensity(
                value, MagneticFluxDensityUnit.MICROTESLA);

        final MagneticFluxDensity outputB = new MagneticFluxDensity();
        MagneticFluxDensityConverter.convert(inputB, MagneticFluxDensityUnit.TESLA,
                outputB);

        // check
        assertEquals(inputB.getValue().doubleValue(), value, 0.0);
        assertEquals(inputB.getUnit(), MagneticFluxDensityUnit.MICROTESLA);

        assertEquals(outputB.getUnit(), MagneticFluxDensityUnit.TESLA);
        assertEquals(outputB.getValue().doubleValue(),
                MagneticFluxDensityConverter.convert(value, inputB.getUnit(),
                        outputB.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateMagneticFluxDensity() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity b = new MagneticFluxDensity(
                value, MagneticFluxDensityUnit.MICROTESLA);

        MagneticFluxDensityConverter.convert(b, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(b.getUnit(), MagneticFluxDensityUnit.TESLA);
        assertEquals(b.getValue().doubleValue(),
                MagneticFluxDensityConverter.convert(
                        value, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.TESLA), 0.0);
    }

    @Test
    public void testConvertAndReturnNewMagneticFluxDensity() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity inputB = new MagneticFluxDensity(
                value, MagneticFluxDensityUnit.MICROTESLA);

        final MagneticFluxDensity outputB = MagneticFluxDensityConverter
                .convertAndReturnNew(inputB, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(inputB.getValue().doubleValue(), value, 0.0);
        assertEquals(inputB.getUnit(), MagneticFluxDensityUnit.MICROTESLA);

        assertEquals(outputB.getUnit(), MagneticFluxDensityUnit.TESLA);
        assertEquals(outputB.getValue().doubleValue(),
                MagneticFluxDensityConverter.convert(value, inputB.getUnit(),
                        outputB.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputMagneticFluxDensityUnit() {
        final double value = new Random().nextDouble();
        final MagneticFluxDensity inputB = new MagneticFluxDensity(
                value, MagneticFluxDensityUnit.MICROTESLA);

        final MagneticFluxDensity outputB = new MagneticFluxDensity();
        outputB.setUnit(MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensityConverter.convert(inputB, outputB);

        // check
        assertEquals(inputB.getValue().doubleValue(), value, 0.0);
        assertEquals(inputB.getUnit(), MagneticFluxDensityUnit.MICROTESLA);

        assertEquals(outputB.getUnit(), MagneticFluxDensityUnit.TESLA);
        assertEquals(outputB.getValue().doubleValue(),
                MagneticFluxDensityConverter.convert(value, inputB.getUnit(),
                        outputB.getUnit()), 0.0);
    }
}
