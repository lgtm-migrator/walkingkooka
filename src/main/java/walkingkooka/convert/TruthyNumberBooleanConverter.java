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
 * A {@link Converter} for various {@link Number} sub classes that returns false for zeros, and true for all other values,
 * following the truthy convention.
 */
final class TruthyNumberBooleanConverter extends NumberConverter<Boolean>{

    /**
     * Singleton
     */
    final static TruthyNumberBooleanConverter INSTANCE = new TruthyNumberBooleanConverter();

    /**
     * Private ctor use singleton
     */
    private TruthyNumberBooleanConverter() {
        super();
    }

    @Override
    Class<Boolean> onlySupportedType() {
        return Boolean.class;
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
    Boolean doubleValue(final double value) {
        return 0 != value;
    }

    @Override
    Boolean longValue(final long value) {
        return 0 != value;
    }

    @Override
    String toStringPrefix() {
        return "Truthy ";
    }
}
