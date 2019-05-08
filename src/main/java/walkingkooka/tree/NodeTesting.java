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

package walkingkooka.tree;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.naming.Name;
import walkingkooka.test.BeanPropertiesTesting;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.text.cursor.parser.select.NodeSelectorExpressionParserToken;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.NodeSelectorTesting;
import walkingkooka.tree.visit.VisitableTesting;
import walkingkooka.type.MethodAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A mixin interface that contains tests and helpers to assist testing of {@link Node} implementations..
 */
public interface NodeTesting<N extends Node<N, NAME, ANAME, AVALUE>,
        NAME extends Name,
        ANAME extends Name,
        AVALUE>
        extends
        NodeSelectorTesting<N, NAME, ANAME, AVALUE>,
        TraversableTesting<N>,
        VisitableTesting<N>,
        TypeNameTesting<N> {

    @Test
    default void testPublicStaticMethodAbsoluteNodeSelector() throws Exception {
        final Method method = Arrays.stream(this.type().getMethods())
                .filter(MethodAttributes.STATIC::is)
                .filter(m -> m.getReturnType() == NodeSelector.class)
                .filter(m -> m.getName().equals("absoluteNodeSelector"))
                .filter(m -> m.getParameterTypes().length == 0)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Unable to find static method NodeSelector absoluteNodeSelector()"));
        assertNotEquals(null, method.invoke(null));
    }

    @Test
    default void testPublicStaticMethodRelativeNodeSelector() throws Exception {
        final Method method = Arrays.stream(this.type().getMethods())
                .filter(MethodAttributes.STATIC::is)
                .filter(m -> m.getReturnType() == NodeSelector.class)
                .filter(m -> m.getName().equals("relativeNodeSelector"))
                .filter(m -> m.getParameterTypes().length == 0)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Unable to find static method NodeSelector relativeNodeSelector()"));
        assertNotEquals(null, method.invoke(null));
    }

    @Test
    default void testPublicStaticMethodNodeSelectorExpressionParserToken() throws Exception {
        final Method method = Arrays.stream(this.type().getMethods())
                .filter(MethodAttributes.STATIC::is)
                .filter(m -> m.getReturnType() == NodeSelector.class)
                .filter(m -> m.getName().equals("nodeSelectorExpressionParserToken"))
                .filter(m -> Arrays.equals(m.getParameterTypes(), new Object[]{NodeSelectorExpressionParserToken.class, Predicate.class}))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Unable to find static method NodeSelector nodeSelectorExpressionParserToken(NodeSelectorExpressionParserToken, Predicate)"));
    }

    @Test
    default void testNameCached() {
        final N node = this.createNode();
        this.checkCached(node, "name", node.name(), node.name());
    }

    @Test
    default void testRemoveParent() {
        final N node = this.createNode();
        this.removeParentAndCheck(node);
    }

    @Test
    default void testParentWithoutChild() {
        final N parent = this.createNode();
        final List<N> children = parent.children();
        assertNotEquals(Lists.empty(), children, "expected at least 1 child");

        this.parentWithoutAndCheck(children.get(0), parent.removeChild(0));
        this.checkWithoutParent(this.createNode());
    }

    @Test
    default void testRootWithoutParent() {
        final N node = this.createNode();
        assertEquals(Optional.empty(), node.parent(), "node must have no parent");
        assertSame(node, node.root());
    }

    @Test
    default void testSetChildIndexInvalidFails() {
        final N node = this.createNode();

        assertThrows(IllegalArgumentException.class, () -> {
            node.setChild(-1, node);
        });
    }

    @Test
    default void testSetChildIndexNullNodeFails() {
        final N node = this.createNode();

        assertThrows(NullPointerException.class, () -> {
            node.setChild(0, null);
        });
    }

    @Test
    default void testSetChildNameNullFails() {
        final N node = this.createNode();

        assertThrows(NullPointerException.class, () -> {
            node.setChild(null, node);
        });
    }

    @Test
    default void testSetChildNameNullNodeFails() {
        final N node = this.createNode();

        assertThrows(NullPointerException.class, () -> {
            node.setChild(node.name(), null);
        });
    }

    @Test
    default void testPropertiesNeverReturnNull() throws Exception {
        BeanPropertiesTesting.allPropertiesNeverReturnNullCheck(this.createNode(),
                (m) -> m.getName().equals("parentOrFail") || m.getName().equals("removeParent"));
    }

    N createNode();

    @Override
    default N createTraversable() {
        return this.createNode();
    }

    default void removeParentAndCheck(final N node) {
        final N without = node.removeParent();
        this.checkWithoutParent(without);

        if (node.isRoot()) {
            assertSame(node, without);
        } else {
            assertNotSame(node, without);
            assertNotEquals(node, without, "node.removeParent result should not be equal to node");
        }
    }

    default void checkWithoutParent(final N node) {
        assertEquals(Optional.empty(), node.parent(), "parent");
        assertEquals(true, node.isRoot(), "root");
        assertEquals(Optional.empty(), node.parentWithout(), () -> "parent without " + node);
    }

    @Test
    default void testSetSameAttributes() {
        final N node = this.createNode();
        assertSame(node, node.setAttributes(node.attributes()));
    }

    default void parentWithoutAndCheck(final N node) {
        assertEquals(Optional.empty(), node.parentWithout(), () -> "node parentWithout " + node);

        assertThrows(NodeException.class, () -> {
            node.parentOrFail();
        });
    }

    default void parentWithoutAndCheck(final N node, final N parentWithout) {
        assertEquals(Optional.of(parentWithout), node.parentWithout(), () -> "node parentWithout " + node);

        assertNotEquals(null, node.parentOrFail(), () -> "parent of node: " + node);
    }

    default void replaceAndCheck(final N node, final N replaceWith) {
        this.replaceAndCheck(node, replaceWith, replaceWith);
    }

    default void replaceAndCheck(final N node, final N replaceWith, final N result) {
        assertEquals(result,
                node.replace(replaceWith),
                () -> node + " replace with " + replaceWith);
        assertEquals(result.pointer(), node.pointer(), () -> "pointer for\n" + node + "\n" + result);
    }

    default N appendChildAndCheck(final N parent, final N child) {
        final N newParent = parent.appendChild(child);
        assertNotSame(newParent, parent, "appendChild must not return the same node");

        final List<N> children = newParent.children();
        assertNotEquals(0, children.size(), "children must have at least 1 child");
        assertEquals(child.name(), children.get(children.size() - 1).name(), "last child must be the added child");

        this.childrenParentCheck(newParent);

        this.checkWithoutParent(child);

        return newParent;
    }

    default N removeChildAndCheck(final N parent, final N child) {
        final N newParent = parent.removeChild(child.index());
        assertNotSame(newParent, parent, "removeChild must not return the same node");

        final List<N> oldChildren = parent.children();
        final List<N> newChildren = newParent.children();
        assertEquals(oldChildren.size(), 1 + newChildren.size(), "new children must have 1 less child than old");

        this.childrenParentCheck(newParent);

        return newParent;
    }

    default N setChildrenAndCheck(final N parent, final N... children) {
        final N newParent = parent.setChildren(Arrays.asList(children));

        this.childrenParentCheck(newParent);
        this.childCountCheck(newParent, children);

        return newParent;
    }

    // TypeNameTesting............................................................................................

    @Override
    default String typeNameSuffix() {
        return Node.class.getSimpleName();
    }
}
