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

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.PathSeparator;
import walkingkooka.tree.HasChildrenValues;
import walkingkooka.tree.Node;
import walkingkooka.tree.select.NodeSelectorBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A {@link Node} wrapper around a {@link ParserToken}. This allows selectors to match and replace nodes typically
 * during a simplification phase.
 */
public abstract class ParserTokenNode implements Node<ParserTokenNode, ParserTokenNodeName, ParserTokenNodeAttributeName, String>,
        HasChildrenValues<ParserToken, ParserTokenNode>,
        Value<ParserToken> {

    /**
     * {@see NodeSelectorBuilder}
     */
    public static NodeSelectorBuilder<ParserTokenNode, ParserTokenNodeName, ParserTokenNodeAttributeName, String> nodeSelectorBuilder() {
        return NodeSelectorBuilder.create(PathSeparator.requiredAtStart('/'));
    }

    /**
     * Wraps the provided node.
     */
    static ParserTokenNode with(final ParserToken token) {
        return with(token, NO_PARENT, UNKNOWN_INDEX);
    }

    static ParserTokenNode with(final ParserToken token, final Optional<ParserTokenNode> parent, final int index) {
        return token instanceof SequenceParserToken ?
                new SequenceParserTokenNode(Cast.to(token), parent, index) :
                new ParserTokenLeafNode(token, parent, index);
    }

    ParserTokenNode(final ParserToken token, final Optional<ParserTokenNode> parent, final int index) {
        this.token = token;
        this.parent = parent;
        this.index = index;
    }

    final SequenceParserToken<ParserToken> asSequenceParserToken() {
        return Cast.to(this.token);
    }

    // Node ...........................................................................................................

    // name ...........................................................................................................

    @Override
    public ParserTokenNodeName name() {
        return this.token.name();
    }

    // parent ...........................................................................................................

    @Override
    public final Optional<ParserTokenNode> parent() {
        return this.parent;
    }

    private final Optional<ParserTokenNode> parent;

    private final static Optional<ParserTokenNode> NO_PARENT = Optional.empty();

    // index ...........................................................................................................

    final static int UNKNOWN_INDEX = Integer.MIN_VALUE;

    @Override
    public final int index() {
        if(UNKNOWN_INDEX == this.index) {
            final Optional<ParserTokenNode> parent = this.parent();
            this.index = parent.isPresent() ?
                    this.findIndex(parent.get().asSequenceParserToken()) :
                    -1;
        }
        return this.index;
    }

    private int index;

    private int findIndex(final SequenceParserToken<ParserToken> parent) {
        return parent.value().indexOf(this);
    }

    // children ...........................................................................................................

    /**
     * Only really implemented by {@link SequenceParserTokenNode}
     */
    abstract ParserTokenNode replaceChild1(final ParserTokenNode child);

    /**
     * Returns the child values as actual {@link ParserToken}
     */
    abstract public List<ParserToken> childrenValues();

    /**
     * Would be setter that accepts actual {@link ParserToken} rather than {@link ParserTokenNode}
     */
    abstract public ParserTokenNode setChildrenValues(final List<ParserToken> childrenValues);

    // attributes ...........................................................................................................

    @Override
    public final Map<ParserTokenNodeAttributeName, String> attributes() {
        if(null==this.attributes) {
            this.attributes = Maps.one(ParserTokenNodeAttributeName.TEXT, this.token.text());
        }
        return this.attributes;
    }

    private Map<ParserTokenNodeAttributeName, String> attributes;

    @Override
    public final ParserTokenNode setAttributes(final Map<ParserTokenNodeAttributeName, String> attributes) {
        throw new UnsupportedOperationException();
    }

    // Value ...........................................................................................................

    @Override
    public final ParserToken value() {
        return this.token;
    }

    final ParserToken token;

    // Object ...........................................................................................................

    @Override
    public final int hashCode() {
        return this.token.hashCode();
    }

    @Override
    public final boolean equals(final Object other) {
        return this == other || other instanceof ParserTokenNode && this.equals0(Cast.to(other));
    }

    private boolean equals0(final ParserTokenNode other) {
        return this.token.equals(other.token);
    }

    @Override
    public final String toString() {
        return this.token.toString();
    }
}
