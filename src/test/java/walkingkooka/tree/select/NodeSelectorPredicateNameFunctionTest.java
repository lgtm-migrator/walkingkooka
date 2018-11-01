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

package walkingkooka.tree.select;

import org.junit.Test;
import walkingkooka.tree.Node;

import static org.junit.Assert.assertEquals;

public final class NodeSelectorPredicateNameFunctionTest extends NodeSelectorPredicateFunctionTestCase<NodeSelectorPredicateNameFunction, String> {

    @Test
    public void testExecuteFunction() {
        final String name = "Abc123";

        this.applyAndCheck2(this.createBiFunction(),
                list(),
                new FakeNodeSelectorPredicateExpressionEvaluationContext() {
                    @Override
                    public Node node() {
                        return TestFakeNode.node(name);
                    }
                },
                name);
    }

    @Test
    public void testToString() {
        assertEquals("name", this.createBiFunction().toString());
    }

    @Override
    protected NodeSelectorPredicateNameFunction createBiFunction() {
        return NodeSelectorPredicateNameFunction.INSTANCE;
    }

    @Override
    protected Class<NodeSelectorPredicateNameFunction> type() {
        return NodeSelectorPredicateNameFunction.class;
    }
}