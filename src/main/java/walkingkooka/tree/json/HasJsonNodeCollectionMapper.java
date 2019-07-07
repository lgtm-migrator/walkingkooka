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

import java.util.Collection;
import java.util.stream.Collectors;

abstract class HasJsonNodeCollectionMapper<C extends Collection<?>> extends HasJsonNodeTypedMapper<C> {

    HasJsonNodeCollectionMapper() {
        super();
    }

    @Override
    final C fromJsonNodeNull() {
        return null;
    }

    @Override
    final JsonNode toJsonNodeNonNull(final C value) {
        return JsonObjectNode.array()
                .setChildren(value.stream()
                        .map(HasJsonNodeMapper::toJsonNodeWithTypeObject)
                        .collect(Collectors.toList()));
    }

    final JsonNode toJsonNodeWithTypeCollection0(final C collection) {
        return null == collection ?
                JsonNode.nullNode() :
                this.toJsonNodeNonNull(collection);
    }
}
