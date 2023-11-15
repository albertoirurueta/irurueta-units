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

import java.math.BigDecimal;

/**
 * Does volume conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying volume measurements.
 */
public class VolumeConverter {

    /**
     * Number of cubic meters in 1 cubic centimeter.
     */
    static final double CUBIC_METER_PER_CUBIC_CENTIMETER = 1e-6;

    /**
     * Number of cubic meters in 1 milliliter.
     */
    static final double CUBIC_METER_PER_MILLILITER = 1e-6;

    /**
     * Number of cubic meters in 1 cubic decimeter.
     */
    static final double CUBIC_METER_PER_CUBIC_DECIMETER = 1e-3;

    /**
     * Number of cubic meters in 1 liter.
     */
    static final double CUBIC_METER_PER_LITER = 1e-3;

    /**
     * Number of cubic meters in 1 hectoliter.
     */
    static final double CUBIC_METER_PER_HECTOLITER = 1e-1;

    /**
     * Number of cubic meters in 1 cubic inch.
     */
    static final double CUBIC_METER_PER_CUBIC_INCH = 0.000016387064;

    /**
     * Number of cubic meters in 1 pint.
     */
    static final double CUBIC_METER_PER_PINT = 0.000473176473;

    /**
     * Number of cubic meters in 1 gallon.
     */
    static final double CUBIC_METER_PER_GALLON = 0.003785411784;

    /**
     * Number of cubic meters in 1 cubic foot.
     */
    static final double CUBIC_METER_PER_CUBIC_FOOT = 0.028316846592;

    /**
     * Number of cubic meters in 1 barrel.
     */
    static final double CUBIC_METER_PER_BARREL = 0.158987294928;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private VolumeConverter() {
    }

    /**
     * Converts a volume in provided output volume unit.
     *
     * @param input  input volume to be converted.
     * @param output output volume where result will be stored and
     *               containing output unit.
     */
    public static void convert(final Volume input, final Volume output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a volume to requested output unit.
     *
     * @param input      input volume to be converted.
     * @param outputUnit requested output unit.
     * @return converted volume.
     */
    public static Volume convertAndReturnNew(
            final Volume input, final VolumeUnit outputUnit) {
        final Volume result = new Volume();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a volume to requested output unit.
     *
     * @param volume     input volume to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Volume volume, final VolumeUnit outputUnit) {
        convert(volume, outputUnit, volume);
    }

    /**
     * Converts a volume to requested output unit.
     *
     * @param input      input volume to be converted.
     * @param outputUnit requested output unit.
     * @param result     volume instance where result will be stored.
     */
    public static void convert(
            final Volume input, final VolumeUnit outputUnit,
            final Volume result) {
        final Number value = convert(input.getValue(), input.getUnit(),
                outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a volume value from input unit to provided output unit.
     *
     * @param input      volume value.
     * @param inputUnit  input volume unit.
     * @param outputUnit output volume unit.
     * @return converted volume value.
     */
    public static Number convert(
            final Number input, final VolumeUnit inputUnit,
            final VolumeUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a volume value from input unit to provided output unit.
     *
     * @param input      volume value.
     * @param inputUnit  input volume unit.
     * @param outputUnit output volume unit.
     * @return converted volume value.
     */
    public static double convert(
            final double input, final VolumeUnit inputUnit,
            final VolumeUnit outputUnit) {
        double cubicMeters;

        // convert to cubic meters
        switch (inputUnit) {
            case CUBIC_CENTIMETER:
                cubicMeters = cubicCentimeterToCubicMeter(input);
                break;
            case MILLILITER:
                cubicMeters = milliliterToCubicMeter(input);
                break;
            case CUBIC_DECIMETER:
                cubicMeters = cubicDecimeterToCubicMeter(input);
                break;
            case LITER:
                cubicMeters = literToCubicMeter(input);
                break;
            case HECTOLITER:
                cubicMeters = hectoliterToCubicMeter(input);
                break;
            case CUBIC_INCH:
                cubicMeters = cubicInchToCubicMeter(input);
                break;
            case PINT:
                cubicMeters = pintToCubicMeter(input);
                break;
            case GALLON:
                cubicMeters = gallonToCubicMeter(input);
                break;
            case CUBIC_FOOT:
                cubicMeters = cubicFootToCubicMeter(input);
                break;
            case BARREL:
                cubicMeters = barrelToCubicMeter(input);
                break;

            case CUBIC_METER:
            default:
                cubicMeters = input;
                break;
        }

        // convert from cubic meters to output unit
        switch (outputUnit) {
            case CUBIC_CENTIMETER:
                return cubicMeterToCubicCentimeter(cubicMeters);
            case MILLILITER:
                return cubicMeterToMilliliter(cubicMeters);
            case CUBIC_DECIMETER:
                return cubicMeterToCubicDecimeter(cubicMeters);
            case LITER:
                return cubicMeterToLiter(cubicMeters);
            case HECTOLITER:
                return cubicMeterToHectoliter(cubicMeters);
            case CUBIC_INCH:
                return cubicMeterToCubicInch(cubicMeters);
            case PINT:
                return cubicMeterToPint(cubicMeters);
            case GALLON:
                return cubicMeterToGallon(cubicMeters);
            case CUBIC_FOOT:
                return cubicMeterToCubicFoot(cubicMeters);
            case BARREL:
                return cubicMeterToBarrel(cubicMeters);

            case CUBIC_METER:
            default:
                return cubicMeters;
        }
    }

    /**
     * Converts provided cubic meter value to cubic centimeters.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to cubic centimeters.
     */
    public static double cubicMeterToCubicCentimeter(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_CUBIC_CENTIMETER;
    }

    /**
     * Converts provided cubic centimeter value to cubic meters.
     *
     * @param cubicCentimeter cubic centimeter value.
     * @return same volume converted to cubic meters.
     */
    public static double cubicCentimeterToCubicMeter(final double cubicCentimeter) {
        return cubicCentimeter * CUBIC_METER_PER_CUBIC_CENTIMETER;
    }

    /**
     * Converts provided cubic meter value to milliliters.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to milliliters.
     */
    public static double cubicMeterToMilliliter(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_MILLILITER;
    }

    /**
     * Converts provided milliliter value to cubic meters.
     *
     * @param milliliter milliliter value.
     * @return same volume converted to cubic meters.
     */
    public static double milliliterToCubicMeter(final double milliliter) {
        return milliliter * CUBIC_METER_PER_MILLILITER;
    }

    /**
     * Converts provided cubic meter value to cubic decimeters.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to cubic decimeters.
     */
    public static double cubicMeterToCubicDecimeter(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_CUBIC_DECIMETER;
    }

    /**
     * Converts provided cubic decimeter value to cubic meters.
     *
     * @param cubicDecimeter cubic decimeter value.
     * @return same volume converted to cubic meters.
     */
    public static double cubicDecimeterToCubicMeter(final double cubicDecimeter) {
        return cubicDecimeter * CUBIC_METER_PER_CUBIC_DECIMETER;
    }

    /**
     * Converts provided cubic meter value to liters.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to liters.
     */
    public static double cubicMeterToLiter(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_LITER;
    }

    /**
     * Converts provided liter value to cubic meters.
     *
     * @param liter liter value.
     * @return same volume converted to cubic meters.
     */
    public static double literToCubicMeter(final double liter) {
        return liter * CUBIC_METER_PER_LITER;
    }

    /**
     * Converts provided cubic meter value to hectoliters.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to hectoliters.
     */
    public static double cubicMeterToHectoliter(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_HECTOLITER;
    }

    /**
     * Converts provided hectoliter value to cubic meters.
     *
     * @param hectoliter hectoliter value.
     * @return same volume converted to cubic meters.
     */
    public static double hectoliterToCubicMeter(final double hectoliter) {
        return hectoliter * CUBIC_METER_PER_HECTOLITER;
    }

    /**
     * Converts provided cubic meter value to cubic inches.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to cubic inches.
     */
    public static double cubicMeterToCubicInch(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_CUBIC_INCH;
    }

    /**
     * Converts provided cubic inch value to cubic meters.
     *
     * @param cubicInch cubic inch value.
     * @return same volume converted to cubic meters.
     */
    public static double cubicInchToCubicMeter(final double cubicInch) {
        return cubicInch * CUBIC_METER_PER_CUBIC_INCH;
    }

    /**
     * Converts provided cubic meter value to pints.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to pints.
     */
    public static double cubicMeterToPint(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_PINT;
    }

    /**
     * Converts provided pint value to cubic meters.
     *
     * @param pint pint value.
     * @return same volume converted to cubic meters.
     */
    public static double pintToCubicMeter(final double pint) {
        return pint * CUBIC_METER_PER_PINT;
    }

    /**
     * Converts provided cubic meter value to gallons.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to gallons.
     */
    public static double cubicMeterToGallon(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_GALLON;
    }

    /**
     * Converts provided gallon value to cubic meters.
     *
     * @param gallon gallon value.
     * @return same volume converted to cubic meters.
     */
    public static double gallonToCubicMeter(final double gallon) {
        return gallon * CUBIC_METER_PER_GALLON;
    }

    /**
     * Converts provided cubic meter value to cubic feet.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to cubic feet.
     */
    public static double cubicMeterToCubicFoot(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_CUBIC_FOOT;
    }

    /**
     * Converts provided cubic foot value to cubic meters.
     *
     * @param cubicFoot cubic foot value.
     * @return same volume converted to cubic meters.
     */
    public static double cubicFootToCubicMeter(final double cubicFoot) {
        return cubicFoot * CUBIC_METER_PER_CUBIC_FOOT;
    }

    /**
     * Converts provided cubic meter value to barrels.
     *
     * @param cubicMeter cubic meter value.
     * @return same volume converted to barrels.
     */
    public static double cubicMeterToBarrel(final double cubicMeter) {
        return cubicMeter / CUBIC_METER_PER_BARREL;
    }

    /**
     * Converts provided barrel value to cubic meters.
     *
     * @param barrel barrel value.
     * @return same volume converted to cubic meters.
     */
    public static double barrelToCubicMeter(final double barrel) {
        return barrel * CUBIC_METER_PER_BARREL;
    }
}
