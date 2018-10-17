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

import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public final class ChainConverterTest extends ConverterTestCase<ChainConverter> {

    @Test(expected = NullPointerException.class)
    public void testWithNullFirstConverterFails() {
        ChainConverter.with(null, this.intermediateTargetType(), this.localDateToBigDecimal());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullIntermediateTargetTypeFails() {
        ChainConverter.with(this.stringToLocalDate(), null, this.localDateToBigDecimal());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullLastConverterFails() {
        ChainConverter.with(this.stringToLocalDate(), this.intermediateTargetType(), null);
    }

    @Test
    public void testFirstFail() {
        this.convertFails("abc", BigDecimal.class);
    }

    public void testIncompatibleTargetTypeFails() {
        this.convertFails("abc", BigInteger.class);
    }

    @Test
    public void testSuccessful() {
        this.convertAndCheck("2000-12-31", BigDecimal.class, BigDecimal.valueOf(11322));
    }

    @Test
    public void testThenSuccessful() {
        this.convertAndCheck(this.stringToLocalDate().then(this.intermediateTargetType(), this.localDateToBigDecimal()),
                "2000-12-31",
                BigDecimal.class,
                BigDecimal.valueOf(11322));
    }

    @Test
    public void testToString() {
        assertEquals(this.stringToLocalDate() + "->" + this.localDateToBigDecimal(), this.createConverter().toString());
    }

    @Override
    protected ChainConverter createConverter() {
        return ChainConverter.with(this.stringToLocalDate(), this.intermediateTargetType(), this.localDateToBigDecimal());
    }

    private Converter stringToLocalDate() {
        return Converters.stringLocalDate(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private Class<?> intermediateTargetType() {
        return LocalDate.class;
    }

    private Converter localDateToBigDecimal() {
        return Converters.localDateBigDecimal(Converters.JAVA_EPOCH_OFFSET);
    }

    @Override
    protected Class<ChainConverter> type() {
        return ChainConverter.class;
    }
}