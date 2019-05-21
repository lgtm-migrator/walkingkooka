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

import walkingkooka.Cast;
import walkingkooka.build.tostring.ToStringBuilder;

import java.util.function.Function;

final class MapPushableStreamStreamIntermediatePushableStreamConsumer<T> extends NonLimitOrSkipPushableStreamStreamIntermediatePushableStreamConsumer<T> {

    static <T> MapPushableStreamStreamIntermediatePushableStreamConsumer<T> with(final Function<T, ?> mapper,
                                                                                 final PushableStreamConsumer<T> next) {
        return new MapPushableStreamStreamIntermediatePushableStreamConsumer<>(mapper, next);
    }

    private MapPushableStreamStreamIntermediatePushableStreamConsumer(final Function<T, ?> mapper,
                                                                      final PushableStreamConsumer<T> next) {
        super(next);
        this.mapper = mapper;
    }

    @Override
    public boolean isFinished() {
        return this.next.isFinished();
    }

    @Override
    public void accept(final T value) {
        this.next.accept(Cast.to(this.mapper.apply(value)));
    }

    private final Function<T, ?> mapper;

    // Object............................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof MapPushableStreamStreamIntermediatePushableStreamConsumer;
    }

    @Override
    boolean equals2(final PushableStreamStreamIntermediatePushableStreamConsumer<?> other) {
        return this.equals3(Cast.to(other));
    }

    private boolean equals3(final MapPushableStreamStreamIntermediatePushableStreamConsumer<?> other) {
        return this.mapper.equals(other.mapper);
    }

    @Override
    final void buildToString1(final ToStringBuilder builder) {
        builder.label("map");
        builder.value(this.mapper);
    }
}