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
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;

import static org.junit.Assert.assertEquals;

public class StringParserTest extends ParserTemplateTestCase<StringParser<FakeParserContext>, StringParserToken> {

    private final static String STRING = "abcd";
    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;

    @Test(expected = NullPointerException.class)
    public void testWithNullStringFails() {
        StringParser.with(null, CASE_SENSITIVITY);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyStringFails() {
        StringParser.with("", CASE_SENSITIVITY);
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullCaseSensitivityFails() {
        StringParser.with(STRING, null);
    }

    @Test
    public void testIncomplete() {
        this.parseFailAndCheck("a");
    }

    @Test
    public void testIncomplete2() {
        this.parseFailAndCheck("ab");
    }

    @Test
    public void testIncompleteInsensitive() {
        this.parseFailAndCheck(this.createParserInsensitive(), "a");
    }

    @Test
    public void testIncompleteInsensitive2() {
        this.parseFailAndCheck(this.createParserInsensitive(),"ab");
    }

    @Test
    public void testStringEoc() {
        this.parseAndCheck(STRING, this.token(), STRING, "");
    }

    @Test
    public void testStringEocInsensitive() {
        final String text = "abCD";
        this.parseAndCheck(this.createParserInsensitive(),
                text, this.token(text), text, "");
    }

    @Test
    public void testStringIgnoresRemainder() {
        this.parseAndCheck(STRING + "xyz", this.token(), STRING, "xyz");
    }

    @Test
    public void testStringIgnoresRemainderInsensitive() {
        final String text = "abCD";
        this.parseAndCheck(this.createParserInsensitive(),
                text + "xyz",
                this.token(text),
                text,
                "xyz");
    }

    @Test
    public void testToString() {
        assertEquals(CharSequences.quoteAndEscape(STRING).toString(), this.createParser().toString());
    }

    @Test
    public void testToStringInsensitive() {
        assertEquals(CharSequences.quoteAndEscape(STRING) + " (CaseInsensivite)", this.createParserInsensitive().toString());
    }

    @Override
    protected StringParser createParser() {
        return StringParser.with(STRING, CASE_SENSITIVITY);
    }

    private StringParser createParserInsensitive() {
        return StringParser.with(STRING, CaseSensitivity.INSENSITIVE);
    }

    private StringParserToken token() {
        return this.token(STRING);
    }

    private StringParserToken token(final String text) {
        return StringParserToken.with(text, text);
    }

    @Override
    protected Class<StringParser<FakeParserContext>> type() {
        return Cast.to(StringParser.class);
    }
}
