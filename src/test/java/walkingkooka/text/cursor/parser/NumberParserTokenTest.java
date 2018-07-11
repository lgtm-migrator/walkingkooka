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

import java.math.BigInteger;

public final class NumberParserTokenTest extends ParserTokenTestCase<NumberParserToken> {

    @Test(expected = NullPointerException.class)
    public void testWithNullValueFails() {
        NumberParserToken.with(null, "123");
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullTextFails() {
        NumberParserToken.with(BigInteger.ZERO, null);
    }
    
    @Test
    public void testIgnoresPrefix() {
        NumberParserToken.with(BigInteger.valueOf(123), "+123");
    }

    @Test
    public void testHex() {
        NumberParserToken.with(BigInteger.valueOf(0x1234), "0x1234");
    }

    @Override
    protected NumberParserToken createToken() {
        return NumberParserToken.with(BigInteger.valueOf(123), "123");
    }

    @Override
    protected NumberParserToken createDifferentToken() {
        return NumberParserToken.with(BigInteger.valueOf(987), "987");
    }

    @Override
    protected Class<NumberParserToken> type() {
        return NumberParserToken.class;
    }
}
