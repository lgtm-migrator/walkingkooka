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

package walkingkooka.tree.search;

import walkingkooka.Cast;
import walkingkooka.naming.Name;
import walkingkooka.test.HashCodeEqualsDefined;

import java.util.Objects;

/**
 * The attributeName of an expression node.
 */
public final class SearchNodeName implements Name, Comparable<SearchNodeName>, HashCodeEqualsDefined {

    public static SearchNodeName with(final String name) {
        Objects.requireNonNull(name, "attributeName");
        return new SearchNodeName(name);
    }

    static SearchNodeName fromClass(final Class<? extends SearchNode> klass) {
        final String name = klass.getSimpleName();
        return new SearchNodeName(name.substring("Search".length(), name.length() - Name.class.getSimpleName().length()));
    }

    // @VisibleForTesting
    private SearchNodeName(final String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    // Object..................................................................................................

    public final int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
               other instanceof SearchNodeName &&
               this.equals0(Cast.to(other));
    }

    private boolean equals0(final SearchNodeName other) {
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return this.name;
    }

    // Comparable ...................................................................................................

    @Override
    public int compareTo(final SearchNodeName other) {
        return this.name.compareTo(other.name);
    }
}