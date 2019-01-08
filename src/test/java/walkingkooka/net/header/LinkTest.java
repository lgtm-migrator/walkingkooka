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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.RelativeUrl;
import walkingkooka.net.Url;
import walkingkooka.text.CharSequences;
import walkingkooka.type.MemberVisibility;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public final class LinkTest extends HeaderValueWithParametersTestCase<Link,
        LinkParameterName<?>> {

    @Test(expected = NullPointerException.class)
    public void testWithNullFails() {
        Link.with(null);
    }

    @Test
    public void testWithAbsoluteUrl() {
        final AbsoluteUrl url = this.value();
        this.check(Link.with(url), url, Link.NO_PARAMETERS);
    }

    @Test
    public void testWithRelativeUrl() {
        final RelativeUrl url = RelativeUrl.parse("/path/file");
        this.check(Link.with(url), url, Link.NO_PARAMETERS);
    }

    @Test(expected = NullPointerException.class)
    public void testSetValueNullFails() {
        this.createLink().setValue(null);
    }

    @Test
    public void testSetValueSame() {
        final Link link = this.createLink();
        assertSame(link, link.setValue(link.value()));
    }

    @Test
    public void testSetValueSame2() {
        final Link link = this.createLink();
        assertSame(link, link.setValue(this.value()));
    }

    @Test
    public void testSetValueDifferent() {
        final Link link = this.createLink();
        final AbsoluteUrl value = AbsoluteUrl.parse("http://example2.com");
        final Link different = link.setValue(value);
        assertNotSame(link, different);

        this.check(different, value, Link.NO_PARAMETERS);
    }

    @Test
    public final void testSetParameterDifferent() {
        final Link link = this.createLink();
        final Map<LinkParameterName<?>, Object> parameters = this.parameters();
        final Link different = link.setParameters(parameters);
        this.check(different, link.value(), parameters);
    }

    @Test
    public void testSetParametersDifferentAndBack() {
        final Link link = this.createLink();
        assertEquals(link,
                link
                        .setParameters(this.parameters())
                        .setParameters(Link.NO_PARAMETERS));
    }

    private Map<LinkParameterName<?>, Object> parameters() {
        return Maps.one(LinkParameterName.REL, LinkRelation.parse("previous"));
    }

    final void check(final Link language,
                     final Url value,
                     final Map<LinkParameterName<?>, Object> parameters) {
        assertEquals("value", value, language.value());
        assertEquals("parameters", parameters, language.parameters());
    }

    // toHeaderTextList.......................................................................................

    @Test
    public void testToHeaderTextListListOfOne() {
        this.toHeaderTextListAndCheck("<http://example.com>",
                this.createLink());
    }

    @Test
    public void testToHeaderTextListListOfOneWithParameters() {
        this.toHeaderTextListAndCheck("<http://example.com>;rel=previous",
                this.createLink().setParameters(Maps.one(LinkParameterName.REL, LinkRelation.parse("previous"))));
    }

    @Test
    public void testToHeaderTextListListOfMany() {
        this.toHeaderTextListAndCheck("<http://example.com>, <http://example2.com>",
                this.createLink(),
                Link.with(Url.parse("http://example2.com")));
    }

    // parse.......................................................................................

    @Test
    public void testParse() {
        final String text = "<http://example.com>;rel=previous, <http://example2.com>";
        assertEquals("Failed to parse " + CharSequences.quote(text),
                Lists.of(
                        this.createLink().setParameters(Maps.one(LinkParameterName.REL, LinkRelation.parse("previous"))),
                        Link.with(Url.parse("http://example2.com"))
                ),
                Link.parse(text));
    }

    // helpers.......................................................................................

    @Override
    protected Link createHeaderValueWithParameters() {
        return this.createLink();
    }

    private Link createLink() {
        return Link.with(this.value());
    }

    private AbsoluteUrl value() {
        return AbsoluteUrl.parse("http://example.com");
    }

    @Override
    protected boolean isMultipart() {
        return false;
    }

    @Override
    protected boolean isRequest() {
        return true;
    }

    @Override
    protected boolean isResponse() {
        return true;
    }

    @Override
    protected Class<Link> type() {
        return Link.class;
    }

    @Override
    protected MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }
}