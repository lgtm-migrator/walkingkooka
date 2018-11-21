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
import walkingkooka.net.http.cookie.ClientCookie;

import java.util.List;

public final class HttpHeaderValueClientCookieListConverterTest extends
        HttpHeaderValueConverterTestCase<HttpHeaderValueClientCookieListConverter, List<ClientCookie>> {

    @Test
    public void testClientCookie() {
        final String header = "cookie123=value456";
        this.parseAndCheck(header, ClientCookie.parseHeader(header));
    }

    @Override
    HttpHeaderName<List<ClientCookie>> headerOrParameterName() {
        return HttpHeaderName.COOKIE;
    }

    @Override
    HttpHeaderValueClientCookieListConverter converter() {
        return HttpHeaderValueClientCookieListConverter.INSTANCE;
    }

    @Override
    String invalidHeaderValue() {
        return "";
    }

    @Override
    String converterToString() {
        return "List<ClientCookie>";
    }

    @Override
    protected Class<HttpHeaderValueClientCookieListConverter> type() {
        return HttpHeaderValueClientCookieListConverter.class;
    }
}