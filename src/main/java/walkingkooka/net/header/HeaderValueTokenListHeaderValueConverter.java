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

import java.util.List;

/**
 * A {@link HeaderValueConverter2} that expects comma separated {@link HeaderValueToken tokens}.
 */
final class HeaderValueTokenListHeaderValueConverter extends HeaderValueConverter2<List<HeaderValueToken>> {

    /**
     * Singleton
     */
    final static HeaderValueTokenListHeaderValueConverter INSTANCE = new HeaderValueTokenListHeaderValueConverter();

    /**
     * Private ctor use singleton.
     */
    private HeaderValueTokenListHeaderValueConverter() {
        super();
    }

    @Override
    List<HeaderValueToken> parse0(final String value, final Name name) {
        return HeaderValueToken.parse(value);
    }

    @Override
    void check0(final Object value) {
        this.checkListOfType(value, HeaderValueToken.class);
    }

    @Override
    String format0(final List<HeaderValueToken> values, final Name name) {
        return HeaderValueToken.format(values);
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public String toString() {
        return toStringListOf(HeaderValueToken.class);
    }
}