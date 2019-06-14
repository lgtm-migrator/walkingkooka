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
package walkingkooka.tree.select.parser;

import walkingkooka.text.cursor.parser.LeafParserToken;

import java.util.Objects;

/**
 * Base class for a leaf token. A leaf has no further breakdown into more detailed tokens.
 */
abstract class NodeSelectorLeafParserToken<T> extends NodeSelectorParserToken
        implements LeafParserToken<T> {

    static void checkValue(final Object value) {
        Objects.requireNonNull(value, "value");
    }

    NodeSelectorLeafParserToken(final T value, final String text) {
        super(text);
        this.value = value;
    }

    @Override
    public final T value() {
        return this.value;
    }

    final T value;

    // is...............................................................................................................

    @Override
    public final boolean isAddition() {
        return false;
    }

    @Override
    public final boolean isAnd() {
        return false;
    }

    @Override
    public final boolean isAttribute() {
        return false;
    }

    @Override
    public final boolean isDivision() {
        return false;
    }

    @Override
    public final boolean isEquals() {
        return false;
    }

    @Override
    public final boolean isExpression() {
        return false;
    }

    @Override
    public final boolean isFunction() {
        return false;
    }

    @Override
    public final boolean isGreaterThan() {
        return false;
    }

    @Override
    public final boolean isGreaterThanEquals() {
        return false;
    }

    @Override
    public final boolean isGroup() {
        return false;
    }

    @Override
    public final boolean isLessThan() {
        return false;
    }

    @Override
    public final boolean isLessThanEquals() {
        return false;
    }

    @Override
    public final boolean isModulo() {
        return false;
    }

    @Override
    public final boolean isMultiplication() {
        return false;
    }

    @Override
    public final boolean isNegative() {
        return false;
    }

    @Override
    public final boolean isNotEquals() {
        return false;
    }

    @Override
    public final boolean isOr() {
        return false;
    }

    @Override
    public final boolean isPredicate() {
        return false;
    }

    @Override
    public final boolean isSubtraction() {
        return false;
    }
}