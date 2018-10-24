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

import org.junit.*;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FrequencyConverterTest {

    private static final double HERTZS_PER_KILOHERTZ = 1e3;
    private static final double HERTZ_PER_MEGAHERTZ = 1e6;
    private static final double HERTZ_PER_GIGAHERTZ = 1e9;
    private static final double HERTZ_PER_TERAHERTZ = 1e12;

    private static final double ERROR = 1e-6;

    public FrequencyConverterTest() { }

    @BeforeClass
    public static void setUpClass() { }

    @AfterClass
    public static void tearDownClass() { }

    @Before
    public void setUp() { }

    @After
    public void tearDown() { }

    @Test
    public void testConstructor() {
        //noinspection all
        assertNotNull(new FrequencyConverter());
    }

    @Test
    public void testHertzKiloHertz() {
        double inputValue = new Random().nextDouble();

        assertEquals(FrequencyConverter.hertzToKiloHertz(inputValue),
                inputValue / HERTZS_PER_KILOHERTZ, ERROR);
        assertEquals(FrequencyConverter.kiloHertzToHertz(inputValue),
                inputValue * HERTZS_PER_KILOHERTZ, ERROR);
    }

    @Test
    public void testHertzMegaHertz() {
        double inputValue = new Random().nextDouble();

        assertEquals(FrequencyConverter.hertzToMegaHertz(inputValue),
                inputValue / HERTZ_PER_MEGAHERTZ, ERROR);
        assertEquals(FrequencyConverter.megaHertzToHertz(inputValue),
                inputValue * HERTZ_PER_MEGAHERTZ, ERROR);
    }

    @Test
    public void testHertzGigaHertz() {
        double inputValue = new Random().nextDouble();

        assertEquals(FrequencyConverter.hertzToGigaHertz(inputValue),
                inputValue / HERTZ_PER_GIGAHERTZ, ERROR);
        assertEquals(FrequencyConverter.gigaHertzToHertz(inputValue),
                inputValue * HERTZ_PER_GIGAHERTZ, ERROR);
    }

    @Test
    public void testHertzTeraHertz() {
        double inputValue = new Random().nextDouble();

        assertEquals(FrequencyConverter.hertzToTeraHertz(inputValue),
                inputValue / HERTZ_PER_TERAHERTZ, ERROR);
        assertEquals(FrequencyConverter.teraHertzToHertz(inputValue),
                inputValue * HERTZ_PER_TERAHERTZ, ERROR);
    }

    @Test
    public void testConvertDouble() {
        double inputValue = new Random().nextDouble();

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.HERTZ),
                inputValue, ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.KILOHERTZ),
                FrequencyConverter.hertzToKiloHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.MEGAHERTZ),
                FrequencyConverter.hertzToMegaHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.GIGAHERTZ),
                FrequencyConverter.hertzToGigaHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.TERAHERTZ),
                FrequencyConverter.hertzToTeraHertz(inputValue), ERROR);

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.HERTZ),
                FrequencyConverter.kiloHertzToHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.KILOHERTZ),
                inputValue, ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.MEGAHERTZ),
                FrequencyConverter.hertzToMegaHertz(
                FrequencyConverter.kiloHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.GIGAHERTZ),
                FrequencyConverter.hertzToGigaHertz(
                FrequencyConverter.kiloHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.KILOHERTZ, FrequencyUnit.TERAHERTZ),
                FrequencyConverter.hertzToTeraHertz(
                FrequencyConverter.kiloHertzToHertz(inputValue)), ERROR);

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.MEGAHERTZ, FrequencyUnit.HERTZ),
                FrequencyConverter.megaHertzToHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.MEGAHERTZ, FrequencyUnit.KILOHERTZ),
                FrequencyConverter.hertzToKiloHertz(
                FrequencyConverter.megaHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.MEGAHERTZ, FrequencyUnit.MEGAHERTZ),
                inputValue, ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.MEGAHERTZ, FrequencyUnit.GIGAHERTZ),
                FrequencyConverter.hertzToGigaHertz(
                FrequencyConverter.megaHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.MEGAHERTZ, FrequencyUnit.TERAHERTZ),
                FrequencyConverter.hertzToTeraHertz(
                FrequencyConverter.megaHertzToHertz(inputValue)), ERROR);

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.GIGAHERTZ, FrequencyUnit.HERTZ),
                FrequencyConverter.gigaHertzToHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.GIGAHERTZ, FrequencyUnit.KILOHERTZ),
                FrequencyConverter.hertzToKiloHertz(
                FrequencyConverter.gigaHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.GIGAHERTZ, FrequencyUnit.MEGAHERTZ),
                FrequencyConverter.hertzToMegaHertz(
                FrequencyConverter.gigaHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.GIGAHERTZ, FrequencyUnit.GIGAHERTZ),
                inputValue, ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.GIGAHERTZ, FrequencyUnit.TERAHERTZ),
                FrequencyConverter.hertzToTeraHertz(
                FrequencyConverter.gigaHertzToHertz(inputValue)), ERROR);

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.TERAHERTZ, FrequencyUnit.HERTZ),
                FrequencyConverter.teraHertzToHertz(inputValue), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.TERAHERTZ, FrequencyUnit.KILOHERTZ),
                FrequencyConverter.hertzToKiloHertz(
                FrequencyConverter.teraHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.TERAHERTZ, FrequencyUnit.MEGAHERTZ),
                FrequencyConverter.hertzToMegaHertz(
                FrequencyConverter.teraHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.TERAHERTZ, FrequencyUnit.GIGAHERTZ),
                FrequencyConverter.hertzToGigaHertz(
                FrequencyConverter.teraHertzToHertz(inputValue)), ERROR);
        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.TERAHERTZ, FrequencyUnit.TERAHERTZ),
                inputValue, ERROR);
    }

    @Test
    public void testConvertNumber() {
        BigDecimal inputValue = new BigDecimal(new Random().nextDouble());

        assertEquals(FrequencyConverter.convert(inputValue,
                FrequencyUnit.HERTZ, FrequencyUnit.HERTZ).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertFrequency() {
        double value = new Random().nextDouble();
        Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        Frequency outputFrequency = new Frequency();
        FrequencyConverter.convert(inputFrequency, FrequencyUnit.HERTZ,
                outputFrequency);

        //check
        assertEquals(inputFrequency.getValue().doubleValue(), value, 0.0);
        assertEquals(inputFrequency.getUnit(), FrequencyUnit.KILOHERTZ);

        assertEquals(outputFrequency.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(outputFrequency.getValue().doubleValue(),
                FrequencyConverter.convert(value, inputFrequency.getUnit(),
                outputFrequency.getUnit()), 0.0);
    }

    @Test
    public void testConvertAndUpdateFrequency() {
        double value = new Random().nextDouble();
        Frequency frequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        FrequencyConverter.convert(frequency, FrequencyUnit.HERTZ);

        //check
        assertEquals(frequency.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(frequency.getValue().doubleValue(),
                FrequencyConverter.convert(value, FrequencyUnit.KILOHERTZ,
                FrequencyUnit.HERTZ), 0.0);
    }

    @Test
    public void testConvertAndReturnNewFrequency() {
        double value = new Random().nextDouble();
        Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        Frequency outputFrequency = FrequencyConverter.convertAndReturnNew(
                inputFrequency, FrequencyUnit.HERTZ);

        //check
        assertEquals(inputFrequency.getValue().doubleValue(), value, 0.0);
        assertEquals(inputFrequency.getUnit(), FrequencyUnit.KILOHERTZ);

        assertEquals(outputFrequency.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(outputFrequency.getValue().doubleValue(),
                FrequencyConverter.convert(value, inputFrequency.getUnit(),
                outputFrequency.getUnit()), 0.0);
    }

    @Test
    public void testConvertToOutputFrequencyUnit() {
        double value = new Random().nextDouble();
        Frequency inputFrequency = new Frequency(value, FrequencyUnit.KILOHERTZ);

        Frequency outputFrequency = new Frequency();
        outputFrequency.setUnit(FrequencyUnit.HERTZ);
        FrequencyConverter.convert(inputFrequency, outputFrequency);

        //check
        assertEquals(inputFrequency.getValue().doubleValue(), value, 0.0);
        assertEquals(inputFrequency.getUnit(), FrequencyUnit.KILOHERTZ);

        assertEquals(outputFrequency.getUnit(), FrequencyUnit.HERTZ);
        assertEquals(outputFrequency.getValue().doubleValue(),
                FrequencyConverter.convert(value, inputFrequency.getUnit(),
                outputFrequency.getUnit()), 0.0);
    }
}
