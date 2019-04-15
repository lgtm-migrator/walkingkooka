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

import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonStringNode;

final class RemoveNodePatchNodePatchJsonObjectNodePropertyVisitor extends NodePatchJsonObjectNodePropertyVisitor {

    static RemoveNodePatch<?, ?> remove(final JsonNode patch) {
        final RemoveNodePatchNodePatchJsonObjectNodePropertyVisitor visitor = new RemoveNodePatchNodePatchJsonObjectNodePropertyVisitor(patch);
        visitor.accept(patch);
        return RemoveNodePatch.with(visitor.pathOrFail());
    }

    // VisibleForTesting
    RemoveNodePatchNodePatchJsonObjectNodePropertyVisitor(final JsonNode node) {
        super(node);
    }

    @Override
    void visitFrom(final JsonStringNode from) {
        this.unknownPropertyPresent(NodePatch.FROM_PROPERTY);
    }

    @Override
    void visitValueType(final JsonNode valueType) {
        this.unknownPropertyPresent(NodePatch.VALUE_TYPE_PROPERTY);
    }

    @Override
    void visitValue(final JsonNode value) {
        this.unknownPropertyPresent(NodePatch.VALUE_PROPERTY);
    }
}
