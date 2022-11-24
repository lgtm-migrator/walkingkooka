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

import walkingkooka.Value;

import java.util.Arrays;
import java.util.Objects;

/**
 * A {@link Value} and {@link CharSequence} that holds the indentation that may be used by a printer.
 */
final public class Indentation implements Value<String>, CharSequence {

    /**
     * The max length of the last constant.
     */
    final static int SPACES_COUNT = 30;

    private final static char INDENTATION_CONSTANT_CHAR = ' ';

    static {
        final Indentation[] indentations = new Indentation[Indentation.SPACES_COUNT];
        final StringBuilder b = new StringBuilder();

        for (int i = 0; i < Indentation.SPACES_COUNT; i++) {
            indentations[i] = new Indentation(b.toString());
            b.append(INDENTATION_CONSTANT_CHAR);
        }

        EMPTY = indentations[0];
        SPACES2 = indentations[2];
        SPACES4 = indentations[4];

        SPACES = indentations;
    }

    /**
     * Holds an {@link Indentation} which is empty.
     */
    public final static Indentation EMPTY;

    /**
     * An {@link Indentation} with 2 spaces.
     */
    public final static Indentation SPACES2;

    /**
     * An {@link Indentation} with 4 spaces.
     */
    public final static Indentation SPACES4;

    /**
     * Holds indentations with 0 to {@link #SPACES_COUNT} spaces.
     */
    final static Indentation[] SPACES;

    /**
     * Creates an {@link Indentation} with the given character repeated so many times.
     */
    public static Indentation with(final char c,
                                   final int count) {
        if ('\n' == c) {
            throw new IllegalArgumentException("Repeating character must not be NL");
        }
        if ('\r' == c) {
            throw new IllegalArgumentException("Repeating character must not be CR");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Count " + count + " must be greater than 0");
        }

        final Indentation indentation;
        if ((' ' == c) && (count < Indentation.SPACES_COUNT)) {
            indentation = Indentation.SPACES[count];
        } else {
            final char[] array = new char[count];
            Arrays.fill(array, c);
            indentation = new Indentation(new String(array));
        }
        return indentation;
    }

    /**
     * Creates an {@link Indentation} with the given {@link String indentation}.
     */
    public static Indentation with(final String indentation) {
        Objects.requireNonNull(indentation, "");
        checkCharacterAbsent(indentation, '\n', "NL");
        checkCharacterAbsent(indentation, '\r', "CR");

        Indentation result;

        Exit:
        for (; ; ) {
            final int length = indentation.length();

            // too long can not be a constant
            if (length > Indentation.SPACES_COUNT) {
                result = new Indentation(indentation);
                break;
            }
            for (int i = 0; i < length; i++) {
                if (INDENTATION_CONSTANT_CHAR != indentation.charAt(i)) {

                    // includes something other than space can not be a constant.
                    result = new Indentation(indentation);
                    break Exit;
                }
            }
            result = Indentation.SPACES[length];
            break;
        }

        return result;
    }

    private static void checkCharacterAbsent(final String indentation,
                                             final char c,
                                             final String label) {
        if (indentation.indexOf(c) != -1) {
            throw new IllegalArgumentException(
                    "Indentation contains " + label + "=" + CharSequences.escape(indentation));
        }
    }

    /**
     * Private constructor use static factory.
     */
    private Indentation(final String value) {
        super();
        this.value = value;
    }

    /**
     * Returns a {@link Indentation} that is a multiple of the given count. A count of less than zero will throw a
     * {@link IllegalArgumentException}.
     */
    public Indentation repeat(final int count) {
        final Indentation indentation;
        switch (count) {
            case 0:
                indentation = EMPTY;
                break;
            case 1:
                indentation = this;
                break;
            default:
                if (count < 0) {
                    throw new IllegalArgumentException("Invalid count " + count + " < 0");
                }

                final String value = this.value;

                final StringBuilder b = new StringBuilder(value.length() * count);

                for (int i = 0; i < count; i++) {
                    b.append(value);
                }

                indentation = with(b.toString());
                break;
        }

        return indentation;
    }

    /**
     * Appends another {@link Indentation} with the current into a larger longer {@link
     * Indentation}.
     */
    public Indentation append(final Indentation indentation) {
        Objects.requireNonNull(indentation, "indentation");

        Indentation result;

        for (; ; ) {
            final String indentationValue = indentation.value;
            final int appendLength = indentationValue.length();

            // appending empty return this.
            if (0 == appendLength) {
                result = this;
                break;
            }

            final String value = this.value;

            // is a constant check if indentation is also a constant, might be able to return a constant.
            if ((appendLength < Indentation.SPACES_COUNT) && (Indentation.SPACES[appendLength]
                    == indentation)) {
                final int length = value.length();
                final int total = length + appendLength;
                if ((total < Indentation.SPACES_COUNT) && (Indentation.SPACES[length] == this)) {
                    result = Indentation.SPACES[total];
                    break;
                }
            }

            // cannot be a constant
            result = new Indentation(value + indentationValue);
            break;
        }

        return result;
    }

    // CharSequence

    @Override
    public char charAt(final int index) {
        return this.value.charAt(index);
    }

    @Override
    public int length() {
        return this.value.length();
    }

    @Override
    public Indentation subSequence(final int start,
                                   final int end) {
        final String value = this.value;
        final String subSequence = value.substring(start, end);
        return value.equals(subSequence) ? this : new Indentation(subSequence);
    }

    /**
     * The raw {@link String} indentation value.
     */
    @Override
    public String value() {
        return this.value;
    }

    private final String value;

    // Object

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return (this == other) || ((other instanceof Indentation)
                && this.equals((Indentation) other));
    }

    private boolean equals(final Indentation other) {
        return this.value.equals(other.value);
    }

    @Override
    public String toString() {
        return this.value;
    }
}
