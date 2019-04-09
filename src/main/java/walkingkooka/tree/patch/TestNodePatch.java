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
import walkingkooka.util.Optionals;

/**
 * Represents a test node-patch operation.
 */
final class TestNodePatch<N extends Node<N, ?, ?, ?>> extends AddReplaceOrTestNodePatch<N> {

    static <N extends Node<N, ?, ?, ?>> TestNodePatch<N> with(final NodePointer<N, ?> path,
                                                              final N value) {
        checkPath(path);
        checkValue(value);

        return new TestNodePatch<>(path,
                value.removeParent(),
                null);
    }

    private TestNodePatch(final NodePointer<N, ?> path,
                          final N value,
                          final NodePatch<N> next) {
        super(path, value, next);
    }

    @Override
    NodePatch<N> append0(final NodePatch<N> next) {
        return new TestNodePatch<>(this.path,
                this.value,
                next);
    }

    /**
     * Find the node at the path and then test its value and then return the original {@link Node}.
     */
    @Override
    final N apply1(final N node, final NodePointer<N, ?> start) {
        Optionals.ifPresentOrElse(this.path.traverse(node),
                this::test,
                this::throwFailed);
        return node;
    }

    private void test(final N node) {
        final N without = node.removeParent();
        if (!this.value.equals(without)) {
            throw new ApplyNodePatchException("Value test failed: " + without, this);
        }
    }

    @Override
    String operation() {
        return "test";
    }
}