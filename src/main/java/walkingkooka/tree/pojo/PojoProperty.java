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
package walkingkooka.tree.pojo;

import walkingkooka.naming.HasName;

/**
 * Provides read and write access to a single pojo property.
 */
public interface PojoProperty extends HasName<PojoName> {

    /**
     * Reads the value for a property.
     */
    Object get(final Object instance);

    /**
     * Writes a new value to a property with support for would be setters that may return a new instance.
     */
    Object set(final Object instance, Object value);

    /**
     * Returns true if only a getter is present.
     */
    boolean isReadOnly();
}
