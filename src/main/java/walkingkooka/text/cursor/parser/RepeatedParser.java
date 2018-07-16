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
final class RepeatedParser<T extends ParserToken, C extends ParserContext> extends ParserTemplate2<RepeatedParserToken<T>, C> {

    static <T extends ParserToken, C extends ParserContext> RepeatedParser<T,C> with(final Parser<T, C> parser){
        Objects.requireNonNull(parser, "parser");

        return parser instanceof RepeatedParser ?
                parser.cast() :
                new RepeatedParser<>(parser);
    }

    private RepeatedParser(final Parser<T, C> parser){
        this.parser = parser;
    }

    @Override
    Optional<RepeatedParserToken<T>> tryParse0(final TextCursor cursor, final C context, final TextCursorSavePoint start) {
        final List<T> tokens = Lists.array();

        for(;;) {
            final Optional<T> token = this.parser.parse(cursor, context);
            if(!token.isPresent()){
                break;
            }
            tokens.add(token.get());
        }

        return tokens.isEmpty() ?
                this.fail() :
                RepeatedParserToken.with(tokens,
                        start.textBetween().toString())
                .success();
    }

    private final Parser<T, C> parser;

    @Override
    public String toString() {
        return parser.toString() + "*";
    }
}
