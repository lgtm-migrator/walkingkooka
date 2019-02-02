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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.convert.ConversionException;
import walkingkooka.text.CharSequences;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class GeneralSpreadsheetTextFormatterTest extends SpreadsheetTextFormatterTestCase<GeneralSpreadsheetTextFormatter, Object> {

    private final static BigDecimal LOCAL_DATE_TIME_BIGDECIMAL = BigDecimal.valueOf(999);

    @Test
    public void testFormatText() {
        final String text = "abc123";
        this.formatAndCheck(text, text);
    }

    @Test
    public void testFormatBigDecimal() {
        this.formatAndCheck(BigDecimal.valueOf(123), "123D00Text");
    }

    @Test
    public void testFormatLocalDateTime() {
        this.formatAndCheck(LocalDateTime.of(2000, 12, 31, 12, 58, 59), "999D00Text");
    }

    @Override
    public void testToString() {
        assertEquals("General", this.createFormatter().toString());
    }

    @Override
    protected GeneralSpreadsheetTextFormatter createFormatter() {
        return GeneralSpreadsheetTextFormatter.INSTANCE;
    }

    @Override
    protected Object value() {
        return BigDecimal.valueOf(1.5);
    }

    @Override
    protected SpreadsheetTextFormatContext createContext() {
        return new FakeSpreadsheetTextFormatContext() {

            @Override
            public String currencySymbol() {
                return "C";
            }

            @Override
            public char decimalPoint() {
                return 'D';
            }

            @Override
            public char exponentSymbol() {
                return 'X';
            }

            @Override
            public char groupingSeparator() {
                return 'G';
            }

            @Override
            public char minusSign() {
                return 'M';
            }

            @Override
            public char percentageSymbol() {
                return 'R';
            }

            @Override
            public char plusSign() {
                return 'P';
            }

            @Override
            public <T> T convert(final Object value, final Class<T> target) {
                assertEquals(BigDecimal.class, target, "targetType");

                if (value instanceof String) {
                    throw new ConversionException("Failed to convert " + value);
                }
                if (value instanceof BigDecimal) {
                    return target.cast(value);
                }
                if (value instanceof LocalDateTime) {
                    return target.cast(LOCAL_DATE_TIME_BIGDECIMAL);
                }
                throw new ConversionException("Failed to convert " + CharSequences.quoteIfChars(value) + " to " + target.getName());
            }

            @Override
            public String generalDecimalFormatPattern() {
                return "#0.00'Text'";
            }
        };
    }

    @Override
    protected Class<GeneralSpreadsheetTextFormatter> type() {
        return Cast.to(GeneralSpreadsheetTextFormatter.class);
    }
}
