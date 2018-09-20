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

import java.math.BigDecimal;

/**
 * A {@link SearchNode} that holds a {@link BigDecimal} value.
 */
public final class SearchBigDecimalNode extends SearchLeafNode<BigDecimal>{

    public final static SearchNodeName NAME = SearchNodeName.fromClass(SearchBigDecimalNode.class);

    static SearchBigDecimalNode with(final String text, final BigDecimal value) {
        check(text, value);

        return new SearchBigDecimalNode(NO_PARENT_INDEX, text, value);
    }

    private SearchBigDecimalNode(final int index, final String text, final BigDecimal value) {
        super(index, text, value);
    }
    
    @Override
    public SearchNodeName name() {
        return NAME;
    }

    @Override
    SearchBigDecimalNode wrap1(final int index, final String text, final BigDecimal value) {
        return new SearchBigDecimalNode(index, text, value);
    }

    @Override
    public boolean isBigDecimal() {
        return true;
    }

    @Override
    public boolean isBigInteger() {
        return false;
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public boolean isLocalDate() {
        return false;
    }

    @Override
    public boolean isLocalDateTime() {
        return false;
    }

    @Override
    public boolean isLocalTime() {
        return false;
    }

    @Override
    public boolean isLong() {
        return false;
    }

    @Override
    public boolean isText() {
        return false;
    }

    // SearchQuery......................................................................................................

    @Override
    void select(final SearchQuery query, final SearchQueryContext context) {
        query.visit(this, context);
    }

    // Visitor ..........................................................................................................

    @Override
    public void accept(final SearchNodeVisitor visitor){
        visitor.visit(this);
    }

    @Override
    final boolean canBeEqual(final Object other) {
        return other instanceof SearchBigDecimalNode;
    }

    @Override
    final void toString0(final StringBuilder b) {
        b.append(this.value());
    }
}