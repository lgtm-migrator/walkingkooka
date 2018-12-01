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

import org.junit.Ignore;
import org.junit.Test;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.Url;
import walkingkooka.net.http.HttpHeaderName;

public final class UrlHeaderValueConverterTest extends
        HeaderValueConverterTestCase<UrlHeaderValueConverter, Url> {

    @Override
    protected String requiredPrefix() {
        return Url.class.getSimpleName();
    }

    @Test
    @Ignore
    public void testInvalidHeaderValueFails() {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testLocationAbsoluteUrl() {
        final String url = "http://example.com";
        this.parseAndFormatAndCheck(url, AbsoluteUrl.parse(url));
    }

    @Test
    public void testLocationRelativeUrl() {
        final String url = "/relative/url/file.html";
        this.parseAndFormatAndCheck(url, RelativeUrl.parse(url));
    }

    @Override
    protected UrlHeaderValueConverter converter() {
        return UrlHeaderValueConverter.INSTANCE;
    }

    @Override
    protected HttpHeaderName<Url> name() {
        return HttpHeaderName.LOCATION;
    }

    @Override
    protected String invalidHeaderValue() {
        return "\\";
    }

    @Override
    protected Url value() {
        return Url.parse("/path?p1=v1");
    }

    @Override
    protected String converterToString() {
        return Url.class.getSimpleName();
    }

    @Override
    protected Class<UrlHeaderValueConverter> type() {
        return UrlHeaderValueConverter.class;
    }
}