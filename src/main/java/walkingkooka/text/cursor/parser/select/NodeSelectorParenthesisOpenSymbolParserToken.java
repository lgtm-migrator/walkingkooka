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

import walkingkooka.text.cursor.parser.ParserTokenNodeName;

/**
 * Represents a open object (parens) symbol token.
 */
public final class NodeSelectorParenthesisOpenSymbolParserToken extends NodeSelectorSymbolParserToken {

    public final static ParserTokenNodeName NAME = parserTokenNodeName(NodeSelectorParenthesisOpenSymbolParserToken.class);

    static NodeSelectorParenthesisOpenSymbolParserToken with(final String value, final String text) {
        checkValue(value);
        checkTextNullOrWhitespace(text);

        return new NodeSelectorParenthesisOpenSymbolParserToken(value, text);
    }

    private NodeSelectorParenthesisOpenSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public NodeSelectorParenthesisOpenSymbolParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    NodeSelectorParenthesisOpenSymbolParserToken replaceText(final String text) {
        return new NodeSelectorParenthesisOpenSymbolParserToken(this.value, text);
    }

    // name................................................................................................

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    // is..........................................................................................................

    @Override
    public boolean isAndSymbol() {
        return false;
    }

    @Override
    public boolean isAtSignSymbol() {
        return false;
    }

    @Override
    public boolean isBracketOpenSymbol() {
        return false;
    }

    @Override
    public boolean isBracketCloseSymbol() {
        return false;
    }

    @Override
    public boolean isEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isGreaterThanSymbol() {
        return false;
    }

    @Override
    public boolean isGreaterThanEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isLessThanSymbol() {
        return false;
    }

    @Override
    public boolean isLessThanEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isNotEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isOrSymbol() {
        return false;
    }

    @Override
    public boolean isParameterSeparatorSymbol() {
        return false;
    }

    @Override
    public boolean isParenthesisOpenSymbol() {
        return true;
    }

    @Override
    public boolean isParenthesisCloseSymbol() {
        return false;
    }

    @Override
    public boolean isSlashSeparatorSymbol() {
        return false;
    }

    @Override
    public boolean isWhitespace() {
        return false;
    }

    // Visitor................................................................................................

    @Override
    public void accept(final NodeSelectorParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof NodeSelectorParenthesisOpenSymbolParserToken;
    }
}