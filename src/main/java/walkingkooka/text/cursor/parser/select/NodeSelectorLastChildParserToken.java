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
 * Holds an last-child axis
 */
public final class NodeSelectorLastChildParserToken extends NodeSelectorNonSymbolParserToken<String> {

    public final static ParserTokenNodeName NAME = parserTokenNodeName(NodeSelectorLastChildParserToken.class);

    static NodeSelectorLastChildParserToken with(final String value, final String text) {
        checkValue(value);
        checkTextNullOrWhitespace(text);

        return new NodeSelectorLastChildParserToken(value, text);
    }

    private NodeSelectorLastChildParserToken(final String value, final String text) {
        super(value, text);
    }

    @Override
    public NodeSelectorLastChildParserToken setText(final String text) {
        return this.setText0(text).cast();
    }

    @Override
    void checkText(final String text) {
        checkTextNullOrWhitespace(text);
    }

    @Override
    NodeSelectorLastChildParserToken replaceText(final String text) {
        return new NodeSelectorLastChildParserToken(this.value, text);
    }

    // name................................................................................................

    @Override
    public ParserTokenNodeName name() {
        return NAME;
    }

    // is................................................................................................
    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public boolean isAncestor() {
        return false;
    }

    @Override
    public boolean isAttributeName() {
        return false;
    }

    @Override
    public boolean isChild() {
        return false;
    }

    @Override
    public boolean isDescendant() {
        return false;
    }

    @Override
    public boolean isFirstChild() {
        return false;
    }

    @Override
    public boolean isFollowing() {
        return false;
    }

    @Override
    public boolean isFollowingSibling() {
        return false;
    }

    @Override
    public boolean isFunctionName() {
        return false;
    }

    @Override
    public boolean isLastChild() {
        return true;
    }

    @Override
    public boolean isNodeName() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isParentOf() {
        return false;
    }

    @Override
    public boolean isPreceding() {
        return false;
    }

    @Override
    public boolean isPrecedingSibling() {
        return false;
    }

    @Override
    public boolean isQuotedText() {
        return false;
    }

    @Override
    public boolean isSelf() {
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
        return other instanceof NodeSelectorLastChildParserToken;
    }
}
