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

package walkingkooka.tree.json;

import org.junit.Ignore;
import org.junit.Test;
import walkingkooka.collect.list.Lists;

import static org.junit.Assert.assertNotSame;

public abstract class JsonLeafNodeTestCase<N extends JsonLeafNode<V>, V> extends JsonNodeTestCase<N> {

    @Test
    public final void testCreate() {
        final N node = this.createJsonNode();
        assertEquals("children", Lists.empty(), node.children());
        this.checkWithoutParent(node);
        this.checkValue(node, this.value());
    }

    @Test
    public final void testSetNameDifferent() {
        final N node = this.createJsonNode();
        final JsonNodeName originalName = node.name();
        final V value = node.value();

        final JsonNodeName differentName = JsonNodeName.with("different");
        final N different = node.setName(differentName).cast();
        assertEquals("name", differentName, different.name());
        this.checkValue(different, value);

        assertEquals("original name", originalName, node.name());
        this.checkValue(node, value);
    }

    @Test
    public final void testSetSameValue() {
        final N node = this.createJsonNode();
        assertSame(node, node.setValue0(node.value()));
    }

    @Test
    public void testSetDifferentValue() {
        final N node = this.createJsonNode();

        final V differentValue = this.differentValue();
        final N different = node.setValue0(differentValue).cast();
        assertNotSame(node, different);
        this.checkValue(different, differentValue);
        this.checkWithoutParent(different);

        this.checkValue(node, this.value());
    }

    @Test
    public void testEqualsDifferentValue() {
        assertNotEquals(this.createJsonNode(), this.createJsonNode(this.differentValue()));
    }

    @Test
    @Ignore
    public void testAppendChild() {
        // Ignored
    }

    @Test
    @Ignore
    public void testAppendChild2() {
        // Ignored
    }

    @Test
    @Ignore
    public void testRemoveChildWithoutParent() {
        // Ignored
    }

    @Test
    @Ignore
    public void testRemoveChildDifferentParent() {
        // Ignored
    }

    @Test
    @Ignore
    public void testRemoveChild() {
        // Ignored
    }

    @Test
    @Ignore
    public void testRemoveChildFirst() {
        // Ignored
    }

    @Test
    @Ignore
    public void testRemoveChildLast() {
        // Ignored
    }

    @Test
    @Ignore
    public void testReplaceChildWithoutParent() {
        // Ignored
    }

    @Test
    @Ignore
    public void testReplaceChildDifferentParent() {
        // Ignored
    }

    @Test
    @Ignore
    public void testReplaceChild() {
        // Ignored
    }

    @Test
    @Ignore
    public void testSameChildren() {
        // Ignored
    }

    @Test
    @Ignore
    public void testSetDifferentChildren() {
        // Ignored
    }

    @Test
    @Ignore
    public void testSetSameAttributes() {
        // Ignored
    }

    @Override
    final N createJsonNode() {
        return this.createJsonNode(this.value());
    }

    abstract N createJsonNode(final V value);

    abstract V value();

    abstract V differentValue();

    final void checkValue(final N node, final V value) {
        assertEquals("value", value, node.value());
    }
}
