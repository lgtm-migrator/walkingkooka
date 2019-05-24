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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.convert.Converter;
import walkingkooka.convert.Converters;
import walkingkooka.math.DecimalNumberContext;
import walkingkooka.math.DecimalNumberContexts;
import walkingkooka.naming.StringName;
import walkingkooka.predicate.Predicates;
import walkingkooka.test.ClassTesting2;
import walkingkooka.tree.TestNode;
import walkingkooka.tree.expression.ExpressionNodeName;
import walkingkooka.tree.expression.function.ExpressionFunction;
import walkingkooka.type.MemberVisibility;

import java.math.MathContext;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class BasicNodeSelectorContextTest implements ClassTesting2<BasicNodeSelectorContext<TestNode, StringName, StringName, Object>>,
        NodeSelectorContextTesting<BasicNodeSelectorContext<TestNode, StringName, StringName, Object>,
                TestNode,
                StringName,
                StringName,
                Object> {

    @Test
    public void testWithNullFinisher() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(null,
                    this.predicate(),
                    this.mapper(),
                    this.functions(),
                    this.converter(),
                    this.decimalNumberContext(),
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullFilter() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    null,
                    this.mapper(),
                    this.functions(),
                    this.converter(),
                    this.decimalNumberContext(),
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullSelectedFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    null,
                    this.functions(),
                    this.converter(),
                    this.decimalNumberContext(),
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullFunctionsFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    this.mapper(),
                    null,
                    this.converter(),
                    this.decimalNumberContext(),
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullConverterFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    this.mapper(),
                    this.functions(),
                    null,
                    this.decimalNumberContext(),
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullDecimalNumberContextFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    this.mapper(),
                    this.functions(),
                    this.converter(),
                    null,
                    this.mathContext(),
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullMathContextFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    this.mapper(),
                    this.functions(),
                    this.converter(),
                    this.decimalNumberContext(),
                    null,
                    this.nodeType());
        });
    }

    @Test
    public void testWithNullNodeTypeFails() {
        assertThrows(NullPointerException.class, () -> {
            BasicNodeSelectorContext.with(this.finisher(),
                    this.predicate(),
                    this.mapper(),
                    this.functions(),
                    this.converter(),
                    this.decimalNumberContext(),
                    this.mathContext(),
                    null);
        });
    }

    @Test
    public void testToString() {
        final BooleanSupplier finisher = this.finisher();
        final Predicate<TestNode> filter = this.predicate();
        final Function<TestNode, TestNode> mapper = this.mapper();
        final Function<ExpressionNodeName, Optional<ExpressionFunction<?>>> functions = this.functions();
        final Converter converter = this.converter();
        final DecimalNumberContext decimalNumberContext = this.decimalNumberContext();
        final MathContext mathContext = this.mathContext();

        this.toStringAndCheck(BasicNodeSelectorContext.with(finisher,
                filter,
                mapper,
                functions,
                converter,
                decimalNumberContext,
                mathContext,
                this.nodeType()),
                finisher + " " + filter + " " + mapper + " " + functions + " " + converter + " " + decimalNumberContext + " " + mathContext);
    }

    @Override
    public String typeNameSuffix() {
        return NodeSelectorContext.class.getSimpleName();
    }

    @Override
    public BasicNodeSelectorContext<TestNode, StringName, StringName, Object> createContext() {
        return BasicNodeSelectorContext.with(this.finisher(),
                this.predicate(),
                this.mapper(),
                this.functions(),
                this.converter(),
                this.decimalNumberContext(),
                this.mathContext(),
                this.nodeType());
    }

    private BooleanSupplier finisher() {
        return () -> false;
    }

    private Predicate<TestNode> predicate() {
        return Predicates.always();
    }

    private Function<TestNode, TestNode> mapper() {
        return Function.identity();
    }

    private Function<ExpressionNodeName, Optional<ExpressionFunction<?>>> functions() {
        return NodeSelectorContexts.basicFunctions();
    }

    private Converter converter() {
        return Converters.fake();
    }

    private DecimalNumberContext decimalNumberContext() {
        return DecimalNumberContexts.fake();
    }

    private MathContext mathContext() {
        return MathContext.DECIMAL32;
    }

    private Class<TestNode> nodeType() {
        return TestNode.class;
    }

    @Override
    public Class<BasicNodeSelectorContext<TestNode, StringName, StringName, Object>> type() {
        return Cast.to(BasicNodeSelectorContext.class);
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
