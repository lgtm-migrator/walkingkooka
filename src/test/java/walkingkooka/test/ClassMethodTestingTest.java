/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.test;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import walkingkooka.type.JavaVisibility;

import java.math.RoundingMode;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ClassMethodTestingTest implements ClassTesting<ClassMethodTesting> {

    // testAllMethodsVisibility..............................................................................................

    @Test
    public void testAllMethodsVisibilityPublicEnum() {
        ClassMethodTesting.testAllMethodsVisibility(RoundingMode.class);
    }

    @Test
    public void testAllMethodsVisibilityPackagePrivateEnum() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPackagePrivateEnum.class);
    }

    enum TestAllMethodsVisibilityPackagePrivateEnum {
        ONLY;
    }

    @Test
    public void testAllMethodsVisibilityMainMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityMainMethod.class);
    }

    static class TestAllMethodsVisibilityMainMethod {

        public static void main(final String[] args) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPackagePrivateStaticMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPackagePrivateStaticMethod.class);
    }

    static class TestAllMethodsVisibilityPackagePrivateStaticMethod {

        static void packagePrivateStaticMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicStaticMethodFails() {
        this.mustFail(() -> ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicStaticMethodFails.class));
    }

    @Test
    public void testAllMethodsVisibilityPublicStaticMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicStaticMethodFails.class, "publicStaticMethod");
    }

    static class TestAllMethodsVisibilityPublicStaticMethodFails {

        static public void publicStaticMethod(final Object param1) {
        }
    }

    @Test
    protected void testAllMethodsVisibilityProtectedStaticMethodFails() {
        this.mustFail(() -> ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityProtectedStaticMethodFails.class));
    }

    static class TestAllMethodsVisibilityProtectedStaticMethodFails {

        static protected void protectedStaticMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPrivateStaticMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPrivateStaticMethod.class);
    }

    static class TestAllMethodsVisibilityPrivateStaticMethod {

        static private void privateStaticMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPackagePrivateInstanceMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPackagePrivateInstanceMethod.class);
    }

    static class TestAllMethodsVisibilityPackagePrivateInstanceMethod {

        void packagePrivateInstanceMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicInstanceMethodFails() {
        this.mustFail(() -> ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicInstanceMethodFails.class));
    }

    static class TestAllMethodsVisibilityPublicInstanceMethodFails {

        public void publicInstanceMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicObjectInstanceMethodOverload() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicObjectInstanceMethodOverload.class);
    }

    static class TestAllMethodsVisibilityPublicObjectInstanceMethodOverload {

        @Override
        public String toString() {
            return "";
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicSuperInstanceMethodOverload() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicSuperInstanceMethodOverload.class);
    }

    static class TestAllMethodsVisibilityPublicSuperInstanceMethodOverload extends Super {

        @Override
        public String override() {
            return "2";
        }
    }

    static class Super {

        public String override() {
            return "1";
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicInstanceMethodGenericInterfaceOverload() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicInstanceMethodGenericInterfaceOverload.class);
    }

    static class TestAllMethodsVisibilityPublicInstanceMethodGenericInterfaceOverload implements Comparator<String> {

        @Override
        public int compare(final String a, final String b) {
            return 0;
        }
    }

    @Test
    public void testAllMethodsVisibilityPublicInstanceMethodExtendsInterface() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPublicInstanceMethodExtendsInterface.class);
    }

    static class TestAllMethodsVisibilityPublicInstanceMethodExtendsInterface implements Interface1 {

        @Override
        public void interfaceMethod() {
        }
    }

    interface Interface1 extends Interface2 {
    }

    interface Interface2 {
        void interfaceMethod();
    }

    @Test
    protected void testAllMethodsVisibilityProtectedInstanceMethodFails() {
        this.mustFail(() -> ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityProtectedInstanceMethodFails.class));
    }

    static class TestAllMethodsVisibilityProtectedInstanceMethodFails {

        protected void protectedInstanceMethod(final Object param1) {
        }
    }

    @Test
    public void testAllMethodsVisibilityPrivateInstanceMethod() {
        ClassMethodTesting.testAllMethodsVisibility(TestAllMethodsVisibilityPrivateInstanceMethod.class);
    }

    static class TestAllMethodsVisibilityPrivateInstanceMethod {

        private void privateInstanceMethod(final Object param1) {
        }
    }

    private void mustFail(final Runnable runnable) {
        boolean failed = false;
        try {
            runnable.run();
        } catch (final AssertionFailedError expected) {
            failed = true;
        }
        assertEquals(true, failed);
    }

    @Override
    public Class<ClassMethodTesting> type() {
        return ClassMethodTesting.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}