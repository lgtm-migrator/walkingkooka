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

package walkingkooka.tree.pojo;

import java.util.Arrays;

/**
 * Base class for all children lists of a fixed size such as arrays and object.
 */
abstract class PojoNodeFixedChildrenList<P extends PojoNode2> extends PojoNodeChildrenList<P> {

    PojoNodeFixedChildrenList(final P parent) {
        super(parent);
        this.nodes = new PojoNode[parent.childrenCount()];
    }

    /**
     * Supports caching pojo wrappers for individual elements.
     */
    @Override
    public final PojoNode get(final int index) {
        PojoNode node = this.nodes[index];
        if(null==node){
            node = this.wrap(index);
            this.nodes[index] = node;
        }
        return node;
    }

    @Override
    final void clearChildrenNodeCache() {
        Arrays.fill(this.nodes, null);
    }

    /**
     * Lazily wrapped pojo nodes.
     */
    private final PojoNode[] nodes;
}
