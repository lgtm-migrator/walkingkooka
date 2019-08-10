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

import java.math.BigInteger;
import java.time.LocalTime;

public final class LocalTimeConverterBigIntegerTest extends LocalTimeConverterTestCase<LocalTimeConverterBigInteger, BigInteger> {

    private final static int VALUE = 123;

    @Test
    public void testLocalTime() {
        this.convertAndCheck(LocalTime.ofSecondOfDay(VALUE), BigInteger.valueOf(VALUE));
    }

    @Test
    public void testConverterRoundTripWithNanos() {
        final LocalTime localTime = LocalTime.ofSecondOfDay(VALUE);
        final BigInteger bigInteger = this.convertAndCheck(localTime, BigInteger.valueOf(VALUE));
        this.convertAndCheck(Converters.numberLocalTime(), bigInteger, LocalTime.class, localTime);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createConverter(), "LocalTime->BigInteger");
    }

    @Override
    public LocalTimeConverterBigInteger createConverter() {
        return LocalTimeConverterBigInteger.INSTANCE;
    }

    @Override
    protected Class<BigInteger> onlySupportedType() {
        return BigInteger.class;
    }

    @Override
    public Class<LocalTimeConverterBigInteger> type() {
        return LocalTimeConverterBigInteger.class;
    }
}