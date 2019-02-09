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

package walkingkooka.text.cursor.parser.spreadsheet;

import walkingkooka.test.ClassTestCase;
import walkingkooka.test.TypeNameTesting;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserTesting;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.type.MemberVisibility;

public abstract class SpreadsheetParserTestCase<P extends Parser<T, SpreadsheetParserContext>,
        T extends SpreadsheetParserToken> extends ClassTestCase<P>
        implements ParserTesting<P, T, SpreadsheetParserContext>,
        TypeNameTesting<P> {

    SpreadsheetParserTestCase() {
        super();
    }

    @Override
    public final SpreadsheetParserContext createContext() {
        return SpreadsheetParserContexts.basic(this.decimalNumberContext());
    }

    @Override
    public final String parserTokenToString(final ParserToken token) {
        return SpreadsheetParserPrettySpreadsheetParserTokenVisitor.toString(token);
    }

    // TypeNameTesting .........................................................................................

    @Override
    public final String typeNamePrefix() {
        return "Spreadsheet";
    }

    @Override
    public final String typeNameSuffix() {
        return Parser.class.getSimpleName();
    }

    // ClassTestCase .........................................................................................

    @Override
    protected final MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
