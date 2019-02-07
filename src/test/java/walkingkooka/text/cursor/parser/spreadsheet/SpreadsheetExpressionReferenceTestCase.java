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

import org.junit.jupiter.api.Test;
import walkingkooka.test.ClassTestCase;
import walkingkooka.test.ToStringTesting;
import walkingkooka.tree.json.HasJsonNodeTesting;
import walkingkooka.tree.json.JsonNode;

public abstract class SpreadsheetExpressionReferenceTestCase<R extends SpreadsheetExpressionReference> extends ClassTestCase<R>
        implements HasJsonNodeTesting<R>,
        ToStringTesting<R> {

    SpreadsheetExpressionReferenceTestCase() {
        super();
    }

    @Test
    public final void testToJsonNode() {
        final R reference = this.createReference();
        this.toJsonNodeAndCheck(reference, JsonNode.string(reference.toString()));
    }

    abstract R createReference();
}