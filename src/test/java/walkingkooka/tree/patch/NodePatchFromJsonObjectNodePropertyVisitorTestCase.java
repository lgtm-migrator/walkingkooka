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
import walkingkooka.tree.visit.VisitorTesting;
import walkingkooka.type.MemberVisibility;

public abstract class NodePatchFromJsonObjectNodePropertyVisitorTestCase<V extends NodePatchFromJsonObjectNodePropertyVisitor> extends NodePatchTestCase<V>
        implements VisitorTesting<V, JsonNode> {

    NodePatchFromJsonObjectNodePropertyVisitorTestCase() {
        super();
    }

    @Override
    public final MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public final String typeNameSuffix() {
        return NodePatchFromJsonObjectNodePropertyVisitor.class.getSimpleName();
    }
}