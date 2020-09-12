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

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class VolumeConverterTest {

    private static final double CUBIC_METER_PER_CUBIC_CENTIMETER = 1e-6;
    private static final double CUBIC_METER_PER_MILLILITER = 1e-6;
    private static final double CUBIC_METER_PER_CUBIC_DECIMETER = 1e-3;
    private static final double CUBIC_METER_PER_LITER = 1e-3;
    private static final double CUBIC_METER_PER_HECTOLITER = 1e-1;
    private static final double CUBIC_METER_PER_CUBIC_INCH = 0.000016387064;
    private static final double CUBIC_METER_PER_PINT = 0.000473176473;
    private static final double CUBIC_METER_PER_GALLON = 0.003785411784;
    private static final double CUBIC_METER_PER_CUBIC_FOOT = 0.028316846592;
    private static final double CUBIC_METER_PER_BARREL = 0.158987294928;

    private static final double ERROR = 1e-6;

    @Test
    public void testCubicMeterCubicCentimeter() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToCubicCentimeter(inputValue),
                inputValue / CUBIC_METER_PER_CUBIC_CENTIMETER, ERROR);
        assertEquals(VolumeConverter.cubicCentimeterToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_CUBIC_CENTIMETER, ERROR);
    }

    @Test
    public void testCubicMeterMilliliter() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToMilliliter(inputValue),
                inputValue / CUBIC_METER_PER_MILLILITER, ERROR);
        assertEquals(VolumeConverter.milliliterToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_MILLILITER, ERROR);
    }

    @Test
    public void testCubicMeterCubicDecimeter() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToCubicDecimeter(inputValue),
                inputValue / CUBIC_METER_PER_CUBIC_DECIMETER, ERROR);
        assertEquals(VolumeConverter.cubicDecimeterToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_CUBIC_DECIMETER, ERROR);
    }

    @Test
    public void testCubicMeterLiter() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToLiter(inputValue),
                inputValue / CUBIC_METER_PER_LITER, ERROR);
        assertEquals(VolumeConverter.literToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_LITER, ERROR);
    }

    @Test
    public void testCubicMeterHectoliter() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToHectoliter(inputValue),
                inputValue / CUBIC_METER_PER_HECTOLITER, ERROR);
        assertEquals(VolumeConverter.hectoliterToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_HECTOLITER, ERROR);
    }

    @Test
    public void testCubicMeterCubicInch() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToCubicInch(inputValue),
                inputValue / CUBIC_METER_PER_CUBIC_INCH, ERROR);
        assertEquals(VolumeConverter.cubicInchToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_CUBIC_INCH, ERROR);
    }

    @Test
    public void testCubicMeterPint() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToPint(inputValue),
                inputValue / CUBIC_METER_PER_PINT, ERROR);
        assertEquals(VolumeConverter.pintToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_PINT, ERROR);
    }

    @Test
    public void testCubicMeterGallon() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToGallon(inputValue),
                inputValue / CUBIC_METER_PER_GALLON, ERROR);
        assertEquals(VolumeConverter.gallonToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_GALLON, ERROR);
    }

    @Test
    public void testCubicMeterCubicFoot() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToCubicFoot(inputValue),
                inputValue / CUBIC_METER_PER_CUBIC_FOOT, ERROR);
        assertEquals(VolumeConverter.cubicFootToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_CUBIC_FOOT, ERROR);
    }

    @Test
    public void testCubicMeterBarrel() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.cubicMeterToBarrel(inputValue),
                inputValue / CUBIC_METER_PER_BARREL, ERROR);
        assertEquals(VolumeConverter.barrelToCubicMeter(inputValue),
                inputValue * CUBIC_METER_PER_BARREL, ERROR);
    }

    @Test
    public void testConvertDouble() {
        final double inputValue = new Random().nextDouble();

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_CENTIMETER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_METER),
                VolumeConverter.cubicCentimeterToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicCentimeterToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.MILLILITER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_METER),
                VolumeConverter.milliliterToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.milliliterToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_DECIMETER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_METER),
                VolumeConverter.cubicDecimeterToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicDecimeterToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.LITER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_METER),
                VolumeConverter.literToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.literToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.HECTOLITER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_METER),
                VolumeConverter.hectoliterToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.hectoliterToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_METER), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(inputValue), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_METER),
                VolumeConverter.cubicInchToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_INCH), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicInchToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_METER),
                VolumeConverter.pintToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.PINT), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.pintToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_METER),
                VolumeConverter.gallonToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.GALLON), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.gallonToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_METER),
                VolumeConverter.cubicFootToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_FOOT), inputValue, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.BARREL),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicFootToCubicMeter(inputValue)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_CENTIMETER),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.MILLILITER),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_DECIMETER),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.LITER),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.HECTOLITER),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_METER),
                VolumeConverter.barrelToCubicMeter(inputValue), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_INCH),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.PINT),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.GALLON),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_FOOT),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.barrelToCubicMeter(inputValue)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.BARREL), inputValue, ERROR);
    }
}
