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

package walkingkooka.tree.expression.function;

import org.junit.jupiter.api.Test;
import walkingkooka.naming.StringName;
import walkingkooka.tree.FakeNode;
import walkingkooka.tree.select.NodeSelector;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ExpressionNodePositionFunctionTest extends ExpressionFunctionTestCase<ExpressionNodePositionFunction, Number> {

    private final static int INDEX = 123;

    @Test
    public void testInteger() {
        this.applyAndCheck2(
                this.createBiFunction(),
                parameters(new TestFakeNode()),
                Long.valueOf(INDEX + NodeSelector.INDEX_BIAS));
    }

    final static class TestFakeNode extends FakeNode<ExpressionNodeNameFunctionTest.TestFakeNode, StringName, StringName, Object> {

        @Override
        public int index() {
            return INDEX;
        }
    }

    @Test
    public void testToString() {
        assertEquals("position", this.createBiFunction().toString());
    }

    @Override
    protected ExpressionNodePositionFunction createBiFunction() {
        return ExpressionNodePositionFunction.INSTANCE;
    }

    @Override
    protected Class<ExpressionNodePositionFunction> type() {
        return ExpressionNodePositionFunction.class;
    }
}
