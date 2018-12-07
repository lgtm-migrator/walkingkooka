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

package walkingkooka.net.http;

import walkingkooka.net.header.HeaderValueException;
import walkingkooka.predicate.character.CharPredicate;
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.text.CharSequences;

/**
 * Base parser for any parser in this package.
 */
abstract class HttpHeaderParser {

    /**
     * Package private to limit sub classing.
     */
    HttpHeaderParser(final String text) {
        CharSequences.failIfNullOrEmpty(text, "Text");
        this.text = text;
        this.position = 0;
    }

    final static char DOUBLE_QUOTE = '"';

    /**
     * Matches any whitespace characters.<br>
     * <a href="https://en.wikipedia.org/wiki/Augmented_Backus%E2%80%93Naur_form"></a>
     * <pre>
     * HS | SP
     * </pre>
     */
    final static CharPredicate WHITESPACE = CharPredicates.any("\u0009\u0020")
            .setToString("SP|HTAB");

    /**
     * Reports an invalid character within the unparsed text.
     */
    final void failInvalidCharacter() {
        this.fail(invalidCharacter(this.position, text));
    }

    /**
     * Builds a message to report an invalid or unexpected character.
     */
    static String invalidCharacter(final int position, final String text) {
        return "Invalid character " + CharSequences.quoteIfChars(text.charAt(position)) +
                " at " + position +
                " in " + CharSequences.quoteAndEscape(text);
    }

    /**
     * Reports a missing closing quote.
     */
    static String missingClosingQuote(final String text) {
        return "Missing closing '\"' " + CharSequences.quote(text);
    }

    /**
     * Reports a failure.
     */
    static <T> T fail(final String message) {
        throw new HeaderValueException(message);
    }

    final String text;
    int position;

    @Override
    public final String toString() {
        return this.position + " " + CharSequences.quote(this.text);
    }
}