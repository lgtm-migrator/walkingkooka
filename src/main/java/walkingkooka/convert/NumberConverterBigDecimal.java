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
 * A {@link Converter} that handles converting any {@link Number} to a {@link BigDecimal}.
 */
final class NumberConverterBigDecimal extends NumberConverter<BigDecimal> {

    final static NumberConverterBigDecimal INSTANCE = new NumberConverterBigDecimal();

    private NumberConverterBigDecimal() {
        super();
    }

    @Override
    BigDecimal bigDecimal(final BigDecimal value) {
        return value;
    }

    @Override
    BigDecimal bigInteger(final BigInteger value) {
        return new BigDecimal(value);
    }

    @Override
    BigDecimal doubleValue(final Double value) {
        return BigDecimal.valueOf(value);
    }

    @Override
    BigDecimal longValue(final Long value) {
        return BigDecimal.valueOf(value);
    }

    @Override
    Class<BigDecimal> targetType() {
        return BigDecimal.class;
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