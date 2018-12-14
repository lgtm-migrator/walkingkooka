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
import walkingkooka.build.tostring.ToStringBuilder;
import walkingkooka.collect.list.Lists;
import walkingkooka.net.http.HttpEntity;
import walkingkooka.net.http.HttpStatus;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

/**
 * A response that records all parameters set up on it for later verification.
 */
final class TestHttpResponse implements HttpResponse {

    TestHttpResponse() {
        super();
    }

    TestHttpResponse(final HttpStatus status,
                     final List<HttpEntity> entities) {
        super();
        this.status = status;
        this.entities.addAll(entities);
    }

    @Override
    public void setStatus(final HttpStatus status) {
        this.status = status;
    }

    HttpStatus status;

    @Override
    public void addEntity(final HttpEntity entity) {
        Objects.requireNonNull(entity, "entity");
        this.entities.add(entity);
    }

    private final List<HttpEntity> entities = Lists.array();

    String bodyText;

    void check(final HttpRequest request,
               final HttpStatus status,
               final HttpEntity...entities) {
        assertEquals(request.toString(),
                new TestHttpResponse(status, Lists.of(entities)),
                this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.status, this.entities);
    }

    public boolean equals(final Object other) {
        return this == other || other instanceof TestHttpResponse && this.equals0(Cast.to(other));
    }

    private boolean equals0(final TestHttpResponse other) {
        return Objects.equals(this.status, other.status) &&
                Objects.equals(this.entities, other.entities);
    }

    @Override
    public String toString() {
        return ToStringBuilder.create()
                .value(this.status)
                .value(this.entities)
                .build();
    }
}
