

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

package walkingkooka.text;

import org.junit.jupiter.api.Test;

public final class GlobPatternComponentWildcardTest extends GlobPatternComponentTestCase<GlobPatternComponentWildcard> {

    private final static int MIN = 1;
    private final static int MAX = 234;

    @Test
    public void testDifferentMin() {
        this.checkNotEquals(
                GlobPatternComponentWildcard.with(MIN + 1, MAX)
        );
    }

    @Test
    public void testDifferentMax() {
        this.checkNotEquals(
                GlobPatternComponentWildcard.with(MIN, 1 + MAX)
        );
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(
                this.createObject(),
                "1..234"
        );
    }

    @Test
    public void testToStringMax() {
        this.toStringAndCheck(
            GlobPatternComponentWildcard.with(12, Integer.MAX_VALUE), "12*"
        );
    }

    @Override
    public GlobPatternComponentWildcard createObject() {
        return GlobPatternComponentWildcard.with(MIN, MAX);
    }

    @Override
    public Class<GlobPatternComponentWildcard> type() {
        return GlobPatternComponentWildcard.class;
    }
}