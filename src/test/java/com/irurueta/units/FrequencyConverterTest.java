/*
 * Copyright (C) 2018 Alberto Irurueta Carro (alberto@irurueta.com)
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

public class FrequencyConverterTest {

    private static final double HERTZ_PER_KILOHERTZ = 1e3;
    private static final double HERTZ_PER_MEGAHERTZ = 1e6;
    private static final double HERTZ_PER_GIGAHERTZ = 1e9;
    private static final double HERTZ_PER_TERAHERTZ = 1e12;

    private static final double ERROR = 1e-6;

    @Test
    public void testHertzKiloHertz() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / HERTZ_PER_KILOHERTZ,
                FrequencyConverter.hertzToKiloHertz(inputValue),
                ERROR);
        assertEquals(inputValue * HERTZ_PER_KILOHERTZ,
                FrequencyConverter.kiloHertzToHertz(inputValue),
                ERROR);
    }

    @Test
    public void testHertzMegaHertz() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / HERTZ_PER_MEGAHERTZ,
                FrequencyConverter.hertzToMegaHertz(inputValue),
                ERROR);
        assertEquals(inputValue * HERTZ_PER_MEGAHERTZ,
                FrequencyConverter.megaHertzToHertz(inputValue),
                ERROR);
    }

    @Test
    public void testHertzGigaHertz() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / HERTZ_PER_GIGAHERTZ,
                FrequencyConverter.hertzToGigaHertz(inputValue),
                ERROR);
        assertEquals(inputValue * HERTZ_PER_GIGAHERTZ,
                FrequencyConverter.gigaHertzToHertz(inputValue),
                ERROR);
    }

    @Test
    public void testHertzTeraHertz() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / HERTZ_PER_TERAHERTZ,
                FrequencyConverter.hertzToTeraHertz(inputValue),
                ERROR);
        assertEquals(inputValue * HERTZ_PER_TERAHERTZ,
                FrequencyConverter.teraHertzToHertz(inputValue),
                ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.HERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToKiloHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.KILOHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToMegaHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.MEGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToGigaHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.GIGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToTeraHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.TERAHERTZ),
                ERROR);

        assertEquals(FrequencyConverter.kiloHertzToHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ),
                ERROR);
        assertEquals(inputValue,
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.KILOHERTZ, FrequencyUnit.KILOHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToMegaHertz(
                        FrequencyConverter.kiloHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.KILOHERTZ, FrequencyUnit.MEGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToGigaHertz(
                        FrequencyConverter.kiloHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.KILOHERTZ, FrequencyUnit.GIGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToTeraHertz(
                        FrequencyConverter.kiloHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.KILOHERTZ, FrequencyUnit.TERAHERTZ),
                ERROR);

        assertEquals(FrequencyConverter.megaHertzToHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.MEGAHERTZ, FrequencyUnit.HERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToKiloHertz(
                        FrequencyConverter.megaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.MEGAHERTZ, FrequencyUnit.KILOHERTZ),
                ERROR);
        assertEquals(inputValue,
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.MEGAHERTZ, FrequencyUnit.MEGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToGigaHertz(
                        FrequencyConverter.megaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.MEGAHERTZ, FrequencyUnit.GIGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToTeraHertz(
                        FrequencyConverter.megaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.MEGAHERTZ, FrequencyUnit.TERAHERTZ),
                ERROR);

        assertEquals(FrequencyConverter.gigaHertzToHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.GIGAHERTZ, FrequencyUnit.HERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToKiloHertz(
                        FrequencyConverter.gigaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.GIGAHERTZ, FrequencyUnit.KILOHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToMegaHertz(
                        FrequencyConverter.gigaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.GIGAHERTZ, FrequencyUnit.MEGAHERTZ),
                ERROR);
        assertEquals(inputValue,
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.GIGAHERTZ, FrequencyUnit.GIGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToTeraHertz(
                        FrequencyConverter.gigaHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.GIGAHERTZ, FrequencyUnit.TERAHERTZ),
                ERROR);

        assertEquals(FrequencyConverter.teraHertzToHertz(inputValue),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.TERAHERTZ, FrequencyUnit.HERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToKiloHertz(
                        FrequencyConverter.teraHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.TERAHERTZ, FrequencyUnit.KILOHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToMegaHertz(
                        FrequencyConverter.teraHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.TERAHERTZ, FrequencyUnit.MEGAHERTZ),
                ERROR);
        assertEquals(FrequencyConverter.hertzToGigaHertz(
                        FrequencyConverter.teraHertzToHertz(inputValue)),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.TERAHERTZ, FrequencyUnit.GIGAHERTZ),
                ERROR);
        assertEquals(inputValue,
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.TERAHERTZ, FrequencyUnit.TERAHERTZ),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                FrequencyConverter.convert(inputValue,
                        FrequencyUnit.HERTZ, FrequencyUnit.HERTZ).doubleValue(),
                ERROR);
    }

    @Test
    public void testConvertFrequency() {
        final double value = new Random().nextDouble();
        final Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        final Frequency outputFrequency = new Frequency();
        FrequencyConverter.convert(inputFrequency, FrequencyUnit.HERTZ,
                outputFrequency);

        // check
        assertEquals(value, inputFrequency.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.KILOHERTZ, inputFrequency.getUnit());

        assertEquals(FrequencyUnit.HERTZ, outputFrequency.getUnit());
        assertEquals(FrequencyConverter.convert(value, inputFrequency.getUnit(),
                        outputFrequency.getUnit()),
                outputFrequency.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertAndUpdateFrequency() {
        final double value = new Random().nextDouble();
        final Frequency frequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        FrequencyConverter.convert(frequency, FrequencyUnit.HERTZ);

        // check
        assertEquals(FrequencyUnit.HERTZ, frequency.getUnit());
        assertEquals(FrequencyConverter.convert(value, FrequencyUnit.KILOHERTZ,
                        FrequencyUnit.HERTZ),
                frequency.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertAndReturnNewFrequency() {
        final double value = new Random().nextDouble();
        final Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        final Frequency outputFrequency = FrequencyConverter.convertAndReturnNew(
                inputFrequency, FrequencyUnit.HERTZ);

        // check
        assertEquals(value, inputFrequency.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.KILOHERTZ, inputFrequency.getUnit());

        assertEquals(FrequencyUnit.HERTZ, outputFrequency.getUnit());
        assertEquals(FrequencyConverter.convert(value, inputFrequency.getUnit(),
                        outputFrequency.getUnit()),
                outputFrequency.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertToOutputFrequencyUnit() {
        final double value = new Random().nextDouble();
        final Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        final Frequency outputFrequency = new Frequency();
        outputFrequency.setUnit(FrequencyUnit.HERTZ);
        FrequencyConverter.convert(inputFrequency, outputFrequency);

        // check
        assertEquals(value, inputFrequency.getValue().doubleValue(), 0.0);
        assertEquals(FrequencyUnit.KILOHERTZ, inputFrequency.getUnit());

        assertEquals(FrequencyUnit.HERTZ, outputFrequency.getUnit());
        assertEquals(FrequencyConverter.convert(value, inputFrequency.getUnit(),
                        outputFrequency.getUnit()),
                outputFrequency.getValue().doubleValue(),
                0.0);
    }
}
