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

import static org.junit.Assert.fail;

public abstract class ExpressionArithmeticBinaryNodeTestCase2<N extends ExpressionArithmeticBinaryNode> extends ExpressionArithmeticBinaryNodeTestCase<N> {

    // toText.....................................................................................

    @Test
    public final void testEvaluateToTextBigDecimalFail() {
        this.evaluate(this.createExpressionNode(text(12), bigDecimal(34)));
    }

    @Test
    public final void testEvaluateToTextBigIntegerFail() {
        this.evaluate(this.createExpressionNode(text(12), bigInteger(34)));
    }

    @Test
    public final void testEvaluateToTextDoubleFail() {
        this.evaluate(this.createExpressionNode(text(12), doubleValue(34)));
    }

    @Test
    public final void testEvaluateToTextLocalDateFail() {
        this.evaluate(this.createExpressionNode(text(12), localDate(34)));
    }

    @Test
    public final void testEvaluateToTextLocalDateTimeFail() {
        this.evaluate(this.createExpressionNode(text(12), localDateTime(34)));
    }

    @Test
    public final void testEvaluateToTextLocalTimeFail() {
        this.evaluate(this.createExpressionNode(text(12), localTime(34)));
    }

    @Test
    public final void testEvaluateToTextLongFail() {
        this.evaluate(this.createExpressionNode(text(12), longValue(34)));
    }

    @Test
    public final void testEvaluateToTextTextFail() {
        this.evaluate(this.createExpressionNode(text(12), text(34)));
    }

    private void evaluate(final ExpressionNode node) {
        try {
            node.toValue(this.context());
            fail("Evaluating " + node + " should have failed");
        } catch (final UnsupportedOperationException expected) {
            // expected
        }
    }
}