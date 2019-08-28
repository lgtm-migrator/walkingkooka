/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 */

package walkingkooka.tree.expression;

import walkingkooka.Value;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.FromJsonNodeContext;
import walkingkooka.tree.json.marshall.ToJsonNodeContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Base class for any expression with a single parameter.
 */
abstract class ExpressionUnaryNode extends ExpressionParentFixedNode implements Value<ExpressionNode> {

    ExpressionUnaryNode(final int index, final ExpressionNode expression) {
        super(index, Lists.of(expression));
    }

    @Override
    public final ExpressionNode value() {
        return this.children().get(0);
    }

    @Override
    int expectedChildCount() {
        return 1;
    }

    @Override
    final ExpressionUnaryNode replace0(final int index, final List<ExpressionNode> children) {
        return replace1(index, children.get(0));
    }

    abstract ExpressionUnaryNode replace1(final int index, final ExpressionNode expression);

    // is.......................................................................................................

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
    public final boolean isEquals() {
        return false;
    }

    @Override
    public final boolean isFunction() {
        return false;
    }

    @Override
    public final boolean isGreaterThan() {
        return false;
    }

    @Override
    public final boolean isGreaterThanEquals() {
        return false;
    }

    @Override
    public final boolean isLessThan() {
        return false;
    }

    @Override
    public final boolean isLessThanEquals() {
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
    public final boolean isNotEquals() {
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


    // Node........................................................................................................

    @Override
    public ExpressionNode appendChild(final ExpressionNode child) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ExpressionNode removeChild(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Optional<ExpressionNode> firstChild() {
        return Optional.of(this.value());
    }

    @Override
    public final Optional<ExpressionNode> lastChild() {
        return this.firstChild();
    }

    // Visitor........................................................................................................

    final void acceptValues(final ExpressionNodeVisitor visitor) {
        visitor.accept(this.value());
    }

    // Evaluation...................................................................................................

    @Override
    public final LocalDate toLocalDate(final ExpressionEvaluationContext context) {
        return context.convert(this.toNumber(context), LocalDate.class);
    }

    @Override
    public final LocalDateTime toLocalDateTime(final ExpressionEvaluationContext context) {
        return context.convert(this.toNumber(context), LocalDateTime.class);
    }

    @Override
    public final LocalTime toLocalTime(final ExpressionEvaluationContext context) {
        return context.convert(this.toNumber(context), LocalTime.class);
    }

    @Override
    public final Number toValue(final ExpressionEvaluationContext context) {
        return this.toNumber(context);
    }

    // JsonNodeContext..................................................................................................

    static <N extends ExpressionUnaryNode> N fromJsonNode0(final JsonNode node,
                                                           final Function<ExpressionNode, N> factory,
                                                           final FromJsonNodeContext context) {

        return factory.apply(context.fromJsonNodeWithType(node));
    }

    final JsonNode toJsonNode(final ToJsonNodeContext context) {
        return context.toJsonNodeWithType(this.value());
    }
}
