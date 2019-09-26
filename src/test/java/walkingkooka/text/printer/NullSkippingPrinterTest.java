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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.text.LineEnding;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class NullSkippingPrinterTest extends PrinterTestCase<NullSkippingPrinter> {

    // constants

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    // tests

    @Test
    public void testWrapNullPrinterFails() {
        assertThrows(NullPointerException.class, () -> {
            NullSkippingPrinter.wrap(null);
        });
    }

    @Test
    public void testDoesntWrapNullSkippingPrinter() {
        final NullSkippingPrinter printer = NullSkippingPrinter.wrap(Printers.fake());
        assertSame(printer, NullSkippingPrinter.wrap(printer));
    }

    @Override
    @Test
    public void testPrintNullFails() {
        // nop
    }

    @Test
    public void testPrintNull() {
        final StringBuilder builder = new StringBuilder();
        final NullSkippingPrinter printer = this.createPrinter();
        builder.append("before ");
        printer.print("before ");
        printer.print(null);
        builder.append(" after");
        printer.print(" after");
        this.check(builder.toString(), printer);
    }

    @Test
    public void testMixedCharsAndLineEndings() {
        final StringBuilder builder = new StringBuilder();
        final NullSkippingPrinter printer = this.createPrinter();
        builder.append("before ");
        printer.print("before ");
        printer.print(null);
        printer.print(printer.lineEnding());
        builder.append(LINE_ENDING);
        builder.append(" after");
        printer.print(" after");
        this.check(builder.toString(), printer);
    }

    @Test
    public void testToString() {
        final Printer printer = Printers.fake();
        checkEquals("skip nulls " + printer, NullSkippingPrinter.wrap(printer).toString());
    }

    private void check(final String expected, final NullSkippingPrinter printer) {
        final StringBuilderPrinter stringBuilder = Cast.to(printer.printer);
        checkEquals(expected, stringBuilder.stringBuilder.toString());
    }

    @Override
    public NullSkippingPrinter createPrinter() {
        return NullSkippingPrinter.wrap(Printers.stringBuilder(new StringBuilder(),
                LINE_ENDING));
    }

    @Override
    public Class<NullSkippingPrinter> type() {
        return NullSkippingPrinter.class;
    }
}