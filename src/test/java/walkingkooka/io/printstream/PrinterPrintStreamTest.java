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
 */

package walkingkooka.io.printstream;

import org.junit.Assert;
import org.junit.Test;
import walkingkooka.Cast;
import walkingkooka.io.printer.Printer;
import walkingkooka.io.printer.Printers;
import walkingkooka.text.LineEnding;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

final public class PrinterPrintStreamTest extends PrintStreamTestCase<PrinterPrintStream> {

    // constants

    private final static Printer PRINTER = Printers.fake();

    private final static LineEnding LINE_ENDING = LineEnding.CR;

    private final static Charset CHARSET = Charset.forName("UTF-8");

    // tests

    @Test
    public void testAdaptNullPrinterFails() {
        this.adaptFails(null, PrinterPrintStreamTest.LINE_ENDING, PrinterPrintStreamTest.CHARSET);
    }

    @Test
    public void testAdaptNullLineEndingFails() {
        this.adaptFails(PrinterPrintStreamTest.PRINTER, null, PrinterPrintStreamTest.CHARSET);
    }

    @Test
    public void testAdaptNullCharsetFails() {
        this.adaptFails(PrinterPrintStreamTest.PRINTER, PrinterPrintStreamTest.LINE_ENDING, null);
    }

    private void adaptFails(final Printer printer, final LineEnding lineEnding,
                            final Charset charset) {
        try {
            PrinterPrintStream.adapt(printer, lineEnding, charset);
            Assert.fail();
        } catch (final NullPointerException expected) {
        }
    }

    @Test
    public void testUnwrapsHasPrintStream() {
        final PrintStream printStream = System.out;
        assertSame(printStream,
                PrinterPrintStream.adapt(Printers.printStream(printStream,
                        PrinterPrintStreamTest.LINE_ENDING),
                        PrinterPrintStreamTest.LINE_ENDING,
                        PrinterPrintStreamTest.CHARSET));
    }

    @Test
    public void testWriteInt() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).write(123);
        assertEquals("", b.toString());
    }

    @Test
    public void testWriteIntAndFlush() {
        final StringBuilder b = new StringBuilder();
        final PrintStream printStream = this.createPrintStream(b);
        printStream.write('a');
        printStream.flush();
        assertEquals("a", b.toString());
    }

    @Test
    public void testWriteByteArray() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).write(new byte[]{0, 1, 2, 3}, 1, 2);
        assertEquals("", b.toString());
    }

    @Test
    public void testWriteByteArrayAndFlush() {
        final String source = "ABCDEF";
        final StringBuilder b = new StringBuilder();
        final byte[] bytes = source.getBytes();
        final PrinterPrintStream printStream = this.createPrintStream(b);
        printStream.write(bytes, 0, bytes.length);
        printStream.flush();
        assertEquals(source, b.toString());
    }

    @Test
    public void testWriteByteArrayAndFlush2() {
        final StringBuilder b = new StringBuilder();
        final PrinterPrintStream printStream = this.createPrintStream(b);
        printStream.write(new byte[]{'a', 'b', 'c', 'd'}, 1, 2);
        printStream.flush();
        assertEquals("bc", b.toString());
    }

    @Test
    public void testWriteByteArrayAndFlush3() {
        final String source = "ABCD\u0100;EFGHIJKLMN";
        final StringBuilder b = new StringBuilder();
        final byte[] bytes = source.getBytes(PrinterPrintStreamTest.CHARSET);
        final PrinterPrintStream printStream = this.createPrintStream(b);
        printStream.write(bytes, 0, bytes.length);
        printStream.flush();

        assertEquals(source, b.toString());
    }

    @Test
    public void testWriteByteArrayAndFlush4() {
        final String source = "ABCD\u1234;EFGHIJKLMN";
        final StringBuilder b = new StringBuilder();
        final byte[] bytes = source.getBytes(PrinterPrintStreamTest.CHARSET);
        final PrinterPrintStream printStream = this.createPrintStream(b);
        printStream.write(bytes, 0, bytes.length);
        printStream.flush();

        assertEquals(source, b.toString());
    }

    @Test
    public void testPrintBooleanTrue() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(true);
        assertEquals("true", b.toString());
    }

    @Test
    public void testPrintBooleanFalse() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(false);
        assertEquals("false", b.toString());
    }

    @Test
    public void testPrintChar() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print('A');
        assertEquals("A", b.toString());
    }

    @Test
    public void testPrintInt() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(123);
        assertEquals("123", b.toString());
    }

    @Test
    public void testPrintLong() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(123L);
        assertEquals("123", b.toString());
    }

    @Test
    public void testPrintFloat() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(123.0f);
        assertEquals("123.0", b.toString());
    }

    @Test
    public void testPrintDouble() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).print(123.5);
        assertEquals("123.5", b.toString());
    }

    @Test
    public void testPrintCharArray() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).print(printed.toCharArray());
        assertEquals(printed, b.toString());
    }

    @Test
    public void testPrintString() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).print(printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testPrintObject() {
        final StringBuilder b = new StringBuilder();
        final Object printed = new Object();
        this.createPrintStream(b).print(printed);
        assertEquals(printed.toString(), b.toString());
    }

    @Test
    public void testPrintlnWithCr() {
        this.println(LineEnding.CR);
    }

    @Test
    public void testPrintlnWithCrNl() {
        this.println(LineEnding.CRNL);
    }

    @Test
    public void testPrintlnWithNl() {
        this.println(LineEnding.NL);
    }

    @Test
    public void testPrintlnWithNone() {
        this.println(LineEnding.NONE);
    }

    @Test
    public void testPrintlnWithSystem() {
        this.println(LineEnding.SYSTEM);
    }

    private void println(final LineEnding lineEnding) {
        final StringBuilder b = new StringBuilder();
        PrinterPrintStream.adapt(Printers.stringBuilder(b, lineEnding),
                lineEnding,
                PrinterPrintStreamTest.CHARSET).println();
        assertEquals(lineEnding.toString(), b.toString());
    }

    @Test
    public void testPrintlnBooleanTrue() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(true);
        assertEquals("true" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnBooleanFalse() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(false);
        assertEquals("false" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnChar() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println('A');
        assertEquals("A" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnInt() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(123);
        assertEquals("123" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnLong() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(123L);
        assertEquals("123" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnFloat() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(123.0f);
        assertEquals("123.0" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnDouble() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).println(123.5);
        assertEquals("123.5" + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnCharArray() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).println(printed.toCharArray());
        assertEquals(printed + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnString() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).println(printed);
        assertEquals(printed + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintlnObject() {
        final StringBuilder b = new StringBuilder();
        final Object printed = new Object();
        this.createPrintStream(b).println(printed);
        assertEquals(printed.toString() + PrinterPrintStreamTest.LINE_ENDING, b.toString());
    }

    @Test
    public void testPrintf() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).printf(printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testPrintfLocale() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).printf(Locale.getDefault(), printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testFormat() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).format("%s", printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testFormatLocale() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).format(Locale.getDefault(), "%s", printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testAppendChar() {
        final StringBuilder b = new StringBuilder();
        this.createPrintStream(b).append('A');
        assertEquals("A", b.toString());
    }

    @Test
    public void testAppend() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).append(printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testAppendWithStartAndLength() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        this.createPrintStream(b).append("***" + printed + "***", 3, 3 + printed.length());
        assertEquals(printed, b.toString());
    }

    @Test
    public void testWritePrint() throws IOException {
        final StringBuilder b = new StringBuilder();
        final PrintStream printStream = this.createPrintStream(b);
        printStream.write("123".getBytes());
        printStream.print("abc");
        assertEquals("123abc", b.toString());
    }

    @Test
    public void testPrintWritePrint() throws IOException {
        final StringBuilder b = new StringBuilder();
        final PrintStream printStream = this.createPrintStream(b);
        printStream.print('a');
        printStream.print("bc");
        printStream.write("123".getBytes());
        printStream.print("def");
        assertEquals("abc123def", b.toString());
    }

    @Test
    public void testFlush() {
        this.createPrintStream(new StringBuilder()).flush();
    }

    @Test
    public void testWriteFlush() throws IOException {
        final StringBuilder b = new StringBuilder();
        final PrintStream printStream = this.createPrintStream(b);
        printStream.write("123".getBytes());
        printStream.flush();
        assertEquals("123", b.toString());
    }

    @Test
    public void testClose() {
        PrinterPrintStream.adapt(Printers.sink(),
                PrinterPrintStreamTest.LINE_ENDING,
                PrinterPrintStreamTest.CHARSET).close();
    }

    @Test
    public void testPrintAfterClosing() {
        final StringBuilder b = new StringBuilder();
        final String printed = "printed";
        final PrintStream printStream = this.createPrintStream(b);
        printStream.close();
        printStream.print(printed);
        assertEquals(printed, b.toString());
    }

    @Test
    public void testCheckError() {
        Assert.assertFalse(this.createPrintStream().checkError());
    }

    @Test
    public void testToString() {
        assertEquals(PrinterPrintStreamTest.PRINTER + " \"\\r\"",
                this.createPrintStream().toString());
    }

    @Override
    protected PrinterPrintStream createPrintStream() {
        return Cast.to(PrinterPrintStream.adapt(PrinterPrintStreamTest.PRINTER,
                PrinterPrintStreamTest.LINE_ENDING,
                PrinterPrintStreamTest.CHARSET));
    }

    private PrinterPrintStream createPrintStream(final StringBuilder b) {
        return Cast.to(PrinterPrintStream.adapt(Printers.stringBuilder(b,
                PrinterPrintStreamTest.LINE_ENDING),
                PrinterPrintStreamTest.LINE_ENDING,
                PrinterPrintStreamTest.CHARSET));
    }

    @Override
    protected Class<PrinterPrintStream> type() {
        return PrinterPrintStream.class;
    }
}
