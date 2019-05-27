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

package walkingkooka.tree.text;

import walkingkooka.tree.json.JsonNode;

/**
 * A {@link TextPropertyValueConverter} for {@link FontSize} parameter values.
 */
final class FontSizeTextPropertyValueConverter extends TextPropertyValueConverter<FontSize> {

    /**
     * Singleton
     */
    final static FontSizeTextPropertyValueConverter INSTANCE = new FontSizeTextPropertyValueConverter();

    /**
     * Private ctor
     */
    private FontSizeTextPropertyValueConverter() {
        super();
    }

    @Override
    void check0(final Object value, final TextPropertyName<?> name) {
        this.checkType(value, FontSize.class, name);
    }

    // fromJsonNode ....................................................................................................

    @Override
    FontSize fromJsonNode(final JsonNode node) {
        return FontSize.fromJsonNode(node);
    }

    @Override
    JsonNode toJsonNode(final FontSize value) {
        return value.toJsonNode();
    }

    // Object ..........................................................................................................

    @Override
    public String toString() {
        return FontSize.class.getSimpleName();
    }
}
