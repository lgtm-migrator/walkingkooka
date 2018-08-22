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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.text.cursor.TextCursor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * A {@link Parser} consumes one or more characters from a {@link TextCursor} and gives a {@link ParserToken}.
 * The value (an {@link java.util.Optional} will contain the parsed value and text when successful or be empty when
 * not.
 */
public interface Parser<T extends ParserToken, C extends ParserContext> {

    /**
     * Attempts to parse the text given by the {@link TextCursor}.
     */
    Optional<T> parse(final TextCursor cursor, final C context);

    /**
     * Creates a parser that matches this parser and fails the given parser.
     */
    default Parser<T, C> andNot(final Parser<T, C> parser) {
        return Parsers.andNot(this, parser);
    }

    /**
     * Creates a new {@link SequenceParserBuilder} and adds this parser as a required(default) or optional(if already an optional).
     * The builder may then be used to continue building...
     */
    default SequenceParserBuilder<C> builder(final ParserTokenNodeName name){
        Objects.requireNonNull(name, "name");

        final SequenceParserBuilder<C> builder = Parsers.sequenceParserBuilder();
        if(this instanceof OptionalParser) {
            builder.optional(this.cast(), name);
        } else {
            builder.required(this.cast(), name);
        }
        return builder;
    }

    /**
     * Converts this parser into an optional.
     */
    default Parser<ParserToken, C> optional(final ParserTokenNodeName name) {
        return Parsers.optional(this, name);
    }

    /**
     * Combines this parser with another.
     */
    default Parser<ParserToken, C> or(final Parser<? extends T, C> parser) {
        Objects.requireNonNull(parser, "parser");

        return Parsers.alternatives(Lists.of(this.cast(), parser.cast()));
    }

    /**
     * Makes this a repeating token.
     */
    default Parser<RepeatedParserToken, C> repeating(){
        return Parsers.repeated(this.cast());
    }


    default Parser<T, C> setToString(final String toString) {
        final Parser<T, C> toStringParser = Parsers.customToString(this, toString);
        return this.equals(toStringParser) ?
                this :
                toStringParser;
    }

    /**
     * {@see TransformingParser}
     */
    default <R extends ParserToken> Parser<R, C> transform(final BiFunction<T, C, R> transformer) {
        return TransformingParser.with(this, transformer);
    }

    /**
     * Returns a {@link ParserReporter} which will be triggered if this (original unwrapped) {@link Parser} fails.
     */
    default Parser<T, C> orReport(final ParserReporter<T, C> reporter) {
        final Parser<ParserToken, C> that = this.cast();
        return Parsers.alternatives(Lists.of(that, Parsers.report(Cast.to(reporter), that))).cast();
    }

    /**
     * Helper that makes casting and working around generics a little less noisy.
     */
    default <P extends Parser<?, ?>> P cast() {
        return Cast.to(this);
    }
}
