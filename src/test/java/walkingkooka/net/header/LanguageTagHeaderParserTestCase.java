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

import org.junit.Test;
import walkingkooka.collect.map.Maps;

import java.util.Map;

public abstract class LanguageTagHeaderParserTestCase<P extends LanguageTagHeaderParser, V> extends HeaderParserTestCase<P, V> {

    LanguageTagHeaderParserTestCase() {
        super();
    }

    @Test
    public final void testWildcard() {
        this.parseAndCheck2("*", LanguageTag.wildcard());
    }

    @Test
    public final void testWildcardKeyValueSeparatorFails() {
        this.parseInvalidCharacterFails("*;=");
    }

    @Test
    public final void testWildcardKeyValueSeparatorFails2() {
        this.parseInvalidCharacterFails("*; =");
    }

    @Test
    public final void testWildcardParameterNameFails() {
        this.parseMissingParameterValueFails("*; parameter");
    }

    @Test
    public final void testWildcardParameterNameKeyValueSeparatorFails() {
        this.parseMissingParameterValueFails("*; parameter=");
    }
    
    @Test
    public final void testWildcardQWeightInvalidValueFails() {
        this.parseFails("*; q=ABC",
                "Failed to convert \"q\" value \"ABC\", message: For input string: \"ABC\"");
    }

    @Test
    public final void testWildcardWithQWeight() {
        this.parseAndCheck2("*; q=0.75",
                LanguageTag.wildcard().setParameters(Maps.one(LanguageTagParameterName.Q_FACTOR, 0.75f)));
    }

    @Test
    public final void testWildcardWithParameters() {
        final Map<LanguageTagParameterName<?>, Object> parameters = Maps.ordered();
        parameters.put(LanguageTagParameterName.with("a"), "b");
        parameters.put(LanguageTagParameterName.with("c"), "d");

        this.parseAndCheck2("*; a=b; c=d",
                LanguageTag.wildcard().setParameters(parameters));
    }

    @Test
    public final void testLanguage_en() {
        this.parseAndCheck2("en", LanguageTag.with("en"));
    }

    @Test
    public final void testLanguage_de_CH() {
        this.parseAndCheck2("de-CH", LanguageTag.with("de-CH"));
    }

    abstract void parseAndCheck2(final String text, final LanguageTag expected);

    @Override
    final String valueLabel() {
        return LanguageTagHeaderParser.LANGUAGE;
    }
}