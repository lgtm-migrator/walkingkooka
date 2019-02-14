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

package walkingkooka.io.printer;

import walkingkooka.text.CharSequences;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Interface with default methods which can be mixed in to assist testing of an {@link Printer}.
 */
public interface PrinterTesting2<P extends Printer> extends PrinterTesting<P> {

    @Override
    default P createPrinter() {
        return this.createPrinter(new StringBuilder());
    }

    P createPrinter(StringBuilder target);

    default void printAndCheck(final CharSequence printed) {
        this.printAndCheck(printed, printed.toString());
    }

    default void printAndCheck(final CharSequence printed, final String expected) {
        this.printAndCheck(printed, expected, null);
    }

    default void printAndCheck(final CharSequence printed, final String expected,
                               final String message) {
        this.printAndCheck(new CharSequence[]{printed}, expected, null);
    }

    default void printAndCheck(final CharSequence[] chars, final String expected) {
        this.printAndCheck(chars, expected, null);
    }

    default void printAndCheck(final CharSequence[] chars, final String expected,
                               final String message) {
        final StringBuilder target = new StringBuilder();
        this.printAndCheck(this.createPrinter(target), chars, target, expected, message);
    }

    default void printAndCheck(final Printer printer, final CharSequence printed,
                               final StringBuilder target, final String expected) {
        this.printAndCheck(printer, printed, target, expected, "printed");
    }

    default void printAndCheck(final Printer printer, final CharSequence printed,
                               final StringBuilder target, final String expected, final String message) {
        this.printAndCheck(printer, new CharSequence[]{printed}, target, expected, message);
    }

    default void printAndCheck(final Printer printer, final CharSequence[] chars,
                               final StringBuilder target, final String expected) {
        this.printAndCheck(printer, chars, target, expected, "printed");
    }

    default void printAndCheck(final Printer printer, final CharSequence[] chars,
                               final StringBuilder target, final String expected, final String message) {
        this.printAndCheck(printer, chars, target, expected, true, message);
    }

    default void printAndCheck(final Printer printer,
                               final CharSequence[] chars,
                               final StringBuilder target,
                               final String expected,
                               final boolean flushAndClose,
                               final String message) {
        Objects.requireNonNull(printer, "printer");
        Objects.requireNonNull(chars, "chars");
        Objects.requireNonNull(target, "target");

        for (final CharSequence c : chars) {
            printer.print(c);
        }
        if (flushAndClose) {
            printer.flush();
            printer.close();
        }
        final String printed = target.toString();
        if (false == printed.equals(expected)) {
            assertEquals(CharSequences.quoteAndEscape(expected),
                    CharSequences.quoteAndEscape(printed),
                    message);
        }
    }

    /**
     * Splits a CharSequence into many CharSequence each holding a single character.
     */
    default CharSequence[] characterByCharacter(final CharSequence chars) {
        Objects.requireNonNull(chars, "chars");

        final int length = chars.length();
        final CharSequence[] array = new CharSequence[length];
        for (int i = 0; i < length; i++) {
            array[i] = chars.subSequence(i, i + 1);
        }
        return array;
    }
}