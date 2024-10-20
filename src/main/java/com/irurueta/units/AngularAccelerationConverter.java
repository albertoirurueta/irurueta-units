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
 * Does angular acceleration conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying angular acceleration measurements.
 */
@SuppressWarnings("WeakerAccess")
public class AngularAccelerationConverter {

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private AngularAccelerationConverter() {
    }

    /**
     * Converts an angular acceleration to provided output angular acceleration unit.
     *
     * @param input  input angular acceleration to be converted.
     * @param output output angular acceleration where result will be stored and
     *               containing output unit.
     */
    public static void convert(
            final AngularAcceleration input, final AngularAcceleration output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts an angular acceleration to requested output unit.
     *
     * @param input      input angular acceleration to be converted.
     * @param outputUnit requested output unt.
     * @return converted angular acceleration.
     */
    public static AngularAcceleration convertAndReturnNew(
            final AngularAcceleration input, final AngularAccelerationUnit outputUnit) {
        final var result = new AngularAcceleration();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates an angular acceleration to requested output unit.
     *
     * @param angularAcceleration input angular acceleration to be converted and updated.
     * @param outputUnit          requested output unit.
     */
    public static void convert(
            final AngularAcceleration angularAcceleration, final AngularAccelerationUnit outputUnit) {
        convert(angularAcceleration, outputUnit, angularAcceleration);
    }

    /**
     * Converts an angular acceleration to requested output unit.
     *
     * @param input      input angular acceleration to be converted.
     * @param outputUnit requested output unit.
     * @param result     angular acceleration instance where result will be stored.
     */
    public static void convert(
            final AngularAcceleration input,
            final AngularAccelerationUnit outputUnit,
            final AngularAcceleration result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts an angle value from input unit to provided output unit.
     *
     * @param input      angular acceleration value.
     * @param inputUnit  input angular acceleration unit.
     * @param outputUnit output angular acceleration unit.
     * @return converted angular acceleration value.
     */
    public static Number convert(
            final Number input, final AngularAccelerationUnit inputUnit, final AngularAccelerationUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts an angular acceleration value from input unit to provided output unit.
     *
     * @param input      angular acceleration value.
     * @param inputUnit  input angular acceleration unit.
     * @param outputUnit output angular acceleration unit.
     * @return converted angular acceleration value.
     */
    @SuppressWarnings("Duplicates")
    public static double convert(
            final double input, final AngularAccelerationUnit inputUnit, final AngularAccelerationUnit outputUnit) {
        //convert to radians per squared second
        double radiansPerSquaredSecond;
        if (inputUnit == AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND) {
            radiansPerSquaredSecond = Math.toRadians(input);
        } else {
            radiansPerSquaredSecond = input;
        }

        //convert from radians per squared second to required output unit
        if (outputUnit == AngularAccelerationUnit.DEGREES_PER_SQUARED_SECOND) {
            return Math.toDegrees(radiansPerSquaredSecond);
        } else {
            return radiansPerSquaredSecond;
        }
    }

    /**
     * Converts provided degrees per squared second value to radians per squared second.
     *
     * @param degreesPerSquaredSecond degrees per squared second value.
     * @return same angular acceleration converted to radians per squared second.
     */
    public static double degreesPerSquaredSecondToRadiansPerSquaredSecond(final double degreesPerSquaredSecond) {
        return Math.toRadians(degreesPerSquaredSecond);
    }

    /**
     * Converts provided radians per squared second value to degrees per squared second.
     *
     * @param radiansPerSquaredSecond radians per squared second value.
     * @return same angular acceleration converted to degrees per squared second.
     */
    public static double radiansPerSquaredSecondToDegreesPerSquaredSecond(final double radiansPerSquaredSecond) {
        return Math.toDegrees(radiansPerSquaredSecond);
    }
}
