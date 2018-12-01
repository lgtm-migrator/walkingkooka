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

/**
 * A {@link HeaderValueConverter} that parses a content header value into a {@link ContentDispositionFilename}.
 */
final class ContentDispositionFilenameHeaderValueConverter extends HeaderValueConverter2<ContentDispositionFilename> {

    /**
     * Singleton
     */
    final static ContentDispositionFilenameHeaderValueConverter INSTANCE = new ContentDispositionFilenameHeaderValueConverter();

    /**
     * Private ctor use singleton.
     */
    private ContentDispositionFilenameHeaderValueConverter() {
        super();
    }

    @Override
    ContentDispositionFilename parse0(final String value, final Name name) {
        return ContentDispositionFilename.with(value);
    }

    @Override
    void check0(final Object value) {
        this.checkType(value, ContentDispositionFilename.class);
    }

    @Override
    String format0(final ContentDispositionFilename value, final Name name) {
        return value.toString();
    }

    @Override
    public boolean isString() {
        return false;
    }

    @Override
    public String toString() {
        return ContentDispositionFilename.class.getSimpleName();
    }
}