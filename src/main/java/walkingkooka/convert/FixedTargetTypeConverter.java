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

import walkingkooka.Cast;

/**
 * A base converter that only accepts one target.
 */
abstract class FixedTargetTypeConverter<T> extends ConverterTemplate {

    /**
     * Package private to limit sub classing.
     */
    FixedTargetTypeConverter(){
        super();
    }

    final <TT> TT convert0(final Object value, final Class<TT> type) {
        return Cast.to(this.targetType()==value.getClass() ?
               value :
               this.convert1(value, Cast.to(type)));
    }

    abstract T convert1(final Object value, Class<T> type);

    final T failConversion(final Object value) {
        return this.failConversion(value, this.targetType());
    }

    final T failConversion(final Object value, final Throwable cause) {
        return this.failConversion(value, this.targetType(), cause);
    }

    abstract Class<T> targetType();

    /**
     * Returns the {@link String} as a signed offset including a plus or minus when the value is non zero.
     */
    final static String toStringOffset(final long offset) {
        return 0 == offset ?
               "" :
               toStringOffset0(offset);
    }

    private static String toStringOffset0(final long offset) {
        final StringBuilder b = new StringBuilder();
        b.append('(');

        if(offset > 0) {
            b.append('+');
        }
        b.append(offset);
        b.append(')');

        return b.toString();
    }
}