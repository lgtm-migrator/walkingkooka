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

package walkingkooka.color;

import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.text.cursor.parser.ColorParserToken;
import walkingkooka.text.cursor.parser.DoubleParserToken;
import walkingkooka.text.cursor.parser.SequenceParserToken;

/**
 * Handles converting a {@link SequenceParserToken} into a {@link ColorParserToken}.
 */
final class ColorParseArgbFunctionParserTokenVisitor extends ColorParseParserTokenVisitor {

    static ColorParserToken parseSequenceParserToken(final SequenceParserToken token) {
        return new ColorParseArgbFunctionParserTokenVisitor().acceptAndCreateColorParserToken(token);
    }

    @Override
    protected void visit(final DoubleParserToken token) {
        this.color = AlphaColor.with(this.red,
                this.green,
                this.blue,
                AlphaColorComponent.with((byte)(token.value().floatValue() * ColorComponent.MAX_VALUE)));
    }

    @Override
    Color blue(final BlueColorComponent blue) {
        this.blue = blue;
        return null;
    }

    BlueColorComponent blue;

    @Override
    public String toString() {
        return ToStringBuilder.empty()
                .label("red")
                .value(this.red)
                .label("green")
                .value(this.green)
                .label("blue")
                .value(this.blue)
                .label("color")
                .value(this.color)
                .build();
    }
}
