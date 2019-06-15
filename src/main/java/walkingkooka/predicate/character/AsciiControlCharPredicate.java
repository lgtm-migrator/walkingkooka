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

package walkingkooka.predicate.character;

import walkingkooka.text.Ascii;

import java.io.Serializable;

/**
 * A {@link CharPredicate} that only test ascii characters using {@link Ascii#isControl(char)}
 */
final class AsciiControlCharPredicate implements CharPredicate, Serializable {

    /**
     * Singleton
     */
    final static AsciiControlCharPredicate INSTANCE = new AsciiControlCharPredicate();

    /**
     * Private constructor use singleton
     */
    private AsciiControlCharPredicate() {
        super();
    }

    @Override
    public boolean test(final char c) {
        return Ascii.isControl(c);
    }

    // Object

    @Override
    public String toString() {
        return "ASCII control";
    }

    // Serializable

    private Object readResolve() {
        return AsciiControlCharPredicate.INSTANCE;
    }

    private static final long serialVersionUID = -8775161679665358040L;
}
