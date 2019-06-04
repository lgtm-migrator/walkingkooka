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

import walkingkooka.text.CharSequences;

import java.util.Arrays;
import java.util.function.Function;

/**
 * The unit portion of a length. This is basically an ENUMS with type parameters
 */
public final class LengthUnit<V, L extends Length> {

    /**
     * Pixel length unit.
     */
    public final static LengthUnit<Double, PixelLength> PIXEL = new LengthUnit<Double, PixelLength>("px",
            PixelLength::parsePixels,
            Length::pixel);

    /**
     * Returns all units.
     */
    public final static LengthUnit<?, ?>[] units() {
        return UNITS.clone();
    }

    // @see Length:parse
    static Length tryAllParse(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");

        return Arrays.stream(UNITS)
                .filter(u -> u.unitPresent(text))
                .map(u -> u.parse(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid text or missing unit " + CharSequences.quoteAndEscape(text)));
    }

    private final static LengthUnit[] UNITS = new LengthUnit[]{PIXEL};

    /**
     * Private ctor use constant.
     */
    private LengthUnit(final String suffix,
                       final Function<String, L> parser,
                       final Function<V, L> factory) {
        super();
        this.suffix = suffix;
        this.parser = parser;
        this.factory = factory;
    }

    public String suffix() {
        return this.suffix;
    }

    boolean unitPresent(final String text) {
        return text.endsWith(this.suffix);
    }

    void parseUnitTextCheck(final String text) {
        if (!this.unitPresent(text)) {
            throw new IllegalArgumentException("Text " + CharSequences.quoteAndEscape(text) + " missing unit suffix " + CharSequences.quoteAndEscape(this.suffix));
        }
    }

    /**
     * Formats a double value, dropping trailing zeros.
     */
    String toString(final Double value) {
        final long longValue = Math.round(value);
        return (longValue == value ? String.valueOf(longValue) : String.valueOf(value)) + this.suffix;
    }

    private final String suffix;

    /**
     * Invokes the parser for this unit.
     */
    public L parse(final String text) {
        return this.parser.apply(text);
    }

    private final Function<String, L> parser;

    /**
     * Factory that creates a {@link Length} with the given value.
     */
    public L create(final V value) {
        return this.factory.apply(value);
    }

    private Function<V, L> factory;

    @Override
    public String toString() {
        return this.suffix;
    }
}
