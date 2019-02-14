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

package walkingkooka.tree.visit;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.type.MemberVisibility;
import walkingkooka.type.MethodAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * A mixin interface with tests and helpers to assist in testing a {@link Visitor}
 */
public interface VisitorTesting<V extends Visitor<T>, T>
        extends ToStringTesting<V>,
        TypeNameTesting<V> {

    /**
     * All visitors must have protected constructors.
     */
    @Test
    default void testAllConstructorsVisibility() throws Exception {
        assertEquals(Lists.empty(),
                Arrays.stream(this.type().getConstructors())
                        .filter(c -> !MemberVisibility.PROTECTED.is(c))
                        .collect(Collectors.toList()));
    }

    @Test
    default void testAcceptNullFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createVisitor().accept(null);
        });
    }

    @Test
    default void testSinglePublicAcceptMethod() {
        final List<Method> defaultAcceptMethods = Arrays.stream(this.type().getMethods())
                .filter(m -> m.getName().startsWith("accept"))
                .collect(Collectors.toList());

        // because of generics two accept methods will be present accept(Object) and accept(N)
        assertEquals(2,
                defaultAcceptMethods.size(),
                () -> "visitor " + this.type().getName() + " should have only 1 default accept method=" + defaultAcceptMethods);
    }

    // all visit methods are protected..

    @Test
    default void testVisitMethodsProtected() {
        this.visitMethodsProtectedCheck("visit");
    }

    @Test
    default void testStartVisitMethodsProtected() {
        this.visitMethodsProtectedCheck("startVisit");
    }

    @Test
    default void testEndVisitMethodsProtected() {
        this.visitMethodsProtectedCheck("endVisit");
    }

    default void visitMethodsProtectedCheck(final String name) {
        final List<Method> wrong = this.allMethods()
                .stream()
                .filter(m -> !MethodAttributes.STATIC.is(m)) // only interested in instance methods.
                .filter(m -> m.getName().startsWith(name))
                .filter(m -> !MemberVisibility.PROTECTED.is(m))
                .collect(Collectors.toList());

        // because of generics two accept methods will be present accept(Object) and accept(N)
        assertEquals(Lists.empty(), wrong, () -> "all " + name + " methods in " + this.type().getName() + " should be protected=" + wrong);
    }

    // all visit methods have a single parameter.

    @Test
    default void testVisitMethodsSingleParameter() {
        this.visitMethodsSingleParameterCheck("visit");
    }

    @Test
    default void testStartVisitMethodsSingleParameter() {
        this.visitMethodsSingleParameterCheck("startVisit");
    }

    @Test
    default void testEndVisitMethodsSingleParameter() {
        this.visitMethodsSingleParameterCheck("endVisit");
    }

    default void visitMethodsSingleParameterCheck(final String name) {
        final List<Method> wrong = this.allMethods()
                .stream()
                .filter(m -> !MethodAttributes.STATIC.is(m)) // only interested in instance methods.
                .filter(m -> m.getName().startsWith(name))
                .filter(m -> MemberVisibility.PROTECTED.is(m))
                .filter(m -> m.getParameterTypes().length != 1)
                .collect(Collectors.toList());

        // because of generics two accept methods will be present accept(Object) and accept(N)
        assertEquals(Lists.empty(), wrong, () -> "all " + name + " methods in " + this.type().getName() + " should have 1 parameter=" + wrong);
    }

    default List<Method> allMethods() {
        final List<Method> all = Lists.array();
        Class<?> type = this.type();
        do {
            all.addAll(Lists.of(type.getMethods()));

            type = type.getSuperclass();
        } while (type != Object.class);

        return all;
    }

    V createVisitor();
}