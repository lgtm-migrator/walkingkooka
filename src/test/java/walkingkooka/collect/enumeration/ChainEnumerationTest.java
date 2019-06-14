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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.JavaVisibility;

import java.util.Enumeration;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

final public class ChainEnumerationTest implements ClassTesting2<ChainEnumeration<String>>,
        EnumerationTesting<ChainEnumeration<String>, String> {

    // constants

    private final static Enumeration<String> FIRST = Enumerations.fake();

    private final static Enumeration<String> SECOND = Enumerations.fake();

    // tests

    @Test
    public void testWithNullFirstFails() {
        assertThrows(NullPointerException.class, () -> {
            ChainEnumeration.with(null, SECOND);
        });
    }

    @Test
    public void testWithNullEnumerationsFails() {
        assertThrows(NullPointerException.class, () -> {
            ChainEnumeration.with(FIRST, null);
        });
    }

    @Test
    public void testWithOne() {
        final Enumeration<?> only = Enumerations.fake();
        assertSame(only, Enumerations.chain(only));
    }

    @Test
    public void testConsume() {
        final Vector<String> first = Lists.vector();
        first.add("1");
        first.add("2");
        final Vector<String> second = Lists.vector();
        second.add("3");
        second.add("4");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        assertTrue(enumerator.hasMoreElements(), "hasMoreElements from 1st enumerator");
        assertSame("1", enumerator.nextElement(), "next from 1st enumerator");

        assertTrue(enumerator.hasMoreElements(), "hasMoreElements from 1st enumerator");
        assertSame("2", enumerator.nextElement(), "next from 1st enumerator");

        assertTrue(enumerator.hasMoreElements(), "hasMoreElements from last enumerator");
        assertSame("3", enumerator.nextElement(), "next from last enumerator");

        assertTrue(enumerator.hasMoreElements(), "hasMoreElements from last enumerator");
        assertSame("4", enumerator.nextElement(), "next from last enumerator");

        assertFalse(enumerator.hasMoreElements(), "hasMoreElements should be false when empty");
        this.checkNextElementFails(enumerator);
    }

    @Test
    public void testConsumeWithoutHasNext() {
        final Vector<String> first = Lists.vector();
        first.add("1");
        first.add("2");
        final Vector<String> second = Lists.vector();
        second.add("3");
        second.add("4");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        assertSame("1", enumerator.nextElement(), "next from 1st enumerator");
        assertSame("2", enumerator.nextElement(), "next from 1st enumerator");
        assertSame("3", enumerator.nextElement(), "next from last enumerator");
        assertSame("4", enumerator.nextElement(), "next from last enumerator");
    }

    @Test
    public void testNextWhenEmpty() {
        final Vector<String> first = Lists.vector();
        first.add("1");
        first.add("2");
        final Vector<String> second = Lists.vector();
        second.add("3");
        second.add("4");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        enumerator.nextElement();
        enumerator.nextElement();
        enumerator.nextElement();
        enumerator.nextElement();

        this.checkNextElementFails(enumerator);
    }

    @Test
    public void testConsumeEverythingFromEmptyEnumerations() {
        final Vector<String> first = Lists.vector();
        final Vector<String> second = Lists.vector();

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        assertFalse(enumerator.hasMoreElements(), "hasMoreElements from 1st enumerator");
        this.checkNextElementFails(enumerator);
    }

    @Test
    public void testFirstEnumerationEmpty() {
        final Vector<String> first = Lists.vector();
        final Vector<String> second = Lists.vector();
        second.add("1");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        assertTrue(enumerator.hasMoreElements(), "hasMoreElements from 2nd enumerator");
        assertSame("1", enumerator.nextElement(), "next from 2nd enumerator");

        assertFalse(enumerator.hasMoreElements(), "hasMoreElements from empty 2nd enumerator");
        this.checkNextElementFails(enumerator);
    }

    @Test
    public void testFirstEnumerationEmptyWithoutHasNext() {
        final Vector<String> first = Lists.vector();
        final Vector<String> second = Lists.vector();
        second.add("1");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        assertSame("1", enumerator.nextElement(), "next from 2nd enumerator");

        assertFalse(enumerator.hasMoreElements(), "hasMoreElements from empty 2nd enumerator");
        this.checkNextElementFails(enumerator);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createEnumeration(), FIRST + "...");
    }

    @Test
    public void testToStringAfterConsumingFirstEnumeration() {
        final Vector<String> first = Lists.vector();
        first.add("1");
        final Vector<String> second = Lists.vector();
        second.add("2");
        second.add("3");

        final Enumeration<String> secondEnumeration = second.elements();
        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                secondEnumeration);
        enumerator.nextElement();
        enumerator.hasMoreElements();

        this.toStringAndCheck(enumerator, secondEnumeration.toString());
    }

    @Test
    public void testToStringWhenEmpty() {
        final Vector<String> first = Lists.vector();
        final Vector<String> second = Lists.vector();
        second.add("1");

        final ChainEnumeration<String> enumerator = this.createEnumeration(first.elements(),
                second.elements());
        enumerator.nextElement();
        enumerator.hasMoreElements();

        this.toStringAndCheck(enumerator, "");
    }

    @Override
    public ChainEnumeration<String> createEnumeration() {
        return this.createEnumeration(FIRST, SECOND);
    }

    private ChainEnumeration<String> createEnumeration(final Enumeration<String> first,
                                                       final Enumeration<String> second) {
        return Cast.to(ChainEnumeration.with(first, second));
    }

    @Override
    public Class<ChainEnumeration<String>> type() {
        return Cast.to(ChainEnumeration.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}