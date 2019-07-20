/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 */

package walkingkooka.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.math.NumberVisitorTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.type.JavaVisibility;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class NumberConverterNumberVisitorTest implements NumberVisitorTesting<NumberConverterNumberVisitor<BigDecimal>> {

    @Test
    public void testConvertUnsupported() {
        assertThrows(ConversionException.class, () -> {
            NumberConverterNumberVisitor.convert(this.converter(),
                    new Number() {
                        @Override
                        public int intValue() {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public long longValue() {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public float floatValue() {
                            throw new UnsupportedOperationException();
                        }

                        @Override
                        public double doubleValue() {
                            throw new UnsupportedOperationException();
                        }

                        private final static long serialVersionUID = 1L;
                    },
                    BigDecimal.class);
        });
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createVisitor(), this.converter() + " " + CharSequences.quoteAndEscape(BigDecimal.class.getName()));
    }

    @Override
    public NumberConverterNumberVisitor<BigDecimal> createVisitor() {
        return new NumberConverterNumberVisitor<BigDecimal>(this.converter(), BigDecimal.class);
    }

    private NumberBigDecimalConverter converter() {
        return NumberBigDecimalConverter.INSTANCE;
    }

    @Override
    public String typeNamePrefix() {
        return NumberConverter.class.getSimpleName();
    }

    @Override
    public Class<NumberConverterNumberVisitor<BigDecimal>> type() {
        return Cast.to(NumberConverterNumberVisitor.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}