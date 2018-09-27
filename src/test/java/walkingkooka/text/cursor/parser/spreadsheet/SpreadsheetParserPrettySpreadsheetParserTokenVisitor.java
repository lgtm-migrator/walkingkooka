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

import walkingkooka.io.printer.IndentingPrinters;
import walkingkooka.io.printer.Printers;
import walkingkooka.text.Indentation;
import walkingkooka.text.LineEnding;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.visit.Visiting;
import walkingkooka.tree.visit.VisitorPrettyPrinter;

/**
 * Pretty prints a graph of {@link SpreadsheetParserToken tokens}.
 */
final class SpreadsheetParserPrettySpreadsheetParserTokenVisitor extends SpreadsheetParserTokenVisitor {

    static String toString(final ParserToken token) {
        final StringBuilder b = new StringBuilder();

        new SpreadsheetParserPrettySpreadsheetParserTokenVisitor(VisitorPrettyPrinter.with(
                IndentingPrinters.printer(Printers.stringBuilder(b, LineEnding.NL)),
                Indentation.with("  "),
                SpreadsheetParserPrettySpreadsheetParserTokenVisitor::tokenName)).accept(token);
        return b.toString();
    }

    private static String tokenName(final SpreadsheetParserToken token) {
        final String typeName = token.getClass().getSimpleName();
        return typeName.substring("Spreadsheet".length(), typeName.length() - "ParserToken".length());
    }

    private SpreadsheetParserPrettySpreadsheetParserTokenVisitor(final VisitorPrettyPrinter<SpreadsheetParserToken> printer) {
        this.printer = printer;
    }

    @Override
    protected Visiting startVisit(final SpreadsheetAdditionParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetAdditionParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetCellReferenceParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetCellReferenceParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetDivisionParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetDivisionParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetEqualsParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetEqualsParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetFunctionParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFunctionParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetGreaterThanParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetGreaterThanParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetGreaterThanEqualsParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetGreaterThanEqualsParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetGroupParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetGroupParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetLessThanParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetLessThanParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetLessThanEqualsParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetLessThanEqualsParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetMultiplicationParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetMultiplicationParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetNegativeParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetNegativeParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetNotEqualsParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetNotEqualsParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetPercentageParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetPercentageParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetPowerParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetPowerParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetRangeParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetRangeParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SpreadsheetSubtractionParserToken token) {
        this.printer.enter(token);
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final SpreadsheetSubtractionParserToken token) {
        this.printer.exit(token);
        super.endVisit(token);
    }

    // leaf ......................................................................................................

    @Override
    protected void visit(final SpreadsheetBetweenSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetBigDecimalParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetBigIntegerParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetCloseParenthesisSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetColumnReferenceParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetDivideSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetDoubleParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetEqualsSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetFunctionNameParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetFunctionParameterSeparatorSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetGreaterThanSymbolParserToken token) {
        this.printer.leaf(token);
        super.visit(token);
    }

    @Override
    protected void visit(final SpreadsheetGreaterThanEqualsSymbolParserToken token) {
        this.printer.leaf(token);
        super.visit(token);
    }

    @Override
    protected void visit(final SpreadsheetLabelNameParserToken token) {
        this.printer.leaf(token);
        super.visit(token);
    }

    @Override
    protected void visit(final SpreadsheetLessThanSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetLessThanEqualsSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetLocalDateParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetLocalDateTimeParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetLocalTimeParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetLongParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetMinusSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetMultiplySymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetNotEqualsSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetOpenParenthesisSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetPercentSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetPlusSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetPowerSymbolParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetRowReferenceParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetTextParserToken token) {
        this.printer.leaf(token);
    }

    @Override
    protected void visit(final SpreadsheetWhitespaceParserToken token) {
        this.printer.leaf(token);
    }

    private final VisitorPrettyPrinter<SpreadsheetParserToken> printer;

    @Override
    public String toString() {
        return this.printer.toString();
    }
}
