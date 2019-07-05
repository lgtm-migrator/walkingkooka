/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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
package walkingkooka.tree.select.parser;

import walkingkooka.text.cursor.parser.ParserToken;

import java.util.List;

/**
 * Base class for all non binary operand symbols
 */
abstract class NodeSelectorNonBinaryOperandSymbolParserToken extends NodeSelectorSymbolParserToken {

    NodeSelectorNonBinaryOperandSymbolParserToken(final String value, final String text) {
        super(value, text);
    }

    // is..............................................................................................

    @Override
    public final boolean isAndSymbol() {
        return false;
    }

    @Override
    public final boolean isDivideSymbol() {
        return false;
    }

    @Override
    public final boolean isEqualsSymbol() {
        return false;
    }

    @Override
    public final boolean isGreaterThanSymbol() {
        return false;
    }

    @Override
    public final boolean isGreaterThanEqualsSymbol() {
        return false;
    }

    @Override
    public final boolean isLessThanSymbol() {
        return false;
    }

    @Override
    public final boolean isLessThanEqualsSymbol() {
        return false;
    }

    @Override
    public final boolean isMinusSymbol() {
        return false;
    }

    @Override
    public final boolean isModuloSymbol() {
        return false;
    }

    @Override
    public final boolean isMultiplySymbol() {
        return false;
    }

    @Override
    public final boolean isNotEqualsSymbol() {
        return false;
    }

    @Override
    public final boolean isOrSymbol() {
        return false;
    }

    @Override
    public final boolean isPlusSymbol() {
        return false;
    }

    // operator priority..................................................................................................

    @Override
    final int operatorPriority() {
        return IGNORED;
    }

    @Override
    final NodeSelectorBinaryParserToken binaryOperand(final List<ParserToken> tokens, final String text) {
        throw new UnsupportedOperationException();
    }
}
