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

package walkingkooka.text;

import walkingkooka.Cast;
import walkingkooka.test.Testing;
import walkingkooka.tree.Node;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface HasTextLengthTesting extends Testing {

    default void textLengthAndCheck(final HasTextLength has,
                                    final String text) {
        this.textLengthAndCheck(has, text.length());
    }

    default void textLengthAndCheck(final HasTextLength has,
                                    final int length) {
        assertEquals(length,
                has.textLength(),
                () -> (has instanceof HasTextLength ? HasTextLength.class.cast(has).textLength() : has.toString()) + (has instanceof Node ? "\n" + Node.class.cast(has).root() : ""));

        if (has instanceof HasText) {
            final HasText hasText = Cast.to(has);
            assertEquals(length,
                    hasText.text().length(),
                    () -> (has.toString()) + (has instanceof Node ? "\n" + Node.class.cast(has).root() : ""));
        }
    }
}