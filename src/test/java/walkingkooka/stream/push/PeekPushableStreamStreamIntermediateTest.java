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

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PeekPushableStreamStreamIntermediateTest extends NonLimitOrSkipPushableStreamStreamIntermediateTestCase<PeekPushableStreamStreamIntermediate> {

    @Test
    public void testWithNullActionFails() {
        assertThrows(NullPointerException.class, () -> {
            PeekPushableStreamStreamIntermediate.with(null);
        });
    }

    @Test
    public void testDifferentFunction() {
        this.checkNotEquals(PeekPushableStreamStreamIntermediate.with((ignored) -> {}));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createPushableStreamStreamIntermediate(), "peek " + ACTION);
    }

    @Override
    PeekPushableStreamStreamIntermediate createPushableStreamStreamIntermediate() {
        return PeekPushableStreamStreamIntermediate.with(ACTION);
    }

    private final static Consumer<String> ACTION = (ignored) -> {};

    @Override
    public Class<PeekPushableStreamStreamIntermediate> type() {
        return PeekPushableStreamStreamIntermediate.class;
    }
}