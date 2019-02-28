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
import walkingkooka.tree.search.SearchNode;

import java.util.Optional;

/**
 * Represents a json null.
 */
public final class JsonNullNode extends JsonLeafNode<Void>{

    /**
     * Simply returns the given {@link JsonNode}.
     */
    static JsonNullNode fromJsonNode0(final JsonNode node) {
        return node.toJsonNode()
                .cast();
    }

    private final static JsonNodeName NAME = JsonNodeName.fromClass(JsonNullNode.class);

    final static JsonNullNode INSTANCE = new JsonNullNode(NAME,NO_INDEX, null);

    JsonNullNode(final JsonNodeName name, final int index, final Void value) {
        super(name, index, value);
    }

    @Override
    public JsonNullNode setName(final JsonNodeName name) {
        checkName(name);
        return this.setName0(name).cast();
    }

    /**
     * Always returns this, only included for completeness.
     */
    public JsonNullNode setValue(final Void value) {
        return this;
    }

    @Override
    JsonNullNode create(final JsonNodeName name, final int index, final Void value) {
        return new JsonNullNode(name, index, value);
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
        return SearchNode.text(text, text);
    }

    // isXXX............................................................................................................

    @Override
    public boolean isBoolean() {
        return false;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isString() {
        return false;
    }

    // functional .................................................................................................

    @Override
    @SuppressWarnings("unchecked")
    public Optional<JsonNullNode> optional() {
        return Optional.empty();
    }

    // Visitor .................................................................................................

    @Override
    public void accept(final JsonNodeVisitor visitor){
        visitor.visit(this);
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof JsonNullNode;
    }

    @Override
    void printJson0(final IndentingPrinter printer) {
        printer.print(String.valueOf(this.value));
    }
}
