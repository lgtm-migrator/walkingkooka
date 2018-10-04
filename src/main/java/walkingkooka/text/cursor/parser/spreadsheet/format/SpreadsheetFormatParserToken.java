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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenVisitor;
import walkingkooka.tree.visit.Visiting;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a token within the spreadsheet format grammar.
 */
public abstract class SpreadsheetFormatParserToken implements ParserToken {

    /**
     * An empty list of {@link ParserToken tokens/values}.
     */
    public final static List<ParserToken> EMPTY = Lists.empty();

    /**
     * {@see SpreadsheetFormatAmPmParserToken}
     */
    public static SpreadsheetFormatAmPmParserToken amPm(final String value, final String text) {
        return SpreadsheetFormatAmPmParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatBigDecimalParserToken}
     */
    public static SpreadsheetFormatBigDecimalParserToken bigDecimal(final BigDecimal value, final String text) {
        return SpreadsheetFormatBigDecimalParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatCloseBracketSymbolParserToken}
     */
    public static SpreadsheetFormatCloseBracketSymbolParserToken closeBracketSymbol(final String value, final String text) {
        return SpreadsheetFormatCloseBracketSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatColorParserToken}
     */
    public static SpreadsheetFormatColorParserToken color(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatColorParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatColorLiteralSymbolParserToken}
     */
    public static SpreadsheetFormatColorLiteralSymbolParserToken colorLiteralSymbol(final String value, final String text) {
        return SpreadsheetFormatColorLiteralSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatColorNameParserToken}
     */
    public static SpreadsheetFormatColorNameParserToken colorName(final String value, final String text) {
        return SpreadsheetFormatColorNameParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatColorNumberParserToken}
     */
    public static SpreadsheetFormatColorNumberParserToken colorNumber(final Integer value, final String text) {
        return SpreadsheetFormatColorNumberParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatCurrencyParserToken}
     */
    public static SpreadsheetFormatCurrencyParserToken currency(final String value, final String text) {
        return SpreadsheetFormatCurrencyParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDateParserToken}
     */
    public static SpreadsheetFormatDateParserToken date(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatDateParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDateTimeParserToken}
     */
    public static SpreadsheetFormatDateTimeParserToken dateTime(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatDateTimeParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDayParserToken}
     */
    public static SpreadsheetFormatDayParserToken day(final String value, final String text) {
        return SpreadsheetFormatDayParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDecimalPointParserToken}
     */
    public static SpreadsheetFormatDecimalPointParserToken decimalPoint(final String value, final String text) {
        return SpreadsheetFormatDecimalPointParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDigitParserToken}
     */
    public static SpreadsheetFormatDigitParserToken digit(final String value, final String text) {
        return SpreadsheetFormatDigitParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDigitLeadingSpaceParserToken}
     */
    public static SpreadsheetFormatDigitLeadingSpaceParserToken digitLeadingSpace(final String value, final String text) {
        return SpreadsheetFormatDigitLeadingSpaceParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatDigitLeadingZeroParserToken}
     */
    public static SpreadsheetFormatDigitLeadingZeroParserToken digitLeadingZero(final String value, final String text) {
        return SpreadsheetFormatDigitLeadingZeroParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatEqualsParserToken}
     */
    public static SpreadsheetFormatEqualsParserToken equalsParserToken(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatEqualsParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatEqualsSymbolParserToken}
     */
    public static SpreadsheetFormatEqualsSymbolParserToken equalsSymbol(final String value, final String text) {
        return SpreadsheetFormatEqualsSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatEscapeParserToken}
     */
    public static SpreadsheetFormatEscapeParserToken escape(final Character value, final String text) {
        return SpreadsheetFormatEscapeParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatExponentParserToken}
     */
    public static SpreadsheetFormatExponentParserToken exponent(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatExponentParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatExponentSymbolParserToken}
     */
    public static SpreadsheetFormatExponentSymbolParserToken exponentSymbol(final String value, final String text) {
        return SpreadsheetFormatExponentSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatExpressionParserToken}
     */
    public static SpreadsheetFormatExpressionParserToken expression(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatExpressionParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatFractionParserToken}
     */
    public static SpreadsheetFormatFractionParserToken fraction(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatFractionParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatFractionSymbolParserToken}
     */
    public static SpreadsheetFormatFractionSymbolParserToken fractionSymbol(final String value, final String text) {
        return SpreadsheetFormatFractionSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGeneralParserToken}
     */
    public static SpreadsheetFormatGeneralParserToken general(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatGeneralParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGeneralSymbolParserToken}
     */
    public static SpreadsheetFormatGeneralSymbolParserToken generalSymbol(final String value, final String text) {
        return SpreadsheetFormatGeneralSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGreaterThanParserToken}
     */
    public static SpreadsheetFormatGreaterThanParserToken greaterThan(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatGreaterThanParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGreaterThanSymbolParserToken}
     */
    public static SpreadsheetFormatGreaterThanSymbolParserToken greaterThanSymbol(final String value, final String text) {
        return SpreadsheetFormatGreaterThanSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGreaterThanEqualsParserToken}
     */
    public static SpreadsheetFormatGreaterThanEqualsParserToken greaterThanEquals(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatGreaterThanEqualsParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatGreaterThanEqualsParserToken}
     */
    public static SpreadsheetFormatGreaterThanEqualsSymbolParserToken greaterThanEqualsSymbol(final String value, final String text) {
        return SpreadsheetFormatGreaterThanEqualsSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatHourParserToken}
     */
    public static SpreadsheetFormatHourParserToken hour(final String value, final String text) {
        return SpreadsheetFormatHourParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatLessThanParserToken}
     */
    public static SpreadsheetFormatLessThanParserToken lessThan(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatLessThanParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatLessThanSymbolParserToken}
     */
    public static SpreadsheetFormatLessThanSymbolParserToken lessThanSymbol(final String value, final String text) {
        return SpreadsheetFormatLessThanSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatLessThanEqualsParserToken}
     */
    public static SpreadsheetFormatLessThanEqualsParserToken lessThanEquals(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatLessThanEqualsParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatLessThanEqualsParserToken}
     */
    public static SpreadsheetFormatLessThanEqualsSymbolParserToken lessThanEqualsSymbol(final String value, final String text) {
        return SpreadsheetFormatLessThanEqualsSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatMonthOrMinuteParserToken}
     */
    public static SpreadsheetFormatMonthOrMinuteParserToken monthOrMinute(final String value, final String text) {
        return SpreadsheetFormatMonthOrMinuteParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatNotEqualsParserToken}
     */
    public static SpreadsheetFormatNotEqualsParserToken notEquals(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatNotEqualsParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatNotEqualsSymbolParserToken}
     */
    public static SpreadsheetFormatNotEqualsSymbolParserToken notEqualsSymbol(final String value, final String text) {
        return SpreadsheetFormatNotEqualsSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatNumberParserToken}
     */
    public static SpreadsheetFormatNumberParserToken number(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatNumberParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatOpenBracketSymbolParserToken}
     */
    public static SpreadsheetFormatOpenBracketSymbolParserToken openBracketSymbol(final String value, final String text) {
        return SpreadsheetFormatOpenBracketSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatPercentSymbolParserToken}
     */
    public static SpreadsheetFormatPercentSymbolParserToken percentSymbol(final String value, final String text) {
        return SpreadsheetFormatPercentSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatQuotedTextParserToken}
     */
    public static SpreadsheetFormatQuotedTextParserToken quotedText(final String value, final String text) {
        return SpreadsheetFormatQuotedTextParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatSecondParserToken}
     */
    public static SpreadsheetFormatSecondParserToken second(final String value, final String text) {
        return SpreadsheetFormatSecondParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatSeparatorSymbolParserToken}
     */
    public static SpreadsheetFormatSeparatorSymbolParserToken separatorSymbol(final String value, final String text) {
        return SpreadsheetFormatSeparatorSymbolParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatStarParserToken}
     */
    public static SpreadsheetFormatStarParserToken star(final Character value, final String text) {
        return SpreadsheetFormatStarParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatTextParserToken}
     */
    public static SpreadsheetFormatTextParserToken text(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatTextParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatTextLiteralParserToken}
     */
    public static SpreadsheetFormatTextLiteralParserToken textLiteral(final String value, final String text) {
        return SpreadsheetFormatTextLiteralParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatTextPlaceholderParserToken}
     */
    public static SpreadsheetFormatTextPlaceholderParserToken textPlaceholder(final String value, final String text) {
        return SpreadsheetFormatTextPlaceholderParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatThousandsParserToken}
     */
    public static SpreadsheetFormatThousandsParserToken thousands(final String value, final String text) {
        return SpreadsheetFormatThousandsParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatTimeParserToken}
     */
    public static SpreadsheetFormatTimeParserToken time(final List<ParserToken> value, final String text) {
        return SpreadsheetFormatTimeParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatUnderscoreParserToken}
     */
    public static SpreadsheetFormatUnderscoreParserToken underscore(final Character value, final String text) {
        return SpreadsheetFormatUnderscoreParserToken.with(value, text);
    }

    /**
     * {@see SpreadsheetFormatWhitespaceParserToken}
     */
    public static SpreadsheetFormatWhitespaceParserToken whitespace(final String value, final String whitespace) {
        return SpreadsheetFormatWhitespaceParserToken.with(value, whitespace);
    }

    /**
     * {@see SpreadsheetFormatYearParserToken}
     */
    public static SpreadsheetFormatYearParserToken year(final String value, final String text) {
        return SpreadsheetFormatYearParserToken.with(value, text);
    }

    // factory helpers..................................................................................................

    static List<ParserToken> copyAndCheckTokens(final List<ParserToken> tokens) {
        Objects.requireNonNull(tokens, "tokens");

        final List<ParserToken> copy = Lists.array();
        copy.addAll(tokens);
        return copy;
    }

    static List<ParserToken> copyAndCheckTokensFailIfEmpty(final List<ParserToken> tokens) {
        final List<ParserToken> copy = copyAndCheckTokens(tokens);

        if (copy.isEmpty()) {
            throw new IllegalArgumentException("Tokens is empty");
        }
        return copy;
    }

    /**
     * Package private ctor to limit sub classing.
     */
    SpreadsheetFormatParserToken(final String text) {
        this.checkText(text);
        this.text = text;
    }

    abstract void checkText(final String text);

    static void checkTextNull(final String text) {
        Objects.requireNonNull(text, "text");
    }

    static void checkTextNullOrEmpty(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");
    }

    @Override
    public final String text() {
        return this.text;
    }

    private final String text;

    final SpreadsheetFormatParserToken setText0(final String text) {
        Objects.requireNonNull(text, "text");
        return this.text.equals(text) ?
                this :
                replaceText(text);
    }

    abstract SpreadsheetFormatParserToken replaceText(final String text);

    /**
     * Returns a copy without any symbols or whitespace tokens. The original text form will still contain
     * those tokens as text, but the tokens themselves will be removed.
     */
    abstract public Optional<SpreadsheetFormatParserToken> withoutSymbols();

    // isXXX ...........................................................................................................

    /**
     * Only {@link SpreadsheetFormatAmPmParserToken} return true
     */
    public abstract boolean isAmPm();

    /**
     * Only {@link SpreadsheetFormatBigDecimalParserToken} return true
     */
    public abstract boolean isBigDecimal();

    /**
     * Only {@link SpreadsheetFormatCloseBracketSymbolParserToken} return true
     */
    public abstract boolean isCloseBracketSymbol();

    /**
     * Only {@link SpreadsheetFormatColorParserToken} return true
     */
    public abstract boolean isColor();

    /**
     * Only {@link SpreadsheetFormatColorLiteralSymbolParserToken} return true
     */
    public abstract boolean isColorLiteralSymbol();

    /**
     * Only {@link SpreadsheetFormatColorNameParserToken} return true
     */
    public abstract boolean isColorName();

    /**
     * Only {@link SpreadsheetFormatColorNumberParserToken} return true
     */
    public abstract boolean isColorNumber();

    /**
     * Only {@link SpreadsheetFormatCurrencyParserToken} return true
     */
    public abstract boolean isCurrency();

    /**
     * Only {@link SpreadsheetFormatDateParserToken} return true
     */
    public abstract boolean isDate();

    /**
     * Only {@link SpreadsheetFormatDateTimeParserToken} return true
     */
    public abstract boolean isDateTime();

    /**
     * Only {@link SpreadsheetFormatDayParserToken} return true
     */
    public abstract boolean isDay();

    /**
     * Only {@link SpreadsheetFormatDecimalPointParserToken} return true
     */
    public abstract boolean isDecimalPoint();

    /**
     * Only {@link SpreadsheetFormatDigitParserToken} return true
     */
    public abstract boolean isDigit();

    /**
     * Only {@link SpreadsheetFormatDigitLeadingSpaceParserToken} return true
     */
    public abstract boolean isDigitLeadingSpace();

    /**
     * Only {@link SpreadsheetFormatDigitLeadingZeroParserToken} return true
     */
    public abstract boolean isDigitLeadingZero();

    /**
     * Only {@link SpreadsheetFormatEqualsParserToken} return true
     */
    public abstract boolean isEquals();

    /**
     * Only {@link SpreadsheetFormatEqualsSymbolParserToken} return true
     */
    public abstract boolean isEqualsSymbol();

    /**
     * Only {@link SpreadsheetFormatEscapeParserToken} return true
     */
    public abstract boolean isEscape();

    /**
     * Only {@link SpreadsheetFormatExponentParserToken} return true
     */
    public abstract boolean isExponent();

    /**
     * Only {@link SpreadsheetFormatExponentSymbolParserToken} return true
     */
    public abstract boolean isExponentSymbol();

    /**
     * Only {@link SpreadsheetFormatExpressionParserToken} return true
     */
    public abstract boolean isExpression();

    /**
     * Only {@link SpreadsheetFormatFractionParserToken} return true
     */
    public abstract boolean isFraction();

    /**
     * Only {@link SpreadsheetFormatFractionSymbolParserToken} return true
     */
    public abstract boolean isFractionSymbol();

    /**
     * Only {@link SpreadsheetFormatGeneralParserToken} return true
     */
    public abstract boolean isGeneral();

    /**
     * Only {@link SpreadsheetFormatGeneralSymbolParserToken} return true
     */
    public abstract boolean isGeneralSymbol();

    /**
     * Only {@link SpreadsheetFormatGreaterThanParserToken} return true
     */
    public abstract boolean isGreaterThan();

    /**
     * Only {@link SpreadsheetFormatGreaterThanSymbolParserToken} return true
     */
    public abstract boolean isGreaterThanSymbol();

    /**
     * Only {@link SpreadsheetFormatGreaterThanEqualsParserToken} return true
     */
    public abstract boolean isGreaterThanEquals();

    /**
     * Only {@link SpreadsheetFormatGreaterThanEqualsSymbolParserToken} return true
     */
    public abstract boolean isGreaterThanEqualsSymbol();

    /**
     * Only {@link SpreadsheetFormatHourParserToken} return true
     */
    public abstract boolean isHour();

    /**
     * Only {@link SpreadsheetFormatLessThanParserToken} return true
     */
    public abstract boolean isLessThan();

    /**
     * Only {@link SpreadsheetFormatLessThanSymbolParserToken} return true
     */
    public abstract boolean isLessThanSymbol();

    /**
     * Only {@link SpreadsheetFormatLessThanEqualsParserToken} return true
     */
    public abstract boolean isLessThanEquals();

    /**
     * Only {@link SpreadsheetFormatLessThanEqualsSymbolParserToken} return true
     */
    public abstract boolean isLessThanEqualsSymbol();

    /**
     * Only {@link SpreadsheetFormatMonthOrMinuteParserToken} return true
     */
    public abstract boolean isMonthOrMinute();

    /**
     * Only {@link SpreadsheetFormatNotEqualsParserToken} return true
     */
    public abstract boolean isNotEquals();

    /**
     * Only {@link SpreadsheetFormatNotEqualsSymbolParserToken} return true
     */
    public abstract boolean isNotEqualsSymbol();

    /**
     * Only {@link SpreadsheetFormatNumberParserToken} return true
     */
    public abstract boolean isNumber();

    /**
     * Only {@link SpreadsheetFormatOpenBracketSymbolParserToken} return true
     */
    public abstract boolean isOpenBracketSymbol();

    /**
     * Only {@link SpreadsheetFormatPercentSymbolParserToken} return true
     */
    public abstract boolean isPercentSymbol();

    /**
     * Only {@link SpreadsheetFormatQuotedTextParserToken} return true
     */
    public abstract boolean isQuotedText();

    /**
     * Only {@link SpreadsheetFormatSecondParserToken} return true
     */
    public abstract boolean isSecond();

    /**
     * Only {@link SpreadsheetFormatSeparatorSymbolParserToken} return true
     */
    public abstract boolean isSeparatorSymbol();

    /**
     * Only {@link SpreadsheetFormatStarParserToken} return true
     */
    public abstract boolean isStar();

    /**
     * Only sub classes of {@link SpreadsheetFormatSymbolParserToken} return true
     */
    public abstract boolean isSymbol();

    /**
     * Only {@link SpreadsheetFormatTextParserToken} return true
     */
    public abstract boolean isText();

    /**
     * Only {@link SpreadsheetFormatTextLiteralParserToken} return true
     */
    public abstract boolean isTextLiteral();

    /**
     * Only {@link SpreadsheetFormatTextPlaceholderParserToken} return true
     */
    public abstract boolean isTextPlaceholder();

    /**
     * Only {@link SpreadsheetFormatThousandsParserToken} return true
     */
    public abstract boolean isThousands();

    /**
     * Only {@link SpreadsheetFormatTimeParserToken} return true
     */
    public abstract boolean isTime();

    /**
     * Only {@link SpreadsheetFormatUnderscoreParserToken} return true
     */
    public abstract boolean isUnderscore();

    /**
     * Only {@link SpreadsheetFormatWhitespaceParserToken} return true
     */
    public abstract boolean isWhitespace();

    /**
     * Only {@link SpreadsheetFormatYearParserToken} return true
     */
    public abstract boolean isYear();

    // Visitor ...........................................................................................................

    public final void accept(final ParserTokenVisitor visitor) {
        final SpreadsheetFormatParserTokenVisitor formatVisitor = Cast.to(visitor);
        final SpreadsheetFormatParserToken token = this;

        if (Visiting.CONTINUE == formatVisitor.startVisit(token)) {
            this.accept(SpreadsheetFormatParserTokenVisitor.class.cast(visitor));
        }
        formatVisitor.endVisit(token);
    }

    abstract public void accept(final SpreadsheetFormatParserTokenVisitor visitor);

    // Object ...........................................................................................................

    @Override
    public final int hashCode() {
        return this.text().hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
                this.canBeEqual(other) &&
                        this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    private boolean equals0(final SpreadsheetFormatParserToken other) {
        return this.text().equals(other.text()) &&
                this.equals1(other);
    }

    abstract boolean equals1(SpreadsheetFormatParserToken other);

    @Override
    public final String toString() {
        return this.text();
    }
}