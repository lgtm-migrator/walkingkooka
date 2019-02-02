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

public final class ExpressionSubstringBeforeFunctionTest extends ExpressionFunctionTestCase<ExpressionSubstringBeforeFunction, String> {

    @Test
    public void testZeroParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2();
        });
    }

    @Test
    public void testOnlyThisParameterFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2(this);
        });
    }

    @Test
    public void testOneParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2(this, "a1");
        });
    }


    @Test
    public void testThreeParametersFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.apply2(this, "a1", 2, 3);
        });
    }

    @Test
    public void testMissing() {
        this.applyAndCheck2(parameters(this, "abcdef", "z"), "");
    }

    @Test
    public void testMissingWrongCase() {
        this.applyAndCheck2(parameters(this, "abcdef", "A"), "");
    }

    @Test
    public void testPresent() {
        this.applyAndCheck2(parameters(this, "abc", "c"), "ab");
    }

    @Test
    public void testPresent2() {
        this.applyAndCheck2(parameters(this, "abcdef", "de"), "abc");
    }

    @Test
    public void testPresent3() {
        this.applyAndCheck2(parameters(this, "abcdef", "bcde"), "a");
    }

    @Test
    public void testPresentFirst() {
        this.applyAndCheck2(parameters(this, "abcd", "a"), "");
    }

    @Test
    public void testToString() {
        assertEquals("substring-before", this.createBiFunction().toString());
    }

    @Override
    protected ExpressionSubstringBeforeFunction createBiFunction() {
        return ExpressionSubstringBeforeFunction.INSTANCE;
    }

    @Override
    protected Class<ExpressionSubstringBeforeFunction> type() {
        return ExpressionSubstringBeforeFunction.class;
    }
}
