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

import walkingkooka.net.header.LinkRelation;
import walkingkooka.net.http.HttpMethod;
import walkingkooka.net.http.server.HttpRequest;
import walkingkooka.tree.Node;

import java.util.List;

/**
 * A {@link HateosHandlerRouterMapperHateosIdHandlerMapping} for {@link HateosIdResourceCollectionResourceCollectionHandler}.
 */
final class HateosHandlerRouterMapperHateosIdResourceCollectionResourceCollectionHandlerMapping<I extends Comparable<I>,
        R extends HateosResource<?>,
        S extends HateosResource<?>>
        extends HateosHandlerRouterMapperHateosIdHandlerMapping<HateosIdResourceCollectionResourceCollectionHandler<I, R, S>, I, R, S> {

    static <I extends Comparable<I>,
            R extends HateosResource<?>,
            S extends HateosResource<?>>
    HateosHandlerRouterMapperHateosIdResourceCollectionResourceCollectionHandlerMapping<I, R, S> with(final HateosIdResourceCollectionResourceCollectionHandler<I, R, S> handler) {
        return new HateosHandlerRouterMapperHateosIdResourceCollectionResourceCollectionHandlerMapping<>(handler);
    }

    private HateosHandlerRouterMapperHateosIdResourceCollectionResourceCollectionHandlerMapping(final HateosIdResourceCollectionResourceCollectionHandler<I, R, S> handler) {
        super(handler);
    }

    @Override
    <N extends Node<N, ?, ?, ?>> void handleId(final HateosResourceName resourceName,
                                               final I id,
                                               final LinkRelation<?> linkRelation,
                                               final String requestText,
                                               final Class<R> resourceType,
                                               final HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequest<N> request) {
        final HateosContentType<N> hateosContentType = request.hateosContentType();
        final List<R> requestResource = request.resourcesListOrBadRequest(requestText,
                hateosContentType,
                resourceType,
                request);

        if (null != requestResource) {
            final HttpRequest httpRequest = request.request;
            final HttpMethod method = httpRequest.method();
            final List<S> responseResource = this.handler.handle(id,
                    requestResource,
                    request.parameters);

            request.setStatusAndBody(method + " resources successful", hateosContentType.toTextList(responseResource,
                    null,
                    method,
                    request.router.base,
                    resourceName,
                    request.router.linkRelations(resourceName)));
        }
    }
}