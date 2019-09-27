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

package walkingkooka.tree.json.marshall;

import walkingkooka.tree.json.JsonNode;

import java.util.Locale;

/**
 * A {@link BasicJsonMarshaller} that handles {@link Locale}.
 */
final class BasicJsonMarshallerTypedLocale extends BasicJsonMarshallerTyped<Locale> {

    static BasicJsonMarshallerTypedLocale instance() {
        return new BasicJsonMarshallerTypedLocale();
    }

    private BasicJsonMarshallerTypedLocale() {
        super();
    }

    @Override
    void register() {
        this.registerTypeNameAndType();
    }

    @Override
    Class<Locale> type() {
        return Locale.class;
    }

    @Override
    String typeName() {
        return "locale";
    }

    @Override
    Locale unmarshallNull(final JsonNodeUnmarshallContext context) {
        return null;
    }

    @Override
    Locale unmarshallNonNull(final JsonNode node,
                               final JsonNodeUnmarshallContext context) {
        return Locale.forLanguageTag(node.stringValueOrFail());
    }

    @Override
    JsonNode marshallNonNull(final Locale value,
                               final JsonNodeMarshallContext context) {
        return JsonNode.string(value.toLanguageTag());
    }
}
