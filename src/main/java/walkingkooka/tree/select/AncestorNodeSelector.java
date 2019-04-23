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

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.tree.Node;

/**
 * A {@link NodeSelector} that selects all the ancestors of a given {@link Node} until the root of the graph is reached.
 */
final class AncestorNodeSelector<N extends Node<N, NAME, ANAME, AVALUE>, NAME extends Name, ANAME extends Name, AVALUE>
        extends
        NonLogicalNodeSelector2<N, NAME, ANAME, AVALUE> {

    /**
     * Type safe {@link AncestorNodeSelector} getter
     */
    static <N extends Node<N, NAME, ANAME, AVALUE>, NAME extends Name, ANAME extends Name, AVALUE> AncestorNodeSelector<N, NAME, ANAME, AVALUE> get() {
        return Cast.to(INSTANCE);
    }

    @SuppressWarnings("unchecked")
    private final static AncestorNodeSelector INSTANCE = new AncestorNodeSelector(NodeSelector.terminal());

    /**
     * Private constructor
     */
    private AncestorNodeSelector(final NodeSelector<N, NAME, ANAME, AVALUE> selector) {
        super(selector);
    }

    // NodeSelector

    NodeSelector<N, NAME, ANAME, AVALUE> append1(final NodeSelector<N, NAME, ANAME, AVALUE> selector) {
        // no point appending a ancestor to another...
        return selector instanceof AncestorNodeSelector ?
                this :
                new AncestorNodeSelector<N, NAME, ANAME, AVALUE>(selector);
    }

    @Override
    final N accept1(final N node, final NodeSelectorContext<N, NAME, ANAME, AVALUE> context) {
        return this.selectParent(node, context);
    }

    @Override
    N select(final N node, final NodeSelectorContext<N, NAME, ANAME, AVALUE> context) {
        final N node2 = super.select(node, context);

        return this.selectParent(node2, context);
    }

    @Override
    void toString1(final NodeSelectorToStringBuilder b) {
        b.axis("ancestor");
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof AncestorNodeSelector;
    }
}
