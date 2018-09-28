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

import org.junit.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.tree.visit.Visiting;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class SequenceParserTokenTest extends RepeatedOrSequenceParserTokenTestCase<SequenceParserToken> {

    private final static StringParserToken STRING1 = ParserTokens.string("a1", "a1");
    private final static StringParserToken STRING2 = ParserTokens.string("b2", "b2");
    private final static ParserToken MISSING3 =  ParserTokens.missing(StringParserToken.NAME, "");
    private final static StringParserToken STRING4 = ParserTokens.string("d4", "d4");
    private final static StringParserToken STRING5 = ParserTokens.string("e5", "e5");
    private final static StringParserToken STRING6 = ParserTokens.string("f6", "f6");
    private final static ParserToken WHITESPACE = new FakeParserToken() {

        @Override
        public String text() {
            return "";
        }

        @Override
        public boolean isWhitespace() {
            return true;
        }
    };
    
    // optional...............................................................................................

    @Test(expected = IndexOutOfBoundsException.class)
    public void testOptionalInvalidIndexFails() {
        this.createToken().optional(999, StringParserToken.class);
    }

    @Test(expected = ClassCastException.class)
    public void testOptionalInvalidTypeFails() {
        this.createToken().optional(0, BigIntegerParserToken.class);
    }

    @Test
    public void testOptionalPresent() {
        this.optionalAndGet(0, Optional.of(STRING1));
    }

    @Test
    public void testOptionalPresent2() {
        this.optionalAndGet(1, Optional.of(STRING2));
    }

    @Test
    public void testOptionalMissing() {
        this.optionalAndGet(2, Optional.empty());
    }

    private void optionalAndGet(final int index, final Optional<ParserToken> result) {
        final SequenceParserToken sequence = this.createToken();
        assertEquals("failed to get optional " + index + " from " + sequence,
                result,
                sequence.optional(index, StringParserToken.class));
    }

    // required...............................................................................................

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRequiredInvalidIndexFails() {
        this.createToken().required(999, StringParserToken.class);
    }

    @Test(expected = ClassCastException.class)
    public void testRequiredInvalidTypeFails() {
        this.createToken().required(0, BigIntegerParserToken.class);
    }

    @Test
    public void testRequiredPresent() {
        this.requiredAndGet(0, STRING1);
    }

    @Test
    public void testRequiredPresent2() {
        this.requiredAndGet(1, STRING2);
    }

    @Test(expected = MissingParserTokenException.class)
    public void testRequiredMissingFails() {
        this.createToken().required(2, StringParserToken.class);
    }

    private void requiredAndGet(final int index, final ParserToken result) {
        final SequenceParserToken sequence = this.createToken();
        assertEquals("failed to get required " + index + " from " + sequence,
                result,
                sequence.required(index, StringParserToken.class));
    }

    // removeMissing...........................................................................................................

    @Test
    public void testRemovingMissingNone() {
        final SequenceParserToken token = createToken(STRING1, STRING2);
        assertSame(token, token.removeMissing());
    }

    @Test
    public void testRemovingMissingSome() {
        final SequenceParserToken token = createToken(STRING1, STRING2, MISSING3);
        final SequenceParserToken different = token.removeMissing();

        assertEquals("value", Lists.of(STRING1, STRING2), different.value());
        assertSame(different, different.removeMissing());
    }

    @Test
    public void testRemovingMissingSomeIgnoresWhitespace() {
        final SequenceParserToken token = createToken(STRING1, STRING2, MISSING3, WHITESPACE);
        final SequenceParserToken different = token.removeMissing();

        assertEquals("value", Lists.of(STRING1, STRING2, WHITESPACE), different.value());
        assertSame(different, different.removeMissing());
    }

    // removeNoise...........................................................................................................

    @Test
    public void testRemovingNoiseNone() {
        final SequenceParserToken token = createToken(STRING1, STRING2);
        assertSame(token, token.removeNoise());
    }

    @Test
    public void testRemovingNoiseSome() {
        final SequenceParserToken token = createToken(STRING1, STRING2, MISSING3);
        final SequenceParserToken different = token.removeNoise();

        assertEquals("value", Lists.of(STRING1, STRING2), different.value());
        assertSame(different, different.removeNoise());
    }

    @Test
    public void testRemovingNoiseSomeIgnoresWhitespace() {
        final SequenceParserToken token = createToken(STRING1, STRING2, MISSING3, WHITESPACE);
        final SequenceParserToken different = token.removeNoise();

        assertEquals("value", Lists.of(STRING1, STRING2, WHITESPACE), different.value());
        assertSame(different, different.removeNoise());
    }

    // removeWhitespace...........................................................................................................

    @Test
    public void testRemovingWhitespaceNone() {
        final SequenceParserToken token = createToken(STRING1, STRING2);
        assertSame(token, token.removeWhitespace());
    }

    @Test
    public void testRemovingWhitespaceSome() {
        final SequenceParserToken token = createToken(STRING1, STRING2, WHITESPACE);
        final SequenceParserToken different = token.removeWhitespace();

        assertEquals("value", Lists.of(STRING1, STRING2), different.value());
        assertSame(different, different.removeWhitespace());
    }

    @Test
    public void testRemovingWhitespaceSomeIgnoresMissing() {
        final SequenceParserToken token = createToken(STRING1, STRING2, MISSING3, WHITESPACE);
        final SequenceParserToken different = token.removeWhitespace();

        assertEquals("value", Lists.of(STRING1, STRING2, MISSING3), different.value());
        assertSame(different, different.removeWhitespace());
    }
    
    // accept...........................................................................................................

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<ParserToken> visited = Lists.array();

        final SequenceParserToken token = this.createToken();

        new FakeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                b.append("1");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                b.append("2");
                visited.add(t);
            }

            @Override
            protected Visiting startVisit(final SequenceParserToken t) {
                assertSame(token, t);
                b.append("3");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final SequenceParserToken t) {
                assertSame(token, t);
                b.append("4");
                visited.add(t);
            }

            @Override
            protected void visit(final MissingParserToken t) {
                b.append("5");
                visited.add(t);
            }

            @Override
            protected void visit(final StringParserToken t) {
                b.append("6");
                visited.add(t);
            }
        }.accept(token);
        assertEquals("1316216215242", b.toString());
        assertEquals("visited tokens", Lists.of(token, token, STRING1, STRING1, STRING1, STRING2, STRING2, STRING2, MISSING3, MISSING3, MISSING3, token, token), visited);
    }

    @Test
    public void testAcceptSkip() {
        final StringBuilder b = new StringBuilder();
        final List<ParserToken> visited = Lists.array();

        final SequenceParserToken token = this.createToken();

        new FakeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                b.append("1");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
                visited.add(t);
            }

            @Override
            protected Visiting startVisit(final SequenceParserToken t) {
                b.append("3");
                visited.add(t);
                return Visiting.SKIP;
            }

            @Override
            protected void endVisit(final SequenceParserToken t) {
                assertSame(token, t);
                b.append("4");
                visited.add(t);
            }
        }.accept(token);
        assertEquals("1342", b.toString());
        assertEquals("visited tokens", Lists.of(token, token, token, token), visited);
    }

    @Override
    protected SequenceParserToken createToken(final String text) {
        return this.createToken(this.tokens(), text);
    }

    @Override
    protected String text() {
        return ParserToken.text(this.tokens());
    }

    private List<ParserToken> tokens() {
        return Lists.of(STRING1, STRING2, MISSING3);
    }

    @Override
    protected SequenceParserToken createDifferentToken() {
        return this.createToken(string("different"), string("different2"));
    }

    @Override
    SequenceParserToken createToken(final List<ParserToken> value, final String text) {
        return SequenceParserToken.with(value, text);
    }

    @Override
    protected Class<SequenceParserToken> type() {
        return SequenceParserToken.class;
    }
}
