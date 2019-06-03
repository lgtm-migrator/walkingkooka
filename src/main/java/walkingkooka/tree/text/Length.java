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
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonNode;

import java.util.Objects;

/**
 * Base class for any measure.
 */
public abstract class Length implements HashCodeEqualsDefined,
        HasJsonNode{

    /**
     * Parses text that contains a support measurement mostly a number and unit.
     */
    public static Length parse(final String text) {
        CharSequences.failIfNullOrEmpty(text, "text");

        Length length;
        for(;;) {
            if(text.endsWith(Pixels.UNIT)) {
                length = Pixels.parsePixels(text);
                break;
            }
            throw new IllegalArgumentException("Invalid text or missing unit " + CharSequences.quoteAndEscape(text));
        }

        return length;
    }

    /**
     * {@see Pixels}
     */
    public static Pixels pixels(final double value) {
        return Pixels.with(value);
    }

    /**
     * Package private to limit subclassing
     */
    Length() {
    }

    abstract double doubleValue();

    // is ..............................................................................................................

    /**
     * Only {@link Pixels} returns true.
     */
    public abstract boolean isPixels();

    // Object ..........................................................................................................

    @Override
    public abstract int hashCode();

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                this.canBeEqual(other) &&
                        this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    abstract boolean equals0(final Length other);

    static {
        HasJsonNode.register("length",
                Length::fromJsonNode,
                Length.class, Pixels.class);
    }

    // HasJsonNode......................................................................................................

    /**
     * Accepts a json string holding a number and unit.
     */
    public static Length fromJsonNode(final JsonNode node) {
        Objects.requireNonNull(node, "node");

        return parse(node.stringValueOrFail());
    }
}
