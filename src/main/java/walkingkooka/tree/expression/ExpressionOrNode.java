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

import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonArrayNode;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.visit.Visiting;

import java.math.BigInteger;
import java.util.List;

/**
 * A or expression.
 */
public final class ExpressionOrNode extends ExpressionLogicalBinaryNode {

    public final static ExpressionNodeName NAME = ExpressionNodeName.fromClass(ExpressionOrNode.class);

    public final static String SYMBOL = "|";

    static ExpressionOrNode with(final ExpressionNode left, final ExpressionNode right){
        check(left, right);
        return new ExpressionOrNode(NO_INDEX, left, right);
    }

    private ExpressionOrNode(final int index, final ExpressionNode left, final ExpressionNode right){
        super(index, left, right);
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    @Override
    public ExpressionOrNode setChildren(final List<ExpressionNode> children) {
        return this.setChildren0(children).cast();
    }

    @Override
    ExpressionOrNode wrap1(final int index, final ExpressionNode left, final ExpressionNode right) {
        return new ExpressionOrNode(index, left, right);
    }
    // is .........................................................................................................

    @Override
    public boolean isAnd() {
        return false;
    }

    @Override
    public boolean isOr() {
        return true;
    }

    @Override
    public boolean isXor() {
        return false;
    }

    // Visitor .........................................................................................................

    @Override
    public void accept(final ExpressionNodeVisitor visitor){
        if(Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // evaluation .....................................................................................................

    @Override
    ExpressionNode applyBigInteger(final BigInteger left, final BigInteger right, final ExpressionEvaluationContext context) {
        return ExpressionNode.bigInteger(left.or(right));
    }

    @Override
    boolean applyBoolean0(final boolean left, final boolean right) {
        return left | right;
    }

    @Override
    ExpressionNode applyLong(final long left, final long right, final ExpressionEvaluationContext context) {
        return ExpressionNode.longNode(left | right);
    }

    // HasJsonNode....................................................................................................

    // @VisibleForTesting
    static ExpressionOrNode fromJsonNode(final JsonNode node) {
        final JsonArrayNode array = node.arrayOrFail();

        return ExpressionOrNode.with(
                HasJsonNode.fromJsonNodeWithType(array.get(0)),
                HasJsonNode.fromJsonNodeWithType(array.get(1)));
    }

    static {
        register(SYMBOL, ExpressionOrNode::fromJsonNode, ExpressionOrNode.class);
    }

    // Object ........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ExpressionOrNode;
    }

    @Override
    void appendSymbol(final StringBuilder b) {
        b.append(SYMBOL);
    }
}
