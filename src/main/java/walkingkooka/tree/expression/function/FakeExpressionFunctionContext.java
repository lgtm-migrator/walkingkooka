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

import walkingkooka.math.FakeDecimalNumberContext;
import walkingkooka.tree.expression.ExpressionNodeName;

import java.util.List;
import java.util.Objects;

public class FakeExpressionFunctionContext extends FakeDecimalNumberContext implements ExpressionFunctionContext {

    public FakeExpressionFunctionContext() {
        super();
    }

    @Override
    public Object function(final ExpressionNodeName name, final List<Object> parameters) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(parameters, "parameters");
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T convert(final Object value, final Class<T> target) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(target, "target");

        throw new UnsupportedOperationException();
    }
}
