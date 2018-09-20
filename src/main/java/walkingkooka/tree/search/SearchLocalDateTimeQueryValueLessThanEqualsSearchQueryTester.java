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

package walkingkooka.tree.search;

import java.time.LocalDateTime;

/**
 * A {@link SearchQueryTester} that only returns true if a {@link LocalDateTime} is less than equal {@link SearchLocalDateTimeNode}.
 */
final class SearchLocalDateTimeQueryValueLessThanEqualsSearchQueryTester extends SearchLocalDateTimeQueryValueSearchQueryTester {

    static SearchLocalDateTimeQueryValueLessThanEqualsSearchQueryTester with(final LocalDateTime value) {
        return new SearchLocalDateTimeQueryValueLessThanEqualsSearchQueryTester(value);
    }

    private SearchLocalDateTimeQueryValueLessThanEqualsSearchQueryTester(final LocalDateTime value) {
        super(value);
    }

    @Override
    SearchQueryTester not() {
        return greaterThan(this.value);
    }

    @Override
    final boolean test(final SearchLocalDateTimeNode node) {
        final LocalDateTime otherValue = node.value();
        return this.value.equals(otherValue) || otherValue.isBefore(this.value);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SearchLocalDateTimeQueryValueLessThanEqualsSearchQueryTester;
    }
}