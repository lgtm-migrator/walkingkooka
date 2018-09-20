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

package walkingkooka.tree.expression;

import org.junit.Test;
import walkingkooka.convert.Converters;
import walkingkooka.tree.visit.Visiting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class ExpressionLocalDateTimeNodeTest extends ExpressionLeafNodeTestCase<ExpressionLocalDateTimeNode, LocalDateTime>{

    private final static String DATETIMESTRING = "2000-01-31T12:59:00";
    private final static String DIFFERENT_DATETIME_STRING = "1999-12-31T12:58";
    private final static double VALUE = Converters.localDateTimeDouble().convert(LocalDateTime.parse(DATETIMESTRING), Double.class);

    private final static String DATETIMESTRING2 = "1999-12-31T00:00:00";
    private final static long VALUE2 = Converters.localDateTimeLong().convert(LocalDateTime.parse(DATETIMESTRING2), Long.class);

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final ExpressionLocalDateTimeNode node = this.createExpressionNode();

        new FakeExpressionNodeVisitor() {
            @Override
            protected Visiting startVisit(final ExpressionNode n) {
                assertSame(node, n);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ExpressionNode n) {
                assertSame(node, n);
                b.append("2");
            }

            @Override
            protected void visit(final ExpressionLocalDateTimeNode n) {
                assertSame(node, n);
                b.append("3");
            }
        }.accept(node);
        assertEquals("132", b.toString());
    }

    // Evaluation ...................................................................................................

    @Test
    public void testToBooleanFalse() {
        this.evaluateAndCheckBoolean(this.createExpressionNode(0), false);
    }

    @Test
    public void testToBooleanTrue() {
        this.evaluateAndCheckBoolean(this.createExpressionNode(1), true);
    }

    @Test
    public void testToBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(), VALUE);
    }

    @Test
    public void testToBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(DATETIMESTRING2), VALUE2);
    }

    @Test
    public void testToDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(), VALUE);
    }

    @Test
    public void testToLocalDate() {
        this.evaluateAndCheckLocalDate(this.createExpressionNode(),
                Converters.localDateTimeLocalDate().convert(this.value(), LocalDate.class));
    }

    @Test
    public void testToLocalDateTime() {
        this.evaluateAndCheckLocalDateTime(this.createExpressionNode(), this.value());
    }

    @Test
    public void testToLocalTime() {
        this.evaluateAndCheckLocalTime(this.createExpressionNode(),
                Converters.localDateTimeLocalTime().convert(this.value(), LocalTime.class));
    }

    @Test
    public void testToLong() {
        this.evaluateAndCheckLong(this.createExpressionNode(DATETIMESTRING2), VALUE2);
    }

    @Test
    public void testToText() {
        this.evaluateAndCheckText(this.createExpressionNode(DATETIMESTRING), DATETIMESTRING);
    }

    // ToString ...................................................................................................

    @Test
    public void testToString() {
        assertEquals("2000-01-31T12:59", this.createExpressionNode().toString());
    }

    @Test
    public void testToString2() {
        assertEquals(DIFFERENT_DATETIME_STRING, this.createExpressionNode(DIFFERENT_DATETIME_STRING).toString());
    }

    private ExpressionLocalDateTimeNode createExpressionNode(final double value) {
        return this.localDateTime(value);
    }

    private ExpressionLocalDateTimeNode createExpressionNode(final String value) {
        return this.createExpressionNode(LocalDateTime.parse(value));
    }

    @Override
    ExpressionLocalDateTimeNode createExpressionNode(final LocalDateTime value) {
        return ExpressionLocalDateTimeNode.with(value);
    }

    @Override
    LocalDateTime value() {
        return LocalDateTime.parse(DATETIMESTRING);
    }

    @Override
    LocalDateTime differentValue() {
        return LocalDateTime.parse(DIFFERENT_DATETIME_STRING);
    }

    @Override
    Class<ExpressionLocalDateTimeNode> expressionNodeType() {
        return ExpressionLocalDateTimeNode.class;
    }
}