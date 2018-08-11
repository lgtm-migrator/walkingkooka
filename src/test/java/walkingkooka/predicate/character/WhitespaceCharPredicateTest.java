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

import static org.junit.Assert.assertEquals;

final public class WhitespaceCharPredicateTest
        extends CharPredicateTestCase<WhitespaceCharPredicate> {

    @Test
    public void testWhitespace() {
        this.testTrue(' ');
    }

    @Test
    public void testNotWhitespace() {
        this.testFalse('A');
    }

    @Test
    public void testToString() {
        assertEquals("whitespace", WhitespaceCharPredicate.INSTANCE.toString());
    }

    @Override
    protected WhitespaceCharPredicate createCharacterPredicate() {
        return WhitespaceCharPredicate.INSTANCE;
    }

    @Override
    protected Class<WhitespaceCharPredicate> type() {
        return WhitespaceCharPredicate.class;
    }
}
