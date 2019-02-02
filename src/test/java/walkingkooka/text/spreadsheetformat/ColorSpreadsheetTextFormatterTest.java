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
import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatColorParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserContext;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParserToken;
import walkingkooka.text.cursor.parser.spreadsheet.format.SpreadsheetFormatParsers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class ColorSpreadsheetTextFormatterTest extends SpreadsheetTextFormatterTemplate2TestCase<ColorSpreadsheetTextFormatter<String>,
        String,
        SpreadsheetFormatColorParserToken> {

    private final static String TEXT_PATTERN = "@@";

    @Test
    public void testWithNullWrappedFormatterFails() {
        assertThrows(NullPointerException.class, () -> {
            ColorSpreadsheetTextFormatter.with(this.parsePatternOrFail(this.pattern()), null);
        });
    }

    @Test
    public void testWrappedFormatterFails() {
        this.formatFailAndCheck(ColorSpreadsheetTextFormatter.with(this.parsePatternOrFail(this.pattern()),
                new FakeSpreadsheetTextFormatter<String>() {
                    @Override
                    public Optional<SpreadsheetFormattedText> format(String value, SpreadsheetTextFormatContext context) {
                        return Optional.empty();
                    }
                }),
                "Ignored text",
                this.createContext());
    }

    @Test
    public void testColorNameAndTextFormatted() {
        final String text = "abc123";
        final Color color = Color.BLACK;
        this.parseFormatAndCheck0(
                "[RED]",
                text,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public Color colorName(final String name) {
                        assertEquals("RED", name, "color name");
                        return color;
                    }
                },
                SpreadsheetFormattedText.with(Optional.of(color), text + text));
    }

    @Test
    public void testColorNumberAndTextFormatted() {
        final String text = "abc123";
        final Color color = Color.BLACK;
        this.parseFormatAndCheck0(
                "[COLOR 15]",
                text,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public Color colorNumber(final int number) {
                        assertEquals(15, number);
                        return color;
                    }
                },
                SpreadsheetFormattedText.with(Optional.of(color), text + text));
    }

    private void parseFormatAndCheck0(final String pattern,
                                      String value,
                                      final SpreadsheetTextFormatContext context,
                                      final SpreadsheetFormattedText formattedText) {
        this.formatAndCheck(this.createFormatter(pattern), value, context, Optional.of(formattedText));
    }

    @Test
    public void testToString() {
        assertEquals(this.pattern() + " " + TEXT_PATTERN, this.createFormatter().toString());
    }

    @Override
    ColorSpreadsheetTextFormatter<String> createFormatter0(final SpreadsheetFormatColorParserToken token) {
        return ColorSpreadsheetTextFormatter.with(token, this.textFormatter());
    }

    private SpreadsheetTextFormatter<String> textFormatter() {
        return SpreadsheetTextFormatters.text(this.parsePatternOrFail(SpreadsheetFormatParsers.text(), TEXT_PATTERN).cast());
    }

    @Override
    Parser<SpreadsheetFormatParserToken, SpreadsheetFormatParserContext> parser() {
        return SpreadsheetFormatParsers.color();
    }

    @Override
    String pattern() {
        return "[RED]";
    }

    @Override
    protected String value() {
        return "Text123";
    }

    @Override
    protected SpreadsheetTextFormatContext createContext() {
        return SpreadsheetTextFormatContexts.fake();
    }

    private Color red() {
        return Color.fromRgb(0x0FF);
    }

    private Color color12() {
        return Color.fromRgb(0x00F);
    }

    @Override
    protected Class<ColorSpreadsheetTextFormatter<String>> type() {
        return Cast.to(ColorSpreadsheetTextFormatter.class);
    }
}
