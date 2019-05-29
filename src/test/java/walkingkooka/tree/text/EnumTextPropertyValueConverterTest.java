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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;

public final class EnumTextPropertyValueConverterTest extends TextPropertyValueConverterTestCase<EnumTextPropertyValueConverter<TextWrapping>, TextWrapping> {

    @Test
    public void testFromJsonNode() {
        final TextWrapping textWrapping = TextWrapping.CLIP;
        this.fromJsonNodeAndCheck(JsonNode.string(textWrapping.name()), textWrapping);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.converter(), TextWrapping.class.getSimpleName());
    }

    @Override
    EnumTextPropertyValueConverter<TextWrapping> converter() {
        return EnumTextPropertyValueConverter.with(TextWrapping::valueOf, TextWrapping.class);
    }

    @Override
    TextPropertyName<TextWrapping> propertyName() {
        return TextPropertyName.TEXT_WRAPPING;
    }

    @Override
    TextWrapping propertyValue() {
        return TextWrapping.CLIP;
    }

    @Override
    String propertyValueType() {
        return TextWrapping.class.getName();
    }

    @Override
    public String typeNamePrefix() {
        return Enum.class.getSimpleName();
    }

    @Override
    public Class<EnumTextPropertyValueConverter<TextWrapping>> type() {
        return Cast.to(EnumTextPropertyValueConverter.class);
    }
}
