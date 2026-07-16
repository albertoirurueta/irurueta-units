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
    private static final double WATTS_PER_HORSEPOWER = 745.6998715822702;

    private static final double ERROR = 1e-6;

    @Test
    void testWattKilowatt() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / WATTS_PER_KILOWATT, PowerConverter.wattToKilowatt(inputValue), ERROR);
        assertEquals(inputValue * WATTS_PER_KILOWATT, PowerConverter.kilowattToWatt(inputValue), ERROR);
    }

    @Test
    void testWattMegawatt() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / WATTS_PER_MEGAWATT, PowerConverter.wattToMegawatt(inputValue), ERROR);
        assertEquals(inputValue * WATTS_PER_MEGAWATT, PowerConverter.megawattToWatt(inputValue), ERROR);
    }

    @Test
    void testWattHorsepower() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue / WATTS_PER_HORSEPOWER, PowerConverter.wattToHorsepower(inputValue), ERROR);
        assertEquals(inputValue * WATTS_PER_HORSEPOWER, PowerConverter.horsepowerToWatt(inputValue), ERROR);
    }

    @Test
    void testConvertDouble() {
        final var inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.WATT), ERROR);
        assertEquals(PowerConverter.wattToKilowatt(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.KILOWATT), ERROR);
        assertEquals(PowerConverter.wattToMegawatt(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.MEGAWATT), ERROR);
        assertEquals(PowerConverter.wattToHorsepower(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.HORSEPOWER), ERROR);

        assertEquals(PowerConverter.kilowattToWatt(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.KILOWATT, PowerUnit.WATT), ERROR);
        assertEquals(inputValue,
                PowerConverter.convert(inputValue, PowerUnit.KILOWATT, PowerUnit.KILOWATT), ERROR);
        assertEquals(PowerConverter.wattToMegawatt(
                PowerConverter.kilowattToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.KILOWATT, PowerUnit.MEGAWATT), ERROR);
        assertEquals(PowerConverter.wattToHorsepower(
                PowerConverter.kilowattToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.KILOWATT, PowerUnit.HORSEPOWER), ERROR);

        assertEquals(PowerConverter.megawattToWatt(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.MEGAWATT, PowerUnit.WATT), ERROR);
        assertEquals(PowerConverter.wattToKilowatt(
                PowerConverter.megawattToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.MEGAWATT, PowerUnit.KILOWATT), ERROR);
        assertEquals(inputValue,
                PowerConverter.convert(inputValue, PowerUnit.MEGAWATT, PowerUnit.MEGAWATT), ERROR);
        assertEquals(PowerConverter.wattToHorsepower(
                PowerConverter.megawattToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.MEGAWATT, PowerUnit.HORSEPOWER), ERROR);

        assertEquals(PowerConverter.horsepowerToWatt(inputValue),
                PowerConverter.convert(inputValue, PowerUnit.HORSEPOWER, PowerUnit.WATT), ERROR);
        assertEquals(PowerConverter.wattToKilowatt(
                PowerConverter.horsepowerToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.HORSEPOWER, PowerUnit.KILOWATT), ERROR);
        assertEquals(PowerConverter.wattToMegawatt(
                PowerConverter.horsepowerToWatt(inputValue)),
                PowerConverter.convert(inputValue, PowerUnit.HORSEPOWER, PowerUnit.MEGAWATT), ERROR);
        assertEquals(inputValue,
                PowerConverter.convert(inputValue, PowerUnit.HORSEPOWER, PowerUnit.HORSEPOWER), ERROR);
    }

    @Test
    void testConvertNumber() {
        final var inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                PowerConverter.convert(inputValue, PowerUnit.WATT, PowerUnit.WATT)
                        .doubleValue(), ERROR);
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
