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
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatColorNameParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatColorNumberParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatEscapeParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatQuotedTextParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatStarParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatTextLiteralParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatTextPlaceholderParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatUnderscoreParserToken;

import java.util.Optional;

/**
 * This visitor is used exclusively by {@link TextSpreadsheetTextFormatter#format(Object, SpreadsheetTextFormatContext)}.
 * Only some methods in {@link walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserTokenVisitor} are overridden, all other tokens will be ignored.
 */
final class TextSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor extends TextFormatterSpreadsheetFormatParserTokenVisitor {

    /**
     * Visits all the individual tokens in the given token which was compiled from the given pattern.
     */
    static SpreadsheetFormattedText format(final SpreadsheetFormatParserToken token, final String value, final SpreadsheetTextFormatContext context) {
        final TextSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor visitor = new TextSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor(value, context);
        visitor.accept(token);
        return SpreadsheetFormattedText.with(Optional.ofNullable(visitor.color), visitor.text.toString());
    }

    /**
     * Private ctor use static method.
     */
    TextSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor(final String value, final SpreadsheetTextFormatContext context) {
        super();
        this.context = context;
        this.value = value;
    }

    @Override
    protected final void visit(final SpreadsheetFormatColorNameParserToken token) {
        this.color = this.context.colorName(token.value());
    }

    @Override
    protected final void visit(final SpreadsheetFormatColorNumberParserToken token) {
        this.color = this.context.colorNumber(token.value());
    }

    /**
     * Will be set either the {@link SpreadsheetFormatColorNameParserToken} or {@link SpreadsheetFormatColorNumberParserToken}
     * is encountered.
     */
    private Color color = null;

    @Override
    protected void visit(final SpreadsheetFormatEscapeParserToken token) {
        this.append(token.value());
    }

    @Override
    protected void visit(final SpreadsheetFormatQuotedTextParserToken token) {
        this.append(token.value());
    }

    @Override
    protected void visit(final SpreadsheetFormatStarParserToken token) {
        final int fill = this.context.width() - this.text.length();
        final char c = token.value();

        for (int i = 0; i < fill; i++) {
            this.text.append(c);
        }
    }

    @Override
    protected void visit(final SpreadsheetFormatTextLiteralParserToken token) {
        this.append(token.value());
    }

    @Override
    protected void visit(final SpreadsheetFormatTextPlaceholderParserToken token) {
        this.append(this.value);
    }

    private final String value;

    @Override
    protected void visit(final SpreadsheetFormatUnderscoreParserToken token) {
        this.append(token.value());
    }

    private void append(final char c) {
        this.text.append(c);
    }

    private void append(final String text) {
        this.text.append(text);
    }

    private final SpreadsheetTextFormatContext context;

    /**
     * Aggregates the formatted output text.
     */
    private final StringBuilder text = new StringBuilder();

    @Override
    public String toString() {
        return this.text.toString();
    }
}
