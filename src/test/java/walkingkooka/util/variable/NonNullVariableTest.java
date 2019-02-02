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
 */

package walkingkooka.util.variable;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class NonNullVariableTest extends VariableTestCase<NonNullVariable<Object>, Object> {

    @Test
    public void testWrapNullFails() {
        assertThrows(NullPointerException.class, () -> {
            NonNullVariable.wrap(null);
        });
    }

    @Override
    @Test
    public void testWith() {
        final Object value = "value";
        assertEquals(value, NonNullVariable.wrap(Variables.with(value)).get());
    }

    @Test
    public void testWrapNonNull() {
        final NonNullVariable<Object> variable = NonNullVariable.wrap(Variables.fake());
        assertSame(variable, NonNullVariable.wrap(variable));
    }

    @Override
    @Test
    public void testReplaceWithNull() {
        // nop
    }

    @Test
    public void testReplaceWithNullFails() {
        final NonNullVariable<Object> variable = NonNullVariable.wrap(Variables.fake());
        assertThrows(NullPointerException.class, () -> {
            variable.set(null);
        });
    }

    @Test
    public void testToString() {
        final Object value = "value";
        assertEquals("non null " + value, NonNullVariable.wrap(Variables.with(value)).toString());
    }

    @Override
    protected NonNullVariable<Object> createVariable() {
        return NonNullVariable.wrap(Variables.with(null));
    }

    @Override
    protected Object createValue() {
        return new Object();
    }
}
