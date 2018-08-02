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

import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursorSavePoint;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.text.cursor.parser.StringParserToken;

import java.util.Optional;

/**
 * A {@link Parser} that consumes a {@link SpreadsheetLabelNameParserToken}
 */
final class SpreadsheetLabelNameParser implements Parser<SpreadsheetLabelNameParserToken, SpreadsheetParserContext> {

    /**
     * Singleton
     */
    final static SpreadsheetLabelNameParser INSTANCE = new SpreadsheetLabelNameParser();

    /**
     * Private ctor use singleton
     */
    private SpreadsheetLabelNameParser() {
        super();
    }
    
    @Override
    public Optional<SpreadsheetLabelNameParserToken> parse(final TextCursor cursor, final SpreadsheetParserContext context) {
        final TextCursorSavePoint save = cursor.save();
        final Optional<StringParserToken> stringParserToken = LABEL.parse(cursor, context);
        return stringParserToken.isPresent() ?
               this.token(stringParserToken.get(), save) :
               Optional.empty();

    }

    private Optional<SpreadsheetLabelNameParserToken> token(final StringParserToken stringParserToken, final TextCursorSavePoint save) {
        final String text = stringParserToken.text();
        return SpreadsheetLabelName.isAcceptableLength(text) && !SpreadsheetLabelName.isCellReference(text) ?
            Optional.of(SpreadsheetParserToken.labelName(SpreadsheetLabelName.with(text), text)) :
            restoreAndNothing(save);
    }

    private Optional<SpreadsheetLabelNameParserToken> restoreAndNothing(final TextCursorSavePoint save) {
        save.restore();
        return Optional.empty();
    }

    private final static Parser<StringParserToken, SpreadsheetParserContext> LABEL = Parsers.stringInitialAndPartCharPredicate(
        SpreadsheetLabelName.INITIAL,
        SpreadsheetLabelName.PART,
        1,
        SpreadsheetLabelName.MAX_LENGTH
    );

    @Override
    public String toString() {
        return SpreadsheetLabelNameParserToken.NAME.toString();
    }
}
