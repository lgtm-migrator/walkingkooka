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

package walkingkooka.type;

import org.junit.Test;
import walkingkooka.collect.set.Sets;
import walkingkooka.test.ClassTestCase;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

public final class MethodAttributesTest extends ClassTestCase<MethodAttributes> {

    @Test
    public void testAbstract() throws Exception {
        methodAndCheck("abstractMethod", MethodAttributes.ABSTRACT);
    }

    @Test
    public void testFinal() throws Exception {
        methodAndCheck("finalMethod", MethodAttributes.FINAL);
    }


    @Test
    public void testNone() throws Exception {
        methodAndCheck("noneMethod");
    }


    @Test
    public void testStatic() throws Exception {
        methodAndCheck("staticMethod", MethodAttributes.STATIC);
    }

    @Test
    public void testFinalStatic() throws Exception {
        methodAndCheck("finalStaticMethod", MethodAttributes.FINAL, MethodAttributes.STATIC);
    }

    private void methodAndCheck(final String name, final MethodAttributes... attributes) throws Exception {
        final Method method = MethodAttributesTestTest.class.getDeclaredMethod(name);
        assertEquals(Sets.of(attributes), MethodAttributes.get(method));
    }

    static abstract class MethodAttributesTestTest {
        abstract void abstractMethod();

        final void finalMethod() {
        }

        final static void finalStaticMethod() {
        }

        void noneMethod() {
        }

        static void staticMethod() {
        }
    }

    @Override
    protected Class<MethodAttributes> type() {
        return MethodAttributes.class;
    }

    @Override
    protected MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }
}