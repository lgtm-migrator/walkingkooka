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
import walkingkooka.collect.map.Maps;
import walkingkooka.net.Url;
import walkingkooka.test.HashCodeEqualsDefinedEqualityTestCase;

import java.util.Map;

public final class LinkEqualityTest extends HashCodeEqualsDefinedEqualityTestCase<Link> {

    @Test
    public void testDifferentUrl() {
        this.checkNotEquals(Link.with(Url.parse("/different")).setParameters(this.parameters()));
    }

    @Test
    public void testDifferentParameters() {
        this.checkNotEquals(Link.with(this.url()).setParameters(Maps.one(LinkParameterName.MEDIA_TYPE, MediaType.TEXT_PLAIN)));
    }

    @Test
    public void testDifferentParameters2() {
        this.checkNotEquals(Link.with(this.url()));
    }

    @Override
    protected Link createObject() {
        return Link.with(this.url()).setParameters(this.parameters());
    }

    private Url url() {
        return Url.parse("/path/file");
    }

    private Map<LinkParameterName<?>, Object> parameters() {
        return Maps.one(LinkParameterName.REL, LinkRelation.parse("previous"));
    }
}