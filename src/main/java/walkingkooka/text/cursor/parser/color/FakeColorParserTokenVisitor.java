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

package walkingkooka.text.cursor.parser.color;

import walkingkooka.test.Fake;
import walkingkooka.text.cursor.parser.BigDecimalParserToken;
import walkingkooka.text.cursor.parser.BigIntegerParserToken;
import walkingkooka.text.cursor.parser.CharacterParserToken;
import walkingkooka.text.cursor.parser.DoubleParserToken;
import walkingkooka.text.cursor.parser.DoubleQuotedParserToken;
import walkingkooka.text.cursor.parser.LocalDateParserToken;
import walkingkooka.text.cursor.parser.LocalDateTimeParserToken;
import walkingkooka.text.cursor.parser.LocalTimeParserToken;
import walkingkooka.text.cursor.parser.LongParserToken;
import walkingkooka.text.cursor.parser.OffsetDateTimeParserToken;
import walkingkooka.text.cursor.parser.OffsetTimeParserToken;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.RepeatedParserToken;
import walkingkooka.text.cursor.parser.SequenceParserToken;
import walkingkooka.text.cursor.parser.SignParserToken;
import walkingkooka.text.cursor.parser.SingleQuotedParserToken;
import walkingkooka.text.cursor.parser.StringParserToken;
import walkingkooka.text.cursor.parser.ZonedDateTimeParserToken;
import walkingkooka.tree.visit.Visiting;

public class FakeColorParserTokenVisitor extends ColorParserTokenVisitor implements Fake {

    @Override
    protected Visiting startVisit(final ParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final ParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final BigDecimalParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final BigIntegerParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final CharacterParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ColorParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final DoubleParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final DoubleQuotedParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final HslParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final HsvParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LocalDateParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LocalDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LocalTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final LongParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final OffsetDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final OffsetTimeParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Visiting startVisit(final RepeatedParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final RepeatedParserToken token) {
        super.endVisit(token);
    }

    @Override
    protected Visiting startVisit(final SequenceParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void endVisit(final SequenceParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final SingleQuotedParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final SignParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final StringParserToken token) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void visit(final ZonedDateTimeParserToken token) {
        throw new UnsupportedOperationException();
    }
}
