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

    private static final double NEWTONS_PER_POUND_FORCE = 4.4482216152605;
    private static final double NEWTONS_PER_DYNE = 0.00001;
    private static final double NEWTONS_PER_KILOGRAM_FORCE = 9.80665;
    private static final double NEWTONS_PER_KILONEWTON = 1000.0;

    private static final double ERROR = 1e-6;

    /**
     * Converts provided value expressed in provided unit into newtons, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link ForceConverter}.
     *
     * @param value value to be converted.
     * @param unit  unit of provided value.
     * @return value converted to newtons.
     */
    private static double toNewtons(final double value, final ForceUnit unit) {
        return switch (unit) {
            case POUND_FORCE -> value * NEWTONS_PER_POUND_FORCE;
            case DYNE -> value * NEWTONS_PER_DYNE;
            case KILOGRAM_FORCE -> value * NEWTONS_PER_KILOGRAM_FORCE;
            case KILONEWTON -> value * NEWTONS_PER_KILONEWTON;
            default -> value;
        };
    }

    /**
     * Converts provided value expressed in newtons into provided unit, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link ForceConverter}.
     *
     * @param newtons value expressed in newtons.
     * @param unit    unit to convert to.
     * @return value converted to provided unit.
     */
    private static double fromNewtons(final double newtons, final ForceUnit unit) {
        return switch (unit) {
            case POUND_FORCE -> newtons / NEWTONS_PER_POUND_FORCE;
            case DYNE -> newtons / NEWTONS_PER_DYNE;
            case KILOGRAM_FORCE -> newtons / NEWTONS_PER_KILOGRAM_FORCE;
            case KILONEWTON -> newtons / NEWTONS_PER_KILONEWTON;
            default -> newtons;
        };
    }

    @Test
    void testNewtonPoundForce() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * NEWTONS_PER_POUND_FORCE, ForceConverter.poundForceToNewton(inputValue), ERROR);
        assertEquals(inputValue / NEWTONS_PER_POUND_FORCE, ForceConverter.newtonToPoundForce(inputValue), ERROR);
    }

    @Test
    void testNewtonDyne() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * NEWTONS_PER_DYNE, ForceConverter.dyneToNewton(inputValue), ERROR);
        assertEquals(inputValue / NEWTONS_PER_DYNE, ForceConverter.newtonToDyne(inputValue), ERROR);
    }

    @Test
    void testNewtonKilogramForce() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.kilogramForceToNewton(inputValue),
                ERROR);
        assertEquals(inputValue / NEWTONS_PER_KILOGRAM_FORCE, ForceConverter.newtonToKilogramForce(inputValue),
                ERROR);
    }

    @Test
    void testNewtonKilonewton() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * NEWTONS_PER_KILONEWTON, ForceConverter.kilonewtonToNewton(inputValue), ERROR);
        assertEquals(inputValue / NEWTONS_PER_KILONEWTON, ForceConverter.newtonToKilonewton(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        for (final var inputUnit : ForceUnit.values()) {
            for (final var outputUnit : ForceUnit.values()) {
                final var expected = fromNewtons(toNewtons(inputValue, inputUnit), outputUnit);
                assertEquals(expected, ForceConverter.convert(inputValue, inputUnit, outputUnit), ERROR);
            }
        }
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                ForceConverter.convert(inputValue, ForceUnit.NEWTON, ForceUnit.NEWTON).doubleValue(), ERROR);
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
