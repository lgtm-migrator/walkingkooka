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

package walkingkooka.tree.select;

import walkingkooka.tree.expression.ExpressionEvaluationContext;

import java.util.List;

/**
 * A function that returns true if the second string is contained within the first string.
 */
final class NodeSelectorPredicateContainsFunction extends NodeSelectorPredicateFunction<Boolean> {

    /**
     * Singleton
     */
    static final NodeSelectorPredicateContainsFunction INSTANCE = new NodeSelectorPredicateContainsFunction();

    /**
     * Private ctor
     */
    private NodeSelectorPredicateContainsFunction() {
        super();
    }

    @Override
    public Boolean apply(final List<Object> parameters,
                         final ExpressionEvaluationContext context) {
        this.checkParameterCount(parameters, 2);

        return this.string(parameters, 0, context)
                .contains(this.string(parameters, 1, context));
    }

    @Override
    public String toString() {
        return "contains";
    }
}
