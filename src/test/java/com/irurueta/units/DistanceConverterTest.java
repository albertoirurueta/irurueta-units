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

import static org.junit.Assert.*;

public class DistanceConverterTest {

    private static final double METERS_PER_MILLIMETER = 0.001;
    private static final double METERS_PER_CENTIMETER = 0.01;
    private static final double METERS_PER_KILOMETER = 1000.0;
    private static final double METERS_PER_INCH = 0.0254;
    private static final double METERS_PER_FOOT = 0.3048;
    private static final double METERS_PER_YARD = 0.9144;
    private static final double METERS_PER_MILE = 1609.344;

    private static final double ERROR = 1e-6;

    @Test
    public void testMetersMillimeters() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_MILLIMETER,
                DistanceConverter.meterToMillimeter(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_MILLIMETER,
                DistanceConverter.millimeterToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersCentimeters() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_CENTIMETER,
                DistanceConverter.meterToCentimeter(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_CENTIMETER,
                DistanceConverter.centimeterToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersKilometers() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_KILOMETER,
                DistanceConverter.meterToKilometer(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_KILOMETER,
                DistanceConverter.kilometerToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersInches() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_INCH,
                DistanceConverter.meterToInch(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_INCH,
                DistanceConverter.inchToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersFeet() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_FOOT,
                DistanceConverter.meterToFoot(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_FOOT,
                DistanceConverter.footToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersYards() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_YARD,
                DistanceConverter.meterToYard(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_YARD,
                DistanceConverter.yardToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testMetersMiles() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / METERS_PER_MILE,
                DistanceConverter.meterToMile(inputValue),
                ERROR);
        assertEquals(inputValue * METERS_PER_MILE,
                DistanceConverter.mileToMeter(inputValue),
                ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.millimeterToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.millimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.centimeterToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.centimeterToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.CENTIMETER, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.METER, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.kilometerToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.METER),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.kilometerToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.KILOMETER, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.inchToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.inchToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.INCH, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.footToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.INCH),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.footToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.FOOT, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.yardToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.FOOT),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.YARD),
                ERROR);
        assertEquals(DistanceConverter.meterToMile(
                        DistanceConverter.yardToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.YARD, DistanceUnit.MILE),
                ERROR);

        assertEquals(DistanceConverter.meterToMillimeter(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.MILLIMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToCentimeter(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.CENTIMETER),
                ERROR);
        assertEquals(DistanceConverter.mileToMeter(inputValue),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.METER),
                ERROR);
        assertEquals(DistanceConverter.meterToKilometer(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.KILOMETER),
                ERROR);
        assertEquals(DistanceConverter.meterToInch(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.INCH),
                ERROR);
        assertEquals(DistanceConverter.meterToFoot(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.FOOT),
                ERROR);
        assertEquals(DistanceConverter.meterToYard(
                        DistanceConverter.mileToMeter(inputValue)),
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.YARD),
                ERROR);
        assertEquals(inputValue,
                DistanceConverter.convert(inputValue,
                        DistanceUnit.MILE, DistanceUnit.MILE),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(DistanceConverter.convert(inputValue,
                        DistanceUnit.MILLIMETER, DistanceUnit.MILLIMETER).doubleValue(),
                inputValue.doubleValue(), ERROR);
    }

    @Test
    public void testConvertDistance() {
        final double value = new Random().nextDouble();
        final Distance inputDistance = new Distance(value, DistanceUnit.METER);

        final Distance outputDistance = new Distance();
        DistanceConverter.convert(inputDistance, DistanceUnit.KILOMETER,
                outputDistance);

        // check
        assertEquals(value, inputDistance.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.METER, inputDistance.getUnit());

        assertEquals(DistanceUnit.KILOMETER, outputDistance.getUnit());
        assertEquals(DistanceConverter.convert(value, inputDistance.getUnit(),
                        outputDistance.getUnit()),
                outputDistance.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertAndUpdateDistance() {
        final double value = new Random().nextDouble();
        final Distance distance = new Distance(value, DistanceUnit.METER);

        DistanceConverter.convert(distance, DistanceUnit.KILOMETER);

        // check
        assertEquals(DistanceUnit.KILOMETER, distance.getUnit());
        assertEquals(DistanceConverter.convert(value,
                        DistanceUnit.METER, DistanceUnit.KILOMETER),
                distance.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertAndReturnNewDistance() {
        final double value = new Random().nextDouble();
        final Distance inputDistance = new Distance(value, DistanceUnit.METER);

        final Distance outputDistance = DistanceConverter.convertAndReturnNew(
                inputDistance, DistanceUnit.KILOMETER);

        // check
        assertEquals(value, inputDistance.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.METER, inputDistance.getUnit());

        assertEquals(DistanceUnit.KILOMETER, outputDistance.getUnit());
        assertEquals(DistanceConverter.convert(value, inputDistance.getUnit(),
                        outputDistance.getUnit()),
                outputDistance.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertToOutputDistanceUnit() {
        final double value = new Random().nextDouble();
        final Distance inputDistance = new Distance(value, DistanceUnit.METER);

        final Distance outputDistance = new Distance();
        outputDistance.setUnit(DistanceUnit.KILOMETER);
        DistanceConverter.convert(inputDistance, outputDistance);

        // check
        assertEquals(value, inputDistance.getValue().doubleValue(), 0.0);
        assertEquals(DistanceUnit.METER, inputDistance.getUnit());

        assertEquals(DistanceUnit.KILOMETER, outputDistance.getUnit());
        assertEquals(DistanceConverter.convert(value, inputDistance.getUnit(),
                        outputDistance.getUnit()),
                outputDistance.getValue().doubleValue(),
                0.0);
    }
}
