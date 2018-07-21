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

import java.math.BigDecimal;

/**
 * Does frequency conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying frequency measurements.
 */
public class FrequencyConverter {

    /**
     * Number of Hertz's in one KiloHertz.
     */
    static final double HERTZS_PER_KILOHERTZ = 1e3;

    /**
     * Number of Hertz's in one MegaHertz.
     */
    static final double HERTZ_PER_MEGAHERTZ = 1e6;

    /**
     * Number of Hertz's in one GigaHertz.
     */
    static final double HERTZ_PER_GIGAHERTZ = 1e9;

    /**
     * Number of HErtz's in one TeraHertz.
     */
    static final double HERTZ_PER_TERAHERTZ = 1e12;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    FrequencyConverter() { }

    /**
     * Converts a frequency to provided output frequency unit.
     * @param input input frequency to be converted.
     * @param output output frequency where result will be store and containing
     *               output unit.
     */
    public static void convert(Frequency input, Frequency output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a frequency to requested output unit.
     * @param input input frequency to be converted.
     * @param outputUnit requested output unit.
     * @return converted frequency.
     */
    public static Frequency convertAndReturnNew(Frequency input,
                                                FrequencyUnit outputUnit) {
        Frequency result = new Frequency();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a frequency to requested output unit.
     * @param frequency input frequency to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(Frequency frequency, FrequencyUnit outputUnit) {
        convert(frequency, outputUnit, frequency);
    }

    /**
     * Converts a frequency to requested output unit.
     * @param input input frequency to be converted.
     * @param outputUnit requested output unit.
     * @param result frequency unit where result will be stored.
     */
    public static void convert(Frequency input, FrequencyUnit outputUnit,
                               Frequency result) {
        Number value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a frequency value from input unit to provided output unit.
     * @param input frequency value.
     * @param inputUnit input frequency unit.
     * @param outputUnit output frequency unit.
     * @return converted frequency value.
     */
    public static Number convert(Number input, FrequencyUnit inputUnit,
                                 FrequencyUnit outputUnit) {
        return new BigDecimal(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a frequency value from input unit to provided output unit.
     * @param input frequency value.
     * @param inputUnit input frequency unit.
     * @param outputUnit output frequency unit.
     * @return converted frequency value.
     */
    public static double convert(double input, FrequencyUnit inputUnit,
                                 FrequencyUnit outputUnit) {
        double hertz;

        //convert to hertz's
        switch (inputUnit) {
            case KILOHERTZ:
                hertz = kiloHertzToHertz(input);
                break;
            case MEGAHERTZ:
                hertz = megaHertzToHertz(input);
                break;
            case GIGAHERTZ:
                hertz = gigaHertzToHertz(input);
                break;
            case TERAHERTZ:
                hertz = teraHertzToHertz(input);
                break;

            case HERTZ:
            default:
                hertz = input;
                break;
        }

        //convert from Hertz to required output unit
        switch (outputUnit) {
            case KILOHERTZ:
                return hertzToKiloHertz(hertz);
            case MEGAHERTZ:
                return hertzToMegaHertz(hertz);
            case GIGAHERTZ:
                return hertzToGigaHertz(hertz);
            case TERAHERTZ:
                return hertzToTeraHertz(hertz);

            case HERTZ:
            default:
                return hertz;
        }
    }

    /**
     * Converts provided Hertz value to KiloHertz.
     * @param hertz hertz value.
     * @return same amount of frequency converted to KiloHertz.
     */
    public static double hertzToKiloHertz(double hertz) {
        return hertz / HERTZS_PER_KILOHERTZ;
    }

    /**
     * Converts provided Hertz value to MegaHertz.
     * @param hertz hertz value.
     * @return same amount of frequency converted to MegaHertz.
     */
    public static double hertzToMegaHertz(double hertz) {
        return hertz / HERTZ_PER_MEGAHERTZ;
    }

    /**
     * Converts provided Hertz value to GigaHertz.
     * @param hertz hertz value.
     * @return same amount of frequency converted to GigaHertz.
     */
    public static double hertzToGigaHertz(double hertz) {
        return hertz / HERTZ_PER_GIGAHERTZ;
    }

    /**
     * Converts provided Hertz value to TeraHertz.
     * @param hertz hertz value.
     * @return same amount of frequency converted to TeraHertz.
     */
    public static double hertzToTeraHertz(double hertz) {
        return hertz / HERTZ_PER_TERAHERTZ;
    }

    /**
     * Converts provided KiloHertz value to Hertz.
     * @param kiloHertz KiloHertz value.
     * @return same amount of frequency converted to Hertz.
     */
    public static double kiloHertzToHertz(double kiloHertz) {
        return kiloHertz * HERTZS_PER_KILOHERTZ;
    }

    /**
     * Converts provided MegaHertz value to Hertz.
     * @param megaHertz MegaHertz value.
     * @return same amount of frequency converted to Hertz.
     */
    public static double megaHertzToHertz(double megaHertz) {
        return megaHertz * HERTZ_PER_MEGAHERTZ;
    }

    /**
     * Converts provided GigaHertz value to Hertz.
     * @param gigaHertz GigaHertz value.
     * @return same amount of frequency converted to Hertz.
     */
    public static double gigaHertzToHertz(double gigaHertz) {
        return gigaHertz * HERTZ_PER_GIGAHERTZ;
    }

    /**
     * Converts provided TeraHertz value to Hertz.
     * @param teraHertz TeraHertz value.
     * @return same amount of frequency converted to Hertz.
     */
    public static double teraHertzToHertz(double teraHertz) {
        return teraHertz * HERTZ_PER_TERAHERTZ;
    }
}
