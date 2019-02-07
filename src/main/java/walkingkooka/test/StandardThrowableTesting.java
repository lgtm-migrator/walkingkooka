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

package walkingkooka.test;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * An interface with default methods which may be mixed into a test.
 */
public interface StandardThrowableTesting<T extends Throwable> extends ThrowableTesting<T>{

    String MESSAGE = "message";
    Exception CAUSE = new Exception();

    @Test
    default void testWithNullMessageFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                this.type().getDeclaredConstructor(String.class).newInstance(null);
            } catch (final InvocationTargetException thrown) {
                throw thrown.getTargetException();
            }
        });
    }

    @Test
    default void testWithEmptyMessageFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                this.type().getDeclaredConstructor(String.class).newInstance("");
            } catch (final InvocationTargetException thrown) {
                throw thrown.getTargetException();
            }
        });
    }

    @Test
    default void testWithMessage() throws Exception {
        this.check(this.type()
                        .getDeclaredConstructor(String.class)
                        .newInstance(MESSAGE),
                MESSAGE,
                null);
    }

    @Test
    default void testConstructorCauseUnavailable() {
        assertThrows(NoSuchMethodException.class, () -> {
            this.type().getDeclaredConstructor(Throwable.class);
        });
    }

    @Test
    default void testWithNullMessageAndCauseExceptionFails() {
        assertThrows(NullPointerException.class, () -> {
            try {
                this.type()
                        .getDeclaredConstructor(String.class, Throwable.class)
                        .newInstance(null, CAUSE);
            } catch (final InvocationTargetException thrown) {
                throw thrown.getTargetException();
            }
        });
    }

    @Test
    default void testWithEmptyMessageAndNonNullCauseFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            try {
                this.type()
                        .getDeclaredConstructor(String.class, Throwable.class)
                        .newInstance("", new Throwable());
            } catch (final InvocationTargetException thrown) {
                throw thrown.getTargetException();
            }
        });
    }

    @Test
    default void testWithMessageAndNullCauseFails() {
        assertThrows(NullPointerException.class, () -> {
            try {
                this.type()
                        .getDeclaredConstructor(String.class, Throwable.class)
                        .newInstance(MESSAGE, null);
            } catch (final InvocationTargetException thrown) {
                throw thrown.getTargetException();
            }
        });
    }

    @Test
    default void testWithMessageAndCause() throws Throwable {
        this.check(this.type()
                        .getDeclaredConstructor(String.class, Throwable.class)
                        .newInstance(MESSAGE, CAUSE),
                MESSAGE,
                CAUSE);
    }
}