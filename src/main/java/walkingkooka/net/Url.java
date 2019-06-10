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

package walkingkooka.net;

import walkingkooka.Cast;
import walkingkooka.Value;
import walkingkooka.test.HashCodeEqualsDefined;
import walkingkooka.text.CharacterConstant;

import java.util.Objects;
import java.util.Optional;

/**
 * Base class with getters that return the common components of a {@link Url}.
 */
public abstract class Url implements HashCodeEqualsDefined, Value<String> {

    // constants.......................................................................................................

    /**
     * The character that separates a host name or address from the following port number.
     */
    public final static CharacterConstant HOST_PORT_SEPARATOR = CharacterConstant.with(':');

    /**
     * The character within a URL that marks the beginning of a path.
     */
    public final static CharacterConstant PATH_START = CharacterConstant.with(UrlPath.SEPARATOR.character());

    /**
     * The character within a URL that marks the start of a query string.
     */
    public final static CharacterConstant QUERY_START = CharacterConstant.with('?');

    /**
     * The character within a URL that proceeds the query string.
     */
    public final static CharacterConstant QUERY_PARAMETER_SEPARATOR = CharacterConstant.with('&');

    /**
     * The alternate character within a URL that proceeds the query string.
     */
    public final static CharacterConstant QUERY_PARAMETER_SEPARATOR2 = CharacterConstant.with(';');

    /**
     * The character within a query string that separates name value pairs.
     */
    public final static CharacterConstant QUERY_NAME_VALUE_SEPARATOR = CharacterConstant.with('=');

    /**
     * The character proceeding an anchor within a URL.
     */
    public final static CharacterConstant FRAGMENT_START = CharacterConstant.with('#');

    // factories..................................................................................................

    /**
     * Examines the URL and attempts to parse it as a relative or absolute url.
     */
    public static Url parse(final String url) {
        Objects.requireNonNull(url, "url");

        final int colon = url.indexOf(':');
        final int slash = url.indexOf('/');

        return -1 != colon && colon < slash ?
                parseAbsolute(url) :
                parseRelative(url);
    }

    /**
     * Parses a {@link String url} into a {@link AbsoluteUrl}
     */
    public static AbsoluteUrl parseAbsolute(final String url) {
        return AbsoluteUrl.parse(url);
    }

    /**
     * Parses a {@link String url} into a {@link RelativeUrl}
     */
    public static RelativeUrl parseRelative(final String url) {
        return RelativeUrl.parse(url);
    }

    /**
     * Creates a {@link AbsoluteUrl}.
     */
    public static AbsoluteUrl absolute(final UrlScheme scheme,
                                       final Optional<UrlCredentials> credentials,
                                       final HostAddress host,
                                       final Optional<IpPort> port,
                                       final UrlPath path,
                                       final UrlQueryString query,
                                       final UrlFragment fragment) {
        return AbsoluteUrl.with(scheme, credentials, host, port, path, query, fragment);
    }

    /**
     * Creates an {@link RelativeUrl}.
     */
    public static RelativeUrl relative(final UrlPath path, final UrlQueryString query, final UrlFragment fragment) {
        return RelativeUrl.with(path, query, fragment);
    }

    /**
     * Helper used by all parse methods.
     */
    static String nullToEmpty(final String value) {
        return null == value ?
                "" :
                value;
    }

    // ctor.............................................................................................................

    /**
     * Package private constructor to limit sub classing.
     */
    Url() {
        super();
    }

    // isXXX............................................................................................................

    /**
     * Only {@link RelativeUrl} returns true
     */
    public abstract boolean isRelative();

    /**
     * Only {@link AbsoluteUrl} returns true.
     */
    public abstract boolean isAbsolute();

    // helper...........................................................................................................

    /**
     * Convenient cast method used by would be public setters.
     */
    final <U extends Url> U cast() {
        return Cast.to(this);
    }

    // Serializable

    private final static long serialVersionUID = 1L;
}
