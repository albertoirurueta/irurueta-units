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
 * Does angular speed conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying angular speed measurements.
 */
@SuppressWarnings("WeakerAccess")
public class AngularSpeedConverter {

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    AngularSpeedConverter() { }

    /**
     * Converts an angular speed to provided output angular speed unit.
     * @param input input angular speed to be converted.
     * @param output output angular speed where result will be stored and
     *               containing output unit.
     */
    public static void convert(AngularSpeed input, AngularSpeed output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts an angular speed to requested output unit.
     * @param input input angular speed to be converted.
     * @param outputUnit requested output unit.
     * @return converted angular speed.
     */
    public static AngularSpeed convertAndReturnNew(AngularSpeed input,
            AngularSpeedUnit outputUnit) {
        AngularSpeed result = new AngularSpeed();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates an angular speed to requested output unit.
     * @param angularSpeed input angular speed to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(AngularSpeed angularSpeed, AngularSpeedUnit outputUnit) {
        convert(angularSpeed, outputUnit, angularSpeed);
    }

    /**
     * Converts an angular speed to requested output unit.
     * @param input input angular speed to be converted.
     * @param outputUnit requested output unit.
     * @param result angular speed instance where result will be stored.
     */
    public static void convert(AngularSpeed input, AngularSpeedUnit outputUnit,
                               AngularSpeed result) {
        Number value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts an angle value from input unit to provided output unit.
     * @param input angular speed value.
     * @param inputUnit input angular speed unit.
     * @param outputUnit output angular speed unit.
     * @return converted angular speed value.
     */
    public static Number convert(Number input, AngularSpeedUnit inputUnit,
                                 AngularSpeedUnit outputUnit) {
        return new BigDecimal(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts an angular speed value from input unit to provided output unit.
     * @param input angular speed value.
     * @param inputUnit input angular speed unit.
     * @param outputUnit output angular speed unit.
     * @return converted angular speed value.
     */
    public static double convert(double input, AngularSpeedUnit inputUnit,
                                 AngularSpeedUnit outputUnit) {
        double radiansPerSecond;

        //convert to radians per second
        switch (inputUnit) {
            case DEGREES_PER_SECOND:
                radiansPerSecond = Math.toRadians(input);
                break;
            case RADIANS_PER_SECOND:
            default:
                radiansPerSecond = input;
                break;
        }

        //convert from radians per second to required output unit
        switch (outputUnit) {
            case DEGREES_PER_SECOND:
                return Math.toDegrees(radiansPerSecond);
            case RADIANS_PER_SECOND:
            default:
                return radiansPerSecond;
        }
    }

    /**
     * Converts provided degrees per second value to radians per second.
     * @param degreesPerSecond degrees per second value.
     * @return same angular speed converted to radians per second.
     */
    public static double degreesPerSecondToRadiansPerSecond(double degreesPerSecond) {
        return Math.toRadians(degreesPerSecond);
    }

    /**
     * Converts provided radians per second value to degrees per second.
     * @param radiansPerSecond radians per second value.
     * @return same angular speed converted to degrees per second.
     */
    public static double radiansPerSecondToDegreesPerSecond(double radiansPerSecond) {
        return Math.toDegrees(radiansPerSecond);
    }
}
