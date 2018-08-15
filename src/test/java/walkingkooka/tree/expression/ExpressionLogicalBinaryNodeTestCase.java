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

public abstract class ExpressionLogicalBinaryNodeTestCase<N extends ExpressionLogicalBinaryNode> extends ExpressionBinaryNodeTestCase<N> {

    @Test(expected = ExpressionEvaluationConversionException.class)
    public final void testEvaluateBigDecimalWithFractionFails() {
        this.createExpressionNode(bigDecimal(1.5), bigDecimal(0)).toText(this.context());
    }

    @Test(expected = ExpressionEvaluationConversionException.class)
    public final void testEvaluateDoubleWithFractionFails() {
        this.createExpressionNode(doubleValue(1.5), doubleValue(0)).toText(this.context());
    }
}
