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
package walkingkooka.text.cursor.parser.spreadsheet;

/**
 * Represents a plus symbol token.
 */
public final class SpreadsheetPercentSymbolParserToken extends SpreadsheetNonBinaryOperandSymbolParserToken {

    static SpreadsheetPercentSymbolParserToken with(final String value, final String text) {
        checkValueAndText(value, text);

        return new SpreadsheetPercentSymbolParserToken(value, text);
    }

    private SpreadsheetPercentSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public boolean isFunctionParameterSeparatorSymbol() {
        return false;
    }

    // isXXX............................................................................................................

    @Override
    public boolean isParenthesisCloseSymbol() {
        return false;
    }

    @Override
    public boolean isParenthesisOpenSymbol() {
        return false;
    }

    @Override
    public boolean isPercentSymbol() {
        return true;
    }

    @Override
    public boolean isWhitespace() {
        return false;
    }

    // SpreadsheetParserTokenVisitor....................................................................................

    @Override
    public void accept(final SpreadsheetParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object...........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetPercentSymbolParserToken;
    }
}
