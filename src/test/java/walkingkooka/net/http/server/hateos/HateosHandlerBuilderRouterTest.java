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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.compare.Range;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.Url;
import walkingkooka.net.header.AcceptCharset;
import walkingkooka.net.header.CharsetName;
import walkingkooka.net.header.HttpHeaderName;
import walkingkooka.net.header.LinkRelation;
import walkingkooka.net.http.HttpEntity;
import walkingkooka.net.http.HttpMethod;
import walkingkooka.net.http.HttpProtocolVersion;
import walkingkooka.net.http.HttpStatus;
import walkingkooka.net.http.HttpStatusCode;
import walkingkooka.net.http.HttpTransport;
import walkingkooka.net.http.server.HttpRequest;
import walkingkooka.net.http.server.HttpRequestAttribute;
import walkingkooka.net.http.server.HttpRequestParameterName;
import walkingkooka.net.http.server.HttpResponse;
import walkingkooka.net.http.server.HttpResponses;
import walkingkooka.net.http.server.TestRecordingHttpResponse;
import walkingkooka.routing.RouterTesting;
import walkingkooka.test.ClassTesting2;
import walkingkooka.test.Latch;
import walkingkooka.tree.json.HasJsonNode;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.type.MemberVisibility;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class HateosHandlerBuilderRouterTest implements ClassTesting2<HateosHandlerBuilderRouter<JsonNode, HasJsonNode>>,
        RouterTesting<HateosHandlerBuilderRouter<JsonNode, HasJsonNode>,
                        HttpRequestAttribute<?>,
                        BiConsumer<HttpRequest, HttpResponse>> {

    final static byte[] NO_BODY = new byte[0];
    final static byte[] BODY = "{\"prop1\":\"value1\"}".getBytes(Charset.defaultCharset());

    final static BigInteger ID = BigInteger.valueOf(123);
    final static BigInteger NO_ID = null;
    final static Range<BigInteger> ALL = Range.all();
    final static Range<BigInteger> RANGE = Range.greaterThanEquals(BigInteger.valueOf(123)).and(Range.lessThanEquals(BigInteger.valueOf(456)));

    private final static Function<String, BigInteger> ID_PARSER = BigInteger::new;

    private final static FakeHateosGetHandler<BigInteger, JsonNode> GET_HANDLER = new FakeHateosGetHandler<>();
    private final static FakeHateosPostHandler<BigInteger, JsonNode> POST_HANDLER = new FakeHateosPostHandler<>();
    private final static FakeHateosPutHandler<BigInteger, JsonNode> PUT_HANDLER = new FakeHateosPutHandler<>();
    private final static FakeHateosDeleteHandler<BigInteger, JsonNode> DELETE_HANDLER = new FakeHateosDeleteHandler<>();


    // GET RESOURCE ................................................................................................

    @Test
    public void testGetInvalidResourceNameBadRequest() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/999/123/self",
                HttpStatusCode.BAD_REQUEST,
                "Invalid resource name \"999\"",
                null);
    }

    @Test
    public void testGetWithoutHandlersNotFound() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource3/123/xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testGetInvalidIdBadRequest() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource1/abc123/self",
                HttpStatusCode.BAD_REQUEST,
                "Invalid id \"abc123\"",
                null);
    }

    @Test
    public void testGetInvalidRangeBeginningBadRequest() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource1/-456/self",
                HttpStatusCode.BAD_REQUEST,
                "Invalid range begin \"-456\"",
                null);
    }

    @Test
    public void testGetInvalidRangeEndBadRequest() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource1/123-/self",
                HttpStatusCode.BAD_REQUEST,
                "Invalid range end \"123-\"",
                null);
    }

    @Test
    public void testGetInvalidLinkRelationBadRequest() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource1/123/999",
                HttpStatusCode.BAD_REQUEST,
                "Invalid link relation \"999\"",
                null);
    }

    @Test
    public void testGetIdWithRelationSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetHandler(b, "{}", getted),
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Get resource successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetIdWithoutRelationDefaultsSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetHandler(b, "{}", getted),
                "/api/resource1/123?param1=value1",
                HttpStatusCode.OK,
                "Get resource successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetIdIgnoresAlternatives() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> {
                    this.addGetHandler(b, "{}", getted);
                    b.get(this.resourceName1(), ID_PARSER, this.relation2(), GET_HANDLER);
                    b.get(this.resourceName2(), ID_PARSER, this.relation1(), GET_HANDLER);
                },
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Get resource successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {
                    @Override
                    public Optional<JsonNode> get(final BigInteger id,
                                                  final Map<HttpRequestParameterName, List<String>> parameters,
                                                  final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.GET,
                "/api/resource1/123/self",
                NO_BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testGetUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {
                    @Override
                    public Optional<JsonNode> get(final BigInteger id,
                                                  final Map<HttpRequestParameterName, List<String>> parameters,
                                                  final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.GET,
                "/api/resource1/123/self",
                NO_BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    @Test
    public void testGetContextAddLinks() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {
                    @Override
                    public Optional<JsonNode> get(final BigInteger id,
                                                  final Map<HttpRequestParameterName, List<String>> parameters,
                                                  final HateosHandlerContext<JsonNode> context) {
                        return Optional.of(context.addLinks(resourceName1(), id, JsonNode.parse("{\"p1\": \"v1\"}")));
                    }
                });
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation2(),
                GET_HANDLER);

        this.routeHandleAndCheck(builder,
                HttpMethod.GET,
                "/api/resource1/123/self",
                NO_BODY,
                HttpStatusCode.OK.setMessage("Get resource successful"),
                this.httpEntity("{\n" +
                        "  \"p1\": \"v1\",\n" +
                        "  \"_links\": [{\n" +
                        "    \"href\": \"http://www.example.com/api/resource1/123/next\",\n" +
                        "    \"method\": \"GET\",\n" +
                        "    \"rel\": \"next\",\n" +
                        "    \"type\": \"application/hal+json\"\n" +
                        "  }, {\n" +
                        "    \"href\": \"http://www.example.com/api/resource1/123\",\n" +
                        "    \"method\": \"GET\",\n" +
                        "    \"rel\": \"self\",\n" +
                        "    \"type\": \"application/hal+json\"\n" +
                        "  }]\n" +
                        "}"));
    }

    private void addGetHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                               final String reply,
                               final Latch getted) {
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {

                    @Override
                    public Optional<JsonNode> get(final BigInteger id,
                                                  final Map<HttpRequestParameterName, List<String>> parameters,
                                                  final HateosHandlerContext<JsonNode> context) {
                        assertEquals(ID, id, "id");
                        assertEquals(Lists.of("value1"),
                                parameters.get(HttpRequestParameterName.with("param1")),
                                "parameters");

                        getted.set("123 getted");
                        return Optional.ofNullable(null != reply ?
                                JsonNode.parse(reply) : null);
                    }
                });
    }

    // GET COLLECTION WITHOUT ID ................................................................................................

    @Test
    public void testGetCollectionWithoutIdWithoutHandlersNotFound() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource3//xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testGetCollectionWithoutIdWithRelationSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, ALL, "{}", getted),
                "/api/resource1//self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionWithoutIdWithoutRelationDefaultsSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, ALL, "{}", getted),
                "/api/resource1/?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionWithoutIdIgnoresAlternatives() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> {
                    this.addGetCollectionHandler(b, ALL, "{}", getted);
                    b.get(this.resourceName1(), ID_PARSER, this.relation2(), GET_HANDLER);
                    b.get(this.resourceName2(), ID_PARSER, this.relation1(), GET_HANDLER);
                },
                "/api/resource1//self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    // GET COLLECTION THROWS ............................................................................................

    @Test
    public void testGetCollectionIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {
                    @Override
                    public Optional<JsonNode> getCollection(final Range<BigInteger> id,
                                                            final Map<HttpRequestParameterName, List<String>> parameters,
                                                            final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.GET,
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testGetCollectionUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {
                    @Override
                    public Optional<JsonNode> getCollection(final Range<BigInteger> id,
                                                            final Map<HttpRequestParameterName, List<String>> parameters,
                                                            final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.GET,
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    // GET COLLECTION WILDCARD ................................................................................................

    @Test
    public void testGetCollectionWildcardWithoutHandlersNotFound() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource3/*/xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testGetCollectionWildcardWithRelationSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, ALL, "{}", getted),
                "/api/resource1/*/self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionWildcardWithoutRelationDefaultsSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, ALL, "{}", getted),
                "/api/resource1/*?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionWildcardIgnoresAlternatives() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> {
                    this.addGetCollectionHandler(b, ALL, "{}", getted);
                    b.get(this.resourceName1(), ID_PARSER, this.relation2(), GET_HANDLER);
                    b.get(this.resourceName2(), ID_PARSER, this.relation1(), GET_HANDLER);
                },
                "/api/resource1/*/self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    // GET COLLECTION RANGE ................................................................................................

    @Test
    public void testGetCollectionRangeWithoutHandlersNotFound() {
        this.routeGetHandleAndCheck((b) -> b.get(this.resourceName1(), ID_PARSER, this.relation1(), GET_HANDLER),
                "/api/resource3/123-456/xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testGetCollectionRangeWithRelationSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, RANGE, "{}", getted),
                "/api/resource1/123-456/self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionRangeWithoutRelationDefaultsSelf() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> this.addGetCollectionHandler(b, RANGE, "{}", getted),
                "/api/resource1/123-456?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    @Test
    public void testGetCollectionRangeIgnoresAlternatives() {
        final Latch getted = Latch.create();

        this.routeGetHandleAndCheck((b) -> {
                    this.addGetCollectionHandler(b, RANGE, "{}", getted);
                    b.get(this.resourceName1(), ID_PARSER, this.relation2(), GET_HANDLER);
                    b.get(this.resourceName2(), ID_PARSER, this.relation1(), GET_HANDLER);
                },
                "/api/resource1/123-456/self?param1=value1",
                HttpStatusCode.OK,
                "Get collection successful",
                "{}");

        assertEquals(true, getted.value(), "Getted");
    }

    // GET HELPERS.....................................................................................................

    private void addGetCollectionHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                                         final Range<BigInteger> ids,
                                         final String reply,
                                         final Latch getted) {
        builder.get(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosGetHandler<BigInteger, JsonNode>() {

                    @Override
                    public Optional<JsonNode> getCollection(final Range<BigInteger> i,
                                                            final Map<HttpRequestParameterName, List<String>> parameters,
                                                            final HateosHandlerContext<JsonNode> context) {
                        assertEquals(ids, i, "ids");
                        assertEquals(Lists.of("value1"),
                                parameters.get(HttpRequestParameterName.with("param1")),
                                "parameters");

                        getted.set("123 getted");
                        return Optional.ofNullable(null != reply ?
                                JsonNode.parse(reply) : null);
                    }
                });
    }

    private void routeGetHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                        final String url,
                                        final HttpStatusCode status,
                                        final String statusMessage,
                                        final String body) {
        this.routeHandleAndCheck(build,
                HttpMethod.GET,
                url,
                NO_BODY,
                status.setMessage(statusMessage),
                this.httpEntity(body));
    }

    // POST RESOURCE ID................................................................................................

    @Test
    public void testPostIdWithoutHandlersNotFound() {
        this.routePostHandleAndCheck((b) -> b.post(this.resourceName1(), ID_PARSER, this.relation1(), POST_HANDLER),
                "/api/resource3/123/xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testPostIdWithRelationSelf() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> this.addPostHandler(b, ID, "{}", posted),
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    @Test
    public void testPostIdWithoutRelationDefaultsSelf() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> this.addPostHandler(b, ID, "{}", posted),
                "/api/resource1/123?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    @Test
    public void testPostIdIgnoresAlternatives() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> {
                    this.addPostHandler(b, ID, "{}", posted);
                    b.post(this.resourceName1(), ID_PARSER, this.relation2(), POST_HANDLER);
                    b.post(this.resourceName2(), ID_PARSER, this.relation1(), POST_HANDLER);
                },
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    // POST RESOURCE WITHOUT ID................................................................................................

    @Test
    public void testPostWithoutIdWithoutHandlersNotFound() {
        this.routePostHandleAndCheck((b) -> b.post(this.resourceName1(), ID_PARSER, this.relation1(), POST_HANDLER),
                "/api/resource3//xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testPostWithoutIdWithRelationSelf() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> this.addPostHandler(b, NO_ID, "{}", posted),
                "/api/resource1//self?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    @Test
    public void testPostWithoutIdWithoutRelationDefaultsSelf() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> this.addPostHandler(b, NO_ID, "{}", posted),
                "/api/resource1/?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    @Test
    public void testPostWithoutIdIgnoresAlternatives() {
        final Latch posted = Latch.create();

        this.routePostHandleAndCheck((b) -> {
                    this.addPostHandler(b, NO_ID, "{}", posted);
                    b.post(this.resourceName1(), ID_PARSER, this.relation2(), POST_HANDLER);
                    b.post(this.resourceName2(), ID_PARSER, this.relation1(), POST_HANDLER);
                },
                "/api/resource1//self?param1=value1",
                HttpStatusCode.OK,
                "Post resource successful",
                "{}");

        assertEquals(true, posted.value(), "Posted");
    }

    // POST RESOURCE THROWS................................................................................................

    @Test
    public void testPostIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.post(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPostHandler<BigInteger, JsonNode>() {
                    @Override
                    public JsonNode post(final Optional<BigInteger> id,
                                         final JsonNode body,
                                         final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.POST,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testPostUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.post(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPostHandler<BigInteger, JsonNode>() {
                    @Override
                    public JsonNode post(final Optional<BigInteger> id,
                                         final JsonNode body,
                                         final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.POST,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    // POST RESOURCE WILDCARD................................................................................................

    @Test
    public void testPostWildcardFails() {
        this.routePostHandleAndCheck((b) -> b.post(this.resourceName1(), ID_PARSER, this.relation1(), POST_HANDLER),
                "/api/resource1/*/self",
                HttpStatusCode.BAD_REQUEST,
                "Collections not supported",
                null);
    }

    // POST RESOURCE RANGE................................................................................................

    @Test
    public void testPostRangeWithoutHandlersBadRequest() {
        this.routePostHandleAndCheck((b) -> b.post(this.resourceName1(), ID_PARSER, this.relation1(), POST_HANDLER),
                "/api/resource3/123-456/xyz",
                HttpStatusCode.BAD_REQUEST,
                "Collections not supported",
                null);
    }

    private void addPostHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                                final BigInteger id,
                                final String reply,
                                final Latch posted) {
        builder.post(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPostHandler<BigInteger, JsonNode>() {

                    @Override
                    public JsonNode post(final Optional<BigInteger> i,
                                         final JsonNode post,
                                         final HateosHandlerContext<JsonNode> context) {
                        assertEquals(Optional.ofNullable(id), i, "id");

                        posted.set("123 posted");
                        return null != reply ?
                                JsonNode.parse(reply) :
                                null;
                    }
                });
    }

    private void routePostHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                         final String url,
                                         final HttpStatusCode status,
                                         final String statusMessage,
                                         final String body) {
        this.routeHandleAndCheck(build,
                HttpMethod.POST,
                url,
                BODY,
                status.setMessage(statusMessage),
                this.httpEntity(body));
    }

    // PUT RESOURCE ID................................................................................................

    @Test
    public void testPutIdWithoutHandlersNotFound() {
        this.routePutHandleAndCheck((b) -> b.put(this.resourceName1(), ID_PARSER, this.relation1(), PUT_HANDLER),
                "/api/resource3/123/xyz",
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz",
                null);
    }

    @Test
    public void testPutIdWithRelationSelf() {
        final Latch putted = Latch.create();

        this.routePutHandleAndCheck((b) -> this.addPutHandler(b, ID, "{}", putted),
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Put resource successful",
                "{}");

        assertEquals(true, putted.value(), "Putted");
    }

    @Test
    public void testPutIdWithoutRelationDefaultsSelf() {
        final Latch putted = Latch.create();

        this.routePutHandleAndCheck((b) -> this.addPutHandler(b, ID, "{}", putted),
                "/api/resource1/123?param1=value1",
                HttpStatusCode.OK,
                "Put resource successful",
                "{}");

        assertEquals(true, putted.value(), "Putted");
    }

    @Test
    public void testPutIdIgnoresAlternatives() {
        final Latch putted = Latch.create();

        this.routePutHandleAndCheck((b) -> {
                    this.addPutHandler(b, ID, "{}", putted);
                    b.put(this.resourceName1(), ID_PARSER, this.relation2(), PUT_HANDLER);
                    b.put(this.resourceName2(), ID_PARSER, this.relation1(), PUT_HANDLER);
                },
                "/api/resource1/123/self?param1=value1",
                HttpStatusCode.OK,
                "Put resource successful",
                "{}");

        assertEquals(true, putted.value(), "Putted");
    }

    // PUT RESOURCE WILDCARD................................................................................................

    @Test
    public void testPutWithoutIdBadRequest() {
        this.routePutHandleAndCheck((b) -> b.put(this.resourceName1(), ID_PARSER, this.relation1(), PUT_HANDLER),
                "/api/resource1//self",
                HttpStatusCode.BAD_REQUEST,
                "Id required",
                null);
    }

    // PUT RESOURCE WILDCARD................................................................................................

    @Test
    public void testPutWildcardBadRequest() {
        this.routePutHandleAndCheck((b) -> b.put(this.resourceName1(), ID_PARSER, this.relation1(), PUT_HANDLER),
                "/api/resource1/*/self",
                HttpStatusCode.BAD_REQUEST,
                "Collections not supported",
                null);
    }

    // PUT RESOURCE RANGE................................................................................................

    @Test
    public void testPutRangeWithoutHandlersNotFound() {
        this.routePutHandleAndCheck((b) -> b.put(this.resourceName1(), ID_PARSER, this.relation1(), PUT_HANDLER),
                "/api/resource3/123-456/xyz",
                HttpStatusCode.BAD_REQUEST,
                "Collections not supported",
                null);
    }

    // PUT RESOURCE THROWS................................................................................................

    @Test
    public void testPutIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.put(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPutHandler<BigInteger, JsonNode>() {
                    @Override
                    public JsonNode put(final BigInteger id,
                                        final JsonNode body,
                                        final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.PUT,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testPutUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.put(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPutHandler<BigInteger, JsonNode>() {
                    @Override
                    public JsonNode put(final BigInteger id,
                                        final JsonNode body,
                                        final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.PUT,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    private void addPutHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                               final BigInteger id,
                               final String reply,
                               final Latch putted) {
        builder.put(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosPutHandler<BigInteger, JsonNode>() {

                    @Override
                    public JsonNode put(final BigInteger i,
                                        final JsonNode put,
                                        final HateosHandlerContext<JsonNode> context) {
                        assertEquals(id, i, "id");

                        putted.set("123 putted");
                        return null != reply ?
                                JsonNode.parse(reply) :
                                null;
                    }
                });
    }

    private void routePutHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                        final String url,
                                        final HttpStatusCode status,
                                        final String statusMessage,
                                        final String body) {
        this.routeHandleAndCheck(build,
                HttpMethod.PUT,
                url,
                BODY,
                status.setMessage(statusMessage),
                this.httpEntity(body));
    }

    // DELETE RESOURCE ................................................................................................

    @Test
    public void testDeleteIdInvalidBodyBadRequest() {
        this.routeDeleteHandleAndCheck((b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource1/123/self",
                "{".getBytes(Charset.defaultCharset()),
                HttpStatusCode.BAD_REQUEST,
                "Invalid content: End of text at (2,1) \"{\" expected [ OBJECT_PROPERTY, [{[ WHITESPACE ], SEPARATOR, OBJECT_PROPERTY_REQUIRED }]], [ WHITESPACE ], OBJECT_END");
    }

    @Test
    public void testDeleteIdWithoutHandlersNotFound() {
        this.routeDeleteHandleAndCheck((b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource3/123/xyz",
                NO_BODY,
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz");
    }

    @Test
    public void testDeleteIdWithRelationSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteHandleAndCheck((b) -> this.addDeleteHandler(b, NO_BODY, deleted),
                "/api/resource1/123/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete resource successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteIdWithoutRelationDefaultsSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteHandleAndCheck((b) -> this.addDeleteHandler(b, NO_BODY, deleted),
                "/api/resource1/123",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete resource successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteIdIgnoresAlternatives() {
        final Latch deleted = Latch.create();

        this.routeDeleteHandleAndCheck((b) -> {
                    this.addDeleteHandler(b, NO_BODY, deleted);
                    b.delete(this.resourceName1(), ID_PARSER, this.relation2(), DELETE_HANDLER);
                    b.delete(this.resourceName2(), ID_PARSER, this.relation1(), DELETE_HANDLER);
                },
                "/api/resource1/123/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete resource successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteWithBody() {
        final Latch deleted = Latch.create();

        this.routeDeleteHandleAndCheck((b) -> this.addDeleteHandler(b, BODY, deleted),
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete resource successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    private void addDeleteHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                                  final byte[] resource,
                                  final Latch deleted) {

        final Optional<JsonNode> resourceJson = Optional.ofNullable(null != resource && resource.length > 0 ?
                JsonNode.parse(new String(resource)) :
                null);

        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void delete(final BigInteger id,
                                       final Optional<JsonNode> r,
                                       final HateosHandlerContext<JsonNode> context) {
                        assertEquals(ID, id, "id");
                        assertEquals(resourceJson, r, "resource");
                        deleted.set("123 deleted");
                    }
                });
    }

    private void routeDeleteHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                           final String url,
                                           final byte[] resource,
                                           final HttpStatusCode status,
                                           final String statusMessage) {
        this.routeHandleAndCheck(build,
                HttpMethod.DELETE,
                url,
                resource,
                status.setMessage(statusMessage));
    }

    // DELETE RESOURCE THROWS................................................................................................

    @Test
    public void testDeleteIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void delete(final BigInteger id,
                                       final Optional<JsonNode> resource,
                                       final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.DELETE,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testDeleteUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void delete(final BigInteger id,
                                       final Optional<JsonNode> resource,
                                       final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.DELETE,
                "/api/resource1/123/self",
                BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    // DeleteCollection WithoutId RESOURCE ................................................................................................

    @Test
    public void testDeleteCollectionWithInvalidBodyBadRequest() {
        this.routeDeleteCollectionHandleAndCheck((b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource1/*/self",
                "{".getBytes(Charset.defaultCharset()),
                HttpStatusCode.BAD_REQUEST,
                "Invalid content: End of text at (2,1) \"{\" expected [ OBJECT_PROPERTY, [{[ WHITESPACE ], SEPARATOR, OBJECT_PROPERTY_REQUIRED }]], [ WHITESPACE ], OBJECT_END");
    }

    @Test
    public void testDeleteCollectionWithoutIdWithoutHandlersNotFound() {
        this.routeDeleteCollectionHandleAndCheck((b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource3//xyz",
                NO_BODY,
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz");
    }

    @Test
    public void testDeleteCollectionWithoutIdWithRelationSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> this.addDeleteCollectionHandler(b, ALL, deleted),
                "/api/resource1//self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }


    @Test
    public void testDeleteCollectionWithoutIdWithoutRelationDefaultsSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> this.addDeleteCollectionHandler(b, ALL, deleted),
                "/api/resource1/",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteCollectionWithoutIdIgnoresAlternatives() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> {
                    this.addDeleteCollectionHandler(b, ALL, deleted);
                    b.delete(this.resourceName1(), ID_PARSER, this.relation2(), DELETE_HANDLER);
                    b.delete(this.resourceName2(), ID_PARSER, this.relation1(), DELETE_HANDLER);
                },
                "/api/resource1//self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    // DeleteCollectionWildcard RESOURCE ................................................................................................

    @Test
    public void testDeleteCollectionWildcardWithoutHandlersNotFound() {
        this.routeDeleteCollectionHandleAndCheck((b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource3/*/xyz",
                NO_BODY,
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz");
    }

    @Test
    public void testDeleteCollectionWildcardWithRelationSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> this.addDeleteCollectionHandler(b, ALL, deleted),
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }


    @Test
    public void testDeleteCollectionWildcardWithoutRelationDefaultsSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> this.addDeleteCollectionHandler(b, ALL, deleted),
                "/api/resource1/*",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteCollectionWildcardIgnoresAlternatives() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> {
                    this.addDeleteCollectionHandler(b, ALL, deleted);
                    b.delete(this.resourceName1(), ID_PARSER, this.relation2(), DELETE_HANDLER);
                    b.delete(this.resourceName2(), ID_PARSER, this.relation1(), DELETE_HANDLER);
                },
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    // DeleteCollection Range  ................................................................................................

    @Test
    public void testDeleteCollectionRangeWithoutHandlersNotFound() {
        this.routeDeleteCollectionHandleAndCheck(
                (b) -> b.delete(this.resourceName1(), ID_PARSER, this.relation1(), DELETE_HANDLER),
                "/api/resource3/123-456/xyz",
                NO_BODY,
                HttpStatusCode.NOT_FOUND,
                "resource: resource3, link relation: xyz");
    }

    @Test
    public void testDeleteCollectionRangeWithRelationSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck(
                (b) -> this.addDeleteCollectionHandler(b, RANGE, deleted),
                "/api/resource1/123-456/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }


    @Test
    public void testDeleteCollectionRangeWithoutRelationDefaultsSelf() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck(
                (b) -> this.addDeleteCollectionHandler(b, RANGE, deleted),
                "/api/resource1/123-456",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteCollectionRangeIgnoresAlternatives() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> {
                    this.addDeleteCollectionHandler(b, RANGE, deleted);
                    b.delete(this.resourceName1(), ID_PARSER, this.relation2(), DELETE_HANDLER);
                    b.delete(this.resourceName2(), ID_PARSER, this.relation1(), DELETE_HANDLER);
                },
                "/api/resource1/123-456/self",
                NO_BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    @Test
    public void testDeleteCollectionWithBody() {
        final Latch deleted = Latch.create();

        this.routeDeleteCollectionHandleAndCheck((b) -> {
                    this.addDeleteCollectionHandler(b, RANGE, deleted);
                    b.delete(this.resourceName1(), ID_PARSER, this.relation2(), DELETE_HANDLER);
                    b.delete(this.resourceName2(), ID_PARSER, this.relation1(), DELETE_HANDLER);
                },
                "/api/resource1/123-456/self",
                BODY,
                HttpStatusCode.NO_CONTENT,
                "Delete collection successful");

        assertEquals(true, deleted.value(), "Deleted");
    }

    // DELETE COLLECTION THROWS ............................................................................................

    @Test
    public void testDeleteCollectionIllegalArgumentExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void deleteCollection(final Range<BigInteger> id,
                                                 final Optional<JsonNode> parameters,
                                                 final HateosHandlerContext<JsonNode> context) {
                        throw new IllegalArgumentException("Invalid parameter");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.DELETE,
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.BAD_REQUEST.setMessage("Invalid parameter"));
    }

    @Test
    public void testDeleteCollectionUnsupportedOperationExceptionThrownBadRequest() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void deleteCollection(final Range<BigInteger> id,
                                                 final Optional<JsonNode> parameters,
                                                 final HateosHandlerContext<JsonNode> context) {
                        throw new UnsupportedOperationException("Not available 123");
                    }
                });
        this.routeHandleAndCheck(builder,
                HttpMethod.DELETE,
                "/api/resource1/*/self",
                NO_BODY,
                HttpStatusCode.NOT_IMPLEMENTED.setMessage("Not available 123"));
    }

    // DELETE HELPERS.....................................................................................................

    private void addDeleteCollectionHandler(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                                            final Range<BigInteger> range,
                                            final Latch deleted) {
        builder.delete(this.resourceName1(),
                ID_PARSER,
                this.relation1(),
                new FakeHateosDeleteHandler<BigInteger, JsonNode>() {
                    @Override
                    public void deleteCollection(final Range<BigInteger> r,
                                                 final Optional<JsonNode> parameters,
                                                 final HateosHandlerContext<JsonNode> context) {
                        assertEquals(range, r, "range");
                        deleted.set("123-456 deleted");
                    }
                });
    }

    private void routeDeleteCollectionHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                                     final String url,
                                                     final byte[] body,
                                                     final HttpStatusCode status,
                                                     final String statusMessage) {
        this.routeHandleAndCheck(build,
                HttpMethod.DELETE,
                url,
                body,
                status.setMessage(statusMessage));
    }

    // HELPERS ............................................................................................

    @Override
    public HateosHandlerBuilderRouter<JsonNode, HasJsonNode> createRouter() {
        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        builder.get(this.resourceName1(), ID_PARSER, this.relation1(), new FakeHateosGetHandler<BigInteger, JsonNode>());
        return Cast.to(builder.build()); // builder returns interface which is HHBR class.
    }

    private HateosHandlerBuilder<JsonNode, HasJsonNode> builder() {
        return HateosHandlerBuilder.with(
                Url.parseAbsolute("http://www.example.com/api"),
                HateosContentType.JSON);
    }

    private HateosResourceName resourceName1() {
        return HateosResourceName.with("resource1");
    }

    private HateosResourceName resourceName2() {
        return HateosResourceName.with("resource2");
    }

    private LinkRelation<?> relation1() {
        return LinkRelation.SELF;
    }

    private LinkRelation<?> relation2() {
        return LinkRelation.NEXT;
    }

    private HttpEntity[] httpEntity(final String body) {
        HttpEntity[] entities = new HttpEntity[0];

        if (null != body) {
            final CharsetName charsetName = CharsetName.UTF_8;
            final byte[] bytes = body.getBytes(charsetName.charset().get());

            final Map<HttpHeaderName<?>, Object> headers = Maps.sorted();
            headers.put(HttpHeaderName.CONTENT_TYPE, HateosContentType.JSON.contentType().setCharset(charsetName));
            headers.put(HttpHeaderName.CONTENT_LENGTH, Long.valueOf(body.length()));
            entities = new HttpEntity[]{HttpEntity.with(headers, bytes)};
        }
        return entities;
    }

    private void routeHandleAndCheck(final Consumer<HateosHandlerBuilder<JsonNode, HasJsonNode>> build,
                                     final HttpMethod method,
                                     final String url,
                                     final byte[] body,
                                     final HttpStatus status,
                                     final HttpEntity... entities) {
        assertTrue(url.startsWith("/api"), url + " must start with /api");

        final HateosHandlerBuilder<JsonNode, HasJsonNode> builder = this.builder();
        build.accept(builder);

        this.routeHandleAndCheck(builder, method, url, body, status, entities);
    }

    private void routeHandleAndCheck(final HateosHandlerBuilder<JsonNode, HasJsonNode> builder,
                                     final HttpMethod method,
                                     final String url,
                                     final byte[] body,
                                     final HttpStatus status,
                                     final HttpEntity... entities) {
        assertTrue(url.startsWith("/api"), url + " must start with /api");

        final TestRecordingHttpResponse response = HttpResponses.testRecording();

        final HttpRequest request = this.request(method, url, body);
        Optional<BiConsumer<HttpRequest, HttpResponse>> handle = builder.build()
                .route(request.routingParameters());
        if (handle.isPresent()) {
            handle.get().accept(request, response);
        }
        response.check(request, status, entities);
    }


    private HttpRequest request(final HttpMethod method,
                                final String url,
                                final byte[] body) {
        return new HttpRequest() {

            @Override
            public HttpTransport transport() {
                return HttpTransport.UNSECURED;
            }

            @Override
            public HttpProtocolVersion protocolVersion() {
                return HttpProtocolVersion.VERSION_1_0;
            }

            @Override
            public HttpMethod method() {
                return method;
            }

            @Override
            public RelativeUrl url() {
                return RelativeUrl.parse(url);
            }

            @Override
            public Map<HttpHeaderName<?>, Object> headers() {
                return Maps.one(HttpHeaderName.ACCEPT_CHARSET, AcceptCharset.parse("utf8;"));
            }

            @Override
            public byte[] body() {
                return body.clone();
            }

            @Override
            public Map<HttpRequestParameterName, List<String>> parameters() {
                final Map<HttpRequestParameterName, List<String>> parameters = Maps.sorted();

                this.url()
                        .query()
                        .parameters()
                        .entrySet()
                        .forEach(e -> parameters.put(HttpRequestParameterName.with(e.getKey().value()), e.getValue()));

                return parameters;
            }

            @Override
            public List<String> parameterValues(final HttpRequestParameterName parameterName) {
                final List<String> values = this.parameters().get(parameterName);
                return null == values ?
                        Lists.empty() :
                        values;
            }

            @Override
            public String toString() {
                return this.method() + " " + this.url() + " " + parameters();
            }
        };
    }

    @Override
    public Class<HateosHandlerBuilderRouter<JsonNode, HasJsonNode>> type() {
        return Cast.to(HateosHandlerBuilderRouter.class);
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
