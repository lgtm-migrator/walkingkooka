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

package walkingkooka.net.header;

import walkingkooka.collect.list.Lists;
import walkingkooka.net.HasQFactorWeight;

import java.util.List;

/**
 * A parser that creates a list of {@link TokenHeaderValue}.
 */
final class TokenHeaderValueListHeaderParser extends TokenHeaderValueHeaderParser {

    static List<TokenHeaderValue> parseTokenHeaderValueList(final String text) {
        checkText(text, "header");

        final TokenHeaderValueListHeaderParser parser = new TokenHeaderValueListHeaderParser(text);
        parser.parse();
        parser.list.sort(HasQFactorWeight.qFactorDescendingComparator());
        return Lists.readOnly(parser.list);
    }

    // @VisibleForTesting
    TokenHeaderValueListHeaderParser(final String text) {
        super(text);
    }

    @Override final void tokenHeaderValueEnd() {
        this.list.add(this.token);
    }

    @Override
    void separator() {
        // multiple tokens allowed.
    }

    private final List<TokenHeaderValue> list = Lists.array();
}