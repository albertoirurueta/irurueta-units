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

import java.math.BigDecimal;
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
    public void testConvertVolume() {
        final double inputValue = new Random().nextDouble();
        final Volume inputVolume = new Volume(inputValue, VolumeUnit.CUBIC_CENTIMETER);
        final Volume outputVolume = new Volume(0.0, VolumeUnit.MILLILITER);

        VolumeConverter.convert(inputVolume, outputVolume);

        // check
        assertEquals(inputValue, inputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_CENTIMETER, inputVolume.getUnit());

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER),
                outputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.MILLILITER, outputVolume.getUnit());
    }

    @Test
    public void testConvertAndReturnVolume() {
        final double inputValue = new Random().nextDouble();
        final Volume inputVolume = new Volume(inputValue, VolumeUnit.CUBIC_CENTIMETER);

        final Volume outputVolume = VolumeConverter.convertAndReturnNew(
                inputVolume, VolumeUnit.MILLILITER);

        // check
        assertEquals(inputValue, inputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_CENTIMETER, inputVolume.getUnit());

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER),
                outputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.MILLILITER, outputVolume.getUnit());
    }

    @Test
    public void testConvertAndUpdateVolume() {
        final double inputValue = new Random().nextDouble();
        final Volume volume = new Volume(inputValue, VolumeUnit.CUBIC_CENTIMETER);

        VolumeConverter.convert(volume, VolumeUnit.MILLILITER);

        // check
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER),
                volume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.MILLILITER, volume.getUnit());
    }

    @Test
    public void testConvertAndReturnVolume2() {
        final double inputValue = new Random().nextDouble();
        final Volume inputVolume = new Volume(inputValue, VolumeUnit.CUBIC_CENTIMETER);
        final Volume outputVolume = new Volume(0.0, VolumeUnit.HECTOLITER);

        VolumeConverter.convert(inputVolume, VolumeUnit.MILLILITER, outputVolume);

        // check
        assertEquals(inputValue, inputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.CUBIC_CENTIMETER, inputVolume.getUnit());

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER),
                outputVolume.getValue().doubleValue(), 0.0);
        assertEquals(VolumeUnit.MILLILITER, outputVolume.getUnit());
    }

    @Test
    public void testConvertNumber() {
        final double value = new Random().nextDouble();
        final BigDecimal inputValue = new BigDecimal(value);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_CENTIMETER)
                .doubleValue(), value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.cubicCentimeterToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_CENTIMETER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicCentimeterToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.MILLILITER).doubleValue(),
                value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.milliliterToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.MILLILITER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.milliliterToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_CENTIMETER)
                        .doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_DECIMETER)
                .doubleValue(), value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.cubicDecimeterToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_DECIMETER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicDecimeterToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.LITER).doubleValue(), value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.literToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.literToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.LITER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.literToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.HECTOLITER).doubleValue(),
                value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.hectoliterToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.HECTOLITER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.hectoliterToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_METER).doubleValue(),
                value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_METER, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(value), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.cubicInchToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_INCH).doubleValue(),
                value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_INCH, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicInchToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.pintToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.PINT).doubleValue(), value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.PINT, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.pintToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.gallonToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.GALLON).doubleValue(), value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.GALLON, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.gallonToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.cubicFootToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.CUBIC_FOOT).doubleValue(),
                value, ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.CUBIC_FOOT, VolumeUnit.BARREL).doubleValue(),
                VolumeConverter.cubicMeterToBarrel(
                        VolumeConverter.cubicFootToCubicMeter(value)), ERROR);

        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_CENTIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicCentimeter(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.MILLILITER).doubleValue(),
                VolumeConverter.cubicMeterToMilliliter(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_DECIMETER).doubleValue(),
                VolumeConverter.cubicMeterToCubicDecimeter(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.LITER).doubleValue(),
                VolumeConverter.cubicMeterToLiter(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.HECTOLITER).doubleValue(),
                VolumeConverter.cubicMeterToHectoliter(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_METER).doubleValue(),
                VolumeConverter.barrelToCubicMeter(value), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_INCH).doubleValue(),
                VolumeConverter.cubicMeterToCubicInch(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.PINT).doubleValue(),
                VolumeConverter.cubicMeterToPint(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.GALLON).doubleValue(),
                VolumeConverter.cubicMeterToGallon(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.CUBIC_FOOT).doubleValue(),
                VolumeConverter.cubicMeterToCubicFoot(
                        VolumeConverter.barrelToCubicMeter(value)), ERROR);
        assertEquals(VolumeConverter.convert(inputValue,
                VolumeUnit.BARREL, VolumeUnit.BARREL).doubleValue(),
                value, ERROR);
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
