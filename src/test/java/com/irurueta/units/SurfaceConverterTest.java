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

public class SurfaceConverterTest {

    private static final double SQUARE_METERS_PER_SQUARE_MILLIMETER =
            DistanceConverter.METERS_PER_MILLIMETER * DistanceConverter.METERS_PER_MILLIMETER;
    private static final double SQUARE_METERS_PER_SQUARE_CENTIMETER =
            DistanceConverter.METERS_PER_CENTIMETER * DistanceConverter.METERS_PER_CENTIMETER;
    private static final double SQUARE_METERS_PER_SQUARE_KILOMETER =
            DistanceConverter.METERS_PER_KILOMETER * DistanceConverter.METERS_PER_KILOMETER;
    private static final double SQUARE_METERS_PER_SQUARE_INCH =
            DistanceConverter.METERS_PER_INCH * DistanceConverter.METERS_PER_INCH;
    private static final double SQUARE_METERS_PER_SQUARE_FOOT =
            DistanceConverter.METERS_PER_FOOT * DistanceConverter.METERS_PER_FOOT;
    private static final double SQUARE_METERS_PER_SQUARE_YARD =
            DistanceConverter.METERS_PER_YARD * DistanceConverter.METERS_PER_YARD;
    private static final double SQUARE_METERS_PER_SQUARE_MILE =
            DistanceConverter.METERS_PER_MILE * DistanceConverter.METERS_PER_MILE;
    private static final double SQUARE_METERS_PER_CENTIARE = 1.0;
    private static final double SQUARE_METERS_PER_ARE = 100.0;
    private static final double SQUARE_METERS_PER_DECARE = 1000.0;
    private static final double SQUARE_METERS_PER_HECTARE = 10000.0;
    private static final double SQUARE_METERS_PER_ACRE = 4046.8564224;

    private static final double ERROR = 1e-6;

    @Test
    public void testSquareMetersSquareMillimeters() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_MILLIMETER,
                SurfaceConverter.squareMeterToSquareMillimeter(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_MILLIMETER,
                SurfaceConverter.squareMillimeterToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareCentimeters() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_CENTIMETER,
                SurfaceConverter.squareMeterToSquareCentimeter(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_CENTIMETER,
                SurfaceConverter.squareCentimeterToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareKilometers() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_KILOMETER,
                SurfaceConverter.squareMeterToSquareKilometer(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_KILOMETER,
                SurfaceConverter.squareKilometerToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareInches() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_INCH,
                SurfaceConverter.squareMeterToSquareInch(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_INCH,
                SurfaceConverter.squareInchToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareFeet() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_FOOT,
                SurfaceConverter.squareMeterToSquareFoot(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_FOOT,
                SurfaceConverter.squareFootToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareYards() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_YARD,
                SurfaceConverter.squareMeterToSquareYard(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_YARD,
                SurfaceConverter.squareYardToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersSquareMiles() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_SQUARE_MILE,
                SurfaceConverter.squareMeterToSquareMile(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_SQUARE_MILE,
                SurfaceConverter.squareMileToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersCentiares() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_CENTIARE,
                SurfaceConverter.squareMeterToCentiare(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_CENTIARE,
                SurfaceConverter.centiareToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersAres() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_ARE,
                SurfaceConverter.squareMeterToAre(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_ARE,
                SurfaceConverter.areToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersDecares() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_DECARE,
                SurfaceConverter.squareMeterToDecare(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_DECARE,
                SurfaceConverter.decareToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersHectares() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_HECTARE,
                SurfaceConverter.squareMeterToHectare(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_HECTARE,
                SurfaceConverter.hectareToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testSquareMetersAcres() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue / SQUARE_METERS_PER_ACRE,
                SurfaceConverter.squareMeterToAcre(inputValue),
                ERROR);
        assertEquals(inputValue * SQUARE_METERS_PER_ACRE,
                SurfaceConverter.acreToSquareMeter(inputValue),
                ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMillimeterToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareMillimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareCentimeterToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareCentimeterToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_CENTIMETER, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareKilometerToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareKilometerToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_KILOMETER, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareInchToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareInchToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_INCH, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareFootToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareFootToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_FOOT, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareYardToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareYardToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_YARD, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMileToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMileToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.squareMileToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.squareMeterToCentiare(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.centiareToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.squareMeterToCentiare(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.centiareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.CENTIARE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.areToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.areToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ARE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.decareToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.decareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.DECARE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.hectareToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAcre(
                        SurfaceConverter.hectareToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.HECTARE, SurfaceUnit.ACRE),
                ERROR);

        assertEquals(SurfaceConverter.squareMeterToSquareMillimeter(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_MILLIMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareCentimeter(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_CENTIMETER),
                ERROR);
        assertEquals(SurfaceConverter.acreToSquareMeter(inputValue),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_METER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareKilometer(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_KILOMETER),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareInch(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_INCH),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareFoot(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_FOOT),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareYard(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_YARD),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToSquareMile(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.SQUARE_MILE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToCentiare(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.CENTIARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToAre(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.ARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToDecare(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.DECARE),
                ERROR);
        assertEquals(SurfaceConverter.squareMeterToHectare(
                        SurfaceConverter.acreToSquareMeter(inputValue)),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.HECTARE),
                ERROR);
        assertEquals(inputValue,
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.ACRE, SurfaceUnit.ACRE),
                ERROR);
    }

    @Test
    public void testConvertNumber() {
        final BigDecimal inputValue = BigDecimal.valueOf(new Random().nextDouble());

        assertEquals(inputValue.doubleValue(),
                SurfaceConverter.convert(inputValue,
                        SurfaceUnit.SQUARE_MILLIMETER,
                        SurfaceUnit.SQUARE_MILLIMETER).doubleValue(),
                ERROR);

    }

    @Test
    public void testConvertSurface() {
        final double value = new Random().nextDouble();
        final Surface inputSurface = new Surface(value, SurfaceUnit.SQUARE_METER);

        final Surface outputSurface = new Surface();
        SurfaceConverter.convert(inputSurface, SurfaceUnit.SQUARE_KILOMETER,
                outputSurface);

        // check
        assertEquals(value, inputSurface.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_METER, inputSurface.getUnit());

        assertEquals(SurfaceUnit.SQUARE_KILOMETER, outputSurface.getUnit());
        assertEquals(SurfaceConverter.convert(value, inputSurface.getUnit(),
                outputSurface.getUnit()), outputSurface.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertAndUpdateSurface() {
        final double value = new Random().nextDouble();
        final Surface surface = new Surface(value, SurfaceUnit.SQUARE_METER);

        SurfaceConverter.convert(surface, SurfaceUnit.SQUARE_KILOMETER);

        // check
        assertEquals(SurfaceUnit.SQUARE_KILOMETER, surface.getUnit());
        assertEquals(SurfaceConverter.convert(value,
                        SurfaceUnit.SQUARE_METER, SurfaceUnit.SQUARE_KILOMETER),
                surface.getValue().doubleValue(),
                0.0);
    }

    @Test
    public void testConvertAndReturnNewSurface() {
        final double value = new Random().nextDouble();
        final Surface inputSurface = new Surface(value, SurfaceUnit.SQUARE_METER);

        final Surface outputSurface = SurfaceConverter.convertAndReturnNew(
                inputSurface, SurfaceUnit.SQUARE_KILOMETER);

        // check
        assertEquals(value, inputSurface.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_METER, inputSurface.getUnit());

        assertEquals(SurfaceUnit.SQUARE_KILOMETER, outputSurface.getUnit());
        assertEquals(SurfaceConverter.convert(value, inputSurface.getUnit(),
                outputSurface.getUnit()), outputSurface.getValue().doubleValue(), 0.0);
    }

    @Test
    public void testConvertToOutputSurfaceUnit() {
        final double value = new Random().nextDouble();
        final Surface inputSurface = new Surface(value, SurfaceUnit.SQUARE_METER);

        final Surface outputSurface = new Surface();
        outputSurface.setUnit(SurfaceUnit.SQUARE_KILOMETER);
        SurfaceConverter.convert(inputSurface, outputSurface);

        // check
        assertEquals(value, inputSurface.getValue().doubleValue(), 0.0);
        assertEquals(SurfaceUnit.SQUARE_METER, inputSurface.getUnit());

        assertEquals(SurfaceUnit.SQUARE_KILOMETER, outputSurface.getUnit());
        assertEquals(SurfaceConverter.convert(value, inputSurface.getUnit(),
                outputSurface.getUnit()), outputSurface.getValue().doubleValue(), 0.0);
    }
}
