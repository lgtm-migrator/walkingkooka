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

import walkingkooka.naming.Name;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.http.HttpHeaderName;

/**
 * A {@link HeaderValueConverter2} that parses a header value into a {@link RelativeUrl}.
 * This is useful for headers such as {@link HttpHeaderName#CONTENT_LOCATION}.
 */
final class RelativeUrlHeaderValueConverter extends HeaderValueConverter2<RelativeUrl> {

    /**
     * Singleton
     */
    final static RelativeUrlHeaderValueConverter INSTANCE = new RelativeUrlHeaderValueConverter();

    /**
     * Private ctor use singleton.
     */
    private RelativeUrlHeaderValueConverter() {
        super();
    }

    @Override
    RelativeUrl parse0(final String value, final Name name) {
        return RelativeUrl.parse(value);
    }

    @Override
    void check0(final Object value) {
        this.checkType(value, RelativeUrl.class);
    }

    @Override
    String format0(final RelativeUrl value, final Name name) {
        return value.toString();
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public String toString() {
        return toStringType(RelativeUrl.class);
    }
}