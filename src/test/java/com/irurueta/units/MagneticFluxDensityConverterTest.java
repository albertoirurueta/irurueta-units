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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagneticFluxDensityConverterTest {

    private static final double TESLA_PER_NANOTESLA = 1e-9;
    private static final double TESLA_PER_MICROTESLA = 1e-6;
    private static final double TESLA_PER_MILLITESLA = 1e-3;
    private static final double TESLA_PER_KILOTESLA = 1e3;
    private static final double TESLA_PER_MEGATESLA = 1e6;
    private static final double TESLA_PER_GIGATESLA = 1e9;

    private static final double ERROR = 1e-6;

    @Test
    void testTeslaNanoTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_NANOTESLA,
                MagneticFluxDensityConverter.teslaToNanoTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_NANOTESLA,
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testTeslaMicroTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_MICROTESLA,
                MagneticFluxDensityConverter.teslaToMicroTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_MICROTESLA,
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testTeslaMilliTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_MILLITESLA,
                MagneticFluxDensityConverter.teslaToMilliTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_MILLITESLA,
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testTeslaKiloTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_KILOTESLA,
                MagneticFluxDensityConverter.teslaToKiloTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_KILOTESLA,
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testTeslaMegaTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_MEGATESLA,
                MagneticFluxDensityConverter.teslaToMegaTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_MEGATESLA,
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testTeslaGigaTesla() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / TESLA_PER_GIGATESLA,
                MagneticFluxDensityConverter.teslaToGigaTesla(inputValue), ERROR);
        assertEquals(inputValue * TESLA_PER_GIGATESLA,
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(
                MagneticFluxDensityConverter.nanoTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.NANOTESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.microTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(
                MagneticFluxDensityConverter.microTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MICROTESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.milliTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(
                MagneticFluxDensityConverter.milliTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MILLITESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.TESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue,
                        MagneticFluxDensityUnit.KILOTESLA, MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(
                MagneticFluxDensityConverter.kiloTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.KILOTESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.megaTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToGigaTesla(
                MagneticFluxDensityConverter.megaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.MEGATESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);

        assertEquals(MagneticFluxDensityConverter.teslaToNanoTesla(
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.NANOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMicroTesla(
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.MICROTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMilliTesla(
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.MILLITESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.TESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToKiloTesla(
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.KILOTESLA), ERROR);
        assertEquals(MagneticFluxDensityConverter.teslaToMegaTesla(
                MagneticFluxDensityConverter.gigaTeslaToTesla(inputValue)),
                MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.MEGATESLA), ERROR);
        assertEquals(inputValue, MagneticFluxDensityConverter.convert(inputValue, MagneticFluxDensityUnit.GIGATESLA,
                        MagneticFluxDensityUnit.GIGATESLA), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(), MagneticFluxDensityConverter.convert(inputValue,
                        MagneticFluxDensityUnit.TESLA, MagneticFluxDensityUnit.TESLA).doubleValue(), ERROR);
    }

    @Test
    void testConvertMagneticFluxDensity() {
        final var value = new Random().nextDouble();
        final var inputB = new MagneticFluxDensity(value, MagneticFluxDensityUnit.MICROTESLA);

        final var outputB = new MagneticFluxDensity();
        MagneticFluxDensityConverter.convert(inputB, MagneticFluxDensityUnit.TESLA, outputB);

        // check
        assertEquals(value, inputB.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MICROTESLA, inputB.getUnit());

        assertEquals(MagneticFluxDensityUnit.TESLA, outputB.getUnit());
        assertEquals(MagneticFluxDensityConverter.convert(value, inputB.getUnit(), outputB.getUnit()),
                outputB.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateMagneticFluxDensity() {
        final var value = new Random().nextDouble();
        final var b = new MagneticFluxDensity(value, MagneticFluxDensityUnit.MICROTESLA);

        MagneticFluxDensityConverter.convert(b, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(MagneticFluxDensityUnit.TESLA, b.getUnit());
        assertEquals(MagneticFluxDensityConverter.convert(value, MagneticFluxDensityUnit.MICROTESLA,
                MagneticFluxDensityUnit.TESLA), b.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewMagneticFluxDensity() {
        final var value = new Random().nextDouble();
        final var inputB = new MagneticFluxDensity(value, MagneticFluxDensityUnit.MICROTESLA);

        final var outputB = MagneticFluxDensityConverter.convertAndReturnNew(inputB, MagneticFluxDensityUnit.TESLA);

        // check
        assertEquals(value, inputB.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MICROTESLA, inputB.getUnit());

        assertEquals(MagneticFluxDensityUnit.TESLA, outputB.getUnit());
        assertEquals(MagneticFluxDensityConverter.convert(value, inputB.getUnit(), outputB.getUnit()),
                outputB.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputMagneticFluxDensityUnit() {
        final var value = new Random().nextDouble();
        final var inputB = new MagneticFluxDensity(value, MagneticFluxDensityUnit.MICROTESLA);

        final var outputB = new MagneticFluxDensity();
        outputB.setUnit(MagneticFluxDensityUnit.TESLA);
        MagneticFluxDensityConverter.convert(inputB, outputB);

        // check
        assertEquals(value, inputB.getValue().doubleValue(), 0.0);
        assertEquals(MagneticFluxDensityUnit.MICROTESLA, inputB.getUnit());

        assertEquals(MagneticFluxDensityUnit.TESLA, outputB.getUnit());
        assertEquals(MagneticFluxDensityConverter.convert(value, inputB.getUnit(), outputB.getUnit()),
                outputB.getValue().doubleValue(), 0.0);
    }
}
