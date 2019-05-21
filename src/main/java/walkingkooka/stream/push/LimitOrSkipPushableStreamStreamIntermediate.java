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

/**
 * Base class for both LIMIT and SKIP.
 */
abstract class LimitOrSkipPushableStreamStreamIntermediate extends PushableStreamStreamIntermediate {

    LimitOrSkipPushableStreamStreamIntermediate(final long value) {
        super();
        this.value = value;
    }

    final long value;

    @Override
    public final int hashCode() {
        return Long.hashCode(this.value);
    }

    @Override
    final boolean equals0(final Object other) {
        return this.equals1(Cast.to(other));
    }

    private boolean equals1(final LimitOrSkipPushableStreamStreamIntermediate other) {
        return this.value == other.value;
    }

    @Override
    public String toString() {
        return this.label() + " " + this.value;
    }

    abstract String label();
}