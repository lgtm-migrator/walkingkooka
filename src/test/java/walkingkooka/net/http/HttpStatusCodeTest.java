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
import walkingkooka.collect.map.Maps;
import walkingkooka.test.EnumTestCase;

import java.util.Map;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

public final class HttpStatusCodeTest extends EnumTestCase<HttpStatusCode> {

    @Test
    public void testStatusCodeUnique() {
        final Map<Integer, HttpStatusCode> intToCode = Maps.ordered();
        for (HttpStatusCode code : HttpStatusCode.values()) {
            if(code == HttpStatusCode.FOUND || code == HttpStatusCode.MOVED_TEMPORARILY) {
                continue;
            }
            assertNull("Code value is not unique " + code.code() + "=" + code, intToCode.put(code.code(), code));
        }
    }

    @Test
    public void testStatusDefaultMessageUnique() {
        final Map<String, HttpStatusCode> intToCode = Maps.ordered();
        for (HttpStatusCode code : HttpStatusCode.values()) {
            assertNull("Default message is not unique " + code.message + "=" + code, intToCode.put(code.message, code));
        }
    }

    @Test
    public void testStatus() {
        for (HttpStatusCode code : HttpStatusCode.values()) {
            final HttpStatus status = code.status();
            assertSame("code", code, status.value());
            assertNotEquals("message", "", status.message());
        }
    }

    // category ....................................................................

    @Test
    public void testCategory1xx() {
        this.categoryAndCheck(HttpStatusCode.CONTINUE, HttpStatusCodeCategory.INFORMATION);
    }

    @Test
    public void testCategory2xx() {
        this.categoryAndCheck(HttpStatusCode.OK, HttpStatusCodeCategory.SUCCESSFUL);
    }

    @Test
    public void testCategory3xx() {
        this.categoryAndCheck(HttpStatusCode.MOVED_TEMPORARILY, HttpStatusCodeCategory.REDIRECTION);
    }

    @Test
    public void testCategory4xx() {
        this.categoryAndCheck(HttpStatusCode.BAD_REQUEST, HttpStatusCodeCategory.CLIENT_ERROR);
    }

    @Test
    public void testCategory5xx() {
        this.categoryAndCheck(HttpStatusCode.INTERNAL_SERVER_ERROR, HttpStatusCodeCategory.SERVER_ERROR);
    }

    private void categoryAndCheck(final HttpStatusCode code, final HttpStatusCodeCategory category) {
        assertSame("category for status code: " + code, category, code.category());
    }

    @Override
    protected Class<HttpStatusCode> type() {
        return HttpStatusCode.class;
    }
}