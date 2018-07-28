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
package walkingkooka.text.cursor.parser;

import walkingkooka.Cast;

import java.util.Objects;

/**
 * The parser token for a number with the value contained in a {@link Long}.
 */
public final class LongParserToken extends ParserTemplateToken<Long> implements HasSign<LongParserToken> {

    public final static ParserTokenNodeName NAME = ParserTokenNodeName.fromClass(LongParserToken.class);

    public static LongParserToken with(final long value, final String text) {
        Objects.requireNonNull(text, "text");

        return new LongParserToken(value, text);
    }

    private LongParserToken(final Long value, final String text) {
        super(value, text);
    }

    @Override
    public LongParserToken setText(final String text){
        return Cast.to(this.setText0(text));
    }

    @Override
    LongParserToken replaceText(final String text) {
        return with(this.value(), text);
    }

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    @Override
    public boolean isNegative() {
        return this.value() < 0;
    }

    @Override
    public LongParserToken setNegative(final boolean negative) {
        final Long value = this.value();
        return value < 0 && negative ?
                this :
                new LongParserToken(-value, this.text());
    }

    @Override
    public void accept(final ParserTokenVisitor visitor){
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof LongParserToken;
    }

    @Override
    boolean equals1(final ParserTemplateToken<?> other) {
        return true; // no extra properties to compare
    }

    @Override
    public String toString() {
        return this.text();
    }
}
