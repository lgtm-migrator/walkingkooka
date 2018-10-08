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

import walkingkooka.color.Color;

/**
 * Helper that resolves the reference in the original expression into a context supplied {@link Color}
 */
enum ColorSpreadsheetTextFormatterColorSource {

    NAME {
        @Override
        Color resolve(final Object value, final SpreadsheetTextFormatContext context) {
            return context.colorName((String) value);
        }
    },

    NUMBER {
        @Override
        Color resolve(final Object value, final SpreadsheetTextFormatContext context) {
            return context.colorNumber((Integer) value);
        }
    };

    abstract Color resolve(final Object value, final SpreadsheetTextFormatContext context);
}