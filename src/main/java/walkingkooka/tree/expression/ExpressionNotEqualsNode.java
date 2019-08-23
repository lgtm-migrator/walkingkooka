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
import walkingkooka.tree.json.map.FromJsonNodeContext;
import walkingkooka.visit.Visiting;

import java.util.List;

public final class ExpressionNotEqualsNode extends ExpressionComparisonBinaryNode {

    public final static ExpressionNodeName NAME = ExpressionNodeName.fromClass(ExpressionNotEqualsNode.class);

    public final static String SYMBOL = "!=";

    static ExpressionNotEqualsNode with(final ExpressionNode left, final ExpressionNode right) {
        check(left, right);
        return new ExpressionNotEqualsNode(NO_INDEX, left, right);
    }

    private ExpressionNotEqualsNode(final int index, final ExpressionNode left, final ExpressionNode right) {
        super(index, left, right);
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    @Override
    public ExpressionNotEqualsNode removeParent() {
        return this.removeParent0().cast();
    }

    @Override
    public ExpressionNotEqualsNode setChildren(final List<ExpressionNode> children) {
        return this.setChildren0(children).cast();
    }

    @Override
    ExpressionNotEqualsNode replace1(final int index, final ExpressionNode left, final ExpressionNode right) {
        return new ExpressionNotEqualsNode(index, left, right);
    }

    // is .........................................................................................................

    @Override
    public boolean isEquals() {
        return false;
    }

    @Override
    public boolean isGreaterThan() {
        return false;
    }

    @Override
    public boolean isGreaterThanEquals() {
        return false;
    }

    @Override
    public boolean isLessThan() {
        return false;
    }

    @Override
    public boolean isLessThanEquals() {
        return false;
    }

    @Override
    public boolean isNotEquals() {
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
    boolean isComparisonTrue(final int comparisonResult) {
        return 0 != comparisonResult;
    }

    // JsonNodeContext..................................................................................................

    // @VisibleForTesting
    static ExpressionNotEqualsNode fromJsonNode(final JsonNode node,
                                                final FromJsonNodeContext context) {
        return fromJsonNode0(node,
                ExpressionNotEqualsNode::with,
                context);
    }

    static {
        register(SYMBOL,
                ExpressionNotEqualsNode::fromJsonNode,
                ExpressionNotEqualsNode::toJsonNode,
                ExpressionNotEqualsNode.class);
    }

    // Object .........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof ExpressionNotEqualsNode;
    }

    @Override
    void appendSymbol(final StringBuilder b) {
        b.append(SYMBOL);
    }
}
