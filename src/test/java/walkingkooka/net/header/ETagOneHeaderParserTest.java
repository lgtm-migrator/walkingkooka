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

public final class ETagOneHeaderParserTest extends ETagHeaderParserTestCase<ETagOneHeaderParser> {

    @Test
    public void testValueSeparatorFails() {
        this.parseInvalidCharacterFails(",");
    }

    @Test
    public final void testSeparatorFails() {
        this.parseInvalidCharacterFails("\"ABC\",", ',');
    }

    @Test
    public final void testSeparatorWhitespaceFails() {
        this.parseInvalidCharacterFails("\"ABC\", ", ',');
    }

    @Test
    public final void testWeakSeparatorWhitespaceFails() {
        this.parseInvalidCharacterFails("W/\"ABC\", ", ',');
    }

    @Test
    public void testManyTags() {
        this.parseInvalidCharacterFails("\"A\",\"B\"", ',');
    }

    @Override
    public ETag parse(final String text) {
        return ETagOneHeaderParser.parseOne(text);
    }

    @Override
    public Class<ETagOneHeaderParser> type() {
        return ETagOneHeaderParser.class;
    }
}
