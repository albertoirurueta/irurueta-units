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

class PowerConverterTest {

    private static final double WATTS_PER_KILOWATT = 1000.0;
    private static final double WATTS_PER_MEGAWATT = 1000000.0;
    private static final double WATTS_PER_HORSEPOWER = 745.699872;

    private static final double ERROR = 1e-6;

    /**
     * Converts provided value expressed in provided unit into watts, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link PowerConverter}.
     *
     * @param value value to be converted.
     * @param unit  unit of provided value.
     * @return value converted to watts.
     */
    private static double toWatts(final double value, final PowerUnit unit) {
        return switch (unit) {
            case KILOWATT -> value * WATTS_PER_KILOWATT;
            case MEGAWATT -> value * WATTS_PER_MEGAWATT;
            case HORSEPOWER -> value * WATTS_PER_HORSEPOWER;
            default -> value;
        };
    }

    /**
     * Converts provided value expressed in watts into provided unit, using locally
     * re-declared conversion factors, independent from the ones used internally by
     * {@link PowerConverter}.
     *
     * @param watts value expressed in watts.
     * @param unit  unit to convert to.
     * @return value converted to provided unit.
     */
    private static double fromWatts(final double watts, final PowerUnit unit) {
        return switch (unit) {
            case KILOWATT -> watts / WATTS_PER_KILOWATT;
            case MEGAWATT -> watts / WATTS_PER_MEGAWATT;
            case HORSEPOWER -> watts / WATTS_PER_HORSEPOWER;
            default -> watts;
        };
    }

    @Test
    void testWattKilowatt() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * WATTS_PER_KILOWATT, PowerConverter.kilowattToWatt(inputValue), ERROR);
        assertEquals(inputValue / WATTS_PER_KILOWATT, PowerConverter.wattToKilowatt(inputValue), ERROR);
    }

    @Test
    void testWattMegawatt() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * WATTS_PER_MEGAWATT, PowerConverter.megawattToWatt(inputValue), ERROR);
        assertEquals(inputValue / WATTS_PER_MEGAWATT, PowerConverter.wattToMegawatt(inputValue), ERROR);
    }

    @Test
    void testWattHorsepower() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue * WATTS_PER_HORSEPOWER, PowerConverter.horsepowerToWatt(inputValue), ERROR);
        assertEquals(inputValue / WATTS_PER_HORSEPOWER, PowerConverter.wattToHorsepower(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        for (final var inputUnit : PowerUnit.values()) {
            for (final var outputUnit : PowerUnit.values()) {
                final var expected = fromWatts(toWatts(inputValue, inputUnit), outputUnit);
                assertEquals(expected, PowerConverter.convert(inputValue, inputUnit, outputUnit), ERROR);
            }
        }
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.WATT).doubleValue(),
                ERROR);
    }

    @Test
    void testConvertPower() {
        final var value = new Random().nextDouble();
        final var inputPower = new Power(value, PowerUnit.WATT);

        final var outputPower = new Power();
        PowerConverter.convert(inputPower, PowerUnit.KILOWATT, outputPower);

        // check
        assertEquals(value, inputPower.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.WATT, inputPower.getUnit());

        assertEquals(PowerUnit.KILOWATT, outputPower.getUnit());
        assertEquals(PowerConverter.convert(value, inputPower.getUnit(), outputPower.getUnit()),
                outputPower.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndUpdatePower() {
        final var value = new Random().nextDouble();
        final var power = new Power(value, PowerUnit.WATT);

        PowerConverter.convert(power, PowerUnit.KILOWATT);

        // check
        assertEquals(PowerUnit.KILOWATT, power.getUnit());
        assertEquals(PowerConverter.convert(value, PowerUnit.WATT, PowerUnit.KILOWATT),
                power.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertAndReturnNewPower() {
        final var value = new Random().nextDouble();
        final var inputPower = new Power(value, PowerUnit.WATT);

        final var outputPower = PowerConverter.convertAndReturnNew(inputPower, PowerUnit.KILOWATT);

        // check
        assertEquals(value, inputPower.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.WATT, inputPower.getUnit());

        assertEquals(PowerUnit.KILOWATT, outputPower.getUnit());
        assertEquals(PowerConverter.convert(value, inputPower.getUnit(), outputPower.getUnit()),
                outputPower.getValue().doubleValue(), 0.0);
    }

    @Test
    void testConvertToOutputPowerUnit() {
        final var value = new Random().nextDouble();
        final var inputPower = new Power(value, PowerUnit.WATT);

        final var outputPower = new Power();
        outputPower.setUnit(PowerUnit.KILOWATT);
        PowerConverter.convert(inputPower, outputPower);

        // check
        assertEquals(value, inputPower.getValue().doubleValue(), 0.0);
        assertEquals(PowerUnit.WATT, inputPower.getUnit());

        assertEquals(PowerUnit.KILOWATT, outputPower.getUnit());
        assertEquals(PowerConverter.convert(value, inputPower.getUnit(),
                outputPower.getUnit()), outputPower.getValue().doubleValue(), 0.0);
    }
}
