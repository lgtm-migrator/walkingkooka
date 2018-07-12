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

import walkingkooka.naming.Name;
import walkingkooka.naming.PathSeparator;
import walkingkooka.tree.Node;

import java.util.Objects;
import java.util.Set;


/**
 * A {@link NodeSelector} that selects all the descendants of a given {@link Node} until all are visited.
 */
final class DescendantNodeSelector<N extends Node<N, NAME, ANAME, AVALUE>, NAME extends Name, ANAME extends Name, AVALUE>
        extends
        UnaryNodeSelector2<N, NAME, ANAME, AVALUE> {

    /**
     * Type safe {@link DescendantNodeSelector} getter
     */
    static <N extends Node<N, NAME, ANAME, AVALUE>, NAME extends Name, ANAME extends Name, AVALUE> DescendantNodeSelector<N, NAME, ANAME, AVALUE> with(final PathSeparator separator) {
        Objects.requireNonNull(separator, "separator");
        return new DescendantNodeSelector(separator);
    }

    /**
     * Private constructor use type safe getter
     */
    private DescendantNodeSelector(final PathSeparator separator) {
        super();
        this.separator = separator;
    }

    /**
     * Private constructor
     */
    private DescendantNodeSelector(final PathSeparator separator, final NodeSelector<N, NAME, ANAME, AVALUE> selector) {
        super(selector);
        this.separator = separator;
    }

    // NodeSelector

    NodeSelector<N, NAME, ANAME, AVALUE> append1(final NodeSelector<N, NAME, ANAME, AVALUE> selector) {
        // no point appending a descending to another...
        return selector instanceof DescendantNodeSelector ?
                this :
                new DescendantNodeSelector(this.separator, selector);
    }

    @Override
    public Set<N> accept(final N node) {
        return this.accept0(node.root());
    }

    @Override
    final void accept(final N node, final NodeSelectorContext<N, NAME, ANAME, AVALUE> context) {
        this.matchChildren(node, context);
    }

    @Override
    void match(final N node, final NodeSelectorContext<N, NAME, ANAME, AVALUE> context) {
        super.match(node, context);
        this.matchChildren(node, context);
    }

    @Override
    void toString0(final StringBuilder b, String separator) {
        b.append(this.separator).append(this.separator);
        this.toStringNext(b, "");
    }

    // ignore in hashcode / equals...
    private final PathSeparator separator;

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof DescendantNodeSelector;
    }
}
