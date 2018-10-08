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

/**
 * Inserts the exponent symbol taken from the {@link SpreadsheetTextFormatContext} into the formatted text output.
 */
final class BigDecimalSpreadsheetTextFormatterExponentSymbolComponent extends BigDecimalSpreadsheetTextFormatterComponent {

    /**
     * Singleton
     */
    static final BigDecimalSpreadsheetTextFormatterExponentSymbolComponent INSTANCE = new BigDecimalSpreadsheetTextFormatterExponentSymbolComponent();

    /**
     * Private ctor use singleton
     */
    private BigDecimalSpreadsheetTextFormatterExponentSymbolComponent() {
        super();
    }

    @Override
    void append(final BigDecimalSpreadsheetTextFormatterComponentContext context) {
        context.appendExponent();
    }

    @Override
    public String toString() {
        return "E";
    }
}