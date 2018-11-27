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
 * Does angle conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying angle measurements.
 */
@SuppressWarnings("WeakerAccess")
public class AngleConverter {

    /**
     * Length for degrees and minutes.
     */
    private static final int DEGREES_AND_MINUTES = 2;

    /**
     * Length for degrees, minutes and seconds.
     */
    private static final int DEGREES_MINUTES_AND_SECONDS = 3;

    /**
     * Number of minutes on each degree.
     */
    private static final int DEGREES_TO_MINUTES = 60;

    /**
     * Number of seconds on each minute.
     */
    private static final int MINUTES_TO_SECONDS = 60;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    AngleConverter() { }

    /**
     * Converts an angle to provided output angle unit.
     * @param input input angle to be converted.
     * @param output output angle where result will be stored and
     *               containing output unit.
     */
    public static void convert(Angle input, Angle output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts an angle to requested output unit.
     * @param input input angle to be converted.
     * @param outputUnit requested output unit.
     * @return converted angle.
     */
    public static Angle convertAndReturnNew(Angle input, AngleUnit outputUnit) {
        Angle result = new Angle();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates an angle to requested output unit.
     * @param angle input angle to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(Angle angle, AngleUnit outputUnit) {
        convert(angle, outputUnit, angle);
    }

    /**
     * Converts an angle to requested output unit.
     * @param input input angle to be converted.
     * @param outputUnit requested output unit.
     * @param result angle instance where result will be stored.
     */
    public static void convert(Angle input, AngleUnit outputUnit, Angle result) {
        Number value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts an angle value from input unit to provided output unit.
     * @param input angle value.
     * @param inputUnit input angle unit.
     * @param outputUnit output angle unit.
     * @return converted angle value.
     */
    public static Number convert(Number input, AngleUnit inputUnit,
                                 AngleUnit outputUnit) {
        return new BigDecimal(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts an angle value from input unit to provided output unit.
     * @param input angle value.
     * @param inputUnit input angle unit.
     * @param outputUnit output angle unit.
     * @return converted angle value.
     */
    public static double convert(double input, AngleUnit inputUnit,
                                 AngleUnit outputUnit) {

        double radians;

        //convert to radians
        switch (inputUnit) {
            case DEGREES:
                radians = Math.toRadians(input);
                break;
            case RADIANS:
            default:
                radians = input;
                break;
        }

        //convert from radians to required output unit
        switch (outputUnit) {
            case DEGREES:
                return Math.toDegrees(radians);
            case RADIANS:
            default:
                return radians;
        }
    }

    /**
     * Converts provided degree value to radians.
     * @param degree degree value.
     * @return same angle converted to radians.
     */
    public static double degreeToRadian(double degree) {
        return Math.toRadians(degree);
    }

    /**
     * Converts provided radian value to degrees.
     * @param radian radian value.
     * @return same angle converted to degrees.
     */
    public static double radianToDegree(double radian) {
        return Math.toDegrees(radian);
    }

    /**
     * Converts provided angle value and unit to integer degrees and decimal minutes.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @param result array where computed degrees and minutes will be stored.
     * @throws IllegalArgumentException if provided array does not have length 2.
     */
    @SuppressWarnings("Duplicates")
    public static void toDegreesAndMinutes(double value, AngleUnit unit,
            double[] result) {
        if (result.length != DEGREES_AND_MINUTES) {
            throw new IllegalArgumentException();
        }

        double decimalDegrees = convert(value, unit, AngleUnit.DEGREES);

        double degrees = Math.floor(decimalDegrees);

        double remainderDegrees = decimalDegrees - degrees;
        double decimalMinutes = remainderDegrees * DEGREES_TO_MINUTES;

        result[0] = degrees;
        result[1] = decimalMinutes;
    }

    /**
     * Converts provided angle value and unit to integer degrees and decimal minutes.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @return array containing degrees and minutes.
     */
    public static double[] toDegreesAndMinutes(double value, AngleUnit unit) {
        double[] result = new double[DEGREES_AND_MINUTES];
        toDegreesAndMinutes(value, unit, result);
        return result;
    }

    /**
     * Converts provided angle value and unit to integer degrees and decimal minutes.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @param result array where computed degrees and minutes will be stored.
     * @throws IllegalArgumentException if provided array does not have length 2.
     */
    public static void toDegreesAndMinutes(Number value, AngleUnit unit,
            double[] result) {
        toDegreesAndMinutes(value.doubleValue(), unit, result);
    }

    /**
     * Converts provided angle value and unit to integer degrees and decimal minutes.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @return array containing degrees and minutes.
     */
    public static double[] toDegreesAndMinutes(Number value, AngleUnit unit) {
        return toDegreesAndMinutes(value.doubleValue(), unit);
    }

    /**
     * Converts provided angle to integer degrees and decimal minutes.
     * @param angle angle to be converted from.
     * @param result array where computed degrees and minutes will be stored.
     * @throws IllegalArgumentException if provided array does not have length 2.
     */
    public static void toDegreesAndMinutes(Angle angle, double[] result) {
        toDegreesAndMinutes(angle.getValue(), angle.getUnit(), result);
    }

    /**
     * Converts provided angle to integer degrees and decimal minutes.
     * @param angle angle to be converted from.
     * @return array containing degrees and minutes.
     */
    public static double[] toDegreesAndMinutes(Angle angle) {
        return toDegreesAndMinutes(angle.getValue(), angle.getUnit());
    }

    /**
     * Gets angle value from provided integer degrees and decimal minutes.
     * @param degrees integer degrees.
     * @param minutes decimal minutes.
     * @param resultUnit unit of returned value.
     * @return decimal degrees.
     * @throws IllegalArgumentException if minutes is negative.
     */
    public static double fromDegreesAndMinutes(int degrees, double minutes,
            AngleUnit resultUnit) {
        if (minutes < 0.0) {
            throw new IllegalArgumentException();
        }
        return convert(degrees + minutes / DEGREES_TO_MINUTES,
                AngleUnit.DEGREES, resultUnit);
    }

    /**
     * Gets angle from provided integer degrees and decimal minutes and
     * sets the value into provided angle instance.
     * @param degrees integer degrees.
     * @param minutes decimal minutes.
     * @param result instance where angle value and unit will be stored.
     * @throws IllegalArgumentException if minutes is negative.
     */
    public static void fromDegreesAndMinutes(int degrees, double minutes, Angle result) {
        result.setValue(fromDegreesAndMinutes(degrees, minutes, result.getUnit()));
    }

    /**
     * Gets new angle containing decimal degrees from provided integer degrees and
     * decimal minutes.
     * @param degrees integer degrees.
     * @param minutes decimal minutes.
     * @param resultUnit unit of returned value.
     * @return a new angle containing computed decimal degrees.
     * @throws IllegalArgumentException if minutes is negative.
     */
    public static Angle fromDegreesAndMinutesAndReturnNew(int degrees, double minutes,
            AngleUnit resultUnit) {
        Angle result = new Angle(0.0, resultUnit);
        fromDegreesAndMinutes(degrees, minutes, result);
        return result;
    }

    /**
     * Converts provided angle value and unit to integer degrees, integer minutes and
     * decimal seconds.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @param result array where computed degrees, minutes and seconds will be stored.
     * @throws IllegalArgumentException if provided array does not have length 3.
     */
    @SuppressWarnings("Duplicates")
    public static void toDegreesMinutesAndSeconds(double value, AngleUnit unit,
            double[] result) {
        if (result.length != DEGREES_MINUTES_AND_SECONDS) {
            throw new IllegalArgumentException();
        }

        double decimalDegrees = convert(value, unit, AngleUnit.DEGREES);

        double degrees = Math.floor(decimalDegrees);

        double remainderDegrees = decimalDegrees - degrees;
        double decimalMinutes = remainderDegrees * DEGREES_TO_MINUTES;

        double minutes = Math.floor(decimalMinutes);

        double remainderMinutes = decimalMinutes - minutes;
        double decimalSeconds = remainderMinutes * MINUTES_TO_SECONDS;

        result[0] = degrees;
        result[1] = minutes;
        result[2] = decimalSeconds;
    }

    /**
     * Converts provided angle value and unit to integer degrees, integer minutes and
     * decimal seconds.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @return array containing degrees, minutes and seconds.
     */
    public static double[] toDegreesMinutesAndSeconds(double value, AngleUnit unit) {
        double[] result = new double[DEGREES_MINUTES_AND_SECONDS];
        toDegreesMinutesAndSeconds(value, unit, result);
        return result;
    }

    /**
     * Converts provided angle value and unit to integer degrees, integer minutes and
     * decimal seconds.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @param result array where computed degrees, minutes and seconds will be stored.
     * @throws IllegalArgumentException if provided array does not have length 3.
     */
    public static void toDegreesMinutesAndSeconds(Number value, AngleUnit unit,
            double[] result) {
        toDegreesMinutesAndSeconds(value.doubleValue(), unit, result);
    }

    /**
     * Converts provided angle value and unit to integer degrees, integer minutes and
     * decimal seconds.
     * @param value angle value to be converted from.
     * @param unit angle unit to be converted from.
     * @return array containing degrees, minutes and seconds.
     */
    public static double[] toDegreesMinutesAndSeconds(Number value, AngleUnit unit) {
        return toDegreesMinutesAndSeconds(value.doubleValue(), unit);
    }

    /**
     * Converts provided angle to integer degrees, integer minutes and decimal seconds.
     * @param angle angle to be converted from.
     * @param result array where computed degrees and minutes will be stored.
     * @throws IllegalArgumentException if provided array does not have length 3.
     */
    public static void toDegreesMinutesAndSeconds(Angle angle, double[] result) {
        toDegreesMinutesAndSeconds(angle.getValue(), angle.getUnit(), result);
    }

    /**
     * Converts provided angle to integer degrees, integer minutes and decimal seconds.
     * @param angle angle to be converted from.
     * @return array containing degrees and minutes.
     */
    public static double[] toDegreesMinutesAndSeconds(Angle angle) {
        return toDegreesMinutesAndSeconds(angle.getValue(), angle.getUnit());
    }

    /**
     * Gets decimal degrees from provided integer degrees, integer minutes and decimal
     * seconds.
     * @param degrees integer degrees.
     * @param minutes integer minutes.
     * @param seconds deciaml seconds.
     * @param resultUnit unit of returned value.
     * @return decimal degrees.
     * @throws IllegalArgumentException if minutes or seconds are negative.
     */
    public static double fromDegreesMinutesAndSeconds(int degrees, int minutes,
            double seconds, AngleUnit resultUnit) {
        if (minutes < 0 || seconds < 0.0) {
            throw new IllegalArgumentException();
        }
        return convert(degrees + (minutes + (seconds / MINUTES_TO_SECONDS)) /
                DEGREES_TO_MINUTES, AngleUnit.DEGREES, resultUnit);
    }

    /**
     * Gets decimal degrees from provided integer degrees, integer minutes and decimal
     * seconds and sets the value into provided angle instance.
     * @param degrees integer degrees.
     * @param minutes integer minutes.
     * @param seconds decimal seconds.
     * @param result instance where angle value and unit will be stored.
     * @throws IllegalArgumentException if minutes or seconds are negative.
     */
    public static void fromDegreesMinutesAndSeconds(int degrees, int minutes,
            double seconds, Angle result) {
        result.setValue(fromDegreesMinutesAndSeconds(degrees, minutes, seconds,
                result.getUnit()));
    }

    /**
     * Gets new angle containing decimal degrees from provided integer degrees, integer
     * minutes and decimal seconds.
     * @param degrees integer degrees.
     * @param minutes integer minutes.
     * @param seconds decimal seconds.
     * @param resultUnit unit of returned value.
     * @return a new angle containing computed decimal degrees.
     * @throws IllegalArgumentException if minutes or seconds are negative.
     */
    public static Angle fromDegreesMinutesAndSecondsAndReturnNew(int degrees,
            int minutes, double seconds, AngleUnit resultUnit) {
        Angle result = new Angle(0.0, resultUnit);
        fromDegreesMinutesAndSeconds(degrees, minutes, seconds, result);
        return result;
    }
}
