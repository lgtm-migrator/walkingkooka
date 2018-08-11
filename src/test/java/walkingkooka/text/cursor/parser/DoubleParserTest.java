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
import walkingkooka.Cast;
import walkingkooka.text.cursor.TextCursor;

import static org.junit.Assert.assertEquals;

public final class DoubleParserTest extends ParserTemplateTestCase<DoubleParser<FakeParserContext>, DoubleParserToken> {

    @Test
    public void testFailure() {
        this.parseFailAndCheck("a");
    }

    @Test
    public void testFailure2() {
        this.parseFailAndCheck("abc");
    }

    @Test
    public void testPlusZero() {
        this.parseAndCheck2("+0", +0.0);
    }

    @Test
    public void testPlusZero2() {
        this.parseAndCheck2("+0", +0.0, "~");
    }

    @Test
    public void testMinusZero() {
        this.parseAndCheck2("-0", -0.0);
    }

    @Test
    public void testMinusZero2() {
        this.parseAndCheck2("-0", -0.0, "~");
    }

    @Test
    public void testZero() {
        this.parseAndCheck2("0", 0);
    }

    @Test
    public void testZero2() {
        this.parseAndCheck2("0", 0, "~");
    }

    @Test
    public void testPlusPlusFail() {
        this.parseFailAndCheck("++1");
    }

    @Test
    public void testMinusMinusFail() {
        this.parseFailAndCheck("--1");
    }

    @Test
    public void testPlusMinusFail() {
        this.parseFailAndCheck("+-1");
    }

    @Test
    public void testMinusPlusFail() {
        this.parseFailAndCheck("-+1");
    }

    @Test
    public void testPlusZeroDecimal() {
        this.parseAndCheck2("+0.", +0.);
    }

    @Test
    public void testPlusZeroDecimal2() {
        this.parseAndCheck2("+0.", +0., "~");
    }

    @Test
    public void testMinusZeroDecimal() {
        this.parseAndCheck2("-0.", -0.0);
    }

    @Test
    public void testMinusZeroDecimal2() {
        this.parseAndCheck2("-0.", -0.0, "~");
    }

    @Test
    public void testZeroDecimal() {
        this.parseAndCheck2("0.", 0);
    }

    @Test
    public void testZeroDecimal2() {
        this.parseAndCheck2("0.", 0, "~");
    }

    @Test
    public void testZeroDecimalFraction() {
        this.parseAndCheck2("0.5", 0.5);
    }

    @Test
    public void testZeroDecimalFraction2() {
        this.parseAndCheck2("0.5", 0.5, "~");
    }

    @Test
    public void testZeroDecimalFraction3() {
        this.parseAndCheck2("0.875", 0.875);
    }

    @Test
    public void testZeroDecimalFraction4() {
        this.parseAndCheck2("0.875", 0.875, "~");
    }

    @Test
    public void testMinusZeroDecimalFraction() {
        this.parseAndCheck2("-0.5", -0.5);
    }

    @Test
    public void testMinusZeroDecimalFraction2() {
        this.parseAndCheck2("-0.5", -0.5, "~");
    }

    @Test
    public void testMinusZeroDecimalFraction3() {
        this.parseAndCheck2("-0.875", -0.875);
    }

    @Test
    public void testMinusZeroDecimalFraction4() {
        this.parseAndCheck2("-0.875", -0.875, "~");
    }

    @Test
    public void testZeroDecimalFraction5() {
        this.parseAndCheck2("-0.000000001", -0.000000001);
    }

    @Test
    public void testNumber() {
        this.parseAndCheck2("123", 123);
    }

    @Test
    public void testNumber2() {
        this.parseAndCheck2("123", 123, "~");
    }

    @Test
    public void testNumberDecimal() {
        this.parseAndCheck2("123.", 123);
    }

    @Test
    public void testNumberDecimal2() {
        this.parseAndCheck2("123.", 123, "~");
    }

    @Test
    public void testNumberDecimalFraction() {
        this.parseAndCheck2("123.5", 123.5);
    }

    @Test
    public void testNumberDecimalFraction2() {
        this.parseAndCheck2("123.5", 123.5, "~");
    }

    @Test
    public void testNumberDecimalFraction3() {
        this.parseAndCheck2("123.875", 123.875);
    }

    @Test
    public void testNumberDecimalFraction4() {
        this.parseAndCheck2("123.875", 123.875, "~");
    }

    @Test
    public void testMinusNumberDecimal() {
        this.parseAndCheck2("-123.", -123);
    }

    @Test
    public void testMinusNumberDecimal2() {
        this.parseAndCheck2("-123.", -123, "~");
    }

    @Test
    public void testMinusNumberDecimalFraction() {
        this.parseAndCheck2("-123.5", -123.5);
    }

    @Test
    public void testMinusNumberDecimalFraction2() {
        this.parseAndCheck2("-123.5", -123.5, "~");
    }

    @Test
    public void testMinusNumberDecimalFraction3() {
        this.parseAndCheck2("-123.875", -123.875);
    }

    @Test
    public void testMinusNumberDecimalFraction4() {
        this.parseAndCheck2("-123.875", -123.875, "~");
    }

    @Test
    public void testZeroDecimalZeroes() {
        this.parseAndCheck2("0.0000", 0.000);
    }

    @Test
    public void testMinusZeroDecimalZeroes() {
        this.parseAndCheck2("-0.0000", -0.000);
    }

    @Test
    public void testZeroE() {
        this.parseAndCheck2("0E", 0);
    }

    @Test
    public void testZeroE2() {
        this.parseAndCheck2("0E", 0, "~");
    }

    @Test
    public void testNumberE() {
        this.parseAndCheck2("1E", 1);
    }

    @Test
    public void testNumberE2() {
        this.parseAndCheck2("1E", 1, "~");
    }

    @Test
    public void testNumberE3() {
        this.parseAndCheck2("123E", 123);
    }

    @Test
    public void testNumberE4() {
        this.parseAndCheck2("123E", 123, "~");
    }

    @Test
    public void testNumberEExponent() {
        this.parseAndCheck2("123E45", 123E45);
    }

    @Test
    public void testNumberEExponent2() {
        this.parseAndCheck2("123E45", 123E45, "~");
    }

    @Test
    public void testNumberEPlusExponent() {
        this.parseAndCheck2("123E+45", 123E+45);
    }

    @Test
    public void testNumberPlusEExponent2() {
        this.parseAndCheck2("123E+45", 123E+45, "~");
    }

    @Test
    public void testNumberEMinusExponent() {
        this.parseAndCheck2("123E-45", 123E-45);
    }

    @Test
    public void testNumberMinusEExponent2() {
        this.parseAndCheck2("123E-45", 123E-45, "~");
    }

    @Test
    public void tesMinustNumberEMinusExponent() {
        this.parseAndCheck2("-123E-45", -123E-45);
    }

    @Test
    public void testMinusNumberMinusEExponent2() {
        this.parseAndCheck2("-123E-45", -123E-45, "~");
    }

    @Test
    public void testNumberDecimalFractionEExponent() {
        this.parseAndCheck2("123.5E-67", 123.5E-67);
    }

    @Test
    public void testNumberDecimalFractionEExponent2() {
        this.parseAndCheck2("123.5E-67", 123.5E-67, "~");
    }

    @Test
    public void testNumberDecimalFractionEExponent3() {
        this.parseAndCheck2("-123.5E-67", -123.5E-67);
    }

    @Test
    public void testNumberDecimalFractionEExponent4() {
        this.parseAndCheck2("-123.5E-67", -123.5E-67, "~");
    }

    @Test
    public void testZeroDecimalFractionEExponent() {
        this.parseAndCheck2("0.00000E-67", 0, "~");
    }

    @Test
    public void testNegativeZeroDecimalFractionEExponent2() {
        this.parseAndCheck2("-0.00000E-67", -0.0, "~");
    }

    @Test
    public void testNanFail() {
        this.parseFailAndCheck("N");
    }

    @Test
    public void testNanFail2() {
        this.parseFailAndCheck("N2");
    }

    @Test
    public void testNanFail3() {
        this.parseFailAndCheck("Na");
    }

    @Test
    public void testNanFail4() {
        this.parseFailAndCheck("Na2");
    }

    @Test
    public void testNan() {
        this.parseAndCheck2("NaN", Double.NaN);
    }

    @Test
    public void testNan2() {
        this.parseAndCheck2("NaN", Double.NaN, "~");
    }

    @Test
    public void testNan3() {
        this.parseAndCheck2("NaN", Double.NaN, "1");
    }

    @Test
    public void testInfinityFail() {
        this.parseFailAndCheck("I");
    }

    @Test
    public void testInfinityFail2() {
        this.parseFailAndCheck("I2");
    }

    @Test
    public void testInfinityFail3() {
        this.parseFailAndCheck("In");
    }

    @Test
    public void testInfinityFail4() {
        this.parseFailAndCheck("Inf");
    }

    @Test
    public void testInfinityFail5() {
        this.parseFailAndCheck("Infi");
    }

    @Test
    public void testInfinityFail6() {
        this.parseFailAndCheck("Infin");
    }

    @Test
    public void testInfinityFail7() {
        this.parseFailAndCheck("Infini");
    }

    @Test
    public void testInfinityFail8() {
        this.parseFailAndCheck("Infinit");
    }

    @Test
    public void testInfinity() {
        this.parseAndCheck2("Infinity", Double.POSITIVE_INFINITY);
    }

    @Test
    public void testInfinity2() {
        this.parseAndCheck2("Infinity", Double.POSITIVE_INFINITY, "~");
    }

    @Test
    public void testPlusInfinity() {
        this.parseAndCheck2("+Infinity", Double.POSITIVE_INFINITY);
    }

    @Test
    public void testMinusInfinity() {
        this.parseAndCheck2("-Infinity", Double.NEGATIVE_INFINITY);
    }

    @Test
    public void testToString() {
        assertEquals("Double", this.createParser().toString());
    }

    @Override
    protected DoubleParser createParser() {
        return DoubleParser.with('.');
    }

    private TextCursor parseAndCheck2(final String in, final double value){
        return this.parseAndCheck2(in, value, "");
    }

    private TextCursor parseAndCheck2(final String text, final double value, final String textAfter){
        return this.parseAndCheck(text + textAfter, DoubleParserToken.with(value, text), text, textAfter);
    }

    @Override
    protected Class<DoubleParser<FakeParserContext>> type() {
        return Cast.to(DoubleParser.class);
    }
}
