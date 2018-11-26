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

package walkingkooka.net.http;

import walkingkooka.naming.Name;
import walkingkooka.text.CharSequences;

/**
 * Base class for all {@link walkingkooka.net.http.HttpHeaderValueConverter} that only supports reading and not writing
 * header values
 */
abstract class HttpHeaderValueConverter2<T> extends HttpHeaderValueConverter<T> {

    /**
     * Package private to limit sub classing.
     */
    HttpHeaderValueConverter2() {
        super();
    }

    @Override
    String format0(final T value, final Name name) {
        throw new UnsupportedOperationException(name + "=" + CharSequences.quoteIfChars(value));
    }
}
