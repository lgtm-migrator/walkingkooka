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

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Handles converting one of {@link BigDecimal}, {@link BigInteger}, {@link Double} or {@link Long} to {@link Integer}
 * without any loss.
 */
final class NumberIntegerConverter extends NumberConverter<Integer> {

    final static NumberIntegerConverter INSTANCE = new NumberIntegerConverter();

    private NumberIntegerConverter() {
        super();
    }

    @Override
    Integer bigDecimal(final BigDecimal value) {
        return value.intValueExact();
    }

    @Override
    Integer bigInteger(final BigInteger value) {
        return value.intValueExact();
    }

    @Override
    Integer doubleValue(final Double value) {
        final int intValue = value.intValue();
        if (intValue != value) {
            this.failConversion(value);
        }
        return intValue;
    }

    @Override
    Integer longValue(final Long value) {
        final int intValue = value.intValue();
        if (intValue != value) {
            this.failConversion(value);
        }
        return intValue;
    }

    @Override
    Class<Integer> targetType() {
        return Integer.class;
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