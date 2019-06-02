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
 * A {@link TextStylePropertyValueHandler} for {@link FontFamilyName} parameter values.
 */
final class FontFamilyNameTextStylePropertyValueHandler extends TextStylePropertyValueHandler<FontFamilyName> {

    /**
     * Singleton
     */
    final static FontFamilyNameTextStylePropertyValueHandler INSTANCE = new FontFamilyNameTextStylePropertyValueHandler();

    /**
     * Private ctor
     */
    private FontFamilyNameTextStylePropertyValueHandler() {
        super();
    }

    @Override
    void check0(final Object value, final TextStylePropertyName<?> name) {
        this.checkType(value, FontFamilyName.class, name);
    }

    // fromJsonNode ....................................................................................................

    @Override
    FontFamilyName fromJsonNode(final JsonNode node) {
        return FontFamilyName.fromJsonNode(node);
    }

    @Override
    JsonNode toJsonNode(final FontFamilyName value) {
        return value.toJsonNode();
    }

    // Object ..........................................................................................................

    @Override
    public String toString() {
        return FontFamilyName.class.getSimpleName();
    }
}