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

package walkingkooka.color;

import org.junit.jupiter.api.Test;
import walkingkooka.test.ClassTesting2;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.test.IsMethodTesting;
import walkingkooka.test.SerializationTesting;
import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.type.MemberVisibility;
import walkingkooka.type.MethodAttributes;

import java.lang.reflect.Method;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract public class HslComponentTestCase<C extends HslComponent> implements ClassTesting2<C>,
        HashCodeEqualsDefinedTesting<C>,
        IsMethodTesting<C>,
        SerializationTesting<C>,
        ToStringTesting<C>,
        TypeNameTesting<C> {

    HslComponentTestCase() {
        super();
    }

    // constants

    private final static float LIGHTNESS = 0.5f;

    // tests

    @Test
    public final void testBelowLowerBoundsFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createHslComponent(HueHslComponent.MIN - 0.1f);
        });
    }

    @Test
    public final void testAboveUpperBoundsFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createHslComponent(HueHslComponent.MAX + 0.1f);
        });
    }

    @Test
    public final void testWith() {
        final C component = this.createHslComponent(LIGHTNESS);
        assertEquals(LIGHTNESS, component.value(), 0.1, "value");
    }

    // set

    @Test
    public final void testSameValue() {
        final C component = this.createHslComponent(LIGHTNESS);
        assertSame(component, component.setValue(LIGHTNESS));
    }

    @Test
    public final void testSetBelowLowerBoundsFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createHslComponent(this.min()).setValue(this.min() - 0.1f);
        });
    }

    @Test
    public final void testSetAboveUpperBoundsFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createHslComponent(this.min()).setValue(this.max() + 0.1f);
        });
    }

    @Test
    public final void testSet() {
        final float min = this.min();
        final float value = (min + this.max()) / 2;
        final C original = this.createHslComponent(min);
        final HslComponent component = original.setValue(value);
        assertNotSame(original, component);
        assertEquals(value, component.value(), 0.1, "value");
    }

    // add

    @Test
    public final void testAddZero() {
        final C component = this.createHslComponent(LIGHTNESS);
        assertSame(component, component.add(0));
    }

    @Test
    public final void testAddZero2() {
        final C component = this.createHslComponent(LIGHTNESS);
        assertSame(component, component.add(0));
    }

    @Test
    public final void testAdd() {
        final float min = this.min();
        this.addAndCheck(min, 0.1f, min + 0.1f);
    }

    @Test
    public final void testAddUnderflows() {
        final float min = this.min();
        final float max = this.max();
        this.addAndCheck((min + max) / 2, -max, min);
    }

    @Test
    public final void testAddOverflow() {
        final float min = this.min();
        final float max = this.max();
        this.addAndCheck((min + max) / 2, max, max);
    }

    private void addAndCheck(final float initial, final float delta, final float expected) {
        final C component = this.createHslComponent(initial);
        final HslComponent added = component.add(delta);
        assertEquals(expected, added.value(), 0.1f, "value");
    }

    @Test
    public final void testIsMethods() throws Exception {
        final C component = this.createHslComponent();
        final String name = component.getClass().getSimpleName();

        final String isMethodName = "is" + CharSequences.capitalize(name.substring(0, name.length() - "HslComponent".length()));

        for(Method method : component.getClass().getMethods()) {
            if(MethodAttributes.STATIC.is(method)) {
                continue;
            }
            final String methodName = method.getName();

            if(!methodName.startsWith("is")) {
                continue;
            }
            assertEquals(methodName.equals(isMethodName),
                    method.invoke(component),
                    method + " returned");
        }
    }

    // toString

    @Test
    public final void testToString() {
        this.toStringAndCheck(this.createHslComponent(LIGHTNESS), String.valueOf(LIGHTNESS));
    }

    final C createHslComponent() {
        return this.createHslComponent(LIGHTNESS);
    }

    abstract C createHslComponent(float value);

    abstract float min();

    abstract float max();

    @Override
    public final MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }

    @Override
    public final C createObject() {
        return this.createHslComponent();
    }

    // IsMethodTesting.................................................................................................

    @Override
    public final C createIsMethodObject() {
        return this.createObject();
    }

    @Override
    public final String isMethodTypeNamePrefix() {
        return "";
    }

    @Override
    public final String isMethodTypeNameSuffix() {
        return HslComponent.class.getSimpleName();
    }

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> false;
    }

    // TypeNameTesting .........................................................................................

    @Override
    public final String typeNamePrefix() {
        return "";
    }

    @Override
    public final String typeNameSuffix() {
        return HslComponent.class.getSimpleName();
    }

}
