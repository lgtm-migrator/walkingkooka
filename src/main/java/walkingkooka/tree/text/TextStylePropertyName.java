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

import walkingkooka.collect.map.Maps;
import walkingkooka.color.Color;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeName;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The name of an expression node.
 */
public final class TextStylePropertyName<T> extends TextNodeNameName<TextStylePropertyName<?>> {

    /**
     * First because required by {@link #CONSTANTS} init.
     */
    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;

    // constants

    /**
     * A read only cache of already style.
     */
    final static Map<String, TextStylePropertyName<?>> CONSTANTS = Maps.sorted(TextStylePropertyName.CASE_SENSITIVITY.comparator());

    /**
     * Creates and adds a new {@link TextStylePropertyName} to the cache being built that handles {@link Color} values.
     */
    private static TextStylePropertyName<Color> registerColorConstant(final String property) {
        return registerConstant(property, TextStylePropertyValueHandler.color());
    }

    /**
     * Creates and adds a new {@link TextStylePropertyName} that handles {@link Enum} values.
     */
    private static <E extends Enum<E>> TextStylePropertyName<E> registerEnumConstant(final String property,
                                                                                     final Function<String, E> factory,
                                                                                     final Class<E> type) {
        return registerConstant(property, TextStylePropertyValueHandler.enumTextPropertyValueHandler(factory, type));
    }

    /**
     * Creates and adds a new {@link TextStylePropertyName} to the cache being built that handles {@link FontFamilyName} values.
     */
    private static TextStylePropertyName<FontFamilyName> registerFontFamilyNameConstant(final String property) {
        return registerConstant(property, TextStylePropertyValueHandler.fontFamilyName());
    }

    /**
     * Creates and adds a new {@link TextStylePropertyName} to the cache being built that handles {@link FontSize} values.
     */
    private static TextStylePropertyName<FontSize> registerFontSizeConstant(final String property) {
        return registerConstant(property, TextStylePropertyValueHandler.fontSize());
    }

    /**
     * Creates and adds a new {@link TextStylePropertyName} to the cache being built that handles {@link FontWeight} values.
     */
    private static TextStylePropertyName<FontWeight> registerFontWeightConstant(final String property) {
        return registerConstant(property, TextStylePropertyValueHandler.fontWeight());
    }

    /**
     * Creates and adds a new {@link TextStylePropertyName} to the cache being built.
     */
    private static <T> TextStylePropertyName<T> registerConstant(final String property,
                                                                 final TextStylePropertyValueHandler<T> handler) {
        final TextStylePropertyName<T> textStylePropertyName = new TextStylePropertyName<>(property, handler);
        TextStylePropertyName.CONSTANTS.put(property, textStylePropertyName);
        return textStylePropertyName;
    }

    /**
     * Background color
     */
    public final static TextStylePropertyName<Color> BACKGROUND_COLOR = registerColorConstant("background-color");

    /**
     * direction
     */
    public final static TextStylePropertyName<Direction> DIRECTION = registerEnumConstant("direction",
            Direction::valueOf,
            Direction.class);

    /**
     * font-family-name
     */
    public final static TextStylePropertyName<FontFamilyName> FONT_FAMILY_NAME = registerFontFamilyNameConstant("font-family-name");

    /**
     * font-kerning
     */
    public final static TextStylePropertyName<FontKerning> FONT_KERNING = registerEnumConstant("font-kerning",
            FontKerning::valueOf,
            FontKerning.class);

    /**
     * font-size
     */
    public final static TextStylePropertyName<FontSize> FONT_SIZE = registerFontSizeConstant("font-size");

    /**
     * font-stretch
     */
    public final static TextStylePropertyName<FontStretch> FONT_STRETCH = registerEnumConstant("font-stretch",
            FontStretch::valueOf,
            FontStretch.class);

    /**
     * font-style
     */
    public final static TextStylePropertyName<FontStyle> FONT_STYLE = registerEnumConstant("font-style",
            FontStyle::valueOf,
            FontStyle.class);

    /**
     * font-variant
     */
    public final static TextStylePropertyName<FontVariant> FONT_VARIANT = registerEnumConstant("font-variant",
            FontVariant::valueOf,
            FontVariant.class);

    /**
     * font-weight
     */
    public final static TextStylePropertyName<FontWeight> FONT_WEIGHT = registerFontWeightConstant("font-weight");

    /**
     * hanging-punctuation
     */
    public final static TextStylePropertyName<HangingPunctuation> HANGING_PUNCTUATION = registerEnumConstant("hanging-punctuation",
            HangingPunctuation::valueOf,
            HangingPunctuation.class);

    /**
     * horizontal-alignment
     */
    public final static TextStylePropertyName<HorizontalAlignment> HORIZONTAL_ALIGNMENT = registerEnumConstant("horizontal-alignment",
            HorizontalAlignment::valueOf,
            HorizontalAlignment.class);

    /**
     * hyphens
     */
    public final static TextStylePropertyName<Hyphens> HYPHENS = registerEnumConstant("hyphens",
            Hyphens::valueOf,
            Hyphens.class);

    /**
     * text-alignment
     */
    public final static TextStylePropertyName<TextAlignment> TEXT_ALIGNMENT = registerEnumConstant("text-align",
            TextAlignment::valueOf,
            TextAlignment.class);

    /**
     * Text color
     */
    public final static TextStylePropertyName<Color> TEXT_COLOR = registerColorConstant("text-color");

    /**
     * text-decoration: UNDERLINE, OVERLINE, LINE_THROUGH
     */
    public final static TextStylePropertyName<TextDecoration> TEXT_DECORATION = registerEnumConstant("text-decoration",
            TextDecoration::valueOf,
            TextDecoration.class);

    /**
     * text-decoration-color
     */
    public final static TextStylePropertyName<Color> TEXT_DECORATION_COLOR = registerColorConstant("text-decoration-color");

    /**
     * text-decoration-style
     */
    public final static TextStylePropertyName<TextDecorationStyle> TEXT_DECORATION_STYLE = registerEnumConstant("text-decoration-style",
            TextDecorationStyle::valueOf,
            TextDecorationStyle.class);

    /**
     * text-justify
     */
    public final static TextStylePropertyName<TextJustify> TEXT_JUSTIFY = registerEnumConstant("text-justify",
            TextJustify::valueOf,
            TextJustify.class);

    /**
     * text-transform
     */
    public final static TextStylePropertyName<TextTransform> TEXT_TRANSFORM = registerEnumConstant("text-transform",
            TextTransform::valueOf,
            TextTransform.class);

    /**
     * text-wrapping
     */
    public final static TextStylePropertyName<TextWrapping> TEXT_WRAPPING = registerEnumConstant("text-wrapping",
            TextWrapping::valueOf,
            TextWrapping.class);

    /**
     * vertical-alignment
     */
    public final static TextStylePropertyName<VerticalAlignment> VERTICAL_ALIGNMENT = registerEnumConstant("vertical-alignment",
            VerticalAlignment::valueOf,
            VerticalAlignment.class);

    /**
     * white-space
     */
    public final static TextStylePropertyName<TextWhitespace> WHITE_SPACE = registerEnumConstant("white-space",
            TextWhitespace::valueOf,
            TextWhitespace.class);

    /**
     * word-break
     */
    public final static TextStylePropertyName<WordBreak> WORD_BREAK = registerEnumConstant("word-break",
            WordBreak::valueOf,
            WordBreak.class);

    /**
     * word-wrap
     */
    public final static TextStylePropertyName<WordWrap> WORD_WRAP = registerEnumConstant("word-wrap",
            WordWrap::valueOf,
            WordWrap.class);

    /**
     * writing-mode
     */
    public final static TextStylePropertyName<WritingMode> WRITING_MODE = registerEnumConstant("writing-mode",
            WritingMode::valueOf,
            WritingMode.class);

    /**
     * Factory that retrieves an existing property or if unknown a property that assumes non empty string value.
     */
    public static TextStylePropertyName<?> with(final String name) {
        Objects.requireNonNull(name, "name");

        final TextStylePropertyName<?> textStylePropertyName = CONSTANTS.get(name);
        return null != textStylePropertyName ?
                textStylePropertyName :
                new TextStylePropertyName<>(checkName(name), TextStylePropertyValueHandler.string());
    }

    private TextStylePropertyName(final String name,
                                  final TextStylePropertyValueHandler<T> handler) {
        super(name);
        this.handler= handler;

        this.jsonNodeName = JsonNodeName.with(this.name);
    }

    /**
     * Verifies that a value is legal for this {@link TextStylePropertyName}
     */
    public void check(final Object value) {
        this.handler.check(value, this);
    }

    /**
     * Used to handle/validate property values.
     */
    final TextStylePropertyValueHandler<T> handler;

    /**
     * Returns the name in quotes, useful for error messages.
     */
    CharSequence inQuotes() {
        return CharSequences.quoteAndEscape(this.name);
    }

    // HasJsonNode.....................................................................................................

    static TextStylePropertyName<?> fromJsonNodeName(final JsonNode node) {
        return TextStylePropertyName.with(node.name().value());
    }

    JsonNodeName toJsonNodeName() {
        return this.jsonNodeName;
    }

    private final JsonNodeName jsonNodeName;

    // Object..........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof TextStylePropertyName;
    }

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }
}