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

package walkingkooka.tree.expression;

import org.junit.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.text.cursor.parser.spreadsheet.SpreadsheetLabelName;

import java.math.MathContext;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public final class BasicExpressionEvaluationContextTest extends  ExpressionEvaluationContextTestCase<BasicExpressionEvaluationContext> {

    @Test(expected = NullPointerException.class)
    public void testWithNullFunctionsFails() {
        BasicExpressionEvaluationContext.with(null, this.references(), this.mathContext(), this.converter());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullReferencesFails() {
        BasicExpressionEvaluationContext.with(this.functions(), null, this.mathContext(), this.converter());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullMathContextFails() {
        BasicExpressionEvaluationContext.with(this.functions(), this.references(), null, this.converter());
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullConverterFails() {
        BasicExpressionEvaluationContext.with(this.functions(), this.references(), this.mathContext(), null);
    }

    @Test
    public void testFunction() {
        assertEquals(this.functionValue(), this.createContext().function(this.functionName(), this.parameters()));
    }

    @Test
    public void testReferences() {
        assertEquals(this.expressionNode(), this.createContext().reference(this.expressionReference()));
    }

    @Test
    public void testMathContext() {
        assertEquals(this.mathContext(), this.createContext().mathContext());
    }

    @Test
    public void testConvert() {
        assertEquals(Long.valueOf(123L), this.createContext().convert(123.0, Long.class));
    }

    @Override
    protected BasicExpressionEvaluationContext createContext() {
        return BasicExpressionEvaluationContext.with(this.functions(), this.references(), this.mathContext(), this.converter());
    }

    private BiFunction<ExpressionNodeName, List<Object>, Object> functions() {
        return (functionName, parameters) -> {
            Objects.requireNonNull(functionName, "functionName");
            Objects.requireNonNull(parameters, "parameters");

            assertEquals("functionName", this.functionName(), functionName);
            assertEquals("parameters", this.parameters(), parameters);
            return this.functionValue();
        };
    }

    private ExpressionNodeName functionName() {
        return ExpressionNodeName.with("sum");
    }

    private List<Object> parameters() {
        return Lists.of("parameter-1", 2);
    }

    private Object functionValue() {
        return "function-value-234";
    }

    private Function<ExpressionReference, ExpressionNode> references() {
        return (r -> {
            Objects.requireNonNull(r, "references");
            assertEquals("reference", this.expressionReference(), r);
            return this.expressionNode();
        });
    }

    private ExpressionReference expressionReference() {
        return SpreadsheetLabelName.with("label123");
    }

    private ExpressionNode expressionNode() {
        return ExpressionNode.text("expression node 123");
    }

    private MathContext mathContext() {
        return MathContext.DECIMAL128;
    }

    private Converter converter() {
        return Converters.numberLong();
    }

    @Override
    protected Class<BasicExpressionEvaluationContext> type() {
        return BasicExpressionEvaluationContext.class;
    }
}