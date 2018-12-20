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

package walkingkooka.net.http.server;

import org.junit.Test;
import walkingkooka.collect.enumeration.Enumerations;
import walkingkooka.collect.map.Maps;
import walkingkooka.collect.set.SetTestCase;
import walkingkooka.net.http.HttpHeaderName;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.assertEquals;

public final class HttpServletRequestHttpRequestHeadersMapEntrySetTest extends
        SetTestCase<HttpServletRequestHttpRequestHeadersMapEntrySet,
                Entry<HttpHeaderName<?>, Object>> {

    private final static HttpHeaderName<?> HEADER1 = HttpHeaderName.CONTENT_LENGTH;
    private final static Long VALUE1 = 111L;

    private final static HttpHeaderName<?> HEADER2 = HttpHeaderName.SERVER;
    private final static String VALUE2 = "Server2";

    @Test
    public void testContains() {
        final HttpServletRequest request = this.request();
        this.containsAndCheck(HttpServletRequestHttpRequestHeadersMapEntrySet.with(request),
                HttpServletRequestHttpRequestHeadersMapEntrySetIteratorEntry.with(HEADER1.value(), request));
    }

    @Test
    public void testIterator() {
        final Map<HttpHeaderName<?>, Object> entries = Maps.ordered();

        final HttpServletRequest request = this.request();
        for (Entry<HttpHeaderName<?>, Object> e : HttpServletRequestHttpRequestHeadersMapEntrySet.with(request)) {
            entries.put(e.getKey(), e.getValue());
        }

        final Map<HttpHeaderName<?>, Object> expected = Maps.ordered();
        expected.put(HEADER1, VALUE1);
        expected.put(HEADER2, VALUE2);

        assertEquals("iterator entries", expected, entries);
    }

    @Test
    public void testSize() {
        this.sizeAndCheck(this.createSet(), 2);
    }

    @Override
    protected HttpServletRequestHttpRequestHeadersMapEntrySet createSet() {
        return HttpServletRequestHttpRequestHeadersMapEntrySet.with(this.request());
    }

    private HttpServletRequest request() {
        return new FakeHttpServletRequest() {

            @Override
            public String getHeader(final String header) {
                if (HEADER1.value().equalsIgnoreCase(header)) {
                    return "" + VALUE1;
                }
                if (HEADER2.value().equalsIgnoreCase(header)) {
                    return "" + VALUE2;
                }

                throw new UnsupportedOperationException();
            }

            @Override
            public Enumeration<String> getHeaderNames() {
                return Enumerations.array(new Object[]{HEADER1.value(), HEADER2.value()});
            }
        };
    }

    @Override
    protected Class<HttpServletRequestHttpRequestHeadersMapEntrySet> type() {
        return HttpServletRequestHttpRequestHeadersMapEntrySet.class;
    }
}