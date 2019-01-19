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

package walkingkooka.net.header;

import walkingkooka.Cast;
import walkingkooka.predicate.character.CharPredicate;

/**
 * A {@link HeaderValueConverter} that handles string values.
 */
abstract class StringHeaderValueConverter extends HeaderValueConverter<String> {

    /**
     * Package private to limit sub classing.
     */
    StringHeaderValueConverter(final CharPredicate predicate) {
        super();
        this.predicate = predicate;
    }

    final CharPredicate predicate;

    @Override final HttpHeaderName<String> httpHeaderNameCast(final HttpHeaderName<?> headerName) {
        return Cast.to(headerName);
    }

    @Override
    public final String toString() {
        return this.predicate.toString();
    }
}
