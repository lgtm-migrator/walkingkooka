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
package walkingkooka;

import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;

/**
 * Mixing interface that provides methods to test a {@link Context}
 */
public interface ContextTesting<C extends Context> extends TypeNameTesting<C>,
        ToStringTesting<C> {

    C createContext();


    // TypeNameTesting......................................................................................

    @Override
    default String typeNamePrefix() {
        return "";
    }
}