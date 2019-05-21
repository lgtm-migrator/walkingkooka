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

import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class ReducePushableStreamStreamTerminalPushableStreamConsumerTestCase<T extends ReducePushableStreamStreamTerminalPushableStreamConsumer<String, R>, R> extends PushableStreamStreamTerminalPushableStreamConsumerTestCase<T, R> {

    ReducePushableStreamStreamTerminalPushableStreamConsumerTestCase() {
        super();
    }

    @Test
    public final void testWithNullReducerFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createPushableStreamConsumer(null, this.closeables);
        });
    }

    @Override
    final T createPushableStreamConsumer(final CloseableCollection closeables) {
        return this.createPushableStreamConsumer(REDUCER, closeables);
    }

    final BinaryOperator<String> REDUCER = (a, b) -> a + b;

    abstract T createPushableStreamConsumer(final BiFunction<String, String, String> reducer,
                                            final CloseableCollection closeables);
}