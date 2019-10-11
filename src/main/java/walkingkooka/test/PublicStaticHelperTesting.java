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
import walkingkooka.type.FieldAttributes;
import walkingkooka.type.JavaVisibility;
import walkingkooka.type.MethodAttributes;
import walkingkooka.type.PublicStaticHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Interface with default methods implementing tests and other test helpers.
 */
public interface PublicStaticHelperTesting<H extends PublicStaticHelper> extends ClassTesting2<H>,
        TestSuiteNameTesting<H> {

    @Test
    default void testClassIsFinal() {
        final Class<H> type = this.type();
        assertTrue(Modifier.isFinal(type.getModifiers()), () -> type.getName() + " is NOT final");
    }

    @Test
    default void testOnlyConstructorIsPrivate() throws Exception {
        final Class<H> type = this.type();
        final Constructor<H> constructor = type.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), () -> type.getName() + " is NOT private");
    }

    @Test
    default void testDefaultConstructorThrowsUnsupportedOperationException() throws Exception {
        final Class<H> type = this.type();
        final Constructor<H> constructor = type.getDeclaredConstructor();
        constructor.setAccessible(true);

        final InvocationTargetException cause = assertThrows(InvocationTargetException.class, constructor::newInstance);
        final Throwable target = cause.getTargetException();
        assertTrue(target instanceof UnsupportedOperationException, "Expected UnsupportedOperationException but got " + target);
    }

    @Test
    default void testAllMethodsAreStatic() {
        PublicStaticHelperTesting2.methodFilterAndCheckNone(this.type(),
                m -> false == MethodAttributes.STATIC.is(m),
                "All methods must be static");
    }

    /**
     * Verifies that all static methods are public, package private or private.
     */
    @Test
    default void testCheckVisibilityOfAllStaticMethods() {
        PublicStaticHelperTesting2.methodFilterAndCheckNone(this.type(),
                m -> JavaVisibility.PROTECTED == JavaVisibility.of(m),
                "All methods must be public or package private");
    }

    /**
     * Verifies that the parameter and return types of all public methods are also public.
     */
    @Test
    default void testPublicStaticMethodsParameterAndReturnTypesArePublic() {
        final Predicate<Method> publicReturnTypeAndParameters = (m) -> {
            return JavaVisibility.PUBLIC != JavaVisibility.of(m.getReturnType()) ||
                    Arrays.stream(m.getParameterTypes())
                            .anyMatch(t -> JavaVisibility.PUBLIC != JavaVisibility.of(t));
        };
        PublicStaticHelperTesting2.methodFilterAndCheckNone(this.type(),
                publicReturnTypeAndParameters,
                "All method parameter and return type must be public");
    }

    /**
     * Methods should return true if the method contains package private types. This method is only
     * called when public types are encountered while checking methods by {@link
     * #testPublicStaticMethodsParameterAndReturnTypesArePublic()}.
     */
    boolean canHavePublicTypes(Method method);

    @Test
    default void testContainsZeroInstanceFields() {
        final Class<H> type = this.type();

        assertEquals("",
                Arrays.stream(type.getDeclaredFields())
                        .filter(f -> false == f.getName().contains("jacoco"))
                        .filter(f -> false == FieldAttributes.get(f).contains(FieldAttributes.STATIC))
                        .map(Field::toGenericString)
                        .collect(Collectors.joining(",")));
    }

    /**
     * The type being tested.
     */
    Class<H> type();
}
