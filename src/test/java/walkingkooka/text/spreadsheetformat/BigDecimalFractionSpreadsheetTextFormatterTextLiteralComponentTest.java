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

import static org.junit.Assert.assertEquals;

public final class BigDecimalFractionSpreadsheetTextFormatterTextLiteralComponentTest extends BigDecimalFractionSpreadsheetTextFormatterComponentTestCase<BigDecimalFractionSpreadsheetTextFormatterTextLiteralComponent> {

    @Test
    public void testToString() {
        final String text = "abc123";
        assertEquals(text, BigDecimalFractionSpreadsheetTextFormatterTextLiteralComponent.with(text).toString());
    }

    @Override
    protected Class<BigDecimalFractionSpreadsheetTextFormatterTextLiteralComponent> type() {
        return BigDecimalFractionSpreadsheetTextFormatterTextLiteralComponent.class;
    }
}