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

package walkingkooka.net.http;

import org.junit.Test;

import java.time.LocalDateTime;

public final class IfRangeHttpHeaderValueConverterTest extends
        HttpHeaderValueConverterTestCase<IfRangeHttpHeaderValueConverter, IfRange<?>> {

    @Override
    protected String requiredPrefix() {
        return IfRange.class.getSimpleName();
    }

    @Test
    public void testIfRange() {
        final String header = this.etag().toHeaderText();
        this.parseAndToTextAndCheck(header, IfRange.with(this.etag()));
    }

    @Override
    protected IfRangeHttpHeaderValueConverter converter() {
        return IfRangeHttpHeaderValueConverter.INSTANCE;
    }

    @Override
    protected HttpHeaderName<IfRange<?>> name() {
        return HttpHeaderName.IF_RANGE;
    }

    @Override
    protected String invalidHeaderValue() {
        return "///";
    }

    @Override
    protected IfRange<?> value() {
        return IfRange.with(this.etag());
    }

    private HttpETag etag() {
        return HttpETag.with("abc123", HttpETagValidator.WEAK);
    }

    private LocalDateTime lastModified() {
        return LocalDateTime.of(2000, 12, 31, 6, 28, 29);
    }

    @Override
    protected String converterToString() {
        return "IfRange";
    }

    @Override
    protected Class<IfRangeHttpHeaderValueConverter> type() {
        return IfRangeHttpHeaderValueConverter.class;
    }
}
