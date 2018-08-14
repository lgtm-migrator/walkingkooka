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

import walkingkooka.Cast;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Base class for all comparison {@link ExpressionBinaryNode} nodes such as LT, GTE etc.
 */
abstract class ExpressionComparisonBinaryNode extends ExpressionBinaryNode {

    ExpressionComparisonBinaryNode(final int index, final ExpressionNode left, final ExpressionNode right){
        super(index, left, right);
    }

    // is .........................................................................................................

    @Override
    public final boolean isAddition() {
        return false;
    }

    @Override
    public final boolean isAnd() {
        return false;
    }

    @Override
    public final boolean isDivision() {
        return false;
    }

    @Override
    public final boolean isModulo() {
        return false;
    }

    @Override
    public final boolean isMultiplication() {
        return false;
    }

    @Override
    public final boolean isOr() {
        return false;
    }

    @Override
    public final boolean isPower() {
        return false;
    }

    @Override
    public final boolean isSubtraction() {
        return false;
    }

    @Override
    public final boolean isXor() {
        return false;
    }

    // evaluation .....................................................................................................

    @Override
    public final Boolean toValue(final ExpressionEvaluationContext context) {
        return this.toBoolean(context);
    }

    /**
     * Comparison operations accept all number types.
     */
    final Class<Number> commonNumberType(final Class<? extends Number> type) {
        return Cast.to(type);
    }

    @Override
    final ExpressionNode applyBigDecimal(final BigDecimal left, final BigDecimal right, final ExpressionEvaluationContext context) {
        return ExpressionNode.booleanNode(this.isComparisonTrue(left.compareTo(right)));
    }

    @Override
    final ExpressionNode applyBigInteger(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context) {
        return ExpressionNode.booleanNode(this.isComparisonTrue(left.compareTo(right)));
    }

    @Override
    final ExpressionNode applyDouble(final double left, final double right, final ExpressionEvaluationContext context) {
        return ExpressionNode.booleanNode(Double.isFinite(left) & Double.isFinite(right) &
                (this.isComparisonTrue(Double.compare(left, right))));
    }

    @Override
    final ExpressionNode applyLong(final long left, final long right, final ExpressionEvaluationContext context) {
        return ExpressionNode.booleanNode(this.isComparisonTrue(Long.compare(left, right)));
    }

    /**
     * Converts the ternary result of a comparison into a boolean for this comparison type.
     */
    abstract boolean isComparisonTrue(final int comparisonResult);
}
