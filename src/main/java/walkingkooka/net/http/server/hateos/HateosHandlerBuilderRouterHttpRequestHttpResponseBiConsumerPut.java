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
import walkingkooka.net.header.LinkRelation;
import walkingkooka.net.http.server.HttpRequest;
import walkingkooka.net.http.server.HttpResponse;
import walkingkooka.tree.Node;

import java.math.BigInteger;

/**
 * Handles PUT requests.
 */
final class HateosHandlerBuilderRouterHttpRequestHttpResponseBiConsumerPut<N extends Node<N, ?, ?, ?>>
        extends HateosHandlerBuilderRouterHttpRequestHttpResponseBiConsumerMethod<N> {

    /**
     * Factory called by {@link HateosHandlerBuilder#build()}
     */
    static <N extends Node<N, ?, ?, ?>> HateosHandlerBuilderRouterHttpRequestHttpResponseBiConsumerPut<N> with(final HateosHandlerBuilderRouter<N> router,
                                                                                                               final HttpRequest request,
                                                                                                               final HttpResponse response) {
        return new HateosHandlerBuilderRouterHttpRequestHttpResponseBiConsumerPut<N>(router,
                request,
                response);
    }

    /**
     * Private ctor use factory.
     */
    private HateosHandlerBuilderRouterHttpRequestHttpResponseBiConsumerPut(final HateosHandlerBuilderRouter<N> router,
                                                                           final HttpRequest request,
                                                                           final HttpResponse response) {
        super(router, request, response);
    }

    @Override
    void idMissing(final HateosResourceName resourceName, final LinkRelation<?> linkRelation) {
        this.badRequestIdRequired();
    }

    @Override
    void wildcard(final HateosResourceName resourceName, final LinkRelation<?> linkRelation) {
        this.badRequestCollectionsUnsupported();
    }

    @Override
    void id(final HateosResourceName resourceName,
            final BigInteger id,
            final LinkRelation<?> linkRelation) {
        final HateosHandlerBuilderRouterHandlers<N> handlers = this.handlersOrResponseNotFound(resourceName, linkRelation);
        if (null != handlers) {
            final HateosPutHandler<N> put = this.handlerOrResponseMethodNot(resourceName, linkRelation, handlers.put);
            if (null != put) {
                this.setStatusAndBody("Put resource successful",
                        put.put(id,
                                this.resource(),
                                this.router.putContext));
            }
        }
    }

    @Override
    void collection(final HateosResourceName resourceName,
                    final Range<BigInteger> ids,
                    final LinkRelation<?> linkRelation) {
        this.badRequestCollectionsUnsupported();
    }
}