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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.UrlPathName;
import walkingkooka.net.header.ClientCookie;
import walkingkooka.net.header.CookieName;
import walkingkooka.net.header.HttpHeaderName;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A {@link Map} view of a {@link HttpRequest}.
 */
final class RouterHttpRequestParametersMap extends AbstractMap<HttpRequestAttribute, Object> {

    /**
     * Factory that creates a map of parameters from a {@link HttpRequest}.
     */
    static RouterHttpRequestParametersMap with(final HttpRequest request) {
        return new RouterHttpRequestParametersMap(request);
    }

    /**
     * Private ctor use factory.
     */
    private RouterHttpRequestParametersMap(final HttpRequest request) {
        super();
        this.request = request;
    }

    @Override
    public boolean containsKey(final Object key) {
        return null != this.get(key);
    }

    @Override
    public Object get(final Object key) {
        Object value = null;

        while (key instanceof HttpRequestAttribute) {
            if (key instanceof CookieName) {
                value = this.clientCookieOrNull(Cast.to(key));
                break;
            }
            if (key instanceof HttpHeaderName) {
                value = this.headers().get(key);
                break;
            }
            if (key instanceof HttpRequestAttributes) {
                value = HttpRequestAttributes.class.cast(key).value(this.request);
                break;
            }
            if(key instanceof HttpRequestParameterName) {
                value = this.parameters().get(key);
                break;
            }
            if (key instanceof UrlPathNameHttpRequestAttribute) {
                value = this.pathNameOrNull(UrlPathNameHttpRequestAttribute.class.cast(key).index);
                break;
            }
            break;
        }

        return value;
    }

    /**
     * Finds a cookie with the {@link CookieName}
     */
    private ClientCookie clientCookieOrNull(final CookieName name) {
        return this.request.cookies().stream()
                .filter(c -> c.name().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Lazily splits the path component into {@link UrlPathName name components}.
     */
    private UrlPathName pathNameOrNull(final int index) {
        final UrlPathName[] pathNames = this.pathNames();
        return index < pathNames.length ?
                pathNames[index] :
                null;
    }

    /**
     * Lazily gets the path names from the request url.
     */
    UrlPathName[] pathNames() {
        if (null == this.pathNames) {
            final List<UrlPathName> names = Lists.array();
            final Iterator<UrlPathName> i = this.url().path().iterator();

            while (i.hasNext()) {
                names.add(i.next());
            }
            this.pathNames = names.toArray(new UrlPathName[names.size()]);
        }
        return this.pathNames;
    }

    private transient UrlPathName[] pathNames;

    @Override
    public Set<Entry<HttpRequestAttribute, Object>> entrySet() {
        return this.entrySet;
    }

    private final RouterHttpRequestParametersMapEntrySet entrySet = RouterHttpRequestParametersMapEntrySet.with(this);

    @Override
    public int size() {
        return HttpRequestAttributes.size() +
                this.cookies().size() +
                this.headers().size() +
                this.pathNames().length +
                this.parameters().size();
    }

    private List<ClientCookie> cookies() {
        return this.request.cookies();
    }

    private Map<HttpHeaderName<?>, Object> headers() {
        return this.request.headers();
    }

    private Map<HttpRequestParameterName, List<String>> parameters() {
        return this.request.parameters();
    }

    private RelativeUrl url() {
        return this.request.url();
    }

    // Object ............................................................................................................

    @Override
    public final String toString() {
        return this.request.toString();
    }

    // shared
    final HttpRequest request;
}