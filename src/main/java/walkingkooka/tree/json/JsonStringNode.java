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

import walkingkooka.io.printer.IndentingPrinter;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.search.SearchNode;

import java.util.Objects;

/**
 * Represents an immutable json string.
 */
public final class JsonStringNode extends JsonLeafNode<String>{

    /**
     * Simply returns the given {@link JsonNode}.
     */
    static JsonStringNode fromJsonNode0(final JsonNode node) {
        return node.cast();
    }

    static JsonStringNode with(final String value) {
        Objects.requireNonNull(value, "value");
        return new JsonStringNode(NAME, NO_INDEX, value);
    }

    private final static JsonNodeName NAME = JsonNodeName.fromClass(JsonStringNode.class);

    private JsonStringNode(final JsonNodeName name, final int index, final String value) {
        super(name, index, value);
    }

    @Override
    public JsonStringNode setName(final JsonNodeName name) {
        checkName(name);
        return this.setName0(name).cast();
    }

    public JsonStringNode setValue(final String value) {
        return this.setValue0(value).cast();
    }

    @Override
    JsonStringNode create(final JsonNodeName name, final int index, final String value) {
        return new JsonStringNode(name, index, value);
    }

    // HasJsonNode...............................................................................................

    @Override
    JsonNodeName defaultName() {
        return NAME;
    }

    // HasSearchNode...............................................................................................

    @Override
    public SearchNode toSearchNode() {
        final String text = this.text();
        return SearchNode.text(text, this.value());
    }

    // isXXX......................................................................................................

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isString() {
        return true;
    }

    @Override
    public void accept(final JsonNodeVisitor visitor){
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonStringNode;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(CharSequences.quoteAndEscape(this.value));
    }
}
