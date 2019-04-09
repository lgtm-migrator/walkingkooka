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

package walkingkooka.tree.patch;

import walkingkooka.tree.Node;
import walkingkooka.tree.pointer.NodePointer;

import java.util.Objects;

/**
 * An empty patch, which also acts at the starting point for building a multi step patch.
 */
final class EmptyNodePatch<N extends Node<N, ?, ?, ?>> extends NodePatch<N> {

    /**
     * Creates an empty patch to begin addition of new operations.
     */
    @SuppressWarnings("unchecked")
    static <N extends Node<N, ?, ?, ?>> EmptyNodePatch<N> get(final Class<N> type) {
        Objects.requireNonNull(type, "type");
        return INSTANCE;
    }

    /**
     * Singleton instance
     */
    @SuppressWarnings("unchecked")
    private final static EmptyNodePatch INSTANCE = new EmptyNodePatch<>();

    /**
     * Private ctor use singleton getter.
     */
    private EmptyNodePatch() {
        super();
    }

    /**
     * Adding a patch to this returns the patch.
     */
    @Override
    NodePatch<N> append0(final NodePatch<N> next) {
        return next;
    }

    /**
     * Do nothing and return the original {@link Node}.
     */
    @Override
    N apply0(final N node, final NodePointer<N, ?> start) {
        return node;
    }

    @Override
    NodePatch<N> nextOrNull() {
        return null; // end of patch.
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    void toString0(final StringBuilder b) {
        throw new UnsupportedOperationException(); // never
    }
}