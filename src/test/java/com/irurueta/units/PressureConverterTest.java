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
    private static final double PASCALS_PER_PSI = 6894.757293168;

    private static final double ERROR = 1e-6;

    /**
     * Converts provided value expressed in provided unit into pascals, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link PressureConverter}.
     *
     * @param value value to be converted.
     * @param unit  unit of provided value.
     * @return value converted to pascals.
     */
    private static double toPascals(final double value, final PressureUnit unit) {
        return switch (unit) {
            case KILOPASCAL -> value * PASCALS_PER_KILOPASCAL;
            case BAR -> value * PASCALS_PER_BAR;
            case ATMOSPHERE -> value * PASCALS_PER_ATMOSPHERE;
            case PSI -> value * PASCALS_PER_PSI;
            default -> value;
        };
    }

    /**
     * Converts provided value expressed in pascals into provided unit, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link PressureConverter}.
     *
     * @param pascals value expressed in pascals.
     * @param unit    unit to convert to.
     * @return value converted to provided unit.
     */
    private static double fromPascals(final double pascals, final PressureUnit unit) {
        return switch (unit) {
            case KILOPASCAL -> pascals / PASCALS_PER_KILOPASCAL;
            case BAR -> pascals / PASCALS_PER_BAR;
            case ATMOSPHERE -> pascals / PASCALS_PER_ATMOSPHERE;
            case PSI -> pascals / PASCALS_PER_PSI;
            default -> pascals;
        };
    }

    @Test
    void testPascalKilopascal() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * PASCALS_PER_KILOPASCAL, PressureConverter.kilopascalToPascal(inputValue), ERROR);
        assertEquals(inputValue / PASCALS_PER_KILOPASCAL, PressureConverter.pascalToKilopascal(inputValue), ERROR);
    }

    @Test
    void testPascalBar() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * PASCALS_PER_BAR, PressureConverter.barToPascal(inputValue), ERROR);
        assertEquals(inputValue / PASCALS_PER_BAR, PressureConverter.pascalToBar(inputValue), ERROR);
    }

    @Test
    void testPascalAtmosphere() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * PASCALS_PER_ATMOSPHERE, PressureConverter.atmosphereToPascal(inputValue),
                ERROR);
        assertEquals(inputValue / PASCALS_PER_ATMOSPHERE, PressureConverter.pascalToAtmosphere(inputValue),
                ERROR);
    }

    @Test
    void testPascalPsi() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * PASCALS_PER_PSI, PressureConverter.psiToPascal(inputValue), ERROR);
        assertEquals(inputValue / PASCALS_PER_PSI, PressureConverter.pascalToPsi(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        for (final var inputUnit : PressureUnit.values()) {
            for (final var outputUnit : PressureUnit.values()) {
                final var expected = fromPascals(toPascals(inputValue, inputUnit), outputUnit);
                assertEquals(expected, PressureConverter.convert(inputValue, inputUnit, outputUnit), ERROR);
            }
        }
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                PressureConverter.convert(inputValue, PressureUnit.PASCAL, PressureUnit.PASCAL).doubleValue(),
                ERROR);
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
