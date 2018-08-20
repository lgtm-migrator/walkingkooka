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

import org.junit.Test;
import walkingkooka.tree.visit.Visiting;

import java.time.OffsetTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class OffsetTimeParserTokenTest extends ParserTokenTestCase<OffsetTimeParserToken> {

    @Test(expected = NullPointerException.class)
    public void testWithNullTextFails() {
        OffsetTimeParserToken.with(OffsetTime.MAX, null);
    }

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final OffsetTimeParserToken token = this.createToken();

        new FakeParserTokenVisitor() {
            @Override
            protected Visiting startVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("1");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final ParserToken t) {
                assertSame(token, t);
                b.append("2");
            }

            @Override
            protected void visit(final OffsetTimeParserToken t) {
                assertSame(token, t);
                b.append("3");
            }
        }.accept(token);
        assertEquals("132", b.toString());
    }

    @Override
    protected OffsetTimeParserToken createToken() {
        return OffsetTimeParserToken.with(OffsetTime.MAX, "OffsetTime.MAX");
    }

    @Override
    protected OffsetTimeParserToken createDifferentToken() {
        return OffsetTimeParserToken.with(OffsetTime.MIN, "OffsetTime.MIN");
    }

    @Override
    protected Class<OffsetTimeParserToken> type() {
        return OffsetTimeParserToken.class;
    }
}