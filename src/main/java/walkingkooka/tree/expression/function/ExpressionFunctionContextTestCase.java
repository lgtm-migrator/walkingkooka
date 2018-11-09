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
import walkingkooka.ContextTestCase;
import walkingkooka.tree.expression.ExpressionNodeName;

public abstract class ExpressionFunctionContextTestCase<C extends ExpressionFunctionContext> extends ContextTestCase<C> {

    @Test(expected = NullPointerException.class)
    public void testFunctionNullNameFails() {
        this.createContext().function(null, ExpressionFunctionContext.NO_PARAMETERS);
    }

    @Test(expected = NullPointerException.class)
    public void testFunctionNullParametersFails() {
        this.createContext().function(ExpressionNodeName.with("sum"), null);
    }

    @Test(expected = NullPointerException.class)
    public void testConvertNullValueFails() {
        this.createContext().convert(null, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testConvertNullTargetTypeFails() {
        this.createContext().convert("value", null);
    }

    @Override
    protected String requiredNameSuffix() {
        return ExpressionFunctionContext.class.getSimpleName();
    }
}
