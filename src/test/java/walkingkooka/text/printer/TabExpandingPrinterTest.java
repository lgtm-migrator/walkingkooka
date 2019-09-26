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
import walkingkooka.text.LineEnding;

import java.util.function.IntUnaryOperator;

import static org.junit.jupiter.api.Assertions.assertThrows;

final public class TabExpandingPrinterTest extends PrinterTestCase2<TabExpandingPrinter> {

    // constants

    private final static Printer PRINTER = Printers.fake();

    private final static IntUnaryOperator TAB_STOPS = new IntUnaryOperator() {
        @Override
        public int applyAsInt(final int operand) {
            throw new UnsupportedOperationException();
        }
    };

    private final static LineEnding LINE_ENDING = LineEnding.NL;

    // tests

    @Test
    public void testWrapNullPrinterFails() {
        assertThrows(NullPointerException.class, () -> {
            TabExpandingPrinter.wrap(null, TAB_STOPS);
        });
    }

    @Test
    public void testWrapNullTabStopsFails() {
        assertThrows(NullPointerException.class, () -> {
            TabExpandingPrinter.wrap(PRINTER, null);
        });
    }

    @Test
    public void testWithoutTabs() {
        final String chars = "1234567890";
        this.printAndCheck(chars, chars);
    }

    @Test
    public void testIncludesCr() {
        final String chars = "12345\r67890";
        this.printAndCheck(chars, chars);
    }

    @Test
    public void testIncludesNl() {
        final String chars = "12345\n67890";
        this.printAndCheck(chars, chars);
    }

    @Test
    public void testIncludesCrNl() {
        final String chars = "12345\r\n67890";
        this.printAndCheck(chars, chars);
    }

    @Test
    public void testTab() {
        this.printAndCheck("01\t456", "01  456");
    }

    @Test
    public void testZeroAdvanceTabStop() {
        this.printAndCheck("0123\t456", "0123456");
    }

    @Test
    public void testManyTabStops() {
        this.printAndCheck("01234\t8\t9", "01234   8   9");
    }

    @Test
    public void testTabAndNl() {
        this.printAndCheck("A\n01\t456", "A\n01  456");
    }

    @Test
    public void testTabAndCr() {
        this.printAndCheck("A\r01\t456", "A\r01  456");
    }

    @Test
    public void testTabAndCrNl() {
        this.printAndCheck("A\r\n01\t456", "A\r\n01  456");
    }

    @Test
    public void testOneCharacterPrints() {
        final String printed = "012345\t8\nAB\tC";
        final int length = printed.length();
        final CharSequence[] chars = new CharSequence[length];
        for (int i = 0; i < length; i++) {
            chars[i] = printed.subSequence(i, i + 1);
        }

        this.printAndCheck(chars, "012345  8\nAB  C");
    }

    @Test
    public void testMixedCharsAndLineEndings() {
        final StringBuilder printed = new StringBuilder();
        final TabExpandingPrinter printer = this.createPrinter(printed);
        printer.print("01\t56");
        printer.print(printer.lineEnding());
        printer.print("ABC\t*");
        printer.flush();
        checkEquals("01  56" + LINE_ENDING + "ABC *", printed.toString());
    }

    @Test
    public void testToString() {
        checkEquals("tab x " + PRINTER,
                TabExpandingPrinter.wrap(PRINTER,
                        TAB_STOPS).toString());
    }

    @Override
    public TabExpandingPrinter createPrinter(final StringBuilder printed) {
        return TabExpandingPrinter.wrap(//
                Printers.stringBuilder(printed, LINE_ENDING), //
                (tabStop -> ((tabStop + 3) / 4 * 4)));
    }

    @Override
    public Class<TabExpandingPrinter> type() {
        return TabExpandingPrinter.class;
    }
}