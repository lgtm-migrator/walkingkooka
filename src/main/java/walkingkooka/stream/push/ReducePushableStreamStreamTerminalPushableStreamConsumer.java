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
import walkingkooka.build.tostring.ToStringBuilderOption;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * Base class for both REDUCE terminators {@link PushableStreamConsumer}.
 */
abstract class ReducePushableStreamStreamTerminalPushableStreamConsumer<T, R> extends PushableStreamStreamTerminalPushableStreamConsumer<T, R> {

    static void checkReducer(final BiFunction<?, ?, ?> reducer) {
         Objects.requireNonNull(reducer,"reducer");
    }

    /**
     * Package private to limit sub classing.
     */
    ReducePushableStreamStreamTerminalPushableStreamConsumer(final T initial,
                                                             final BiFunction<T, ? super T, T> reducer,
                                                             final CloseableCollection closeables) {
        super(closeables);
        this.value = initial;
        this.reducer = reducer;
    }

    /**
     * REDUCE want to process all values.
     */
    @Override
    public final boolean isFinished() {
        return false;
    }

    final BiFunction<T, ? super T, T> reducer;

    T value;


    @Override
    final boolean equals2(final PushableStreamStreamTerminalPushableStreamConsumer<?, ?> other) {
        return this.equals3(Cast.to(other));
    }

    private boolean equals3(final ReducePushableStreamStreamTerminalPushableStreamConsumer<?, ?> other) {
        return this.isValuePresent() == other.isValuePresent() &&
                this.reducer.equals(other.reducer) &&
                Objects.equals(this.value, other.value);
    }

    @Override
    final void buildToString1(final ToStringBuilder builder) {
        builder.label("reduce");
        builder.value(this.reducer);

        if (this.isValuePresent()) {
            builder.enable(ToStringBuilderOption.QUOTE);
            builder.value(this.value);
        }
    }

    abstract boolean isValuePresent();
}