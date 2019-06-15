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

package walkingkooka.text.cursor.parser.spreadsheet;

import walkingkooka.test.Fake;

public final class FakeSpreadsheetParserTokenVisitorTest extends SpreadsheetParserTokenVisitorTestCase<FakeSpreadsheetParserTokenVisitor> {

    @Override
    public FakeSpreadsheetParserTokenVisitor createVisitor() {
        return new FakeSpreadsheetParserTokenVisitor();
    }

    @Override
    public String typeNamePrefix() {
        return Fake.class.getSimpleName();
    }

    @Override
    public Class<FakeSpreadsheetParserTokenVisitor> type() {
        return FakeSpreadsheetParserTokenVisitor.class;
    }
}
