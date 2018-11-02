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

import static org.junit.Assert.assertEquals;

public final class ExpressionNodeSelectorPredicateNumberFunctionTest extends ExpressionNodeSelectorPredicateFunctionTestCase<ExpressionNodeSelectorPredicateNumberFunction, Number> {

    @Test(expected = IllegalArgumentException.class)
    public void testZeroParametersFails() {
        this.apply2();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTwoParametersFails() {
        this.apply2("a1", "b2");
    }

    @Test
    public void testInteger() {
        this.applyAndCheck2(parameters(123), 123);
    }

    @Test
    public void testString() {
        this.applyAndCheck2(parameters("123"), 123);
    }

    @Test(expected = NumberFormatException.class)
    public void testStringNonNumericFails() {
        this.apply2("abc");
    }

    @Test
    public void testToString() {
        assertEquals("number", this.createBiFunction().toString());
    }

    @Override
    protected ExpressionNodeSelectorPredicateNumberFunction createBiFunction() {
        return ExpressionNodeSelectorPredicateNumberFunction.INSTANCE;
    }

    @Override
    protected Class<ExpressionNodeSelectorPredicateNumberFunction> type() {
        return ExpressionNodeSelectorPredicateNumberFunction.class;
    }
}