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

package walkingkooka.text.spreadsheetformat;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.map.Maps;
import walkingkooka.color.Color;
import walkingkooka.test.ClassTesting2;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.test.ToStringTesting;
import walkingkooka.text.CharSequences;
import walkingkooka.tree.text.HasTextNodeTesting;
import walkingkooka.tree.text.TextNode;
import walkingkooka.tree.text.TextPropertyName;
import walkingkooka.type.MemberVisibility;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class SpreadsheetFormattedTextTest implements ClassTesting2<SpreadsheetFormattedText>,
        HashCodeEqualsDefinedTesting<SpreadsheetFormattedText>,
        HasTextNodeTesting,
        ToStringTesting<SpreadsheetFormattedText>{

    private final static Optional<Color> COLOR = Optional.of(Color.BLACK);
    private final static String TEXT = "1/1/2000";

    @Test
    public void testWithNullColorFails() {
        assertThrows(NullPointerException.class, () -> {
            SpreadsheetFormattedText.with(null, TEXT);
        });
    }

    @Test
    public void testWithNullTextFails() {
        assertThrows(NullPointerException.class, () -> {
            SpreadsheetFormattedText.with(COLOR, null);
        });
    }

    @Test
    public void testWith() {
        final SpreadsheetFormattedText formatted = this.createFormattedText();
        this.check(formatted, COLOR, TEXT);
    }

    @Test
    public void testWithEmptyColor() {
        this.createAndCheck(SpreadsheetFormattedText.WITHOUT_COLOR, TEXT);
    }

    @Test
    public void testWithEmptyText() {
        this.createAndCheck(COLOR, "");
    }

    private void createAndCheck(final Optional<Color> color, final String text) {
        final SpreadsheetFormattedText formatted = SpreadsheetFormattedText.with(color, text);
        this.check(formatted, color, text);
    }

    // setColor...........................................................

    @Test
    public void testSetColorNullFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createFormattedText().setColor(null);
        });
    }

    @Test
    public void testSetColorSame() {
        final SpreadsheetFormattedText formatted = this.createFormattedText();
        assertSame(formatted, formatted.setColor(COLOR));
    }

    @Test
    public void testSetColorDifferent() {
        final Optional<Color> differentColor = Optional.of(Color.fromRgb(123));
        final SpreadsheetFormattedText formatted = this.createFormattedText();
        final SpreadsheetFormattedText different = formatted.setColor(differentColor);
        assertNotSame(formatted, different);
        this.check(different, differentColor, TEXT);
    }

    private void check(final SpreadsheetFormattedText formatted, final Optional<Color> color, final String text) {
        assertEquals(color, formatted.color(), "color");
        assertEquals(text, formatted.text(), "text");
    }

    // ToTextNode.... ..................................................................................................

    @Test
    public void testToTextNodeWithoutColor() {
        final String text = "abc123";

        this.toTextNodeAndCheck(SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, text),
                TextNode.text(text));
    }

    @Test
    public void testToTextNodeWithColor() {
        final String text = "abc123";
        final Color color = Color.fromRgb(0x123456);

        this.toTextNodeAndCheck(SpreadsheetFormattedText.with(Optional.of(color), text),
                TextNode.text(text).setAttributes(Maps.of(TextPropertyName.TEXT_COLOR, color)));
    }

    // HashCodeEqualsDefined ..................................................................................................

    @Test
    public void testEqualsDifferentColor() {
        this.checkNotEquals(SpreadsheetFormattedText.with(Optional.of(Color.WHITE), TEXT));
    }

    @Test
    public void testEqualsDifferentColor2() {
        this.checkNotEquals(SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, TEXT));
    }

    @Test
    public void testEqualsDifferentText() {
        this.checkNotEquals(SpreadsheetFormattedText.with(COLOR, "different"));
    }

    // toString ..................................................................................................

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createFormattedText(), COLOR.get() + " " + CharSequences.quote(TEXT));
    }

    @Test
    public void testToStringWithoutColor() {
        this.toStringAndCheck(SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, TEXT),
                CharSequences.quote(TEXT).toString());
    }

    private SpreadsheetFormattedText createFormattedText() {
        return SpreadsheetFormattedText.with(COLOR, TEXT);
    }

    @Override
    public Class<SpreadsheetFormattedText> type() {
        return SpreadsheetFormattedText.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }

    @Override
    public SpreadsheetFormattedText createObject() {
        return SpreadsheetFormattedText.with(COLOR, TEXT);
    }
}
