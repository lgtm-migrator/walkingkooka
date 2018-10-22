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

import walkingkooka.collect.list.Lists;
import walkingkooka.math.Fraction;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatBigDecimalParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatColorParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatConditionParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatDateTimeParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatExpressionParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatFractionParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatGeneralParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatGreaterThanEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatGreaterThanParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatLessThanEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatLessThanParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatNotEqualsParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatSeparatorSymbolParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatTextParserToken;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This visitor is used exclusively by {@link ExpressionSpreadsheetTextFormatter} to create formatters for all individual formatters
 * in an expressioned.
 */
final class ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor extends TextFormatterSpreadsheetFormatParserTokenVisitor {

    /**
     * Visits all the individual tokens in the given token which was compiled from the given pattern.
     */
    static List<SpreadsheetTextFormatter<Object>> analyze(final SpreadsheetFormatExpressionParserToken token,
                                                     final MathContext mathContext,
                                                     final Function<BigDecimal, Fraction> fractioner) {
        final ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor visitor = new ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor(mathContext, fractioner);
        return visitor.acceptAndMakeFormatter(token);
    }

    static SpreadsheetTextFormatter<Object> noText() {
        return SpreadsheetTextFormatters.fixed(Object.class, SpreadsheetTextFormatter.NO_TEXT);
    }

    /**
     * Private ctor use static method.
     */
    ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitor(final MathContext mathContext,
                                                                          final Function<BigDecimal, Fraction> fractioner) {
        super();
        this.mathContext = mathContext;
        this.fractioner = fractioner;
        this.createEmptyFormatter();
    }

    // BigDecimal.....................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatBigDecimalParserToken token) {
        this.setSpreadsheetTextFormatter(SpreadsheetTextFormatters.bigDecimal(token, this.mathContext), token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatFractionParserToken token) {
        this.setSpreadsheetTextFormatter(SpreadsheetTextFormatters.bigDecimalFraction(token, this.mathContext, this.fractioner), token);
    }

    private final MathContext mathContext;
    private final Function<BigDecimal, Fraction> fractioner;

    // Color.....................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatColorParserToken token) {
        this.formatter.color = token;
    }

    // Condition.....................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatEqualsParserToken token) {
        this.setCondition(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatGreaterThanEqualsParserToken token) {
        this.setCondition(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatGreaterThanParserToken token) {
        this.setCondition(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatLessThanEqualsParserToken token) {
        this.setCondition(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatLessThanParserToken token) {
        this.setCondition(token);
    }

    @Override
    protected void endVisit(final SpreadsheetFormatNotEqualsParserToken token) {
        this.setCondition(token);
    }

    private void setCondition(final SpreadsheetFormatConditionParserToken token) {
        this.formatter.condition = token;
    }

    // Date .....................................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatDateTimeParserToken token) {
        this.setSpreadsheetTextFormatter(SpreadsheetTextFormatters.localDateTime(token), token);
    }

    // General................................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatGeneralParserToken token) {
        this.setSpreadsheetTextFormatter(SpreadsheetTextFormatters.general(), token);
    }

    // Text..................................................................................................

    @Override
    protected void endVisit(final SpreadsheetFormatTextParserToken token) {
        this.setSpreadsheetTextFormatter(SpreadsheetTextFormatters.text(token), token);
    }

    // Separator.................................................................................................

    @Override
    protected void visit(final SpreadsheetFormatSeparatorSymbolParserToken token) {
        this.createEmptyFormatter();
    }

    // main..............................................................................................

    private List<SpreadsheetTextFormatter<Object>> acceptAndMakeFormatter(final SpreadsheetFormatExpressionParserToken token) {
        this.accept(token);

        final int count = this.formatters.size();
        return IntStream.range(0, count)
                .mapToObj(i -> this.formatters.get(i).formatter(i, this.numberFormatters))
                .collect(Collectors.toList());
    }

    private void createEmptyFormatter() {
        this.formatter = ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitorFormatter.create();
        this.formatters.add(this.formatter);
    }

    private void setSpreadsheetTextFormatter(final SpreadsheetTextFormatter<?> formatter, final SpreadsheetFormatParserToken token) {
        this.formatter.setFormatter(formatter);

        if(!token.isText()) {
            this.numberFormatters++;
        }
    }

    /**
     * Actually formatter, possible color and possible condition.
     */
    private ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitorFormatter formatter;

    private List<ExpressionSpreadsheetTextFormatterSpreadsheetFormatParserTokenVisitorFormatter> formatters = Lists.array();

    private int numberFormatters;
}