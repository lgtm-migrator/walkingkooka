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

package walkingkooka.text.cursor.parser;

import org.junit.Test;
import walkingkooka.DecimalNumberContext;

import static org.junit.Assert.assertEquals;

public final class DecimalNumberContextParserContextTest extends ParserContextTestCase<DecimalNumberContextParserContext> {

    private final static char DECIMAL = 'D';
    private final static char EXPONENT = 'X';
    private final static char MINUS = 'M';
    private final static char PLUS = 'P';

    @Test(expected = NullPointerException.class)
    public void testWithNullDecimalNumberContextFails() {
        DecimalNumberContextParserContext.with(null);
    }

    @Test
    public void testWith() {
        final DecimalNumberContextParserContext context = this.createContext();
        this.checkDecimalPoint(context, DECIMAL);
        this.checkExponentSymbol(context, EXPONENT);
        this.checkMinusSign(context, MINUS);
        this.checkPlusSign(context, PLUS);
    }

    @Test
    public void testToString() {
        assertEquals(this.basic().toString(), this.createContext().toString());
    }

    @Override
    protected DecimalNumberContextParserContext createContext() {
        return DecimalNumberContextParserContext.with(this.basic());
    }

    private DecimalNumberContext basic() {
        return ParserContexts.basic(DECIMAL, EXPONENT, MINUS, PLUS);
    }

    @Override
    protected Class<DecimalNumberContextParserContext> type() {
        return DecimalNumberContextParserContext.class;
    }
}