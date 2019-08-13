/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TryConverterTest extends ConverterTestCase2<TryConverter> {

    @Test
    public void testWithZeroFails() {
        assertThrows(NullPointerException.class, () -> {
            TryConverter.with(null);
        });
    }

    @Test
    public void testWithOne() {
        final Converter only = Converters.string();
        assertSame(only, TryConverter.with(Lists.of(only)));
    }

    @Test
    public void testWithIncludesTryConverter() {
        final Converter converter1 = Converters.fake();
        final Converter converter2 = Converters.fake();
        final Converter converter3 = Converters.fake();

        final TryConverter tryConverter = Cast.to(TryConverter.with(Lists.of(TryConverter.with(Lists.of(converter1, converter2)), converter3)));
        assertEquals(Lists.of(converter1, converter2, converter3), tryConverter.converters, "converters");
    }

    @Test
    public void testFirst() {
        this.convertAndCheck(Byte.MAX_VALUE,
                Long.class,
                Long.valueOf(Byte.MAX_VALUE));
    }

    @Test
    public void testLast() {
        this.convertAndCheck(1L,
                Double.class,
                1.0);
    }

    @Test
    public void testUnhandledTargetType() {
        this.convertFails("String conversion not supported", Boolean.class);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createConverter(), Converters.numberLong() + ", " + Converters.numberDouble());
    }

    @Override
    public TryConverter createConverter() {
        return Cast.to(TryConverter.with(Lists.of(Converters.numberLong(), Converters.numberDouble())));
    }

    @Override
    public ConverterContext createContext() {
        return ConverterContexts.fake();
    }

    // ConverterTestCase................................................................................................

    @Override
    public Class<TryConverter> type() {
        return TryConverter.class;
    }
}