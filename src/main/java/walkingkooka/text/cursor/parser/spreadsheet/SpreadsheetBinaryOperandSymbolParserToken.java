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
 * Base class for all binary operand symbols.
 */
abstract class SpreadsheetBinaryOperandSymbolParserToken extends SpreadsheetSymbolParserToken {

    SpreadsheetBinaryOperandSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    final void checkText(final String text) {
        checkTextNullOrWhitespace(text);
    }

    @Override
    public final boolean isCloseParenthesisSymbol() {
        return false;
    }

    @Override
    public final boolean isFunctionParameterSeparatorSymbol() {
        return false;
    }

    @Override
    public final boolean isOpenParenthesisSymbol() {
        return false;
    }

    @Override
    public final boolean isPercentSymbol() {
        return false;
    }

    @Override
    public final boolean isWhitespace() {
        return false;
    }
}