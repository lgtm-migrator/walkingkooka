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
package walkingkooka.text.cursor.parser;

import walkingkooka.collect.list.Lists;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.TextCursorSavePoint;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link Parser} that only matches one or more tokens matched by a different provided {@link Parser}.
 */
final class RepeatedParser<C extends ParserContext> extends Parser2<C> {

    static <C extends ParserContext> RepeatedParser<C> with(final Parser<C> parser) {
        Objects.requireNonNull(parser, "parser");

        return parser instanceof RepeatedParser ?
                parser.cast() :
                new RepeatedParser<>(parser);
    }

    private RepeatedParser(final Parser<C> parser) {
        this.parser = parser;
    }

    @Override
    Optional<ParserToken> tryParse0(final TextCursor cursor, final C context, final TextCursorSavePoint start) {
        final List<ParserToken> tokens = Lists.array();

        for (; ; ) {
            final Optional<ParserToken> maybe = this.parser.parse(cursor, context);
            if (!maybe.isPresent()) {
                break;
            }
            tokens.add(maybe.get());
        }

        return tokens.isEmpty() ?
                this.fail() :
                Optional.of(RepeatedParserToken.with(tokens, start.textBetween().toString()));
    }

    private final Parser<C> parser;

    @Override
    public String toString() {
        return "{" + parser.toString() + "}";
    }
}
