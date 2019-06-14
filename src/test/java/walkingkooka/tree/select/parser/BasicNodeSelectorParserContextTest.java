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

package walkingkooka.tree.select.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.JavaVisibility;

public final class BasicNodeSelectorParserContextTest implements ClassTesting2<BasicNodeSelectorParserContext>,
        NodeSelectorParserContextTesting<BasicNodeSelectorParserContext> {

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createContext(),
                "decimalPoint='.' exponentSymbol='E' minusSign='-' percentageSymbol='%' plusSign='+'");
    }

    @Override
    public BasicNodeSelectorParserContext createContext() {
        return BasicNodeSelectorParserContext.instance();
    }

    @Override
    public Class<BasicNodeSelectorParserContext> type() {
        return BasicNodeSelectorParserContext.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}