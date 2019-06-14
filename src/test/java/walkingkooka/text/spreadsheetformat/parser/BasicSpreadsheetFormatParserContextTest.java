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

package walkingkooka.text.spreadsheetformat.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.JavaVisibility;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicSpreadsheetFormatParserContextTest implements ClassTesting2<BasicSpreadsheetFormatParserContext>,
        SpreadsheetFormatParserContextTesting<BasicSpreadsheetFormatParserContext> {

    @Test
    public void testWithNullDecimalNumberContextFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicSpreadsheetFormatParserContext.with(null);
        });
    }

    @Override
    public BasicSpreadsheetFormatParserContext createContext() {
        return BasicSpreadsheetFormatParserContext.with(DecimalNumberContexts.fake());
    }

    @Override
    public Class<BasicSpreadsheetFormatParserContext> type() {
        return BasicSpreadsheetFormatParserContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}