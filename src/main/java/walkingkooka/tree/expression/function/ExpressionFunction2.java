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

import walkingkooka.Cast;
import walkingkooka.tree.Node;
import walkingkooka.tree.select.NodeSelectorException;

import java.util.List;

/**
 * Base class for many {@link ExpressionFunction} within this package.
 */
abstract class ExpressionFunction2<T> implements ExpressionFunction<T> {

    /**
     * Package private to limit sub classing.
     */
    ExpressionFunction2() {
        super();
    }

    /**
     * Checks and complains if the parameter count doesnt match the expected count.
     * Not the expected count should not include the implied {@link Node} which occupies position 0.
     */
    final void checkParameterCount(final List<Object> parameters, final int expectedCount) {
        final int count = parameters.size() - 1;
        if (expectedCount != count) {
            throw new IllegalArgumentException("Expected " + expectedCount + " but got " + count + "=" + parameters);
        }
    }

    /**
     * Converts a value into a boolean.
     */
    final boolean booleanValue(final Object value, final ExpressionFunctionContext context) {
        return context.convert(value, Boolean.class);
    }

    /**
     * Converts a value into a {@link Comparable} with type parameters.
     */
    final Comparable comparable(final Object value, final ExpressionFunctionContext context) {
        return context.convert(value, Comparable.class);
    }

    /**
     * Converts a value into an integer.
     */
    final int integer(final Object value, final ExpressionFunctionContext context) {
        return context.convert(value, Integer.class);
    }

    /**
     * Converts a value into a {@link Number}.
     */
    final Number number(final Object value, final ExpressionFunctionContext context) {
        return context.convert(value, Number.class);
    }

    /**
     * Converts a value into a string.
     */
    final String string(final Object value, final ExpressionFunctionContext context) {
        return context.convert(value, String.class);
    }

    /**
     * Type safe {@link Boolean} parameter getter.
     */
    final Boolean booleanValue(final List<?> parameters, final int i, final ExpressionFunctionContext context) {
        return this.booleanValue(this.parameter(parameters, i), context);
    }

    /**
     * Type safe {@link Comparable} parameter getter.
     */
    final Comparable comparable(final List<?> parameters, final int i, final ExpressionFunctionContext context) {
        return this.comparable(this.parameter(parameters, i), context);
    }

    /**
     * Type safe integer parameter getter.
     */
    final int integer(final List<?> parameters, final int i, final ExpressionFunctionContext context) {
        return this.integer(this.parameter(parameters, i), context);
    }

    /**
     * Type safe integer parameter getter.
     */
    final Node<?, ?, ?, ?> thisInstance(final List<?> parameters) {
        return Cast.to(parameters.get(0));
    }

    /**
     * Type safe number parameter getter.
     */
    final Number number(final List<?> parameters, final int i, final ExpressionFunctionContext context) {
        return this.number(this.parameter(parameters, i), context);
    }

    /**
     * Type safe String parameter getter.
     */
    final String string(final List<?> parameters, final int i, final ExpressionFunctionContext context) {
        return this.string(this.parameter(parameters, i), context);
    }

    /**
     * Retrieves the parameter at the index or throws a nice exception message.
     */
    final <TT> TT parameter(final List<?> parameters, final int i, final Class<TT> type, final ExpressionFunctionContext context) {
        return context.convert(this.parameter(parameters, i), type);
    }

    /**
     * Retrieves the parameter at the index or throws a nice exception message.
     */
    final Object parameter(final List<?> parameters, final int i) {
        final int count = parameters.size() - 1; // without node at 0.
        if (i < 0 || i >= count) {
            throw new NodeSelectorException("Parameter " + i + " missing from " + parameters);
        }
        return parameters.get(i + 1);
    }

    @Override
    public abstract String toString();
}
