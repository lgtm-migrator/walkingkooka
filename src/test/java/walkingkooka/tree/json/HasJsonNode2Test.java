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

package walkingkooka.tree.json;

import org.junit.jupiter.api.Test;
import walkingkooka.test.ClassTesting;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class HasJsonNode2Test implements ClassTesting<HasJsonNode2> {

    @Test
    public void testRegisterNullTypeFails() {
        assertThrows(NullPointerException.class, () -> {
            HasJsonNode2.register(null, JsonNode::fromJsonNode);
        });
    }

    @Test
    public void testRegisterNullFactoryFails() {
        assertThrows(NullPointerException.class, () -> {
            HasJsonNode2.register(JsonNode.class, null);
        });
    }

    @Test
    public void testFromJsonNodeWithTypeNullFails() {
        assertThrows(NullPointerException.class, () -> {
            HasJsonNode2.fromJsonNodeWithType(null);
        });
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<HasJsonNode2> type() {
        return HasJsonNode2.class;
    }
}