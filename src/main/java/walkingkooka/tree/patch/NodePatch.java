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
import walkingkooka.tree.pointer.NodePointerException;

import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link NodePatch} supports operations for a {@link Node} that match the functionality of json-patch with json.<br>
 * <A href="http://jsonpatch.com">jsonpatch.com</A>
 */
public abstract class NodePatch<N extends Node<N, ?, ?, ?>> implements Function<N, N> {

    /**
     * Returns an empty patch.
     */
    public static <N extends Node<N, ?, ?, ?>> EmptyNodePatch<N> empty(final Class<N> type) {
        return EmptyNodePatch.get(type);
    }

    /**
     * Package private to limit sub classing.
     */
    NodePatch() {
        super();
    }

    // public factory methods supporting the builder pattern to build up a patch.........................

    /**
     * Adds an add operation to this patch.
     */
    public NodePatch<N> add(final NodePointer<N, ?> path,
                            final N value) {
        return this.append(AddNodePatch.with(path, value));
    }

    /**
     * Adds a copy operation to this patch.
     */
    public NodePatch<N> copy(final NodePointer<N, ?> from,
                             final NodePointer<N, ?> path) {
        return this.append(CopyNodePatch.with(from, path));
    }

    /**
     * Adds a copy operation to this patch.
     */
    public NodePatch<N> move(final NodePointer<N, ?> from,
                             final NodePointer<N, ?> path) {
        return this.append(MoveNodePatch.with(from, path));
    }

    /**
     * Adds an remove operation to this patch.
     */
    public NodePatch<N> remove(final NodePointer<N, ?> path) {
        return this.append(RemoveNodePatch.with(path));
    }

    /**
     * Adds an add operation to this patch.
     */
    public NodePatch<N> replace(final NodePointer<N, ?> path,
                                final N value) {
        return this.append(ReplaceNodePatch.with(path, value));
    }

    /**
     * Adds a test operation to this patch.
     */
    public NodePatch<N> test(final NodePointer<N, ?> path,
                             final N value) {
        return this.append(TestNodePatch.with(path, value));
    }

    final NodePatch<N> append(final NodePatch<N> patch) {
        final NodePatch<N> next = this.nextOrNull();
        return this.append0(null != next ? next.append(patch) : patch);
    }

    /**
     * Used to concatenate multiple patch components.
     */
    abstract NodePatch<N> append0(final NodePatch<N> patch);

    // Function................................................................................................

    /**
     * Executes this patch accepting the {@link Node} as the base of all pointer paths and operations.
     */
    @Override
    public final N apply(final N node) {
        Objects.requireNonNull(node, "node");

        final NodePointer<N, ?> start = node.pointer();
        NodePatch<N> currentPatch = this;
        N currentNode = node;

        do {
            currentNode = currentPatch.apply0(currentNode, start);
            currentPatch = currentPatch.nextOrNull();
        } while (null != currentPatch);

        return currentNode;
    }

    /**
     * Performs the actual operation. Base is necessary for two step operations such as replace which need to relocate prior to executing the second half.
     */
    abstract N apply0(final N node, final NodePointer<N, ?> start);

    /**
     * Helper which locates the original start {@link Node} or fails.
     */
    final N traverseStartOrFail(final N node, final NodePointer<N, ?> start) {
        return start.traverse(node.root())
                .orElseThrow(() -> new NodePointerException("Unable to navigate to starting node: " + node));
    }

    /**
     * Returns the next component in the patch or null if the end has been reached.
     */
    abstract NodePatch<N> nextOrNull();

    // Object................................................................................................

    @Override
    public abstract String toString();

    /**
     * Sub classes must add their individual toString representation.
     */
    abstract void toString0(final StringBuilder b);
}