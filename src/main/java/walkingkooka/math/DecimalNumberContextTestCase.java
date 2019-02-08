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
package walkingkooka.math;

import walkingkooka.ContextTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class DecimalNumberContextTestCase<C extends DecimalNumberContext> extends ContextTestCase<C> {

    @Override
    public String typeNameSuffix() {
        return DecimalNumberContext.class.getSimpleName();
    }

    protected void checkCurrencySymbol(final DecimalNumberContext context, final String currencySymbol) {
        assertEquals(currencySymbol, context.currencySymbol(), "currencySymbol");
    }

    protected void checkDecimalPoint(final DecimalNumberContext context, final char decimalPoint) {
        assertEquals(decimalPoint, context.decimalPoint(), "decimalPoint");
    }

    protected void checkExponentSymbol(final DecimalNumberContext context, final char exponentSymbol) {
        assertEquals(exponentSymbol, context.exponentSymbol(), "exponentSymbol");
    }

    protected void checkGroupingSeparator(final DecimalNumberContext context, final char groupingSeparator) {
        assertEquals(groupingSeparator, context.groupingSeparator(), "groupingSeparator");
    }

    protected void checkMinusSign(final DecimalNumberContext context, final char minusSign) {
        assertEquals(minusSign, context.minusSign(), "minusSign");
    }
    
    protected void checkPercentageSymbol(final DecimalNumberContext context, final char percentageSymbol) {
        assertEquals(percentageSymbol, context.percentageSymbol(), "percentageSymbol");
    }
    
    protected void checkPlusSign(final DecimalNumberContext context, final char plusSign) {
        assertEquals(plusSign, context.plusSign(), "plusSign");
    }
}
