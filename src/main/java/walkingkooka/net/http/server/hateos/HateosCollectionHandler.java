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
import walkingkooka.net.http.server.HttpRequestAttribute;

import java.util.List;
import java.util.Map;

/**
 * Handles both single resources and collection requests producing their response.<br>
 * This assumes each ID maps to a zero or more {@link HateosResource resources}.
 */
public interface HateosCollectionHandler<I extends Comparable<I>, R extends HateosResource<?>> {

    /**
     * An empty {@link Map} with no parameters.
     */
    Map<HttpRequestAttribute<?>, Object> NO_PARAMETERS = Maps.empty();

    /**
     * Handles a request resource identified by the ID to multiple resources.
     */
    List<R> handle(final I id,
                   final List<R> resources,
                   final Map<HttpRequestAttribute<?>, Object> parameters);
}