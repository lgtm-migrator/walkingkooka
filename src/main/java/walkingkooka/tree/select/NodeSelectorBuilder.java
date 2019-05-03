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
 */

package walkingkooka.tree.select;

import walkingkooka.build.Builder;
import walkingkooka.build.BuilderException;
import walkingkooka.collect.list.Lists;
import walkingkooka.naming.Name;
import walkingkooka.tree.Node;
import walkingkooka.tree.expression.ExpressionNode;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Build that supports complex assembly / concatenation of one or more selectors.
 */
public final class NodeSelectorBuilder<N extends Node<N, NAME, ANAME, AVALUE>, NAME extends Name, ANAME extends Name, AVALUE> implements Builder<NodeSelector<N, NAME, ANAME, AVALUE>> {

    /**
     * Creates an select that begins its search from the root of the graph.
     */
    public static <N extends Node<N, NAME, ANAME, AVALUE>,
            NAME extends Name,
            ANAME extends Name,
            AVALUE> NodeSelectorBuilder<N, NAME, ANAME, AVALUE> absolute(final Class<N> node) {
        check(node);
        return new NodeSelectorBuilder<N, NAME, ANAME, AVALUE>().append(AbsoluteNodeSelector.get());
    }

    /**
     * Creates a select that begins its search with the given node.
     */
    public static <N extends Node<N, NAME, ANAME, AVALUE>,
            NAME extends Name,
            ANAME extends Name,
            AVALUE> NodeSelectorBuilder<N, NAME, ANAME, AVALUE> relative(final Class<N> node) {
        check(node);
        return new NodeSelectorBuilder<>();
    }

    /**
     * Checks the parameters are not null.
     */
    private static void check(final Class<?> node) {
        Objects.requireNonNull(node, "node");
    }

    /**
     * Private ctor use factory.
     */
    private NodeSelectorBuilder() {
        super();
    }

    /**
     * {@see AncestorNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> ancestor() {
        return this.append(AncestorNodeSelector.get());
    }

    /**
     * {@see AncestorOrSelfNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> ancestorOrSelf() {
        return this.append(AncestorOrSelfNodeSelector.get());
    }

    /**
     * {@see AndNodeSelector}
     */
    @SafeVarargs
    public final NodeSelectorBuilder<N, NAME, ANAME, AVALUE> and(final NodeSelector<N, NAME, ANAME, AVALUE>... selectors) {
        return this.append(AndNodeSelector.with(Lists.of(selectors)));
    }

    /**
     * {@see NodeAttributeValueContainsPredicate}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> attributeValueContains(final ANAME name, final AVALUE value) {
        return this.nodePredicate(NodeAttributeValuePredicate.contains(name, value));
    }

    /**
     * {@see NodeAttributeValueEndsWithPredicate}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> attributeValueEndsWith(final ANAME name, final AVALUE value) {
        return this.nodePredicate(NodeAttributeValuePredicate.endsWith(name, value));
    }

    /**
     * {@see NodeAttributeValueEqualsPredicate}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> attributeValueEquals(final ANAME name, final AVALUE value) {
        return this.nodePredicate(NodeAttributeValuePredicate.equalsPredicate(name, value));
    }

    /**
     * {@see NodeAttributeValueStartsWithPredicate}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> attributeValueStartsWith(final ANAME name, final AVALUE value) {
        return this.nodePredicate(NodeAttributeValuePredicate.startsWith(name, value));
    }

    /**
     * {@see ExpressionNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> nodePredicate(final Predicate<N> predicate) {
        return this.append(NodeSelector.nodePredicate(predicate));
    }

    /**
     * {@see ChildrenNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> children() {
        return this.append(ChildrenNodeSelector.get());
    }

    /**
     * {@see DescendantNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> descendant() {
        return this.append(DescendantNodeSelector.get());
    }

    /**
     * {@see DescendantOrSelfNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> descendantOrSelf() {
        return this.append(DescendantOrSelfNodeSelector.get());
    }

    /**
     * {@see ExpressionNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> expression(final ExpressionNode expression) {
        return this.append(ExpressionNodeSelector.with(expression));
    }

    /**
     * {@see FirstChildNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> firstChild() {
        return this.append(FirstChildNodeSelector.get());
    }

    /**
     * {@see FollowingNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> following() {
        return this.append(FollowingNodeSelector.get());
    }

    /**
     * {@see FollowingSiblingNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> followingSibling() {
        return this.append(FollowingSiblingNodeSelector.get());
    }

    /**
     * {@see LastChildNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> lastChild() {
        return this.append(LastChildNodeSelector.get());
    }

    /**
     * {@see NamedNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> named(final NAME name) {
        return this.append(NamedNodeSelector.with(name));
    }

    /**
     * {@see OrNodeSelector}
     */
    @SafeVarargs
    public final NodeSelectorBuilder<N, NAME, ANAME, AVALUE> or(final NodeSelector<N, NAME, ANAME, AVALUE>... selectors) {
        return this.append(OrNodeSelector.with(Lists.of(selectors)));
    }

    /**
     * {@see ParentNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> parent() {
        return this.append(ParentNodeSelector.get());
    }

    /**
     * {@see PrecedingNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> preceding() {
        return this.append(PrecedingNodeSelector.get());
    }

    /**
     * {@see PrecedingSiblingNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> precedingSibling() {
        return this.append(PrecedingSiblingNodeSelector.get());
    }

    /**
     * {@see SelfNodeSelector}
     */
    public NodeSelectorBuilder<N, NAME, ANAME, AVALUE> self() {
        return this.append(SelfNodeSelector.get());
    }

    // called by NodeSelectorNodeSelectorParserTokenVisitor
    NodeSelectorBuilder<N, NAME, ANAME, AVALUE> append(final NodeSelector<N, NAME, ANAME, AVALUE> selector) {
        this.selector = null != this.selector ? this.selector.append(selector) : selector;
        return this;
    }

    private NodeSelector<N, NAME, ANAME, AVALUE> selector;

    /**
     * Returns a {@link NodeSelector} ready for use, throwing an exception if no selectors have been added.
     */
    @Override
    public NodeSelector<N, NAME, ANAME, AVALUE> build() throws BuilderException {
        if (null == this.selector) {
            throw new BuilderException("Builder is empty!");
        }
        return this.selector;
    }

    @Override
    public String toString() {
        return String.valueOf(this.selector);
    }
}
