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
 * Does acceleration conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying acceleration measurements.
 */
@SuppressWarnings("WeakerAccess")
public class AccelerationConverter {
    /**
     * Standard gravity (g) on Earth. This value is equivalent to 1G
     */
    public static final double STANDARD_GRAVITY = 9.80665;

    /**
     * Number of meters in 1 foot.
     */
    static final double METERS_PER_FOOT = 0.3048;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    AccelerationConverter() { }

    /**
     * Converts an acceleration instance to provided output acceleration unit.
     * @param input input acceleration to be converted.
     * @param output output acceleration where result will be stored containing output unit.
     */
    public static void convert(Acceleration input, Acceleration output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts an acceleration instance to requested output unit.
     * @param input input acceleration to be converted.
     * @param outputUnit requested output unit.
     * @return converted acceleration.
     */
    public static Acceleration convertAndReturnNew(Acceleration input, AccelerationUnit outputUnit) {
        Acceleration result = new Acceleration();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updated an acceleration to requested output unit.
     * @param acceleration input acceleration to be converted and updated
     * @param outputUnit requested output unit.
     */
    public static void convert(Acceleration acceleration, AccelerationUnit outputUnit) {
        convert(acceleration, outputUnit, acceleration);
    }

    /**
     * Converts an acceleration to requested output unit.
     * @param input input acceleration to be converted.
     * @param outputUnit requested output unit.
     * @param result acceleration instance where result will be stored.
     */
    public static void convert(Acceleration input, AccelerationUnit outputUnit, Acceleration result) {
        Number value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts an acceleration value from input unit to provided output unit.
     * @param input acceleration value.
     * @param inputUnit input acceleration unit
     * @param outputUnit output acceleration unit.
     * @return converted acceleration value.
     */
    public static Number convert(Number input, AccelerationUnit inputUnit,
                                 AccelerationUnit outputUnit) {
        return new BigDecimal(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts an acceleration value from input unit to provided output unit.
     * @param input acceleration value.
     * @param inputUnit input acceleration unit.
     * @param outputUnit output acceleration unit.
     * @return converted acceleration value.
     */
    public static double convert(double input, AccelerationUnit inputUnit,
                                 AccelerationUnit outputUnit) {

        double metersPerSquaredSecond;

        //convert to meters per squared seocnd
        switch (inputUnit) {
            case FEET_PER_SQUARED_SECOND:
                metersPerSquaredSecond = feetPerSquaredSecondToMetersPerSquaredSecond(input);
                break;
            case G:
                metersPerSquaredSecond = GToMetersPerSquaredSecond(input);
                break;
            case METERS_PER_SQUARED_SECOND:
            default:
                metersPerSquaredSecond = input;
                break;
        }

        //convert from meters per squared second to required output unit
        switch (outputUnit) {
            case FEET_PER_SQUARED_SECOND:
                return metersPerSquaredSecondToFeetPerSquaredSecond(metersPerSquaredSecond);
            case G:
                return metersPerSquaredSecondToG(metersPerSquaredSecond);
            case METERS_PER_SQUARED_SECOND:
            default:
                return metersPerSquaredSecond;

        }
    }

    /**
     * Converts provided feet per squared second value to meters per squared second.
     * @param feetPerSquaredSecond feet per squared second value.
     * @return same acceleration converted to meters per squared second.
     */
    public static double feetPerSquaredSecondToMetersPerSquaredSecond(double feetPerSquaredSecond) {
        return feetPerSquaredSecond * METERS_PER_FOOT;
    }

    /**
     * Converts provided meters per squared second value to feet per squared second.
     * @param metersPerSquaredSecond meters per squared second value.
     * @return same acceleration converted to feet per squared second.
     */
    public static double metersPerSquaredSecondToFeetPerSquaredSecond(double metersPerSquaredSecond) {
        return metersPerSquaredSecond / METERS_PER_FOOT;
    }

    /**
     * Converts provided acceleration expressed in terms of standard gravity
     * (9.8 m/s^2) to meters per squared second.
     * @param G acceleration relative to standard gravity.
     * @return same acceleration converted to meters per squared second.
     */
    public static double GToMetersPerSquaredSecond(double G) {
        return G * STANDARD_GRAVITY;
    }

    /**
     * Converts provided acceleration expressed in meters per squared second to a
     * value relative to the standard gravity (9.81 m/s^2).
     * @param metersPerSquaredSecond meters per squared second value.
     * @return same acceleration expressed in relative terms to the standard gravity.
     */
    public static double metersPerSquaredSecondToG(double metersPerSquaredSecond) {
        return metersPerSquaredSecond / STANDARD_GRAVITY;
    }
}
