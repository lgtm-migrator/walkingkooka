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

import walkingkooka.Cast;
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.test.HashCodeEqualsDefined;

import java.text.DecimalFormatSymbols;
import java.util.Objects;

/**
 * Used as a key to {@link java.text.DecimalFormat}.
 */
final class DecimalFormatStringConverterSymbols implements HashCodeEqualsDefined {

    /**
     * Factory only called by {@link DecimalFormatStringConverter}
     */
    static DecimalFormatStringConverterSymbols with(final String currencySymbol,
                                                    final char decimalPoint,
                                                    final char exponentSymbol,
                                                    final char groupingSeparator,
                                                    final char minusSign,
                                                    final char percentageSymbol,
                                                    final char plusSign) {
        return new DecimalFormatStringConverterSymbols(currencySymbol,
                decimalPoint,
                exponentSymbol,
                groupingSeparator,
                minusSign,
                percentageSymbol,
                plusSign);
    }

    private DecimalFormatStringConverterSymbols(final String currencySymbol,
                                                final char decimalPoint,
                                                final char exponentSymbol,
                                                final char groupingSeparator,
                                                final char minusSign,
                                                final char percentageSymbol,
                                                final char plusSign) {
        super();
        this.currencySymbol = currencySymbol;
        this.decimalPoint = decimalPoint;
        this.exponentSymbol = exponentSymbol;
        this.groupingSeparator = groupingSeparator;
        this.minusSign = minusSign;
        this.percentageSymbol = percentageSymbol;
        this.plusSign = plusSign;
    }

    DecimalFormatSymbols decimalFormatSymbols() {
        final DecimalFormatSymbols symbols = new DecimalFormatSymbols();

        symbols.setCurrencySymbol(this.currencySymbol);
        symbols.setDecimalSeparator(this.decimalPoint);
        symbols.setExponentSeparator(String.valueOf(this.exponentSymbol));
        symbols.setGroupingSeparator(this.groupingSeparator);
        symbols.setMinusSign(this.minusSign);
        symbols.setPercent(this.percentageSymbol);

        return symbols;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.currencySymbol,
                this.decimalPoint,
                this.exponentSymbol,
                this.groupingSeparator,
                this.minusSign,
                this.percentageSymbol,
                this.plusSign);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
               other instanceof DecimalFormatStringConverterSymbols &&
               this.equals0(Cast.to(other));
    }

    private boolean equals0(final DecimalFormatStringConverterSymbols other) {
        return this.currencySymbol.equals(other.currencySymbol) &&
                this.decimalPoint == other.decimalPoint &&
                this.exponentSymbol == other.exponentSymbol &&
                this.groupingSeparator == other.groupingSeparator &&
                this.minusSign == other.minusSign &&
                this.percentageSymbol == other.percentageSymbol &&
                this.plusSign == other.plusSign;
    }

    private final String currencySymbol;

    private final char decimalPoint;

    private final char exponentSymbol;

    private final char groupingSeparator;

    private final char minusSign;

    private final char percentageSymbol;

    private final char plusSign;

    @Override
    public String toString() {
        return ToStringBuilder.create()
                .value(this.currencySymbol)
                .value(this.decimalPoint)
                .value(this.exponentSymbol)
                .value(this.groupingSeparator)
                .value(this.minusSign)
                .value(this.percentageSymbol)
                .value(this.plusSign)
                .build();
    }
}