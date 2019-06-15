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

import walkingkooka.collect.list.Lists;
import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonNode;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Base class for any expression that accepts two parameters.
 */
abstract class ExpressionBinaryNode extends ExpressionParentFixedNode {

    static void check(final ExpressionNode left, final ExpressionNode right) {
        Objects.requireNonNull(left, "left");
        Objects.requireNonNull(right, "right");
    }

    ExpressionBinaryNode(final int index, final ExpressionNode left, final ExpressionNode right) {
        super(index, Lists.of(left, right));
    }

    public ExpressionNode left() {
        return this.children().get(0);
    }

    public ExpressionNode right() {
        return this.children().get(1);
    }

    @Override
    int expectedChildCount() {
        return 2;
    }

    @Override
    final ExpressionBinaryNode replace0(final int index, final List<ExpressionNode> children) {
        return replace1(index, children.get(0), children.get(1));
    }

    abstract ExpressionBinaryNode replace1(final int index, final ExpressionNode left, final ExpressionNode right);

    // is...........................................................................................................

    @Override
    public final boolean isFunction() {
        return false;
    }

    @Override
    public final boolean isNegative() {
        return false;
    }

    @Override
    public final boolean isNot() {
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
        return Optional.of(this.left());
    }

    @Override
    public final Optional<ExpressionNode> lastChild() {
        return Optional.of(this.right());
    }

    // Visitor........................................................................................................

    final void acceptValues(final ExpressionNodeVisitor visitor) {
        visitor.accept(this.left());
        visitor.accept(this.right());
    }

    // Evaluation ...................................................................................................

    @Override
    public final BigDecimal toBigDecimal(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toBigDecimal(context);
    }

    @Override
    public final BigInteger toBigInteger(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toBigInteger(context);
    }

    @Override
    public final boolean toBoolean(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toBoolean(context);
    }

    @Override
    public final double toDouble(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toDouble(context);
    }

    @Override
    public final LocalDate toLocalDate(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toLocalDate(context);
    }

    @Override
    public final LocalDateTime toLocalDateTime(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toLocalDateTime(context);
    }

    @Override
    public final LocalTime toLocalTime(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toLocalTime(context);
    }

    @Override
    public final long toLong(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toLong(context);
    }

    @Override
    public final Number toNumber(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toNumber(context);
    }

    @Override
    public final String toText(final ExpressionEvaluationContext context) {
        return this.apply(this.left(), this.right(), context)
                .toText(context);
    }

    abstract ExpressionNode apply(final ExpressionNode left,
                                  final ExpressionNode right,
                                  final ExpressionEvaluationContext context);

    abstract ExpressionNode applyBigInteger(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context);

    abstract ExpressionNode applyLong(final long left, final long right, final ExpressionEvaluationContext context);

    static boolean isByteShortIntegerLong(final Object value) {
        return value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long;
    }

    static boolean isFloatDouble(final Object value) {
        return value instanceof Float || value instanceof Double;
    }

    // HasJsonNode....................................................................................................

    /**
     * Converts both left and right into a {@link walkingkooka.tree.json.JsonArrayNode}.
     */
    @Override
    public final JsonNode toJsonNode() {
        return HasJsonNode.toJsonNodeWithTypeList(this.children());
    }

    // Object........................................................................................................

    final void toString0(final StringBuilder b) {
        this.left().toString0(b);
        this.appendSymbol(b);
        this.right().toString0(b);
    }

    abstract void appendSymbol(final StringBuilder b);
}
