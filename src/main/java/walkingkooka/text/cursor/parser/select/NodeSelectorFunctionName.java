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

package walkingkooka.text.cursor.parser.select;

import walkingkooka.naming.Name;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.expression.ExpressionReference;

/**
 * The {@link Name} of a function.
 */
final public class NodeSelectorFunctionName implements ExpressionReference, Name, HashCodeEqualsDefined {

    final static CharPredicate INITIAL = CharPredicates.range('A', 'Z').or(CharPredicates.range('a', 'z'));

    final static CharPredicate PART = INITIAL.or(CharPredicates.range('0', '9').or(CharPredicates.is('-')));

    final static int MAX_LENGTH = 255;

    /**
     * Factory that creates a {@link NodeSelectorFunctionName}
     */
    public static NodeSelectorFunctionName with(final String name) {
        CharSequences.failIfNullOrEmptyOrInitialAndPartFalse(name, NodeSelectorFunctionName.class.getSimpleName(), INITIAL, PART);

        final int length = name.length();
        if (length > MAX_LENGTH) {
            throw new IllegalArgumentException("Function name length " + length + " greater than allowed of " + MAX_LENGTH);
        }

        return new NodeSelectorFunctionName(name);
    }

    /**
     * Private constructor
     */
    private NodeSelectorFunctionName(final String name) {
        super();
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    final String name;

    // Object

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        return (this == other) || other instanceof NodeSelectorFunctionName && this.equals0((NodeSelectorFunctionName) other);
    }

    private boolean equals0(final NodeSelectorFunctionName other) {
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}