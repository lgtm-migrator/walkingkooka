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

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.test.ClassTestCase;
import walkingkooka.test.TestCase;
import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Base {@link TestCase} for numerous parsers in this package.
 */
public abstract class ParserTestCase<P extends Parser<T, ParserContext>, T extends ParserToken>
        extends ClassTestCase<P>
        implements ParserTesting<P, T, ParserContext>,
        ToStringTesting<P>,
        TypeNameTesting<P> {

    ParserTestCase() {
        super();
    }

    @Test
    public void testEmptyCursorFail() {
        this.parseFailAndCheck("");
    }

    @Test
    public final void testOrNullParserFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createParser().or(null);
        });
    }

    @Test
    public final void testRepeating() {
        final Parser<RepeatedParserToken, ParserContext> parser = this.createParser().repeating();
        assertEquals(RepeatedParser.class, parser.getClass(), () -> parser.toString());
    }

    @Test
    public void testOr() {
        final P parser = this.createParser();
        final P parser2 = this.createParser();
        assertEquals(Parsers.alternatives(Lists.<Parser<ParserToken, ParserContext>>of(parser.cast(), parser2.cast())), parser.or(parser2));
    }

    @Override
    public ParserContext createContext() {
        return ParserContexts.fake();
    }

    final TextCursor parseFailAndCheck(final Parser <T, ParserContext> parser, final TextCursor cursor) {
        return this.parseFailAndCheck(parser, this.createContext(), cursor);
    }

    // TypeNameTesting ........................................................................

    @Override
    public final String typeNamePrefix() {
        return "";
    }

    @Override
    public final String typeNameSuffix() {
        return Parser.class.getSimpleName();
    }

    // ClassTestCase ........................................................................

    @Override
    protected final MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}