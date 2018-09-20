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
import java.time.format.DateTimeFormatter;

/**
 * A {@link SearchNode} that holds a {@link LocalDateTime} value.
 */
public final class SearchLocalDateTimeNode extends SearchLeafNode<LocalDateTime>{

    public final static SearchNodeName NAME = SearchNodeName.fromClass(SearchLocalDateTimeNode.class);

    static SearchLocalDateTimeNode with(final String text, final LocalDateTime value) {
        check(text, value);

        return new SearchLocalDateTimeNode(NO_PARENT_INDEX, text, value);
    }

    private SearchLocalDateTimeNode(final int index, final String text, final LocalDateTime value) {
        super(index, text, value);
    }
    
    @Override
    public SearchNodeName name() {
        return NAME;
    }

    @Override
    SearchLocalDateTimeNode wrap1(final int index, final String text, final LocalDateTime value) {
        return new SearchLocalDateTimeNode(index, text, value);
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
        return false;
    }

    @Override
    public boolean isLocalDateTime() {
        return true;
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
        return other instanceof SearchLocalDateTimeNode;
    }

    @Override
    final void toString0(final StringBuilder b) {
        b.append(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this.value()));
    }
}