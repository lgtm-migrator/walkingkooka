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

package walkingkooka.text.spreadsheetformat;

import org.junit.Test;
import walkingkooka.math.Fraction;
import walkingkooka.text.CharSequences;
import walkingkooka.text.cursor.parser.BigDecimalParserToken;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserReporterException;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatFractionParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserContext;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParsers;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.function.Function;

import static org.junit.Assert.fail;

/**
 * In expectations all symbols are doubled, as a means to verify the context is supplying the values.
 */
public final class BigDecimalFractionSpreadsheetTextFormatterTest extends SpreadsheetTextFormatterTemplateTestCase<BigDecimalFractionSpreadsheetTextFormatter,
        BigDecimal,
        SpreadsheetFormatFractionParserToken> {

    //creation ..............................................................................................

    @Test(expected = NullPointerException.class)
    public void testWithNullTokenFails() {
        BigDecimalFractionSpreadsheetTextFormatter.with(null, mathContext(), fractioner());
    }

    @Test(expected = NullPointerException.class)
    public void testWithMathContextFails() {
        BigDecimalFractionSpreadsheetTextFormatter.with(this.token(), null, fractioner());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullFractionerFails() {
        BigDecimalFractionSpreadsheetTextFormatter.with(this.token(), mathContext(), null);
    }

    private SpreadsheetFormatFractionParserToken token() {
        return this.parsePatternOrFail("#/#");
    }

    // text-literal, escaped etc........................................................................................

    @Test
    public void testCloseParensFails() {
        this.parsePatternFails(")");
    }

    @Test
    public void testColonFails() {
        this.parsePatternFails(":");
    }

    @Test
    public void testDecimalFails() {
        this.parsePatternFails(".");
    }

    @Test
    public void testMinusFails() {
        this.parsePatternFails("-");
    }

    @Test
    public void testPlusFails() {
        this.parsePatternFails("+");
    }

    @Test
    public void testOpenParensFails() {
        this.parsePatternFails("(");
    }

    @Test
    public void testEscapedFails() {
        this.parsePatternFails("\\A");
    }

    @Test
    public void testTextLiteralFails() {
        this.parsePatternFails("\"Quoted text\"");
    }

    @Test
    public void testHashFails() {
        this.parsePatternFails("#");
    }

    @Test
    public void testQuestionFails() {
        this.parsePatternFails("?");
    }

    @Test
    public void testZeroFails() {
        this.parsePatternFails("0");
    }

    @Test
    public void testHashDecimalFails() {
        this.parsePatternFails("#.");
    }

    @Test
    public void testQuestionDecimalFails() {
        this.parsePatternFails("?.");
    }

    @Test
    public void testZeroDecimalFails() {
        this.parsePatternFails("0.");
    }

    @Test
    public void testHashSlashFails() {
        this.parsePatternFails("#/");
    }

    @Test
    public void testQuestionSlashFails() {
        this.parsePatternFails("?/");
    }

    @Test
    public void testZeroSlashFails() {
        this.parsePatternFails("0/");
    }

    private void parsePatternFails(final String pattern) {
        try {
            this.parsePatternOrFail(pattern);
            fail("Expected " + ParserReporterException.class.getSimpleName() + " to be thrown with pattern " + CharSequences.quote(pattern));
        } catch (final ParserReporterException expected) {
        }
    }

    // fraction space. space dot space ...................................................................................

    @Test
    public void testHashFractionHash0_0() {
        this.parseFormatAndCheck("#/#", 0.0, "/1");
    }

    @Test
    public void testHashFractionHash0_2() {
        this.parseFormatAndCheck("#/#", 0.2, "1/5");
    }

    @Test
    public void testHashFractionHash0_6() {
        this.parseFormatAndCheck("#/#", 0.6, "3/5");
    }

    @Test
    public void testHashFractionHash0_71() {
        this.parseFormatAndCheck("#/#", 0.61, "3/5");
    }

    @Test
    public void testHashFractionHash0_85() {
        this.parseFormatAndCheck("#/#", 0.85, "4/5");
    }

    @Test
    public void testHashFractionHash1_0() {
        this.parseFormatAndCheck("#/#", 1, "5/5");
    }

    @Test
    public void testHashFractionHash1_99() {
        this.parseFormatAndCheck("#/#", 1.99, "10/5");
    }

    @Test
    public void testHashFractionHash0_025() {
        this.parseFormatAndCheck("#/#", 0.025, "/1");
    }

    // fraction space. space dot space ...................................................................................

    @Test
    public void testQuestionFractionQuestion0_0() {
        this.parseFormatAndCheck("?/?", 0.0, " /1");
    }

    @Test
    public void testQuestionFractionQuestion0_2() {
        this.parseFormatAndCheck("?/?", 0.2, "1/5");
    }

    @Test
    public void testQuestionFractionQuestion0_6() {
        this.parseFormatAndCheck("?/?", 0.6, "3/5");
    }

    @Test
    public void testQuestionFractionQuestion0_85() {
        this.parseFormatAndCheck("?/?", 0.85, "4/5");
    }

    @Test
    public void testQuestionFractionQuestion1_0() {
        this.parseFormatAndCheck("?/?", 1, "5/5");
    }

    @Test
    public void testQuestionFractionQuestion1_99() {
        this.parseFormatAndCheck("?/?", 1.99, "10/5");
    }

    @Test
    public void testQuestionFractionQuestion0_025() {
        this.parseFormatAndCheck("?/?", 0.025, " /1");
    }

    // fraction zero. zero dot zero ...................................................................................

    @Test
    public void testZeroFractionZero0_0() {
        this.parseFormatAndCheck("0/0", 0.0, "0/1");
    }

    @Test
    public void testZeroFractionZero0_2() {
        this.parseFormatAndCheck("0/0", 0.2, "1/5");
    }

    @Test
    public void testZeroFractionZero0_6() {
        this.parseFormatAndCheck("0/0", 0.6, "3/5");
    }

    @Test
    public void testZeroFractionZero0_71() {
        this.parseFormatAndCheck("0/0", 0.71, "3/5");
    }

    @Test
    public void testZeroFractionZero0_85() {
        this.parseFormatAndCheck("0/0", 0.85, "4/5");
    }

    @Test
    public void testZeroFractionZero1_0() {
        this.parseFormatAndCheck("0/0", 1, "5/5");
    }

    @Test
    public void testZeroFractionZero1_25() {
        this.parseFormatAndCheck("0/0", 1.25, "6/5");
    }

    @Test
    public void testZeroFractionZero1_975() {
        this.parseFormatAndCheck("0/0", 1.975, "10/5");
    }

    @Test
    public void testZeroFractionZero0_025() {
        this.parseFormatAndCheck("0/0", 0.025, "0/1");
    }

    // long fraction patterns

    @Test
    public void testHashFractionHashHashHash0_0000005() {
        this.parseFormatAndCheck("#/###", 0.0000005, "/1");
    }

    @Test
    public void testQuestionFractionQuestionQuestionQuestion0_0000005() {
        this.parseFormatAndCheck("?/???", 0.0000005, " /  1");
    }

    @Test
    public void testZeroFractionZeroZeroZero0_0000005() {
        this.parseFormatAndCheck("0/000", 0.0000005, "0/001");
    }

    // hash space zero.................................................................................

    @Test
    public void testHashFractionSpaceHash1_05() {
        this.parseFormatAndCheck("?/?#", 1.05, "52/50");
    }

    @Test
    public void testHashFractionZeroHash1_05() {
        this.parseFormatAndCheck("?/?0", 1.05, "52/50");
    }

    @Test
    public void testHashFractionSpaceZeroHash1_00005() {
        this.parseFormatAndCheck("?/?", 1.005, "5/5");
    }

    // currency .........................................................................................

    @Test
    public void testCurrency() {
        this.parseFormatAndCheck("$0/0", 0.4, "D2/5");
    }

    // percentage .........................................................................................

    @Test
    public void testPercentage0() {
        this.parseFormatAndCheck("0/0%", 0, "0/1P");
    }

    @Test
    public void testPercentagePositive1() {
        this.parseFormatAndCheck("0/0%", 1, "500/5P");
    }

    @Test
    public void testPercentageNegative1() {
        this.parseFormatAndCheck("0/0%", -1, "M500/5P");
    }

    // thousands divider .........................................................................................

    @Test
    public void testThousandsDividerDigitComma0() {
        this.parseFormatAndCheck("0/0,", 1, "0/1");
    }

    @Test
    public void testThousandsDividerCommaSlash12345() {
        this.parseFormatAndCheck("0,/#", 12345, "61/5");
    }

    @Test
    public void testThousandsDividerCommaTextLiteralSLash12345() {
        this.parseFormatAndCheck("0,\"Text\"/#", 12345, "61Text/5");
    }

    @Test
    public void testThousandsDividerCommaCommaSlash123456789() {
        this.parseFormatAndCheck("0,,/#", 123456789, "617/5");
    }

    //helpers .......................................................................................................

    private void parseFormatAndCheck(final String pattern,
                                     final double value,
                                     final String text) {
        this.parseFormatAndCheck(pattern, String.valueOf(value), text);
    }

    private void parseFormatAndCheck(final String pattern,
                                     final String value,
                                     final String text) {
        this.parseFormatAndCheck0(pattern, value, SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, text));
    }

    private void parseFormatAndCheck0(final String pattern,
                                      final String value,
                                      final SpreadsheetFormattedText text) {
        this.formatAndCheck(this.createFormatter(pattern), new BigDecimal(value), text);
    }

    @Override
    String pattern() {
        return "#/#";
    }

    @Override
    Parser<SpreadsheetFormatParserToken, SpreadsheetFormatParserContext> parser(final Parser<BigDecimalParserToken, SpreadsheetFormatParserContext> bigDecimal) {
        return SpreadsheetFormatParsers.fraction(bigDecimal);
    }

    @Override
    BigDecimalFractionSpreadsheetTextFormatter createFormatter0(final SpreadsheetFormatFractionParserToken token) {
        return BigDecimalFractionSpreadsheetTextFormatter.with(token, this.mathContext(), this.fractioner());
    }

    private MathContext mathContext() {
        return MathContext.UNLIMITED;
    }

    private Function<BigDecimal, Fraction> fractioner() {
        return this::makeIntoFraction;
    }

    private Fraction makeIntoFraction(final BigDecimal value) {
        if (value.signum() == 0) {
            return Fraction.with(BigInteger.ZERO, BigInteger.ONE);
        }

        final int scale = value.scale();
        final BigDecimal two = BigDecimal.valueOf(2);

        return Fraction.with(
                value.scaleByPowerOfTen(scale).divide(two).toBigInteger(),
                BigDecimal.ONE.scaleByPowerOfTen(scale).divide(two).toBigInteger());
    }

    @Override
    protected BigDecimal value() {
        return new BigDecimal(123);
    }

    @Override
    protected SpreadsheetTextFormatContext createContext() {
        return new FakeSpreadsheetTextFormatContext() {
            @Override
            public char currencySymbol() {
                return 'D';
            }

            @Override
            public char minusSign() {
                return 'M';
            }

            @Override
            public char percentageSymbol() {
                return 'P';
            }
        };
    }

    @Override
    protected Class<BigDecimalFractionSpreadsheetTextFormatter> type() {
        return BigDecimalFractionSpreadsheetTextFormatter.class;
    }
}