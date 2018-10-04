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

package walkingkooka.text.spreadsheetformat;

import walkingkooka.Cast;
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.build.tostring.ToStringBuilderOption;
import walkingkooka.build.tostring.UsesToStringBuilder;
import walkingkooka.color.Color;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.HasText;

import java.util.Objects;
import java.util.Optional;

/**
 * Holds the color and text that results from formatting a value.
 */
public final class SpreadsheetFormattedText implements HasText, HashCodeEqualsDefined, UsesToStringBuilder {

    /**
     * Constant that holds an empty color.
     */
    public final static Optional<Color> WITHOUT_COLOR = Optional.empty();

    /**
     * Creates a {@link SpreadsheetFormattedText}
     */
    public static SpreadsheetFormattedText with(final Optional<Color> color, final String text) {
        Objects.requireNonNull(color, "color");
        Objects.requireNonNull(text, "text");

        return new SpreadsheetFormattedText(color, text);
    }

    /**
     * Private ctor use factory.
     */
    private SpreadsheetFormattedText(final Optional<Color> color, final String text) {
        this.color = color;
        this.text = text;
    }

    public Optional<Color> color() {
        return this.color;
    }

    private final Optional<Color> color;

    @Override
    public String text() {
        return this.text;
    }

    private final String text;

    // Object ............................................................................

    @Override
    public int hashCode() {
        return Objects.hash(this.color, this.text);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof SpreadsheetFormattedText &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final SpreadsheetFormattedText other) {
        return this.color.equals(other.color) &&
                this.text.equals(other.text);
    }

    @Override
    public String toString() {
        return ToStringBuilder.buildFrom(this);
    }

    @Override
    public void buildToString(ToStringBuilder builder) {
        builder.separator(" ");
        builder.value(this.color);

        builder.enable(ToStringBuilderOption.QUOTE);
        builder.value(this.text);
    }
}