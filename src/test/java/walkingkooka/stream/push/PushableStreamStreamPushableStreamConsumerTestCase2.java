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

import walkingkooka.collect.list.Lists;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.test.ToStringTesting;

import java.util.List;

public abstract class PushableStreamStreamPushableStreamConsumerTestCase2<P extends PushableStreamStreamPushableStreamConsumer<String>>
        extends PushableStreamStreamPushableStreamConsumerTestCase<P>
        implements HashCodeEqualsDefinedTesting<P>,
        PushableStreamConsumerTesting<P, String>,
        ToStringTesting<P> {

    PushableStreamStreamPushableStreamConsumerTestCase2() {
        super();
    }

    @Override
    public final P createObject() {
        return this.createPushableStreamConsumer();
    }

    final List<String> commaSeparated(final String source) {
        return source.isEmpty() ?
                Lists.empty() :
                Lists.of(source.split(","));
    }
}