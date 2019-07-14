/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.map.Maps;

import java.util.Map;

public final class HasJsonNodeMapMapperTest extends HasJsonNodeTypedMapperTestCase<HasJsonNodeMapMapper, Map<?, ?>> {

    @Test
    public final void testFromBooleanFails() {
        this.fromJsonNodeFailed(JsonNode.booleanNode(true), null);
    }

    @Test
    public final void testFromNumberFails() {
        this.fromJsonNodeFailed(JsonNode.number(123), null);
    }

    @Test
    public final void testFromStringFails() {
        this.fromJsonNodeFailed(JsonNode.string("abc123"), null);
    }

    @Test
    public final void testFromObjectFails() {
        this.fromJsonNodeFailed(JsonNode.object(), null);
    }

    @Test
    public void testFromEmptyArray() {
        this.fromJsonNodeAndCheck(JsonNode.array(), Maps.empty());
    }

    @Test
    public void testToEmptyMap() {
        this.toJsonNodeWithTypeAndCheck(Maps.empty(), this.typeAndValue(JsonNode.array()));
    }

    @Override
    public void testRoundtripToJsonNodeObjectFromJsonNodeWithType() {
        // ignore
    }

    @Override
    HasJsonNodeMapMapper mapper() {
        return HasJsonNodeMapMapper.instance();
    }

    @Override
    Map<?, ?> value() {
        return Maps.of(Boolean.TRUE, null,
                123.5, "abc123",
                TestHasJsonNode.with("test-has-json-node-a1"), Boolean.FALSE);
    }

    @Override
    JsonNode node() {
        return JsonNode.array()
                .appendChild(entry(JsonNode.booleanNode(Boolean.TRUE), JsonNode.nullNode()))
                .appendChild(entry(JsonNode.number(123.5), JsonNode.string("abc123")))
                .appendChild(entry(TestHasJsonNode.with("test-has-json-node-a1").toJsonNodeWithType(), JsonNode.booleanNode(Boolean.FALSE)));
    }

    private JsonNode entry(final JsonNode key, final JsonNode value) {
        return JsonNode.object()
                .set(HasJsonNodeMapper.ENTRY_KEY, key)
                .set(HasJsonNodeMapper.ENTRY_VALUE, value);
    }

    @Override
    Map<?, ?> jsonNullNode() {
        return null;
    }

    @Override
    String typeName() {
        return "map";
    }

    @Override
    Class<Map<?, ?>> mapperType() {
        return Cast.to(Map.class);
    }

    @Override
    public Class<HasJsonNodeMapMapper> type() {
        return HasJsonNodeMapMapper.class;
    }
}
