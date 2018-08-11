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

package walkingkooka.text;

import org.junit.Test;
import walkingkooka.test.HashCodeEqualsDefinedTestCase;

import static org.junit.Assert.assertEquals;

public class LineAndColumnTest extends HashCodeEqualsDefinedTestCase<LineAndColumn> {

    private final static int LINE_NUMBER = 1;
    private final static int COLUMN_NUMBER = 2;
    private final static String LINE = "abcdef";

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidLineNumberFails()
    {
        LineAndColumn.with(0, COLUMN_NUMBER, LINE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidColumnNumberFails()
    {
        LineAndColumn.with(LINE_NUMBER, 0, LINE);
    }

    @Test(expected = NullPointerException.class)
    public void testCreateNullLineFails()
    {
        LineAndColumn.with(LINE_NUMBER, COLUMN_NUMBER, null);
    }

    @Test
    public void testCreate()
    {
        final LineAndColumn a = this.create();
        assertEquals("lineNumber", LINE_NUMBER, a.lineNumber());
        assertEquals("columnNumber", COLUMN_NUMBER, a.columnNumber());
        assertEquals("line", LINE, a.line());
    }

    @Test
    public void testToString(){
        assertEquals("line: 1, col: 2, \"abcdef\"", this.create().toString());
    }

    @Test
    public void testFirstLine() {
        this.determineAndCheck("line", 0, "line", 1, 1);
    }

    @Test
    public void testNewLineAfterEmptyLine() {
        this.determineAndCheck("\n", 0, "", 1, 1);
    }

    @Test
    public void testCarriageReturnAfterEmptyLine() {
        this.determineAndCheck("\r", 0, "", 1, 1);
    }

    @Test
    public void testEndAfterNewLine() {
        this.determineAndCheck("\n", 1, "", 2, 1);
    }

    @Test
    public void testEndAfterCarriageReturn() {
        this.determineAndCheck("\r", 1, "", 2, 1);
    }

    @Test
    public void testEndAfterCarriageReturnNewLine() {
        this.determineAndCheck("\r\n", 2, "", 2, 1);
    }

    @Test
    public void testMiddleOfFirstLine() {
        this.determineAndCheck("line", 1, "line", 1, 2);
    }

    @Test
    public void testCarriageReturnAfterLine() {
        this.determineAndCheck("line\r", 4, "line", 1, 4);
    }

    @Test
    public void testNewLineAfterLine() {
        this.determineAndCheck("line\n", 4, "line", 1, 4);
    }

    @Test
    public void testCarriageReturnNewLineAfterLine() {
        this.determineAndCheck("line\r\n", 5, "line", 1, 4);
    }

    @Test
    public void testSecondLineAfterNewLine() {
        final String prefix = "first\nl";
        this.determineAndCheck(prefix + "ine\nafter", prefix, "line", 2, 2);
    }

    @Test
    public void testSecondLineAfterCarriageReturn() {
        final String prefix = "first\rl";
        this.determineAndCheck(prefix + "ine\nafter", prefix, "line", 2, 2);
    }

    @Test
    public void testSecondLineAfterCarriageReturnNewLine() {
        final String prefix = "first\r\nl";
        this.determineAndCheck(prefix + "ine\nafter", prefix, "line", 2, 2);
    }

    @Test
    public void testSecondLineAfterCarriageReturnNewLine2() {
        final String prefix = "first\r\nli";
        this.determineAndCheck(prefix + "ne\nafter", prefix, "line", 2, 3);
    }

    @Test
    public void testFirstCharacterOfMiddleLine() {
        final String prefix = "first\n";
        this.determineAndCheck(prefix + "NEXT\nafter", prefix, "NEXT", 2, 1);
    }

    @Test
    public void testEmptyNotFirstLine() {
        final String text = "first\n\rthird\n\r";
        this.determineAndCheck(text, "first\n", "", 2, 1);
    }

    @Test
    public void testLastLine() {
        final String prefix = "first\nsecond\rt";
        this.determineAndCheck(prefix + "hird", prefix.length(), "third", 3, 2);
    }

    @Test
    public void testMiddleOfLastLine() {
        final String text = "first\nsecond\rthird";
        this.determineAndCheck(text, text.length() - 2, "third", 3, 4);
    }

    @Test
    public void testEmptyLastLineTerminatedByCarriageReturn() {
        final String text = "first\n\r";
        this.determineAndCheck(text, text.length(), "", 3, 1);
    }

    @Test
    public void testEmptyLastLineTerminatedByNewLine() {
        final String text = "first\n\n";
        this.determineAndCheck(text, text.length(), "", 3, 1);
    }

    @Test
    public void testLastChar() {
        final String text = "first\nsecond\rthird";
        this.determineAndCheck(text, text.length() - 1, "third", 3, 5);
    }

    @Test
    public void testLastCharEndsWithCarriageReturn() {
        final String text = "first\nsecond\rthird\r";
        this.determineAndCheck(text, text.length() - 1, "third", 3, 5);
    }

    @Test
    public void testLastCharEndsWithNewLine() {
        final String text = "first\nsecond\rthird\n";
        this.determineAndCheck(text, text.length() - 1, "third", 3, 5);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testAfterLastCharFails() {
        final String text = "first\nsecond\rthird";
        this.determineAndCheck(text, text.length() + 1, "", 4, 1);
    }

    private void determineAndCheck(final String text, final String pos, final String line, final int lineNumber,
                                   final int column) {
        if (false == text.startsWith(pos)) {
            failNotEquals("Pos text must be at the start of text", text, pos);
        }
        this.determineAndCheck(text, pos.length(), line, lineNumber, column);
    }

    private void determineAndCheck(final String text, final int pos, final String line, final int lineNumber,
                                   final int column) {
        final LineAndColumn info = LineAndColumn.determine(text, pos);
        assertEquals("lineNumber", lineNumber, info.lineNumber());
        assertEquals("column()", column, info.columnNumber());
        assertEquals("line()", line, info.line());
    }

    private LineAndColumn create(){
        return LineAndColumn.with(LINE_NUMBER, COLUMN_NUMBER, LINE);
    }

    @Override protected Class<? extends LineAndColumn> type() {
        return LineAndColumn.class;
    }
}
