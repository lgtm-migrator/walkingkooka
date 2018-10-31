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

public final class ExpressionEqualsNodeTest extends ExpressionComparisonBinaryNodeTestCase<ExpressionEqualsNode> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<ExpressionNode> visited = Lists.array();

        final ExpressionEqualsNode equals = this.createExpressionNode();
        final ExpressionNode text1 = equals.children().get(0);
        final ExpressionNode text2 = equals.children().get(1);

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
            protected Visiting startVisit(final ExpressionEqualsNode t) {
                assertSame(equals, t);
                b.append("3");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ExpressionEqualsNode t) {
                assertSame(equals, t);
                b.append("4");
                visited.add(t);
            }

            @Override
            protected void visit(final ExpressionTextNode t) {
                b.append("5");
                visited.add(t);
            }
        }.accept(equals);
        assertEquals("1315215242", b.toString());
        assertEquals("visited",
                Lists.of(equals, equals,
                        text1, text1, text1,
                        text2, text2, text2,
                        equals, equals),
                visited);
    }
    
    // BigDecimal.................................................................................................

    @Test
    public void testEvaluateToBooleanBigDecimalBigDecimal() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigDecimal2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigInteger() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigInteger(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalBigInteger2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalDouble() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), doubleValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalDouble2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalLong() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), longValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalLong2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalText() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), text(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigDecimalText2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigDecimal(12), text(12)), true);
    }
    
    // BigInteger................................................................................................

    @Test
    public void testEvaluateToBooleanBigIntegerBigDecimal() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigDecimal(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigDecimal2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigInteger() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigInteger(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerBigInteger2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerDouble() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), doubleValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerDouble2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerLong() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), longValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerLong2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerText() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), text(34)), false);
    }

    @Test
    public void testEvaluateToBooleanBigIntegerText2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(bigInteger(12), text(12)), true);
    }

    // Double ................................................................................................

    @Test
    public void testEvaluateToBooleanDoubleBigDecimal() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigDecimal(34)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigDecimal2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigInteger() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigInteger(34)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleBigInteger2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleDouble() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), doubleValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleDouble2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleLong() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), longValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleLong2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanDoubleText() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), text(34)), false);
    }

    @Test
    public void testEvaluateToBooleanDoubleText2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(doubleValue(12), text(12)), true);
    }

    // Long................................................................................................

    @Test
    public void testEvaluateToBooleanLongBigDecimal() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigDecimal(34)), false);
    }

    @Test
    public void testEvaluateToBooleanLongBigDecimal2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigDecimal(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongBigInteger() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigInteger(34)), false);
    }

    @Test
    public void testEvaluateToBooleanLongBigInteger2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), bigInteger(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongDouble() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), doubleValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanLongDouble2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), doubleValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongLong() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), longValue(34)), false);
    }

    @Test
    public void testEvaluateToBooleanLongLong2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), longValue(12)), true);
    }

    @Test
    public void testEvaluateToBooleanLongText() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), text(34)), false);
    }

    @Test
    public void testEvaluateToBooleanLongText2() {
        // left eq right == truthy number
        this.evaluateAndCheckBoolean(this.createExpressionNode(longValue(12), text(12)), true);
    }
    
    @Override
    ExpressionEqualsNode createExpressionNode(final ExpressionNode left, final ExpressionNode right) {
        return ExpressionEqualsNode.with(left, right);
    }

    @Override
    String expectedToString(){
        return LEFT_TO_STRING + "=" + RIGHT_TO_STRING;
    }

    @Override
    Class<ExpressionEqualsNode> expressionNodeType() {
        return ExpressionEqualsNode.class;
    }
}
