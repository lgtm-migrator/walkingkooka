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

import walkingkooka.type.JavaVisibility;

public final class NodeSelectorParserTokenVisitorTest implements NodeSelectorParserTokenVisitorTesting<NodeSelectorParserTokenVisitor> {

    @Override
    public void testAllConstructorsVisibility() {
    }

    @Override
    public void testCheckToStringOverridden() {
    }

    @Override
    public NodeSelectorParserTokenVisitor createVisitor() {
        return new NodeSelectorParserTokenVisitor() {};
    }

    @Override
    public String typeNamePrefix() {
        return "";
    }

    @Override
    public Class<NodeSelectorParserTokenVisitor> type() {
        return NodeSelectorParserTokenVisitor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}