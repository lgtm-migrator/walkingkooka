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
 * Represents a am/pm placeholder.
 */
public final class SpreadsheetFormatAmPmParserToken extends SpreadsheetFormatLeafParserToken2<String> {

    public final static ParserTokenNodeName NAME = ParserTokenNodeName.fromClass(SpreadsheetFormatAmPmParserToken.class);

    static SpreadsheetFormatAmPmParserToken with(final String value, final String text) {
        checkValue(value);

        return new SpreadsheetFormatAmPmParserToken(value, text);
    }

    private SpreadsheetFormatAmPmParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    void checkText(final String text) {
        checkTextNullOrEmpty(text);
    }

    @Override
    public SpreadsheetFormatAmPmParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    SpreadsheetFormatAmPmParserToken replaceText(final String text) {
        return new SpreadsheetFormatAmPmParserToken(this.value, text);
    }

    @Override
    public boolean isAmPm() {
        return true;
    }

    @Override
    public boolean isBigDecimal() {
        return false;
    }

    @Override
    public boolean isColorName() {
        return false;
    }

    @Override
    public boolean isColorNumber() {
        return false;
    }

    @Override
    public boolean isCurrency() {
        return false;
    }

    @Override
    public boolean isDay() {
        return false;
    }

    @Override
    public boolean isDecimalPoint() {
        return false;
    }

    @Override
    public boolean isDigit() {
        return false;
    }

    @Override
    public boolean isDigitLeadingSpace() {
        return false;
    }

    @Override
    public boolean isDigitLeadingZero() {
        return false;
    }

    @Override
    public boolean isEscape() {
        return false;
    }

    @Override
    public boolean isHour() {
        return false;
    }

    @Override
    public boolean isMonthOrMinute() {
        return false;
    }

    @Override
    public boolean isQuotedText() {
        return false;
    }

    @Override
    public boolean isSecond() {
        return false;
    }

    @Override
    public boolean isStar() {
        return false;
    }

    @Override
    public boolean isTextLiteral() {
        return false;
    }

    @Override
    public boolean isTextPlaceholder() {
        return false;
    }

    @Override
    public boolean isThousands() {
        return false;
    }

    @Override
    public boolean isUnderscore() {
        return false;
    }

    @Override
    public boolean isWhitespace() {
        return false;
    }

    @Override
    public boolean isYear() {
        return false;
    }

    @Override
    public void accept(final SpreadsheetFormatParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetFormatAmPmParserToken;
    }

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }
}
