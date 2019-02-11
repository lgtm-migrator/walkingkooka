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

package walkingkooka.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.test.ClassTestCase;
import walkingkooka.type.MemberVisibility;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class TruthyNumberBooleanConverterTest extends ClassTestCase<TruthyNumberBooleanConverter>
        implements ConverterTesting<TruthyNumberBooleanConverter> {

    // BigDecimal..................................................

    @Test
    public void testBigDecimalNumberNonZero() {
        this.convertAndCheckTrue(BigDecimal.valueOf(+1));
    }

    @Test
    public void testBigDecimalNumberNonZero2() {
        this.convertAndCheckTrue(BigDecimal.valueOf(-1));
    }

    @Test
    public void testBigDecimalNumberZero() {
        this.convertAndCheckFalse(BigDecimal.valueOf(0));
    }

    // BigInteger..............................................................................

    @Test
    public void testBigIntegerNumberNonZero() {
        this.convertAndCheckTrue(BigInteger.valueOf(+1));
    }

    @Test
    public void testBigIntegerNumberNonZero2() {
        this.convertAndCheckTrue(BigInteger.valueOf(-1));
    }

    @Test
    public void testBigIntegerNumberZero() {
        this.convertAndCheckFalse(BigInteger.valueOf(0));
    }

    // Double..............................................................................

    @Test
    public void testDoubleNumberNonZero() {
        this.convertAndCheckTrue(-1.0);
    }

    @Test
    public void testDoubleNumberNonZero2() {
        this.convertAndCheckTrue(1.0);
    }

    @Test
    public void testDoubleNumberZero() {
        this.convertAndCheckFalse(0.0);
    }

    // Long..............................................................................

    @Test
    public void testLongNumberNonZero() {
        this.convertAndCheckTrue(-1L);
    }

    @Test
    public void testLongNumberNonZero2() {
        this.convertAndCheckTrue(1L);
    }

    @Test
    public void testLongNumberZero() {
        this.convertAndCheckFalse(0L);
    }

    // String......

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createConverter(), "Truthy BigDecimal|BigInteger|Byte|Short|Integer|Long|Float|Double->Boolean");
    }

    // helper............................................................................................................

    private void convertAndCheckTrue(final Number number) {
        this.convertAndCheck2(number,true);
    }

    private void convertAndCheckFalse(final Number number) {
        this.convertAndCheck2(number,false);
    }

    private void convertAndCheck2(final Number number, final boolean expected) {
        this.convertAndCheck(number, Boolean.class, expected);
    }

    @Override
    public TruthyNumberBooleanConverter createConverter() {
        return TruthyNumberBooleanConverter.INSTANCE;
    }

    @Override
    public ConverterContext createContext() {
        return ConverterContexts.fake();
    }

    @Override
    public Class<TruthyNumberBooleanConverter> type() {
        return TruthyNumberBooleanConverter.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
