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

package walkingkooka.net.http.server.hateos;

import walkingkooka.net.http.server.HttpRequestAttribute;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A {@link HateosHandler} where all methods throw {@link UnsupportedOperationException}.
 */
public class FakeHateosIdResourceResourceHandler<I extends Comparable<I>, R extends HateosResource<?>, S extends HateosResource<?>>
        extends FakeHateosHandler<I, R, S>
        implements HateosIdResourceResourceHandler<I, R, S> {

    @Override
    public Optional<S> handle(final I id,
                              final Optional<R> resource,
                              final Map<HttpRequestAttribute<?>, Object> parameters) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(resource, "resource");
        Objects.requireNonNull(parameters, "parameters");

        throw new UnsupportedOperationException();
    }
}