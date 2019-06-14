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

import walkingkooka.math.DecimalNumberContext;
import walkingkooka.type.PublicStaticHelper;

public final class SpreadsheetFormatParserContexts implements PublicStaticHelper {

    /**
     * {@see BasicSpreadsheetFormatParserContext}
     */
    public static SpreadsheetFormatParserContext basic(final DecimalNumberContext context) {
        return BasicSpreadsheetFormatParserContext.with(context);
    }

    /**
     * Stop creation.
     */
    private SpreadsheetFormatParserContexts() {
        throw new UnsupportedOperationException();
    }
}