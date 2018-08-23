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

package walkingkooka.text.cursor.parser;

import walkingkooka.Value;

import java.util.List;

/**
 * Interface that all parent parser tokens must implement.
 */
public interface ParentParserToken<P extends ParentParserToken> extends ParserToken, Value<List<ParserToken>> {

    /**
     * Would be setter that returns an instance with the given tokens or value, creating a new instance if necessary.
     */
    P setValue(List<ParserToken> tokens);
}
