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
package walkingkooka.text.spreadsheetformat.parser;


/**
 * Represents a digit that replaces leading zeroes with spaces.
 */
public final class SpreadsheetFormatDigitLeadingSpaceParserToken extends SpreadsheetFormatNonSymbolParserToken<String> {

    static SpreadsheetFormatDigitLeadingSpaceParserToken with(final String value, final String text) {
        checkValueAndText(value, text);

        return new SpreadsheetFormatDigitLeadingSpaceParserToken(value, text);
    }

    private SpreadsheetFormatDigitLeadingSpaceParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public boolean isAmPm() {
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
    public boolean isConditionNumber() {
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
        return true;
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
    public boolean isYear() {
        return false;
    }

    @Override
    public void accept(final SpreadsheetFormatParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetFormatDigitLeadingSpaceParserToken;
    }

}