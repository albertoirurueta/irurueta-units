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
 * Does speed conversions to different units.
 * To prevent loss of accuracy, conversion should only be done as a final step
 * before displaying speed measurements.
 */
@SuppressWarnings("WeakerAccess")
public class SpeedConverter {

    /**
     * Number of meters in 1 kilometer.
     */
    static final double METERS_PER_KILOMETER = 1000.0;

    /**
     * Number of meters in 1 mile.
     */
    static final double METERS_PER_MILE = 1609.344;

    /**
     * Number of meters in 1 foot.
     */
    static final double METERS_PER_FOOT = 0.3048;

    /**
     * Number of seconds in 1 hour.
     */
    static final double SECONDS_PER_HOUR = 3600.0;

    /**
     * Constructor.
     * Prevents instantiation of helper class.
     */
    private SpeedConverter() {
    }

    /**
     * Converts a speed instance to provided output speed unit.
     *
     * @param input  input speed to be converted.
     * @param output output speed where result will be stored containing output unit.
     */
    public static void convert(final Speed input, final Speed output) {
        convert(input, output.getUnit(), output);
    }

    /**
     * Converts a speed instance to requested output unit.
     *
     * @param input      input speed to be converted.
     * @param outputUnit requested output unit.
     * @return converted speed.
     */
    public static Speed convertAndReturnNew(
            final Speed input, final SpeedUnit outputUnit) {
        final var result = new Speed();
        convert(input, outputUnit, result);
        return result;
    }

    /**
     * Converts and updates a speed to requested output unit.
     *
     * @param speed      input speed to be converted and updated.
     * @param outputUnit requested output unit.
     */
    public static void convert(final Speed speed, final SpeedUnit outputUnit) {
        convert(speed, outputUnit, speed);
    }

    /**
     * Converts a speed to requested output unit.
     *
     * @param input      input speed to be converted.
     * @param outputUnit requested output unit.
     * @param result     speed instance where result will be stored.
     */
    public static void convert(
            final Speed input, final SpeedUnit outputUnit, final Speed result) {
        final var value = convert(input.getValue(), input.getUnit(), outputUnit);
        result.setValue(value);
        result.setUnit(outputUnit);
    }

    /**
     * Converts a speed value from input unit to provided output unit.
     *
     * @param input      speed value.
     * @param inputUnit  input speed unit.
     * @param outputUnit output speed unit.
     * @return converted speed value.
     */
    public static Number convert(final Number input, final SpeedUnit inputUnit, final SpeedUnit outputUnit) {
        return BigDecimal.valueOf(convert(input.doubleValue(), inputUnit, outputUnit));
    }

    /**
     * Converts a speed value from input unit to provided output unit.
     *
     * @param input      speed value.
     * @param inputUnit  input speed unit.
     * @param outputUnit output speed unit.
     * @return converted speed value.
     */
    public static double convert(final double input, final SpeedUnit inputUnit, final SpeedUnit outputUnit) {
        //convert to meters per second
        final var metersPerSecond = switch (inputUnit) {
            case FEET_PER_SECOND -> feetPerSecondToMetersPerSecond(input);
            case MILES_PER_HOUR -> milesPerHourToMetersPerSecond(input);
            case KILOMETERS_PER_HOUR -> kilometersPerHourToMetersPerSecond(input);
            case KILOMETERS_PER_SECOND -> kilometersPerSecondToMetersPerSecond(input);
            default -> input;
        };

        //convert from meters per second to required output unit
        return switch (outputUnit) {
            case FEET_PER_SECOND -> metersPerSecondToFeetPerSecond(metersPerSecond);
            case MILES_PER_HOUR -> metersPerSecondToMilesPerHour(metersPerSecond);
            case KILOMETERS_PER_HOUR -> metersPerSecondToKilometersPerHour(metersPerSecond);
            case KILOMETERS_PER_SECOND -> metersPerSecondToKilometersPerSecond(metersPerSecond);
            default -> metersPerSecond;
        };
    }

    /**
     * Converts provided feet per second value to meters per second.
     *
     * @param feetPerSecond feet per second value.
     * @return same speed converted to meters per second.
     */
    public static double feetPerSecondToMetersPerSecond(final double feetPerSecond) {
        return feetPerSecond * METERS_PER_FOOT;
    }

    /**
     * Converts provided meters per second value to feet per second.
     *
     * @param metersPerSecond meters per second value.
     * @return same speed converted to feet per second.
     */
    public static double metersPerSecondToFeetPerSecond(final double metersPerSecond) {
        return metersPerSecond / METERS_PER_FOOT;
    }

    /**
     * Converts provided miles per hour value to meters per second.
     *
     * @param milesPerHour miles per hour value.
     * @return same speed converted to meters per second.
     */
    public static double milesPerHourToMetersPerSecond(final double milesPerHour) {
        return milesPerHour * METERS_PER_MILE / SECONDS_PER_HOUR;
    }

    /**
     * Converts provided meters per second value to miles per hour.
     *
     * @param metersPerSecond meters per second value.
     * @return same speed converted to miles per hour.
     */
    public static double metersPerSecondToMilesPerHour(final double metersPerSecond) {
        return metersPerSecond / METERS_PER_MILE * SECONDS_PER_HOUR;
    }

    /**
     * Converts provided Kilometers per hour value to meters per second.
     *
     * @param kilometersPerHour Kilometers per hour value.
     * @return same speed converted to meters per second.
     */
    public static double kilometersPerHourToMetersPerSecond(final double kilometersPerHour) {
        return kilometersPerHour * METERS_PER_KILOMETER / SECONDS_PER_HOUR;
    }

    /**
     * Converts provided meters per second value to Kilometers per hour.
     *
     * @param metersPerSecond meters per second value.
     * @return same speed converted to Kilometers per hour.
     */
    public static double metersPerSecondToKilometersPerHour(final double metersPerSecond) {
        return metersPerSecond / METERS_PER_KILOMETER * SECONDS_PER_HOUR;
    }

    /**
     * Converts provided Kilometers per second value to meters per second.
     *
     * @param kilometersPerSecond Kilometers per second value.
     * @return same speed converted to meters per second.
     */
    public static double kilometersPerSecondToMetersPerSecond(final double kilometersPerSecond) {
        return kilometersPerSecond * METERS_PER_KILOMETER;
    }

    /**
     * Converts provided meters per second value to Kilometers per second.
     *
     * @param metersPerSecond meters per second value.
     * @return same speed converted to Kilometers per second.
     */
    public static double metersPerSecondToKilometersPerSecond(final double metersPerSecond) {
        return metersPerSecond / METERS_PER_KILOMETER;
    }
}
