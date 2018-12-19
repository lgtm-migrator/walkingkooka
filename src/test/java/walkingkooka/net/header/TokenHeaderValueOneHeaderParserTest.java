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

import org.junit.Test;

public final class TokenHeaderValueOneHeaderParserTest extends TokenHeaderValueHeaderParserTestCase<TokenHeaderValueOneHeaderParser,
        TokenHeaderValue> {

    @Test
    public void testValueSeparatorFails() {
        this.parseFails("A,");
    }

    @Test
    public void testValueSpaceSeparatorFails() {
        this.parseFails("A ,");
    }

    @Test
    public void testValueTabSeparatorFails() {
        this.parseFails("A\t,");
    }

    @Test
    public void testValueParameterSeparatorFails() {
        this.parseFails("A;b=c,");
    }

    @Override
    TokenHeaderValueOneHeaderParser createHeaderParser(final String text) {
        return new TokenHeaderValueOneHeaderParser(text);
    }

    @Override
    TokenHeaderValue parse(final String text) {
        return TokenHeaderValueOneHeaderParser.parseTokenHeaderValue(text);
    }

    @Override
    void parseAndCheck2(final String headerValue, final TokenHeaderValue token) {
        this.parseAndCheck3(headerValue, token);
    }

    @Override
    protected Class<TokenHeaderValueOneHeaderParser> type() {
        return TokenHeaderValueOneHeaderParser.class;
    }
}