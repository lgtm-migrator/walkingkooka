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

package walkingkooka.net.header;

import org.junit.Test;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.type.MemberVisibility;

import static org.junit.Assert.assertEquals;

public final class LinkRelationUrlTest extends LinkRelationTestCase<LinkRelationUrl, AbsoluteUrl> {

    private final static String TEXT = "http://example.com";

    @Test
    public void testWith() {
        final LinkRelationUrl linkRelation = this.createLinkRelation();
        assertEquals("value", AbsoluteUrl.parse(TEXT), linkRelation.value());
    }

    @Test
    public void testHeaderText() {
        this.toHeaderTextAndCheck(TEXT);
    }

    @Override
    boolean url() {
        return true;
    }

    @Override
    LinkRelationUrl createLinkRelation() {
        return LinkRelationUrl.url(TEXT);
    }

    @Override
    protected Class<LinkRelationUrl> type() {
        return LinkRelationUrl.class;
    }

    @Override
    protected final MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}