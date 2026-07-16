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

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PressureConverterTest {

    private static final double PASCALS_PER_KILOPASCAL = 1000.0;
    private static final double PASCALS_PER_BAR = 100000.0;
    private static final double PASCALS_PER_ATMOSPHERE = 101325.0;
    private static final double PASCALS_PER_PSI = 6894.757293168361;

    private static final double ERROR = 1e-6;

    @Test
    void testPascalKilopascal() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / PASCALS_PER_KILOPASCAL, PressureConverter.pascalToKilopascal(inputValue),
                ERROR);
        assertEquals(inputValue * PASCALS_PER_KILOPASCAL, PressureConverter.kilopascalToPascal(inputValue),
                ERROR);
    }

    @Test
    void testPascalBar() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / PASCALS_PER_BAR, PressureConverter.pascalToBar(inputValue), ERROR);
        assertEquals(inputValue * PASCALS_PER_BAR, PressureConverter.barToPascal(inputValue), ERROR);
    }

    @Test
    void testPascalAtmosphere() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / PASCALS_PER_ATMOSPHERE, PressureConverter.pascalToAtmosphere(inputValue),
                ERROR);
        assertEquals(inputValue * PASCALS_PER_ATMOSPHERE, PressureConverter.atmosphereToPascal(inputValue),
                ERROR);
    }

    @Test
    void testPascalPsi() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / PASCALS_PER_PSI, PressureConverter.pascalToPsi(inputValue), ERROR);
        assertEquals(inputValue * PASCALS_PER_PSI, PressureConverter.psiToPascal(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.PASCAL), ERROR);
        assertEquals(PressureConverter.pascalToKilopascal(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(PressureConverter.pascalToBar(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.BAR), ERROR);
        assertEquals(PressureConverter.pascalToAtmosphere(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(PressureConverter.pascalToPsi(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.PSI), ERROR);

        assertEquals(PressureConverter.kilopascalToPascal(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.KILOPASCAL, PressureUnit.PASCAL), ERROR);
        assertEquals(inputValue,
                PressureConverter.convert(inputValue, PressureUnit.KILOPASCAL, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(PressureConverter.pascalToBar(
                PressureConverter.kilopascalToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.KILOPASCAL, PressureUnit.BAR), ERROR);
        assertEquals(PressureConverter.pascalToAtmosphere(
                PressureConverter.kilopascalToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.KILOPASCAL, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(PressureConverter.pascalToPsi(
                PressureConverter.kilopascalToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.KILOPASCAL, PressureUnit.PSI), ERROR);

        assertEquals(PressureConverter.barToPascal(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.BAR, PressureUnit.PASCAL), ERROR);
        assertEquals(PressureConverter.pascalToKilopascal(
                PressureConverter.barToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.BAR, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(inputValue,
                PressureConverter.convert(inputValue, PressureUnit.BAR, PressureUnit.BAR), ERROR);
        assertEquals(PressureConverter.pascalToAtmosphere(
                PressureConverter.barToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.BAR, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(PressureConverter.pascalToPsi(
                PressureConverter.barToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.BAR, PressureUnit.PSI), ERROR);

        assertEquals(PressureConverter.atmosphereToPascal(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.ATMOSPHERE, PressureUnit.PASCAL), ERROR);
        assertEquals(PressureConverter.pascalToKilopascal(
                PressureConverter.atmosphereToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.ATMOSPHERE, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(PressureConverter.pascalToBar(
                PressureConverter.atmosphereToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.ATMOSPHERE, PressureUnit.BAR), ERROR);
        assertEquals(inputValue,
                PressureConverter.convert(inputValue, PressureUnit.ATMOSPHERE, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(PressureConverter.pascalToPsi(
                PressureConverter.atmosphereToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.ATMOSPHERE, PressureUnit.PSI), ERROR);

        assertEquals(PressureConverter.psiToPascal(inputValue),
                PressureConverter.convert(inputValue, PressureUnit.PSI, PressureUnit.PASCAL), ERROR);
        assertEquals(PressureConverter.pascalToKilopascal(
                PressureConverter.psiToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.PSI, PressureUnit.KILOPASCAL), ERROR);
        assertEquals(PressureConverter.pascalToBar(
                PressureConverter.psiToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.PSI, PressureUnit.BAR), ERROR);
        assertEquals(PressureConverter.pascalToAtmosphere(
                PressureConverter.psiToPascal(inputValue)),
                PressureConverter.convert(inputValue, PressureUnit.PSI, PressureUnit.ATMOSPHERE), ERROR);
        assertEquals(inputValue,
                PressureConverter.convert(inputValue, PressureUnit.PSI, PressureUnit.PSI), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.PASCAL)
                        .doubleValue(), ERROR);
    }

    @Test
    void testConvertPressure() {
        final var value = new Random().nextDouble();
        final var inputPressure = new Pressure(value, PressureUnit.PASCAL);

        final var outputPressure = new Pressure();
        PressureConverter.convert(inputPressure, PressureUnit.KILOPASCAL, outputPressure);

        // check
        assertEquals(value, inputPressure.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.PASCAL, inputPressure.getUnit());

        assertEquals(PressureUnit.KILOPASCAL, outputPressure.getUnit());
        assertEquals(PressureConverter.convert(value, inputPressure.getUnit(), outputPressure.getUnit()),
                outputPressure.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdatePressure() {
        final var value = new Random().nextDouble();
        final var pressure = new Pressure(value, PressureUnit.PASCAL);

        PressureConverter.convert(pressure, PressureUnit.KILOPASCAL);

        // check
        assertEquals(PressureUnit.KILOPASCAL, pressure.getUnit());
        assertEquals(PressureConverter.convert(value, PressureUnit.PASCAL, PressureUnit.KILOPASCAL),
                pressure.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewPressure() {
        final var value = new Random().nextDouble();
        final var inputPressure = new Pressure(value, PressureUnit.PASCAL);

        final var outputPressure = PressureConverter.convertAndReturnNew(inputPressure, PressureUnit.KILOPASCAL);

        // check
        assertEquals(value, inputPressure.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.PASCAL, inputPressure.getUnit());

        assertEquals(PressureUnit.KILOPASCAL, outputPressure.getUnit());
        assertEquals(PressureConverter.convert(value, inputPressure.getUnit(), outputPressure.getUnit()),
                outputPressure.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputPressureUnit() {
        final var value = new Random().nextDouble();
        final var inputPressure = new Pressure(value, PressureUnit.PASCAL);

        final var outputPressure = new Pressure();
        outputPressure.setUnit(PressureUnit.KILOPASCAL);
        PressureConverter.convert(inputPressure, outputPressure);

        // check
        assertEquals(value, inputPressure.getValue().doubleValue(), 0.0);
        assertEquals(PressureUnit.PASCAL, inputPressure.getUnit());

        assertEquals(PressureUnit.KILOPASCAL, outputPressure.getUnit());
        assertEquals(PressureConverter.convert(value, inputPressure.getUnit(),
                outputPressure.getUnit()), outputPressure.getValue().doubleValue(), 0.0);
    }
}
