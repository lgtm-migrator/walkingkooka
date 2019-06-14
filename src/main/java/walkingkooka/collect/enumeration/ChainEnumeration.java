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

package walkingkooka.collect.enumeration;

import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * An {@link Enumeration} that chains two {@link Enumeration enumerations together}. When the first
 * becomes empty the next is called.
 */
final class ChainEnumeration<E> implements Enumeration<E> {

    /**
     * Creates an {@link ChainEnumeration} from two {@link Enumerations}.
     */
    static <E> Enumeration<E> with(final Enumeration<E> first,
                                   final Enumeration<E>... enumerations) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(enumerations, "enumerations");

        return enumerations.length == 0 ?
                first :
                new ChainEnumeration<E>(first, enumerations.clone());
    }

    /**
     * Private constructor use static factory.
     */
    private ChainEnumeration(final Enumeration<E> first,
                             final Enumeration<E>[] enumerations) {
        super();
        this.current = first;
        this.enumerations = enumerations;
        this.next = 0;
    }

    /**
     * Tests if another element is available. If the current enumeration is empty the next is
     * checked etc.
     */
    @Override
    public boolean hasMoreElements() {
        boolean hasMoreElements = false;
        Enumeration<E> current = this.current;

        for (; ; ) {
            if (null == current) {
                break;
            }
            hasMoreElements = current.hasMoreElements();
            if (hasMoreElements) {
                break;
            }
            current = this.loadNext();
        }

        return hasMoreElements;
    }

    /**
     * Attempts to fetch an element from the current element or if that is empty from the next until
     * none.
     */
    @Override
    public E nextElement() {
        Enumeration<E> current = this.current;
        for (; ; ) {
            if (null == current) {
                throw new NoSuchElementException();
            }
            // current is not empty next!.
            if (current.hasMoreElements()) {
                break;
            }
            current = this.loadNext();
        }
        return current.nextElement();
    }

    /**
     * The current {@link Enumeration}. This will become null when exhausted.
     */
    private Enumeration<E> current;

    /**
     * All the enumerations
     */
    private final Enumeration<E>[] enumerations;

    /**
     * An index that points to the next {@link Enumeration}.
     */
    private int next;

    /**
     * Advances to the next {@link Enumeration} if possible setting {@link #current}.
     */
    private Enumeration<E> loadNext() {
        final Enumeration<E>[] enumerations = this.enumerations;
        final int next = this.next;
        Enumeration<E> current = null;
        if (next < enumerations.length) {
            this.next = next + 1;
            current = enumerations[next];
        }
        this.current = current;
        return current;
    }

    /**
     * Dumps the current {@link Enumeration} with trailing ellipses if it is not the last.
     */
    @Override
    public String toString() {
        final Enumeration<E> current = this.current;
        return null == current ?
                "" :
                this.next == this.enumerations.length ? current.toString() : current + "...";
    }
}