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

import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class UnitLocaleTest {

    @Test
    public void testGetDefault() {
        final Locale defaultLocale = Locale.getDefault();
        boolean isImperial = "US".equals(defaultLocale.getCountry()) ||
                "LR".equals(defaultLocale.getCountry()) ||
                "MM".equals(defaultLocale.getCountry());

        assertEquals(UnitLocale.getDefault() == UnitSystem.IMPERIAL,
                isImperial);
    }

    @Test
    public void testGetFrom() {
        Locale l = new Locale("en", UnitLocale.USA);
        assertEquals(UnitLocale.getFrom(l), UnitSystem.IMPERIAL);

        l = new Locale("en", UnitLocale.LIBERIA);
        assertEquals(UnitLocale.getFrom(l), UnitSystem.IMPERIAL);

        l = new Locale("en", UnitLocale.BURMA);
        assertEquals(UnitLocale.getFrom(l), UnitSystem.IMPERIAL);

        l = new Locale("en", "GB");
        assertEquals(UnitLocale.getFrom(l), UnitSystem.METRIC);

        l = new Locale("es", "ES");
        assertEquals(UnitLocale.getFrom(l), UnitSystem.METRIC);
    }
}
