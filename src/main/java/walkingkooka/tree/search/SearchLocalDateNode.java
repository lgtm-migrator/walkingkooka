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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A {@link SearchNode} that holds a {@link LocalDate} value.
 */
public final class SearchLocalDateNode extends SearchLeafNode<LocalDate>{

    public final static SearchNodeName NAME = SearchNodeName.fromClass(SearchLocalDateNode.class);

    static SearchLocalDateNode with(final String text, final LocalDate value) {
        check(text, value);

        return new SearchLocalDateNode(NO_PARENT_INDEX, text, value);
    }

    private SearchLocalDateNode(final int index, final String text, final LocalDate value) {
        super(index, text, value);
    }
    
    @Override
    public SearchNodeName name() {
        return NAME;
    }

    @Override
    SearchLocalDateNode wrap1(final int index, final String text, final LocalDate value) {
        return new SearchLocalDateNode(index, text, value);
    }

    @Override
    public boolean isBigDecimal() {
        return false;
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
        return true;
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
        return other instanceof SearchLocalDateNode;
    }

    @Override
    final void toString0(final StringBuilder b) {
        b.append(DateTimeFormatter.ISO_LOCAL_DATE.format(this.value()));
    }
}