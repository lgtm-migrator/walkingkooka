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

import org.junit.Test;
import walkingkooka.Cast;

import static org.junit.Assert.assertEquals;

public class RangeCharPredicateTest extends CharPredicateTestCase<RangeCharPredicate> {

    @Test(expected = IllegalArgumentException.class)
    public void testWithStartAfterEndFails() {
        this.createCharacterPredicate('z', 'a');
    }

    @Test
    public void testWithSameStartAndEnd() {
        assertEquals(CharPredicates.is('a'), this.createCharacterPredicate('a', 'a'));
    }

    @Test
    public void testBeforeRange() {
        this.testFalse('l');
    }

    @Test
    public void testRangeStart() {
        this.testTrue('m');
    }

    @Test
    public void testBetween() {
        this.testTrue('n');
    }

    @Test
    public void testRangeEnd() {
        this.testTrue('p');
    }

    @Test
    public void testAfterRange() {
        this.testFalse('q');
    }

    @Test
    public void testToString() {
        assertEquals("'m'..'p'", this.createCharacterPredicate().toString());
    }

    @Override protected RangeCharPredicate createCharacterPredicate() {
        return Cast.to(this.createCharacterPredicate('m', 'p'));
    }

    private CharPredicate createCharacterPredicate(final char start, final char end){
        return RangeCharPredicate.with(start, end);
    }

    @Override protected Class<RangeCharPredicate> type() {
        return RangeCharPredicate.class;
    }
}
