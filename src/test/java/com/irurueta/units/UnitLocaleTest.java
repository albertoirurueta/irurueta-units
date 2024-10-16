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

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnitLocaleTest {

    @Test
    void testGetDefault() {
        final var defaultLocale = Locale.getDefault();
        var isImperial = "US".equals(defaultLocale.getCountry()) ||
                "LR".equals(defaultLocale.getCountry()) ||
                "MM".equals(defaultLocale.getCountry());

        assertEquals(isImperial, UnitLocale.getDefault() == UnitSystem.IMPERIAL);
    }

    @Test
    void testGetFrom() {
        var l = new Locale("en", UnitLocale.USA);
        assertEquals(UnitSystem.IMPERIAL, UnitLocale.getFrom(l));

        l = new Locale("en", UnitLocale.LIBERIA);
        assertEquals(UnitSystem.IMPERIAL, UnitLocale.getFrom(l));

        l = new Locale("en", UnitLocale.BURMA);
        assertEquals(UnitSystem.IMPERIAL, UnitLocale.getFrom(l));

        l = new Locale("en", "GB");
        assertEquals(UnitSystem.METRIC, UnitLocale.getFrom(l));

        l = new Locale("es", "ES");
        assertEquals(UnitSystem.METRIC, UnitLocale.getFrom(l));
    }
}
