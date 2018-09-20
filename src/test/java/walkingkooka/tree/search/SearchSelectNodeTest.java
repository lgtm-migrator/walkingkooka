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

package walkingkooka.tree.search;

import org.junit.Ignore;
import org.junit.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.visit.Visiting;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public final class SearchSelectNodeTest extends SearchParentNodeTestCase<SearchSelectNode> {

    @Test(expected = NullPointerException.class)
    public void testWithNullFails() {
        SearchSelectNode.with(null);
    }

    @Test
    public void testWithDoesntWrapIgnored() {
        final SearchSelectNode node = this.createSearchNode();
        assertSame(node, SearchSelectNode.with(node));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetChildrenIncorrectCountFails() {
        this.createSearchNode().setChildren(Lists.of(this.text("child-1"), this.text("child-2")));
    }

    @Test
    public void testSetDifferentChildren() {
        this.setChildrenDifferent(Lists.of(this.text("different")));
    }

    @Test
    public void testSetChildrenWithSameSelect() {
        final SearchSelectNode node = this.createSearchNode();
        assertSame(node, node.setChildren(Lists.of(node.child().selected())));
    }

    @Test
    public void testSetChildrenWithDifferentSelectedChild() {
        final SearchSelectNode node = this.createSearchNode();
        final SearchNode child = this.differentSearchNode();
        assertEquals(child.selected(), node.setChildren(Lists.of(child.selected())));
    }

    @Test
    public final void testReplaceAll() {
        final SearchSelectNode node = this.createSearchNode();
        final SearchNode replace = this.replaceNode();
        assertEquals(replace.selected(), node.replace(0, node.text().length(), replace));
    }

    @Test
    public void testReplace() {
        final SearchSelectNode node = SearchSelectNode.with(this.text("123"));
        final SearchTextNode replacing = this.text("REPLACEMENT");
        final SearchNode replaced = node.replace(1, 2, replacing);
        assertEquals(this.sequence(this.text("1"), replacing, this.text("3")).selected(), replaced);
    }

    @Test
    @Ignore
    public void testReplaceChild() {
        throw new UnsupportedOperationException();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAppendChild() {
        this.createSearchNode().appendChild(this.text("append-fails"));
    }

    @Test
    @Ignore
    public void testAppendChild2() {
        throw new UnsupportedOperationException();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemoveChildFirst() {
        this.createSearchNode().removeChild(0);
    }

    @Test
    @Ignore
    public void testRemoveChildLast() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testText() {
        assertEquals(this.child().text(), this.createSearchNode().text());
    }

    @Test
    public void testIgnored() {
        final SearchNode node = this.createSearchNode();
        assertNotSame(node, node.ignored());
    }

    @Test
    public void testSelected() {
        final SearchNode node = this.createSearchNode();
        assertSame(node, node.selected());
    }

    @Test
    public void testReplaceSelectedNotReplaced() {
        final SearchNode node = this.text("ignored")
                .selected();

        this.replaceSelectedNothingAndCheck(node);
    }

    @Test
    public void testReplaceSelectedNothingReplaced() {
        final SearchNode node = this.text("not-replaced")
                .selected();
        this.replaceSelectedNothingAndCheck(node);
    }

    @Test
    public void testReplaceSelected() {
        final SearchNode node = this.text("will-be-replaced")
                .selected();
        final SearchNode replaced = this.text("replaced");

        this.replaceSelectedAndCheck(node,
                (n) -> replaced,
                replaced);
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final SearchSelectNode node = this.createSearchNode();
        final SearchTextNode child = node.children().get(0).cast();

        new FakeSearchNodeVisitor() {
            @Override
            protected Visiting startVisit(final SearchNode n) {
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final SearchNode n) {
                b.append("2");
            }

            @Override
            protected Visiting startVisit(final SearchSelectNode n) {
                assertSame(node, n);
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final SearchSelectNode n) {
                assertSame(node, n);
                b.append("4");
            }

            @Override
            protected void visit(final SearchTextNode n) {
                assertSame(child, n);
                b.append("5");
            }
        }.accept(node);

        assertEquals("1315242", b.toString());
    }

    @Test
    public void testToString() {
        assertEquals("< \"child\" >", this.createSearchNode().toString());
    }

    @Override
    SearchSelectNode createSearchNode() {
        return SearchSelectNode.with(this.child());
    }

    @Override
    List<SearchNode> children() {
        return Lists.of(this.child());
    }

    private SearchNode child() {
        return this.text("child");
    }

    @Override
    Class<SearchSelectNode> searchNodeType() {
        return SearchSelectNode.class;
    }
}