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

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The base {@link SearchNode} for all leaf values that hold a value.
 */
abstract class SearchLeafNode<V> extends SearchNode implements Value<V> {

    static <V> void check(final String text, final V value) {
        Objects.requireNonNull(text, "text");
        Objects.requireNonNull(value, "value");
    }

    SearchLeafNode(final int index, final String text, final V value) {
        super(index);
        this.text = text;
        this.value = value;
    }

    public final String text() {
        return this.text;
    }

    private final String text;

    @Override final void appendText(final StringBuilder b) {
        b.append(this.text);
    }

    @Override
    public final V value() {
        return this.value;
    }

    private final V value;

    final SearchLeafNode<V> setValue0(final V value) {
        return Objects.equals(this.value(), value) ?
                this :
                this.replaceValue(value);
    }

    final SearchLeafNode<V> replaceValue(final V value) {
        final int index = this.index();

        return this.wrap1(index, this.text, value)
                .replaceChild(this.parent(), index)
                .cast();
    }

    final SearchLeafNode wrap0(final int index) {
        return this.wrap1(index, this.text, this.value);
    }

    abstract SearchLeafNode wrap1(final int index, final String text, final V value);

    @Override final SearchNode wrap(final int index) {
        return this.wrap0(index);
    }

    @Override
    SearchNode replaceAll(final SearchNode replace) {
        return this.replaceAll0(replace);
    }

    @Override
    final SearchNode replace0(final int beginOffset, final int endOffset, final SearchNode replace, final String text) {
        final int textLength = text.length();

        return this.replaceAll(0==beginOffset ?
                sequence(Lists.of(replace, text0(text.substring(endOffset)))):
                sequence(
                        endOffset ==textLength?
                                Lists.of(text0(text.substring(0, beginOffset)),
                                        replace): // nothing after
                                Lists.of(
                                        text0(text.substring(0, beginOffset)),
                                        replace,
                                        text0(text.substring(endOffset)))
                )
        );
    }

    @Override
    final SearchNode extract0(final int beginOffset, final int endOffset, final String text) {
        return this.text1(beginOffset, endOffset, text);
    }

    @Override
    public final boolean isIgnored() {
        return false;
    }

    @Override
    public final boolean isMeta() {
        return false;
    }

    @Override
    public final boolean isSelect() {
        return false;
    }

    @Override
    public final boolean isSequence() {
        return false;
    }

    @Override
    public final List<SearchNode> children() {
        return NO_CHILDREN;
    }

    public final SearchNode setChildren(final List<SearchNode> children) {
        Objects.requireNonNull(children, "children");
        throw new UnsupportedOperationException();
    }

    @Override
    final SearchNode setChild(final SearchNode newChild, final int index) {
        throw new UnsupportedOperationException();
    }

    // attributes........................................................................................

    @Override
    public final Map<SearchNodeAttributeName, String> attributes() {
        return Maps.empty();
    }

    @Override
    public final SearchNode setAttributes(final Map<SearchNodeAttributeName, String> attributes) {
        throw new UnsupportedOperationException();
    }

    // Select...........................................................................................

    @Override
    public final SearchIgnoredNode ignored() {
        return SearchNode.ignored(this);
    }

    @Override
    public final SearchMetaNode meta(final Map<SearchNodeAttributeName, String> attributes) {
        return SearchNode.meta(this, attributes);
    }

    @Override
    public final SearchSelectNode selected() {
        return SearchNode.select(this);
    }

    /**
     * By definition {@link SearchLeafNode} never have a child so theres nothing to replace.
     */
    @Override
    final SearchNode replaceSelected0(final Function<SearchSelectNode, SearchNode> replacer) {
        return this;
    }

    // Object....................................................................

    @Override
    public final int hashCode() {
        return this.value.hashCode();
    }

    @Override
    final boolean equalsDescendants0(final SearchNode other) {
        return true;
    }

    @Override
    boolean equalsIgnoringParentAndChildren(final SearchNode other) {
        return other instanceof SearchLeafNode && this.equals1(Cast.to(other));
    }

    private boolean equals1(final SearchLeafNode<?> other) {
        return this.text.equals(other.text) &&
               this.value.equals(other.value);
    }
}