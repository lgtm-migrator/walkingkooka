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

import java.util.List;
import java.util.Objects;

/**
 * This {@link ParserToken} holds one or more of the tokens of the same type but not equal.
 */
public final class RepeatedParserToken<T extends ParserToken> extends ParserTemplateToken2<T> implements SupportsFlat<RepeatedParserToken<T>, T> {

    public final static ParserTokenNodeName NAME = ParserTokenNodeName.fromClass(RepeatedParserToken.class);

    static <T extends ParserToken> RepeatedParserToken<T> with(final List<T> tokens, final String text) {
        Objects.requireNonNull(tokens, "tokens");
        Objects.requireNonNull(text, "text");
        if(tokens.isEmpty()) {
            throw new IllegalArgumentException("Tokens must not be empty");
        }

        return new RepeatedParserToken<T>(tokens, text);
    }
    
    private RepeatedParserToken(final List<T> tokens, final String text) {
        super(tokens, text);
    }

    @Override
    public RepeatedParserToken setText(final String text){
        return Cast.to(this.setText0(text));
    }

    @Override
    RepeatedParserToken replaceText(final String text) {
        return with(this.value(), text);
    }

    @Override
    public RepeatedParserToken<T> flat() {
        final List<T> tokens = this.value();
        final List<T> flat = this.flat(tokens);
        return tokens.equals(flat) ? this : new RepeatedParserToken<>(flat, this.text());
    }

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof RepeatedParserToken;
    }

    @Override
    public String toString() {
        return this.value().toString();
    }
}
