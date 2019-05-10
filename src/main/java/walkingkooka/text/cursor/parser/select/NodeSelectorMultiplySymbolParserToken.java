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

import java.util.List;

/**
 * Represents a MOD symbol token.
 */
public final class NodeSelectorMultiplySymbolParserToken extends NodeSelectorArithmeticSymbolParserToken {

    public final static ParserTokenNodeName NAME = parserTokenNodeName(NodeSelectorMultiplySymbolParserToken.class);

    static NodeSelectorMultiplySymbolParserToken with(final String value, final String text) {
        checkValue(value);
        checkTextNullOrWhitespace(text);

        return new NodeSelectorMultiplySymbolParserToken(value, text);
    }

    private NodeSelectorMultiplySymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public NodeSelectorMultiplySymbolParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    NodeSelectorMultiplySymbolParserToken replaceText(final String text) {
        return new NodeSelectorMultiplySymbolParserToken(this.value, text);
    }

    // name................................................................................................

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    // is..........................................................................................................

    @Override
    public boolean isDivideSymbol() {
        return false;
    }

    @Override
    public boolean isEqualsSymbol() {
        return false;
    }

    @Override
    public boolean isMinusSymbol() {
        return false;
    }

    @Override
    public boolean isModuloSymbol() {
        return false;
    }

    @Override
    public boolean isMultiplySymbol() {
        return true;
    }

    @Override
    public boolean isPlusSymbol() {
        return false;
    }

    // operator priority..................................................................................................

    @Override
    int operatorPriority() {
        return MULTIPLY_DIVISION_PRIORITY;
    }

    @Override
    final NodeSelectorBinaryParserToken binaryOperand(final List<ParserToken> tokens, final String text) {
        return NodeSelectorParserToken.multiplication(tokens, text);
    }

    // Visitor................................................................................................

    @Override
    public void accept(final NodeSelectorParserTokenVisitor visitor) {
        visitor.visit(this);
    }

    // Object................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof NodeSelectorMultiplySymbolParserToken;
    }
}