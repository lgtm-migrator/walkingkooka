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

package walkingkooka.tree.json;

import walkingkooka.Cast;
import walkingkooka.collect.map.Maps;
import walkingkooka.io.printer.IndentingPrinter;
import walkingkooka.io.printer.IndentingPrinters;
import walkingkooka.io.printer.Printers;
import walkingkooka.naming.Name;
import walkingkooka.naming.PathSeparator;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.LineEnding;
import walkingkooka.tree.Node;
import walkingkooka.tree.select.NodeSelectorBuilder;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Base class for all json nodes, all of which are immutable. Note that performing a seemingly mutable operation
 * actually returns a new graph of nodes as would be expected including all parents and the root.
 */
public abstract class JsonNode implements Node<JsonNode, JsonNodeName, Name, Object>, HashCodeEqualsDefined {

    public static JsonArrayNode array() {
        return JsonArrayNode.EMPTY;
    }

    public static JsonBooleanNode booleanNode(final boolean value) {
        return JsonBooleanNode.with(value);
    }

    public static JsonNullNode nullNode() {
        return JsonNullNode.INSTANCE;
    }

    public static JsonNumberNode number(final double value) {
        return JsonNumberNode.with(value);
    }

    public static JsonObjectNode object() {
        return JsonObjectNode.EMPTY;
    }

    public static JsonStringNode string(final String value) {
        return JsonStringNode.with(value);
    }

    /**
     * Absolute {@see NodeSelectorBuilder}
     */
    public static NodeSelectorBuilder<JsonNode, JsonNodeName, Name, Object> absoluteNodeSelectorBuilder() {
        return NodeSelectorBuilder.absolute(PathSeparator.notRequiredAtStart('/'));
    }

    /**
     * Relative {@see NodeSelectorBuilder}
     */
    public static NodeSelectorBuilder<JsonNode, JsonNodeName, Name, Object> relativeNodeSelectorBuilder() {
        return NodeSelectorBuilder.relative(PathSeparator.notRequiredAtStart('/'));
    }

    final static Optional<JsonNode> NO_PARENT = Optional.empty();
    final static int NO_PARENT_INDEX = -1;

    /**
     * Package private ctor to limit sub classing.
     */
    JsonNode(final JsonNodeName name, final int index) {
        this.name = name;
        this.parent = NO_PARENT;
        this.index = index;
    }

    // Name ..............................................................................................

    @Override
    public final JsonNodeName name() {
        return this.name;
    }

    /**
     * Returns an instance with the given name, creating a new instance if necessary.
     */
    JsonNode setName(final JsonNodeName name) {
        return this.name.equals(name) ?
               this :
               this.replaceName(name);

    }

    final JsonNodeName name;

    /**
     * Returns a new instance with the given name.
     */
    private JsonNode replaceName(final JsonNodeName name) {
        return this.wrap(name, this.index);
    }

//    final JsonNode replaceName(final JsonNodeName name) {
//        return this.wrap(name, NO_PARENT, NO_PARENT_INDEX)
//               .replaceChild(this.parent())
//               .cast();
//    }

    // parent ..................................................................................................

    @Override
    public final Optional<JsonNode> parent() {
        return this.parent;
    }

    /**
     * This setter is used to recreate the entire graph including parents of parents receiving new children.
     * It is only ever called by a parent node and is used to adopt new children.
     */
    final JsonNode setParent(final Optional<JsonNode> parent, final int index) {
        final JsonNode copy = this.wrap(this.name, index);
        copy.parent = parent;
        return copy;
    }

    Optional<JsonNode> parent;

//    abstract JsonNode

    /**
     * Sub classes must create a new copy of the parent and replace the identified child using its index or similar,
     * and also sets its parent after creation, returning the equivalent child at the same index.
     */
    abstract JsonNode setChild(final JsonNode newChild);

    /**
     * Only ever called after during the completion of a setChildren, basically used to recreate the parent graph
     * containing this child.
     */
    final JsonNode replaceChild(final Optional<JsonNode> previousParent) {
        return previousParent.isPresent() ?
                previousParent.get()
                        .setChild(this)
                        .children()
                        .get(this.index()) :
                this;
    }

    // index............................................................................................................

    @Override
    public final int index() {
        return this.index;
    }

    int index;

    /**
     * Would be setter that returns an instance with the index, creating a new instance if necessary.
     */
    final JsonNode setIndex(final int index) {
        return this.index == index ?
               this :
               this.replaceIndex(index);
    }

    private JsonNode replaceIndex(final int index) {
        return this.wrap(this.name, index);
    }

    abstract JsonNode wrap(final JsonNodeName name, final int index);

    final JsonNode setIndexAndName(final int index){
        if(index < 0) {
            throw new IllegalArgumentException("Index " + index + " must be greater than 0");
        }
        //return this.wrap(JsonNodeName.index(index), NO_PARENT, index);
        return this.setName(JsonNodeName.index(index))
               .setIndex(index);
    }

    // wrap............................................................................................................

    //abstract JsonNode wrap(final JsonNodeName name, final Optional<JsonNode> parent, final int index);

    // attributes............................................................................................................

    @Override
    public final Map<Name, Object> attributes() {
        return Maps.empty();
    }

    @Override
    public final JsonNode setAttributes(final Map<Name, Object> attributes) {
        Objects.requireNonNull(attributes, "attributes");
        throw new UnsupportedOperationException();
    }

    // isXXX............................................................................................................

    abstract public boolean isArray();

    abstract public boolean isBoolean();

    abstract public boolean isNumber();

    abstract public boolean isNull();

    abstract public boolean isObject();

    abstract public boolean isString();

    final <T extends JsonNode> T cast() {
        return Cast.to(this);
    }

    abstract void accept(final JsonNodeVisitor visitor);

    // Object .......................................................................................................

    @Override
    public final boolean equals(final Object other) {
        return this == other ||
               this.canBeEqual(other) &&
               this.equals0(Cast.to(other));
    }

    abstract boolean canBeEqual(final Object other);

    private boolean equals0(final JsonNode other) {
        return this.equalsAncestors(other) && this.equalsDescendants0(other);
    }

    private boolean equalsAncestors(final JsonNode other) {
        boolean result = this.equalsIgnoringParentAndChildren(other);

        if(result) {
            final Optional<JsonNode> parent = this.parent();
            final Optional<JsonNode> otherParent = other.parent();
            final boolean hasParent = parent.isPresent();
            final boolean hasOtherParent = otherParent.isPresent();

            if (hasParent) {
                if (hasOtherParent) {
                    result = parent.get().equalsAncestors(otherParent.get());
                }
            } else {
                // result is only true if other is false
                result = !hasOtherParent;
            }
        }

        return result;
    }

    final boolean equalsDescendants(final JsonNode other) {
        return this.equalsIgnoringParentAndChildren(other) &&
                this.equalsDescendants0(other);
    }

    abstract boolean equalsDescendants0(final JsonNode other);

    private boolean equalsIgnoringParentAndChildren(final JsonNode other) {
        return this.name.equals(other.name) &&
               this.equalsIgnoringParentAndChildren0(other);
    }

    /**
     * Sub classes should do equals but ignore the parent and children properties.
     */
    abstract boolean equalsIgnoringParentAndChildren0(final JsonNode other);
    /**
     * Pretty prints the entire json graph.
     */
    @Override
    public final String toString() {
        final StringBuilder b = new StringBuilder();
        final IndentingPrinter printer = IndentingPrinters.printer(Printers.stringBuilder(b, LineEnding.SYSTEM));
        this.prettyPrint(printer);
        return b.toString();
    }

    abstract void prettyPrint(final IndentingPrinter printer);
}
