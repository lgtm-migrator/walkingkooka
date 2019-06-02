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

package walkingkooka.tree.text;

import walkingkooka.Cast;
import walkingkooka.NeverError;
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.collect.map.Maps;
import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.JsonNodeException;
import walkingkooka.tree.json.JsonNodeName;
import walkingkooka.tree.json.JsonObjectNode;
import walkingkooka.tree.visit.Visiting;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A {@link TextNode} with a {@link TextStyleName}.
 */
public final class TextStyledNode extends TextParentNode {

    /**
     * Factory that creates a {@link TextStyledNode}.
     */
    static TextStyledNode with(final TextStyleName styleName) {
        checkStyleName(styleName);

        return new TextStyledNode(NO_INDEX, NO_CHILDREN, styleName);
    }

    private TextStyledNode(final int index, final List<TextNode> children, final TextStyleName styleName) {
        super(index, children);

        this.styleName = styleName;
    }

    @Override
    public TextNodeName name() {
        return NAME;
    }

    public final static TextNodeName NAME = TextNodeName.fromClass(TextStyledNode.class);

    public TextStyleName styleName() {
        return this.styleName;
    }

    public TextStyledNode setStyleName(final TextStyleName styleName) {
        checkStyleName(styleName);

        return this.styleName.equals(styleName) ?
                this :
                this.replace1(this.index, this.children, styleName);
    }

    private final TextStyleName styleName;

    private static void checkStyleName(final TextStyleName styleName) {
        Objects.requireNonNull(styleName, "styled");
    }

    @Override
    public TextStyledNode removeParent() {
        return this.removeParent0().cast();
    }

    // children........................................................................................................

    @Override
    public TextStyledNode setChildren(final List<TextNode> children) {
        return this.setChildren0(children).cast();
    }

    @Override
    public TextStyledNode appendChild(final TextNode child) {
        return super.appendChild(child).cast();
    }

    @Override
    public TextStyledNode replaceChild(final TextNode oldChild, final TextNode newChild) {
        return super.replaceChild(oldChild, newChild).cast();
    }

    @Override
    public TextStyledNode removeChild(final int child) {
        return super.removeChild(child).cast();
    }

    // attribute........................................................................................................

    @Override
    public Map<TextPropertyName<?>, Object> attributes() {
        return Maps.empty();
    }

    @Override
    TextNode setAttributesEmptyTextPropertiesMap() {
        return this;
    }

    @Override
    final TextNode setAttributesNonEmptyTextPropertiesMap(final TextPropertiesMap textPropertiesMap) {
        return this.setAttributesNonEmptyTextPropertiesMap0(textPropertiesMap);
    }

    // replace..........................................................................................................

    @Override
    TextStyledNode replace0(final int index,
                            final List<TextNode> children) {
        return new TextStyledNode(index, children, this.styleName);
    }

    private TextStyledNode replace1(final int index,
                                    final List<TextNode> children,
                                    final TextStyleName styleName) {
        return new TextStyledNode(index, children, styleName);
    }

    // isXXXX...... ....................................................................................................

    @Override
    public boolean isProperties() {
        return false;
    }

    @Override
    public boolean isStyled() {
        return true;
    }

    // HasJsonNode.....................................................................................................

    /**
     * Accepts a json object which holds a {@link TextStyledNode}.
     */
    public static TextStyledNode fromJsonNode(final JsonNode node) {
        Objects.requireNonNull(node, "node");

        try {
            return fromJsonNode0(node.objectOrFail());
        } catch (final JsonNodeException cause) {
            throw new IllegalArgumentException(cause.getMessage(), cause);
        }
    }

    private static TextStyledNode fromJsonNode0(final JsonObjectNode node) {
        TextStyleName styleName = null;
        List<TextNode> children = NO_CHILDREN;

        for (JsonNode child : node.children()) {
            switch (child.name().value()) {
                case STYLE:
                    styleName = TextStyleName.fromJsonNode(child);
                    break;
                case VALUES:
                    children = child.arrayOrFail().fromJsonNodeWithTypeList();
                    break;
                default:
                    NeverError.unhandledCase(child, STYLE_PROPERTY, VALUES_PROPERTY);
            }
        }

        if (null == styleName) {
            HasJsonNode.requiredPropertyMissing(STYLE_PROPERTY, node);
        }

        return TextStyledNode.with(styleName)
                .setChildren(children);
    }

    @Override
    public JsonNode toJsonNode() {
        return this.addChildrenValuesJson(JsonNode.object()
                .set(STYLE_PROPERTY, this.styleName.toJsonNode()));
    }

    final static String STYLE = "style";
    final static JsonNodeName STYLE_PROPERTY = JsonNodeName.with(STYLE);

    static {
        HasJsonNode.register("text-styled", TextStyledNode::fromJsonNode, TextStyledNode.class);
    }
    
    // Visitor .................................................................................................

    @Override
    void accept(final TextNodeVisitor visitor) {
        if (Visiting.CONTINUE == visitor.startVisit(this)) {
            this.acceptValues(visitor);
        }
        visitor.endVisit(this);
    }

    // Object ..........................................................................................................

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof TextStyledNode;
    }

    @Override
    boolean equalsIgnoringParentAndChildren(final TextNode other) {
        return other instanceof TextStyledNode &&
                this.equalsIgnoringParentAndChildren0(Cast.to(other));
    }

    private boolean equalsIgnoringParentAndChildren0(final TextStyledNode other) {
        return this.styleName.equals(other.styleName);
    }

    // UsesToStringBuilder..............................................................................................

    @Override
    void buildToStringBefore(final ToStringBuilder b) {
        b.value(this.styleName);
    }
}
