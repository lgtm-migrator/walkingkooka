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
import walkingkooka.test.ClassTestCase;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ExpressionConcatFunctionTest extends ClassTestCase<ExpressionConcatFunction>
        implements ExpressionFunctionTesting<ExpressionConcatFunction, String> {

    @Test
    public void testZeroParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2();
        });
    }

    @Test
    public void testOneString() {
        this.applyAndCheck2(parameters(this, "a1"), "a1");
    }

    @Test
    public void testTwoStrings() {
        this.applyAndCheck2(parameters(this, "a1", "b2"), "a1b2");
    }

    @Test
    public void testThreeStrings() {
        this.applyAndCheck2(parameters(this, "a1", "b2", "c3"), "a1b2c3");
    }

    @Test
    public void testFourStrings() {
        this.applyAndCheck2(parameters(this, "a1", "b2", "c3", "d4"), "a1b2c3d4");
    }

    @Test
    public void testBoolean() {
        this.applyAndCheck2(parameters(this, "a1", true, false), "a1truefalse");
    }

    @Test
    public void testNumbers() {
        this.applyAndCheck2(parameters(this, "a1", 1, 234L), "a11234");
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createBiFunction(), "concat");
    }

    @Override
    public ExpressionConcatFunction createBiFunction() {
        return ExpressionConcatFunction.INSTANCE;
    }

    @Override
    public Class<ExpressionConcatFunction> type() {
        return ExpressionConcatFunction.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
