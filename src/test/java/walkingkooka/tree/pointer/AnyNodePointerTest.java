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
import walkingkooka.tree.visit.Visiting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class AnyNodePointerTest extends NodePointerTestCase2<AnyNodePointer<JsonNode, JsonNodeName>> {

    @Test
    public void testNextAppend() {
        this.nextAndCheck(this.createNodePointer().append(), null);
    }

    @Test
    public void testAddFails() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.createNodePointer().add(JsonNode.object(), JsonNode.string("add"));
        });
    }

    @Test
    public void testRemoveFails() {
        assertThrows(UnsupportedOperationException.class, () -> {
            this.createNodePointer().remove(JsonNode.object());
        });
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(AnyNodePointer.get(), "");
    }

    @Test
    public void testVisitor() {
        final StringBuilder b = new StringBuilder();

        new FakeNodePointerVisitor<JsonNode, JsonNodeName>() {
            @Override
            protected Visiting startVisit(final NodePointer<JsonNode, JsonNodeName> node) {
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final NodePointer<JsonNode, JsonNodeName> node) {
                b.append("2");
            }

            @Override
            protected void visit(final AnyNodePointer<JsonNode, JsonNodeName> node) {
                b.append("3");
            }
        }.accept(this.createNodePointer());

        assertEquals("132", b.toString());
    }

    @Override
    AnyNodePointer<JsonNode, JsonNodeName> createNodePointer() {
        return AnyNodePointer.get();
    }

    @Override
    public Class<AnyNodePointer<JsonNode, JsonNodeName>> type() {
        return Cast.to(AnyNodePointer.class);
    }
}
