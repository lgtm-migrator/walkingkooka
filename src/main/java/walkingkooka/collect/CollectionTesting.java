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

package walkingkooka.collect;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.test.ToStringTesting;
import walkingkooka.text.CharSequences;

import java.util.Collection;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface CollectionTesting<C extends Collection<E>, E> extends ToStringTesting<C> {

    @Test
    default void testIteratorContainsAndCollection() {
        int size = 0;
        final C collection = this.createCollection();
        final Iterator<E> iterator = collection.iterator();

        while (iterator.hasNext()) {
            final E element = iterator.next();
            containsAndCheck(collection, element);
            size++;
        }

        sizeAndCheck(collection, size);
    }

    @Test
    default void testIsEmptyAndSize() {
        final C collection = this.createCollection();
        final int size = collection.size();
        this.isEmptyAndCheck(collection, 0 == size);
    }

    C createCollection();

    default void containsAndCheck(final Collection<E> collection, final E element) {
        assertTrue(collection.contains(element), () -> collection + " should contain " + CharSequences.quoteIfChars(element));
        assertTrue(collection.containsAll(Lists.of(element)),
                () -> collection + " should contain Collection of " + CharSequences.quoteIfChars(element));
    }

    default void isEmptyAndCheck(final Collection<?> collection, final boolean empty) {
        assertEquals(empty, collection.isEmpty(), ()-> "isEmpty of " + collection);
    }

    default void sizeAndCheck(final Collection<?> collection, final int size) {
        assertEquals(size, collection.size(), () -> "size of " + collection);
        this.isEmptyAndCheck(collection, 0 == size);
    }
}