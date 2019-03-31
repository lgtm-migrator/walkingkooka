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

import walkingkooka.Cast;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.type.MemberVisibility;

import java.util.function.BiConsumer;

public final class HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequestTest extends HateosHandlerRouterTestCase<HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequest<JsonNode>> {

    @Override
    public Class<HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequest<JsonNode>> type() {
        return Cast.to(HateosHandlerRouterHttpRequestHttpResponseBiConsumerRequest.class);
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }

    @Override
    String typeNamePrefix2() {
        return "HttpRequestHttpResponse";
    }

    @Override
    public String typeNameSuffix() {
        return BiConsumer.class.getSimpleName() + "Request";
    }
}
