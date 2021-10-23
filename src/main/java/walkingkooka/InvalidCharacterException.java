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

package walkingkooka;

import walkingkooka.text.CharSequences;

import java.util.Objects;

/**
 * An {@link IllegalArgumentException} that reports an invalid character within some text.
 */
public class InvalidCharacterException extends InvalidTextException {

    public InvalidCharacterException(final String text,
                                     final int position) {
        super();
        checkText(text, position);
        this.text = text;
        this.position = position;
    }

    public InvalidCharacterException(final String text,
                                     final int position,
                                     final Throwable cause) {
        super(cause);
        checkText(text, position);
        this.text = text;
        this.position = position;
    }

    private static void checkText(final String text, final int position) {
        CharSequences.failIfNullOrEmpty(text, "text");
        if (position < 0 || position >= text.length()) {
            throw new IllegalArgumentException("Invalid position " + position + " not between 0 and " +
                    text.length() + " in " +
                    CharSequences.quoteAndEscape(text));
        }
    }

    public String text() {
        return this.text;
    }

    public InvalidCharacterException setTextAndPosition(final String text, final int position) {
        return this.text.equals(text) && this.position == position ?
                this :
                this.replace(text, position);
    }

    private InvalidCharacterException replace(final String text, final int position) {
        final Throwable cause = this.getCause();
        return null != cause ?
                new InvalidCharacterException(text, position, cause) :
                new InvalidCharacterException(text, position);
    }

    private final String text;

    public int position() {
        return this.position;
    }

    private final int position;

    @Override
    public String getMessage() {
        return "Invalid character " + CharSequences.quoteIfChars(this.text.charAt(this.position)) +
                " at " + this.position +
                " in " + CharSequences.quote(this.text);
    }

    private static final long serialVersionUID = 1L;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return Objects.hash(this.getMessage());
    }

    @Override
    public boolean equals(final Object other) {
        return this == other || other instanceof InvalidCharacterException && this.equals0((InvalidCharacterException) other);
    }

    private boolean equals0(final InvalidCharacterException other) {
        return this.text().equals(other.text()) &&
                this.position() == other.position();
    }
}
