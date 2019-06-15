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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Handles converting one of {@link BigDecimal}, {@link BigInteger}, {@link Double} or {@link Long} to {@link Double}
 * without any loss.
 */
final class NumberDoubleConverter extends NumberConverter<Double> {

    final static NumberDoubleConverter INSTANCE = new NumberDoubleConverter();

    private NumberDoubleConverter() {
        super();
    }

    @Override
    Double bigDecimal(final BigDecimal value) {
        final double doubleValue = value.doubleValue();
        final BigDecimal back = new BigDecimal(doubleValue);
        if (0 != value.compareTo(back)) {
            this.failConversion(value);
        }
        return doubleValue;
    }

    @Override
    Double bigInteger(final BigInteger value) {
        return this.bigDecimal(new BigDecimal(value));
    }

    @Override
    Double doubleValue(final Double value) {
        return value;
    }

    @Override
    Double longValue(final Long value) {
        final double doubleValue = value;
        if (value != (long) doubleValue) {
            this.failConversion(value);
        }
        return doubleValue;
    }

    @Override
    Class<Double> targetType() {
        return Double.class;
    }

    @Override
    String toStringPrefix() {
        return "";
    }

    @Override
    String toStringSuffix() {
        return "";
    }
}
