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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.visit.Visiting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public final class ExpressionSubtractionNodeTest extends ExpressionArithmeticBinaryNodeTestCase2<ExpressionSubtractionNode> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<ExpressionNode> visited = Lists.array();

        final ExpressionSubtractionNode sub = this.createExpressionNode();
        final ExpressionNode text1 = sub.children().get(0);
        final ExpressionNode text2 = sub.children().get(1);

        new FakeExpressionNodeVisitor() {
            @Override
            protected Visiting startVisit(final ExpressionNode n) {
                b.append("1");
                visited.add(n);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ExpressionNode n) {
                b.append("2");
                visited.add(n);
            }

            @Override
            protected Visiting startVisit(final ExpressionSubtractionNode t) {
                assertSame(sub, t);
                b.append("3");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ExpressionSubtractionNode t) {
                assertSame(sub, t);
                b.append("4");
                visited.add(t);
            }

            @Override
            protected void visit(final ExpressionTextNode t) {
                b.append("5");
                visited.add(t);
            }
        }.accept(sub);
        assertEquals("1315215242", b.toString());
        assertEquals(Lists.of(sub, sub,
                text1, text1, text1,
                text2, text2, text2,
                sub, sub),
                visited,
                "visited");
    }

    // toBoolean....................................................................................................

    @Test
    public void testEvaluateToBooleanTrue() {
        // left - right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(34)), true);
    }

    @Test
    public void testEvaluateToBooleanFalse() {
        // left - right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(12)), false);
    }

    // toBigDecimal....................................................................................................

    @Test
    public void testEvaluateToBigDecimalBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), bigDecimal(34.5)), BigDecimal.valueOf(12 - 34.5));
    }

    @Test
    public void testEvaluateToBigDecimalBigInteger() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), bigInteger(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalDouble() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), doubleValue(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalLocalDate() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), localDate(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalLocalDateTime() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), localDateTime(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalLocalTime() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), localTime(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalLong() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), longValue(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigDecimalText() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigDecimal(12), text(34)), BigDecimal.valueOf(12 - 34));
    }

    // toBigInteger....................................................................................................

    @Test
    public void testEvaluateToBigIntegerBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigInteger(12), bigDecimal(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(bigInteger(12), bigInteger(34)), BigInteger.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerDouble() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigInteger(12), doubleValue(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerLocalDate() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigInteger(12), localDate(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerLocalDateTime() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigInteger(12), localDateTime(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerLocalTime() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(bigInteger(12), localTime(34)), BigDecimal.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerLong() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(bigInteger(12), longValue(34)), BigInteger.valueOf(12 - 34));
    }

    @Test
    public void testEvaluateToBigIntegerText() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(bigInteger(12), text(34)), BigInteger.valueOf(12 - 34));
    }

    // toDouble....................................................................................................

    @Test
    public void testEvaluateToDoubleBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(doubleValue(12), bigDecimal(34)), BigDecimal.valueOf(12.0 - 34.0));
    }

    @Test
    public void testEvaluateToDoubleBigInteger() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(doubleValue(12), bigInteger(34)), BigDecimal.valueOf(12.0 - 34.0));
    }

    @Test
    public void testEvaluateToDoubleDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), doubleValue(34)), 12.0 - 34.0);
    }

    @Test
    public void testEvaluateToDoubleLocalDate() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), localDate(34)), 12.0 - 34.0);
    }

    @Test
    public void testEvaluateToDoubleLocalDateTime() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), localDateTime(34)), 12.0 - 34.0);
    }

    @Test
    public void testEvaluateToDoubleLocalTime() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), localTime(34)), 12.0 - 34.0);
    }

    @Test
    public void testEvaluateToDoubleLong() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), longValue(34)), 12.0 - 34.0);
    }

    @Test
    public void testEvaluateToDoubleText() {
        this.evaluateAndCheckDouble(this.createExpressionNode(doubleValue(12), text(34)), 12.0 - 34.0);
    }

    // toLocalDate....................................................................................................

    @Test
    public void testEvaluateToLocalDateBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(localDate(12), bigDecimal(34)), BigDecimal.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalDateBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(localDate(12), bigInteger(34)), BigInteger.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalDateDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(localDate(12), doubleValue(34)), 12L - 34.0);
    }

    @Test
    public void testEvaluateToLocalDateLocalDate() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDate(12), localDate(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateLocalDateTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDate(12), localDateTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateLocalTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDate(12), localTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateLong() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDate(12), longValue(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateText() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDate(12), text(34)), 12L - 34L);
    }

    // toLocalDateTime....................................................................................................

    @Test
    public void testEvaluateToLocalDateTimeBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(localDateTime(12), bigDecimal(34)), BigDecimal.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalDateTimeBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(localDateTime(12), bigInteger(34)), BigInteger.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalDateTimeDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(localDateTime(12), doubleValue(34)), 12L - 34.0);
    }

    @Test
    public void testEvaluateToLocalDateTimeLocalDate() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDateTime(12), localDate(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateTimeLocalDateTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDateTime(12), localDateTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateTimeLocalTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDateTime(12), localTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateTimeLong() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDateTime(12), longValue(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalDateTimeText() {
        this.evaluateAndCheckLong(this.createExpressionNode(localDateTime(12), text(34)), 12L - 34L);
    }

    // toLocalTime....................................................................................................

    @Test
    public void testEvaluateToLocalTimeBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(localTime(12), bigDecimal(34)), BigDecimal.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalTimeBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(localTime(12), bigInteger(34)), BigInteger.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLocalTimeDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(localTime(12), doubleValue(34)), 12L - 34.0);
    }

    @Test
    public void testEvaluateToLocalTimeLocalDate() {
        this.evaluateAndCheckLong(this.createExpressionNode(localTime(12), localDate(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalTimeLocalDateTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localTime(12), localDateTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalTimeLocalTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(localTime(12), localTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalTimeLong() {
        this.evaluateAndCheckLong(this.createExpressionNode(localTime(12), longValue(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLocalTimeText() {
        this.evaluateAndCheckLong(this.createExpressionNode(localTime(12), text(34)), 12L - 34L);
    }

    // toLong....................................................................................................

    @Test
    public void testEvaluateToLongBigDecimal() {
        this.evaluateAndCheckBigDecimal(this.createExpressionNode(longValue(12), bigDecimal(34)), BigDecimal.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLongBigInteger() {
        this.evaluateAndCheckBigInteger(this.createExpressionNode(longValue(12), bigInteger(34)), BigInteger.valueOf(12L - 34L));
    }

    @Test
    public void testEvaluateToLongDouble() {
        this.evaluateAndCheckDouble(this.createExpressionNode(longValue(12), doubleValue(34)), 12L - 34.0);
    }

    @Test
    public void testEvaluateToLongLocalDate() {
        this.evaluateAndCheckLong(this.createExpressionNode(longValue(12), localDate(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLongLocalDateTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(longValue(12), localDateTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLongLocalTime() {
        this.evaluateAndCheckLong(this.createExpressionNode(longValue(12), localTime(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLongLong() {
        this.evaluateAndCheckLong(this.createExpressionNode(longValue(12), longValue(34)), 12L - 34L);
    }

    @Test
    public void testEvaluateToLongText() {
        this.evaluateAndCheckLong(this.createExpressionNode(longValue(12), text(34)), 12L - 34L);
    }

    // toNumber.....................................................................................

    @Test
    public void testEvaluateToNumberBigDecimal() {
        this.evaluateAndCheckNumberBigDecimal(this.createExpressionNode(bigDecimal(12), bigDecimal(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberBigInteger() {
        this.evaluateAndCheckNumberBigInteger(this.createExpressionNode(bigInteger(12), bigInteger(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberDouble() {
        this.evaluateAndCheckNumberDouble(this.createExpressionNode(doubleValue(12), doubleValue(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberLocalDate() {
        this.evaluateAndCheckNumberBigDecimal(this.createExpressionNode(longValue(12), localDate(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberLocalDateTime() {
        this.evaluateAndCheckNumberBigDecimal(this.createExpressionNode(longValue(12), localDateTime(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberLocalTime() {
        this.evaluateAndCheckNumberBigDecimal(this.createExpressionNode(longValue(12), localTime(34)), 12 - 34);
    }

    @Test
    public void testEvaluateToNumberLong() {
        this.evaluateAndCheckNumberLong(this.createExpressionNode(longValue(12), longValue(34)), 12 - 34);
    }

    @Override
    ExpressionSubtractionNode createExpressionNode(final ExpressionNode left, final ExpressionNode right) {
        return ExpressionSubtractionNode.with(left, right);
    }

    @Override
    String expectedToString() {
        return LEFT_TO_STRING + "-" + RIGHT_TO_STRING;
    }

    @Override
    Class<ExpressionSubtractionNode> expressionNodeType() {
        return ExpressionSubtractionNode.class;
    }

    // HasJsonNodeTesting...........................................................................................

    @Override
    public ExpressionSubtractionNode fromJsonNode(final JsonNode from) {
        return ExpressionSubtractionNode.fromJsonNode(from);
    }
}
