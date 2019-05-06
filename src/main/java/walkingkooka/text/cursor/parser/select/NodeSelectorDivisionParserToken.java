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

import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.ParserTokenNodeName;
import walkingkooka.tree.visit.Visiting;

import java.util.List;

/**
 * Holds a division
 */
public final class NodeSelectorDivisionParserToken extends NodeSelectorArithmeticParserToken<NodeSelectorDivisionParserToken> {

    public final static ParserTokenNodeName NAME = parserTokenNodeName(NodeSelectorDivisionParserToken.class);

    static NodeSelectorDivisionParserToken with(final List<ParserToken> value, final String text) {
        return new NodeSelectorDivisionParserToken(copyAndCheckTokens(value),
                checkTextNullOrWhitespace(text),
                WITHOUT_COMPUTE_REQUIRED);
    }

    private NodeSelectorDivisionParserToken(final List<ParserToken> value, final String text, final List<ParserToken> valueWithout) {
        super(value, text, valueWithout);
    }

    @Override
    public NodeSelectorDivisionParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    NodeSelectorDivisionParserToken replaceText(final String text) {
        return new NodeSelectorDivisionParserToken(this.value, text, this.valueIfWithoutSymbolsOrNull());
    }

    @Override
    public NodeSelectorDivisionParserToken setValue(final List<ParserToken> value) {
        return this.setValue0(value).cast();
    }

    @Override
    NodeSelectorParentParserToken replaceValue(final List<ParserToken> tokens, final List<ParserToken> without) {
        return new NodeSelectorDivisionParserToken(tokens, this.text(), without);
    }

    // name................................................................................................

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    // is................................................................................................

    @Override
    public boolean isAddition() {
        return false;
    }

    @Override
    public boolean isDivision() {
        return true;
    }

    @Override
    public boolean isModulo() {
        return false;
    }

    @Override
    public boolean isMultiplication() {
        return false;
    }

    @Override
    public boolean isSubtraction() {
        return false;
    }

    // Visitor........................................................................................................

    @Override
    public void accept(final NodeSelectorParserTokenVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Object........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof NodeSelectorDivisionParserToken;
    }
}
