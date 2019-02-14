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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

public abstract class ExpressionLeafNodeTestCase<N extends ExpressionLeafNode<V>, V> extends ExpressionNodeTestCase<N> {

    ExpressionLeafNodeTestCase() {
        super();
    }

    @Test
    public final void testCreate() {
        final N node = this.createExpressionNode();
        assertEquals(Lists.empty(), node.children(), "children");
        this.checkWithoutParent(node);
        this.checkValue(node, this.value());
    }

    @Test
    public final void testSetSameValue() {
        final N node = this.createExpressionNode();
        assertSame(node, node.setValue0(node.value()));
    }

    @Test
    public final void testSetDifferentValue() {
        final N node = this.createExpressionNode();

        final V differentValue = this.differentValue();
        final N different = node.setValue0(differentValue).cast();
        assertNotSame(node, different);
        this.checkValue(different, differentValue);
        this.checkWithoutParent(different);

        this.checkValue(node, this.value());
    }

    @Test
    public final void testEqualsDifferentValue() {
        assertNotEquals(this.createExpressionNode(), this.createExpressionNode(this.differentValue()));
    }

    @Override
    public void testAppendChild() {
        // Ignored
    }

    @Override
    public void testAppendChild2() {
        // Ignored
    }

    @Override
    public void testRemoveChildFirst() {
        // Ignored
    }

    @Override
    public void testRemoveChildLast() {
        // Ignored
    }

    @Override
    public void testReplaceChildWithoutParent() {
        // Ignored
    }

    @Override
    public void testReplaceChildDifferentParent() {
        // Ignored
    }

    @Override
    public void testReplaceChild() {
        // Ignored
    }

    @Override
    public void testSetChildrenSame() {
        // Ignored
    }

    @Override
    public void testSetDifferentChildren() {
        // Ignored
    }

    @Override
    final N createExpressionNode() {
        return this.createExpressionNode(this.value());
    }

    abstract N createExpressionNode(final V value);

    abstract V value();

    abstract V differentValue();

    final void checkValue(final N node, final V value) {
        assertEquals(value, node.value(), "value");
    }
}
