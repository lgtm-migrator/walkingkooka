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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ExpressionStringLengthFunctionTest extends ExpressionFunctionTestCase<ExpressionStringLengthFunction, Number> {

    @Test
    public void testZeroParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2();
        });
    }

    @Test
    public void testOnlyThisFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2(this);
        });
    }

    @Test
    public void testTwoParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2(this, "a1", "b2");
        });
    }

    @Test
    public void testEmptyString() {
        this.applyAndCheck2(parameters(this, ""), 0L);
    }


    @Test
    public void testString() {
        this.applyAndCheck2(parameters(this, "xyz"), 3L);
    }

    @Test
    public void testNumber() {
        this.applyAndCheck2(parameters(this, 123), 3L);
    }

    @Test
    public void testBoolean() {
        this.applyAndCheck2(parameters(this, true), 4L);
    }

    @Test
    public void testToString() {
        assertEquals("string-length", this.createBiFunction().toString());
    }

    @Override
    protected ExpressionStringLengthFunction createBiFunction() {
        return ExpressionStringLengthFunction.INSTANCE;
    }

    @Override
    protected Class<ExpressionStringLengthFunction> type() {
        return ExpressionStringLengthFunction.class;
    }
}
