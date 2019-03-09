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

package walkingkooka.tree.json;

import walkingkooka.test.ClassTesting;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.type.MemberVisibility;

public abstract class HasJsonNodeMapperTestCase<M extends HasJsonNodeMapper<T>, T> implements ClassTesting<M>,
        TypeNameTesting<M> {

    HasJsonNodeMapperTestCase() {
        super();
    }

    // ClassTesting................................................................................

    @Override
    public final MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }

    // TypeNameTesting................................................................................

    @Override
    public final String typeNamePrefix() {
        return HasJsonNode.class.getSimpleName();
    }

    @Override
    public String typeNameSuffix() {
        return "";
    }
}