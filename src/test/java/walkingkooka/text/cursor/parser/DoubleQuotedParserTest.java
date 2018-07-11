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
 */
package walkingkooka.text.cursor.parser;

import org.junit.Test;
import walkingkooka.Cast;

public final class DoubleQuotedParserTest extends QuotedParserTestCase<DoubleQuotedParser<TestParserContext>, DoubleQuotedParserToken> {

    @Test
    public void testToStringDoubleQuoted() {
        assertEquals("double quoted string", this.createParser().toString());
    }

    @Override
    protected DoubleQuotedParser createParser() {
        return DoubleQuotedParser.instance();
    }

    @Override
    char quoteChar() {
        return '"';
    }

    @Override
    char otherQuoteChar(){
        return '\'';
    }

    @Override
    final DoubleQuotedParserToken createToken(final String content, final String text) {
        return DoubleQuotedParserToken.with(content, text);
    }

    @Override
    protected Class<DoubleQuotedParser<TestParserContext>> type() {
        return Cast.to(DoubleQuotedParser.class);
    }
}
