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

import org.junit.jupiter.api.Test;
import walkingkooka.test.ToStringTesting;
import walkingkooka.test.TypeNameTesting;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface HttpResponseTesting<R extends HttpResponse> extends ToStringTesting<R>, TypeNameTesting<R> {

    @Test
    default void testSetStatusNullFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createResponse().setStatus(null);
        });
    }

    @Test
    default void testAddEntityNullFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createResponse().addEntity(null);
        });
    }

    R createResponse();

    // TypeNameTesting .........................................................................................

    @Override
    default String typeNamePrefix() {
        return "";
    }

    @Override
    default String typeNameSuffix() {
        return HttpResponse.class.getSimpleName();
    }
}