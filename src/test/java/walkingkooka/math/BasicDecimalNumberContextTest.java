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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicDecimalNumberContextTest extends DecimalNumberContextTestCase<BasicDecimalNumberContext> {

    @Test
    public void testWithNullCurrencySymbol() {
        assertThrows(NullPointerException.class, () -> {
            BasicDecimalNumberContext.with(null, '.', 'E', ',', '-', '%','+');
        });
    }

    @Test
    public void testWith() {
        final BasicDecimalNumberContext context = this.createContext();
        this.checkCurrencySymbol(context, "$");
        this.checkDecimalPoint(context, '.');
        this.checkExponentSymbol(context, 'E');
        this.checkGroupingSeparator(context, ',');
        this.checkMinusSign(context, '-');
        this.checkPercentageSymbol(context, '%');
        this.checkPlusSign(context, '+');
    }

    @Test
    public void testToString() {
        assertEquals("\"$\" '.' 'E' ',' '-' '%' '+'", this.createContext().toString());
    }

    @Override
    protected BasicDecimalNumberContext createContext() {
        return BasicDecimalNumberContext.with("$", '.', 'E', ',', '-', '%','+');
    }

    @Override
    protected Class<BasicDecimalNumberContext> type() {
        return BasicDecimalNumberContext.class;
    }
}
