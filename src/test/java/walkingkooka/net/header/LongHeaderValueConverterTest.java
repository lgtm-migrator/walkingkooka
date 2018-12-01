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
import walkingkooka.net.http.HttpHeaderName;

public final class LongHeaderValueConverterTest extends
        HeaderValueConverterTestCase<LongHeaderValueConverter, Long> {

    private final static String TEXT = "123";
    private final static Long VALUE = 123L;

    @Override
    protected String requiredPrefix() {
        return Long.class.getSimpleName();
    }

    @Test
    public void testContentLength() {
        this.parseAndFormatAndCheck(TEXT, VALUE);
    }

    @Override
    protected LongHeaderValueConverter converter() {
        return LongHeaderValueConverter.INSTANCE;
    }

    @Override
    protected HttpHeaderName<Long> name() {
        return HttpHeaderName.CONTENT_LENGTH;
    }

    @Override
    protected String invalidHeaderValue() {
        return "abc";
    }

    @Override
    protected Long value() {
        return VALUE;
    }

    @Override
    protected String converterToString() {
        return Long.class.getSimpleName();
    }

    @Override
    protected Class<LongHeaderValueConverter> type() {
        return LongHeaderValueConverter.class;
    }
}