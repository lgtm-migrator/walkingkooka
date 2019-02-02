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


import org.junit.jupiter.api.Test;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.NameTesting;
import walkingkooka.net.http.server.FakeHttpRequest;
import walkingkooka.test.ClassTestCase;
import walkingkooka.text.CaseSensitivity;
import walkingkooka.type.MemberVisibility;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Set-Cookie
final public class CookieNameTest extends ClassTestCase<CookieName> implements NameTesting<CookieName, CookieName> {

    /**
     * A <cookie-name> can be any US-ASCII characters except control characters (CTLs), spaces, or tabs.
     * It also must not contain a separator character like the following: ( ) < > @ , ; : \ " /  [ ] ? = { }.
     */
    @Test
    public void testIncludeParensOpenFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie(");
        });
    }

    @Test
    public void testIncludeParensCloseFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie)");
        });
    }

    @Test
    public void testIncludeLessThanFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie<");
        });
    }

    @Test
    public void testIncludeGreaterThanFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie>");
        });
    }

    @Test
    public void testIncludeAtSignFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie@");
        });
    }

    @Test
    public void testIncludeCommaFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie,");
        });
    }

    @Test
    public void testIncludeSemiColonFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie;");
        });
    }

    @Test
    public void testIncludeColonFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie:");
        });
    }

    @Test
    public void testIncludeBackslashFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie\\");
        });
    }

    @Test
    public void testIncludeDoubleQuoteFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie\"");
        });
    }

    @Test
    public void testIncludeForwardSlashFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie/");
        });
    }

    @Test
    public void testIncludeBracketOpenFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie[");
        });
    }

    @Test
    public void testIncludeBracketCloseFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie]");
        });
    }

    @Test
    public void testQuestionMarkFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie?");
        });
    }


    @Test
    public void testEqualsSignFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie=");
        });
    }

    @Test
    public void testBraceOpenFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie{");
        });
    }

    @Test
    public void testBraceCloseFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            CookieName.with("cookie}");
        });
    }

    @Test
    public void testIncludesDigits() {
        this.createNameAndCheck("cookie123");
    }

    @Test
    public void testIncludesDash() {
        this.createNameAndCheck("cookie-123");
    }

    @Test
    public void testBeginsDollarSign() {
        this.createNameAndCheck("$cookie");
    }

    @Test
    public void testParameterValue() {
        final CookieName name = CookieName.with("cookie123");
        assertEquals(Optional.of(Cookie.client(name, "value123")),
                name.parameterValue(new FakeHttpRequest() {

                    @Override
                    public Map<HttpHeaderName<?>, Object> headers() {
                        return Maps.one(HttpHeaderName.COOKIE, Cookie.parseClientHeader("a=b;cookie123=value123;x=y"));
                    }
                }));
    }

    @Test
    public void testEqualsDifferentName() {
        this.checkNotEquals(CookieName.with("different"));
    }

    @Test
    public void testEqualsDifferentCase() {
        this.checkNotEquals(CookieName.with("COOKIE"));
    }

    @Test
    public void testToString() {
        assertEquals("cookie123", CookieName.with("cookie123").toString());
    }

    @Override
    public CookieName createName(final String name) {
        return CookieName.with(name);
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.SENSITIVE;
    }

    @Override
    public String nameText() {
        return "cookie123";
    }

    @Override
    public String differentNameText() {
        return "different";
    }

    @Override
    public String nameTextLess() {
        return "abc";
    }

    @Override
    protected Class<CookieName> type() {
        return CookieName.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }
}
