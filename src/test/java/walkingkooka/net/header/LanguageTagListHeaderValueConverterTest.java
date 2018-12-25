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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;

import java.util.List;

public final class LanguageTagListHeaderValueConverterTest extends
        HeaderValueConverterTestCase<LanguageTagListHeaderValueConverter, List<LanguageTag>> {

    private final static String TEXT = "en; q=1.0, en-au; q=0.5";

    @Override
    protected String requiredPrefix() {
        return LanguageTag.class.getSimpleName();
    }

    @Test
    public void testContentType() {
        this.parseAndToTextAndCheck(TEXT,
                Lists.of(this.en_10(), this.en_au_05()));
    }

    @Test(expected = HeaderValueException.class)
    public void testCheckIncludesNullFails() {
        this.check(Lists.of(this.en_10(), null));
    }

    @Test(expected = HeaderValueException.class)
    public void testCheckIncludesWrongTypeFails() {
        this.check(Lists.of(this.en_10(), "WRONG!"));
    }

    private LanguageTag en_10() {
        return LanguageTag.with("en").setParameters(Maps.one(LanguageTagParameterName.Q_FACTOR, 1.0f));
    }

    private LanguageTag en_au_05() {
        return LanguageTag.with("en-au").setParameters(Maps.one(LanguageTagParameterName.Q_FACTOR, 0.5f));
    }

    @Override
    protected LanguageTagListHeaderValueConverter converter() {
        return LanguageTagListHeaderValueConverter.INSTANCE;
    }

    @Override
    protected HttpHeaderName<List<LanguageTag>> name() {
        return HttpHeaderName.ACCEPT_LANGUAGE;
    }

    @Override
    protected String invalidHeaderValue() {
        return "invalid!!!";
    }

    @Override
    protected List<LanguageTag> value() {
        return LanguageTag.parseList(TEXT);
    }

    @Override
    protected String converterToString() {
        return "List<LanguageTag>";
    }

    @Override
    protected Class<LanguageTagListHeaderValueConverter> type() {
        return LanguageTagListHeaderValueConverter.class;
    }
}