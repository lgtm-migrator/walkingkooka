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

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.FromJsonNodeContext;
import walkingkooka.visit.Visiting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * A subtraction expression.
 */
public final class ExpressionSubtractionNode extends ExpressionArithmeticBinaryNode {

    public final static ExpressionNodeName NAME = ExpressionNodeName.fromClass(ExpressionSubtractionNode.class);

    public final static String SYMBOL = "-";

    static ExpressionSubtractionNode with(final ExpressionNode left, final ExpressionNode right) {
        check(left, right);
        return new ExpressionSubtractionNode(NO_INDEX, left, right);
    }

    private ExpressionSubtractionNode(final int index, final ExpressionNode left, final ExpressionNode right) {
        super(index, left, right);
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    @Override
    public ExpressionSubtractionNode removeParent() {
        return this.removeParent0().cast();
    }

    @Override
    public ExpressionSubtractionNode setChildren(final List<ExpressionNode> children) {
        return this.setChildren0(children).cast();
    }

    @Override
    ExpressionSubtractionNode replace1(final int index, final ExpressionNode left, final ExpressionNode right) {
        return new ExpressionSubtractionNode(index, left, right);
    }

    // is .........................................................................................................

    @Override
    public boolean isAddition() {
        return false;
    }

    @Override
    public boolean isDivision() {
        return false;
    }

    @Override
    public boolean isModulo() {
        return false;
    }

    @Override
    public boolean isMultiplication() {
        return false;
    }

    @Override
    public boolean isPower() {
        return false;
    }

    @Override
    public boolean isSubtraction() {
        return true;
    }

    // Visitor .........................................................................................................

    @Override
    public void accept(final ExpressionNodeVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Evaluation .......................................................................................................

    @Override
    String applyText0(final String left, final String right, final ExpressionEvaluationContext context) {
        throw new UnsupportedOperationException(left + SYMBOL + right); // TODO maybe if right exists in left "remove" it, else throw.
    }

    @Override
    BigDecimal applyBigDecimal0(final BigDecimal left, final BigDecimal right, final ExpressionEvaluationContext context) {
        return left.subtract(right, context.mathContext());
    }

    @Override
    BigInteger applyBigInteger0(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context) {
        return left.subtract(right);
    }

    @Override
    double applyDouble0(final double left, final double right, final ExpressionEvaluationContext context) {
        return left - right;
    }

    @Override
    long applyLong0(final long left, final long right, final ExpressionEvaluationContext context) {
        return left - right;
    }

    // JsonNodeContext..................................................................................................

    // @VisibleForTesting
    static ExpressionSubtractionNode fromJsonNode(final JsonNode node,
                                                  final FromJsonNodeContext context) {
        return fromJsonNode0(node,
                ExpressionSubtractionNode::with,
                context);
    }

    static {
        register(SYMBOL,
                ExpressionSubtractionNode::fromJsonNode,
                ExpressionSubtractionNode::toJsonNode,
                ExpressionSubtractionNode.class);
    }

    // Object .........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ExpressionSubtractionNode;
    }

    @Override
    void appendSymbol(final StringBuilder b) {
        b.append(SYMBOL);
    }
}
