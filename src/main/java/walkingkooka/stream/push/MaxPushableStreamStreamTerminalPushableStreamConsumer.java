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

import java.util.Comparator;

/**
 * The MAX TERMINAL {@link PushableStreamStreamTerminalPushableStreamConsumer}.
 */
final class MaxPushableStreamStreamTerminalPushableStreamConsumer<T> extends MaxOrMinPushableStreamStreamTerminalPushableStreamConsumer<T> {
    /**
     * Package private to limit sub classing.
     */
    static <T> MaxPushableStreamStreamTerminalPushableStreamConsumer<T> with(final Comparator<? super T> comparator,
                                                                             final CloseableCollection closeables) {
        check(comparator);

        return new MaxPushableStreamStreamTerminalPushableStreamConsumer<T>(comparator, closeables);
    }

    /**
     * Package private to limit sub classing.
     */
    private MaxPushableStreamStreamTerminalPushableStreamConsumer(final Comparator<? super T> comparator,
                                                                  final CloseableCollection closeables) {
        super(comparator, closeables);
    }

    @Override
    final T compare(final int compare,
                    final T previous,
                    final T value) {
        return compare > 0 ? previous : value;
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof MaxPushableStreamStreamTerminalPushableStreamConsumer;
    }

    @Override
    String label() {
        return "max";
    }
}