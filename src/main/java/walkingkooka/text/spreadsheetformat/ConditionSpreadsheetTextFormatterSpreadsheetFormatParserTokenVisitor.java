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

import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.compare.ComparisonRelation;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatConditionNumberParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatConditionParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatGreaterThanEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatGreaterThanParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatLessThanEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatLessThanParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatNotEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserTokenVisitor;

import java.math.BigDecimal;
import java.util.function.Predicate;

/**
 * Finds the condition and number parameter in the {@link SpreadsheetFormatConditionParserToken}.
 */
final class ConditionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor extends SpreadsheetFormatParserTokenVisitor {

    static Predicate<BigDecimal> predicateOrFail(final SpreadsheetFormatConditionParserToken token) {
        final ConditionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor visitor = new ConditionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor();
        token.accept(visitor);
        return visitor.relation.predicate(visitor.number);
    }

    // @VisibleForTesting.
    ConditionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor() {
        super();
    }

    @Override
    protected void endVisit(final SpreadsheetFormatEqualsParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatGreaterThanEqualsParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatGreaterThanParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatLessThanEqualsParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatLessThanParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatNotEqualsParserToken token) {
        this.setRelation(token);
    }

    @Override
    protected void visit(final SpreadsheetFormatConditionNumberParserToken token) {
        this.number = token.value();
    }

    private void setRelation(final SpreadsheetFormatConditionParserToken token) {
        this.relation = token.relation();
    }

    private ComparisonRelation relation;
    private BigDecimal number;

    @Override
    public String toString() {
        return ToStringBuilder.create()
                .valueSeparator(" ")
                .value(this.relation)
                .value(this.number)
                .build();
    }
}