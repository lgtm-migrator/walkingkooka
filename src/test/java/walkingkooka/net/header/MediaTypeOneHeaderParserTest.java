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

import org.junit.jupiter.api.Test;

import java.util.Map;

public final class MediaTypeOneHeaderParserTest extends MediaTypeHeaderParserTestCase<MediaTypeOneHeaderParser,
        MediaType> {
    @Test
    public void testParameterSeparatorFails() {
        this.parseMissingValueFails(";", 0);
    }

    @Test
    public void testTypeSlashSubTypeValueSeparatorFails() {
        this.parseInvalidCharacterFails("type/subtype,");
    }

    @Test
    public void testTypeSlashSubTypeParameterSeparatorParameterNameKeyValueSeparatorParameterValueValueSeparatorFails() {
        this.parseInvalidCharacterFails("type/subtype;p=v,");
    }

    @Test
    public void testCommentMediaType() {
        this.parseAndCheck("(comment-123)text/plain-text",
                "text",
                "plain-text",
                MediaType.NO_PARAMETERS);
    }

    @Test
    public void testMediaTypeComment() {
        this.parseAndCheck("text/plain-text(comment-123)",
                "text",
                "plain-text",
                MediaType.NO_PARAMETERS);
    }

    @Override
    final void parseAndCheck(final String text,
                             final String type,
                             final String subtype,
                             final Map<MediaTypeParameterName<?>, Object> parameters) {
        this.check(MediaTypeOneHeaderParser.parseMediaType(text), type, subtype, parameters);
    }

    @Override
    public MediaType parse(final String text) {
        return MediaTypeOneHeaderParser.parseMediaType(text);
    }

    @Override
    public Class<MediaTypeOneHeaderParser> type() {
        return MediaTypeOneHeaderParser.class;
    }
}
