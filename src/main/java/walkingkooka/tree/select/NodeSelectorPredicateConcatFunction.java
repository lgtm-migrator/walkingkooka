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

import java.util.List;
import java.util.stream.Collectors;

/**
 * A function that concats all the Strings given to it.
 */
final class NodeSelectorPredicateConcatFunction extends NodeSelectorPredicateFunction<String> {

    /**
     * Singleton
     */
    static final NodeSelectorPredicateConcatFunction INSTANCE = new NodeSelectorPredicateConcatFunction();

    /**
     * Private ctor
     */
    private NodeSelectorPredicateConcatFunction() {
        super();
    }

    @Override
    public String apply(final List<Object> parameters, final NodeSelectorPredicateExpressionEvaluationContext context) {
        final int count = parameters.size();
        if (count < 1) {
            throw new IllegalArgumentException("Expected at least 1 parameter but got " + parameters.size() + "=" + parameters);
        }

        return parameters.stream()
                .map(p -> context.string(p))
                .collect(Collectors.joining());
    }

    @Override
    public String toString() {
        return "concat";
    }
}
