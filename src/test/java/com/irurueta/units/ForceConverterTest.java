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

class ForceConverterTest {

    private static final double NEWTONS_PER_KILONEWTON = 1000.0;
    private static final double NEWTONS_PER_DYNE = 1e-5;
    private static final double NEWTONS_PER_KILOGRAM_FORCE = 9.80665;
    private static final double NEWTONS_PER_POUND_FORCE = 4.4482216152605;

    private static final double ERROR = 1e-6;

    @Test
    void testNewtonKilonewton() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / NEWTONS_PER_KILONEWTON, ForceConverter.newtonToKilonewton(inputValue), ERROR);
        assertEquals(inputValue * NEWTONS_PER_KILONEWTON, ForceConverter.kilonewtonToNewton(inputValue), ERROR);
    }

    @Test
    void testNewtonDyne() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / NEWTONS_PER_DYNE, ForceConverter.newtonToDyne(inputValue), ERROR);
        assertEquals(inputValue * NEWTONS_PER_DYNE, ForceConverter.dyneToNewton(inputValue), ERROR);
    }

    @Test
    void testNewtonKilogramForce() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.newtonToKilogramForce(inputValue),
                ERROR);
        assertEquals(inputValue * NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.kilogramForceToNewton(inputValue),
                ERROR);
    }

    @Test
    void testNewtonPoundForce() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / NEWTONS_PER_POUND_FORCE, ForceConverter.newtonToPoundForce(inputValue), ERROR);
        assertEquals(inputValue * NEWTONS_PER_POUND_FORCE, ForceConverter.poundForceToNewton(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        // from NEWTON
        assertEquals(inputValue,
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.NEWTON), ERROR);
        assertEquals(ForceConverter.newtonToKilonewton(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.KILONEWTON), ERROR);
        assertEquals(ForceConverter.newtonToPoundForce(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.POUND_FORCE), ERROR);
        assertEquals(ForceConverter.newtonToDyne(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.DYNE), ERROR);
        assertEquals(ForceConverter.newtonToKilogramForce(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.KILOGRAM_FORCE), ERROR);

        // from KILONEWTON
        assertEquals(ForceConverter.kilonewtonToNewton(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.KILONEWTON, ForceUnit.NEWTON), ERROR);
        assertEquals(inputValue,
                ForceConverter.convert(inputValue, ForceUnit.KILONEWTON, ForceUnit.KILONEWTON), ERROR);
        assertEquals(ForceConverter.newtonToPoundForce(
                ForceConverter.kilonewtonToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILONEWTON, ForceUnit.POUND_FORCE), ERROR);
        assertEquals(ForceConverter.newtonToDyne(
                ForceConverter.kilonewtonToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILONEWTON, ForceUnit.DYNE), ERROR);
        assertEquals(ForceConverter.newtonToKilogramForce(
                ForceConverter.kilonewtonToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILONEWTON, ForceUnit.KILOGRAM_FORCE), ERROR);

        // from POUND_FORCE
        assertEquals(ForceConverter.poundForceToNewton(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.POUND_FORCE, ForceUnit.NEWTON), ERROR);
        assertEquals(ForceConverter.newtonToKilonewton(
                ForceConverter.poundForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.POUND_FORCE, ForceUnit.KILONEWTON), ERROR);
        assertEquals(inputValue,
                ForceConverter.convert(inputValue, ForceUnit.POUND_FORCE, ForceUnit.POUND_FORCE), ERROR);
        assertEquals(ForceConverter.newtonToDyne(
                ForceConverter.poundForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.POUND_FORCE, ForceUnit.DYNE), ERROR);
        assertEquals(ForceConverter.newtonToKilogramForce(
                ForceConverter.poundForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.POUND_FORCE, ForceUnit.KILOGRAM_FORCE), ERROR);

        // from DYNE
        assertEquals(ForceConverter.dyneToNewton(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.DYNE, ForceUnit.NEWTON), ERROR);
        assertEquals(ForceConverter.newtonToKilonewton(
                ForceConverter.dyneToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.DYNE, ForceUnit.KILONEWTON), ERROR);
        assertEquals(ForceConverter.newtonToPoundForce(
                ForceConverter.dyneToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.DYNE, ForceUnit.POUND_FORCE), ERROR);
        assertEquals(inputValue,
                ForceConverter.convert(inputValue, ForceUnit.DYNE, ForceUnit.DYNE), ERROR);
        assertEquals(ForceConverter.newtonToKilogramForce(
                ForceConverter.dyneToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.DYNE, ForceUnit.KILOGRAM_FORCE), ERROR);

        // from KILOGRAM_FORCE
        assertEquals(ForceConverter.kilogramForceToNewton(inputValue),
                ForceConverter.convert(inputValue, ForceUnit.KILOGRAM_FORCE, ForceUnit.NEWTON), ERROR);
        assertEquals(ForceConverter.newtonToKilonewton(
                ForceConverter.kilogramForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILOGRAM_FORCE, ForceUnit.KILONEWTON), ERROR);
        assertEquals(ForceConverter.newtonToPoundForce(
                ForceConverter.kilogramForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILOGRAM_FORCE, ForceUnit.POUND_FORCE), ERROR);
        assertEquals(ForceConverter.newtonToDyne(
                ForceConverter.kilogramForceToNewton(inputValue)),
                ForceConverter.convert(inputValue, ForceUnit.KILOGRAM_FORCE, ForceUnit.DYNE), ERROR);
        assertEquals(inputValue,
                ForceConverter.convert(inputValue, ForceUnit.KILOGRAM_FORCE, ForceUnit.KILOGRAM_FORCE), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.NEWTON)
                        .doubleValue(), ERROR);
    }

    @Test
    void testConvertForce() {
        final var value = new Random().nextDouble();
        final var inputForce = new Force(value, ForceUnit.NEWTON);

        final var outputForce = new Force();
        ForceConverter.convert(inputForce, ForceUnit.KILONEWTON, outputForce);

        // check
        assertEquals(value, inputForce.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, inputForce.getUnit());

        assertEquals(ForceUnit.KILONEWTON, outputForce.getUnit());
        assertEquals(ForceConverter.convert(value, inputForce.getUnit(), outputForce.getUnit()),
                outputForce.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdateForce() {
        final var value = new Random().nextDouble();
        final var force = new Force(value, ForceUnit.NEWTON);

        ForceConverter.convert(force, ForceUnit.KILONEWTON);

        // check
        assertEquals(ForceUnit.KILONEWTON, force.getUnit());
        assertEquals(ForceConverter.convert(value, ForceUnit.NEWTON, ForceUnit.KILONEWTON),
                force.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewForce() {
        final var value = new Random().nextDouble();
        final var inputForce = new Force(value, ForceUnit.NEWTON);

        final var outputForce = ForceConverter.convertAndReturnNew(inputForce, ForceUnit.KILONEWTON);

        // check
        assertEquals(value, inputForce.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, inputForce.getUnit());

        assertEquals(ForceUnit.KILONEWTON, outputForce.getUnit());
        assertEquals(ForceConverter.convert(value, inputForce.getUnit(), outputForce.getUnit()),
                outputForce.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputForceUnit() {
        final var value = new Random().nextDouble();
        final var inputForce = new Force(value, ForceUnit.NEWTON);

        final var outputForce = new Force();
        outputForce.setUnit(ForceUnit.KILONEWTON);
        ForceConverter.convert(inputForce, outputForce);

        // check
        assertEquals(value, inputForce.getValue().doubleValue(), 0.0);
        assertEquals(ForceUnit.NEWTON, inputForce.getUnit());

        assertEquals(ForceUnit.KILONEWTON, outputForce.getUnit());
        assertEquals(ForceConverter.convert(value, inputForce.getUnit(),
                outputForce.getUnit()), outputForce.getValue().doubleValue(), 0.0);
    }
}
