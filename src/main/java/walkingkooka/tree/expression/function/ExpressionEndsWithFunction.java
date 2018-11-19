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

package walkingkooka.tree.expression.function;

import walkingkooka.tree.expression.ExpressionNodeName;

import java.util.List;

/**
 * A function that returns true if the first string ends with the second string.
 */
final class ExpressionEndsWithFunction extends ExpressionTemplateFunction<Boolean> {

    /**
     * Singleton
     */
    static final ExpressionEndsWithFunction INSTANCE = new ExpressionEndsWithFunction();

    /**
     * Private ctor
     */
    private ExpressionEndsWithFunction() {
        super();
    }

    @Override
    public Boolean apply(final List<Object> parameters,
                         final ExpressionFunctionContext context) {
        this.checkParameterCount(parameters, 2);

        return this.string(parameters, 0, context)
                .endsWith(this.string(parameters, 1, context));
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    private final static ExpressionNodeName NAME = ExpressionNodeName.with("ends-with");

    @Override
    public String toString() {
        return this.name().toString();
    }
}