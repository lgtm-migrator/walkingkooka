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

package walkingkooka.tree.select;

import walkingkooka.collect.list.Lists;
import walkingkooka.collect.stack.Stack;
import walkingkooka.collect.stack.Stacks;
import walkingkooka.text.cursor.parser.select.NodeSelectorAndParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorAttributeNameParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorEqualsParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorFunctionNameParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorFunctionParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorGreaterThanEqualsParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorGreaterThanParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorLessThanEqualsParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorLessThanParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorNotEqualsParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorNumberParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorOrParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorParserTokenVisitor;
import walkingkooka.text.cursor.parser.select.NodeSelectorPredicateParserToken;
import walkingkooka.text.cursor.parser.select.NodeSelectorQuotedTextParserToken;
import walkingkooka.tree.expression.ExpressionNode;
import walkingkooka.tree.expression.ExpressionNodeName;
import walkingkooka.tree.expression.ExpressionReference;
import walkingkooka.tree.visit.Visiting;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 * This {@link NodeSelectorParserTokenVisitor} translates a {@link NodeSelectorPredicateParserToken} into a {@link walkingkooka.tree.expression.ExpressionNode} equivalent.
 * A support {@link walkingkooka.tree.expression.ExpressionEvaluationContext} will provide function to definition and attribute to value lookups.
 */
final class ExpressionNodeSelectorNodeSelectorParserTokenVisitor extends NodeSelectorParserTokenVisitor {

    /**
     * Converts the contents of a predicate into a {@link ExpressionNode}.
     */
    static ExpressionNode toExpressionNode(final NodeSelectorPredicateParserToken token,
                                           final Predicate<ExpressionNodeName> functions) {
        final ExpressionNodeSelectorNodeSelectorParserTokenVisitor visitor = new ExpressionNodeSelectorNodeSelectorParserTokenVisitor(functions);
        token.accept(visitor);

        final List<ExpressionNode> nodes = visitor.children;
        final int count = nodes.size();
        if (1 != count) {
            throw new IllegalArgumentException("Expected either 0 or 1 ExpressionNodes but got " + count + "=" + nodes);
        }

        return nodes.get(0);
    }

    /**
     * Private ctor use static method.
     */
    // @VisibleForTesting
    ExpressionNodeSelectorNodeSelectorParserTokenVisitor(final Predicate<ExpressionNodeName> functions) {
        super();
        this.functions = functions;
    }

    @Override
    protected Visiting startVisit(final NodeSelectorAndParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorAndParserToken token) {
        this.exitBinary(ExpressionNode::and, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorEqualsParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorEqualsParserToken token) {
        this.exitBinary(ExpressionNode::equalsNode, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorFunctionParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorFunctionParserToken token) {
        final ExpressionNodeName functionName = ExpressionNodeName.with(token.functionName().value());
        if (!this.functions.test(functionName)) {
            throw new NodeSelectorException("Unknown function " + functionName + " in " + token);
        }

        final ExpressionNode function = ExpressionNode.function(
                functionName,
                this.children.subList(1, this.children.size()));
        this.exit();
        this.add(function, token);
    }

    private final Predicate<ExpressionNodeName> functions;

    @Override
    protected Visiting startVisit(final NodeSelectorGreaterThanParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorGreaterThanParserToken token) {
        this.exitBinary(ExpressionNode::greaterThan, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorGreaterThanEqualsParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorGreaterThanEqualsParserToken token) {
        this.exitBinary(ExpressionNode::greaterThanEquals, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorLessThanParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorLessThanParserToken token) {
        this.exitBinary(ExpressionNode::lessThan, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorLessThanEqualsParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorLessThanEqualsParserToken token) {
        this.exitBinary(ExpressionNode::lessThanEquals, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorNotEqualsParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorNotEqualsParserToken token) {
        this.exitBinary(ExpressionNode::notEquals, token);
    }

    @Override
    protected Visiting startVisit(final NodeSelectorOrParserToken token) {
        this.enter();
        return super.startVisit(token);
    }

    @Override
    protected void endVisit(final NodeSelectorOrParserToken token) {
        this.exitBinary(ExpressionNode::or, token);
    }

    // Leaf................................................................................................

    @Override
    protected void visit(final NodeSelectorAttributeNameParserToken token) {
        this.addReference(token.value(), token);
    }

    @Override
    protected void visit(final NodeSelectorFunctionNameParserToken token) {
        this.addReference(token.value(), token);
    }

    @Override
    protected void visit(final NodeSelectorNumberParserToken token) {
        this.add(ExpressionNode.longNode(token.value()), token);
    }

    @Override
    protected void visit(final NodeSelectorQuotedTextParserToken token) {
        this.add(ExpressionNode.text(token.value()), token);
    }

    // GENERAL PURPOSE .................................................................................................

    private void enter() {
        this.previousChildren = this.previousChildren.push(this.children);
        this.children = Lists.array();
    }

    private void exitBinary(final BiFunction<ExpressionNode, ExpressionNode, ExpressionNode> factory,
                            final NodeSelectorParserToken token) {
        final ExpressionNode left = this.children.get(0);
        final ExpressionNode right = this.children.get(1);
        this.exit();
        this.add(factory.apply(left, right), token);
    }

    private void exit() {
        this.children = this.previousChildren.peek();
        this.previousChildren = this.previousChildren.pop();
    }

    private void addReference(final ExpressionReference reference, final NodeSelectorParserToken token) {
        final ExpressionNode node = ExpressionNode.reference(reference);
        this.add(node, token);
    }

    private void add(final ExpressionNode node, final NodeSelectorParserToken token) {
        if (null == node) {
            throw new NullPointerException("Null node returned for " + token);
        }
        this.children.add(node);
    }

    private Stack<List<ExpressionNode>> previousChildren = Stacks.arrayList();

    /**
     * Aggregates the child {@link ExpressionNode}.
     */
    private List<ExpressionNode> children = Lists.array();

    @Override
    public String toString() {
        return this.children + "," + this.previousChildren;
    }
}
