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
 * A {@link HeaderValueConverter} that handles parsing a quoted boundary string into its raw form.<br>
 * <pre>
 * Content-type: multipart/mixed; boundary="abcdefGHIJK"
 * </pre>
 */
final class MediaTypeBoundaryHeaderValueConverter extends HeaderValueConverter2<MediaTypeBoundary> {

    /**
     * Singleton
     */
    final static MediaTypeBoundaryHeaderValueConverter INSTANCE = new MediaTypeBoundaryHeaderValueConverter();

    /**
     * Private ctor use singleton.
     */
    private MediaTypeBoundaryHeaderValueConverter() {
        super();
    }

    @Override
    MediaTypeBoundary parse0(final String text, final Name name) {
        final HeaderValueConverter<String> converter = text.charAt(0) == DOUBLEQUOTE ?
                QUOTED :
                UNQUOTED;
        return MediaTypeBoundary.with0(converter.parse(text, name), text);
    }

    private final static char DOUBLEQUOTE = '"';
    private final static HeaderValueConverter<String> UNQUOTED = HeaderValueConverters.string(MediaTypeBoundary.UNQUOTED_CHARACTER_PREDICATE);
    private final static HeaderValueConverter<String> QUOTED = HeaderValueConverters.string(MediaTypeBoundary.QUOTED_CHARACTER_PREDICATE,
            StringHeaderValueConverterFeature.DOUBLE_QUOTES);

    @Override
    void check0(final Object value) {
        this.checkType(value, MediaTypeBoundary.class);
    }

    /**
     * Delegates to the {@link MediaTypeBoundary#toHeaderText()}.
     */
    @Override
    String toText0(final MediaTypeBoundary boundary, final Name name) {
        return boundary.toHeaderText();
    }

    @Override
    public String toString() {
        return MediaTypeBoundary.class.getSimpleName();
    }
}