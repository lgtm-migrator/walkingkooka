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

import walkingkooka.tree.Node;
import walkingkooka.tree.expression.ExpressionNodeName;

import java.util.List;

/**
 * A non standard function that returns {@link Node#name()}.
 */
final class ExpressionNodeNameFunction extends ExpressionFunction2<String> {
    /**
     * Singleton
     */
    static final ExpressionNodeNameFunction INSTANCE = new ExpressionNodeNameFunction();

    /**
     * Private ctor
     */
    private ExpressionNodeNameFunction() {
        super();
    }

    @Override
    public String apply(final List<Object> parameters,
                        final ExpressionFunctionContext context) {
        return this.thisInstance(parameters).name().value();
    }

    @Override
    public ExpressionNodeName name() {
        return NAME;
    }

    private final static ExpressionNodeName NAME = ExpressionNodeName.with("name");

    @Override
    public String toString() {
        return this.name().toString();
    }
}
