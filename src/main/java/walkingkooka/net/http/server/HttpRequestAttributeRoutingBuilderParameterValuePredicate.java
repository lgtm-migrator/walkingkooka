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

package walkingkooka.net.http.server;

import walkingkooka.Cast;
import walkingkooka.test.HashCodeEqualsDefined;

import java.util.List;
import java.util.function.Predicate;

/**
 * A {@link Predicate} that tries all parameter values against the wrapped {@link Predicate}.
 */
final class HttpRequestAttributeRoutingBuilderParameterValuePredicate implements Predicate<List<String>>,
        HashCodeEqualsDefined {

    /**
     * Creates a new {@link HttpRequestAttributeRoutingBuilderParameterValuePredicate}.
     */
    static HttpRequestAttributeRoutingBuilderParameterValuePredicate with(final Predicate<String> predicate) {
        return new HttpRequestAttributeRoutingBuilderParameterValuePredicate(predicate);
    }

    /**
     * Private ctor
     */
    private HttpRequestAttributeRoutingBuilderParameterValuePredicate(final Predicate<String> predicate) {
        this.predicate = predicate;
    }

    // Predicate ......................................................................................................

    @Override
    public boolean test(final List<String> values) {
        return null != values &&
                values.stream()
                        .anyMatch(this.predicate);
    }

    private final Predicate<String> predicate;

    // Object ......................................................................................................

    @Override
    public int hashCode() {
        return this.predicate.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
                other instanceof HttpRequestAttributeRoutingBuilderParameterValuePredicate &&
                        this.equals0(Cast.to(other));
    }

    private boolean equals0(final HttpRequestAttributeRoutingBuilderParameterValuePredicate other) {
        return this.predicate.equals(other.predicate);
    }

    @Override
    public String toString() {
        return this.predicate.toString();
    }
}
