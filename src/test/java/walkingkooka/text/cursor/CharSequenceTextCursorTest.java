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

package walkingkooka.text.cursor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class CharSequenceTextCursorTest extends TextCursorTestCase<CharSequenceTextCursor> {

    @Test
    public void testFromNullFails() {
        assertThrows(NullPointerException.class, () -> {
            CharSequenceTextCursor.with(null);
        });
    }

    @Test
    public void testFrom() {
        final String text = "text";
        final CharSequenceTextCursor cursor = CharSequenceTextCursor.with(text);
        assertEquals(0, cursor.position, "positon");
        assertEquals(text, cursor.text, "text");
    }

    @Test
    public void testAtWhenEmpty() {
        final CharSequenceTextCursor cursor = CharSequenceTextCursor.with("");
        this.atFails(cursor, CharSequenceTextCursor.cursorIsEmpty(0, 0));
    }

    @Test
    public void testAtWhenEmpty2() {
        final CharSequenceTextCursor cursor = CharSequenceTextCursor.with("123");
        cursor.next();
        cursor.next();
        cursor.next();
        this.atFails(cursor, CharSequenceTextCursor.cursorIsEmpty(3, 3));
    }

    @Override
    public void testLineInfo() {
        final CharSequenceTextCursor cursor = CharSequenceTextCursor.with("text\nnext");
        cursor.next();
        this.checkLineInfo(cursor, "text", 1, 2);
    }


    @Test
    public void testToStringAtBeginning() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.checkToStringContains(cursor, "at 0 \"[a]bcdefgh\"");
    }

    @Test
    public void testToStringNearBeginning() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        cursor.next();
        this.checkToStringContains(cursor, "at 1 \"a[b]cdefghi\"");
    }

    @Test
    public void testToStringNearBeginning2() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.moveBy(cursor, 2);
        this.checkToStringContains(cursor, "at 2 \"ab[c]defghij\"");
    }

    @Test
    public void testToStringMiddle() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.moveBy(cursor, 13);
        this.checkToStringContains(cursor, "at 13 \"ghijklm[n]opqrstu\"");
    }

    @Test
    public void testToSteringAtEnd() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.moveBy(cursor, 25);
        this.checkToStringContains(cursor, "at 25 \"stuvwxy[z]\"");
    }

    @Test
    public void testToStringAfterEnd() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        cursor.end();
        this.checkToStringContains(cursor, "at 26 \"tuvwxyz\"[]");
    }

    @Test
    public void testToStringNearEnd() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.moveBy(cursor, 24);
        this.checkToStringContains(cursor, "at 24 \"rstuvwx[y]z\"");
    }

    @Test
    public void testToStringNearEnd2() {
        final CharSequenceTextCursor cursor = this.createTextCursor("abcdefghijklmnopqrstuvwxyz");
        this.moveBy(cursor, 23);
        this.checkToStringContains(cursor, "at 23 \"qrstuvw[x]yz\"");
    }

    @Override
    protected CharSequenceTextCursor createTextCursor(final String text) {
        return CharSequenceTextCursor.with(text);
    }

    private void moveBy(final TextCursor cursor, final int skip) {
        for (int i = 0; i < skip; i++) {
            cursor.next();
        }
    }

    private void checkToStringContains(final CharSequenceTextCursor cursor, final String... components) {
        final String toString = cursor.toString();
        checkContains(toString, components);
    }

    private void checkContains(final String string, final String... contains) {
        assertNotNull(string);
        String s = string;

        for (final String c : contains) {
            final int foundIndex = s.indexOf(c);
            if (-1 == foundIndex) {
                Assertions.fail("Cannot find \"" + c + "\" within \"" + string + "\".");
            }
            s = s.substring(0, foundIndex) + s.substring(foundIndex + c.length());
        }
    }

    @Override
    protected Class<CharSequenceTextCursor> type() {
        return CharSequenceTextCursor.class;
    }
}
