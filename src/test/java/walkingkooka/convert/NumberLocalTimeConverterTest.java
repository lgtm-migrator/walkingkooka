/*
 * Copyright 2018 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.convert;

import org.junit.Ignore;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalTime;

public final class NumberLocalTimeConverterTest extends NumberConverterTestCase<NumberLocalTimeConverter, LocalTime> {

    private final static int VALUE = 123;

    @Test
    public void testNonNumberTypeFails() {
        this.convertFails("fail!");
    }

    @Test
    public void testFromLocalTimeFails() {
        this.convertFails(LocalTime.of(12, 59));
    }

    @Test
    public void testBigDecimal() {
        this.convertAndCheck(BigDecimal.valueOf(VALUE), LocalTime.ofSecondOfDay(VALUE));
    }

    @Test
    public void testBigDecimalWithFraction() {
        this.convertAndCheck(BigDecimal.valueOf(123.5), LocalTime.ofSecondOfDay(VALUE).plusNanos(Converters.NANOS_PER_SECOND/2));
    }

    @Test
    public void testBigInteger() {
        this.convertAndCheck(BigInteger.valueOf(123), LocalTime.ofSecondOfDay(VALUE));
    }

    @Test
    public void testDouble() {
        this.convertAndCheck(Double.valueOf(VALUE), LocalTime.ofSecondOfDay(VALUE));
    }

    @Test
    public void testDoubleWithFraction() {
        this.convertAndCheck(BigDecimal.valueOf(123.5), LocalTime.ofSecondOfDay(VALUE).plusNanos(Converters.NANOS_PER_SECOND/2));
    }

    @Test
    @Ignore
    public void testDoubleMaxFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testLong() {
        this.convertAndCheck(Long.valueOf(VALUE), LocalTime.ofSecondOfDay(VALUE));
    }

    @Override
    protected NumberLocalTimeConverter createConverter() {
        return NumberLocalTimeConverter.INSTANCE;
    }

    @Override
    protected Class<LocalTime> onlySupportedType() {
        return LocalTime.class;
    }

    @Override
    protected Class<NumberLocalTimeConverter> type() {
        return NumberLocalTimeConverter.class;
    }
}