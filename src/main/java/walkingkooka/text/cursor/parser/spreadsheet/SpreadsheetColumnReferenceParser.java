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

package walkingkooka.text.cursor.parser.spreadsheet;

import walkingkooka.text.cursor.parser.Parser;

/**
 * A {@link Parser} that consumes a {@link SpreadsheetColumnReferenceParserToken}
 */
final class SpreadsheetColumnReferenceParser extends SpreadsheetColumnOrRowReferenceParser<SpreadsheetColumnReferenceParserToken> {

    /**
     * Singleton
     */
    final static SpreadsheetColumnReferenceParser INSTANCE = new SpreadsheetColumnReferenceParser();

    /**
     * Private ctor use singleton
     */
    private SpreadsheetColumnReferenceParser() {
        super();
    }

    @Override
    int valueFromDigit(final char c) {
        return valueFromDigit0(c);
    }

    // @Visible SpreadsheetLabelNameParser
    static int valueFromDigit0(final char c) {
        final int digit = c - 'A';
        return digit >= 0 && digit < RADIX ? digit + 1 : -1;
    }

    @Override int radix() {
        return RADIX;
    }

    private final static int RADIX = 26;

    @Override
    SpreadsheetColumnReferenceParserToken token1(final SpreadsheetReferenceKind absoluteOrRelative, final int value, final String text) {
        return SpreadsheetColumnReferenceParserToken.columnReference(absoluteOrRelative.column(value), text);
    }

    @Override
    public String toString() {
        return SpreadsheetColumnReferenceParserToken.NAME.toString();
    }
}