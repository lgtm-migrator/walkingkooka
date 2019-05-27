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

import walkingkooka.Cast;
import walkingkooka.collect.map.Maps;
import walkingkooka.color.Color;
import walkingkooka.naming.Name;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeName;

import java.util.Map;
import java.util.Objects;

/**
 * The name of an expression node.
 */
public final class TextPropertyName<T> implements Name,
        Comparable<TextPropertyName<?>> {

    /**
     * First because required by {@link #CONSTANTS} init.
     */
    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;

    // constants

    /**
     * A read only cache of already properties.
     */
    final static Map<String, TextPropertyName<?>> CONSTANTS = Maps.sorted(TextPropertyName.CASE_SENSITIVITY.comparator());

    /**
     * Creates and adds a new {@link TextPropertyName} to the cache being built that handles {@link Color} values.
     */
    private static TextPropertyName<Color> registerColorConstant(final String property) {
        return registerConstant(property, TextPropertyValueConverter.color());
    }

    /**
     * Creates and adds a new {@link TextPropertyName} to the cache being built that handles {@link String} values.
     */
    private static TextPropertyName<String> registerStringConstant(final String property) {
        return registerConstant(property, TextPropertyValueConverter.string());
    }

    /**
     * Creates and adds a new {@link TextPropertyName} to the cache being built.
     */
    private static <T> TextPropertyName<T> registerConstant(final String property,
                                                            final TextPropertyValueConverter<T> converter) {
        final TextPropertyName<T> propertyName = new TextPropertyName<>(property, converter);
        TextPropertyName.CONSTANTS.put(property, propertyName);
        return propertyName;
    }

    /**
     * Background color
     */
    public final static TextPropertyName<Color> BACKGROUND_COLOR = registerColorConstant("background-color");

    /**
     * Text color
     */
    public final static TextPropertyName<Color> TEXT_COLOR = registerColorConstant("text-color");

    /**
     * Factory that retrieves an existing property or if unknown a property that assumes non empty string value.
     */
    public static TextPropertyName<?> with(final String name) {
        Objects.requireNonNull(name, "name");

        final TextPropertyName<?> httpHeaderName = CONSTANTS.get(name);
        return null != httpHeaderName ?
                httpHeaderName :
                new TextPropertyName<>(checkName(name), TextPropertyValueConverter.string());
    }

    private static String checkName(final String name) {
        CharPredicates.failIfNullOrEmptyOrInitialAndPartFalse(name,
                "name",
                INITIAL,
                PART);
        return name;
    }

    private final static CharPredicate INITIAL = CharPredicates.letter();
    private final static CharPredicate PART = CharPredicates.letterOrDigit().or(CharPredicates.any("-"));

    // @VisibleForTesting
    private TextPropertyName(final String name,
                             final TextPropertyValueConverter<T> converter) {
        super();
        this.name = name;
        this.converter= converter;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    /**
     * Used to convert/validate property values.
     */
    final TextPropertyValueConverter<T> converter;

    /**
     * Returns the name in quotes, useful for error messages.
     */
    CharSequence inQuotes() {
        return CharSequences.quoteAndEscape(this.name);
    }

    // HasJsonNode.....................................................................................................

    static TextPropertyName<?> fromJsonNodeName(final JsonNode node) {
        return TextPropertyName.with(node.name().value());
    }

    JsonNodeName toJsonNodeName() {
        return JsonNodeName.with(this.name);
    }

    // Object..........................................................................................................

    @Override
    public final int hashCode() {
        return CASE_SENSITIVITY.hash(this.name);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof TextPropertyName &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final TextPropertyName other) {
        return CASE_SENSITIVITY.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Comparable ......................................................................................................

    @Override
    public int compareTo(final TextPropertyName other) {
        return CASE_SENSITIVITY.comparator().compare(this.name, other.name);
    }

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }
}