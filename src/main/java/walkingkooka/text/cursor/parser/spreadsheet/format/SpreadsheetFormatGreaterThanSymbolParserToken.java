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
package walkingkooka.text.cursor.parser.spreadsheet.format;

import walkingkooka.text.cursor.parser.ParserTokenNodeName;

/**
 * Represents a greater than symbol token.
 */
public final class SpreadsheetFormatGreaterThanSymbolParserToken extends SpreadsheetFormatSymbolParserToken {

    public final static ParserTokenNodeName NAME = ParserTokenNodeName.fromClass(SpreadsheetFormatGreaterThanSymbolParserToken.class);

    static SpreadsheetFormatGreaterThanSymbolParserToken with(final String value, final String text) {
        checkValue(value);

        return new SpreadsheetFormatGreaterThanSymbolParserToken(value, text);
    }

    private SpreadsheetFormatGreaterThanSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public SpreadsheetFormatGreaterThanSymbolParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    SpreadsheetFormatGreaterThanSymbolParserToken replaceText(final String text) {
        return new SpreadsheetFormatGreaterThanSymbolParserToken(this.value, text);
    }

    @Override
    public boolean isCloseBracketSymbol() {
        return false;
    }

    @Override
    public boolean isColorLiteralSymbol() {
        return false;
    }

    @Override
    public boolean isEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isExponentSymbol() {
        return false;
    }

    @Override
    public boolean isFractionSymbol() {
        return false;
    }

    @Override
    public boolean isGeneralSymbol() {
        return false;
    }

    @Override
    public boolean isGreaterThanSymbol() {
        return true;
    }

    @Override
    public boolean isGreaterThanEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isLessThanSymbol() {
        return false;
    }

    @Override
    public boolean isLessThanEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isNotEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isOpenBracketSymbol() {
        return false;
    }

    @Override
    public boolean isPercentSymbol() {
        return false;
    }

    @Override
    public boolean isSeparatorSymbol() {
        return false;
    }

    @Override
    public boolean isWhitespace() {
        return false;
    }

    @Override
    public void accept(final SpreadsheetFormatParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetFormatGreaterThanSymbolParserToken;
    }

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }
}
