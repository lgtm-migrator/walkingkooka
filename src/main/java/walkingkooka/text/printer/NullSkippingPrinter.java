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

package walkingkooka.text.printer;

import walkingkooka.text.LineEnding;

import java.util.Objects;

/**
 * A {@link Printer} that filters and ignore any nulls passed to {@link #print(CharSequence)}.
 */
final class NullSkippingPrinter implements Printer {

    /**
     * Creates a new {@link NullSkippingPrinter} without wrapping if the printer is already a {@link
     * NullSkippingPrinter}.
     */
    static NullSkippingPrinter wrap(final Printer printer) {
        Objects.requireNonNull(printer, "printer");

        return printer instanceof NullSkippingPrinter ?
                (NullSkippingPrinter) printer :
                new NullSkippingPrinter(printer);
    }

    /**
     * Private constructor use static factory.
     */
    private NullSkippingPrinter(final Printer printer) {
        super();
        this.printer = printer;
    }

    /**
     * Does nothing if {@link CharSequence} is null.
     */
    @Override
    public void print(final CharSequence chars) throws PrinterException {
        if (null != chars) {
            this.printer.print(chars);
        }
    }

    /**
     * Returns the wrapped {@link Printer#lineEnding()}.
     */
    @Override
    public LineEnding lineEnding() throws PrinterException {
        return this.printer.lineEnding();
    }

    @Override
    public void flush() throws PrinterException {
        this.printer.flush();
    }

    @Override
    public void close() throws PrinterException {
        this.printer.close();
    }

    // properties

    final Printer printer;

    @Override
    public String toString() {
        return "skip nulls " + this.printer;
    }
}