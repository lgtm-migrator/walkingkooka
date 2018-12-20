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

package walkingkooka.predicate.character;

import org.junit.Assert;
import org.junit.Test;
import walkingkooka.Cast;
import walkingkooka.build.BuilderTestCase;
import walkingkooka.text.CharSequences;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

final public class CharPredicateBuilderTest
        extends BuilderTestCase<CharPredicateBuilder, CharPredicate> {

    // constants

    private final static CharPredicate PREDICATE = CharPredicates.fake();

    // tests

    @Test
    public void testCreate() {
        final CharPredicateBuilder builder = this.createBuilder();
        Assert.assertNull("predicate", builder.predicate);
    }

    // or

    @Test
    public void testOrNullPredicateFails() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.or(null);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testOr() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.or(CharPredicates.is('A'));
        builder.or(CharPredicates.is('B'));
        builder.or(CharPredicates.is('C'));
        builder.or(CharPredicates.is('D'));
        builder.or(CharPredicates.is('E'));
        this.checkPredicate(builder);
    }

    @Test
    public void testOr2() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.or(CharPredicates.any("AB"));
        builder.or(CharPredicates.any("CDE"));
        this.checkPredicate(builder);
    }

    // any(String)

    @Test
    public void testAnyNullStringFails() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.any((String) null);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testAnyString() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABCDE");
        this.checkPredicate(builder);
    }

    @Test
    public void testAnyString2() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABC");
        builder.any("DE");
        this.checkPredicate(builder);
    }

    @Test
    public void testAnyString3() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABCD");
        builder.any("ADE");
        this.checkPredicate(builder);
    }

    // any(char[])

    @Test
    public void testAnyNullCharsFails() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.any((char[]) null);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testAnyChars() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABCDE".toCharArray());
        this.checkPredicate(builder);
    }

    @Test
    public void testAnyChars2() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABC".toCharArray());
        builder.any("DE".toCharArray());
        this.checkPredicate(builder);
    }

    @Test
    public void testAnyChars3() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABCD".toCharArray());
        builder.any("ADE".toCharArray());
        this.checkPredicate(builder);
    }

    // and

    @Test
    public void testAndNullPredicate() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.and(null);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testAnd() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABCDEF");
        builder.and(CharPredicates.any("ABCDEXYZ"));
        this.checkPredicate(builder);
    }

    @Test
    public void testAnd2() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("123");
        builder.and(CharPredicates.any("ABC"));
        builder.any("ABCDE123");
        builder.and(CharPredicates.any("ABCDE"));
        this.checkPredicate(builder);
    }

    private void checkPredicate(final CharPredicateBuilder builder) {
        final CharPredicate predicate = builder.build();
        for (char c = 0; c < Character.MAX_VALUE; c++) {
            switch (c) {
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                case 'E':
                    if (false == predicate.test(c)) {
                        Assert.fail("Failed to predicate " + CharSequences.quoteAndEscape(c) + "="
                                + predicate);
                    }
                    break;
                default:
                    if (predicate.test(c)) {
                        Assert.fail(
                                "Should not have matched " + CharSequences.quoteAndEscape(c) + "="
                                        + predicate);
                    }
            }
        }
    }

    // andNot

    @Test
    public void testAndNotNullPredicate() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.andNot(null);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testAndNot() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("ABC");
        builder.andNot(CharPredicates.is('A'));

        final CharPredicate predicate = builder.build();
        assertEquals("'A' should not be matched", false, predicate.test('A'));
        assertEquals("'B' should be matched", true, predicate.test('B'));
        assertEquals("'C' should be matched", true, predicate.test('C'));
        assertEquals("'Z' should be matched", false, predicate.test('Z'));
    }

    // negate

    @Test
    public void testInvertWithoutPredicate() {
        final CharPredicateBuilder builder = this.createBuilder();
        assertSame(builder, builder.negate());
        assertSame("predicate", null, builder.predicate);
    }

    @Test
    public void testInvert() {
        final CharPredicateBuilder builder = this.createBuilder();
        final char c = 'Z';
        builder.or(CharPredicates.is(c));
        assertSame(builder, builder.negate());
        final CharPredicate predicate = builder.build();
        Assert.assertFalse("testing with 'z' should fail", predicate.test(c));

        final CharPredicate predicate2 = builder.negate().build();
        Assert.assertTrue("testing with 'z' should fail", predicate2.test(c));
    }

    // range

    @Test
    public void testRange() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.range('A', 'E');
        this.checkPredicate(builder);
    }

    @Test
    public void testRange2() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.range('A', 'B');
        builder.range('C', 'D');
        builder.range('E', 'E');
        this.checkPredicate(builder);
    }
    
    @Test
    public void testNullStringFails() {
        this.toStringFails(null);
    }

    @Test
    public void testEmptyStringFails() {
        this.toStringFails("");
    }

    @Test
    public void testWhitespaceOnlyStringFails() {
        this.toStringFails("  \t\r\n");
    }

    private void toStringFails(final String toString) {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.predicate = CharPredicateBuilderTest.PREDICATE;
        try {
            builder.toString(toString);
            Assert.fail();
        } catch (final RuntimeException expected) {
        }
        assertSame("predicate", CharPredicateBuilderTest.PREDICATE, builder.predicate);
    }

    @Test
    public void testToString() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any("123");
        final CharPredicate predicate = builder.build();
        assertEquals(predicate.toString(), builder.toString());
    }

    @Test
    public void testToStringAfterToString() {
        final CharPredicateBuilder builder = this.createBuilder();
        final String toString = "XYZ";
        builder.toString("lost");
        builder.toString(toString);
        builder.any("123");
        assertEquals(toString, builder.toString());
    }

    @Test
    public void testBuildWithoutAnything() {
        this.buildFails();
    }

    // Copyable

    @Test
    public void testCopyEmpty() {
        final CharPredicateBuilder builder = this.createBuilder();
        Assert.assertNull("predicate", builder.predicate);
    }

    @Test
    public void testCopyNotEmpty() {
        final CharPredicateBuilder builder = this.createBuilder();
        builder.any('a');

        final CharPredicateBuilder copy = builder.copy();
        assertSame("predicate", builder.predicate, copy.predicate);
    }

    @Override
    protected CharPredicateBuilder createBuilder() {
        return CharPredicateBuilder.empty();
    }

    @Override
    protected Class<CharPredicateBuilder> type() {
        return Cast.to(CharPredicateBuilder.class);
    }

    @Override
    protected Class<CharPredicate> builderProductType() {
        return CharPredicate.class;
    }
}
