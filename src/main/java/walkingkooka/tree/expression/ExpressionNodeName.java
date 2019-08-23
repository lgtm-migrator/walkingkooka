/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
 */

package walkingkooka.tree.expression;

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.map.FromJsonNodeContext;
import walkingkooka.tree.json.map.JsonNodeContext;
import walkingkooka.tree.json.map.ToJsonNodeContext;

/**
 * The name of an expression node.
 */
public final class ExpressionNodeName implements Name,
        Comparable<ExpressionNodeName> {

    public static ExpressionNodeName with(final String name) {
        CharPredicates.failIfNullOrEmptyOrInitialAndPartFalse(name,
                "name",
                INITIAL,
                PART);
        return new ExpressionNodeName(name);
    }

    private final static CharPredicate INITIAL = CharPredicates.letter();
    private final static CharPredicate PART = CharPredicates.letterOrDigit().or(CharPredicates.any("-"));

    static ExpressionNodeName fromClass(final Class<? extends ExpressionNode> klass) {
        final String name = klass.getSimpleName();
        return new ExpressionNodeName(name.substring("Expression".length(), name.length() - Name.class.getSimpleName().length()));
    }

    // @VisibleForTesting
    private ExpressionNodeName(final String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    // JsonNodeContext..................................................................................................

    /**
     * Accepts a json string holding the name.
     */
    static ExpressionNodeName fromJsonNode(final JsonNode node,
                                           final FromJsonNodeContext context) {
        return with(node.stringValueOrFail());
    }

    JsonNode toJsonNode(final ToJsonNodeContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register("expression-node-name",
                ExpressionNodeName::fromJsonNode,
                ExpressionNodeName::toJsonNode,
                ExpressionNodeName.class);
    }

    // Object...........................................................................................................

    @Override
    public final int hashCode() {
        return CASE_SENSITIVITY.hash(this.name);
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof ExpressionNodeName &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final ExpressionNodeName other) {
        return CASE_SENSITIVITY.equals(this.name, other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Comparable ...................................................................................................

    @Override
    public int compareTo(final ExpressionNodeName other) {
        return CASE_SENSITIVITY.comparator().compare(this.name, other.name);
    }

    // HasCaseSensitivity................................................................................................

    @Override
    public CaseSensitivity caseSensitivity() {
        return CASE_SENSITIVITY;
    }

    private final static CaseSensitivity CASE_SENSITIVITY = CaseSensitivity.SENSITIVE;
}
