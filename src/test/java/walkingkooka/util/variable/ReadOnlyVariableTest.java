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

final public class ReadOnlyVariableTest extends VariableTestCase<ReadOnlyVariable<Object>, Object> {

    @Test
    public void testWrapNullVariableFails() {
        assertThrows(NullPointerException.class, () -> {
            ReadOnlyVariable.wrap(null);
        });
    }

    @Override
    @Test
    public void testWith() {
        final Object value = "value";
        assertEquals(value, ReadOnlyVariable.wrap(Variables.with(value)).get());
    }

    @Test
    public void testWrapReadOnly() {
        final ReadOnlyVariable<Object> variable = ReadOnlyVariable.wrap(Variables.fake());
        assertSame(variable, ReadOnlyVariable.wrap(variable));
    }

    @Override
    @Test
    public void testSet() {
        // nop
    }

    @Test
    public void testSetFails() {
        final ReadOnlyVariable<Object> variable = ReadOnlyVariable.wrap(Variables.fake());
        assertThrows(UnsupportedOperationException.class, () -> {
            variable.set(new Object());
        });
    }

    @Override
    @Test
    public void testReplaceWithNull() {
        // nop
    }

    @Test
    public void testReplaceFails() {
        final ReadOnlyVariable<Object> variable = ReadOnlyVariable.wrap(Variables.fake());
        assertThrows(UnsupportedOperationException.class, () -> {
            variable.set(null);
        });
    }

    @Test
    public void testToString() {
        final Object value = "value";
        assertEquals("read only " + value, ReadOnlyVariable.wrap(Variables.with(value)).toString());
    }

    @Override
    protected ReadOnlyVariable<Object> createVariable() {
        return ReadOnlyVariable.wrap(Variables.with(null));
    }

    @Override
    protected Object createValue() {
        return new Object();
    }
}
