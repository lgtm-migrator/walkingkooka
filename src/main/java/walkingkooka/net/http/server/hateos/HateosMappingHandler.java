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

import walkingkooka.collect.map.Maps;
import walkingkooka.compare.Range;
import walkingkooka.net.http.server.HttpRequestAttribute;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Handles both single resources and collection requests producing their response.<br>
 * This assumes each ID maps to a single {@link HateosResource}.
 */
public interface HateosMappingHandler<I extends Comparable<I>, R extends HateosResource<?>> {

    /**
     * An empty {@link Map} with no parameters.
     */
    Map<HttpRequestAttribute<?>, Object> NO_PARAMETERS = Maps.empty();

    /**
     * Maps the resources matching the given id into a value.
     */
    Object handle(final I id,
                  final Optional<R> resource,
                  final Map<HttpRequestAttribute<?>, Object> parameters);

    /**
     * Handles a request which involves a collection of resources.
     */
    List<R> handleCollection(final Range<I> ids,
                             final List<R> resources,
                             final Map<HttpRequestAttribute<?>, Object> parameters);
}
