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

import java.math.BigInteger;

/**
 * A {@link SearchQueryValue} that holds a {@link BigInteger}
 */
public final class SearchBigIntegerQueryValue extends SearchNumberQueryValue<BigInteger> {

    static SearchBigIntegerQueryValue with(final BigInteger value) {
        check(value);
        return new SearchBigIntegerQueryValue(value);
    }

    private SearchBigIntegerQueryValue(final BigInteger value) {
        super(value);
    }

    @Override
    SearchBigIntegerQueryValueSearchQueryTester tester(final SearchQueryValueSearchQueryTesterComparisonPredicate resultPredicate) {
        return SearchBigIntegerQueryValueSearchQueryTester.with(this.value, resultPredicate);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SearchBigIntegerQueryValue;
    }
}