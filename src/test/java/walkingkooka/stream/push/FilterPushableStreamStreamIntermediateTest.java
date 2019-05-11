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
import walkingkooka.predicate.Predicates;

import java.util.function.Predicate;

public final class FilterPushableStreamStreamIntermediateTest extends NonLimitOrSkipPushableStreamStreamIntermediateTestCase<FilterPushableStreamStreamIntermediate> {

    @Test
    public void testDifferentPredicate() {
        this.checkNotEquals(FilterPushableStreamStreamIntermediate.with(Predicates.fake()));
    }

    @Test
    public void testToString() {
        this.checkNotEquals(this.createPushableStreamStreamIntermediate(), "filter: " + PREDICATE);
    }

    @Override
    FilterPushableStreamStreamIntermediate createPushableStreamStreamIntermediate() {
        return FilterPushableStreamStreamIntermediate.with(PREDICATE);
    }

    private final static Predicate<String> PREDICATE = Predicates.fake();

    @Override
    public Class<FilterPushableStreamStreamIntermediate> type() {
        return FilterPushableStreamStreamIntermediate.class;
    }
}
