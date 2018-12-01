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
import walkingkooka.net.http.HttpHeaderName;

import java.util.List;

public final class MediaTypeListHeaderValueConverterTest extends
        HeaderValueConverterTestCase<MediaTypeListHeaderValueConverter, List<MediaType>> {

    private final static String TEXT = "type1/subType1; p1=v1, type2/subType2";

    @Override
    protected String requiredPrefix() {
        return MediaType.class.getSimpleName();
    }

    @Test
    public void testContentType() {
        this.parseAndFormatAndCheck(TEXT,
                Lists.of(this.mediaType1(), this.mediaType2()));
    }

    @Test(expected = HeaderValueException.class)
    public void testCheckIncludesNullFails() {
        this.check(Lists.of(this.mediaType1(), null));
    }

    @Test(expected = HeaderValueException.class)
    public void testCheckIncludesWrongTypeFails() {
        this.check(Lists.of(this.mediaType1(), "WRONG!"));
    }

    private MediaType mediaType1() {
        return MediaType.with("type1", "subType1").setParameters(Maps.one(MediaTypeParameterName.with("p1"), "v1"));
    }

    private MediaType mediaType2() {
        return MediaType.with("type2", "subType2");
    }

    @Override
    protected MediaTypeListHeaderValueConverter converter() {
        return MediaTypeListHeaderValueConverter.INSTANCE;
    }

    @Override
    protected HttpHeaderName<List<MediaType>> name() {
        return HttpHeaderName.ACCEPT;
    }

    @Override
    protected String invalidHeaderValue() {
        return "invalid!!!";
    }

    @Override
    protected List<MediaType> value() {
        return MediaType.parseList("type1/sub1;p1=v1,type2/sub2;p2=v2");
    }

    @Override
    protected String converterToString() {
        return "List<MediaType>";
    }

    @Override
    protected Class<MediaTypeListHeaderValueConverter> type() {
        return MediaTypeListHeaderValueConverter.class;
    }
}