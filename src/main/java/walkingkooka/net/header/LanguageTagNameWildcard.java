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

package walkingkooka.net.header;

import java.util.Locale;
import java.util.Optional;

/**
 * Holds a Wildcard language tag.
 */
final class LanguageTagNameWildcard extends LanguageTagName {

    /**
     * Wildcard with no parameters instance.
     */
    final static LanguageTagNameWildcard INSTANCE = new LanguageTagNameWildcard();

    /**
     * Private ctor use factory
     */
    private LanguageTagNameWildcard() {
        super(HeaderValue.WILDCARD.string());
    }

    @Override
    public Optional<Locale> locale() {
        return NO_LOCALE;
    }

    // isXXX........................................................................................................

    @Override
    public boolean isWildcard() {
        return true;
    }

    /**
     * Matches any other languageTag except for another wildcard.
     */
    @Override
    boolean isMatch(final LanguageTag languageTag) {
        if (languageTag.value.isWildcard()) {
            throw new IllegalArgumentException("Parameter is wildcard=" + languageTag);
        }
        return true;
    }

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof LanguageTagNameNonWildcard;
    }
}