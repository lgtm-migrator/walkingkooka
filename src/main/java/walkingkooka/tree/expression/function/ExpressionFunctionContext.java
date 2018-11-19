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

import walkingkooka.Context;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.expression.ExpressionNodeName;

import java.util.List;

/**
 * Context that accompanies a {@link ExpressionFunction}.
 */
public interface ExpressionFunctionContext extends Context {

    /**
     * Constant for functions without any parameters.
     */
    public final static List<Object> NO_PARAMETERS = Lists.empty();

    /**
     * Locates a function with the given name and then executes it with the provided parameter values.
     */
    Object function(final ExpressionNodeName name, final List<Object> parameters);

    /**
     * Handles converting the given value to the target.
     */
    <T> T convert(final Object value, final Class<T> target);
}