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
 */

package walkingkooka.naming;


import walkingkooka.text.CharSequences;

import java.io.Serializable;

/**
 * A simple {@link Name} that accepts a {@link String} composed of any character.
 */
final public class PropertiesName implements Name,
        Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * Root singleton
     */
    final static PropertiesName ROOT = new PropertiesName("");

    /**
     * Factory that creates a {@link PropertiesName}, only the root path has this name.
     */
    static PropertiesName with(final String name) {
        CharSequences.failIfNullOrEmpty(name, "name");
        if (-1 != name.indexOf(PropertiesPath.SEPARATOR.character())) {
            throw new IllegalArgumentException("Name " + CharSequences.quote(name) +
                    " cannot contain " + CharSequences.quoteIfChars(PropertiesPath.SEPARATOR.character()));
        }

        return new PropertiesName(name);
    }

    private PropertiesName(final String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return this.name;
    }

    private final String name;

    // Object

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return (this == other) || ((other instanceof PropertiesName) && this.equals0((PropertiesName) other));
    }

    private boolean equals0(final PropertiesName other) {
        return this.name.equals(other.name);
    }

    @Override
    public String toString() {
        return CharSequences.quote(this.name).toString();
    }
}
