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

package walkingkooka.predicate;

import walkingkooka.test.Fake;

import java.util.function.Predicate;

/**
 * A {@link Predicate} that always throws {@link UnsupportedOperationException}.
 */
public class FakePredicate<T> implements Predicate<T>, Fake {

    static <T> FakePredicate<T> create() {
        return new FakePredicate<T>();
    }

    /**
     * Non public constructor use static factory
     */
    protected FakePredicate() {
        super();
    }

    @Override
    public boolean test(final T value) {
        throw new UnsupportedOperationException();
    }
}
