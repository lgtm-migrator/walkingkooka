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

package walkingkooka.net.http;

import org.junit.Test;
import walkingkooka.collect.list.Lists;

import java.util.List;

public final class HttpHeaderValueHttpMethodListConverterTest extends
        HttpHeaderValueConverterTestCase<HttpHeaderValueHttpMethodListConverter, List<HttpMethod>> {

    @Test
    public void testGet() {
        this.parseAndCheck2("GET", HttpMethod.GET);
    }

    @Test
    public void testGetPost() {
        this.parseAndCheck2("GET,POST", HttpMethod.GET, HttpMethod.POST);
    }

    @Test
    public void testGetWhitespacePost() {
        this.parseAndCheck2("GET,  POST", HttpMethod.GET, HttpMethod.POST);
    }

    private void parseAndCheck2(final String headerValue, final HttpMethod... methods) {
        this.parseAndCheck(headerValue, Lists.of(methods));
    }

    @Override
    HttpHeaderName<List<HttpMethod>> headerOrParameterName() {
        return HttpHeaderName.ALLOW;
    }

    @Override
    HttpHeaderValueHttpMethodListConverter converter() {
        return HttpHeaderValueHttpMethodListConverter.INSTANCE;
    }

    @Override
    String invalidHeaderValue() {
        return "/relative/url/must/fail";
    }

    @Override
    String converterToString() {
        return "List<HttpMethod>";
    }

    @Override
    protected Class<HttpHeaderValueHttpMethodListConverter> type() {
        return HttpHeaderValueHttpMethodListConverter.class;
    }
}