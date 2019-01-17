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

package walkingkooka.compare;

import org.junit.Test;
import walkingkooka.test.PublicStaticHelperTestCase;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

final public class ComparatorsTest extends PublicStaticHelperTestCase<Comparators> {

    @Test
    public void testNormalizeIntZero() {
        this.normalizeIntAndCheck(0, 0);
    }

    @Test
    public void testNormalizeIntPositive() {
        this.normalizeIntAndCheck(2, 1);
    }

    @Test
    public void testNormalizeIntNegative() {
        this.normalizeIntAndCheck(-3, -1);
    }

    private void normalizeIntAndCheck(final int value, final int expected) {
        assertEquals("Normalize (int)" + value,
                expected,
                Comparables.normalize(value));
    }

    @Test
    public void testNormalizeLongZero() {
        this.normalizeLongAndCheck(0L, 0L);
    }

    @Test
    public void testNormalizeLongPositive() {
        this.normalizeLongAndCheck(2L, 1L);
    }

    @Test
    public void testNormalizeLongNegative() {
        this.normalizeLongAndCheck(-3L, -1L);
    }

    private void normalizeLongAndCheck(final long value, final long expected) {
        assertEquals("Normalize (long)" + value,
                expected,
                Comparables.normalize(value));
    }

    // helpers

    @Override
    protected Class<Comparators> type() {
        return Comparators.class;
    }

    @Override
    protected boolean canHavePublicTypes(final Method method) {
        return false;
    }
}
