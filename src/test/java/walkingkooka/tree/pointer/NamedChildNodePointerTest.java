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

package walkingkooka.tree.pointer;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeName;
import walkingkooka.type.MemberVisibility;

public final class NamedChildNodePointerTest extends NodePointerTestCase<NamedChildNodePointer<JsonNode, JsonNodeName>> {

    @Test
    public void testWithSlash() {
        this.toStringAndCheck(NamedChildNodePointer.with(JsonNodeName.with("slash/")), "/slash~1");
    }

    @Test
    public void testWithTilde() {
        this.toStringAndCheck(NamedChildNodePointer.with(JsonNodeName.with("tilde~")), "/tilde~0");
    }

    @Override
    public Class<NamedChildNodePointer<JsonNode, JsonNodeName>> type() {
        return Cast.to(NamedChildNodePointer.class);
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}