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
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.visit.Visiting;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class ExpressionLessThanEqualsNodeTest extends ExpressionComparisonBinaryNodeTestCase<ExpressionLessThanEqualsNode> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<ExpressionNode> visited = Lists.array();

        final ExpressionLessThanEqualsNode lte = this.createExpressionNode();
        final ExpressionNode text1 = lte.children().get(0);
        final ExpressionNode text2 = lte.children().get(1);

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
            protected Visiting startVisit(final ExpressionLessThanEqualsNode t) {
                assertSame(lte, t);
                b.append("3");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ExpressionLessThanEqualsNode t) {
                assertSame(lte, t);
                b.append("4");
                visited.add(t);
            }

            @Override
            protected void visit(final ExpressionTextNode t) {
                b.append("5");
                visited.add(t);
            }
        }.accept(lte);
        assertEquals("1315215242", b.toString());
        assertEquals("visited",
                Lists.of(lte, lte,
                        text1, text1, text1,
                        text2, text2, text2,
                        lte, lte),
                visited);
    }

    // BigDecimal ................................................................................................

    @Test
    public void testEvaluateToBooleanBigDecimalBigDecimal() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigDecimal2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigDecimal3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigInteger() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigInteger(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigInteger2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigInteger3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigInteger(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalDouble() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), doubleValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalDouble2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalDouble3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), doubleValue(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalLong() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), longValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalLong2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalLong3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), longValue(-99)), false);
    }

    // BigInteger................................................................................................

    @Test
    public void testEvaluateToBooleanBigIntegerBigDecimal() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigDecimal(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigDecimal2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigDecimal3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigDecimal(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigInteger() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigInteger(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigInteger2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigInteger3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigInteger(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerDouble() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), doubleValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerDouble2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerDouble3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), doubleValue(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerLong() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), longValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerLong2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerLong3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), longValue(-99)), false);
    }

    // Double ................................................................................................

    @Test
    public void testEvaluateToBooleanDoubleBigDecimal() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigDecimal(34)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigDecimal2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigDecimal3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigDecimal(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigInteger() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigInteger(34)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigInteger2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigInteger3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigInteger(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleDouble() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), doubleValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleDouble2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleDouble3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), doubleValue(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleLong() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), longValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleLong2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleLong3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), longValue(-99)), false);
    }

    // Long ................................................................................................

    @Test
    public void testEvaluateToBooleanLongBigDecimal() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigDecimal(34)), true);
    }

    @Test
    public void testEvaluateToBooleanLongBigDecimal2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongBigDecimal3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigDecimal(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanLongBigInteger() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigInteger(34)), true);
    }

    @Test
    public void testEvaluateToBooleanLongBigInteger2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongBigInteger3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigInteger(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanLongDouble() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), doubleValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanLongDouble2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongDouble3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), doubleValue(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanLongLong() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), longValue(34)), true);
    }

    @Test
    public void testEvaluateToBooleanLongLong2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongLong3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), longValue(-99)), false);
    }

    @Test
    public void testEvaluateToBooleanLongText() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), text(34)), true);
    }

    @Test
    public void testEvaluateToBooleanLongText2() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), text(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongText3() {
        // left lte right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), text(-99)), false);
    }

    @Override
    ExpressionLessThanEqualsNode createExpressionNode(final ExpressionNode left, final ExpressionNode right) {
        return ExpressionLessThanEqualsNode.with(left, right);
    }

    @Override
    String expectedToString(){
        return LEFT_TO_STRING + "<=" + RIGHT_TO_STRING;
    }

    @Override
    Class<ExpressionLessThanEqualsNode> expressionNodeType() {
        return ExpressionLessThanEqualsNode.class;
    }
}
