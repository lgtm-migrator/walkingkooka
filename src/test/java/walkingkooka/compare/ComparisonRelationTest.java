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

package walkingkooka.compare;

import org.junit.Test;
import walkingkooka.test.PublicClassTestCase;

import java.util.function.Predicate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public final class ComparisonRelationTest extends PublicClassTestCase<ComparisonRelation> {

    @Test
    public void testSymbolEQ() {
        this.symbolAndCheck(ComparisonRelation.EQ, "=");
    }

    @Test
    public void testSymbolGT() {
        this.symbolAndCheck(ComparisonRelation.GT, ">");
    }

    @Test
    public void testSymbolGTE() {
        this.symbolAndCheck(ComparisonRelation.GTE, ">=");
    }

    @Test
    public void testSymbolLT() {
        this.symbolAndCheck(ComparisonRelation.LT, "<");
    }

    @Test
    public void testSymbolLTE() {
        this.symbolAndCheck(ComparisonRelation.LTE, "<=");
    }

    @Test
    public void testSymbolNE() {
        this.symbolAndCheck(ComparisonRelation.NE, "!=");
    }

    private void symbolAndCheck(final ComparisonRelation relation, final String pattern) {
        assertEquals("pattern for " + relation, pattern, relation.symbol());
    }

    @Test
    public void testPredicate() {
        final Predicate<String> predicate = ComparisonRelation.EQ.predicate("M");
        assertTrue("EQ \"M\"", predicate.test("M"));
        assertFalse("EQ \"Z\"", predicate.test("Z"));
    }

    @Test
    public void testInvert() {
        for(ComparisonRelation relation: ComparisonRelation.values()) {
            predicateAndInvertedPredicateTest(relation, 0, -1);
            predicateAndInvertedPredicateTest(relation, 0, 0);
            predicateAndInvertedPredicateTest(relation, 0, +1);
        }
    }

    private void predicateAndInvertedPredicateTest(final ComparisonRelation relation, final int value, final int value2) {
        final boolean result = relation.predicate(value).test(value2);
        final ComparisonRelation inverted = relation.invert();
        final boolean invertedResult = inverted.predicate(value).test(value2);

        assertNotEquals(result + " inverted " + inverted + " " + value + " " + value2,result, invertedResult);
    }

    @Test(expected = NullPointerException.class)
    public void testFindWithSymbolNullFails() {
        ComparisonRelation.findWithSymbol(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindWithSymbolUnknownFails() {
        ComparisonRelation.findWithSymbol("?");
    }

    @Test
    public void testFindWithSymbol() {
        for (ComparisonRelation relation : ComparisonRelation.values()) {
            assertSame(relation, ComparisonRelation.findWithSymbol(relation.symbol()));
        }
    }

    @Test
    public void testToString() {
        assertEquals("EQ", ComparisonRelation.EQ.toString());
    }

    @Override
    protected Class<ComparisonRelation> type() {
        return ComparisonRelation.class;
    }
}
