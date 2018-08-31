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

import walkingkooka.text.cursor.parser.ParserTokenNodeName;
import walkingkooka.tree.search.SearchNode;

/**
 * Holds a single long number.
 */
public final class SpreadsheetLongParserToken extends SpreadsheetNumericParserToken<Long> {

    public final static ParserTokenNodeName NAME = parserTokenNodeName(SpreadsheetLongParserToken.class);

    static SpreadsheetLongParserToken with(final long value, final String text){
        checkText(text);

        return new SpreadsheetLongParserToken(value, text);
    }

    private SpreadsheetLongParserToken(final Long value, final String text){
        super(value, text);
    }

    @Override
    public SpreadsheetLongParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    SpreadsheetLongParserToken replaceText(final String text) {
        return new SpreadsheetLongParserToken(this.value, text);
    }

    @Override
    public boolean isBigDecimal() {
        return false;
    }

    @Override
    public boolean isBigInteger() {
        return false;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public boolean isLong() {
        return true;
    }

    @Override
    public boolean isSymbol() {
        return false;
    }

    @Override
    public boolean isText() {
        return false;
    }

    @Override
    public boolean isWhitespace() {
        return false;
    }

    @Override
    public void accept(final SpreadsheetParserTokenVisitor visitor){
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetLongParserToken;
    }

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    // HasSearchNode ...............................................................................................

    @Override
    public SearchNode toSearchNode()  {
        return SearchNode.longNode(this.text(), this.value());
    }
}
