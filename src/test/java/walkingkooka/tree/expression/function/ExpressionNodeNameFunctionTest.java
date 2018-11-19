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

import org.junit.Test;
import walkingkooka.naming.Names;
import walkingkooka.naming.StringName;
import walkingkooka.tree.FakeNode;

import static org.junit.Assert.assertEquals;

public final class ExpressionNodeNameFunctionTest extends ExpressionFunctionTestCase<ExpressionNodeNameFunction, String> {

    private final static String NAME = "Abc123";

    @Test
    public void testExecuteFunction() {
        this.applyAndCheck2(this.createBiFunction(),
                parameters(new TestFakeNode()),
                NAME);
    }

    final static class TestFakeNode extends FakeNode<TestFakeNode, StringName, StringName, Object> {

        @Override
        public StringName name() {
            return Names.string(NAME);
        }
    }

    @Test
    public void testToString() {
        assertEquals("name", this.createBiFunction().toString());
    }

    @Override
    protected ExpressionNodeNameFunction createBiFunction() {
        return ExpressionNodeNameFunction.INSTANCE;
    }

    @Override
    protected Class<ExpressionNodeNameFunction> type() {
        return ExpressionNodeNameFunction.class;
    }
}