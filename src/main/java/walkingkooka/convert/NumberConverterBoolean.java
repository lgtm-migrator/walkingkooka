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
 * A {@link Converter} that handles converting any {@link Number} to a {@link BigDecimal}, following truth conventions,
 * where zero becomes false and all other values are true.
 */
final class NumberConverterBoolean extends NumberConverter<Boolean> {

    /**
     * Singleton
     */
    final static NumberConverterBoolean INSTANCE = new NumberConverterBoolean();

    /**
     * Private ctor use singleton
     */
    private NumberConverterBoolean() {
        super();
    }

    @Override
    public boolean canConvert(final Object value, final Class<?> type, ConverterContext context) {
        return value instanceof Number && Boolean.class == type;
    }

    @Override
    Boolean bigDecimal(final BigDecimal value) {
        return value.signum() != 0;
    }

    @Override
    Boolean bigInteger(final BigInteger value) {
        return value.signum() != 0;
    }

    @Override
    Boolean doubleValue(final Double value) {
        return !value.isInfinite() && !value.isNaN() && 0 != value.doubleValue();
    }

    @Override
    Boolean longValue(final Long value) {
        return 0 != value.longValue();
    }

    @Override
    String toStringPrefix() {
        return "Truthy ";
    }

    String toStringSuffix() {
        return "";
    }

    @Override
    Class<Boolean> targetType() {
        return Boolean.class;
    }
}