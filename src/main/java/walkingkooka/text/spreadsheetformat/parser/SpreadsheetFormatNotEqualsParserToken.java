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

import walkingkooka.compare.ComparisonRelation;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.visit.Visiting;

import java.util.List;

/**
 * Represents a not equals test operation with its parameters.
 */
public final class SpreadsheetFormatNotEqualsParserToken extends SpreadsheetFormatConditionParserToken<SpreadsheetFormatNotEqualsParserToken> {

    static SpreadsheetFormatNotEqualsParserToken with(final List<ParserToken> value, final String text) {
        return new SpreadsheetFormatNotEqualsParserToken(copyAndCheckTokensFailIfEmpty(value),
                checkTextNotEmptyOrWhitespace(text),
                WITHOUT_COMPUTE_REQUIRED);
    }

    private SpreadsheetFormatNotEqualsParserToken(final List<ParserToken> value, final String text, final List<ParserToken> valueWithout) {
        super(value, text, valueWithout);
    }

    @Override
    SpreadsheetFormatNotEqualsParserToken replace(final List<ParserToken> tokens, final String text, final List<ParserToken> without) {
        return new SpreadsheetFormatNotEqualsParserToken(tokens,
                text,
                without);
    }

    @Override
    public ComparisonRelation relation() {
        return ComparisonRelation.NE;
    }

    // isXXX..............................................................................................................

    @Override
    public boolean isEquals() {
        return false;
    }

    @Override
    public boolean isGreaterThan() {
        return false;
    }

    @Override
    public boolean isGreaterThanEquals() {
        return false;
    }

    @Override
    public boolean isLessThan() {
        return false;
    }

    @Override
    public boolean isLessThanEquals() {
        return false;
    }

    @Override
    public boolean isNotEquals() {
        return true;
    }

    // SpreadsheetFormatParserTokenVisitor..............................................................................

    @Override
    public void accept(final SpreadsheetFormatParserTokenVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Object...........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SpreadsheetFormatNotEqualsParserToken;
    }

}