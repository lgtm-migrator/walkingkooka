/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.stream.push;

import walkingkooka.Cast;
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.build.tostring.ToStringBuilderOption;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

/**
 * The COLLECTOR TERMINAL {@link PushableStreamConsumer}.
 */
final class CollectorPushableStreamStreamTerminalPushableStreamConsumer<T, A, R> extends PushableStreamStreamTerminalPushableStreamConsumer<T, R> {

    static <T, A, R> CollectorPushableStreamStreamTerminalPushableStreamConsumer<T, A, R> with(final Collector<? super T, A, R> collector,
                                                                                               final CloseableCollection closeables) {
        Objects.requireNonNull(collector, "collector");

        return new CollectorPushableStreamStreamTerminalPushableStreamConsumer<T, A, R>(collector, closeables);
    }

    /**
     * Package private to limit sub classing.
     */
    private CollectorPushableStreamStreamTerminalPushableStreamConsumer(final Collector<? super T, A, R> collector,
                                                                        final CloseableCollection closeables) {
        super(closeables);
        this.collector = collector;
        this.container = collector.supplier().get();
        this.accumulator = Cast.to(collector.accumulator());
    }

    /**
     * Collectors want to collect everything.
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void accept(final T value) {
        this.accumulator.accept(this.container, value);
    }

    private final BiConsumer<A, T> accumulator;

    @Override
    R result() {
        return this.collector.finisher().apply(this.container);
    }

    /**
     * The {@link Collector}.
     */
    private final Collector<? super T, A, R> collector;

    /**
     * The container that is aggregating values.
     */
    // VisibleForTesting
    final A container;

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof CollectorPushableStreamStreamTerminalPushableStreamConsumer;
    }

    @Override
    boolean equals2(final PushableStreamStreamTerminalPushableStreamConsumer<?, ?> other) {
        return this.equals3(Cast.to(other));
    }

    private boolean equals3(final CollectorPushableStreamStreamTerminalPushableStreamConsumer<?, ?, ?> other) {
        return this.collector.equals(other.collector);
    }

    @Override
    void buildToString1(final ToStringBuilder builder) {
        builder.disable(ToStringBuilderOption.SKIP_IF_DEFAULT_VALUE);

        builder.label("collect");
        builder.value(this.collector);

        builder.enable(ToStringBuilderOption.QUOTE);
        builder.value(this.container);
        builder.disable(ToStringBuilderOption.QUOTE);
    }
}
