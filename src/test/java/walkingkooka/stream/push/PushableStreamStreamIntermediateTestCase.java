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

package walkingkooka.stream.push;

import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;

public abstract class PushableStreamStreamIntermediateTestCase<I extends PushableStreamStreamIntermediate> extends PushTestCase<I>
implements HashCodeEqualsDefinedTesting,
        ToStringTesting<I>,
        TypeNameTesting<I> {

    PushableStreamStreamIntermediateTestCase() {
        super();
    }

    abstract I createPushableStreamStreamIntermediate();

    @Override
    public final HashCodeEqualsDefined createObject() {
        return this.createPushableStreamStreamIntermediate();
    }

    // TypeNameTesting.................................................................................................


    @Override
    public final String typeNamePrefix() {
        return "";
    }

    @Override
    public final String typeNameSuffix() {
        return PushableStreamStreamIntermediate.class.getSimpleName();
    }
}
