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

package walkingkooka.text.cursor.parser;

import org.junit.jupiter.api.Test;
import walkingkooka.type.JavaVisibility;

public final class RepeatedOrSequenceParserTokenFlatParserTokenVisitorTest implements ParserTokenVisitorTesting<RepeatedOrSequenceParserTokenFlatParserTokenVisitor, ParserToken> {

    @Test
    public void testToString() {
        final RepeatedOrSequenceParserTokenFlatParserTokenVisitor visitor = new RepeatedOrSequenceParserTokenFlatParserTokenVisitor();
        visitor.accept(ParserTokens.string("hello", "hello"));
        this.toStringAndCheck(visitor, "[hello]");
    }

    @Override
    public RepeatedOrSequenceParserTokenFlatParserTokenVisitor createVisitor() {
        return new RepeatedOrSequenceParserTokenFlatParserTokenVisitor();
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<RepeatedOrSequenceParserTokenFlatParserTokenVisitor> type() {
        return RepeatedOrSequenceParserTokenFlatParserTokenVisitor.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    // TypeNameTesting..................................................................................................

    @Override
    public String typeNamePrefix() {
        return RepeatedOrSequenceParserToken.class.getSimpleName();
    }

    @Override
    public String typeNameSuffix() {
        return ParserTokenVisitor.class.getSimpleName();
    }
}