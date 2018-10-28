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
package walkingkooka.text.cursor.parser.select;

import org.junit.Test;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.tree.visit.Visiting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class NodeSelectorFunctionNameParserTokenTest extends NodeSelectorNonSymbolParserTokenTestCase<NodeSelectorFunctionNameParserToken, NodeSelectorFunctionName> {

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final NodeSelectorFunctionNameParserToken token = this.createToken();

        new FakeNodeSelectorParserTokenVisitor() {
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
            protected Visiting startVisit(final NodeSelectorParserToken t) {
                assertSame(token, t);
                b.append("3");
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final NodeSelectorParserToken t) {
                assertSame(token, t);
                b.append("4");
            }

            @Override
            protected void visit(final NodeSelectorFunctionNameParserToken t) {
                assertSame(token, t);
                b.append("5");
            }
        }.accept(token);
        assertEquals("13542", b.toString());
    }

    @Override
    protected String text() {
        return "starts-with";
    }

    @Override
    NodeSelectorFunctionName value() {
        return NodeSelectorFunctionName.with(this.text());
    }

    @Override
    protected NodeSelectorFunctionNameParserToken createToken(final NodeSelectorFunctionName value, final String text) {
        return NodeSelectorFunctionNameParserToken.with(value, text);
    }

    @Override
    protected NodeSelectorFunctionNameParserToken createDifferentToken() {
        return NodeSelectorFunctionNameParserToken.with(NodeSelectorFunctionName.with("ends-with"), "ends-with");
    }

    @Override
    protected Class<NodeSelectorFunctionNameParserToken> type() {
        return NodeSelectorFunctionNameParserToken.class;
    }
}
