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

package walkingkooka.text.cursor.parser.ebnf;

import walkingkooka.collect.set.Sets;

import java.util.Set;

/**
 * Used to report at least one unknown references within a grammar.
 */
public class EbnfGrammarParserTokenInvalidReferencesException extends EbnfParserException {

    EbnfGrammarParserTokenInvalidReferencesException(final String message, final Set<EbnfIdentifierName> references) {
        super(message);
        this.references = references;
    }

    public Set<EbnfIdentifierName> references() {
        return Sets.readOnly(this.references);
    }

    private final Set<EbnfIdentifierName> references;

    @Override
    public String toString() {
        return "Unknown references=" + this.references;
    }

    private final static long serialVersionUID = 1L;
}
