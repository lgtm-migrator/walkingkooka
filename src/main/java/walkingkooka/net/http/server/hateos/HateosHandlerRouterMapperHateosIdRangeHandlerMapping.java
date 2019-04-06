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

import walkingkooka.compare.Range;
import walkingkooka.tree.Node;

abstract class HateosHandlerRouterMapperHateosIdRangeHandlerMapping<H extends HateosHandler<I, R, S>,
        I extends Comparable<I>,
        R extends HateosResource<?>,
        S extends HateosResource<?>>
        extends HateosHandlerRouterMapperHateosHandlerMapping<H, I, R, S> {

    /**
     * Package private to limit sub classing.
     */
    HateosHandlerRouterMapperHateosIdRangeHandlerMapping(final H handler) {
        super(handler);
    }

    /**
     * Handles a request for a range of ids returning a list of resources.
     */
    abstract <N extends Node<N, ?, ?, ?>> void handleIdRange(final HateosResourceName resourceName,
                                                             final Range<I> ids,
                                                             final String requestText,
                                                             final Class<R> resourceType,
                                                             final HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequest<N> request);
}