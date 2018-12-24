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

import java.time.LocalDateTime;

import static org.junit.Assert.assertSame;

public final class IfRangeLastModifiedTest extends IfRangeTestCase<IfRangeLastModified, LocalDateTime, ETag> {

    @Test(expected = HeaderValueException.class)
    public void testETag() {
        this.createHeaderValue().etag();
    }

    @Test
    public void testLastModified() {
        final IfRangeLastModified ifRange = this.createHeaderValue();
        assertSame(ifRange, ifRange.lastModified());
    }

    @Override
    IfRangeLastModified createHeaderValue(final LocalDateTime value) {
        return IfRangeLastModified.lastModified(value);
    }

    @Override
    LocalDateTime value() {
        return this.lastModified();
    }

    @Override
    String headerText() {
        return HeaderValueConverter.localDateTime().toText(this.value(), HttpHeaderName.LAST_MODIFIED);
    }

    @Override
    LocalDateTime differentValue() {
        return this.value().plusYears(1);
    }

    @Override
    ETag otherValue() {
        return this.etag();
    }

    @Override
    boolean isETag() {
        return false;
    }

    @Override
    protected Class<IfRangeLastModified> type() {
        return IfRangeLastModified.class;
    }
}