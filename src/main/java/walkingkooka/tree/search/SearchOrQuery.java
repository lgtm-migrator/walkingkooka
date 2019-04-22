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

final class SearchOrQuery extends SearchBinaryQuery {

    static SearchOrQuery with(final SearchQuery left, final SearchQuery right) {
        check(left, right);

        return new SearchOrQuery(left, right);
    }

    private SearchOrQuery(final SearchQuery left, final SearchQuery right) {
        super(left, right);
    }

    @Override
    final SearchOrQueryContext context(final SearchQueryContext context) {
        return SearchOrQueryContext.with(context, this.right);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof SearchOrQuery;
    }

    @Override
    String toStringBinaryOperator() {
        return "||";
    }
}
